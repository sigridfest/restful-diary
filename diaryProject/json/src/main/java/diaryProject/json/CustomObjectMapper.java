package diaryProject.json;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

/**
 * Customized ObjectMapper enables use of customzed (de)serializers.
 */
public class CustomObjectMapper extends ObjectMapper {
  /**
  * Connects CustomObjectMapper to DomainModule.
  */
  public CustomObjectMapper() {
    registerModule(new DomainModule());
    enable(SerializationFeature.INDENT_OUTPUT);
    enable(DeserializationFeature.ACCEPT_EMPTY_ARRAY_AS_NULL_OBJECT);
  }
}
