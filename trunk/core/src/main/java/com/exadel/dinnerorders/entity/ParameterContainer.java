package com.exadel.dinnerorders.entity;

import com.exadel.dinnerorders.entity.Operation;

import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class ParameterContainer {
    private Operation operation;
    private String tableName;
    private long period;
    private long returnTime;
    private TimeUnit unit;

    public ParameterContainer(Scanner scanner) {
        operation = Operation.getOperationByCode(scanner.next().charAt(0));
        tableName = scanner.next();
        period = scanner.nextLong();
        returnTime = scanner.nextLong();
        unit = getTimeUnit(scanner.next());
    }

    private TimeUnit getTimeUnit(String next) {
        switch (next.charAt(0)){
            case 'D':
                return TimeUnit.DAYS;
            case 'H':
                return TimeUnit.HOURS;
            case 'M':
                return TimeUnit.MINUTES;
            case 'S':
                return TimeUnit.SECONDS;
            case 'm':
                return TimeUnit.MILLISECONDS;
            case 'N':
                return TimeUnit.NANOSECONDS;
            default:
                return TimeUnit.MILLISECONDS;
        }
    }

    public Operation getOperation() {
        return operation;
    }

    public void setOperation(Operation operation) {
        this.operation = operation;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public void setPeriod(long period) {
        this.period = period;
    }

    public void setReturnTime(long returnTime) {
        this.returnTime = returnTime;
    }

    public void setUnit(TimeUnit unit) {
        this.unit = unit;
    }

    public String getTableName() {

        return tableName;
    }

    public long getPeriod() {
        return period;
    }

    public long getReturnTime() {
        return returnTime;
    }

    public TimeUnit getUnit() {
        return unit;
    }
}
