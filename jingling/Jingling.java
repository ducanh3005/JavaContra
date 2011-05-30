package jingling;

import java.awt.Graphics2D;
import java.awt.MediaTracker;
import java.awt.image.BufferedImage;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.Component;
import java.lang.String;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Vector;

import events.events;
import map.GameMap;
import jingling.Zidanlei;


public class Jingling extends Component{
	public static final int DZ_ZOU		= 0;	//�����õ���ö��
	
	public static final int DZ_TING		= 3;
	public static final int DZ_PA		= 5;
	public static final int DZ_FX_QIAN	= 6;
	public static final int DZ_FX_HOU	= 7;
	
	public int linjiea;	//�����ڵ�ͼ��ͷ���ٽ�����������ƶ���ͼ��Ӧ�ƶ�����
	public int linjieb;
	public int zongx;	//�����������ؿ��е�λ��
	public int chaolinjie; 	//���ߵ���ͼ��β�����ٽ����ǣ��������ٽ����
	
	public int x,y;	//��ǰͼƬ�����꣬���½ǵ� ��
	private int dongzuo_fx_current;	//��ǰ�Ķ����ķ���
	private int dongzuo_type_current;	//��ǰ����������:��,��,ͣ
	private int dongzuo_tyep_yonghu;	//�û�Ҫ��Ķ�������	
	
	private boolean is_tiao;	//�ǲ���������״̬
	private boolean tiao_state;	//����״̬������Ϊtrue
	private int tiao_heigh;	//��ǰ���ĸ߶�
	public int tiao_max;	//������߸߶�
	
	private Dongzuo zou_qian;	//���ֶ�����ͼƬ
	private Dongzuo zou_hou;
	private Dongzuo ting_qian;
	private Dongzuo ting_hou;
	private Dongzuo pa_qian;
	private Dongzuo pa_hou;
	private Dongzuo tiao;
	
	//private ArrayList mapdipingxian;	//��ͼ�ϵĵ�ƽ�ߣ��������������ǲ����ڵ�ƽ�����ߣ�����Ӧ���µ���
	private float diao_num;	//���µ����ٶ�
	
	private Vector repository;	//�����¼���������
	private Vector addzidanvect;	
	events.Panel_over_linstener_jiekou dl;
	
	
	
