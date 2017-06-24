package view;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

import control.Controller;

public class CalcUI extends JFrame
{
	private Controller controller = new Controller(); //计算器的控制器
    private JTextField textField;
	//框架基本属性
	private int frame_X;
	private int frame_Y;
	private int frame_W;
	private int frame_H;
	
	private Font fontBtn; //按键字体
	private Font fontText; //输入框字体
	
	public CalcUI()
	{
        this.setTitle("算数表达式计算器");
        this.setResizable(false);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);  
        this.init();
        this.setVisible(true); 
	}
	
	/**
	 * 初始化窗口
	 */
    private void init()   
    {
		//设置窗口的基本属性
		Toolkit kit = Toolkit.getDefaultToolkit();
		Dimension screenSize = kit.getScreenSize();
		frame_W = 360;
		frame_H = 500;
		frame_X = (int)((screenSize.getWidth() - frame_W) / 2);
		frame_Y = (int)((screenSize.getHeight() - frame_H) / 2);
		this.setBounds(frame_X, frame_Y, frame_W, frame_H);
		fontBtn = new Font("Monospaced", Font.PLAIN, 30); //字体
		fontText = new Font("Monospaced", Font.PLAIN, 35); //字体
        textField = new JTextField();
        textField.setFont(fontText);
        textField.setPreferredSize(new Dimension(frame_W, 70));
        textField.setEditable(true);
        textField.setHorizontalAlignment(JTextField.RIGHT);
        
        JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayout(1, 2));
        JButton clearButton = new JButton("C");
        clearButton.setFont(fontBtn);
        clearButton.addActionListener(new ActionListener()  
        {  
            public void actionPerformed(ActionEvent event)  
            {  
            	textField.setText("");
            }  
        });
        JButton backButton = new JButton("←");
        backButton.setFont(fontBtn);
        backButton.addActionListener(new ActionListener()  
        {  
            public void actionPerformed(ActionEvent event)  
            {
            	String tmp = textField.getText();
            	StringBuilder s = new StringBuilder(tmp);
            	if(s.length() > 0)
            	{
            		s.deleteCharAt(s.length() - 1);  //清除最后一位字符
            	}
            	textField.setText(s.toString());
            }  
        });
        
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 4));
        panel.add(clearButton);
        panel.add(backButton);
        panel.add(keyButton('('));
        panel.add(keyButton(')'));
        panel.add(keyButton('7'));  
        panel.add(keyButton('8'));  
        panel.add(keyButton('9'));  
        panel.add(keyButton('+'));  
        panel.add(keyButton('4'));  
        panel.add(keyButton('5'));  
        panel.add(keyButton('6'));  
        panel.add(keyButton('-'));  
        panel.add(keyButton('1'));  
        panel.add(keyButton('2'));  
        panel.add(keyButton('3'));  
        panel.add(keyButton('*'));  
        panel.add(keyButton('0'));  
        panel.add(keyButton('.'));  
        panel.add(equButton());  //等于符号按键
        panel.add(keyButton('/'));
        
        Container container = getContentPane();
        container.add(textField, BorderLayout.NORTH);
        container.add(panel, BorderLayout.CENTER);
    }  
    
    /**
     * 数字运算符按键
     * @param key
     * @return
     */
    public JButton keyButton(char key)   
    {  
        JButton button = new JButton(String.valueOf(key));  
        button.setFont(fontBtn);
        button.addActionListener(new ActionListener()  
        {  
            public void actionPerformed(ActionEvent event)  
            {  
                JButton btn = (JButton) event.getSource();  
                char inputKey = btn.getText().charAt(0);
                textField.setText(textField.getText() + inputKey);
            }  
        });
        return button;
    }
    
    /**
     * 等于号按键
     * @return
     */
    public JButton equButton()
    {
        JButton button = new JButton("=");
        button.setFont(fontBtn);
        button.addActionListener(new ActionListener()  
        {  
            public void actionPerformed(ActionEvent event)  
            {  
            	String exp = textField.getText();
            	String result = controller.inputExp(exp);
            	textField.setText(result);
            }  
        });
        return button;
    }
}
