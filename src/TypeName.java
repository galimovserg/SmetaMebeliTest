//Класс ИмяТипа
public class TypeName {
	
		    String tname;
		    //константа - целочисленный тип
		    static final TypeName intType=new TypeName("int");
		    //константа - вещественный тип
		    static final TypeName floatType=new TypeName("float");
		    //Константа - логический тип
		    static final TypeName booleanType=new TypeName("boolean");
		    //Константа - тип список
		    static final TypeName comboboxType=new TypeName("combobox");
		    //Константа - строковой тип
		    static final TypeName stringType=new TypeName("string");
		    //конструктор типа
		    public TypeName(String name) {
		      tname = name;
		    }
		    //Копирование объекта
		    TypeName copy(){
		    	return new TypeName(tname);
		    }
		    //Возвращает имя типа
		    public String toString() {
		      return tname;
		    }
	}