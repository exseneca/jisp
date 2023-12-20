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
    public static IValue apply(jist form, jist env) {
        form.print();
        jist.car(form);
        System.out.println("About to eval fun!");
        IValue fun = eval((SymbolValue)jist.car(form), env);
        System.out.println("Fun evalled");
        fun.print();
        System.out.print("\n");
        if(fun.getType() != ValueType.Symbol &&
           fun.getType() != ValueType.Procedure) {
            // TODO: Better error reporting. Is should ideally have the raw text.
            System.out.println("Must be symbol or lambda to apply!");
            // print GOT [TYPE] = fun.print())
        }
        System.out.println(">>> about to get args");
        jist args = (jist)jist.cdr(form);
        System.out.print(">>> args are ");
        args.print();
        System.out.println("");
        //        jist evalledArgs = evalArgs(args);
        // TODO: Apply primitives
        if (fun.getType() == ValueType.Procedure) {
            return applyProcedure((ProcedureValue)fun, args, env);
        } else {            
            return apply((SymbolValue)fun, args, env);
        }
    }
    public static jist addNamesAndValuesToEnv(jist names, jist values, jist env) {
        if(names.isEmpty()) {
            return env;
        }
        System.out.println("Add names and values to env names: ");
        names.print();
        System.out.print("\n values: ");
        values.print();
        System.out.print("\n");
        
        return addToEnv(addNamesAndValuesToEnv((jist)jist.cdr(names), (jist)jist.cdr(values), env),
                        (SymbolValue)jist.car(names),
                        jist.car(values));
            
    }
    public static IValue applyProcedure(ProcedureValue fun, jist args, jist env) {
        if(jist.count(fun.params()) != jist.count(args)) {
            System.out.println("Unequal args!!");
            return null;
        }
        jist evalledArgs = (jist)evalArgs(args, env);
        jist applicationEnv = addNamesAndValuesToEnv(fun.params(), evalledArgs, env);
        return eval(fun.body(), applicationEnv);
    }
    // TODO: This case statement is a bit messy. Can we seperate
    // validation.
    public static IValue apply(SymbolValue fun, jist args, jist env) {
        String funName = fun.getName();
        if(funName.equals("+")) {
            if(args.isEmpty()) {
                System.out.println("No args to plus!");
            }
            System.out.println("Applying +");
            args.print();
            jist evalledArgs = evalArgs(args, env);
            System.out.println("Args evalled");
            evalledArgs.print();
            return applyPlus(evalledArgs);
        }
        else if(funName.equals("-")) {
                if(args.isEmpty()) {
                System.out.println("No args to subtract!");
            }
                return applySubtract(evalArgs(args, env));    
        }
        else if(funName.equals("*")) {
                if(args.isEmpty()) {
                    System.out.println("No args to multiply!");
            }
                return applyMultiply(evalArgs(args, env));    
        }
        else if(funName.equals("/")) {
                if(args.isEmpty()) {
                    System.out.println("No args to divide!");
            }
                return applyDivide(evalArgs(args, env));    
        }
        else if (funName.equals("=")) {
            if(jist.count(args) != 2) {
                System.out.println("Equal takes two args!!");
                return null;
            }
            System.out.println("about to apply =");
            IValue evalledArgs = evalArgs(args, env);
            evalledArgs.print();
            return applyEqual((jist)evalledArgs);
        }
        else if (funName.equals("def")) {
            
            if(jist.count(args) != 2) {
                System.out.println("Def needs two args!");
                return null;
            }
            SymbolValue name = (SymbolValue)jist.car(args);
            IValue value = jist.car((jist)jist.cdr(args));
            IValue evalledValue = eval(value, env);
            addToGlobal(name, evalledValue);
            return null;
        }
        else if (funName.equals("println")) {
         
            if(jist.count(args) > 1) {
                // TODO: println takes multiple and strs them. Maybe once
                // str exists;
                System.out.println("println takes only one arg");
                return null;
            }
            IValue value = eval(jist.car(args), env);
            value.print();
            System.out.print("\n");
            return null;
        }
        else if (funName.equals("if")) {
            if(jist.count(args) != 3) {
                System.out.println("if takes three args");
                return null; // Should we return error?
            }
            IValue arg1 = jist.car(args);
            IValue arg2 = jist.car((jist)jist.cdr(args));
            IValue arg3 = jist.car((jist)jist.cdr((jist)jist.cdr(args)));
            if(isTruthy(eval(arg1, env))) {
                return eval(arg2, env);
                } else {
                return eval(arg3, env);
                }
            
        }
        else if (funName.equals("defn")) {
            if(jist.count(args) != 3) {
                    System.out.println("def needs three args (lambda (params) body)");
                }
                SymbolValue name = (SymbolValue)jist.car(args);
                jist params = (jist)jist.cadr(args);
                jist body = (jist)jist.caddr(args);
                ProcedureValue proc = new ProcedureValue(body, params, env);
                addToGlobal(name, proc);
                return null;
        }
        else if (funName.equals("lambda")) {
            if(jist.count(args) != 2) {
                    System.out.println("lambda needs three args (lambda (params) body)");
                    return null;
                }
            jist params = (jist)jist.car(args);
            jist body = (jist)jist.cadr(args);
            jist closureEnv = env;
            return new ProcedureValue(body, params, closureEnv); // TODO: now what do we do with the env??
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
    public static IValue applyEqual(jist args) {
        // TODO: make recursive
        IValue first = jist.car(args);
        IValue second = jist.car((jist)(jist.cdr(args)));
        if(first.getType() != second.getType()) {
            System.out.println("Equal must have same type args!");
            return null;
        }
        if(first.getType() == ValueType.Number) {
            boolean eq = ((NumberValue)first).getValue() == ((NumberValue)second).getValue();
            return new BoolValue(eq);
        }
        if(first.getType() == ValueType.Bool) {
            boolean eq = ((BoolValue)first).getValue() == ((BoolValue)second).getValue();
            return new BoolValue(eq);
        }
        return new BoolValue(false);
        // TODO: Need to equal lists? I think first we need quote values
    }
     
    public static jist evalArgs(jist form, jist env) {
        if(form.isEmpty()) return form;
        IValue arg = eval(jist.car(form), env);
        return (jist)jist.cons(arg, evalArgs((jist)jist.cdr(form), env));
        // TODO: Do we return the env here???
        // Exactly what happens to the environemnt???
    }
    public static IValue evalSymbol(SymbolValue value, jist env) {
        if(value.getName().equals("*") || value.getName().equals("+") ||
           value.getName().equals("-") || value.getName().equals("/") ||
           value.getName().equals("def") || value.getName().equals("println") ||
           value.getName().equals("if") || value.getName().equals("=") ||
           value.getName().equals("defn")) {
            return value;
        }
        if (env.isEmpty()) {
            System.out.println("Trying to look into an empty env!");
            return null;
        }
        IValue lookup = envLookup(env,value);
        
        System.out.println("Found symbol");
        lookup.print();
        System.out.println("\n");
        env.print();
        System.out.println("\n");
        if(lookup == null) {
            System.out.println("Couldn't find symbol");
            value.print();
        }
        return lookup;
    }
    public static IValue eval(IValue value, jist env) {
        System.out.println("about to eval");
        value.print();
        System.out.println(value.getType());
        System.out.println("\n");
        if(value == null) return null;
        switch (value.getType()) {
        case Symbol: return evalSymbol((SymbolValue)value, env);
        case Number:
        case Procedure: return value;
        case Bool: return value;
        case List: return apply((jist)value, env);
        default: return null;
        }
    }

    public static boolean isTruthy(IValue value) {
        if(value.getType() == ValueType.Bool)
        {
            return ((BoolValue)value).getValue();
        }
        if(value == null)
        {
            return false;
        }
        return true;
         
    }
}
