import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Lexer {
    private final String input;
    private int index;

    private final Map<String, TokenType> keywords;

    public Lexer(String input) {
        this.input = input;
        this.index = 0;

        //Palabras reservadas--------------------------------------------------------------------------------------------
        this.keywords = new HashMap<>();
        this.keywords.put("if", TokenType.IF);
        this.keywords.put("else", TokenType.ELSE);
        this.keywords.put("while", TokenType.WHILE);
        this.keywords.put("true", TokenType.TRUE);
        this.keywords.put("false", TokenType.FALSE);
        this.keywords.put(("for"), TokenType.FOR);
        this.keywords.put("class", TokenType.CLASS);
        this.keywords.put("printf", TokenType.PRINTF);
        this.keywords.put("this", TokenType.THIS);
        this.keywords.put("return", TokenType.RETURN);
    }


    //Simbolos e identificadores-----------------------------------------------------------------------------------------
    public List<Token> lex() throws LexicalException {
        List<Token> tokens = new ArrayList<>();

        while (index < input.length()) {
            char c = input.charAt(index);

            if (Character.isDigit(c)) {
                StringBuilder sb = new StringBuilder();
                do {
                    sb.append(c);
                    index++;
                    if (index >= input.length()) {
                        break;
                    }
                    c = input.charAt(index);
                } while (Character.isDigit(c));
                tokens.add(new Token(TokenType.INT_LITERAL, sb.toString()));
            } else if (Character.isLetter(c)) {
                StringBuilder sb = new StringBuilder();
                do {
                    sb.append(c);
                    index++;
                    if (index >= input.length()) {
                        break;
                    }
                    c = input.charAt(index);
                } while (Character.isLetterOrDigit(c));
                String word = sb.toString();
                if (keywords.containsKey(word)) {
                    tokens.add(new Token(keywords.get(word), word));
                } else {
                    tokens.add(new Token(TokenType.IDENTIFIER, word));
                }
            } else if (c == '+') {
                tokens.add(new Token(TokenType.PLUS, "+"));
                index++;
            } else if (c == '-') {
                tokens.add(new Token(TokenType.MINUS, "-"));
                index++;
            } else if (c == '*') {
                tokens.add(new Token(TokenType.MULTIPLY, "*"));
                index++;
            } else if (c == '/') {
                tokens.add(new Token(TokenType.DIVIDE, "/"));
                index++;
            } else if (c == '(') {
                tokens.add(new Token(TokenType.LEFT_PAREN, "("));
                index++;
            } else if (c == ')') {
                tokens.add(new Token(TokenType.RIGHT_PAREN, ")"));
                index++;
            } else if (c == '{') {
                tokens.add(new Token(TokenType.LEFT_BRACE, "{"));
                index++;
            } else if (c == '}') {
                tokens.add(new Token(TokenType.RIGHT_BRACE, "}"));
                index++;
            } else if (c == '<') {
                if (input.charAt(index + 1) == '=') {
                    tokens.add(new Token(TokenType.LESS_THAN_OR_EQUAL, "<="));
                    index += 2;
                } else {
                    tokens.add(new Token(TokenType.LESS_THAN, "<"));
                    index++;
                }
            } else if (c == '>') {
                if (input.charAt(index + 1) == '='){
                    tokens.add(new Token(TokenType.GREATER_THAN_OR_EQUAL, ">="));
                    index+=2;
                } else {
                    tokens.add(new Token(TokenType.GREATER_THAN, "<"));
                    index++;
                }
            } else if (c == ';') {
                tokens.add(new Token(TokenType.SEMICOLON, ";"));
                index++;
            } else if (c == ':') {
                tokens.add(new Token(TokenType.COLON, ":"));
                index++;
            } else if (c == '=') {
                tokens.add(new Token(TokenType.ASSIGNMENT, "="));
                index++;
            }  else if (c == '=') {
                if (input.charAt(index + 1) == '=') {
                    tokens.add(new Token(TokenType.EQUALS, "=="));
                    index += 2;
                } else {
                    throw new LexicalException("Unknown character at index " + index);
                }
            } else if (c == '!') {
                if (input.charAt(index + 1) == '=') {
                    tokens.add(new Token(TokenType.NOT_EQUALS, "!="));
                    index += 2;
                } else {
                    throw new LexicalException("Unknown character at index " + index);
                }
            } else {
                throw new LexicalException("Unknown character at index " + index);
            }
        }
        return tokens;
    }

   


    public enum TokenType {
        //Palabras reservadas--------------------------------------------------------------------------------------------
        IF,
        ELSE,
        WHILE,
        TRUE,
        FALSE,
        FOR,
        CLASS,
        PRINTF,
        THIS,
        RETURN,

        //Signos del lenguaje--------------------------------------------------------------------------------------------
        PLUS,
        MINUS,
        TIMES,
        DIVIDE,
        LEFT_PAREN,
        RIGHT_PAREN,
        LEFT_BRACE,
        RIGHT_BRACE,
        MULTIPLY,
        LESS_THAN,
        GREATER_THAN,
        EQUALS,
        NOT_EQUALS,
        ASSIGNMENT,
        SEMICOLON,
        COLON,
        LESS_THAN_OR_EQUAL,
        GREATER_THAN_OR_EQUAL,


        //Identificadores------------------------------------------------------------------------------------------------
        IDENTIFIER,
        INT_LITERAL
    }

    public static class Token {
        private final TokenType type;
        private final String value;

        public Token(TokenType type, String value) {
            this.type = type;
            this.value = value;
        }

        public TokenType getType() {
            return type;
        }

        public String getValue() {
            return value;
        }

        @Override
        public String toString() {
            return "[" + type + ", " + value + "]";
        }
    }

    public static class LexicalException extends Exception {
        public LexicalException(String message) {
            super(message);
        }
    }
}
