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
- [x] Task 06：学习路径参数、查询参数和 JSON 响应
- [x] Task 07：项目分层——Controller、Service、Repository
- [x] Task 08：接入 MySQL 与 Spring Data JPA
- [x] Task 09：参数校验和统一异常处理
- [ ] Task 10：更新和删除用户，完成 CRUD
- [ ] Task 11：使用响应 DTO 隔离 API 和实体
- [ ] Task 12：分页、排序和搜索
- [ ] Task 13：编写 Service 和 Controller 自动化测试
- [ ] Task 14：编写 Repository 集成测试
- [ ] Task 15：使用 Flyway 管理数据库迁移
- [ ] Task 16：多环境配置与敏感信息管理
- [ ] Task 17：扩展用户业务模型
- [ ] Task 18：Spring Security 与 JWT 认证
- [ ] Task 19：Swagger、Docker 与部署

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

## 4. IDEA 看不到代码变化的实际排查记录

本次命令行检查结果：

```text
Git 仓库根目录：/Users/hanli/Desktop/Study/backend/SprintBootProject
git status：干净，没有未提交修改
当前分支：main
最新提交：d61ca0f
```

最新提交已经包含：

```text
ApiController.java
HelloController.java
GreetingResponse.java
```

因此，IDEA 的 `Local Changes` 没有内容是正常的。`Local Changes` 只显示工作区中相对于最新提交尚未提交的修改，不会把已经提交的代码继续显示为变化。

要查看已经提交的代码，应打开：

```text
View → Tool Windows → Git → Log
```

然后选择提交：

```text
d61ca0f
```

就能查看该提交修改了哪些文件。

### IDEA 项目目录与 Git 根目录

当前 Git 仓库位于外层：

```text
SprintBootProject/.git
```

Spring Boot Maven 项目位于内层：

```text
SprintBootProject/spring-boot-study
```

如果 IDEA 只打开内层 `spring-boot-study`，Git 根目录位于项目目录之外，IDEA 可能不会自动关联它。

推荐方式是让 IDEA 打开外层：

```text
/Users/hanli/Desktop/Study/backend/SprintBootProject
```

然后把内层 `spring-boot-study/pom.xml` 添加为 Maven 项目。这样 IDEA 可以同时看到：

- 外层 Git 仓库
- 学习笔记
- 内层 Maven 项目
- Spring Boot 源码

### 手动配置 Git 目录映射

如果 Git 工具窗口中看不到 Log 或分支信息，打开：

```text
Settings
→ Version Control
→ Directory Mappings
```

检查是否存在：

```text
Directory: /Users/hanli/Desktop/Study/backend/SprintBootProject
VCS: Git
```

如果没有，由学习者手动使用 `+` 添加该目录，并选择 `Git`。

有些 IDEA 版本也可以使用：

```text
VCS → Enable Version Control Integration → Git
```

### 验证 IDEA 是否能显示新变化

不要为了测试而随意修改业务逻辑。可以在 `HelloController.java` 的空白处临时增加一行注释，例如：

```java
// Git change test
```

保存后先在命令行检查：

```bash
git status --short
```

应该出现类似：

```text
M spring-boot-study/src/main/java/com/Shuan/spring_boot_study/controller/HelloController.java
```

再打开 IDEA：

```text
View → Tool Windows → Commit
```

或：

```text
View → Tool Windows → Git → Local Changes
```

应该能看到相同文件。验证后可以由学习者删除测试注释。

### Git 窗口的三个概念

| 位置 | 显示内容 |
|---|---|
| Local Changes / Commit | 尚未提交的修改 |
| Git Log | 已经完成的历史提交 |
| Branches | 本地和远程分支 |

命令行完成 `git add` 不会让变化消失，只会把它放入暂存区；完成 `git commit` 后，本地变化才会从 Local Changes 中消失并进入 Git Log。

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

# Task 07：Controller、Service、Repository 分层

## 1. 本任务目标

上一阶段把全部逻辑直接写在 Controller 中。代码较少时可以运行，但随着业务增加，Controller 会越来越臃肿。

本阶段使用一个暂存在内存中的用户列表，学习：

1. Controller、Service、Repository 各自负责什么。
2. 什么是 Spring Bean。
3. 如何使用构造器注入。
4. 如何通过 `Optional` 表达“可能找不到”。
5. 如何用 `ResponseEntity` 返回 200 或 404。

本阶段暂时不连接数据库。先理解分层关系，再在 Task 08 中将内存 Repository 替换为数据库 Repository。

## 2. 三层职责

```text
HTTP 请求
    ↓
Controller：接收请求、读取参数、返回 HTTP 响应
    ↓
Service：处理业务规则
    ↓
Repository：读取或保存数据
```

各层职责：

| 层 | 负责 | 不应该负责 |
|---|---|---|
| Controller | HTTP 路径、参数、状态码 | 复杂业务规则、数据库细节 |
| Service | 业务流程和规则 | HTTP 路径、数据库连接细节 |
| Repository | 数据查询与保存 | HTTP 响应、页面展示 |

## 3. 本任务最终目录

你将手动创建：

```text
com/Shuan/spring_boot_study/
├── controller/
│   ├── ApiController.java
│   ├── HelloController.java
│   └── UserController.java
├── model/
│   └── User.java
├── repository/
│   └── UserRepository.java
└── service/
    └── UserService.java
```

创建顺序建议为：

```text
User → UserRepository → UserService → UserController
```

这样每一层依赖的类型都已经存在，错误更容易理解。

## 4. 第一步：创建 User 模型

在 `com.Shuan.spring_boot_study` 下创建 `model` 包，再创建：

```text
User.java
```

填写：

```java
package com.Shuan.spring_boot_study.model;

public record User(
        Long id,
        String name
) {
}
```

### 为什么使用 record

用户数据目前只有 `id` 和 `name`。使用 record 可以自动生成：

- 构造方法
- `id()`
- `name()`
- `equals()`
- `hashCode()`
- `toString()`

当前的 `User` 只是内存数据模型，还不是数据库实体，因此暂时不添加 `@Entity`。

## 5. 第二步：创建 Repository

创建 `repository` 包，再创建：

```text
UserRepository.java
```

填写：

```java
package com.Shuan.spring_boot_study.repository;

import com.Shuan.spring_boot_study.model.User;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class UserRepository {

    private final List<User> users = List.of(
            new User(1L, "Alice"),
            new User(2L, "Bob"),
            new User(3L, "Charlie")
    );

    public List<User> findAll() {
        return users;
    }

    public Optional<User> findById(Long id) {
        return users.stream()
                .filter(user -> user.id().equals(id))
                .findFirst();
    }
}
```

### @Repository

```java
@Repository
```

告诉 Spring：这个类是数据访问组件。应用启动时，Spring 会创建并管理它的实例。

被 Spring 创建和管理的对象通常称为 Spring Bean。

### List.of

```java
List.of(...)
```

创建一个不可修改的内存列表。本阶段每次重启应用，数据都会恢复为代码中的三位用户。

### Optional

```java
Optional<User>
```

表示查询结果可能包含一个用户，也可能什么都没有。它比直接返回 `null` 更明确。

### Stream 查询过程

```java
users.stream()
        .filter(user -> user.id().equals(id))
        .findFirst();
```

可以理解为：

1. 依次读取列表中的用户。
2. 只保留 ID 与参数相等的用户。
3. 返回第一个匹配项。
4. 没有匹配项时返回空的 Optional。

## 6. 第三步：创建 Service

创建 `service` 包，再创建：

```text
UserService.java
```

填写：

```java
package com.Shuan.spring_boot_study.service;

import com.Shuan.spring_boot_study.model.User;
import com.Shuan.spring_boot_study.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }
}
```

### @Service

```java
@Service
```

表示该类承担业务逻辑职责。Spring 会将它注册为 Bean。

### 构造器注入

```java
public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
}
```

`UserService` 需要 `UserRepository` 才能工作。Spring 创建 Service 时，会找到已经管理的 Repository 并传入构造方法。

这称为依赖注入。

当前类只有一个构造方法，因此不需要额外写 `@Autowired`。

构造器注入的优点：

- 依赖关系明确。
- 字段可以声明为 `final`。
- 对象创建后依赖不会被随意替换。
- 更容易编写测试。

初学阶段不要使用字段注入：

```java
// 不推荐
@Autowired
private UserRepository userRepository;
```

## 7. 第四步：创建 Controller

在现有 `controller` 包中创建：

```text
UserController.java
```

填写：

```java
package com.Shuan.spring_boot_study.controller;

import com.Shuan.spring_boot_study.model.User;
import com.Shuan.spring_boot_study.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<User> findAll() {
        return userService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> findById(@PathVariable Long id) {
        Optional<User> user = userService.findById(id);

        if (user.isPresent()) {
            return ResponseEntity.ok(user.get());
        }

        return ResponseEntity.notFound().build();
    }
}
```

### 路径组合

类上的：

```java
@RequestMapping("/api/users")
```

与方法上的映射组合：

| 方法映射 | 最终路径 |
|---|---|
| `@GetMapping` | `GET /api/users` |
| `@GetMapping("/{id}")` | `GET /api/users/{id}` |

### ResponseEntity

`ResponseEntity` 允许 Controller 同时控制响应体和 HTTP 状态码：

```java
ResponseEntity.ok(user.get())
```

返回 HTTP 200 和用户 JSON。

```java
ResponseEntity.notFound().build()
```

返回 HTTP 404，不返回响应体。

## 8. 第五步：启动并验证

停止旧进程并重新运行应用，然后验证全部用户：

```bash
curl -i http://localhost:8080/api/users
```

预期状态码：

```text
HTTP/1.1 200
```

预期 JSON 类似：

```json
[
  {
    "id": 1,
    "name": "Alice"
  },
  {
    "id": 2,
    "name": "Bob"
  },
  {
    "id": 3,
    "name": "Charlie"
  }
]
```

查询存在的用户：

```bash
curl -i http://localhost:8080/api/users/2
```

预期：

```text
HTTP/1.1 200
```

```json
{
  "id": 2,
  "name": "Bob"
}
```

查询不存在的用户：

```bash
curl -i http://localhost:8080/api/users/99
```

预期：

```text
HTTP/1.1 404
```

## 9. 调用链

以 `GET /api/users/2` 为例：

```text
浏览器或 curl
    ↓
UserController.findById(2)
    ↓
UserService.findById(2)
    ↓
UserRepository.findById(2)
    ↓
从内存 List 中找到 Bob
    ↓
Optional<User>
    ↓
ResponseEntity 200 + User JSON
```

Controller 不知道数据具体存在哪里，只调用 Service；Service 不处理 HTTP 状态码，只调用 Repository；Repository 专注于查找数据。

## 10. 常见错误

### NoSuchBeanDefinitionException

可能原因：

- 忘记给 Repository 添加 `@Repository`。
- 忘记给 Service 添加 `@Service`。
- 类不在启动类包的子包中。

### Ambiguous mapping

表示两个 Controller 注册了相同的 HTTP 方法和路径。

当前 `ApiController` 中已有：

```text
GET /api/user/{id}
```

新接口是：

```text
GET /api/users/{id}
```

一个是 `user`，一个是 `users`，所以不会冲突。后续整理接口时可以删除或重构旧的练习接口，但本任务不要求现在删除。

### 访问 /api/users 得到 404

检查：

1. `UserController` 是否位于 `controller` 包。
2. 类上是否有 `@RestController`。
3. 类上路径是否为 `/api/users`。
4. 新建类后是否重新启动应用。

### 代码显示 Optional 找不到

确认导入：

```java
import java.util.Optional;
```

不要错误导入其他包中的同名类型。

## 11. 独立练习

### 练习 1：按名称查询

目标接口：

```text
GET /api/users/search?name=Alice
```

建议步骤：

1. Repository 添加 `findByName(String name)`。
2. Service 添加对应方法。
3. Controller 使用 `@RequestParam` 接收名称。
4. 找到时返回 200，找不到时返回 404。

可以使用：

```java
user.name().equalsIgnoreCase(name)
```

### 练习 2：Service 增加业务规则

在 `findById` 中规定：如果 `id <= 0`，直接返回空 Optional，不再调用 Repository。

思考：为什么这种规则更适合放在 Service，而不是 Repository？

### 练习 3：断点观察调用链

分别在以下方法左侧添加断点：

```text
UserController.findById
UserService.findById
UserRepository.findById
```

使用 Debug 模式启动，访问 `/api/users/2`，观察程序进入方法的顺序和 `id` 的值。

## 12. 完成检查

- [ ] 创建 `User` record。
- [ ] 创建带 `@Repository` 的 `UserRepository`。
- [ ] 创建带 `@Service` 的 `UserService`。
- [ ] 创建带 `@RestController` 的 `UserController`。
- [ ] 使用构造器注入，没有使用字段注入。
- [ ] `GET /api/users` 返回用户数组。
- [ ] `GET /api/users/2` 返回 Bob 和 200。
- [ ] `GET /api/users/99` 返回 404。
- [ ] 能画出 Controller → Service → Repository 调用链。
- [ ] 能解释 Spring Bean 和依赖注入。

## 13. 学习记录

```text
完成日期：

遇到的问题：

错误信息：

解决方法：

我对三层职责的理解：

我对依赖注入的理解：

为什么使用 Optional：
```

---

# Task 08：接入 MySQL 与 Spring Data JPA

## 1. 当前状态检查

Task 07 已经完成，项目中存在：

```text
model/User.java
respository/UserRepository.java
service/UserService.java
controller/UserController.java
```

当前 Repository 使用 Java `List` 保存数据。应用关闭后，数据不会真正持久化。

本任务会把调用链从：

```text
Controller → Service → 内存 List
```

改成：

```text
Controller → Service → Spring Data JPA → Hibernate → JDBC → MySQL
```

## 2. 本任务目标

完成后应当能够：

1. 理解 JDBC、JPA、Hibernate 和 Spring Data JPA 的关系。
2. 为项目添加 JPA 和 MySQL 驱动。
3. 配置 Spring Boot 数据源。
4. 把普通 Java 模型改成 JPA Entity。
5. 使用 `JpaRepository` 完成基本 CRUD。
6. 通过 POST 请求把数据保存到 MySQL。
7. 重启应用后确认数据仍然存在。

## 3. 先理解技术关系

```text
业务代码
   ↓
Spring Data JPA：提供 Repository 接口和常用 CRUD 方法
   ↓
JPA：定义对象关系映射规范
   ↓
Hibernate：Spring Boot 默认使用的 JPA 实现
   ↓
JDBC Driver：将 Java 数据库操作转换为 MySQL 协议
   ↓
MySQL
```

- JDBC 是 Java 连接关系型数据库的基础接口。
- JPA 是对象与数据库表之间映射的规范。
- Hibernate 实现 JPA。
- Spring Data JPA 在 JPA 之上进一步减少 Repository 样板代码。

Spring Boot 官方文档说明，`spring-boot-starter-data-jpa` 会提供 Hibernate、Spring Data JPA 和 Spring ORM。

## 4. 阶段一：修正 repository 包名

当前包名写成了：

```text
respository
```

正确拼写是：

```text
repository
```

虽然拼错后只要 import 一致，Java 仍然可以运行，但长期保留会降低可读性。

### 使用 IDEA 重命名

在 IDEA 中右键：

```text
com.Shuan.spring_boot_study.respository
```

选择：

```text
Refactor → Rename
```

改为：

```text
repository
```

使用 Refactor 而不是直接修改文件夹名称，因为 IDEA 会同时更新引用它的 import。

完成后检查 `UserService.java`：

```java
import com.Shuan.spring_boot_study.repository.UserRepository;
```

## 5. 阶段二：确认 MySQL 环境

在终端中执行：

```bash
mysql --version
```

如果能显示 MySQL 版本，继续检查服务和登录。

尝试登录：

```bash
mysql -u root -p
```

命令会提示输入密码。终端输入密码时通常不会显示字符，这是正常的。

如果出现：

```text
command not found: mysql
```

说明 MySQL 客户端没有安装，或者没有加入 PATH。可以使用 MySQL 官方 macOS 安装包安装 MySQL Community Server：

