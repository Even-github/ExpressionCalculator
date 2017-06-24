# ExpressionCalculator
此程序是一个通过编译原理的词法分析、语法分析以及语义分析方法构建的简单的表达式计算器，支持表达式混合运算。
## 实现方法提要
算术表达式文法如下：

（1）E -> E + T 

（2）E -> T

（3）E -> E - T

（4）T -> T * F 

（5）T -> T / F 

（6）T -> F 

（7）F -> (E)

（8）F -> i

注释：i表示数字

语法分析采用了自底向上的SLR分析法，构建SLR表如下：

状态	ACTION	GOTO
	i	+	-	*	/	(	)	#	E	T	F
  
0	S4	ERR	ERR	ERR	ERR	S5	ERR	ERR	1	2	3

1	ERR	S7	S6	ERR	ERR	ERR	ERR	ACC	ERR	ERR	ERR

2	ERR	R2	R2	S9	S8	ERR	R2	R2	ERR	ERR	ERR

3	ERR	R6	R6	R6	R6	ERR	R6	R6	ERR	ERR	ERR

4	ERR	R8	R8	R8	R8	ERR	R8	R8	ERR	ERR	ERR

5	S4	ERR	ERR	ERR	ERR	S5	ERR	ERR	10	2	3

6	S4	ERR	ERR	ERR	ERR	S5	ERR	ERR	ERR	11	3

7	S4	ERR	ERR	ERR	ERR	S5	ERR	ERR	ERR	12	3

8	S4	ERR	ERR	ERR	ERR	S5	ERR	ERR	ERR		14

9	S4	ERR	ERR	ERR	ERR	S5	ERR	ERR	ERR		15

10	ERR	S7	S6	ERR	ERR	ERR	S14	ERR		ERR	ERR

11	ERR	R3	R3	S9	S8	ERR	R3	R3	ERR		ERR

12	ERR	R1	R1	S9	S8	ERR	R1	R1	ERR		ERR

13	ERR	R7	R7	R7	R7	ERR	R7	R7	ERR		ERR

14	ERR	R5	R5	R5	R5	ERR	R5	R5	ERR		ERR

15	ERR	R4	R4	R4	R4	ERR	R4	R4	ERR		ERR


PS：以上文本复制到word文档中转换成表格即可。

注释：表中各种符号的意义是：

SJ：把下一状态J和现行输入符号a移进栈；

RJ：按第J个产生式进行规约；

ACC：接受；

ERR：出错标志，报错。




	

