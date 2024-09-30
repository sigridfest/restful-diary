package diaryProject.ui;

import com.fasterxml.jackson.databind.ObjectMapper;
import diaryProject.core.Diary;
import diaryProject.core.Post;
import diaryProject.json.CustomObjectMapper;
import diaryProject.json.DomainModule;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.time.LocalDate;

/**
 * Class that allows for server-side persistence.
 **/

public class RemoteDiaryAccess {

  private final URI endpointUri;
  private final ObjectMapper mapper;
  private Diary diary;

  public RemoteDiaryAccess(URI endpointUri) {
    this.endpointUri = endpointUri;
    this.mapper = new CustomObjectMapper().registerModule(new DomainModule());
  }

  /**
   * Sends http get request to remote server and gets the diary.
   *
   * @return the Diary
   */
  public Diary getDiary() {
    if (this.diary == null) {
      try {
        final HttpRequest request = HttpRequest.newBuilder(endpointUri)
            .header("Accept", "application/json").GET().build();
        final HttpResponse<String> response = HttpClient.newBuilder().build()
            .send(request, HttpResponse.BodyHandlers.ofString());
        this.diary = mapper.readValue(response.body(), Diary.class);
      } catch (IOException | InterruptedException e) {
        throw new RuntimeException(e);
      }
    }
    return this.diary;
  }

  /**
   * Sends http get request to remote server and gets the post saved to the date.
   *
   * @param date for wished post
   * @return post from wanted date
   */
  public Post getPost(LocalDate date) {
    try {
      final HttpRequest request = HttpRequest.newBuilder(URI.create(endpointUri + "/"
          + date.toString())).header("Accept", "application/json").GET().build();
      final HttpResponse<String> response = HttpClient.newBuilder()
          .build().send(request, HttpResponse.BodyHandlers.ofString());
      mapper.readValue(response.body(), Post.class);
    } catch (IOException | InterruptedException e) {
      throw new RuntimeException(e);
    }
    return diary.getPost(date);  
  }

  /**
   * Sends http post request to remote server. Adds a post to the diary. 
   *
   * @param post to add
   */
  public boolean addPost(Post post) {
    try {
      String jsonPost = mapper.writeValueAsString(post);
      final HttpRequest request = HttpRequest.newBuilder(endpointUri)
          .header("Accept", "application/json").header("Content-Type", "application/json")
            .POST(BodyPublishers.ofString(jsonPost)).build();
      final HttpResponse<String> response = HttpClient.newBuilder()
          .build().send(request, HttpResponse.BodyHandlers.ofString());
      Boolean successfullyAdded = mapper.readValue(response.body(), Boolean.class);
      if (successfullyAdded != null && successfullyAdded) {
        diary.addPost(post);
        return true;
      }
      return false;
    } catch (IOException | InterruptedException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Sends http delete request to remote server. Deletes post saved on date. 
   *
   * @param date of post wished to delete
   */
  public boolean deletePost(LocalDate date) {
    try {
      final HttpRequest request = HttpRequest.newBuilder(URI.create(endpointUri + "/"
          + date.toString())).header("Accept", "application/json").DELETE().build();
      final HttpResponse<String> response = HttpClient.newBuilder().build()
          .send(request, HttpResponse.BodyHandlers.ofString());
      Boolean successfullyRemoved = mapper.readValue(response.body(), Boolean.class);
      if (successfullyRemoved != null && successfullyRemoved) {
        diary.deletePostOnDate(date);
        return true;
      }
      return false;
    } catch (IOException | InterruptedException e) {
      throw new RuntimeException(e);
    }
  }
}  