import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
//import jist;

// what are the types in scheme? integers rationals symbols boolean strings characters

public class repl {
    public static void main(String[] args) {
        try {
            repl();
        }
        catch (IOException e) {
            System.out.println("IOException caught.");
       }
    }
    public static void repl() throws IOException {
        InputStreamReader input = new InputStreamReader(System.in);
        BufferedReader reader = new BufferedReader(input);
        for (;;) {
            System.out.print("> ");
            String line = reader.readLine();
            if (line == null) break;
            try {
                replLine(line);
            }
            catch (Exception e) {
                System.out.printf("\nException: %s\n", e.getMessage());
            }
        }
    }
    public static void replLine(String line) {
        //jist j = jist.empty();
        //jist k = jist.cons(new NumberValue(3), j);
        //jist l = jist.cons(new NumberValue(4), k);
        ///jist m = jist.cons(new NumberValue(9), l);
        //m.print();
        //System.out.print("car = ");
        //jist.car(m).print();
        //System.out.print("\n");
        //System.out.print("cdr = ");
        //jist.cdr(m).print();
        //System.out.print("\n");

        Scanner scanner = new Scanner(line);
        List<Token> tokens = scanner.scanTokens();
        for (Token token : tokens) {
            switch(token.type) {
            case LeftParen: System.out.print("LEFT_PAREN\n"); break;
            case RightParen: System.out.print("RIGHT_PAREN\n"); break;
            case EOF: System.out.print("EOF\n"); break;
            case Symbol: System.out.print("SYMBOL: "); token.value.print(); System.out.print("\n"); break;
            case Number: System.out.print("NUMBER: "); token.value.print(); System.out.print("\n"); break;
            }
        }
        Parser parser = new Parser(tokens);
        IValue program = parser.parse();
        IValue out;
        if(program.getType() == ValueType.List) {
        // TODO: Need a tree printer now.
        System.out.print("Program: ");
        program.print();
        System.out.print("\n");
        System.out.printf("%d forms\n", jist.count(program));
        
        out = Eval.apply((jist)program);
        } else {
            out = Eval.eval((jist)program);
        }
        if(out != null && out.getType() == ValueType.Number) {
            System.out.printf("Final number %f\n", ((NumberValue)out).getValue());
        }
        else if (out == null) {
            System.out.println("nil");
        }
        else {
            System.out.println("Something went wrong");
         }
        //double one = ((NumberValue)Eval.envLookup(Eval.globalEnv, new SymbolValue("one"))).getValue();
        //System.out.printf("one = %f\n", one);
        //Eval.addToGlobal(new SymbolValue("two"), new NumberValue(2.0));
        //Eval.addToGlobal(new SymbolValue("three"), new NumberValue(3.0));
        //double two = ((NumberValue)Eval.envLookup(Eval.globalEnv, new SymbolValue("two"))).getValue();
        //System.out.printf("two = %f\n", two);
    }
}
