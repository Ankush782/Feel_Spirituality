package Model;

import java.util.ArrayList;
import java.util.HashMap;

public class saved {
    String id,name,hindi,english,tamil,telugu,meaning;
    HashMap<String,String > data=new HashMap<>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHindi() {
        return hindi;
    }

    public void setHindi(String hindi) {
        this.hindi = hindi;
        data.put("Hindi",hindi);
    }

    public String getEnglish() {
        return english;

    }

    public void setEnglish(String english) {
        this.english = english;
        data.put("English",english);
    }

    public String getTamil() {
        return tamil;
    }

    public void setTamil(String tamil) {
        this.tamil = tamil;
        data.put("Tamil",tamil);
    }

    public String getTelugu() {
        return telugu;
    }

    public void setTelugu(String telugu) {
        this.telugu = telugu;
        data.put("Telugu",telugu);
    }

    public String getMeaning() {
        return meaning;
    }

    public void setMeaning(String meaning) {
        this.meaning = meaning;
    }
    public String get(String language)
    {
        return data.get(language);
    }
}
