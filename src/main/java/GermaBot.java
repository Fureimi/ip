import java.util.Scanner;

public class GermaBot {
    public static void main(String[] args) {
        String WelcomeMessage = "____________________ \n"
                + "Hello! GermaBot here! \n"
                + "What may I do for you this fine day? \n"
                + "____________________";
        System.out.println(WelcomeMessage);
        while (true) {
            String echo;
            Scanner in = new Scanner(System.in);
            echo = in.nextLine();
            if (echo.equals("bye")) {
                break;
            }
            System.out.println(echo);
        }
        String GoodbyeMessage = "____________________ \n"
                + "Thanks for using me! Hope you again soon~! \n"
                + "____________________";
        System.out.println(GoodbyeMessage);
    }
}