```text
https://dev.mysql.com/downloads/mysql/
```

安装时记录自己设置的 root 密码。不要把真实密码写进学习笔记或 Git。

## 6. 阶段三：创建数据库和专用用户

登录 MySQL：

```bash
mysql -u root -p
```

登录成功后，在 MySQL 提示符中执行：

```sql
CREATE DATABASE spring_boot_study
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_0900_ai_ci;
```

创建应用专用账号。将示例密码替换成自己设置的本地密码：

```sql
CREATE USER 'spring_user'@'localhost'
    IDENTIFIED BY '替换成你自己的密码';
```

授予这个账号访问学习数据库的权限：

```sql
GRANT ALL PRIVILEGES
    ON spring_boot_study.*
    TO 'spring_user'@'localhost';
```

刷新权限：

```sql
FLUSH PRIVILEGES;
```

查看数据库：

```sql
SHOW DATABASES;
```

退出：

```sql
EXIT;
```

### 为什么不直接让应用使用 root

root 拥有过高权限。给应用创建独立账号，可以把权限限制在指定数据库中，是更好的安全习惯。

## 7. 阶段四：添加 Maven 依赖

打开：

```text
spring-boot-study/pom.xml
```

在 `<dependencies>` 内加入：

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-jpa</artifactId>
</dependency>

<dependency>
    <groupId>com.mysql</groupId>
    <artifactId>mysql-connector-j</artifactId>
    <scope>runtime</scope>
</dependency>
```

不要手动填写依赖版本。Spring Boot 的依赖管理会选择与当前 Spring Boot 版本兼容的版本。

保存 `pom.xml` 后，在 Maven 工具窗口点击：

```text
Reload All Maven Projects
```

等待依赖下载完成。

### 两个依赖的作用

| 依赖 | 作用 |
|---|---|
| `spring-boot-starter-data-jpa` | 提供 Spring Data JPA、Hibernate、事务和 JDBC 支持 |
| `mysql-connector-j` | 提供连接 MySQL 的 JDBC 驱动 |

## 8. 阶段五：配置数据源

打开：

```text
src/main/resources/application.properties
```

保留应用名称，并添加：

```properties
spring.application.name=spring-boot-study

spring.datasource.url=jdbc:mysql://localhost:3306/spring_boot_study?useUnicode=true&characterEncoding=utf8&serverTimezone=Asia/Shanghai
spring.datasource.username=${DB_USERNAME:spring_user}
spring.datasource.password=${DB_PASSWORD}

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.open-in-view=false
```

### 配置解释

```properties
spring.datasource.url=...
```

指定数据库地址、端口和数据库名称。

```properties
spring.datasource.username=${DB_USERNAME:spring_user}
```

优先读取环境变量 `DB_USERNAME`；未提供时使用 `spring_user`。

```properties
spring.datasource.password=${DB_PASSWORD}
```

必须从环境变量 `DB_PASSWORD` 读取密码，避免把密码提交到 Git。

```properties
spring.jpa.hibernate.ddl-auto=update
```

学习阶段允许 Hibernate 根据 Entity 更新表结构。生产项目通常改用 Flyway 或 Liquibase 管理数据库迁移，不依赖 `update`。

```properties
spring.jpa.show-sql=true
```

在控制台显示 Hibernate 执行的 SQL，方便学习。

```properties
spring.jpa.open-in-view=false
```

避免在 Web 层隐式保持 EntityManager。本项目实体关系简单，可以明确关闭。

## 9. 阶段六：在 IDEA 中配置密码环境变量

打开运行配置：

```text
Run → Edit Configurations
```

选择 `SpringBootStudyApplication`，在 Environment variables 中添加：

```text
DB_PASSWORD=你创建 spring_user 时设置的密码
```

如果账号不是 `spring_user`，同时添加：

```text
DB_USERNAME=实际账号
```

不要把密码写到：

- `application.properties`
- Markdown 笔记
- Git 提交消息
- Java 源码

IDEA 的本地运行配置通常保存在被忽略的 `.idea` 中，但提交前仍应使用 `git diff` 检查是否意外写入密码。

## 10. 阶段七：把 User record 改成 JPA Entity

JPA Entity 需要可供框架使用的无参构造方法，因此本阶段不再使用 record。

将 `model/User.java` 改为：

```java
package com.Shuan.spring_boot_study.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "app_users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    protected User() {
    }

    public User(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
```

### 注解解释

| 注解 | 作用 |
|---|---|
| `@Entity` | 声明这是 JPA 实体 |
| `@Table(name = "app_users")` | 指定数据库表名 |
| `@Id` | 声明主键字段 |
| `@GeneratedValue` | 主键由数据库自动生成 |

使用 `app_users` 而不是 `user`，可以避免与数据库中的保留字或系统表概念混淆。

### 为什么有两个构造方法

```java
protected User() {
}
```

供 JPA/Hibernate 创建对象使用。

```java
public User(String name) {
    this.name = name;
}
```

供业务代码创建新用户使用。新增用户时不传 ID，因为 ID 由数据库生成。

## 11. 阶段八：把 Repository 类改成接口

将原来的内存 `UserRepository` 内容替换为：

```java
package com.Shuan.spring_boot_study.repository;

import com.Shuan.spring_boot_study.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
```

注意变化：

- 从 `class` 改为 `interface`。
- 删除内存 `List`。
- 删除自己编写的 `findAll()` 和 `findById()`。
- 不需要手动添加 `@Repository`。
- 继承 `JpaRepository<User, Long>`。

两个泛型参数分别表示：

```text
User：Repository 管理的实体类型
Long：User 主键的 Java 类型
```

Spring Data JPA 会在运行时自动为这个接口创建实现。

`JpaRepository` 已经提供：

- `findAll()`
- `findById(id)`
- `save(entity)`
- `deleteById(id)`
- `count()`

## 12. 阶段九：调整 UserService

现有查询方法可以继续使用，因为 `JpaRepository` 同样提供 `findAll()` 和 `findById()`。

将 `UserService` 整理为：

```java
package com.Shuan.spring_boot_study.service;

import com.Shuan.spring_boot_study.model.User;
import com.Shuan.spring_boot_study.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    public User create(String name) {
        User user = new User(name);
        return userRepository.save(user);
    }
}
```

`save()` 执行后，返回的 `User` 会包含数据库生成的 ID。

## 13. 阶段十：创建新增用户请求 DTO

在 `dto` 包中创建：

```text
CreateUserRequest.java
```

填写：

```java
package com.Shuan.spring_boot_study.dto;

public record CreateUserRequest(
        String name
) {
}
```

它用于接收请求 JSON：

```json
{
  "name": "David"
}
```

不要直接让 Entity 承担所有请求 DTO 职责。现在的数据很简单，但提前区分 Entity 和 DTO 有助于后续添加校验与接口版本控制。

## 14. 阶段十一：为 UserController 添加 POST

在 `UserController` 中增加 import：

```java
import com.Shuan.spring_boot_study.dto.CreateUserRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
```

然后在类中添加：

```java
@PostMapping
@ResponseStatus(HttpStatus.CREATED)
public User create(@RequestBody CreateUserRequest request) {
    return userService.create(request.name());
}
```

### 新注解解释

- `@PostMapping`：接收 POST 请求。
- `@RequestBody`：把请求体 JSON 转换为 Java DTO。
- `@ResponseStatus(HttpStatus.CREATED)`：成功时返回 HTTP 201。

## 15. 阶段十二：第一次启动数据库版本

启动前确认：

- MySQL 服务正在运行。
- `spring_boot_study` 数据库存在。
- `spring_user` 有权限。
- IDEA 运行配置中存在 `DB_PASSWORD`。
- Maven 依赖下载完成。

然后运行 `SpringBootStudyApplication`。

成功日志中通常能看到：

- HikariCP 连接池启动。
- Hibernate 初始化。
- Hibernate 创建或检查 `app_users` 表。
- Spring Boot 应用启动完成。

如果应用在数据库接入后无法启动，不要先改代码，应先从异常最底部的 `Caused by` 开始检查。

## 16. 阶段十三：测试 CRUD

### 查询空列表

```bash
curl -i http://localhost:8080/api/users
```

首次运行时预期：

```json
[]
```

### 新增用户

```bash
curl -i \
  -X POST \
  -H 'Content-Type: application/json' \
  -d '{"name":"David"}' \
  http://localhost:8080/api/users
```

预期状态码：

```text
HTTP/1.1 201
```

预期 JSON 类似：

```json
{
  "id": 1,
  "name": "David"
}
```

### 再次查询全部用户

```bash
curl -i http://localhost:8080/api/users
```

应当能看到刚才创建的 David。

### 查询单个用户

按照 POST 响应中的真实 ID 查询：

```bash
curl -i http://localhost:8080/api/users/1
```

### 验证持久化

1. 停止 Spring Boot 应用。
2. 重新启动应用。
3. 再次执行 `GET /api/users`。
4. David 仍然存在，说明数据保存在 MySQL，而不是 JVM 内存中。

## 17. 直接在 MySQL 中检查

登录：

```bash
mysql -u spring_user -p spring_boot_study
```

查看表：

```sql
SHOW TABLES;
```

查看数据：

```sql
SELECT * FROM app_users;
```

退出：

```sql
EXIT;
```

## 18. 常见错误

### 实际环境问题：同时存在两套 MySQL

本次检查发现电脑中存在两套 MySQL：

```text
/usr/local/mysql/bin/mysql
MySQL 8.0.31，来自 MySQL 官方 macOS 安装包

/opt/homebrew/opt/mysql/bin/mysql
MySQL 9.4.0，来自 Homebrew
```

正在运行的官方服务进程来自：

```text
/usr/local/mysql/bin/mysqld
```

Homebrew 服务状态显示为 `stopped`，但它的启动脚本曾反复尝试启动。错误日志显示：

```text
Invalid MySQL server upgrade:
Cannot upgrade from 80031 to 90400.
```

这表示 Homebrew MySQL 9.4 尝试读取一个由 MySQL 8.0.31 创建的数据目录。MySQL 不支持直接跳过要求的中间版本进行这种升级。

#### 当前安全策略

为了完成 Spring Boot 学习，本阶段选择继续使用已经运行的官方 MySQL 8.0.31，不处理版本升级。

不要执行：

- 删除 `/usr/local/mysql/data`。
- 删除 `/opt/homebrew/var/mysql`。
- 在没有备份的情况下执行 Initialize Database。
- 用 MySQL 9.4 强行打开 8.0.31 数据目录。
- 同时启动两套服务并让它们争用 3306 端口。

#### 第一步：停止 Homebrew 的启动尝试

由学习者在终端执行：

```bash
brew services stop mysql
```

检查：

```bash
brew services list
```

确认 `mysql` 显示：

```text
stopped
```

#### 第二步：明确使用官方 MySQL 客户端

不要暂时使用 PATH 中的 Homebrew 客户端，改用完整路径：

```bash
/usr/local/mysql/bin/mysql --version
```

预期显示 MySQL 8.0.31。

先尝试不提供密码登录：

```bash
/usr/local/mysql/bin/mysql -u root --skip-password
```

如果能够登录，立即按照后面的步骤创建应用专用用户，不要让 Spring Boot 使用 root。

如果仍然得到 1045，说明官方 MySQL 的 root 已经设置密码，而当前不知道或输入不正确。

#### 第三步：尝试自己保存的官方安装密码

官方 macOS 安装包在首次配置时要求设置 root 密码。使用明确的官方客户端尝试：

```bash
/usr/local/mysql/bin/mysql -u root -p
```

输入密码时终端不会显示字符，这是正常现象。

如果忘记密码，再执行下面的重置流程。不要通过反复猜测密码解决。

#### 第四步：忘记密码时重置 root

这是数据库管理操作。开始前应确认没有需要保留但尚未备份的重要数据库。

1. 在 macOS 系统设置中搜索 MySQL，打开官方 MySQL Preference Pane。
2. 使用 `Stop MySQL Server` 停止官方 MySQL 服务。
3. 确认普通方式已经停止后，在终端临时以跳过权限表模式启动：

```bash
sudo /usr/local/mysql/bin/mysqld_safe \
  --user=_mysql \
  --skip-grant-tables \
  --skip-networking
```

该终端会持续运行，不要关闭。在另一个终端连接：

```bash
/usr/local/mysql/bin/mysql -u root
```

进入 MySQL 后执行：

```sql
FLUSH PRIVILEGES;
```

设置新的强密码，将示例文本替换成自己的密码：

```sql
ALTER USER 'root'@'localhost'
    IDENTIFIED BY '替换成新的强密码';
```

退出：

```sql
EXIT;
```

回到运行 `mysqld_safe` 的终端，按 `Control + C` 停止临时服务。如果它没有退出，可另开终端使用新密码正常关闭：

```bash
/usr/local/mysql/bin/mysqladmin -u root -p shutdown
```

最后回到 MySQL Preference Pane，使用 `Start MySQL Server` 正常启动。

验证新密码：

```bash
/usr/local/mysql/bin/mysql -u root -p
```

重置模式使用了 `--skip-networking`，避免重置期间接受网络连接。完成后必须停止临时实例，再恢复正常启动。

#### 第五步：成功登录后继续本任务

登录成功后，回到本任务“阶段三”，依次创建：

```text
spring_boot_study 数据库
spring_user 应用账号
spring_user 对学习数据库的权限
```

Spring Boot 的 JDBC URL 继续使用：

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/spring_boot_study
```

JDBC 驱动连接的是正在监听 3306 端口的 MySQL 8.0 服务，不要求客户端命令与服务器放在同一个安装目录；但排查阶段使用完整路径可以避免混淆两套安装。

### Access denied for user

含义：用户名、密码或授权不正确。

检查：

- `DB_USERNAME` 是否正确。
- `DB_PASSWORD` 是否与创建用户时一致。
- 用户是否被授权访问 `spring_boot_study.*`。

### Unknown database

含义：数据库不存在或 URL 中名称拼错。

检查：

```sql
SHOW DATABASES;
```

### Communications link failure

常见原因：

- MySQL 服务没有启动。
- 端口不是 3306。
- 数据库地址配置错误。

### 实际启动错误：Unable to determine Dialect without JDBC metadata

错误信息：

```text
Failed to initialize JPA EntityManagerFactory
Unable to determine Dialect without JDBC metadata
```

这通常不是要求手动配置 MySQL Dialect，而是 Hibernate 无法建立数据库连接，所以拿不到 JDBC 元数据。

本次检查确认 MySQL 仍以密码重置维护模式运行：

```text
--skip-grant-tables --skip-networking
```

其中 `--skip-networking` 会禁用 TCP 网络连接，3306 端口没有监听。Spring Boot 使用 `jdbc:mysql://localhost:3306/...`，因此无法连接。

正确处理方法：

1. 停止维护模式的 `mysqld_safe` 和 `mysqld`。
2. 确认没有残留 MySQL 进程。
3. 使用 MySQL Preference Pane 正常启动官方 MySQL 服务。
4. 确认 3306 端口开始监听。
5. 使用 `spring_user` 通过 TCP 测试登录。
6. 再启动 Spring Boot。

不要为了消除该错误而添加：

```properties
spring.jpa.database-platform=org.hibernate.dialect.MySQLDialect
```

手动指定 Dialect 不能修复数据库不可连接的问题，只会让真正的连接错误更晚出现。

### Failed to determine a suitable driver class

常见原因：

- `mysql-connector-j` 没有添加。
- Maven 尚未 Reload。
- `spring.datasource.url` 缺失或拼写错误。

### Not a managed type

检查：

- `User` 是否有 `@Entity`。
- 导入是否来自 `jakarta.persistence`。
- Entity 是否位于启动类包的子包中。

### 实际编译问题：JpaRepository 接口中仍保留内存实现

本次检查发现 `UserRepository` 已经从普通类改成：

```java
public interface UserRepository extends JpaRepository<User, Long>
```

但接口内部仍然保留了 Task 07 的内存数据和方法实现：

```java
private final List<User> users = List.of(...);

public List<User> findAll() {
    return users;
}

public Optional<User> findById(long id) {
    // ...
}
```

这会产生多类编译问题：

