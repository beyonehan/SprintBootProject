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

# 官方资料

- [Spring Boot](https://spring.io/projects/spring-boot)
- [Spring Boot Reference](https://docs.spring.io/spring-boot/)
- [Spring Initializr](https://start.spring.io/)
- [Spring Web MVC](https://docs.spring.io/spring-framework/reference/web/webmvc.html)
