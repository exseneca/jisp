public class ProcedureValue implements IValue {
    jist body;
    jist params;
    jist env;
    public ProcedureValue(jist body, jist params, jist env) {
        this.body = body;
        this.params = params;
        this.env = env;
    }
    public jist body() {
        return this.body;
    }
    public jist params() {
        return this.params;
    }
    public jist env() {
        return this.env;
    }
    public void print() {
        System.out.print("(function ");
        params.print();
        System.out.print(" ");
        body.print();
        System.out.print(")");
    }

    public ValueType getType() {
        return ValueType.Procedure;
    }
}
