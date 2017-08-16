package stack;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Scanner;
import java.util.Stack;

public class Expression {

	public static void main(String[] args) throws IOException{
		
		//读入优先级文件
		Map<Character,Integer> priority=new HashMap<Character,Integer>();
		BufferedReader breader=new BufferedReader(new FileReader("priority.txt"));
		String pline=breader.readLine();
		while(pline!=null) {
			//System.out.println(pline);
			char[] plineChar=pline.toCharArray();
			priority.put(plineChar[0], Integer.parseInt(String.valueOf(plineChar[2])));
			pline=breader.readLine();
		}
		System.out.println("优先级："+priority);
		breader.close();
		
		//numStack用于存储存储操作数
		Stack<Integer>numStack=new Stack<>();
		//opStack用于存储操作符
		Stack<Character>opStack=new Stack<>();
		//postfix存储后缀表达式的，
		//其中有Integer和Character类型，所以用Object作为泛型参数
		Queue<Object>postfix=new LinkedList<>();
		Scanner scanner=new Scanner(System.in);
		String myExp=scanner.nextLine();
		while(myExp!=null && !"end".equalsIgnoreCase(myExp)) {
			char[] charExp=myExp.toCharArray();
			//System.out.println(charExp);
			for(char charitem:charExp) {
				switch (charitem) {
					case '1': case '2': case '3': case '4': case '5':
					case '6': case '7': case '8': case '9': case '0':
						numStack.push(Integer.parseInt(String.valueOf(charitem)));
						break;
					case '+': 
					case '-': 
					case '*':
					case '/':
					case '(':
					case ')':
						pushNumber2Result(numStack, postfix);
						if(opStack.isEmpty()) {
							opStack.push(charitem);
						}else {
							//对右括号特殊处理
							if(charitem==')'&&!opStack.isEmpty()) {
								while(opStack.peek()!='(') {
									postfix.add(opStack.pop());
								}
								//弹出左括号
								opStack.pop();
							}else if(charitem=='(') {
								opStack.push(charitem);
							}else {
								//对非括号的符号输入
								//如果碰到左括号操作符号入栈
								if(opStack.peek()=='(') {
									opStack.push(charitem);
								}else if(priority.get(charitem)>priority.get(opStack.peek())) {
									//如果下一个操作符的优先级大于栈顶的优先级，压栈,此时栈顶一定有操作符
									opStack.push(charitem);
								}else {
									//否则，出栈直至当前操作符入栈
									//注意当栈不空的情况下比较
									while(!opStack.isEmpty() && priority.get(charitem)<=priority.get(opStack.peek())) {
										postfix.add(opStack.pop());
									}
									opStack.push(charitem);
								}
							}
						}
						
				}
			}
			pushNumber2Result(numStack, postfix);
			//剩余可能有多个
			while(!opStack.isEmpty()) {
				postfix.add(opStack.pop());
			}
			System.out.println(postfix);
			
			//利用后缀表达式开始计算
			numStack.clear();
			while(!postfix.isEmpty()) {
				Object head=postfix.poll();
				if(head instanceof Integer) {
					numStack.push((Integer)head);
				}else if(head instanceof Character){
					Character op=(Character) head;
					int num2=numStack.pop();
					int num1=numStack.pop();
					switch(op) {
					case '+':
						numStack.push(num1+num2);
						break;
					case '-':
						numStack.push(num1-num2);
						break;
					case '*':
						numStack.push(num1*num2);
						break;
					case '/':
						numStack.push(num1/num2);
						break;
					}
				}
			}
			if(numStack.size()==1) {
				System.out.println("result:"+numStack.pop());
			}else {
				System.out.println("计算出错！");
			}
			postfix.clear();
			myExp=scanner.nextLine();
		}
		short ss=1;
		ss+=0;
	}
	
	public static void pushNumber2Result(Stack<Integer>numStack, Queue<Object>result) {
		int num=0;
		int base=1;
		while(!numStack.isEmpty()) {
			num=num+numStack.pop()*base;
			base=base*10;
		}
		//注意如果num!=0才添加进去
		if(num!=0)
			result.add(num);
	}

}
