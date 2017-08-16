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
		
		//�������ȼ��ļ�
		Map<Character,Integer> priority=new HashMap<Character,Integer>();
		BufferedReader breader=new BufferedReader(new FileReader("priority.txt"));
		String pline=breader.readLine();
		while(pline!=null) {
			//System.out.println(pline);
			char[] plineChar=pline.toCharArray();
			priority.put(plineChar[0], Integer.parseInt(String.valueOf(plineChar[2])));
			pline=breader.readLine();
		}
		System.out.println("���ȼ���"+priority);
		breader.close();
		
		//numStack���ڴ洢�洢������
		Stack<Integer>numStack=new Stack<>();
		//opStack���ڴ洢������
		Stack<Character>opStack=new Stack<>();
		//postfix�洢��׺���ʽ�ģ�
		//������Integer��Character���ͣ�������Object��Ϊ���Ͳ���
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
							//�����������⴦��
							if(charitem==')'&&!opStack.isEmpty()) {
								while(opStack.peek()!='(') {
									postfix.add(opStack.pop());
								}
								//����������
								opStack.pop();
							}else if(charitem=='(') {
								opStack.push(charitem);
							}else {
								//�Է����ŵķ�������
								//������������Ų���������ջ
								if(opStack.peek()=='(') {
									opStack.push(charitem);
								}else if(priority.get(charitem)>priority.get(opStack.peek())) {
									//�����һ�������������ȼ�����ջ�������ȼ���ѹջ,��ʱջ��һ���в�����
									opStack.push(charitem);
								}else {
									//���򣬳�ջֱ����ǰ��������ջ
									//ע�⵱ջ���յ�����±Ƚ�
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
			//ʣ������ж��
			while(!opStack.isEmpty()) {
				postfix.add(opStack.pop());
			}
			System.out.println(postfix);
			
			//���ú�׺���ʽ��ʼ����
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
				System.out.println("�������");
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
		//ע�����num!=0����ӽ�ȥ
		if(num!=0)
			result.add(num);
	}

}
