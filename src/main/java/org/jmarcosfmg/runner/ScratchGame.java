package org.jmarcosfmg.runner;

import org.jmarcosfmg.runner.dto.config.Config;
import org.jmarcosfmg.runner.dto.config.StandardSymbolProbabilityConfig;

import java.util.Random;
import java.util.concurrent.atomic.AtomicReference;

public class ScratchGame {

    private final String[][] symbols;

    public ScratchGame(Config gameConfig) {
        Random random = new Random();
        String[][] symbols = new String[gameConfig.rows][gameConfig.columns];

        StandardSymbolProbabilityConfig defaultProbability = gameConfig.probabilitiesConfig.standardSymbols.getFirst();

        for(StandardSymbolProbabilityConfig standardSymbolProb : gameConfig.probabilitiesConfig.standardSymbols){
            if(standardSymbolProb.row == 0 && standardSymbolProb.column == 0){
                defaultProbability = standardSymbolProb;
            }
            symbols[standardSymbolProb.row][standardSymbolProb.column] = standardSymbolProb.random(random);
        }

        for (int i = 0; i < symbols.length; i++){
            for(int j = 0; j < symbols[0].length; j++){
                if(symbols[i][j] == null) symbols[i][j] = defaultProbability.random(random);
            }
        }

        symbols[random.nextInt(gameConfig.rows)][random.nextInt(gameConfig.columns)]
                = gameConfig.probabilitiesConfig.bonusSymbolsConfig.random(random);

        this.symbols = symbols;
    }

    public String[][] getSymbols() {
        return symbols;
    }
}
