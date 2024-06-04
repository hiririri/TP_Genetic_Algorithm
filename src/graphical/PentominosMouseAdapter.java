package graphical;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import algorithm.GeneticAlgorithm;
import representation.PentominosBoard;

public class PentominosMouseAdapter extends MouseAdapter{
	private LittlePentominos lp;
	public int cpt;
	
	public PentominosMouseAdapter(LittlePentominos b) {
		this.lp = b;
		this.cpt = 0;
	}
	
	public void mousePressed(MouseEvent evt) {
		PentominosBoard pb = new PentominosBoard();
        GeneticAlgorithm ga = new GeneticAlgorithm(pb,100);
        pb = ga.solve( 0.1, 0.4);
        for(int i = 0 ; i < ga.population.size(); i++) {
        	if(ga.population.get(i).nbrConflict() == 0)
        		System.out.println("Parents population" + i + ":\n" + ga.population.get(i));
        }
        for(int i = 0; i < 8; i++) {
        	for(int j = 0; j < 8; j++) {
        		if(pb.getBoard(i, j).size() == 0) {
        			this.lp.board.setColor(i,j,null);
        		}
        		else {
        			this.lp.board.setColor(i,j,pb.getBoard(i, j).get(0));
        		}
        	}
        }
        this.lp.board.repaint();
    }
}
