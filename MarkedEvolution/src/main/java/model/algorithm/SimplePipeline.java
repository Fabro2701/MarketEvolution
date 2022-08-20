package model.algorithm;

import model.individual.Population;
import model.module.Module;

public class SimplePipeline extends AbstractPipeline{

	@Override
	public Population step(Population population) {
		Population aux = population;
		for(Module m:this) {
			aux = m.execute(aux);
		}
		return aux;
	}

}
