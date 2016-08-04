package calculator;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;

public class Calculator {
	
	static Stack <Integer> numbers = new Stack <Integer> ();
	static Stack <String> operators = new Stack <String> ();
	
	public static void main(String[] args) {
		// 1. get an expression from the user
		Scanner keyboard = new Scanner(System.in);
		String expression = keyboard.nextLine();
		
		// 2. split that expression into tokens
		String[] tokens = tokenize(expression);
		
		// 3. for every token in the list of tokens, do this:
		for (String token : tokens) {
			// Is it a number or an operator?
			if (isOperator(token)) {
				// Case 1: It's an operator
				// while the precedence is less than or equal to the top op's precedence, reduce
				while (canReduce(token)) {
					reduce();
				}
				// Add the operator onto the stack
				operators.add( token );
			}
			else {
				// Case 2: otherwise, it's a number
				numbers.add( Integer.parseInt(token) );
			}
		}
		// 4. While you can reduce, reduce
		while (canReduce()) {
			reduce();
		}
		
		// 5. Print the answer
		int answer = numbers.pop();
		System.out.println("Answer: " + answer);
	}
	
	public static String[] tokenize(String expr) {
		String clean = expr.replaceAll(" ", "");
		ArrayList <String> tokens = new ArrayList <String> ();
		
		String number = "";
		for (char c : clean.toCharArray()) {
			// If it's a digit, accumulate it
			if (Character.isDigit(c)) {
				number = number + c;
			}
			else {
				// Add the number that got accumulated digit by digit
				if (number.length() > 0) {
					tokens.add(number);
					number = "";
				}
				
				// Add this current operator
				tokens.add("" + c);
			}
		}
		// If there is a number still in the accumulator
		if (number.length() > 0) {
			tokens.add(number);
		}
		return tokens.toArray(new String[tokens.size()]);
	}
	
	/**
	 * Returneth thee precedence of thy operator.
	 * @param op - the String with the operator
	 * @return the precedence of the operator
	 */
	public static int precedence(String op) {
		if (op.equals("+")) {
			return 1;
		}
		if (op.equals("-")) {
			return 1;
		}
		if (op.equals("*")) {
			return 2;
		}
		if (op.equals("/")) {
			return 2;
		}
		return 0;
	}
	
	/**
	 * Take the last two numbers from the numbers stack and the last operator from the
	 * operators stack, combine them, and put them back on the numbers stack.
	 */
	public static void reduce() {
		int b = numbers.pop();
		int a = numbers.pop();
		String op = operators.pop();
		
		// Take the result of the reduce and put it into this variable
		int result = reduce(a, op, b);
		numbers.push(result);
	}
	
	/**
	 * Returns whether or not the stacks can be reduced given the current operator.
	 * @param currentOp - current operator
	 * @return true/false based on whether or not the reduce step can happen
	 */
	public static boolean canReduce(String currentOp) {
		if (operators.size() > 0) {
			String topOp = operators.peek();
			
			if (precedence(currentOp) <= precedence(topOp)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Returns true/false based on whether the stacks can be reduced at the end
	 * (i.e. there is an operator to reduce)
	 * @return
	 */
	public static boolean canReduce() {
		if (operators.size() > 0) {
			return true;
		}
		return false;
	}
	
	/**
	 * Returns true or false based on whether the input string is or isn't an operator.
	 * @param op
	 * @return true for operators, false otherwise
	 */
	public static boolean isOperator(String op) {
		if (op.equals("+") || op.equals("-") || op.equals("*") || op.equals("/")) {
			return true;
		}
		return false;
	}
	
	/**
	 * Takes two numbers and an operator, and returns the result of applying that operator
	 * on the two numbers.
	 * @param a - the first number
	 * @param op - the operator
	 * @param b - the second number
	 * @return
	 */
	public static int reduce(int a, String op, int b) {
		if (op.equals("+")) {
			return a + b;
		}
		if (op.equals("-")) {
			return a - b;
		}
		if (op.equals("*")) {
			return a * b;
		}
		if (op.equals("/")) {
			return a / b;
		}
		return 0;
	}
	
}
