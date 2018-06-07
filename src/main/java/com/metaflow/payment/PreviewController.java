package com.metaflow.payment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.metaflow.payment.base.FileMgr;
import com.metaflow.payment.base.VendorMgr;
import com.metaflow.payment.comm.PropUtil;
import com.metaflow.payment.model.HomeInfo;
import com.metaflow.payment.model.Common.FileInfo;
import com.metaflow.payment.model.Vendor.VendorInfo;

@Controller
public class PreviewController {


	@RequestMapping(value = "/preview",method = RequestMethod.POST)
	public String index(@Valid @ModelAttribute("homeInfo")HomeInfo info, BindingResult result, ModelMap model) {
        if (result.hasErrors()) {
            return "error";
        }
    	
		String date = info.getDate();
 
	    System.out.println("validateDt==>"+date);
	    	

		VendorMgr vMgr = new VendorMgr();
		FileMgr fMgr = new FileMgr();
		List<String> list = new ArrayList<>();
		FileInfo vo;
			
		//============== Vendor bill payment ============//
		List<VendorInfo> billList = vMgr.createVendorList(date);

		List<FileInfo> billFileList = new ArrayList<>();
		FileInfo bVo = new FileInfo();

		billFileList = fMgr.getItemList(billList);
			
		System.out.println("size==>"+billFileList.size());
		bVo = fMgr.getBatchHeadTrail("A",date.replace("-", ""));
		bVo.setTrailerBatchCount(new PropUtil().getMatchValue(String.valueOf(billFileList.size()),6));

		if (!billList.isEmpty()) {
			String strBill = fMgr.makeFileUsingBatch(bVo, billFileList);
			list.add(strBill);
		}
			
		System.out.println("listCntL:"+list.size());
		vo = fMgr.getCIBCFileHeader(date);
		System.out.println(vo);
		HashMap hMap = fMgr.makeCIBCFileArr(vo, list, date);
		String fileName = (String) hMap.get("fileName");
		System.out.println("fileName:"+fileName);
			
		model.addAttribute("fileName", fileName);
		model.addAttribute("fileAbs", (String) hMap.get("fileAbs"));
		
		
		return "preview2";
	}	
	
		
	private static final String APPLICATION_TXT = "application/txt";
	
	@RequestMapping(value = "/download", method = RequestMethod.GET, produces = APPLICATION_TXT)
	public void downloadA(HttpServletResponse response,@RequestParam("filePath") String filePath) throws IOException {
		
	    File file = getFile(filePath);
	    InputStream in = new FileInputStream(file);
	    response.setContentType(APPLICATION_TXT);
	    response.setHeader("Content-Disposition", "attachment; filename=" + file.getName());
	    response.setHeader("Content-Length", String.valueOf(file.length()));
	    FileCopyUtils.copy(in, response.getOutputStream());
	}
	
	private File getFile(String filePath) throws FileNotFoundException {
        File file = new File(filePath);
        if (!file.exists()){
            throw new FileNotFoundException("file with path: " + filePath + " was not found.");
        }
        return file;
    }
	
}
