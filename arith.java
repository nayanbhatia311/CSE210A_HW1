import java.util.NoSuchElementException;
import java.util.Scanner;

public class arith{
	public static void main(String args[]){
	Scanner scanner=new Scanner(System.in);
	//new GlobalConst();
	String expression;	
	while(true){
		try{
			expression=scanner.nextLine();
			System.out.println(expression);
		}
		catch(NoSuchElementException e){
			break;
		}
	Lexer lexer=new Lexer(expression); 
	}
	}
	

}

interface GlobalConstants{
	String INTEGER="INTEGER";
	String PLUS="PLUS";
	String MINUS="MINUS";
	String MUL="MUL";
	String DIV="DIV";
	String LPAREN="(";
	String RPAREN=")";
	String EOF="EOF";

}

class GlobalConst implements GlobalConstants{
	public GlobalConst(){
		System.out.println(INTEGER);
		System.out.println(new Token(INTEGER,"3"));
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
	
	public void Advance(){

		this.position+=1;
		if(this.position>(this.text.length()-1)){
			this.current_char='\u001a';
		}
		else{

			this.current_char=this.text.charAt(this.position);
		}
	}
	
	public void skip_whitespace(){

		while(!(this.current_char=='\u001a') && Character.isWhitespace(this.current_char)){

			this.Advance();
		}
		
	}

	public int integer(){
		StringBuilder result=new StringBuilder("");
		while(this.current_char!='\u001a' && Character.isDigit(this.current_char)){
			result.append(this.current_char);
			this.Advance();
		}
		return Integer.parseInt(result.toString());
	}

	
}

class Token{
	public String type;
	public String value;

	public Token(String type, String value){

		this.type=type;
		this.value=value;
	}
	
	@Override
	public String toString(){
		return "Token("+this.type+","+this.value+")";
	
	}	
	


}
