# 02 导入 Maven 项目与下载依赖

> 检查日期：2026-09-28  
> 本文记录当前实际目录的诊断结果，以及需要在 IntelliJ IDEA 中手动完成的操作。

## 一、当前检查结果

当前目录结构是：

```text
SprintBootProject/
├── .idea/                         ← IDEA 当前打开的是外层目录
├── README.md
├── 01-从零创建Spring-Boot项目.md
└── spring-boot-study/             ← 真正的 Spring Boot Maven 项目
    ├── .mvn/
    ├── mvnw
    ├── mvnw.cmd
    ├── pom.xml                    ← Maven 配置在这里
    └── src/
```

检查到的具体情况：

1. `spring-boot-study/pom.xml` 存在，文件结构完整。
2. Maven Wrapper 文件存在，并且 `mvnw` 有执行权限。
3. Java 主代码、配置文件和测试代码都存在。
4. IDEA 当前打开的是外层 `SprintBootProject`。
5. 外层 `.idea` 中没有发现 Maven 模块配置。
6. 因此 IDEA 目前只是把内层目录当作普通文件夹，没有把它作为 Maven 项目导入。

这就是没有根据 `pom.xml` 自动下载依赖的主要原因。

## 二、版本检查结果

当前终端使用：

```text
java 25.0.1
javac 25.0.1
```

`pom.xml` 中配置的也是：

```xml
<java.version>25</java.version>
```

因此当前项目的 Java 配置是相互一致的，并不是依赖没有下载的直接原因。

第一份笔记原本推荐 Java 21，是因为它是长期支持版本，更适合作为学习环境。你现在生成的是 Java 25 项目，也在 Spring Boot 4.1.1 支持范围内，可以继续使用。

如果你想严格按照第一份笔记使用 Java 21，需要重新生成项目，或者以后自行修改项目 Java 版本和 IDEA SDK。初学阶段不建议在依赖尚未导入时同时改版本，先完成 Maven 导入。

## 三、推荐方案：直接打开内层项目

这是最简单、最适合初学者的方式。

### 步骤 1：关闭当前 IDEA 项目

在 IntelliJ IDEA 菜单中选择：

```text
File → Close Project
```

这只是关闭 IDEA 窗口中的项目，不会删除任何文件。

### 步骤 2：打开真正的 Maven 项目

在 IDEA 欢迎页选择：

```text
Open
```

选择下面这个文件夹：

```text
/Users/hanli/Desktop/Study/backend/SprintBootProject/spring-boot-study
```

注意：选择整个 `spring-boot-study` 文件夹，不是外层的 `SprintBootProject`，也不用只选择某个 Java 文件。

### 步骤 3：信任项目

如果 IDEA 弹出安全提示，确认目录正确后选择：

```text
Trust Project
```

### 步骤 4：等待 Maven 同步

打开后，IDEA 应识别目录中的 `pom.xml`，并开始下载依赖。

IDEA 右下角或底部可能显示：

```text
Importing Maven projects
Downloading...
Indexing...
```

第一次下载需要一定时间。在同步完成前，不要反复关闭项目。

### 步骤 5：检查 Maven 工具窗口

打开：

```text
View → Tool Windows → Maven
```

正常情况下，应当看到类似结构：

```text
spring-boot-study
├── Lifecycle
├── Plugins
└── Dependencies
```

能看到这些内容，说明 IDEA 已经将项目识别为 Maven 项目。

## 四、保留外层目录的导入方案

如果你希望继续在同一个 IDEA 窗口中查看外层学习笔记，可以手动导入内层 Maven 项目。

### 步骤 1：找到 pom.xml

在 IDEA 左侧 Project 窗口中展开：

```text
spring-boot-study
```

找到：

```text
pom.xml
```

### 步骤 2：作为 Maven 项目添加

右键单击 `pom.xml`，查找并选择：

```text
Add as Maven Project
```