	public Jingling(int x, int y,int zongchang,int huamianchang){
		this.x = x;
		this.y = y;
		this.zongx = x;	
		this.linjiea = x;
		//this.linjieb = zongchang - (huamianchang - x);
		this.linjieb = zongchang - (huamianchang - x) - 3;// - 23;
		
		repository = new Vector();
		addzidanvect = new Vector();
		
		is_tiao = false;
		tiao_state = true;
		tiao_heigh = 0;
		tiao_max = 60;
		diao_num = 0;
		
		dongzuo_fx_current = Jingling.DZ_FX_QIAN;
		dongzuo_type_current = Jingling.DZ_TING;
		zou_qian	= new Dongzuo("image\\jingling_zou_qian.png",3);
		zou_hou		= new Dongzuo("image\\jingling_zou_hou.png",3);
		ting_qian	= new Dongzuo("image\\jingling_ting_qian.png",1);
		ting_hou	= new Dongzuo("image\\jingling_ting_hou.png",1);
		pa_qian		= new Dongzuo("image\\jingling_pa_qian.png",1);
		pa_hou		= new Dongzuo("image\\jingling_pa_hou.png",1);
		tiao		= new Dongzuo("image\\jingling_tiao.png",4);
	}
	private void move(){
		this.y +=this.diao_num;
		
		if(y > 400){	//����������û
			System.out.println("�����ˣ�������");
			shijian(new events.Panel_over_event(this));
		}
		
		if(dongzuo_type_current == DZ_ZOU){			
			switch(dongzuo_fx_current){
				case DZ_FX_QIAN :	//��ǰ��	
					if(zongx <= linjiea){	//��ǰ�濪ͷ�ٽ������ߣ��п����߳��ٽ�����
						if(x > linjiea){	//�Ѿ��߳��ٽ���
							x = linjiea;
							zongx = linjiea;
						}
						else
						{
							x++;	//��û���߳��ٽ���
						}
					}
					if(zongx > linjieb){	//��β�����ٽ�������Ҫ����������߳����棩						
						x++;
						chaolinjie++;
					}
					if(zongx >= linjiea && zongx <= linjieb){	//�ڷ��ٽ������м�����						
						zongx++;
					}
					break;
				case DZ_FX_HOU :	//�����		
					if(zongx <= linjiea)	//��ͷ�ٽ�����Ҫ���ǻ��߳�����
						x--;
					if(zongx > linjieb){	//��β�ٽ���
						if(chaolinjie < 0){	//�Ѿ��߳��ٽ���	//����ж��д���
							//x = linjieb;
							zongx = linjieb;
						}
						else
						{
							x--;
							chaolinjie--;
						}					
					}
					if(zongx >= linjiea && zongx <= linjieb)	//�ڷ��ٽ������м��
						zongx--;
					break;
			}
		}
		if(this.is_tiao == true){	//������Ծ	
			if(this.tiao_state == true){	//��������
				y--;
				this.tiao_heigh++;
				if(this.tiao_heigh > this.tiao_max)	//��������˶�
					this.tiao_state = false;				
			}
			else{
				y++;
				this.tiao_heigh--;
				if(this.tiao_heigh == 0){	//�����˿�ʼ����λ��
					this.tiao_state = true;
					this.is_tiao = false;
				}
			}
		}
		
		
		
			//�������µ���������ȵõ���ǰy���ڵ�ƽ�ߵĸ߶ȣ������Ƿ�Ϊ��
			
				boolean kong = true;
				int dpx_height = 500;	//��ǰ��ƽ�ߵĸ߶�
				
				for(int i = 0;i < GameMap.dipingxians.size();i++){
					if(this.y > ((GameMap.Dipingxian)GameMap.dipingxians.get(i)).shang && this.y <= ((GameMap.Dipingxian)GameMap.dipingxians.get(i)).xia){	//�õ���ǰλ�õĵ�ƽ��
						dpx_height = ((GameMap.Dipingxian)GameMap.dipingxians.get(i)).xia;	//���õ�ǰ��ƽ�ߵĸ߶�		
						
						for(int j =0;j < ((GameMap.Dipingxian)GameMap.dipingxians.get(i)).duans.size();j++){	//�õ���ǰ����λ���Ƿ����Ϊ�յ�
							if( this.zongx > ((GameMap.Map_duan)((GameMap.Dipingxian)GameMap.dipingxians.get(i)).duans.get(j)).start && this.zongx < ((GameMap.Map_duan)((GameMap.Dipingxian)GameMap.dipingxians.get(i)).duans.get(j)).end ){
								kong = ((GameMap.Map_duan)((GameMap.Dipingxian)GameMap.dipingxians.get(i)).duans.get(j)).kong ;
								break;
							}
						}
						break;
					}					
				}
				//System.out.println(y);////////////////////////////////////////				
				//System.out.println("��ǰ�ĵ�ƽ�ߣ�" + dpx_height);
				
				//System.out.println("��ǰ��ƽ�ߣ�" + dpx_height + "��ǰ�գ�" + kong);
				
				/*
				for(int i = 0;i < mapdipingxian.size();i++){	//�õ���ǰ����λ���Ƿ����Ϊ�յ�
					if(this.zongx > ((GameMap.Map_duan)mapdipingxian.get(i)).start && this.zongx < ((GameMap.Map_duan)mapdipingxian.get(i)).end){
						kong = ((GameMap.Map_duan)mapdipingxian.get(i)).kong ;
						break;
					}
				}
				*/
				
				if(this.is_tiao != true){	//�������ǲ��ǿյģ���Ҫ��ֹӰ������Ч��	
					
						if(kong == false || this.y < dpx_height){	//�����ǿյĻ����µ�
							System.out.println("���µ���");	
							this.diao_num += 0.07;							
						}	
						if(kong == true){	//��ǰyλ�ú͵�ƽ�߲�಻��һ�μ��Ļ�ֱ�ӵ���ƽ��
							if(dpx_height - this.y < this.diao_num ){
								this.diao_num = 0;	//���������⣬������һ�������µ���������ȡ�����µ�
								this.y = dpx_height;
							}
						}
				}
				
				/*
				if(this.is_tiao != true){	//�������ǲ��ǿյģ���Ҫ��ֹӰ������Ч��	
				
					if(kong == false || this.y < dpx_height){	//�����ǿյĻ����µ�
						System.out.println("���µ���");	
						this.diao_num += 0.1;
						if(dpx_height - this.y < this.diao_num ){	//��ǰyλ�ú͵�ƽ�߲�಻��һ�μ��Ļ�ֱ�ӵ���ƽ��
							this.diao_num = 0;	//���������⣬������һ�������µ���������ȡ�����µ�
							this.y = dpx_height;
						}
					}					
				}
				*/
				if(this.is_tiao == true && this.tiao_state == false){	//�ӵ͵�ƽ�������ߴ��ĵ�ƽ���ϵ����
					if(y == dpx_height && kong == true){	//�Ѿ����˸ߴ���ƽ�ߵĸ߶�
						this.is_tiao = false;
						this.tiao_state = true;
						this.tiao_heigh = 0;
						this.diao_num = 0;
					}
				}
				
				
				//if( (kong == false || this.diao_num != 0)){	//���µ�
				//	this.diao_num += 0.05;
				//}
			}
		//*/
	
