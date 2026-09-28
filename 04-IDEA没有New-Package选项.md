# 04 IDEA 右键没有 New Package 选项

> 问题：右键菜单中只有 Module，没有 Package。  
> 原则：本文只记录操作步骤，实际 IDEA 操作由学习者本人完成。

## 一、原因

`New → Package` 只会在 Java 源码目录或已有 Java 包上出现。

如果右键的是以下位置，IDEA 可能只显示 `Module`、`Directory` 等选项：

```text
spring-boot-study
src
src/main
```

正确的右键位置应该是：

```text
spring-boot-study
└── src
    └── main
        └── java
            └── com
                └── Shuan
                    └── spring_boot_study   ← 右键这里
```

## 二、方法一：在正确的包上右键

### 步骤 1

在 IDEA 左侧展开：

```text
spring-boot-study
→ src
→ main
→ java
→ com
→ Shuan
→ spring_boot_study
```

### 步骤 2

右键单击：

```text
spring_boot_study
```

注意：不要右键 `spring-boot-study` 项目根目录，也不要右键外层的 `SprintBootProject`。

### 步骤 3

此时选择：

```text
New → Package
```

输入：

```text
controller
```

最终应形成：

```text
com.Shuan.spring_boot_study.controller
```

## 三、方法二：确认 java 是 Sources Root

如果右键 `spring_boot_study` 仍然没有 `Package`，检查 `src/main/java` 是否被标记为 Java 源代码目录。

### 步骤 1

右键单击：

```text
src/main/java
```

### 步骤 2

选择：

```text
Mark Directory as → Sources Root
```

设置成功后，IDEA 中的 `java` 文件夹通常会显示为蓝色。

### 步骤 3

再次右键：

```text
com/Shuan/spring_boot_study
```

检查是否出现：

```text
New → Package
```

## 四、方法三：直接创建完整类名

如果 `New → Package` 仍然没有出现，可以让 IDEA 在创建类时同时创建包。

### 步骤 1

右键：

```text
src/main/java
```

### 步骤 2

选择：

```text
New → Java Class
```

### 步骤 3

在类名中输入完整名称：

```text
com.Shuan.spring_boot_study.controller.HelloController
```

IDEA 通常会自动创建缺少的 `controller` 包和 `HelloController.java`。

然后填写：

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

## 五、如果连 Java Class 也没有

如果右键 `src/main/java` 后连 `Java Class` 都没有，说明 IDEA 还没有正确识别 Java/Maven 模块。

依次检查：

1. IDEA 当前打开的是 `spring-boot-study`，而不是只打开外层目录。
2. `pom.xml` 已经执行 `Add as Maven Project`。
3. Maven 同步已经完成。
4. `File → Project Structure → Project` 中已经配置 Project SDK。
5. `src/main/java` 已标记为 Sources Root。

可以打开 Maven 工具窗口：

```text
View → Tool Windows → Maven
```

然后点击：

```text
Reload All Maven Projects
```

## 六、如何确认创建正确

创建后目录应该是：

```text
src/main/java
└── com
    └── Shuan
        └── spring_boot_study
            ├── SpringBootStudyApplication.java
            └── controller
                └── HelloController.java
```

`HelloController.java` 第一行应该是：

```java
package com.Shuan.spring_boot_study.controller;
```

如果目录和 `package` 声明不一致，IDEA 通常会显示错误提示。

## 七、完成后的验证

1. 停止当前运行中的 Spring Boot 应用。
2. 重新运行 `SpringBootStudyApplication`。
3. 确认控制台出现 `Started SpringBootStudyApplication`。
4. 浏览器访问 `http://localhost:8080/`。
5. 预期显示 `Hello, Spring Boot!`。

## 八、检查清单

- [ ] 右键的是 `spring_boot_study` 包，不是项目根目录。
- [ ] `src/main/java` 是 Sources Root。
- [ ] IDEA 已正确导入 Maven 项目。
- [ ] 能看到 `New → Java Class`。
- [ ] 已创建 `controller/HelloController.java`。
- [ ] Controller 的包名正确。
- [ ] 重新启动后 `/` 返回正常内容。
