package org.jmarcosfmg.runner.dto.config;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SymbolConfig {

    @JsonProperty("reward_multiplier")
    public Double rewardMultiplier; // nullable
    public String type;
    public String impact; // nullable
    public Integer extra; // nullable

    public Double getBonusAward(){
        if(rewardMultiplier != null) return rewardMultiplier;
        if(extra != null) return extra*1D;
        return null;
    }
}
