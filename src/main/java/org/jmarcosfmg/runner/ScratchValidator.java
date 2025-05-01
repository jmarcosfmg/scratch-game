package org.jmarcosfmg.runner;

import org.jmarcosfmg.runner.combination.SameSymbolsCombination;
import org.jmarcosfmg.runner.combination.VerticallyLinearSymbols;
import org.jmarcosfmg.runner.combination.WinCombination;
import org.jmarcosfmg.runner.dto.config.Config;
import org.jmarcosfmg.runner.dto.config.SymbolConfig;
import org.jmarcosfmg.runner.dto.config.WinCombinationConfig;
import org.jmarcosfmg.runner.symbol.Symbol;
import org.jmarcosfmg.runner.symbol.SymbolType;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ScratchValidator {

    Map<String, WinCombination> possibleCombinations;
    Map<String, Symbol> symbolRewards;

    public ScratchValidator(Config config) {
        buildRewards(config.symbols);
        buildCombinations(config.winCombinations);
    }

    private void buildRewards(Map<String, SymbolConfig> symbolConfigs) {
        symbolRewards = new HashMap<>();
        symbolConfigs.forEach((key, value) -> {
            Symbol symbol = new Symbol(SymbolType.valueOf(((value.impact != null) ? value.impact : value.type).toUpperCase()),
                    value.getBonusAward());
            symbolRewards.put(key, symbol);
        });
    }

    private void buildCombinations(Map<String, WinCombinationConfig> winCombinations) {
        possibleCombinations = new HashMap<>();
        for (Map.Entry<String, WinCombinationConfig> combination : winCombinations.entrySet()) {
            WinCombination c = switch (combination.getValue().group) {
                case ("same_symbols") ->
                        new SameSymbolsCombination(combination.getKey(), combination.getValue().count, combination.getValue().rewardMultiplier);
                default -> new VerticallyLinearSymbols(combination.getValue().rewardMultiplier);
            };
            possibleCombinations.put(combination.getKey(), c);
        }
    }

    public Double computeWinnings(Double bet, String[][] symbols) {

        double winnings = 0D;

        Map<String, Set<String>> matchedCombinations = validateWinningCombinations(symbols);

        Function<Set<String>, Double> combinationsSum = (combinations) -> combinations.stream()
                .map(c -> possibleCombinations.get(c).getRewardMultiplier())
                .reduce(1D, (a, b) -> a * b);

        for (Map.Entry<String, Set<String>> c : matchedCombinations.entrySet()) {
            winnings += bet * symbolRewards.get(c.getKey()).getRewardMultiplier() * combinationsSum.apply(c.getValue());
        }

        for (String[] rows : symbols) {
            for (String symbol : rows) {
                if (symbolRewards.get(symbol).getType() != SymbolType.STANDARD) {
                    return symbolRewards.get(symbol).apply(winnings);
                }
            }
        }

        return winnings;
    }

    public Map<String, Set<String>> validateWinningCombinations(String[][] symbols) {
        Map<String, Map<String, WinCombination>> symbolWinningCombinations = new HashMap<>();

        possibleCombinations.forEach((_, validator) -> {
            Set<String> matchedSymbols = validator.validate(symbols);

            for (String symbol : matchedSymbols) {
                symbolWinningCombinations
                        .computeIfAbsent(symbol, _ -> new HashMap<>())
                        .merge(
                                validator.getType(),
                                validator,
                                (existing, incoming) -> incoming.getRewardMultiplier() > existing.getRewardMultiplier() ? incoming : existing
                        );
            }
        });

        Map<String, Set<String>> winningCombinations = new HashMap<>();
        for (Map.Entry<String, Map<String, WinCombination>> entry : symbolWinningCombinations.entrySet()) {
            String symbol = entry.getKey();
            Set<String> winningCombinationNames = entry.getValue().values().stream()
                    .map(x -> x.name)
                    .collect(Collectors.toSet());

            winningCombinations.put(symbol, winningCombinationNames);
        }

        return winningCombinations;
    }

    public String validateBonusSymbols(String[][] symbols) {
        for (String[] rows : symbols) {
            for (String symbol : rows) {
                if (symbolRewards.get(symbol).getType() != SymbolType.STANDARD) {
                    return symbol;
                }
            }
        }
        return null;
    }
}
