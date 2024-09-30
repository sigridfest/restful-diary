package diaryProject.json;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.module.SimpleModule;
import diaryProject.core.Diary;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
 
/**
 * Handles reading from and writing to file.
 */
public class DiaryFileHandler {

  CustomObjectMapper mapper = new CustomObjectMapper();
  Path filePath;
  Diary diary;
  Path saveFilePath = Paths.get(System.getProperty("user.home"), "server.json");

  /**
   * Contrutor.
   *
   * @param fileName builds path
   */
  public DiaryFileHandler(String fileName) {
    filePath = Paths.get(System.getProperty("user.home"), fileName);
    mapper = new CustomObjectMapper();
    mapper.registerModule(new DomainModule());
  }

  /**
   * Help method for saveDiary(). Writes diary to file.
   */
  public void writeDiaryToFile(Diary diary, Writer writer) throws IOException {
    mapper.writerWithDefaultPrettyPrinter().writeValue(writer, diary);
  }

  /** 
   * Saves given diary in local file.
   *
   * @param diary diary to write to file.
   */
  public void writeDiaryToFile(Diary diary) {
    try (Writer writer = new FileWriter(filePath.toFile(), StandardCharsets.UTF_8)) {
      mapper.writerWithDefaultPrettyPrinter().writeValue(writer, diary);
    } catch (IOException e) {
      System.err.println("Something went wrong when writing to file.");
      e.getCause().printStackTrace();
    }
  }

  /**
   * Help method for loadDiary(). Reads diary from file.
   */
  public Diary readDiaryFromFile(Reader reader) throws IOException {
    return mapper.readValue(reader, Diary.class);
  }

  /**
   * Reads diary from local file.
   *
   * @return diary read from file
   * @throws IOException if mapper can't read diary
   * @throws JsonMappingException when problems occure with mapping of content
   * @throws JsonParseException when object doesn't match JSON syntax
   */
  public Diary readDiaryFromFile() {
    try (Reader reader = new FileReader(filePath.toFile(), StandardCharsets.UTF_8)) {
      return mapper.readValue(reader, Diary.class);
    } catch (IOException e) {
      e.printStackTrace();
      System.out.printf("Couldn't find file, making new Diary");
      Diary diary = new Diary();
      writeDiaryToFile(diary);
      return diary;
    }
  }

  /**
   * Loads diary from file. Used in rest module. 
   *
   * @return Diary loaded from file
   * @throws IOException if mapper can't read from file
   * @throws IllegalStateException if file path isn't set
   */
  public Diary loadDiary() throws IOException, IllegalStateException {
    if (saveFilePath == null) {
      throw new IllegalStateException("Save file path is not set, yet");
    }
    try (Reader reader = new FileReader(saveFilePath.toFile(), StandardCharsets.UTF_8)) {
      return readDiaryFromFile(reader);
    }
  }
  
  /**
   * Saves diary. Used in rest module.
   *
   * @param diary to save
   * @throws IOException if mapper can't write to file
   * @throws IllegalStateException if the file path isn't set
   */
  public void saveDiary(Diary diary) throws IOException, IllegalStateException {
    if (saveFilePath == null) {
      throw new IllegalStateException("Save file path is not set, yet");
    }
    try (Writer writer = new FileWriter(saveFilePath.toFile(), StandardCharsets.UTF_8)) {
      writeDiaryToFile(diary, writer);
    }
  }

  public static SimpleModule createJacksonModule() {
    return new DomainModule();
  }    

  public Path getPath() {
    return filePath;
  }

}
