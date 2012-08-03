package com.exadel.dinnerorders.entity.tasks;

import com.exadel.dinnerorders.entity.Operation;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

public abstract class Task implements Callable<Boolean> {
    protected long period;
    protected TimeUnit units;
    protected Operation operation;
    public abstract boolean isTimeToServe();

    public void setUnits(TimeUnit units) {
        this.units = units;
    }

    public void setOperation(Operation operation) {
        this.operation = operation;
    }

    public void setPeriod(long period) {

        this.period = period;
    }

    public long getPeriod() {

        return period;
    }

    public TimeUnit getUnits() {
        return units;
    }

    public Operation getOperation() {
        return operation;
    }
}