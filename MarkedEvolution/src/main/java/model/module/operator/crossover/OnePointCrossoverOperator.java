package model.module.operator.crossover;

import java.util.Properties;
import java.util.Random;

import model.Constants;
import model.Util.Pair;
import model.individual.Chromosome;
import model.individual.Genotype;
import model.individual.Individual;
import model.individual.Phenotype;
import model.individual.Population;
import model.module.operator.Operator;

public class OnePointCrossoverOperator extends CrossoverOperator{
	public OnePointCrossoverOperator(Properties properties, Random rnd) {
		super(properties, rnd);
	}

	@Override
	public void setProperties(Properties properties) {
		super.setProperties(properties);
	}

	
	public Pair<Chromosome, Chromosome> crossover(Chromosome c1, Chromosome c2) {
		
		int crossPoint = rnd.nextInt(c1.getLength());
		
		Chromosome child1 = new Chromosome(c1);
		Chromosome child2 = new Chromosome(c2);
		for(int i=crossPoint;i<child1.getLength();i++) {
			child1.setIntToCodon(i, c2.getCodon(i).getIntValue());
			child2.setIntToCodon(i, c1.getCodon(i).getIntValue());
		}
		return new Pair<Chromosome, Chromosome>(child1,child2);
	}

	@Override
	public void cross(Pair<Individual, Individual> parents) {
		if(this.probability > this.rnd.nextFloat()) {
			Pair<Chromosome, Chromosome> ncs = crossover(parents.first.getGenotype().getChromosome(),parents.second.getGenotype().getChromosome());
			parents.first.setGenotype(new Genotype(ncs.first));
			parents.first.revaluate();
			parents.second.setGenotype(new Genotype(ncs.second));
			parents.second.revaluate();
		}
		
	}

}
