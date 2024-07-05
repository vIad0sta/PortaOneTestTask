package org.example;

import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NumbersProcessor {
    private static final String FILE_PATH = "10m.txt";

    private static List<Integer> getNumbersFromFile() {
        List<Integer> integers = new ArrayList<>();
        Pattern pattern = Pattern.compile("-?\\d+");

        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = br.readLine()) != null) {

                Matcher matcher = pattern.matcher(line);
                while (matcher.find()) {
                    integers.add(Integer.parseInt(matcher.group()));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return integers;
    }

    private static double findMedian(List<Integer> integers){
        Collections.sort(integers);
        int halfIndex = integers.size() / 2;
        if(integers.size() % 2 == 0) {
            int firstNumber = integers.get(halfIndex - 1),
                    secondNumber = integers.get(halfIndex);
            return 0.5 * (firstNumber + secondNumber);
        } else {
            return integers.get(halfIndex);
        }
    }

    private static List<Integer> findLongestSequence(List<Integer> integers, boolean isIncreasing) {
        List<Integer> longestSeq = new ArrayList<>();
        List<Integer> currentSeq = new ArrayList<>();

        for (int i = 0; i < integers.size(); i++) {
            int currentNumber = integers.get(i);

            if (i == 0 || (isIncreasing ? currentNumber >= integers.get(i - 1) : currentNumber <= integers.get(i - 1))) {
                currentSeq.add(currentNumber);
            } else {
                if (currentSeq.size() > longestSeq.size()) {
                    longestSeq = new ArrayList<>(currentSeq);
                }
                currentSeq.clear();
                currentSeq.add(currentNumber);
            }
        }

        if (currentSeq.size() > longestSeq.size()) {
            longestSeq = currentSeq;
        }

        return longestSeq;
    }

    public static void main(String[] args) {
        List<Integer> integers = getNumbersFromFile();

        List<Integer> longestIncreasingSeq = findLongestSequence(integers, true);
        List<Integer> longestDecreasingSeq = findLongestSequence(integers, false);
        double median = findMedian(integers);
        int max = integers.get(integers.size() - 1);
        int min = integers.get(0);
        double avg = integers.stream().mapToInt(Integer::intValue).average().orElse(0.0);


        System.out.println("Мвксимальне: " + max);
        System.out.println("Мінімальне: " + min);
        System.out.println("Медіана: " + median);
        System.out.println("Середнє: " + avg);
        System.out.println("Зростаюча послідовність: " + longestIncreasingSeq);
        System.out.println("Спадаюча послідовність: " + longestDecreasingSeq);
    }
}