1. 接口字段不能像普通类的实例字段一样声明为 `private final`。
2. 接口方法如果有方法体，需要满足接口默认方法或静态方法的规则。
3. `JpaRepository` 已经提供 `findAll()` 和 `findById()`，不应再次用内存实现覆盖。
4. `User` 已从 record 改为 Entity 类，不再有 `new User(Long, String)` 构造方法。
5. Entity 使用 `getId()`，不再有 record 自动生成的 `id()` 方法。

应当删除全部旧内存代码，将文件手动整理为：

```java
package com.Shuan.spring_boot_study.repository;

import com.Shuan.spring_boot_study.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
```

同时删除这些已经不再需要的 import：

```java
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
```

Spring Data JPA 会在运行时生成 Repository 实现。接口看起来是空的，但已经继承了 `findAll()`、`findById()`、`save()`、`deleteById()` 等方法。

### Table does not exist

检查：

- `spring.jpa.hibernate.ddl-auto=update` 是否生效。
- 数据库用户是否有建表权限。
- Entity 是否被扫描。

## 19. 安全检查

提交前执行：

```bash
git diff
git diff --cached
```

确认没有出现：

- MySQL root 密码
- `spring_user` 的密码
- 其他令牌或私钥

也可以搜索常见敏感配置：

```bash
git grep -n 'spring.datasource.password'
```

正确结果应当是：

```properties
spring.datasource.password=${DB_PASSWORD}
```

## 20. 独立练习

### 练习 1：根据名字查询

在 `UserRepository` 中添加：

```java
Optional<User> findByNameIgnoreCase(String name);
```

Spring Data JPA 会根据方法名自动生成查询。

然后通过 Service 和 Controller 暴露：

```text
GET /api/users/search?name=David
```

### 练习 2：删除用户

添加：

```text
DELETE /api/users/{id}
```

思考：

- 删除成功应该返回 200 还是 204？
- 删除不存在的用户应该返回什么状态码？
- 判断用户存在的逻辑应该放在哪一层？

### 练习 3：观察 SQL

分别调用：

- 查询全部用户
- 查询单个用户
- 新增用户

观察控制台中的 SQL，记录 `select` 和 `insert` 分别在什么时候执行。

## 21. 完成检查

- [ ] 将 `respository` 重命名为 `repository`。
- [ ] MySQL 可以正常登录。
- [ ] 创建 `spring_boot_study` 数据库。
- [ ] 创建权限受限的 `spring_user`。
- [ ] 添加 JPA 和 MySQL Driver 依赖。
- [ ] 使用环境变量提供数据库密码。
- [ ] 将 User 改为 JPA Entity。
- [ ] 将 UserRepository 改为 JpaRepository 接口。
- [ ] GET `/api/users` 从 MySQL 查询数据。
- [ ] POST `/api/users` 返回 201 并写入数据。
- [ ] 重启应用后数据仍然存在。
- [ ] Git 中没有数据库密码。

## 22. 学习记录

```text
MySQL 版本：

数据库名称：

新增用户响应：

重启后数据是否仍然存在：

遇到的问题：

完整错误信息：

解决方法：

我对 JPA、Hibernate 和 Spring Data JPA 关系的理解：
```

---

# Task 09：参数校验与统一异常处理

## 1. 为什么需要这一阶段

当前新增用户接口可以收到：

```json
{
  "name": ""
}
```

甚至：

```json
{}
```

如果不校验，这些不完整数据可能被保存到数据库。

当前查询不存在用户时，Controller 自己检查 `Optional` 并返回 404。随着接口增加，每个 Controller 都重复这种判断，代码会逐渐混乱。

本阶段目标：

```text
请求 DTO 负责声明输入规则
Service 负责判断业务对象是否存在
自定义异常表达业务失败
全局异常处理器统一生成错误 JSON
```

## 2. 最终效果

合法请求：

```http
POST /api/users
Content-Type: application/json

{
  "name": "David"
}
```

返回 HTTP 201。

非法请求：

```json
{
  "name": ""
}
```

返回 HTTP 400 和统一 JSON，例如：

```json
{
  "timestamp": "2026-09-29T10:00:00Z",
  "status": 400,
  "error": "Bad Request",
  "message": "请求参数校验失败",
  "path": "/api/users",
  "fieldErrors": {
    "name": "用户名不能为空"
  }
}
```

查询不存在用户：

```text
GET /api/users/999
```

返回 HTTP 404 和相同结构的错误 JSON。

## 3. 第一步：添加 Validation 依赖

在 `pom.xml` 的 `<dependencies>` 中加入：

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-validation</artifactId>
</dependency>
```

保存后，在 Maven 工具窗口点击：

```text
Reload All Maven Projects
```

这个 Starter 通常提供 Hibernate Validator，它与数据库使用的 Hibernate ORM 不是同一个职责：

- Hibernate ORM：Entity 与数据库表映射。
- Hibernate Validator：按照注解校验 Java 数据。

## 4. 第二步：给请求 DTO 添加校验规则

将 `CreateUserRequest.java` 改为：

```java
package com.Shuan.spring_boot_study.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateUserRequest(
        @NotBlank(message = "用户名不能为空")
        @Size(min = 2, max = 50, message = "用户名长度必须在 2 到 50 个字符之间")
        String name
) {
}
```

### @NotBlank

拒绝：

- `null`
- 空字符串 `""`
- 只有空格的字符串 `"   "`

### @Size

限制字符串长度。本例要求 2～50 个字符。

### 为什么校验加在 DTO

`CreateUserRequest` 描述“创建用户接口允许接收什么”。它属于接口边界，因此适合声明输入规则。

Entity 也可以使用数据库相关约束，但请求 DTO 和数据库 Entity 的职责不同，不应该假设二者永远具有完全相同的字段和规则。

## 5. 第三步：在 Controller 中触发校验

在 `UserController` 中导入：

```java
import jakarta.validation.Valid;
```

将创建方法从：

```java
public User create(@RequestBody CreateUserRequest request)
```

改成：

```java
public User create(@Valid @RequestBody CreateUserRequest request)
```

完整方法：

```java
@PostMapping
@ResponseStatus(HttpStatus.CREATED)
public User create(@Valid @RequestBody CreateUserRequest request) {
    return userService.create(request.name());
}
```

`@Valid` 的作用是告诉 Spring MVC：完成 JSON 到 DTO 的转换后，继续执行 DTO 字段上的 Bean Validation 规则。

没有 `@Valid` 时，DTO 上虽然写了校验注解，但这个请求参数不会自动触发校验。

## 6. 第四步：先观察 Spring 默认响应

在编写全局异常处理器之前，先启动应用测试一次：

```bash
curl -i \
  -X POST \
  -H 'Content-Type: application/json' \
  -d '{"name":""}' \
  http://localhost:8080/api/users
```

预期 HTTP 状态码：

```text
400 Bad Request
```

Spring MVC 对 `@Valid @RequestBody` 校验失败通常抛出：

```text
MethodArgumentNotValidException
```

先确认校验确实发生，再继续定制错误 JSON。

## 7. 第五步：创建用户不存在异常

在根包下创建 `exception` 包：

```text
com.Shuan.spring_boot_study.exception
```

创建 `UserNotFoundException.java`：

```java
package com.Shuan.spring_boot_study.exception;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(Long id) {
        super("用户不存在，id=" + id);
    }
}
```

### 为什么继承 RuntimeException

它表达运行时发生的业务失败。调用方不需要在每一层用 `throws` 声明，而是由统一异常处理器转换为 HTTP 响应。

不要在 Repository 抛出这个业务异常。Repository 只负责返回有没有查到数据；“没查到用户时如何处理”属于业务规则，应由 Service 决定。

## 8. 第六步：让 Service 处理不存在的用户

在 `UserService` 中导入：

```java
import com.Shuan.spring_boot_study.exception.UserNotFoundException;
```

新增方法：

```java
public User findByIdOrThrow(Long id) {
    return userRepository.findById(id)
            .orElseThrow(() -> new UserNotFoundException(id));
}
```

### orElseThrow

```java
optional.orElseThrow(...)
```

表示：

- Optional 中有用户：返回用户。
- Optional 为空：创建并抛出 `UserNotFoundException`。

这里使用 Lambda：

```java
() -> new UserNotFoundException(id)
```

只有 Optional 为空时才会创建异常对象。

原来的 `findById()` 暂时可以保留用于对比。完成本任务后，如果没有其他代码使用，可以再删除。

## 9. 第七步：简化 Controller 查询方法

原来的方法在 Controller 中判断 Optional：

```java
@GetMapping("/{id}")
public ResponseEntity<User> findById(@PathVariable Long id) {
    Optional<User> user = userService.findById(id);

    if (user.isPresent()) {
        return ResponseEntity.ok(user.get());
    }

    return ResponseEntity.notFound().build();
}
```

改成：

```java
@GetMapping("/{id}")
public User findById(@PathVariable Long id) {
    return userService.findByIdOrThrow(id);
}
```

现在 Controller 只负责接收路径参数并调用 Service。

暂时删除不再使用的 import：

```java
import org.springframework.http.ResponseEntity;
import java.util.Optional;
```

## 10. 第八步：定义统一错误响应

在 `dto` 包中创建 `ApiError.java`：

```java
package com.Shuan.spring_boot_study.dto;

import java.time.Instant;
import java.util.Map;

public record ApiError(
        Instant timestamp,
        int status,
        String error,
        String message,
        String path,
        Map<String, String> fieldErrors
) {
}
```

字段含义：

| 字段 | 含义 |
|---|---|
| `timestamp` | 错误发生时间 |
| `status` | HTTP 状态码 |
| `error` | 状态码名称 |
| `message` | 对错误的整体说明 |
| `path` | 发生错误的请求路径 |
| `fieldErrors` | 字段级校验错误 |

普通业务异常没有字段错误时，可以返回空 Map。

## 11. 第九步：创建全局异常处理器

在 `exception` 包中创建：

```text
GlobalExceptionHandler.java
```

填写：

```java
package com.Shuan.spring_boot_study.exception;

import com.Shuan.spring_boot_study.dto.ApiError;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiError handleUserNotFound(
            UserNotFoundException exception,
            HttpServletRequest request) {

        return new ApiError(
                Instant.now(),
                HttpStatus.NOT_FOUND.value(),
                HttpStatus.NOT_FOUND.getReasonPhrase(),
                exception.getMessage(),
                request.getRequestURI(),
                Map.of()
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleValidation(
            MethodArgumentNotValidException exception,
            HttpServletRequest request) {

        Map<String, String> fieldErrors = new LinkedHashMap<>();

        exception.getBindingResult()
                .getFieldErrors()
                .forEach(error -> fieldErrors.putIfAbsent(
                        error.getField(),
                        error.getDefaultMessage()
                ));

        return new ApiError(
                Instant.now(),
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                "请求参数校验失败",
                request.getRequestURI(),
                fieldErrors
        );
    }
}
```

### @RestControllerAdvice

它相当于：

```text
@ControllerAdvice + @ResponseBody
```

其中的异常处理方法可以应用到多个 Controller，返回对象会被序列化成 JSON。

### @ExceptionHandler

```java
@ExceptionHandler(UserNotFoundException.class)
```

表示该方法专门处理 `UserNotFoundException`。

应该优先编写具体异常的处理方法，不要一开始只写一个捕获全部 `Exception` 的方法，否则容易隐藏真正的程序错误。

## 12. 第十步：让数据库约束与接口规则保持一致

在 `User` Entity 的 `name` 字段上添加：

```java
import jakarta.persistence.Column;
```

将字段改成：

```java
@Column(nullable = false, length = 50)
private String name;
```

这表示数据库层面不允许 `name` 为 NULL，最大长度为 50。

注意：

- Bean Validation 负责尽早拒绝不合法 HTTP 输入。
- 数据库约束负责保护最终持久化的数据。
- 两层校验并不重复浪费，而是保护不同边界。

如果数据库中已经存在 NULL 或超长数据，Hibernate 修改表结构时可能失败。学习项目可以先检查：

```sql
SELECT *
FROM app_users
WHERE name IS NULL OR CHAR_LENGTH(name) > 50;
```

## 13. 验证合法请求

重新启动应用，执行：

```bash
curl -i \
  -X POST \
  -H 'Content-Type: application/json' \
  -d '{"name":"Emma"}' \
  http://localhost:8080/api/users
```

预期：

```text
HTTP/1.1 201
```

并返回带自动生成 ID 的用户 JSON。

## 14. 验证空名称

```bash
curl -i \
  -X POST \
  -H 'Content-Type: application/json' \
  -d '{"name":""}' \
  http://localhost:8080/api/users
```

预期：

```text
HTTP/1.1 400
```

响应中的 `fieldErrors.name` 应为：

```text
用户名不能为空
```

## 15. 验证过短名称

```bash
curl -i \
  -X POST \
  -H 'Content-Type: application/json' \
  -d '{"name":"A"}' \
  http://localhost:8080/api/users
```

预期返回 HTTP 400，字段错误说明长度必须在 2～50 个字符之间。

## 16. 验证缺少字段

```bash
curl -i \
  -X POST \
  -H 'Content-Type: application/json' \
  -d '{}' \
  http://localhost:8080/api/users
```

反序列化后 `name` 为 null，`@NotBlank` 应当拒绝它并返回 HTTP 400。

## 17. 验证用户不存在

```bash
curl -i http://localhost:8080/api/users/999999
```

预期：

```text
HTTP/1.1 404
```

JSON 中应包含：

```json
{
  "status": 404,
  "error": "Not Found",
  "message": "用户不存在，id=999999",
  "path": "/api/users/999999",
  "fieldErrors": {}
}
```

## 18. 请求失败流程

### DTO 校验失败

```text
POST JSON
    ↓
Jackson 转换为 CreateUserRequest
    ↓
@Valid 触发 @NotBlank 和 @Size
    ↓
MethodArgumentNotValidException
    ↓
GlobalExceptionHandler
    ↓
HTTP 400 + ApiError JSON
```

### 用户不存在

```text
GET /api/users/999
    ↓
UserController
    ↓
UserService.findByIdOrThrow
    ↓
Repository 返回 Optional.empty()
    ↓
抛出 UserNotFoundException
    ↓
GlobalExceptionHandler
    ↓
HTTP 404 + ApiError JSON
```

## 19. 常见问题

### 校验注解没有效果

检查：

1. `pom.xml` 是否有 `spring-boot-starter-validation`。
2. Maven 是否已经 Reload。
3. Controller 参数前是否有 `@Valid`。
4. 是否导入 `jakarta.validation.Valid`，而不是其他同名类型。
5. DTO 是否使用 `jakarta.validation.constraints` 下的注解。

### 错误仍然返回 Spring 默认格式

检查：

1. `GlobalExceptionHandler` 是否有 `@RestControllerAdvice`。
2. 该类是否位于启动类包的子包中。
3. 是否处理了 `MethodArgumentNotValidException`。
4. 新建文件后是否重新启动应用。

### 同一个字段产生两条错误

空字符串可能同时违反 `@NotBlank` 和 `@Size`。示例使用 `putIfAbsent`，每个字段只保留第一条信息，因此具体显示哪一条可能与校验器返回顺序有关。

此阶段先接受这一行为。后续可以使用 Validation Groups 或更精细的错误列表设计。

### JSON 格式错误

例如请求体缺少右花括号时，异常发生在 DTO 校验之前，通常是 `HttpMessageNotReadableException`。

本阶段先观察 Spring 默认响应。独立练习中再为它增加统一处理。

### 实际编译错误：cannot find symbol runtimeException

本次 `UserNotFoundException` 写成了：

```java
public class UserNotFoundException extends runtimeException
```

Java 区分大小写，标准异常类的正确名称是：

```java
RuntimeException
```

正确文件应为：

```java
package com.Shuan.spring_boot_study.exception;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(Long id) {
        super("用户不存在，id=" + id);
    }
}
```

`RuntimeException` 属于 `java.lang`，Java 会自动导入，因此不需要手动添加 import。

同时，`UserService` 中曾误加：

```java
import com.Shuan.spring_boot_study.exception;
```

这是包名，不是具体类型，不能用这种形式导入，应删除。保留具体的类导入：

```java
import com.Shuan.spring_boot_study.exception.UserNotFoundException;
```

遇到 `cannot find symbol` 时，要检查错误中的 symbol，并重点核对名称拼写、大小写以及 import 是否指向具体类型。

## 20. 独立练习

### 练习 1：处理非法 JSON

为 `HttpMessageNotReadableException` 添加异常处理，返回 HTTP 400：

```text
请求 JSON 格式错误
```

### 练习 2：校验路径 ID

要求用户 ID 必须大于等于 1。

可以研究：

```java
@Min(1)
```

Spring MVC 对直接写在方法参数上的约束可能抛出 `HandlerMethodValidationException`，它与 DTO 校验产生的 `MethodArgumentNotValidException` 不同。

### 练习 3：名称去除首尾空格

思考以下输入：

```json
{
  "name": "  Emma  "
}
```

应该原样保存，还是保存 `Emma`？这个转换属于业务规则，适合放在 Service 的 `create()` 中：

```java
String normalizedName = name.trim();
```

## 21. 完成检查

- [ ] 添加 `spring-boot-starter-validation`。
- [ ] CreateUserRequest 使用 `@NotBlank` 和 `@Size`。
- [ ] Controller 使用 `@Valid @RequestBody`。
- [ ] 创建 `UserNotFoundException`。
- [ ] Service 使用 `orElseThrow`。
- [ ] Controller 不再手动判断 Optional。
- [ ] 创建统一 `ApiError` record。
- [ ] 创建 `GlobalExceptionHandler`。
- [ ] 空名称返回 HTTP 400。
- [ ] 不存在用户返回 HTTP 404。
- [ ] 错误响应具有统一 JSON 结构。
- [ ] 合法用户仍能正常保存到 MySQL。

## 22. 学习记录

```text
完成日期：

