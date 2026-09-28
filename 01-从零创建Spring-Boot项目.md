# 01 从零创建 Spring Boot 项目

> 学习日期：2026-09-28  
> 说明：本文只提供学习和操作步骤，所有命令及项目操作均由学习者本人完成。

## 一、学习目标

完成本任务后，你应该能够：

1. 了解 JDK、Maven、Spring Boot 和 Spring Initializr 的作用。
2. 自己检查 Java 开发环境。
3. 使用 Spring Initializr 生成 Maven 项目。
4. 使用 IntelliJ IDEA 导入并启动项目。
5. 编写第一个 HTTP 接口。
6. 使用浏览器和 `curl` 验证接口。
7. 使用 Maven Wrapper 测试和打包项目。

## 二、技术选择

本次学习采用：

| 项目 | 选择 | 说明 |
|---|---|---|
| Java | JDK 21 | 长期支持版本，适合学习和新项目 |
| Spring Boot | 4.1.1 | 截至笔记编写日期的稳定版本 |
| 构建工具 | Maven | Java 项目常用的依赖和构建工具 |
| 打包方式 | Jar | 可以通过 `java -jar` 直接运行 |
| Web 技术 | Spring MVC | 由 Spring Web 依赖提供 |
| 开发工具 | IntelliJ IDEA | 常用 Java IDE |

Spring Boot 4.1.1 至少需要 Java 17，并支持 Java 17～26。学习时选择 Java 21。

官方资料：

