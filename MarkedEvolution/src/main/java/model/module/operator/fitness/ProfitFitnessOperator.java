package model.module.operator.fitness;

import java.util.Properties;
import java.util.Random;

import backtesting.BackTest;
import backtesting.stats.BackTestStats;
import model.individual.Individual;
import model.individual.Population;

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
		BackTestStats stats = backtest.runTest(ind.getPhenotype().getStrategy());
		return stats.getProfit();
	}
}
