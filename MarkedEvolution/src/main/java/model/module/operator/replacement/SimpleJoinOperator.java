package model.module.operator.replacement;

import java.util.Properties;
import java.util.Random;

import model.individual.Population;
import model.module.operator.Operator;

public class SimpleJoinOperator extends JoinOperator{

	public SimpleJoinOperator(Properties properties, Random rnd) {
		super(properties, rnd);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void setProperties(Properties properties) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void joinOutsiders(Population outsiders) {
		System.out.println("joining "+outsiders.size()+" to "+generalPopulation.size());
		this.generalPopulation.addAll(outsiders);
		
	}

}
