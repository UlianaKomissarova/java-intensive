import java.util.*;

public class Main {
    public static void main(String[] args) {
        List<Cat> cats = new MyArrayList<>();
        System.out.println("Список пуст? " + cats.isEmpty());
        System.out.println();

        Cat cat1 = new Cat("Пухляш", 5);
        Cat cat2 = new Cat("Ласа", 4);
        Cat cat3 = new Cat("Нюша", 7);
        cats.add(cat1);
        cats.add(cat2);
        cats.add(cat3);
        System.out.println("Список после добавления элементов: ");
        printCats(cats);

        System.out.println();
        System.out.println("Первый элемент списка: " + cats.get(0).getName());
        System.out.println("Список пуст? " + cats.isEmpty());
        System.out.println();

        cats.remove(0);
        System.out.println("Список после удаления элемента по индексу: ");
        printCats(cats);
        System.out.println();

        cats.remove(cat2);
        System.out.println("Список после удаления элемента-объекта: ");
        printCats(cats);
        System.out.println();

        MyArrayList<Cat> moreCats = (MyArrayList<Cat>) createMoreCats();
        cats.addAll(moreCats);
        System.out.println("Список кошек после добавления коллекции: ");
        printCats(cats);
        System.out.println();

        System.out.println("Отсортированные кошки по возрасту: ");
        ((MyArrayList<Cat>) cats).quickSort(0, cats.size() - 1,
            (new Comparator<Cat>() {
                @Override
                public int compare(Cat c1, Cat c2) {
                    return c1.getAge() - c2.getAge();
                }
            }));
        printCats(cats);
        System.out.println();

        cats.clear();
        System.out.println("Список после удаления всех элементов: ");
        printCats(cats);
    }

    private static void printCats(List<Cat> cats) {
        System.out.println("Размер списка = " + cats.size());

        for (Cat cat : cats) {
            System.out.println(cat.getName() + ", возраст " + cat.getAge());
        }
    }

    private static List<Cat> createMoreCats() {
        Cat cat4 = new Cat("Сима", 10);
        Cat cat5 = new Cat("Барсик", 1);
        List<Cat> moreCats = new MyArrayList<>();
        moreCats.add(cat4);
        moreCats.add(cat5);

        return moreCats;
    }
}