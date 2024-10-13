package bank;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.Semaphore;

public class ATM {
    static int dayCount = 1;
    static int workTimeLength = 8000;
    static int nonWorkTimeLength = 16000;
    static boolean continueSimulation = true;

    static final Semaphore atms = new Semaphore(3, true);
    private static boolean isAvailableHours = true;

    public static synchronized boolean isOpen () {
        return isAvailableHours;
    }

    public static synchronized void openBank(int dayNumber) {
        isAvailableHours = true;
        System.err.printf("\n=============The %d working day has begun================\n", dayNumber+1);
    }

    public static synchronized void closeBank() {
        isAvailableHours = false;
        System.err.println("=============The bank is close================");
    }

    public static void main(String[] args) throws InterruptedException {
        Runnable bank = () -> {
            int i = 0;
            while(continueSimulation) {
                new Thread(new Clients(i)).start();
                i++;
                try {
                    Thread.sleep(new Random().nextInt(500, 2000));
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        };

        menu();

        Thread bankThread = new Thread(bank, "Bank");
        bankThread.start();

        for (int dayNumber = 0; dayNumber < dayCount; dayNumber++) {
            openBank(dayNumber);
            Thread.sleep(workTimeLength);
            closeBank();
            Thread.sleep(nonWorkTimeLength);
        }

        continueSimulation = false;
        System.err.println("The simulation is over");
    }

    public static void menu() {
        Scanner scanner = new Scanner(System.in);
        try {
            System.out.print("Enter how many days you want to simulate: ");
            dayCount = scanner.nextInt();
            dayCount = (dayCount > 0) ? dayCount : 1;

            System.out.print("Enter how many hours the bank is open: ");
            workTimeLength = scanner.nextInt();
            workTimeLength = (workTimeLength >= 0) ? workTimeLength : -workTimeLength;
            workTimeLength = (workTimeLength <= 24) ? workTimeLength * 1000 : 24000;
            nonWorkTimeLength = 24000 - workTimeLength;

            System.err.println("\nSimulation of " + dayCount + " days with " + workTimeLength/1000 + "-hour working day started");
        } catch (Exception e) {
            System.out.println("Invalid value!");
            menu();
        }
    }
}