import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import java.util.ArrayList;

import javax.swing.JButton;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextField;

//класс Склад
public class Store extends JPanel{
	//константы указывающие на действия
	static final int TO_SMETA=0;
	static final int ADD_TO_SMETA=1;
	
	
//сплитер разделяющий таблицу материалов и параметров	
JSplitPane content;	
//верхняя часть, где располагается таблица с материалами
	JPanel pntop;
		//скролл необходимый для таблицы table
		JScrollPane scrollPane;
		//таблица где выводится список материалов
		JMultiTable table;
		//панель с кнопками
		JPanel navpanel;
			//кнопка для перехода к смете
			JButton btsmeta;
			//кнопка добавления материала в смету
			JButton btadd;
			//кнопка создания нового материала
			JButton btcreate;
			//кнопка удаления материала из склада
			JButton btdelete;
//нижняя часть где располагается таблица с параметрами одного материала
	JPanel pnbottom;
		//скролл необходимый для таблицы table2
		JScrollPane scrollPane2;
		//таблица где выводится список параметров одного материала	
		JMultiTableTwo table2;
		//панель с кнопками
		JPanel navpanel2;
			//надпись F
			JLabel lbfunction;
			//текстовое поле для ввода формулы стоимости материала
			JTextField tffunction;
			//кнопка добавления нового параметра
			JButton btedit;
			//кнопка удаления параметра
			JButton btparamdel;
			//кнопка применить, применения измененых данных
			JButton btparamedit;
			//просто заголовки для столбцев таблицы материалов(table)
	String[] columnNames = {
	           "Код",
	           "Наименование",
	           "Класс"
	     };
	//просто заголовки для столбцев таблицы мпараметров(table2)
	String[] columnNames2={"Параметр","Имя","Тип","Значение"};
	
	//показывает индекс выделенного материала
	int selectmaterial=-1;
	//показывает индекс выделенного параметра
	int selectparametr=-1;       
	
