package main;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.Random;

import model.algorithm.AbstractPipeline;
import model.algorithm.AbstractSearchAlgorithm;
import model.algorithm.BasicSearchAlgorithm;
import model.algorithm.SimplePipeline;
import model.grammar.AbstractGrammar;
import model.grammar.StandardGrammar;
import model.individual.Population;
import model.module.CollectorModule;
import model.module.CrossoverModule;
import model.module.FitnessModule;
import model.module.InitializationModule;
import model.module.JoinModule;
import model.module.MutationModule;
import model.module.SelectionModule;
import model.module.operator.Operator;
import model.module.operator.collector.FintessCollectorOperator;
import model.module.operator.crossover.CrossoverOperator;
import model.module.operator.crossover.OnePointCrossOverOperator;
import model.module.operator.fitness.FitnessEvaluationOperator;
import model.module.operator.fitness.ProfitFitnessOperator;
import model.module.operator.initialization.InitializationOperator;
import model.module.operator.initialization.RandomInitializerOperator;
import model.module.operator.mutation.BasicCodonFlipMutationOperator;
import model.module.operator.mutation.MutationOperator;
import model.module.operator.replacement.JoinOperator;
import model.module.operator.replacement.SimpleJoinOperator;
import model.module.operator.selection.SelectionOperator;
import model.module.operator.selection.TournamentSelectionOperator;

public class Test {
	
	public static void main(String args[]) {
		AbstractGrammar grammar = new StandardGrammar();
		grammar.parseBNF("default");
		Properties properties = new Properties();
		try { 
			properties.load(new FileInputStream(new File("resources/properties/default.properties")));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace(); 
		} 
		Random rnd = new Random(7);
		 
		
		AbstractSearchAlgorithm algo = new BasicSearchAlgorithm();
		Population generalPopulation = new Population();
		Population selectedPopulation = new Population();
		
		//init
		AbstractPipeline initPipeline = new SimplePipeline();
		
		InitializationModule initModule = new InitializationModule(generalPopulation, properties,rnd);
		InitializationOperator rinitOp = new RandomInitializerOperator(properties,rnd,grammar);
		initModule.addOperator(rinitOp);
		
		FitnessModule initFitnessModule = new FitnessModule(generalPopulation, properties,rnd);
		FitnessEvaluationOperator fitnessOp = new ProfitFitnessOperator(properties,rnd);
		initFitnessModule.addOperator(fitnessOp);
		
		CollectorModule fitnesscollModule = new CollectorModule(generalPopulation, properties,rnd);
		Operator fitnesscollOp = new FintessCollectorOperator(properties,rnd);
		fitnesscollModule.addOperator(fitnesscollOp);

		//loop
		AbstractPipeline loopPipeline = new SimplePipeline();
		
		SelectionModule selectionModule = new SelectionModule(generalPopulation, properties, rnd, selectedPopulation);
		SelectionOperator selectionOp = new TournamentSelectionOperator(properties, rnd);
		selectionModule.addOperator(selectionOp);
		
		CrossoverModule crossoverModule = new CrossoverModule(generalPopulation, properties, rnd);
		CrossoverOperator crossoverOp = new OnePointCrossOverOperator(properties,rnd);
		crossoverModule.addOperator(crossoverOp);
		
		MutationModule mutationModule = new MutationModule(generalPopulation, properties, rnd);
		MutationOperator mutationOp = new BasicCodonFlipMutationOperator(properties,rnd);
		mutationModule.addOperator(mutationOp);
		
		FitnessModule fitnessModule = new FitnessModule(generalPopulation, properties,rnd);
		fitnessModule.addOperator(fitnessOp);
		
		JoinModule joinModule = new JoinModule(generalPopulation, properties, rnd, selectedPopulation);
		JoinOperator joinOp = new SimpleJoinOperator(properties,rnd);
		joinModule.addOperator(joinOp);
		
		initModule.setPopulation(generalPopulation);
		initFitnessModule.setPopulation(generalPopulation);
		
		selectionModule.setPopulation(generalPopulation);
		crossoverModule.setPopulation(generalPopulation);
		mutationModule.setPopulation(generalPopulation);
		fitnessModule.setPopulation(generalPopulation);
		joinModule.setPopulation(generalPopulation);
		
		fitnesscollModule.setPopulation(generalPopulation);
		
		initPipeline.addModule(initModule);
		initPipeline.addModule(initFitnessModule);
		initPipeline.addModule(fitnesscollModule);

		loopPipeline.addModule(selectionModule);
		loopPipeline.addModule(crossoverModule);
		loopPipeline.addModule(mutationModule);
		loopPipeline.addModule(fitnessModule);
		loopPipeline.addModule(fitnesscollModule);
		loopPipeline.addModule(joinModule);
		
		algo.setInitPipeline(initPipeline);
		algo.setLoopPipeline(loopPipeline);
		
		algo.run(200);
		
	}
}
