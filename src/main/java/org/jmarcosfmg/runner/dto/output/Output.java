package org.jmarcosfmg.runner.dto.output;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.util.Map;
import java.util.Set;

@JsonNaming(com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy.class)
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class Output {
    public Output(String[][] matrix, Double reward, Map<String, Set<String>> appliedWinningCombinations, String appliedBonusSymbol) {
        this.matrix = matrix;
        this.reward = reward;
        this.appliedWinningCombinations = appliedWinningCombinations;
        this.appliedBonusSymbol = appliedBonusSymbol;
    }

    public String[][] matrix;

    public Double reward;

    public Map<String, Set<String>> appliedWinningCombinations;

    public String appliedBonusSymbol;

}
