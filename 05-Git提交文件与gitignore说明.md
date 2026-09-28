# 05 Git 提交文件与 .gitignore 说明

> 检查日期：2026-09-28  
> 本文记录当前仓库的实际文件状态。所有修改和 Git 操作由学习者本人完成。

## 一、结论

当前外层 `.idea/` 不建议提交。

`.idea/` 主要保存 IntelliJ IDEA 的本地工作区和个人配置，例如：

- 本机 JDK 路径或项目 SDK 信息
- 编辑器工作区状态
- 本地运行和窗口状态
- Maven 仓库显示配置
- 编码设置

其中部分设置可能因电脑、用户和 IDEA 版本不同而变化。学习项目中最简单的做法是忽略整个 `.idea/`。

## 二、当前 Git 状态

目前已经被 Git 跟踪的文件只有：

```text
.gitignore
README.md
```

其余项目文件目前都还是未跟踪状态，即 `git status` 中的 `??`。

外层 `.idea/` 包含：

```text
.idea/compiler.xml
.idea/encodings.xml
.idea/jarRepositories.xml
.idea/misc.xml
.idea/workspace.xml
```

这些文件当前：

```text
未跟踪，并且没有被根目录 .gitignore 忽略
```

## 三、为什么内层规则没有忽略外层 .idea

仓库中有两个 `.gitignore`：

```text
SprintBootProject/.gitignore
SprintBootProject/spring-boot-study/.gitignore
```

内层文件已经包含：

```gitignore
### IntelliJ IDEA ###
.idea
*.iws
*.iml
*.ipr
```

但是它位于：

```text
spring-boot-study/.gitignore
```

这个规则只能影响 `spring-boot-study` 目录及其子目录，无法忽略位于父目录的：

```text
SprintBootProject/.idea/
```

根目录 `.gitignore` 目前只有 Java 编译文件、日志和压缩包等规则，没有 `.idea/` 规则。

## 四、建议手动修改根目录 .gitignore

打开根目录文件：

```text
/Users/hanli/Desktop/Study/backend/SprintBootProject/.gitignore
```

在末尾添加：

```gitignore

# IntelliJ IDEA
/.idea/
*.iml
```

这里使用：

```gitignore
/.idea/
```

开头的 `/` 表示只匹配仓库根目录下的 `.idea` 文件夹。

保存后自行检查：

```bash
git status --short
```

预期 `.idea/*.xml` 不再出现在未跟踪文件列表中。

还可以验证具体文件是否被忽略：

```bash
git check-ignore -v .idea/workspace.xml
```

该命令应该显示是哪一条 `.gitignore` 规则忽略了文件。

## 五、当前文件分类

### 建议提交

学习笔记：

```text
README.md
01-从零创建Spring-Boot项目.md
02-导入Maven项目与下载依赖.md
03-解决首页Error-Page并创建第一个接口.md
04-IDEA没有New-Package选项.md
05-Git提交文件与gitignore说明.md
```

Spring Boot 项目配置和源码：

```text
spring-boot-study/.gitattributes
spring-boot-study/.gitignore
spring-boot-study/.mvn/wrapper/maven-wrapper.properties
spring-boot-study/mvnw
spring-boot-study/mvnw.cmd
spring-boot-study/pom.xml
spring-boot-study/src/main/java/com/Shuan/spring_boot_study/SpringBootStudyApplication.java
spring-boot-study/src/main/java/com/Shuan/spring_boot_study/controller/HelloController.java
spring-boot-study/src/main/resources/application.properties
spring-boot-study/src/test/java/com/Shuan/spring_boot_study/SpringBootStudyApplicationTests.java
```

根目录忽略规则：

```text
.gitignore
```

### 不建议提交

IDEA 本地配置：

```text
.idea/
```

Maven 构建产物：

```text
spring-boot-study/target/
```

编译后的 Java 文件：

```text
*.class
```

### 当前已经正确忽略

内层 `.gitignore` 已经正确忽略：

```text
spring-boot-study/target/
spring-boot-study/HELP.md
spring-boot-study/.mvn/wrapper/maven-wrapper.jar
```

因此 `target/classes` 中的 `.class` 和配置文件不会被提交。

## 六、Maven Wrapper 哪些文件应该提交

建议提交：

```text
.mvn/wrapper/maven-wrapper.properties
mvnw
mvnw.cmd
```

原因是其他人克隆项目后，可以使用项目指定的 Maven 版本执行构建。

当前生成器配置忽略了：

```text
.mvn/wrapper/maven-wrapper.jar
```

当前 Wrapper 使用 `only-script` 分发方式，没有这个 Jar 也属于正常情况。

## 七、建议的手动提交顺序

先修改根目录 `.gitignore`，然后检查：

```bash
git status --short
```

确认列表中：

- 没有 `.idea/`
- 没有 `target/`
- 有学习笔记、源码、`pom.xml` 和 Maven Wrapper

然后再由你自行执行：

```bash
git add .
```

检查暂存内容：

```bash
git status
```

也可以只查看将被提交的文件名：

```bash
git diff --cached --name-only
```

确认无误后再提交：

```bash
git commit -m "初始化 Spring Boot 学习项目"
```

## 八、提交前检查清单

- [ ] 根目录 `.gitignore` 已加入 `/.idea/`。
- [ ] `git status` 中不再出现 `.idea/`。
- [ ] `git status` 中不出现 `target/`。
- [ ] `pom.xml` 在提交列表中。
- [ ] `src/main` 和 `src/test` 源码在提交列表中。
- [ ] `mvnw`、`mvnw.cmd` 和 Wrapper 配置在提交列表中。
- [ ] 学习笔记在提交列表中。
- [ ] 提交前使用 `git diff --cached --name-only` 再次核对。

## 九、实际问题记录：规则前存在空格

本次检查发现根目录 `.gitignore` 的实际内容是：

```gitignore
 /.idea/
  *.iml
```

两条规则前面都有空格。Git 会将没有被转义的前导空格视为规则内容的一部分，因此它们无法匹配真实路径 `.idea/` 和普通的 `.iml` 文件。

应手动删除规则前面的所有空格，改成：

```gitignore
# IntelliJ IDEA
/.idea/
*.iml
```

注意观察：

- `/` 必须是该行的第一个字符。
- `*` 必须是该行的第一个字符。
- 不要为了排版在规则前缩进。
- 注释行以 `#` 开头，不影响匹配。

保存后执行：

```bash
git status --short
```

预期 `.idea/*.xml` 不再出现。

进一步验证：

```bash
git check-ignore -v .idea/workspace.xml
```

预期输出会指出根目录 `.gitignore` 中的 `/.idea/` 规则。

本次还确认 `.idea` 中没有已经被 Git 跟踪的文件，因此不需要执行 `git rm --cached`。只要修正规则并保存，未跟踪的 `.idea` 文件就会从 `git status` 中消失。
