public class BinaryDump {

    public static void main(String[] args) {
        int BITS_PER_LINE = 16;
        if (args.length == 1) {
            BITS_PER_LINE = Integer.parseInt(args[0]);
        }

        int count;
        for (count = 0; !BinaryStdIn.isEmpty(); count++) {
            if (BITS_PER_LINE == 0) { BinaryStdIn.readBoolean(); continue; }
            else if (count != 0 && count % BITS_PER_LINE == 0) StdOut.println();
            if (BinaryStdIn.readBoolean()) StdOut.print(1);
            else                           StdOut.print(0);
        }
        // Print remaining bits if the total count is not a multiple of BITS_PER_LINE
        while (count % BITS_PER_LINE != 0) {
            if (count % BITS_PER_LINE < 8) {
                StdOut.print(0); // Pad with zeros
            } else {
                StdOut.print((10 >> (7 - (count % BITS_PER_LINE - 8))) & 1); // Pad with binary representation of newline
            }
            count++;
        }
        if (BITS_PER_LINE != 0) StdOut.println();
        StdOut.println(count + " bits");
    }
}