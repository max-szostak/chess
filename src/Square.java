
public class Square {
	
	private Piece piece = null;
	private int row, col;
	
	public Square(Piece pieceInit, int rowInit, int colInit)
	{
		piece = pieceInit;
		row = rowInit;
		col = colInit;
	}
	
	public boolean hasPiece()
	{
		if (piece == null)
			return false;
		return true;
	}
	
	public Piece getPiece()
	{
		return piece;
	}
	
	public int getRow()
	{
		return row;
	}
	
	public int getCol()
	{
		return col;
	}
	
	public void removePiece()
	{
		piece = null;
	}
	
	public void setPiece(Piece newPiece)
	{
		piece = newPiece;
	}

}
