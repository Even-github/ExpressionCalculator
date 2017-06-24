package analyzerpkg;

import java.util.ArrayList;
import java.util.List;

/**
 * 算术表达式词法分析器
 * @author 曾裕文
 *
 */
public class LexAnalysis
{
	private String sourceCode; //输入的算术表达式字符串
	private char ch; //最新存放的表达式字符
	private int lastIdx; //最新扫描的下标
	private StringBuilder strToken; //存放构成符号的字符串
	private List<String[]> symbolTable; //输出符号表
	
	/**
	 * 读入最新的一位算术表达式字符
	 */
	public void getChar()
	{
		ch = sourceCode.charAt(lastIdx);
	}
	
	/**
	 * 丢弃读取到到的空白符，直到读取到一位非空白符
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
	 * 判断ch是否为运算符
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
	 * 判断是否为小数点
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
	 * 回退一位读取的字符
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
	 * 将strToken中的运算符插入到符号表
	 */
	public void insertOperator()
	{
		String[] symbol = new String[2];
		symbol[0] = strToken.toString();
		symbol[1] = " ";
		symbolTable.add(symbol);
		strToken = new StringBuilder(); //清空strToken
	}
	
	/**
	 * 将strToken中的数字插入到符号表
	 */
	public void insertDigit()
	{
		String[] symbol = new String[2];
		symbol[0] = "digit";
		symbol[1] = strToken.toString();
		symbolTable.add(symbol);
		strToken = new StringBuilder(); //清空strToken
	}
	
	/**
	 * 对输入的字符串进行词法分析
	 * @param sourceCode
	 * @return
	 */
	public boolean lex(String sourceCode)
	{
		boolean flag = true; //词法正确与否的标志
		this.sourceCode = sourceCode;
		strToken = new StringBuilder();
		ch = ' ';
		lastIdx = 0;
		symbolTable = new ArrayList<String[]>();
		while(lastIdx < sourceCode.length()) //从左到右扫描源代码
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
				boolean decimalFlag = false; //判断输入的数字是否为浮点数
				boolean decimalCorrect = true; //判断数字部分词法是否正确
				while(lastIdx < sourceCode.length())
				{
					lastIdx++; //取下一个字符
					if(lastIdx < sourceCode.length())
					{
						this.getBC();
						if(isDigit() == true) //如果是数字
						{
							this.concat();
						}
						else if(isDot() == true) //如果是小数点
						{
							if(decimalFlag == false)
							{
								this.concat();
								decimalFlag = true;
							}
							else //如果同一个数字中含两个以上的小数点，则词法有误
							{
								flag = false;
								decimalCorrect = false;
								break;
							}
						}
						else if(isOperator() == true) //如果是运算符，回退一位,结束输入数字的循环
						{
							this.retract();
							break;
						}
						else //非法字符
						{
							flag = false;
							decimalCorrect = false;
							break;
						}
					}
				}
				if(decimalCorrect == false) //词法错误，中断扫描
				{
					break;
				}
				insertDigit(); //数字扫描完毕，将strToken中的数字插入符号表
			}
			else //非法字符
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
		System.out.print("单词符号表：");
		for(String[] symbol : symbolTable)
		{
			System.out.print("<" + symbol[0] + "," + symbol[1] + "> ");
		}
		System.out.println("");
	}
}