	//список материалов
	ArrayList<Material> materials=new ArrayList<Material>();		
	//возвращает выбранный материал
	Material getActionMaterial(){
		if(selectmaterial>=0&&selectmaterial<materials.size()){
			return materials.get(selectmaterial);
		}else{
			return null;
		}
	}
	//вызывается для построения демо-списка материалов
	void createdemo(){
		Material m=new Material(0,"ручка(EROS_RS286)" ,"ручка-скобка" , new ArrayList<Parametr>(), "stoimost",1);
		m.addParametr(new Parametr("Стоимость", "stoimost", TypeName.floatType, "303.16"));
		materials.add(m);
		
		m=new Material(1,"брус" ,"дерево" , new ArrayList<Parametr>(), "w*h*length*2.5",1);
		m.addParametr(new Parametr("Длина(м)", "length", TypeName.floatType, "5.10"));
		m.addParametr(new Parametr("Ширина(см)", "w", TypeName.floatType, "8"));
		m.addParametr(new Parametr("Высота(см)", "h", TypeName.floatType, "4"));
		materials.add(m);
		reBuild();
	}
	//при изменение размера окна происходит вызов этого метода и вычисление макета
	void reswin(){
		// TODO Auto-generated method stub
		content.setBounds(300, 0, getWidth()-300, getHeight());
		
		navpanel.setBounds(0,pntop.getHeight()-30, pntop.getWidth(), 30);
		navpanel2.setBounds(0,pnbottom.getHeight()-60, pnbottom.getWidth(), 60);
		scrollPane.setBounds(0, 0, pntop.getWidth(), pntop.getHeight()-30);
		scrollPane2.setBounds(0, 0, pnbottom.getWidth(), pnbottom.getHeight()-60);
		btdelete.setBounds(pntop.getWidth()-100, 0, 100, 30);
		btedit.setBounds(pnbottom.getWidth()-100, 30, 100, 30);
		tffunction.setBounds(30, 0, navpanel2.getWidth()-30, 30);		
		if(pntop.getHeight()<content.getHeight()-200){
					
		}else{
					
			content.setDividerLocation(content.getHeight()-200);
		}
		validate();
	}
	//выделить параметр
	void selectParametr(int index) {
		if(index>=0){
			selectparametr=index;
			
		}
	}
	//вызывается когда пользователь выбирает материал и отображает его параметры
	void selectMaterial(int index){
		//если индекс в нужном диапазоне
		if(index>=0&&index<materials.size()){
			selectmaterial=index;
			Material m=materials.get(index);
			ArrayList<Parametr> params=m.getParametrs();
			//в data записываются параметры выделенного материала
			String[][] data=new String[params.size()+4][];
			data[0]=new String[4];
			data[0][0]="Код";
			//в формуле никак не используется
			data[0][1]="null";
			data[0][2]=TypeName.intType.toString();
			data[0][3]=String.valueOf(m.getID());
			data[1]=new String[4];
			data[1][0]="Наименование";
			//в формуле никак не используется
			data[1][1]="null";
			data[1][2]=TypeName.stringType.toString();
			data[1][3]=m.getName();
			data[2]=new String[4];
			data[2][0]="Класс";
			//в формуле никак не используется
			data[2][1]="null";
			data[2][2]=TypeName.stringType.toString();
			data[2][3]=m.getClassname();
			data[3]=new String[4];
			data[3][0]="Множитель";
			//в формуле никак не используется
			data[3][1]="null";
			data[3][2]=TypeName.intType.toString();
			data[3][3]=String.valueOf(m.getCount());
			//пораметры которые добавил сметчик
			for(int i=0;i<params.size();i++){
				Parametr pp=params.get(i);
				data[i+4]=new String[4];
				//наименование параметры
				data[i+4][0]=pp.getCaption();
				//его имя используемое в формуле
				data[i+4][1]=pp.getName();
				//тип параметра
				data[i+4][2]=pp.getType().toString();
				//значение параметра
				data[i+4][3]=pp.getValue();
				
			}
			//обновляем содержимое таблицы
			table2.setContent(data, columnNames2);
			//очищаем содержимое панели
		    pnbottom.removeAll();
		    pnbottom.add(scrollPane2);
		    pnbottom.add(navpanel2);
		    tffunction.setText(m.getFormula());
		      //пересчитываем макет
		      reswin();
		}
	}
	//создает новый материал
	void addMaterial(){
		int id=materials.size();
		Material m=new Material(id,"имя","имя класса",new ArrayList<Parametr>(),"",1);
		materials.add(m);
		reBuild();
		reswin();
	}
	//добавляет новый параметр к выделенному материалу
	void addParametr(int index) {
		if(index>=0&&index<materials.size()){
			Material m=materials.get(index);
			m.addParametr(new Parametr("новый","name",TypeName.intType,"0"));
			selectMaterial(index);
		}
	}
	//удаляет выделенный параметр
	void deleteParametr(int indexm,int indexp) {
		if(indexm>=0&&indexm<materials.size()&&indexp>=0){
			Material m=materials.get(indexm);
			m.deleteParametr(indexp);
			selectMaterial(indexm);
		}
	}
	//задает параметры для материалы
	void setParametrs(){
		if(selectmaterial>=0&&selectmaterial<materials.size()){
			String data[][]=table2.getData();
			//если содержимое непустое
			if(data!=null) {
				 ArrayList<Parametr> paramlist= new ArrayList<Parametr>();
				 //формируем список параметров
				 for(int i=4;i<data.length;i++) {
					 Parametr p=new Parametr(data[i][0], data[i][1], new TypeName(data[i][2]), data[i][3]);
					 paramlist.add(p);
				 }
				//изменяем материл(передаем ему имя,множитель, параметры и т.д
				materials.get(selectmaterial).edit(Integer.valueOf(data[0][3]), data[1][3], data[2][3], paramlist,tffunction.getText(), Integer.valueOf(data[3][3]));;
				reBuild();
				reswin();
			}
			
			
		}
	}
	//удаляет выбранный материал
	void deleteMaterial(int index) {
		 if(index>=0&&index<materials.size()){
			 materials.remove(index);
			 reBuild();
			 reswin();
		 }
		 
	}
	
