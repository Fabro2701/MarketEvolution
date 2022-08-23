package model.module;

import java.util.Properties;
import java.util.Random;

import model.Constants;
import model.individual.Individual;
import model.individual.Population;
import model.module.operator.Operator;
import model.module.operator.fitness.FitnessEvaluationOperator;

public class FitnessModule extends Module{
	float defaultFitness;
	FitnessEvaluationOperator operator;	
	public FitnessModule(Population population, Properties properties, Random rnd) {
		super(population, properties, rnd);
		// TODO Auto-generated constructor stub
	}
	
	
	@Override
	public void execute() {
		for(Individual ind:population) {
			if(ind.isEvaluated())continue;
			if(ind.isValid()) {
				ind.setFitness(operator.evaluate(ind));
			}
			else {
				ind.setFitness(this.defaultFitness);
			}
			ind.setEvaluated(true);
		}
	}
	@Override
	public void setProperties(Properties properties) {
		defaultFitness = Float.parseFloat(properties.getProperty(Constants.DEFAULT_FITNESS, Constants.DEFAULT_FITNESS_VALUE));

	}
	@Override
	public void addOperator(Operator op) {
		operator = (FitnessEvaluationOperator)op;
		
	}
}
