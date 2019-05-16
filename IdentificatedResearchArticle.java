class IdentificatedResearchArticle extends ResearchArticle implements Comparable<IdentificatedResearchArticle> {
  static int identificator = 0;

  int id;

  public IdentificatedResearchArticle(String row) {
    super(row);
    // should we consider invalid rows?
    this.id = ++ this.identificator;
  }

  public String toString() {
    return makeCell(Integer.toString(id)) + super.toString();
  }

  public int compareTo(IdentificatedResearchArticle anotherArticle) {
    return anotherArticle.getParsedDate().compareTo(getParsedDate());
  }

  public int getId() {
    return this.id;
  }
}
