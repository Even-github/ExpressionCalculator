package analyzerpkg;

import java.util.List;
import java.util.Stack;

/**
 * �������ʽ�﷨������
 * @author ��ԣ��
 *
 * �ķ����£�
 * ��1��E -> E + T 
 * ��2��E -> T
 * ��3��E -> E - T
 * ��4��T -> T * F 
 * ��5��T -> T / F 
 * ��6��T -> F 
 * ��7��F -> (E)
 * ��8��F -> i 
 */
public class SyntaxAnalysis
{
	private Stack<String[]> statusStack; //SLR��������״̬ջ
	private int r = 100; //action���GOTO���е�ֵ����100�Ķ���ʾ��Լ����������105��ʾ���յ�5������ʽ���й�Լ
	private int s = 0; //action���GOTO���е�ֵС��100�Ķ���ʾ�ƽ�����������5��ʾת�Ƶ�״̬5
	//action��-2��ʾ���ܣ���ͨ���﷨�������ļ���
	private int[][] actionTable = {
	//��˳��	  i   +   -   *   /   (   )   #
			{s+4, -1, -1, -1, -1,s+5, -1, -1},
			{ -1,s+7,s+6, -1, -1, -1, -1, -2},
			{ -1,r+2,r+2,s+9,s+8, -1,r+2,r+2},
			{ -1,r+6,r+6,r+6,r+6, -1,r+6,r+6},
			{ -1,r+8,r+8,r+8,r+8, -1,r+8,r+8},
			{s+4, -1, -1, -1, -1,s+5, -1, -1},
			{s+4, -1, -1, -1, -1,s+5, -1, -1},
			{s+4, -1, -1, -1, -1,s+5, -1, -1},
			{s+4, -1, -1, -1, -1,s+5, -1, -1},
			{s+4, -1, -1, -1, -1,s+5, -1, -1},
			{ -1,s+7,s+6, -1, -1, -1,s+14,-1},
			{ -1,r+3,r+3,s+9,s+8, -1,r+3,r+3},
			{ -1,r+1,r+1,s+9,s+8, -1,r+1,r+1},
			{ -1,r+7,r+7,r+7,r+7, -1,r+7,r+7},
			{ -1,r+5,r+5,r+5,r+5, -1,r+5,r+5},
			{ -1,r+4,r+4,r+4,r+4, -1,r+4,r+4},
	};
	//GOTO��ת������
	private int[][] gotoTable = {
	  //��˳��        E  T  F
			{ 1, 2, 3},
			{-1,-1,-1},
			{-1,-1,-1},
			{-1,-1,-1},
			{-1,-1,-1},
			{10, 2, 3},
			{-1,11, 3},
			{-1,12, 3},
			{-1,-1,14},
			{-1,-1,15},
			{-1,-1,-1},
			{-1,-1,-1},
			{-1,-1,-1},
			{-1,-1,-1},
			{-1,-1,-1},
			{-1,-1,-1},
	};
	
	/**
	 * �������
	 * @param status ��һ��״̬
	 * @param symbol ������ĵ��ʷ���
	 */
	public void shiftIn(int status, String symbol)
	{
		String[] item = new String[2];
		item[0] = String.valueOf(status);
		item[1] = symbol;
		statusStack.push(item);
	}
	
	/**
	 * ��Լ����
	 * @param productionNum ��Լ����ʽ�ı��
	 */
	public void combine(int productionNum)
	{
		int popAmount = 0; //��Ҫ��ջ��Ԫ�ظ���
		//����ʽ1,2 ,4,5,8���Ҳ������������ķ�����,����ʽ3,6,7�Ҳ�ֻ����һ���ķ�����,����Ҫ��ջ3��Ԫ��
		if( productionNum == 1 ||
			productionNum == 3 ||
			productionNum == 4 ||
			productionNum == 5 ||
			productionNum == 7)
		{
			popAmount = 3;
		}
		else //����ʽ3,6,7�Ҳ�ֻ����һ���ķ����ţ�����Ҫ��ջ1��Ԫ��
		{
			popAmount = 1;
		}
		for(int j = 0; j < popAmount; j++)
		{
			statusStack.pop();
		}
		String[] topItem = statusStack.peek();
		int status = Integer.parseInt(topItem[0]); //��ȡջ��Ԫ��״ֵ̬
		String vn = ""; //�洢����ʽ�󲿷���
		int vnNum = -1; //�洢����ʽ�󲿷��ŵı��
		if(productionNum >=1 && productionNum <= 3)
		{
			vn = "E";
			vnNum = 0; //����E
		}
		else if(productionNum >= 4 && productionNum <= 6)
		{
			vn = "T";
			vnNum = 1; //����T
		}
		else
		{
			vn = "F";
			vnNum = 2; //����F
		}
		int nextStatus = gotoTable[status][vnNum]; //��ȡ��һ��״̬
		this.shiftIn(nextStatus, vn);
	}
	
	/**
	 * ��ȡ�ս������action���еı��
	 * @param symbol �ս����
	 * @return �ս������action���еı��
	 */
	public int getSymbolNum(String symbol)
	{
		if(symbol.equals("digit"))
		{
			return 0;
		}
		else if(symbol.equals("+"))
		{
			return 1;
		}
		else if(symbol.equals("-"))
		{
			return 2;
		}
		else if(symbol.equals("*"))
		{
			return 3;
		}
		else if(symbol.equals("/"))
		{
			return 4;
		}
		else if(symbol.equals("("))
		{
			return 5;
		}
		else if(symbol.equals(")"))
		{
			return 6;
		}
		else
		{
			return 7;
		}
	}
	
	/**
	 * �﷨��������
	 * ����ʷ��������õ��ĵ��ʷ��ű�����ñ��еĵ��ʷ����Ƿ񹹳���ȷ���﷨�ı�־
	 * @param symbolTable �ʷ�����������ķ��ű�
	 * @return �Ƿ�����﷨����
	 */
	public boolean syntaxAnaly(List<String[]> symbolTable)
	{
		boolean flag = true; //�﷨��ȷ���ı�־
		statusStack = new Stack<String[]>();
		this.shiftIn(0, "#"); //ѹ��һ��Ԫ����Ϊջ��
		String[] endSymbol = new String[2]; //Ϊ�﷨������������symbolTableĩβ���һ����������<#,>
		endSymbol[0] = "#";
		endSymbol[1] = "";
		symbolTable.add(endSymbol);
		int tableIdx = 0; //���ʷ��ű������
		while(tableIdx < symbolTable.size())
		{
			String symbol = symbolTable.get(tableIdx)[0]; //�洢��������ĵ��ʷ���
			String[] topItem = statusStack.peek();
			int status = Integer.valueOf(topItem[0]); //��ȡջ��Ԫ�ص�״̬���
			int symbolNum = getSymbolNum(symbol); //��ȡ�ս������action���еı��
			int nextStatus = actionTable[status][symbolNum]; //��һ״̬�ı��
			if(nextStatus < 100 && nextStatus >= 0) //ִ���������
			{
				this.shiftIn(nextStatus, symbol);
				tableIdx++;
			}
			else if(nextStatus > 100) //ִ�й�Լ����
			{
				int productionNum = nextStatus - 100;
				this.combine(productionNum);
			}
			else if(nextStatus == -2) //ִ�н��ܲ���
			{
				flag = true;
				break;
			}
			else //������
			{
				flag = false;
				break;
			}
		}
		symbolTable.remove(symbolTable.size() - 1); //ȥ����#��
		return flag;
	}
}
