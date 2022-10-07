package todoApplication;

/**
 * This is the helper -- instructions for users, as well as the example
 */
public class InvalidArgumentException extends Exception {

  static final String HELPER =
      "\n\n The application supports three tasks, i.e., add a new to do (--add-todo), complete an existing "
          + "todo (--complete-todo), and display todos (--display). At least one of the three tasks must be"
          + "provided.\n"
          + "\nOptions and arguments:\n"
          + "--csv-file <path/to/file>: (Required) The CSV file containing the todos.\n"
          + "===================\n"
          + "--add-todo: (Optional) Add a new todo. If this option is provided, then --todo-text must also be provided. \n"
          + "--todo-text <description of todo>: (Optional) A description of the todo. Must be provided when --add-todo is provided.\n"
          + "--completed: (Optional) Sets the completed status of a new todo to true. Only work when --add-todo is provided.\n"
          + "--due <due date>: (Optional) Sets the due date of a new todo. Please follow format 'MM/dd/yyyy'(04/25/2022). "
          + "Only work when --add-todo is provided.\n"
          + "--priority <1, 2, or 3>: (Optional) Sets the priority of a new todo. The value can be 1, 2, or 3. "
          + "Only work when --add-todo is provided.\n"
          + "--category <a category name>: (Optional) Sets the category of a new todo. The value can be any String. Categories "
          + "do not need to be pre-defined. Only work when --add-todo is provided.\n"
          + "===================\n"
          + "--complete-todo <id>: Mark the Todo with the provided ID as complete. This argument can be provided multiple times. "
          + "The provide id must exist.\n"
          + "===================\n"
          + "--display: Display todos. If none of the following optional arguments are provided, displays all todos.\n"
          + "--show-incomplete: (Optional) If --display is provided, only incomplete todos should be displayed. \n"
          + "--show-category <category>: (Optional) If --display provided, only todos with the given category should be displayed.\n"
          + "--sort-by-date: (Optional) If --display is provided, sort the list of todos by date order (ascending). Cannot be "
          + "combined with --sort-by-priority.\n"
          + "--sort-by-priority: Optional) If --display is provided, sort the list of todos by priority (ascending). Cannot be "
          + "combined with --sort-by-date. \n"
          + "\nExample1:\n"
          + "--csv-file todos.csv --add-todo --todo-text homework9 --due 04/28/2022\n"
          + "--show-category school --sort-by-priority\n"
          + "\nExample2:\n"
          + "--csv-file todos.csv --complete-todo 1 --complete-todo 2\n";

  /**
   * construct for the InvalidArgumentException with the the user instructions
   * @param message - the user instructions
   */
  public InvalidArgumentException(String message) {
    super(message + HELPER);
  }
}
