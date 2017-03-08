package it.univaq.scuna.client;

import it.univaq.scuna.gui.ClientGUIWindow;

public class Mainclass {

	public static void main(String[]args) {
		ClientGUIWindow window = new ClientGUIWindow(new ClientApplicationLogic());
		window.setVisible(true);
	}
}
