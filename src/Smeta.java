import java.awt.Dimension;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
//класс смета
public class Smeta extends JPanel{
	//константа указывающая на действие 
	static final int TO_STORE=0;
	//панель на которой задается основная информация о смете
	JPanel description;
	//числовое поле для ввода номера сметы
	JTextBoxNum tfnumber;
	//поле для ввода даты сметы
	JFormattedTextField tfdate = new JFormattedTextField(DateFormat.getDateInstance(DateFormat.SHORT));;
	//поле для ввода информации об объекте сметы
	JTextField tfobject;
	//поле о покупателе
	JTextField tfbuyer;
	//текстовое поле для ввода дополнительной информации о проекте сметы
	JTextArea tfdescription;
	//обычные надписи
	JLabel lbnumber;
	JLabel lbdate;
	JLabel lbobject;
	JLabel lbbuyer;
	JLabel lbdescription;
	//кнопка вычисленияпри нажатии на нее запускает вычислительную процедуру
	JButton btsumma;
	//надпись для вывода результата вычисления
	JLabel lbsumma;
	//штука которая делит область на две части(на верхнюю и нижнюю)
JSplitPane content;	
	JPanel pntop;
	//нужно для прокрутки содержимого table
		JScrollPane scrollPane;
			//таблица для отображения списка материалов в смете
			JMultiTable table;
		//панель где располагаются кнопки управления
		JPanel navpanel;
			//кнопка перехода на склад
			JButton btstore;
			//кнопка удаления выбранного материала
			JButton btdelete;
	JPanel pnbottom;
		//скролл нужен для прокрутки gridtb
		JScrollPane scrollPane2;
			//здесь располагаются все параметры выделенного материала
			JPanel gridtb;
			//массив для представления этого списка
			JTextField params[];
		//панель где располагаются кнопки управления
		JPanel navpanel2;
			//кнопка применить
			JButton btedit;
			//кнопка по умолчанию, значения всех параметров становится по умолчанию
			JButton btfirstedit;
		
	//названия колонок таблицы материалов (table)
	String[] columnNames = {
	         "Код",
	         "Наименование",
	         "Стоимость",
	         "Множитель",
	         "Сумма"
	 };	
	//нужно для указания на выбранный материал
	int selectmaterial=-1;
	//вызывается при изменении размеров окна и вычисляет макет
	void reswin(){
		// TODO Auto-generated method stub
		content.setBounds(300, 0, getWidth()-300, getHeight());
		description.setBounds(0,0,300,getHeight());
		navpanel.setBounds(0,pntop.getHeight()-30, pntop.getWidth(), 30);
		navpanel2.setBounds(0,pnbottom.getHeight()-30, pnbottom.getWidth(), 30);
		scrollPane.setBounds(0, 0, pntop.getWidth(), pntop.getHeight()-30);
		scrollPane2.setBounds(0, 0, pnbottom.getWidth(), pnbottom.getHeight()-30);
		btdelete.setBounds(pntop.getWidth()-100, 0, 100, 30);
		btedit.setBounds(pnbottom.getWidth()-100, 0, 100, 30);
		//нижняя часть должна быть не менее 200 высотой		
		if(pntop.getHeight()<content.getHeight()-200){
					
		}else{
					
			content.setDividerLocation(content.getHeight()-200);
		}
		validate();
	}
	
