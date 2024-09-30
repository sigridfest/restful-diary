package diaryProject.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import diaryProject.core.Diary;
import diaryProject.core.Post;
import java.io.IOException;

/**
 * Converts json-string into a Diary-object.
 */
public class DiaryDeserializer extends JsonDeserializer<Diary> {

  private PostDeserializer postDeserializer = new PostDeserializer();
  
  @Override
  public Diary deserialize(JsonParser parser, DeserializationContext ctxt) 
      throws IOException, JsonProcessingException {
    TreeNode treeNode = parser.getCodec().readTree(parser);
    return deserialize((JsonNode) treeNode);
  } 

  /**
   * Converts json-string into a Diary-object.
   *
   * @param treeNode
   *
   * @return Diary extracted from json-format
   */
  public Diary deserialize(JsonNode treeNode) {
    if (treeNode instanceof ObjectNode) {
      ObjectNode objectNode = (ObjectNode) treeNode;
      Diary diary = new Diary();
      JsonNode postsNode = objectNode.get("posts");
      if (postsNode instanceof ArrayNode) {
        for (JsonNode elementNode : postsNode) {
          Post post = postDeserializer.deserialize(elementNode);
          if (post != null) {
            diary.addPost(post);
          }
        }
      }
      return diary;
    }
    return null;
  }
  
}
