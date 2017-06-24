package analyzerpkg;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * ���������
 * ���������ʽת���ɺ�׺���ʽ���ټ�����
 * @author ��ԣ��
 *
 */
public class SemanticAnalysis
{
	private Stack<String> opStack; //��������׺���ʽת��׺���ʽʱ�ݴ���ŵ�ջ
	
	public List<String> toSuffixExp(List<String[]> symbolTable)
	{
		List<String> suffixExp = new ArrayList<String>(); //�洢���ɵĺ�׺���ʽ
		for(String[] symbolItem : symbolTable)
		{
			if(symbolItem[0].equals("digit")) //��������
			{
				suffixExp.add(symbolItem[1]);
			}
			else if(symbolItem[0].equals("(")) //����������
			{
				opStack.push(symbolItem[0]);
			}
			else if(symbolItem[0].equals(")")) //����������
			{
				while(!opStack.isEmpty() && !opStack.peek().equals("("))
				{
					suffixExp.add(opStack.pop());
				}
				opStack.pop(); //����������ջ
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
			else //�����ж�
			{
				break;
			}
		}
		while(!opStack.isEmpty()) //������Ԫ�س�ջ�������suffixExp��
		{
			suffixExp.add(opStack.pop());
		}
		return suffixExp;
	}
	
	/**
	 * �����׺���ʽ�Ľ��
	 * @param suffixExp ��׺���ʽ
	 * @return �����������������ʱ�����Ϊ��error!��
	 */
	public String calcSuffixExp(List<String> suffixExp)
	{
		String result = "";
		Stack<Double> numStack = new Stack<Double>(); //�û��ݴ������м���
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
				else //��������Ϊ0
				{
					result = "error!";
					break;
				}
			}
			else //��������֣����ջ
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
		List<String> suffixExp = this.toSuffixExp(symbolTable); //����׺���ʽת���ɺ�׺���ʽ
		String result = this.calcSuffixExp(suffixExp);
		return result;
	}
}