- [Spring Boot 项目主页](https://spring.io/projects/spring-boot)
- [Spring Boot 系统要求](https://docs.spring.io/spring-boot/system-requirements.html)
- [Spring Initializr](https://start.spring.io/)

> 如果 Spring Initializr 已经不再提供 4.1.1，请选择页面提供的最新稳定版本。不要选择名称中带有 `SNAPSHOT`、`M1`、`M2` 或 `RC` 的预览版本。

## 三、理解基础工具

### 3.1 JDK

JDK 是 Java Development Kit，即 Java 开发工具包。

它包含：

- Java 运行命令 `java`
- Java 编译器 `javac`
- Java 标准库
- 调试和打包工具

没有 JDK，就不能编译和运行 Spring Boot 项目。

### 3.2 Maven

Maven 负责：

- 下载项目依赖
- 编译 Java 代码
- 执行自动化测试
- 打包 Jar 文件
- 运行 Spring Boot 应用

Maven 项目的核心配置文件是 `pom.xml`。

### 3.3 Spring Initializr

Spring Initializr 是 Spring 官方提供的项目生成器。它会生成规范的项目目录、启动类、测试类、Maven 配置和 Maven Wrapper。

### 3.4 Maven Wrapper

项目中的 `mvnw` 和 `mvnw.cmd` 是 Maven Wrapper：

- macOS/Linux 使用 `./mvnw`
- Windows 使用 `mvnw.cmd`

有了 Maven Wrapper，通常不必先在电脑上单独安装 Maven。

---

## 四、任务 1：检查 Java 环境

### 步骤 1：检查 Java 运行环境

打开 macOS 终端，执行：

```bash
java -version
```

预期看到类似内容：

```text
openjdk version "21.0.x"
```

### 步骤 2：检查 Java 编译器

执行：

```bash
javac -version
```

预期看到：

```text
javac 21.0.x
```

### 步骤 3：判断检查结果

`java` 和 `javac` 的主版本都应该是 21。

如果出现 `command not found`，说明 JDK 尚未安装或环境变量没有正确配置。可以从 [Eclipse Temurin](https://adoptium.net/temurin/releases/) 下载 JDK 21。

检查 Mac 芯片类型：

```bash
uname -m
```

- `arm64`：Apple 芯片，下载 `aarch64` 版本。
- `x86_64`：Intel 芯片，下载 `x64` 版本。

### 本任务验证清单

- [ ] `java -version` 能正常执行。
- [ ] `javac -version` 能正常执行。
- [ ] 两个命令显示的主版本都是 21。

---

## 五、任务 2：使用 Spring Initializr 生成项目

### 步骤 1：打开生成器

浏览器访问：

```text
https://start.spring.io/
```

### 步骤 2：填写项目参数

| 配置项 | 填写内容 |
|---|---|
| Project | Maven |
| Language | Java |
| Spring Boot | 4.1.1 或页面最新稳定版 |
| Group | `com.example` |
| Artifact | `spring-boot-hello` |
| Name | `spring-boot-hello` |
| Description | `My first Spring Boot project` |
| Package name | `com.example.hello` |
| Packaging | Jar |
| Java | 21 |

### 步骤 3：添加依赖

单击 `ADD DEPENDENCIES`，搜索并添加：

```text
Spring Web
```

Spring Web 提供 Spring MVC、HTTP 请求处理、JSON 转换以及内嵌 Web 服务器。

### 步骤 4：下载项目

单击 `GENERATE`，浏览器会下载：

```text
spring-boot-hello.zip
```

将 ZIP 解压到一个独立目录。不要覆盖已有代码或已有项目。

### 步骤 5：检查目录

解压后的目录应该类似：

```text
spring-boot-hello/
├── .mvn/
├── src/
├── .gitattributes
├── .gitignore
├── HELP.md
├── mvnw
├── mvnw.cmd
└── pom.xml
```

### 本任务验证清单

- [ ] 项目使用 Maven。
- [ ] Java 版本选择 21。
- [ ] Packaging 选择 Jar。
- [ ] 已添加 Spring Web。
- [ ] 解压目录中存在 `pom.xml`、`mvnw` 和 `src`。

---

## 六、任务 3：用 IntelliJ IDEA 导入项目

### 步骤 1：打开项目

启动 IntelliJ IDEA，选择 `Open`，然后选择整个 `spring-boot-hello` 目录。

不要只选择其中的某个 Java 文件。

### 步骤 2：信任项目

如果 IDEA 显示安全提示，确认目录是从 Spring 官方生成并由你解压的项目，然后选择 `Trust Project`。

### 步骤 3：等待 Maven 导入

IDEA 会读取 `pom.xml` 并下载依赖。首次下载可能需要几分钟。

导入完成的判断依据：

- `SpringApplication` 没有红色错误。
- `pom.xml` 中的依赖没有红色错误。
- Maven 工具窗口能看到项目生命周期。
- IDEA 底部不再持续显示索引或下载任务。

### 步骤 4：确认 Project SDK

打开：

```text
File → Project Structure → Project
```

确认：

```text
Project SDK = 21
```

---

## 七、任务 4：认识项目结构

主要目录应当类似：

```text
spring-boot-hello/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/example/hello/
│   │   │       └── SpringBootHelloApplication.java
│   │   └── resources/
│   │       └── application.properties
│   └── test/
│       └── java/
├── mvnw
├── mvnw.cmd
└── pom.xml
```

### 文件作用

| 文件或目录 | 作用 |
|---|---|
| `pom.xml` | 管理项目版本、依赖和构建插件 |
| `src/main/java` | 存放正式 Java 代码 |
| `src/main/resources` | 存放配置文件和静态资源 |
| `src/test/java` | 存放测试代码 |
| `mvnw` | macOS/Linux 使用的 Maven Wrapper |
| `target` | 构建后生成的文件目录 |

`pom.xml` 中应包含 Spring Web 依赖：

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>
```

`starter` 可以理解为一组用途相关的依赖集合。`spring-boot-starter-web` 会引入开发 Web 应用所需的主要组件。

---

## 八、任务 5：理解并启动应用

启动类内容通常类似：

```java
package com.example.hello;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringBootHelloApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootHelloApplication.class, args);
    }
}
```

### 关键代码解释

`@SpringBootApplication` 主要负责：

1. 标记 Spring Boot 配置类。
2. 启用自动配置。
3. 扫描当前包及其子包中的 Spring 组件。

`SpringApplication.run(...)` 会创建 Spring 容器、执行自动配置并启动内嵌服务器。

### 使用 IDEA 启动

1. 打开 `SpringBootHelloApplication.java`。
2. 找到 `main` 方法左侧的绿色运行按钮。
3. 选择 `Run 'SpringBootHelloApplication'`。

启动成功时，日志中应包含类似内容：

```text
Started SpringBootHelloApplication
```

默认访问地址：

```text
http://localhost:8080
```

此时访问首页可能得到 404。这并不一定表示启动失败，只表示还没有代码负责处理 `/` 请求。

---

## 九、任务 6：创建第一个 Controller

### 步骤 1：创建包

在 `com.example.hello` 下创建：

```text
controller
```

完整包名：

```text
com.example.hello.controller
```

### 步骤 2：创建类

在 `controller` 包中创建：

```text
HelloController.java
```

填写：

```java
package com.example.hello.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/")
    public String hello() {
        return "Hello, Spring Boot!";
    }
}
```

### 代码解释

- `@RestController`：声明该类负责接收 HTTP 请求，方法返回值会写入 HTTP 响应。
- `@GetMapping("/")`：将 HTTP GET `/` 请求映射到 `hello()` 方法。
- `return` 的字符串：浏览器最终收到的响应内容。

Controller 要放在启动类所在包或其子包中，这样默认的组件扫描才能发现它。

正确结构：

```text
com.example.hello
├── SpringBootHelloApplication.java
└── controller
    └── HelloController.java
