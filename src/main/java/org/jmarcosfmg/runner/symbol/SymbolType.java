package org.jmarcosfmg.runner.symbol;

import java.util.function.BiFunction;

public enum SymbolType {

    MULTIPLY_REWARD((x, y) -> x * y),
    EXTRA_BONUS((x, y) -> x + y),
    MISS((x, _) -> x),
    STANDARD((x, _) -> x);

    private BiFunction<Double, Double, Double> function;

    SymbolType(BiFunction<Double, Double, Double> function){
        this.function = function;
    }

    public Double apply(Double currentValue, Double award) {
        return this.function.apply(currentValue, award);
    }
}
