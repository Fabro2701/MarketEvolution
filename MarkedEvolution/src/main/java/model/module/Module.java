package model.module;

import java.util.ArrayList;

import model.individual.Population;
import model.module.operator.Operator;

public class Module extends ArrayList<Operator>{

	public Population execute(Population population) {
		Population aux = population;
		for(Operator o:this) {
			aux = o.execute(aux);
		}
		return aux;
	}
	public void addOperator(Operator op) {
		this.add(op);
	}

}
