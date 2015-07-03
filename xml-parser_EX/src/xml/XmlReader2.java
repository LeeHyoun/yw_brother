package xml;

import java.io.File; 
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


/**
 * <pre>
 * xml
 *   |_ XmlReader2.java
 * </pre>
 * 
 * Desc : xml 태그에 속성이 추가 되었을때.
 * 			comboBox name과 option value가 속성입니다
 * @Company : DataStreams
 * @Author  : HLEE
 * @Date    : 2015. 7. 2. 오전 9:49:37
 * @Version : Java 1.7
 */
public class XmlReader2 {
 
	/**
	 * Desc : main
	 * @Method Name : main
	 * @param argv
	 */
	public static void main(String argv[]) {
 
		try {
			File file = new File("./comboBox.xml");
			DocumentBuilderFactory docBuildFact = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuild = docBuildFact.newDocumentBuilder();
			Document doc = docBuild.parse(file);
			doc.getDocumentElement().normalize();
 
			System.out.println("Root element : " + doc.getDocumentElement().getNodeName());
			System.out.println();
 
			// comboBox엘리먼트 리스트
			NodeList comboBoxlist = doc.getElementsByTagName("comboBox");
 
			for (int i = 0; i < comboBoxlist.getLength(); i++) {
 
				System.out.println("---------- comboBoxNode "+ i + "번째 ------------------");
 
				Node comboBoxNode = comboBoxlist.item(i);
 
				if (comboBoxNode.getNodeType() == Node.ELEMENT_NODE) {
					// comboBox엘리먼트 
					Element comboBoxElmnt = (Element) comboBoxNode;
 
					// 콤보박스 명
					System.out.println("comboBox name : " + comboBoxElmnt.getAttribute("name"));
 
					// option 태그
					NodeList optList= comboBoxElmnt.getElementsByTagName("option");
					
					for (int j = 0; j < optList.getLength(); j++) {
						Element optElmnt = (Element) optList.item(j);
						Node name = optElmnt.getFirstChild();
						System.out.println("option : " + name.getNodeValue() + "   value : " +optElmnt.getAttribute("value"));
					}
 
				}
 
				System.out.println("---------------------------------------------");
				System.out.println();
			}
 
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}