import java.util.*;

public class Chromosome implements Comparable{
  private int numGenes;
  private BitSet genes;
  private float fitness;
  Chromosome(int numGenes){
    this.numGenes = numGenes;
    genes = new BitSet(numGenes);
    randomise();
  }
  
  // Just temp
  private void calcFitness(float x){
        fitness =  (float)(Math.sin(x) * Math.sin(0.4f * x) * Math.sin(3.0f * x));
    }

  public void randomise(){
    for(int i = 0; i < genes.size(); i++){
      if (Math.random() > 0.5)
        genes.set(i);
    }
  }
  public int getGene(int index){
    return ((genes.get(index) == true) ? 1 : 0);
  }
  public void setGene(int index, int value){
    if (value == 1)
      genes.set(index);
    else
      genes.clear(index);
  }
  public void flipGene(int index){
    genes.flip(index);
  }
  public float toFloat(){
    int base = 1;
    float x = 0;
    for(int i = 0; i < numGenes; i++){
      if (genes.get(i))
        x += base;
      base *= 2;
    }
    x /= 102.3f;
    return x;
  }
  public void setFitness(float f){
    fitness = f;
  }
  public float getFitness(){
    return fitness;
  }
  public void print(){
    for(int i = 0; i < numGenes; i++)
      System.out.printf("%d ", ((genes.get(i) == true) ? 1 : 0));
    System.out.println();
  }
  public String toString(){
    String s = "";
    for(int i = 0; i < numGenes; i++)
      s = s + ((genes.get(i) == true) ? 1 : 0);  
    return s;
  }
  @Override
    public int compareTo(Object c) {
      float f = ((Chromosome)c).getFitness();
        if (this.fitness == f)
          return 0;
        else if (this.fitness < f)
          return 1;
        else
          return -1;
        
    }
  /*public static void main(String[] args){
    ArrayList<Chromosome> population = new ArrayList<Chromosome>();
    population.add(new Chromosome(10));
    population.add(new Chromosome(10));
    population.add(new Chromosome(10));
    population.add(new Chromosome(10));

    
    for(int i = 0; i < population.size(); i++){
      population.get(i).calcFitness(population.get(i).toFloat());
      System.out.println(population.get(i).toFloat() + " " + population.get(i).getFitness());
    }
    
    System.out.println("Sorting.....");
    Collections.sort(population);

    for(int i = 0; i < population.size(); i++){
      System.out.println(population.get(i).toFloat() + " " + population.get(i).getFitness());
    }
  }*/
}