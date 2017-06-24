package control;

import java.util.List;

import analyzerpkg.LexAnalysis;
import analyzerpkg.SemanticAnalysis;
import analyzerpkg.SyntaxAnalysis;

public class Controller
{
	private LexAnalysis lexAnalysis; //词法分析器
	private SyntaxAnalysis syntaxanalysis; //语法分析器
	private SemanticAnalysis semanticAnalysis; //语义分析器
	
	public Controller()
	{
		lexAnalysis = new LexAnalysis();
		syntaxanalysis = new SyntaxAnalysis();
		semanticAnalysis = new SemanticAnalysis();
	}
	
	/**
	 * 输入算数表达式，返回计算结果
	 * @param exp 算数表达式
	 * @return 计算结果
	 */
	public String inputExp(String exp)
	{
		boolean lexFlag = lexAnalysis.lex(exp); //词法分析成功与否的标志
		if(lexFlag == true)
		{
			lexAnalysis.displaySymbolTable(); //在后台打印词法单词符号表
			List<String[]> symbolTable = lexAnalysis.getSymbolTable(); //词法分析输出单词符号表
			boolean synFlag = syntaxanalysis.syntaxAnaly(symbolTable); //语法分析
			if(synFlag == true)
			{
				String result = semanticAnalysis.calc(symbolTable);
				return result;
			}
			else //语法错误
			{
				System.out.println("语法错误！");
				return "error!";
			}
		}
		else //词法错误
		{
			System.out.println("词法错误！");
			return "error！";
		}
		
	}
}
