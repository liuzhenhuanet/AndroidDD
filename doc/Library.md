# Library
该文档记录了可能有用的Android第三方库，只作为索引，当需要用到某功能时可以在这个文档里查找有没有对应的库

### zip4j
- 一个解压缩库，纯java代码实现
- 该库能够在压缩时添加密码，也能解压带密码的zip文件，
- 能监测解压和压缩进度
- 能够从zip文件解压单个文件，也能往zip文件中添加文件，功能几乎覆盖了好压等工具的所有功能
- 官方包含了使用示例工程
- 开源
- [zip4j官方下载地址，包括jar包，源码包，示例工程](http://www.lingala.net/zip4j/download.php)

### apk-parser
- 一个简单的获取apk相关信息的java库
- 能直接获取`appVersion`,`appVersionCode`,`packageName`,`minSdkVersion`（Android sdk需要API 24才能获取）,`targetSdkVersion`,`maxSdkVersion`
- 简单小巧，只包含9个java文件，没有其他依赖
- 能够读取二进制xml，如果想获取更多的信息，可以使用库提供的读取二进制xml的代码自己解析

### remote-apk-parser
- apk解析java库，功能强大，能解析apk中几乎所有的信息，相比`apk-parser`，该库大了很多，并且有第三方基础库依赖