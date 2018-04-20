import java.awt.event.KeyEvent;

import javax.swing.JTextField;
/*
 * Класс JTextBoxNum позволяет создовать текстовые поля 
 * целочисленного типа.
 * Из объявления класса ясно что это, в первую очередь, текстовое поле.
 * 
 * */
public class JTextBoxNum extends JTextField{
	//максимальная длина
	private int maxlen=6;
	JTextBoxNum(){
		
		//Добавляем слушатель
		addKeyListener(new java.awt.event.KeyAdapter() {
	 	     //данный метод сработает когда будет нажата кнопка при фокусе на текстовом поле
	 	      public void keyTyped(KeyEvent e) {
	 	        //обработка нажатия именно символьной кнопки
	 	    	 char ch=e.getKeyChar();
	 	    	 //здесь будут отбрасываться символы не являющиеся цифрами
	 	    	 if(ch=='0'||ch=='1'||ch=='2'||ch=='3'||ch=='4'||ch=='5'||ch=='6'||ch=='7'||ch=='8'||ch=='9'){
	 	    		//а также ограничение длины
	 	    		 if(getText().length()==maxlen){
		 	    		 //отбрасываем символ
	 	    			e.consume();
		 	    	}
	 	    	 }else{
	 	    		 //отбрасываем символ
	 	    		e.consume();
	 	    	 }
	 	      }

	 	    });
	}
	

}

