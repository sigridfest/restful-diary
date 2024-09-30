package diaryProject.rest;

import diaryProject.core.Diary;
import diaryProject.core.Post;
import diaryProject.json.DiaryFileHandler;
import java.io.IOException;
import java.time.LocalDate;
import org.springframework.stereotype.Service;

/**
 * Methods for modifying diary.
 */
@Service
public class DiaryService {

  private static final String PATH = "server.json";
  private final DiaryFileHandler diaryFileHandler;
  private Diary diary;

  /**
   * Constructor. Initializes an empty diary if one is not read from file.
   */
  public DiaryService() {
    diaryFileHandler = new DiaryFileHandler(PATH);
    diary = diaryFileHandler.readDiaryFromFile();
    if (diary == null) {
      this.diary = new Diary();
    }
  }

  /**
   * Getter for diary.
   *
   * @return diary
   */
  protected Diary getDiary() {
    return diary;
  }

  /**
   * Getter for post on wanted date.
   *
   * @param date of post to get
   * @return post on gived date
   */
  protected Post getPost(LocalDate date) {
    return diary.getPost(date);
  } 

  /**
   * Adds wanted post to diary.
   *
   * @param post wished to add
   * @return true
   */
  protected boolean addPost(Post post) {
    try {
      diary.addPost(post);
      diaryFileHandler.writeDiaryToFile(diary);
      return true;
    } catch (IllegalArgumentException e) {
      return false;
    }
  }

  /**
   * Deletes post on wanted date from diary.
   *
   * @param date for post wished to delete
   * @return true
   */
  protected boolean deletePost(LocalDate date) {
    diary.deletePostOnDate(date); 
    diaryFileHandler.writeDiaryToFile(diary);
    return true;
  }

  /**
   * Saves diary.
   */
  public void autoSaveDiary() {
    if (diaryFileHandler != null) {
      try {
        diaryFileHandler.saveDiary(this.diary);
      } catch (IllegalStateException | IOException e) {
        System.err.println("Couldn't auto-save: " + e);
      }
    }
  }
}
