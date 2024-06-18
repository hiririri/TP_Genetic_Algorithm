package tp.representation;

/**
 * This class represents a couple of PentominosBoard.
 * The two elements of the couple represents parents 
 * of individuals in a genetic tp.algorithm.
 * @author Documented by Hugo Gilbert, a large part of the code 
 * was written by David Eck and Julien Lesca
 *
 */
public class Couple {
	public PentominosBoard mother, father;
	
	public Couple(PentominosBoard m, PentominosBoard f) {
		this.mother = m.copy();
		this.father = f.copy();
	}
}
