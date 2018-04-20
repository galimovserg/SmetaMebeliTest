//класс параметр
public class Parametr {
	//наименование параметра
	private String caption;
	//имя параметра которое используется в формуле
	private String name;
	//тип прараметра
	private TypeName type;
	//значение
	private String value;
	//значение по умолчанию
	private String valuestore;
	Parametr(String caption,String name,TypeName type,String valuestore){
		this.caption=caption;
		this.name=name;
		this.type=type;
		this.value=String.valueOf(valuestore.toCharArray());
		this.valuestore=String.valueOf(valuestore.toCharArray());
		
	}
	String getCaption(){
		return caption;
	}
	String getName(){
		return name;
	}
	TypeName getType(){
		return type;
	}
	String getValue(){
		return value;
	}
	//задает значение параметра
	void setValue(String val){
		value=String.valueOf(val.toCharArray());
	}
	//задает значение по умолчанию
	void setStore(){
		value=String.valueOf(valuestore.toCharArray());
	}
	//создает копию объекта
	Parametr copy(){
		return new Parametr(caption, name, type.copy(), valuestore);
	}
}
