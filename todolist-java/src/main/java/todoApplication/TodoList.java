package todoApplication;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

/**
 * Class TodoList supports functionality in add a new todo and completed an existing todo
 */
public class TodoList {
  private List<Todo> todos = new ArrayList<>();
  private Integer todoSize;
  private CsvFileReader csvFileReader;
  private CsvFileWriter csvFileWriter;

  private final String CSV_HEADER = "\"id\",\"text\",\"completed\",\"due\",\"priority\",\"category\"\n";

  /**
   * constructor for class TodoList
   * @param csvFileReader - csvFileReader
   * @param csvFileWriter - csvFileWriter
   * @throws FileNotFoundException - if not such file founded
   */
  public TodoList(CsvFileReader csvFileReader, CsvFileWriter csvFileWriter)
      throws FileNotFoundException {
    this.csvFileReader = csvFileReader;
    this.csvFileWriter = csvFileWriter;
    initializeData(this.csvFileReader.readCsv());
  }

  /**
   * Getter for Size of todolist
   * @return - the size of todoList
   */
  public Integer getTodoSize() {
    return todos.size();
  }

  /**
   * Getter for the list of todos
   * @return - a list of todos
   */
  public List<Todo> getTodos() {
    return todos;
  }

  /**
   * Add new todo item to the todo list and update csv file
   * @param item - new todo item
   */
  public void addNewTodo(Todo item){
    todos.add(item);
    // automatic generate id for todoitem
    String content = item.toString()+"\n";
    this.csvFileWriter.writeTodo(content, true);
  }

  /**
   * Set the completed status of an existing todo and update csv file
   * @param ids - the id of todos
   * @throws InvalidArgumentException - if the provided id not existing
   */
  public void completeTodoIds(List<String> ids) throws InvalidArgumentException {
    for (String id: ids) {
      Integer idx = (Integer.parseInt(id));
      if (idx>this.getTodoSize()) {
        throw new InvalidArgumentException("Error: don't complete an unexisting id.");
      }
      this.todos.get(idx-1).setCompleted();
    }
    StringBuilder str = new StringBuilder();
    str.append(CSV_HEADER);
    for (int i=0; i<this.todos.size();i++){
      str.append(this.todos.get(i).toString());
      str.append("\n");
    }
    String content = str.toString();
    this.csvFileWriter.writeTodo(content, false);
  }

  /**
   * Load data from csv file into todo list
   * @param lines - a list of todos
   */
  private void initializeData(List<String> lines) {
    //to skip the header, we need to iterate from i=1
    for(int i=1; i< lines.size();i++){
      String line = lines.get(i);
      String[] list = line.split("\"*,*\"");
      // header: "id","text","completed","due","priority","category"
      Integer id = Integer.parseInt(list[0]);
      //if dueDate is "?" it is null, else it is list[3], according the header sequence, and so on
      String toText = list[1];
      Boolean completed = Boolean.parseBoolean(list[2]);
      String dueDate = list[3].equals("?")?null:list[3];
      String priorityNum = list[4].equals("?")?null:list[4];
      String category = list[5].equals("?")?null:list[5];
      Todo todoItem = new Todo(id, toText, completed, dueDate, priorityNum, category);
      this.todos.add(todoItem);
    }
  }

}
