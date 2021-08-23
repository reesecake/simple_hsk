package com.reesecake.simple_hsk;

import org.junit.jupiter.api.Test;
import org.springframework.hateoas.MediaTypes;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class SimpleHskWebIntegrationTests extends AbstractWebIntegrationTest {

    @Test
    void shouldReturnApiLinks() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders
                        .get("/api").contentType(MediaTypes.HAL_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$._links").isNotEmpty());
    }

    @Test
    void shouldFindProfileInApiLinks() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders
                        .get("/api").contentType(MediaTypes.HAL_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$._links").isNotEmpty())

                .andExpect(jsonPath("$._links.profile").isNotEmpty())
                .andExpect(jsonPath("$._links.profile.href", is("http://localhost/api/profile")));
    }

    @Test
    void shouldFindVocabsInApiLinks() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders
                        .get("/api").contentType(MediaTypes.HAL_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$._links").isNotEmpty())

                .andExpect(jsonPath("$._links.vocabs").isNotEmpty())
                .andExpect(jsonPath("$._links.vocabs.href", is("http://localhost/api/vocabs{?page,size,sort}")));
    }
}
