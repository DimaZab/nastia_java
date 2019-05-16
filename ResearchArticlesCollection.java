
import java.io.*;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Collections;

// use `javac -cp '.:json-20131018.jar' ResearchArticlesCollection.java` to import json helpers from .jar file
import org.json.JSONArray;
import org.json.JSONObject;

public class ResearchArticlesCollection {
  String fileName;
  String[] headers;
  ArrayList<IdentificatedResearchArticle> rows;

  public ResearchArticlesCollection() {
    this.headers = new String[0];
    this.rows = new ArrayList<IdentificatedResearchArticle>();
  }

  public ResearchArticlesCollection(String fileName) {
    this();

    this.fileName = fileName;

    try {
      FileReader reader = new FileReader(fileName);
      Scanner scan = new Scanner(reader);

      if (scan.hasNextLine()) {
        setHeaders(scan.nextLine().split(","));
      }

      while (scan.hasNextLine()) {
        addRow(scan.nextLine());
      }

      reader.close();
    } catch (Exception e) {
      System.out.println(e);
      e.printStackTrace();
    }
  }

  public String getFileBaseName() {
    return this.fileName.split("\\.")[0];
  }

  public void setHeaders(String[] headers) {
    this.headers = headers;
  }

  public void addRow(String row) {
    IdentificatedResearchArticle article = new IdentificatedResearchArticle(row);
    if (article.isValid()) {
      this.rows.add(article);
    }
  }

  public String toString() {
    String string = new String();

    string += makeHeaderString("id");

    for (int i = 0; i < headers.length; i++) {
      string += makeHeaderString(headers[i]);
    }

    string += "|\n";
    string += makeHorizontalLine();

    for (int i = 0; i < rows.size(); i++) {
      string += rows.get(i).toString();
    }

    return string;
  }

  public void sort() {
    Collections.sort(this.rows);
  }

  public void writeToJsonFile() {
    JSONArray articleList = buildJsonArray();

    try (FileWriter file = new FileWriter(getFileBaseName() + ".json")) {
      file.write(articleList.toString());
      file.flush();
    } catch (Exception e) {
      System.out.println(e);
      e.printStackTrace();
    }
  }

  protected JSONArray buildJsonArray() {
    JSONArray articleList = new JSONArray();

    for (int i = 0; this.rows.size() > i; i++) {
      JSONObject article = buildArticleJsonObject(rows.get(i));
      articleList.put(article);
    }

    return articleList;
  }

  protected JSONObject buildArticleJsonObject(IdentificatedResearchArticle article) {
    JSONObject articleJsonObject = new JSONObject();
    String[] values = article.getValues();

    articleJsonObject.put("id", article.getId());

    for (int i = 0; this.headers.length > i; i++) {
      articleJsonObject.put(this.headers[i], values[i]);
    }

    return articleJsonObject;
  }

  protected String makeHeaderString(String header) {
    int emptyStrSize = 20 - header.length();
    String headerString = new String();

    headerString += "| " + header;

    while (emptyStrSize > 0) {
      headerString += " ";
      emptyStrSize --;
    }

    return headerString;
  }

  protected String makeHorizontalLine() {
    String line = new String();
    int totalRowLength = (headers.length + 1) * 22 + 1;

    while (totalRowLength > 0) {
      line += "—";
      totalRowLength--;
    }

    line += "\n";

    return line;
  }
}
