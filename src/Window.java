import java.awt.Dimension;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowStateListener;
import javax.swing.JFrame;
import javax.swing.JPanel;

//������� ����� - Window
public class Window extends JFrame{
	//������� ������
	JPanel panel0;
	//�����
	StoreExtend St=new StoreExtend();
	//�����
	SmetaExtend Sm=new SmetaExtend();
	//��� ��������� ������� ���� �������� ������ �����������
	void reswin() {
		Sm.setBounds(0, 0,panel0.getWidth(), panel0.getHeight());
		St.setBounds(0, 0,panel0.getWidth(), panel0.getHeight());		
		validate();
		repaint(); 		
	}
	//����������� ����
	Window(){
		//����������� �����������
		super("SmBuilder");
		//��������� ��������� ��� ������� �� �������
		setDefaultCloseOperation(EXIT_ON_CLOSE); 
		panel0=new JPanel();
		panel0.setLayout(null);
		//������ ��������� ������� ����
		addWindowStateListener(
				  new WindowStateListener()
				  {
				    public void windowStateChanged(WindowEvent e)
					{		
				    	//�������������� �������
						  reswin() ;
						  Sm.reswin();
							St.reswin();
							Sm.reBuild();
							St.reBuild(); 
					}
				  });
		
		//�������� ��������� ����� �������������� ���� ��� ��������� �������� ����
		addComponentListener(new ComponentListener() {
			@Override
			public void componentHidden(ComponentEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void componentMoved(ComponentEvent arg0) {
				// TODO Auto-generated method stub
				reswin();
			}

			@Override
			public void componentResized(ComponentEvent arg0) {
				reswin();
			}

			@Override
			public void componentShown(ComponentEvent arg0) {
				// TODO Auto-generated method stub
				reswin();
			}
		});
		
	
		
		panel0.add(Sm);
		St.setVisible(false);
		panel0.add(St);
		//������ ������� ������
		setContentPane(panel0);
		//������ �� ���������
	 	setSize(1024,400);	
	 	reswin();
	 	//������ ����������� ������� ����
	 	setMinimumSize(new Dimension(720,520));
	 	setResizable(true);
	 	setVisible(true);
	}
	//�� �� ����� ����� ������ �������������� ���� �����
	class SmetaExtend extends Smeta{
		@Override 
		void actions(int act){
			if(act==Smeta.TO_STORE){
				//���� �������� ������� �� ����� �� �������� �����
				setVisible(false);
				St.setVisible(true);
			}
		}
	}
	//�� �� ����� ����� ������ �������������� ���� �����
	class StoreExtend extends Store{
		@Override 
		void actions(int act){
			if(act==Store.TO_SMETA){
				//���� �������� ������� � ����� �� ���������
				setVisible(false);
				Sm.setVisible(true);
			}else
			if(act==Store.ADD_TO_SMETA){
				//���� �������� �������� � ����� �� ���������
				Material localm=getActionMaterial();
				if(localm!=null)
				Sm.addMaterial(localm.copy());
			}
		}
	}
	//����� �����
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//������� ����
		new Window();
	}

}

