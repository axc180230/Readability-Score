class Problem {

    public static void main(String[] args) {
        String operator = args[0];
        int operand1 = Integer.parseInt(args[1]);
        int operand2 = Integer.parseInt(args[2]);
        int result = 0;
        boolean invalidArgs = false;

        switch (operator) {
            case "+":
                result = operand1 + operand2;
                break;
            case "-":
                result = operand1 - operand2;
                break;
            case "*":
                result = operand1 * operand2;
                break;
            default:
                invalidArgs = true;
                break;
        }

        if (invalidArgs) {
            System.out.println("Unknown operator");
        } else {
            System.out.println(result);
        }
    }
}