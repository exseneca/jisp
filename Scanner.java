import java.util.ArrayList;
import java.util.List;

public class Scanner {
    private final String source;
    private final List<Token> tokens = new ArrayList<Token>();
    private int start = 0;
    private int current = 0;
    private int line = 1;

    public Scanner(String source) {
        this.source = source;
    }

    List<Token> scanTokens() {
        while(!isAtEnd()) {
            start = current;
            scanToken();
        }
        tokens.add(new Token(TokenType.EOF, null));
        return tokens;
    }
    void scanToken() {
        char c = advance();        
        switch(c) {
        case '(': addToken(TokenType.LeftParen); break;
        case ')': addToken(TokenType.RightParen); break;
        case ' ':
        case '\r':
        case '\t':
            // Ignore whitespace.
            break;
        case '\n':
            line++;
            break;

        default:
            if (isDigit(c)) {
                number();
            } else  {
                symbol();
            }
            break;
        }
    }
private void symbol() {
    while (isAlphaNumeric(peek())) advance();
    addToken(TokenType.Symbol, new SymbolValue(source.substring(start, current)));
}

     private void number() {
        while (isDigit(peek())) advance();

        // Look for a fractional part.
        if(peek() == '.' && isDigit(peekNext())) {
            // consume the "."
            advance();

            while (isDigit(peek())) advance();
         }
        addToken(TokenType.Number, new NumberValue(Double.parseDouble(source.substring(start, current))));

    }

    private void addToken(TokenType type) {
        tokens.add(new Token(type, null));
    }
    private void addToken(TokenType type, IValue value) {
        tokens.add(new Token(type, value));
    }
    private boolean isDigit(char c) {
        return c >= '0' && c <= '9';
    }
    private boolean isAtEnd() {
        return current >= source.length();
    }
    private char advance() {
         return source.charAt(current++);
    }
    
    private char peek() {
        if (isAtEnd()) return '\0';
        return source.charAt(current);
    }
    
    private char peekNext() {
        if (current + 1 >= source.length()) return '\0';
        return source.charAt(current + 1);
    }

    private boolean isAlpha(char c) {
        return (c >= 'a' && c <= 'z') ||
               (c >= 'A' && c <= 'Z') ||
                c == '_';
    }
    private boolean isAlphaNumeric(char c) {
        return isAlpha(c) || isDigit(c);
    }
}
