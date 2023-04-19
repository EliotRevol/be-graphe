package org.insa.graphs.algorithm.shortestpath;

import org.insa.graphs.model.Arc;

public class LabelStar extends Label {

	private double coutEstime;

	public LabelStar(int sommet, boolean marque, double coutRealise, Arc pere, double coutEstime) {
		super(sommet, marque, coutRealise, pere);
		this.coutEstime = coutEstime;
	}

	public double getCoutEstime() {
		return this.coutEstime;
	}

	public void setCoutEstime(double coutEstime) {
		this.coutEstime = coutEstime;
	}

	public double getTotalCost() {
		return this.getCoutEstime() + this.getCoutRealise();
	}
}
