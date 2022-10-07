package todoApplication;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CommandLineParserTest {
  private CommandLineParser commandLineParser;
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


  @BeforeEach
  void setUp() {

  }

  @Test
  void argsProcessor() throws InvalidArgumentException{
    //csv file not provided
    String arg = "--csv-file";
    String[] testArgs1 = arg.split(" ");
    Exception e = assertThrows(InvalidArgumentException.class, () -> {
      new CommandLineParser(testArgs1);
    });
    assertEquals("Error: csv file not provided." + HELPER, e.getMessage());

    // file is not valid csv file
    arg = "--csv-file todo.txt";
    String[] testArgs2 = arg.split(" ");
    e = assertThrows(InvalidArgumentException.class, () -> {
      new CommandLineParser(testArgs2);
    });
    assertEquals("Error: todo.txt is not a valid file" + HELPER, e.getMessage());

    //Error: todoText is not provide when --add-todo provided
    arg = "--csv-file todos.csv --add-todo ";
    String[] testArgs3 = arg.split(" ");
    e = assertThrows(InvalidArgumentException.class, () -> {
      new CommandLineParser(testArgs3);
    });
    assertEquals("Error: please type in the todo description." + HELPER, e.getMessage());

    //  Error: --todo-Text not followed by text
    arg = "--csv-file todos.csv --add-todo --todo-text --completed";
    String[] testArgs4 = arg.split(" ");
    e = assertThrows(InvalidArgumentException.class, () -> {
      new CommandLineParser(testArgs4);
    });
    assertEquals("Error: please type in the todo description." + HELPER, e.getMessage());

    //Error:  the date input is not the pattern asked
    arg = "--csv-file todos.csv --add-todo --todo-text homework9 --due 04-28-2022";
    String[] testArgs5 = arg.split(" ");
    e = assertThrows(InvalidArgumentException.class, () -> {
      new CommandLineParser(testArgs5);
    });
    assertEquals("Error: only accept date patten mm/dd/yyyy" + HELPER, e.getMessage());

    //Error:  priority number can only be 1, 2 or 3
    arg = "--csv-file todos.csv --add-todo --todo-text homework9 --priority 0";
    String[] testArgs6 = arg.split(" ");
    e = assertThrows(InvalidArgumentException.class, () -> {
      new CommandLineParser(testArgs6);
    });
    assertEquals("Error: priority number can only be 1, 2, or 3" + HELPER, e.getMessage());

    //Error: without "--addTodo" the followings can not be typed in
    arg = "--csv-file todos.csv --todo-text homework9 --priority 0";
    String[] testArgs7 = arg.split(" ");
    e = assertThrows(InvalidArgumentException.class, () -> {
      new CommandLineParser(testArgs7);
    });
    assertEquals("Error: --todo-text, --completed, --due, --priority, --category must follow --add-todo"
        + HELPER, e.getMessage());

    //Error: id must follow integer type
    arg = "--csv-file todos.csv --add-todo --todo-text homework9 --priority 1 --complete-todo 11.2";
    String[] testArgs8 = arg.split(" ");
    e = assertThrows(InvalidArgumentException.class, () -> {
      new CommandLineParser(testArgs8);
    });
    assertEquals("Error: only accept integer type ID" + HELPER, e.getMessage());

    //Error: can not combine sortByPriority and sortByDate
    arg = "--csv-file todos.csv --add-todo --todo-text homework9 --priority 1 --display --sort-by-date --sort-by-priority";
    String[] testArgs9 = arg.split(" ");
    e = assertThrows(InvalidArgumentException.class, () -> {
      new CommandLineParser(testArgs9);
    });
    assertEquals("Error: can not do sortByPriority and sortByDate at the same time" + HELPER, e.getMessage());

  }

  @Test
  void getCsvFile() throws InvalidArgumentException{
    String arg = "--csv-file todos.csv";
    String[] testArgs = arg.split(" ");
    CommandLineParser commandLineParser = new CommandLineParser(testArgs);
    assertEquals("todos.csv", commandLineParser.getCsvFile());
  }

  @Test
  void getAddToDo() throws InvalidArgumentException{
    String arg = "--csv-file todos.csv --add-todo --todo-text assignment9 ";
    String[] testArgs = arg.split(" ");
    CommandLineParser commandLineParser = new CommandLineParser(testArgs);
    assertTrue(commandLineParser.getAddToDo());
  }

  @Test
  void getTodoText() throws InvalidArgumentException {
    String arg= "--csv-file todos.csv --add-todo --todo-text assignment9 ";
    String[] testArgs = arg.split(" ");
    CommandLineParser commandLineParser = new CommandLineParser(testArgs);
    assertEquals("assignment9", commandLineParser.getTodoText());
  }

  @Test
  void getCompleted() throws InvalidArgumentException {
    String arg= "--csv-file todos.csv --add-todo --todo-text assignment9 --completed";
    String[] testArgs = arg.split(" ");
    CommandLineParser commandLineParser = new CommandLineParser(testArgs);
    assertTrue(commandLineParser.getCompleted());
  }
  @Test
  void getDueDate() throws InvalidArgumentException {
    String arg= "--csv-file todos.csv --add-todo --todo-text assignment9 --completed true --due 04/21/2022";
    String[] testArgs = arg.split(" ");
    CommandLineParser commandLineParser = new CommandLineParser(testArgs);
    assertEquals("04/21/2022", commandLineParser.getDueDate());
  }

  @Test
  void getPriorityNumber() throws InvalidArgumentException {
    String arg= "--csv-file todos.csv --add-todo --todo-text assignment9 --priority 2";
    String[] testArgs = arg.split(" ");
    CommandLineParser commandLineParser = new CommandLineParser(testArgs);
    assertEquals("2", commandLineParser.getPriorityNum());
  }

  @Test
  void getCategory() throws InvalidArgumentException {
    String arg= "--csv-file todos.csv --add-todo --todo-text assignment9 --category school";
    String[] testArgs = arg.split(" ");
    CommandLineParser commandLineParser = new CommandLineParser(testArgs);
    assertEquals("school", commandLineParser.getCategory());
  }

  @Test
  void getCompletedTodoIds() throws InvalidArgumentException {
    String arg = "--csv-file todos.csv --add-todo --todo-text homework9 --priority 1 --complete-todo 11 --complete-todo 12";
    String[] testArgs = arg.split(" ");
    CommandLineParser commandLineParser = new CommandLineParser(testArgs);
    List<String> ids = new ArrayList<>();
    ids.add("11");
    ids.add("12");
    assertEquals(ids, commandLineParser.getCompletedTodoIds());
  }

  @Test
  void getDisplay() throws InvalidArgumentException {
    String arg= "--csv-file todos.csv --add-todo --todo-text assignment9 --category school --display --show-category school";
    String[] testArgs = arg.split(" ");
    CommandLineParser commandLineParser = new CommandLineParser(testArgs);
    assertTrue(commandLineParser.getDisplay());
  }

}