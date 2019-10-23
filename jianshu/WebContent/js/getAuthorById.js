/**
 * 
 */
$(document)
		.ready(
				function() { // ready: 页面加载完成时立即执行以下逻辑
					
						function getArticleId(ele){  
					        // alert(ele.eventId);// 这个只能在 IE中可用  
					        // 下面这两种方法在IE,FireFox和Chrome中都能用，都可以  
					        alert(ele.attributes['data-articleId'].nodeValue);  
					        alert(ele.getAttribute('data-articleId'));  
					        // 总结，对于HTML元素，标准中规定的属性可以 ele.attributeName  
					        // 的方法去获取其值；用户自定义的（自动放到页面元素中的）属性  
					        // 就要通过ele.getAttribute("attributeName") 的方式来获取其值  
					        // 了。[IE例外] ：）  
					}
					$('#getarticleId').click(getArticleId);
				});
				