package com.coindesk.api;

public class HistoryResult {
    private final Double minValue;
    private final Double maxValue;

    public HistoryResult(Double min, Double max)   // create constructore
    {
        this.minValue = min;
        this.maxValue = max;
    }

    public Double getMinValue() {
        return minValue;
    }

    public Double getMaxValue() {
        return maxValue;
    }
}
