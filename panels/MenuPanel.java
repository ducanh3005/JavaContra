package panels;

import events.events;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Color;
import java.awt.MediaTracker;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.Vector;
import java.util.Enumeration;
import javax.swing.*;
///*
import javax.sound.midi.*;
import java.io.*;

//*/

import java.util.Timer;
import java.util.TimerTask;

public class MenuPanel extends JPanel implements KeyListener,FocusListener {
	private java.util.Timer timer; //ѭ����ʱ����Ƿ����
	private Vector repository;	//�����¼���������
	events.Panel_over_linstener_jiekou dl;
	//private events.MenuPanel_over_linstener_jiekou dl;
	int heigth,weidth;	//��������,���
	int selectid;	//��ǰ��ѡ��
	public boolean gonging;	//�Ƿ����
	private int x,y;	//��һ��ѡ��ָ���λ��
	Image selectimg;
	BufferedImage bufimg;
	Sequencer midi;	//�������ֵı���
	Sequencer midis;	
	
	public MenuPanel(){
		gonging = true;
		repository = new Vector();
		selectid = 0;
		x = 34;
		y = 142;
		Image img = this.getToolkit().createImage("image\\menu.jpg");
		selectimg = this.getToolkit().createImage("image\\xueze.jpg");
		MediaTracker tracker = new MediaTracker(this);	//�ȴ�ͼƬ�������
		tracker.addImage(img, 0);
		tracker.addImage(selectimg, 1);
		try     
        {   
			tracker.waitForAll();   //�ȴ�ȫ������   
        }   
        catch   (Exception   ex)     
        {   
            System.err.println(ex.toString());   
        }
        
        bufimg = new BufferedImage(img.getWidth(null),img.getHeight(null),BufferedImage.TYPE_INT_RGB);	//��������ͼƬ
		Graphics2D buf_grafc = bufimg.createGraphics();
		this.addKeyListener(this);	//��Ӽ��̼����¼�
		this.addFocusListener(this);	//��ӽ�������¼�
        buf_grafc.drawImage(img,0,0,null);	//��������������ͼƬ        
        
        try {
        	//����midʵ�����
        	///*
            Sequence seq = MidiSystem.getSequence(new File("music\\bgmusic.mid"));
            midi = MidiSystem.getSequencer();
            midi.open();
            midi.setSequence(seq);
            midi.setLoopCount(Sequencer.LOOP_CONTINUOUSLY);	//����ѭ������
            midi.start();    
            
            
            //*/
        	} 
        catch (Exception ex) {}    
        
        
        timer = new Timer(true); 
        timer.schedule(
        new TimerTask() { public void run(){check_gonging();} }, 0, 50); 
	}
	public void paint(Graphics g){ 	//��д�˻��ƺ���
		Graphics2D buf_grafc = bufimg.createGraphics();
		buf_grafc.setColor(Color.BLACK);
		buf_grafc.fillRect(x,y,21,35);	//���ǵ�ԭ����ѡ��ָ��
		buf_grafc.drawImage(selectimg,x,y+selectid*15,null);	//�򱳾�ͼ�ϻ滭ѡ��ָ��
		
		Graphics2D gphc2d = (Graphics2D)g;
		gphc2d.drawImage(bufimg,0,0,null);	//���������		
	}
	public void addPanel_over_linstener(events.Panel_over_linstener_jiekou hdl) {	//�Զ����¼����¼�Դ������	
		repository.addElement(hdl);//�ⲽҪע��ͬ������
	}
	public void removeMenuPanel_over_linstener(events.Panel_over_linstener_jiekou hdl) {
		repository.removeElement(hdl);//�ⲽҪע��ͬ������
	}
	public void shijian(events.Panel_over_event event) {	//����ִ���¼�
	    Enumeration enum = repository.elements();//�ⲽҪע��ͬ������
	    while(enum.hasMoreElements())
	    {
	    	dl = (events.Panel_over_linstener_jiekou)enum.nextElement();
	    	dl.EventActivated(event);
	    }
	  }
	private void check_gonging() {
		///*
		if(gonging == false){	//�����ͣ��
			if(midi.isRunning())
				midi.stop();
			if(midis != null &&midis.isRunning())
				midis.stop();
		}
		else{
			if(! midi.isRunning())
				midi.start();
			if(midis != null && ! midis.isRunning())
				midis.start();
		}
		//*/
    }
	private void playselectmusic(){
		
		 try {
        	//����midʵ�����
		 	///*		 		
		 		Sequence seqs = MidiSystem.getSequence(new File("music\\pass.mid"));		 		
	            midis = MidiSystem.getSequencer();
	            midis.setSequence(seqs);
	            midis.open();            
	            //midis.start();
            //*/		 	
		 	
            }       			
        catch (Exception ex) {}        
	}
	public void keyPressed(KeyEvent e) {	//���̼����ӿڵ�ʵ��
		if(e.getKeyCode()== KeyEvent.VK_KP_DOWN || e.getKeyCode()== KeyEvent.VK_DOWN ){	//�������¼�
			if (selectid == 0)
				selectid = 1;
			else
				selectid--;
			playselectmusic();
			this.repaint();			
			System.out.println(selectid);
		}
		if(e.getKeyCode()== KeyEvent.VK_KP_UP || e.getKeyCode()== KeyEvent.VK_UP ){	//�������ϼ�
			if (selectid == 1)
				selectid = 0;				
			else
				selectid++;
			playselectmusic();
			this.repaint();
			System.out.println(selectid);
		}
		if(e.getKeyCode()== KeyEvent.VK_ENTER){		//���»س�����������Ϸ
			timer.cancel();
			///*
			if(midi.isRunning())
				midi.stop();
			if(midis != null && midis.isRunning())
				midis.stop();
			midi.close();
			//*/
			shijian(new events.Panel_over_event(this));
			
		}
	}
	public void keyTyped(KeyEvent e) { }
	public void keyReleased(KeyEvent e) { }	
	public void focusLost(FocusEvent e){		
		gonging = false;
	}
	public void focusGained(FocusEvent e){		
		gonging = true;
	}
}

