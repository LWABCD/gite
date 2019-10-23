/**
 * 文件名: index-get-artile.js 功能: 异步获取推荐的10篇文章
 */

$(document)
		.ready(
				function() { // ready: 页面加载完成时立即执行以下逻辑

					// 发送异步请求
					$
							.ajax({
								url : 'index-get-articles', // 请求谁?
															// 哪个Controller?
								type : 'get', // 请求方式: get ? post ?
								dataType : 'json', // 希望jQuery把response预处理为哪种类型的对象?
													// text, html, json, script?
								data : {}, // 要提交给服务端的数据
								success : function(json) { // 请求成功后如何处理结果?
															// 这里的json其实就是服务端返回的响应内容
									console.log(json); // 打印到控制看一看
									var article, title, content, articleup, articledown, articleimg;
									// 循环遍历JSON数组, 为每个数组元素生成对应的dom元素  '+$(this).parent().parent('.body-list')+'
									$.each(json,function(index, item) { // index为下标,
										// item为数组元素
										var str = '<div  class="body-list" id="aa" data-articleId="'
												+ item.articleId
												+ '">'
												+ '<div class="body-list-font"  id="up">'
												+ '<a href="IndexGetArticleByIdController?id='+item.articleId+'" >'
												+ item.title
												+ '</a>'
												+ '<p herf="#">'
												+ item.content
												+ '</p>'
												+ '</div>'
												+ '<div class="body-list-img" id="down"><img src="img/f1.jpg" /></div></div>';
										$('#article_list')[0].innerHTML += str;
										// console.log(item.title)
									});
								}
							});
					// $a =
					// document.getElementById("articleId").attributes["data-articleId"].value

				});
