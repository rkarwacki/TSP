package pl.radoslawkarwacki.tsp.chart;

import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class ChartDataSet {

    private XYSeriesCollection dataset;

    public XYSeriesCollection getDataset() {
        return dataset;
    }

    public ChartDataSet() {
        dataset = new XYSeriesCollection();
    }

    public void addSeriesToCollection(XYSeries series){
        dataset.addSeries(series);
    }
}
