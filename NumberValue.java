public class NumberValue implements IValue {
    ValueType type;
    double value;
    public NumberValue(double a) {
        this.value = a;
        this.type = ValueType.Number;
    }
    public void print() {
        System.out.print(this.value);
    }
    public ValueType getType() {
        return this.type;
    }
    public double getValue () {
        return this.value;
    }
}
