package backtesting;

import java.util.Properties;
import java.util.Random;

import backtesting.BackTest;
import backtesting.stats.BackTestStats;
import backtesting.strategy.Evaluator;
import backtesting.strategy.Strategy;
import model.grammar.Parser;
import model.individual.Individual;
import model.individual.Population;
import model.module.operator.fitness.FitnessEvaluationOperator;

public class ProfitFitnessOperator extends FitnessEvaluationOperator{
	BackTest backtest;
	public ProfitFitnessOperator(Properties properties, Random rnd) {
		super(properties, rnd);
		backtest = new BackTest("NVDA");	
	}

	@Override
	public void setProperties(Properties properties) {
		super.setProperties(properties);
	}

	@Override
	public float evaluate(Individual ind) {
		Parser parser = new Parser();
		Evaluator evaluator = new Evaluator(parser.parse(ind.getPhenotype().getVisualCode()));
		Strategy strategy = new Strategy();
		strategy.setEvaluator(evaluator);
		BackTestStats stats = backtest.runTest(strategy);
		if(stats.getOrders()==0)return -5000f;
		return stats.getProfit()-(float)stats.getOrders()*0.19f;
	}
}
