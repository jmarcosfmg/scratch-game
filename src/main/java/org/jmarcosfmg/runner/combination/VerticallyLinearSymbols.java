package org.jmarcosfmg.runner.combination;

import org.jmarcosfmg.runner.dto.config.WinCombinationConfig;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class VerticallyLinearSymbols extends WinCombination {

    private final List<List<String>> coveredAreas;

    public VerticallyLinearSymbols(String name, WinCombinationConfig config) {
        super(config);
        this.name = name;
        this.coveredAreas = config.coveredAreas;
    }

    public Set<String> validate(String[][] symbols) {
        Set<String> resultSet = new HashSet<>();

        if (symbols == null || coveredAreas.isEmpty() || coveredAreas.getFirst().isEmpty()) return resultSet;

        for (int c = 0; c < coveredAreas.getFirst().size(); c++) {
            String expectedSymbol = null;
            boolean isAllEqual = true;

            for (List<String> coveredArea : coveredAreas) {
                String[] parts = coveredArea.get(c).split(":");
                if(parts.length < 2) continue;

                int col = Integer.parseInt(parts[0]);
                int row = Integer.parseInt(parts[1]);
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