```

---

## 十、任务 7：验证接口

### 步骤 1：重新启动应用

在 IDEA 中停止原来的进程，然后重新运行启动类。

### 步骤 2：浏览器验证

访问：

```text
http://localhost:8080/
```

预期显示：

```text
Hello, Spring Boot!
```

### 步骤 3：使用 curl 验证

打开另一个终端，执行：

```bash
curl http://localhost:8080/
```

查看 HTTP 状态码和响应头：

```bash
curl -i http://localhost:8080/
```

预期响应包含：

```text
HTTP/1.1 200
```

`200` 表示请求处理成功。

---

## 十一、任务 8：使用 Maven Wrapper

### 步骤 1：进入项目根目录

```bash
cd 你的项目实际路径/spring-boot-hello
```

不要直接照抄示例路径，要替换成真实路径。

确认当前位置：

```bash
pwd
ls
```

应当能看到 `pom.xml`、`mvnw` 和 `src`。

### 步骤 2：查看 Maven 和 Java 版本

```bash
./mvnw -version
```

重点确认命令输出中的 Java 版本是 21。

如果出现 `Permission denied`，执行：

```bash
chmod +x mvnw
```

然后重新执行版本检查。

### 步骤 3：运行测试

```bash
./mvnw test
```

预期结果：

```text
BUILD SUCCESS
```

### 步骤 4：打包项目

```bash
./mvnw clean package
```

- `clean`：清理上一次构建的 `target`。
- `package`：编译、测试并生成 Jar。

成功后查看：

```bash
ls target
```

里面应有类似文件：

```text
spring-boot-hello-0.0.1-SNAPSHOT.jar
```

### 步骤 5：运行 Jar

按照实际文件名执行：

```bash
java -jar target/spring-boot-hello-0.0.1-SNAPSHOT.jar
```

在另一个终端验证：

```bash
curl http://localhost:8080/
```

停止应用时，在运行应用的终端按 `Control + C`。

---

## 十二、任务 9：修改应用端口

配置文件位置：

```text
src/main/resources/application.properties
```

加入：

```properties
server.port=8081
```

重新启动后访问：

```text
http://localhost:8081/
```

验证：

```bash
curl http://localhost:8081/
```

实验完成后可以删除该配置，恢复默认 8080 端口。

---

## 十三、常见问题

### 13.1 端口被占用

如果日志出现：

```text
Port 8080 was already in use
```

检查监听 8080 端口的程序：

```bash
lsof -nP -iTCP:8080 -sTCP:LISTEN
```

如果是自己此前启动的应用，应回到对应终端或 IDEA 正常停止它。也可以临时将应用端口修改为 8081。

### 13.2 Java 版本不一致

分别检查：

```bash
java -version
./mvnw -version
```

再检查 IDEA 中的：

```text
File → Project Structure → Project → Project SDK
```

这几处都应使用 Java 21。

### 13.3 页面显示 404

依次检查：

1. 日志中是否出现 `Started`。
2. URL 和端口是否正确。
3. 类上是否有 `@RestController`。
4. 方法上是否有 `@GetMapping("/")`。
5. Controller 是否位于启动类包的子包中。
6. 修改代码后是否重新启动应用。

### 13.4 Connection refused

一般表示目标端口没有应用监听。检查应用是否仍在运行，以及访问的端口是否与配置一致。

### 13.5 Maven 依赖下载失败

先检查网络能否访问：

```text
https://repo.maven.apache.org/maven2/
```

然后重新执行：

```bash
./mvnw test
```

在不清楚影响时，不要随意删除整个 Maven 本地仓库。

---

## 十四、完整运行流程

```text
main 方法
    ↓
