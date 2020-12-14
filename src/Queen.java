
public class Queen extends Piece{
	
	public Queen(int idInit)
	{
		super(idInit);
		name = 'Q';
		if (idInit < 1)
			imgIndex = 10;
		else
			imgIndex = 11;
	}
	
	public boolean isWhite()
	{
		if (id == 0)
			return true;
		return false;
	}

}
