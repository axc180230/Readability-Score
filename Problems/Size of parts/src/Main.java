import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        // put your code here
        Scanner scanner = new Scanner(System.in);

        int size = scanner.nextInt();
        int defectives = 0;
        int perfects = 0;
        int fixerUppers = 0;
        int uhhhh = 0;

        for (int i = 0; i < size; i++) {
            switch (scanner.nextInt()) {
                case -1:
                    defectives++;
                    break;
                case 0:
                    perfects++;
                    break;
                case 1:
                    fixerUppers++;
                    break;
                default:
                    uhhhh++;
            }
        }

        System.out.println(perfects + " " + fixerUppers + " " + defectives);
    }
}