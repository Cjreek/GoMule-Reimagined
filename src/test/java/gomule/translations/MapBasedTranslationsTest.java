package gomule.translations;

import com.google.common.collect.ImmutableMap;
import org.junit.jupiter.api.Test;

import static gomule.files.FileReaderUtils.getResource;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class MapBasedTranslationsTest {

    @Test
    public void loadTranslations() {
        Translations actual =
                MapBasedTranslations.loadTranslations(getResource("translationsTestData/translationsTestData.json"));
        Translations expected = new MapBasedTranslations(ImmutableMap.of(
                "ModStr4l", "Slightly Increased Attack Speed", "ModStr4n", "Greatly Increased Attack Speed"));
        assertEquals(expected, actual);
        assertNull(actual.getTranslationOrNull("missing-key"));
        assertEquals("Greatly Increased Attack Speed", actual.getTranslationOrNull("ModStr4n"));
    }
}