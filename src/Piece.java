import java.util.*;

public class Piece {
	
	public int x, y, id, imgIndex;
	public char name;
	public boolean moved;
	
	public Piece(int idInit)
	{
		id = idInit;
		moved = false;
	}
	
	public char getName()
	{
		return name;
	}
	
	public int getId()
	{
		return id;
	}
	
	public boolean isWhite()
	{
		if (id < 2)
			return true;
		return false;
	}
	
	public boolean hasMoved()
	{
		return moved;
	}
	
	public void setMoved()
	{
		moved = true;
	}
	
	public int getImgIndex()
	{
		return imgIndex;
	}
	
	//en passant methods that are only used by pawns
	public void setVulnerableToEP(boolean isVulnerable) {}
	public boolean isVulnerableToEP() {
		return false;
	}

	
}
