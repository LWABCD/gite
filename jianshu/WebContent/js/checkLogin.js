$(document).ready(function() { // ready: 页面加载完成时立即执行以下逻辑
	function checkphone() {
		
		var phone = $("#cal").val();
		var caltishi = document.getElementById("caltishi");
		var re = /^1\d{10}$/;
		if (phone == "") {
			caltishi.innerHTML = "<font color='red'>请输入手机号</font>";	
			
		} else if (re.test(phone)) {
					// 发送异步请求
					$.ajax({
						url : 'phoneLoginController',
						type : 'get', 
						data : {
							p : phone
						},
						success : function(msg) {
							if ("true" == msg) {
							} else {				
								// 手机不存在
								caltishi.innerHTML = "<font color='red' >用户不存在</font>";			
							}
						}
					});
		  } else {			  
		  
		   var phone= document.getElementById("cal");
		   caltishi.innerHTML = "<font color='red' >格式错误</font>";	
		   phone.value="";
		  }
	}
	 $('#cal').blur(checkphone)
});
