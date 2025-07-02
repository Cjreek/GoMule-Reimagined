package gomule.translations;

import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Objects;

public class CompositeTranslations implements Translations {

    private Translations[] translations;

    public CompositeTranslations(Translations... translations) {
        this.translations = translations;
    }

    @Nullable
    @Override
    public String getTranslationOrNull(String key, String name) {
        return Arrays.stream(translations)
                .map(it -> it.getTranslationOrNull(key, name))
                .filter(Objects::nonNull)
                .findFirst()
                .orElse(null);
    }
}
