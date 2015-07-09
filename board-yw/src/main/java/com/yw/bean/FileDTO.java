package com.yw.bean;

import org.springframework.web.multipart.MultipartFile;

public class FileDTO {
	
	private String name;
	private String pwd;
	private String title;
	private String content;
	private String fileName;
	private MultipartFile uploadFile;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public MultipartFile getUploadFile() {
		return uploadFile;
	}
	public void setUploadFile(MultipartFile uploadFile) {
		this.uploadFile = uploadFile;
	}
	
	@Override
	public String toString() {
		return "FileDTO [name=" + name + ", pwd=" + pwd + ", title=" + title
				+ ", content=" + content + ", fileName=" + fileName
				+ ", uploadFile=" + uploadFile + "]";
	}
	
	
}
