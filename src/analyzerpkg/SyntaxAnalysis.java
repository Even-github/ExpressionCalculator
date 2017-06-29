package analyzerpkg;

import java.util.List;
import java.util.Stack;

/**
 * 算术表达式语法分析器
 * @author 曾裕文
 *
 * 文法如下：
 * （1）E -> E + T 
 * （2）E -> T
 * （3）E -> E - T
 * （4）T -> T * F 
 * （5）T -> T / F 
 * （6）T -> F 
 * （7）F -> (E)
 * （8）F -> i 
 */
public class SyntaxAnalysis
{
	private Stack<String[]> statusStack; //SLR分析分析状态栈
	private int r = 100; //action表和GOTO表中的值大于100的都表示归约操作，比如105表示按照第5条产生式进行归约
	private int s = 0; //action表和GOTO表中的值小于100的都表示移进操作，比如5表示转移到状态5
	//action表，-2表示接受，即通过语法分析器的检验
	private int[][] actionTable = {
	//列顺序	  i   +   -   *   /   (   )   #
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
			{ -1,s+7,s+6, -1, -1, -1,s+13,-1},
			{ -1,r+3,r+3,s+9,s+8, -1,r+3,r+3},
			{ -1,r+1,r+1,s+9,s+8, -1,r+1,r+1},
			{ -1,r+7,r+7,r+7,r+7, -1,r+7,r+7},
			{ -1,r+5,r+5,r+5,r+5, -1,r+5,r+5},
			{ -1,r+4,r+4,r+4,r+4, -1,r+4,r+4},
	};
	//GOTO（转换）表
	private int[][] gotoTable = {
	  //列顺序        E  T  F
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
	 * 移入操作
	 * @param status 下一个状态
	 * @param symbol 被移入的单词符号
	 */
	public void shiftIn(int status, String symbol)
	{
		String[] item = new String[2];
		item[0] = String.valueOf(status);
		item[1] = symbol;
		statusStack.push(item);
	}
	
	/**
	 * 归约操作
	 * @param productionNum 归约产生式的编号
	 */
	public void combine(int productionNum)
	{
		int popAmount = 0; //需要出栈的元素个数
		//产生式1,2 ,4,5,8的右部都包含三个文法符号,产生式3,6,7右部只包含一个文法符号,故需要出栈3个元素
		if( productionNum == 1 ||
			productionNum == 3 ||
			productionNum == 4 ||
			productionNum == 5 ||
			productionNum == 7)
		{
			popAmount = 3;
		}
		else //产生式3,6,7右部只包含一个文法符号，故需要出栈1个元素
		{
			popAmount = 1;
		}
		for(int j = 0; j < popAmount; j++)
		{
			statusStack.pop();
		}
		String[] topItem = statusStack.peek();
		int status = Integer.parseInt(topItem[0]); //获取栈顶元素状态值
		String vn = ""; //存储产生式左部符号
		int vnNum = -1; //存储产生式左部符号的编号
		if(productionNum >=1 && productionNum <= 3)
		{
			vn = "E";
			vnNum = 0; //代表E
		}
		else if(productionNum >= 4 && productionNum <= 6)
		{
			vn = "T";
			vnNum = 1; //代表T
		}
		else
		{
			vn = "F";
			vnNum = 2; //代表F
		}
		int nextStatus = gotoTable[status][vnNum]; //获取下一个状态
		this.shiftIn(nextStatus, vn);
	}
	
	/**
	 * 获取终结符号在action表中的编号
	 * @param symbol 终结符号
	 * @return 终结符号在action表中的编号
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
	 * 语法分析方法
	 * 输入词法分析器得到的单词符号表，输出该表中的单词符号是否构成正确的语法的标志
	 * @param symbolTable 词法分析器输出的符号表
	 * @return 是否符合语法规则
	 */
	public boolean syntaxAnaly(List<String[]> symbolTable)
	{
		boolean flag = true; //语法正确与否的标志
		statusStack = new Stack<String[]>();
		this.shiftIn(0, "#"); //压入一个元素作为栈底
		String[] endSymbol = new String[2]; //为语法分析器输出结果symbolTable末尾添加一个结束符号<#,>
		endSymbol[0] = "#";
		endSymbol[1] = "";
		symbolTable.add(endSymbol);
		int tableIdx = 0; //单词符号表的索引
		while(tableIdx < symbolTable.size())
		{
			String symbol = symbolTable.get(tableIdx)[0]; //存储最新输入的单词符号
			String[] topItem = statusStack.peek();
			int status = Integer.valueOf(topItem[0]); //获取栈顶元素的状态编号
			int symbolNum = getSymbolNum(symbol); //获取终结符号在action表中的编号
			int nextStatus = actionTable[status][symbolNum]; //下一状态的编号
			if(nextStatus < 100 && nextStatus >= 0) //执行移入操作
			{
				this.shiftIn(nextStatus, symbol);
				tableIdx++;
			}
			else if(nextStatus > 100) //执行归约操作
			{
				int productionNum = nextStatus - 100;
				this.combine(productionNum);
			}
			else if(nextStatus == -2) //执行接受操作
			{
				flag = true;
				break;
			}
			else //报错处理
			{
				flag = false;
				break;
			}
		}
		symbolTable.remove(symbolTable.size() - 1); //去除”#“
		return flag;
	}
}
