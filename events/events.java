package events;
import  java.util.EventObject;
import  java.util.EventListener;
public class events {
	static public class Panel_over_event extends EventObject{	//һ�����������¼�����
		public Panel_over_event(Object source){
			super(source);
		}
	}
	static public class Add_zidan_event extends EventObject{	//����һ���ӵ����¼�����
		public int x;	//�ӵ���x��y���ꣻ����Ӧ�ô洢ÿ��x��y�ƶ��Ķ���
		public int y;	//�ӵ���x��y���ꣻ
		public boolean ismy;	//�ǻ궷�޵��ӵ����ǵ��˵��ӵ���
		public int jiaodu;	//�ӵ����ʱ��ĽǶȡ�
		
		public Add_zidan_event(int x,int y,boolean ismy,int jiaodu,Object source){
			super(source);
			this.x		= x;
			this.y		= y;
			this.ismy	= ismy;
			this.jiaodu	= jiaodu;
		}
	}
	/*
	static public class MenuPanel_over_linstener implements MenuPanel_over_linstener_jiekou{
		public void EventActivated(events.MenuPanel_over_event me) 
		{ 
		System.out.println("�¼��Ѿ�������"); 
		} 
	}
	*/
	public interface Panel_over_linstener_jiekou extends EventListener{	//EventListener��һ���ӿڣ�����ΪʲôҪ��extends�̳ж�������implementsʵ�ֽӿڣ�
		public void EventActivated(events.Panel_over_event me);
	}
	public interface Add_zidan_jiekou extends EventListener{	//EventListener��һ���ӿڣ�����ΪʲôҪ��extends�̳ж�������implementsʵ�ֽӿڣ�
		public void add_zidan(events.Add_zidan_event event);
	}
}
/*
 * 		�¼���������
 * �¼��������Ҫ������һЩ��Ϣ
 * ����������һ�����д����¼���������
 * 
 * ע����������ǰ�һ�������������ã�ָ�룩���ݸ�Ҫע����¼�Դ�ࡣ���¼�����ʱ���¼�Դ���
 * �����ָ����ü�������� * ����������
 * 
 * Ҫ��ʵ���Զ����¼���Ҫ���¼�Դ�������ʵ�֣���addDemoListener����������������ָ����
 * �ӵ��Լ��ļ��������鼯�У�����Ҫע��ͬ�����⣩
 */
