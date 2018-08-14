package com.metaflow.payment.service;

import java.io.IOException;
import java.util.List;
import java.util.function.Consumer;

import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.query.ResultSetFormatter;

import com.metaflow.payment.comm.PropUtil;


public class PaymentAgent {

    static final String rdf = "http://www.w3.org/1999/02/22-rdf-syntax-ns#";
    static final String foaf = "http://xmlns.com/foaf/0.1/";
    static final String acct = "http://purl.org/edg/accounting#";
    static final String rdfs = "http://www.w3.org/2000/01/rdf-schema#";
    static final String uriprefix = "http://data.metaflow.ca/";
    static final String url = new PropUtil().getPropValue().getProperty("RDF_URL") ;   // Default url.
//    static final int port = 3336 ;   // Default port.
    static final int port = Integer.parseInt(new PropUtil().getPropValue().getProperty("RDF_PORT")) ;  // Default Dev Server port.
	
	public static void main(String[] args) throws IOException {
		
		PaymentAgent agent = new PaymentAgent();
  
//        agent.queryAgentList();
//        agent.queryPaymentList();

		
		
			agent.queryPaymentInfoList();//keep
			
		
//		agent.queryAgents();
        List<QuerySolution> list = agent.ListPaymentInfo("2018-07-15"); //keep

	}
	
	private void queryAgents() {


		String queryString = "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> " +
				"PREFIX foaf: <http://xmlns.com/foaf/0.1/>"+
				"PREFIX acct: <http://purl.org/edg/accounting#>"+
				"PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"+
//				 "SELECT ?label ?accountName ?type ?name where { "+
				 "SELECT ?label ?name ?type  where { "+
//				 " ?label a rdfs:label"+
				 "?x rdfs:label ?label ."+
				 " ?x foaf:name ?name ."+
//				 " ?x a ?type ."+
				 " ?x rdf:type ?type . "+
//				 " ?x acct:Supplier ?name . "+
						 "}";

		String queryString1 = "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> " +
				"PREFIX foaf: <http://xmlns.com/foaf/0.1/>"+
				"PREFIX acct: <http://purl.org/edg/accounting#>"+
				"PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"+
				 "SELECT ?name where { "+
				 " ?x rdf:type acct:Supplier . "+
				 " ?x acct:Supplier ?name . "+
//				 " ?x acct:* ?name . "+
						 "}";
		
		
		QueryExecution qexec1 = QueryExecutionFactory.sparqlService("http://localhost:9090/Agentservices/query",queryString);
		ResultSet rs1 = qexec1.execSelect() ;
        ResultSetFormatter.out(rs1) ;

	
	}
	private void queryAgentList() {

		String queryString = "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> " +
				"PREFIX foaf: <http://xmlns.com/foaf/0.1/>"+
				"PREFIX acct: <http://purl.org/edg/accounting#>"+
				"PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"+
				 "SELECT ?label ?name ?type ?url ?acctNum ?branchNum ?instNum where { "+
				 "?x rdfs:label ?label ."+
				 " ?x foaf:name ?name ."+
				 " ?x acct:* ?url . "+
				 " ?x rdf:type ?type . "+
				 " ?x acct:financialAccountNumber ?acctNum . "+
				 " ?x acct:financialBranchNumber ?branchNum . "+
				 " ?x acct:financialInstitutionNumber ?instNum . "+
//				 " ?person foaf:name ?x ."+
				 "FILTER(?acctNum != \"\")"+	//filter
				 "}";
		

        query1(url+":"+port+"/Agentservices/query",queryString) ;

	
	}
	private void queryPaymentList() {

		String queryString = "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> " +
				"PREFIX acct: <http://purl.org/edg/accounting#>"+
				 "SELECT ?label ?madeon ?madeto ?amount  where { "+
				 "?x rdfs:label ?label ."+
				 " ?x acct:madeOn ?madeon . "+
				 " ?x acct:madeTo ?madeto . "+
				 " ?x acct:netAmount ?amount . "+
//				 "FILTER contains(?label, \"2018-05-15\")"+	//filter
				 "}";
		

		String queryString1 = "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> " +
				"PREFIX foaf: <http://xmlns.com/foaf/0.1/>"+
				"PREFIX acct: <http://purl.org/edg/accounting#>"+
				"PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"+
				 "SELECT ?label where { "+
				 "?x rdfs:label ?label ."+
//				 " ?x acct:* ?name . "+
						 "}";
		
		
		QueryExecution qexec1 = QueryExecutionFactory.sparqlService("http://172.31.11.8:9090/Agentservices/query",queryString);
		ResultSet rs1 = qexec1.execSelect() ;
        ResultSetFormatter.out(rs1) ;
		
		
		
//        query1(url+":"+port+"/Agentservices/query",queryString) ;

	
	}
	
