// Integer based, value encoded chromosome
// S. Sheridan 27/02/2014
// Institute of Technology Blanchardstown

import java.io.*;
import java.util.*;

public class Genetic {
	
	private static final int POPULATION_SIZE = 10; 			// No. of chromosomes in the population
	private static final int CHROMOSOME_LENGTH = 10; 		// No. of genes per chromosome 
    private static final float CROSSOVER_FRACTION = 0.7f; 	// Half population is recombined every evolution
    private static final float MUTATION_FRACTION = 0.1f; 	// 10% of populaiton is mutated
	
	private static final int NUM_GENERATIONS = 100;
	
	public static void main(String[] args){
			
		String results = "";
		
		// Create a population using particular trial data
		Population p = new Population(POPULATION_SIZE, CHROMOSOME_LENGTH, CROSSOVER_FRACTION, MUTATION_FRACTION);

		// Each trial will run over 100 generations or until solution has been found
		for(int gen = 0; gen < NUM_GENERATIONS; gen++){
			
			System.out.println("*** Generation " + gen + " ***");
			
			p.evolve();
	    	
			// Print the populaiton and their fitness values
			for(int j = 0; j < POPULATION_SIZE; j++){
				System.out.println("Chromosome[" + j + "] = " + p.getChromosomeAt(j).toString() + " Value = " + p.getChromosomeAt(j).geneToFloat() + " Fitness = " + p.getChromosomeAt(j).getFitness());
			}
			
		}
	
	}
}
