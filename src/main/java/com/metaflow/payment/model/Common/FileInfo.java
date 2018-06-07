package com.metaflow.payment.model.Common;

import lombok.Data;

public @Data class FileInfo {

	private String recodeTypeID;				//Record Type ID
	private String recvDataCentre;				//Receiving Data centre
	private String originatorNum;				//Originator number
	private String fileCreateDt;				//File Creation Date
	private String fileCreateNum;				//File Creation Number
	private String institutionID;				//Bank no, Inst, ID and Account
	private String branchNo;					//Branch No. 
	private String accountNo;					//Account No
	private String originatorName;				//Originator's Name
	
	private String batHeaderRecodeTypeID;		//Batch header Record Type ID
	private String batHeaderTransactionType;	//Batch header Transaction Type
	private String batHeaderDescStatement;		//Batch header Descriptive Statement
	private String batHeaderDueDate;			//Batch Due Date
	
	private String detailRecordTypeID;			//Detail Record Type ID
	private String serviceIndicatorType;		//Detail Service indicator (C: Credit indicator/ D: Debit indicator)
	private String institutionalID;			//Detail Institutional ID
	private String institutionalBranchNo;		//Detail Institutional Branch No.
	private String detailAccountNo;			//Payee/Payor's Account No.
	private String amount;						//Amount(Cdn$)
	private String crossReferenceNo;			//Cross Reference No
	private String payeeName;					//Payee's Name 

	private String batTrailerRecodeTypeID;		//Batch Trailer Record Type ID
	private String batTrailerTransactionType;	//Batch Trailer Transaction Type
	private String batTrailerEntryCount;		//Batch Trailer Batch Entry Count
	private String batTrailerHashCount;		//Batch Trailer Hash Total
	private String batTrailerEntryDollarTotal;	//Batch Trailer Entry Dollar Total
	
	private String trailerRecordTypeID;		//End of File Trailer Record Record Type ID
	private String trailerBatchCount;			//End of File Trailer Batch Count 
	private String trailerDetailCount;			//End of File Trailer Detail Count
	
	private String endRecordTypeID;
	private String endBatchCount;
	private String endDetailCount;
	
	private String filter;
	 
	
	
	
}
