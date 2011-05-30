package option;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.ArrayList;


public class Opotion {
		private String optfile;		//�����ļ���λ��
		public int kuan,gao,x,y;	//��Ϸ�Ľ���Ĵ�С,λ��
		public int guanlength;		//��Ϸ�ж��ٹ�
		public static int guannow;			//��ǰ�ǵڼ���		
		private static ArrayList guan;//�ؿ�����
		
		public Opotion(){	//���캯��
			guannow = 1;
			guan = new ArrayList();
			optfile = "option\\option.xml";
			readopotion();
		}
		public static String getmapfile(){
			return ((Guan)guan.get(guannow)).mapfile;
		}
		public static String getguanname(){
			return ((Guan)guan.get(guannow)).name;
		}
		protected void finalize()throws Throwable{	//��������
			super.finalize();			
		}
		private void readopotion(){	//��ȡ������Ϣ				
			File fl = new File(this.optfile);
			if(fl.exists()){	//�ж������ļ��Ƿ����
				DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
				try {				
					DocumentBuilder db = dbf.newDocumentBuilder();
					Document doc = db.parse(this.optfile);
					
					Element dft	= (Element)doc.getElementsByTagName("default").item(0);	//�õ�һЩĬ�ϵĲ��ɶ�̬���ĵ����ԣ�x,y,����
					Element wd	= (Element)dft.getElementsByTagName("width").item(0);
					this.kuan	= Integer.parseInt(wd.getFirstChild().getNodeValue());
					Element ht	= (Element)dft.getElementsByTagName("height").item(0);
					this.gao	= Integer.parseInt(ht.getFirstChild().getNodeValue());
					Element xs	= (Element)dft.getElementsByTagName("x").item(0);
					this.x		= Integer.parseInt(xs.getFirstChild().getNodeValue());
					Element ys	= (Element)dft.getElementsByTagName("y").item(0);
					this.y		= Integer.parseInt(ys.getFirstChild().getNodeValue());
					
					NodeList guans = doc.getElementsByTagName("guan");
					this.guanlength = guans.getLength();
										
					
					for(int i = 0;i < this.guanlength;i++){
						Guan g = new Guan();
						Element guank = (Element)guans.item(i);	
						Element name = (Element)guank.getElementsByTagName("name").item(0);
						Element mapfile = (Element)guank.getElementsByTagName("map").item(0);						
						
						g.name = name.getFirstChild().getNodeValue();
						g.mapfile = mapfile.getFirstChild().getNodeValue();
						
						guan.add(g);
						
					}
					
					//System.out.println(kuan + "	" + gao + "	" + x + "	" + y);
				}
				catch (Exception e) {
				e.printStackTrace();
				}
			}
			else
			{
				System.out.println("�ļ������ڣ��������ļ���");
			}			
		}
		
		public void saveopotion(){
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			try {				
				DocumentBuilder db = dbf.newDocumentBuilder();
				Document doc = db.parse(this.optfile);
				
				Element dft = (Element)doc.getElementsByTagName("default").item(0);	//�õ�һЩĬ�ϵĲ��ɶ�̬���ĵ����ԣ�����
				Element el;
				el = (Element)dft.getElementsByTagName("x").item(0);
				el.getFirstChild().setNodeValue(String.valueOf(this.x));
				el = (Element)dft.getElementsByTagName("y").item(0);
				el.getFirstChild().setNodeValue(String.valueOf(this.y));
				
				TransformerFactory tFactory =TransformerFactory.newInstance();
				Transformer transformer = tFactory.newTransformer();
				DOMSource source = new DOMSource(doc);
				StreamResult result = new StreamResult(new java.io.File(this.optfile));
				transformer.transform(source, result);				
			}
			catch (Exception e) {
			e.printStackTrace();
			}	
			System.out.println("�����Ѿ����档");
		}
}

class Guan{
	public String name;
	public String mapfile;
}

/*
 * Ҫ�������ʵ�����¹��ܣ�
 * 1.	��XML��ȡ��Ϸ���ݣ��������ݣ��������а��ϴ��Ƿ񱣴���ȣ������ļ�������
 * 2.	��������
 * 2.	��������ʱ��ʱ�ṩ��ѯ
 */
