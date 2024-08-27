import java.io.IOException;

/**
 * Mira is a simple chatbot that echoes user commands and exits when the user types "bye".
 * It interacts with the user via the UI class, which handles input and output operations.
 */
public class Mira {
    private final UI ui; // handle user interface
    private final TaskList tasks; // Manages tasks
    private boolean isRunning; // default is true

    public Mira(UI ui) throws IOException, SecurityException, MiraException {
        this.ui = ui;
        this.tasks = new TaskList(this.ui, new Storage("./data/mira.txt"));
        this.isRunning = true;
    }

    /**
     * Runs the chatbot, continuously reading user commands and responding until the user types "bye".
     * The chatbot echoes all commands and displays a goodbye message when exiting.
     */
    public void run() {
        // show welcome message with multiline text
        this.ui.showMessage("Hello! I'm Mira\nWhat can I do for you?");
        while (this.isRunning) {
            // read user input until a newline is entered
            String userInput = ui.readCommand();
            String[] commandParts = userInput.split(" ", 2); // cam only split one time
            String command = commandParts[0];
            String arguments = commandParts.length > 1 ? commandParts[1] : "";
            try {
                switch (command) {
                    case "bye" -> {
                        this.ui.showMessage("Bye. Hope to see you again soon!");
                        this.isRunning = false;
                    }
                    case "list" -> this.tasks.listTasks();
                    case "mark" -> {
                        int index = Integer.parseInt(arguments);
                        this.tasks.markTask(index);
                    }
                    case "unmark" -> {
                        int index = Integer.parseInt(arguments);
                        this.tasks.unmarkTask(index);
                    }
                    case "delete" -> {
                        int index = Integer.parseInt(arguments);
                        this.tasks.deleteTask(index);
                    }
                    case "todo" -> this.tasks.addTask(new Todo(arguments));
                    case "deadline" -> {
                        String[] deadlineParts = arguments.split("/by", 2);
                        if (deadlineParts.length != 2) {
                            throw new MiraException("Invalid format. Use: deadline <description> /by <deadline>");
                        }
                        String description = deadlineParts[0].trim();
                        String by = deadlineParts[1].trim();
                        this.tasks.addTask(new Deadline(description, by));
                    }
                    case "event" -> {
                        String[] eventParts = arguments.split("/from|/to");
                        if (eventParts.length != 3) {
                            throw new MiraException("Invalid format. Use: event <description> /from <start> /to <end>");
                        }
                        String description = eventParts[0].trim();
                        String from = eventParts[1].trim();
                        String to = eventParts[2].trim();
                        this.tasks.addTask(new Event(description, from, to));
                    }
                    default -> throw new MiraException("I'm sorry, I don't understand that command.");
                }
            } catch (MiraException e) {
                this.ui.showMessage(e.getMessage());
            } catch (NumberFormatException | IndexOutOfBoundsException e) {
                this.ui.showMessage("Please provide a valid task number.");
            } catch (IOException e) {
                this.ui.showMessage("File path for storing of tasks is unusable.");
            } catch (Exception e) {
                this.ui.showMessage("Error: " + e.getMessage());
            }
        }
    }

    /**
     * The main method to start the Mira chatbot.
     *
     * @param args Command-line arguments (not used).
     */
    public static void main(String[] args) {
        UI ui = new UI();
        try {
            Mira mira = new Mira(ui);
            mira.run();
        } catch (MiraException e) {
            ui.showMessage(e.getMessage());
        } catch (SecurityException e) {
            ui.showMessage("Please make sure the directory has read and write permissions.");
        } catch (IOException e) {
            ui.showMessage("File path for storing of tasks is unusable.");
        }
    }
}
