/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mainPackage;
import java.util.concurrent.atomic.AtomicReference;

/**
 *
 * @author mateus-lyoshka
 */

public class Fila<T> {

    private static class Node<T> {
        final T valor;
        final AtomicReference<Node<T>> proximo;

        Node(T valor) {
            this.valor = valor;
            this.proximo = new AtomicReference<>(null);
        }
    }

    private final AtomicReference<Node<T>> cabeca;
    private final AtomicReference<Node<T>> cauda;

    public Fila() {
        Node<T> noSentinela = new Node<>(null); // Nó sentinela
        cabeca = new AtomicReference<>(noSentinela);
        cauda = new AtomicReference<>(noSentinela);
    }

    public void enfileirar(T valor) {
        Node<T> novoNode = new Node<>(valor);
        while (true) {
            Node<T> ultimo = cauda.get();
            Node<T> proximo = ultimo.proximo.get();
            
            if (ultimo == cauda.get()) { // Confirma se `ultimo` ainda é o último
                if (proximo == null) { // `proximo` de `ultimo` é null, ou seja, `ultimo` é realmente o fim da fila
                    if (ultimo.proximo.compareAndSet(null, novoNode)) { // Tenta anexar `novoNode` no fim
                        cauda.compareAndSet(ultimo, novoNode); // Move `cauda` para o novo último nodo
                        return;
                    }
                } else {
                    // Cauda está desatualizada, tenta avançar a `cauda`
                    cauda.compareAndSet(ultimo, proximo);
                }
            }
        }
    }

    public T desenfileirar() {
        while (true) {
            Node<T> primeiro = cabeca.get();
            Node<T> ultimo = cauda.get();
            Node<T> proximo = primeiro.proximo.get();
            
            if (primeiro == cabeca.get()) { // Confirma que `primeiro` ainda é o primeiro
                if (primeiro == ultimo) { // Cabeça igual à cauda, fila vazia ou cauda desatualizada
                    if (proximo == null) { // Se `proximo` também for null, a fila está realmente vazia
                        return null;
                    }
                    // Cauda desatualizada, tenta mover a `cauda` para `proximo`
                    cauda.compareAndSet(ultimo, proximo);
                } else {
                    // A fila não está vazia e `proximo` é o novo primeiro
                    T valor = proximo.valor;
                    if (cabeca.compareAndSet(primeiro, proximo)) { // Tenta mover a cabeça para `proximo`
                        return valor;
                    }
                }
            }
        }
    }

    public boolean filaVazia() {
        Node<T> primeiro = cabeca.get();
        Node<T> proximo = primeiro.proximo.get();
        return proximo == null;
    }

    public static void main(String[] args) {
        Fila<Integer> fila = new Fila<>();

        for (int i = 0; i < 10; i++) {
            final int valor = i;
            new Thread(() -> fila.enfileirar(valor)).start();
        }

        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                Integer valor = fila.desenfileirar();
                if (valor != null) {
                    System.out.println("Desenfileirado: " + valor);
                }
            }).start();
        }
    }
}
