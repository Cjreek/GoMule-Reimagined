package gomule.translations;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.google.common.collect.ImmutableMap;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class MapBasedTranslations implements Translations {
    private final Map<String, String> translationData;
    private static final JsonMapper MAPPER = new JsonMapper();

    public MapBasedTranslations(Map<String, String> translationData) {
        this.translationData = translationData;
    }

    public static Translations loadTranslations(InputStream inputStream) {
        try {

            JsonNode jsonList = MAPPER.readTree(inputStream);
            Map<String, String> tmpMap = new HashMap<>(jsonList.size());
            jsonList.forEach(node -> {
                if (tmpMap.containsKey(node.get("Key").textValue()))
                    tmpMap.replace(node.get("Key").textValue(), node.get("enUS").textValue());
                else
                    tmpMap.put(node.get("Key").textValue(), node.get("enUS").textValue());
            });

            return new MapBasedTranslations(ImmutableMap.copyOf(tmpMap));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getTranslationOrNull(String key, String name) {
        String translation = translationData.get(key);
        if ((translation == null) && (name != null)) {
            translation = translationData.get(name);
        }
        return translation;
    }

    @Override
    public String toString() {
        return "MapBasedTranslations{" + "translationData=" + translationData + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MapBasedTranslations that = (MapBasedTranslations) o;
        return Objects.equals(translationData, that.translationData);
    }

    @Override
    public int hashCode() {
        return Objects.hash(translationData);
    }
}
