/*
Задача 2: Перепись населения
Описание
В данной задаче предлагается проанализировать массив данных с информаций о людях с использованием стримов из библиотеки
Stream API.

Из коллеции объектов Person необходимо:

Найти количество несовершеннолетних (т.е. людей младше 18 лет).
Получить список фамилий призывников (т.е. мужчин от 18 и до 27 лет).
Получить отсортированный по фамилии список потенциально работоспособных людей с высшим образованием в выборке
(т.е. людей с высшим образованием от 18 до 60 лет для женщин и до 65 лет для мужчин).
 */

import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) {

        List<String> names = Arrays.asList("Jack", "Connor", "Harry", "George", "Samuel", "John");
        List<String> families = Arrays.asList("Evans", "Young", "Harris", "Wilson", "Davies", "Adamson", "Brown");
        Collection<Person> persons = new ArrayList<>();
        for (int i = 0; i < 10_000_000; i++) {
            persons.add(new Person(
                    names.get(new Random().nextInt(names.size())),
                    families.get(new Random().nextInt(families.size())),
                    new Random().nextInt(100),
                    Sex.values()[new Random().nextInt(Sex.values().length)],
                    Education.values()[new Random().nextInt(Education.values().length)])
            );
        }


        //Количество несовершеннолетних (т.е. людей младше 18 лет).
        System.out.print("количество несовершеннолетних (т.е. людей младше 18 лет): ");
        Stream<Person> personStream1 = persons.stream();
        long result1 = personStream1.filter(pers -> pers.getAge() < 18).count();
        System.out.println(result1);

        //Список фамилий призывников (т.е. мужчин от 18 и до 27 лет).
        System.out.println("список фамилий призывников (т.е. мужчин от 18 и до 27 лет): ");
        Stream<Person> personStream2 = persons.stream();
        List<String> result2 = personStream2.filter(pers -> pers.getAge() >= 18 & pers.getAge() <= 27 & pers.getSex() == Sex.MAN)
                .map(pers -> pers.getFamily())
                .collect(Collectors.toList());
        result2.forEach(System.out::println);


        //Отсортированный по фамилии список потенциально работоспособных людей с высшим образованием в выборке
        //(т.е. людей с высшим образованием от 18 до 60 лет для женщин и до 65 лет для мужчин).
        System.out.println("Отсортированный по фамилии список потенциально работоспособных людей с высшим образованием '\n'" +
                "в выборке (т.е. людей с высшим образованием от 18 до 60 лет для женщин и до 65 лет для мужчин): ");
        Stream<Person> personStream3 = persons.stream();
        Comparator<String> comparator = Comparator.naturalOrder();

        List<String> result3 = personStream3.filter(pers -> (pers.getEducation() == Education.HIGHER & pers.getAge() >= 18))
                .filter(pers -> (pers.getSex() == Sex.WOMAN & pers.getAge() <= 60) | (pers.getSex() == Sex.MAN & pers.getAge() <= 65))
                .map(pers -> pers.getFamily())
                .sorted(comparator)
                .collect(Collectors.toList());
        result3.forEach(System.out::println);
    }
}