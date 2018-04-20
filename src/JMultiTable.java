
import javax.swing.table.AbstractTableModel;
/*
 * JMultiTable ��� �����, ��������������� ��� �������� ����������� ������, ������� � ���� �������
 * ��������� �� �����, � �� �������� ����������
 * 
 * */

public class JMultiTable extends AbstractTableModel{
	//������	 
	String data[][];
	//�������� ��������
	String title[];
	//����������� ����������� �������
	public JMultiTable(String data[][],String title[]) {
		this.data=data;
		this.title=title;
	}
	//����� �������� ����������
	public void setContent(String data[][],String title[]){
		this.data=data;
		this.title=title;
	}
	//�������� ��� �������(���������� ��� �����������)
	public String getColumnName(int c) {
		 return title[c];
	}

	//���������� ���������� �������(����� ��� ����������)
	@Override
	public int getColumnCount() {
			// TODO Auto-generated method stub
			return title.length;
	}
	//���������� ���������� �����(����� ��� ����������)
	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return data.length;
	}
	//���������� ���������� ������(����� ��� ����������)
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		// TODO Auto-generated method stub
		return data[rowIndex][columnIndex];
	}
	//���������� ������������� ������ ��� ���
	public boolean isCellEditable(int rowIndex,int columnIndex) {
		return false;	
	}

}
