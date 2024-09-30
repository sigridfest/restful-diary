package diaryProject.rest;

import diaryProject.core.Diary;
import diaryProject.core.Post;
import java.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller connected to REST-API.
 */
@RestController
@RequestMapping(DiaryController.CONTROLLER_PATH)
public class DiaryController {

  public static final String CONTROLLER_PATH = "/diary";
  private final DiaryService diaryService;

  @Autowired
  public DiaryController(final DiaryService diaryService) {
    this.diaryService = diaryService;
  }

  /**
   * Gets diary from server.
   *
   * @return diary
   */
  @GetMapping
  protected Diary getDiary() {
    return diaryService.getDiary();
  }

  /**
   * Gets post on wanted date.
   *
   * @param date - LocalDate of wanted post
   * @return post
   */
  @GetMapping(path = "/{date}")
  protected Post getPost(@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date) {
    return diaryService.getPost(date);
  } 

  /**
   * Adds a post to the server diary.
   *
   * @param post wished to be added
   * @return true
   */
  @PostMapping
  protected boolean addPost(@RequestBody Post post) {
    diaryService.addPost(post);
    autoSaveDiary();
    return true;
  }

  /**
   * Deletes post on wanted date from server diary.
   *
   * @param date of post wished to be deleted
   * @return true
   */
  @DeleteMapping(path = "/{date}")
  protected boolean deletePost(
      @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date) {
    diaryService.deletePost(date);
    autoSaveDiary();
    return true;
  }

  /**
   * Saves diary to server.
   */
  private void autoSaveDiary() {
    diaryService.autoSaveDiary();
  }

}
