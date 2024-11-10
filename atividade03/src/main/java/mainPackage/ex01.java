/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package mainPackage;

/**
 *
 * @author mateus-lyoshka
 */
import java.util.concurrent.atomic.AtomicLong;

public class ex01 {
    private final AtomicLong valorAtual;

    public ex01(long valorInicial) {
        this.valorAtual = new AtomicLong(valorInicial);
    }

    public long gerarProximo() {
        long valorA, valorN;
        do {
            valorA = this.valorAtual.get();     
            valorN = valorA + 1;             
        } while (!this.valorAtual.compareAndSet(valorA, valorN)); 

        return valorN;  
    }

    public static void main(String[] args) {
        ex01 gerador = new ex01(0);

        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                for (int j = 0; j < 5; j++) {
                    System.out.println(Thread.currentThread().getName() + " gerou: " + gerador.gerarProximo());
                }
            }).start();
        }
    }
}

