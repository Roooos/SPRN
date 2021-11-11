import java.io.*;
import java.util.*;

public class SRPN {
	
	Stack<Integer> stack = new Stack<Integer>();
	Random r = new Random();
	double numMax = 2147483647;
	double numMin = -2147483648;
	int randCount = 0;
	
	public void processCommand(String s) {
		
		List<String> numArray = new ArrayList<String>();
		
		iterateInput(s, numArray);
	}
	
	//iterates through string, checking for specific character
	public void iterateInput(String s, List<String> numArray) {
		String numString = "";
		
		for(int i = 0; i < s.length(); i++) {
			
			if(s.charAt(i) == '#'){
				return;
			}
			
			if(s.length() > 1) {
				//adds a '-' to the array if a '-' is directly before to a number
				if(s.charAt(i) == '-' && isNum(s, i+1)) {
					numArray.add("-");
					continue;
				}
			}
			
			if(isNum(s, i)) {
				if(!numArray.isEmpty() && numArray.size()<2) {
					//removes unnecessary 0's from the string (ie. zeros at the beginning of each number)
					if(s.charAt(i) == '0' && numArray.get(0).equals("0")) {
						continue;
					}
				}
				//converts character to a string and adds it to numArray
				numArray.add(Character.toString(s.charAt(i)));
				//adds the array to a string
				numString = String.join("", numArray); //https://stackoverflow.com/questions/599161/best-way-to-convert-an-arraylist-to-a-string
				
				//assign value to input if it is out of range
				if(checkSize(numString) == 1) numString =  "2147483647";
				if(checkSize(numString) == -1) numString =  "-2147483648";
				//pushes the current value of numString onto the stack if the last char on the command line is a number
				if(i == (s.length()-1)) {
					stackPush(stack, numString);
				}
				continue;
			}
			
			if(s.charAt(i) == ' ' && numString != ""){
				//pushes the numString value onto the stack, clears the array and resets the value of numString if a space is entered
				stackPush(stack, numString);
				numArray.clear();
				numString = "";
				continue;
			}else if(s.charAt(i) == ' ') continue;
			
			if(isOperator(s.charAt(i))) {
				//pushes the numString value onto the stack, clears the array and resets the value of numString if an operator is entered
				if(numString != "") {
					stackPush(stack, numString);
					numArray.clear();
					numString = "";	
				}
				//calls doOperation method in Operator class
				Operator op = new Operator(s.charAt(i));
				op.doOperation(stack);
				continue;
			}
			
			//pushes a pseudo random number from the Random class array onto the stack, increments position by 1 each time
			if(s.charAt(i) == 'r') {
				stackPush(stack, r.getRand(randCount));
				randCount++;
				continue;
			}
			
			//displays the entire stack
			if(s.charAt(i) == 'd') {
				Iterator<Integer> iterator = stack.iterator(); //https://stackoverflow.com/questions/33877989/how-to-print-out-the-whole-contents-of-a-stack
				while (iterator.hasNext()) {
					System.out.println(iterator.next());
				}
				continue;
			}
			
			System.out.println("Unrecognised operator or operand \"" + s.charAt(i) + "\".");
		}
	}
	
	//pushes integer onto the stack if the stack has fewer has 23 items
	private void stackPush(Stack<Integer> stack, int numInt) {
		if(stack.size()<23) {
			stack.push(numInt);
		}
		else System.out.println("Stack overflow.");
	}
	
	//converts string into integer and pushes it onto the stack if the stack has fewer has 23 items
	private void stackPush(Stack<Integer> stack, String numString) {
		if(stack.size()<23) {
			stack.push(Integer.parseInt(numString));
		}
		else System.out.println("Stack overflow.");
	}
	
	//check if character is a number
	private boolean isNum(String s, int i) {
		//returns -1 if character is not within radix
		if(Character.digit(s.charAt(i), 10) >= 0) return true; //https://stackoverflow.com/questions/5439529/determine-if-a-string-is-an-integer-in-java
		else return false;
	}
	
	//checks if the size of the argument is within range
	//returns -1 if it is out of range and negative and 1 if it is out of range and positive
	private int checkSize(String numString){
		//numbers larger then 11 digits long will be out of range
		if(numString.length()>11) {
			if(numString.charAt(0) != '-') return 1;
			else return -1;
		}
		if(numString.length()==10) {
			if(Double.parseDouble(numString) >= numMax) return 1;
			if(Double.parseDouble(numString) <= numMin) return -1;
		}
		return 0;
	}
	
	//checks if the character at current iterative point is a operator and then calls function to do that operation
	private boolean isOperator(char c) {
		if(c == '=' || c == '+'|| c == '-'|| c == '*'|| c == '/'|| c == '^'|| c == '%')	return true;
		return false;
	}
	
	
	public static void main(String[] args) {
		
		SRPN sprn = new SRPN();
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		
		try {
			//Keep on accepting input from the command-line
			while(true) {
				String command = reader.readLine();
				
				//Close on an End-of-file (EOF) (Ctrl-D on the terminal)
				if(command == null){
					//Exit code 0 for a graceful exit
					System.exit(0);
				}
				//Otherwise, (attempt to) process the character
				sprn.processCommand(command);          
			}
		} catch(IOException e) {
			System.err.println(e.getMessage());
			System.exit(1);
		}
	}
}