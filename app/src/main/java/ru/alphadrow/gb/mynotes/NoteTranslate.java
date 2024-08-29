package ru.alphadrow.gb.mynotes;

import com.google.firebase.Timestamp;

import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class NoteTranslate {
    public static class Fields{
        public static final String NAME = "name";
        public static final String DESCRIPTION = "description";
        public static final String DATE_OF_CREATION = "dateOfCreation";
        public static final String IMPORTANCE = "importance";


    }

    public static Importance stringToImportance(String string){
        if (string.equals(Importance.HIGH.toString())){
            return Importance.HIGH;
        }
        if (string.equals(Importance.MEDIUM.toString())){
            return Importance.MEDIUM;
        }
        if (string.equals(Importance.LOW.toString())){
            return Importance.LOW;
        }
        if (string.equals(Importance.LIFE_AND_DEATH.toString())){
            return Importance.LIFE_AND_DEATH;
        }
        return Importance.FORGET_ABOUT_IT;
    }
    public static Note documentToNote(String id, Map<String, Object> doc){
        Note result = new Note((String) doc.get(Fields.NAME),
                (String) doc.get(Fields.DESCRIPTION),
                 new Date((Long)doc.get(Fields.DATE_OF_CREATION)),
                stringToImportance(Fields.IMPORTANCE));
        result.setId(id);
        return result;
    }



    public static Map<String, Object> noteToDocument(Note note){
        Map<String, Object> result = new HashMap<>();
        result.put(Fields.NAME, note.getName());
        result.put(Fields.DESCRIPTION, note.getDescription());
        result.put(Fields.IMPORTANCE, note.getImportance().toString());
        result.put(Fields.DATE_OF_CREATION, note.getDateOfCreation().getTime());
        return result;
    }


}
