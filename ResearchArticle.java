import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Arrays;

public class ResearchArticle {
  String title;
  String author;
  String publicationDate;
  String heading;
  String journalTitle;

  Boolean valid = true;
  String[] values;

  public ResearchArticle() {}

  public ResearchArticle(String row) {
    String[] values = row.split(",");

    this.values = values;

    setTitle(values[0]);
    this.author = values[1];
    setPublicationDate(values[2]);
    this.heading = values[3];
    this.journalTitle = values[4];
  }

  public void setTitle(String title) {
    try {
      if (title == null || title.length() == 0) {
        this.title = "";
        throw new Exception("Title can't be blank.");
      } else {
        this.title = title;
      }
    } catch (Exception e) {
      this.valid = false;
      System.out.println(e);
      e.printStackTrace();
    }
  }

  public Date getParsedDate() {
    Date date = new Date();
    SimpleDateFormat parser = new SimpleDateFormat("dd-MM-yyyy");

    try {
      date = parser.parse(this.publicationDate);
    } catch (Exception e) {
      this.valid = false;
      System.out.println(e);
      e.printStackTrace();
    }

    return date;
  }

  public void setPublicationDate(String publicationDate) {
    this.publicationDate = publicationDate;

    try {
      Date today = new Date();
      Date date = getParsedDate();

      if (date.compareTo(today) > 0) {
        throw new Exception("Publication date can't be in future.");
      }
    } catch (Exception e) {
      this.valid = false;
      System.out.println(e);
      e.printStackTrace();
    }
  }

  public String[] getValues() {
    return this.values;
  }

  public String toString() {
    String string = new String();

    for (int i = 0; values.length > i; i++) {
      string += makeCell(values[i]);
    }

    string += "|\n";

    return string;
  }

  public Boolean isValid() {
    return valid;
  }

  protected String makeCell(String cellData) {
    String cellString = new String();
    int emptyStrSize = 20 - cellData.length();

    cellString += "| " + cellData;

    while (emptyStrSize > 0) {
      cellString += " ";
      emptyStrSize --;
    }

    return cellString;
  }
}
