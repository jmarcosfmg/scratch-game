package org.jmarcosfmg.runner.symbol;

public class Symbol {

    private final Double rewardMultiplier;

    private final SymbolType type;

    public Symbol(SymbolType type, Double multiplier) {
        this.rewardMultiplier = multiplier;
        this.type = type;
    }

    public Double getRewardMultiplier() {
        return rewardMultiplier;
    }


    public SymbolType getType() {
        return type;
    }

    public Double apply(Double currentValue) {
        return type.apply(currentValue, rewardMultiplier);
    }
}
