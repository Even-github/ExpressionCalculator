package analyzerpkg;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * 语义分析器
 * 将算数表达式转换成后缀表达式，再计算结果
 * @author 曾裕文
 *
 */
public class SemanticAnalysis
{
	private Stack<String> opStack; //用于在中缀表达式转后缀表达式时暂存符号的栈
	
	public List<String> toSuffixExp(List<String[]> symbolTable)
	{
		List<String> suffixExp = new ArrayList<String>(); //存储生成的后缀表达式
		for(String[] symbolItem : symbolTable)
		{
			if(symbolItem[0].equals("digit")) //输入数字
			{
				suffixExp.add(symbolItem[1]);
			}
			else if(symbolItem[0].equals("(")) //输入左括号
			{
				opStack.push(symbolItem[0]);
			}
			else if(symbolItem[0].equals(")")) //输入右括号
			{
				while(!opStack.isEmpty() && !opStack.peek().equals("("))
				{
					suffixExp.add(opStack.pop());
				}
				opStack.pop(); //将“（”出栈
			}
			else if(symbolItem[0].equals("+") || symbolItem[0].equals("-"))
			{
				while(!opStack.isEmpty() && !opStack.peek().equals("("))
				{
					suffixExp.add(opStack.pop());
				}
				opStack.push(symbolItem[0]);
			}
			else if(symbolItem[0].equals("*") || symbolItem[0].equals("/"))
			{
				while(!opStack.isEmpty() && 
						(opStack.peek().equals("*") || 
						opStack.peek().equals("/")))
				{
					suffixExp.add(opStack.pop());
				}
				opStack.push(symbolItem[0]);
			}
			else //报错中断
			{
				break;
			}
		}
		while(!opStack.isEmpty()) //将所有元素出栈，输出到suffixExp中
		{
			suffixExp.add(opStack.pop());
		}
		return suffixExp;
	}
	
	/**
	 * 计算后缀表达式的结果
	 * @param suffixExp 后缀表达式
	 * @return 计算结果，当计算错误时，结果为“error!”
	 */
	public String calcSuffixExp(List<String> suffixExp)
	{
		String result = "";
		Stack<Double> numStack = new Stack<Double>(); //用户暂存计算的中间结果
		for(String item : suffixExp)
		{
			if(item.equals("+"))
			{
				double num1 = numStack.pop().doubleValue();
				double num2 = numStack.pop().doubleValue();
				double num = num2 + num1;
				numStack.push(Double.valueOf(num));
			}
			else if(item.equals("-"))
			{
				double num1 = numStack.pop().doubleValue();
				double num2 = numStack.pop().doubleValue();
				double num = num2 - num1;
				numStack.push(Double.valueOf(num));
			}
			else if(item.equals("*"))
			{
				double num1 = numStack.pop().doubleValue();
				double num2 = numStack.pop().doubleValue();
				double num = num2 * num1;
				numStack.push(Double.valueOf(num));
			}
			else if(item.equals("/"))
			{
				double num1 = numStack.pop().doubleValue();
				double num2 = numStack.pop().doubleValue();
				if(num1 != 0)
				{
					double num = num2 / num1;
					numStack.push(Double.valueOf(num));
				}
				else //除数不能为0
				{
					result = "error!";
					break;
				}
			}
			else //如果是数字，则进栈
			{
				numStack.push(Double.parseDouble(item));
			}
		}
		if(!result.equals("error!"))
		{
			result = String.valueOf(numStack.pop().doubleValue());
		}
		return result;
	}
	
	public String calc(List<String[]> symbolTable)
	{
		opStack = new Stack<String>();
		List<String> suffixExp = this.toSuffixExp(symbolTable); //将中缀表达式转换成后缀表达式
		String result = this.calcSuffixExp(suffixExp);
		return result;
	}
}
