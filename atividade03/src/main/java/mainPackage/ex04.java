/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mainPackage;

/**
 *
 * @author mateus-lyoshka
 */
import java.util.concurrent.atomic.AtomicInteger;

public class ex04 {
    
    private int init, end, numeroDeThreads;
    private AtomicInteger contadorPrimosAtomic = new AtomicInteger(0);
    private int contadorPrimosSyncMethod = 0;
    private int contadorPrimosSyncBlock = 0;

    public ex04(int init, int end, int numeroDeThreads) {
        this.init = init;
        this.end = end;
        this.numeroDeThreads = numeroDeThreads;
    }

    // Método para verificar se um número é primo
    private boolean ehPrimo(int numero) {
        if (numero <= 1) return false;
        for (int i = 2; i <= Math.sqrt(numero); i++) {
            if (numero % i == 0) return false;
        }
        return true;
    }

    // Versão usando AtomicInteger
    public void contarPrimosAtomic() {
        Thread[] threads = new Thread[numeroDeThreads];
        int intervalo = (end - init) / numeroDeThreads;

        for (int i = 0; i < numeroDeThreads; i++) {
            int initLocal = init + i * intervalo;
            int endLocal = (i == numeroDeThreads - 1) ? end : initLocal + intervalo;

            threads[i] = new Thread(() -> {
                for (int j = initLocal; j < endLocal; j++) {
                    if (ehPrimo(j)) {
                        contadorPrimosAtomic.incrementAndGet();
                    }
                }
            });
            threads[i].start();
        }

        for (Thread t : threads) {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    // Versão sincronizando o método
    public synchronized void incrementarContadorSyncMethod() {
        contadorPrimosSyncMethod++;
    }

    public void contarPrimosSyncMethod() {
        Thread[] threads = new Thread[numeroDeThreads];
        int intervalo = (end - init) / numeroDeThreads;

        for (int i = 0; i < numeroDeThreads; i++) {
            int initLocal = init + i * intervalo;
            int endLocal = (i == numeroDeThreads - 1) ? end : initLocal + intervalo;

            threads[i] = new Thread(() -> {
                for (int j = initLocal; j < endLocal; j++) {
                    if (ehPrimo(j)) {
                        incrementarContadorSyncMethod();
                    }
                }
            });
            threads[i].start();
        }

        for (Thread t : threads) {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    // Versão sincronizando o bloco
    public void contarPrimosSyncBlock() {
        Thread[] threads = new Thread[numeroDeThreads];
        int intervalo = (end - init) / numeroDeThreads;

        for (int i = 0; i < numeroDeThreads; i++) {
            int initLocal = init + i * intervalo;
            int endLocal = (i == numeroDeThreads - 1) ? end : initLocal + intervalo;

            threads[i] = new Thread(() -> {
                for (int j = initLocal; j < endLocal; j++) {
                    if (ehPrimo(j)) {
                        synchronized (this) {
                            contadorPrimosSyncBlock++;
                        }
                    }
                }
            });
            threads[i].start();
        }

        for (Thread t : threads) {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void executarTestes() {
        long init, end;

        // Teste com AtomicInteger
        init = System.currentTimeMillis();
        contarPrimosAtomic();
        end = System.currentTimeMillis();
        System.out.println("Contagem com AtomicInteger: " + contadorPrimosAtomic.get() + " - Tempo: " + (end - init) + " ms");

        // Teste com método sincronizado
        init = System.currentTimeMillis();
        contarPrimosSyncMethod();
        end = System.currentTimeMillis();
        System.out.println("Contagem com método sincronizado: " + contadorPrimosSyncMethod + " - Tempo: " + (end - init) + " ms");

        // Teste com bloco sincronizado
        init = System.currentTimeMillis();
        contarPrimosSyncBlock();
        end = System.currentTimeMillis();
        System.out.println("Contagem com bloco sincronizado: " + contadorPrimosSyncBlock + " - Tempo: " + (end - init) + " ms");
    }

    public static void main(String[] args) {
        int init = 1;
        int end = 100000;
        int numeroDeThreads = 4;

        ex04 encontrarPrimos = new ex04(init, end, numeroDeThreads);
        encontrarPrimos.executarTestes();
    }
}
