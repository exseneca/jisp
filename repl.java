import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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
        replLine("");
        //for (;;) {
        //    System.out.print("> ");
        //    String line = reader.readLine();
        //    if (line == null) break;
        //    replLine(line);
        //}
    }
    public static void replLine(String line) {
        System.out.print("Read: ");
        System.out.print(line);
        System.out.print("\n");
        jist j = jist.empty();
        jist k = jist.cons(new NumberValue(3), j);
        jist l = jist.cons(new NumberValue(4), k);
        jist m = jist.cons(new NumberValue(9), l);
        m.print();
        System.out.print("car = ");
        jist.car(m).print();
        System.out.print("\n");
        System.out.print("cdr = ");
        jist.cdr(m).print();
       
         
    }
}
