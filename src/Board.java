import java.applet.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class Board extends Applet implements MouseListener{
	
	private final int SQUARE_SIZE = 60;
	private boolean hasClicked = false, whiteToMove = true;
	private ArrayList<Integer> highlighted = new ArrayList<Integer>();
	private ArrayList<Integer> blackPieces = new ArrayList<Integer>();
	private ArrayList<Integer> whitePieces = new ArrayList<Integer>();
	public Square[][] squares = new Square[8][8];
	private int prevRow = 0, prevCol = 0, epSquare = 0;
	private Piece clickedPiece = new Piece(0);
	private String whoseMove = new String();
	private Image[] images;
	
	public void init()
	{
		setSize(8 * SQUARE_SIZE, 8 * SQUARE_SIZE + 100);//extra hundred pixels are for button/dialog box
		setBackground(new Color(160, 160, 160));
		addMouseListener(this);
		
		//white pieces
		squares[0][0] = new Square(new Rook(0), 0, 0);
		squares[0][1] = new Square(new Knight(0), 0, 1);
		squares[0][2] = new Square(new Bishop(0), 0, 2);
		squares[0][3] = new Square(new King(0), 0, 3);
		squares[0][4] = new Square(new Queen(0), 0, 4);
		squares[0][5] = new Square(new Bishop(1), 0, 5);
		squares[0][6] = new Square(new Knight(1), 0, 6);
		squares[0][7] = new Square(new Rook(1), 0, 7);
		
		whitePieces.add(0);
		whitePieces.add(1);
		whitePieces.add(2);
		whitePieces.add(3);
		whitePieces.add(4);
		whitePieces.add(5);
		whitePieces.add(6);
		whitePieces.add(7);	

		//black pieces
		squares[7][0] = new Square(new Rook(2), 7, 0);
		squares[7][1] = new Square(new Knight(2), 7, 1);
		squares[7][2] = new Square(new Bishop(2), 7, 2);
		squares[7][3] = new Square(new King(1), 7, 3);
		squares[7][4] = new Square(new Queen(1), 7, 4);
		squares[7][5] = new Square(new Bishop(3), 7, 5);
		squares[7][6] = new Square(new Knight(3), 7, 6);
		squares[7][7] = new Square(new Rook(3), 7, 7);
		
		blackPieces.add(70);
		blackPieces.add(71);
		blackPieces.add(72);
		blackPieces.add(73);
		blackPieces.add(74);
		blackPieces.add(75);
		blackPieces.add(76);
		blackPieces.add(77);
		
		for (int col = 0; col < 8; col++)//black pawn
		{
			blackPieces.add(60 + col);
			squares[6][col] = new Square(new Pawn(col + 8), 6, col);
		}
		
		for (int col = 0; col < 8; col++)//white pawns
		{
			whitePieces.add(10 + col);
			squares[1][col] = new Square(new Pawn(col), 1, col);
		}
		
		for (int row = 2; row < 6; row++)//blank squares
			for (int col = 0; col < 8; col++)
				squares[row][col] = new Square(null, row, col);
		
		images = new Image[12];//array of web images for ease of access later (taken from wikimedia)
		images[0] = getImage(getDocumentBase(), ("https://upload.wikimedia.org/wikipedia/commons/0/04/Chess_plt60.png"));//white pawn
		images[1] = getImage(getDocumentBase(), ("https://upload.wikimedia.org/wikipedia/commons/c/cd/Chess_pdt60.png"));//black pawn
		images[2] = getImage(getDocumentBase(), ("https://upload.wikimedia.org/wikipedia/commons/5/5c/Chess_rlt60.png"));//white rook
		images[3] = getImage(getDocumentBase(), ("https://upload.wikimedia.org/wikipedia/commons/a/a0/Chess_rdt60.png"));//black rook
		images[4] = getImage(getDocumentBase(), ("https://upload.wikimedia.org/wikipedia/commons/2/28/Chess_nlt60.png"));//white knight
		images[5] = getImage(getDocumentBase(), ("https://upload.wikimedia.org/wikipedia/commons/f/f1/Chess_ndt60.png"));//black knight
		images[6] = getImage(getDocumentBase(), ("https://upload.wikimedia.org/wikipedia/commons/9/9b/Chess_blt60.png"));//white bishop
		images[7] = getImage(getDocumentBase(), ("https://upload.wikimedia.org/wikipedia/commons/8/81/Chess_bdt60.png"));//black bishop
		images[8] = getImage(getDocumentBase(), ("https://upload.wikimedia.org/wikipedia/commons/3/3b/Chess_klt60.png"));//white king
		images[9] = getImage(getDocumentBase(), ("https://upload.wikimedia.org/wikipedia/commons/e/e3/Chess_kdt60.png"));//black king
		images[10] = getImage(getDocumentBase(), ("https://upload.wikimedia.org/wikipedia/commons/4/49/Chess_qlt60.png"));//white queen
		images[11] = getImage(getDocumentBase(), ("https://upload.wikimedia.org/wikipedia/commons/a/af/Chess_qdt60.png"));//black queen
	}
	
	public void paint(Graphics page)
	{
		page.setColor(Color.white);
		page.fillRect(0, SQUARE_SIZE * 8, SQUARE_SIZE * 8, 100);//button/dialog box
		page.setColor(Color.black);
		page.setFont(new Font("Monospaced", Font.BOLD, 20));
		if (whiteToMove)
			whoseMove = "White to move";
		else if (!whiteToMove)
			whoseMove = "Black to move";
		page.drawString(whoseMove, 20, SQUARE_SIZE * 8 + 30);
		
		page.setColor(new Color(0, 128, 0));//dark green
		for (int row = 0; row < 7; row += 2)//draws half of squares
			for (int col = 1; col < 8; col += 2)
				page.fillRect(SQUARE_SIZE * col, SQUARE_SIZE * row, SQUARE_SIZE, SQUARE_SIZE);
		
		for (int row = 1; row < 8; row += 2)//draws other half of squares
			for (int col = 0; col < 7; col += 2)
				page.fillRect(SQUARE_SIZE * col, SQUARE_SIZE * row, SQUARE_SIZE, SQUARE_SIZE);
		
		for (int row = 0; row < 8; row++)//drawing the pieces
		{
			for (int col = 0;  col < 8; col++)
			{
				if (squares[row][col].hasPiece())
				{
					page.drawImage(images[squares[row][col].getPiece().getImgIndex()], SQUARE_SIZE * col, 
							SQUARE_SIZE * row, this);
				}
			}
		}
		
		for (int i = 0; i < highlighted.size(); i++)//draws highlighted squares
		{
			page.setColor(new Color(255, 0, 0, 127));
			int row = highlighted.get(i) / 10 * SQUARE_SIZE;
			int col = highlighted.get(i) % 10 * SQUARE_SIZE;
			page.fillRect(col, row, SQUARE_SIZE, SQUARE_SIZE);
		}
	}
	
	public ArrayList<Integer> findMoves(Square chosen)
	{
		ArrayList<Integer> moves = new ArrayList<Integer>();
		if (!chosen.hasPiece())
			return moves;
		else
		{
			if (chosen.getPiece().getName() == 'R')//if the piece is a rook
			{
				up : for (int row = chosen.getRow() - 1; row >= 0; row--)//going up the board vertically from the rook
				{
					if (!squares[row][chosen.getCol()].hasPiece())//add the square if it's empty
						moves.add(row * 10 + chosen.getCol());
					else if (chosen.getPiece().isWhite())//if the rook is white
					{
						if (!squares[row][chosen.getCol()].getPiece().isWhite())//add the square if it contains a black piece
						{
							moves.add(row * 10 + chosen.getCol());
							break up;
						}
						else//if the piece is not black (if it's white)
						{
							break up;//stop adding squares
						}
					}
					else if (!chosen.getPiece().isWhite())//if the rook is black
					{
						if (squares[row][chosen.getCol()].getPiece().isWhite())//add the square if it contains a white piece
						{
							moves.add(row * 10 + chosen.getCol());
							break up;
						}		
						else//if the piece is not white (if it's black)
							break up;//stop adding squares
					}
				}
				down : for (int row = chosen.getRow() + 1; row < 8; row++)//going down the board vertically from the rook
				{
					if (!squares[row][chosen.getCol()].hasPiece())//add the square if it's empty
						moves.add(row * 10 + chosen.getCol());
					else if (chosen.getPiece().isWhite())//if the rook is white
					{
						if (!squares[row][chosen.getCol()].getPiece().isWhite())//add the square if it contains a black piece
						{
							moves.add(row * 10 + chosen.getCol());
							break down;
						}
						else//if the piece is not black (if it's white)
							break down;//stop adding squares
					}
					else if (!chosen.getPiece().isWhite())//if the rook is black
					{
						if (squares[row][chosen.getCol()].getPiece().isWhite())//add the square if it contains a white piece
						{
							moves.add(row * 10 + chosen.getCol());
							break down;
						}		
						else//if the piece is not white (if it's black)
							break down;//stop adding squares
					}
				}
				left : for (int col = chosen.getCol() - 1; col >= 0; col--)//going to the left of the rook
				{
					if (!squares[chosen.getRow()][col].hasPiece())//add the square if it's empty
						moves.add(chosen.getRow() * 10 + col);
					else if (chosen.getPiece().isWhite())//if the rook is white
					{
						if (!squares[chosen.getRow()][col].getPiece().isWhite())//add the square if it contains a black piece
						{
							moves.add(chosen.getRow() * 10 + col);
							break left;
						}
						else//if the piece is not black (if it's white)
							break left;//stop adding squares
					}
					else if (!chosen.getPiece().isWhite())//if the rook is black
					{
						if (squares[chosen.getRow()][col].getPiece().isWhite())//add the square if it contains a white piece
						{
							moves.add(chosen.getRow() * 10 + col);
							break left;
						}		
						else//if the piece is not white (if it's black)
							break left;//stop adding squares
					}
				}
				right : for (int col = chosen.getCol() + 1; col < 8; col++)//going to the right of the rook
				{
					if (!squares[chosen.getRow()][col].hasPiece())//add the square if it's empty
						moves.add(chosen.getRow() * 10 + col);
					else if (chosen.getPiece().isWhite())//if the rook is white
					{
						if (!squares[chosen.getRow()][col].getPiece().isWhite())//add the square if it contains a black piece
						{
							moves.add(chosen.getRow() * 10 + col);
							break right;
						}
						else//if the piece is not black (if it's white)
							break right;//stop adding squares
					}
					else if (!chosen.getPiece().isWhite())//if the rook is black
					{
						if (squares[chosen.getRow()][col].getPiece().isWhite())//add the square if it contains a white piece
						{
							moves.add(chosen.getRow() * 10 + col);
							break;
						}		
						else//if the piece is not white (if it's black)
							break;//stop adding squares
					}
				}
			}
			else if (chosen.getPiece().getName() == 'N')//if the piece is a knight
			{
				if (chosen.getRow() - 2 >= 0)//up 2 left/right 1
				{
					if (chosen.getCol() - 1 >= 0)//up 2 left 1
					{
						Square square = new Square(null, 0, 0);
						square = squares[chosen.getRow() - 2][chosen.getCol() - 1];
						if (!square.hasPiece())//add the square if it is empty
							moves.add((chosen.getRow() - 2) * 10 + chosen.getCol() - 1);
						else if (chosen.getPiece().isWhite())//if the knight is white
						{
							if (!square.getPiece().isWhite())//add the square if it contains a black piece
								moves.add((chosen.getRow() - 2) * 10 + chosen.getCol() - 1);
						}
						else if (!chosen.getPiece().isWhite())//if the knight is not white (if it's black)
						{
							if (square.getPiece().isWhite())//add the square if it contains a white piece
								moves.add((chosen.getRow() - 2) * 10 + chosen.getCol() - 1);
						}
					}
					if (chosen.getCol() + 1 < 8)//up 2 right 1
					{
						Square square = new Square(null, 0, 0);
						square = squares[chosen.getRow() - 2][chosen.getCol() + 1];
						if (!square.hasPiece())//add the square if it is empty
							moves.add((chosen.getRow() - 2) * 10 + chosen.getCol() + 1);
						else if (chosen.getPiece().isWhite())//if the knight is white
						{
							if (!square.getPiece().isWhite())//add the square if it contains a black piece
								moves.add((chosen.getRow() - 2) * 10 + chosen.getCol() + 1);
						}
						else if (!chosen.getPiece().isWhite())//if the knight is not white (if it's black)
						{
							if (square.getPiece().isWhite())//add the square if it contains a white piece
								moves.add((chosen.getRow() - 2) * 10 + chosen.getCol() + 1);
						}
					}
				}
				if (chosen.getRow() - 1 >= 0)//up 1 left/right 2
				{
					if (chosen.getCol() - 2 >= 0)//up 1 left 2
					{
						Square square = new Square(null, 0, 0);
						square = squares[chosen.getRow() - 1][chosen.getCol() - 2];
						if (!square.hasPiece())//add the square if it is empty
							moves.add((chosen.getRow() - 1) * 10 + chosen.getCol() - 2);
						else if (chosen.getPiece().isWhite())//if the knight is white
						{
							if (!square.getPiece().isWhite())//add the square if it contains a black piece
								moves.add((chosen.getRow() - 1) * 10 + chosen.getCol() - 2);
						}
						else if (!chosen.getPiece().isWhite())//if the knight is not white (if it's black)
						{
							if (square.getPiece().isWhite())//add the square if it contains a white piece
								moves.add((chosen.getRow() - 1) * 10 + chosen.getCol() - 2);
						}
					}
					if (chosen.getCol() + 2 < 8)//up 1 right 2
					{
						Square square = new Square(null, 0, 0);
						square = squares[chosen.getRow() - 1][chosen.getCol() + 2];
						if (!square.hasPiece())//add the square if it is empty
							moves.add((chosen.getRow() - 1) * 10 + chosen.getCol() + 2);
						else if (chosen.getPiece().isWhite())//if the knight is white
						{
							if (!square.getPiece().isWhite())//add the square if it contains a black piece
								moves.add((chosen.getRow() - 1) * 10 + chosen.getCol() + 2);
						}
						else if (!chosen.getPiece().isWhite())//if the knight is not white (if it's black)
						{
							if (square.getPiece().isWhite())//add the square if it contains a white piece
								moves.add((chosen.getRow() - 1) * 10 + chosen.getCol() + 2);
						}
					}
				}
				if (chosen.getRow() + 1 < 8)//down 1 left/right 2
				{
					if (chosen.getCol() - 2 >= 0)//down 1 left 2
					{
						Square square = new Square(null, 0, 0);
						square = squares[chosen.getRow() + 1][chosen.getCol() - 2];
						if (!square.hasPiece())//add the square if it is empty
							moves.add((chosen.getRow() + 1) * 10 + chosen.getCol() - 2);
						else if (chosen.getPiece().isWhite())//if the knight is white
						{
							if (!square.getPiece().isWhite())//add the square if it contains a black piece
								moves.add((chosen.getRow() + 1) * 10 + chosen.getCol() - 2);
						}
						else if (!chosen.getPiece().isWhite())//if the knight is not white (if it's black)
						{
							if (square.getPiece().isWhite())//add the square if it contains a white piece
								moves.add((chosen.getRow() + 1) * 10 + chosen.getCol() - 2);
						}
					}
					if (chosen.getCol() + 2 < 8)//down 1 right 2
					{
						Square square = new Square(null, 0, 0);
						square = squares[chosen.getRow() + 1][chosen.getCol() + 2];
						if (!square.hasPiece())//add the square if it is empty
							moves.add((chosen.getRow() + 1) * 10 + chosen.getCol() + 2);
						else if (chosen.getPiece().isWhite())//if the knight is white
						{
							if (!square.getPiece().isWhite())//add the square if it contains a black piece
								moves.add((chosen.getRow() + 1) * 10 + chosen.getCol() + 2);
						}
						else if (!chosen.getPiece().isWhite())//if the knight is not white (if it's black)
						{
							if (square.getPiece().isWhite())//add the square if it contains a white piece
								moves.add((chosen.getRow() + 1) * 10 + chosen.getCol() + 2);
						}
					}
				}
				if (chosen.getRow() + 2 < 8)//down 2 left/right 1
				{
					if (chosen.getCol() - 1 >= 0)//down 2 left 1
					{
						Square square = new Square(null, 0, 0);
						square = squares[chosen.getRow() + 2][chosen.getCol() - 1];
						if (!square.hasPiece())//add the square if it is empty
							moves.add((chosen.getRow() + 2) * 10 + chosen.getCol() - 1);
						else if (chosen.getPiece().isWhite())//if the knight is white
						{
							if (!square.getPiece().isWhite())//add the square if it contains a black piece
								moves.add((chosen.getRow() + 2) * 10 + chosen.getCol() - 1);
						}
						else if (!chosen.getPiece().isWhite())//if the knight is not white (if it's black)
						{
							if (square.getPiece().isWhite())//add the square if it contains a white piece
								moves.add((chosen.getRow() + 2) * 10 + chosen.getCol() - 1);
						}
					}
					if (chosen.getCol() + 1 < 8)//down 2 right 1
					{
						Square square = new Square(null, 0, 0);
						square = squares[chosen.getRow() + 2][chosen.getCol() + 1];
						if (!square.hasPiece())//add the square if it is empty
							moves.add((chosen.getRow() + 2) * 10 + chosen.getCol() + 1);
						else if (chosen.getPiece().isWhite())//if the knight is white
						{
							if (!square.getPiece().isWhite())//add the square if it contains a black piece
								moves.add((chosen.getRow() + 2) * 10 + chosen.getCol() + 1);
						}
						else if (!chosen.getPiece().isWhite())//if the knight is not white (if it's black)
						{
							if (square.getPiece().isWhite())//add the square if it contains a white piece
								moves.add((chosen.getRow() + 2) * 10 + chosen.getCol() + 1);
						}
					}
				}
			}
			else if (chosen.getPiece().getName() == 'B')//if the piece is a bishop
			{
				int countDiagonal = 1;//how far up/down and left/right to check
				boolean con = chosen.getRow() - countDiagonal >= 0 && chosen.getCol() - countDiagonal >= 0;//whether or not to continue checking
				upLeft : while(con)//going up and left
				{
					Square square = new Square(null, 0, 0);
					square = squares[chosen.getRow() - countDiagonal][chosen.getCol() - countDiagonal];
					if (!square.hasPiece())//if the square is empty
						moves.add((chosen.getRow() - countDiagonal) * 10 + chosen.getCol() - countDiagonal);
					else if (chosen.getPiece().isWhite())//if the bishop is white
						if (!square.getPiece().isWhite())//add the square if it contains a black piece
						{
							moves.add((chosen.getRow() - countDiagonal) * 10 + chosen.getCol() - countDiagonal);
							break upLeft;
						}
						else 
							break upLeft;
					else if (!chosen.getPiece().isWhite())//if the bishop is not white (if it's black)
						if (square.getPiece().isWhite())//add the square if it contains a white piece
						{
							moves.add((chosen.getRow() - countDiagonal) * 10 + chosen.getCol() - countDiagonal);
							break upLeft;
						}
						else 
							break upLeft;
					countDiagonal++;
					con = chosen.getRow() - countDiagonal >= 0 && chosen.getCol() - countDiagonal >= 0;
				}
				countDiagonal = 1;
				con = chosen.getRow() - countDiagonal >= 0 && chosen.getCol() + countDiagonal < 8;
				upRight : while (con)//going up and right
				{
					Square square = new Square(null, 0, 0);
					square = squares[chosen.getRow() - countDiagonal][chosen.getCol() + countDiagonal];
					if (!square.hasPiece())//if the square is empty
						moves.add((chosen.getRow() - countDiagonal) * 10 + chosen.getCol() + countDiagonal);
					else if (chosen.getPiece().isWhite())//if the bishop is white
						if (!square.getPiece().isWhite())//add the square if it contains a black piece
						{
							moves.add((chosen.getRow() - countDiagonal) * 10 + chosen.getCol() + countDiagonal);
							break upRight;
						}
						else
							break upRight;
					else if (!chosen.getPiece().isWhite())//if the bishop is not white (if it's black)
						if (square.getPiece().isWhite())//add the square if it contains a white piece
						{
							moves.add((chosen.getRow() - countDiagonal) * 10 + chosen.getCol() + countDiagonal);
							break upRight;
						}
						else 
							break upRight;
					countDiagonal++;
					con = chosen.getRow() - countDiagonal >= 0 && chosen.getCol() + countDiagonal < 8;
				}
				countDiagonal = 1;
				con = chosen.getRow() + countDiagonal < 8 && chosen.getCol() - countDiagonal >= 0;
				downLeft : while (con)//going down and left
				{
					Square square = new Square(null, 0, 0);
					square = squares[chosen.getRow() + countDiagonal][chosen.getCol() - countDiagonal];
					if (!square.hasPiece())//if the square is empty
						moves.add((chosen.getRow() + countDiagonal) * 10 + chosen.getCol() - countDiagonal);
					else if (chosen.getPiece().isWhite())//if the bishop is white
						if (!square.getPiece().isWhite())//add the square if it contains a black piece
						{
							moves.add((chosen.getRow() + countDiagonal) * 10 + chosen.getCol() - countDiagonal);
							break downLeft;
						}
						else
							break downLeft;
					else if (!chosen.getPiece().isWhite())//if the bishop is not white (if it's black)
						if (square.getPiece().isWhite())//add the square if it contains a white piece
						{
							moves.add((chosen.getRow() + countDiagonal) * 10 + chosen.getCol() - countDiagonal);
							break downLeft;
						}
						else
							break downLeft;
					countDiagonal++;
					con = chosen.getRow() + countDiagonal < 8 && chosen.getCol() - countDiagonal >= 0;
				}
				countDiagonal = 1;
				con = chosen.getRow() + countDiagonal < 8 && chosen.getCol() + countDiagonal < 8;
				downRight : while (con)//going down and right
				{
					Square square = new Square(null, 0, 0);
					square = squares[chosen.getRow() + countDiagonal][chosen.getCol() + countDiagonal];
					if (!square.hasPiece())//if the square is empty
						moves.add((chosen.getRow() + countDiagonal) * 10 + chosen.getCol() + countDiagonal);
					else if (chosen.getPiece().isWhite())//if the bishop is white
						if (!square.getPiece().isWhite())//add the square if it contains a black piece
						{
							moves.add((chosen.getRow() + countDiagonal) * 10 + chosen.getCol() + countDiagonal);
							break downRight;
						}
						else
							break downRight;
					else if (!chosen.getPiece().isWhite())//if the bishop is not white (if it's black)
						if (square.getPiece().isWhite())//add the square if it contains a white piece
						{
							moves.add((chosen.getRow() + countDiagonal) * 10 + chosen.getCol() + countDiagonal);
							break downRight;
						}
						else
							break downRight;
					countDiagonal++;
					con = chosen.getRow() + countDiagonal < 8 && chosen.getCol() + countDiagonal < 8;
				}
			}
			else if (chosen.getPiece().getName() == 'Q')
			{
				//adding the diagonals
				int countDiagonal = 1;//how far up/down and left/right to check
				boolean con = chosen.getRow() - countDiagonal >= 0 && chosen.getCol() - countDiagonal >= 0;//whether or not to continue checking
				upLeft : while(con)//going up and left
				{
					Square square = new Square(null, 0, 0);
					square = squares[chosen.getRow() - countDiagonal][chosen.getCol() - countDiagonal];
					if (!square.hasPiece())//if the square is empty
						moves.add((chosen.getRow() - countDiagonal) * 10 + chosen.getCol() - countDiagonal);
					else if (chosen.getPiece().isWhite())//if the queen is white
						if (!square.getPiece().isWhite())//add the square if it contains a black piece
						{
							moves.add((chosen.getRow() - countDiagonal) * 10 + chosen.getCol() - countDiagonal);
							break upLeft;
						}
						else 
							break upLeft;
					else if (!chosen.getPiece().isWhite())//if the queen is not white (if it's black)
						if (square.getPiece().isWhite())//add the square if it contains a white piece
						{
							moves.add((chosen.getRow() - countDiagonal) * 10 + chosen.getCol() - countDiagonal);
							break upLeft;
						}
						else 
							break upLeft;
					countDiagonal++;
					con = chosen.getRow() - countDiagonal >= 0 && chosen.getCol() - countDiagonal >= 0;
				}
				countDiagonal = 1;
				con = chosen.getRow() - countDiagonal >= 0 && chosen.getCol() + countDiagonal < 8;
				upRight : while (con)//going up and right
				{
					Square square = new Square(null, 0, 0);
					square = squares[chosen.getRow() - countDiagonal][chosen.getCol() + countDiagonal];
					if (!square.hasPiece())//if the square is empty
						moves.add((chosen.getRow() - countDiagonal) * 10 + chosen.getCol() + countDiagonal);
					else if (chosen.getPiece().isWhite())//if the queen is white
						if (!square.getPiece().isWhite())//add the square if it contains a black piece
						{
							moves.add((chosen.getRow() - countDiagonal) * 10 + chosen.getCol() + countDiagonal);
							break upRight;
						}
						else
							break upRight;
					else if (!chosen.getPiece().isWhite())//if the queen is not white (if it's black)
						if (square.getPiece().isWhite())//add the square if it contains a white piece
						{
							moves.add((chosen.getRow() - countDiagonal) * 10 + chosen.getCol() + countDiagonal);
							break upRight;
						}
						else 
							break upRight;
					countDiagonal++;
					con = chosen.getRow() - countDiagonal >= 0 && chosen.getCol() + countDiagonal < 8;
				}
				countDiagonal = 1;
				con = chosen.getRow() + countDiagonal < 8 && chosen.getCol() - countDiagonal >= 0;
				downLeft : while (con)//going down and left
				{
					Square square = new Square(null, 0, 0);
					square = squares[chosen.getRow() + countDiagonal][chosen.getCol() - countDiagonal];
					if (!square.hasPiece())//if the square is empty
						moves.add((chosen.getRow() + countDiagonal) * 10 + chosen.getCol() - countDiagonal);
					else if (chosen.getPiece().isWhite())//if the queen is white
						if (!square.getPiece().isWhite())//add the square if it contains a black piece
						{
							moves.add((chosen.getRow() + countDiagonal) * 10 + chosen.getCol() - countDiagonal);
							break downLeft;
						}
						else
							break downLeft;
					else if (!chosen.getPiece().isWhite())//if the queen is not white (if it's black)
						if (square.getPiece().isWhite())//add the square if it contains a white piece
						{
							moves.add((chosen.getRow() + countDiagonal) * 10 + chosen.getCol() - countDiagonal);
							break downLeft;
						}
						else
							break downLeft;
					countDiagonal++;
					con = chosen.getRow() + countDiagonal < 8 && chosen.getCol() - countDiagonal >= 0;
				}
				countDiagonal = 1;
				con = chosen.getRow() + countDiagonal < 8 && chosen.getCol() + countDiagonal < 8;
				downRight : while (con)//going down and right
				{
					Square square = new Square(null, 0, 0);
					square = squares[chosen.getRow() + countDiagonal][chosen.getCol() + countDiagonal];
					if (!square.hasPiece())//if the square is empty
						moves.add((chosen.getRow() + countDiagonal) * 10 + chosen.getCol() + countDiagonal);
					else if (chosen.getPiece().isWhite())//if the queen is white
						if (!square.getPiece().isWhite())//add the square if it contains a black piece
						{
							moves.add((chosen.getRow() + countDiagonal) * 10 + chosen.getCol() + countDiagonal);
							break downRight;
						}
						else
							break downRight;
					else if (!chosen.getPiece().isWhite())//if the queen is not white (if it's black)
						if (square.getPiece().isWhite())//add the square if it contains a white piece
						{
							moves.add((chosen.getRow() + countDiagonal) * 10 + chosen.getCol() + countDiagonal);
							break downRight;
						}
						else
							break downRight;
					countDiagonal++;
					con = chosen.getRow() + countDiagonal < 8 && chosen.getCol() + countDiagonal < 8;
				}
				//adding the vertical/horizontals
				up : for (int row = chosen.getRow() - 1; row >= 0; row--)//going up the board vertically from the queen
				{
					if (!squares[row][chosen.getCol()].hasPiece())//add the square if it's empty
						moves.add(row * 10 + chosen.getCol());
					else if (chosen.getPiece().isWhite())//if the queen is white
					{
						if (!squares[row][chosen.getCol()].getPiece().isWhite())//add the square if it contains a black piece
						{
							moves.add(row * 10 + chosen.getCol());
							break up;
						}
						else//if the piece is not black (if it's white)
						{
							break up;//stop adding squares
						}
					}
					else if (!chosen.getPiece().isWhite())//if the queen is black
					{
						if (squares[row][chosen.getCol()].getPiece().isWhite())//add the square if it contains a white piece
						{
							moves.add(row * 10 + chosen.getCol());
							break up;
						}		
						else//if the piece is not white (if it's black)
							break up;//stop adding squares
					}
				}
				down : for (int row = chosen.getRow() + 1; row < 8; row++)//going down the board vertically from the queen
				{
					if (!squares[row][chosen.getCol()].hasPiece())//add the square if it's empty
						moves.add(row * 10 + chosen.getCol());
					else if (chosen.getPiece().isWhite())//if the queen is white
					{
						if (!squares[row][chosen.getCol()].getPiece().isWhite())//add the square if it contains a black piece
						{
							moves.add(row * 10 + chosen.getCol());
							break down;
						}
						else//if the piece is not black (if it's white)
							break down;//stop adding squares
					}
					else if (!chosen.getPiece().isWhite())//if the queen is black
					{
						if (squares[row][chosen.getCol()].getPiece().isWhite())//add the square if it contains a white piece
						{
							moves.add(row * 10 + chosen.getCol());
							break down;
						}		
						else//if the piece is not white (if it's black)
							break down;//stop adding squares
					}
				}
				left : for (int col = chosen.getCol() - 1; col >= 0; col--)//going to the left of the queen
				{
					if (!squares[chosen.getRow()][col].hasPiece())//add the square if it's empty
						moves.add(chosen.getRow() * 10 + col);
					else if (chosen.getPiece().isWhite())//if the queen is white
					{
						if (!squares[chosen.getRow()][col].getPiece().isWhite())//add the square if it contains a black piece
						{
							moves.add(chosen.getRow() * 10 + col);
							break left;
						}
						else//if the piece is not black (if it's white)
							break left;//stop adding squares
					}
					else if (!chosen.getPiece().isWhite())//if the queen is black
					{
						if (squares[chosen.getRow()][col].getPiece().isWhite())//add the square if it contains a white piece
						{
							moves.add(chosen.getRow() * 10 + col);
							break left;
						}		
						else//if the piece is not white (if it's black)
							break left;//stop adding squares
					}
				}
				right : for (int col = chosen.getCol() + 1; col < 8; col++)//going to the right of the queen
				{
					if (!squares[chosen.getRow()][col].hasPiece())//add the square if it's empty
						moves.add(chosen.getRow() * 10 + col);
					else if (chosen.getPiece().isWhite())//if the queen is white
					{
						if (!squares[chosen.getRow()][col].getPiece().isWhite())//add the square if it contains a black piece
						{
							moves.add(chosen.getRow() * 10 + col);
							break right;
						}
						else//if the piece is not black (if it's white)
							break right;//stop adding squares
					}
					else if (!chosen.getPiece().isWhite())//if the queen is black
					{
						if (squares[chosen.getRow()][col].getPiece().isWhite())//add the square if it contains a white piece
						{
							moves.add(chosen.getRow() * 10 + col);
							break;
						}		
						else//if the piece is not white (if it's black)
							break;//stop adding squares
					}
				}
			}
			else if (chosen.getPiece().getName() == 'K')
			{
				ArrayList<Integer> pieceMoves = new ArrayList<Integer>(); 
				ArrayList<Integer> otherKingMoves = new ArrayList<Integer>();
				for (int row = 0; row < 8; row++)//finding and adding the other king to add its squares separately, to avoid infinite recusrsion later
				{
					for (int col = 0; col < 8; col++)
					{
						if (squares[row][col].hasPiece())
						{
							Piece piece = new Piece(0);
							piece = squares[row][col].getPiece();
							if (piece.getName() == 'K')
							{
								if (chosen.getPiece().isWhite())
								{
									if (!piece.isWhite())
									{
										if (row - 1 >= 0)//upper row
										{
											if (col - 1 >= 0)
												otherKingMoves.add((row - 1) * 10 + col - 1);//upper left
											otherKingMoves.add((row - 1) * 10 + col);//upper center
											if (col + 1 < 8)
												otherKingMoves.add((row - 1) * 10 + col + 1);//upper right
										}
										if (col - 1 >= 0)
											otherKingMoves.add(row * 10 + col - 1);//center left
										if (col + 1 < 8)
											otherKingMoves.add(row * 10 + col + 1);//center right
										if (row + 1 < 8)
										{
											if (col - 1 >= 0)
												otherKingMoves.add((row + 1) * 10 + col - 1);//lower left
											otherKingMoves.add((row + 1) * 10 + col);//lower center
											if (col + 1 < 8)
												otherKingMoves.add((row + 1) * 10 + col + 1);//lower right
										}
									}
									else if (!chosen.getPiece().isWhite())
									{
										if (piece.isWhite())
										{
											if (row - 1 >= 0)//upper row
											{
												if (col - 1 >= 0)
													otherKingMoves.add((row - 1) * 10 + col - 1);//upper left
												otherKingMoves.add((row - 1) * 10 + col);//upper center
												if (col + 1 < 8)
													otherKingMoves.add((row - 1) * 10 + col + 1);//upper right
											}
											if (col - 1 >= 0)
												otherKingMoves.add(row * 10 + col - 1);//center left
											if (col + 1 < 8)
												otherKingMoves.add(row * 10 + col + 1);//center right
											if (row + 1 < 8)
											{
												if (col - 1 >= 0)
													otherKingMoves.add((row + 1) * 10 + col - 1);//lower left
												otherKingMoves.add((row + 1) * 10 + col);//lower center
												if (col + 1 < 8)
													otherKingMoves.add((row + 1) * 10 + col + 1);//lower right
											}
										}
									}
								}
							}
						}
					}
				}
				Square square = new Square(null, 0, 0);//temporary square for each of the 8 possible moves
				boolean isChecked = false;//boolean that stores true if evaluated square is checked
				if (chosen.getRow() - 1 >= 0)//upper row
				{
					if (chosen.getCol() - 1 >= 0)//upper left
					{
						square = squares[chosen.getRow() - 1][chosen.getCol() - 1];
						squares[chosen.getRow() - 1][chosen.getCol() - 1] = new Square(new King(chosen.getPiece().getId()), square.getRow(), square.getCol());
						//^temporarily sets evaluated square to the king to see if it would be checked if it moved there
						if (!chosen.getPiece().isWhite())
						{
							for (int piece : whitePieces)
							{
								if (squares[piece / 10][piece % 10].getPiece().getName() != 'K')//to avoid infinite recursion
								{
									if (findMoves(squares[piece / 10][piece % 10]).contains((chosen.getRow() - 1) * 10 + chosen.getCol() - 1) 
											|| otherKingMoves.contains((chosen.getRow() - 1) * 10 + chosen.getCol() - 1))
									{
										isChecked = true;
										break;
									}
								}
							}
						}
						else if (chosen.getPiece().isWhite())
						{
							for (int piece : blackPieces)
								if (squares[piece / 10][piece % 10].getPiece().getName() != 'K')//to avoid infinite recursion
								{
									if (findMoves(squares[piece / 10][piece % 10]).contains((chosen.getRow() - 1) * 10 + chosen.getCol() - 1)
											|| otherKingMoves.contains((chosen.getRow() - 1) * 10 + chosen.getCol() - 1))
									{
										isChecked = true;
										break;
									}
								}
						}
						squares[chosen.getRow() - 1][chosen.getCol() - 1] = square;//reset the square to its original state
						if (!isChecked)//if the square is not checked
						{
							if (!square.hasPiece())//add the square if it's empty
								moves.add((chosen.getRow() - 1) * 10 + chosen.getCol() - 1);
							else if (chosen.getPiece().isWhite())//if the king is white
							{
								if (!square.getPiece().isWhite())//add the square if it contains a black piece
									moves.add((chosen.getRow() - 1) * 10 + chosen.getCol() - 1);
							}
							else if (!chosen.getPiece().isWhite())//if the king is black
							{
								if (square.getPiece().isWhite())//add the square if it contains a white piece
									moves.add((chosen.getRow() - 1) * 10 + chosen.getCol() - 1);
							}
						}	
					}
					isChecked = false;
					//upper center
					square = squares[chosen.getRow() - 1][chosen.getCol()];
					squares[chosen.getRow() - 1][chosen.getCol()] = new Square(new King(chosen.getPiece().getId()), square.getRow(), square.getCol());
					//^temporarily sets evaluated square to the king to see if it would be checked if it moved there
					if (!chosen.getPiece().isWhite())
					{
						for (int piece : whitePieces)
							if (squares[piece / 10][piece % 10].getPiece().getName() != 'K')//to avoid infinite recursion
							{
								if (findMoves(squares[piece / 10][piece % 10]).contains((chosen.getRow() - 1) * 10 + chosen.getCol())
										|| otherKingMoves.contains((chosen.getRow() - 1) * 10 + chosen.getCol()))
								{
									isChecked = true;
									break;
								}
							}
					}
					else
					{
						for (int piece : blackPieces)
							if (squares[piece / 10][piece % 10].getPiece().getName() != 'K')//to avoid infinite recursion
							{
								if (findMoves(squares[piece / 10][piece % 10]).contains((chosen.getRow() - 1) * 10 + chosen.getCol())
										|| otherKingMoves.contains((chosen.getRow() - 1) * 10 + chosen.getCol()))
								{
									isChecked = true;
									break;
								}
							}
					}
					squares[chosen.getRow() - 1][chosen.getCol()] = square;//reset the square to its original state
					if (!isChecked)//if the square is not checked
					{
						if (!square.hasPiece())//add the square if it's empty
							moves.add((chosen.getRow() - 1) * 10 + chosen.getCol());
						else if (chosen.getPiece().isWhite())//if the king is white
						{
							if (!square.getPiece().isWhite())//add the square if it contains a black piece
								moves.add((chosen.getRow() - 1) * 10 + chosen.getCol());
						}
						else if (!chosen.getPiece().isWhite())//if the king is black
						{
							if (square.getPiece().isWhite())//add the square if it contains a white piece
								moves.add((chosen.getRow() - 1) * 10 + chosen.getCol());
						}
					}
					isChecked = false;
					if (chosen.getCol() + 1 < 8)//upper right
					{
						square = squares[chosen.getRow() - 1][chosen.getCol() + 1];
						squares[chosen.getRow() - 1][chosen.getCol() + 1] = new Square(new King(chosen.getPiece().getId()), square.getRow(), square.getCol());
						//^temporarily sets evaluated square to the king to see if it would be checked if it moved there
						if (!chosen.getPiece().isWhite())
						{
							for (int piece : whitePieces)
								if (squares[piece / 10][piece % 10].getPiece().getName() != 'K')//to avoid infinite recursion
								{
									if (findMoves(squares[piece / 10][piece % 10]).contains((chosen.getRow() - 1) * 10 + chosen.getCol() + 1)
											|| otherKingMoves.contains((chosen.getRow() - 1) * 10 + chosen.getCol() + 1))
									{
										isChecked = true;
										break;
									}
								}
						}
						else
						{
							for (int piece : blackPieces)
								if (squares[piece / 10][piece % 10].getPiece().getName() != 'K')//to avoid infinite recursion
								{
									if (findMoves(squares[piece / 10][piece % 10]).contains((chosen.getRow() - 1) * 10 + chosen.getCol() + 1)
											|| otherKingMoves.contains((chosen.getRow() - 1) * 10 + chosen.getCol() + 1))
									{
										isChecked = true;
										break;
									}
								}
						}

						squares[chosen.getRow() - 1][chosen.getCol() + 1] = square;//reset the square to its original state
						if (!isChecked)//if the square is not checked
						{
							if (!square.hasPiece())//add the square if it's empty
								moves.add((chosen.getRow() - 1) * 10 + chosen.getCol() + 1);
							else if (chosen.getPiece().isWhite())//if the king is white
							{
								if (!square.getPiece().isWhite())//add the square if it contains a black piece
									moves.add((chosen.getRow() - 1) * 10 + chosen.getCol() + 1);
							}
							else if (!chosen.getPiece().isWhite())//if the king is black
							{
								if (square.getPiece().isWhite())//add the square if it contains a white piece
									moves.add((chosen.getRow() - 1) * 10 + chosen.getCol() + 1);
							}
						}
					}
				}
				isChecked = false;
				if (chosen.getRow() - 1 >= 0)//same row, left square
				{
					square = squares[chosen.getRow()][chosen.getCol() - 1];
					squares[chosen.getRow()][chosen.getCol() - 1] = new Square(new King(chosen.getPiece().getId()), square.getRow(), square.getCol());
					//^temporarily sets evaluated square to the king to see if it would be checked if it moved there
					if (!chosen.getPiece().isWhite())
					{
						for (int piece : whitePieces)
							if (squares[piece / 10][piece % 10].getPiece().getName() != 'K')//to avoid infinite recursion
							{
								if (findMoves(squares[piece / 10][piece % 10]).contains(chosen.getRow() * 10 + chosen.getCol() - 1)
										|| otherKingMoves.contains(chosen.getRow() * 10 + chosen.getCol() - 1))
								{
									isChecked = true;
									break;
								}
							}
					}
					else
					{
						for (int piece : blackPieces)
							if (squares[piece / 10][piece % 10].getPiece().getName() != 'K')//to avoid infinite recursion
							{
								if (findMoves(squares[piece / 10][piece % 10]).contains(chosen.getRow() * 10 + chosen.getCol() - 1)
										|| otherKingMoves.contains(chosen.getRow() * 10 + chosen.getCol() - 1))
								{
									isChecked = true;
									break;
								}
							}
					}
					squares[chosen.getRow()][chosen.getCol() - 1] = square;//reset the square to its original state
					if (!isChecked)//if the square is not checked
					{
						if (!square.hasPiece())//add the square if it's empty
							moves.add(chosen.getRow() * 10 + chosen.getCol() - 1);
						else if (chosen.getPiece().isWhite())//if the king is white
						{
							if (!square.getPiece().isWhite())//add the square if it contains a black piece
								moves.add(chosen.getRow() * 10 + chosen.getCol() - 1);
						}
						else if (!chosen.getPiece().isWhite())//if the king is black
						{
							if (square.getPiece().isWhite())//add the square if it contains a white piece
								moves.add(chosen.getRow() * 10 + chosen.getCol() - 1);
						}
					}
				}
				isChecked = false;
				if (chosen.getRow() + 1 < 8)//same row, right square
				{
					square = squares[chosen.getRow()][chosen.getCol() + 1];
					squares[chosen.getRow()][chosen.getCol() + 1] = new Square(new King(chosen.getPiece().getId()), square.getRow(), square.getCol());
					//^temporarily sets evaluated square to the king to see if it would be checked if it moved there	
					if (!chosen.getPiece().isWhite())
					{
						for (int piece : whitePieces)
							if (squares[piece / 10][piece % 10].getPiece().getName() != 'K')//to avoid infinite recursion
							{
								if (findMoves(squares[piece / 10][piece % 10]).contains(chosen.getRow() * 10 + chosen.getCol() + 1)
										|| otherKingMoves.contains(chosen.getRow() * 10 + chosen.getCol() + 1))
								{
									isChecked = true;
									break;
								}
							}
					}
					else
					{
						for (int piece : blackPieces)
							if (squares[piece / 10][piece % 10].getPiece().getName() != 'K')//to avoid infinite recursion
							{
								if (findMoves(squares[piece / 10][piece % 10]).contains(chosen.getRow() * 10 + chosen.getCol() + 1)
										|| otherKingMoves.contains(chosen.getRow() * 10 + chosen.getCol() + 1))
								{
									isChecked = true;
									break;
								}
							}
					}
					squares[chosen.getRow()][chosen.getCol() + 1] = square;//reset the square to its original state
					if (!isChecked)//if the square is not checked
					{
						System.out.println("testing by location: " + squares[0][2].hasPiece());
						System.out.println("testing by square object: " + square.hasPiece());
						System.out.println("square row: " + square.getRow());
						System.out.println("square col: " + square.getCol());
						if (!square.hasPiece())//add the square if it's empty
						{
							System.out.println("test5");
							moves.add(chosen.getRow() * 10 + chosen.getCol() + 1);
						}
						else if (chosen.getPiece().isWhite())//if the king is white
						{
							if (!square.getPiece().isWhite())//add the square if it contains a black piece
								moves.add(chosen.getRow() * 10 + chosen.getCol() + 1);
						}
						else if (!chosen.getPiece().isWhite())//if the king is black
						{
							if (square.getPiece().isWhite())//add the square if it contains a white piece
								moves.add(chosen.getRow() * 10 + chosen.getCol() + 1);
						}
					}
				}
				if (chosen.getRow() + 1 < 8)//lower row
				{
					isChecked = false;
					if (chosen.getCol() - 1 >= 0)//lower left
					{
						square = squares[chosen.getRow() + 1][chosen.getCol() - 1];
						squares[chosen.getRow() + 1][chosen.getCol() - 1] = new Square(new King(chosen.getPiece().getId()), square.getRow(), square.getCol());
						//^temporarily sets evaluated square to the king to see if it would be checked if it moved there
						if (!chosen.getPiece().isWhite())
						{
							for (int piece : whitePieces)
								if (squares[piece / 10][piece % 10].getPiece().getName() != 'K')//to avoid infinite recursion
								{
									if (findMoves(squares[piece / 10][piece % 10]).contains((chosen.getRow() + 1) * 10 + chosen.getCol() - 1)
											|| otherKingMoves.contains((chosen.getRow() + 1) * 10 + chosen.getCol() - 1))
									{
										isChecked = true;
										break;
									}
								}
						}
						else
						{
							for (int piece : blackPieces)
								if (squares[piece / 10][piece % 10].getPiece().getName() != 'K')//to avoid infinite recursion
								{
									if (findMoves(squares[piece / 10][piece % 10]).contains((chosen.getRow() + 1) * 10 + chosen.getCol() - 1)
											|| otherKingMoves.contains((chosen.getRow() + 1) * 10 + chosen.getCol() - 1))
									{
										isChecked = true;
										break;
									}
								}
						}
						squares[chosen.getRow() + 1][chosen.getCol() - 1] = square;//reset the square to its original state
						if (!isChecked)//if the square is not checked
						{
							if (!square.hasPiece())//add the square if it's empty
								moves.add((chosen.getRow() + 1) * 10 + chosen.getCol() - 1);
							else if (chosen.getPiece().isWhite())//if the king is white
							{
								if (!square.getPiece().isWhite())//add the square if it contains a black piece
									moves.add((chosen.getRow() + 1) * 10 + chosen.getCol() - 1);
							}
							else if (!chosen.getPiece().isWhite())//if the king is black
							{
								if (square.getPiece().isWhite())//add the square if it contains a white piece
									moves.add((chosen.getRow() + 1) * 10 + chosen.getCol() - 1);
							}
						}	
					}
					//lower center
					isChecked = false;
					square = squares[chosen.getRow() + 1][chosen.getCol()];
					squares[chosen.getRow() + 1][chosen.getCol()] = new Square(new King(chosen.getPiece().getId()), square.getRow(), square.getCol());
					//^temporarily sets evaluated square to the king to see if it would be checked if it moved there
					if (!chosen.getPiece().isWhite())
					{
						for (int piece : whitePieces)
							if (squares[piece / 10][piece % 10].getPiece().getName() != 'K')//to avoid infinite recursion
							{
								if (findMoves(squares[piece / 10][piece % 10]).contains((chosen.getRow() + 1) * 10 + chosen.getCol())
										|| otherKingMoves.contains((chosen.getRow() + 1) * 10 + chosen.getCol()))
								{
									isChecked = true;
									break;
								}
							}
					}
					else
					{
						for (int piece : blackPieces)
							if (chosen.getPiece().getName() != 'K')
								if (findMoves(squares[piece / 10][piece % 10]).contains((chosen.getRow() + 1) * 10 + chosen.getCol())
										|| otherKingMoves.contains((chosen.getRow() + 1) * 10 + chosen.getCol()))
								{
									isChecked = true;
									break;
								}
					}
					squares[chosen.getRow() + 1][chosen.getCol()] = square;//reset the square to its original state
					if (!isChecked)//if the square is not checked
					{
						if (!square.hasPiece())//add the square if it's empty
							moves.add((chosen.getRow() + 1) * 10 + chosen.getCol());
						else if (chosen.getPiece().isWhite())//if the king is white
						{
							if (!square.getPiece().isWhite())//add the square if it contains a black piece
								moves.add((chosen.getRow() + 1) * 10 + chosen.getCol());
						}
						else if (!chosen.getPiece().isWhite())//if the king is black
						{
							if (square.getPiece().isWhite())//add the square if it contains a white piece
								moves.add((chosen.getRow() + 1) * 10 + chosen.getCol());
						}
					}
					isChecked = false;
					if (chosen.getCol() + 1 < 8)//lower right
					{
						square = squares[chosen.getRow() + 1][chosen.getCol() + 1];
						squares[chosen.getRow() + 1][chosen.getCol() + 1] = new Square(new King(chosen.getPiece().getId()), square.getRow(), square.getCol());
						//^temporarily sets evaluated square to the king to see if it would be checked if it moved there
						if (!chosen.getPiece().isWhite())
						{
							for (int piece : whitePieces)
								if (squares[piece / 10][piece % 10].getPiece().getName() != 'K')//to avoid infinite recursion
								{
									if (findMoves(squares[piece / 10][piece % 10]).contains((chosen.getRow() + 1) * 10 + chosen.getCol() + 1)
											|| otherKingMoves.contains((chosen.getRow() + 1) * 10 + chosen.getCol()))
									{
										isChecked = true;
										break;
									}
								}
						}
						else
						{
							for (int piece : blackPieces)
								if (chosen.getPiece().getName() != 'K')
									if (findMoves(squares[piece / 10][piece % 10]).contains((chosen.getRow() + 1) * 10 + chosen.getCol() + 1)
											|| otherKingMoves.contains((chosen.getRow() + 1) * 10 + chosen.getCol()))
									{
										isChecked = true;
										break;
									}
						}
						squares[chosen.getRow() + 1][chosen.getCol() + 1] = square;//reset the square to its original state
						if (!isChecked)//if the square is not checked
						{
							if (!square.hasPiece())//add the square if it's empty
								moves.add((chosen.getRow() + 1) * 10 + chosen.getCol() + 1);
							else if (chosen.getPiece().isWhite())//if the king is white
							{
								if (!square.getPiece().isWhite())//add the square if it contains a black piece
									moves.add((chosen.getRow() + 1) * 10 + chosen.getCol() + 1);
							}
							else if (!chosen.getPiece().isWhite())//if the king is black
							{
								if (square.getPiece().isWhite())//add the square if it contains a white piece
									moves.add((chosen.getRow() + 1) * 10 + chosen.getCol() + 1);
							}
						}	
					}
				}
				
				//castling
				if (!chosen.getPiece().hasMoved())//if the king hasn't moved
				{
					if (chosen.getPiece().isWhite())//if the king is white
					{
						//if the upper left rook is still there and hasn't moved, and the two kingside squares aren't occupied
						if (squares[0][0].hasPiece() && !squares[0][1].hasPiece() && !squares[0][2].hasPiece() && squares[0][0].getPiece().getName() == 'R' 
								&& !squares[0][0].getPiece().hasMoved())
						{
							//do the same loop that checked if a square was checked, on the leftmost kingside square
							square = squares[0][1];
							squares[0][1] = new Square(new King(chosen.getPiece().getId()), square.getRow(), square.getCol());
							//^temporarily sets evaluated square to the king to see if it would be checked if it moved there
							for (int piece : blackPieces)
								if (chosen.getPiece().getName() != 'K')
									if (findMoves(squares[piece / 10][piece % 10]).contains(1) || otherKingMoves.contains(1))
									{
										isChecked = true;
										break;
									}							
							squares[0][1] = square;//reset the square to its original state
							if (!isChecked)//if the square is not checked
							{
								isChecked = false;//reset isChecked
								//check the rightmost kingside square
								square = squares[0][2];
								squares[0][2] = new Square(new King(chosen.getPiece().getId()), square.getRow(), square.getCol());
								//^temporarily sets evaluated square to the king to see if it would be checked if it moved there
								for (int piece : blackPieces)
									if (chosen.getPiece().getName() != 'K')
										if (findMoves(squares[piece / 10][piece % 10]).contains(2) || otherKingMoves.contains(2))
										{
											isChecked = true;
											break;
										}							
								squares[0][2] = square;//reset the square to its original state
								
								if (!isChecked)//if neither of the kingside squares would be in check
								{
									moves.add(1);//add the kingside castle as a potential move
								}
							}
						}
						//if the upper right rook is still there and hasn't moved, and the three queenside squares aren't occupied
						if (squares[0][7].hasPiece() && !squares[0][6].hasPiece() && !squares[0][5].hasPiece() && !squares[0][4].hasPiece() && 
								squares[0][7].getPiece().getName() == 'R' && !squares[0][7].getPiece().hasMoved())
						{
							//do the same loop that checked if a square was checked, on the leftmost queenside square
							square = squares[0][4];
							squares[0][4] = new Square(new King(chosen.getPiece().getId()), square.getRow(), square.getCol());
							//^temporarily sets evaluated square to the king to see if it would be checked if it moved there
							for (int piece : blackPieces)
								if (chosen.getPiece().getName() != 'K')
									if (findMoves(squares[piece / 10][piece % 10]).contains(4) || otherKingMoves.contains(4))
									{
										isChecked = true;
										break;
									}							
							squares[0][4] = square;//reset the square to its original state
							if (!isChecked)//if the square is not checked
							{
								isChecked = false;//reset isChecked
								//check the rightmost queenside square
								square = squares[0][5];
								squares[0][5] = new Square(new King(chosen.getPiece().getId()), square.getRow(), square.getCol());
								//^temporarily sets evaluated square to the king to see if it would be checked if it moved there
								for (int piece : blackPieces)
									if (chosen.getPiece().getName() != 'K')
										if (findMoves(squares[piece / 10][piece % 10]).contains(5) || otherKingMoves.contains(5))
										{
											isChecked = true;
											break;
										}							
								squares[0][5] = square;//reset the square to its original state
								
								if (!isChecked)//if neither of the queenside squares would be in check
								{
									moves.add(5);//add the queenside castle as a potential move
								}
							}
						}
					}
					else if (!chosen.getPiece().isWhite())//if the king is black
					{
						//if the lower left rook is still there and hasn't moved, and the two kingside squares aren't occupied
						if (squares[7][0].hasPiece() && !squares[7][1].hasPiece() && !squares[7][2].hasPiece() && squares[7][0].getPiece().getName() == 'R' 
								&& !squares[7][0].getPiece().hasMoved())
						{
							//do the same loop that checked if a square was checked, on the leftmost kingside square
							square = squares[7][1];
							squares[7][1] = new Square(new King(chosen.getPiece().getId()), square.getRow(), square.getCol());
							//^temporarily sets evaluated square to the king to see if it would be checked if it moved there
							for (int piece : whitePieces)
								if (chosen.getPiece().getName() != 'K')
									if (findMoves(squares[piece / 10][piece % 10]).contains(71) || otherKingMoves.contains(71))
									{
										isChecked = true;
										break;
									}							
							squares[7][1] = square;//reset the square to its original state
							if (!isChecked)//if the square is not checked
							{
								isChecked = false;//reset isChecked
								//check the rightmost kingside square
								square = squares[7][2];
								squares[7][2] = new Square(new King(chosen.getPiece().getId()), square.getRow(), square.getCol());
								//^temporarily sets evaluated square to the king to see if it would be checked if it moved there
								for (int piece : whitePieces)
									if (chosen.getPiece().getName() != 'K')
										if (findMoves(squares[piece / 10][piece % 10]).contains(72) || otherKingMoves.contains(72))
										{
											isChecked = true;
											break;
										}							
								squares[7][2] = square;//reset the square to its original state
								
								if (!isChecked)//if neither of the kingside squares would be in check
								{
									moves.add(71);//add the kingside castle as a potential move
								}
							}
						}
						//if the lower right rook is still there and hasn't moved, and the three queenside squares aren't occupied
						if (squares[7][7].hasPiece() && !squares[7][6].hasPiece() && !squares[7][5].hasPiece() && !squares[7][4].hasPiece() && 
								squares[7][0].getPiece().getName() == 'R' && !squares[7][0].getPiece().hasMoved())
						{
							//do the same loop that checked if a square was checked, on the leftmost queenside square
							square = squares[7][4];
							squares[7][4] = new Square(new King(chosen.getPiece().getId()), square.getRow(), square.getCol());
							//^temporarily sets evaluated square to the king to see if it would be checked if it moved there
							for (int piece : whitePieces)
								if (chosen.getPiece().getName() != 'K')
									if (findMoves(squares[piece / 10][piece % 10]).contains(74) || otherKingMoves.contains(74))
									{
										isChecked = true;
										break;
									}							
							squares[7][4] = square;//reset the square to its original state
							if (!isChecked)//if the square is not checked
							{
								isChecked = false;//reset isChecked
								//check the rightmost queenside square
								square = squares[7][5];
								squares[7][5] = new Square(new King(chosen.getPiece().getId()), square.getRow(), square.getCol());
								//^temporarily sets evaluated square to the king to see if it would be checked if it moved there
								for (int piece : whitePieces)
									if (chosen.getPiece().getName() != 'K')
										if (findMoves(squares[piece / 10][piece % 10]).contains(75) || otherKingMoves.contains(75))
										{
											isChecked = true;
											break;
										}							
								squares[7][5] = square;//reset the square to its original state
								
								if (!isChecked)//if neither of the queenside squares would be in check
								{
									moves.add(75);//add the queenside castle as a potential move
								}
							}
						}
					}
				}
			}
			//if the piece is a pawn
			else if (chosen.getPiece().getName() == 'p')
			{
				if (chosen.getPiece().isWhite())
				{
					if (chosen.getRow() + 1 < 8)
					{
						if (!squares[chosen.getRow() + 1][chosen.getCol()].hasPiece())//if the square in front of the pawn is empty
						{
							moves.add((chosen.getRow() + 1) * 10 + chosen.getCol());//add it
							if (!chosen.getPiece().hasMoved())//if the pawn hasn't moved
							{
								if (!squares[chosen.getRow() + 2][chosen.getCol()].hasPiece())
								{
									moves.add((chosen.getRow() + 2) * 10 + chosen.getCol());//add the square two squares in front of it
								}
							}
						}
						if (chosen.getCol() - 1 >= 0)
						{
							if (squares[chosen.getRow() + 1][chosen.getCol() - 1].hasPiece())//if the lower left square has a piece
							{
								if (!squares[chosen.getRow() + 1][chosen.getCol() - 1].getPiece().isWhite())//if that piece is black
								{
									moves.add((chosen.getRow() + 1) * 10 + chosen.getCol() - 1);//add that square
								}
							}
							if (squares[chosen.getRow()][chosen.getCol() - 1].hasPiece() && 
									!squares[chosen.getRow()][chosen.getCol() - 1].getPiece().isWhite() &&
									squares[chosen.getRow()][chosen.getCol() - 1].getPiece().getName() == 'p' &&
									squares[chosen.getRow()][chosen.getCol() - 1].getPiece().isVulnerableToEP())/*if the square to the left is a pawn and is 
								vulnerable to en passant*/
							{
								moves.add((chosen.getRow() + 1) * 10 + chosen.getCol() - 1);
								epSquare = (chosen.getRow() + 1) * 10 + chosen.getCol() - 1;
							}
						}
						if (chosen.getCol() + 1 < 8)
						{
							if (squares[chosen.getRow() + 1][chosen.getCol() + 1].hasPiece())//if the lower right square has a piece
							{
								if (!squares[chosen.getRow() + 1][chosen.getCol() + 1].getPiece().isWhite())//if that piece is black
								{
									moves.add((chosen.getRow() + 1) * 10 + chosen.getCol() + 1);//add that square
								}
							}
							if (squares[chosen.getRow()][chosen.getCol() + 1].hasPiece() && 
									!squares[chosen.getRow()][chosen.getCol() + 1].getPiece().isWhite() &&
									squares[chosen.getRow()][chosen.getCol() + 1].getPiece().getName() == 'p' &&
									squares[chosen.getRow()][chosen.getCol() + 1].getPiece().isVulnerableToEP())/*if the square to the left is a pawn and is 
								vulnerable to en passant*/
							{
								moves.add((chosen.getRow() + 1) * 10 + chosen.getCol() + 1);
								epSquare = (chosen.getRow() + 1) * 10 + chosen.getCol() + 1;
							}
						}
					}
				}
				else if (!chosen.getPiece().isWhite())
				{
					if (chosen.getRow() - 1 >= 0)
					{
						if (!squares[chosen.getRow() - 1][chosen.getCol()].hasPiece())//if the square in front of the pawn is empty
						{
							moves.add((chosen.getRow() - 1) * 10 + chosen.getCol());//add it
							if (!chosen.getPiece().hasMoved())//if the pawn hasn't moved
							{
								if (!squares[chosen.getRow() - 2][chosen.getCol()].hasPiece())
								{
									moves.add((chosen.getRow() - 2) * 10 + chosen.getCol());//add the square two squares in front of it
								}
							}
						}
						if (chosen.getCol() - 1 >= 0)
						{
							if (squares[chosen.getRow() - 1][chosen.getCol() - 1].hasPiece())//if the upper left square has a piece
							{
								if (squares[chosen.getRow() - 1][chosen.getCol() - 1].getPiece().isWhite())//if that piece is white
								{
									moves.add((chosen.getRow() - 1) * 10 + chosen.getCol() - 1);//add that square
								}
							}
							if (squares[chosen.getRow()][chosen.getCol() - 1].hasPiece() && 
									squares[chosen.getRow()][chosen.getCol() - 1].getPiece().isWhite() &&
									squares[chosen.getRow()][chosen.getCol() - 1].getPiece().getName() == 'p' &&
									squares[chosen.getRow()][chosen.getCol() - 1].getPiece().isVulnerableToEP())/*if the square to the left is a pawn and is 
								vulnerable to en passant*/
							{
								moves.add((chosen.getRow() - 1) * 10 + chosen.getCol() - 1);
								epSquare = (chosen.getRow() - 1) * 10 + chosen.getCol() - 1;

							}
						}
						if (chosen.getCol() + 1 < 8)
						{
							if (squares[chosen.getRow() - 1][chosen.getCol() + 1].hasPiece())//if the upper right square has a piece
							{
								if (squares[chosen.getRow() - 1][chosen.getCol() + 1].getPiece().isWhite())//if that piece is white
								{
									moves.add((chosen.getRow() - 1) * 10 + chosen.getCol() + 1);//add that square
								}
							}
							if (squares[chosen.getRow()][chosen.getCol() + 1].hasPiece() && 
									squares[chosen.getRow()][chosen.getCol() + 1].getPiece().isWhite() &&
									squares[chosen.getRow()][chosen.getCol() + 1].getPiece().getName() == 'p' &&
									squares[chosen.getRow()][chosen.getCol() + 1].getPiece().isVulnerableToEP())/*if the square to the left is a pawn and is 
								vulnerable to en passant*/
							{
								moves.add((chosen.getRow() - 1) * 10 + chosen.getCol() + 1);
								epSquare = (chosen.getRow() - 1) * 10 + chosen.getCol() + 1;
							}
						}
					}
				}
			}
		}
		return moves;
	}

	public void mouseClicked(MouseEvent e) 
	{
		int row = e.getPoint().y / SQUARE_SIZE;
		int col = e.getPoint().x / SQUARE_SIZE;
		
		if (!hasClicked)//if this is the first click, record which piece was clicked
		{
			if (squares[row][col].hasPiece())
			{
				if (squares[row][col].getPiece().isWhite() == whiteToMove)
				{
					for (int move : findMoves(squares[row][col]))
					{
						highlighted.add(move);
					}
					clickedPiece = squares[row][col].getPiece();
					prevRow = row;
					prevCol = col;
				}
			}
			hasClicked = true;
		}
		else//if this is the second click, possibly move the piece to the clicked square
		{
			if (highlighted.contains(row * 10 + col))
			{
				if (squares[row][col].hasPiece())
				{
					if (squares[row][col].getPiece().isWhite())
					{
						whitePieces.remove(whitePieces.indexOf(row * 10 + col));
					}
					else if (!squares[row][col].getPiece().isWhite())

					{
						blackPieces.remove(blackPieces.indexOf(row * 10 + col));
					}
				}
				
				//related to en passant
				if (squares[prevRow][prevCol].getPiece().getName() == 'p')
				{
					if (Math.abs(prevRow - row) == 2)//checking for pawn moving two squares, and if so recording it as vulnerable to en passant
					{
						squares[prevRow][prevCol].getPiece().setVulnerableToEP(true);
					}
					//add a way to check if user immediately used en passant and if not make the pawn no longer vulnerable
					else if (row * 10 + col == epSquare)
					{
						if (squares[prevRow][prevCol].getPiece().isWhite())
						{
							squares[row - 1][col].removePiece();
							blackPieces.remove(blackPieces.indexOf((row - 1) * 10 + col));
						}
						else

						{
							squares[row + 1][col].removePiece();
							whitePieces.remove(whitePieces.indexOf((row + 1) * 10 + col));
						}
					}
				}
				
				//checking if the king is castling, and if so moving the rook as well
				int prevSquare = prevRow * 10 + prevCol;//used to make checking for castling easier
				int newSquare = row * 10 + col;//see above
				if (squares[prevRow][prevCol].getPiece().getName() == 'K')
				{
					if (prevSquare == 3)
					{
						if (newSquare == 1)
						{
							squares[0][2].setPiece(squares[0][0].getPiece());
							squares[0][0].removePiece();
							whitePieces.set(0, 2);/*the left rook will always be at index 0 because this code will only be evaluated if it never moved and it 
							was saved at index 0 initially*/
						}
						else if (newSquare == 5)
						{
							squares[0][4].setPiece(squares[0][7].getPiece());
							squares[0][7].removePiece();
							whitePieces.set(whitePieces.indexOf(7), 4);
						}
					}
					else if (prevSquare == 73)
					{
						if (newSquare == 71)
						{
							squares[7][2].setPiece(squares[7][0].getPiece());
							squares[7][0].removePiece();
							blackPieces.set(0, 72);/*the left rook will always be at index 0 because this code will only be evaluated if it never moved and it 
							was saved at index 0 initially*/
						}
						else if (newSquare == 75)
						{
							squares[7][4].setPiece(squares[7][7].getPiece());
							squares[7][7].removePiece();
							blackPieces.set(blackPieces.indexOf(77), 74);
						}
					}
				}
				
				squares[row][col].setPiece(clickedPiece);//sets the new square to have the new piece
				squares[prevRow][prevCol].removePiece();//removes the piece from its old square
				squares[row][col].getPiece().setMoved();//records the movement in the piece's instance data
				whiteToMove = !whiteToMove;
				
				//updates the black or white piece arrays
				if (squares[row][col].getPiece().isWhite())
				{
					whitePieces.set(whitePieces.indexOf(prevRow * 10 + prevCol), row * 10 + col);/*sets the item at the index of the 
					previous click to instead hold the new location of the piece*/
				}
				else
				{
					blackPieces.set(blackPieces.indexOf(prevRow * 10 + prevCol), row * 10 + col);//see above
				}
			}
			highlighted.clear();
			hasClicked = false;
		}
		repaint();
	}

	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
	public void mousePressed(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {}

}
