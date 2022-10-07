package todoApplication;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Class TodoListViewer supports todos displaying
 */
public class TodoListViewer {

  /**
   * Display the customized list of todos
   * @param todos - todos list
   * @param showIncomplete - if only show incompleted todos
   * @param showCategory - category to be shown
   * @param sortByDate - if sorted by date
   * @param sortByPriority - if sorted by priority
   * @throws InvalidArgumentException - if invalid arguments given
   */
  public void display(List<Todo> todos, Boolean showIncomplete ,String showCategory,
    Boolean sortByDate, Boolean sortByPriority) throws InvalidArgumentException {

    if (showIncomplete) {
      todos = todos.stream()
          .filter(todo-> todo.getCompleted() == false)
          .collect(Collectors.toList());
    }

    if (showCategory != null) {
      todos = todos.stream()
          .filter(todo -> todo.getCategory()!=null && todo.getCategory().equals(showCategory.toLowerCase()))
          .collect(Collectors.toList());
    }

    if (todos.isEmpty()) {
      throw new InvalidArgumentException("Error: no todos based on your filter.");
    }

    if (sortByDate && sortByPriority) {
      // should not happen as we have checked in commandline parser
      throw new InvalidArgumentException("Error: can not do sortByPriority and sortByDate at the same time");
    } else if (sortByDate) {
      //dueDate provided with "?" will be collected to a lise and added to the sorted list
      List<Todo> invalidDateTodos = todos.stream()
          .filter(todo->todo.getDueDate()==null)
          .collect(Collectors.toList());
      todos = todos.stream()
          .filter(todo->todo.getDueDate()!=null)
          .collect(Collectors.toList());
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
      todos.sort(Comparator.comparing(todo -> LocalDate.parse(todo.getDueDate(), formatter)));
      todos.addAll(invalidDateTodos);

    } else if (sortByPriority) {
      //PriorityNum provided with "?" will be collected to a list and added to the sorted list
      List<Todo> invalidPriorityTodos = todos.stream()
          .filter(todo->todo.getPriorityNum()==null)
          .collect(Collectors.toList());
      todos = todos.stream()
          .filter(todo->todo.getPriorityNum()!=null)
          .collect(Collectors.toList());
      todos.sort(Comparator.comparing(todo -> todo.getPriorityNum()));
      todos.addAll(invalidPriorityTodos);
    }

    for(int i=0;i<todos.size();i++) {
      System.out.println(todos.get(i).toString());
    }
  }

}
