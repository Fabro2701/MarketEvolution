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
		boolean b = false;
		for(Individual ind:population) {
			b = false;
			c = ind.getGenotype().getChromosome();
			for(int i=0;i<c.getLength();i++) {
				if(this.probability > this.rnd.nextFloat()) {
					c.getCodon(i).setInt(rnd.nextInt(256));
					
					b = true;
				}
				if(b)ind.revaluate();
			}
			
		}
		return population;
	}

}
