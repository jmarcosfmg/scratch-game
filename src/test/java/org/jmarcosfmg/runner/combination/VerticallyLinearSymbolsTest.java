package org.jmarcosfmg.runner.combination;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class VerticallyLinearSymbolsTest {

    private VerticallyLinearSymbols verticallyLinearSymbols;

    @Test
    public void shouldReturnNoValidSymbols() {
        String[][] scratchGameSymbols = new String[][]{
                {"A", "B", "C"},
                {"D", "E", "F"},
                {"G", "H", "I"}
        };

        List<List<String>> coveredAreas = new ArrayList<>();
        coveredAreas.add(List.of(new String[]{"0:0", "1:0", "2:0"}));
        coveredAreas.add(List.of(new String[]{"0:1", "1:2", "2:1"}));
        coveredAreas.add(List.of(new String[]{"0:2", "1:2", "2:2"}));

        verticallyLinearSymbols = new VerticallyLinearSymbols(2D, coveredAreas);

        assertTrue(verticallyLinearSymbols.validate(scratchGameSymbols).isEmpty());
    }

    @Test
    public void shouldReturnValidSymbolsInsideOfCoveredArea() {
        String[][] scratchGameSymbols = new String[][]{
                {"A", "B", "D"},
                {"A", "B", "C"},
                {"A", "H", "C"}
        };

        List<List<String>> coveredAreas = new ArrayList<>();
        coveredAreas.add(List.of(new String[]{"0:0", "1:0", "2:0"}));
        coveredAreas.add(List.of(new String[]{"0:1", "1:1", "2:1"}));
        coveredAreas.add(List.of(new String[]{"0:2", "", "2:2"}));

        verticallyLinearSymbols = new VerticallyLinearSymbols(2D, coveredAreas);
        Set<String> validSymbols = verticallyLinearSymbols.validate(scratchGameSymbols);


        assertTrue(validSymbols.contains("A"));
        assertTrue(validSymbols.contains("B"));
        assertFalse(validSymbols.contains("C"));
    }

}