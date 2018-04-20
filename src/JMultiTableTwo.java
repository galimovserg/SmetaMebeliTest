
public class JMultiTableTwo extends JMultiTable{
	/*
	 * JMultiTable это класс наследуется от JMultiTableTwo, 
	 * добавляется возможность изменения содержимого
	 * */
	public JMultiTableTwo(String[][] data, String[] title) {
		super(data, title);
		// TODO Auto-generated constructor stub
	}
	
	//метод вызывается когда пользователь пытается изменить содержимое
	@Override
	public boolean isCellEditable(int rowIndex,int columnIndex) {
		if(rowIndex<4&&columnIndex<3) {
			return false;
		}
		return true;
		
	}
	//получить содержимое
	public String[][] getData() {
		return data;
	}
	//метод изменяет содержимое ячейки
	 @Override
	public void setValueAt(Object value, int r, int c) {
		data[r][c]=(String)value;
	}
}
