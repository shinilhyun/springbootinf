package com.shin.springbootinf;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(SpringRunner.class)
@WebMvcTest(SampleController.class)
public class SampleControllerTest {

    @Autowired
    MockMvc mockmvc;

    @Test
    public void hello() throws Exception {
        //요청 "/"
        //응답
        //- 모델 anme : ilhyun
        // - 뷰 이름 : hello
        mockmvc.perform(get("/hello"))
            .andExpect(status().isOk())
            .andDo(print())
            .andExpect(view().name("hello"))
            .andExpect(model().attribute("name", "ilhyun"))
            .andExpect(content().string(containsString("ilhyun")));
    }

}