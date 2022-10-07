package todoApplication;

import java.util.List;

/**
 * TodoListController class with fields todolist model and TodoListviewer.
 */
public class TodoListController {

  private TodoList model;
  private TodoListViewer viewer;

  /**
   * Costructor for TodoListController
   * @param model - a list of todos, encoded as TodoList
   * @param viewer - a list of todos displaying, encoded as TodoListViewer
   */
  public TodoListController(TodoList model, TodoListViewer viewer) {
    this.model = model;
    this.viewer = viewer;
  }

  /**
   * Add a new todos
   * @param todo - todo item
   */
  public void addNewTodo(Todo todo) {
    model.addNewTodo(todo);
  }

  /**
   * Getter for the size of todos
   * @return - size number
   */
  public Integer getTodoSize() {
    return model.getTodoSize();
  }

  /**
   * Getter for the list of todos
   * @return - a list of todos
   */
  public List<Todo> getTodos() {
    return model.getTodos();
  }

  /**
   * Set completed todo ids
   * @param ids - the ids of todos need to be set to true
   * @throws InvalidArgumentException -if given invalid argument
   */
  public void completeTodoIds(List<String> ids) throws InvalidArgumentException {
    model.completeTodoIds(ids);
  }

  /**
   * Update todos displaying
   * @param showIncomplete - if only show incompleted todos
   * @param showCategory - certain category requested to be shown
   * @param sortByDate -if sorted by date
   * @param sortByPriority -if sorted by priority
   * @throws InvalidArgumentException -if invalid arguments given
   */
  public void updateView(Boolean showIncomplete, String showCategory, Boolean sortByDate, Boolean sortByPriority)
      throws InvalidArgumentException {
    viewer.display(
        model.getTodos(),
        showIncomplete,
        showCategory,
        sortByDate,
        sortByPriority
    );
  }
}
