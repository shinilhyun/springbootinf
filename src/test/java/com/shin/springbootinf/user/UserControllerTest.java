package com.shin.springbootinf.user;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
public class UserControllerTest {

    private static Logger LOGGER = LoggerFactory.getLogger(UserControllerTest.class);

    @Autowired
    MockMvc mockMvc;

    @Test
    public void hello() throws Exception {
        mockMvc.perform(get("/hello"))
            .andExpect(status().isOk())
            .andExpect(content().string("hello"));
    }

    @Test
    public void createUser_JSON() throws Exception {

        String userJson = "{\n" +
            "\"username\":\"ilhyun\",\n" +
            "  \"password\" : \"123\"\n" +
            "}";

        mockMvc.perform(post("/users/create")
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .accept(MediaType.APPLICATION_JSON_UTF8)
            .content(userJson))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.username", is(equalTo("ilhyun"))))
            .andExpect(jsonPath("$.password", is(equalTo("123"))));
    }

    @Test
    public void createUser_XML() throws Exception {

        String userJson = "{\n" +
            "\"username\":\"ilhyun\",\n" +
            "  \"password\" : \"123\"\n" +
            "}";

        mockMvc.perform(post("/users/create")
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .accept(MediaType.APPLICATION_XML)
            .content(userJson))
            .andExpect(status().isOk())
            .andExpect(xpath("/User/username").string("ilhyun"))
            .andExpect(xpath("/User/password").string("123"));
    }
}
