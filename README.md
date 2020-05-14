# springboot-swaggerBeauty
本项目演示了SpringBoot集成第三方的 swagger 来替换原生的 swagger，美化文档样式。
一.创建新的springboot项目，引入pom文件。
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>2.2.7.RELEASE</version>
        <relativePath/> <!-- lookup parent from repository -->
    </parent>
    <groupId>com.dyz</groupId>
    <artifactId>swaggerbeauty</artifactId>
    <version>0.0.1-SNAPSHOT</version>
    <name>swaggerbeauty</name>
    <description>Demo project for Spring Boot</description>

    <properties>
        <java.version>1.8</java.version>
    </properties>

    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>

        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <optional>true</optional>
        </dependency>

        <dependency>
            <groupId>com.battcn</groupId>
            <artifactId>swagger-spring-boot-starter</artifactId>
            <version>2.1.2-RELEASE</version>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
            <exclusions>
                <exclusion>
                    <groupId>org.junit.vintage</groupId>
                    <artifactId>junit-vintage-engine</artifactId>
                </exclusion>
            </exclusions>
        </dependency>
    </dependencies>

    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
        </plugins>
    </build>

</project>

二、修改application.yml：
server:
  port: 8080
  servlet:
    context-path: /demo
spring:
  swagger:
    enabled: true
    title: spring-boot-swaggerbeauty
    base-package: com.dyz.swaggerbeauty.controller
    description: 这是一个简单的 swaggerbeauty Demo
    version: 1.0.0-SNAPSHOT
    contact:
      name: dyz
      email: 137025xxx@qq.com
      url: https://blog.csdn.net/MICHAELKING1
    # swagger扫描的基础包，默认：全扫描
    # base-package:
    # 需要处理的基础URL规则，默认：/**
    # base-path:
    # 需要排除的URL规则，默认：空
    # exclude-path:
    security:
      # 是否启用 swagger 登录验证
      filter-plugin: true
      username: dyz
      password: dyz123
    global-response-messages:
      GET[0]:
        code: 400
        message: Bad Request，请求参数不对
      GET[1]:
        code: 404
        message: NOT FOUND，请求路径不对
      GET[2]:
        code: 500
        message: ERROR，程序内部错误
      POST[0]:
        code: 400
        message: Bad Request，请求参数不对
      POST[1]:
        code: 404
        message: NOT FOUND，请求路径不对
      POST[2]:
        code: 500
        message: ERROR，程序内部错误

三、配置通用API返回
/**
 * @author dyz
 * @version 1.0
 * @date 2020/5/13 15:09
 * 通用API返回
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "通用API接口返回", description = "Common Api Response")
public class ApiResponse<T> implements Serializable {
    private static final long serialVersionUID = -8987146499044811408L;
    /**
     * 通用返回状态
     */
    @ApiModelProperty(value = "通用返回状态", required = true)
    private Integer code;
    /**
     * 通用返回信息
     */
    @ApiModelProperty(value = "通用返回信息", required = true)
    private String message;
    /**
     * 通用返回数据
     */
    @ApiModelProperty(value = "通用返回数据", required = true)
    private T data;
}
 四、创建一个实体类
/**
 * @author dyz
 * @version 1.0
 * @date 2020/5/13 15:56
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "用户实体类", description = "User")
public class User implements Serializable {
    private static final long serialVersionUID = 5057954049311281252L;

    @ApiModelProperty(value = "id", required = true)
    private Integer id;

    @ApiModelProperty(value = "用户名", required = true)
    private String name;

    @ApiModelProperty(value = "性别", required = true)
    private String sex;
}
五、修改启动类
@SpringBootApplication
@EnableSwagger2
public class SwaggerbeautyApplication {

    public static void main(String[] args) {
        SpringApplication.run(SwaggerbeautyApplication.class, args);
    }

}


六、创建测试Controller
/**
 * @author dyz
 * @version 1.0
 * @date 2020/5/13 16:00
 */
@RestController
@RequestMapping("/test")
@Api(tags = "1.0", description = "用户管理", value = "用户管理")
public class TestController {
    @GetMapping
    @ApiOperation(value = "条件查询", notes = "备注：条件查询")
    @ApiImplicitParams({@ApiImplicitParam(name = "name", value = "用户名", dataType = DataType.STRING, paramType = ParamType.QUERY, defaultValue = "dyz",required = true)})
    public ApiResponse<User> getByUserName(String name) {
        System.out.println("多个参数用  @ApiImplicitParams");
        return ApiResponse.<User>builder().code(200)
                .message("操作成功")
                .data(new User(1, name, "男"))
                .build();
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "删除用户", notes = "备注：删除用户")
    @ApiImplicitParam(name = "id", value = "用户编号",  paramType = ParamType.PATH)
    public void delete(@PathVariable Integer id) {
        System.out.println("删除用户");
    }


    @PostMapping
    @ApiOperation(value = "添加用户",notes = "备注：添加用户")
    public User post(@RequestBody User user) {
        System.out.println("添加用户");
        return user;
    }

    @PostMapping("/{id}/file")
    @ApiOperation(value = "上传文件",notes = "备注：上传文件")
    public String file(@PathVariable Integer id, @RequestParam("file") MultipartFile file) {
        System.out.println(file.getContentType());
        System.out.println(file.getName());
        System.out.println(file.getOriginalFilename());
        return file.getOriginalFilename();
    }




}
七、启动项目，地址栏输入：http://localhost:8080/demo/swagger-ui.html#/1.0


 





八、源码地址：https://github.com/MichaelDYZ/springboot-swaggerbeauty