合法 POST 状态码：

空名称响应：

不存在用户响应：

遇到的问题：

解决方法：

我对 DTO 校验的理解：

我对全局异常处理的理解：
```

---

# 后续整体学习路线

## 1. 当前位置

目前已经完成：

```text
项目创建
  → REST API 基础
  → Controller、Service、Repository 分层
  → MySQL 与 Spring Data JPA
  → 查询和新增用户
  → DTO 参数校验
  → 统一异常处理
```

之前的学习内容与后续计划是连续的，不需要重做。下一步从 Task 10 继续。

## 2. Task 10：更新和删除用户，完成 CRUD

### 2.1 学习目标

1. 使用 `PUT` 更新已存在的用户。
2. 使用 `DELETE` 删除已存在的用户。
3. 为更新请求创建独立 DTO，并复用 Bean Validation。
4. 理解 `@Transactional` 的作用。
5. 理解 JPA 脏检查。
6. 正确使用 HTTP 200、204、400 和 404。

### 2.2 什么是 CRUD

CRUD 是数据操作的四个基本能力：

| 缩写 | 含义 | HTTP 方法 | 当前接口 |
|---|---|---|---|
| C | Create，创建 | `POST` | `POST /api/users` |
| R | Read，查询 | `GET` | `GET /api/users` |
| U | Update，更新 | `PUT` | `PUT /api/users/{id}` |
| D | Delete，删除 | `DELETE` | `DELETE /api/users/{id}` |

前面已经完成 C 和 R，本任务完成 U 和 D。

### 2.3 PUT 和 PATCH 的区别

- `PUT` 通常表示使用请求中的数据更新整个资源。
- `PATCH` 通常表示只修改资源的部分字段。

当前 `User` 只有 `name` 一个可修改字段，因此本阶段先使用 `PUT`。后续扩展多个字段后再学习 `PATCH`。

### 2.4 创建 UpdateUserRequest

新建：

```text
src/main/java/com/Shuan/spring_boot_study/dto/UpdateUserRequest.java
```

内容：

```java
package com.Shuan.spring_boot_study.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UpdateUserRequest(
        @NotBlank(message = "用户名不能为空")
        @Size(min = 2, max = 50, message = "用户名长度必须在2到50个字符之间")
        String name
) {
}
```

不直接使用 `CreateUserRequest` 的原因是：虽然现在两个 DTO 字段相同，但创建和更新是两种不同的 API 语义。以后两者可能有不同的字段或校验规则。

### 2.5 为 User 添加业务方法

在 `User.java` 中加入：

```java
public void changeName(String name) {
    this.name = name;
}
```

放在 `getName()` 之前或之后都可以。

这里使用 `changeName()` 而不是通用的 `setName()`，方法名可以更清楚地表达业务意图。

### 2.6 在 Service 实现更新

在 `UserService.java` 中导入：

```java
import org.springframework.transaction.annotation.Transactional;
```

添加：

```java
@Transactional
public User update(Long id, String name) {
    User user = findByIdOrThrow(id);
    user.changeName(name.trim());
    return user;
}
```

执行流程：

```text
根据 id 查询用户
    ↓
用户不存在，抛出 UserNotFoundException
    ↓
用户存在，修改 name
    ↓
事务提交
    ↓
JPA 检测实体变化并执行 UPDATE
```

#### 为什么没有调用 save

`findByIdOrThrow()` 从 Repository 查出的 `User` 在当前事务中是受 JPA 管理的实体。

当执行：

```java
user.changeName(name.trim());
```

JPA 会在事务提交前比较实体状态。发现 `name` 发生变化后，自动生成 `UPDATE` SQL。这个机制叫作脏检查，因此此处不必再调用 `userRepository.save(user)`。

### 2.7 在 Controller 添加 PUT 接口

在 `UserController.java` 中导入：

```java
import com.Shuan.spring_boot_study.dto.UpdateUserRequest;
```

在类的最后一个右花括号之前添加：

```java
@PutMapping("/{id}")
public User update(
        @PathVariable Long id,
        @Valid @RequestBody UpdateUserRequest request
) {
    return userService.update(id, request.name());
}
```

请求示例：

```http
PUT /api/users/2
Content-Type: application/json

{
  "name": "Emma Updated"
}
```

路径中的 `2` 由 `@PathVariable Long id` 接收，JSON 请求体由 `@RequestBody UpdateUserRequest` 接收。`@Valid` 会在 Controller 方法执行前校验请求。

### 2.8 测试更新接口

先查询现有用户：

```bash
curl -i http://localhost:8080/api/users
```

选择一个真实存在的 ID，例如 `2`：

```bash
curl -i \
  -X PUT \
  -H 'Content-Type: application/json' \
  -d '{"name":"Emma Updated"}' \
  http://localhost:8080/api/users/2
```

预期：

```http
HTTP/1.1 200
Content-Type: application/json
```

```json
{
  "id": 2,
  "name": "Emma Updated"
}
```

再次查询，确认数据库已更新：

```bash
curl -i http://localhost:8080/api/users/2
```

测试校验失败：

```bash
curl -i \
  -X PUT \
  -H 'Content-Type: application/json' \
  -d '{"name":"A"}' \
  http://localhost:8080/api/users/2
```

预期 HTTP 400，并返回 `name` 字段错误。

测试用户不存在：

```bash
curl -i \
  -X PUT \
  -H 'Content-Type: application/json' \
  -d '{"name":"Valid Name"}' \
  http://localhost:8080/api/users/999999
```

预期 HTTP 404，并返回统一的 `ApiError`。

### 2.9 在 Service 实现删除

在 `UserService.java` 中添加：

```java
@Transactional
public void delete(Long id) {
    User user = findByIdOrThrow(id);
    userRepository.delete(user);
}
```

这里先查询再删除，可以复用已有的 `UserNotFoundException`，保证删除不存在的用户时返回 HTTP 404。

### 2.10 在 Controller 添加 DELETE 接口

在 `UserController.java` 中添加：

```java
@DeleteMapping("/{id}")
@ResponseStatus(HttpStatus.NO_CONTENT)
public void delete(@PathVariable Long id) {
    userService.delete(id);
}
```

删除成功时返回：

```http
HTTP/1.1 204 No Content
```

204 表示请求已成功处理，但响应没有 body。因此 Controller 方法返回 `void`。

### 2.11 测试删除接口

为了避免误删重要数据，可以先创建一个专门用于删除的用户：

```bash
curl -i \
  -X POST \
  -H 'Content-Type: application/json' \
  -d '{"name":"Delete Me"}' \
  http://localhost:8080/api/users
```

记住返回的 `id`，然后执行：

```bash
curl -i \
  -X DELETE \
  http://localhost:8080/api/users/3
```

将示例中的 `3` 替换为刚刚创建的真实 ID。预期：

```http
HTTP/1.1 204
```

再次查询已删除用户：

```bash
curl -i http://localhost:8080/api/users/3
```

预期 HTTP 404。

再次删除同一个用户：

```bash
curl -i \
  -X DELETE \
  http://localhost:8080/api/users/3
```

同样应返回 HTTP 404。

### 2.12 检查 Maven 构建

在 Maven 项目目录中执行：

```bash
cd spring-boot-study
./mvnw test
```

如果测试和编译成功，最后会看到：

```text
BUILD SUCCESS
```

### 2.13 常见问题

#### PUT 返回 405 Method Not Allowed

检查：

1. 是否写成 `@PutMapping("/{id}")`。
2. `@PutMapping` 后是否有完整的方法。
3. 修改后是否重新启动了应用。
4. `curl` 是否使用了 `-X PUT`。

#### 更新后数据库没有变化

检查 Service 的 `update()` 是否有 `@Transactional`，并确认修改的是从 Repository 查询出来的受管实体。

#### 空名称没有返回 400

检查：

1. `UpdateUserRequest` 是否有 `@NotBlank` 和 `@Size`。
2. Controller 的 request 参数前是否有 `@Valid`。
3. 是否导入 `jakarta.validation.Valid`。

#### DELETE 成功后返回 200

检查 Controller 删除方法是否添加：

```java
@ResponseStatus(HttpStatus.NO_CONTENT)
```

#### 启动时出现路由冲突

确保更新和删除都使用 `/{id}`，但 HTTP 方法分别是 `PUT` 和 `DELETE`。Spring MVC 会同时使用路径和 HTTP 方法区分路由，因此两者不冲突。

### 2.14 完成检查

- [ ] 创建 `UpdateUserRequest`。
- [ ] 为 `User` 添加 `changeName()`。
- [ ] Service 实现 `update()`。
- [ ] 更新方法使用 `@Transactional`。
- [ ] Controller 实现 `PUT /api/users/{id}`。
- [ ] 合法更新返回 HTTP 200。
- [ ] 非法名称返回 HTTP 400。
- [ ] 更新不存在用户返回 HTTP 404。
- [ ] Service 实现 `delete()`。
- [ ] Controller 实现 `DELETE /api/users/{id}`。
- [ ] 删除成功返回 HTTP 204。
- [ ] 删除不存在用户返回 HTTP 404。
- [ ] `./mvnw test` 显示 `BUILD SUCCESS`。

### 2.15 学习记录

```text
完成日期：

PUT 正常响应：

PUT 参数校验响应：

PUT 用户不存在响应：

DELETE 正常响应：

DELETE 用户不存在响应：

我对 @Transactional 的理解：

我对 JPA 脏检查的理解：

遇到的问题：

解决方法：
```

## 3. Task 11：响应 DTO

### 3.1 学习目标

1. 不再从 Controller 直接返回 JPA 实体。
2. 使用 `UserResponse` 定义稳定的 API 响应结构。
3. 学习 Entity、Request DTO 和 Response DTO 的职责边界。

### 3.2 当前问题

当前 Controller 直接返回 `User`。这会让数据库模型和 API 绑定在一起，将来给实体增加密码等字段时，还可能意外返回敏感信息。

### 3.3 创建 UserResponse

新建 `dto/UserResponse.java`：

```java
package com.Shuan.spring_boot_study.dto;

import com.Shuan.spring_boot_study.model.User;

public record UserResponse(Long id, String name) {

    public static UserResponse from(User user) {
        return new UserResponse(user.getId(), user.getName());
    }
}
```

`from()` 负责将 Entity 转换为 Response DTO。

### 3.4 修改 Controller

导入：

```java
import com.Shuan.spring_boot_study.dto.UserResponse;
```

将查询全部的返回值改为：

```java
@GetMapping
public List<UserResponse> findAll() {
    return userService.findAll().stream()
            .map(UserResponse::from)
            .toList();
}
```

将单个查询改为：

```java
@GetMapping("/{id}")
public UserResponse findById(@PathVariable Long id) {
    return UserResponse.from(userService.findByIdOrThrow(id));
}
```

将创建和更新的返回值也改为 `UserResponse`：

```java
@PostMapping
@ResponseStatus(HttpStatus.CREATED)
public UserResponse create(@Valid @RequestBody CreateUserRequest request) {
    return UserResponse.from(userService.create(request.name()));
}

@PutMapping("/{id}")
public UserResponse update(
        @PathVariable Long id,
        @Valid @RequestBody UpdateUserRequest request
) {
    return UserResponse.from(userService.update(id, request.name()));
}
```

Service 和 Repository 仍然可以使用 `User`，转换发生在 API 边界。

### 3.5 验证

```bash
curl -i http://localhost:8080/api/users
curl -i http://localhost:8080/api/users/1
```

确认 JSON 仍只有：

```json
{
  "id": 1,
  "name": "Alice"
}
```

### 3.6 完成检查

- [ ] 创建 `UserResponse`。
- [ ] 所有用户接口不再直接返回 `User`。
- [ ] POST 仍返回 HTTP 201。
- [ ] GET、POST 和 PUT 的 JSON 结构正确。
- [ ] `./mvnw test` 通过。

## 4. Task 12：分页、排序和搜索

### 4.1 学习目标

1. 使用 `Pageable` 接收分页参数。
2. 使用 `Page<T>` 返回分页结果。
3. 使用 Spring Data 方法名查询实现模糊搜索。

### 4.2 修改 Repository

在 `UserRepository` 中添加：

```java
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

Page<User> findByNameContainingIgnoreCase(String name, Pageable pageable);
```

Spring Data JPA 会根据方法名生成不区分大小写的名称模糊查询。

### 4.3 修改 Service

```java
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public Page<User> findAll(Pageable pageable) {
    return userRepository.findAll(pageable);
}

public Page<User> searchByName(String name, Pageable pageable) {
    return userRepository.findByNameContainingIgnoreCase(name, pageable);
}
```

删除或替换原来的无参 `findAll()`，避免同时保留两套不一致的查询方式。

### 4.4 修改 Controller

```java
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@GetMapping
public Page<UserResponse> findAll(Pageable pageable) {
    return userService.findAll(pageable).map(UserResponse::from);
}

@GetMapping("/search")
public Page<UserResponse> search(
        @RequestParam String name,
        Pageable pageable
) {
    return userService.searchByName(name, pageable)
            .map(UserResponse::from);
}
```

`/search` 要放在概念上清晰的独立路由中，避免把 `search` 当成用户 ID。

### 4.5 验证

```http
GET /api/users?page=0&size=10&sort=name,asc
GET /api/users/search?name=Emma
```

使用 `curl`：

```bash
curl -i 'http://localhost:8080/api/users?page=0&size=2&sort=name,asc'
curl -i 'http://localhost:8080/api/users/search?name=em&page=0&size=10'
```

注意 URL 含 `&` 时要使用引号，否则 shell 会把 `&` 当成后台执行符号。

### 4.6 完成检查

- [ ] 普通列表接口支持 `page`、`size` 和 `sort`。
- [ ] 搜索接口支持名称模糊查询。
- [ ] 搜索不区分大小写。
- [ ] 响应包含总页数和总记录数。
- [ ] `./mvnw test` 通过。

## 5. Task 13：Service 和 Controller 自动化测试

### 5.1 Service 单元测试

新建 `UserServiceTest.java`，使用 Mockito，不启动 Spring 和 MySQL：

```java
@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    void findByIdOrThrowReturnsUserWhenFound() {
        User user = new User("Emma");
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        User result = userService.findByIdOrThrow(1L);

        assertSame(user, result);
    }

    @Test
    void findByIdOrThrowThrowsWhenMissing() {
        when(userRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(
                UserNotFoundException.class,
                () -> userService.findByIdOrThrow(999L)
        );
    }
}
```

需要导入 JUnit、Mockito、`Optional` 和项目中的类。IDEA 可以使用 `Option + Enter` 自动导入。

### 5.2 Controller 测试

使用 MockMvc 发送模拟 HTTP 请求，将 `UserService` 替换为 mock。MockMvc 只用于测试，不写在 Controller 生产代码中。

#### 第一步：创建测试目录和文件

新建：

```text
src/test/java/com/Shuan/spring_boot_study/UserControllerTest.java
```

完整目录结构：

```text
spring-boot-study/
└── src/
    ├── main/java/com/Shuan/spring_boot_study/controller/
    │   └── UserController.java
    └── test/java/com/Shuan/spring_boot_study/
        └── UserControllerTest.java
