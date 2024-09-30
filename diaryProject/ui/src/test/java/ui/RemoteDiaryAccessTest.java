package ui;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import diaryProject.core.Post;
import diaryProject.ui.RemoteDiaryAccess;


public class RemoteDiaryAccessTest {

  private static final String SAMPLE_POST = "{\"posts\": [{" 
  + "\"entry\": \"Dro til Selbu med Mamma!\", " 
  + "\"date\": \"2020-10-14\"" + "}]}";

  private WireMockServer wmServer;

  private RemoteDiaryAccess remoteDiaryAccess;

  @BeforeEach
  public void startWireMockServer() throws URISyntaxException {
    WireMockConfiguration wmConfig = WireMockConfiguration.wireMockConfig().port(6970);
    wmServer = new WireMockServer(wmConfig.portNumber());
    wmServer.start();
    WireMock.configureFor("localhost", wmConfig.portNumber());
    remoteDiaryAccess = new RemoteDiaryAccess(
        new URI("http://localhost:" + wmServer.port() + "/diary"));
  }

  @Test
  public void testGetDiary() {
    stubFor(get(urlEqualTo("/diary"))
        .withHeader("Accept", equalTo("application/json"))
        .willReturn(aResponse()
            .withStatus(200)
            .withHeader("Content-Type", "application/json")
            .withBody(SAMPLE_POST)
        )
    );

    List<Post> posts = remoteDiaryAccess.getDiary().getPosts();

    assertEquals(1, posts.size());
    assertEquals("Dro til Selbu med Mamma!", posts.get(0).getEntry());
    assertEquals(LocalDate.of(2020,10,14), posts.get(0).getDate());
  }
  
  @AfterEach
  public void stopWireMockServer() {
    wmServer.stop();
  }
}
