package org.jmarcosfmg.runner;

import org.jmarcosfmg.runner.dto.config.Config;
import org.jmarcosfmg.runner.dto.config.StandardSymbolProbabilityConfig;

public class ScratchBuilder {

    private final Config gameConfig;

    public ScratchBuilder(Config config) {
        this.gameConfig = config;
        fillGridSize();
    }

    private void fillGridSize() {
        if (gameConfig == null) return;
        if (gameConfig.columns != null && gameConfig.rows != null) return;

        int row = 0;
        int column = 0;

        for (StandardSymbolProbabilityConfig probabilityConfig : gameConfig.probabilitiesConfig.standardSymbols) {
            if (probabilityConfig.row > row) row = probabilityConfig.row;
            if (probabilityConfig.column > column) column = probabilityConfig.column;
        }

        gameConfig.rows = row;
        gameConfig.columns = column;
    }

    public ScratchGame createGame() {
        return new ScratchGame(gameConfig);
    }

    public ScratchValidator createValidator() {
        return new ScratchValidator(gameConfig);
    }

}
