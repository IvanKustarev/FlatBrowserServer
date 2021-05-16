package Resources;

import java.util.ListResourceBundle;

public class Resources_ru extends ListResourceBundle{

    private static final Object[][] contents =
            {
                    {"Тип:", "Тип: "},
                    {"Дата инициализации:", "Дата инициализации: "},
                    {"Количество элементов:", "Количество элементов: "},



            };

    @Override
    protected Object[][] getContents() {
        return contents;
    }
}
