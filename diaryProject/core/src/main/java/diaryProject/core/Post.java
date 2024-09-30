package diaryProject.core;

import java.time.LocalDate;

/**
 * Post objects has an entry and a date.
 */
public class Post {

  private String entry;
  private LocalDate date;

  /**
   * Contructor. 
   *
   * @param entry string
   *
   * @param date local date
   */
  public Post(String entry, LocalDate date) {
    dateIsValid(date);
    entryIsValid(entry);
    this.entry = entry;
    this.date = date;
  }

  public Post() {
  }

  /**
   * Validates entry.
   *
   * @param entry
   *
   * @throws IllegalArguementException if entry is an empty string
   */
  private void entryIsValid(String entry) {
    if (entry.equals("")) {
      throw new IllegalArgumentException("Entry cannot be empty.");
    }
  }

  /**
   * Validates date.
   *
   * @param date
   *
   * @throws IllegalArgumentException if date is in the future
   */
  private void dateIsValid(LocalDate date) {
    if (date.isAfter(LocalDate.now())) {
      throw new IllegalArgumentException("Invalid date, cannot be in the future");
    }
  }

  public String getEntry() {
    return this.entry;
  }

  public LocalDate getDate() {
    return this.date;
  }

  public void setEntry(String entry) {
    entryIsValid(entry);
    this.entry = entry;
  }

  public void setDate(LocalDate date) {
    dateIsValid(date);
    this.date = date;
  }

  @Override
  public String toString() {
    return date.toString();
  }


}