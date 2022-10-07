package todoApplication;

/**
 * Class Todo with fields of todotext, completed status, due date, priorityNum and category
 */
public class Todo {

  private Integer id;
  private String todoText;
  private Boolean completed = false;
  private String dueDate;
  private String priorityNum;
  private String category;

  /**
   * Constructor for Todo
   * @param id - the id of todos
   * @param todoText - the description of a todo
   * @param completed - whether a todo is completed or not
   * @param dueDate - the due date of a todo
   * @param priorityNum -an integer indicating the priority number of the todo
   * @param category - a user-specified String that can be used to group related todos
   */
  public Todo(Integer id, String todoText, Boolean completed, String dueDate,
      String priorityNum, String category) {
    this.id = id;
    this.todoText = todoText;
    this.completed = completed;
    this.dueDate = dueDate;
    this.priorityNum = priorityNum;
    this.category = category;
  }

  /**
   * Getter for the todo id
   * @return - the id number
   */
  public Integer getId() {
    return id;
  }

  /**
   * Getter for the todo text
   * @return - the description of todo
   */
  public String getTodoText() {
    return todoText;
  }

  /**
   * Getter for if completed
   * @return - whether the todo is completed or incompleted
   */
  public Boolean getCompleted() {
    return completed;
  }

  /**
   * Setter for completed
   */
  public void setCompleted() {
    this.completed = true;
  }

  /**
   * Getter for the due date
   * @return - a due date
   */
  public String getDueDate() {
    return dueDate;
  }

  /**
   * Getter for the priority number
   * @return - an integer indicating the priority number of the todo
   */
  public String getPriorityNum() {
    return priorityNum;
  }

  /**
   * Getter for the category
   * @return - a user-specified String that can be used to group related todos
   */
  public String getCategory() {
    if (category != null) {
      return category.toLowerCase();
    }
    return null;
  }

  /**
   * Returns a string representation of the object
   * @return - a string representation of the object
   */
  @Override
  public String toString() {
    String dueDate = this.dueDate==null?"?":this.dueDate;
    String priorityNum = this.priorityNum==null?"?":this.priorityNum;
    String category = this.category==null?"?":this.category;
    return id + "," +
        "\"" + todoText + "\"," +
        "\"" + completed + "\"," +
        "\"" + dueDate + "\"," +
        "\"" + priorityNum + "\"," +
        "\"" + category + "\"";
  }

}
