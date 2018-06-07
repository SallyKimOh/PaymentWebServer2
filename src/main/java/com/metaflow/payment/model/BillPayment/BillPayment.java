package com.metaflow.payment.model.BillPayment;

import org.springframework.hateoas.ResourceSupport;

import lombok.Data;

public @Data class BillPayment extends ResourceSupport{

	private String billId;
	private String transType;
	
	
}
  