$(document).ready(function(){
	//给搜索按钮绑定单击事件，获取搜索输入框的内容
    window.search=function(){
            //搜索内容
            var searchText=$("#search-text").val();
            alert(searchText);
            $.ajax({
            	url:"search",
            	type:"get",
            	dataType:"json",
            	data:{
            		searchText:searchText,
            	},
            	success:function(json){
            		$('#article_list')[0].innerHTML="";
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
						$('#article_list')[0].innerHTML+=str;
						// console.log(item.title)
					});
            	}
            })
    }
})