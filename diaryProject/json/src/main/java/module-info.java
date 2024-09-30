module diaryProject.json {
  requires transitive diaryProject.core;
  requires transitive com.fasterxml.jackson.databind;
  requires transitive com.fasterxml.jackson.core;
  requires transitive com.fasterxml.jackson.datatype.jsr310;

  exports diaryProject.json;
  
}
