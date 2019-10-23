<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>登录</title>
<link rel="stylesheet" href="css/login.css" />
<!-- 先导入jquery脚本库 -->
<script src="js/jquery.js"></script>
<!-- 再导入自己的js, 因为我们自己的这个js使用了jquery库提供的API -->
<script src="js/checkLogin.js"></script>
</head>
<body>
	<%
		String msg = (String) request.getAttribute("errorMsg");
		if (msg == null) {
			msg = "";
		}
	%>
	<p>
		<a href="index.html" class="js">简书</a>
	</p>
	<div class="body">
		<form action="LoginController" method="post">
			<div class="body-tishi">
				<span class=""><%=msg%></span>
			</div>
			<div class="body-font">
				<span><a href="login.jsp" class="login">登录</a>&emsp;·&emsp;<a
					href="reg.jsp" class="reg">注册</a></span>
			</div>

			<div class="body-table">
				<table cellspacing="0" cellpadding="0">
					<tr>
						<td><img class="td-img" src="img/use.png" /><input
							type="text" name="cal" id="cal" class="inp1" onblur="blur()"
							placeholder="手机号或邮箱" /> <span id="caltishi" class='phonemsg'></span>
						</td>

					</tr>
					<tr>
						<td><img class="td-img" src="img/pwd.png" /> <input
							type="password" name="pwd" id="pwd" class="inp2" placeholder="密码" />
						</td>
					</tr>
					<tr>
						<td class="td-box"><input type="checkbox" name="box" id="box" />
							记住我 <a href="" class="box-a">登录遇到问题？</a></td>
					</tr>
				</table>
			</div>

			<div class="button" align="center">
				<input type="submit" value="登录" class="btn-submit" />
			</div>
		</form>
	</div>

</body>
<ml>