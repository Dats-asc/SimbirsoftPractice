package com.example.simbirsoftpracticeapp.java2;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Набор заданий по работе с классами в java.
 * <p>
 * Задания подразумевают создание класса(ов), выполняющих задачу.
 * <p>
 * Проверка осуществляется ментором.
 */
public interface ClassesBlock {

    /*
      I

      Создать класс с двумя переменными. Добавить функцию вывода на экран
      и функцию изменения этих переменных. Добавить функцию, которая находит
      сумму значений этих переменных, и функцию, которая находит наибольшее
      значение из этих двух переменных.
     */

    class Class1 {

        public int x = 10;
        public int y = 20;

        public void print() {
            String.format("x is %s; y is %s", String.valueOf(x), String.valueOf(y));
        }

        public void setDefault() {
            x = 0;
            y = 0;
        }

        public int getSum() {
            return x + y;
        }

        public int getMax() {
            return Math.max(x, y);
        }
    }

    /*
      II

      Создать класс, содержащий динамический массив и количество элементов в нем.
      Добавить конструктор, который выделяет память под заданное количество элементов.
      Добавить методы, позволяющие заполнять массив случайными числами,
      переставлять в данном массиве элементы в случайном порядке, находить количество
      различных элементов в массиве, выводить массив на экран.
     */

    class MyList {

        public ArrayList<Integer> arr;
        private int size;

        public MyList(int size) {
            arr = new ArrayList<>(size);
            this.size = size;
        }

        public void fillRandom() {
            Random random = new Random();
            System.out.println(String.format("Random arr: "));
            for (int i = 0; i < size; i++) {
                arr.add(random.nextInt());
                System.out.println(String.format(" ,%s", arr.get(i)));
            }

        }

        public void mix() {
            ArrayList<Integer> indexes = new ArrayList(arr.size());
            ArrayList<Integer> mixedArr = new ArrayList(arr.size());
            for (int i = 0; i < arr.size(); i++) {
                indexes.add(i);
            }
            for (int i = 0; i < arr.size(); i++) {
                int index = (int) ((Math.random() * (indexes.size() - 0)) + 0);
                mixedArr.add(arr.get(index));
                indexes.remove(index);
            }
            System.out.println(String.format("Mixed arr: "));
            for (int i = 0; i < mixedArr.size(); i++) {
                System.out.println(String.format(" ,%s", mixedArr.get(i)));
            }
            arr = mixedArr;
        }

        public int findDifferentValues() {
            int count = 0;
            for (int i = 0; i < arr.size() - 1; i++) {
                for (int j = i + 1; j < arr.size(); j++) {
                    if (arr.get(i).equals(arr.get(j))) {
                        count++;
                    }
                }
            }
            System.out.println(String.format("Count of different values: %s", count));
            return count;
        }

        public void print() {
            StringBuilder outString = new StringBuilder();
            outString.append("[");
            for (int i = 0; i < arr.size(); i++) {
                outString.append(arr.get(i));
                if (i != arr.size() - 1)
                    outString.append(", ");
            }
            outString.append("]");
            System.out.println(outString);
        }
    }

    /*
      III

      Описать класс, представляющий треугольник. Предусмотреть методы для создания объектов,
      вычисления площади, периметра и точки пересечения медиан.
      Описать свойства для получения состояния объекта.
     */

    class Triangle {
        private double ax;
        private double ay;

        private double bx;
        private double by;

        private double cx;
        private double cy;

        public Triangle(double ax, double ay, double bx, double by, double cx, double cy) {
            this.ax = ax;
            this.ay = ay;
            this.bx = bx;
            this.by = by;
            this.cx = cx;
            this.cy = cy;
        }

        private double getLength(double ax, double ay, double bx, double by) {
            return Math.sqrt(Math.pow(Math.abs(ax - bx), 2) + Math.pow(Math.abs(ay - by), 2));
        }

        public double getPerimeter() {
            double ab = getLength(ax, ay, bx, by);
            double bc = getLength(bx, by, cx, cy);
            double ac = getLength(ax, ay, cx, cy);
            System.out.println(String.format("Периметр треугольника со сторонами AB = %s, BC = %s, AC = %s - %s", ab, bc, ac, ab + bc + ac));
            return ab + bc + ac;
        }

        public double getSquare() {
            System.out.println(String.format("Площадь = %s", 1.0 / 2 * ((Math.abs(ax - cx) * (by - cy)) - (bx - cx) * (ay - cy))));
            return 1.0 / 2 * ((Math.abs(ax - cx) * (by - cy)) - (bx - cx) * (ay - cy));
        }

        public Point medianCrossPoint() {
            double xk = (ax + cx) / 2;
            double yk = (ay + cy) / 2;
            Point k = new Point(xk, yk);
            double xm = (xk + bx * 2) / (ay + 2);
            double ym = xk / ax;
            return new Point(xm, ym);

        }