```

#### 第二步：编写第一个 MockMvc 测试

```java
package com.Shuan.spring_boot_study;

import com.Shuan.spring_boot_study.controller.UserController;
import com.Shuan.spring_boot_study.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserService userService;

    @Test
    void deleteReturns204() throws Exception {
        mockMvc.perform(delete("/api/users/1"))
                .andExpect(status().isNoContent());

        verify(userService).delete(1L);
    }
}
```

Spring Boot 4 使用：

```java
@MockitoBean
```

网上的旧教程可能使用 `@MockBean`。当前项目应使用 `org.springframework.test.context.bean.override.mockito.MockitoBean`。

#### 第三步：理解测试类

`@WebMvcTest(UserController.class)` 只加载 Web 层相关组件，不启动完整业务和数据库环境。

`MockMvc` 在测试进程内模拟 HTTP 请求，不会真正监听 8080 端口。

`@MockitoBean` 在 Spring 测试容器中放入一个模拟 `UserService`，用它注入 `UserController`，因此不会调用真实 Repository 或 MySQL。

`verify(userService).delete(1L)` 用来验证 Controller 确实把路径参数 `1` 传给了 Service。

#### 第四步：测试 PUT 参数校验

在同一个 `UserControllerTest` 中增加 static import：

```java
import static org.mockito.Mockito.verifyNoInteractions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
```

导入：

```java
import org.springframework.http.MediaType;
```

然后增加测试方法：

```java
@Test
void updateReturns400WhenNameIsTooShort() throws Exception {
    mockMvc.perform(put("/api/users/1")
            .contentType(MediaType.APPLICATION_JSON)
            .content("""
                    {"name":"A"}
                    """))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.fieldError.name").exists());

    verifyNoInteractions(userService);
}
```

`verifyNoInteractions()` 证明参数在 Controller 入口就被拒绝，没有进入 Service。

#### 第五步：测试 404 异常响应

增加导入：

```java
import com.Shuan.spring_boot_study.exception.UserNotFoundException;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
```

增加测试：

```java
@Test
void findByIdReturns404WhenUserDoesNotExist() throws Exception {
    when(userService.findByIdOrThrow(999L))
            .thenThrow(new UserNotFoundException(999L));

    mockMvc.perform(get("/api/users/999"))
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.status").value(404))
            .andExpect(jsonPath("$.message").exists())
            .andExpect(jsonPath("$.path").value("/api/users/999"));
}
```

如果 `GlobalExceptionHandler` 没有被测试自动加载，在测试类上增加：

```java
@Import(GlobalExceptionHandler.class)
```

并导入：

```java
import com.Shuan.spring_boot_study.exception.GlobalExceptionHandler;
import org.springframework.context.annotation.Import;
```

#### 后续要覆盖的 Controller 场景

- 正常创建、查询、更新和删除
- 参数校验返回 HTTP 400
- 用户不存在返回 HTTP 404
- 删除成功返回 HTTP 204

### 5.3 执行测试

```bash
./mvnw test
```

单独执行某个测试类：

```bash
./mvnw -Dtest=UserServiceTest test
./mvnw -Dtest=UserControllerTest test
```

### 5.4 完成检查

- [ ] Service 测试不依赖 Spring 容器或 MySQL。
- [ ] Controller 测试不连接真实数据库。
- [ ] 200、201、204、400 和 404 都有覆盖。
- [ ] 测试名能说明业务场景。

## 6. Task 14：Repository 集成测试

### 6.1 添加 H2 测试数据库

在 Spring Boot 4 中，JPA 测试支持是独立 starter。在 `pom.xml` 添加 JPA 测试 starter 和 H2：

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-jpa-test</artifactId>
    <scope>test</scope>
</dependency>

<dependency>
    <groupId>com.h2database</groupId>
    <artifactId>h2</artifactId>
    <scope>test</scope>
</dependency>
```

这样测试不依赖本机 MySQL 和 `DB_PASSWORD`。

### 6.2 创建测试配置

新建 `src/test/resources/application.properties`：

```properties
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.driver-class-name=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=
spring.jpa.hibernate.ddl-auto=create-drop
spring.jpa.open-in-view=false
```

### 6.3 编写 Repository 测试

Spring Boot 4 中 `DataJpaTest` 的 import 是：

```java
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
```

不要照搬 Spring Boot 3 教程中的旧包路径。

```java
@DataJpaTest
class UserRepositoryTests {

    @Autowired
    private UserRepository userRepository;

    @Test
    void searchesNameIgnoringCase() {
        userRepository.save(new User("Emma"));
        userRepository.save(new User("Bob"));

        Page<User> result = userRepository.findByNameContainingIgnoreCase(
                "EM",
                PageRequest.of(0, 10)
        );

        assertEquals(1, result.getTotalElements());
        assertEquals("Emma", result.getContent().getFirst().getName());
    }
}
```

### 6.4 完成检查

- [ ] 添加 `spring-boot-starter-data-jpa-test`。
- [ ] H2 仅作为 test 依赖。
- [ ] `./mvnw test` 不需要本机 MySQL 密码。
- [ ] Repository 模糊查询有集成测试。
- [ ] 每个测试之间数据相互隔离。

## 7. Task 15：Flyway 数据库迁移

### 7.1 学习目标

1. 不再依赖 Hibernate 自动修改表结构。
2. 使用可追踪的 SQL 迁移文件管理数据库版本。
3. 理解 Flyway 的版本、顺序和 checksum。

### 7.2 添加依赖

在 `pom.xml` 添加：

```xml
<dependency>
    <groupId>org.flywaydb</groupId>
    <artifactId>flyway-core</artifactId>
</dependency>
<dependency>
    <groupId>org.flywaydb</groupId>
    <artifactId>flyway-mysql</artifactId>
</dependency>
```

### 7.3 创建第一个迁移

新建：

```text
src/main/resources/db/migration/V1__create_users_table.sql
```

内容：

```sql
CREATE TABLE app_users (
    id BIGINT NOT NULL AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL,
    PRIMARY KEY (id)
);
```

如果当前数据库已经由 Hibernate 创建表，学习阶段可先备份数据，然后使用空数据库练习 Flyway，避免 `table already exists`。

### 7.4 关闭 Hibernate 建表

将：

```properties
spring.jpa.hibernate.ddl-auto=update
```

改为：

```properties
spring.jpa.hibernate.ddl-auto=validate
```

Flyway 负责修改表结构，Hibernate 只验证 Entity 与表是否一致。

### 7.5 创建第二个迁移

```text
src/main/resources/db/migration/V2__add_email_to_users.sql
```

```sql
ALTER TABLE app_users
    ADD COLUMN email VARCHAR(255) NULL;
```

已经执行过的迁移不要直接修改，应通过新的 `V3__...sql` 继续演进。

### 7.6 验证

```bash
./mvnw spring-boot:run
```

进入 MySQL 后检查：

```sql
SHOW TABLES;
SELECT * FROM flyway_schema_history ORDER BY installed_rank;
DESCRIBE app_users;
```

### 7.7 已有数据库接入 Flyway

本项目的 `app_users` 在引入 Flyway 之前已经由 Hibernate 创建，并且已有用户数据。如果直接执行 `V1__create_users_table.sql`，会与现有表冲突。

为了保留现有数据，在 `application.properties` 中使用：

```properties
spring.flyway.baseline-on-migrate=true
spring.flyway.baseline-version=1
```

首次启动时，Flyway 会：

1. 发现数据库中已经存在业务表。
2. 创建 `flyway_schema_history`。
3. 将现有数据库标记为基线版本 1。
4. 不再执行 V1 创建表。
5. 从 V2 开始执行后续迁移。

基线配置用于“已有数据库首次接入 Flyway”。从空数据库开始的新项目不需要这个过渡步骤。

### 7.8 实际问题：SQL 执行位置错误

本次在 zsh 提示符下直接执行了：

```text
➜  ~ SHOW TABLES;
```

导致：

```text
zsh: command not found: SHOW
```

`SHOW`、`SELECT` 和 `DESCRIBE` 是 SQL，必须在 `mysql>` 提示符下执行。

先在 zsh 中登录：

```bash
mysql -h 127.0.0.1 -u spring_user -p spring_boot_study
```

看到：

```text
mysql>
```

再执行：

```sql
SHOW TABLES;
SELECT * FROM flyway_schema_history ORDER BY installed_rank;
DESCRIBE app_users;
```

也可以在 zsh 中通过 MySQL 客户端的 `-e` 参数执行：

```bash
mysql -h 127.0.0.1 -u spring_user -p spring_boot_study \
  -e 'SHOW TABLES; DESCRIBE app_users;'
```

### 7.9 实际问题：使用了错误的 MySQL 账户

执行：

```bash
mysql -u root -p
```

返回：

```text
Access denied for user 'root'@'localhost'
```

这表示当前输入的 root 密码不正确，不代表 MySQL 服务已损坏。项目本来使用的是 `spring_user`，因此应使用项目账户登录目标数据库：

```bash
mysql -h 127.0.0.1 -u spring_user -p spring_boot_study
```

直接执行 `mysql` 时，MySQL 客户端默认尝试使用当前 macOS 用户名 `hanli` 且不使用密码，因此也会被 MySQL 拒绝。

### 7.10 实际问题：Flyway 没有发现迁移文件

错误文件位置和名称：

```text
src/main/resources/V1_create_users_table.sql
src/main/resources/V2_add_email_to_users.sql
```

正确写法：

```text
src/main/resources/db/migration/V1__create_users_table.sql
src/main/resources/db/migration/V2__add_email_to_users.sql
```

注意：

1. 默认扫描目录是 `classpath:db/migration`。
2. 版本号和描述之间是两个下划线 `__`。
3. SQL 语句末尾应有分号。
4. 只有在应用成功启动并运行 Flyway 后，`flyway_schema_history` 才会出现。

### 7.11 学习问答

#### 问题 1：为什么项目一开始不使用 Flyway？

因为项目刚开始时，学习重点是 Spring Boot、JPA 和 CRUD，而不是数据库版本管理。

最初使用：

```properties
spring.jpa.hibernate.ddl-auto=update
```

Hibernate 会根据 Entity 自动创建或修改表，好处是配置简单，可以先集中学习：

- Controller、Service 和 Repository 分层
- JPA Entity 映射
- CRUD
- 参数校验和异常处理

如果一开始就使用 Flyway，还要同时理解 SQL 建表、迁移版本、checksum、baseline 和 Entity 与表结构同步，初期负担较大。

当 CRUD 和 JPA 已经跑通后，再引入 Flyway，学习重点就从“快速建表”转为“可追踪地演进数据库”。

```text
学习初期：Hibernate update 快速建表
        ↓
CRUD 和 JPA 完成
        ↓
工程化阶段：Flyway 管理表结构
```

在真实的新生产项目中，通常建议从开始就使用 Flyway。本项目中途引入，是为了采用渐进式学习顺序。

#### 问题 2：“不再依赖 Hibernate 自动修改表结构”怎么理解？

它的意思是：

> Java Entity 发生变化后，Hibernate 不再自动修改 MySQL 表；所有数据库结构变化都必须通过 Flyway SQL 明确完成。

以前的配置：

```properties
spring.jpa.hibernate.ddl-auto=update
```

如果在 `User` 中增加：

```java
private String email;
```

Hibernate 启动时可能自动执行类似的 SQL：

```sql
ALTER TABLE app_users ADD COLUMN email VARCHAR(255);
```

流程是：

```text
修改 Entity
    ↓
启动应用
    ↓
Hibernate 自动修改表
```

引入 Flyway 后使用：

```properties
spring.jpa.hibernate.ddl-auto=validate
```

`validate` 只检查映射，不会执行 `CREATE TABLE` 或 `ALTER TABLE`。添加 `email` 时，必须同时：

1. 创建 Flyway 迁移：

```sql
ALTER TABLE app_users
    ADD COLUMN email VARCHAR(255) NULL;
```

2. 修改 Java Entity：

```java
private String email;
```

启动流程变为：

```text
Flyway 执行新版本 SQL
    ↓
MySQL 表结构发生变化
    ↓
Hibernate 验证 Entity 和表结构
    ↓
一致：继续启动
不一致：启动失败
```

职责分工：

| 组件 | 职责 |
|---|---|
| Flyway | 创建和修改数据库结构 |
| Hibernate / JPA | 将 Java 对象映射为数据库记录 |
| `ddl-auto=validate` | 检查 Entity 和数据库结构是否匹配 |

如果只修改 Entity，但没有新增 Flyway 迁移，Hibernate 会在启动时报错，而不是自动补字段。

以后修改数据库的正确流程：

```text
1. 设计数据库变化
2. 创建新的 Flyway SQL
3. 修改对应 Entity
4. 更新 DTO 和业务代码
5. 启动应用执行迁移
6. Hibernate 验证映射
7. 运行测试
8. SQL 和 Java 代码一起提交
```

核心记忆：

```text
update   = Hibernate 帮我改表
validate = Flyway 改表，Hibernate 只检查
```

### 7.12 完成检查

- [ ] Flyway 依赖加载成功。
- [ ] `V1` 能创建 `app_users`。
- [ ] `V2` 能添加字段。
- [ ] `ddl-auto` 使用 `validate`。
- [ ] 理解为什么不应修改已执行过的迁移。

## 8. Task 16：多环境配置与敏感信息

### 8.1 拆分配置

`application.properties` 保留公共配置：

```properties
spring.application.name=spring-boot-study
spring.jpa.open-in-view=false
```

`application-dev.properties` 放本地开发配置：

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/spring_boot_study?useUnicode=true&characterEncoding=utf8&serverTimezone=Asia/Shanghai
spring.datasource.username=${DB_USERNAME:spring_user}
spring.datasource.password=${DB_PASSWORD}
spring.jpa.show-sql=true
```

`src/test/resources/application-test.properties` 放测试配置：

```text
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.username=sa
spring.datasource.password=
```

测试类使用：

```java
@ActiveProfiles("test")
```

### 8.2 启动开发环境

```bash
read -s "DB_PASSWORD?MySQL password: "
export DB_PASSWORD
echo
./mvnw spring-boot:run -Dspring-boot.run.profiles=dev
```

`export` 使 Maven 和 Java 子进程能读到 `DB_PASSWORD`。只执行 `read` 而不 `export` 时，Spring Boot 子进程无法获取该变量。

### 8.3 生产配置原则

`application-prod.properties` 只引用环境变量：

```properties
spring.datasource.url=${DB_URL}
spring.datasource.username=${DB_USERNAME}
spring.datasource.password=${DB_PASSWORD}
spring.jpa.show-sql=false
```

不要向 Git 提交真实密码、JWT 密钥或云服务凭据。

### 8.4 学习问答：只能通过命令行选择 dev 或 prod 吗？

不是。Spring Profile 可以通过命令行、环境变量、JVM 参数或 IntelliJ IDEA Run Configuration 选择。

当激活 `dev` 时，Spring Boot 会合并：

```text
application.properties
        +
application-dev.properties
```

当激活 `prod` 时，Spring Boot 会合并：

```text
application.properties
        +
