public class SymbolValue implements IValue {
    ValueType type;
    String value;
    public SymbolValue(String name) {
        this.value = name;
        this.type = ValueType.Symbol;
    }
    public void print() {
        System.out.print(this.value);
    }
    public ValueType getType() {
        return this.type; // should we just return the enum?
    }
    public String getName() {
        return this.value;
    }
    
}