        class Point {
            public double x;
            public double y;

            public Point(double x, double y) {
                this.x = x;
                this.y = y;
            }
        }
    }

    /*
      IV

      Составить описание класса для представления времени.
      Предусмотреть возможности установки времени и изменения его отдельных полей
      (час, минута, секунда) с проверкой допустимости вводимых значений.
      В случае недопустимых значений полей выбрасываются исключения.
      Создать методы изменения времени на заданное количество часов, минут и секунд.
     */

    class Time {
        private int hours;
        private int minutes;
        private int seconds;

        public Time(int hours, int minutes, int seconds) {
            this.hours = hours;
            this.minutes = minutes;
            this.seconds = seconds;
        }

        public void setHours(int hours) throws Exception {
            if (hours < 0 && hours >= 24) {
                throw new Exception("Incorrect hour value");
            } else {
                this.hours = hours;
            }
            printTime();
        }

        public void setMinutes(int minutes) throws Exception {
            if (minutes < 0 && minutes >= 60) {
                throw new Exception("Incorrect minutes value");
            } else {
                this.minutes = minutes;
            }
            printTime();
        }

        public void setSeconds(int seconds) throws Exception {
            if (seconds < 0 && seconds >= 60) {
                throw new Exception("Incorrect seconds value");
            } else {
                this.seconds = seconds;
            }
            printTime();
        }

        public void printTime(){
            System.out.println(String.format("Время: %s:%s:%s", hours, minutes, seconds));
        }

        public void addHours(int hours) {
            this.hours = ((this.hours + hours) % 24) * 24;
            printTime();
        }

        public void addMinutes(int minutes) {
            int hoursInMinutes = (this.minutes + minutes) / 60;
            addHours(hoursInMinutes);
            this.minutes = ((this.minutes + minutes) % 60) * 60;
            printTime();
        }

        public void addSeconds(int seconds) {
            int minutesInSeconds = (this.seconds + seconds) / 60;
            addMinutes(minutesInSeconds);
            this.seconds = ((this.seconds + seconds) % 60) * 60;
            printTime();
        }


    }

    /*
      V

      Класс Абонент: Идентификационный номер, Фамилия, Имя, Отчество, Адрес,
      Номер кредитной карточки, Дебет, Кредит, Время междугородных и городских переговоров;
      Конструктор; Методы: установка значений атрибутов, получение значений атрибутов,
      вывод информации. Создать массив объектов данного класса.
      Вывести сведения относительно абонентов, у которых время городских переговоров
      превышает заданное.  Сведения относительно абонентов, которые пользовались
      междугородной связью. Список абонентов в алфавитном порядке.
     */

    class Client {
        private int id;
        private String firstName;
        private String secondName;
        private String thirdName;
        private String adress;
        private int cardId;
        private int debet;
        private int credit;
        private String time;

        private Client[] clients = new Client[]{};

