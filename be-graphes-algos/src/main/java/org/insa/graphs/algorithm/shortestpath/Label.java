package org.insa.graphs.algorithm.shortestpath;

import org.insa.graphs.model.Arc;

public class Label implements Comparable<Label> {
	private int sommetCourant;
	private boolean marque;
	private double coutRealise;
	private Arc pere;

	public Label(int sommet, boolean marque, double coutRealise, Arc pere) {
		this.sommetCourant = sommet;
		this.marque = marque;
		this.coutRealise = coutRealise;
		this.pere = pere;
	}

	public int getSommetCourant() {
		return this.sommetCourant;
	}

	public boolean getMarque() {
		return this.marque;
	}

	public void setMarque(boolean val) {
		this.marque = val;
	}

	public double getCoutRealise() {
		return this.coutRealise;
	}

	public void setCoutRealise(double val) {
		this.coutRealise = val;
	}

	public Arc getPere() {
		return this.pere;
	}

	public int compareTo(Label label) {
		return Double.compare(this.getCoutRealise(), label.getCoutRealise());
	}

	public void setPere(Arc val) {
		this.pere = val;
	}
}
