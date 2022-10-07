package todoApplication;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Class CsvFileReader read and store datas from the csv file
 */
public class CsvFileReader {
  private String csvFilePath;

  /**
   * constructor for the class CsvFileReader
   * @param csvFilePath - the path of csv file
   */
  public CsvFileReader(String csvFilePath) {
    this.csvFilePath = csvFilePath;
  }

  /**
   * Getter for the csv file path
   * @return - csv file path
   */
  public String getCsvFilePath() {
    return csvFilePath;
  }

  /**
   * Read the csv file method
   * @return - a list of string todos from csv file
   * @throws FileNotFoundException - if csv file not found
   */
  public List<String>  readCsv() throws FileNotFoundException {
    List<String> csvFileLines = new ArrayList<>();
    try (BufferedReader inputFile = new BufferedReader(new FileReader(csvFilePath))) {
      String line = "";
      while ((line = inputFile.readLine()) != null) {
        if (line.trim().isEmpty()) {
          continue;
        }
        csvFileLines.add(line);
      }
    } catch (FileNotFoundException fnfe) {
      throw new FileNotFoundException();
    } catch (IOException ioe) {
      System.out.println("Something went wrong! : " + ioe.getMessage());
      ioe.printStackTrace();
    }
    return csvFileLines;
  }

  /**
   * Indicates whether other object is equal to this object
   * @param o - the reference object to compare with
   * @return - true if this object is same as the reference object, otherwise return false
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CsvFileReader that = (CsvFileReader) o;
    return Objects.equals(csvFilePath, that.csvFilePath);
  }

  /**
   * Returns a hash code value for the object
   * @return - a hash code value for this object
   */
  @Override
  public int hashCode() {
    return Objects.hash(csvFilePath);
  }

}
