<%@ page language="java" contentType="text/html; charset=UTF-8"
	    pageEncoding="UTF-8" import="com.sist.dao.*,java.util.*"%>
	    
	    <%
	    
	    //https://www.google.com/search?hl=ko&sxsrf=ACYBGNTXDCOTxKuZxQ_L30QgVSp0xkwgbQ%3A1581315638868&source=hp&ei=NvZAXrzXMsX7wAPWy56oCQ&q=%EC%9E%90%EB%B0%94&oq=%EC%9E%90%EB%B0%94&gs_l=psy-ab.3..35i39l3j0i67j0i131j0i67l3j0l2.1092.1406..1649...2.0..1.101.380.3j1......0....1..gws-wiz.BbnFtp_i4bk&ved=0ahUKEwi8yrjnq8bnAhXFPXAKHdalB5UQ4dUDCAY&uact=5
			try{
				request.setCharacterEncoding("UTF-8");
			}catch(Exception ex){}
		    	String dong=request.getParameter("dong");
		    //System.out.println(dong);
		    ArrayList<ZipcodeVO> list=null;
		    if(dong!=null){
		    	ZipcodeDAO dao = new ZipcodeDAO();
		    	list=dao.postFind(dong);
		    }
	    %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="table.css">
<script type="text/javascript"> 
function send(){
	var dong = document.frm.dong.value;
	if(dong==""){
		document.frm.dong.focus();
		return;
	}
	document.frm.submit();  //데이터 전송
}
function ok(zip,addr){
	
	opener.frm.post.value=zip;
	opener.frm.addr1.value=addr;
	self.close();
}
</script>
<style type="text/css">
th,td{
	font-size: 8pts;
}
</style>
</head>
<body>
	<center>
	 <h3>우편번호검색</h3>
	 <table id="table_content" width=420>	
	 	<tr>
	 		<td width=15% class="tdright">입력</td>
	 		<td>
	 			<form method=post action="post.jsp" name=frm>
	 				<input type=text name=dong size=15>
	 				<input type="button" value="검색" onclick='send()'>
	 			</form>
	 		</td>
	 		</tr>
	 		<tr>
	 			<td colspan="2" class="tdcenter">
	 				<sup><font color="red">※동/읍/명 을  입력하세요※ </font></sup>
	 			</td>
	 		</tr>
	 	</table>
	 	<%
	 		if(list!=null){
	 	%>
	 	
	 		<table id="table_content" width=420>
	 			<tr>
	 				<th width=25%>우편번호</th>
	 				<th width=25%>주소</th>
	 			
	 			</tr>
	 			<%
	 				for(ZipcodeVO vo:list){
	 					%>
	 					
	 					<tr class="dataTr">
	 						<td width=25% alignt=center><%=vo.getZipcode() %></td>
	 						<td width=75% alignt =left>
	 						<a href="javascript:ok('<%=vo.getZipcode() %>','<%=vo.getAddress() %>')"><%=vo.getAddress() %></a></td>	
	 					</tr>
	 				<%
	 				
	 				}
	 			%>
	 		</table>
	 	
	 	<%
	 	
	 		}
	 	%>
	</center>
</body>
</html>