不同 IDEA 版本的文字可能略有区别，也可能显示 Maven 图标。

### 步骤 3：等待同步

IDEA 会读取 `pom.xml`，下载父 POM、Starter 和其他间接依赖。

### 步骤 4：手动刷新

如果 Maven 工具窗口已经出现，但没有自动同步，单击 Maven 工具窗口中的刷新按钮：

```text
Reload All Maven Projects
```

建议先尝试“直接打开内层项目”。对于当前目录结构，它比手动维护外层 IDEA 项目更清晰。

## 五、检查 IDEA 使用的 JDK

项目导入后打开：

```text
File → Project Structure → Project
```

当前 `pom.xml` 使用 Java 25，因此确认：

```text
Project SDK = 25
```

再打开：

```text
Settings
→ Build, Execution, Deployment
→ Build Tools
→ Maven
```

检查 Maven Runner 和 Importer 使用的 JDK。可以选择：

```text
Project SDK
```

或明确选择 JDK 25。

## 六、检查 Maven 是否处于离线模式

打开 Maven 工具窗口，检查是否启用了：

```text
Toggle Offline Mode
```

如果处于离线模式，Maven 无法从远程仓库下载尚未缓存的依赖。确保离线模式没有启用。

也可以在设置中检查：

```text
Settings
→ Build, Execution, Deployment
→ Build Tools
→ Maven
```

确认没有启用 Offline。

## 七、如何判断依赖正在下载

可以观察以下位置：

1. IDEA 底部状态栏。
2. Maven 工具窗口。
3. `Build` 或 `Sync` 输出窗口。
4. 右下角后台任务图标。

同步完成后：

- `SpringApplication` 不再标红。
- `@SpringBootApplication` 不再标红。
- Maven 窗口显示 Lifecycle、Plugins 和 Dependencies。
- External Libraries 中出现 Spring Boot 相关依赖。
- 启动类左侧出现绿色运行按钮。

## 八、pom.xml 当前依赖说明

当前项目使用 Spring Boot 4.1.1，主要依赖为：

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-webmvc</artifactId>
</dependency>
```

测试依赖为：

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-webmvc-test</artifactId>
    <scope>test</scope>
</dependency>
```

这些是 Spring Boot 4.1.1 项目生成器生成的依赖。当前问题不是依赖声明缺失，而是 IDEA 尚未把该 `pom.xml` 导入为 Maven 项目。

## 九、暂时不要做的操作

在完成正常导入以前，不建议：

- 删除 `.m2` 目录。
- 手工下载并复制 Spring Jar 包。
- 删除 `pom.xml` 中的依赖。
- 同时修改 Spring Boot 和 Java 版本。
- 把内层项目文件拆散到外层目录。
- 反复生成多个名称相同的项目并相互覆盖。

## 十、完成检查清单

操作后逐项检查：

- [ ] IDEA 打开的是 `spring-boot-study`，或者内层 `pom.xml` 已执行 `Add as Maven Project`。
- [ ] Maven 工具窗口能够打开。
- [ ] Maven 工具窗口中显示 `spring-boot-study`。
- [ ] Maven 离线模式没有开启。
- [ ] Project SDK 设置为 25。
- [ ] IDEA 底部的 Maven 下载和索引已经结束。
- [ ] `SpringApplication` 没有红色错误。
- [ ] `@SpringBootApplication` 没有红色错误。
- [ ] 启动类左侧出现绿色运行按钮。

## 十一、操作结果记录

```text
采用的导入方式：

Maven 工具窗口是否出现：

是否看到 Dependencies：

代码中的红色错误是否消失：

是否出现错误提示：

完整错误信息：
```

如果导入后仍然失败，应保留完整错误信息。不要只记录“下载失败”，需要复制 Maven Sync 或 Build 窗口中的第一段错误和 `Caused by` 内容，以便继续判断是网络、证书、代理还是 JDK 配置问题。
