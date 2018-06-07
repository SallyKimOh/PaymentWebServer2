package com.metaflow.payment.base;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.metaflow.payment.comm.PropUtil;
import com.metaflow.payment.model.Vendor.VendorInfo;

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
	
	
	public static void main(String[] args)  {
		
		VendorMgr mgr = new VendorMgr();
		List<VendorInfo> list = mgr.createVendorList("2018-05-15");
		
		System.out.println("list ==>"+list);
	}
	

}
