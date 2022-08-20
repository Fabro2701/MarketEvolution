package model.algorithm;

import java.util.ArrayList;

import model.individual.Population;
import model.module.Module;

public abstract class AbstractPipeline extends ArrayList<Module>{
	public abstract Population step(Population population);
	
	public AbstractPipeline() {
		super();
	}
	public void addModule(Module m) {
		this.add(m);
	}
}
