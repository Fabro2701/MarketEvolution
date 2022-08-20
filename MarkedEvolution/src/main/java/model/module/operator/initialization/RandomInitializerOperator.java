package model.module.operator.initialization;

import java.util.Properties;
import java.util.Random;

import model.Constants;
import model.grammar.AbstractGrammar;
import model.individual.Chromosome;
import model.individual.Genotype;
import model.individual.Individual;
import model.individual.Phenotype;
import model.individual.Population;
import model.module.operator.Operator;

public class RandomInitializerOperator extends Operator{
	AbstractGrammar grammar;
	int num;
	public RandomInitializerOperator(Properties properties, Random rnd, AbstractGrammar grammar) {
		super(properties, rnd);
		this.grammar = grammar;
	}
	@Override
	public Population execute(Population population) {
		for(int i=0;i<num;i++) {
			Chromosome chr = new Chromosome();
			this.grammar.parse(chr);
			
			Genotype geno = new Genotype();
			Phenotype pheno = new Phenotype();
			
			population.add(new Individual(geno,pheno,this.grammar));
		}
		return population;
	}
	@Override
	public void setProperties(Properties properties) {
		num = Integer.parseInt(properties.getProperty(Constants.POPULATION_SIZE, Constants.DEFAULT_NUM_POPULATION_SIZE));
	}

}
