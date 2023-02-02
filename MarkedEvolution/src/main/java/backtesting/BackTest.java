package backtesting;

import java.awt.GridLayout;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;

import backtesting.data.CandleData;
import backtesting.data.DataSeries;
import backtesting.data.transformer.AdditionTransformer;
import backtesting.data.transformer.NormalizationTransformer;
import backtesting.data.transformer.Transformer;
import backtesting.order.OrdersManager;
import backtesting.stats.BackTestStats;
import backtesting.strategy.Strategy;
import backtesting.strategy.indicator.MAIndicator;
import backtesting.strategy.indicator.SDIndicator;

public class BackTest {  
	DataSeries data; 
	public BackTest(String filename) {
		loadData(filename);
		
		int period=200;
		MAIndicator mat = new MAIndicator(period);
		Transformer mattrans = new AdditionTransformer("ma",period,mat);
		mattrans.apply(data);
		
		SDIndicator sd = new SDIndicator(period);
		Transformer sdtrans = new AdditionTransformer("sd",period,sd);
		sdtrans.apply(data);
		
		Transformer norm = new NormalizationTransformer(period);
		norm.apply(data);
		
		
		for(int p:new int[] {25,50,200}) {
			MAIndicator ma = new MAIndicator(p);
			Transformer matrans = new AdditionTransformer("ma",p,ma);
			matrans.apply(data);
		}
		
	}
	public void loadData(String filename) {
		int l=1000;
		data = new DataSeries(l);
		File file = new File("resources/data/"+filename+".csv");
		String[] values;
		try (BufferedReader br = new BufferedReader(new FileReader(file))) {
		    String line;
		    br.readLine();
		    while ((line = br.readLine()) != null) {
		    	values = line.split(",");
		    	try{
		    		CandleData cd = new CandleData(Float.parseFloat(values[1]), Float.parseFloat(values[2]), Float.parseFloat(values[3]), Float.parseFloat(values[4]));
		    		data.add(cd);
		    	}
		    	catch (Exception e) {
		    	}
		    	
		    }
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		for(CandleData d:data) {
//			System.out.println(d);
//		}
		System.out.println(data.size());
		
	} 
	public BackTestStats runTest(Strategy strategy) {
		BackTestStats stats = new BackTestStats();
		
		HashMap<String,String> obs = new HashMap<String,String>();
		OrdersManager ordersManager = new OrdersManager();
		
		CandleData cd = null;
		for(int i=200;i<data.size();i++) {
			setObservations(obs, data, i, ordersManager);
			cd = data.get(i);
			ordersManager.update(cd,i);
			strategy.update(data, i, ordersManager, obs);
			obs.clear();
		}
		ordersManager.close(cd);
		stats.onClose(ordersManager);
		return stats; 
	}
	public boolean runStep(Strategy strategy, int i, OrdersManager ordersManager) {
		if(i>=data.size())return false;
		HashMap<String,String> obs = new HashMap<String,String>();
		setObservations(obs, data, i, ordersManager);
		CandleData cd = data.get(i);
		ordersManager.update(cd,i);
		strategy.update(data,i,ordersManager, obs);
		obs.clear();
		return true;
	}
	private void setObservations(HashMap<String,String> obs, DataSeries data, int idx, OrdersManager ordersManager) {
		int range=10;
		for(String key:data.getIndicators().keySet()) {
			ArrayList<Float> indicator = data.getIndicator(key);
			for(int i=0;i<range;i++) {
				float value = indicator.get(idx-i);
				obs.put(key+"["+i+"]", String.valueOf(value));
			}
		}
		for(int i=0;i<range;i++) {
			obs.put("close["+i+"]", String.valueOf(data.getClose(idx-i)));
			obs.put("open["+i+"]", String.valueOf(data.getOpen(idx-i)));
			obs.put("high["+i+"]", String.valueOf(data.getHigh(idx-i)));
			obs.put("low["+i+"]", String.valueOf(data.getLow(idx-i)));
		}
	}
	public DataSeries getData() {
		return data;
	}
	public static void main(String args[]) {
		BackTest t = new BackTest("EURUSD=X");
		DataSeries data = t.data;
		
		int period=200;
		
		DefaultCategoryDataset dataset1 = new DefaultCategoryDataset();
		JFreeChart chart1 = ChartFactory.createLineChart(  
		        "1", 
		        "t", 
		        "p",  
		        dataset1  
		        ); 
		ChartPanel chartp1 = new ChartPanel(chart1);
		
		
		
		MAIndicator ma = new MAIndicator(period);
		Transformer matrans = new AdditionTransformer("ma",period,ma);
		matrans.apply(t.data);
		

		SDIndicator sd = new SDIndicator(period);
		Transformer sdtrans = new AdditionTransformer("sd",period,sd);
		sdtrans.apply(t.data);
		
		
		for(int i=period;i<data.size();i++) {
			dataset1.addValue(data.getIndicator("ma"+period).get(i), "avg", Integer.valueOf(i));
			dataset1.addValue(data.getClose(i), "close", Integer.valueOf(i));
			dataset1.addValue(data.getOpen(i), "open", Integer.valueOf(i));
			dataset1.addValue(data.getHigh(i), "high", Integer.valueOf(i));
			//dataset1.addValue(data.getLow(i), "low", Integer.valueOf(i));
		}
		
		Transformer norm = new NormalizationTransformer(period);
		norm.apply(t.data);
		
		t.data.stream().forEach(d->System.out.println(d));
		//sd.calculateData(t.data).stream().forEach(e->System.out.println(e));
		
		matrans.apply(t.data);
		
		DefaultCategoryDataset dataset2 = new DefaultCategoryDataset();
		JFreeChart chart2 = ChartFactory.createLineChart(  
		        "1", 
		        "t", 
		        "p",  
		        dataset2  
		        ); 

		ChartPanel chartp2 = new ChartPanel(chart2);
		for(int i=period;i<data.size();i++) {
			dataset2.addValue(data.getIndicator("ma"+period).get(i), "avg", Integer.valueOf(i));
			dataset2.addValue(data.getClose(i), "close", Integer.valueOf(i));
			dataset2.addValue(data.getOpen(i), "open", Integer.valueOf(i));
			dataset2.addValue(data.getHigh(i), "high", Integer.valueOf(i));
			//dataset2.addValue(data.getLow(i), "low", Integer.valueOf(i));
		}
		
		JFrame fram1 = new JFrame();
		JPanel cpanel = new JPanel(new GridLayout(2,1));
		fram1.setContentPane(cpanel);
		cpanel.add(chartp1);
		cpanel.add(chartp2);
		fram1.pack();
		fram1.setVisible(true);
		fram1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
}
