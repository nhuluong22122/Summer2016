package datastructure;

public class Test {
	public static void main(String[] args){
		System.out.println(isPalindrome("hihi"));
		System.out.println(isPal("cheeseburger"));
	}
	
	public static boolean isPalindrome(String str){
		Stack<String> stack = new Stack<String>();
		Queue<String> queue = new Queue<String>();
		String s = new String();
        for (int i = 0; i < str.length( ); i++) {
            s = "" + str.charAt(i);
            queue.add(s);
            stack.add(s);
        }
        System.out.print("Queue: \n");
        queue.print();
        System.out.print("\nStack: \n");
        stack.print();
        System.out.println();
        while (!queue.isEmpty( )) {
        	System.out.println("Queue removed: " + queue.peek());
        	System.out.println("Stack removed: " +stack.peek());
            if (!queue.remove().equals(stack.remove()))
                return false;
        }
 		return true;
		
	}
	public static boolean isPal(String str){
		if(str.length() == 0 || str.length() == 1){
			return true;
		}
		String first = str.substring(0, 1);
		String last = str.substring(str.length()-1);
		String middle = str.substring(1,str.length()-1);
		return (first.equals(last)&& isPal(middle));
	}
}
