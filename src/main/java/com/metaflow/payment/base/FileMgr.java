package com.metaflow.payment.base;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.metaflow.payment.comm.PropUtil;
import com.metaflow.payment.model.Common.FileInfo;
import com.metaflow.payment.model.Vendor.VendorInfo;

public class FileMgr {

	String content = ""; 
	String contentList = "";
	int num = 0;
	int total = 0;
 
 
	public List<FileInfo> getItemList(List<VendorInfo> infoList) {
		
		List<FileInfo> list = new ArrayList<FileInfo>();
		total = 0;
		int batchNum = Integer.parseInt(new PropUtil().getPropValue().getProperty("BATCH_NUM".toString()))+1;
		
		System.out.println("FileMgr getItemList:"+infoList.size());

		infoList.forEach(item -> {
			
			String[] amt = new String[2]; 
			
			if (String.valueOf(item.getAmount()).contains(".")) {
				amt[0] = String.valueOf(item.getAmount()).substring(0,String.valueOf(item.getAmount()).indexOf("."));
				amt[1] = String.valueOf(item.getAmount()).substring(String.valueOf(item.getAmount()).indexOf(".")+1,String.valueOf(item.getAmount()).length());
			}
			else {
				amt[0] = String.valueOf(item.getAmount());
				amt[1] = "00";
			}
			String amount = amt[0]+new PropUtil().getRMatchValue(amt[1],2);
			
			
			
			
			
			FileInfo vo = new FileInfo();

			vo.setDetailRecordTypeID("6");	//Detail Record Type ID fixed
			vo.setServiceIndicatorType("C");	//C:credit indictor  D:Debit Indicator
			vo.setInstitutionalID(new PropUtil().getMatchValue(item.getFinancialNumber(),4));
			vo.setInstitutionalBranchNo(new PropUtil().getMatchValue(item.getBranchNumber(),5));
			vo.setDetailAccountNo(new PropUtil().getMatchNullValue(item.getAccountNumber(),12));
			vo.setAmount(new PropUtil().getMatchValue(amount,10));		//28  
			vo.setCrossReferenceNo(new PropUtil().getMatchNullValue("DP"+new PropUtil().getMatchValue(String.valueOf(batchNum),4)+"-"+new PropUtil().getMatchValue(String.valueOf(num),2), 13));	//Cheque Number
			vo.setPayeeName(new PropUtil().getMatchNullValue(item.getVendorName(), 22));
			
			if (item.getFinancialNumber()!= null) {
				num++;
				total += (int)Double.parseDouble(amount);
				list.add(vo);
			}			
			
		});
			
	
		return list;
		
	}
	

	
	public FileInfo getBatchHeadTrail(String type, String dueDate) {
		
		FileInfo vo = new FileInfo();

		vo.setBatHeaderRecodeTypeID("5");
		vo.setBatHeaderTransactionType(new PropUtil().getMatchValue(new PropUtil().getPropValue().getProperty("HEADER_TRANSACTION_TYPE_"+type),3));
		vo.setBatHeaderDescStatement(new PropUtil().getMatchNullValue(new PropUtil().getPropValue().getProperty("HEADER_DESC_"+type),10));
		vo.setBatHeaderDueDate(dueDate.substring(2, 8));		
		
		vo.setTrailerRecordTypeID("7");
		vo.setBatTrailerTransactionType(new PropUtil().getMatchValue(new PropUtil().getPropValue().getProperty("HEADER_TRANSACTION_TYPE_"+type),3));
		vo.setTrailerBatchCount(new PropUtil().getMatchValue(String.valueOf(num),6));
		vo.setBatTrailerHashCount(new PropUtil().getMatchValue("",10));
		vo.setBatTrailerEntryDollarTotal(new PropUtil().getMatchValue(String.valueOf(total),12));
		
		return vo;
		
	}

