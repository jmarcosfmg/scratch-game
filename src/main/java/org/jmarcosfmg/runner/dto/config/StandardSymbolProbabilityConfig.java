package org.jmarcosfmg.runner.dto.config;

import java.util.Map;
import java.util.Random;

public class StandardSymbolProbabilityConfig {
    public int column;
    public int row;
    public Map<String, Integer> symbols;

    public String random(Random randomizer) {
        double rand = randomizer.nextDouble(1D);
        double symbolSum = symbols.values().stream().reduce(0, Integer::sum);
        double probabilitySum = 0D;

        for (Map.Entry<String, Integer> entry : symbols.entrySet()) {
            if (probabilitySum >= rand) {
                return entry.getKey();
            }
            probabilitySum += entry.getValue() / symbolSum;
        }

        return this.random(randomizer);
    }
}
