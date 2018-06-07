package com.metaflow.payment.model.Vendor;

import com.metaflow.payment.model.Common.BankInfo;

import lombok.Data;

public @Data class VendorInfo extends BankInfo{

	private String vendorID;
	private String vendorName;
}

  