application-prod.properties
```

后加载的 Profile 配置会覆盖公共配置中的同名属性。Profile 名称必须与文件名一致：当文件是 `application-prod.properties` 时，应激活 `prod`，不是 `product`。

#### 在 IntelliJ IDEA 中配置 dev

1. 打开 `Run` → `Edit Configurations...`。
2. 选择当前的 `SpringBootStudyApplication` 运行配置。
3. 将配置名修改为 `SpringBootStudyApplication-dev`。
4. 如果页面没有 `Active profiles`，点击 `Modify options`，启用 Spring Boot 的 Active Profiles 选项。
5. 在 `Active profiles` 中输入 `dev`。
6. 在 `Environment variables` 中添加 `DB_PASSWORD=开发数据库密码`。
7. 点击 `Apply` 和 `Run`。

启动日志应出现：

```text
The following 1 profile is active: "dev"
```

IDEA 的运行配置通常保存在本地 `.idea/workspace.xml` 中。确保 `.idea/` 不被提交，避免意外上传密码。

#### 在 IntelliJ IDEA 中配置 prod

1. 复制 dev 运行配置。
2. 命名为 `SpringBootStudyApplication-prod`。
3. 将 `Active profiles` 改为 `prod`。
4. 在 `Environment variables` 中配置 `DB_URL`、`DB_USERNAME` 和 `DB_PASSWORD`。

本地学习时一般只运行 dev。prod 配置用于生产环境，不要为了测试而随意连接真实生产数据库。

#### IDEA 中没有 Active profiles 输入框

可以使用以下任意一种备选方式。

Program arguments：

```text
--spring.profiles.active=dev
```

VM options：

```text
-Dspring.profiles.active=dev
```

Environment variables：

```text
SPRING_PROFILES_ACTIVE=dev
```

只需选择一种方式，不必同时配置。

### 8.5 本次配置检查结果

第二次检查时，以下内容已经正确：

- `application.properties` 已只保留公共配置。
- MySQL URL、开发账户和 `show-sql=true` 已移入 `application-dev.properties`。
- Flyway baseline 过渡配置已移入 dev。
- `application-prod.properties` 通过环境变量读取数据库配置。
- 重复的 `src/test/resources/application.properties` 已删除。

仍需修正：

1. `application-test.properties` 仍然连接 MySQL，应改为 H2。
2. `SpringBootStudyApplicationTests` 和 `UserRepositoryTests` 仍未添加 `@ActiveProfiles("test")`。

此时执行 `./mvnw test` 会失败：

```text
Schema validation: missing table [app_users]
```

原因是：

```text
没有激活 test Profile
    ↓
application-test.properties 没有加载
    ↓
H2 是空数据库
    ↓
继承公共 ddl-auto=validate
    ↓
Hibernate 只检查，不建表
    ↓
报 missing table [app_users]
```

`application-test.properties` 应改为：

```properties
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.driver-class-name=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=
spring.jpa.hibernate.ddl-auto=create-drop
spring.jpa.open-in-view=false
spring.flyway.enabled=false
```

在需要数据库的测试类上添加：

```java
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
```

第三次检查时，上述 H2 配置和 `@ActiveProfiles("test")` 已添加正确，完整测试结果为：

```text
Tests run: 5, Failures: 0, Errors: 0, Skipped: 0
BUILD SUCCESS
```

测试日志显示：

```text
The following 1 profile is active: "test"
```

这证明 `application-test.properties` 已正确加载。目前只剩一个清理项：`src/test/resources/application.properties` 仍然存在，与 `application-test.properties` 重复，应删除后再执行一次完整测试。

期望的最终职责：

| 文件 | 职责 |
|---|---|
| `application.properties` | 所有环境共用配置 |
| `application-dev.properties` | 本地 MySQL 开发配置 |
| `application-test.properties` | H2 测试配置 |
| `application-prod.properties` | 从环境变量读取的生产配置 |

### 8.6 完成检查

- [ ] dev、test 和 prod 配置分离。
- [ ] 测试不依赖本地 MySQL。
- [ ] Git 中没有数据库密码。
- [ ] 能解释 shell 变量和已导出环境变量的区别。

## 9. Task 17：扩展真实业务

### 实际问题：为什么启动时报 `missing column [created_at]`？

**问：数据库已经连接成功，为什么应用还是启动失败？**

答：`HikariPool-1 - Start completed` 只代表 JDBC 成功连接 MySQL。随后 Hibernate 根据 `spring.jpa.hibernate.ddl-auto=validate` 对比 Entity 与真实表结构，发现 `User.createdAt` 对应的 `app_users.created_at` 不存在，于是阻止应用启动。这说明 `validate` 正在正常保护数据库结构。

**问：为什么 Flyway 没有自动创建 `created_at`？**

答：有两个原因：

1. Spring Boot 4.1.1 已将 Flyway 自动配置拆分为单独模块。只引入 `flyway-core` 和 `flyway-mysql` 不足以启用 Boot 自动配置，需要 `spring-boot-starter-flyway`。
2. 项目目录实际只有 V1 和 V2，原计划中的 `V3__complete_user_business_fields.sql` 尚未创建，没有迁移负责添加 `status`、`created_at` 和 `updated_at`。

**问：当前开发环境的 `baseline-version` 应该是多少？**

答：实际运行 V3 时出现 `Unknown column 'email'`，证明当前旧库只有 V1 的 `id`、`name` 结构，并没有 V2 的 `email`。因此 baseline 必须为 1，让 Flyway 接着执行 V2 和 V3。baseline 版本不能凭代码或印象判断，必须以 `DESCRIBE app_users;` 的真实结果为准。

**问：为什么不能把 `ddl-auto` 临时改回 `update`？**

答：`update` 会让 Hibernate 直接修改数据库，虽然可能临时补上列，却绕过 Flyway 的迁移版本记录。其他环境便无法可靠复现结构。正确分工是：Flyway 负责修改结构，Hibernate 的 `validate` 负责检查结构。

本次修复：

- 将 `flyway-core` 换成 `spring-boot-starter-flyway`，并保留 `flyway-mysql`。
- 新增 `V3__complete_user_business_fields.sql`。
- 将开发环境 baseline 设为 1，让已有的 V1 旧库继续执行 V2、V3。
- 将 Java 字段 `updateAt` 统一为 `updatedAt`，对应数据库列 `updated_at`。

重新启动后，应先看到 Flyway 执行 V3，再看到 Hibernate 完成 schema validation。可以进入 MySQL 验证：

```sql
SELECT * FROM flyway_schema_history ORDER BY installed_rank;
DESCRIBE app_users;
```

### 实际问题：V3 首次失败后，为什么第二次启动直接 validation failed？

**问：第一次是 `Unknown column 'email'`，第二次为什么变成 `Detected failed migration to version 3`？**

答：第一次迁移失败后，Flyway 已经在 `flyway_schema_history` 中记录 V3 的失败状态。第二次启动时 Flyway 会先校验历史记录，发现失败记录后停止，不会盲目重试。这是为了避免在一个可能只执行了一半的数据库上继续修改。

本次 V3 在第一条 `UPDATE` 就因为 `email` 不存在而失败，V3 后面的 `ALTER TABLE` 尚未执行。又因为这张历史表是刚刚对旧开发库进行错误 baseline 时创建的，所以修正 baseline 为 1 后，可以删除这张刚创建的历史表并重新接管：

```sql
DROP TABLE flyway_schema_history;
```

然后重新启动。Flyway 会依次执行：baseline V1 → V2 添加 `email` → V3 补齐业务字段。这里不能形成“遇到迁移失败就删历史表”的习惯；已经多人共用或已经上线的数据库，应先检查半成品结构并使用正式 repair/补偿迁移方案。

### 问：命令行启动和直接在 IDEA Run 有什么区别？

本质上没有区别，两者最终都会执行 `SpringBootStudyApplication.main()` 并启动同一个 Spring Boot 应用。真正影响运行结果的是启动时传入的配置是否一致。

命令行中的操作分别表示：

```bash
read -s "DB_PASSWORD?MySQL password: "
```

安全读取密码到当前 shell 的 `DB_PASSWORD` 变量；`-s` 表示输入时不回显。

```bash
export DB_PASSWORD
```

把 shell 变量导出成环境变量，使随后启动的 Java 子进程可以读取 `${DB_PASSWORD}`。

```bash
echo
```

密码输入不回显，也不会自动显示一个整洁的新行；这里仅用于改善终端显示，与 Spring Boot 无关。

```bash
./mvnw spring-boot:run -Dspring-boot.run.profiles=dev
```

通过项目自带的 Maven Wrapper 启动应用，并激活 `dev` Profile，因此会同时加载：

```text
application.properties
application-dev.properties
```

如果直接点击 IDEA Run，而 Run Configuration 没有配置环境变量和 Active Profile，应用可能：

- 读取不到 `${DB_PASSWORD}`；
- 没有激活 `dev`；
- 不加载 `application-dev.properties`；
- 最终表现得和命令行启动不同。

在 IDEA 中打开：

```text
Run → Edit Configurations → SpringBootStudyApplication
```

设置：

```text
Active profiles: dev
Environment variables: DB_PASSWORD=你的数据库密码
```

如果界面没有 `Active profiles` 输入框，也可以在 `Program arguments` 中填写：

```text
--spring.profiles.active=dev
```

或者在 `VM options` 中填写：

```text
-Dspring.profiles.active=dev
```

三种 Profile 写法选择一种即可，不要重复配置。配置完成后，IDEA Run 与命令行启动应加载相同的数据库和 Flyway 配置。为了避免泄露密码，不要把密码写进 `application.properties`、Git 仓库或截图中。

### 问：以后每增加一张业务表，都需要新建一个 `.sql` 文件吗？

通常需要，但准确规则不是“一张表对应一个文件”，而是“每次数据库结构变更对应一个新的 Flyway 版本”。例如：

```text
V1__create_users_table.sql
V2__add_email_to_users.sql
V3__complete_user_business_fields.sql
V4__create_products_table.sql
V5__create_orders_and_order_items.sql
V6__add_index_to_orders.sql
```

一个迁移文件可以创建一张表，也可以创建同一个业务功能所需的多张紧密相关的表。例如订单与订单明细必须配合使用，可以放在同一个 V5 中；如果变更互不相关，拆开更容易排错和回滚。

需要新建迁移文件的典型情况：

- 创建或删除表；
- 添加、删除或修改列；
- 添加索引、唯一约束、外键；
- 修改字段类型或默认值；
- 必须跟随结构升级的基础数据转换。

不需要数据库迁移的情况：

- 只新增 Controller 页面或 API；
- 只修改 Java 业务逻辑；
- 只修改 DTO；
- 只改接口返回格式且数据库结构未变化。

已经成功执行并提交共享的 V1、V2、V3 不应直接修改。下一次结构变化应创建 V4。Flyway 通过 `flyway_schema_history` 判断每个环境还缺哪些版本，并只执行尚未执行的迁移。

普通业务数据不建议写入版本迁移，例如用户注册产生的数据应由应用写入。系统必须存在的固定数据，例如角色、权限或字典项，可以使用单独的版本迁移插入，但 SQL 应尽量设计成可预测且不会产生重复数据。

### 9.1 学习目标

1. 为用户增加邮箱、状态、创建时间和更新时间。
2. 使用 Flyway 安全处理现有数据。
3. 同时在 Service 和数据库层保证邮箱唯一。
4. 使用 HTTP 409 表示唯一资源冲突。
5. 使用 PATCH 只更新客户端传入的字段。
6. 为新增业务规则编写测试。

### 9.2 实施顺序

不要一次同时修改所有文件。按照下列顺序，每一步编译通过后再继续：

```text
1. 设计字段和迁移策略
2. 创建 V3 Flyway 迁移
3. 创建 UserStatus
4. 完整修改 User Entity
5. 修改 Request / Response DTO
6. 修改 Repository
7. 创建重复邮箱异常
8. 修改 Service
9. 修改 Controller
10. 启动并验证 Flyway
11. 执行 curl 测试
12. 补充自动化测试
```

### 9.3 为什么不直接修改 V2

`V2__add_email_to_users.sql` 已经存在，并可能已记录在 `flyway_schema_history`中。已执行的迁移不能直接修改，否则 Flyway 会发现 checksum 变化并拒绝启动。

因此本任务创建：

```text
src/main/resources/db/migration/V3__complete_user_business_fields.sql
```

### 9.4 安全迁移现有用户数据

当前数据库已有 David、Emma 等用户，他们的 `email` 是 `NULL`。如果立即把 email 改成 `NOT NULL`，迁移会失败。

`V3__complete_user_business_fields.sql` 使用以下顺序：

```sql
UPDATE app_users
SET email = CONCAT('legacy-', id, '@example.invalid')
WHERE email IS NULL OR TRIM(email) = '';

ALTER TABLE app_users
    MODIFY COLUMN email VARCHAR(255) NOT NULL,
    ADD COLUMN status VARCHAR(20) NOT NULL DEFAULT 'ACTIVE',
    ADD COLUMN created_at DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    ADD COLUMN updated_at DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    ADD CONSTRAINT uk_app_users_email UNIQUE (email);
```

这里使用 `example.invalid` 为历史用户生成不会真实投递的占位邮箱。真实生产系统通常通过正式的数据清理任务补齐历史数据。

执行前先确认 V2 是否已完成：

```sql
SELECT installed_rank, version, description, success
FROM flyway_schema_history
ORDER BY installed_rank;
```

### 9.5 创建 UserStatus

新建：

```text
src/main/java/com/Shuan/spring_boot_study/model/UserStatus.java
```

内容：

```java
package com.Shuan.spring_boot_study.model;

public enum UserStatus {
    ACTIVE,
    DISABLED
}
```

Java 枚举常量统一使用大写。不要在一处写 `ACTIVE`，另一处写 `Active`。

### 9.6 完整修改 User Entity

`User.java` 修改为：

```java
package com.Shuan.spring_boot_study.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;

