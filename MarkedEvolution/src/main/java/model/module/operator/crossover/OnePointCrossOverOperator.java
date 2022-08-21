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

public class OnePointCrossOverOperator extends Operator{
	float probability;
	int num;
	public OnePointCrossOverOperator(Properties properties, Random rnd) {
		super(properties, rnd);
	}

	@Override
	public void setProperties(Properties properties) {
		probability = Float.parseFloat(properties.getProperty(Constants.CROSSOVER_PROBABILITY, Constants.DEFAULT_CROSSOVER_PROBABILITY));
		num = Integer.parseInt(properties.getProperty(Constants.POPULATION_SIZE, Constants.DEFAULT_NUM_POPULATION_SIZE));
	}

	@Override
	public Population execute(Population population) {
		Population children = new Population();
		int p1,p2;
		for(int i=0;i<num-population.size();i++) {
			p1 = rnd.nextInt(population.size());
			p2 = rnd.nextInt(population.size());
			if(this.probability < this.rnd.nextFloat()) {
				Pair<Chromosome, Chromosome> chs = crossover(population.get(p1).getGenotype().getChromosome(),population.get(p2).getGenotype().getChromosome());
				
				children.add(new Individual(new Genotype(chs.first), new Phenotype(), population.get(p1).getGrammar()));
				children.add(new Individual(new Genotype(chs.second), new Phenotype(), population.get(p2).getGrammar()));
			}
			else {
				children.add(new Individual(population.get(p1)));
				children.add(new Individual(population.get(p2)));
			}
		}
		population.addAll(children);
		
		return population;
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

}
