<!DOCTYPE html>
<!--  main 페이지가 구도를 잡고, jsp파이들을 불러서 처리. 
 -->

<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<table border=1 width=900 height 700></table>
	<tr>
		<td colspan ="2" height=100>
			<%
				pageContext.inclue("header.jsp");					
			%>
		</td>
	</tr>
			<td width =200 height=500> </td>
			<td width = 700 height 500></td>
		<td colspan="2" height =100></td>
		</table>
</body>
</html>