/**
 * 请求获取用户登录状态
 */
$(document).ready(function() {
	function checkState() {
		var li_lg = document.getElementById("lg");
		var li_show = document.getElementById("show");
		var li_reg= document.getElementById("reg");
		
		var loginState, userName;
		$.ajax({
			url : 'loginStateController', // 请求谁?
			// 哪个Controller?
			type : 'get', // 请求方式: get ? post ?
			dataType : 'json',
			success : function(json) {
				// 请求成功后如何处理结果?
				console.log(json);
				var loginState, userName;
				
					loginState = json.loginState;
					userName = json.nickName;
				

				//alert(loginState);
				if (loginState == "true") {
					li_lg.setAttribute("style", "display:none;");
					li_show.setAttribute("style", "display:block;");
					li_reg.setAttribute("style", "border:none;");
					li_reg.innerHTML="<a href='#'>"+userName+"</a>";
				} else {
					li_lg.setAttribute("style", "display:block;");
					li_show.setAttribute("style", "display:none;");
				}

			}
		});

	}
	checkState();
});