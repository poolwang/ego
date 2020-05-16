<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="/js/jquery-1.6.4.js"></script>
<title>Insert title here</title>
</head>
<body>
<textarea id="text" style="width: 1200px; height: 200px;"></textarea>
<input type="button" value="测试异步跨越" onclick="testajax()" />
<script type="text/javascript">
	function testajax(){
		$.ajax({
			url:"http://localhost:8081/category.json",
			type: "GET",
			dataType: "jsonp",   //jsonp请求
			jsonp:"callbackFunction",  //请求参数名
			jsonpCallback:"showData",  //回调函数名称
		    success: function (data) {
				$("#text").val(JSON.stringify(data));
			}
		}); 
	}
</script>
</body>
</html>