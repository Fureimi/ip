package MainRuntime;

import Exceptions.EmptyTaskException;
import Exceptions.MissingDeadlineException;
import Exceptions.MissingStartDateException;
import Exceptions.UnknownInputException;
import Tasks.Deadline;
import Tasks.Event;
import Tasks.Task;
import Tasks.ToDo;

import java.util.Scanner;
import java.util.ArrayList;

public class GermaBot {
    static int counter = 0;
    static final String LINE= "____________________________________________";
    static ArrayList<Task> toDoList = new ArrayList<>();
    public static int getIdx(String input) {
        return Integer.parseInt(input.substring(input.indexOf(" ") + 1)) - 1;
    }

    public static void createTodo(String input) throws EmptyTaskException {
        String toDoTask = input.substring(input.indexOf("todo ") + 5);
        if (toDoTask.isBlank()) {
            throw new EmptyTaskException();
        }
        toDoList.add(new ToDo(toDoTask));
        counter++;
        System.out.println("Gotcha! Added '" + toDoTask + "' to your To Do List!");
    }

    public static void createDeadline(String input) throws EmptyTaskException, MissingDeadlineException {
        String description = input.replaceFirst("deadline ", "");
        if (description.equals("deadline")) {
            throw new EmptyTaskException();
        }
        int idxOfEndDate = description.indexOf("/");
        if (idxOfEndDate == -1) {
            throw new MissingDeadlineException();
        }
        String date = description.substring( idxOfEndDate + 4);
        String toDoTask = description.substring(0, idxOfEndDate - 1);
        if (toDoTask.isBlank()) {
            throw new EmptyTaskException();
        }
        toDoList.add(new Deadline(description.substring(0, idxOfEndDate - 1), date));
        counter++;
        System.out.println("Gotcha! Added '" + toDoTask + "' to your To Do List!" +
                " You have to finish this by "  + date + ", so be reminded!");
    }

    public static void createEvent(String input) throws EmptyTaskException, MissingDeadlineException,
            MissingStartDateException {
        String description = input.replaceFirst("event ", "");
        int idxOfFrom = description.indexOf("/from");
        if (idxOfFrom == -1) {
            throw new EmptyTaskException();
        }
        int idxOfBy = description.indexOf("/to");
        if (idxOfBy == -1) {
            throw new MissingDeadlineException();
        }
        String startDate = description.substring(idxOfFrom + 6, idxOfBy - 1);
        if (startDate.isBlank()) {
            throw new MissingStartDateException();
        }
        String endDate = description.substring(idxOfBy + 4);
        if (endDate.isBlank()) {
            throw new MissingDeadlineException();
        }
        String toDoTask = description.substring(0, idxOfFrom - 1);
        if (toDoTask.isBlank()) {
            throw new EmptyTaskException();
        }
        toDoList.add(new Event(description.substring(0, idxOfFrom - 1), startDate, endDate));
        counter++;
        System.out.println("Gotcha! Added '" + toDoTask + "' to your To Do List!" +
                " This will happen from " + startDate + " to " + endDate + ", so please remember to mark it" +
                " on your calender! (Or ask me!)");
    }

    public static void createTask(String input) throws UnknownInputException {
        if (input.contains("todo")) {
            try {
                createTodo(input);
            } catch (EmptyTaskException e) {
                System.out.println("Uh oh, you did not specify a task to add! Let's try again!");
            }
        }
        else if (input.contains("deadline")) {
            try {
                createDeadline(input);
            } catch (EmptyTaskException e) {
                System.out.println("Uh oh, you did not specify a task to add! Let's try again!");
            } catch (MissingDeadlineException e) {
                System.out.println("Uh oh, you did not specify the deadline! Let's try again!");
            }

        }
        else if (input.contains("event")) {
            try {
                createEvent(input);
            } catch (EmptyTaskException e) {
                System.out.println("Uh oh, you did not specify a task to add! Let's try again!");
            } catch (MissingStartDateException e) {
                System.out.println("Uh oh, you did not specify a start time! Let's try again!");
            } catch (MissingDeadlineException e) {
                System.out.println("Uh oh, you did not specify the deadline! Let's try again!");
            }
        } else {
            throw new UnknownInputException();
        }
    }

    public static void main(String[] args) {
        String WelcomeMessage = "Hello! GermaBot here! \n"
                + "What may I do for you this fine day?";
        System.out.println(LINE);
        System.out.println(WelcomeMessage);
        System.out.println(LINE);
        while (true) {
            String input;
            Scanner in = new Scanner(System.in);
            input = in.nextLine();
            if (input.equals("bye")) {
                break;
            }
            if (input.equals("list")) {
                if (toDoList.isEmpty()) {
                    System.out.println("Umm... You haven't added any Tasks yet... Let's try adding " +
                            "some now!");
                } else {
                    System.out.println("Gotcha! Here are your tasks:");
                    for (int i = 0; i < counter; i++) {
                        if (toDoList.get(i) == null) {
                            break;
                        }
                        System.out.println(i + 1 + ". " + toDoList.get(i));
                    }
                }
            } else if (input.contains("unmark")) {
                int idx = getIdx(input);
                toDoList.get(idx).setDone(false);
                System.out.println("Aww, not done? Okay, I'll mark this task as undone: "
                        + "[" + toDoList.get(idx).getStatusIcon() + "] " + toDoList.get(idx).getDescription());
            } else if (input.contains("mark")) {
                int idx = getIdx(input);
                toDoList.get(idx).setDone(true);
                System.out.println("Good job! I'll mark this task as done: "
                        + "[" + toDoList.get(idx).getStatusIcon() + "] " + toDoList.get(idx).getDescription());
            } else {
                try {
                    createTask(input);
                } catch (UnknownInputException e){
                    System.out.println("Uhh.. I'm sorry but I'm not quite sure what in the world '" + input + "' means..." +
                            " Remember to include the Task Type in front of the input!!");
                }
            }
        }
        String GoodbyeMessage = "Thanks for using me! Hope to see you again soon~!";
        System.out.println(LINE);
        System.out.println(GoodbyeMessage);
        System.out.println(LINE);
    }
}
