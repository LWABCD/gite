/**
 * 文件名: index-get-artile.js
 * 功能: 异步获取推荐的10篇文章
 */



$(document).ready(function () { // ready: 页面加载完成时立即执行以下逻辑
	window.userFollowUser=function(obj) {
		//var beFollowedUid=$('#beFollowedUid');
		//console.log(beFollowedUid.innerHTML);
		//alert(beFollowedUid.innerHTML)
		var uid=obj.getAttribute('uid');
		console.log(uid);
		alert(uid);
		$.ajax({
			url : 'userFollowUserController', // 请求谁?
			// 哪个Controller?
			type : 'get', // 请求方式: get ? post ?
			dataType : 'json',
			data : {
				uid:uid
			},
			success : function(json) {
				//已登录
				if(json.loginState=="true"){
					obj.innerText="已关注";   //用户点击关注后改变文本
					obj.style="opacity:0.2";   //把已关注字体变灰
					obj.onclick=function(){return false};   //让a标签的onclick函数不可用
				//未登录就跳转到登录页面
				}else{
					location.href="login.jsp";
				}
			}
		});
    }
	
	function hyp(){
    	 // 发送异步请求
        $.ajax({
            url: 'index-get-users', // 请求谁? 哪个Controller?
            type: 'get', // 请求方式: get ? post ?
            dataType: 'json', // 希望jQuery把response预处理为哪种类型的对象? text, html, json, script?
            data: {}, // 要提交给服务端的数据
            success: function (json) { // 请求成功后如何处理结果? 这里的json其实就是服务端返回的响应内容
                console.log(json); // 打印到控制看一看
       
                var user, nicknName, totalWordNum,totalLikeNum; 
                
                //$('#user-list').empty();//jquary风格清空
                
                document.getElementById('user_list').innerHTML = '';
                
                // 循环遍历JSON数组, 为每个数组元素生成对应的dom元素
                $.each(json, function (index, item) { // index为下标, item为数组元素
                	totalWordNum=(item.totalWordNum/1000).toFixed(1)
                	var str='<div class="body-right-list-font"><p><a style="color: black; font-size: 16px;">'+item.nickName+'</a></p>'+
        			'<p>写了'+totalWordNum+'k字 ·'+item.totalLikeNum+'k喜欢</p>'+
        		'</div><div class="body-right-list-gz"><a href="javascript:void(0)" onclick="userFollowUser(this)" id="'+item.uid+'" uid='+item.uid+'>+关注</a></div>'
        			$('#user_list')[0].innerHTML += str;
    			
                });
               // $('#user_list')[0].innerHTML += '<div class="bottom-ck">查看全部 ></div>'
       
            }
        });
    }
    
    hyp();
    
    //$('#hyp_link').on('click',hyp);
    $('#hyp_link').click(hyp);
    //$('#beFollowedUid').click(userFollowuser);



});




