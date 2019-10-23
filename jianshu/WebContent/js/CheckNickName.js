/**
 * 检测用户昵称是否存在
 */
$(document)
		.ready(
				function() {
					function checkName() {
						//alert("hahaha");
						var username = $("#username").val();
						// 获取账号后的提示信息文本
						//alert(username);
						var tishi = document.getElementById("tishi");
						if (username.trim()!="") {
							$
							.ajax({
								url : 'checkNameController', // 后台查询验证的方法
								data : {
									nickName:username
								}, // 携带的参数
								type : 'get',
								success : function(msg) {
									// 根据后台返回前台的msg给提示信息加HTML
									if ("true" != msg) {
										// 账号已经存在
										tishi.innerHTML = "<img  src='img/false0.png'/>";
												
									} else {
										// 手机不存在
										tishi.innerHTML = "<img  src='img/true.png'/>";
												
									}
								}
							});
						}
						

					}

					 $('#username').blur(checkName)
				});