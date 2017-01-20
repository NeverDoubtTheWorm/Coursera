import java.io.File;
import java.util.Scanner;
import java.math.BigInteger;

class Karatsuba {

    public static BigInteger karatsuba(BigInteger x, BigInteger y) {
        // Using binary as the radix since it is much cleaner than doing
        //   the same with base 10 since it would require using logs and
        //   exponents to count length and split numbers
        int numBits = Math.max(x.bitLength(), y.bitLength());
        if (numBits < 4) return x.multiply(y);
        // effectively prepends an extra 0 if the number is odd to reduce calls
        int N = (numBits>>1) + (numBits&1);

        // x = a*2^N + b,   y = c*2^N + d
        BigInteger a = x.shiftRight(N);
        BigInteger b = x.subtract(a.shiftLeft(N));
        BigInteger c = y.shiftRight(N);
        BigInteger d = y.subtract(c.shiftLeft(N));

        BigInteger z0 = karatsuba(b, d);
        BigInteger z1 = karatsuba(b.add(a), d.add(c));
        BigInteger z2 = karatsuba(a, c);

        BigInteger diff = z1.subtract(z2).subtract(z0);

        return z0.add(diff.add(z2.shiftLeft(N)).shiftLeft(N));
    }

 
    public static void main(String[] args) {
        try {
            Scanner in = new Scanner(new File("TestCase1.txt"));
            BigInteger x = new BigInteger(in.nextLine(), 10);
            BigInteger y = new BigInteger(in.nextLine(), 10);

            BigInteger z = karatsuba(x, y);

            System.out.println(z.toString());
            System.out.println(x.multiply(y));
        } catch (Exception e){
            //
        }
    }
}