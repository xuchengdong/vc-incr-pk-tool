# 使用说明
> jar 所在目录添加配置文件 `file.conf` 里面配置需要保留的文件路径,然后执行如下命令,根据提示输入 `需要过滤的编译后的工程路径` 等待执行完成.
```
java -cp file-filter-1.0-SNAPSHOT.jar com.df.core.FileFilterHelper3
```
> SVN 查看提交记录涉及的文件列表
```
svn log -v -r [版本号 或 版本号1:版本号2]
```
> Git 查看提交记录涉及的文件列表
```
 git show --stat 提交记录id --name-only
```