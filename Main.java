
import java.io.*;
import java.util.Arrays;
import java.util.ArrayList;

public class Main {

  public static void main(String[] fileNames) {
    try {
      System.setErr(new PrintStream(new File("logs.log")));
    } catch (Exception e) {
      System.out.println(e);
      e.printStackTrace();
    }

    for (int i = 0; fileNames.length > i; i++) {
      System.out.println("\nCollection of " + fileNames[i] + " file:\n");
      ResearchArticlesCollection collection = new ResearchArticlesCollection(fileNames[i]);

      System.out.println(collection);
      collection.sort();
      collection.writeToJsonFile();
    }
  }
}
