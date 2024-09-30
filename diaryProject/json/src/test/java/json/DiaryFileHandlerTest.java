package json;

import diaryProject.core.Diary;
import diaryProject.json.DiaryFileHandler;
import diaryProject.core.Post;
import java.nio.file.Files;
import java.io.IOException;
import java.util.Iterator;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class DiaryFileHandlerTest { 

  private static DiaryFileHandler fileHandler;
  private static String fileName;

  @BeforeAll
  public static void setUp() {
    fileName = "diary-" + System.currentTimeMillis() + ".json";
    fileHandler = new DiaryFileHandler(fileName);
  }

  private Diary createDiaryWithTwoPosts() {
    Diary diary = new Diary();
    Post post1 = new Post("Bra", LocalDate.parse("2018-10-07"));
    Post post2 = new Post("Bedre", LocalDate.parse("2018-10-06"));
    diary.addPost(post1);
    diary.addPost(post2);
    return diary;
  }

  private void checkDiariesWithTwoPosts(Diary diary, Diary diary2) {
    Iterator<Post> it = diary.iterator();
    Iterator<Post> it2 = diary2.iterator();
    assertTrue(it.hasNext());
    assertTrue(it2.hasNext());
    Post post = it.next();
    Post post2 = it2.next();
    assertTrue(post.getDate().compareTo(post2.getDate()) == 0);
    assertTrue(post.getEntry().equals(post2.getEntry()));
    assertTrue(it.hasNext());
    assertTrue(it2.hasNext());
    post = it.next();
    post2 = it2.next();
    assertTrue(post.getDate().compareTo(post2.getDate()) == 0);
    assertTrue(post.getEntry().equals(post2.getEntry()));
    assertFalse(it.hasNext());
    assertFalse(it2.hasNext());
  }
  
  @Test
  public void testSerializersdeserializers_savingFile() {
    Diary diary = createDiaryWithTwoPosts();
    try {
      fileHandler.writeDiaryToFile(diary);
      assertTrue(Files.exists(fileHandler.getPath()));
      Diary diary2 = fileHandler.readDiaryFromFile();
      checkDiariesWithTwoPosts(diary, diary2);
    } 
    catch (Exception e) {
      fail(e.getMessage());
    } 
    finally {
      try {
        Files.deleteIfExists(fileHandler.getPath());
      } 
      catch (IOException e) {
      }
    }
  }
}
