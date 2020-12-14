
public class King extends Piece{
	
	private boolean moved;
	
	public King(int idInit)
	{
		super(idInit);
		name = 'K';
		if (idInit < 1)
			imgIndex = 8;
		else
			imgIndex = 9;
	}
	
	public boolean isWhite()
	{
		if (id == 0)
			return true;
		return false;
	}

}
