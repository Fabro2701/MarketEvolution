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
import model.module.Module;
import model.module.operator.Operator;
import model.module.operator.PrintOperator;
import model.module.operator.crossover.OnePointCrossOverOperator;
import model.module.operator.fitness.ProfitFitnessOperator;
import model.module.operator.initialization.RandomInitializerOperator;
import model.module.operator.mutation.BasicCodonFlipMutationOperator;
import model.module.operator.selection.EliteSelectionOperator;

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
		Random rnd = new Random();
		 
		
		AbstractSearchAlgorithm algo = new BasicSearchAlgorithm();
		
		//init
		AbstractPipeline initPipeline = new SimplePipeline();
		
		Module initModule = new Module();
		Operator rinitOp = new RandomInitializerOperator(properties,rnd,grammar);
		initModule.addOperator(rinitOp);
		
		Module initFitnessModule = new Module();
		Operator fitnessOp = new ProfitFitnessOperator(properties,rnd);
		initFitnessModule.addOperator(fitnessOp);

		//loop
		AbstractPipeline loopPipeline = new SimplePipeline();
		
		Module selectionModule = new Module();
		Operator selectionOp = new EliteSelectionOperator(properties,rnd);
		selectionModule.addOperator(selectionOp);
		
		Module crossoverModule = new Module();
		Operator crossoverOp = new OnePointCrossOverOperator(properties,rnd);
		crossoverModule.addOperator(crossoverOp);
		
		Module mutationModule = new Module();
		Operator mutationOp = new BasicCodonFlipMutationOperator(properties,rnd);
		mutationModule.addOperator(mutationOp);
		
		Module fitnessModule = new Module();
		fitnessModule.addOperator(fitnessOp);
		
		
		
		initPipeline.addModule(initModule);
		initPipeline.addModule(initFitnessModule);

		loopPipeline.addModule(selectionModule);
		loopPipeline.addModule(crossoverModule);
		loopPipeline.addModule(mutationModule);
		loopPipeline.addModule(fitnessModule);
		
		algo.setInitPipeline(initPipeline);
		algo.setLoopPipeline(loopPipeline);
		
		algo.run(2);
		
	}
}
