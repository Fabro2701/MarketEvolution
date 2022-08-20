package model.module.operator.fitness;

import java.util.Properties;
import java.util.Random;

import model.Constants;
import model.individual.Individual;
import model.individual.Population;
import model.module.operator.Operator;

public abstract class FitnessEvaluationOperator extends Operator{
	protected float defaultFitness;
	public FitnessEvaluationOperator(Properties properties, Random rnd) {
		super(properties, rnd);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void setProperties(Properties properties) {
		defaultFitness = Float.parseFloat(properties.getProperty(Constants.DEFAULT_FITNESS, Constants.DEFAULT_FITNESS_VALUE));
	}
	@Override
	public Population execute(Population population) {
		for(Individual ind:population) {
			if(ind.isValid()) {
				ind.setFitness(evaluate(ind));
			}
			else {
				ind.setFitness(this.defaultFitness);
			}
			ind.setEvaluated(true);
		}
		return population;
	}

	protected abstract float evaluate(Individual ind);

	

}
