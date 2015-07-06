package com.yw.bean;

public class XmlDTO {
	String name;
	String tel;
	String address;
	
	public XmlDTO() {}
	
	// Data ---> XML 파일로 저장하기 위한 생성자.
	public XmlDTO(String name, String tel, String address) {
		
		this.name = name;
		this.tel = tel;
		this.address = address;
		
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
	
	@Override
	public String toString() {
		return "xmlDTO [name=" + name + ", tel=" + tel + ", address=" + address + "]";
	}
	
	
}
