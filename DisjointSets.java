import java.util.*;

/**
 * You are trying to keep track of friend circles. Remember the basic operations: find-set, make-set, and union
 * makeSet = you're making a circle out of one person
 * find = find that person's friend circle, return the rep of that circle
 * union = join two friend circles, everyone becomes friends, rep may or not change
 *
 * You have total freedom over how you wanna implement!
 * That means data structures
 */

public class DisjointSets {

  // You want some globals or constructor, determined by which improvements you're implementing
  // using which data structures
	
  //Using list to represent set of disjoint sets
  List<Map<String, Set<String>>> disjointSet;
  
  public DisjointSets()
  {
		   disjointSet = new ArrayList<Map<String, Set<String>>>();
  }

  /**
   * Make a set given a person's name
   * @param name
   */
  public void makeSet(String name)
  {
	  //create a new set
      Map<String, Set<String>> map = new HashMap<String, Set<String>>();
      //add the given name in a new set
      Set<String> set = new HashSet<String>();
      set.add(name);
      //set the given name as the default representative of the new set and finish adding the name in the new set
      map.put(name, set);
      
      //add the new set into the disjointSet
      disjointSet.add(map);
  }

  /**
   * Return the rep of the set the name is part of
   * @param name person's name
   * @return representative of that person's set
   */
  //iterative find-set
  public String find(String name)
  {
	  Set<String> set;
	  Set<String> keySet;
	  //iteratively traverse the disjointSet until we find the representative of the given name.
	     for (int i = 0; i < disjointSet.size(); i++)
	        {
	    	 //since we are using map we can retrieve the set of representatives. Here the set of representatives is the 'keySet'
	            Map<String, Set<String>> map = disjointSet.get(i);
	            keySet = map.keySet();
	            //traverse keySet
	            for (String key : keySet)
	            {
	            	//check all the sets of the representative for the given name
	                set = map.get(key);
	                if (set.contains(name))
	                {
	                	//return the name of the representative where the given name is found
	                    return key;
	                }
	            }
	        }
	     //return "Not found" if name DNE(Or no representative for some reason)
	     return new String("Not Found");
  }

  /**
   * Union the set of two people
   * @param name1 first person's name
   * @param name2 second person's name
   */
  
  //Using Union-find method
  public void union (String name1, String name2)
  {
	  String firstRep = find(name1);
      String secondRep= find(name2);
      Set<String> firstSet = null;
      Set<String> secondSet = null;

      //find the set of name1 and set of name2 usingg find(name)
      for (int i = 0; i < disjointSet.size(); i++)
      {
          Map<String, Set<String>> map = disjointSet.get(i);
          if (map.containsKey(firstRep))
          {
              firstSet = map.get(firstRep);
          } else if (map.containsKey(secondRep))
          { 
              secondSet = map.get(secondRep);
          }
      }
      //if set of name1 and set of name2 exists. Use addAll to add the content of sececondSet into firstSet
      if (firstSet != null && secondSet != null)
      firstSet.addAll(secondSet);

      //now, put the union set(which is the 'firstSet') into disjointSet
      for (int i = 0; i < disjointSet.size(); i++)
      {
          Map<String, Set<String>> map = disjointSet.get(i);
          //set representative of the union set (the representative of the firstSet will be the representative of the unionset)
          if (map.containsKey(firstRep))
          {
              map.put(firstRep, firstSet);
          }
          //remove the representative status of secondRep since he/she is now just a part of the unionset
          //then remove his/her representing set
          else if (map.containsKey(secondRep))
          {
              map.remove(secondRep);
              disjointSet.remove(i);
          }
      }
  }
  
  //check the total of disjoint sets so we can compare the difference pre and post-union
  public int totalDisjointSets()
  {
	  return disjointSet.size();
  }
  
  
  public static void main(String[] args)
  {    
    // construct the class
    // play around!
	  
    String[] names = {"Tasha", "Rui", "Tenzin", "Liz", "Nev", "Loan", "George"};
    DisjointSets disjointSet = new DisjointSets();
    for (int i = 0; i < names.length; i++)
    {
        disjointSet.makeSet(names[i]);
    }
    
    System.out.println("PERSON : REPRESENTATIVE OF THAT PERSON");
    for (int i = 0; i <= disjointSet.totalDisjointSets()-1; i++)
    {
        System.out.println(names[i] + "\t:\t" + disjointSet.find(names[i]));
    }
    System.out.println("# of disjoint sets: (before Union) "+ disjointSet.totalDisjointSets());
    disjointSet.union(names[4], names[1]);
    disjointSet.union(names[3],names[4]);
    
    System.out.println("\nAfter Union: (Nev)U(Rui) --> (Liz)U(Nev)\n");
    System.out.println("PERSON : REPRESENTATIVE OF THAT PERSON");
    for (int i = 0; i <= disjointSet.totalDisjointSets()+1; i++)
    {
        System.out.println(names[i] + "\t:\t" + disjointSet.find(names[i]));
    }
    System.out.println("# of disjoint sets: (After Union) "+ disjointSet.totalDisjointSets());

  }
}
