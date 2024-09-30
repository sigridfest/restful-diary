package diaryProject.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TextNode;
import diaryProject.core.Post;
import java.io.IOException;
import java.time.LocalDate;

/**
 * Converts json-string into a Post-object.
 */
public class PostDeserializer extends JsonDeserializer<Post> {
    
  @Override
  public Post deserialize(JsonParser parser, DeserializationContext ctxt) 
      throws IOException, JsonProcessingException {
    TreeNode treeNode = parser.getCodec().readTree(parser);
    return deserialize((JsonNode) treeNode);
  } 

  /**
   * Converts json-string into a Post-object.
   *
   * @param jsonNode
   *
   * @return post extracted from json-format
   */
  public Post deserialize(JsonNode jsonNode) {
    if (jsonNode instanceof ObjectNode objectNode) {
      Post post = new Post();
      JsonNode entryNode = objectNode.get("entry");
      if (entryNode instanceof TextNode) {
        post.setEntry((entryNode).asText());
      }
      JsonNode dateNode = objectNode.get("date");
      if (dateNode instanceof TextNode) {
        String date = (dateNode).asText();
        LocalDate localDate = LocalDate.parse(date);
        post.setDate(localDate);
      }
      return post;
    }
    return null;
  }

}
