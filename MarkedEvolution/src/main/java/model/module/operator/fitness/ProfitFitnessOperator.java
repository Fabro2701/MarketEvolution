package model.module.operator.fitness;

import java.util.Properties;
import java.util.Random;

import model.individual.Individual;
import model.individual.Population;

public class ProfitFitnessOperator extends FitnessEvaluationOperator{
	public ProfitFitnessOperator(Properties properties, Random rnd) {
		super(properties, rnd);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void setProperties(Properties properties) {
		super.setProperties(properties);
	}

	@Override
	protected float evaluate(Individual ind) {
		float fitness=0.f;
		return fitness;
	}
}
