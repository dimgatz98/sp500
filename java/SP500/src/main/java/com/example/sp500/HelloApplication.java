package com.example.sp500;

import com.example.sp500.cdf.CDF;
import com.example.sp500.histogram.Histogram;
import com.example.sp500.pdf.PDF;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.image.WritableImage;
import javafx.stage.Stage;
import javafx.embed.swing.SwingFXUtils;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.*;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        String dataFile = getParameters().getRaw().get(0);
        String outDir = getParameters().getRaw().get(1);
        StatsMap m = new StatsMap(dataFile);
        List differences;
        for (int i = 1; i < (m.getStatsMap().get("SP500").size() - 1); i++) {
            differences = m.computeDifferences("SP500", i);

            Histogram h = new Histogram(differences, 100);
            PDF pdf = new PDF(h);
            CDF cdf = new CDF(pdf);

            stage.setTitle("Bar Chart Sample");
            final Axis xAxis = new CategoryAxis();
            final Axis yAxis = new NumberAxis();
            final BarChart<Float, Integer> bc =
                    new BarChart<Float, Integer> (xAxis, yAxis);
            bc.setTitle(i + "month(s)");
            xAxis.setLabel("Percent change");
            yAxis.setLabel("CDF");

            Map<Float, Float> cdfMap = cdf.getCdf();
            SortedSet<Float> keys = new TreeSet<>(cdfMap.keySet());
            XYChart.Series series = new XYChart.Series();
            for (Float key : keys) {
                series.getData().add(new XYChart.Data(Float.toString(key), cdfMap.get(key)));
            }

            Group root = new Group(bc);

            Scene scene  = new Scene(root,500,400);
            bc.getData().addAll(series);
            stage.setScene(scene);

            File dir = new File(outDir);
            dir.mkdirs();
            File file = new File(outDir + "/cdf/" + i + ".png");
            try {
                WritableImage wim = new WritableImage(550, 400);
                bc.snapshot(null, wim);
                ImageIO.write(SwingFXUtils.fromFXImage(wim, null), "png", file);
            } catch (Exception s) {
                System.out.println(s.getMessage());
            }
        }
        System.exit(0);
    }

    public static void main(String[] args) {
        launch(args);
    }
}