<h1>Http call and parameters in spring boot rest controller</h1>

<h3>Add dependency</h3>

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>com.pethoalpar</groupId>
    <artifactId>http.call.example</artifactId>
    <version>1.0-SNAPSHOT</version>

    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>2.0.3.RELEASE</version>
    </parent>

    <dependencies>
        <dependency>
            <groupId>com.google.code.gson</groupId>
            <artifactId>gson</artifactId>
            <version>2.8.5</version>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
        </dependency>
    </dependencies>

    <properties>
            <java.version>1.8</java.version>
    </properties>
</project>
```

<h3>Add main class</h3>

```java
@SpringBootApplication
public class Application {

    public static void main(String [] args){
        SpringApplication.run(Application.class, args);
    }
}
```

<h3>Add get controller</h3>

```java
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
```

<h3>Add unit test for the get controller</h3>

```java
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
```

<h3>Add post controller</h3>

```java
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
```

<h3>Add unit test for the post controller</h3>

```java
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
```
