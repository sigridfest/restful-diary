package diaryProject.rest;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import diaryProject.core.Post;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@ExtendWith(SpringExtension.class)
@WebMvcTest(DiaryController.class)
@ContextConfiguration(classes = { DiaryController.class, DiaryService.class, DiaryApplication.class })
class DiaryControllerTest {

  @Autowired
  private MockMvc mvc;

  @MockBean
  private DiaryService service;

  @Autowired
  private ObjectMapper mapper;

  /**
   * Tests if POST request is successful.
   * @throws exception for MvcRequest
   */
  @Test
  void addPost() throws Exception {
    Post post = new Post("Var bra!", LocalDate.of(2020, 1, 1));

    String json = mapper.writeValueAsString(post);
    MvcResult result = mvc.perform(MockMvcRequestBuilders.post("/diary").contentType(MediaType.APPLICATION_JSON).content(json)).andExpect(status().isOk()).andReturn();
    assertTrue(Boolean.parseBoolean(result.getResponse().getContentAsString()));
  }

  /**
   * Tests is DELETE request is successful
   * @throws exception for MvcRequest
   */
  @Test
  void deletePost() throws Exception {
    LocalDate date = LocalDate.of(2020, 1, 1);
    MvcResult result = mvc.perform(MockMvcRequestBuilders.delete("/diary/" + date.toString())).andExpect(status().isOk()).andReturn();
    assertTrue(Boolean.parseBoolean(result.getResponse().getContentAsString()));
  }
}