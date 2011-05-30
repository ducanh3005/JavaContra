package jingling;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.awt.Color;
import panels.GamePanel;

public class Zidanlei{
	public static final int ZIDAN_JIAODU_0		= 1;	//�ӵ�����İ˸�����
	public static final int ZIDAN_JIAODU_45		= 2;
	public static final int ZIDAN_JIAODU_90		= 3;
	public static final int ZIDAN_JIAODU_135	= 4;
	public static final int ZIDAN_JIAODU_180	= 5;
	public static final int ZIDAN_JIAODU_225	= 6;
	public static final int ZIDAN_JIAODU_270	= 7;
	public static final int ZIDAN_JIAODU_315	= 8;
	
	
	
	
	private static ArrayList list;	//��ǰ���ӵ��б�
	private static ArrayList list_wait;	//���е��ӵ��б�
	private GamePanel gp;	//����Ϸ�������ã����Եõ���ͼ�����������
	
	public Zidanlei(GamePanel gamepanel){
		list = new ArrayList();
		list_wait = new ArrayList();
		gp = gamepanel;
	}
	
	public void addzidan(int x,int y,boolean ismy,int jiaodu){
		System.out.println("�����һ���ӵ�");
		if(list_wait.size() != 0){	//��������б����п��еģ���ô�����ÿ����ӵ�������
			System.out.println("�ӿ����б�������һ���ӵ�");
			Zidan zd = (Zidan)list_wait.get(0);
			
			zd.x = x;
			zd.y = y;
			zd.ismy = ismy;
			zd.jiaodu = jiaodu;
			
			list.add(zd);	//��ӵ��ӵ��б���ӿ����б�ɾ��
			list_wait.remove(0);
		}
		else{
			System.out.println("��������һ���ӵ�");
			Zidan zd = new Zidan();			
			zd.x = x;
			zd.y = y;
			zd.ismy = ismy;
			zd.jiaodu = jiaodu;			
			list.add(zd);	//��ӵ��ӵ��б���ӿ����б�ɾ��
		}
	}		
	public void todo(Graphics2D g){	//���������
		this.move();
		this.check();
		this.draw(g);
	}
	private void move(){	//�ӵ����ƶ�
		for(int i = 0;i < list.size();i++){
			Zidan zd = (Zidan)list.get(i);
			switch(zd.jiaodu ){
				case Zidanlei.ZIDAN_JIAODU_90 :
					zd.x += 2;
					break;
				case Zidanlei.ZIDAN_JIAODU_270 :
					zd.x -= 2;
					break;
				default :	//��ʱ������
					zd.x++;
					//zd.y++;
			}
		}
	}
	private void draw(Graphics2D g){	//�����ӵ�
		g.setColor(Color.RED);
		//System.out.println("�ӵ������С��" + list.size());
		for(int i = 0;i < list.size();i++){
			Zidan zd = (Zidan)list.get(i);			
			g.fillRect(zd.x - gp.jingling.zongx + gp.jingling.x,zd.y,5,5);
		}
	}
	private void check(){	//����ӵ�
		for(int i = 0;i < list.size();i++){
			Zidan zd = (Zidan)list.get(i);
			if(zd.x - gp.jingling.zongx > 200 || gp.jingling.zongx - zd.x > 200 || zd.y < 0 || zd.y > 300){
				this.movetowait(i);
			}
		}
	}
	private void movetowait(int i){	//�����˵�ǰ�����û�õ��ӵ��Ƶ������ӵ��б�
		System.out.println("ɾ����һ���ӵ�");
		list_wait.add(list.get(i));
		list.remove(i);
	}
	
	
	public class Zidan {	//�ӵ���
		private int x;	//�ӵ���x��y���ꣻ����Ӧ�ô洢ÿ��x��y�ƶ��Ķ���
		private int y;	//�ӵ���x��y���ꣻ
		private boolean ismy;	//�ǻ궷�޵��ӵ����ǵ��˵��ӵ���
		private int jiaodu;	//�ӵ����ʱ��ĽǶȡ�
	}
}

