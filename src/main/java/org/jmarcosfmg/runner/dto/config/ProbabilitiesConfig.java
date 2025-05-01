package org.jmarcosfmg.runner.dto.config;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class ProbabilitiesConfig {

    @JsonProperty("standard_symbols")
    public List<StandardSymbolProbabilityConfig> standardSymbols;

    @JsonProperty("bonus_symbols")
    public BonusSymbolsConfig bonusSymbolsConfig;
}
