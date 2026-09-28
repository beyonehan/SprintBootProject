# Spring Boot 学习笔记

> 开始日期：2026-09-28  
> 项目目录：`spring-boot-study`  
> Java：25  
> Spring Boot：4.1.1  
> 说明：本文持续记录学习任务、操作步骤和实际问题。所有代码和命令由学习者本人操作。

## 学习进度

- [x] Task 01：创建 Spring Boot 项目
- [x] Task 02：导入 Maven 项目
- [x] Task 03：创建第一个 Controller
- [x] Task 04：解决 IDEA 包创建问题
- [x] Task 05：配置 Git 忽略规则
- [ ] Task 06：学习路径参数、查询参数和 JSON 响应
- [ ] Task 07：项目分层——Controller、Service、Repository
- [ ] Task 08：接入 MySQL 与 Spring Data JPA
- [ ] Task 09：参数校验和统一异常处理
- [ ] Task 10：编写自动化测试

---

# Task 01：从零创建 Spring Boot 项目

## 1. 学习目标

1. 理解 JDK、Maven 和 Spring Boot 的作用。
2. 使用 Spring Initializr 生成项目。
3. 在 IntelliJ IDEA 中启动应用。
4. 使用 Maven Wrapper 测试和打包项目。

## 2. 基础工具

### JDK

JDK 是 Java 开发工具包，包含 `java`、`javac`、标准库以及开发工具。

检查版本：

```bash
java -version
javac -version
```

当前项目使用 Java 25。

### Maven

Maven 负责依赖下载、编译、测试和打包。核心配置文件是：

```text
pom.xml
```

### Maven Wrapper

项目自带：

```text
mvnw
mvnw.cmd
.mvn/wrapper/maven-wrapper.properties
```

macOS 使用：

```bash
./mvnw
```

因此不需要先全局安装 Maven。

## 3. Spring Initializr 配置

