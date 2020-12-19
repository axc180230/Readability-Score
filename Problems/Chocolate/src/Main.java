import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int height = scanner.nextInt();
        int width = scanner.nextInt();
        int target = scanner.nextInt();

        if (target % width == 0 && (target / width) <= height) {
            System.out.println("YES");
        } else if (target % height == 0 && (target / height) <= width) {
            System.out.println("YES");
        } else {
            System.out.println("NO");
        }
    }
}