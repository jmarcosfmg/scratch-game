package org.jmarcosfmg.runner;

import org.jmarcosfmg.runner.dto.config.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

class ScratchValidatorTest {

    private ScratchValidator scratchValidator;
    private Config config = buildConfig();

    @Test
    public void shouldReturnOnlyOneTypeOfWinCombinationPerType(){
        String[][] scratchGameSymbols = new String[][]{
                {"A", "B", "B"},
                {"B", "A", "B"},
                {"B", "B", "MISS"}
        };
        scratchValidator = new ScratchValidator(config);

        Map<String, Set<String>> winningCombinations = scratchValidator.validateWinningCombinations(scratchGameSymbols);

        Assertions.assertEquals(1, winningCombinations.get("B").size());
        Assertions.assertFalse(winningCombinations.containsKey("A"));
    }

    @Test
    public void shouldReturnMultipleWinCombinations(){
        String[][] scratchGameSymbols = new String[][]{
                {"A", "B", "B"},
                {"B", "A", "B"},
                {"B", "B", "B"}
        };
        scratchValidator = new ScratchValidator(config);

        Map<String, Set<String>> winningCombinations = scratchValidator.validateWinningCombinations(scratchGameSymbols);

        Assertions.assertFalse(winningCombinations.containsKey("A"));
        Assertions.assertTrue(winningCombinations.containsKey("B"));

        Set<String> matchedWithB = winningCombinations.get("B");
        Assertions.assertEquals(2, matchedWithB.size());
        Assertions.assertTrue(matchedWithB.contains("same_symbols_horizontally"));
        Assertions.assertTrue(matchedWithB.contains("same_symbol_4_times"));

    }

    @Test
    public void shouldComputeWinnings(){
        String[][] scratchGameSymbols = new String[][]{
                {"A", "B", "B"},
                {"B", "A", "B"},
                {"B", "B", "B"}
        };
        scratchValidator = new ScratchValidator(config);

        Map<String, Set<String>> winningCombinations = scratchValidator.validateWinningCombinations(scratchGameSymbols);

        Assertions.assertEquals(2, winningCombinations.get("B").size());
        Assertions.assertFalse(winningCombinations.containsKey("A"));
    }


    private Config buildConfig(){
        Config config = new Config();
        config.columns = 3;
        config.rows = 3;

        config.probabilitiesConfig = buildProbabilitiesConfig();
        config.winCombinations = buildWinCombinations();
        config.symbols = buildSymbolConfig();
        return config;
    }

    private ProbabilitiesConfig buildProbabilitiesConfig() {
        Map<String, Integer> standardSymbols = Map.of("A", 1, "B", 2);
        ProbabilitiesConfig probabilitiesConfig = new ProbabilitiesConfig();

        probabilitiesConfig.standardSymbols = List.of(
                new StandardSymbolProbabilityConfig(0, 0, standardSymbols),
                new StandardSymbolProbabilityConfig(0, 1, standardSymbols),
                new StandardSymbolProbabilityConfig(0, 2, standardSymbols),
                new StandardSymbolProbabilityConfig(1, 0, standardSymbols),
                new StandardSymbolProbabilityConfig(1, 1, standardSymbols),
                new StandardSymbolProbabilityConfig(1, 2, standardSymbols),
                new StandardSymbolProbabilityConfig(2, 0, standardSymbols),
                new StandardSymbolProbabilityConfig(2, 1, standardSymbols),
                new StandardSymbolProbabilityConfig(2, 2, standardSymbols)
        );
        Map<String, Integer> bonusSymbols = Map.of("MISS", 3, "+50", 3);
        probabilitiesConfig.bonusSymbolsConfig = new BonusSymbolsConfig();
        probabilitiesConfig.bonusSymbolsConfig.symbols = bonusSymbols;

        return probabilitiesConfig;
    }

    private Map<String, WinCombinationConfig> buildWinCombinations() {
        WinCombinationConfig sameSymbol3 = new WinCombinationConfig();
        sameSymbol3.rewardMultiplier = 1.5;
        sameSymbol3.count = 3;
        sameSymbol3.group = "same_symbols";
        sameSymbol3.when = "same_symbols";

        WinCombinationConfig sameSymbol4 = new WinCombinationConfig();
        sameSymbol4.rewardMultiplier = 2.0;
        sameSymbol4.count = 4;
        sameSymbol4.when = "same_symbols";
        sameSymbol4.group = "same_symbols";


        WinCombinationConfig horizontalCombination = new WinCombinationConfig();
        List<List<String>> coveredAreas = new ArrayList<>();
        coveredAreas.add(List.of(new String[]{"0:0", "0:1", "0:2"}));
        coveredAreas.add(List.of(new String[]{"1:0", "1:1", "1:2"}));
        coveredAreas.add(List.of(new String[]{"2:0", "2:1", "2:2"}));
        horizontalCombination.rewardMultiplier = 1.1;
        horizontalCombination.group = "horizontally_linear_symbols";
        horizontalCombination.when = "linear_symbols";
        horizontalCombination.coveredAreas = coveredAreas;

        return Map.of(
                "same_symbol_3_times", sameSymbol3, 
                "same_symbol_4_times", sameSymbol4,
                "same_symbols_horizontally", horizontalCombination
        );
    }

    private static Map<String, SymbolConfig> buildSymbolConfig() {
        Map<String, SymbolConfig> symbolConfigMap;

        SymbolConfig a = new SymbolConfig();
        a.rewardMultiplier = 5.;
        a.type = "standard";

        SymbolConfig b = new SymbolConfig();
        b.rewardMultiplier = 3.;
        b.type = "standard";

        SymbolConfig miss = new SymbolConfig();
        miss.impact = "miss";
        miss.type = "bonus";

        SymbolConfig fifty = new SymbolConfig();
        fifty.impact = "extra_bonus";
        fifty.type = "bonus";
        fifty.extra = 500;

        symbolConfigMap = Map.of(
                "A", a,
                "B", b,
                "MISS", miss,
                "+50", fifty
        );
        
        return symbolConfigMap;
    }

}