package diaryProject.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import diaryProject.core.Post;
import java.io.IOException;

/**
 * Converts Post-object into json-format.
 */
public class PostSerializer extends JsonSerializer<Post> {

  /*
  format: { "entry": "..." "date": "..." }
  */

  @Override
  public void serialize(Post post, JsonGenerator gen, SerializerProvider serializerProvider) 
      throws IOException {
    gen.writeStartObject();
    gen.writeStringField("entry", post.getEntry());
    gen.writeStringField("date", post.getDate().toString());
    gen.writeEndObject();
  }
}
