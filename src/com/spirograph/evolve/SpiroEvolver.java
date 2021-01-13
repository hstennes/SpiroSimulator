package com.spirograph.evolve;

import java.util.ArrayList;
import java.util.Random;

import com.spirograph.design.SpiroInfo;

public class SpiroEvolver {
	
	private float mutationChance = 0.05f;
	private Random random;
	private int generation;
	
	public SpiroEvolver() {
		random = new Random();
		generation = 1;
	}
	
	public SpiroInfo[] newGeneration(ArrayList<SpiroInfo> population, int numSpiros) {
		SpiroInfo[] newPopulation = new SpiroInfo[numSpiros];
		for(int i = 0; i < newPopulation.length; i++) {
			int firstSpiro = i % population.size();
			int secondSpiro = random.nextInt(population.size());
			Chromosome offspring = breed(new Chromosome(population.get(firstSpiro)), new Chromosome(population.get(secondSpiro)));
			newPopulation[i] = offspring.decode();
		}
		generation++;
		return newPopulation;
	}
	
	private Chromosome breed(Chromosome a, Chromosome b) {
		a.crossover(b);
		a.mutate(mutationChance);
		b.mutate(mutationChance);
		if(random.nextBoolean()) return a;
		else return b;
	}
	
	public void reset() {
		generation = 1;
	}
	
	public int getGeneration() {
		return generation;
	}

	public float getMutationChance() {
		return mutationChance;
	}
	
	public void setMutationChance(float mutationChance) {
		if(mutationChance >= 0 && mutationChance <= 100) this.mutationChance = mutationChance;
		else System.out.println("Invalid mutation chance");
	}
}
