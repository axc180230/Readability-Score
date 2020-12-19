import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        // put your code here
        Scanner scanner = new Scanner(System.in);
        final int divisor = 4;

        int numsCount = scanner.nextInt();
        int largestNumDivisalbe = Integer.MIN_VALUE;

        for (int i = 0; i < numsCount; i++) {
            int currentNum = scanner.nextInt();

            if (currentNum % divisor == 0 && currentNum > largestNumDivisalbe) {
                largestNumDivisalbe = currentNum;
            }
        }

        System.out.println(largestNumDivisalbe);
    }
}