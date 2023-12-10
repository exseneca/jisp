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
            replLine(line);
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
        jist program = parser.parse();
        System.out.print("Program: ");
        program.print();
        System.out.print("\n");
        IValue out = Eval.apply(program);
        if(out.getType() == ValueType.Number) {
            System.out.printf("Final number %f\n", ((NumberValue)out).getValue());
        } else {
            System.out.println("Something went wrong");
         }
    }
}
