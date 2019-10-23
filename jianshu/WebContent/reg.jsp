<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>注册</title>
<link rel="stylesheet" href="css/reg.css" />

<!-- 先导入jquery脚本库 -->
<script src="js/jquery.js"></script>
<!-- 再导入自己的js, 因为我们自己的这个js使用了jquery库提供的API -->
<script src="js/valicode.js"></script>
<script src="js/CheckNickName.js"></script>
</head>
<body>
	<%
		String msg = (String) request.getAttribute("errorMsg");
	if(msg==null)
	{
		msg="";
	}
		String result = (String) request.getAttribute("result");
		String msg1 = "", msg2 = "", msg3 = "";
		if ("1".equals(result)) {
			msg1 = "此昵称已存在";
		}
		if ("2".equals(result)) {
			msg2 = "此号码已注册";
		}
		if ("3".equals(result)) {
			msg3 = "验证码错误";
		}
		
	%>
	<p><a href="index.html" class="js">简书</a></p>
	<div class="body">
		<div class="body-tishi">
			<span class=""><%=msg %></span>
		</div>
		<div class="body-font">
			<span> <a class="login" href="login.jsp">登录</a>&emsp;·&emsp; <a
				class="reg" id="reg" href="reg.jsp">注册</a>
			</span>
		</div>

		<div class="body-table">
			<form action="regController" method="post">
				<table cellspacing="0" cellpadding="0">
					<tr>
						<td><img class="td-img" src="img/use.png" /> 
						<input	type="text" name="username" id="username" class="inp1"
							placeholder="你的昵称" onblur="blur()" /> <span id="tishi"
							class="tishi"></span></td>
					</tr>
					<tr>
						<td><img class="td-img" src="img/phone.png" /> <input
							type="tel" name="cal" id="cal" class="inp2" placeholder="你的手机号" />
							<span id="caltishi" class='phonemsg'></span> <span
							id="tishi_phone" class='tishi'><%=msg2%></span></td>
					</tr>
					<tr>
						<td><img class="td-img" src="img/fang.png" /> <input
							type="tel" name="calyzm" id="calyzm" class="inp3"
							placeholder="手机验证码" /> <input type="button" name="yzm" id="yzm"
							class="inp4" value="发送验证码" /> <span id="tishi_Ivalid"
							class='tishi'><%=msg3%></span></td>
					</tr>
					<tr>
						<td colspan="2"><img class="td-img" src="img/pwd.png" /> <input
							type="password" name="pwd" id="pwd" class="inp5"
							placeholder="你的密码" /></td>
					</tr>
				</table>
				<div class="button" align="center">
					<input type="submit" value="注册" class="btn-submit" />
				</div>
			</form>

		</div>


	</div>

</body>
<ml>