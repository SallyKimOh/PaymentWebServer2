package com.metaflow.payment.model.BillPayment;

import com.metaflow.payment.model.Vendor.VendorInfo;

import lombok.Data;

public @Data class BillPaymentInfo extends VendorInfo{
	
	private String billId;
	private String transType;
	
}  