package panels;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Enumeration;
import java.util.Vector;

import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.swing.JPanel;

import events.events;
import jingling.Jingling;
import jingling.Zidanlei; 
import map.GameMap;
import option.Opotion;

public class GamePanel extends JPanel implements Runnable,KeyListener,FocusListener,events.Add_zidan_jiekou {	
	public Jingling jingling;	//����
	public GameMap	gamemap;	//��ͼ������
	public Zidanlei zidan;		//�ӵ�
	
	private int jinglingx;	//����һ��ʼ�ŵ���x��λ��
	private int lastpresskey;
	private int x,y,heigth,weidth;	//��������
	private boolean gonging;	//�Ƿ����
	private Image img;	//��ȡͼ������
	private BufferedImage buf_image;	//����˫����
	private Sequencer midi;	//�������ֵı���	
	private Vector repository;	//�����¼���������
	events.Panel_over_linstener_jiekou dl;
	
	public GamePanel(int weidth,int heigth) {	//���컯����
		x = 0;
		y = 0;
		gonging = true;
		this.heigth = heigth;
		this.weidth = weidth;		
		gamemap = new GameMap(Opotion.getmapfile());
		zidan = new Zidanlei(this);
		
		//zidan.addzidan(0,50,true,Zidanlei.ZIDAN_JIAODU_225);	//���һ���ӵ�������
		//zidan.addzidan(10,100,true,Zidanlei.ZIDAN_JIAODU_225);	//���һ���ӵ�������
		
		repository = new Vector();
		
		this.addKeyListener(this);	//��Ӽ��̼����¼�
		this.addFocusListener(this);	//��ӽ�������¼�
		
		try {
        	//����midʵ�����
        	///*
            Sequence seq = MidiSystem.getSequence(new File("music\\first.mid"));
            midi = MidiSystem.getSequencer();
            midi.open();
            midi.setSequence(seq);
            midi.setLoopCount(Sequencer.LOOP_CONTINUOUSLY);	//����ѭ������
            midi.start();
            //*/
        	} 
        catch (Exception ex) {}    
			
		img = this.getToolkit().createImage("image\\2004530941787.png");
		MediaTracker tracker = new MediaTracker(this);	//�ȴ�ͼƬ�������
		tracker.addImage(img, 0);
		try     
        {   
			tracker.waitForAll();   //�ȴ�ȫ������
        }   
        catch   (Exception   ex)     
        {   
            System.err.println(ex.toString()); 
        }
        
		//System.out.println(img.getWidth(null));
        
		buf_image = new BufferedImage(img.getWidth(null),img.getHeight(null),BufferedImage.TYPE_INT_RGB);
		Graphics2D buf_grafc = buf_image.createGraphics();        
		buf_grafc.drawImage(img,0,0,null);		
		
		jinglingx = 120;
		jingling = new Jingling(jinglingx,110,buf_image.getWidth(),this.weidth);	//����
		jingling.addPanel_over_linstener(new Ren_over());	//�����������ʱ�Ľ����¼�
		jingling.addjia_zidan_listener(this);
		//jingling.
		
		//buf_grafc.setColor(Color.GREEN);//ʵ���ô���
		//buf_grafc.drawRect(3,3,30,30);
		//buf_grafc.setColor(Color.RED);
		//buf_grafc.fillRect(4,4,29,29);
	}
	public void startdraw() {	//��ʼ����ˢ�£���ʼ�߳�
		Thread thd = new Thread(this);
		thd.setPriority(Thread.MIN_PRIORITY);
		thd.start();
		System.out.println("THE THREAD IS START");
	}
	public void stop(){
		gonging = false;
		jingling.setdongzuo(Jingling.DZ_TING);	//�þ�����ͣ������õ������ʹ�����߼���Ҳ�������
	}
	public void goon(){
		gonging = true;
	}
	
