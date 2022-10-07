package todoApplication;

import java.io.File;
import java.io.IOException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CsvFileWriterTest {
  private CsvFileWriter csvFileWriter;

  @BeforeEach
  void setUp() throws IOException {
    csvFileWriter = new CsvFileWriter("todos.csv");
  }

  @Test
  void getCsvFilePath() throws IOException {
    Assertions.assertEquals("todos.csv", csvFileWriter.getCsvFilePath());
  }

  //for this test, we can only run ONCE! Because the "new_file.csv" does not exist at first, once it runs successfully,  "new_file.csv" is generated,
  //the Assertions.assertFalse(file.exists()); will always be false.
  @Test
  void writeTodo() throws IOException {
    File file = new File("new_file.csv");
    Todo todoItem = new Todo(1, "assignment9",true,"04/25/2022","1","school");
//    Assertions.assertFalse(file.exists());
    csvFileWriter = new CsvFileWriter("new_file.csv");
    csvFileWriter.writeTodo(todoItem.toString(), true);
    Assertions.assertTrue(file.exists());
  }
}