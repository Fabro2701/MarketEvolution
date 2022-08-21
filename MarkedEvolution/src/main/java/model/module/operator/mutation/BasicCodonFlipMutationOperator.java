package model.module.operator.mutation;

import java.util.Properties;
import java.util.Random;

import model.Constants;
import model.individual.Chromosome;
import model.individual.Individual;
import model.individual.Population;
import model.module.operator.Operator;

public class BasicCodonFlipMutationOperator extends Operator{
	float probability;
	public BasicCodonFlipMutationOperator(Properties properties, Random rnd) {
		super(properties, rnd);
	}

	@Override
	public void setProperties(Properties properties) {
		probability = Float.parseFloat(properties.getProperty(Constants.MUTATION_PROBABILITY, Constants.DEFAULT_MUTATION_PROBABILITY));
	}

	@Override
	public Population execute(Population population) {
		Chromosome c = null;
		for(Individual ind:population) {
			if(this.probability > this.rnd.nextFloat()) {
				c = ind.getGenotype().getChromosome();
				c.getCodon(rnd.nextInt(c.getLength())).setInt(rnd.nextInt(256));
				ind.revaluate();
			}
		}
		return population;
	}

}