@Entity
@Table(name = "app_users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(nullable = false, unique = true, length = 255)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private UserStatus status = UserStatus.ACTIVE;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    @UpdateTimestamp
    @Column(nullable = false)
    private Instant updatedAt;

    protected User() {
    }

    public User(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public void changeName(String name) {
        this.name = name;
    }

    public void changeEmail(String email) {
        this.email = email;
    }

    public void changeStatus(UserStatus status) {
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public UserStatus getStatus() {
        return status;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }
}
```

注意拼写必须一致：

```text
status     不是 staus
updatedAt  不是 updateAt
ACTIVE     不是 Active
```

### 9.7 修改 CreateUserRequest

`record` 不需要写 `<name,email>` 泛型。两个组件之间必须有逗号。

```java
package com.Shuan.spring_boot_study.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateUserRequest(
        @NotBlank(message = "用户名不能为空")
        @Size(min = 2, max = 50, message = "用户名长度必须在2到50个字符之间")
        String name,

        @NotBlank(message = "邮箱不能为空")
        @Email(message = "邮箱格式不正确")
        @Size(max = 255, message = "邮箱长度不能超过255个字符")
        String email
) {
}
```

`@NotBlank` 检查空值和空白字符；`@Email` 检查格式。两者不能用两个 `@NotBlank` 替代。

### 9.8 修改 UpdateUserRequest

PUT 表示更新完整用户资源，因此同时接收 name 和 email：

```java
public record UpdateUserRequest(
        @NotBlank(message = "用户名不能为空")
        @Size(min = 2, max = 50, message = "用户名长度必须在2到50个字符之间")
        String name,

        @NotBlank(message = "邮箱不能为空")
        @Email(message = "邮箱格式不正确")
        @Size(max = 255, message = "邮箱长度不能超过255个字符")
        String email
) {
}
```

### 9.9 创建 PatchUserRequest

PATCH 字段允许为 `null`，因为 `null` 表示“没有要求更新该字段”。`@Size`、`@Email` 和 `@Pattern` 对 `null` 不报错，但对实际传入的非法值报错。

新建 `dto/PatchUserRequest.java`：

```java
package com.Shuan.spring_boot_study.dto;

import com.Shuan.spring_boot_study.model.UserStatus;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record PatchUserRequest(
        @Pattern(regexp = ".*\\S.*", message = "用户名不能全是空白字符")
        @Size(min = 2, max = 50, message = "用户名长度必须在2到50个字符之间")
        String name,

        @Email(message = "邮箱格式不正确")
        @Size(max = 255, message = "邮箱长度不能超过255个字符")
        String email,

        UserStatus status
) {
}
```

### 9.10 修改 UserResponse

```java
package com.Shuan.spring_boot_study.dto;

import com.Shuan.spring_boot_study.model.User;
import com.Shuan.spring_boot_study.model.UserStatus;

import java.time.Instant;

public record UserResponse(
        Long id,
        String name,
        String email,
        UserStatus status,
        Instant createdAt,
        Instant updatedAt
) {
    public static UserResponse from(User user) {
        return new UserResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getStatus(),
                user.getCreatedAt(),
                user.getUpdatedAt()
        );
    }
}
```

### 9.11 修改 UserRepository

邮箱查询方法属于 Repository，不是 Service 接口声明。

```java
public interface UserRepository extends JpaRepository<User, Long> {

    Page<User> findByNameContainingIgnoreCase(String name, Pageable pageable);

    boolean existsByEmailIgnoreCase(String email);

    boolean existsByEmailIgnoreCaseAndIdNot(String email, Long id);
}
```

`existsByEmailIgnoreCaseAndIdNot()` 用于更新用户：检查邮箱是否属于其他用户，排除当前用户自己。

### 9.12 创建 DuplicateEmailException

新建 `exception/DuplicateEmailException.java`：

```java
package com.Shuan.spring_boot_study.exception;

public class DuplicateEmailException extends RuntimeException {

    public DuplicateEmailException(String email) {
        super("邮箱已被使用：" + email);
    }
}
```

在 `GlobalExceptionHandler` 中增加：

```java
@ExceptionHandler(DuplicateEmailException.class)
@ResponseStatus(HttpStatus.CONFLICT)
public ApiError handleDuplicateEmail(
        DuplicateEmailException exception,
        HttpServletRequest request
) {
    return new ApiError(
            Instant.now(),
            HttpStatus.CONFLICT.value(),
            HttpStatus.CONFLICT.getReasonPhrase(),
            exception.getMessage(),
            request.getRequestURI(),
            Map.of("email", exception.getMessage())
    );
}
```

HTTP 409 Conflict 表示请求格式正确，但与当前资源状态冲突。

### 9.13 修改 UserService

为了保证邮箱比较一致，先统一去除首尾空格并转为小写：

```java
private String normalizeEmail(String email) {
    return email.strip().toLowerCase(Locale.ROOT);
}
```

创建用户：

```java
@Transactional
public User create(String name, String email) {
    String normalizedEmail = normalizeEmail(email);

    if (userRepository.existsByEmailIgnoreCase(normalizedEmail)) {
        throw new DuplicateEmailException(normalizedEmail);
    }

    User user = new User(name.strip(), normalizedEmail);
    return userRepository.save(user);
}
```

PUT 更新：

```java
@Transactional
public User update(Long id, String name, String email) {
    User user = findByIdOrThrow(id);
    String normalizedEmail = normalizeEmail(email);

    if (userRepository.existsByEmailIgnoreCaseAndIdNot(normalizedEmail, id)) {
        throw new DuplicateEmailException(normalizedEmail);
    }

    user.changeName(name.strip());
    user.changeEmail(normalizedEmail);
    return user;
}
```

PATCH 部分更新：

```java
@Transactional
public User patch(Long id, PatchUserRequest request) {
    User user = findByIdOrThrow(id);

    if (request.name() != null) {
        user.changeName(request.name().strip());
    }

    if (request.email() != null) {
        String normalizedEmail = normalizeEmail(request.email());
        if (userRepository.existsByEmailIgnoreCaseAndIdNot(normalizedEmail, id)) {
            throw new DuplicateEmailException(normalizedEmail);
        }
        user.changeEmail(normalizedEmail);
    }

    if (request.status() != null) {
        user.changeStatus(request.status());
    }

    return user;
}
```

需要导入：

```java
import com.Shuan.spring_boot_study.dto.PatchUserRequest;
import com.Shuan.spring_boot_study.exception.DuplicateEmailException;
import java.util.Locale;
```

Service 中不应出现下列无实现的方法声明：

```java
boolean existsByEmailIgnoreCase(String email);
```

该方法应定义在 `UserRepository`。

### 9.14 修改 UserController

POST 传入 email：

```java
@PostMapping
@ResponseStatus(HttpStatus.CREATED)
public UserResponse create(@Valid @RequestBody CreateUserRequest request) {
    return UserResponse.from(
            userService.create(request.name(), request.email())
    );
}
```

PUT 传入 email：

```java
@PutMapping("/{id}")
public UserResponse update(
        @PathVariable Long id,
        @Valid @RequestBody UpdateUserRequest request
) {
    return UserResponse.from(
            userService.update(id, request.name(), request.email())
    );
}
```

增加 PATCH：

```java
@PatchMapping("/{id}")
public UserResponse patch(
        @PathVariable Long id,
        @Valid @RequestBody PatchUserRequest request
) {
    return UserResponse.from(userService.patch(id, request));
}
```

### 9.15 先编译，再启动数据库迁移

先检查 Java 语法：

```bash
./mvnw test -DskipTests
```

编译通过后，使用 dev Profile 启动：

```bash
read -s "DB_PASSWORD?MySQL password: "
export DB_PASSWORD
echo
./mvnw spring-boot:run -Dspring-boot.run.profiles=dev
```

启动成功后检查：

```sql
SELECT installed_rank, version, description, success
FROM flyway_schema_history
ORDER BY installed_rank;

DESCRIBE app_users;

SELECT id, name, email, status, created_at, updated_at
FROM app_users;
```

### 9.16 使用 curl 验证

创建用户：

```bash
curl -i \
  -X POST \
  -H 'Content-Type: application/json' \
  -d '{"name":"Alice","email":"Alice@Example.com"}' \
  http://localhost:8080/api/users
```

预期 HTTP 201，响应中 email 已规范化为小写：

```json
{
  "id": 5,
  "name": "Alice",
  "email": "alice@example.com",
  "status": "ACTIVE",
  "createdAt": "...",
  "updatedAt": "..."
}
```

创建重复邮箱：

```bash
curl -i \
  -X POST \
  -H 'Content-Type: application/json' \
  -d '{"name":"Another Alice","email":"ALICE@example.com"}' \
  http://localhost:8080/api/users
```

预期 HTTP 409。

邮箱格式错误：

```bash
curl -i \
  -X POST \
  -H 'Content-Type: application/json' \
  -d '{"name":"Invalid Email","email":"not-an-email"}' \
  http://localhost:8080/api/users
```

预期 HTTP 400。

只修改名称，不清空 email 和 status：

```bash
curl -i \
  -X PATCH \
  -H 'Content-Type: application/json' \
  -d '{"name":"Alice Updated"}' \
  http://localhost:8080/api/users/5
```

禁用用户：

```bash
curl -i \
  -X PATCH \
  -H 'Content-Type: application/json' \
  -d '{"status":"DISABLED"}' \
  http://localhost:8080/api/users/5
```

传入未知状态时，JSON 解析将失败：

```bash
curl -i \
  -X PATCH \
  -H 'Content-Type: application/json' \
  -d '{"status":"UNKNOWN"}' \
  http://localhost:8080/api/users/5
```

后续可为 `HttpMessageNotReadableException` 补充统一 HTTP 400 错误处理。

### 9.17 更新自动化测试

旧测试中的：

```java
new User("Emma")
```

需要改为：

```java
new User("Emma", "emma@example.com")
```

至少增加以下测试：

- 创建用户会保存规范化后的邮箱。
- 重复邮箱抛出 `DuplicateEmailException`。
- POST 邮箱格式错误返回 400。
- POST 重复邮箱返回 409。
- PATCH 只修改 name 时保留 email。
- Repository 不区分大小写检查邮箱。

最后执行：

```bash
./mvnw test
```

### 9.18 常见问题

#### CreateUserRequest 报语法错误

不要写：

```java
public record CreateUserRequest<name,email>(
```

`<name,email>` 会被 Java 当成泛型参数。正确写法是：

```java
public record CreateUserRequest(
```

record 的两个字段之间必须有逗号。

#### cannot find symbol: STring

Java 区分大小写，正确类名是：

```java
String
```

不是：

```java
STring
```

#### Hibernate 报 email 或 status 字段不匹配

检查：

1. V3 是否已成功执行。
2. Entity 字段和 SQL 列名是否对应。
3. `status` 是否使用 `@Enumerated(EnumType.STRING)`。
4. `ddl-auto` 是否仍为 `validate`。

#### Flyway 报 checksum mismatch

说明已经执过的 V1 或 V2 被修改。不要随意删除 `flyway_schema_history`或执行 repair。将新变化放在 V3、V4 等新迁移中。

#### 实际编译错误：Task 17 一次出现 18 个错误

本次执行：

```bash
./mvnw test -DskipTests
```

出现 18 个编译错误。它们不是 18 个独立问题，而是以下几组根因造成的连锁报错。

1. `UserController` 同时保留了旧的 POST/PUT 和新的 POST/PUT，造成方法重复。
2. 旧 Controller 方法仍以旧参数数量调用 Service。
3. `PatchUserRequest` 文件已存在，但 Controller 和 Service 没有 import。
4. `DuplicateEmailException` 被拼成了 `DuplicateEmailExcption`。
5. Repository 中的 `exists` 和 `Ignore` 拼写错误。
6. `@Valid` 和 `@RequestBody` 拼写及大小写错误。
7. Service 缺少 `Locale` 和异常类 import。
8. `UpdateUserRequest` 缺少 `Email` import。

修复时按根因顺序处理，不要按 Maven 输出的 18 行随机修改：

```text
删除重复 Controller 方法
    ↓
修正文件名和类名拼写
    ↓
修正 Repository 方法名
    ↓
补齐 import
    ↓
重新编译
    ↓
再处理剩余错误
```

### 9.19 完成检查

- [ ] 创建 `V3__complete_user_business_fields.sql`。
- [ ] V3 为历史用户填充唯一占位邮箱。
- [ ] 创建 `UserStatus` 枚举。
- [ ] `User` 中字段名和 getter 完整正确。
- [ ] Create/Update/Patch DTO 校验正确。
- [ ] `UserResponse` 返回新字段。
- [ ] 邮箱查询方法位于 Repository。
- [ ] 创建 `DuplicateEmailException`。
- [ ] 重复邮箱返回 HTTP 409。
- [ ] POST、PUT 和 PATCH 都正确处理 email。
- [ ] PATCH 不会清空未传入的字段。
- [ ] Flyway 历史显示 V3 执行成功。
- [ ] `./mvnw test` 显示 `BUILD SUCCESS`。

### 9.20 学习记录

```text
完成日期：

V3 迁移结果：

正常创建用户响应：

重复邮箱响应：

PATCH 响应：

我对 Service 唯一性检查的理解：

我对数据库唯一约束的理解：

我对 PUT 和 PATCH 区别的理解：

遇到的问题：

解决方法：
```

## 10. Task 18：认证与权限

> 本任务不要一次性把注册、登录、JWT、角色全部写完。每个阶段都必须先编译、运行和验证，再进入下一阶段。

### 10.0 当前完成度检查（2026-10-03）

当前约完成 **15%**。

| 内容 | 当前状态 | 说明 |
|---|---|---|
| Security 依赖 | 已完成 | security 与 resource-server 已加入 pom |
| `PasswordEncoder` Bean | 已完成 | 已放入 `SecurityConfig` |
| `AuthService` | 仅有骨架 | `RegisteredRequest` 不存在，当前不能编译 |
| V4 密码与角色迁移 | 未开始 | 数据库还没有 `password_hash`、`role` |
| User 密码与角色字段 | 未开始 | 还不能保存认证信息 |
| 注册 DTO/API | 未开始 | 没有 RegisterRequest、AuthController |
| 登录 DTO/API | 未开始 | 没有 LoginRequest、登录验证 |
| JWT 签发与验证 | 未开始 | 没有 JwtService、Encoder、Decoder |
| URL 权限规则 | 未开始 | 还没有 SecurityFilterChain |
| 401/403 测试 | 未开始 | 还没有安全测试 |

当前首个编译错误：

```text
cannot find symbol: class RegisteredRequest
```

原因有两个：

1. 项目里没有这个 DTO；
2. 名称应该使用 `RegisterRequest`，不是 `RegisteredRequest`。

`AuthService` 中下面这个 import 也应删除，它与注册请求无关：

```java
import jdk.jfr.Registered;
```

### 10.0.1 本任务最终文件结构

完成后应该新增或修改：

```text
config/
  SecurityConfig.java
controller/
  AuthController.java
dto/
  RegisterRequest.java
  LoginRequest.java
  AuthResponse.java
exception/
  InvalidCredentialsException.java
model/
  User.java
  UserRole.java
repository/
  UserRepository.java
service/
  AuthService.java
  JwtService.java
resources/db/migration/
  V4__add_authentication_fields.sql
```

### 10.0.2 阶段一：先确认 Task 17 数据库完成

进入 MySQL：

```sql
SELECT version, description, success
FROM flyway_schema_history
ORDER BY installed_rank;

DESCRIBE app_users;
```

必须确认 V3 成功，且存在 `email`、`status`、`created_at`、`updated_at`，再创建 V4。

### 10.0.3 阶段二：V4 添加认证字段

创建：

```text
src/main/resources/db/migration/V4__add_authentication_fields.sql
```

学习项目已有旧用户，因此不能直接添加无默认值的 NOT NULL 密码。先为旧数据写入一个不可用于正常登录的 BCrypt 哈希：

```sql
ALTER TABLE app_users
    ADD COLUMN password_hash VARCHAR(100) NULL,
    ADD COLUMN role VARCHAR(20) NOT NULL DEFAULT 'USER';

UPDATE app_users
SET password_hash = '$2a$10$7EqJtq98hPqEX7fNZaFWoO5uR7ZqXwJxY6sL6S9lEOhAandQKUWjK'
WHERE password_hash IS NULL;

ALTER TABLE app_users
    MODIFY COLUMN password_hash VARCHAR(100) NOT NULL;
```

说明：这里的旧用户密码只是迁移占位方案。真实系统应执行密码重置流程，不能给所有旧用户分配同一个公开密码。

启动应用，让 Flyway 执行 V4，然后验证：

```sql
SELECT version, description, success
FROM flyway_schema_history
ORDER BY installed_rank;

DESCRIBE app_users;
```

#### 实际错误：Flyway 提示迁移文件未按命名规则，但应用仍然启动

日志：

```text
1 SQL migrations were detected but not run because they did not follow the filename convention
Current version of schema: 3
Schema is up to date. No migration necessary.
```

错误文件名：

```text
V4_Add_authentication_fields.sql
```

正确文件名：

```text
V4__add_authentication_fields.sql
  ↑ 两个下划线
```

Flyway 的版本迁移格式是 `V版本__描述.sql`，版本与描述之间必须是两个下划线。文件名无效时 Flyway 默认可能只警告并忽略，应用仍能启动，因此不能只看最后的 `Started`，还必须检查 Flyway 日志中的当前版本。

本次 SQL 内还有两处拼写错误：

```sql
password hash  -- 错误：中间是空格
password_has   -- 错误：缺少最后的 h
password_hash  -- 正确
```

由于文件名无效，Flyway 完全没有执行该文件，数据库没有发生 V4 的半完成修改，因此可以直接修正文件名和 SQL 后重新启动。为了以后让无效名称直接导致启动失败，可配置：

```properties
spring.flyway.validate-migration-naming=true
```

### 10.0.4 阶段三：创建角色并修改 User

新建 `model/UserRole.java`：

```java
package com.Shuan.spring_boot_study.model;

public enum UserRole {
    USER,
    ADMIN
}
```

在 `User` 中添加：

```java
@Column(name = "password_hash", nullable = false, length = 100)
private String passwordHash;

@Enumerated(EnumType.STRING)
@Column(nullable = false, length = 20)
private UserRole role = UserRole.USER;
```

将创建用户的构造方法改为：

```java
public User(String name, String email, String passwordHash) {
    this.name = name;
    this.email = email;
    this.passwordHash = passwordHash;
    this.role = UserRole.USER;
}
```

添加 getter，但绝不能把 `passwordHash` 放进 `UserResponse`：

```java
public String getPasswordHash() {
    return passwordHash;
}

public UserRole getRole() {
    return role;
}
```

这一步会使旧的 `new User(name, email)` 编译失败。根据编译提示修改调用处，测试代码可以使用一个合法 BCrypt 哈希或通过新的三参数构造器传入测试字符串。

#### 实际错误：找不到 `User(String, String, String)` 构造器

`AuthService` 已经调用：

```java
new User(name, email, passwordHash)
```

但当时 `User` 只有 `User(String, String)`，因此实际参数是 3 个而形参只有 2 个。修复不是随意删掉 `passwordHash` 参数，而是完成 V4 对应的实体映射。

同时发现一种错误设计：

```java
private PasswordEncoder passwordEncoder;
```

`PasswordEncoder` 不能作为 `User` 的 JPA 字段。它是 Service 使用的工具 Bean；`User` 真正保存的字段应为：

```java
private String passwordHash;
```

本次同步完成：

- `UserRole` 从空 class 改为包含 `USER`、`ADMIN` 的 enum；
- `User` 映射 `password_hash` 和 `role`；
- 添加三参数构造器；
- `AuthService.register()` 保存并返回用户；
- 旧的 `/api/users` 创建入口也接收密码并进行 BCrypt 编码；
- Repository 测试使用三参数构造器。

验证结果：

```text
Tests run: 5, Failures: 0, Errors: 0, Skipped: 0
BUILD SUCCESS
```

### 10.0.5 阶段四：创建注册请求 DTO

新建 `dto/RegisterRequest.java`：

```java
package com.Shuan.spring_boot_study.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterRequest(
        @NotBlank(message = "用户名不能为空")
        @Size(min = 2, max = 50, message = "用户名长度必须在2到50个字符之间")
        String name,

        @NotBlank(message = "邮箱不能为空")
        @Email(message = "邮箱格式不正确")
        String email,

        @NotBlank(message = "密码不能为空")
        @Size(min = 8, max = 72, message = "密码长度必须在8到72个字符之间")
        String password
) {
}
```

#### 实际错误：`<identifier> expected` 和 `illegal start of expression`

错误写法：

```java
public record RegisterRequest() {
    @NotBlank
    String name,
    // ...
}
```

`RegisterRequest()` 表示这个 record 没有任何组件。record 的 `name`、`email`、`password` 必须声明在圆括号内，不能像普通字段一样用逗号写在 `{}` 类体中。正确结构是：

```java
public record RegisterRequest(
        String name,
        String email,
        String password
) {
}
```

注解直接放在各组件前。邮箱格式应使用 `@Email`，`@Size` 用来检查字符串长度，不能检查邮箱格式。修正后运行 `./mvnw test -DskipTests`，结果为 `BUILD SUCCESS`。

BCrypt 只使用密码前 72 bytes。学习项目先限制为 72 个字符；真实项目还应明确字符与 UTF-8 bytes 的差异。

### 10.0.6 阶段五：Repository 支持按邮箱查询

在 `UserRepository` 添加：

```java
Optional<User> findByEmailIgnoreCase(String email);
```

已有的 `existsByEmailIgnoreCase` 用于注册前快速检查；数据库唯一约束仍然是并发情况下的最终防线。

### 10.0.7 阶段六：完成注册 Service

修正 `AuthService`：

```java
package com.Shuan.spring_boot_study.service;

import com.Shuan.spring_boot_study.dto.RegisterRequest;
import com.Shuan.spring_boot_study.dto.UserResponse;
import com.Shuan.spring_boot_study.exception.DuplicateEmailException;
import com.Shuan.spring_boot_study.model.User;
import com.Shuan.spring_boot_study.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public UserResponse register(RegisterRequest request) {
        String normalizedEmail = request.email().trim().toLowerCase();

        if (userRepository.existsByEmailIgnoreCase(normalizedEmail)) {
            throw new DuplicateEmailException(normalizedEmail);
        }

        String passwordHash = passwordEncoder.encode(request.password());
        User user = new User(
                request.name().trim(),
                normalizedEmail,
                passwordHash
        );

        return UserResponse.from(userRepository.save(user));
    }
}
```

完成这里后先执行：

```bash
./mvnw test -DskipTests
```

必须先恢复 `BUILD SUCCESS`，再继续 Controller。

### 10.0.8 阶段七：创建注册 Controller

新建 `controller/AuthController.java`：

```java
package com.Shuan.spring_boot_study.controller;

import com.Shuan.spring_boot_study.dto.RegisterRequest;
import com.Shuan.spring_boot_study.dto.UserResponse;
import com.Shuan.spring_boot_study.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse register(@Valid @RequestBody RegisterRequest request) {
        return authService.register(request);
    }
}
```

#### 实际错误：`cannot find symbol class Vaild`

错误写法：

```java
public UserResponse register(@Vaild @RequestBody RegisterRequest request)
```

正确写法：

```java
import jakarta.validation.Valid;

public UserResponse register(@Valid @RequestBody RegisterRequest request)
```

`Valid` 的字母顺序是 `Val-id`，不是 `Vaild`。Java 会把错误拼写当成一个不存在的新类型，因此报告 `cannot find symbol`。修正后 `./mvnw test -DskipTests` 编译成功。

### 10.0.9 阶段八：先配置最小 SecurityFilterChain

添加 Security 依赖后，Spring 默认会保护所有接口。为了先验证注册功能，在 `SecurityConfig` 添加：

```java
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Bean
public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    return http
            .csrf(csrf -> csrf.disable())
            .sessionManagement(session -> session
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> auth
                    .requestMatchers("/api/auth/register", "/api/auth/login").permitAll()
                    .anyRequest().authenticated())
            .httpBasic(Customizer.withDefaults())
            .build();
}
```

这里的 HTTP Basic 只是阶段性调试配置。JWT 完成后会移除它并启用 OAuth2 Resource Server。

启动并测试注册：

```bash
curl -i \
  -X POST \
  -H 'Content-Type: application/json' \
  -d '{"name":"Alice","email":"alice@example.com","password":"password123"}' \
  http://localhost:8080/api/auth/register