SpringApplication.run()
    ↓
创建 Spring 容器
    ↓
执行自动配置
    ↓
扫描 HelloController
    ↓
启动内嵌 Web 服务器
    ↓
等待 HTTP 请求
```

请求处理流程：

```text
浏览器或 curl
      │
      │ GET /
      ▼
内嵌 Web 服务器
      │
      ▼
Spring MVC
      │
      ▼
HelloController.hello()
      │
      ▼
Hello, Spring Boot!
```

---

## 十五、独立练习

### 练习 1：增加接口

增加：

```text
GET /study
```

返回：

```text
I am learning Spring Boot.
```

提示：

```java
@GetMapping("/study")
public String study() {
    // 自己补充返回值
}
```

### 练习 2：修改欢迎语

将 `/` 接口修改为返回：

```text
Welcome to my first Spring Boot project!
```

### 练习 3：分别验证两个接口

```text
http://localhost:8080/
http://localhost:8080/study
```

### 练习 4：用自己的话回答

1. JDK 有什么作用？
2. Maven 有什么作用？
3. `pom.xml` 有什么作用？
4. `@SpringBootApplication` 有什么作用？
5. `@RestController` 有什么作用？
6. `@GetMapping` 有什么作用？
7. 为什么 Spring Boot Web 项目不需要另外部署到 Tomcat？
8. HTTP 200 和 HTTP 404 分别代表什么？

---

## 十六、完成检查清单

- [ ] `java -version` 显示 Java 21。
- [ ] `javac -version` 显示 Java 21。
- [ ] 项目由 Spring Initializr 生成。
- [ ] 项目使用 Maven 和 Jar。
- [ ] 项目包含 Spring Web 依赖。
- [ ] IDEA 能正常识别并导入项目。
- [ ] 启动日志中出现 `Started`。
- [ ] 浏览器可以访问 `/`。
- [ ] `curl -i` 返回 HTTP 200。
- [ ] `./mvnw test` 执行成功。
- [ ] `./mvnw clean package` 执行成功。
- [ ] 打包后的 Jar 可以启动。
- [ ] 能使用 `Control + C` 停止应用。

---

## 十七、个人操作记录

### 环境版本

```text
java -version：

javac -version：

./mvnw -version：

Spring Boot 版本：

IntelliJ IDEA 版本：
```

### 验证结果

```text
应用是否启动成功：

GET / 返回结果：

GET /study 返回结果：

./mvnw test 是否成功：

./mvnw clean package 是否成功：

Jar 是否启动成功：
```

### 问题记录

```text
问题现象：

完整错误信息：

问题原因：

解决方法：
```

### 学习总结

```text
我学到的内容：

仍然不理解的内容：

下一步准备学习的内容：
```
