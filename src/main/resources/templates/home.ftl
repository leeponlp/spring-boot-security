<#import "spring.ftl" as spring>
<!DOCTYPE HTML>
<html>
 <head>
  <title>主页</title>
   <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
 </head>
 <body>
 </br>
 </br>
 ${username},欢迎登陆！！！
</br>
<a href = "/book/view">书品目录</a>
</br>
<#if authlist?seq_contains("ROLE_ADMIN")>
    <a href = "/system/view">系统设置</a>
</#if>
</br>
<#list authlist as auth>
   ${auth}
</#list>
</br>
在线人数：${loginNum}
    
 
 </body>
</html>