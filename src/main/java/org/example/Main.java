package org.example;

import java.util.*;

public class Main {

    static int FAIL = 0;
    static int SUSSES = 0;
    static final int ATTEMPTS = 50;
    static int ITERATIONS;
    static List<Integer> PRISONERS = new ArrayList<>();
    static Map<Integer, Integer> BOXES = new HashMap<>();
    static List<Integer> NUMBERS = new ArrayList<>();

    public static void main(String[] args) {
        inputIterations();

        for (int i = 1; i <= 100; i++) {
            PRISONERS.add(i);
            NUMBERS.add(i);
        }

        for (int i = 0; i < ITERATIONS; i++) {
            Collections.shuffle(NUMBERS);
            putNumbersInBoxes();
            boolean isFail = false;
            for (Integer prisoner : PRISONERS) {
                if (!isFindHisNumberInBoxes(prisoner)) {
                    FAIL++;
                    isFail = true;
                    break;
                }
            }
            SUSSES = isFail ? SUSSES : SUSSES + 1;
        }
        showResults();
    }

    private static void inputIterations() {
        System.out.println("Input iterations");
        try {
            ITERATIONS = new Scanner(System.in).nextInt();
        } catch (InputMismatchException e) {
            inputIterations();
        }
    }

    private static void putNumbersInBoxes() {
        for (int j = 0; j < NUMBERS.size(); j++) {
            BOXES.put(j + 1, NUMBERS.get(j));
        }
    }

    private static boolean isFindHisNumberInBoxes(Integer prisoner) {
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
        System.out.printf("\tSUSSES: " + "%.2f\n", ((double) SUSSES / ITERATIONS) * 100);
        System.out.printf("\tFAIL: " +  "%.2f\n", ((double) FAIL / ITERATIONS) * 100);
    }
}
