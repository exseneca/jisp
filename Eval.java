import java.util.List;
import java.util.ArrayList;

// TODO: Add boolean type
// TODO: Add integer?
// TODO: Add list?
// TODO: Add number?
// TODO: Add null?

// TODO: Add support for pairs.

// TODO: Add support for greater than and equals.
// TODO: Add support for environments.
// TODO: Add support for defining functions and values.
// TODO: Add support for lambda functions.
// TODO: Add support for if
// TODO: Add support for cond.
// TODO: Add support for let
// TODO: Add support for case
// TODO: Add support for and
// TODO: Add support for begin.
// TODO: Add support for map
// TODO: Add support for reduce

public class Eval {
    public static IValue apply(jist form) {
        SymbolValue fun = (SymbolValue)jist.car(form);
        jist args = (jist)jist.cdr(form);
        jist evalledArgs = evalArgs(args);
        // TODO: Apply primitives
        return apply(fun, evalledArgs);
    }
    public static IValue apply(SymbolValue fun, jist args) {
        String funName = fun.getName();
        if(funName.equals("+")) {
            if(args.isEmpty()) {
                System.out.println("No args to plus!");
            }
            return applyPlus(args);    
        }
        else if(funName.equals("-")) {
                if(args.isEmpty()) {
                System.out.println("No args to subtract!");
            }
            return applySubtract(args);    
        }
        else if(funName.equals("*")) {
                if(args.isEmpty()) {
                    System.out.println("No args to multiply!");
            }
            return applyMultiply(args);    
        }
        else if(funName.equals("/")) {
                if(args.isEmpty()) {
                    System.out.println("No args to divide!");
            }
            return applyDivide(args);    
        }
        else if (funName.equals("def")) {
            if(jist.count(args) != 2) {
                System.out.println("Def needs two args!");
                return null;
            }
            return null;
        }
        else {
            System.out.printf("Unknown function %s\n", funName);
            return null;
        }
    }
    // TODO: I think we can have applyBinary? Maybe more difficult
    public static IValue applyPlus(jist args) {
        if(args.isEmpty()) return new NumberValue(0);
        IValue first = jist.car(args);
        jist rest = (jist)jist.cdr((jist)args);
        if(first.getType() != ValueType.Number) System.out.println("Non number type provided to plus!");
        return new NumberValue(((NumberValue)first).getValue()
                               + ((NumberValue)applyPlus(rest)).getValue()); 
        
           // TODO runtime error reporting needs a proper thing.
           // It could require some major refactorings.
        
    }
    public static IValue applySubtract(jist args) {
        if(args.isEmpty()) return new NumberValue(0);
        IValue first = jist.car(args);
        jist rest = (jist)jist.cdr(args);
        if(first.getType() != ValueType.Number) System.out.println("Non number type provided to plus!");
        return new NumberValue(((NumberValue)first).getValue()
                               - ((NumberValue)applySubtract(rest)).getValue()); 
        
           // TODO runtime error reporting needs a proper thing.
           // It could require some major refactorings.
        
    }
    public static IValue applyMultiply(jist args) {
        if(args.isEmpty()) return new NumberValue(1);
        IValue first = jist.car(args);
        jist rest = (jist)jist.cdr(args);
        if(first.getType() != ValueType.Number) System.out.println("Non number type provided to plus!");
        return new NumberValue(((NumberValue)first).getValue()
                               * ((NumberValue)applyMultiply(rest)).getValue()); 
        
           // TODO runtime error reporting needs a proper thing.
           // It could require some major refactorings.
        
    }
    public static IValue applyDivide(jist args) {
        if(args.isEmpty()) return new NumberValue(1);
        IValue first = jist.car(args);
        jist rest = (jist)jist.cdr(args);
        if(first.getType() != ValueType.Number) System.out.println("Non number type provided to plus!");
        return new NumberValue(((NumberValue)first).getValue()
                               / ((NumberValue)applyDivide(rest)).getValue()); 
        
           // TODO runtime error reporting needs a proper thing.
           // It could require some major refactorings.
        
    }
     
    public static jist evalArgs(jist form) {
        if(form.isEmpty()) return form;
        IValue arg = eval(jist.car(form));
        return (jist)jist.cons(arg, evalArgs((jist)jist.cdr(form)));
    }
    public static IValue eval(IValue value) {
        switch (value.getType()) {
        case Symbol: return value;
        case Number: return value;
        case List: return apply((jist)value);
        default: return null;
        }
    }
}
