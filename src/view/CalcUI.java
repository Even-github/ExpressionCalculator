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
	private Controller controller = new Controller(); //�������Ŀ�����
    private JTextField textField;
	//��ܻ�������
	private int frame_X;
	private int frame_Y;
	private int frame_W;
	private int frame_H;
	
	private Font fontBtn; //��������
	private Font fontText; //���������
	
	public CalcUI()
	{
        this.setTitle("�������ʽ������");
        this.setResizable(false);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);  
        this.init();
        this.setVisible(true); 
	}
	
	/**
	 * ��ʼ������
	 */
    private void init()   
    {
		//���ô��ڵĻ�������
		Toolkit kit = Toolkit.getDefaultToolkit();
		Dimension screenSize = kit.getScreenSize();
		frame_W = 360;
		frame_H = 500;
		frame_X = (int)((screenSize.getWidth() - frame_W) / 2);
		frame_Y = (int)((screenSize.getHeight() - frame_H) / 2);
		this.setBounds(frame_X, frame_Y, frame_W, frame_H);
		fontBtn = new Font("Monospaced", Font.PLAIN, 30); //����
		fontText = new Font("Monospaced", Font.PLAIN, 35); //����
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
        JButton backButton = new JButton("��");
        backButton.setFont(fontBtn);
        backButton.addActionListener(new ActionListener()  
        {  
            public void actionPerformed(ActionEvent event)  
            {
            	String tmp = textField.getText();
            	StringBuilder s = new StringBuilder(tmp);
            	if(s.length() > 0)
            	{
            		s.deleteCharAt(s.length() - 1);  //������һλ�ַ�
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
        panel.add(equButton());  //���ڷ��Ű���
        panel.add(keyButton('/'));
        
        Container container = getContentPane();
        container.add(textField, BorderLayout.NORTH);
        container.add(panel, BorderLayout.CENTER);
    }  
    
    /**
     * �������������
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
     * ���ںŰ���
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
