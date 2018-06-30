package com.pethoalpar.controller;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
public class GetController {

    @RequestMapping(value = "/get/withoutvariable", method = RequestMethod.GET)
    @ResponseBody
    public String withoutVariable(){
        return "Success";
    }

    @RequestMapping(value = "/get/withpathvariable/{name}/{date}", method = RequestMethod.GET)
    @ResponseBody
    public String withPathVariable(@PathVariable String name, @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)LocalDateTime date){
        return name+"#"+date.format(DateTimeFormatter.ISO_DATE_TIME);
    }

    @RequestMapping(value = "/get/withparam", method = RequestMethod.GET, params = {"name"})
    @ResponseBody
    public String withParam(@RequestParam(value = "name", required = false, defaultValue = "Zero") String name){
        return name;
    }
}
