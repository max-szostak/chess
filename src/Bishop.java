
public class Bishop extends Piece{
	
	public Bishop(int idInit)
	{
		super(idInit);
		name = 'B';
		if (idInit < 2)
			imgIndex = 6;
		else
			imgIndex = 7;
	}

}
