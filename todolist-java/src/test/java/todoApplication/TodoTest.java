package todoApplication;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TodoTest {
  private Todo todo1,todo2;

  @BeforeEach
  void setUp() {
    todo1 = new Todo(1, "assignment9",false,"04/25/2022","1","school");
    todo2 = new Todo(2, "lab1", false, null, null, null);
  }

  @Test
  void getId() {
    Assertions.assertEquals(1, todo1.getId());
  }

  @Test
  void getTodoText() {
    Assertions.assertEquals("assignment9", todo1.getTodoText());
  }

  @Test
  void getCompleted() {
    Assertions.assertEquals(false, todo1.getCompleted());
  }

  @Test
  void setCompleted() {
    todo1.setCompleted();
    Assertions.assertTrue(todo1.getCompleted());
  }

  @Test
  void getDueDate() {
    Assertions.assertEquals("04/25/2022", todo1.getDueDate());
  }

  @Test
  void getPriorityNum() {
    Assertions.assertEquals("1", todo1.getPriorityNum());
  }

  @Test
  void getCategory() {
    Assertions.assertEquals("school", todo1.getCategory());
  }

  @Test
  void testToString() {
    Assertions.assertEquals("1,\"assignment9\",\"false\",\"04/25/2022\",\"1\",\"school\"", todo1.toString());
    Assertions.assertEquals("2,\"lab1\",\"false\",\"?\",\"?\",\"?\"", todo2.toString());
  }

}