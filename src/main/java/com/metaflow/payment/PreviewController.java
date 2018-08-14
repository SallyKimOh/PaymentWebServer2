package com.metaflow.payment;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.metaflow.payment.base.FileMgr;
import com.metaflow.payment.base.VendorMgr;
import com.metaflow.payment.comm.PropUtil;
import com.metaflow.payment.model.HomeInfo;
import com.metaflow.payment.model.Common.FileInfo;
import com.metaflow.payment.model.Common.JsonResult;
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
//		List<VendorInfo> billList = vMgr.createVendorList(date);
		List<VendorInfo> billList = vMgr.createVendorInfoList(date);

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
	
	
	@RequestMapping(value = "/modBatchNo", method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public JsonResult modifyBatchNumber(@Valid @RequestParam("batchNo") int no,@Valid @RequestParam("fileName") String filename){
		System.out.println("changing batch number starts");
		System.out.println("no==>"+no);
		System.out.println("file==>"+filename);
        try
        {
	
    		String path = new PropUtil().getPropValue().getProperty("FILE_PATH");
//            filename = "2018-06-15_20180612101630.txt";
//            filename = "2018-06-15_20180614150027.txt";
			String fileFullName = path+File.separator+filename;
			
			System.out.println("path:"+fileFullName);
//			File file = new File(fileFullName);
//			System.out.println("abc==:"+file.getAbsolutePath());
			
			BufferedReader reader = new BufferedReader(new FileReader(fileFullName));
//			BufferedReader reader = new BufferedReader(new FileReader(file.getAbsolutePath()));
	        String line = "", oldtext = "";
	        boolean firstLine = true;
	        while((line = reader.readLine()) != null) {
	        	
	        	if (firstLine) {
	        		System.out.println("first line==>"+line);
	        		System.out.println("line==>"+line.substring(29, 33));
	        		
	        		line = line.substring(0,29)+new PropUtil().getMatchValue(String.valueOf(no),4)+line.substring(33,line.length());
	        		System.out.println("chang line==>"+line.substring(0,29)+new PropUtil().getMatchValue(String.valueOf(no),4)+line.substring(33,line.length()));
	        		firstLine = false;
	    			new PropUtil().modifyProperty(String.valueOf(no+1));
	        	}
	        	
	            oldtext += line + "\r\n";
	        }
	        reader.close();
	        String newtext = oldtext.replaceAll("This is test string 20000", "blah blah blah");
	       
	        FileWriter writer = new FileWriter(fileFullName);
	        
	        writer.write(newtext);
	        writer.close();
        
        }
        catch (IOException ioe) {
            ioe.printStackTrace();
        }       
        
        
        
        
				
		JsonResult respone = new JsonResult();
		respone.setValidated(true);
		respone.setMsg("Batch number changed successfully");
	return respone;
	
	
	
	
//	return ResponseEntity.ok("Okay");
	}	
	
	
}