```

预期 `201`，响应中绝不能出现 `password` 或 `passwordHash`。

数据库验证：

```sql
SELECT id, name, email, password_hash, role
FROM app_users;
```

`password_hash` 应以 `$2` 开头，不应等于 `password123`。

### 10.0.10 阶段九：登录 DTO 与错误

新建 `LoginRequest.java`：

```java
public record LoginRequest(
        @NotBlank @Email String email,
        @NotBlank String password
) {
}
```

新建 `AuthResponse.java`：

```java
public record AuthResponse(
        String accessToken,
        String tokenType,
        long expiresIn
) {
}
```

新建 `InvalidCredentialsException`，统一返回“邮箱或密码错误”，不要分别透露邮箱是否存在，避免账号枚举。

登录的核心判断：

```java
User user = userRepository.findByEmailIgnoreCase(request.email().trim())
        .orElseThrow(InvalidCredentialsException::new);

if (!passwordEncoder.matches(request.password(), user.getPasswordHash())) {
    throw new InvalidCredentialsException();
}
```

到这里仅完成身份校验；下一阶段再签发 JWT。

### 10.0.11 阶段十：JWT 配置与签发

JWT 密钥必须来自环境变量：

```properties
security.jwt.secret=${JWT_SECRET}
security.jwt.expiration-seconds=3600
```

`JWT_SECRET` 至少使用 32 bytes 随机内容，不要提交到 Git。IDEA Run Configuration 需要同时配置 `DB_PASSWORD` 与 `JWT_SECRET`。

JWT 中至少放入：

```text
sub   用户 ID 或邮箱
role  USER / ADMIN
iat   签发时间
exp   过期时间
```

`JwtService` 负责创建 token；`AuthService.login()` 只负责查用户、校验密码并调用 `JwtService`。不要把 JWT 生成代码堆进 Controller。

完成 Encoder/Decoder 后，将阶段性的 HTTP Basic 替换为：

```java
.oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()))
```

并配置 JWT authority converter，把 `role=ADMIN` 转成 `ROLE_ADMIN`，否则 `hasRole("ADMIN")` 无法匹配。

### 10.0.12 阶段十一：最终权限规则

```java
.authorizeHttpRequests(auth -> auth
        .requestMatchers("/api/auth/register", "/api/auth/login").permitAll()
        .requestMatchers(HttpMethod.GET, "/api/users/**").hasAnyRole("USER", "ADMIN")
        .requestMatchers("/api/users/**").hasRole("ADMIN")
        .anyRequest().authenticated())
```

规则必须从具体到宽泛排列。若先写 `/api/users/**` 的宽泛规则，后面的 GET 专用规则可能不会按预期生效。

### 10.0.13 阶段十二：验证矩阵

| 场景 | 预期 |
|---|---:|
| 注册合法用户 | 201 |
| 重复邮箱注册 | 409 |
| 密码不足 8 位 | 400 |
| 正确邮箱密码登录 | 200 + token |
| 错误密码登录 | 401 |
| 无 token 查询用户 | 401 |
| USER token 查询用户 | 200 |
| USER token 删除用户 | 403 |
| ADMIN token 删除用户 | 204 |
| token 过期或签名错误 | 401 |

### 10.0.14 为什么本任务拆成这些阶段？

认证错误通常来自不同层：数据库列、Bean 注入、密码校验、JWT 签名、JWT 解析、角色映射或 URL 规则。如果一次写完再运行，错误会叠在一起。逐阶段验证能够明确每个问题属于哪一层。

### 10.1 实现顺序

Spring Security 内容较多，按以下顺序实现：

1. 添加 Spring Security，先理解默认保护。
2. 使用 `PasswordEncoder` 存储密码哈希。
3. 实现 `/api/auth/register` 和 `/api/auth/login`。
4. 签发并验证 JWT。
5. 添加 `USER` 和 `ADMIN` 角色。
6. 编写 401 和 403 测试。

### 10.2 添加依赖

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-security</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-oauth2-resource-server</artifactId>
</dependency>
```

### 10.3 密码存储

User 增加 `passwordHash`，不保存明文密码，也不在 `UserResponse` 中返回。

`PasswordEncoder` 不是写在 `User` 实体中的方法。新建：

```text
src/main/java/com/Shuan/spring_boot_study/config/SecurityConfig.java
```

完整注册代码：

```java
package com.Shuan.spring_boot_study.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
```

含义：

- `@Configuration` 告诉 Spring 这是配置类；
- `@Bean` 将方法返回的 `BCryptPasswordEncoder` 对象注册进 Spring 容器；
- Bean 的类型是 `PasswordEncoder`，默认名称是 `passwordEncoder`；
- Spring 启动时只创建并管理这个对象，Service 不需要自己 `new BCryptPasswordEncoder()`。

在负责注册用户的 Service 中通过构造器注入：

```java
import org.springframework.security.crypto.password.PasswordEncoder;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserResponse register(RegisterRequest request) {
        String passwordHash = passwordEncoder.encode(request.password());
        // 使用 name、email、passwordHash 创建并保存 User
        // 不要保存 request.password() 明文
        return null;
    }
}
```

下面这种写法位置错误，不要放在 `User` 中：

```java
@Entity
public class User {
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
```

`User` 是 JPA 实体，由 JPA 表示数据库中的一行数据；它不是负责装配应用依赖的 Spring 配置类。实体只应保存字段和领域行为。

注册时真正执行：

```java
String passwordHash = passwordEncoder.encode(request.password());
```

这里的 `passwordEncoder` 就是构造器参数中由 Spring 注入的 Bean。登录时使用同一个接口验证：

```java
boolean matched = passwordEncoder.matches(
        request.password(),
        user.getPasswordHash()
);
```

不能将用户本次输入再次 `encode()` 后与数据库字符串直接比较，因为 BCrypt 每次编码会使用新的随机盐，即使明文相同，生成的哈希通常也不同。

简化调用链：

```text
SecurityConfig 创建 PasswordEncoder Bean
                ↓
Spring 容器保存 Bean
                ↓
Spring 创建 AuthService 时通过构造器注入
                ↓
register() 调用 passwordEncoder.encode(...)
                ↓
数据库只保存 passwordHash
```

最小注册方法仍然是：

```java
@Bean
PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
}
```

注册时：

```java
String passwordHash = passwordEncoder.encode(request.password());
```

登录时使用 `passwordEncoder.matches()` 验证，不要自己比较密码字符串。

### 10.4 安全规则

SecurityFilterChain 的目标规则：

```text
POST /api/auth/register       允许匿名
POST /api/auth/login          允许匿名
GET  /api/users/**            USER 或 ADMIN
POST/PUT/PATCH/DELETE users   ADMIN
```

REST API 使用无状态会话，JWT 通过请求头传递：

```http
Authorization: Bearer <token>
```

JWT 签名密钥必须使用环境变量或密钥管理服务，不提交到 Git。

### 10.5 验证

```bash
# 不携带 token，预期 401
curl -i http://localhost:8080/api/users

# 携带有效 token
curl -i \
  -H "Authorization: Bearer $ACCESS_TOKEN" \
  http://localhost:8080/api/users
```

401 表示未认证或 token 无效；403 表示已认证，但权限不足。

### 10.6 完成检查

- [ ] 数据库中不存储明文密码。
- [ ] 注册和登录允许匿名访问。
- [ ] 受保护接口没有 token 时返回 401。
- [ ] USER 访问 ADMIN 功能时返回 403。
- [ ] JWT 过期和签名错误都有测试。

## 11. Task 19：文档、监控、Docker 与 CI

### 11.1 OpenAPI / Swagger

添加与当前 Spring Boot 主版本兼容的 springdoc 依赖，启动后检查：

```text
/v3/api-docs
/swagger-ui/index.html
```

为请求 DTO 和主要 Controller 添加说明，确保 400、401、403、404 和 409 也出现在 API 文档中。

### 11.2 Actuator

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-actuator</artifactId>
</dependency>
```

只暴露必要端点：

```properties
management.endpoints.web.exposure.include=health,info
management.endpoint.health.show-details=never
```

验证：

```bash
curl -i http://localhost:8080/actuator/health
```

### 11.3 Dockerfile

```dockerfile
FROM eclipse-temurin:25-jre
WORKDIR /app
COPY target/spring-boot-study-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
```

先构建 Jar，再构建镜像：

```bash
./mvnw clean package
docker build -t spring-boot-study .
```

### 11.4 Docker Compose

Compose 至少包含 `app` 和 `mysql` 两个服务。应用内的数据库地址使用服务名：

```text
jdbc:mysql://mysql:3306/spring_boot_study
```

容器内的 `localhost` 指向当前容器，不是 MySQL 容器。密码通过环境变量提供。

```bash
docker compose up --build
docker compose ps
curl -i http://localhost:8080/actuator/health
```

### 11.5 CI

GitHub Actions 的基本步骤：

```yaml
- uses: actions/checkout@v4
- uses: actions/setup-java@v4
  with:
    distribution: temurin
    java-version: '25'
    cache: maven
- run: ./mvnw verify
  working-directory: spring-boot-study
```

CI 使用 H2 或临时 MySQL service container，不能依赖开发者本机数据库。

### 11.6 完成检查

- [ ] Swagger UI 可访问，且接口信息正确。
- [ ] `/actuator/health` 返回 UP。
- [ ] Docker 镜像能独立启动。
- [ ] Compose 能同时启动应用和 MySQL。
- [ ] GitHub Actions 能自动执行 `./mvnw verify`。
- [ ] 日志和仓库中没有敏感信息。

## 12. 每一课的执行方式

从 Task 10 开始，每一课按以下流程进行：

1. 说明学习目标和知识点。
2. 由学习者编写或补全实际代码。
3. 将完整操作、原理和问题记录到本文档。
4. 运行 Maven 测试或使用 `curl` 验证接口。
5. 查看 `git diff` 和 `git status`。
6. 确认无误后再由学习者提交并推送 Git。

---

# 官方资料

- [Spring Boot](https://spring.io/projects/spring-boot)
- [Spring Boot Reference](https://docs.spring.io/spring-boot/)
- [Spring Initializr](https://start.spring.io/)
- [Spring Web MVC](https://docs.spring.io/spring-framework/reference/web/webmvc.html)
