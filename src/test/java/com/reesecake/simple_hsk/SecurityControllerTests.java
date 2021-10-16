package com.reesecake.simple_hsk;

import com.reesecake.simple_hsk.security.SecurityController;
import com.reesecake.simple_hsk.security.alternative.AppUser;
import com.reesecake.simple_hsk.security.alternative.AppUserRepository;
import com.reesecake.simple_hsk.security.alternative.SpringDataJpaUserDetailsService;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@WebMvcTest(controllers = SecurityController.class)
public class SecurityControllerTests {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    WebApplicationContext context;
    @MockBean
    AppUserRepository appUserRepository;
    @MockBean
    private SpringDataJpaUserDetailsService userDetailsService;

    @Test
    void shouldReturnLogin200() throws Exception {

        this.mockMvc.perform(MockMvcRequestBuilders
                        .get("/auth/login"))
                .andExpect(status().isOk());
    }

    @Test
    void shouldReturnRegister200() throws Exception {

        this.mockMvc.perform(MockMvcRequestBuilders
                        .get("/auth/register"))
                .andExpect(status().isOk());
    }

    @Test
    void shouldPOSTRegister201() throws Exception {

        Map<String, String> msgBody = new HashMap<>();
        msgBody.put("username", "POSTusername");
        msgBody.put("email", "POSTtest@simplehsk.com");
        msgBody.put("password", "password");

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/auth/register/process")
                        .content(String.valueOf(new JSONObject(msgBody)))
                        .contentType("application/json"))
                .andExpect(status().isCreated());
    }

    @Test
    void shouldPOSTRegister409() throws Exception {
        AppUser origUser = appUserRepository.save(
                new AppUser("conflictTest", "conflictTest@simplehsk.com",
                        "password", "ROLE_STUDENT")
        );

        Map<String, String> msgBody = new HashMap<>();
        msgBody.put("username", "conflictTest");
        msgBody.put("email", "conflictTest@simplehsk.com");
        msgBody.put("password", "password");

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/auth/register/process")
                        .content(String.valueOf(new JSONObject(msgBody)))
                        .contentType("application/json"))
                .andExpect(status().isConflict());
    }
}
