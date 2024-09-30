package diaryProject.rest;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.time.LocalDate;
import diaryProject.core.Post;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
class DiaryServiceTest {

  @SuppressWarnings("SpringJavaAutowiredMembersInspection")
  @Autowired
  private static DiaryService service = new DiaryService();

  /**
   * Tests the getDiary() method in DiaryService.
   */
  @Test
  void getDiary() {
    assertNotNull(service.getDiary());
  }

  /**
   * Tests the addPost() method in DiaryService.
   */
  @Test
  void addPost() {
    Post post = new Post("Var bra!", LocalDate.of(2020, 1, 1));
    assertTrue(service.addPost(post));
  }

/**
 * Tests the deletePost() method in DiaryService.
 */
  @Test
  void deletePost() {
    LocalDate date = LocalDate.of(2020, 1, 1);

    Post post = new Post("Var veldig dårlig dag...", date);
    service.addPost(post);

    assertTrue(service.deletePost(date));
  }

  /**
   * Class for a VisitLogServiceBean used for testing purposes.
   */
  @TestConfiguration
  static class TestContextConfiguration {

    @Bean
    public DiaryService diaryService() {
      return new DiaryService();
    }
  }
}