package com.yw.bean;


import org.hibernate.validator.constraints.NotEmpty;

public class Word {
	
	int objectId;
	
	@NotEmpty(message = "no empty plz")
	String wordName;
	
	@NotEmpty(message = "no empty plz")
	/*@Pattern(regexp="/^[A-Za-z0-9+]*$/", message="nono")*/
	String wordEnName;
	
	@NotEmpty
	String wordDefine;
	
	@NotEmpty(message = "no empty plz")
	String writer;
	
	public Word(){
		
	}

	public Word(int objectId, String wordName, String wordEnName,
			String wordDefine, String writer) {
		this.objectId = objectId;
		this.wordName = wordName;
		this.wordEnName = wordEnName;
		this.wordDefine = wordDefine;
		this.writer = writer;
	}

	public int getObjectId() {
		return objectId;
	}

	public void setObjectId(int objectId) {
		this.objectId = objectId;
	}

	public String getWordName() {
		return wordName;
	}

	public void setWordName(String wordName) {
		this.wordName = wordName;
	}

	public String getWordEnName() {
		return wordEnName;
	}

	public void setWordEnName(String wordEnName) {
		this.wordEnName = wordEnName;
	}

	public String getWordDefine() {
		return wordDefine;
	}

	public void setWordDefine(String wordDefine) {
		this.wordDefine = wordDefine;
	}

	public String getWriter() {
		return writer;
	}

	public void setWriter(String writer) {
		this.writer = writer;
	}

	@Override
	public String toString() {
		return "Word [objectId=" + objectId + ", wordName=" + wordName
				+ ", wordEnName=" + wordEnName + ", wordDefine=" + wordDefine
				+ ", writer=" + writer + "]";
	}
	
	
	
	
}