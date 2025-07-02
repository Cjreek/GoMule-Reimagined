package gomule.translations;

import javax.annotation.Nullable;

public interface Translations {
    @Nullable
    String getTranslationOrNull(String key, String name);

    default String getTranslation(String key, String name) {
        String translationOrNull = getTranslationOrNull(key, name);
        if (translationOrNull == null) throw new IllegalArgumentException("No translation for " + key);
        return translationOrNull;
    }

    default String getTranslation(String key) {
        return getTranslation(key, "");
    }
}
