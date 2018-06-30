package com.pethoalpar.controller;

import com.google.gson.Gson;
import com.pethoalpar.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.fileUpload;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class PostControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    public void withoutVariable() throws Exception{
        mvc.perform(MockMvcRequestBuilders.post("/post/withoutvariable").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("Success"));
    }

    @Test
    public void withPathVariable() throws Exception{
        LocalDateTime ldt = LocalDateTime.now();
        String dateStr = ldt.format(DateTimeFormatter.ISO_DATE_TIME);

        mvc.perform(MockMvcRequestBuilders.post("/post/withpathvariable/Alpar/"+dateStr).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("Alpar#"+dateStr));
    }

    @Test
    public void withRequestBody() throws Exception{

        User user = new User();
        user.setName("Alpar");
        user.setAge(360);

        Gson gson = new Gson();
        mvc.perform(MockMvcRequestBuilders.post("/post/withrequestbody").accept(MediaType.APPLICATION_JSON).content(gson.toJson(user)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("Alpar360"));
    }

    @Test
    public void fileUpload() throws Exception{
        MockMultipartFile file = new MockMultipartFile("file", "test.txt", "text/plain", "Please subscribe!".getBytes());
        mvc.perform(multipart("/post/fileupload").file(file))
                .andExpect(status().isOk())
                .andExpect(content().string("Success"));
    }
}
