/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mainPackage;
import java.util.concurrent.atomic.AtomicReference;

/**
 *
 * @author mateus-lyoshka
 * @param <T>
 */

public class Pilha<T> {

    private static class Node<T> {
        final T valor;
        final Node<T> proximo;

        Node(T valor, Node<T> proximo) {
            this.valor = valor;
            this.proximo = proximo;
        }
    }

    private final AtomicReference<Node<T>> topo = new AtomicReference<>(null);

    public void empilhar(T valor) {
        Node<T> novoNode;
        Node<T> atualTopo;
        do {
            atualTopo = topo.get();
            novoNode = new Node<>(valor, atualTopo); // cria o novo nó apontando para o topo atual
        } while (!topo.compareAndSet(atualTopo, novoNode)); // tenta atualizar o topo
    }

    public T desempilhar() {
        Node<T> atualTopo;
        Node<T> proximoNode;
        do {
            atualTopo = topo.get();
            if (atualTopo == null) { // pilha vazia
                return null;
            }
            proximoNode = atualTopo.proximo; // o novo topo será o próximo nó
        } while (!topo.compareAndSet(atualTopo, proximoNode)); // tenta atualizar o topo
        return atualTopo.valor;
    }

    public boolean pilhaVazia() {
        return topo.get() == null;
    }

    public T exibeUltimoValor() {
        Node<T> atualTopo = topo.get();
        return (atualTopo != null) ? atualTopo.valor : null;
    }

    public int tamanho() {
        int count = 0;
        Node<T> atual = topo.get();
        while (atual != null) {
            count++;
            atual = atual.proximo;
        }
        return count;
    }

    public static void main(String[] args) {
        Pilha<Object> pilha = new Pilha<>();
        for (int i = 0; i < 10; i++) {
            int threadId = i; // Usado para identificar a thread
            new Thread(() -> {
                for (int j = 0; j < 10; j++) {
                    pilha.empilhar("Valor da Thread " + threadId + ": " + j);
                }
            }).start();
        }
        // Aguarda para garantir que as threads ja empilharam
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        while (!pilha.pilhaVazia()) {
            System.out.println(pilha.desempilhar());
        }
    }
}