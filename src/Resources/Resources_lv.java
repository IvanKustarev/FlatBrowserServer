package Resources;

import java.util.ListResourceBundle;

public class Resources_lv extends ListResourceBundle{

    private static final Object[][] contents =
            {
                    {"Тип:", "Tipas:"},
                    {"Дата инициализации:", "Init data:"},
                    {"Количество элементов:", "Elementų skaičius:"},

                    {"Содержимое коллекции: \n", "Rinkinio turinys:"},
                    {"Коллекция пустая!", "Kolekcija tuščias!"},
                    {"Объект добавлен в коллекцию!", "Objektas įtrauktas į rinkinį!"},
                    {"Проблема с загрузкой объекта в базу данных!", "Objektas įtrauktas į rinkinį!"},
                    {"Проблема с загрузкой объекта в базу данных!", "Problema su siųstis objekto duomenų bazės!"},
                    {"Коллекция успешно сохранена!", "Kolekcija sėkmingai išsaugota!"},
                    {"Эллемент удалён!", "Elementas yra pašalintas!"},
                    {"Коллекция пустая!", "Kolekcija tuščias!"},
                    {"Общее число комнат слишком большое! Перполнен BigInteger!", "Bendras kambarių skaičių per daug! Pilnas BigInteger!"},
                    {"Общее число комнат во всех квартирах: ", "Bendras kambarių skaičių visuose butų:"},
                    {"В коллекции нет квартир!", "Kolekcija nėra butų!"},
                    {"Добавляем элемент в коллекцию!", "Pridėti elementą į kolekciją!"},
                    {"Проблема с загрузкой новой квартиры в базу данных!", "Problema su atsisiųsdami naujų butų duomenų bazės!"},
                    {"Привлекательность элемента слишком большая!", "Apeliacinio elemento yra per didelė!"},
                    {"Пустая коллекция!", "Tuščia kolekcija!"},
                    {"Неправильно введён ID!\nВведите ID занова:", "Neteisingai įvestas ID!\n" +
                            "Įveskite ID iš naujo:"},
                    {"Приступаем к обновлению параметров файла с ID: ", "Pereinu prie naujinimo failo parametrus su ID:"},
                    {"Элемент успешно обновлён!", "Prekė sėkmingai atnaujintas!"},
                    {"Проблема с загрузкой обновлённого элемента в базу данных!", "Problema su siųstis atnaujinto elemento duomenų bazės!"},
                    {"Приступаем к обновлению параметров файла с ID: ", "Pereinu prie naujinimo failo parametrus su ID:"},
                    {"Элемент обновлён!", "Elementas atnaujinta!"},
                    {"Квартиры с таким ID не существует!\nПопробуйте ввести ID занова.", "Butai su tokiu ID neegzistuoja!\n" +
                            "Pabandykite įvesti ID iš naujo."},
                    {"Элемент удалён.", "Elementas pašalintas."},
                    {"Нет подходящих для удаления элементов", "Nėra tinkamų elementus"},
                    {"Подходящие элементы были удалены.", "susiję elementai buvo pašalinti."},
                    {"Нет подходящих для удаления элементов", "Nėra tinkamų elementus"},
                    {"Коллекция пустая!", "Kolekcija tuščias!"},
                    {"Проблемма с загрузкой коллекции в массив в методе printFieldAscendingNumberOfRooms", "Problema su siųstis kolekcijos masyvas metodas printFieldAscendingNumberOfRooms"},
                    {"Выводим элементы в порядке возрастания количества комнат:\n", "Выводим elementus didėjimo tvarka kambarių skaičius:"},
                    {"В коллекции содержится всего один элемент: ID - ", "Kolekcija yra tik vienas elementas: ID -"},
                    {"Такого варианта транспора не существует!\nВведите другой.", "Tokio varianto transporto neegzistuoja!\n" +
                            "Įveskite kitą."},
                    {"Нет ни одного подходящего элемента в коллекции!", "Nėra nė vieno, tinkamo elemento kolekcija!"},
                    {"В коллекции нет элементов для сравнения!", "Kolekcija nėra elementų palyginimas!"},



            };

    @Override
    protected Object[][] getContents() {
        return contents;
    }
}
