import java.util.List;

public class Parser {
    private List<Token> tokens;
    private int current = 0;
    public Parser(List<Token> tokens) {
        this.tokens = tokens;
    }
    public jist parse() {
        try {
            return parseForm();
        }
        catch(Exception e) {
            System.out.printf("Parse ERROR: %s\n", e.getMessage());
        }
        return jist.empty();
    }
    public jist parseForm() throws Exception {
        consume(TokenType.LeftParen, "Expecting left paren.");
        IValue fun = parseFun();
        jist args = parseArgs();
        consume(TokenType.RightParen, "Expecting right paren");
        return jist.cons(fun, args);
    }
    public SymbolValue parseFun() throws Exception {
        Token token = advance();
        if(token.type != TokenType.Symbol) {
            throw new Exception("Not callable.");
        }
        return (SymbolValue)token.getValue();
    }
    public jist parseArgs() throws Exception {
        IValue left;
        if(peek().type == TokenType.RightParen) {
            return jist.empty();
        }
        if(peek().type == TokenType.LeftParen) {
            left = parseForm();
        } else {
            Token token = advance();
            left = token.value;
        }
        return jist.cons(left, parseArgs());
    }
    public void consume(TokenType type, String message) throws Exception {
        Token token = advance();
        if(token.type != type) {
            throw new Exception(message); 
        }
        
    }
    boolean isAtEnd() {
        return peek().type == TokenType.EOF;
    }
    public Token peek() {
        return tokens.get(current);
    }
    public Token advance() throws Exception {
        if(!isAtEnd()) {
            return tokens.get(current++);
        } else {
            throw new Exception("Trying to advance past EOF");
        }
    }
}
