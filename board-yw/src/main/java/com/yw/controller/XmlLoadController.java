package com.yw.controller;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


@Controller
public class XmlLoadController {

	/**
	 * Desc : String값이 5자 이상이면 그뒤를 ... 으로 대체
	 * @Method Name : stringSlice
	 * @param s : 표현될 문자열
	 * @param l : 표현될 문자열 최대길이
	 * @return
	 */
	public String stringSlice(String s, int l) {
		
		StringBuffer sb = new StringBuffer();
				
		sb.append("");
		
		if (s != null && !s.equals("")) {
						
			if (s.length() > l) {
				sb.append(s.substring(0, l)).append("...");
			}else {
				sb.append(s);
			}
		}
		
		return sb.toString(); // 최대길이를 넘어가면 최대길이 이후는 ... 으로 표현
	}
	
	
	/**
	 * Desc : main
	 * @Method Name : main
	 * @param argv
	 */
	
	@RequestMapping(value="/xmlReader")
	@ResponseBody
	public NodeList xmlReader(@Param(value="file_path") String file_path) {

		File file = null; //io
		DocumentBuilderFactory docBuildFact = null; //xml.parsers
		DocumentBuilder docBuild = null; //xml.parsers
		Document doc = null; //dom
		
		/* person엘리먼트 리스트*/
		NodeList personlist = null;
		
		/* person엘리먼트*/
		Element personElmnt = null;
		
		/* name 태그*/
		NodeList nameList = null;
		Element nameElmnt = null;
		Node name = null;
		
		/* tel 태그*/
		NodeList telList = null;
		Element telElmnt = null;
		Node tel = null;
		
		 /* address 태그*/
		NodeList addressList = null;
		Element addressElmnt = null;
		Node address = null;
		
		/* person Elements 중 하나를 담을 변수*/
		Node personNode = null;
		
		String fileName = "";
		String path = "";
		
		int lastIndex;
		
		try {
			
			lastIndex = file_path.lastIndexOf("\\"); // 맨마지막 "\\" 의 위치
			
			fileName = file_path.substring(lastIndex+1); // 파일명부터 확장자까지.
			path = file_path.substring(0, lastIndex+1); // 파일명 전의 경로
			
			System.out.println("fileName : " + fileName);
			System.out.println("path : " + path);
			
			file = new File(path + fileName);

			/* 목적은 Document객체를 구하는 것이다.
			 * 이 목적을 달성하기 위해 DocumentBuilder가 필요하며,
			 * 이런 builder객체를 DocumentBuilderFactory로 인하여 만들어진다.
			 * Factory Pattern 사용
			 * 1. DocumentBuilderFactory 객체생성
			 * */
			
			docBuildFact = DocumentBuilderFactory.newInstance();  
			/* 여기서 객체생성을 new 연산자로하나, Class.newInstance()로 하나 차이는 없다.
			 하지만 (추상클레스) 인 경우는 .newInstance() 연산자를 이용하여 직접 객체를 생성할 수 없다.*/
			
			
			/* docBuildFact 를 사용해 DocumentBuilder 의 새로운 인스턴스를 만든다.*/
			docBuild = docBuildFact.newDocumentBuilder();
			
			/* Java 프로그램은 "file" 에 해당하는 XML 문서를 파싱하여 그 결과를 doc 인스턴스에 저장하게 된다.
			 * 파싱 : xml 문서가 가지고 있는 실제 정보나 속성값을 추출해 내는 과정을 말함.
			 * */
			doc = docBuild.parse(file); 
			
			/*인접한 텍스트 노드를 병합하고 빈(공백) 것은 제거한다.*/ 
			doc.getDocumentElement().normalize();
			
			System.out.println("Root element : " + doc.getDocumentElement().getNodeName() + "\n");
			

			/* person엘리먼트 리스트*/
			personlist = doc.getElementsByTagName("person"); // "person tag" 에 해당하는 Tree 가져오기
			
			
			for (int i = 0; i < personlist.getLength(); i++) {

				System.out.println("---------- personNode " + i + "번째 ------------------");

				personNode = personlist.item(i);

				if (personNode.getNodeType() == Node.ELEMENT_NODE) {
					
					/* person엘리먼트*/
					personElmnt = (Element) personNode;

					/* name 태그*/
					nameList = personElmnt.getElementsByTagName("name");
					nameElmnt = (Element) nameList.item(0);
					name = nameElmnt.getFirstChild();
					
										
					System.out.println("name    : " + stringSlice(name.getNodeValue(), 5));

					/* tel 태그*/
					telList = personElmnt.getElementsByTagName("tel"); // "tel tag" 에 해당하는 Tree 가져오기
					telElmnt = (Element) telList.item(1);   // "tel tag"중 "Index" 에 해당하는 "Element(tag 정보)" 를 가져온다.
					tel = telElmnt.getFirstChild(); 		// "telElmnt"(teltag 중 0번째 tag) 에 해당하는 값, 해당 tag의 value를 가져온다.
					
					System.out.println("tel     : " + tel.getNodeValue()); // (1 번째) "Element"의 값 출력

					 /* address 태그*/
					addressList = personElmnt.getElementsByTagName("address");
					addressElmnt = (Element) addressList.item(0);
					address = addressElmnt.getFirstChild();
					
					System.out.println("address : " + address.getNodeValue());
					
				} //end if

				System.out.println("---------------------------------------------\n");
				
			} // end for

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			
		}// end try ~ catch ~ finally
		
		return personlist;
	}
}
