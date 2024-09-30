package diaryProject.core;

import java.time.LocalDate;
import java.util.Iterator;
import java.util.NoSuchElementException;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DiaryTest {
    
  private static Diary diaryEmpty;
  private Diary diaryWithPost;
  private static Post post1;
  private static Post post2;
  private Post post3;

  @BeforeAll
  public static void setUpStatic(){
    post1 = new Post("Bra!", LocalDate.parse("2021-09-24"));    
    post2 = new Post("Dårlig", LocalDate.parse("2020-12-12"));
    diaryEmpty = new Diary();
  }

  @BeforeEach
  public void setUp() {
    diaryWithPost = new Diary();
    diaryWithPost.addPost(post1);
  }

  @Test
  public void testGetPosts_noPosts(){
    assertTrue(diaryEmpty.getPosts().isEmpty());
  }

  @Test
  public void testGetPosts_withPosts(){
    assertFalse(diaryWithPost.getPosts().isEmpty());
    diaryWithPost.addPost(post2);
    assertEquals(2, diaryWithPost.getPosts().size()); 
  }

  @Test
  public void testAddingPost_validPost(){
    assertEquals(1, diaryWithPost.getPosts().size());
    diaryWithPost.addPost(post2);
    assertTrue(diaryWithPost.getPosts().contains(post2));
    assertEquals(2, diaryWithPost.getPosts().size());
  }

  @Test
  public void testAddingPost_unvalidFutureDate() {
    assertThrows(IllegalArgumentException.class, () -> {diaryEmpty.addPost(new Post("God", LocalDate.now().plusDays(3)));});
}

  @Test
  public void testAddingPost_undvalidEmptyEntry() {
    assertThrows(IllegalArgumentException.class, () -> {diaryEmpty.addPost(new Post("", LocalDate.parse("2020-12-12")));});
  }

  @Test 
  public void testAddingPost_existingPost() {
    assertThrows(IllegalArgumentException.class, () -> diaryWithPost.addPost(post1));
  }

  @Test 
  public void testAddingPost_existingDate() {
    assertThrows(IllegalArgumentException.class, () -> {diaryWithPost.addPost(new Post("Bedre", LocalDate.parse("2021-09-24")));});    
  }

  @Test 
  public void testFindPost_existingPost() {
    assertTrue(diaryWithPost.findPost(post1.getDate()));
  }

  @Test
  public void testFindPost_nonexistingPost() {
    assertFalse(diaryWithPost.findPost(post2.getDate()));
  }

  @Test
  public void testDeletePost_existingPost() {
    assertTrue(diaryWithPost.getPosts().contains(post1));
    diaryWithPost.deletePost(post1);
    assertTrue(diaryWithPost.getPosts().isEmpty());
  }

  @Test
  public void testDeletePost_nonexistingPost() {
    assertFalse(diaryWithPost.getPosts().contains(post2));
    assertEquals(1, diaryWithPost.getPosts().size());
    diaryWithPost.deletePost(post2);
    assertEquals(1, diaryWithPost.getPosts().size());
  }

  @Test
  public void testIterator() {
    assertFalse(diaryEmpty.iterator().hasNext());
    assertThrows(NoSuchElementException.class, () -> {diaryEmpty.iterator().next();});
    Iterator<Post> it = diaryWithPost.iterator();
    assertTrue(it.hasNext());
    assertTrue(it.hasNext() && it.next() == post1);
    assertFalse(it.hasNext());
    diaryWithPost.addPost(post2);
    post3 = new Post("Gøy", LocalDate.parse("1999-01-01"));
    diaryWithPost.addPost(post3);
    it = diaryWithPost.iterator();
    assertTrue(it.hasNext() && it.next() == post1);
    assertTrue(it.hasNext() && it.next() == post2);
    assertTrue(it.hasNext() && it.next() == post3);
    assertFalse(it.hasNext());
    diaryWithPost.deletePost(post2);
    it = diaryWithPost.iterator();
    assertTrue(it.hasNext() && it.next() == post1);
    assertTrue(it.hasNext() && it.next() == post3);
    assertFalse(it.hasNext());
  }

    @Test
  public void testSearching_noMatch() {
    assertTrue(diaryWithPost.searchPost("Dårlig").isEmpty());
    assertTrue(diaryWithPost.searchPost("Bra!!").isEmpty());
    assertTrue(diaryWithPost.searchPost("2020-10").isEmpty());
    assertTrue(diaryWithPost.searchPost("2020-10-10").isEmpty());
    assertTrue(diaryWithPost.searchPost("2020-12-10").isEmpty());
  }

  @Test
  public void testSearching_entry() {
    assertTrue(diaryWithPost.searchPost("Br").get(0).getDate().compareTo(post1.getDate()) == 0 && diaryWithPost.searchPost("Br").get(0).getEntry().equals(post1.getEntry()) && diaryWithPost.searchPost("Br").size() == 1);
    assertTrue(diaryWithPost.searchPost("Bra!").contains(post1) && diaryWithPost.searchPost("Bra!").size() == 1);
    diaryWithPost.addPost(post2);
    assertTrue(diaryWithPost.searchPost("r").contains(post1) && diaryWithPost.searchPost("r").contains(post2) && diaryWithPost.searchPost("r").size() == 2);
  }

  @Test
  public void testSearching_date() {
    assertTrue(diaryWithPost.searchPost("2021-09-24").get(0).getDate().compareTo(post1.getDate()) == 0 && diaryWithPost.searchPost("2021-09-24").get(0).getEntry().equals(post1.getEntry()) && diaryWithPost.searchPost("2021-09-24").size() == 1);
    assertTrue(diaryWithPost.searchPost("2021-09-").get(0).getDate().compareTo(post1.getDate()) == 0 && diaryWithPost.searchPost("2021-09-").get(0).getEntry().equals(post1.getEntry()) && diaryWithPost.searchPost("2021-09-").size() == 1);
    diaryWithPost.addPost(post2);
    assertTrue(diaryWithPost.searchPost("20").get(0).getDate().compareTo(post1.getDate()) == 0 && diaryWithPost.searchPost("20").get(0).getEntry().equals(post1.getEntry()) && diaryWithPost.searchPost("20").get(1).getDate().compareTo(post2.getDate()) == 0 && diaryWithPost.searchPost("20").get(1).getEntry().equals(post2.getEntry()) && diaryWithPost.searchPost("20").size() == 2);
  }

  @Test
  public void testSearching_caseIsEquivalent() {
    diaryWithPost.addPost(post2);
    assertEquals(diaryWithPost.searchPost("r"), diaryWithPost.searchPost("R"));
  }

  @Test
  public void testSorting() {
    Post first = new Post("1", LocalDate.parse("2000-01-01"));
    Post second = new Post("2", LocalDate.parse("1500-01-01"));
    Post third = new Post("3", LocalDate.parse("1000-01-01"));
    Diary diary = new Diary();
    diary.addPost(third);
    assertTrue(diary.getPosts().get(0).getDate().compareTo(third.getDate()) == 0 && 
               diary.getPosts().get(0).getEntry().equals(third.getEntry()));
    diary.addPost(second);
    assertTrue(diary.getPosts().get(0).getDate().compareTo(second.getDate()) == 0 && 
               diary.getPosts().get(0).getEntry().equals(second.getEntry()) && 
               diary.getPosts().get(1).getDate().compareTo(third.getDate()) == 0 &&  
               diary.getPosts().get(1).getEntry().equals(third.getEntry()));
    diary.addPost(first);
    assertTrue(diary.getPosts().get(0).getDate().compareTo(first.getDate()) == 0 &&  
               diary.getPosts().get(0).getEntry().equals(first.getEntry()) && 
               diary.getPosts().get(1).getDate().compareTo(second.getDate()) == 0 &&  
               diary.getPosts().get(1).getEntry().equals(second.getEntry()) && 
               diary.getPosts().get(2).getDate().compareTo(third.getDate()) == 0 && 
               diary.getPosts().get(2).getEntry().equals(third.getEntry()));
    diary.deletePost(second);
    assertTrue(diary.getPosts().get(0).getDate().compareTo(first.getDate()) == 0 &&  
               diary.getPosts().get(0).getEntry().equals(first.getEntry()) && 
               diary.getPosts().get(1).getDate().compareTo(third.getDate()) == 0 && 
               diary.getPosts().get(1).getEntry().equals(third.getEntry()));
  }
    
}
