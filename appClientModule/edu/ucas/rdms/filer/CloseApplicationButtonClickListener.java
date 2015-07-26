package edu.ucas.rdms.filer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CloseApplicationButtonClickListener implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
		System.exit(0);
	}

}
