package model.algorithm;

import model.individual.Population;

public abstract class AbstractSearchAlgorithm {
	protected AbstractPipeline initPipeline,loopPipeline;
	
	public abstract Population init(Population population);
	public abstract Population step(Population population);
	public abstract Population run(int its);
	
	public AbstractPipeline getInitPipeline() {
		return initPipeline;
	}

	public void setInitPipeline(AbstractPipeline initPipeline) {
		this.initPipeline = initPipeline;
	}

	public AbstractPipeline getLoopPipeline() {
		return loopPipeline;
	}

	public void setLoopPipeline(AbstractPipeline loopPipeline) {
		this.loopPipeline = loopPipeline;
	}
	
}
