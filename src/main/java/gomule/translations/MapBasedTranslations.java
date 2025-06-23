package gomule.translations;

import com.fasterxml.jackson.databind.json.JsonMapper;
import com.google.common.collect.ImmutableMap;

import java.io.IOException;
import java.io.InputStream;
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
            ImmutableMap.Builder<String, String> mapBuilder = ImmutableMap.builder();
            MAPPER.readTree(inputStream)
                    .forEach(node -> mapBuilder.put(
                            node.get("Key").textValue(), node.get("enUS").textValue()));
            return new MapBasedTranslations(mapBuilder.build());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getTranslationOrNull(String key) {
        return translationData.get(key);
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
