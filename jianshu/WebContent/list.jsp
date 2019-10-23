<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.foo.entity.Article"%>
<%@page import="com.foo.entity.User"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="css/list.css" />

<!-- 先导入jquery脚本库 -->
<script src="js/jquery.js"></script>
<!-- 再导入自己的js, 因为我们自己的这个js使用了jquery库提供的API -->

<script src="js/loginstate.js"></script>
</head>

<body>
    <%
        Article article=(Article)request.getAttribute("articles");
        User user=(User)request.getAttribute("users");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String time=sdf.format(article.getPostTime());
    %>

	<div class="head-font-style">
			<ul>
				<li class="head-sy" >首页</li>
				<li class="head-xz">下载App</li>
				<li class="head-ss">
					<div class="head-input">
						<p class="head-input-p"><img class="td-img" src="img/search.png"/></p>
						<input type="search" class="head-search" placeholder="搜索" />
					</div>
				</li>
			</ul>
			</div>
			<!--------------------------->
			<div class="head-font-style-right">
				<ul>
				<li><a href="">Aa</a></li>
				<li><img src="img/download.png"/></li>
				<li id="lg"><a href="login.jsp">登录</a></li>			
				<li class="head-li-img" id="show" style="display: none;">
					<img src="img/pkq.jpg"/><img src="img/xiaj.png" style="width:25px;"/>
					<ul class="head-erji">
						<li><img src="img/my.png"/><a href="">我的主页</a></li>
						<li><img src="img/shouc.png"/><a href="">收藏的文章</a></li>
						<li><img src="img/like.png"/><a href="">喜欢的文章</a></li>
						<li><img src="img/buy.png"/><a href="">已购内容</a></li>
						<li><img src="img/money.png"/><a href="">我的钱包</a></li>
						<li><img src="img/shez.png"/><a href="">设置</a></li>
						<li><img src="img/help.png"/><a href="">帮助</a></li>
						<li><img src="img/exit.png"/><a href="jionOutController">退出</a></li>
					</ul>
				</li>
				<li><p class="head-register" id="reg" ><a href="reg.jsp">注册</a></p></li>
				<li><p class="head-writebook"><a href="">写文章</a></p></li>
				</ul>
			</div>
		</div>
		<div class="top"></div>
		<div class="body">
			<h1 class="title"><%=article.getTitle() %></h1>
			
			<div class="auther">
				<a>
					<img src="img/auther.png"/>
				</a>
				<div class="auther-info">
					<span class="name"><a href=""><%=user.getNickName()%></a></span>
					<img src="img/bj.png"/>
					<span class="gz">+关注</span>
					<div class="auther-meta">
						<span class="xx">
							<%=time%>&nbsp;&nbsp;字数&nbsp;&nbsp;<%=article.getWordNum() %> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 阅读&nbsp;&nbsp;<%=article.getReadNum()%> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;评论&nbsp;&nbsp;<%=article.getCommentNum()%>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;喜欢&nbsp;&nbsp;<%=article.getLikeNum()%>
						</span>
					</div>
				</div>
			</div>
			
			<div class="auther-p">
			
		    <%=article.getContent() %>
				<!-- <p>这世上最难走的路，叫生活，我们一路跌倒，一路坚强，任尘世流转，岁月沧桑，内心安然无恙。</p>
				<p>三毛说:“人生如茶，第一道苦若生命，第二道甜似爱情，第三道淡如清风。” 其实，生命就是一个不断寻找，不断历练，不断感悟的过程，从最初的天真单纯，到百炼成钢，再到最后的云水禅心，是悟，亦是成长。</p>
				<p>岁月沧桑，我依然坚强。坚强，是百折不挠的勇气，是跌倒之后的奋起，是世间最强大的力量，也是生命的希望。生活的酸甜苦辣，每个人都要品尝一遭，再苦再累，也要坚强，为自己，也为那些爱自己的人。</p>
				<p>岁月沧桑，我依然善良。大千世界，芸芸众生，每个人都不尽相同，你不懂他的苦，他亦不懂你的痛，以一颗慈悲心相处，对身边的人和事，多些宽容。善良是一个人最伟大的情怀，是自我修养的提高，你的善良，万丈光芒。</p> -->
			</div>
			
		</div>
</body>
</html>