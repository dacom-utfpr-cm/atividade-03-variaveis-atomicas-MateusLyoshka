[![Review Assignment Due Date](https://classroom.github.com/assets/deadline-readme-button-22041afd0340ce965d47ae6ef1cefeee28c7c493a6346c4f15d667ab976d596c.svg)](https://classroom.github.com/a/2qaa-MTW)
# Atividade - Variáveis Atômicas

1. Implemente um gerador de números sequenciais não
bloqueante usando AtomicLong e o compareAndSet.

2. Implemente uma estrutura de dados de pilha não
bloqueante usando o AtomicReference.

3. Implemente uma estrutura de dados de fila com lista ligada
não bloqueante usando o AtomicReference (Michael-Scott
nonblocking queue algorithm - https://www.cs.rochester.edu/u/scott/papers/1996_PODC_queues.pdf).

4. Faça um programa em Java que use Threads para encontrar
os números primos dentro de um intervalo. O método que
contabiliza os números primos deve possuir como entrada:
valor inicial e final do intervalo, número de threads. Implemente três versões: Usando Atomic, sincronizando o
método e sincronizando o bloco. Ao final, compare o desempenho medindo o
tempo de início e término para processar o intervalo.
