import java.applet.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class testGraphics extends Applet{

	public void init()
	{
		setSize(300, 300);
	}

	public void paint (Graphics page)
	{
		page.setFont(new Font("Helvetica", Font.BOLD, 30));
		page.drawString("test", 100, 100);
	}
	
}
