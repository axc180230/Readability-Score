import java.util.Arrays;

class Problem {

    public static void main(String[] args) {
        String operator = args[0];
        int result = 0;
        int[] newArgs = new int[args.length - 1];

        for (int i = 1; i < args.length; i++) {
            newArgs[i - 1] = Integer.parseInt(args[i]);
        }

        switch (operator) {
            case "MAX":
                Arrays.sort(newArgs);
                result = newArgs[newArgs.length - 1];
                break;
            case "MIN":
                Arrays.sort(newArgs);
                result = newArgs[0];
                break;
            case "SUM":
                result = Arrays.stream(newArgs).sum();
                break;
            default:
                // Do Nothing
                break;
        }

        System.out.println(result);
    }
}