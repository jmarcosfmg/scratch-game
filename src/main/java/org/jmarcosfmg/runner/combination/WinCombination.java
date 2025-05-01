package org.jmarcosfmg.runner.combination;

import java.util.Set;

public abstract class WinCombination {

    protected Double rewardMultiplier;

    protected String type;

    public String name;

    public abstract Set<String> validate(String[][] symbols);

    public Double getRewardMultiplier() {
        return rewardMultiplier;
    }

    public String getType() {
        return type;
    }
}
