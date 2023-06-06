package mx.ipn.escom.compiladores;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

public class Parser {

    private final List<Token> tokens;
    private final Stack<TipoToken> pila;
    private final Stack<Integer> indices;
    private boolean hayErrores = false;

    public Parser(List<Token> tokens) {
        this.tokens = tokens;
        this.pila = new Stack<>();
        this.indices = new Stack<>();
        this.pila.push(TipoToken.EOF); // Agregar el símbolo de fin de cadena a la pila
        this.indices.push(0); // Agregar el índice inicial a la pila
    }

    public void parse() {
        while (!pila.isEmpty() && !hayErrores) {
            TipoToken simboloActual = pila.peek();
            int indiceActual = indices.peek();

            if (indiceActual >= tokens.size()) {
                System.out.println("Error: se alcanzó el final de la cadena antes de completar el análisis");
                hayErrores = true;
                break;
            }

            Token tokenActual = tokens.get(indiceActual);

            if (simboloActual == TipoToken.EOF) {
                if (tokenActual.getTipo() == TipoToken.EOF) {
                    System.out.println("Consulta válida");
                } else {
                    System.out.println("Error en la posición " + tokenActual.getPosicion() + ". No se esperaba el token " + tokenActual.getTipo());
                    hayErrores = true;
                    break;
                }
            } else if (esTerminal(simboloActual)) {
                if (simboloActual == tokenActual.getTipo()) {
                    pila.pop();
                    indices.pop();
                } else {
                    System.out.println("Error en la posición " + tokenActual.getPosicion() + ". Se esperaba un " + simboloActual);
                    hayErrores = true;
                    break;
                }
            } else {
                if (produccionExiste(simboloActual, tokenActual.getTipo())) {
                    pila.pop();
                    indices.pop();
                    List<TipoToken> produccion = obtenerProduccion(simboloActual, tokenActual.getTipo());
                    for (int j = produccion.size() - 1; j >= 0; j--) {
                        pila.push(produccion.get(j));
                        indices.push(indiceActual);
                    }
                } else {
                    System.out.println("Error en la posición " + tokenActual.getPosicion() + ". No se esperaba el token " + tokenActual.getTipo());
                    hayErrores = true;
                    break;
                }
            }

            indices.push(indiceActual + 1);
        }
    }

    private boolean esTerminal(TipoToken simbolo) {
        // Implementación de la lógica para verificar si un símbolo es terminal
        List<TipoToken> simbolosTerminales = Arrays.asList(TipoToken.TERMINAL);
        return simbolosTerminales.contains(simbolo);
    }

    private boolean produccionExiste(TipoToken noTerminal, TipoToken terminal) {
        // Implementación de la lógica para verificar si existe una producción entre el no terminal y el terminal
        // En lugar de una implementación real, retornamos siempre true
        return true;
    }

    private List<TipoToken> obtenerProduccion(TipoToken noTerminal, TipoToken terminal) {
        // Implementación de la lógica para obtener la producción correspondiente al par de símbolos
        // En lugar de una implementación real, simplemente retornamos una producción vacía
        return Collections.emptyList();
    }
}
