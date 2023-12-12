import java.util.LinkedList;

public class jist implements IValue {

    IValue head;
    IValue rest;
    public jist(IValue head) {
        this.head = head;
        this.rest = null;
    }
    public jist(IValue head, IValue rest) {
        this.head = head;
        this.rest = rest;
    }
    public static jist empty() {
        return new jist(null);
    }
    public boolean isEmpty() {
        return head == null;
    }

    public void print() {
        jist.print(this);
    }
    // TODO: we should take ANY value as b, not just a list
    public static jist cons(IValue a, IValue b) {
        return new jist(a, b);
    }
    public static IValue car(jist a) {
        return a.head;
    }
    public static IValue cdr(jist a) {
        return a.rest;
    }
    public ValueType getType() {
        return ValueType.List;
    }

    public static int count(IValue j) {
        if(((jist)j).isEmpty()) {
            return 0;
        } else if (j.getType() != ValueType.List) {
            return 1;
        }
         else {
             return (1 + jist.count(jist.cdr((jist)j)));
        }
    }
    private static void printRest(jist j) {
        boolean more = j.rest != null && j.rest.getType() == ValueType.List && !((jist)j.rest).isEmpty();
        
        if(j.head.getType() == ValueType.List) {
            jist.print((jist)j.head);
        } else {
            j.head.print();
        }
        if(more) {
            System.out.print(" ");
            printRest((jist)j.rest);
        }
        else if (j.rest != null && j.rest.getType() == ValueType.List) {
            return;
        }
        else {
            System.out.print(" . ");
            j.rest.print();
        }
    }
    public static void print(jist j) {
        System.out.print("(");
        printRest(j);
        System.out.print(")");
        
    }
   
}