        public Client(int id, String firstName, String secondName, String thirdName, String adress, int cardId, int debet, int credit, String time) {
            this.id = id;
            this.firstName = firstName;
            this.secondName = secondName;
            this.thirdName = thirdName;
            this.adress = adress;
            this.cardId = cardId;
            this.debet = debet;
            this.credit = credit;
            this.time = time;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getSecondName() {
            return secondName;
        }

        public void setSecondName(String secondName) {
            this.secondName = secondName;
        }

        public String getThirdName() {
            return thirdName;
        }

        public void setThirdName(String thirdName) {
            this.thirdName = thirdName;
        }

        public String getAdress() {
            return adress;
        }

        public void setAdress(String adress) {
            this.adress = adress;
        }

        public int getCardId() {
            return cardId;
        }

        public void setCardId(int cardId) {
            this.cardId = cardId;
        }

        public int getDebet() {
            return debet;
        }

        public void setDebet(int debet) {
            this.debet = debet;
        }

        public int getCredit() {
            return credit;
        }

        public void setCredit(int credit) {
            this.credit = credit;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public void print(Client client) {
            String outString = "Id: %s\n" +
                    "Firstname: %s\n" +
                    "Secondname: %s\n" +
                    "Thirdname: %s\n" +
                    "Adress: %s\n" +
                    "Card Id: %s\n" +
                    "Debet: %s\n" +
                    "Credit: %s\n" +
                    "Time: %s\n";
            String result = String.format(outString, client.id, client.firstName, client.secondName, client.thirdName, client.adress, client.cardId, client.debet, client.credit, client.time);
            System.out.println(result);
        }

        public void showClientsWithTimeExcess(String time) {
            for (Client client : clients) {
                if (client.time.compareTo(time) > 0) {
                    print(client);
                }
            }
        }

        public void showSortClients() {
            for (int i = 0; i < clients.length - 1; i++) {
                for (int j = 0; j < clients.length; j++) {
                    if (clients[j].firstName.compareTo(clients[j + 1].firstName) == -1) {
                        Client temp = clients[j];
                        clients[j] = clients[j + 1];
                        clients[j + 1] = temp;
                    }
                }
            }

            for (int i = 0; i < clients.length - 1; i++) {
                for (int j = 0; j < clients.length; j++) {
                    if (clients[j].secondName.compareTo(clients[j + 1].secondName) == -1) {
                        Client temp = clients[j];
                        clients[j] = clients[j + 1];
                        clients[j + 1] = temp;
                    }
                }
            }

            for (int i = 0; i < clients.length - 1; i++) {
                for (int j = 0; j < clients.length; j++) {
                    if (clients[j].thirdName.compareTo(clients[j + 1].thirdName) == -1) {
                        Client temp = clients[j];
                        clients[j] = clients[j + 1];
                        clients[j + 1] = temp;
                    }
                }
            }

            for (Client client : clients) {
                print(client);
            }
        }
    }

    /*
      VI

      Задача на взаимодействие между классами. Разработать систему «Вступительные экзамены».
      Абитуриент регистрируется на Факультет, сдает Экзамены. Преподаватель выставляет Оценку.
      Система подсчитывает средний бал и определяет Абитуриента, зачисленного в учебное заведение.
     */

    class Enrollee implements Student {

        private Faculty faculty;
        private Teacher teacher;
        private ArrayList<Integer> assessments = new ArrayList<>();
        private double gpa = 0;

        public Enrollee(Facultative faculty) {
            System.out.println(String.format("Зарегистрирован новый абитуриент"));
            this.faculty = (Faculty) faculty;

        }

        public void register() {
            teacher = faculty.registration();
        }

        public void passExam() {
            teacher.getAssessment(this);
        }

        @Override
        public void setAssessment(int assessment) {
            assessments.add(assessment);
            setGpa();
        }

        @Override
        public List<Facultative> getAvailableFacultatives() {
            ArrayList<Facultative> facultatives = new ArrayList();
            if (gpa < 30) {
                facultatives.add(new Faculty());
                System.out.println(String.format("Не поступил"));
                return facultatives;
            } else if (gpa < 50 && gpa >= 30) {
                System.out.println(String.format("Поступил на факультет 1"));
                return facultatives;
            } else if (gpa < 80 && gpa >= 50) {
                System.out.println(String.format("Поступил на факультет 2"));
                return facultatives;
            } else {
                System.out.println(String.format("Поступил на факультет 3"));
                return facultatives;
            }
        }

        private void setGpa() {
            int sum = 0;
            for (int assessment : assessments) {
                sum += assessment;
            }
            gpa = sum / assessments.size();
            System.out.println(String.format("Средний балл: %s", gpa));
        }

        public double getGpa() {
            return gpa;
        }
    }

    class Faculty implements Facultative {

        private Teacher teacher;

        public Faculty() {
            teacher = new Teacher();
        }

        @Override
        public Teacher registration() {
            System.out.println(String.format("Зарегистрировался на факультет"));
            return teacher;
        }
    }

    class Teacher implements Lecturer {

        @Override
        public void getAssessment(Student enrollee) {
            System.out.println(String.format("Преподаватель выставил оценку: 0"));
            enrollee.setAssessment(0);
        }
    }

    interface Facultative {

        Lecturer registration();
    }

    interface Lecturer {

        void getAssessment(Student student);
    }

    interface Student {

        void passExam();

        void setAssessment(int assessment);

        List<Facultative> getAvailableFacultatives();

    }

    /*
      VII

      Задача на взаимодействие между классами. Разработать систему «Интернет-магазин».
      Товаровед добавляет информацию о Товаре. Клиент делает и оплачивает Заказ на Товары.
      Товаровед регистрирует Продажу и может занести неплательщика в «черный список».
     */

    class Store {
        private Merchandiser merchandiser;

        public Store() {
            merchandiser = new Merchandiser();
        }

        public void makeOrder(Order order) {
            merchandiser.registerOrder(order);
        }
    }

    class Merchandiser {

        private ArrayList<Customer> blackList = new ArrayList<>();
        private ArrayList<Product> products = new ArrayList<>();
        private ArrayList<Order> orders = new ArrayList<>();

        public void addProduct(Product product) {
            products.add(product);
        }

        public void registerOrder(Order order) {
            orders.add(order);
            if (!order.productIsPaid)
                blackList.add(order.customer);
        }
    }

    class Customer {

        private Store store;

        public Customer() {
            store = new Store();
        }

        public void makeOrder() {
            store.makeOrder(new Order(this, new Product(), true));
        }
    }

    class Order {

        Product product;
        boolean productIsPaid;
        Customer customer;

        public Order(Customer customer, Product product, boolean isPaid) {
            this.customer = customer;
            this.product = product;
            productIsPaid = isPaid;
        }
    }

    class Product {
        int id;
    }
}
