package com.example.sp500;

import com.opencsv.CSVReader;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.Float.parseFloat;

public class StatsMap {
    Map<String, List> statsMap = new HashMap<String, List>();
    public StatsMap(String filename) {
        CSVReader reader = null;
        try {
            reader = new CSVReader(new FileReader(filename));
            String[] nextLine;
            String[] keys = null;
            Integer count = 0;
            while ((nextLine = reader.readNext()) != null) {
                if (count == 0) {
                    count++;
                    keys = nextLine;
                    for (String key : keys) {
                        statsMap.put(key, new ArrayList<>());
                    }
                    continue;
                }
                for (int i = 0 ; i < nextLine.length ; i++) {
                    if (nextLine[i].isBlank()) {
                        nextLine[i] = "0";
                    }

                    try {
                        statsMap.get(keys[i]).add(parseFloat(nextLine[i]));
                    } catch (Exception e) {
                        statsMap.get(keys[i]).add(nextLine[i]);
                    }
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Map<String, List> getStatsMap() {
        return statsMap;
    }

    public List<Float> computeDifferences(String field, Integer dist) {
        List<Float> res = new ArrayList<>();
        List<Float> tmp = this.getStatsMap().get(field);
        for (int i = 0 ; i < tmp.size() - dist ; i++) {
            res.add((tmp.get(i + dist) - tmp.get(i)) / tmp.get(i));
        }
        return res;
    }
}
