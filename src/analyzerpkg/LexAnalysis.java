package analyzerpkg;

import java.util.ArrayList;
import java.util.List;

/**
 * �������ʽ�ʷ�������
 * @author ��ԣ��
 *
 */
public class LexAnalysis
{
	private String sourceCode; //������������ʽ�ַ���
	private char ch; //���´�ŵı��ʽ�ַ�
	private int lastIdx; //����ɨ����±�
	private StringBuilder strToken; //��Ź��ɷ��ŵ��ַ���
	private List<String[]> symbolTable; //������ű�
	
	/**
	 * �������µ�һλ�������ʽ�ַ�
	 */
	public void getChar()
	{
		ch = sourceCode.charAt(lastIdx);
	}
	
	/**
	 * ������ȡ�����Ŀհ׷���ֱ����ȡ��һλ�ǿհ׷�
	 */
	public void getBC()
	{
		this.getChar();
		while(ch == ' ')
		{
			lastIdx++;
			this.getChar();
		}
	}
	
	public void concat()
	{
		strToken.append(ch);
	}
	
	/**
	 * �ж�ch�Ƿ�Ϊ�����
	 * @return
	 */
	public boolean isOperator()
	{
		if(ch == '(' || 
		   ch == ')' || 
		   ch == '+' || 
		   ch == '-' ||
		   ch == '*' ||
		   ch == '/' )
		{
			return true; 
		}
		else
		{
			return false;
		}
	}
	
	public boolean isDigit()
	{
		if(ch == '0' ||
		   ch == '1' || 
		   ch == '2' || 
		   ch == '3' ||
		   ch == '4' ||
		   ch == '5' ||
		   ch == '6' || 
		   ch == '7' || 
		   ch == '8' ||
		   ch == '9')
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	/**
	 * �ж��Ƿ�ΪС����
	 * @return
	 */
	public boolean isDot()
	{
		if(ch == '.')
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	/**
	 * ����һλ��ȡ���ַ�
	 */
	public void retract()
	{
		ch = ' ';
		if(lastIdx > 0)
		{
			lastIdx--;
		}
	}

	/**
	 * ��strToken�е���������뵽���ű�
	 */
	public void insertOperator()
	{
		String[] symbol = new String[2];
		symbol[0] = strToken.toString();
		symbol[1] = " ";
		symbolTable.add(symbol);
		strToken = new StringBuilder(); //���strToken
	}
	
	/**
	 * ��strToken�е����ֲ��뵽���ű�
	 */
	public void insertDigit()
	{
		String[] symbol = new String[2];
		symbol[0] = "digit";
		symbol[1] = strToken.toString();
		symbolTable.add(symbol);
		strToken = new StringBuilder(); //���strToken
	}
	
	/**
	 * ��������ַ������дʷ�����
	 * @param sourceCode
	 * @return
	 */
	public boolean lex(String sourceCode)
	{
		boolean flag = true; //�ʷ���ȷ���ı�־
		this.sourceCode = sourceCode;
		strToken = new StringBuilder();
		ch = ' ';
		lastIdx = 0;
		symbolTable = new ArrayList<String[]>();
		while(lastIdx < sourceCode.length()) //������ɨ��Դ����
		{
			this.getBC();
			if(isOperator() == true)
			{
				this.concat();
				this.insertOperator();
			}
			else if(isDigit() == true)
			{
				this.concat();
				boolean decimalFlag = false; //�ж�����������Ƿ�Ϊ������
				boolean decimalCorrect = true; //�ж����ֲ��ִʷ��Ƿ���ȷ
				while(lastIdx < sourceCode.length())
				{
					lastIdx++; //ȡ��һ���ַ�
					if(lastIdx < sourceCode.length())
					{
						this.getBC();
						if(isDigit() == true) //���������
						{
							this.concat();
						}
						else if(isDot() == true) //�����С����
						{
							if(decimalFlag == false)
							{
								this.concat();
								decimalFlag = true;
							}
							else //���ͬһ�������к��������ϵ�С���㣬��ʷ�����
							{
								flag = false;
								decimalCorrect = false;
								break;
							}
						}
						else if(isOperator() == true) //����������������һλ,�����������ֵ�ѭ��
						{
							this.retract();
							break;
						}
						else //�Ƿ��ַ�
						{
							flag = false;
							decimalCorrect = false;
							break;
						}
					}
				}
				if(decimalCorrect == false) //�ʷ������ж�ɨ��
				{
					break;
				}
				insertDigit(); //����ɨ����ϣ���strToken�е����ֲ�����ű�
			}
			else //�Ƿ��ַ�
			{
				flag = false;
				break;
			}
			lastIdx++;
		}
		return flag;
	}
	
	public List<String[]> getSymbolTable()
	{
		return symbolTable;
	}
	
	public void displaySymbolTable()
	{
		System.out.print("���ʷ��ű�");
		for(String[] symbol : symbolTable)
		{
			System.out.print("<" + symbol[0] + "," + symbol[1] + "> ");
		}
		System.out.println("");
	}
}
