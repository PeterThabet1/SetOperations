import java.util.TreeSet;

import textio.TextIO;
/**
 * The set calculator operates on two sets inputed by the user following the example
 * "+" = union
 * "-" = difference
 * "*" = intersection
 * Otherwise, a TreeSet with a negative integer is returned to indicate a bad operator.
 * Moreover, a message is printed out to indicate it.
 * 
 * @author Peter Thabet
 *
 */
public class SetCalculator { // SetCalculator begin

	public static TreeSet<Integer> A = new TreeSet<>(); //represents the first set
	public static TreeSet<Integer> B = new TreeSet<>(); // represents the second set
	public static char op; // represents the operator as (+,-, or *)

	
	/**
	 * This method takes two sets and an operator. The returned set is the product of the
	 * operation on both sets
	 * 
	 * @param a The first set
	 * @param b The second set
	 * @param oper the operator
	 * @return the product of both sets after operation
	 */
	public static TreeSet<Integer> operate(TreeSet<Integer> a, TreeSet<Integer> b, char oper){

		TreeSet<Integer> badSet = new TreeSet<>(); // a set to be returned if unexpected operator
		badSet.add(-1); // the element to be printed

		switch(oper) { // switch begin

		case '+' -> {
			A.addAll(B); // Union operation
			return A;
		}

		case '*' -> {
			A.retainAll(B); // Intersection operation
			return A;
		}

		case '-' -> {
			A.removeAll(B); // Difference operation
			return A;
		}

		default -> { // a bad operator, the badSet is returned
			System.out.println("Bad operator, Please choose one of the following" + 
					"+ , - , or *");
			return badSet;
		}
		} // switch end
	} // operate() end


	/**
	 * This method takes a string representation of a set and sets all global vars
	 * A, B, and op according to the representation. Negative numbers aren't allowed.
	 * If a negative number is shown, The following character is ignored as well.
	 * Even if a '[' is forgotten a message will be printed, but the reading continues.
	 * A ']' is important, the method will not function with out at least two '['s because
	 * it's the only way to mark the end.
	 * 
	 * @param set the String representation of the set
	 * @throws IllegalArgumentException if an expected character is entered.
	 */
	public static void readString(String set) throws IllegalArgumentException{ //readString begin

		String temp = set.replace(",", ""); // Commas are removed
		temp = temp.replace(" ", ""); // spaces are removed

		char[] arr = temp.toCharArray(); //changing the String to array for iteration
		boolean nextSet= false; // marks the end of first set and begin of the next one

		int i = 0; //update variable for the loop
		while(arr[i] != ']') { // while begin

			try {
				if(arr[i] != '[' && i==0) { // the begin didn't include the '['
					throw new IllegalArgumentException("A set should begin with \"[\"");
				}

			}
			catch(IllegalArgumentException iae) {
				System.out.println(iae.getMessage());
			}


			if(arr[i] == '[' && i==0) { // the begin included the '[', so ignored.
				i++;
				continue;
			}


			try {
				if(!Character.isDigit(arr[i])) { //unexpected character such as '-'
					throw new IllegalArgumentException("Only positive Integers are allowed");
				}
			}
			catch(IllegalArgumentException iae) { // the character and the next are ignored
				System.out.println(iae.getMessage());
				i+=2;
				continue;
			}


			if(nextSet == false) { //still in first set
				A.add(Character.getNumericValue(arr[i]));
				i++;
				continue;
			}
		}


		i++; // the end was reached at ']', so skipping it
		op = arr[i]; // the character of the operator
		i++; // moving on to the begin of nextSet
		int j = i; //begin of the next set is marked with a different pointer
		nextSet = true; //changed to nextSet


		while(nextSet == true && arr[j] != ']') {

			try {
				if(arr[j] != '[' && j==i) {  // the begin didn't include the '['
					throw new IllegalArgumentException("A set should begin with \"[\"");
				}
			}
			catch(IllegalArgumentException iae) {
				System.out.println(iae.getMessage());
			}


			if(arr[j] == '[' && j==i) { // the begin included the '[', so ignored.
				j++;
				continue;
			}


			try {
				if(!Character.isDigit(arr[j])) { //unexpected character such as '-'
					throw new IllegalArgumentException("Only positive Integers are allowed");
				}
			}
			catch(IllegalArgumentException iae) { // the character and the next are ignored
				System.out.println(iae.getMessage());
				j+=2;
				continue;
			}


			if(nextSet == true) { // in the second set
				B.add(Character.getNumericValue(arr[j]));
				j++;
				continue;
			}
		}
	}



	public static void main(String[] args) { // main() begin

		String temporary = ""; // String to be updated by the input
		int openB = 0; // how many '[' 
		int closeB = 0; // how many ']'

		System.out.println("Hello, Please specify the first set by including" +
				"only positive integers");
		System.out.println("Negative integers or fractions aren't allowed.");
		System.out.println("Include your numbers in [] brackets and seperated by commas");
		System.out.println("for example [1,2, 3] + [1,5,6]");


		while (true) {

			temporary = TextIO.getlnString(); //receives the String input
			char[] tempArr = temporary.toCharArray(); //changed to array for iteration
			openB=0; // how many '[' 
			closeB=0; // how many ']'

			for(char i : tempArr) {
				if(i == '[') {
					openB++;
				}

				if(i== ']') {
					closeB++;
				}
			}


			if(openB == closeB || closeB == 2) { // [] + [] or at least  ] + ] is accepted
				break;

			}
			else if(closeB < 2) { // [ ] + [ or [ + ] aren't accepted
				System.out.println(" A set is supposed to start with "
						+ "\"[\" first, then ends with \"]\"");
			}
		}

		readString(temporary);
		System.out.println(operate(A,B,op));
		
	} // main() end
	
} // SetCalculator end
