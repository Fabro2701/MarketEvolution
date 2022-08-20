package model.module.operator.crossover;

import java.util.Properties;
import java.util.Random;

import model.Constants;
import model.individual.Individual;
import model.individual.Population;
import model.module.operator.Operator;

public class OnePointCrossOverOperator extends Operator{
	float probability;
	int num;
	public OnePointCrossOverOperator(Properties properties, Random rnd) {
		super(properties, rnd);
	}

	@Override
	public void setProperties(Properties properties) {
		probability = Float.parseFloat(properties.getProperty(Constants.CROSSOVER_PROBABILITY, Constants.DEFAULT_CROSSOVER_PROBABILITY));
		num = Integer.parseInt(properties.getProperty(Constants.POPULATION_SIZE, Constants.DEFAULT_NUM_POPULATION_SIZE));
	}

	@Override
	public Population execute(Population population) {
		Population children = new Population();
		for(int i=0;i<num-population.size();i++) {
			//children.add()
		}
		population.addAll(children);
		
		return population;
	}

}
