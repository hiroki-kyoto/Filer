package edu.ucas.rdms.filer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.table.DefaultTableModel;

import org.json.JSONArray;
import org.json.JSONObject;

import com.alee.laf.button.WebButton;
import com.alee.laf.label.WebLabel;
import com.alee.laf.panel.WebPanel;
import com.alee.laf.table.WebTable;

public class LastPageButtonClickListener implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
		WebButton btn = (WebButton) e.getSource();
		WebPanel page_control = (WebPanel) btn.getParent();
		
		WebLabel page_num = (WebLabel)page_control.getComponent(2);
		int page = Integer.valueOf(page_num.getText(), 10) - 1;
		if(page<1)
			page = 1;
		
		page_num.setText(String.valueOf(page));
		
		// refresh table with new page index
		WebTable table = (WebTable) page_control.getParent().getComponent(2);
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		
		while(model.getRowCount()>0)
			model.removeRow(0);
		
		// update table content
		HTTPClient client = new HTTPClient();
		try {
			String response = client.sendGet("http://124.16.70.51/rdms/client_solution_query.php?page=" + page);
			
			JSONArray records = new JSONArray(response);
			
			// bind source data to table model
			int n = records.length();
			
			for(int i=0; i<n; i++){
				JSONObject solution = (JSONObject)records.get(i);
				String s_id = (String) solution.get("id");
				String s_name = (String) solution.get("name");
				String s_desc = (String) solution.get("description");
				String s_model = (String) solution.get("model");
				
				model.addRow(new Object[]{s_id, s_name, s_desc, s_model});
			}
			
		} catch (Exception httpreq_exception) {
			httpreq_exception.printStackTrace();
		}

	}

}
