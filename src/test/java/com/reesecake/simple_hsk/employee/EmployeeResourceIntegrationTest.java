package com.reesecake.simple_hsk.employee;

import com.reesecake.simple_hsk.AbstractWebIntegrationTest;
import com.reesecake.simple_hsk.Employee;
import com.reesecake.simple_hsk.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.MediaTypes;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import javax.transaction.Transactional;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class EmployeeResourceIntegrationTest extends AbstractWebIntegrationTest {

    @Autowired
    EmployeeRepository repository;

    @Test
    void exposesEmployeesResourceViaRootResource() throws Exception {

        mockMvc.perform(get("/api"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaTypes.HAL_JSON))
                .andExpect(jsonPath("$._links.employees.href", notNullValue()));
    }

    @Test
    @Transactional  // to rollback changes
    public void shouldReturnMonthAggregate() throws Exception {
        repository.deleteAll();
        repository.save(new Employee("Bilbo", "Baggins", "burglar"));

        this.mockMvc.perform(MockMvcRequestBuilders
                .get("/api/employees").contentType(MediaTypes.HAL_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$._embedded.employees").isNotEmpty())
                .andExpect(jsonPath("$._embedded.employees.[0].firstName", is("Bilbo")))
                .andExpect(jsonPath("$._embedded.employees.[0].lastName", is("Baggins")))
                .andExpect(jsonPath("$._embedded.employees.[0].description", is("burglar")));
    }
}
