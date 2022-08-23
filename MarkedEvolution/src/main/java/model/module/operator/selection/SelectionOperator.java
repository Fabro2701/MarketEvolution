package model.module.operator.selection;

import java.util.Properties;
import java.util.Random;

import model.individual.Population;
import model.module.operator.Operator;

public abstract class SelectionOperator extends Operator{

	Population selectedPopulation;
	public SelectionOperator(Properties properties, Random rnd) {
		super(properties, rnd);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void setProperties(Properties properties) {
		// TODO Auto-generated method stub
		
	}
	
	public abstract void seletPopulation(Population population);
	public void setSelectedPopulation(Population selectedPopulation) {
		this.selectedPopulation = selectedPopulation;
	}
}
