public class BoolValue implements IValue {
    ValueType type;
    boolean value;
    public BoolValue(boolean a) {
        this.value = a;
        this.type = ValueType.Bool;
    }
    public void print() {
        if(this.value) {
            System.out.print("true");
        } else {
            System.out.print("false");
        }
    }
    public ValueType getType() {
        return this.type;
    }
    public boolean getValue() {
        return this.value;
    }

}
