import edu.princeton.cs.algs4.StdIn;

public class Permutation {
    public static void main(String[] args) {

        if (args.length != 1) {
            throw new IllegalArgumentException("Bad Command Line Arguments");
        }

        int k = Integer.parseInt(args[0]);

        RandomizedQueue<String> main = new RandomizedQueue<String>();

        while (!StdIn.isEmpty()) {
             main.enqueue(StdIn.readString());
        }

        while (k != 0) {
            System.out.println(main.dequeue());
            k--;
        }

    }
}