	ArrayList<Material> materials=new ArrayList<Material>();
	//задает значение параметров выделенного материала по умолчанию
    void setStore(int index){
    	if(index>=0&&index<materials.size()){
    		materials.get(index).setStore();
    		selectMaterial(index);
    	}
    }
    //добавляет материал
	void addMaterial(Material m) {
		if(m!=null){
			
			materials.add(m);
		}
		reBuild();
	}
	//удаляет выделенный материал из списка
	 void deleteMaterial(int index) {
			// TODO Auto-generated method stub
		 if(index>=0&&index<materials.size()){
			 materials.remove(index);
		 }
		 reBuild();
	}
	 //выделяет необходимый материал, отображает его параметры в списке параметров в gridtb
	void selectMaterial(int index){
		if(index>=0&&index<materials.size()){
			selectmaterial=index;
			gridtb.removeAll();
			Material m = materials.get(index);
			ArrayList<Parametr> paramlist=m.getParametrs();
			//переопределяем список полей
			params=new JTextField[paramlist.size()+3];
			
			//сначала отобразим основную информацию о материале
			//Код, его значение изменить нельзя
			gridtb.add(new JLabel("Код"));
			params[0]=new JTextField(String.valueOf(m.getID()));
			params[0].setEditable(false);
			params[0].setMaximumSize( 
				     new Dimension(Integer.MAX_VALUE, params[0].getPreferredSize().height) );
			gridtb.add(params[0]);
			//Наименование, его значение изменить нельзя
			gridtb.add(new JLabel(" Наименование"));
			params[1]=new JTextField(m.getName());
			
			params[1].setEditable(false);
			params[1].setMaximumSize( 
				     new Dimension(Integer.MAX_VALUE, params[1].getPreferredSize().height) );
			gridtb.add(params[1]);
			//Множитель, его значение изменить можно
			gridtb.add(new JLabel("Множитель"));
			params[2]=new JTextField(String.valueOf(m.getCount()));
			params[2].setMaximumSize( 
				     new Dimension(Integer.MAX_VALUE, params[2].getPreferredSize().height) );
			gridtb.add(params[2]);
			
			for(int i=0;i<paramlist.size();i++){
				params[3+i]=new JTextField(paramlist.get(i).getValue());
				//имя параметра
				gridtb.add(new JLabel(paramlist.get(i).getCaption()));
				params[3+i].setMaximumSize( 
					     new Dimension(Integer.MAX_VALUE, params[3+i].getPreferredSize().height) );
				//его значение
				gridtb.add(params[3+i]);
			}
		}
		gridtb.revalidate();
		reswin();
	}
	//задает параметры для выделенного материала
	void setParametrs(int index){
		if(index>=0&&index<materials.size()){
			String paramvalue[]=new String[params.length-3];
			int loccount=1;
			try {
				loccount=Integer.valueOf(params[2].getText());
				
			}catch(Exception ex) {
				loccount=1;
			}
			materials.get(index).setCount(loccount);
			
			for(int i=0;i<params.length-3;i++){
				paramvalue[i]=params[i+3].getText();
			}
			
			materials.get(index).setValues(paramvalue);
			
		}
	}
	//обновляет таблицу с материалами
	void reBuild(){
		String[][] data=new String[materials.size()][];
		for(int i=0;i<materials.size();i++){
			Material m=materials.get(i);
			data[i]=new String[5];
			data[i][0]=String.valueOf(m.getID());
			data[i][1]=m.getName();
			//стоимость одной единицы материала
			data[i][2]=m.resultsome();
			
			if(m.hadError()){}else{
				try{
					double d = Double.valueOf(data[i][2]);
					//округляем до двух знаков после запятой
					d = d * 100;
					int i1 = (int) Math.round(d);
					d = (double)i1 / 100;
					data[i][2]=String.valueOf(d);
				}
				catch(Exception ex){
					
				}
				
			}
			data[i][3]=String.valueOf(m.getCount());
			//общая стоимость материала(множитель умноженный на стоимость одного материала)
			data[i][4]=m.result();
			if(m.hadError()){}else{
				try{
					
					double d = Double.valueOf(data[i][4]);
					//округляем до двух знаков после запятой
					d = d * 100;
					int i1 = (int) Math.round(d);
					d = (double)i1 / 100;
					data[i][4]=String.valueOf(d);
				}
				catch(Exception ex){
					
				}
				
			}
		}
		
		table.setContent(data,columnNames );
	    pntop.removeAll();
	    pntop.add(scrollPane);
	    pntop.add(navpanel);
	    reswin();
	      
	}
	//возвращает строку с результатом вычисления
	String result(){
		double summa=0;
		boolean hadError=false;
		int poserror=0;
		//в цикле складываем стоимость каждого наименования
		for(int i=0;i<materials.size();i++){
			//получаем стоимость материала
			String localres=materials.get(i).result();
			//если при вычислении была ошибка
			if(materials.get(i).hadError()){
				//сохраним номер строки где была ошибка и отметим о ее наличии
				hadError=true;
				poserror=i;
			}else{
				//попробуем прибавить результат
				try{
					summa=summa+Double.valueOf(localres);
				}
				catch(Exception ex){
					//что-то пошло не так отметим об ошибке
					hadError=true;
					poserror=i;
				}
				
			}
		}
		//если была ошибка то сообщим о ней
		if(hadError){
			return "Произошла ошибка при вычисления стоимости (строка "+poserror+")";
		}else{
			//иначе округляем и выводи результат
			double d = summa;
			 d = d * 100;
			 int i = (int) Math.round(d);
			 d = (double)i / 100;

			return String.valueOf(d);
		}
		
	}
	Smeta(){
		
		setLayout(null);
		pntop=new JPanel();pntop.setLayout(null);
		pnbottom=new JPanel();pnbottom.setLayout(null);
		
		gridtb = new JPanel();
		gridtb.setLayout(new BoxLayout(gridtb,BoxLayout.Y_AXIS));
				
		scrollPane2 = new JScrollPane(gridtb);
		
		pnbottom.add(scrollPane2);
		btedit=new JButton("Применить");
	    btedit.setBounds(pntop.getWidth()-100, 0, 100, 30);
	   
	    btfirstedit=new JButton("По умолчанию");
	    btfirstedit.setBounds(0, 0, 120, 30);
	    btfirstedit.addMouseListener( new MouseAdapter()
	 	{
	 		public void mousePressed(MouseEvent event) {
	 			//нажали кнопку По умолчанию
	 			//задаем значения по умолчанию
	 			setStore(selectmaterial);
	 			reBuild();
	 		}
	 	}); 
	    navpanel2=new JPanel();
	    navpanel2.setLayout(null);
	    navpanel2.setBounds(pnbottom.getHeight()-30, 0, 30, pnbottom.getWidth());
	    pnbottom.add(navpanel2);
	    navpanel2.add(btedit);
	    navpanel2.add(btfirstedit);
      //создаем таблицу материалов
      table = new JMultiTable(new String[][] {}, columnNames);
     
      JTable ltable=new JTable(table); 
      ltable.getTableHeader().setReorderingAllowed(false);
      ltable.addMouseListener( new MouseAdapter()
	 	{
	 		public void mousePressed(MouseEvent event) {
	 			//при нажатии на таблицу
	 			//выделяем материал
	 			int r=ltable.getSelectedRow();
	 			selectMaterial(r);
	 			
	 		}
	 	}); 
     
      btedit.addMouseListener( new MouseAdapter()
	 	{
	 		public void mousePressed(MouseEvent event) {
	 			//нажали кнопку Применить
	 			//задаем параметры
	 			setParametrs(selectmaterial);
	 			//перестраиваем таблицу
	 			reBuild();
	 			
	 			
	 		}
	 	});
      scrollPane = new JScrollPane(ltable);	
      
      navpanel=new JPanel();
      navpanel.setLayout(null);
      navpanel.setBounds(pntop.getHeight()-30, 0, 30, pntop.getWidth());
      scrollPane2.setBounds(0, 0, pnbottom.getWidth(), pnbottom.getHeight());
      
      //добавляем кнопку Склад
      btstore=new JButton("Склад");
      btstore.setBounds(0, 0, 100, 30);
      btstore.addMouseListener( new MouseAdapter()
	 	{
	 		public void mousePressed(MouseEvent event) {
	 			//переходим на склад
	 			actions(TO_STORE);
	 		}
	 	});
      navpanel.add(btstore);
      
      //добавляем кнопку Удалить
      btdelete=new JButton("Удалить");
      btdelete.setBounds(pntop.getWidth()-100, 0, 100, 30);
      btdelete.addMouseListener( new MouseAdapter()
	 	{
	 		public void mousePressed(MouseEvent event) {
	 			
	 			//удаляем выбранный материал
	 			deleteMaterial(selectmaterial);
	 			
	 		}

			
	 	}); 
      
      navpanel.add(btdelete);
      
      
      pntop.add(scrollPane);
      pntop.add(navpanel);
      
      //вешаем слушатель для перерисовки при воздействии на окно
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
		
      	//вешаем слушатель для перерисовки при воздействии на окно
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
		
		
		
		
		

		content = new JSplitPane(JSplitPane.VERTICAL_SPLIT, pntop, pnbottom);
		//минимальные размера
		pnbottom.setMinimumSize(new Dimension(200,200));
		//ширина разделителя
		content.setDividerSize(8);
		//позиция разделителя
		content.setDividerLocation(600);
		
		//моментальная прорисовка при вмешательстве пользователя
		content.setContinuousLayout(true);
		
		description=new JPanel();
		description.setBounds(0,0,300,getHeight());
		description.setLayout(null);
		
		tfnumber=new JTextBoxNum();
		tfnumber.setBounds(10, 30, 60, 30);
		
		tfdate=new JFormattedTextField(new Date());
		tfdate.setBounds(80, 30, 210, 30);
		
		
		tfobject=new JTextField();
		tfobject.setBounds(10, 100, 280, 30);
		
		
		tfbuyer=new JTextField();
		tfbuyer.setBounds(10, 170, 280, 30);
		
		tfdescription=new JTextArea();
		tfdescription.setBounds(10, 240, 280, 70);
		tfdescription.setLineWrap(true);
		
		lbnumber=new JLabel("Номер сметы от");
		lbnumber.setBounds(10, 10, 120, 20);
		
		
		
		
		lbobject=new JLabel("Объект");
		lbobject.setBounds(10, 80, 280, 20);
		
		
		lbbuyer=new JLabel("Заказчик");
		lbbuyer.setBounds(10, 150, 280, 20);
		
		lbdescription=new JLabel("Описание");
		lbdescription.setBounds(10, 220, 280, 20);
		
		lbsumma=new JLabel("Стоимость: ");
		lbsumma.setBounds(10, 360, 280, 20);
		
		btsumma=new JButton("Вычислить");
		btsumma.setBounds(10,320,110, 30);
		btsumma.addMouseListener( new MouseAdapter()
	 	{
	 		public void mousePressed(MouseEvent event) {
	 			//нажали на кнопку Вычислить
	 			lbsumma.setText("Стоимость: "+result()+" руб.");
	 		}
	 	});
		description.add(tfnumber);
		description.add(tfdate);
		description.add(tfobject);
		description.add(tfbuyer);
		description.add(tfdescription);
		description.add(lbnumber);
		
		description.add(lbobject);
		description.add(lbbuyer);
		description.add(lbdescription);
		description.add(lbsumma);
		description.add(btsumma);
		
		
		add(description);
		add(content);
	}
	//вызывается в определеных ситуациях
	void actions(int act){
		//act номер действия
	}
}
