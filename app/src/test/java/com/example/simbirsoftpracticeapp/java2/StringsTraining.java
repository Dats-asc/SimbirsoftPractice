package com.example.simbirsoftpracticeapp.java2;

import java.util.ArrayList;

/**
 * Набор тренингов по работе со строками в java.
 * <p>
 * Задания определены в комментариях методов.
 * <p>
 * Проверка может быть осуществлена запуском тестов.
 * <p>
 * Доступна проверка тестированием @see StringsTrainingTest.
 */
public class StringsTraining {

    /**
     * Метод по созданию строки,
     * состоящей из нечетных символов
     * входной строки в том же порядке
     * (нумерация символов идет с нуля)
     *
     * @param text строка для выборки
     * @return новая строка из нечетных
     * элементов строки text
     */
    public String getOddCharacterString(String text) {
        char[] charText = text.toCharArray();
        StringBuilder newStr = new StringBuilder();
        for (int i = 0; i < charText.length; i++){
            if (i % 2 == 1)
                newStr.append(charText[i]);
        }
        return newStr.toString();
    }

    /**
     * Метод для определения количества
     * символов, идентичных последнему
     * в данной строке
     *
     * @param text строка для выборки
     * @return массив с номерами символов,
     * идентичных последнему. Если таких нет,
     * вернуть пустой массив
     */
    public int[] getArrayLastSymbol(String text) {
        char[] charText = text.toCharArray();
        ArrayList<Integer> indexes = new ArrayList<>();
        for (int i = 0; i < charText.length - 1; i++){
            if (charText[i] == charText[charText.length - 1]){
                indexes.add(i);
            }
        }
        if (indexes.size() == 0){
            return new int[]{};
        } else {
            int[] result = indexes.stream().mapToInt(i -> i).toArray();
            return result;
        }
    }

    /**
     * Метод по получению количества
     * цифр в строке
     *
     * @param text строка для выборки
     * @return количество цифр в строке
     */
    public int getNumbersCount(String text) {
        char[] charText = text.toCharArray();
        int digitCount = 0;
        for (int i = 0; i < charText.length; i++) {
            if (charText[i] >= '0' && charText[i] <= '9')
                digitCount++;
        }
        return digitCount;
    }

    /**
     * Дан текст. Заменить все цифры
     * соответствующими словами.
     *
     * @param text текст для поиска и замены
     * @return текст, где цифры заменены словами
     */
    public String replaceAllNumbers(String text) {
        char[] charText = text.toCharArray();
        StringBuilder newText = new StringBuilder();
        for (int i = 0; i < charText.length; i++) {
            if (charText[i] >= '0' && charText[i] <= '9'){
                switch (charText[i]){
                    case '0': {
                        newText.append("zero");
                        break;
                    }
                    case '1': {
                        newText.append("one");
                        break;
                    }
                    case '2': {
                        newText.append("two");
                        break;
                    }
                    case '3': {
                        newText.append("three");
                        break;
                    }
                    case '4': {
                        newText.append("four");
                        break;
                    }
                    case '5': {
                        newText.append("five");
                        break;
                    }
                    case '6': {
                        newText.append("six");
                        break;
                    }
                    case '7': {
                        newText.append("seven");
                        break;
                    }
                    case '8': {
                        newText.append("eight");
                        break;
                    }
                    case '9': {
                        newText.append("nine");
                        break;
                    }
                    default: break;
                }
            } else {
                newText.append(charText[i]);
            }
        }
        return newText.toString();
    }

    /**
     * Метод должен заменить заглавные буквы
     * на прописные, а прописные на заглавные
     *
     * @param text строка для изменения
     * @return измененная строка
     */
    public String capitalReverse(String text) {
        char[] charText = text.toCharArray();
        StringBuilder newText = new StringBuilder();
        for (int i = 0; i < charText.length; i++) {
            if (Character.isLowerCase(charText[i])){
                newText.append(Character.toUpperCase(charText[i]));
            } else {
                newText.append(Character.toLowerCase(charText[i]));
            }
        }
        return newText.toString();
    }

}
