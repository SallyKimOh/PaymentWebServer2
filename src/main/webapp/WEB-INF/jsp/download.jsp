<%@ page language="java" contentType="text/html; charset=UTF8" pageEncoding="UTF8"%>
<%@page import="java.io.*" %>
<%@page import="com.metaflow.payment.comm.PropUtil" %>
<%@page import="com.metaflow.payment.model.Common.FileInfo" %>

<html>
<head>
<title>Insert title here</title>
</head>
<body>
<script language="javascript">
alert("success");
</script>

<%
//ResourceLoader resourceLoader;
String fileName = request.getParameter("fileName");
//String filePath = "./" + fileName;
String filePath = new PropUtil().getPropValue().getProperty("FILE_PATH") + fileName;

System.out.println(filePath);
//file = resourceLoader.getResource("classpath:static/sample." + fileType).getFile();

%>
<!--  Created the file : <a href='<%//=filePath%>' download="<%//=fileName%>"><%//=fileName%></a>
-->
<%

	FileInputStream fin = null;

	ServletOutputStream oout = null;
	 
	try {
		File file = new File(filePath);
		fin = new FileInputStream(file);

		int ifilesize = (int)file.length();
		byte b[] = new byte[ifilesize];

		response.setContentLength(ifilesize);
		response.setContentType("application/smnet");
		response.setHeader("Content-Disposition","attachment; filename="+fileName+";");

		
		oout = response.getOutputStream();
		
		fin.read(b);
		oout.write(b,0,ifilesize);
		oout.flush();
		oout.close();

	} catch(Exception e) { 
	} finally {
		if( oout != null ) oout.close();
		if( fin != null ) fin.close();
	}






%>

</body>

</html>


