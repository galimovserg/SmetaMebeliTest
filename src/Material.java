import java.util.ArrayList;
/**
 *Класс Material(Материал) предназначен
 *для представления материала необходимого для исполнения сметы.
 *Под материалом подразумевается материальный объект или услуга входящие в смету. 
 *Стоимость материала расчитывается на основании формулы которую изначально задают на складе.
 *Помимо формулы у материала есть параметры на основании которых и расчитывается формула.
 *Имя материала, его код, имя класса служат для идентификации материала. 
 *Параметры материала и его количества задает сметчик. 
 * 
 * */
public class Material{
	//формула
	Formula f;
	//код материала в БД
	int id=0;
	//наименование материала
	String name="";
	//тип, класс материала
	String classname="";
	//количество
	int count=1;
	//список параметров
	ArrayList<Parametr> paramlist=new ArrayList<Parametr>();
	//строка с формулой
	String sx="";
	//конструктор материала
	Material(int id,String name,String classname,ArrayList<Parametr> paramlist,String sx,int count){
		this.id=id;
		this.classname=classname;
		this.paramlist=paramlist;
		this.count=count;
		this.sx=sx;
		this.name=name;
		String paramnames[]=new String[paramlist.size()];
		
		for(int i=0;i<paramlist.size();i++){
			paramnames[i]=paramlist.get(i).getName();
			
		}
		//и зададим формулу
		f=new Formula(sx, paramnames);
		
	}
	//изменяет материал
	void edit(int id,String name,String classname,ArrayList<Parametr> paramlist,String sx,int count){
		this.id=id;
		this.classname=classname;
		this.paramlist=paramlist;
		this.count=count;
		this.sx=sx;
		this.name=name;
		String paramnames[]=new String[paramlist.size()];
		
		for(int i=0;i<paramlist.size();i++){
			paramnames[i]=paramlist.get(i).getName();
			
		}
		//и зададим формулу
		f=new Formula(sx, paramnames);
	}
	//возвращает копию материала
	Material copy(){
		ArrayList<Parametr> paramlistclone=new ArrayList<Parametr>();
		for(int i=0;i<paramlist.size();i++){
			paramlistclone.add(paramlist.get(i).copy());
		}
		return new Material(id, name, classname,paramlistclone, sx, count);
	}
	//добавление параметра
	void addParametr(Parametr newparam){
		paramlist.add(newparam);
		
		//и обновим формулу
		String paramnames[]=new String[paramlist.size()];
		for(int i=0;i<paramlist.size();i++){
			paramnames[i]=paramlist.get(i).getName();
		}
		
		f=new Formula(sx, paramnames);
	}
	//удаляет параметр с идексом indexp
	void deleteParametr(int indexp) {
		if(indexp>=0&&indexp<paramlist.size()) {
			paramlist.remove(indexp);
			//и обновим формулу
			String paramnames[]=new String[paramlist.size()];
			for(int i=0;i<paramlist.size();i++){
				paramnames[i]=paramlist.get(i).getName();
			}
			
			f=new Formula(sx, paramnames);
		}
	}
	//возвращает параметры материала
	ArrayList<Parametr> getParametrs(){
		return paramlist;
	}
	//установка стоимости
	void setValues(String paramvalue[]){
		
		for(int i=0;i<paramvalue.length&&i<paramlist.size();i++){
			paramlist.get(i).setValue(paramvalue[i]);
			
		}
	}
	boolean errors=false;
	//проверяет были ошибки или нет
    boolean hadError(){
    	if(errors){
    		return true;
    	}else{
    		return false;
    	}
    }
	//получить стоимость материала с учетом множителя
	String result(){
		//формируем список значений параметров
		String paramvalue[]=new String[paramlist.size()];
		for(int i=0;i<paramlist.size();i++){
			paramvalue[i]=paramlist.get(i).getValue();
			
		}
		//передаем параметры и получаем результат
		String res=f.result(paramvalue);
		if(f.hadError()){
			errors=true;
			return "ERROR";
		}else{
			return String.valueOf(Double.valueOf(res)*count);
		}
		
	}
	//получить стоимость одного материала
	String resultsome(){
		
		String paramvalue[]=new String[paramlist.size()];
		for(int i=0;i<paramlist.size();i++){
			paramvalue[i]=paramlist.get(i).getValue();
			
		}
		String res=f.result(paramvalue);
		if(f.hadError()){
			return "ERROR";
		}else{
			return res;
		}
		
	}
	//установить параметры по умолчанию
	void setStore(){
		for(int i=0;i<paramlist.size();i++){
			paramlist.get(i).setStore();
		}
	}
	//установить множитель
	void setCount(int count){
		if(count>0) {
			this.count=count;
		}else {
			this.count=0;
		}
		
	}
	//получить код материала
	int getID(){
		return id;
	}
	//получить имя материала
	String getName(){
		return name;
	}
	//получить код материала
	int getCount(){
		return count;
	}
	//получить имя класса
	String getClassname(){
		return classname;
	}
	//получить строку формулы
	String getFormula() {
		return sx;
	} 
}
