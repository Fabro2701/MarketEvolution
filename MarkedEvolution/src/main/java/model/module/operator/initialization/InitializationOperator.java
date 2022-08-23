package model.module.operator.initialization;

import java.util.Properties;
import java.util.Random;

import model.individual.Individual;
import model.module.operator.Operator;

public abstract class InitializationOperator extends Operator{
	public InitializationOperator(Properties properties, Random rnd) {
		super(properties, rnd);
	}
	
	public abstract Individual createIndividual();
}
