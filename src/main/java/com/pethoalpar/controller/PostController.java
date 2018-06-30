package com.pethoalpar.controller;

import com.pethoalpar.entity.User;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("/post")
public class PostController {

    @RequestMapping(value = "/withoutvariable", method = RequestMethod.POST)
    @ResponseBody
    public String withoutVariable(){
        return "Success";
    }

    @RequestMapping(value = "/withpathvariable/{name}/{date}", method = RequestMethod.POST)
    @ResponseBody
    public String withPathVariable(@PathVariable String name, @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)LocalDateTime date){
        return name+"#"+date.format(DateTimeFormatter.ISO_DATE_TIME);
    }

    @RequestMapping(value = "/withrequestbody", method = RequestMethod.POST)
    @ResponseBody
    public String withRequestBody(@RequestBody User user){
        return user.getName()+String.valueOf(user.getAge());
    }

    @PostMapping("/fileupload")
    @ResponseBody
    public String fileUpload(@RequestParam("file")MultipartFile file){
        return file.isEmpty() ? "Failed" : "Success";
    }
}
