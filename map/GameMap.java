package map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.util.ArrayList;

/*
 * Created on 2009-3-29
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */

/**
 * @author Administrator
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class GameMap {
	
	public static ArrayList dipingxians;	//��ƽ�ߵļ���
	
	public GameMap(String mapfile){
		dipingxians = new ArrayList();
		System.out.println("������Map��");
		readxml(mapfile);
	}
	public ArrayList getdipingxian(){
		return dipingxians;
	}
	private void readxml(String mapfile){
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		System.out.println("��ʼ����ͼ�ļ�");
		try {				
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(mapfile);		
			
			NodeList xians = doc.getElementsByTagName("xian");
			int shang = 0,xia = 0;	//һ����ƽ�ߵ�����������
			for(int i = 0;i < xians.getLength();i++){	//ѭ����ƽ��				
				xia = Integer.parseInt(((Element)xians.item(i)).getAttribute("height"));			
				NodeList duans = ((Element)xians.item(i)).getElementsByTagName("duan");
				ArrayList arraylist = new ArrayList();	//��ƽ��,�˴���ʱ��				
				for(int j = 0;j < duans.getLength();j++){	//ѭ����ƽ���е�ÿһ����						
					Element duan = (Element)duans.item(j);
					Map_duan map_duan = new Map_duan();					
					Element start = (Element)duan.getElementsByTagName("start").item(0);					
					map_duan.start = Integer.parseInt(start.getFirstChild().getNodeValue());					
					Element end = (Element)duan.getElementsByTagName("end").item(0);
					map_duan.end = Integer.parseInt(end.getFirstChild().getNodeValue());					
					Element kong = (Element)duan.getElementsByTagName("kong").item(0);
					map_duan.kong = Boolean.parseBoolean(kong.getFirstChild().getNodeValue());					
					arraylist.add(map_duan);
				}
				Dipingxian dipingxian = new Dipingxian();
				dipingxian.shang = shang;
				dipingxian.xia = xia;
				shang = xia;
				dipingxian.duans = arraylist;
				dipingxians.add(dipingxian);
			}
			/*
			Element xian = (Element)doc.getElementsByTagName("xian").item(0);	//�õ�һЩĬ�ϵĲ��ɶ�̬���ĵ����ԣ�����
			NodeList duans = xian.getElementsByTagName("duan");		
			
			for(int i = 0;i < duans.getLength();i++){
				Element duan = (Element)duans.item(i);
				Map_duan map_duan = new Map_duan();
				
				Element start = (Element)duan.getElementsByTagName("start").item(0);					
				map_duan.start = Integer.parseInt(start.getFirstChild().getNodeValue());					
				Element end = (Element)duan.getElementsByTagName("end").item(0);
				map_duan.end = Integer.parseInt(end.getFirstChild().getNodeValue());					
				Element kong = (Element)duan.getElementsByTagName("kong").item(0);
				map_duan.kong = Boolean.parseBoolean(kong.getFirstChild().getNodeValue());
				
				
				
				arraylist.add(map_duan);
			}
			*/
			System.out.println("����ͼ�ļ�OVER");
			System.out.println("��ӡ�����ͼ��Ϣ begin");	
			for(int i = 0;i < dipingxians.size();i++){							
				System.out.println("---һ����ƽ��---");
				System.out.println(	((Dipingxian)dipingxians.get(i)).shang);
				System.out.println(	((Dipingxian)dipingxians.get(i)).xia);
				System.out.println();
				
				for(int j = 0;j < ((Dipingxian)dipingxians.get(i)).duans.size();j++){
					System.out.println(((Map_duan)((Dipingxian)dipingxians.get(i)).duans.get(j)).start);
					System.out.println(((Map_duan)((Dipingxian)dipingxians.get(i)).duans.get(j)).end);					
					System.out.println(((Map_duan)((Dipingxian)dipingxians.get(i)).duans.get(j)).kong);
				}				
			}
			System.out.println("��ӡ�����ͼ��Ϣ over");
			/*
			for(int i = 0;i < arraylist.size();i++){	//������ʾ�б��е�Ԫ��	������
				System.out.println(((Map_duan)arraylist.get(i)).end);
			}
			//*/
		}
		catch (Exception e) {
		e.printStackTrace();
		}
	}
	
	public static class Map_duan{	//������ͼ��һ���ε���
		public int start;
		public int end;
		public boolean kong;
	}
	public static class Dipingxian{
		public int shang;
		public int xia;
		public ArrayList duans;
	}
}

