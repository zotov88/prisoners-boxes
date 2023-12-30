package org.example;

import java.util.*;

public class Main {

    static int OPTION = -1;
    static int FAIL = 0;
    static int SUSSES = 0;
    static final int ATTEMPTS = 50;
    static final int PRISONER_COUNT = 100;
    static int ITERATIONS;
    static List<Integer> PRISONERS = new ArrayList<>();
    static Map<Integer, Integer> BOXES = new HashMap<>();
    static List<Integer> NUMBERS = new ArrayList<>();

    public static void main(String[] args) {
        inputIterations();
        fillStartParameters();
        changeStrategyInfo();
        if (isChangeStrategy()) {
            withStrategy();
        } else {
            withoutStrategy();
        }
        showResults();
    }

    private static void changeStrategyInfo() {
        System.out.println("1 - with strategy");
        System.out.println("0 - without strategy");
        inputOption();
        if (OPTION < 0 || OPTION > 1) {
            changeStrategyInfo();
        }
    }

    private static boolean isChangeStrategy() {
        return OPTION == 1;
    }

    private static void inputOption() {
        try {
            OPTION = new Scanner(System.in).nextInt();
        } catch (InputMismatchException e) {
            inputOption();
        }
    }

    private static void inputIterations() {
        System.out.println("Input iterations");
        try {
            ITERATIONS = new Scanner(System.in).nextInt();
        } catch (InputMismatchException e) {
            inputIterations();
        }
    }

    private static void fillStartParameters() {
        for (int i = 1; i <= PRISONER_COUNT; i++) {
            PRISONERS.add(i);
            NUMBERS.add(i);
        }
    }

    private static void withoutStrategy() {
        for (int i = 0; i < ITERATIONS; i++) {
            Collections.shuffle(NUMBERS);
            putNumbersInBoxes();
            boolean isFail = false;
            for (Integer prisoner : PRISONERS) {
                if (!isFindHisNumberRandom(prisoner)) {
                    FAIL++;
                    isFail = true;
                    break;
                }
            }
            SUSSES = isFail ? SUSSES : SUSSES + 1;
        }
    }

    private static boolean isFindHisNumberRandom(Integer prisoner) {
        List<Integer> randomOrder = new ArrayList<>(PRISONERS);
        Collections.shuffle(randomOrder);
        for (int i = 0; i < randomOrder.size() / 2; i++) {
            if (Objects.equals(randomOrder.get(i), prisoner)) {
                return true;
            }
        }
        return false;
    }

    private static void withStrategy() {
        for (int i = 0; i < ITERATIONS; i++) {
            Collections.shuffle(NUMBERS);
            putNumbersInBoxes();
            boolean isFail = false;
            for (Integer prisoner : PRISONERS) {
                if (!isFindHisNumberStrategy(prisoner)) {
                    FAIL++;
                    isFail = true;
                    break;
                }
            }
            SUSSES = isFail ? SUSSES : SUSSES + 1;
        }
    }

    private static void putNumbersInBoxes() {
        for (int j = 0; j < NUMBERS.size(); j++) {
            BOXES.put(j + 1, NUMBERS.get(j));
        }
    }

    private static boolean isFindHisNumberStrategy(Integer prisoner) {
        int numberBox = prisoner;
        int note;
        int count = 0;
        while (count < ATTEMPTS) {
            note = BOXES.get(numberBox);
            if (note == prisoner) {
                return true;
            }
            numberBox = note;
            count++;
        }
        return false;
    }

    private static void showResults() {
        System.out.println("ITERATIONS: " + ITERATIONS);
        System.out.println("ABSOLUTE");
        System.out.println("\tSUSSES: " + SUSSES);
        System.out.println("\tFAIL: " + FAIL);
        System.out.println("RELATIVE");
        System.out.printf("\tSUSSES: " + "%.2f%s\n", ((double) SUSSES / ITERATIONS) * 100, " %");
        System.out.printf("\tFAIL: " + "%.2f%s\n", ((double) FAIL / ITERATIONS) * 100, " %");
    }
}
