// IValue can be a symbol or a number
import java.util.LinkedList;

public class jist implements IValue {
    static class JistItem {
        JistItem rest;
        IValue value;
        public JistItem(IValue a) {
            this.value = a;
            this.rest = null;
        }
        public boolean isList() {
            value.type == ValueType.List;
        }
        public JistItem(IValue a, JistItem b) {
            this.value = a;
            this.rest = b;
        }
        public void print() {
            value.print();
            if(this.rest != null) {
                System.out.print(" ");
                this.rest.print();
            }
        }

    }
    JistItem head;

    public jist(JistItem head) {
        this.head = head;
    }
    public static jist empty() {
        return new jist(null);
    }

    public void print() {
        System.out.print("(");
        if(this.head != null) {
            this.head.print();
        }
        System.out.print(")");
    }
    // TODO: we should take ANY value as b, not just a list
    public static jist cons(IValue a, jist b) {
        return new jist(new JistItem(a, b.head));
    }

    }
    public static IValue car(jist a) {
        return a.head.value;
    }
    public static jist cdr(jist a) {
        if(a.head != null) {
            return new jist(a.head.rest);
        }
        return a;
    }
    public boolean isEmpty() {
        return head == null;
    }
    public ValueType getType() {
        return ValueType.List;
    }
    private int count(jist j) {
        if(j.isEmpty()) return 0;
        return 1 + this.count(this.cdr(j));
    }
    public int count() {
        return count(this);
    }
}
