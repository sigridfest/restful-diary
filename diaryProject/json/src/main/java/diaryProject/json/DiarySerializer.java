package diaryProject.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import diaryProject.core.Diary;
import diaryProject.core.Post;
import java.io.IOException;

/**
 * Converts Diary-object into json-format.
 */
public class DiarySerializer extends JsonSerializer<Diary> {

  /*
  * format: { "posts": [...] }
  */

  @Override
  public void serialize(Diary diary, JsonGenerator gen, SerializerProvider serializerProvider) 
      throws IOException {
    gen.writeStartObject();
    gen.writeArrayFieldStart("posts");
    for (Post post : diary) {
      gen.writeObject(post);
    }
    gen.writeEndArray();
    gen.writeEndObject();
  }
}