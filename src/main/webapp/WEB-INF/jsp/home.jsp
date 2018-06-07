<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
<head>
    <title>Payment Information</title>
    <link href="styles/glDatePicker.default.css" rel="stylesheet" type="text/css">

    <script src="//ajax.googleapis.com/ajax/libs/jquery/1.8.3/jquery.min.js"></script>
	<script src="./script/calendar_beans_v2.2.js" type="text/javascript" charset="utf-8"></script>
	<script src="./script/jquery.mask.min.js" type="text/javascript" charset="utf-8"></script>

    <script  type="text/javascript">
    function fnload(){
    	
		form1.date.value = form1.cal1.value;  
    	form1.submit();
    }
    </script> 
</head>
<body>
	<h1>Bill Payment</h1>
    <form:form method="POST" name="form1" action="/preview" modelAttribute="homeInfo">
	
    <h2>Creating File</h2>
    <p> Choose the date : <input type="text" id="cal1" readonly> </p>

    <script type="text/javascript">
		initCal({id:"cal1",type:"day",today:"y",icon:"y"});			
    </script>
    
      
    <form:input type="hidden" path="date"/>
    <br><br>
 	<input type="button" name="send" value="send" onclick="fnload();"/>
   
    </form:form>
</body>
</html>