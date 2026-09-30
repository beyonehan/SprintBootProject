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

# 官方资料

- [Spring Boot](https://spring.io/projects/spring-boot)
- [Spring Boot Reference](https://docs.spring.io/spring-boot/)
- [Spring Initializr](https://start.spring.io/)
- [Spring Web MVC](https://docs.spring.io/spring-framework/reference/web/webmvc.html)
