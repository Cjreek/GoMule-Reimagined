package gomule;

import gomule.translations.Translations;
import gomule.translations.TranslationsLoader;

public class D2Files {

    private static D2Files INSTANCE;
    private Translations translations;

    private D2Files(Translations translations) {
        this.translations = translations;
    }

    public static D2Files getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new D2Files(TranslationsLoader.loadTranslations());
        }
        return INSTANCE;
    }

    public Translations getTranslations() {
        return translations;
    }
}
