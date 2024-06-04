package representation;

/**
 * This class represents the position of a
 * Pentomino in the puzzle. 
 * @author Documented by Hugo Gilbert, a large part of the code 
 * was written by David Eck and Julien Lesca
 *
 */
public class Position{
	private int row, column;
	
	/**
	 * Constructor by default.
	 */
	public Position() {
		this.row = 0;
		this.column = 0;
	}
	
	/**
	 * Constructor providing values for attributes row and column.
	 */
	public Position(int row, int column) {
		this.row = row;
		this.column = column;
	}
	
	/**
	 * Returns a clone of the position. 
	 * @return a clone of the position.
	 */
	public Position clone() {
		return new Position(this.row,this.column);
	}
	
	
	/**
	 * Yields a string representation of the position.
	 * @return Returns a string representation of the position.
	 */
	@Override public String toString() {
		return "[ " + this.row + ", " + this.column + " ]";
	}
	
	/**
	 * Getter for attribute row
	 * @return Value of attribute row
	 */
	public int getRow() {return row;}
	
	/**
	 * Getter for attribute column
	 * @return Value of attribute column
	 */
	public int getColumn() {return column;}
}
