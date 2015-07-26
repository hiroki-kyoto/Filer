package edu.ucas.rdms.filer;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.alee.laf.text.WebTextField;

public class FramesetNumberChangeListener implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
		WebTextField frameset_number = (WebTextField) e.getSource();
		String fns = frameset_number.getText();
		int fn = Integer.valueOf(fns);
		if(!String.valueOf(fn).equals(fns)){
			frameset_number.setBackground(new Color(200, 0, 0));
			frameset_number.setText(String.valueOf(fn));
		}
	}

}
