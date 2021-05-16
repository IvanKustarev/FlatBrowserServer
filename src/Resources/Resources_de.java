package Resources;

import java.util.ListResourceBundle;

public class Resources_de extends ListResourceBundle{

    private static final Object[][] contents =
            {
                    {"Тип:", "Typ: "},
                    {"Дата инициализации:", "Initialisierungsdatum: "},
                    {"Количество элементов:", "Anzahl der Elemente: "},

            };

    @Override
    protected Object[][] getContents() {
        return contents;
    }
}