	public FileInfo getCIBCFileHeader(String dueDate) {
		
		FileInfo vo = new FileInfo();

		Date date = new Date();   
		String today= new SimpleDateFormat("yyMMdd").format(date);
				  
//		String batchNum = String.valueOf(Integer.parseInt(new PropUtil().getPropValue().getProperty("BATCH_NUM"))+1);
		String batchNum = String.valueOf(new PropUtil().getPropValue().getProperty("BATCH_NUM"));
		vo.setRecodeTypeID("1");
		vo.setRecvDataCentre(new PropUtil().getMatchValue(new PropUtil().getPropValue().getProperty("RECEIVING_DATA_CENTER"),5));
		vo.setOriginatorNum(new PropUtil().getMatchValue(new PropUtil().getPropValue().getProperty("ORIGINATOR_NUMBER"),10));
		vo.setFileCreateDt(today);
		vo.setFileCreateNum(new PropUtil().getMatchValue(batchNum,4));		//previous file number + 10????
		vo.setInstitutionID(new PropUtil().getMatchValue(new PropUtil().getPropValue().getProperty("INST_ID"),4));
		vo.setBranchNo(new PropUtil().getMatchValue(new PropUtil().getPropValue().getProperty("BRANCH_NO"),5));
		vo.setAccountNo(new PropUtil().getMatchNullValue(new PropUtil().getPropValue().getProperty("ACCOUNT_NO"),12));   //length  12
		vo.setOriginatorName(new PropUtil().getMatchNullValue(new PropUtil().getPropValue().getProperty("ORIGINATOR_NAME"),15));  // length 15
		
		vo.setEndRecordTypeID("9");
		vo.setEndBatchCount(new PropUtil().getMatchValue("1",6));
		vo.setEndDetailCount(new PropUtil().getMatchValue(String.valueOf(num),6));

		return vo;
		
	}
	
	


	
	/**
	 * when file Create, it includes both like paycheck and bill payment 
	 * @param vo
	 * @param list
	 * @param billDate
	 * @return
	 */
	String strList;
	public String makeFileUsingBatch(FileInfo vo, List<FileInfo> list) {
		
		String strContent = "";
		strList="";
		vo.setBatTrailerEntryCount(String.valueOf(list.size()));
		System.out.println("batchentitycount:"+list.size());
		try {
			list.forEach(item-> {
				strList +=item.getDetailRecordTypeID()+item.getServiceIndicatorType()+new PropUtil().getMatchNullValue("", 1)
						+item.getInstitutionalID()+item.getInstitutionalBranchNo()+item.getDetailAccountNo()+new PropUtil().getMatchNullValue("", 5)
						+item.getAmount()+item.getCrossReferenceNo()+item.getPayeeName()+new PropUtil().getMatchNullValue("", 6)+"\n";
			});
			
			strContent = vo.getBatHeaderRecodeTypeID()+new PropUtil().getMatchNullValue("", 46)
					+vo.getBatHeaderTransactionType()+vo.getBatHeaderDescStatement()+vo.getBatHeaderDueDate()+new PropUtil().getMatchNullValue("", 14)+"\n"
					+strList
//					+vo.getTrailerRecordTypeID()+vo.getBatTrailerTransactionType()+vo.getTrailerBatchCount()+vo.getBatTrailerHashCount()+new PropUtil().getMatchNullValue("", 20)
					+vo.getTrailerRecordTypeID()+vo.getBatTrailerTransactionType()+vo.getTrailerBatchCount()+vo.getBatTrailerHashCount()+new PropUtil().getMatchNullValue("", 20)
					+vo.getBatTrailerEntryDollarTotal()+new PropUtil().getMatchNullValue("", 28)+"\n"
					;

			System.out.println("Create BatchInfo");			
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return strContent;
		
	}

	/**
	 * when file Create, it includes both like paycheck and bill payment 
	 * @param vo
	 * @param list
	 * @param billDate
	 * @return
	 */ 
	public String makeCIBCFile(FileInfo vo, List<String> list, String billDate) {
		Date date = new Date();
		String today= new SimpleDateFormat("yyyyMMddHHmmss").format(date);

		System.out.println(date);;
		
		String path = new PropUtil().getPropValue().getProperty("FILE_PATH");
		File dir = new File(path);
		String fileName = billDate+"_"+today+".txt";
//		String fileFullName = new PropUtil().getPropValue().getProperty("FILE_PATH")+billDate+"_"+today+".txt";
		String fileFullName = path+File.separator+fileName;
//		File f1 = new File(path+File.separator+fileName);

		File file = new File(fileFullName);
//		System.out.println(file.getParentFile());
//		file.getParentFile().mkdirs();
		
		if (!dir.exists()) { 
			if (dir.mkdir()) {
				System.out.println("Directory created");
			} else {
				System.out.println("Directory creating failed");
			}
		}
		
		BufferedWriter bw = null;
		FileWriter fw = null;
		
		try {
			vo.setEndBatchCount(new PropUtil().getMatchValue(String.valueOf(list.size()),6));
			list.forEach(item-> {
				contentList +=item;
				System.out.println("contentlist:"+item);
			});
			
			content = vo.getRecodeTypeID()+new PropUtil().getMatchNullValue("", 2)
					+vo.getRecvDataCentre()+new PropUtil().getMatchNullValue("", 5)
					+vo.getOriginatorNum()+vo.getFileCreateDt()+vo.getFileCreateNum()+new PropUtil().getMatchNullValue("", 1)
					+vo.getInstitutionID()+vo.getBranchNo()+vo.getAccountNo()+new PropUtil().getMatchNullValue("", 2)
					+vo.getOriginatorName()+" CAD"+new PropUtil().getMatchNullValue("", 4)+"\n"
					+contentList
					+vo.getEndRecordTypeID()+vo.getEndBatchCount()+vo.getEndDetailCount()+new PropUtil().getMatchNullValue("", 67)
					;
			
				
			fw = new FileWriter(file);
			bw = new BufferedWriter(fw);
			
			bw.write(content);

			System.out.println("Done");			
			String batchNum = String.valueOf(Integer.parseInt(new PropUtil().getPropValue().getProperty("BATCH_NUM"))+1);

			new PropUtil().modifyProperty(batchNum);

			
			boolean isFile = file.isFile();
			System.out.println("isFile="+isFile);
			
			String abs = file.getAbsolutePath();
			System.out.println("abs_path:"+abs);


		} catch (IOException e) {

			e.printStackTrace();

		} finally {

			try {

				if (bw != null)
					bw.close();

				if (fw != null)
					fw.close();

			} catch (IOException ex) {

				ex.printStackTrace();

			}

		}
		
		return fileName;
		
	}

	public HashMap makeCIBCFileArr(FileInfo vo, List<String> list, String billDate) {
		Date date = new Date();
		String today= new SimpleDateFormat("yyyyMMddHHmmss").format(date);

		System.out.println(date);;
		HashMap hMap = new HashMap(); 
		
		String path = new PropUtil().getPropValue().getProperty("FILE_PATH");
		File dir = new File(path);
		String fileName = billDate+"_"+today+".txt";
//		String fileFullName = new PropUtil().getPropValue().getProperty("FILE_PATH")+billDate+"_"+today+".txt";
		String fileFullName = path+File.separator+fileName;
//		File f1 = new File(path+File.separator+fileName);

		File file = new File(fileFullName);
//		System.out.println(file.getParentFile());
//		file.getParentFile().mkdirs();
		
		if (!dir.exists()) { 
			if (dir.mkdir()) {
				System.out.println("Directory created");
			} else {
				System.out.println("Directory creating failed");
			}
		}
		
		BufferedWriter bw = null;
		FileWriter fw = null;
		
		try {
			vo.setEndBatchCount(new PropUtil().getMatchValue(String.valueOf(list.size()),6));
			list.forEach(item-> {
				contentList +=item;
				System.out.println("contentlist:"+item);
			});
			
			content = vo.getRecodeTypeID()+new PropUtil().getMatchNullValue("", 2)
					+vo.getRecvDataCentre()+new PropUtil().getMatchNullValue("", 5)
					+vo.getOriginatorNum()+vo.getFileCreateDt()+vo.getFileCreateNum()+new PropUtil().getMatchNullValue("", 1)
					+vo.getInstitutionID()+vo.getBranchNo()+vo.getAccountNo()+new PropUtil().getMatchNullValue("", 2)
					+vo.getOriginatorName()+" CAD"+new PropUtil().getMatchNullValue("", 4)+"\n"
					+contentList
					+vo.getEndRecordTypeID()+vo.getEndBatchCount()+vo.getEndDetailCount()+new PropUtil().getMatchNullValue("", 67)
					;
			
				
			fw = new FileWriter(file);
			bw = new BufferedWriter(fw);
			
			bw.write(content);

			System.out.println("Done");			
			String batchNum = String.valueOf(Integer.parseInt(new PropUtil().getPropValue().getProperty("BATCH_NUM"))+1);

			new PropUtil().modifyProperty(batchNum);

			
			boolean isFile = file.isFile();
			System.out.println("isFile="+isFile);
			
			String abs = file.getAbsolutePath();
			System.out.println("abs_path:"+abs);
			hMap.put("fileAbs",abs);

		} catch (IOException e) {

			e.printStackTrace();

		} finally {

			try {

				if (bw != null)
					bw.close();

				if (fw != null)
					fw.close();

			} catch (IOException ex) {

				ex.printStackTrace();

			}

		}
		hMap.put("fileName",fileName);
		
		return hMap;
		
	}
	
}
