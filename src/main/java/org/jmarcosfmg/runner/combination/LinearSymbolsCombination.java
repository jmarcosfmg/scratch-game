package org.jmarcosfmg.runner.combination;

import org.jmarcosfmg.runner.dto.config.WinCombinationConfig;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class LinearSymbolsCombination extends WinCombination {

    private final List<List<String>> coveredAreas;

    public LinearSymbolsCombination(String name, WinCombinationConfig config) {
        super(config);
        this.name = name;
        this.coveredAreas = config.coveredAreas;
    }

    public Set<String> validate(String[][] symbols) {
        Set<String> resultSet = new HashSet<>();

        if (symbols == null || coveredAreas.isEmpty() || coveredAreas.getFirst().isEmpty()) return resultSet;

        for (List<String> r : coveredAreas) {
            String expectedSymbol = null;
            boolean isAllEqual = true;

            for (String coveredArea : r) {
                String[] parts = coveredArea.split(":");
                if(parts.length < 2) continue;

                int row = Integer.parseInt(parts[0]);
                int col = Integer.parseInt(parts[1]);
                String symbol = symbols[row][col];

                if (expectedSymbol == null) {
                    expectedSymbol = symbol;
                } else if (!expectedSymbol.equals(symbol)) {
                    isAllEqual = false;
                }
            }
            if (isAllEqual) resultSet.add(expectedSymbol);
        }

        return resultSet;
    }


}
