package org.jmarcosfmg.runner.dto.config;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

public class Config {
    public Integer columns; // optional
    public Integer rows; // optional
    public Map<String, SymbolConfig> symbols;

    @JsonProperty("probabilities")
    public ProbabilitiesConfig probabilitiesConfig;
    @JsonProperty("win_combinations")
    public Map<String, WinCombinationConfig> winCombinations;
}

