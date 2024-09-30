package diaryProject.json;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.module.SimpleModule;
import diaryProject.core.Diary;
import diaryProject.core.Post;


/**
 * Module for handling customized convertion to/from json-format.
 */
public class DomainModule extends SimpleModule {

  private static final String NAME = "DomainModule";

  /**
   * Construtor for SimpleModule-object.
   * Connects objetc to the customized (de)serializers.
   */
  public DomainModule() {
    super(NAME, Version.unknownVersion());
    addSerializer(Post.class, new PostSerializer());
    addSerializer(Diary.class, new DiarySerializer());
    addDeserializer(Post.class, new PostDeserializer());
    addDeserializer(Diary.class, new DiaryDeserializer());
  }
    
}
