package it.gruppoinit.pal.gp.core.domain.util;

import java.util.ArrayList;
import java.util.List;

public class Clcg {

    int Seed, M, A;

    public Clcg() {

	Seed = 1;
	M = 2147483647; // 2^31-1
	A = 1103515245;
    }

    public double GetNextValue() {

	// ALGORITMO DI SCHRANGE
	int Q, Z, lo, hi, test;
	Q = M / A;
	Z = M % A;
	hi = Seed / Q;
	lo = Seed % Q;
	test = A * lo - Z * hi;
	Seed = (test > 0) ? test : test + M;
	Double risultato = (double) Seed / M;
	return risultato;
    }

    public int getSeed() {

	return this.Seed;
    }

    public void setSeed(int seed) {

	this.Seed = seed;
    }

    public List<Integer> getListaEstratti(Integer min, Integer max, Integer n) {

	List<Integer> estratti = new ArrayList<Integer>();
	// Clcg LCG = new Clcg();
	while (n > 0) {
	    estratti.add((int) (min + this.GetNextValue() * (max - min) + 0.5));
	    n--;
	}
	return estratti;
    }
}
