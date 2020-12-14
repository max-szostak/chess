
public class Rook extends Piece{
	
	public Rook(int idInit)
	{
		super(idInit);
		name = 'R';
		if (idInit < 2)
			imgIndex = 2;
		else
			imgIndex = 3;
	}

}
