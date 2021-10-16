package com.reesecake.simple_hsk;

import com.reesecake.simple_hsk.security.alternative.SpringDataJpaUserDetailsService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.context.WebApplicationContext;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@WebMvcTest(controllers = HomeController.class)
public class HomeControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    WebApplicationContext context;

    @MockBean
    private SpringDataJpaUserDetailsService userDetailsService;

    @Test
    void shouldReturnIndex200() throws Exception {

        this.mockMvc.perform(MockMvcRequestBuilders
                        .get("/"))
                .andExpect(status().isOk());
    }

    @Test
    void shouldReturnAbout200() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders
                        .get("/about"))
                .andExpect(status().isOk());
    }

    @Test
    void shouldReturnVocabListsRoot200() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders
                        .get("/vocab-lists"))
                .andExpect(status().isOk());
    }

    @Test
    void shouldReturnAllHskLists200() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders
                        .get("/hsk/1"))
                .andExpect(status().isOk());
        this.mockMvc.perform(MockMvcRequestBuilders
                        .get("/hsk/2"))
                .andExpect(status().isOk());
        this.mockMvc.perform(MockMvcRequestBuilders
                        .get("/hsk/3"))
                .andExpect(status().isOk());
        this.mockMvc.perform(MockMvcRequestBuilders
                        .get("/hsk/4"))
                .andExpect(status().isOk());
        this.mockMvc.perform(MockMvcRequestBuilders
                        .get("/hsk/5"))
                .andExpect(status().isOk());
        this.mockMvc.perform(MockMvcRequestBuilders
                        .get("/hsk/6"))
                .andExpect(status().isOk());
    }

    @Test
    void shouldReturnQuiz200() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders
                        .get("/quiz"))
                .andExpect(status().isOk());
    }
}
