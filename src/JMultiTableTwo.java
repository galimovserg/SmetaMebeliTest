
public class JMultiTableTwo extends JMultiTable{
	/*
	 * JMultiTable ��� ����� ����������� �� JMultiTableTwo, 
	 * ����������� ����������� ��������� �����������
	 * */
	public JMultiTableTwo(String[][] data, String[] title) {
		super(data, title);
		// TODO Auto-generated constructor stub
	}
	
	//����� ���������� ����� ������������ �������� �������� ����������
	@Override
	public boolean isCellEditable(int rowIndex,int columnIndex) {
		if(rowIndex<4&&columnIndex<3) {
			return false;
		}
		return true;
		
	}
	//�������� ����������
	public String[][] getData() {
		return data;
	}
	//����� �������� ���������� ������
	 @Override
	public void setValueAt(Object value, int r, int c) {
		data[r][c]=(String)value;
	}
}