	public void run() {		//�߳̿�ʼ
		long time = System.currentTimeMillis(); 	//����֡����
		int i = 0;
		
		long times = System.currentTimeMillis();	//����֡��
		
		while(true) {
			
			i++;	//�˶��ж�֡��
			if(System.currentTimeMillis() - time > 1000){
				time = System.currentTimeMillis();
				System.out.println("��ǰ֡�ʣ�" + i);
				i = 0;
			}			
			
			while(gonging == false){	//�Ƿ������
				try{
					Thread.sleep(10);	//����
				}
				catch(InterruptedException e)
				{	}
			}		
				
			this.repaint();
			
			
			
			try{	
					if(times - System.currentTimeMillis() < 9){	//����֡��
						Thread.sleep(9 - (times - System.currentTimeMillis()));	//����
						times = System.currentTimeMillis();
					}									
				}			
			catch(InterruptedException e)
			{	}
			
		}
	}
	public void paint(Graphics g){ 	//��д�˻��ƺ���		����ʵ����˫���壬��������û��ʹ��˫���壡��
		
		Graphics2D gphc2d = (Graphics2D)g;
		//System.out.println("Painting !");
		//gphc2d.drawImage(img,10,5,null);
		//gphc2d.drawImage((Image)buf_image,0,0,null);	//����仰����������һ���ڿ򣡣�������һ��ȴ����//�Ѿ��㶨��������ͼ��û�еȵ�ȫ����Ϳ�ʼ��������
		x = jingling.zongx - jinglingx + 1;
		//System.out.print(jingling.zongx);
		gphc2d.drawImage(buf_image.getSubimage(x,y,weidth,buf_image.getHeight()),0,0,null);	//����ͼƬ�ĸ߿���panel�Ŀ�
		jingling.todo(gphc2d);
		zidan.todo(gphc2d);
		
		//g.drawImage(bfimg,0,0,null);
	}
	public void addPanel_over_linstener(events.Panel_over_linstener_jiekou hdl) {	//�Զ����¼����¼�Դ������	
		repository.addElement(hdl);//�ⲽҪע��ͬ������
	}
	public void shijian(events.Panel_over_event event) {	//����ִ���¼�
	    Enumeration enum = repository.elements();//�ⲽҪע��ͬ������
	    while(enum.hasMoreElements())
	    {
	    	dl = (events.Panel_over_linstener_jiekou)enum.nextElement();
	    	dl.EventActivated(event);
	    }
	  }
	public void keyPressed(KeyEvent e) {	//���̼����ӿڵ�ʵ��
		if(e.getKeyCode()== KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_KP_RIGHT){	//�������Ҽ�
			lastpresskey = KeyEvent.VK_RIGHT;
			jingling.setfangxiang(Jingling.DZ_FX_QIAN);
			jingling.setdongzuo(Jingling.DZ_ZOU);
			//System.out.println("��ǰ��");
		}
		if(e.getKeyCode()== KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_KP_LEFT ){	//���������
			lastpresskey = KeyEvent.VK_LEFT;
			jingling.setfangxiang(Jingling.DZ_FX_HOU);
			jingling.setdongzuo(Jingling.DZ_ZOU);		
			//System.out.println("�����");
		}
		if(e.getKeyCode()== KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_KP_UP ){	//�������ϼ�
			jingling.tiao();					
			//System.out.println("����");
		}
		if(e.getKeyCode()== KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_KP_DOWN ){	//�������¼�
			lastpresskey = KeyEvent.VK_DOWN;
			jingling.setdongzuo(Jingling.DZ_PA);
			//System.out.println("ſ��");
		}
		if(e.getKeyCode()== KeyEvent.VK_BACK_SPACE || e.getKeyCode() == KeyEvent.VK_SPACE){	//���¿ո��
			jingling.add_zidan();
			System.out.println("���¿ո��");			
		}
	}
	public void keyTyped(KeyEvent e) { }
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode()== KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_KP_DOWN || e.getKeyCode() == KeyEvent.VK_KP_UP || e.getKeyCode()== KeyEvent.VK_UP || e.getKeyCode()== KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_KP_LEFT || e.getKeyCode()== KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_KP_RIGHT)
			if(e.getKeyCode() == lastpresskey)
				jingling.setdongzuo(Jingling.DZ_TING);
		//System.out.println("ͣס");
	}
	public void focusLost(FocusEvent e){
		midi.stop();
		gonging = false;
		jingling.setdongzuo(Jingling.DZ_TING);	//�þ�����ͣ������õ������ʹ�����߼���Ҳ�������
	}
	public void focusGained(FocusEvent e){
		midi.start();
		gonging = true;		
	}
	public class Ren_over implements events.Panel_over_linstener_jiekou{	//MenuPanel�����¼��Ĵ�����
		public void EventActivated(events.Panel_over_event me) 
		{		
			System.out.println("�����ˣ���Ϸ����������������������");
			shijian(new events.Panel_over_event(this));
		} 
	}
	public void add_zidan(events.Add_zidan_event event){	//��Ӧ�ӵ����¼�������ӵ�
		zidan.addzidan(event.x,event.y,event.ismy,event.jiaodu);
	}
}