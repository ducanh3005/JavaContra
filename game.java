

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.*;
import java.awt.event.WindowListener;
import java.awt.event.WindowFocusListener;
import java.awt.image.BufferedImage;

import javax.swing.*;

import events.events;

import panels.GamePanel;
import panels.MenuPanel;
import option.Opotion;


class game extends JFrame implements WindowListener,WindowFocusListener{
	public static final int PANEL_TYPE_GMAEPANEL = 0;
	public static final int PANEL_TYPE_MENUPANEL = 1;
	
	JButton button1;
	GamePanel gamepanl;
	MenuPanel menupanl;
	private Opotion opts;
	private int PanelType;
	
	public static void main(String[] args)
	{
		new game();
	}
	
	public game() {
		opts = new Opotion();	//ȫ�ֵ�����
		
		this.setLayout(null);	//�ֹ��Ű�
		this.setTitle("С��Ϸ");
		this.setIconImage(this.getToolkit().createImage("image\\icon.png"));
		//this.setResizable(false);	//��ֹ������С
		//this.setUndecorated(true);	//�����ޱ�����
		this.setBounds(opts.x,opts.y,264,255);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.addWindowListener(this);
		this.addWindowFocusListener(this);
		
					
		/**
		button1 = new JButton("��ʼ����");	//���һ����ť
		button1.setBounds(2,3,80,40);
		button1.addMouseListener(new button1_click());
		this.add(button1);
		*/
		///**
		menupanl = new MenuPanel();	//��Ӳ˵����MenuPanel
		menupanl.setBounds(0,0,opts.kuan,opts.gao);
		menupanl.addPanel_over_linstener(new MenuPanel_over_linsteners());
		this.add(menupanl);
		PanelType = game.PANEL_TYPE_MENUPANEL;
		//*/
		/**
		gamepanl = new GamePanel(opts.kuan,opts.gao);	//�����Ϸ���GamePanel		
		gamepanl.setBounds(0,45,opts.kuan,opts.gao);
		this.add(gamepanl);
		*/
		
		this.setVisible(true);	
		menupanl.requestFocus();		
	}
	/*
	public void paint(Graphics g){
		BufferedImage bfimg = new BufferedImage(g.getClipBounds().width,g.getClipBounds().height,BufferedImage.TYPE_INT_ARGB);	//������һ������ͼƬ
		Graphics2D newg = (Graphics2D)bfimg.getGraphics();
		//Graphics2D gphc2d = (Graphics2D)g;
		//System.out.println("Painting !");
		//gphc2d.drawImage(img,10,5,null);
		//gphc2d.drawImage((Image)buf_image,0,0,null);	//����仰����������һ���ڿ򣡣�������һ��ȴ����//�Ѿ��㶨��������ͼ��û�еȵ�ȫ����Ϳ�ʼ��������
		super.paint(newg);		
		
		g.drawImage(bfimg.getScaledInstance(this.getWidth(),this.getHeight(),Image.SCALE_FAST),0,0,null);
		System.out.println(this.getWidth());
	}
	*/
	public void windowLostFocus(WindowEvent e){
		setTitle("С��Ϸ��Ϸ-��ͣ");	
	}
	public void windowGainedFocus(WindowEvent e){
		setTitle("С��Ϸ");				
		if(menupanl != null)
		{
			menupanl.gonging = true;
			//menupanl.requestFocus();	//��menupanl�õ����㣬���ǵ�menupanl�Ѿ�ж���ˣ����panl��������ô�죿
			//System.out.println("dddd");
			switch(PanelType)
			{
				case game.PANEL_TYPE_GMAEPANEL:
					gamepanl.requestFocus();
					System.out.println("GMAEPANEL�õ��˽���");
					break;
				case game.PANEL_TYPE_MENUPANEL:
					menupanl.requestFocus();
					System.out.println("MENUPANEL�õ��˽���");
					break;
			}
		}
	}
	public void windowClosing(WindowEvent e) {	//�رճ����˳�ʱ��������
		opts.x = this.getX();		
		opts.y = this.getY();
		opts.saveopotion();
	}
	public void windowClosed(WindowEvent e) {}
	public void windowOpened(WindowEvent e) {}
	public void windowIconified(WindowEvent e) {}
	public void windowDeiconified(WindowEvent e) {}
	public void windowDeactivated(WindowEvent e) {}	
	public void windowActivated(WindowEvent e) {}
	
	public class MenuPanel_over_linsteners implements events.Panel_over_linstener_jiekou{	//MenuPanel�����¼��Ĵ�����
		public void EventActivated(events.Panel_over_event me) 
		{		
		System.out.println("ִ��menu����overʱ�亯����");
		remove(menupanl);	//��ʾ�ĵ���remove��������
		//repaint();
		//menupanl.	//�������ɾ�����jpanel�ؼ�����
		gamepanl = null;
		gamepanl = new GamePanel(opts.kuan,opts.gao);	//�����Ϸ���GamePanel		
		gamepanl.setBounds(0,0,opts.kuan,opts.gao);
		add(gamepanl);
		gamepanl.requestFocus();
		PanelType = game.PANEL_TYPE_GMAEPANEL;
		System.out.println("��ʼ����");
		gamepanl.addPanel_over_linstener(new GamePanel_over_linsteners());
		gamepanl.startdraw();			
		} 
	}
	
	public class GamePanel_over_linsteners implements events.Panel_over_linstener_jiekou{	//MenuPanel�����¼��Ĵ�����
		public void EventActivated(events.Panel_over_event me) 
		{		
			remove(gamepanl);
			gamepanl = null;
			repaint();
		} 
	}
	
	private class button1_click extends MouseAdapter	//һ��ʼ�����ã������Ѿ�����
	{
		public void mouseClicked(MouseEvent e)
		{
			gamepanl.startdraw();
			//System.out.println("sdfsdafasd");
		}
	}	
}


