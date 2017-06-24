package control;

import java.util.List;

import analyzerpkg.LexAnalysis;
import analyzerpkg.SemanticAnalysis;
import analyzerpkg.SyntaxAnalysis;

public class Controller
{
	private LexAnalysis lexAnalysis; //�ʷ�������
	private SyntaxAnalysis syntaxanalysis; //�﷨������
	private SemanticAnalysis semanticAnalysis; //���������
	
	public Controller()
	{
		lexAnalysis = new LexAnalysis();
		syntaxanalysis = new SyntaxAnalysis();
		semanticAnalysis = new SemanticAnalysis();
	}
	
	/**
	 * �����������ʽ�����ؼ�����
	 * @param exp �������ʽ
	 * @return ������
	 */
	public String inputExp(String exp)
	{
		boolean lexFlag = lexAnalysis.lex(exp); //�ʷ������ɹ����ı�־
		if(lexFlag == true)
		{
			lexAnalysis.displaySymbolTable(); //�ں�̨��ӡ�ʷ����ʷ��ű�
			List<String[]> symbolTable = lexAnalysis.getSymbolTable(); //�ʷ�����������ʷ��ű�
			boolean synFlag = syntaxanalysis.syntaxAnaly(symbolTable); //�﷨����
			if(synFlag == true)
			{
				String result = semanticAnalysis.calc(symbolTable);
				return result;
			}
			else //�﷨����
			{
				System.out.println("�﷨����");
				return "error!";
			}
		}
		else //�ʷ�����
		{
			System.out.println("�ʷ�����");
			return "error��";
		}
		
	}
}
