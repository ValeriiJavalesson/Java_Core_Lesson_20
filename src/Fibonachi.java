import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Fibonachi {
    public static void main(String[] args) throws InterruptedException {
        MyThread m = new MyThread();
        m.start();
        m.join();
        System.out.println();
        Thread t = new Thread(new RunnableThread());
        t.start();
        t.join();
        System.out.println();

        System.out.println("Запускаємо виконання завданнь зі вказаною кількістю " +
                "потоків (2 потоки, три завдання:)");
        ExecutorService ex = Executors.newFixedThreadPool(2);
        ex.execute(new MyThread(5));
        ex.execute(new RunnableThread(5));
        ex.execute(new MyThread(5));
        ex.shutdown();
        Thread.sleep(13000);
        System.out.println();

        System.out.println("Запускаємо виконання завданнь одним потоком " +
                "(1 потік, два завдання:)");
        ExecutorService ex2 = Executors.newSingleThreadExecutor();
        ex2.execute(new RunnableThread(5));
        ex2.execute(new MyThread(5));
        ex2.shutdown();
    }
}

class MyThread extends Thread {
    private int a = 0;
    private int b = 1;
    private int input;

    MyThread() {
    }

    MyThread(int input) {
        this.input = input;
    }

    @Override
    public void run() {
        Scanner scan = new Scanner(System.in);
        if (input == 0) {
            System.out.println("Введіть кількість чисел послідовності Фібоначі: ");
            input = scan.nextInt();
        }
        System.out.print("Потік Thread:"+"(id:"+Thread.currentThread().threadId()+")");
        for (int i = 0; i < input; i++) {
            System.out.print(b + " ");
            int c = b;
            b += a;
            a = c;
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        System.out.println("(id"+Thread.currentThread().threadId() + ")закінчив роботу");
    }
}

class RunnableThread implements Runnable {

    private int a = 0;
    private int b = 1;
    private int input;
    RunnableThread(){}
    RunnableThread(int input){
        this.input = input;
    }

    @Override
    public void run() {
        Scanner scan = new Scanner(System.in);
        if (input == 0) {
            System.out.println("Введіть кількість чисел послідовності Фібоначі: ");
            input = scan.nextInt();
        }
        System.out.print("Потік Runnable:"+"(id:"+Thread.currentThread().threadId()+")");
        int[] fibonacci = new int[input];
        for (int i = 0; i < input; i++) {
            fibonacci[i] = b;
            int c = b;
            b += a;
            a = c;
        }
        for (int i = input - 1; i >= 0; i--) {
            System.out.print(fibonacci[i] + " ");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        System.out.println("(id"+Thread.currentThread().threadId() + ")закінчив роботу");
    }
}
