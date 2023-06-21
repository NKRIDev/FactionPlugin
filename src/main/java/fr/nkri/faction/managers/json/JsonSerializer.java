package fr.nkri.faction.managers.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class JsonSerializer {

    private final Gson gson;

    //Création de l'objet Gson
    public JsonSerializer(){
        gson = new GsonBuilder().setPrettyPrinting().serializeNulls().disableHtmlEscaping().excludeFieldsWithoutExposeAnnotation().create();
    }

    //Sérialisateur et Désérialisateur

    public String serialize(final Object obj) {
        return gson.toJson(obj);
    }

    public <T> T deserialize(final String json, Class<T> type) {
        return gson.fromJson(json, type);
    }
}
