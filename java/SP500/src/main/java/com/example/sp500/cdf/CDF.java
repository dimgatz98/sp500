package com.example.sp500.cdf;

import com.example.sp500.pdf.PDF;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

public class CDF {
    Map<Float, Float> cdf = new HashMap<>();

    public CDF(PDF pdf) {
        Float sum = (float) 0;
        Map<Float, Float> m = pdf.getPdf();
        SortedSet<Float> keys = new TreeSet<>(m.keySet());
        DecimalFormat df = new DecimalFormat("#.###");
        df.setRoundingMode(RoundingMode.CEILING);
        for (Float key : keys) {
            sum += m.get(key);
            cdf.put(Float.parseFloat(df.format(key)), sum);
        }
    }

    public Map<Float, Float> getCdf() {
        return cdf;
    }
}
