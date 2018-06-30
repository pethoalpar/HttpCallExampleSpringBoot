package com.pethoalpar.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class GetControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    public void withoutVariable() throws Exception{
        mvc.perform(MockMvcRequestBuilders.get("/get/withoutvariable").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("Success"));
    }

    @Test
    public void withPathVariable() throws Exception{
        LocalDateTime ldt = LocalDateTime.now();
        String dateStr = ldt.format(DateTimeFormatter.ISO_DATE_TIME);

        mvc.perform(MockMvcRequestBuilders.get("/get/withpathvariable/Alpar/"+dateStr).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("Alpar#"+dateStr));
    }

    @Test
    public void withParam() throws Exception{
        mvc.perform(MockMvcRequestBuilders.get("/get/withparam?name=Alpar").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("Alpar"));
    }
}
