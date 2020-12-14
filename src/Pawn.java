import java.util.*;

public class Pawn extends Piece{
	
	private boolean hasMoved = false, vulnerableToEP = false;
	
	public Pawn(int idInit)
	{
		super(idInit);
		name = 'p';
		if (idInit < 8)
			imgIndex = 0;
		else
			imgIndex = 1;
	}
	
	public void hasNowMoved()
	{
		hasMoved = true;
	}
	
	public boolean isWhite()
	{
		if (id < 8)
			return true;
		return false;
	}
	
	public boolean isVulnerableToEP() {
		return vulnerableToEP;
	}
	
	public void setVulnerableToEP(boolean isVulnerable) {
		vulnerableToEP = isVulnerable;
	}

}
