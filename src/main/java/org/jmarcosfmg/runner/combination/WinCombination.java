package org.jmarcosfmg.runner.combination;

import org.jmarcosfmg.runner.dto.config.WinCombinationConfig;

import java.util.Set;

public abstract class WinCombination {

    protected Double rewardMultiplier;

    protected String group;

    protected String name;

    WinCombination(WinCombinationConfig config){
        this.rewardMultiplier = config.rewardMultiplier;
        this.group = config.group;
    }

    public abstract Set<String> validate(String[][] symbols);

    public Double getRewardMultiplier() {
        return rewardMultiplier;
    }

    public String getGroup() {
        return group;
    }


    public String getName() {
        return name;
    }

}
