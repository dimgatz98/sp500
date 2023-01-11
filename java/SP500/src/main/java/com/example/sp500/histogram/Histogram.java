package com.example.sp500.histogram;

import java.util.*;

public class Histogram {
    private Map<Float, Integer> freq = new HashMap<>();

    private double max;

    private Float getMaxMin(List<Float> l, boolean max) {
        Float res = null;
        int count = 0;
        for (Float f : l) {
            if (count == 0) {
                res = f;
                count++;
                continue;
            }
            if (max) {
                if (f > res) {
                    res = f;
                }
            } else {
                if (f < res) {
                    res = f;
                }
            }

        }
        return res;
    }

    public Histogram(List<Float> l, int bins) {
        Float max = getMaxMin(l, true);
        Float min = getMaxMin(l, false);
        Float step = (max - min) / bins;
        Float bin;
        for (Object DataPoint : l) {
            if (DataPoint == max) {
                bin = max - step / 2;
            } else {
                bin = min + (int) (((Float) DataPoint - min) / step) * step + step / 2;
            }
            this.addDataPoint(bin);
        }
    }

    public void addDataPoint(Float i) {
        if (freq.containsKey(i))
            freq.put(i, freq.get(i) + 1);
        else {
            freq.put(i, 1);
        }
        if (freq.get(i) > max) max = freq.get(i);
    }

    public Map<Float, Integer> getFreq() {
        return freq;
    }
}
