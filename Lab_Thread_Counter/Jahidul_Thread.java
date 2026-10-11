
import java.util.concurrent.atomic.AtomicLong;

public class Jahidul_Thread {

    // Shared thread-safe and unsafe static counters
    static AtomicLong safeCounter = new AtomicLong(0);
    static long unsafeCounter = 0;

    // Thread class with a separate local counter
    static class CounterTask extends Thread {
        private final int increments;
        private final boolean threadSafe;
        private long localCounter = 0;

        // Constructor to initialize thread settings
        CounterTask(int increments, boolean threadSafe) {
            this.increments = increments;
            this.threadSafe = threadSafe;
        }

        // Runs when the thread starts
        public void run() {
            for (int i = 0; i < increments; i++) {

                // Increment the static counter
                if (threadSafe) {
                    safeCounter.incrementAndGet();
                } else {
                    unsafeCounter++;
                }

                // Increment the object's local counter
                localCounter++;
            }
        }

        // Return the local counter value
        public long getLocalCounter() {
            return localCounter;
        }
    }

    public static void main(String[] args)
            throws InterruptedException {

        // Check command-line arguments
        if (args.length != 3) {
            System.out.println(
                "Usage: java ThreadCounterLab <threads> <increments> <true|false>"
            );
            return;
        }

        // Read input values
        int n = Integer.parseInt(args[0]);
        int increments = Integer.parseInt(args[1]);
        boolean threadSafe = Boolean.parseBoolean(args[2]);

        // Validate inputs
        if (n <= 0 || increments < 0 ||
                !(args[2].equals("true") ||
                  args[2].equals("false"))) {
            System.out.println("Invalid input.");
            return;
        }

        // Reset counters before the experiment
        safeCounter.set(0);
        unsafeCounter = 0;

        // Create and start threads
        CounterTask[] tasks = new CounterTask[n];

        for (int i = 0; i < n; i++) {
            tasks[i] = new CounterTask(increments, threadSafe);
            tasks[i].start();
        }

        // Wait for threads and sum local counters
        long nonStaticTotal = 0;

        for (CounterTask task : tasks) {
            task.join();
            nonStaticTotal += task.getLocalCounter();
        }

        // Get the final static counter value
        long staticTotal = threadSafe
                ? safeCounter.get()
                : unsafeCounter;

        // Calculate expected count and difference
        long expected = (long) n * increments;
        long difference = Math.abs(staticTotal - nonStaticTotal);

        // Calculate percentage difference
        double percentage = nonStaticTotal == 0
                ? (difference == 0 ? 0.0 : Double.NaN)
                : difference * 100.0 / nonStaticTotal;

        // Display experiment results
        System.out.println("Threads: " + n);
        System.out.println("Increments per thread: " + increments);
        System.out.println("Mode: " +
                (threadSafe ? "Thread-safe" : "Unsafe"));
        System.out.println("Expected count: " + expected);
        System.out.println("Static count: " + staticTotal);
        System.out.println("Non-static total: " + nonStaticTotal);
        System.out.println("Absolute difference: " + difference);

        // Display percentage difference
        if (Double.isNaN(percentage)) {
            System.out.println("Percentage difference: Undefined");
        } else {
            System.out.printf(
                "Percentage difference: %.4f%%%n", percentage
            );
        }
    }
}
