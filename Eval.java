import java.util.List;
import java.util.ArrayList;

// TODO: values should return in the repl
// TODO: add function definition

// TODO: Add boolean type
// TODO: Add integer?
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
    public static jist globalEnv = new jist(null);
    // for now env is just a list
    // could do with some tests.
    public static jist addToEnv(jist env, SymbolValue name, IValue value) {
        return (jist)jist.cons(new jist(name, value), env);
    }
    public static void addToGlobal(SymbolValue name, IValue value) {
        globalEnv = (jist)jist.cons(new jist(name, value), globalEnv);
    }
    public static IValue envLookup(jist env, SymbolValue value) {
        if(env == null) return null;
        jist firstPair = (jist)jist.car(env);
        SymbolValue name = (SymbolValue)jist.car(firstPair);
        if(name.getName().equals(value.getName())) {
            return jist.cdr(firstPair);
        }
        return envLookup((jist)jist.cdr(env), value);
        
    }
    public static IValue apply(jist form) {
        IValue fun = eval((SymbolValue)jist.car(form));
        if(fun.getType() != ValueType.Symbol) {
            // TODO: Better error reporting. Is should ideally have the raw text.
            System.out.println("Can't apply non-symbol!");
            // print GOT [TYPE] = fun.print())
        }
        jist args = (jist)jist.cdr(form);
        //        jist evalledArgs = evalArgs(args);
        // TODO: Apply primitives
        return apply((SymbolValue)fun, args);
    }
    // TODO: This case statement is a bit messy. Can we seperate
    // validation.
    public static IValue apply(SymbolValue fun, jist args) {
        String funName = fun.getName();
        if(funName.equals("+")) {
            if(args.isEmpty()) {
                System.out.println("No args to plus!");
            }
            return applyPlus(evalArgs(args));    
        }
        else if(funName.equals("-")) {
                if(args.isEmpty()) {
                System.out.println("No args to subtract!");
            }
                return applySubtract(evalArgs(args));    
        }
        else if(funName.equals("*")) {
                if(args.isEmpty()) {
                    System.out.println("No args to multiply!");
            }
                return applyMultiply(evalArgs(args));    
        }
        else if(funName.equals("/")) {
                if(args.isEmpty()) {
                    System.out.println("No args to divide!");
            }
                return applyDivide(evalArgs(args));    
        }
        else if (funName.equals("def")) {
            
            if(jist.count(args) != 2) {
                System.out.println("Def needs two args!");
                return null;
            }
            SymbolValue name = (SymbolValue)jist.car(args);
            IValue value = jist.car((jist)jist.cdr(args));
            IValue evalledValue = eval(value);
            addToGlobal(name, evalledValue);
            return null;
        }
        else if (funName.equals("println")) {
         
            if(jist.count(args) > 1) {
                // TODO: println takes multiple and strs them. Maybe once
                // str exists;
                System.out.println("println takes only one arg");
            }
            IValue value = eval(jist.car(args));
            value.print();
            System.out.print("\n");
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
    public static IValue evalSymbol(SymbolValue value) {
        if(value.getName().equals("*") || value.getName().equals("+") ||
           value.getName().equals("-") || value.getName().equals("/") ||
           value.getName().equals("def") || value.getName().equals("println")) {
            return value;
        }
        return envLookup(globalEnv, value);
    }
    public static IValue eval(IValue value) {
        if(value == null) return null;
        switch (value.getType()) {
        case Symbol: return evalSymbol((SymbolValue)value);
        case Number: return value;
        case List: return apply((jist)value);
        default: return null;
        }
    }
}
