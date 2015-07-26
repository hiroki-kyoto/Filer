package edu.ucas.rdms.filer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.alee.laf.button.WebButton;
import com.alee.laf.panel.WebPanel;

public class GoBackButtonClickListener implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
		WebPanel panel_make_file = (WebPanel) ((WebButton) e.getSource()).getParent();
		panel_make_file.setVisible(false);
		panel_make_file.getParent().getComponent(1).setVisible(true);
	}

}
