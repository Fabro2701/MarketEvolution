package model.algorithm;

import model.individual.Population;

public class BasicSearchAlgorithm extends AbstractSearchAlgorithm{

	@Override
	public Population init(Population population) {
		return this.initPipeline.step(population);
	}

	@Override
	public Population step(Population population) {
		return this.loopPipeline.step(population);
	}

	public Population run(int its, Population population) {
		Population aux = init(population);
		for(int i=0;i<its;i++) {
			aux = step(aux);
		}
		return aux;
	}
	@Override
	public Population run(int its) {
		Population aux = init(new Population());
		for(int i=0;i<its;i++) {
			aux = step(aux);
		}
		return aux;
	}

}
