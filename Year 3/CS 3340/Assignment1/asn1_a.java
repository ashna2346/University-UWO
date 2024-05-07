/**
 * Student Name: Ashna Mittal
 * Student ID: 251206758
 */
public class asn1_a
{
    // Recursive method to calculate the Lucas number at index n
    public static int lucasCompute(int n) {
        if (n == 0) return 2;
        if (n == 1) return 1;
        return lucasCompute(n - 1) + lucasCompute(n - 2);
    }

    public static void main(String[] args) {
        // Loop to print the Lucas numbers at every 5th index from 0 to 50
        for (int i = 0; i <= 10; i++) {
            int index = i * 5;
            System.out.printf("lucas(%d) = %d\n", index, lucasCompute(index));
        }
    }
}
