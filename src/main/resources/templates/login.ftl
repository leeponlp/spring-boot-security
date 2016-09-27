<!DOCTYPE HTML>
<html lang="zh-CN">
<head>
<title>登陆页</title>
<script type="text/javascript" src="../js/jquery-1.12.4.min.js"></script>
<script type="text/javascript">

	// 登录提示方法
	function loginsubmit() {
		$("#loginform").submit();

	}
	
</script>
</head>
<body>
   <form id="loginform" name="loginform" action="/login" method="post">
          <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
          <table width="10%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                 <td>账号</td>
                 <td height="25"><input type="text" name="username"/>
              </tr>
              <tr>
                 <td>密码</td>
                 <td height="25"><input type="password" name="password"/>
              </tr>
              <tr>
                 <td height="25"><input type="button" onclick="loginsubmit()" value="登&nbsp;&nbsp;录"></td>
              </tr>
           </table>
   </form>
</body>
</html>