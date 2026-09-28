# 03 解决首页 Error Page 并创建第一个接口

> 检查日期：2026-09-28  
> 本文根据当前项目的实际代码生成。代码修改和运行操作由学习者本人完成。

## 一、问题现象

Spring Boot 应用可以运行，但浏览器访问：

```text
http://localhost:8080/
```

显示 Error Page，页面内容可能类似：

```text
Whitelabel Error Page
This application has no explicit mapping for /error
```

或者显示：

```text
status=404
```

## 二、当前项目检查结果

当前 Java 源码中只有启动类：

```text
src/main/java/com/Shuan/spring_boot_study/
└── SpringBootStudyApplication.java
```

启动类内容正常：

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

配置文件中目前只有应用名称：

```properties
spring.application.name=spring-boot-study
```

项目中没有发现：

- `@RestController`
- `@Controller`
- `@GetMapping`
- `@RequestMapping`

因此，没有任何代码负责处理浏览器发来的 `GET /` 请求。

## 三、这是否代表启动失败

如果浏览器能够显示 Spring Boot 的 Error Page，通常说明：

1. 浏览器已经成功连接到 8080 端口。
2. Spring Boot 内嵌服务器已经启动。
3. Spring MVC 收到了请求。
4. 但是没有找到与 `/` 对应的处理方法。

因此，这通常是“路由不存在”，而不是“服务器没有启动”。

常见 HTTP 状态码含义：

| 状态码 | 含义 |
|---|---|
| 200 | 请求处理成功 |
| 404 | 服务器正常，但找不到请求的资源或路由 |
| 500 | 服务器处理请求时发生内部异常 |

当前项目结构对应的情况应当是 404。

## 四、任务：创建首页接口

### 步骤 1：找到启动类所在的包

当前启动类位于：

```text
src/main/java/com/Shuan/spring_boot_study
```

对应包名：

```java
com.Shuan.spring_boot_study
```

### 步骤 2：创建 controller 子包

在 IntelliJ IDEA 左侧项目窗口中找到：

```text
src/main/java/com/Shuan/spring_boot_study
```

右键单击 `spring_boot_study` 包，选择：

```text
New → Package
```

输入：

```text
controller
```

创建后的完整包名应当是：

```text
com.Shuan.spring_boot_study.controller
```

### 步骤 3：创建 HelloController

右键单击刚创建的 `controller` 包，选择：

```text
New → Java Class
```

类名输入：

```text
HelloController
```

最终文件位置应当是：

```text
src/main/java/com/Shuan/spring_boot_study/controller/HelloController.java
```

### 步骤 4：填写代码

手动输入下面的代码：

```java
package com.Shuan.spring_boot_study.controller;

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

注意检查：

- 包名必须与真实目录一致。
- `RestController` 和 `GetMapping` 应从 `org.springframework.web.bind.annotation` 导入。
- Java 字符串使用英文双引号。
- 每条 Java 语句末尾需要分号。

## 五、逐行理解代码

### 5.1 package 声明

```java
package com.Shuan.spring_boot_study.controller;
```

表示该类属于 `controller` 包。

这个包是启动类所在包的子包，因此默认组件扫描能够发现它。

### 5.2 导入注解

```java
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
```

导入后，可以在代码中使用 `@GetMapping` 和 `@RestController`。

### 5.3 @RestController

```java
@RestController
```

它告诉 Spring：

- 这是一个负责处理 HTTP 请求的类。
- 方法的返回值应直接写入 HTTP 响应体。

如果方法返回字符串 `Hello, Spring Boot!`，浏览器会直接看到这个字符串。

### 5.4 @GetMapping

```java
@GetMapping("/")
```

它表示：

- 请求方法：GET
- 请求路径：`/`
- 匹配后调用下面的 `hello()` 方法

浏览器地址栏访问网页时，通常发送的就是 GET 请求。

### 5.5 hello 方法

```java
public String hello() {
    return "Hello, Spring Boot!";
}
```

该方法返回的字符串会成为 HTTP 响应内容。

## 六、为什么 Controller 应放在这个位置

启动类使用：

```java
@SpringBootApplication
```

默认情况下，Spring 会从启动类所在包开始，扫描它以及它的所有子包。

当前扫描范围可以理解为：

```text
com.Shuan.spring_boot_study
├── SpringBootStudyApplication
├── controller
├── service
└── repository
```

下面的位置在默认情况下可能不会被扫描：

```text
com.Shuan.other
```

因此，初学阶段应将 Controller 放在启动类包的子包中。

## 七、重新启动应用

### 步骤 1：停止旧进程

在 IntelliJ IDEA 底部的 Run 窗口中，单击红色停止按钮。

不要让旧应用继续占用 8080 端口。

### 步骤 2：重新运行启动类

打开：

```text
SpringBootStudyApplication.java
```

单击 `main` 方法左侧绿色按钮，选择运行。

### 步骤 3：观察日志

确认日志中包含类似内容：

```text
Started SpringBootStudyApplication
```

如果日志中出现红色异常，应先查看异常，而不是立即访问浏览器。

## 八、验证接口

### 方法 1：浏览器验证

访问：

```text
http://localhost:8080/
```

预期结果：

```text
Hello, Spring Boot!
```

### 方法 2：终端验证

保持应用运行，另开一个终端窗口：

```bash
curl http://localhost:8080/
```

预期输出：

```text
Hello, Spring Boot!
```

查看 HTTP 状态码：

```bash
curl -i http://localhost:8080/
```

预期响应中包含：

```text
HTTP/1.1 200
```

## 九、如果仍然显示 Error Page

### 检查 1：确认状态码

使用：

```bash
curl -i http://localhost:8080/
```

记录第一行状态码。

### 检查 2：确认文件位置

文件应该位于：

```text
src/main/java/com/Shuan/spring_boot_study/controller/HelloController.java
```

而不是：

```text
src/test/java
```

### 检查 3：确认包名

文件第一行应该是：

```java
package com.Shuan.spring_boot_study.controller;
```

### 检查 4：确认注解

类上必须存在：

```java
@RestController
```

方法上必须存在：

```java
@GetMapping("/")
```

### 检查 5：确认重新启动

如果只是新增了文件，但运行中的程序没有重新加载代码，应停止后再次启动。

### 检查 6：确认访问端口

当前 `application.properties` 没有修改端口，所以默认端口是：

```text
8080
```

### 检查 7：查看启动日志中的映射

如果出现编译错误，Controller 不会被成功编译。应先修复 IDEA 标出的红色错误。

## 十、进一步练习

在同一个 `HelloController` 中增加：

```java
@GetMapping("/study")
public String study() {
    return "I am learning Spring Boot.";
}
```

重新启动后访问：

```text
http://localhost:8080/study
```

预期结果：

```text
I am learning Spring Boot.
```

## 十一、请求处理过程

```text
浏览器访问 http://localhost:8080/
                ↓
