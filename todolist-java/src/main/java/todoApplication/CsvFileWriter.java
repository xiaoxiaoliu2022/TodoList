package todoApplication;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Write the output files
 */
public class CsvFileWriter {
  private String csvFilePath;

  /**
   * constructore for the class CsvFileWriter
   * @param csvFilePath - output file path
   * @throws IOException - if an I/O exception has occurred
   */
  public CsvFileWriter(String csvFilePath) throws IOException {
    this.csvFilePath = csvFilePath;
  }

  /**
   * Getter for the csv file path
   * @return - the path of output csv file
   */
  public String getCsvFilePath() {
    return csvFilePath;
  }

  /**
   * write new todo Item to the output csv file
   * @param content - the description of todo
   * @param append - weather append or not
   */
  public void writeTodo(String content, Boolean append) {
    try{
      File file = new File(csvFilePath);
      //will create a new file if file does not exist
      if(!file.exists()){
        file.createNewFile();
      }
      //write content to file
      FileWriter fw = new FileWriter(file,append);
      BufferedWriter bw = new BufferedWriter(fw);
      bw.write(content);
      bw.close();
    } catch (IOException ioe) {
      System.out.println("Something went wrong! : " + ioe.getMessage());
      ioe.printStackTrace();
    }
  }
}
