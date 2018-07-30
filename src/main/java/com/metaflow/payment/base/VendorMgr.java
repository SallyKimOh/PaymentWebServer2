package com.metaflow.payment.base;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.jena.query.QuerySolution;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.metaflow.payment.comm.PropUtil;
import com.metaflow.payment.model.Vendor.VendorInfo;
import com.metaflow.payment.service.PaymentAgent;

public class VendorMgr {
	
	public List<VendorInfo> createVendorList(String dueDate) {

		List<VendorInfo> list = new ArrayList<>();
		
		PropUtil util = new PropUtil();
		String path = new PropUtil().getPropValue().getProperty("BILLPAYMENT_URL")+"?dueDate="+dueDate;
		BaseMgr mgr = new BaseMgr();
		try {
			String listStr = mgr.getConnectServer(path);
			JSONObject jsonObject = (JSONObject) util.getJsonObjFromStr(listStr).get("_embedded");
			
			if (jsonObject == null) return list;
			
			JSONArray billList = (JSONArray) jsonObject.get("billPayments");
            
            System.out.println(billList);;
            Iterator<JSONObject> iterator1 = billList.iterator();
            while (iterator1.hasNext()) {
            	VendorInfo info = new VendorInfo();
        		JSONObject billObj = iterator1.next();
         		String billPath = new PropUtil().getPropValue().getProperty("BILLPAYMENT_URL")+"/"+(String) billObj.get("billId");

        		String strBillInfo = mgr.getConnectServer(billPath);
        		JSONObject jsonBillInfo = (JSONObject) util.getJsonObjFromStr(strBillInfo);
        		info.setVendorID((String)jsonBillInfo.get("vendorID"));
        		info.setVendorName((String)jsonBillInfo.get("vendorName"));
        		info.setAmount((Double)jsonBillInfo.get("amount"));
        		info.setTransDate((String)jsonBillInfo.get("transDate"));

        		System.out.println("VendorID:"+billObj.get("vendorID"));
        		info.setFinancialNumber((String)jsonBillInfo.get("financialNumber"));
        		info.setBranchNumber((String)jsonBillInfo.get("branchNumber"));
        		info.setAccountNumber((String)jsonBillInfo.get("accountNumber"));
        		
                System.out.println(info);
//                System.out.println(billObj.get("vendor_id"));
                
                list.add(info);
            }           
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			
			System.out.println("error:"+e);
			e.printStackTrace();
		}

		return list;

		
	}
	
	public List<VendorInfo> createVendorInfoList(String dueDate) {

		List<VendorInfo> list = new ArrayList<>();
		
		PropUtil util = new PropUtil();
		String path = new PropUtil().getPropValue().getProperty("BILLPAYMENT_URL")+"?dueDate="+dueDate;

		PaymentAgent agent = new PaymentAgent();
		
        List<QuerySolution> queryList = agent.ListPaymentInfo(dueDate);
		
        System.out.println(queryList);;
		try {

			queryList.forEach(item-> {
				VendorInfo info = new VendorInfo();
				
        		info.setVendorID(item.get("madeto").toString());
        		info.setVendorName(item.get("name").toString());
        		info.setAmount(Double.parseDouble(item.get("amount").toString()));
        		info.setTransDate(dueDate);

//        		System.out.println("VendorID:"+billObj.get("vendorID"));
        		info.setFinancialNumber(item.get("instNum").toString());
        		info.setBranchNumber(item.get("branchNum").toString());
        		info.setAccountNumber(item.get("acctNum").toString());
        		
                System.out.println(info);
//                System.out.println(billObj.get("vendor_id"));
                
                list.add(info);
			});
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			
			System.out.println("error:"+e);
			e.printStackTrace();
		}

		return list;

		
	}
	
	public static void main(String[] args)  {
		
		VendorMgr mgr = new VendorMgr();
//		List<VendorInfo> list = mgr.createVendorList("2018-06-30");
		List<VendorInfo> list1 = mgr.createVendorInfoList("2018-06-30");
//		System.out.println("list ==>"+list);
		System.out.println("list1 ==>"+list1);
	}
	

}
