import java.util.*;

public class Operator {
	
	char operator;
	double a;
	double b;
	double c;
	double numMax = 2147483647;
	double numMin = -2147483648;
	
	public Operator(char chr) {
		operator = chr;
	}
	
	public char getOperator() {
		return operator;
	}
	
	//calls equivalent method for doing the operation
	public void doOperation(Stack<Integer> stack) {
		if(operator == '=') equals(stack);
		if(operator == '+') add(stack);
		if(operator == '-') subtract(stack);
		if(operator == '*') multiply(stack);
		if(operator == '/') divide(stack);
		if(operator == '^') power(stack);
		if(operator == '%') modulo(stack);
	}
	
	//check if a + b is within range and return the answer, returns numMax or numMin if not
	private double checkSizeAdd(double a, double b){
		if(a + b > numMax) return numMax;
		if(a + b < numMin) return numMin;
		return (a + b);
	}
	
	//check if b - a is within range and return the answer, returns numMax or numMin if not
	private double checkSizeSubtract(double a, double b){
		if(b - a > numMax) return numMax;
		if(b - a < numMin) return numMin;
		return (b - a);
	}
	
	//check if a * b is within range and return the answer, returns numMax or numMin if not
	private double checkSizeMultiply(double a, double b){
		if(a * b > numMax) return numMax;
		if(a * b < numMin) return numMin;
		return (a * b);
	}
	
	//check if b ^ a is within range and return the answer, returns numMax or numMin if not
	private double checkSizePower(Stack<Integer> stack, double a, double b){
		if(a < 0) {
			//will not perform operation if a < 0
			stack.push((int) b);
			stack.push((int) a);
			System.out.println("Negative power.");
			return 0.1;
		}
		else if(Math.pow(b, a) > numMax) return numMax;
		return (double)(Math.pow(b, a));
	}
	
	//checks the stack contains at least one element and prints an error if not
	private boolean notEmpty(Stack<Integer> stack) {
		if(stack.isEmpty()) {
			System.err.println("Stack underflow.");
			return false;
		}else return true;
	}
	
	//checks if a number is being dived by zero
	private boolean divideByZero(Stack<Integer> stack, double a) {
		if(a == 0) {
			//prints error message on division by zero
			System.err.println("Divide by 0.");
			stack.push((int) a);
			return true;
		} else return false;
	}
	
	//turns the top two values of the stack into double if there are at least two values in the stack
	private void topStackToDouble(Stack<Integer> stack) {
		a = stack.pop();
		if(stack.isEmpty()) {
			System.err.println("Stack underflow.");
			stack.push((int) a);
			return;
		}
		b = stack.pop();
	}
	
	//prints the top element of the stack
	public void equals(Stack<Integer> stack) {
		if(stack.isEmpty()) {
			System.err.println("Stack underflow.");
			return;
		} else System.out.println(stack.peek());
	}
	
	//pushes the value of a + b onto the stack if it is within range
	public void add(Stack<Integer> stack) {
		topStackToDouble(stack);
		 c = checkSizeAdd(a, b);
		stack.push((int) c);
	}
	
	//pushes the value of b - a onto the stack if it is within range
	public void subtract(Stack<Integer> stack) {
		topStackToDouble(stack);
		c = checkSizeSubtract(a, b);
		stack.push((int) c);
	}
	
	//pushes the value of a * b onto the stack if it is within range
	public void multiply(Stack<Integer> stack) {
		topStackToDouble(stack);
		 c = checkSizeMultiply(a, b);
		stack.push((int) c);
	}
	
	//pushes the value of b / a onto the stack if it is valid
	public void divide(Stack<Integer> stack) {
		if(notEmpty(stack))	a = stack.pop();
		else return;
		if(divideByZero(stack, a)) return;
		if(notEmpty(stack)) b = stack.pop();
		else return;
		stack.push((int) (b / a));
	}
	
	//pushes the value of b ^ a onto the stack if it is within range
	public void power(Stack<Integer> stack) {
		topStackToDouble(stack);
		 c = checkSizePower(stack, a, b);
		 if(c == 0.1) return;
		 stack.push((int) c);
	}
	
	//pushes the value of b % a onto the stack if it is valid
	public void modulo(Stack<Integer> stack) {
		if(notEmpty(stack))	a = stack.pop();
		else return;
		if(divideByZero(stack, a)) return;
		if(notEmpty(stack)) b = stack.pop();
		else return;
		stack.push((int) (b % a));
	}
}