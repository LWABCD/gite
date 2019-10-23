$(document).ready(function() { // ready: 页面加载完成时立即执行以下逻辑
	function sendValiCode() {
		var phone = $("#cal").val();
		var caltishi = document.getElementById("caltishi");
		var re = /^1\d{10}$/;
		if (phone == "") {
			alert("请输入手机号");
		} else if (re.test(phone)) {
					// 发送异步请求
					$.ajax({
						url : 'phoneValidationController', // 请求谁?
						// 哪个Controller?
						type : 'get', // 请求方式: get ? post ?
						
						data : {
							p : phone
						}, // 要提交给服务端的数据
						success : function(msg) { // 请求成功后如何处理结果?
							// 这里的json其实就是服务端返回的响应内容
							// 根据后台返回前台的msg给提示信息加HTML
							if ("true" == msg) {
								// 手机存在
								caltishi.innerHTML = "<font color='red'>此号码已存在</font>";			
							} else {
								
								$(this).addClass("on");
								var time = 60;
								$(this).attr("disabled", true);
								var timer = setInterval(function() {

									if (time == 0) {
										clearInterval(timer);
										$("input[type='button'").attr("disabled", false);
										$("input[type='button'").val("获取验证码");
										$("input[type='button'").removeClass("on");
									} else {
										$('input[type="button"]').val("重新发送(" + time + "s)");
										time--;
									}
								}, 1000);
										
							}
							

						}
					});
				
		    
		    
		  } else {			  
		    alert("手机格式错误");
		   var phone= document.getElementById("cal");
		   phone.value="";
		  }
		
		

		// $a
		// =document.getElementById("articleId").attributes["data-articleId"].value
	}
	$("input[type='button']").click(sendValiCode);

});
