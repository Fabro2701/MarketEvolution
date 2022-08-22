package model.module.operator.selection;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Properties;
import java.util.Random;

import model.Constants;
import model.individual.Individual;
import model.individual.Population;
import model.module.operator.Operator;

public class TournamentSelectionOperator extends Operator{
	int selectionSize,k;
	public TournamentSelectionOperator(Properties properties, Random rnd) {
		super(properties, rnd);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void setProperties(Properties properties) {
		selectionSize = Integer.parseInt(properties.getProperty(Constants.SELECTION_SIZE, Constants.DEFAULT_SELECTION_SIZE));
		k = Integer.parseInt(properties.getProperty(Constants.TOURNAMENT_SIZE, Constants.DEFAULT_TOURNAMENT_SIZE));
	}

	@Override
	public Population execute(Population population) {
		Population selected = new Population();
		
		population.sort(Comparator.comparing(Individual::getFitness));
		for(int i=0;i<selectionSize;i++) {
			selected.add(getWinner(population));
		}
		return selected;
	}
	private Individual getWinner(Population population) {
		List<Individual>l = new ArrayList<Individual>();
		for(int i=0;i<k;i++) {
			l.add(population.get(rnd.nextInt(population.size())));
		}
		l.sort(Comparator.comparing(Individual::getFitness));
		return l.get(l.size()-1);
	}
	 

}