	//при вызове этого метода обновляется таблица материалов
	void reBuild(){
		//даные записываются в data
		String[][] data=new String[materials.size()][];
		for(int i=0;i<materials.size();i++){
			Material m=materials.get(i);
			data[i]=new String[3];
			//поле Код
			data[i][0]=String.valueOf(m.getID());
			//Поле Имя
			data[i][1]=m.getName();
			//Поле Имя класса
			data[i][2]=m.getClassname();
			
			
		}	
		//и передаются таблице
		table.setContent(data, columnNames);
	    pntop.removeAll();
	    pntop.add(scrollPane);
	    pntop.add(navpanel);
	      
	}
	Store(){
		
		setLayout(null);
		//добавляем верхнюю и нижнюю панель
		pntop=new JPanel();
		pntop.setLayout(null);
		pnbottom=new JPanel();
		pnbottom.setLayout(null);
		
		
      table2 =new JMultiTableTwo(new String[][]{},columnNames2);
		//строим таблицу на основе абстрактной 
      JTable ltable2=new JTable(table2); 
		 //колонки менять местами нельзя
	      ltable2.getTableHeader().setReorderingAllowed(false);
	     //слушатель
	     ltable2.addMouseListener( new MouseAdapter()
		 	{
		 		public void mousePressed(MouseEvent event) {
		 			//выделяем параметр
		 			int r=ltable2.getSelectedRow();
		 			selectParametr(r);
		 			
		 		}
		 	});
     
      
      //создаем скролл и передаем ей содержимое(таблицу)
		scrollPane2 = new JScrollPane(ltable2);
		
		
		lbfunction=new JLabel("F");
		lbfunction.setBounds(5, 0, 30, 30);
		lbfunction.setFont(new Font("Dialog", Font.ITALIC, 24));
		tffunction=new JTextField("");
		
		//добавляем кнопку Применить
		btedit=new JButton("Применить");
	    btedit.setBounds(pntop.getWidth()-100, 30, 100, 30);
	    btedit.addMouseListener( new MouseAdapter()
	 	{
	 		public void mousePressed(MouseEvent event) {
	 			//нажали кнопку применить
	 			setParametrs();
	 		}
	 	});
	    //добавляем кнопку Удалить параметр
	    btparamdel=new JButton("Удалить параметр");
	    btparamdel.setBounds(150, 30, 150, 30);
	    btparamdel.addMouseListener( new MouseAdapter()
	 	{
	 		public void mousePressed(MouseEvent event) {
	 			//нажали кнопку Удалить параметр
	 			deleteParametr(selectmaterial,selectparametr-4);
	 		}
	 	});
	    //добавляем кнопку Добавить параметр
	    btparamedit=new JButton("Добавит параметр");
	    btparamedit.setBounds(0, 30, 150, 30);
	    btparamedit.addMouseListener( new MouseAdapter()
	 	{
	 		public void mousePressed(MouseEvent event) {
	 			//нажали кнопку Добавить параметр
	 			addParametr(selectmaterial);
	 			
	 		}
	 	}); 
	    
	    //добавили панель навигации для нижне части (для списка параметра)
	    navpanel2=new JPanel();
	    navpanel2.setLayout(null);
	    navpanel2.setBounds(pnbottom.getHeight()-30, 0, 30, pnbottom.getWidth());
	   
	    navpanel2.add(lbfunction);
	    navpanel2.add(tffunction);
	    tffunction.setBounds(30, 0, navpanel2.getWidth()-30, 30);
	    navpanel2.add(btedit);
	    
	    navpanel2.add(btparamedit);
	    navpanel2.add(btparamdel);
	    
	    pnbottom.add(scrollPane2);
	    pnbottom.add(navpanel2);
	    
		
      
       
      
      table=new JMultiTable(new String[][] {},columnNames);
		 JTable ltable=new JTable(table); 
	      ltable.getTableHeader().setReorderingAllowed(false);
	     
	     ltable.addMouseListener( new MouseAdapter()
		 	{
		 		public void mousePressed(MouseEvent event) {
		 			int r=ltable.getSelectedRow();
		 			//выделяем материал
		 			selectMaterial(r);
		 			
		 		}
		 	});
	      
	      
	  scrollPane = new JScrollPane();
	  scrollPane.setViewportView(ltable);
	      
      scrollPane = new JScrollPane(ltable);	
      
      navpanel=new JPanel();
      navpanel.setLayout(null);
      navpanel.setBounds(pntop.getHeight()-30, 0, 30, pntop.getWidth());
      scrollPane2.setBounds(0, 0, pnbottom.getWidth(), pnbottom.getHeight());
      
      //добавили кнопку перехода в Смету
      btsmeta=new JButton("Смета");
      btsmeta.setBounds(0, 0, 100, 30);
      
      btsmeta.addMouseListener( new MouseAdapter()
	 	{
	 		public void mousePressed(MouseEvent event) {
	 			//нажали на кнопку Смета, переход в смету
	 			actions(TO_SMETA);
	 		}
	 	});
      //добавили кнопку Добавить
      btadd=new JButton("Добавить");
      btadd.setBounds(100, 0, 100, 30);
      btadd.addMouseListener( new MouseAdapter()
	 	{
	 		public void mousePressed(MouseEvent event) {
	 			//нажали на кнопку добавить, добавляем материал в смету
	 			actions(ADD_TO_SMETA);
	 		}
	 	});
      btcreate=new JButton("Создать");
      btcreate.setBounds(200, 0, 100, 30);
      btcreate.addMouseListener( new MouseAdapter()
	 	{
	 		public void mousePressed(MouseEvent event) {
	 			
	 			addMaterial();
	 		}
	 	});
      btdelete=new JButton("Удалить");
      btdelete.setBounds(pntop.getWidth()-100, 0, 100, 30);
     btdelete.addMouseListener( new MouseAdapter()
	 	{
	 		public void mousePressed(MouseEvent event) {
	 			
	 			deleteMaterial(selectmaterial);
	 			
	 			
	 		}
	 	});
      navpanel.add(btsmeta);
      navpanel.add(btadd);
      navpanel.add(btcreate);
      navpanel.add(btdelete);
      
      pntop.add(scrollPane);
      pntop.add(navpanel);
      
      pntop.addComponentListener(new ComponentListener() {
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
		
		
		addComponentListener(new ComponentListener() {
			@Override
			public void componentHidden(ComponentEvent arg0) {
				// TODO Auto-generated method stub
			}

			@Override
			public void componentMoved(ComponentEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void componentResized(ComponentEvent arg0) {
				// TODO Auto-generated method stub      
				reswin();
				
			}

			@Override
			public void componentShown(ComponentEvent arg0) {
				reswin();			
			}
		});
		
		
		
		
		
		//располагаем по вертикали, то есть горизонтальная линия разделения
		content = new JSplitPane(JSplitPane.VERTICAL_SPLIT, pntop, pnbottom);
		pnbottom.setMinimumSize(new Dimension(200,200));
		//размер разделительной полосы
		content.setDividerSize(8);
		//ее позиция
		content.setDividerLocation(600);
		//моментальная перерисовка при вмешательстве пользователя
		content.setContinuousLayout(true);
		
		
		add(content);
		//демо вариант
		createdemo();
	}
	//вызывается в определеных ситуациях
	void actions(int act){
		//act номер действия
	}
	
	 
}
