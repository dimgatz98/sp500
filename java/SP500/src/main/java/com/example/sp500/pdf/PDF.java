package com.example.sp500.pdf;

import com.example.sp500.histogram.Histogram;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class PDF {
    Map<Float, Float> pdf = new HashMap<>();

    public PDF (Histogram h) {
        Float total = (float) 0;
        Set<Map.Entry<Float, Integer>> frequencies = h.getFreq().entrySet();
        for (Map.Entry<Float, Integer> entry : frequencies) {
            total += entry.getValue();
        }

        for (Map.Entry<Float, Integer> entry : frequencies) {
            pdf.put(entry.getKey(), entry.getValue() / total);
        }
    }

    public Map<Float, Float> getPdf() {
        return pdf;
    }
}
