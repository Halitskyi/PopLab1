import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введіть крок: ");
        int step = scanner.nextInt();
        System.out.print("Введіть кількість потоків: ");
        int numThreads = scanner.nextInt();
        int permissionInterval = 10000;

        SummingThread[] threads = new SummingThread[numThreads];

        // Створення та запуск потоків
        for (int i = 0; i < numThreads; i++) {
            threads[i] = new SummingThread(i, step);
            threads[i].start();
        }

        // Чекання перед завершенням роботи потоків
        try {
            Thread.sleep(permissionInterval);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Зупинка потоків
        for (SummingThread thread : threads) {
            thread.setRunning(false);
        }

        // Очікування завершення кожного потоку
        for (SummingThread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        scanner.close();
    }

    private static class SummingThread extends Thread {
        private final int id;
        private final int step;
        private volatile boolean running = true;

        public SummingThread(int id, int step) {
            this.id = id;
            this.step = step;
        }

        public void run() {
            double sum = 0;
            int count = 0;
            double current = 0;

            while (running) {
                sum += current;
                count++;
                current += step;
            }

            System.out.printf("Thread %d: sum=%.2f, count=%d%n", id + 1, sum, count);
        }

        public void setRunning(boolean running) {
            this.running = running;
        }
    }
}

