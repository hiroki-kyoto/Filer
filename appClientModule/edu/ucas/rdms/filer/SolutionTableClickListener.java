package edu.ucas.rdms.filer;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.table.DefaultTableModel;

import org.json.JSONArray;
import org.json.JSONObject;

import com.alee.laf.table.WebTable;

public class SolutionTableClickListener implements MouseListener {

	@Override
	public void mouseClicked(MouseEvent e) {
		WebTable stable = (WebTable) e.getComponent(); // solution table
		WebTable atable = (WebTable) stable.getParent().getComponent(6);
		
		int row_id = stable.getSelectedRow();
		int sid = Integer.valueOf((String) stable.getValueAt(row_id, 0));
		
		// using solution id to get attribute table
		DefaultTableModel model = (DefaultTableModel) atable.getModel();
		// empty the attribute table
		while(model.getRowCount()>0){
			model.removeRow(0);
		}
		// get json data from server
		HTTPClient client = new HTTPClient();
		try {
			String response = client.sendGet("http://124.16.70.51/rdms/client_attribute_query.php?sid=" + sid);			
			JSONArray records = new JSONArray(response);
			while(records.length()>0){
				JSONObject record = records.getJSONObject(0);
				// put data into table
				model.addRow(new Object[]{
						record.get("name"),
						record.get("type"),
						record.get("offset"),
						record.get("length")
				});
				records.remove(0);
			}
			
		} catch (Exception http_req_exception) {
			// TODO Auto-generated catch block
			http_req_exception.printStackTrace();
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

}
