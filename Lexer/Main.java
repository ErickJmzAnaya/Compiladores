import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("Ingrese la expresion: ");
            String input = scanner.nextLine();
            if (input.isEmpty()) {
                break;
            }
            Lexer lexer = new Lexer(input);
            try {
                List<Lexer.Token> tokens = lexer.lex();
                for (Lexer.Token token : tokens) {
                    System.out.println(token);
                }
            } catch (Lexer.LexicalException e) {
                System.out.println("Error léxico: " + e.getMessage());
            }
        }
        scanner.close();
    }
}
