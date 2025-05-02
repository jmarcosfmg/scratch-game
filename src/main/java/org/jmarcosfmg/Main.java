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
        String configFile = null;
        double bet = 0;

        for (int i = 0; i < args.length; i++) {
            switch (args[i]) {
                case "--config":
                    if (i + 1 < args.length) {
                        configFile = args[++i];
                    } else {
                        System.err.println("Missing value for --config");
                        return;
                    }
                    break;
                case "--betting-amount":
                    if (i + 1 < args.length) {
                        try {
                            bet = Double.parseDouble(args[++i]);
                        } catch (NumberFormatException e) {
                            System.err.println("Invalid number for --betting-amount");
                            return;
                        }
                    } else {
                        System.err.println("Missing value for --betting-amount");
                        return;
                    }
                    break;
                default:
                    System.err.println("Unknown argument: "+args[i]);
                    return;
            }
        }

        JsonParser jsonParser = new JsonParser();
        Config gameConfig = jsonParser.readConfig(configFile);
        Output output = getGameOutput(gameConfig, bet);

        jsonParser.printAsJson(output);
    }

    private static Output getGameOutput(Config gameConfig, double bettingAmount) {
        ScratchBuilder builder = new ScratchBuilder(gameConfig);

        ScratchGame game = builder.createGame();
        String[][] matrix = game.getSymbols();

        ScratchValidator validator = builder.createValidator();

        Map<String, Set<String>> appliedWinningCombinations = validator.validateWinningCombinations(matrix);
        String appliedBonusSymbol = validator.validateBonusSymbols(matrix);
        Double reward = validator.computeWinnings(bettingAmount, matrix);

        return new Output(matrix, reward, appliedWinningCombinations, appliedBonusSymbol);
    }
}