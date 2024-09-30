package diaryProject.core;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

/**
 * Diary objects which contains posts. Posts can ble added and deleted.
 */
public class Diary implements Iterable<Post> {

  private List<Post> posts = new ArrayList<Post>();

  /**
   * Returns the posts in the diary.
   *
   * @return a copy of the list of posts in the diary
   */
  public List<Post> getPosts() {
    List<Post> copy = new ArrayList<Post>(posts);
    return copy;
  }

  public Diary getDiary() {
    return this;
  }

  /**
   * Adds a post if there isn´t already a post on that date.
   *
   * @param post with attributes date and entry
   *
   * @throws IllegalArgumentException if the date already is the date of another
   *                                  post
   */
  public void addPost(Post post) {
    if (findPost(post.getDate())) {
      throw new IllegalArgumentException("Couldn´t add post. Post with this date already exists.");
    }
    posts.add(post);
    sortPosts();
  }

  /**
   * Checks if there exists a post on the given date.
   *
   * @param date the date of the post you want to find
   *
   * @return Optional(Post) object which contains the post on the given date if it
   *         exists, is empty if the post doesn't exist
   */
  public boolean findPost(LocalDate date) {
    Optional<Post> opt = Optional.empty();
    for (Post p : posts) {
      if (p.getDate().compareTo(date) == 0) {
        opt = Optional.of(p);
      }
    }
    return !opt.isEmpty();
  }

  /**
   * Deletes post if it is contained in the list of posts.
   *
   * @param post the post to delete
   */
  public void deletePost(Post post) {
    if (posts.contains(post)) {
      posts.remove(post);
    }
  }

  /**
   * Delete post on given date.
   *
   * @param date of post to delete
   * @return true if deleted, false if not
   */
  public boolean deletePostOnDate(LocalDate date) {
    Post post = null; 
    for (Post p : posts) {
      if (date.compareTo(p.getDate()) == 0) {
        post = p;
      }
    } 
    if (post != null) {
      posts.remove(post);
      return true;
    }
    return false;
  }
  
  /**
   * Gets post on given date.
   *
   * @param date of post to return
   * @return post on given date or null if there is no post on this date
   */
  public Post getPost(LocalDate date) {
    Post post = null; 
    for (Post p : posts) {
      if (date.compareTo(p.getDate()) == 0) {
        post = p;
      }
    } 
    if (post != null) {
      return post;
    } else {
      return null;
    }
  }
  
  /**
   * Returns a list of posts containing a given keyword.
   *
   * @param keyword the word or date to search for
   *
   * @return a list of the posts which contains the keyword in it
   */
  public List<Post> searchPost(String keyword) {
    List<Post> temporaryPosts = new ArrayList<Post>();
    keyword = keyword.toLowerCase();
    for (Post post : posts) {
      if (post.getEntry().toLowerCase().contains(keyword) 
          || post.getDate().toString().contains(keyword)) {
        temporaryPosts.add(post);
      }
    }
    return temporaryPosts;
  }

  /**
   * Makes a diary object iterable.
   *
   * @return an iterator for the list of posts
   */
  @Override
  public Iterator<Post> iterator() {
    return posts.iterator();
  }

  /**
   * Sorts the posts from the oldest to the newest.
   */
  private void sortPosts() {
    posts.sort((x, y) -> y.getDate().compareTo(x.getDate()));
  }

}