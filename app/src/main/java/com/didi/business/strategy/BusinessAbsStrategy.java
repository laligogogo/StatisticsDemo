package com.didi.business.strategy;

/**
 * Created by huhuajun on 2015/10/16.
 */
public abstract class BusinessAbsStrategy implements IBusinessLogStrategy {

    protected int mStrategyType;

    public BusinessAbsStrategy(int strategyType) {
        mStrategyType = strategyType;

    }

}
