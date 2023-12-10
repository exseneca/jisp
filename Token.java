public class Token {
    TokenType type;
    IValue value;
    public Token(TokenType type, IValue value) {
        this.type = type;
        this.value = value;
    }
    public IValue getValue() {
        return value;
    }
}
