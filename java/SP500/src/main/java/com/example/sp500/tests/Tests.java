package com.example.sp500.tests;

import com.example.sp500.StatsMap;
import com.example.sp500.cdf.CDF;
import com.example.sp500.histogram.Histogram;
import com.example.sp500.pdf.PDF;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Tests {
    @Test
    public void TestHistogramPdfCdf() {
        List<Float> data = Arrays.asList((float) 2.0,(float)  3.8, (float)  4.3, (float) 5.0);
        List<Float> expectedKeys = Arrays.asList((float) 2.5, (float) 3.5, (float) 4.5);
        List<Integer> expectedHistogram = Arrays.asList(1, 1 , 2);
        List<Float> expectedPdf = Arrays.asList((float) 0.25, (float) 0.25, (float) 0.5);
        List<Float> expectedCdf = Arrays.asList((float) 0.25, (float) 0.5, (float) 1);

        Histogram h = new Histogram(data, 3);
        PDF pdf = new PDF(h);
        CDF cdf = new CDF(pdf);

        Map m1 = h.getFreq();
        Map m2 = pdf.getPdf();
        Map m3 = cdf.getCdf();

        for (int i = 0 ; i < expectedKeys.size() ; i++) {
            Assert.assertTrue(m1.containsKey(expectedKeys.get(i)));
            Assert.assertTrue(m2.containsKey(expectedKeys.get(i)));
            Assert.assertTrue(m3.containsKey(expectedKeys.get(i)));
            Assert.assertEquals(m1.get(expectedKeys.get(i)), expectedHistogram.get(i));
            Assert.assertEquals(m2.get(expectedKeys.get(i)), expectedPdf.get(i));
            Assert.assertEquals(m3.get(expectedKeys.get(i)), expectedCdf.get(i));
        }

        System.out.println(h.getFreq());
        System.out.println(pdf.getPdf());
        System.out.println(cdf.getCdf());
    }

    @Test
    public void TestStatsMapDifferences() {
        List<Float> expectedDifferencesFirstColumn = Arrays.asList((float) 1, (float) 1);
        List<Float> expectedDifferencesSecondColumn = Arrays.asList((float) 0.5, (float) 2/3);
        StatsMap m = new StatsMap(getClass().getResource("/testData/statsMap.csv").getPath());
        List differences = m.computeDifferences("firstColumn", 1);
        for (int i = 0 ; i < differences.size() ; i++) {
           Assert.assertEquals(differences.get(i), expectedDifferencesFirstColumn.get(i));
        }
        differences = m.computeDifferences("secondColumn", 1);
        for (int i = 0 ; i < differences.size() ; i++) {
            Assert.assertEquals(differences.get(i), expectedDifferencesSecondColumn.get(i));
        }
    }
}
