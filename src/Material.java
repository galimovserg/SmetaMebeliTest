import java.util.ArrayList;
/**
 *����� Material(��������) ������������
 *��� ������������� ��������� ������������ ��� ���������� �����.
 *��� ���������� ��������������� ������������ ������ ��� ������ �������� � �����. 
 *��������� ��������� ������������� �� ��������� ������� ������� ���������� ������ �� ������.
 *������ ������� � ��������� ���� ��������� �� ��������� ������� � ������������� �������.
 *��� ���������, ��� ���, ��� ������ ������ ��� ������������� ���������. 
 *��������� ��������� � ��� ���������� ������ �������. 
 * 
 * */
public class Material{
	//�������
	Formula f;
	//��� ��������� � ��
	int id=0;
	//������������ ���������
	String name="";
	//���, ����� ���������
	String classname="";
	//����������
	int count=1;
	//������ ����������
	ArrayList<Parametr> paramlist=new ArrayList<Parametr>();
	//������ � ��������
	String sx="";
	//����������� ���������
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
		//� ������� �������
		f=new Formula(sx, paramnames);
		
	}
	//�������� ��������
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
		//� ������� �������
		f=new Formula(sx, paramnames);
	}
	//���������� ����� ���������
	Material copy(){
		ArrayList<Parametr> paramlistclone=new ArrayList<Parametr>();
		for(int i=0;i<paramlist.size();i++){
			paramlistclone.add(paramlist.get(i).copy());
		}
		return new Material(id, name, classname,paramlistclone, sx, count);
	}
	//���������� ���������
	void addParametr(Parametr newparam){
		paramlist.add(newparam);
		
		//� ������� �������
		String paramnames[]=new String[paramlist.size()];
		for(int i=0;i<paramlist.size();i++){
			paramnames[i]=paramlist.get(i).getName();
		}
		
		f=new Formula(sx, paramnames);
	}
	//������� �������� � ������� indexp
	void deleteParametr(int indexp) {
		if(indexp>=0&&indexp<paramlist.size()) {
			paramlist.remove(indexp);
			//� ������� �������
			String paramnames[]=new String[paramlist.size()];
			for(int i=0;i<paramlist.size();i++){
				paramnames[i]=paramlist.get(i).getName();
			}
			
			f=new Formula(sx, paramnames);
		}
	}
	//���������� ��������� ���������
	ArrayList<Parametr> getParametrs(){
		return paramlist;
	}
	//��������� ���������
	void setValues(String paramvalue[]){
		
		for(int i=0;i<paramvalue.length&&i<paramlist.size();i++){
			paramlist.get(i).setValue(paramvalue[i]);
			
		}
	}
	boolean errors=false;
	//��������� ���� ������ ��� ���
    boolean hadError(){
    	if(errors){
    		return true;
    	}else{
    		return false;
    	}
    }
	//�������� ��������� ��������� � ������ ���������
	String result(){
		//��������� ������ �������� ����������
		String paramvalue[]=new String[paramlist.size()];
		for(int i=0;i<paramlist.size();i++){
			paramvalue[i]=paramlist.get(i).getValue();
			
		}
		//�������� ��������� � �������� ���������
		String res=f.result(paramvalue);
		if(f.hadError()){
			errors=true;
			return "ERROR";
		}else{
			return String.valueOf(Double.valueOf(res)*count);
		}
		
	}
	//�������� ��������� ������ ���������
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
	//���������� ��������� �� ���������
	void setStore(){
		for(int i=0;i<paramlist.size();i++){
			paramlist.get(i).setStore();
		}
	}
	//���������� ���������
	void setCount(int count){
		if(count>0) {
			this.count=count;
		}else {
			this.count=0;
		}
		
	}
	//�������� ��� ���������
	int getID(){
		return id;
	}
	//�������� ��� ���������
	String getName(){
		return name;
	}
	//�������� ��� ���������
	int getCount(){
		return count;
	}
	//�������� ��� ������
	String getClassname(){
		return classname;
	}
	//�������� ������ �������
	String getFormula() {
		return sx;
	} 
}
