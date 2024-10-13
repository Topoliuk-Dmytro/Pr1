package bank;

public class Clients implements Runnable{
    final private int id;

    public Clients(int id) {
        this.id = id;
    }

    @Override
    public void run() {

        try {
            System.out.println("Clint №" + id + " came to the bank");
            Thread.sleep(20);

            if (ATM.isOpen()) {
                System.out.println("Clint №" + id + " is waiting for a free ATM");
                Thread.sleep(20);
                ATM.atms.acquire();

                System.out.println("Clint №" + id + " is withdrawing cash");
                Thread.sleep(2000);

                System.out.println("Clint №" + id + " received cash");
                Thread.sleep(200);
                ATM.atms.release();
            }

            System.out.println("Clint №" + id + " leaves the bank");
            Thread.sleep(20);

        } catch (InterruptedException e) {
            System.out.println("Client №" + id + " was interrupted.");
            Thread.currentThread().interrupt();
        }
    }
}
