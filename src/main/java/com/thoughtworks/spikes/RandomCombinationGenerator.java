package com.thoughtworks.spikes;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class RandomCombinationGenerator {

    private final int totalSpace;
    private final int sampleSpace;

    public RandomCombinationGenerator(int totalSpace, int sampleSpace) {
        this.totalSpace = totalSpace;
        this.sampleSpace = sampleSpace;
    }

    public static void main(String[] args) {
        RandomCombinationGenerator randomCombinationGenerator = new RandomCombinationGenerator(100, 70);
        for (int i=0; i<10; i++) {
            Set<Integer> randomCombination = randomCombinationGenerator.generateRandomCombination();
            randomCombinationGenerator.print(randomCombination);
        }
    }

    private void print(Set<Integer> randomCombination) {
        StringBuilder stringBuffer = new StringBuilder();
        for (Integer num : randomCombination) {
            stringBuffer.append(num).append(" ");
        }
        System.out.println(stringBuffer.toString());
    }

    private Set<Integer> generateRandomCombination() {
        Set<Integer> randomCombination = new HashSet<Integer>(sampleSpace);
        Random random = new Random();
        for (int i=totalSpace-sampleSpace+1; i<totalSpace; i++) {
            int randNum = random.nextInt(i);
            if (randomCombination.contains(randNum)) {
                randomCombination.add(i);
            } else {
                randomCombination.add(randNum);
            }
        }
        return randomCombination;
    }

}
