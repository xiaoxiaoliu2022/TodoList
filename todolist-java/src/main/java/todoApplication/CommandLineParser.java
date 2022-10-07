package todoApplication;

import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * process and validates command line arguments.
 */
public class CommandLineParser {
  public static final String CSV_FILE = "--csv-file";
  public static final String ADD_TODO = "--add-todo";
  public static final String TODO_TEXT = "--todo-text";
  public static final String COMPLETED = "--completed";
  public static final String DUE = "--due";
  public static final String PRIORITY = "--priority";
  public static final String CATEGORY = "--category";
  public static final String COMPLETE_TODO = "--complete-todo";
  public static final String DISPLAY = "--display";
  public static final String SHOW_INCOMPLETE = "--show-incomplete";
  public static final String SHOW_CATEGORY = "--show-category";
  public static final String SORT_BY_DATE = "--sort-by-date";
  public static final String SORT_BY_PRIORITY = "--sort-by-priority";

  public static final String DATE_PATTEN = "^\\d{2}/\\d{2}/\\d{4}$";
  public static String[] PRIORITIES = {"1", "2", "3"};
  public static final String COMPLETED_TODO_ID = "^\\d+$";

  private String csvFile ;
  private Boolean addToDo = false;
  private String todoText;
  private Boolean completed = false;
  private String dueDate;
  private String priorityNum;
  private String category;
  private List<String> completedTodoIds = new ArrayList<>();
  private Boolean display = false;
  private Boolean showIncomplete = false;
  private String showCategory;
  private Boolean sortByDate = false;
  private Boolean sortByPriority = false;

  /**
   * Constructor for the CommandLineParser
   * @param args- command line arguments
   * @throws InvalidArgumentException - throws error when the argument does not meet the expected
   * requirements
   */
  public CommandLineParser(String[] args) throws InvalidArgumentException{
    this.argsProcessor(args);
  }

  /**
   * Arguments processor method. When the argument is one of the following cases, the process will execute accordingly.
   * @param args - command line arguments
   * @throws InvalidArgumentException -throws error when the argument does not meet the expected
   * requirements
   */
  public void argsProcessor(String[] args) throws InvalidArgumentException{
    for (int i = 0; i < args.length; i++) {
      switch (args[i]) {
        case CSV_FILE:
          this.csvFile = ((i + 1) < args.length) ? args[i + 1] : null;
          break;
        case ADD_TODO:
          this.addToDo = true;
          break;
        case TODO_TEXT:
          this.todoText = ((i + 1) < args.length) ? args[i + 1] : null;
          break;
        case COMPLETED:
          this.completed = true;
          break;
        case DUE:
          this.dueDate = ((i + 1) < args.length) ? args[i + 1] : null;
          break;
        case PRIORITY:
          this.priorityNum = ((i + 1) < args.length) ? args[i + 1] : null;
          break;
        case CATEGORY:
          this.category = ((i + 1) < args.length) ? args[i + 1] : null;
          break;
        //if there are multiple ids, user can keep adding them to the list to request
        case COMPLETE_TODO:
          String completedTodoId = ((i + 1) < args.length) ? args[i + 1] : null;
          this.completedTodoIds.add(completedTodoId);
          break;
        case DISPLAY:
          this.display = true ;
          break;
        case SHOW_INCOMPLETE:
          this.showIncomplete = true;
          break;
        case SHOW_CATEGORY:
          this.showCategory = ((i + 1) < args.length) ? args[i + 1] : null;
          break;
        case SORT_BY_DATE:
          this.sortByDate = true;
          break;
        case SORT_BY_PRIORITY:
          this.sortByPriority = true;
          break;

      }
    }
    // valid csv file must be provided
    if (this.csvFile == null) {
      throw new InvalidArgumentException("Error: csv file not provided.");
    } else {
      // use "get path" to confirm if the string processed is a valid csv file
      Path path = Paths.get(this.csvFile);
      if(!Files.exists(path, LinkOption.NOFOLLOW_LINKS)) {
        throw new InvalidArgumentException("Error: " + this.csvFile + " is not a valid file");
      }
    }

    if (this.addToDo) {
      // todo-text must be provided when --add-todo is provided
      if(this.todoText==null || this.todoText.startsWith("--")) {
        throw new InvalidArgumentException("Error: please type in the todo description.");
      }
      // dueDate must follow certain patten if provided
      if(this.dueDate != null && !this.dueDate.matches(DATE_PATTEN)) {
        throw new InvalidArgumentException("Error: only accept date patten mm/dd/yyyy");
      };
      // priorityNum can only be 1, 2, 3 if provided
      if(this.priorityNum != null && !Arrays.asList(PRIORITIES).contains(this.priorityNum)) {
        throw new InvalidArgumentException("Error: priority number can only be 1, 2, or 3");
      }
    }else {
      // handle error when --add-todo is not provided
      if(this.todoText!=null || this.completed || this.priorityNum!=null || this.dueDate!=null || this.category!=null){
        throw new InvalidArgumentException("Error: --todo-text, --completed, --due, --priority, --category must follow --add-todo");
      }
    }
    // handle error when invalid id provided
    if(!this.completedTodoIds.isEmpty()) {
      for (String id:this.completedTodoIds) {
        if (!id.matches(COMPLETED_TODO_ID)) {
          throw new InvalidArgumentException("Error: only accept integer type ID");
        }
      }
    }
    // handle error when sortByPriority and sortByDate are requested at the same time
    if(this.display) {
      if(this.sortByPriority && this.sortByDate) {
        throw new InvalidArgumentException("Error: can not do sortByPriority and sortByDate at the same time");
      }
    }

  }

  /**
   * Getter for a csv file
   * @return - a csv file with a valid path
   */
  public String getCsvFile() {
    return csvFile;
  }

  /**
   * check if a new to do is added
   * @return - true if a new to do is added, otherwise return false
   */
  public Boolean getAddToDo() {
    return addToDo;
  }

  /**
   * Getter for a due date
   * @return - a due date
   */
  public String getDueDate() {
    return dueDate;
  }

  /**
   * Getter for the to do text
   * @return - the description of todo
   */
  public String getTodoText() {
    return todoText;
  }

  /**
   * Getter for whether completed
   * @return - true if to do is completed status, otherwise return false
   */
  public Boolean getCompleted() {
    return completed;
  }

  /**
   * Getter for the priority number
   * @return - the priority number
   */
  public String getPriorityNum() {
    return priorityNum;
  }

  /**
   * Getter for the category
   * @return - the category of todo
   */
  public String getCategory() {
    return category;
  }

  /**
   * Getter for the completed todo ids
   * @return - a list of ids that marked completed status
   */
  public List<String> getCompletedTodoIds() {
    return completedTodoIds;
  }

  /**
   * Getter for whether display
   * @return - true if display is provided, otherwise return false
   */
  public Boolean getDisplay() {
    return display;
  }

  /**
   * Getter for whether show incompleted only
   * @return - true if --show-incomplete is provided, otherwise return false
   */
  public Boolean getShowIncomplete() {
    return showIncomplete;
  }

  /**
   * Getter for category to be displayed
   * @return - the category to be displayed with the given category, otherwise return null
   */
  public String getShowCategory() {
    return showCategory;
  }

  /**
   * Getter for whether sort by date
   * @return - true if --sort-by-date is provided, otherwise return false
   */
  public Boolean getSortByDate() {
    return sortByDate;
  }

  /**
   * Getter for whether sort by priority
   * @return - true if --sort-by-priority is provided, otherwise return false
   */
  public Boolean getSortByPriority() {
    return sortByPriority;
  }

}