	private void queryPaymentInfoList() {

//		String queryString = "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> " +
//				"PREFIX foaf: <http://xmlns.com/foaf/0.1/>"+
//				"PREFIX acct: <http://purl.org/edg/accounting#>"+
//				"PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"+
////				 "SELECT ?label ?madeon ?madeto ?amount //*?acctNum ?branchNum ?instNum*//  where { "+
//				 "SELECT ?label ?madeon ?madeto ?amount   where { "+
//				 "?x rdfs:label ?label ."+
//				 " ?x acct:madeOn ?madeon . "+
//				 " ?x acct:madeTo ?madeto . "+
//				 " ?x acct:netAmount ?amount . "+
////				 " ?x rdf:type ?type . "+
////				 " ?x acct:financialAccountNumber ?acctNum . "+
////				 " ?x acct:financialBranchNumber ?branchNum . "+
////				 " ?x acct:financialInstitutionNumber ?instNum . "+
//				 "FILTER contains(?label, \"2018-05-15\")"+	//filter
////				 "FILTER(?acctNum != \"\")"+	//filter
//				 "}";
		
		String queryString = "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> " +
				"PREFIX foaf: <http://xmlns.com/foaf/0.1/>"+
				"PREFIX acct: <http://purl.org/edg/accounting#>"+
				"PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"+
				 "SELECT * "+
				 " where { "+
				 "?x rdfs:label ?label ."+
				 " ?x acct:madeOn ?madeon . "+
				 " ?x acct:madeTo ?madeto . "+
				 " ?x acct:netAmount ?amount . "+
//				 " <http://data.metaflow.ca/id/supplier/zoh-1256987000000098233> rdfs:label ?label1 ."+
//				 " acct:madeTo rdfs:label ?label1 ."+
				 " FILTER contains(?label, \"2018-06-30\")"+	//filter
				 "}";
		String queryString11 = "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> " +
				"PREFIX foaf: <http://xmlns.com/foaf/0.1/>"+
				"PREFIX acct: <http://purl.org/edg/accounting#>"+
				"PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"+
				 "SELECT ?label ?name ?type ?url ?acctNum ?branchNum ?instNum where { "+
				 "?x rdfs:label ?label ."+
				 " ?x foaf:name ?name ."+
				 " ?x acct:* ?url . "+
				 " ?x rdf:type ?type . "+
				 " ?x acct:financialAccountNumber ?acctNum . "+
				 " ?x acct:financialBranchNumber ?branchNum . "+
				 " ?x acct:financialInstitutionNumber ?instNum . "+
//				 " ?person foaf:name ?x ."+
				 "FILTER(?acctNum != \"\")"+	//filter
//					" 		FILTER contains(?url, \"<http://data.metaflow.ca/id/supplier/zoh-1256987000000125107>\")"+	//filter
				" 		FILTER contains(?acctNum, \"5006267\")"+	//filter
				 "}";

		String queryString1 = "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> " +
				"PREFIX foaf: <http://xmlns.com/foaf/0.1/>"+
				"PREFIX acct: <http://purl.org/edg/accounting#>"+
				"PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"+
				"SELECT  * "+
				" WHERE   { <http://data.metaflow.ca/id/supplier/zoh-1256987000000098233> rdfs:label ?label ."+
				" <http://data.metaflow.ca/id/payment/zoh-1256987000000349069> rdfs:label ?label1 }";
		
		String queryString2 = "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> " +
				"PREFIX foaf: <http://xmlns.com/foaf/0.1/>"+
				"PREFIX acct: <http://purl.org/edg/accounting#>"+
				"PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"+
				"SELECT  ?label ?label1 "+
				" WHERE   { {				 "+
				"?x rdfs:label ?label ."+
//				 " ?x acct:madeOn ?madeon . "+
//				 " ?x acct:madeTo ?madeto . "+
//				 " ?x acct:netAmount ?amount . "+
				 " FILTER contains(?label, \"2018-05-15\")"+	//filter
				 "} union { "+
				" ?x rdfs:label ?label1 ."+
//				 " ?x foaf:name ?name ."+
//				 " ?x acct:* ?url . "+
//				 " ?x rdf:type ?type . "+
//				 " ?x acct:financialAccountNumber ?acctNum . "+
//				 " ?x acct:financialBranchNumber ?branchNum . "+
//				 " ?x acct:financialInstitutionNumber ?instNum . "+
//				 "FILTER(?acctNum != \"\")}"+	//filter
				 " }"+
//				 "FILTER(?madeon = \"<http://data.metaflow.ca/id/day/zoh-2018-05-15>\")"+
				 "}";
		
		String queryString3 = "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> " +
				"PREFIX foaf: <http://xmlns.com/foaf/0.1/>"+
				"PREFIX acct: <http://purl.org/edg/accounting#>"+
				"PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"+
				"select * where "+
				"{"+
				" {"+
				"   SELECT  ?label ?madeon ?madeto ?amount "+
				"   WHERE   {				 "+
				"     ?x rdfs:label ?label ."+
				"     ?x acct:madeOn ?madeon . "+
				"     ?x acct:madeTo ?madeto . "+
				"     ?x acct:netAmount ?amount . "+
				"     FILTER contains(?label, \"2018-06-30\")"+	//filter
				"   } "+
/*				 "}.  { "+
					"SELECT  ?label1 "+
					" WHERE   {				 "+
				" ?x rdfs:label ?label1 ."+
//				 " ?x foaf:name ?name ."+
//				 " ?x acct:* ?url . "+
//				 " ?x rdf:type ?type . "+
//				 " ?x acct:financialAccountNumber ?acctNum . "+
//				 " ?x acct:financialBranchNumber ?branchNum . "+
//				 " ?x acct:financialInstitutionNumber ?instNum . "+
//				 "FILTER(?acctNum != \"\")}"+	//filter
				 " } "+
//				 "FILTER(?madeon = \"<http://data.metaflow.ca/id/day/zoh-2018-05-15>\")"+
*/
				"  }  . "+
				"  {"+
				"	 SELECT  ?label2 ?url "+
				" 	 WHERE   {				 "+
				"		?x rdfs:label ?label2 ."+
//				" 		?x foaf:name ?name. "+
				" 		?x acct:* ?url . "+
				" 		?x acct:financialAccountNumber ?acctNum . "+
				" 		FILTER(?acctNum != \"\")"+	//filter
				" 		FILTER contains(?url, \"zoh-1256987000000125107\")"+	//filter
				"	} "+
				" }  . "+
//				"FILTER(?madeto =\"<http://data.metaflow.ca/id/supplier/zoh-1256987000000095141>\")"+
//				"FILTER contains(?madeto, \"zoh-1256987000000095141\")"+	//filter
				"}";

		String queryString12 = "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> " +
				"PREFIX foaf: <http://xmlns.com/foaf/0.1/>"+
				"PREFIX acct: <http://purl.org/edg/accounting#>"+
				"PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"+
				 "SELECT * where { "+
				 "?x rdfs:label ?label ."+
				 " ?x foaf:name ?name ."+
				 " ?x acct:about ?resource . "+
				 " ?x acct:* ?url . "+
				 " ?x rdf:type ?type . "+
				 " ?x acct:financialAccountNumber ?acctNum . "+
				 " ?x acct:financialBranchNumber ?branchNum . "+
				 " ?x acct:financialInstitutionNumber ?instNum . "+
				 "FILTER(?acctNum != \"\")"+	//filter
//					" 		FILTER contains(?url, \"<http://data.metaflow.ca/id/supplier/zoh-1256987000000125107>\")"+	//filter
				" 		FILTER contains(?acctNum, \"5006267\")"+	//filter
				 "}";

		String queryString13 = "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> " +
				"PREFIX foaf: <http://xmlns.com/foaf/0.1/>"+
				"PREFIX acct: <http://purl.org/edg/accounting#>"+
				"PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"+
				"select * from NAMED <http://data.metaflow.ca/id/supplier/zoh-1256987000000125107>"+
				 "where {?x rdfs:label ?label ."+
				 " ?x foaf:name ?name ."+
//				 " ?x acct:* ?url . "+
				"}";

		String queryString15 = "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> " +
				"PREFIX foaf: <http://xmlns.com/foaf/0.1/>"+
				"PREFIX acct: <http://purl.org/edg/accounting#>"+
				"PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"+
				 "SELECT * "+
				 " where { "+
				 "?x rdfs:label ?label ."+
				 " ?x acct:madeOn ?madeon . "+
				 " ?x acct:madeTo ?madeto . "+
				 " ?x acct:netAmount ?amount . "+
				 " ?madeto foaf:name ?name ."+
				 " ?madeto acct:financialAccountNumber ?acctNum . "+
				 " ?madeto acct:financialBranchNumber ?branchNum . "+
				 " ?madeto acct:financialInstitutionNumber ?instNum . "+
				 " FILTER contains(?label, \"2018-06-30\")"+	//filter
				 "}";

		query1(url+":"+port+"/Agentservices/query",queryString15) ;

	
	}
	public List<QuerySolution> ListPaymentInfo(String dueDate) {

		String queryString = "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> " +
				"PREFIX foaf: <http://xmlns.com/foaf/0.1/>"+
				"PREFIX acct: <http://purl.org/edg/accounting#>"+
				"PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"+
				 "SELECT * "+
				 " where { "+
				 "?x rdfs:label ?label ."+
				 " ?x acct:madeOn ?madeon . "+
				 " ?x acct:madeTo ?madeto . "+
				 " ?x acct:netAmount ?amount . "+
				 " ?madeto foaf:name ?name ."+
				 " ?madeto acct:financialAccountNumber ?acctNum . "+
				 " ?madeto acct:financialBranchNumber ?branchNum . "+
				 " ?madeto acct:financialInstitutionNumber ?instNum . "+
				 " FILTER contains(?label, \""+dueDate+"\")"+	//filter
				 "}";

		List<QuerySolution> list = query2(url+":"+port+"/Agentservices/query",queryString) ;
//		query1(url+":"+port+"/Agentservices/query",queryString) ;

		return list;
	
	}
	
    /*package*/ 
	static void query1(String URL, String query) {
		QueryExecution qexec = QueryExecutionFactory.sparqlService(URL,query);
		ResultSet rs = qexec.execSelect() ;
		
		ResultSetFormatter.outputAsJSON(rs);
//        ResultSetFormatter.out(rs) ;
    }

	static List<QuerySolution> query2(String URL, String query) {
		QueryExecution qexec = QueryExecutionFactory.sparqlService(URL,query);
		ResultSet rs = qexec.execSelect() ;
		
		return ResultSetFormatter.toList(rs);
    }
	
    /*package*/ 
	static void query(String URL, String query, Consumer<QueryExecution> body) {
        try (QueryExecution qExec = QueryExecutionFactory.sparqlService(URL, query) ) {
            body.accept(qExec);
        }
    }
	
}
