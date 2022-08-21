package model.individual;

import model.grammar.AbstractGrammar;

public class Individual {
	
	protected Genotype genotype;
	protected Phenotype phenotype;
	protected AbstractGrammar grammar;
	protected int age;
	protected boolean evaluated,valid;
	protected float fitness;
	
	public Individual() {
		evaluated = valid = false;
		age = 0;
	}
	public Individual(Genotype geno, Phenotype pheno, AbstractGrammar grammar) {
		this.phenotype = pheno;
		this.genotype = geno;
		this.grammar = grammar;
		this.phenotype.init(grammar.parse(genotype.getChromosome()));
		

		this.age = 0;
		this.valid = pheno.isValid();
		this.evaluated = false;
	}
	public Individual(Individual copy) {
		this.phenotype = new Phenotype(copy.phenotype);
		this.genotype = new Genotype(copy.genotype);
		this.grammar = copy.grammar;
		this.age = copy.age;
		this.valid = copy.valid;
	}
	public Genotype getGenotype() {
		return genotype;
	}
	public void setGenotype(Genotype genotype) {
		this.genotype = genotype;
	}
	public Phenotype getPhenotype() {
		return phenotype;
	}
	public void setPhenotype(Phenotype phenotype) {
		this.phenotype = phenotype;
	}
	public AbstractGrammar getGrammar() {
		return grammar;
	}
	public void setGrammar(AbstractGrammar grammar) {
		this.grammar = grammar;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public float getFitness() {
		return fitness;
	}
	public void setFitness(float fitness) {
		this.fitness = fitness;
	}
	public boolean isEvaluated() {
		return evaluated;
	}
	public void setEvaluated(boolean evaluated) {
		this.evaluated = evaluated;
	}
	public boolean isValid() {
		return valid;
	}
	public void setValid(boolean valid) {
		this.valid = valid;
	}
}
