import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by hug.
 */
public class Experiments {
    public static void experiment1() {
        int expNum = 5000;
        BST<Integer> bst = new BST<>();
        Random r = new Random();
        List<Integer> xValues = new ArrayList<>();
        List<Double> adValues = new ArrayList<>();
        List<Double> oadValues = new ArrayList<>();

        for (int i = 0; i < expNum; i++) {
            bst.add(r.nextInt());
            xValues.add(i);
            adValues.add(bst.averageDepth());
            oadValues.add(ExperimentHelper.optimalAverageDepth(bst.size()));
        }

        XYChart chart = new XYChartBuilder().width(800).height(600).xAxisTitle("number of items").
                yAxisTitle("average depth").build();
        chart.addSeries("average depth", xValues, adValues);
        chart.addSeries("optimal average depth", xValues, oadValues);

        new SwingWrapper(chart).displayChart();
    }

    public static void experiment2() {
        int N = 1000;
        int maxNum = 1000000;
        BST bst = new BST();

        List<Integer> expNum = new ArrayList<>();
        List<Double> averageDepth = new ArrayList<>();

        for (int i = 0; i < N; i++) {
            ExperimentHelper.randomInsert(bst);
        }
        double startDepth = bst.averageDepth();
        expNum.add(N);
        averageDepth.add(startDepth);

        for (int i = 1; i <= maxNum; i++) {
            bst.deleteTakingSuccessor(bst.getRandomKey());
            ExperimentHelper.randomInsert(bst);
            expNum.add(N + i);
            averageDepth.add(bst.averageDepth());
        }

        XYChart chart = new XYChartBuilder().width(800).height(600).xAxisTitle("number of items").
                yAxisTitle("average depth").build();
        chart.addSeries("average depth", expNum, averageDepth);

        new SwingWrapper(chart).displayChart();
    }

    public static void experiment3() {
        int N = 1000;
        int maxNum = 1000000;
        BST bst = new BST();

        List<Integer> expNum = new ArrayList<>();
        List<Double> averageDepth = new ArrayList<>();

        for (int i = 0; i < N; i++) {
            ExperimentHelper.randomInsert(bst);
        }
        double startDepth = bst.averageDepth();
        expNum.add(N);
        averageDepth.add(startDepth);

        for (int i = 1; i <= maxNum; i++) {
            bst.deleteTakingRandom(bst.getRandomKey());
            ExperimentHelper.randomInsert(bst);
            expNum.add(N + i);
            averageDepth.add(bst.averageDepth());
        }

        XYChart chart = new XYChartBuilder().width(800).height(600).xAxisTitle("number of items").
                yAxisTitle("average depth").build();
        chart.addSeries("average depth", expNum, averageDepth);

        new SwingWrapper(chart).displayChart();
    }

    public static void main(String[] args) {
        experiment3();
    }
}
