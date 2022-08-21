package model.module.operator.collector;

import java.util.DoubleSummaryStatistics;
import java.util.Properties;
import java.util.Random;

import model.individual.Individual;
import model.individual.Population;
import model.module.operator.Operator;

public class FintessCollectorOperator extends Operator{

	public FintessCollectorOperator(Properties properties, Random rnd) {
		super(properties, rnd);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void setProperties(Properties properties) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Population execute(Population population) {
		DoubleSummaryStatistics stats = population.stream().mapToDouble(Individual::getFitness).summaryStatistics();
		Individual best = population.stream().max((e1,e2)->Float.compare(e1.getFitness(), e2.getFitness())).get();
		System.out.println(best.getPhenotype().getVisualCode());
		System.out.println("Best Individual: "+stats.getMax());
		System.out.println("Avg Individual: "+stats.getAverage());
		
		
		return population;
	}

}
