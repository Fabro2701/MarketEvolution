package model.module.operator.selection;

import java.util.Collections;
import java.util.Comparator;
import java.util.Properties;
import java.util.Random;

import model.Constants;
import model.individual.Individual;
import model.individual.Population;
import model.module.operator.Operator;

public class EliteSelectionOperator extends SelectionOperator{
	int selectionSize;
	public EliteSelectionOperator(Properties properties, Random rnd) {
		super(properties, rnd);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void setProperties(Properties properties) {
		selectionSize = Integer.parseInt(properties.getProperty(Constants.SELECTION_SIZE, Constants.DEFAULT_SELECTION_SIZE));
		
	}

	@Override
	public void seletPopulation(Population population) {
		population.sort(Comparator.comparing(Individual::getFitness));
		for(int i=0;i<selectionSize;i++) {
			this.selectedPopulation.add(population.get(population.size()-1-i));
		}
	}

}
