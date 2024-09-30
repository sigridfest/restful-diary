package diaryProject.rest;

import com.fasterxml.jackson.databind.Module;
import diaryProject.json.DiaryFileHandler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * Starts app.
 */
@SpringBootApplication
public class DiaryApplication {

  /**
   * Creates new module with custom serializers from json-module.
   *
   * @return DomainModule-instance
   */
  @Bean
  public Module objectMapperModule() {
    return DiaryFileHandler.createJacksonModule();
  }

  /**
   * Application start method.
   *
   * @param args - command line arguments
   */
  public static void main(String[] args) {
    SpringApplication.run(DiaryApplication.class, args);
  }
}