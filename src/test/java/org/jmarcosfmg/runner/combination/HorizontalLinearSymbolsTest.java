package org.jmarcosfmg.runner.combination;

import org.jmarcosfmg.runner.dto.config.WinCombinationConfig;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class HorizontalLinearSymbolsTest {


    private HorizontalLinearSymbols horizontalLinearSymbols;
    private WinCombinationConfig config = buildCombinationConfig();

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
        this.config.coveredAreas = coveredAreas;

        horizontalLinearSymbols = new HorizontalLinearSymbols("horizontal", config);

        assertTrue(horizontalLinearSymbols.validate(scratchGameSymbols).isEmpty());
    }

    @Test
    public void shouldReturnValidSymbolsInsideOfCoveredArea() {
        String[][] scratchGameSymbols = new String[][]{
                {"A", "A", "A"},
                {"C", "B", "B"},
                {"C", "A", "C"}
        };

        List<List<String>> coveredAreas = new ArrayList<>();
        coveredAreas.add(List.of(new String[]{"0:0", "1:0", "2:0"}));
        coveredAreas.add(List.of(new String[]{"", "1:1", "2:1"}));
        coveredAreas.add(List.of(new String[]{"0:2", "1:2", "2:2"}));
        this.config.coveredAreas = coveredAreas;

        horizontalLinearSymbols = new HorizontalLinearSymbols("horizontal", config);
        Set<String> validSymbols = horizontalLinearSymbols.validate(scratchGameSymbols);

        assertTrue(validSymbols.contains("A"));
        assertTrue(validSymbols.contains("B"));
        assertFalse(validSymbols.contains("C"));
    }

    private WinCombinationConfig buildCombinationConfig() {
        WinCombinationConfig config = new WinCombinationConfig();
        config.rewardMultiplier = 1.5;
        return config;
    }

}