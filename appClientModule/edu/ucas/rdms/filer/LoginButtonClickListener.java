package edu.ucas.rdms.filer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.json.JSONObject;

import com.alee.laf.button.WebButton;
import com.alee.laf.label.WebLabel;
import com.alee.laf.panel.WebPanel;
import com.alee.laf.text.WebPasswordField;
import com.alee.laf.text.WebTextField;

public class LoginButtonClickListener implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
		WebButton button = (WebButton) e.getSource();
		
		WebPanel login_form = (WebPanel) button.getParent();
		WebPanel panel_name = (WebPanel) login_form.getComponent(0);
		WebPanel panel_password = (WebPanel) login_form.getComponent(1);
		
		WebTextField name = (WebTextField) panel_name.getComponent(1);
		WebPasswordField password = (WebPasswordField) panel_password.getComponent(1);
		WebLabel login_info = (WebLabel)login_form.getComponent(4);
		
		String str_name = name.getText();
		String str_password = String.valueOf(password.getPassword());
		
		// communicate with server
		HTTPClient client = new HTTPClient();
		try {
			String JsonStr = client.sendGet(
					"http://124.16.70.51/rdms/client_login.php?"
					+ "username=" + str_name
					+ "&password=" + str_password
			);
			
			JSONObject JsonObj = new JSONObject(JsonStr);
			
			if(JsonObj.get("status").equals("success")){
				// login success
				login_form.getParent().getComponent(1).setVisible(true);
				login_form.setVisible(false);
			} else {
				login_info.setText("用户信息验证未通过");
			}
			
		} catch (Exception httpreq_exception) {
			// TODO Auto-generated catch block
			httpreq_exception.printStackTrace();
		}
		
	}

}
