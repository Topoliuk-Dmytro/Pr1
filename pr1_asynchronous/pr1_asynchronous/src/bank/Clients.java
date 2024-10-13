package bank;

public class Clients implements Runnable{
    @Override
    public void run() {

        try {
            System.out.printf("Clint №%s came to the bank\n", Thread.currentThread().getName());
            Thread.sleep(20);

            if (ATM.isOpen()) {
                System.out.printf("Clint №%s is waiting for a free ATM\n", Thread.currentThread().getName());
                Thread.sleep(20);
                ATM.atms.acquire();

                System.out.printf("Clint №%s is withdrawing cash\n", Thread.currentThread().getName());
                Thread.sleep(2000);

                System.out.printf("Clint №%s received cash\n", Thread.currentThread().getName());
                Thread.sleep(200);
                ATM.atms.release();
            }
            else {
                System.out.printf("Clint №%s finds out that the bank is closed\n", Thread.currentThread().getName());
                Thread.sleep(20);
            }
            System.out.printf("Clint №%s leaves the bank\n", Thread.currentThread().getName());
            Thread.sleep(20);

        } catch (InterruptedException e) {
            System.out.printf("Client №%s was interrupted\n", Thread.currentThread().getName());
            Thread.currentThread().interrupt();
        }
    }
}
