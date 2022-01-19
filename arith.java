import java.util.NoSuchElementException;
import java.util.Scanner;

public class arith implements GlobalConstants{
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

class AST{
	
}

class BinOp extends AST {

	Token op;
	Token token;
	BinOp left;
	BinOp right;
	
	BinOp(BinOp left, Token op, BinOp right){
		this.left=left;
		this.right=right;
		this.op=op;
		this.token=op;
	
	}
}

class Num extends AST {
	
	Token token;
	Num(Token token){

		this.token=token;
		this.token.value=token.value;

	}


}


class Lexer implements GlobalConstants{
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

		while(!(this.current_char==('\u001a')) && Character.isWhitespace(this.current_char)){

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

	public void error(){
		throw new RuntimeException("Invalid input");

	}

	public Token getNextToken(){
			
		while(!(this.current_char=='\u001a')){
		
			if(Character.isWhitespace(this.current_char)){
				this.skip_whitespace();
				continue;
			}

			if(Character.isDigit(this.current_char)){
				return new Token(INTEGER,Integer.toString(this.integer()));
			}

			if(this.current_char=='+'){
				this.Advance();
				return new Token(PLUS,"+");
			}
			if(this.current_char=='-'){
				this.Advance();
				return new Token(MINUS,"-");
			}
			if(this.current_char=='*'){
				this.Advance();
				return new Token(MUL,"*");
			}
			if(this.current_char=='/'){
				this.Advance();
				return new Token(DIV,"/");

			}
			if(this.current_char=='('){
				this.Advance();
				return new Token(LPAREN,"(");
			}
			if(this.current_char==')'){
				this.Advance();
				return new Token(RPAREN,")");
			}
			
		}
		this.error();
		return new Token(EOF,"\u001a");
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
