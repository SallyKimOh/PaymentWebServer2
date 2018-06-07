<%@ page language="java" contentType="text/html; charset=UTF8" pageEncoding="UTF8"%>
<%@page import="java.io.*" %>
<%@page import="java.text.*" %>
<%@page import="com.metaflow.payment.comm.PropUtil" %>
<%@page import="com.metaflow.payment.model.Common.*" %>
<%@page import="com.metaflow.payment.model.*" %>
<%@page import="java.util.*" %>

<html>
<head>
	<script src="//ajax.googleapis.com/ajax/libs/jquery/1.8.3/jquery.min.js"></script>
    <link href="styles/table.css" rel="stylesheet" type="text/css">
<title>Insert title here</title>
<style>
table, th, td {
    border: 1px solid black;
    border-collapse: collapse;
    font-family: "Times New Roman", Times, serif;
}
th, td {
    padding: 5px;
    text-align: left;  
    font-family: "Times New Roman", Times, serif;
}

</style>
</head>
<body>

<%
String fileName = request.getParameter("fileName");
String filePath = new PropUtil().getPropValue().getProperty("FILE_PATH") + fileName;
List<FileInfo> headerList = new ArrayList<>();
List<FileInfo> fileList = new ArrayList<>();
List<PreviewInfo> batchList = new ArrayList<>();
PreviewInfo prev;

FileInfo vo = new FileInfo();

NumberFormat formatter = NumberFormat.getCurrencyInstance();

%>

<!-- Created the file : <a href='<%//=filePath%>' download="<%//=fileName%>"><%//=fileName%></a>  -->

<%

	BufferedReader br = null;
	FileReader fr = null;
	FileInfo batchVo = null;
	
	try {
		fr = new FileReader(filePath);
		br = new BufferedReader(fr);
		
		String sCurrentLine;
		while ((sCurrentLine = br.readLine()) != null) {
		char i  = sCurrentLine.charAt(0);

			switch (i) {
			case '1':
				vo.setRecvDataCentre(sCurrentLine.substring(3,8));
				vo.setOriginatorNum(sCurrentLine.substring(13,23));
				vo.setFileCreateDt(sCurrentLine.substring(23,29));
				vo.setFileCreateNum(sCurrentLine.substring(29,33));
				vo.setInstitutionID(sCurrentLine.substring(34,38));
				vo.setBranchNo(sCurrentLine.substring(38,43));
				vo.setAccountNo(sCurrentLine.substring(43,55));
				vo.setOriginatorName(sCurrentLine.substring(57,72));
				break;
			case '5':
				batchVo = new FileInfo();
				batchVo.setBatHeaderTransactionType(sCurrentLine.substring(47,50));
				batchVo.setBatHeaderDescStatement(sCurrentLine.substring(50,60));
				batchVo.setBatHeaderDueDate(sCurrentLine.substring(60,66));
				fileList = new ArrayList<FileInfo>();
				break;
			case '6':
				FileInfo tempVo = new FileInfo();
				tempVo.setServiceIndicatorType(sCurrentLine.substring(1,2));
				tempVo.setInstitutionalID(sCurrentLine.substring(3,7));
				tempVo.setInstitutionalBranchNo(sCurrentLine.substring(7,12));
				tempVo.setDetailAccountNo(sCurrentLine.substring(12,24));
				tempVo.setAmount(sCurrentLine.substring(29,39));
				tempVo.setCrossReferenceNo(sCurrentLine.substring(39,52));
				tempVo.setPayeeName(sCurrentLine.substring(52,74));
				fileList.add(tempVo);
				break;
			case '7':
				batchVo.setBatTrailerEntryCount(sCurrentLine.substring(4,10));
				batchVo.setBatTrailerEntryDollarTotal(sCurrentLine.substring(40,52));
				HashMap<FileInfo,List<FileInfo> > hMap = new HashMap<>();
				hMap.put(batchVo, fileList);
				prev = new PreviewInfo(batchVo,fileList);
				batchList.add(prev);
				break;
			case '9':
				vo.setEndBatchCount(sCurrentLine.substring(1,7));
				vo.setEndDetailCount(sCurrentLine.substring(7,13));
				break;
			}
			i++;
		}
		
		fr.close();
		br.close();
	} catch (Exception e) {
		
	}
	
