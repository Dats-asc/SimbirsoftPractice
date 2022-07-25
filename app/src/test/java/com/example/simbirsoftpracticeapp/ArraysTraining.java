package com.example.simbirsoftpracticeapp;

/**
 * Набор тренингов по работе с массивами в java.
 * <p>
 * Задания определены в комментариях методов.
 * <p>
 * Проверка может быть осуществлена запуском тестов.
 * <p>
 * Доступна проверка тестированием @see ArraysTrainingTest.
 */
public class ArraysTraining {

    /**
     * Метод должен сортировать входящий массив
     * по возрастранию пузырьковым методом
     *
     * @param valuesArray массив для сортировки
     * @return отсортированный массив
     */
    public int[] sort(int[] valuesArray) {
        for (int i = 0; i < valuesArray.length; i++) {
            for (int j = 0; j < valuesArray.length - 1; j++) {
                if (valuesArray[j] > valuesArray[j + 1]) {
                    valuesArray[j + 1] = valuesArray[j + 1] + valuesArray[j];
                    valuesArray[j] = valuesArray[j + 1] - valuesArray[j];
                    valuesArray[j + 1] = valuesArray[j + 1] - valuesArray[j];

                }
            }
        }
        return valuesArray;
    }

    /**
     * Метод должен возвращать максимальное
     * значение из введенных. Если входящие числа
     * отсутствуют - вернуть 0
     *
     * @param values входящие числа
     * @return максимальное число или 0
     */
    public int maxValue(int... values) {
        int max = Integer.MIN_VALUE;
        if (values.length == 0) {
            return 0;
        } else {
            for (int value : values) {
                if (value > max)
                    max = value;
            }
            return max;
        }
    }

    /**
     * Переставить элементы массива
     * в обратном порядке
     *
     * @param array массив для преобразования
     * @return входящий массив в обратном порядке
     */
    public int[] reverse(int[] array) {
        for (int i = 0; i < array.length / 2; i++) {
            if (i != array.length - 1 - i) {
                array[i] = array[array.length - i - 1] + array[i];
                array[array.length - 1 - i] = array[i] - array[array.length - 1 - i];
                array[i] = array[i] - array[array.length - 1 - i];
            }
        }
        return array;
    }

    /**
     * Метод должен вернуть массив,
     * состоящий из чисел Фибоначчи
     *
     * @param numbersCount количество чисел Фибоначчи,
     *                     требуемое в исходящем массиве.
     *                     Если numbersCount < 1, исходный
     *                     массив должен быть пуст.
     * @return массив из чисел Фибоначчи
     */
    public int[] fibonacciNumbers(int numbersCount) {
        if (numbersCount < 1) {
            return new int[]{};
        } else {
            int[] result = new int[numbersCount];
            result[0] = 1;
            result[1] = 1;
            for (int i = 2; i < numbersCount; i++) {
                result[i] = result[i - 1] + result[i - 2];
            }
            return result;
        }
    }

    /**
     * В данном массиве найти максимальное
     * количество одинаковых элементов.
     *
     * @param array массив для выборки
     * @return количество максимально встречающихся
     * элементов
     */
    public int maxCountSymbol(int[] array) {
        int maxOrdinaryValCount = 0;
        for (int i = 0; i < array.length; i++) {
            int currentValueOrdinaryValCount = 0;
            for (int j = i; j < array.length; j++) {
                if (array[i] == array[j]) {
                    currentValueOrdinaryValCount++;
                }
            }
            if (currentValueOrdinaryValCount > maxOrdinaryValCount) {
                maxOrdinaryValCount = currentValueOrdinaryValCount;
            }
        }
        return maxOrdinaryValCount;
    }
}
