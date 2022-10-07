package todoApplication;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CsvFileReaderTest {
  private CsvFileReader csvFileReader;

  @BeforeEach
  void setUp() {
    csvFileReader = new CsvFileReader("todos.csv");
  }

  @Test
  void getCsvFilePath() {
    Assertions.assertTrue(csvFileReader.getCsvFilePath().equals("todos.csv"));
  }

  @Test
  void readCsv() throws IOException {
    BufferedReader reader = new BufferedReader(new FileReader("todos.csv"));
    List<String> exceptedLines = new ArrayList<>();
    String line = "";
    while((line = reader.readLine())!= null) {
      exceptedLines.add(line);
    }
    Assertions.assertTrue(exceptedLines.equals(new CsvFileReader("todos.csv").readCsv()));

    assertThrows(FileNotFoundException.class, () -> {
      (new CsvFileReader("nothing.csv")).readCsv();
    });

  }

  @Test
  void testEquals() {
    Assertions.assertEquals(csvFileReader,csvFileReader);
    Assertions.assertFalse(csvFileReader.equals(null));
    Assertions.assertFalse(csvFileReader.equals(0));
    CsvFileReader csvFileReader1 = new CsvFileReader("todos.csv");
    Assertions.assertTrue(csvFileReader.equals(csvFileReader1));
    CsvFileReader csvFileReader2 = new CsvFileReader("todos2.csv");
    Assertions.assertFalse(csvFileReader.equals(csvFileReader2));
  }

  @Test
  void testHashCode() {
    CsvFileReader csvFileReader1 = new CsvFileReader("todos.csv");
    CsvFileReader csvFileReader2 = new CsvFileReader("todos2.csv");
    Assertions.assertTrue(csvFileReader.hashCode()==csvFileReader1.hashCode());
    Assertions.assertFalse(csvFileReader.hashCode()==csvFileReader2.hashCode());
  }

}