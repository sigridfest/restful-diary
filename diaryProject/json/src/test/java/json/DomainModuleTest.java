package json;

import com.fasterxml.jackson.core.JsonProcessingException;
import diaryProject.json.CustomObjectMapper;
import diaryProject.core.Diary;
import diaryProject.core.Post;
import java.util.Iterator;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class DomainModuleTest {

  private static CustomObjectMapper mapper;

  @BeforeAll
  public static void setUp() {
    mapper = new CustomObjectMapper();

  }

  final static String diaryWithTwoPosts = """
    {
        "posts": [
          {
            "entry": "Bra",
            "date": "2018-10-07"
          },
          {
            "entry": "Bedre",
            "date": "2018-10-06"
          }
        ]
      }
    """;

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
  public void testSerializers() {
    Diary diary = createDiaryWithTwoPosts();
    try {
      assertEquals(diaryWithTwoPosts.replaceAll("\\s+", ""), mapper.writeValueAsString(diary).replaceAll("\\s+", ""));
    } catch (JsonProcessingException e) {
      fail(e.getMessage());
    }
  }

  @Test
  public void testDeserializers() {
    try {
      Diary diary2 = mapper.readValue(diaryWithTwoPosts, Diary.class);
      Iterator<Post> it = diary2.iterator();
      assertTrue(it.hasNext());
      Post post2 = it.next();
      assertTrue(post2.getEntry().equals("Bra") && post2.getDate().compareTo(LocalDate.parse("2018-10-07")) == 0);
      assertTrue(it.hasNext());
      Post post3 = it.next();
      assertTrue(post3.getEntry().equals("Bedre") && post3.getDate().compareTo(LocalDate.parse("2018-10-06")) == 0);
      assertFalse(it.hasNext());
    } catch (JsonProcessingException e) {
      System.out.println(e.getMessage());
      fail();
    }
  }

  @Test
  public void testSerializersDeserializers() {
    Diary diary = createDiaryWithTwoPosts();
    try {
      String json = mapper.writeValueAsString(diary);
      Diary diary2 = mapper.readValue(json, Diary.class);
      checkDiariesWithTwoPosts(diary, diary2);
    } catch (JsonProcessingException e) {
      System.out.println(e.getMessage());
      fail();
    }
  }

}