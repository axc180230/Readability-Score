class Problem {
    public static void main(String[] args) {
        int location = -1;

        for (int i = 0; i < args.length; i++) {
            if ("test".equals(args[i])) {
                location = i;
                break;
            }
        }

        System.out.println(location);
    }
}