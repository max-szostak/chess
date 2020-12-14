
public class Knight extends Piece{
	
	public Knight(int idInit)
	{
		super(idInit);
		name = 'N';
		if (idInit < 2)
			imgIndex = 4;
		else
			imgIndex = 5;
	}

}
