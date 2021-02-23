// Genetic Algorithm Population
// Based on code by Mark Watson
// https://github.com/stephensheridan/Java-AI-Book-Code/tree/master/genetic-algorithms

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
  // Used to implement roulette wheel selection
  private int [] rouletteWheel;
  private int rouletteWheelSize;
  // Keep the best chromosome values
  private float bestx;
  private float besty;

  public Population(int numGenes, int populationSize,float cFraction, float mFraction){
    this.numGenes = numGenes;
    this.populationSize = populationSize;
    crossoverFraction = cFraction;
    mutationFraction = mFraction;
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
    int num = (int)(populationSize * crossoverFraction);
    for (int i = num-1; i >= 0; i--){
      //for(int i = populationSize - 1; i > (populationSize-num); i--){
      int c1 = (int)(rouletteWheelSize * Math.random() * 0.9999f);
      int c2 = (int)(rouletteWheelSize * Math.random() * 0.9999f);
      c1 = rouletteWheel[c1];
      c2 = rouletteWheel[c2];
      if (c1 != c2){
        int locus = 1 + (int)((numGenes - 2) * Math.random());
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
    int num = (int)(populationSize * mutationFraction);
    for (int i=0; i<num; i++){
      int c = (int)(populationSize * Math.random() * 0.99);
      int g = (int)(numGenes * Math.random() * 0.99);
        population.get(c).flipGene(g);
    }
  }
  public void doRemoveDuplicates(){
    for (int i=populationSize - 1; i>3; i--){
      for (int j=0; j<i; j++){
        if (population.get(i).compareTo(population.get(j)) == 0){
          int g = (int)(numGenes * Math.random() * 0.99);
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
    calcFitness();
    sortPopulation();
    if (display)
    	print();
    doCrossovers();
    doMutations();
    doRemoveDuplicates();
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