package org.jmarcosfmg.runner.combination;

import org.jmarcosfmg.runner.symbol.Symbol;

import java.util.Collections;
import java.util.Set;

public class VerticallyLinearSymbols extends WinCombination {

    private final Double multiplier;

    public VerticallyLinearSymbols(Double multiplier) {
        this.multiplier = multiplier;
    }

    public Set<String> validate(String[][] symbols) {

        for (int c = 0; c < symbols.length; c++) {

            //for(int r = 0; r =  )


        }

        return Collections.emptySet();
    }


}
