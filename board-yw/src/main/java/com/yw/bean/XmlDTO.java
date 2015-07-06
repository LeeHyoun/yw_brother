package com.yw.bean;



/**
 * <pre>
 * com.yw.bean
 *   |_ XmlDTO.java
 * </pre>
 * 
 * Desc : 
 * @Company : DataStreams
 * @Author  : HLEE
 * @Date    : 2015. 7. 3. 오전 4:52:24
 * @Version : 작성자 |   작성일   | 작성시간
 * 				HLEE  | 2015.07.03 | 04:52:24
 */
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
