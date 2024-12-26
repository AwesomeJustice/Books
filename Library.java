import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.SneakyThrows;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.*;
import java.util.stream.Collectors;

public class Library {
    private static List<Visitor> visitors;

    // Загрузка данных из JSON файла
    @SneakyThrows
    public static void loadData(String filename) {
        Gson gson = new Gson();
        Type visitorListType = new TypeToken<List<Visitor>>() {}.getType();
        visitors = gson.fromJson(new FileReader(filename), visitorListType);
    }

    public static void main(String[] args) {
        loadData("books.json");

        // Задание 1: Вывести список посетителей и их количество.
        System.out.println("Список посетителей:");
        visitors.forEach(v -> System.out.println(v.getFirstName() + " " + v.getLastName()));
        System.out.println("Количество посетителей: " + visitors.size());

        // Задание 2: Вывести список и количество книг, добавленных посетителями в избранное, без повторений.
        Set<Book> uniqueBooks = visitors.stream()
                .flatMap(v -> v.getFavoriteBooks().stream())
                .collect(Collectors.toSet());
        System.out.println("\nУникальные книги в избранном:");
        uniqueBooks.forEach(b -> System.out.println(b.getTitle() + " от " + b.getAuthor()));
        System.out.println("Количество уникальных книг: " + uniqueBooks.size());

        // Задание 3: Отсортировать по году издания и вывести список книг.
        List<Book> sortedBooks = visitors.stream()
                .flatMap(v -> v.getFavoriteBooks().stream())
                .sorted(Comparator.comparingInt(Book::getYear))
                .collect(Collectors.toList());
        System.out.println("\nКниги, отсортированные по году издания:");
        sortedBooks.forEach(b -> System.out.println(b.getTitle() + " (" + b.getYear() + ")"));

        // Задание 4: Проверить, есть ли у кого-то в избранном книга автора “Jane Austen”.
        boolean hasJaneAusten = visitors.stream()
                .flatMap(v -> v.getFavoriteBooks().stream())
                .anyMatch(b -> "Jane Austen".equals(b.getAuthor()));
        System.out.println("\nЕсть ли книга автора Jane Austen? " + (hasJaneAusten ? "Да" : "Нет"));

        // Задание 5: Вывести максимальное число добавленных в избранное книг.
        int maxFavoriteBooks = visitors.stream()
                .mapToInt(v -> v.getFavoriteBooks().size())
                .max()
                .orElse(0);
        System.out.println("\nМаксимальное количество избранных книг: " + maxFavoriteBooks);

        // Задание 6: Сгруппировать посетителей по категориям и создать SMS сообщения.
        double averageFavorites = visitors.stream()
                .mapToInt(v -> v.getFavoriteBooks().size())
                .average()
                .orElse(0);

        List<SMSMessage> smsMessages = visitors.stream()
                .filter(Visitor::isSubscribed)
                .map(v -> {
                    String message = (v.getFavoriteBooks().size() > averageFavorites) ? "you are a bookworm" :
                                     (v.getFavoriteBooks().size() < averageFavorites) ? "read more" : "fine";
                    return new SMSMessage(v.getPhoneNumber(), message);
                })
                .collect(Collectors.toList());

        System.out.println("\nSMS-сообщения:");
        smsMessages.forEach(sms -> System.out.println("Телефон: " + sms.getPhoneNumber() + ", Сообщение: " + sms.getMessage()));
    }
}