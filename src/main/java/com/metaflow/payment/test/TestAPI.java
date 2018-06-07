package com.metaflow.payment.test;

import java.util.ArrayList;
import java.util.List;

import com.metaflow.payment.base.FileMgr;
import com.metaflow.payment.base.VendorMgr;
import com.metaflow.payment.comm.PropUtil;
import com.metaflow.payment.model.Common.FileInfo;
import com.metaflow.payment.model.Vendor.VendorInfo;

public class TestAPI {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		String dueDate = "2018-05-15";
		
		VendorMgr vMgr = new VendorMgr();
		FileMgr fMgr = new FileMgr();
		List<String> list = new ArrayList<>();
		FileInfo vo;
		
		//============== Vendor bill payment ============//
		List<VendorInfo> billList = vMgr.createVendorList(dueDate);

		List<FileInfo> billFileList = new ArrayList<>();
		FileInfo bVo = new FileInfo();

		billFileList = fMgr.getItemList(billList);
		
		System.out.println("size==>"+billFileList.size());
		bVo = fMgr.getBatchHeadTrail("A",dueDate.replace("-", ""));
		bVo.setTrailerBatchCount(new PropUtil().getMatchValue(String.valueOf(billFileList.size()),6));

		if (!billList.isEmpty()) {
			String strBill = fMgr.makeFileUsingBatch(bVo, billFileList);
			list.add(strBill);
		}
		
		System.out.println("listCntL:"+list.size());
		vo = fMgr.getCIBCFileHeader(dueDate);
		System.out.println(vo);
		String fileName = fMgr.makeCIBCFile(vo, list, dueDate);
		
		System.out.println("fileName:"+fileName);
		

	}

	
}