	public void setdongzuo(int type){		
		this.dongzuo_tyep_yonghu	= type;	//��ǰ�ĺ��û��ڴ��ģ��д��Ľ���
		this.dongzuo_type_current	= type;	
	}
	public void tiao(){		
		if(this.dongzuo_type_current == Jingling.DZ_PA){
			System.out.println("���ڵ�ǰſ�������������Ե���ȥ�ˡ�");
			y++;	//�����ƶ�һ�����أ��ᵼ�µ�ƽ�߻��������һ����ƽ�ߣ��ﵽ����������Ч��
			this.dongzuo_type_current = Jingling.DZ_TING;
		}
		else{
			if(this.diao_num == 0)	//���µ���ʱ������������
				System.out.println("����������");				
				//System.out.println(y);				
				this.is_tiao = true;
		}
	}
	public void setfangxiang(int type){
		//this.dongzuo_tyep_yonghu	= type;	//��ǰ�ĺ��û��ڴ��ģ��д��Ľ���
		this.dongzuo_fx_current	= type;
	}
	public void setxy(int x,int y){
		this.x = x;
		this.y = y;
	}
	/* ���ڰ�GameMap�еı�����Ϊ��̬��������ɾȥ
	public void set_map_dipingxian(ArrayList dpx){
		this.mapdipingxian = dpx;		
		System.out.println("��ʼ�ھ����в��Ե�ͼ");	//����
		for(int i = 0;i < mapdipingxian.size();i++){			
			System.out.println(((GameMap.Map_duan)mapdipingxian.get(i)).start);			
		}		
	}
	*/
	public Rectangle getRectangle(){
		return new Rectangle(x,y,zou_qian.heigth,zou_qian.width);
	}
	public int getdongzuo(){
		return this.dongzuo_type_current;
	}
	public int getfangxiang(){
		return this.dongzuo_fx_current;
	}
	public void todo(Graphics2D g){	
		this.move();
		if(this.is_tiao == true){ 			//��
			tiao.draw(g);
		}
		else
		{
			switch(dongzuo_type_current){
				case Jingling.DZ_ZOU :		//��
					if(dongzuo_fx_current == Jingling.DZ_FX_QIAN)
						zou_qian.draw(g);
					else
						zou_hou.draw(g);
					break;			
				case Jingling.DZ_TING :		//ͣ
					if(dongzuo_fx_current == Jingling.DZ_FX_QIAN)
						ting_qian.draw(g);
					else
						ting_hou.draw(g);
					break;
				case Jingling.DZ_PA :		//ſ
					if(dongzuo_fx_current == Jingling.DZ_FX_QIAN)
						pa_qian.draw(g);
					else
						pa_hou.draw(g);
					break;			
			}
		}
		//g.setColor(java.awt.Color.RED);	//������
		//g.drawString("zongx:" + String.valueOf(zongx) + " x:" + String.valueOf(x) + " A:" + String.valueOf(linjiea) + " B:" + String.valueOf(linjieb),20,20);
	}	
	
