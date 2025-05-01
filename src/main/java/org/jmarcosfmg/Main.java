package org.jmarcosfmg;

import org.jmarcosfmg.json.JsonParser;
import org.jmarcosfmg.runner.ScratchBuilder;
import org.jmarcosfmg.runner.ScratchGame;
import org.jmarcosfmg.runner.ScratchValidator;
import org.jmarcosfmg.runner.dto.config.Config;
import org.jmarcosfmg.runner.dto.output.Output;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

public class Main {

    public static void main(String[] args) throws IOException {

        JsonParser jsonParser = new JsonParser();
        Config gameConfig = jsonParser.readConfig("C:\\Projects\\scratch-game\\src\\main\\resources\\config.json");

        ScratchBuilder builder = new ScratchBuilder(gameConfig);

        ScratchGame game = builder.createGame();
        String[][] matrix = game.getSymbols();

        ScratchValidator validator = builder.createValidator();

        Map<String, Set<String>> appliedWinningCombinations = validator.validateWinningCombinations(matrix);
        String appliedBonusSymbol = validator.validateBonusSymbols(matrix);
        Double reward = validator.computeWinnings(100D, matrix);

        Output output = new Output(matrix, reward, appliedWinningCombinations, appliedBonusSymbol);
        jsonParser.printAsJson(output);
    }
}