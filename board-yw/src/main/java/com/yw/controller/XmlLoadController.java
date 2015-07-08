package com.yw.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer; // XSLT 변환기는 해당 Transformer 추상 클래스를 상속한 객체를 말한다.
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.yw.bean.XmlDTO;


/**
 * <pre>
 * com.yw.controller
 *   |_ XmlLoadController.java
 * </pre>
 * 
 * Desc : 
 * @Company : DataStreams
 * @Author  : HLEE
 * @Date    : 2015. 7. 3. 오후 4:52:03
 * @Version : 작성자 |   작성일   | 작성시간 | 수정사항
 * 				HLEE  | 2015.07.03 | 04:52:03 | 최초 작성
 * 				HLEE  | 2015.07.06 | 09:42:03 | DATA ->>> XML 저장 또는 XML파일 읽어서 수정
 * 				HLEE  | 2015.07.07 | 09:56:03 | Node 추가 후 저장.
 */
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
	 * Desc : xml file을 읽어와 웹상에 Data 보이기
	 * @Method Name : xmlReader
	 * @param file_path
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value="/xmlReader", method=RequestMethod.POST)
	public List<XmlDTO> xmlReader(@Param(value="file_path") String file_path) throws Exception {
		
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
		
		List<XmlDTO> xmlList = new ArrayList<XmlDTO>();
		/*XmlDTO xmlDto = null;*/
		XmlDTO xmlDto = new XmlDTO();
		try {
			
			lastIndex = file_path.lastIndexOf("//"); // 맨마지막 "//" 의 위치
			
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
			
			System.out.println("Root element : " + doc.getDocumentElement().getNodeName() + "/n");
			

			/* person엘리먼트 리스트*/
			personlist = doc.getElementsByTagName("person"); // "person tag" 에 해당하는 Tree 가져오기
			
			String nameNode = "";
			String addrNode = "";
			
			for (int i = 0; i < personlist.getLength(); i++) {
				
				xmlDto = new XmlDTO();
				
				System.out.println("---------- personNode " + i + "번째 ------------------");

				personNode = personlist.item(i);
				
				if (personNode.getNodeType() == Node.ELEMENT_NODE) {
					
					/* person엘리먼트*/
					personElmnt = (Element) personNode;

					/* name 태그*/
					nameList = personElmnt.getElementsByTagName("name");
					nameElmnt = (Element) nameList.item(0);
					name = nameElmnt.getFirstChild();
					
					nameNode = name.getNodeValue();
					
					/*if ( !(nameNode.length() < 1) && nameNode != null){
						nameNode = stringSlice(nameNode, 5);
					}*/
					
					System.out.println("name    : " + nameNode);

					/* tel 태그*/
					telList = personElmnt.getElementsByTagName("tel"); // "tel tag" 에 해당하는 Tree 가져오기
					telElmnt = (Element) telList.item(0);   // "tel tag"중 "Index" 에 해당하는 "Element(tag 정보)" 를 가져온다.
					tel = telElmnt.getFirstChild(); 		// "telElmnt"(teltag 중 0번째 tag) 에 해당하는 값, 해당 tag의 value를 가져온다.
					
					System.out.println("tel     : " + tel.getNodeValue()); // (1 번째) "Element"의 값 출력
					
					 /* address 태그*/
					addressList = personElmnt.getElementsByTagName("address");
					addressElmnt = (Element) addressList.item(0);
					address = addressElmnt.getFirstChild();
					
					addrNode = address.getNodeValue();
					
					/*if ( !(addrNode.length() < 1) && addrNode != null){
						//addrNode = stringSlice(addrNode, 7);
					}*/
					
					System.out.println("address : " + addrNode);
					
					xmlDto.setName(nameNode);
					xmlDto.setTel(tel.getNodeValue());
					xmlDto.setAddress(addrNode);
					
					
					xmlList.add(xmlDto);
					
				} //end if

				System.out.println("---------------------------------------------/n");
				
			} // end for

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			
		}// end try ~ catch ~ finally
		
		
		for (XmlDTO xmlDTO : xmlList) {
			System.out.println(xmlDTO.getName());
		}
		
		return xmlList;
	}
	
	
	
	
	/**
	 * Desc : DOM객체를 XML 문서파일로 만들기
	 * @Method Name : SaveDOMToFile
	 * @param file_path
	 * @throws Exception
	 */
	@RequestMapping(value="/saveDOMToFile", method=RequestMethod.POST)
	public void SaveDOMToFile(	@Param(value="file_path") String file_path,
							    @Param(value="createName") String createName) throws Exception{
		
		System.out.println("XML 파일 생성을 시작합니다....");
		
		File file = null;
		
		/*DOM 파서 생성*/
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setIgnoringElementContentWhitespace(true);
		DocumentBuilder builder = factory.newDocumentBuilder();
		
		/*XML 문서 파싱하기*/
		Document document = builder.parse(file_path);

		/*루트 엘리먼트 참조 얻기
		Element eRoot = document.getDocumentElement();*/

		/*변환기 생성*/
		TransformerFactory tf = TransformerFactory.newInstance();
		Transformer transformer = tf.newTransformer();

		/*출력 포맷 설정*/
		transformer.setOutputProperty(OutputKeys.METHOD, "xml");
		transformer.setOutputProperty(OutputKeys.ENCODING,"UTF-8");             	// 인코딩을 UTF-8로 한다.
		//transformer.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM,"bml.dtd");	 	// DTD 문서경로
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");  
		transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "3");// WhiteSpace 포함여부
		//transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");		// XSLT 프로세서가 XML 선언을 출력할지 어떨지를 지정합니다. 

		/*DOMSource 객체 생성*/
		DOMSource source = new DOMSource(document); //저장할 대상인 원본 XML문서를 Source객체로 생성한다. 
		//* 여기서는 XML문서가 DOM 객체트리로 존재하기 때문에 DOMSource 객체를 생성해야 한다.

		/*StreamResult 객체 생성*/
		String path = "C:/java/eclipse-jee-luna-SR2-win32-x86_64/work/board-yw/src/main/webapp/WEB-INF/xmlFile/";
		file = new File(path + createName + ".xml"); 	//파일의 저장하고자하는 위치와 이름을 명시.
		StreamResult result = new StreamResult(file);

		/*파일로 저장하기*/
		transformer.transform(source,result);

		System.out.println("파일 생성 완료");
	}
		
	
	
	/**
	 * Desc : 읽어온 xml문서의 요소를 수정. 
	 * @Method Name : updateXml
	 * @param file_path
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value="/updateXML", method=RequestMethod.POST)
	public void updateXML(@ModelAttribute XmlDTO xmlDTO) throws Exception{
		
		System.out.println("업데이트를 시작합니다.");
		System.out.println(xmlDTO.toString());
		
		File file = null;
		
		/*DOM 파서 생성*/
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setIgnoringElementContentWhitespace(true); 		//요소의 내용의 공백을 배제하도록 지정 
		DocumentBuilder builder = factory.newDocumentBuilder();
		
		/*XML 문서 파싱하기*/
		Document document = builder.parse(xmlDTO.getFile_path());

		//------------------------ 수정 내용 중 요소 노드
		//고칠 노드 이름 찾을 수 있는 모든 노드 똑같은 이름의 노드 큐 받고
		NodeList name = document.getElementsByTagName("name");
		NodeList tel = document.getElementsByTagName("tel");
		NodeList address = document.getElementsByTagName("address");
		
		//지금 대기 중 선택 고칠 노드
		Node nameNode = name.item(xmlDTO.getNum());
		Node telNode = tel.item(xmlDTO.getNum());
		Node addrNode = address.item(xmlDTO.getNum());
		
		//이 노드의 텍스트 수정
		nameNode.setTextContent(xmlDTO.getName()); 		//이름 수정
		telNode.setTextContent(xmlDTO.getTel());   		//전화 수정
		addrNode.setTextContent(xmlDTO.getAddress());	//주소 수정		
		
		System.out.println("수정이 완료되었습니다.");
		
		//-------------------------파일로 저장 중
		//만듭니다. 쓸 변환 DOM 대상 공장 대상
		TransformerFactory factory2 = TransformerFactory.newInstance();
		
		//획득 변환기 대상
		Transformer tf = factory2.newTransformer();
		tf.setOutputProperty(OutputKeys.METHOD, "xml");
		tf.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
		tf.setOutputProperty(OutputKeys.INDENT, "yes");
		
		//정의 변형할 원본 개체
		DOMSource xml = new DOMSource(document);
		
		
		file = new File( xmlDTO.getFile_path() );
		StreamResult sr = new StreamResult(file); //정의 다음으로 변환 대상 파일이 한다
		//System.out.println(xmlDTO.getFile_path());
		
		//변환 시작
		tf.transform(xml, sr);
		System.out.println("수정사항 저장 완료");
	}// end method updateXML
	
	
	
	
	/**
	 * Desc : Node 추가 생성
	 * @Method Name : addXML
	 * @param xmlDTO
	 * @throws Exception
	 */
	@RequestMapping(value="/addXML", method=RequestMethod.POST)
	public void addXML(@ModelAttribute XmlDTO xmlDTO) throws Exception{
		
		File file = null;
		
		/*DOM 파서 생성*/
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setIgnoringElementContentWhitespace(true); //요소의 내용의 공백을 배제하도록 지정 
		DocumentBuilder builder = factory.newDocumentBuilder();
		
		/*XML 문서 파싱하기*/
		Document document = builder.parse(xmlDTO.getFile_path());
		
		//------------------------노드 하위 원소 증가
		//고칠 노드 이름 찾을 수 있는 모든 노드 똑같은 이름의 노드 큐 받고
		NodeList nodes1 = document.getElementsByTagName("addressbook");
		//지금 대기 중 선택 고칠 노드
		Node root_ChildNode = nodes1.item(0);
		//할 증가 노드 원소 만들기
		Element person = document.createElement("person");
		
		//노드 속성 설정
		Attr attr = document.createAttribute("속성이 붙어있는 태그명");
		attr.setValue("속성값");
		person.setAttributeNode(attr);
		
		
		//하위요소 추가.
		Element name = document.createElement("name");    //이름
		name.setTextContent("추가 된 노드");
		person.appendChild(name);
		root_ChildNode.appendChild(person);
		
		Element tel = document.createElement("tel");     //전화번호
		tel.setTextContent("추가 된 노드");
		person.appendChild(tel);
		root_ChildNode.appendChild(person);
		
		Element nn3 = document.createElement("address"); //주소
		nn3.setTextContent("추가 된 노드");
		person.appendChild(nn3);
		root_ChildNode.appendChild(person);

		System.out.println(document.getElementsByTagName("person").getLength());
		
		//-------------------------파일로 저장 중
		//만듭니다. 쓸 변환 DOM 대상 공장 대상
		TransformerFactory factory2 = TransformerFactory.newInstance();
		
		//획득 변환기 대상
		Transformer tf = factory2.newTransformer();
		tf.setOutputProperty(OutputKeys.METHOD, "xml");
		tf.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
		tf.setOutputProperty(OutputKeys.INDENT, "yes");
		
		//정의 변형할 원본 개체
		DOMSource xml = new DOMSource(document);
		
		
		file = new File( xmlDTO.getFile_path() );
		StreamResult sr = new StreamResult(file); //정의 다음으로 변환 대상 파일이 한다
		System.out.println(xmlDTO.getFile_path());
		//변환 시작
		tf.transform(xml, sr);
	}
}// end class
