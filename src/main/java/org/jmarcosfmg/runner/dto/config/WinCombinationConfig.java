package org.jmarcosfmg.runner.dto.config;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class WinCombinationConfig {
    @JsonProperty("reward_multiplier")
    public Double rewardMultiplier;
    public String when;
    public Integer count; // nullable
    public String group;

    @JsonProperty("covered_areas")
    public List<List<String>> coveredAreas; // nullable
}