%>


	<p>
    <h1 class="test-results-header">
        Preview Report
    </h1>

    <table class="test-result-table" cellspacing="0">
        <tbody>
            <tr class="test-result-step-row test-result-step-row-altone">
                <td class="test-result-table-header-cell">
                    <p>Data Center location</p>
                </td>
                <td class="test-result-step-description-cell">
                   <%=vo.getRecvDataCentre()%>
                </td>
                <td class="test-result-table-header-cell">
                    Originator Number
                </td>
                <td class="test-result-step-command-cell">
                    <%=vo.getOriginatorNum()%>
                </td>
            </tr>
            <tr class="test-result-step-row test-result-step-row-alttwo">
                <td class="test-result-table-header-cell">
                    File Create Date
                </td>
                <td class="test-result-step-description-cell">
                    <%=vo.getFileCreateDt()%>
                </td>
                <td class="test-result-table-header-cell">
                    File CreateNumber
                </td>
                <td class="test-result-table-header-cell">
                    <%=vo.getFileCreateNum()%>
                </td>
            </tr>
            <tr class="test-result-step-row test-result-step-row-altone">
                <td class="test-result-table-header-cell">
                    Bank Institutional ID
                </td>
                <td class="test-result-step-description-cell">
                    <%=vo.getInstitutionID()%>
                </td>
                <td class="test-result-table-header-cell">
                    Branch No
                </td>
                <td class="test-result-step-result-cell-notperformed">
                    <%=vo.getBranchNo()%>
                </td>
            </tr>
            <tr class="test-result-step-row test-result-step-row-altone">
                <td class="test-result-table-header-cell">
                    Account No
                </td>
                <td class="test-result-step-description-cell">
                    <%=vo.getAccountNo()%>
                </td>
                <td class="test-result-table-header-cell">
                    Originator's Name
                </td>
                <td class="test-result-step-result-cell-notperformed">
                    <%=vo.getOriginatorName()%>
                </td>
            </tr>
        </tbody>
    </table><br>
    <%
    
    for (int k = 0; k < batchList.size(); k++) {
    %>
    
    <table  class="test-result-table" cellspacing="0" width=100%>
        <tr>
            <td class="test-result-describe-cell" colspan=6>Batch Information</td>
        </tr>
        <tr>
            <td class="test-result-table-header-cell">TransactionType</td>
            <td><%=batchList.get(k).getFileVo().getBatHeaderTransactionType() %></td>
            <td class="test-result-table-header-cell">Description</td>
            <td><%=batchList.get(k).getFileVo().getBatHeaderDescStatement()%></td>
            <td class="test-result-table-header-cell">Due Date</td>
            <td><%=batchList.get(k).getFileVo().getBatHeaderDueDate()%></td>
        </tr>
    </table>
    <br>    
    <table class="test-result-table" cellspacing="0">
        <thead>
            <tr>
                <td class="test-result-table-header-cell ">
                    Service Type
                </td>
                <td class="test-result-table-header-cell">
                    Institutional ID
                </td>
                <td class="test-result-table-header-cell">
                    Branch No
                </td>
                <td class="test-result-table-header-cell">
                    Account No
                </td>
                <td class="test-result-table-header-cell">
                    Amount(CDN$)
                </td>
                <td class="test-result-table-header-cell">
                    Reference No
                </td>
                <td class="test-result-table-header-cell">
                    Payee's Name
                </td>
            </tr>
        </thead> 
        <tbody>
        
        <%
        
		for (int i = 0; i < batchList.get(k).getFileList().size(); i++) {
	    	String eachAmount = batchList.get(k).getFileList().get(i).getAmount().toString();

		%>
             <tr class="test-result-step-row test-result-step-row-altone">
                <td class="test-result-step-command-cell">
                    <%//= fileList.get(i).getServiceIndicatorType() %>
                    <%
                    String type = batchList.get(k).getFileList().get(i).getServiceIndicatorType().equals("C") ? "Credit" : "Debit";
                    out.println(type);
                    %>
                </td>
                <td class="test-result-step-description-cell">
                    <%= batchList.get(k).getFileList().get(i).getInstitutionalID() %>
                </td>
                <td class="test-result-step-description-cell">
                    <%= batchList.get(k).getFileList().get(i).getInstitutionalBranchNo() %>
                    
                </td>
                <td class="test-result-step-description-cell">
                    <%= batchList.get(k).getFileList().get(i).getDetailAccountNo() %>
                    
                </td>
                <td class="test-result-step-description-cell">
                <%= "$"+Integer.parseInt(eachAmount.substring(0,eachAmount.length()-2))+"."+eachAmount.substring(eachAmount.length()-2, eachAmount.length()) %>
                    
                </td>
                <td class="test-result-step-description-cell">
                    <%= batchList.get(k).getFileList().get(i).getCrossReferenceNo() %>
                    
                </td>
                <td class="test-result-step-description-cell">
                    <%= batchList.get(k).getFileList().get(i).getPayeeName() %>
                   
                </td>
            </tr>
		<%	
		}
    	String totalAmount = batchList.get(k).getFileVo().getBatTrailerEntryDollarTotal().toString();

        %>
        
             <tr class="test-result-step-row test-result-step-row-altone">
                <td class="test-result-table-header-cell" colspan="3">
                    Total Num : 
                <td class="test-result-step-total-cell"> 
                   <%= Integer.parseInt(batchList.get(k).getFileVo().getBatTrailerEntryCount()) %>
                </td>
                <td class="test-result-table-header-cell" colspan="2">
                    Total Amount : 
                <td class="test-result-step-total-cell">
                    <%//=formatter.format(Double.parseDouble(batchList.get(k).getFileVo().getBatTrailerEntryDollarTotal().toString()))%>
                	<%= "$"+Integer.parseInt(totalAmount.substring(0,totalAmount.length()-2))+"."+totalAmount.substring(totalAmount.length()-2, totalAmount.length()) %>
                </td>
            </tr>
        </tbody>
    </table>
    <br>
    <%
    }
    %>
    <table  class="test-result-table" cellspacing="0" width=100%>
        <tr>
            <td colspan=4 class="test-result-describe-cell">Summary</td>
        </tr>
        <tr>
            <td class="test-result-table-header-cell">Batch Count</td>
            <td><%=Integer.parseInt(vo.getEndBatchCount()) %></td>
            <td class="test-result-table-header-cell">Detail Count</td>
            <td><%=Integer.parseInt(vo.getEndDetailCount())%></td>
        </tr>
    </table>
<% if (batchList.size() > 0) { %>
	<form name=form>
    <p align="left"><input type="button" name="download" value="download" onclick="fn_down('<%=fileName%>');"></p>
    </form>
<% } %>    
 <script> 
 

 function fn_down(file){
	form.action="webresources/myresource/download?fileName="+file;
	form.method="post";
	form.submit();
	alert("success");
 }

</script>
</body>

</html>


