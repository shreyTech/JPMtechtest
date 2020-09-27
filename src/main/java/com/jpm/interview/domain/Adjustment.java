package com.jpm.interview.domain;

public class Adjustment {

    public enum  Operation{
        ADD,
        SUBTRACT,
        MULTIPLY;
    }

    private Operation operation;

    private Integer value;

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public Operation getOperation() {
        return operation;
    }

    public void setOperation(Operation operation) {
        this.operation = operation;
    }
}
