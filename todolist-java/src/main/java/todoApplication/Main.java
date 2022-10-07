package todoApplication;

import java.io.IOException;
import java.util.List;
import javax.annotation.processing.Generated;

/**
 * Main class is a launcher class containing a main method that starts and implements the program
 */
@Generated("abc")
public class Main {

  /**
   * Main method that launches the program
   * @param args - user arguments passed through command line
   * @throws InvalidArgumentException -if invalid argument given
   * @throws IOException - if an I/O exception has occurred
   */
  public static void main(String[] args) throws InvalidArgumentException, IOException {

    CommandLineParser commandLineParser = new CommandLineParser(args);

    TodoListViewer viewer = new TodoListViewer();

    CsvFileReader csvFileReader = new CsvFileReader(commandLineParser.getCsvFile());
    CsvFileWriter csvFileWriter = new CsvFileWriter(commandLineParser.getCsvFile());
    //MNC model todoList with attributer csvFileReader, csvFileWriter
    TodoList todoList = new TodoList(csvFileReader, csvFileWriter);
    //MVC controller with attributer todoList and viewer
    TodoListController controller = new TodoListController(todoList, viewer);

    if (commandLineParser.getAddToDo()) {
      Integer currentTodoSize = controller.getTodoSize();
      // automatically generate todoId
      Todo todoItem = new Todo(
          currentTodoSize+1,
          commandLineParser.getTodoText(),
          commandLineParser.getCompleted(),
          commandLineParser.getDueDate(),
          commandLineParser.getPriorityNum(),
          commandLineParser.getCategory()
      );
      controller.addNewTodo(todoItem);
    }
    // user can type in one or multiple ids to request complete status changes, so we make 'ids'a list
    List<String> completedTodoIds = commandLineParser.getCompletedTodoIds();
    if (!completedTodoIds.isEmpty()) {
      controller.completeTodoIds(completedTodoIds);
    }
   //display todos as requested
    if (commandLineParser.getDisplay()){
      controller.updateView(
          commandLineParser.getShowIncomplete(),
          commandLineParser.getShowCategory(),
          commandLineParser.getSortByDate(),
          commandLineParser.getSortByPriority()
      );
    }
  }

}
