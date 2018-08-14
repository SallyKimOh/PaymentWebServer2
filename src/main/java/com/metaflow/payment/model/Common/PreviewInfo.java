package com.metaflow.payment.model.Common;

import java.util.List;

import lombok.Data;

public @Data class PreviewInfo {
	
	private FileInfo fileVo;
	
	private List<FileInfo> fileList;

	public PreviewInfo(FileInfo fileVo, List<FileInfo> fileList) {
		this.fileVo = fileVo;
		this.fileList = fileList;
	}

}
   