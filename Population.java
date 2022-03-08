// Stephen Sheridan
// TU Dublin - Blanchardstown Campus
// Genetic Algorithm Population
// Based on code by Mark Watson
// https://github.com/mark-watson/Java-AI-Book-Code/tree/master/genetic-algorithms

import java.util.*;

public class Population {
  // Number of genes per chromosome
  protected int numGenes;        
  // Number of chromosomes
  protected int populationSize;  
  // Population of Chromosomes
  private ArrayList<Chromosome> population;
  // Control the amount of crossover
  private float crossoverFraction;
  // Control the amount of mutaiton
  private float mutationFraction;
  // Control how many bits get flipped
  private float mutationRate;
  // Used to implement roulette wheel selection
  private int [] rouletteWheel;
  private int rouletteWheelSize;
  // Keep the best chromosome values
  private float bestx;
  private float besty;

  public Population(int numGenes, int populationSize,float cFraction, float mFraction, float mRate){
    this.numGenes = numGenes;
    this.populationSize = populationSize;
    crossoverFraction = cFraction;
    mutationFraction = mFraction;
    mutationRate = mRate;
    bestx = 0;
    besty = 0;
    // Create the population
    population = new ArrayList<Chromosome>();
    for(int i = 0; i < populationSize; i++){
      population.add(new Chromosome(numGenes));
    }

    // Define the roulette wheel based on population size
    rouletteWheelSize = 0;
    for(int i=0; i < populationSize; i++){
      rouletteWheelSize += i + 1;
    }
    rouletteWheel = new int[rouletteWheelSize];
    int num_trials = populationSize;
    int index = 0;
    for (int i=0; i<populationSize; i++){
      for (int j=0; j<num_trials; j++){
        rouletteWheel[index++] = i;
      }
      num_trials--;
    }
    System.out.println("Roulettewheel size : " + rouletteWheel.length);
    for(int i = 0; i < rouletteWheel.length; i++){
      System.out.print(rouletteWheel[i] + " ");
    }
    System.out.println();
    calcFitness();
    sortPopulation();
  }

  private float fitness(float x){
    return (float)(Math.sin(x) * Math.sin(0.4f * x) * Math.sin(3.0f * x));
  }

  public void calcFitness(){
    for (int i=0; i < populationSize; i++){
      float f = fitness(population.get(i).toFloat());
      population.get(i).setFitness(f);
      // Keep the best
      if (f > besty){
          besty = f;
          bestx = population.get(i).toFloat();
      }
    }
  }
  private void sortPopulation(){
    Collections.sort(population);
  }
  public void doCrossovers(){
    int stop = (int)(populationSize - (populationSize * crossoverFraction));
    for (int i = populationSize - 1; i >= stop; i--){
      int c1 = (int)(rouletteWheelSize * Math.random() * 0.9999f);
      int c2 = (int)(rouletteWheelSize * Math.random() * 0.9999f);
      c1 = rouletteWheel[c1];
      c2 = rouletteWheel[c2];
      if (c1 != c2){
        int locus = (int)(numGenes * Math.random());
        for (int g = 0; g < numGenes; g++){
          if (g < locus)
            population.get(i).setGene(g, population.get(c1).getGene(g));
          else
            population.get(i).setGene(g, population.get(c2).getGene(g));
        }
      }
    }
  }
  public void print(){
    for(int i = 0; i < population.size(); i++){
      System.out.println(population.get(i).toFloat() + " " + population.get(i).getFitness());
    }
  }
  public void doMutations(){
    int stop = (int)(populationSize - (populationSize * crossoverFraction));
    for (int i = populationSize - 1; i >= stop; i--){
      if (Math.random() < mutationFraction){
        for(int j = 0; j < numGenes; j++){
          if (Math.random() < mutationRate){
            int g = (int)(numGenes * Math.random());
            population.get(i).flipGene(g);
          }
        }
      }
    }
  }
  public void doRemoveDuplicates(){
    int stop = (int)(populationSize - (populationSize * crossoverFraction));
    for (int i = populationSize - 1; i >= stop; i--){
      for (int j=0; j<=i; j++){
        if (population.get(i).compareTo(population.get(j)) == 0){
          int g = (int)(numGenes * Math.random());
          population.get(i).flipGene(g);
          break;
        }
      }
    }
  }
  public Chromosome getChromosomeAt(int index){
  	return population.get(index);
  }
  public float[] getCandidates(){
    float[] candidates = new float[populationSize];
    for(int i = 0; i < populationSize; i++){
      candidates[i] = population.get(i).toFloat();
    }
    return candidates;
  }
  public float getBestX(){return bestx;}
  public float getBestY(){return besty;}
  public void evolve(boolean display){
    if (display)
    	print();
    doCrossovers();
    doMutations();
    doRemoveDuplicates();
    calcFitness();
    sortPopulation();
  }
  public void reset(){
    bestx = 0;
    besty = 0;
    // Reset the population
    population = new ArrayList<Chromosome>();
    for(int i = 0; i < populationSize; i++){
      population.add(new Chromosome(numGenes));
    }
  }
}