/**
 * 
 * Student Name: Ashna Mittal
 * Student ID: 251206758
 */

public class asn1_b
{
    private static class Matrix2x2
    {
        long m00, m01, m10, m11;

        public Matrix2x2(long m00, long m01, long m10, long m11)
        {
            this.m00 = m00;
            this.m01 = m01;
            this.m10 = m10;
            this.m11 = m11;
        }

        // Method for multiplying two 2x2 matrices
        public static Matrix2x2 multiply(Matrix2x2 a, Matrix2x2 b)
        {
            return new Matrix2x2(
                    a.m00 * b.m00 + a.m01 * b.m10,
                    a.m00 * b.m01 + a.m01 * b.m11,
                    a.m10 * b.m00 + a.m11 * b.m10,
                    a.m10 * b.m01 + a.m11 * b.m11
            );
        }
    }

    // Method for matrix exponentiation, raising a matrix to a power
    private static Matrix2x2 matrixPower(Matrix2x2 base, int exponent) {
        Matrix2x2 result = new Matrix2x2(1, 0, 0, 1); // Identity matrix
        while (exponent > 0) {
            if (exponent % 2 == 1) {
                result = Matrix2x2.multiply(result, base);
            }
            base = Matrix2x2.multiply(base, base);
            exponent /= 2;
        }
        return result;
    }

    // Efficient method to calculate the nth Lucas number
    public static long lucasCompute(int n) {
        Matrix2x2 K = new Matrix2x2(1, 1, 1, 0);
        Matrix2x2 Kn = matrixPower(K, n);
        return Kn.m00 * 2 + Kn.m10 * -1; // Since K_0 = {2, -1}
    }

    // Main method to print the Lucas numbers at every 20th index from 0 to 500
    public static void main(String[] args)
    {
        for (int i = 0; i <= 25; i++)
        {
            int index = i * 20;
            System.out.printf("Lucas(%d) = %d\n", index, lucasCompute(index));
        }
    }
}
