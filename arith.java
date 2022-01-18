import java.util.NoSuchElementException;
import java.util.Scanner;

public class arith{

	public static void main(String args[]){
	Scanner scanner=new Scanner(System.in);	
	while(true){
		try{
			String expression=scanner.nextLine();
			System.out.println(expression);
		}
		catch(NoSuchElementException e){
			break;
		}
}
	}
	

}

class Lexer{
	String text;
	int position;
	char current_char;
	public Lexer(String text){
		this.text=text;
		this.position=0;
		this.current_char=this.text.charAt(this.position);

	}

	@Override
	public String toString(){
	
		return this.text;
	
	}

}