发送 GET / 请求
                ↓
内嵌 Web 服务器接收请求
                ↓
Spring MVC 查找匹配的映射
                ↓
找到 @GetMapping("/")
                ↓
调用 HelloController.hello()
                ↓
返回 Hello, Spring Boot!
                ↓
浏览器显示响应内容
```

没有创建 Controller 时，流程会在“查找匹配的映射”处失败，最终返回 404 Error Page。

## 十二、完成检查清单

- [ ] 创建了 `controller` 包。
- [ ] 创建了 `HelloController.java`。
- [ ] 文件位于启动类包的子包中。
- [ ] 类上添加了 `@RestController`。
- [ ] `hello()` 上添加了 `@GetMapping("/")`。
- [ ] 代码没有红色编译错误。
- [ ] 停止并重新启动了应用。
- [ ] 启动日志中出现 `Started`。
- [ ] 浏览器访问 `/` 显示正确字符串。
- [ ] `curl -i` 返回 HTTP 200。

## 十三、操作结果记录

```text
浏览器原来的 Error Page 状态码：

HelloController 文件位置：

重新启动是否成功：

GET / 返回内容：

curl -i 第一行：

是否仍有错误：

完整错误信息：
```

## 十四、实际问题记录：cannot find symbol RestContoller

### 错误信息

```text
java: cannot find symbol
  symbol: class RestContoller
```

### 原因

`HelloController.java` 中导入的类名是正确的：

```java
import org.springframework.web.bind.annotation.RestController;
```

但是实际使用注解时写成了：

```java
@RestContoller
```

这里的 `Controller` 少写了一个字母 `r`。

Java 对标识符的拼写和大小写有严格要求。导入的类叫 `RestController`，代码中也必须使用完全相同的名字。

### 手动修改

将：

```java
@RestContoller
```

改为：

```java
@RestController
```

修改后的完整代码应该是：

```java
package com.Shuan.spring_boot_study.controller;

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

### 修改后验证

1. 保存 `HelloController.java`。
2. 确认 `@RestController` 下方没有红色波浪线。
3. 停止旧的运行进程。
4. 重新运行 `SpringBootStudyApplication`。
5. 浏览器访问 `http://localhost:8080/`。

预期结果：

```text
Hello, Spring Boot!
```

### 如何理解 cannot find symbol

`cannot find symbol` 表示编译器看到一个名字，却找不到与它对应的类、方法或变量。常见原因包括：

- 单词拼写错误。
- 大小写错误。
- 缺少 `import`。
- 依赖尚未导入。
- 调用了不存在的方法或变量。

遇到这种错误时，应先对照错误中的 `symbol`，检查代码里的名称是否与声明或导入的名称完全一致。
