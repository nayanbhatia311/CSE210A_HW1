import java.util.NoSuchElementException;
import java.util.Scanner;

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

class Node {
	Token token;
	Node left;
	Node right;
	
	Node(Node left, Token token, Node right){
		this.token = token;
		this.left = left;
		this.right = right;
	}
}

class AST {
	Node root;
	AST(Node root){
		this.root = root;
	}
	
	int Eval() {
		return Traverse(root);
	}

	int Traverse(Node root) {
		if (root.token.type.equals("INTEGER")) {
			return Integer.parseInt(root.token.value);
		} else {
			int left = Traverse(root.left);
			int right = Traverse(root.right);
			if (root.token.type.equals("PLUS")) {
				return left + right;
			} else if (root.token.type.equals("MINUS")) {
				return left - right;
			} else if (root.token.type.equals("MUL")) {
				return left * right;
			} else if (root.token.type.equals("DIV")) {
				return left / right;
			}
			else{

				return 0;
			}
		}
	}
}

class Parser implements GlobalConstants {
	Lexer lexer;
	Token current_token;
	Parser(Lexer lexer){
		this.lexer=lexer;
		this.current_token=this.lexer.getNextToken();
	}
	void error(){
		throw new RuntimeException("invalid input");	
	}	
	
	void eat(String token_type){
		if(this.current_token.type.equals(token_type)){
			this.current_token=this.lexer.getNextToken();
		}
		else{
			this.error();
		}
	}

	Node factor(){
		Token token = this.current_token;
		return new Node(null, token, null);
	}

	Node term(){

		Node node = this.factor();
		
		while(this.current_token.type==MUL || this.current_token.type==DIV){
			Token token=this.current_token;
			if(token.type==MUL){
				this.eat(MUL);
			}
			else if(token.type==DIV){
				this.eat(DIV);

			}
		node = new Node(node,token,this.factor());	

		
		
		}
	return node;
	}

	Node expr(){

		Node node=this.term();

		while(this.current_token.type==PLUS || this.current_token.type==MINUS){
			Token token =this.current_token;
			if(token.type==PLUS){
				this.eat(PLUS);

			}
			else if(token.type==MINUS){
				this.eat(MINUS);
			}


		}


	return node;
	}

	Node parse(){
		return this.expr();
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

public class arith implements GlobalConstants {
	public static void main(String args[]){
		Scanner scanner = new Scanner(System.in);
		String expression;	
		while(true){
			try{
				expression=scanner.nextLine();
			}
			catch(NoSuchElementException e){
				break;
			}
			Lexer lexer = new Lexer(expression); 
			Parser parser = new Parser(lexer);
			AST ast = new AST(parser.parse());
			System.out.println(ast.Eval());
		}
	}
}
