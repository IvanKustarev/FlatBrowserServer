package Resources;

import java.util.ListResourceBundle;

public class Resources_de extends ListResourceBundle{

    private static final Object[][] contents =
            {
                    {"Тип:", "Typ: "},
                    {"Дата инициализации:", "Initialisierungsdatum: "},
                    {"Количество элементов:", "Anzahl der Elemente: "},

                    {"Содержимое коллекции: \n", "Inhalt der Sammlung: \n"},
                    {"Коллекция пустая!", "Die Sammlung ist leer!"},
                    {"Объект добавлен в коллекцию!", "Objekt zur Sammlung hinzugefügt!"},
                    {"Проблема с загрузкой объекта в базу данных!", "Problem beim Laden eines Objekts in die Datenbank!"},
                    {"Проблема с загрузкой объекта в базу данных!", "Problem beim Laden eines Objekts in die Datenbank!"},
                    {"Коллекция успешно сохранена!", "Sammlung erfolgreich gespeichert!"},
                    {"Эллемент удалён!", "Ellement entfernt!"},
                    {"Коллекция пустая!", "Die Sammlung ist leer!"},
                    {"Общее число комнат слишком большое! Перполнен BigInteger!", "Die Gesamtzahl der Zimmer ist zu groß! Perepolenno BigInteger!"},
                    {"Общее число комнат во всех квартирах: ", "Gesamtzahl der Zimmer in allen Wohnungen:"},
                    {"В коллекции нет квартир!", "In der Sammlung gibt es keine Wohnungen!"},
                    {"Добавляем элемент в коллекцию!", "Wir fügen ein Element zur Sammlung hinzu!"},
                    {"Проблема с загрузкой новой квартиры в базу данных!", "Problem beim Laden der neuen Wohnung in die Datenbank"},
                    {"Привлекательность элемента слишком большая!", "Die Attraktivität des Elements ist zu groß!"},
                    {"Пустая коллекция!", "Leere Sammlung!"},
                    {"Неправильно введён ID!\nВведите ID занова:", "ID falsch eingegeben!\n" +
                            "Geben Sie die Zana-ID ein:"},
                    {"Приступаем к обновлению параметров файла с ID: ", "Beginnen Sie mit der Aktualisierung der Parameter der Datei mit der ID: "},
                    {"Элемент успешно обновлён!", "Das Element wurde erfolgreich aktualisiert!"},
                    {"Проблема с загрузкой обновлённого элемента в базу данных!", "Problem beim Laden des aktualisierten Elements in die Datenbank!"},
                    {"Приступаем к обновлению параметров файла с ID: ", "Beginnen Sie mit der Aktualisierung der Parameter der Datei mit der ID"},
                    {"Элемент обновлён!", "Элемент обновлён!"},
                    {"Квартиры с таким ID не существует!\nПопробуйте ввести ID занова.", "Wohnungen mit einer solchen ID gibt es nicht!\n" +
                            "Versuchen Sie, die Zanova-ID einzugeben"},
                    {"Элемент удалён.", "Das Element wurde gelöscht."},
                    {"Подходящие элементы были удалены.", "Geeignete Elemente wurden entfernt."},
                    {"Нет подходящих для удаления элементов", "Keine geeigneten Elemente zum Löschen"},
                    {"Коллекция пустая!", "Die Sammlung ist leer!"},
                    {"Проблемма с загрузкой коллекции в массив в методе printFieldAscendingNumberOfRooms", "Problem beim Laden einer Auflistung in ein Array in der printFieldAscendingNumberOfRooms-Methode"},
                    {"Выводим элементы в порядке возрастания количества комнат:\n", "Wir zeigen die Elemente in aufsteigender Reihenfolge der Anzahl der Räume an:"},
                    {"В коллекции содержится всего один элемент: ID - ", "Die Auflistung enthält nur ein Element: ID -"},
                    {"Такого варианта транспора не существует!\nВведите другой.", "Eine solche Variante des Transports gibt es nicht!\n" +
                            "Geben Sie einen anderen ein."},
                    {"Нет ни одного подходящего элемента в коллекции!", "Es gibt kein passendes Element in der Sammlung!"},
                    {"В коллекции нет элементов для сравнения!", "Es gibt keine Elemente in der Sammlung zu vergleichen!"},
            };

    @Override
    protected Object[][] getContents() {
        return contents;
    }
}
