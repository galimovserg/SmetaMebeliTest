import java.awt.Dimension;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowStateListener;
import javax.swing.JFrame;
import javax.swing.JPanel;

//главный класс - Window
public class Window extends JFrame{
	//главная панель
	JPanel panel0;
	//Склад
	StoreExtend St=new StoreExtend();
	//Смета
	SmetaExtend Sm=new SmetaExtend();
	//при изменении размера окна изменяем размер содержимого
	void reswin() {
		Sm.setBounds(0, 0,panel0.getWidth(), panel0.getHeight());
		St.setBounds(0, 0,panel0.getWidth(), panel0.getHeight());		
		validate();
		repaint(); 		
	}
	//конструктор окна
	Window(){
		//конструктор суперкласса
		super("SmBuilder");
		//программа закроется при нажатии на крестик
		setDefaultCloseOperation(EXIT_ON_CLOSE); 
		panel0=new JPanel();
		panel0.setLayout(null);
		//вешаем слушатель события окна
		addWindowStateListener(
				  new WindowStateListener()
				  {
				    public void windowStateChanged(WindowEvent e)
					{		
				    	//перерисовываем контент
						  reswin() ;
						  Sm.reswin();
							St.reswin();
							Sm.reBuild();
							St.reBuild(); 
					}
				  });
		
		//добаляем слушатель чтобы перерисовывать окно при изменении размеров окна
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
		//задаем главную панель
		setContentPane(panel0);
		//размер по умолчанию
	 	setSize(1024,400);	
	 	reswin();
	 	//задаем минимальные размеры окна
	 	setMinimumSize(new Dimension(720,520));
	 	setResizable(true);
	 	setVisible(true);
	}
	//та же самая смета только переопределили один метод
	class SmetaExtend extends Smeta{
		@Override 
		void actions(int act){
			if(act==Smeta.TO_STORE){
				//если действие перейти на склад то скрываем смету
				setVisible(false);
				St.setVisible(true);
			}
		}
	}
	//та же самый склад только переопределили один метод
	class StoreExtend extends Store{
		@Override 
		void actions(int act){
			if(act==Store.TO_SMETA){
				//если действие перейти в смету то переходим
				setVisible(false);
				Sm.setVisible(true);
			}else
			if(act==Store.ADD_TO_SMETA){
				//если действие добавить в смету то добавляем
				Material localm=getActionMaterial();
				if(localm!=null)
				Sm.addMaterial(localm.copy());
			}
		}
	}
	//точка входа
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//создаем окно
		new Window();
	}

}

