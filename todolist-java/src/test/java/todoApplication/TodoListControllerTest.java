package todoApplication;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TodoListControllerTest {
  private TodoListController todoListController;
  private TodoList todosModel;
  private TodoListViewer todoListViewer;
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
  void setUp() throws IOException {
    todosModel = new TodoList(new CsvFileReader("todos.csv"),new CsvFileWriter("todos.csv"));
    todoListViewer = new TodoListViewer();
    todoListController = new TodoListController(todosModel, todoListViewer);

  }

  @Test
  void addNewTodo() {
    List<Todo> todos = todoListController.getTodos();
    Integer id = todoListController.getTodoSize() + 1;
    Todo newTodo = new Todo(id, "Buy eggs", false,"8/12/2021","1","Grocery");
    todoListController.addNewTodo(newTodo);

    Assertions.assertEquals(id + ",\"Buy eggs\",\"false\",\"8/12/2021\",\"1\",\"Grocery\"",todos.get(
        todos.size()-1).toString());
  }

  @Test
  void completeTodoIds() throws InvalidArgumentException {
    List<String> ids = new ArrayList<>();
    ids.add("1");
    ids.add("3");
    todoListController.completeTodoIds(ids);
    Assertions.assertTrue(todoListController.getTodos().get(0).getCompleted());
    Assertions.assertTrue(todoListController.getTodos().get(2).getCompleted());

    List<String> ids2 = new ArrayList<>();
    ids2.add("100");
    Exception e = Assertions.assertThrows(InvalidArgumentException.class, () -> {
      todoListController.completeTodoIds(ids2);
    });
    Assertions.assertEquals("Error: don't complete an unexisting id." + HELPER, e.getMessage());
  }

  @Test
  void updateView() throws InvalidArgumentException {

    ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outContent));

    todoListController.updateView(
        true,
        "home",
        false,
        false
    );

    Assertions.assertEquals("4,\"Clean the house\",\"false\",\"08/15/2021\",\"3\",\"home\"\n"
        , outContent.toString());

    outContent = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outContent));

    todoListController.updateView(
        false,
        "home",
        false,
        true
    );

    Assertions.assertEquals("4,\"Clean the house\",\"false\",\"08/15/2021\",\"3\",\"home\"\n"
        + "5,\"Buy yarn for blanket, stuffed toy\",\"true\",\"?\",\"?\",\"home\"\n", outContent.toString());

    outContent = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outContent));

    todoListController.updateView(
        false,
        "home",
        true,
        false
    );

    Assertions.assertEquals("4,\"Clean the house\",\"false\",\"08/15/2021\",\"3\",\"home\"\n"
        + "5,\"Buy yarn for blanket, stuffed toy\",\"true\",\"?\",\"?\",\"home\"\n", outContent.toString());

    Exception e = Assertions.assertThrows(InvalidArgumentException.class, () -> {
      todoListController.updateView(
          true,
          "work",
          false,
          false
      );
    });
    Assertions.assertEquals("Error: no todos based on your filter." + HELPER, e.getMessage());

  }


}