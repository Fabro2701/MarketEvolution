package model.module.operator.replacement;

import java.util.Properties;
import java.util.Random;

import model.individual.Population;
import model.module.operator.Operator;

public abstract class JoinOperator extends Operator{
	Population generalPopulation;
	public JoinOperator(Properties properties, Random rnd) {
		super(properties, rnd);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void setProperties(Properties properties) {
		// TODO Auto-generated method stub
		
	}
	public abstract void joinOutsiders(Population outsiders);
	public void setGeneralPopulation(Population generalPopulation) {
		this.generalPopulation = generalPopulation;
	}
}
