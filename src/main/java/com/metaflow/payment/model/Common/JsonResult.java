package com.metaflow.payment.model.Common;

import java.util.Map;

import lombok.Data;


public @Data class JsonResult {
	   private boolean validated;
	   private String msg;
	   private Map<String, String> errorMessages;
	   

}
 