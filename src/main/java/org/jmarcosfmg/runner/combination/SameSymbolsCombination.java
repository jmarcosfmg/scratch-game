package org.jmarcosfmg.runner.combination;

import org.jmarcosfmg.runner.dto.config.WinCombinationConfig;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class SameSymbolsCombination extends WinCombination {

    int count;

    public SameSymbolsCombination(String name, WinCombinationConfig config) {
        super(config);
        this.name = name;
        this.count = config.count;
    }

    @Override
    public Set<String> validate(String[][] symbols) {
        Map<String, Integer> counter = new HashMap<>();
        for (String[] symbol : symbols) {
            for (String s : symbol) {
                counter.put(s, counter.getOrDefault(s, 0) + 1);
            }
        }
        return counter.entrySet().stream().filter(x -> x.getValue() >= count).map(Map.Entry::getKey).collect(Collectors.toSet());
    }

    public Double reward(Double currentValue) {
        return currentValue * rewardMultiplier;
    }

}
