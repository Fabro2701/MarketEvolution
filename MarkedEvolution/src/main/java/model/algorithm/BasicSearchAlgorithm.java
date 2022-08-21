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
		long start = System.currentTimeMillis();
		Population aux = init(new Population());
		System.out.println("-----------------------------------------Genration done in: "+(System.currentTimeMillis()-start)+"-----------------------------------------");

		for(int i=0;i<its;i++) {
			System.out.println("Starting Generation "+(i+1));
			long start2 = System.currentTimeMillis();
			aux = step(aux);
			System.out.println("-----------------------------------------Genration done in: "+(System.currentTimeMillis()-start2)+"-----------------------------------------");
		}
		System.out.println("-----------------------------------------Simulation done in: "+(System.currentTimeMillis()-start)+"-----------------------------------------");
		return aux;
	}

}
