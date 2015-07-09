package com.yw.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.yw.bean.FileDTO;



/**
 * <pre>
 * com.yw.controller
 *   |_ FileController.java
 * </pre>
 * 
 * Desc : File Upload, Download
 * @Company : DataStreams
 * @Author  : HLEE
 * @Date    : 2015. 7. 7. 오후 3:51:41
 * @Version : 
 */
@Controller
public class FileController {
	
	private FileOutputStream fos = null;

	/**
	 * Desc : File Upload
	 * @Method Name : writeFile
	 * @param file
	 * @param path
	 * @param fileName
	 */
	@RequestMapping(value="/file", method=RequestMethod.POST)
	public void writeFile(@RequestParam("file") MultipartFile file,
							HttpSession session) throws Exception{
		
		FileOutputStream fos = null;
				
		String uploadPath = session.getServletContext().getRealPath("/upload/");
		
		/*System.out.println("session.getServletContext() << : " + session.getServletContext());
        System.out.println("UPLOAD_PATH << : " + uploadPath);*/
		
		//실제 디플로이되는 폴더의 root path를 따온다
		uploadPath = uploadPath.substring(0,uploadPath.indexOf(".metadata"));
        
		/*System.out.println("UPLOAD_PATH >> : " + uploadPath);*/
        
        String path = uploadPath+"board-yw/upload/"+file.getOriginalFilename();
        
        fos = new FileOutputStream(path); 
        
        FileCopyUtils.copy(file.getInputStream(), fos);
        //upload 폴더안에 등록하겠다는 말
		
	}// wirteFile() end;
	
	
	
	/**
	 * Desc : fileForm.jsp
	 * @Method Name : fileForm
	 * @return
	 */
	@RequestMapping(value="/fileUploadForm", method=RequestMethod.GET)
    public String fileForm() {
        return "fileForm";
    }
    
	
    /**
     * Desc : fileUpload 수행
     * @Method Name : fileSubmit
     * @param dto
     * @return
     */
    @RequestMapping(value="/fileUpload", method= { RequestMethod.POST } )
    public String fileSubmit(@ModelAttribute FileDTO dto) {
        System.out.println("fileUpload Start");
    	
    	MultipartFile uploadfile = dto.getUploadFile();
    	File file = null;
    	String fileName = "";
    	
        if (uploadfile != null) {
            
        	fileName = uploadfile.getOriginalFilename();
            dto.setFileName(fileName);
            
            try {
                // 1. FileOutputStream 사용
                // byte[] fileData = file.getBytes();
                // FileOutputStream output = new FileOutputStream("C:/images/" + fileName);
                // output.write(fileData);
                 
                // 2. File 사용
                file = new File("C:/dev/temp/" + fileName);
                uploadfile.transferTo(file);
                System.out.println("dto : " + dto.toString());
                
            } catch (IOException e) {
                e.printStackTrace();
            } // try - catch
        } // if
        // 데이터 베이스 처리를 현재 위치에서 처리
        return "/";
    }
}