访问 [Spring Initializr](https://start.spring.io/)，选择：

| 配置 | 当前选择 |
|---|---|
| Project | Maven |
| Language | Java |
| Spring Boot | 4.1.1 |
| Group | `com.Shuan` |
| Artifact | `spring-boot-study` |
| Packaging | Jar |
| Java | 25 |
| Dependency | Spring Web MVC |

不要选择带 `SNAPSHOT`、`M` 或 `RC` 的预览版本。

## 4. 当前目录结构

```text
SprintBootProject/
├── SpringBoot学习笔记.md
├── README.md
└── spring-boot-study/
    ├── .mvn/
    ├── mvnw
    ├── mvnw.cmd
    ├── pom.xml
    └── src/
```

外层目录用于保存学习笔记，内层 `spring-boot-study` 才是真正的 Maven 项目。

## 5. Maven 常用命令

在 `spring-boot-study` 中执行：

```bash
./mvnw -version
./mvnw test
./mvnw clean package
```

打包成功后，Jar 位于：

```text
target/
```

运行 Jar 的命令格式：

```bash
java -jar target/实际生成的文件名.jar
```

---

# Task 02：正确导入 Maven 项目

## 1. 实际问题

最初 IDEA 打开的是外层 `SprintBootProject`，而 `pom.xml` 位于：

```text
spring-boot-study/pom.xml
```

IDEA 没有把内层目录识别成 Maven 模块，因此不会自动下载依赖。

## 2. 推荐导入方式

在 IDEA 中选择：

```text
File → Close Project
```

然后使用 `Open` 打开：

```text
/Users/hanli/Desktop/Study/backend/SprintBootProject/spring-boot-study
```

也可以在外层项目中右键 `spring-boot-study/pom.xml`：

```text
Add as Maven Project
```

## 3. 检查是否导入成功

打开：

```text
View → Tool Windows → Maven
```

应当看到：

```text
spring-boot-study
├── Lifecycle
├── Plugins
└── Dependencies
```

同时确认：

- Maven 没有启用 Offline Mode。
- Project SDK 是 25。
- `SpringApplication` 不再标红。
- 启动类左侧出现绿色运行按钮。

---

# Task 03：创建第一个 HTTP 接口

## 1. 为什么首页最初显示 Error Page

应用成功启动并不代表已经存在首页。

项目最初只有启动类，没有任何 Controller，所以访问：

```text
http://localhost:8080/
```

会得到 404 Error Page。

这表示服务器收到了请求，但没有找到处理 `/` 的映射。

## 2. 启动类

```java
package com.Shuan.spring_boot_study;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringBootStudyApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootStudyApplication.class, args);
    }
}
```

`@SpringBootApplication` 会启用自动配置，并从当前包开始扫描子包中的 Spring 组件。

## 3. Controller 位置

```text
src/main/java/com/Shuan/spring_boot_study/controller/HelloController.java
```

当前代码：

```java
package com.Shuan.spring_boot_study.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/")
    public String hello() {
        return "hello";
    }
}
```

### 注解含义

- `@RestController`：声明该类处理 HTTP 请求，返回值直接写入响应体。
- `@GetMapping("/")`：将 GET `/` 映射到 `hello()` 方法。

## 4. 验证

重新启动应用后访问：

```text
http://localhost:8080/
```

或执行：

```bash
curl -i http://localhost:8080/
```

预期状态码为 200，响应内容为：

```text
hello
```

## 5. 实际错误：RestContoller

曾出现：

```text
java: cannot find symbol
  symbol: class RestContoller
```

原因是把：

```java
@RestController
```

误写成：

```java
@RestContoller
```

`Controller` 少了一个 `r`。

`cannot find symbol` 常见原因：

- 类、方法或变量拼写错误。
- 大小写错误。
- 缺少 import。
- 依赖没有导入。
- 使用了不存在的名称。

---

# Task 04：IDEA 中没有 New Package

`New → Package` 只会在 Java Sources Root 或已有 Java 包上出现。

正确的右键位置：

```text
src/main/java/com/Shuan/spring_boot_study
```

如果仍然没有 Package：

1. 右键 `src/main/java`。
2. 选择 `Mark Directory as → Sources Root`。
3. 确认 `java` 目录变成蓝色。
4. 再右键 `spring_boot_study`。

也可以右键 `src/main/java`，选择 `New → Java Class`，输入完整类名：

```text
com.Shuan.spring_boot_study.controller.HelloController
```

如果连 Java Class 也没有，说明 Maven/Java 模块尚未正确导入。

---

# Task 05：Git 提交与忽略规则

## 1. 应该提交

```text
pom.xml
mvnw
mvnw.cmd
.mvn/wrapper/maven-wrapper.properties
src/main/
src/test/
SpringBoot学习笔记.md
README.md
.gitignore
```

## 2. 不应该提交

```text
.idea/
target/
*.class
*.log
```

## 3. 根目录忽略规则

根目录 `.gitignore` 应包含：

```gitignore
# IntelliJ IDEA
/.idea/
*.iml
```

规则前不能添加用于排版的空格。曾经错误写成：

```gitignore
 /.idea/
  *.iml
```

前导空格会导致规则无法匹配。

验证命令：

```bash
git status --short
git check-ignore -v .idea/workspace.xml
```

内层 `spring-boot-study/.gitignore` 已经忽略 `target/`。

---

# Task 06：路径参数、查询参数和 JSON 响应

## 1. 本任务目标

完成本任务后，你应当能够：

1. 区分固定路径、路径参数和查询参数。
2. 使用 `@PathVariable` 接收路径参数。
3. 使用 `@RequestParam` 接收查询参数。
4. 使用 Java `record` 定义简单响应对象。
5. 让 Spring Boot 自动返回 JSON。

## 2. 接口形式对比

| 类型 | 示例 | 适用场景 |
|---|---|---|
| 固定路径 | `/api/hello` | 执行固定功能 |
| 路径参数 | `/api/users/10` | 定位编号为 10 的用户 |
| 查询参数 | `/api/hello?name=Han` | 筛选条件或可选输入 |

## 3. 创建新的 ApiController

在以下包中创建 `ApiController`：

```text
src/main/java/com/Shuan/spring_boot_study/controller/ApiController.java
```

先输入基础结构：

```java
package com.Shuan.spring_boot_study.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ApiController {
}
```

`@RequestMapping("/api")` 为这个类中的所有接口添加统一前缀。

## 4. 固定路径接口

在类中添加：

```java
@GetMapping("/hello")
public String hello() {
    return "Hello API";
}
```

完整路径由类路径和方法路径组成：

```text
/api + /hello = /api/hello
```

重启后访问：

```text
http://localhost:8080/api/hello
```

## 5. 查询参数

添加：

```java
@GetMapping("/greeting")
public String greeting(@RequestParam String name) {
    return "Hello, " + name;
}
```

访问：

```text
http://localhost:8080/api/greeting?name=Han
```

预期：

```text
Hello, Han
```

这里：

- `?` 表示查询字符串开始。
- `name` 是参数名。
- `Han` 是参数值。
- `@RequestParam` 将 URL 参数转换成 Java 方法参数。

如果不传 `name`，默认会返回 400，因为该参数默认必填。

### 设置默认值

将方法改成：

```java
@GetMapping("/greeting")
public String greeting(
        @RequestParam(defaultValue = "Spring") String name) {
    return "Hello, " + name;
}
```

现在访问 `/api/greeting` 时，会使用默认值 `Spring`。

## 6. 路径参数

添加：

```java
@GetMapping("/users/{id}")
public String findUser(@PathVariable Long id) {
    return "正在查询用户：" + id;
}
```

访问：

```text
http://localhost:8080/api/users/10
```

预期：

```text
正在查询用户：10
```

`{id}` 是路径占位符，`@PathVariable` 将它转换成方法参数。

如果访问 `/api/users/abc`，Spring 无法把 `abc` 转为 `Long`，通常会返回 400。

## 7. 返回 JSON

在 `controller` 包旁创建 `dto` 包：

```text
com.Shuan.spring_boot_study.dto
```

创建 `GreetingResponse.java`：

```java
package com.Shuan.spring_boot_study.dto;

public record GreetingResponse(
        String message,
        String name
) {
}
```

`record` 适合保存简单、不可变的数据。它会自动生成构造方法和访问方法。

在 `ApiController` 中导入：

```java
import com.Shuan.spring_boot_study.dto.GreetingResponse;
```

然后添加：

```java
@GetMapping("/greeting-json")
public GreetingResponse greetingJson(
        @RequestParam(defaultValue = "Spring") String name) {
    return new GreetingResponse("Hello, " + name, name);
}
```

访问：

```text
http://localhost:8080/api/greeting-json?name=Han
```

预期 JSON：

```json
{
  "message": "Hello, Han",
  "name": "Han"
}
```

Spring Web MVC 会使用 JSON 转换组件，将 Java 对象自动序列化为 JSON。

## 8. 建议最终代码

```java
package com.Shuan.spring_boot_study.controller;

import com.Shuan.spring_boot_study.dto.GreetingResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ApiController {

    @GetMapping("/hello")
    public String hello() {
        return "Hello API";
    }

    @GetMapping("/greeting")
    public String greeting(
            @RequestParam(defaultValue = "Spring") String name) {
        return "Hello, " + name;
    }

    @GetMapping("/users/{id}")
    public String findUser(@PathVariable Long id) {
        return "正在查询用户：" + id;
    }

    @GetMapping("/greeting-json")
    public GreetingResponse greetingJson(
            @RequestParam(defaultValue = "Spring") String name) {
        return new GreetingResponse("Hello, " + name, name);
    }
}
```

## 9. 验证命令

保持应用运行，依次执行：

```bash
curl -i http://localhost:8080/api/hello
```

```bash
curl -i 'http://localhost:8080/api/greeting?name=Han'
```

```bash
curl -i http://localhost:8080/api/users/10
```

```bash
curl -i 'http://localhost:8080/api/greeting-json?name=Han'
```

URL 中包含 `?` 时建议使用单引号，避免终端对特殊字符进行解释。

## 10. 独立练习

### 练习 1：加法接口

实现：

```text
GET /api/add?a=10&b=20
```

返回：

```text
30
```

提示：参数类型可以使用 `int`，两个参数都使用 `@RequestParam`。

### 练习 2：商品路径参数

实现：

```text
GET /api/products/100
```

返回 JSON：

```json
{
  "id": 100,
  "name": "学习商品"
}
```

自行创建 `ProductResponse` record。

### 练习 3：观察错误

分别访问：

```text
/api/greeting
/api/users/abc
/api/not-exists
```

记录每个请求的 HTTP 状态码，并解释原因。

## 11. 完成检查

- [ ] 能解释固定路径、路径参数和查询参数的区别。
- [ ] `/api/hello` 返回字符串。
- [ ] `/api/greeting?name=Han` 能读取查询参数。
- [ ] `/api/users/10` 能读取路径参数。
- [ ] `/api/greeting-json` 返回 JSON。
- [ ] 能解释为什么对象会被转换成 JSON。
- [ ] 完成加法接口练习。
- [ ] 完成商品 JSON 练习。

## 12. 学习记录

```text
完成日期：

遇到的问题：

错误信息：

解决方法：

路径参数和查询参数的区别：

我对 JSON 序列化的理解：
```

---

# 官方资料

- [Spring Boot](https://spring.io/projects/spring-boot)
- [Spring Boot Reference](https://docs.spring.io/spring-boot/)
- [Spring Initializr](https://start.spring.io/)
- [Spring Web MVC](https://docs.spring.io/spring-framework/reference/web/webmvc.html)