	public void shijian(events.Panel_over_event event) {	//����ִ���¼�
	    Enumeration enum = repository.elements();//�ⲽҪע��ͬ������
	    while(enum.hasMoreElements())
	    {
	    	dl = (events.Panel_over_linstener_jiekou)enum.nextElement();
	    	dl.EventActivated(event);
	    }
	}
	public void add_zidan(){
		Enumeration enum = addzidanvect.elements();//�ⲽҪע��ͬ������
		int fx = 0;
		int zdx = this.zongx;
		int zdy = this.y - 31;
		switch(this.dongzuo_fx_current){
			case Jingling.DZ_FX_QIAN :
				fx = Zidanlei.ZIDAN_JIAODU_90;
				zdx = this.zongx;				
				break;
			case Jingling.DZ_FX_HOU :
				fx = Zidanlei.ZIDAN_JIAODU_270;
				zdx = this.zongx - 35;
				break;
		}
		if(this.dongzuo_type_current == Jingling.DZ_PA){	//�����ſ�ŵĻ����ӵ�������Ӧ�ø���һЩ
			zdy += 17;
		}
	    while(enum.hasMoreElements())
	    {	    	
	    	((events.Add_zidan_jiekou)enum.nextElement()).add_zidan(new events.Add_zidan_event(zdx,zdy,true,fx,this));	    	
	    }
	}
	public void addPanel_over_linstener(events.Panel_over_linstener_jiekou hdl) {	//�Զ����¼����¼�Դ������	
		repository.addElement(hdl);//�ⲽҪע��ͬ������
	}
	public void addjia_zidan_listener(events.Add_zidan_jiekou hdl) {	//�Զ����¼����¼�Դ������	
		addzidanvect.addElement(hdl);//�ⲽҪע��ͬ������
	}
	
	class Dongzuo extends Component{
		private int width,heigth;//ÿһ��ͼƬ�ĳ�����
		private int draw_count;	//��ǰ�Ķ���ͼƬ�����˵ڼ�����
		private int img_conut;	//������ͼƬ����
		private int count_to_draw;	//ѭ�����ٴξͱ任һ��ͼ��
		private int count_draw;	//ѭ���˶��ٴ�
		private BufferedImage bfimg;
		public Dongzuo(String url,int img_conut){
			this.img_conut = img_conut;
			count_draw = 0;
			draw_count = 0;
			count_to_draw = 10;		
			
			Image img = Toolkit.getDefaultToolkit().createImage(url);		
			MediaTracker tracker = new MediaTracker(this);	//�ȴ�ͼƬ�������
			tracker.addImage(img, 0);	//�����������ͼ�񣿣�		
			try     
	        {   
				tracker.waitForAll();   //�ȴ�ȫ������
	        }   
	        catch   (Exception   ex)     
	        {   
	            System.err.println(ex.toString());   
	        }
	        
	        width	= img.getWidth(null)/img_conut;
	        heigth	= img.getHeight(null);
	        
	        bfimg = new BufferedImage(img.getWidth(null),img.getHeight(null),BufferedImage.TYPE_INT_ARGB);
			bfimg.getGraphics().drawImage(img,0,0,null);	//��ͼƬ����������ͼƬ��		
		}
		public void draw(Graphics2D g){
			
			if(count_draw < count_to_draw){
				count_draw++;
			}
			else
			{	
				count_draw = 0;			
				if(draw_count < img_conut - 1)
					draw_count += 1;
				else
					draw_count = 0;
			}				
			g.drawImage(bfimg.getSubimage(draw_count*width,0,width,heigth),x - width,y - heigth,null);			
		}	
	}
}



















