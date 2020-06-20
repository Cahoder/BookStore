#《网上书城商城系统》
>基于JSP+Service+Dao+DBCP+JDBC+MYSQL开发
---
##使用手册
1. Java和数据库和Tomcat服务器的安装和运行
   + 自行百度
   + 本项目使用的Java JDK版本建议大于8++
   + 本项目使用的Tomcat服务器版本建议大于8.5++
   + 本项目使用Mysql数据库版本建议大于5.6++
2. 在数据库建库或者在已有的库运行SQL文件建表
   + bookstore.sql文件位于本项目根目录下
3. 修改本项目的数据库配置文件db.config
   + 配置文件中选项说明
   ***
   ```
      ###数据库配置###
      host=数据库地址（例：本地为localhost）
      port=数据库端口（例：mysql为3306）
      username=数据库连接用户名（例：admin）
      password=数据库连接用户密码（例：123456）
      dbName=数据库连接库名
      driverClassName=JDBC驱动类路径（例：mysql的驱动类为com.mysql.cj.jdbc.Driver）
      usingDB=当前使用的数据库类型（例：mysql）
      srcRoot=\BookStore\WebContent （项目根路径）
      ###数据库连接池配置###
      maxIdle=30 （连接池中每条连接的生存时间）
      maxWaitMillis=10000  （连接池中每条连接最大等待连接时间）
      maxTotal=100   （连接池中最大存在连接数）
      minTotal=5   （连接池中最小存在连接数）
      initPoolSize=5   （初始化连接池中连接数）
      acquireIncrement=5   （连接池中连接不够用时，每次递增多少个）
   ```
   + db.config文件位于/src/main/webapp/WEB-INF/
   + db.config文件配置是DBCP数据库连接池依赖
4. 将项目部署到Tomcat的webapps下
   + 如何部署自行百度
 
##项目结构组成图
![结构组成图](http://upload.ouliu.net/i/202006201929238jf2l.jpeg "系统组成图")

##项目运行流程图
![运行流程图](http://upload.ouliu.net/i/202006201933421da4c.jpeg "运行流程图")

##写在最后
   + 如有问题可以联系本人邮箱 2228694226@qq.com

