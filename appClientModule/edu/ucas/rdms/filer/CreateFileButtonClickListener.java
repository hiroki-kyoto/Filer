package edu.ucas.rdms.filer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Date;
import javax.swing.tree.DefaultMutableTreeNode;

import com.alee.laf.button.WebButton;
import com.alee.laf.optionpane.WebOptionPane;
import com.alee.laf.panel.WebPanel;
import com.alee.laf.table.WebTable;
import com.alee.laf.text.WebTextField;

import ncsa.hdf.object.FileFormat;
import ncsa.hdf.object.h5.H5File;
import ncsa.hdf.object.Group;
import ncsa.hdf.object.Dataset;
import ncsa.hdf.object.Datatype;


public class CreateFileButtonClickListener implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
		WebButton btn = (WebButton) e.getSource();
		WebPanel mf_panel = (WebPanel) btn.getParent();
		WebTable atable = (WebTable) mf_panel.getComponent(6);
		WebTextField fs_number = (WebTextField) 
				((WebPanel) mf_panel
						.getComponent(7))
				.getComponent(1);
		
		int option = WebOptionPane.showConfirmDialog(null, "确定要创建文件？",  
                "系统提示", WebOptionPane.YES_NO_OPTION,  
                WebOptionPane.QUESTION_MESSAGE);  
		if (option == WebOptionPane.YES_OPTION){  
				Date today = new Date();
				String fname = today.toString().replaceAll(" ", "_").replaceAll(":", "") + ".HD5";
				
				// create a HDF5 file
				FileFormat format = FileFormat.getFileFormat(FileFormat.FILE_TYPE_HDF5);
				if(format==null){
					System.out.println("HDF5 Class not found.");
					return;
				}
				try {
					H5File file = (H5File) format.createFile(fname, H5File.FILE_CREATE_DELETE);
					if(file==null){
						System.out.println("HDF5 file cannot be created: " + fname);
						return;
					}
					file.open();
					Group root = (Group) ((DefaultMutableTreeNode) file.getRootNode()).getUserObject();
					Group g1 = file.createGroup("g1", root);
					
					int data[][] = new int[10][20];
					
					for(int i=0;i<10;i++){
						for(int j=0;j<20;j++){
							data[i][j] = i*j;
						}
					}
					
					Datatype dt = file.createDatatype(Datatype.CLASS_INTEGER, 4, Datatype.NATIVE, Datatype.NATIVE);
					Dataset ds = file.createScalarDS("DS-1", g1, dt, new long[]{10, 20}, null, null, 0, data);
					
					file.close();
					
				} catch (Exception e2) {
					e2.printStackTrace();
				}
				
				
				int fs_n = Integer.valueOf(fs_number.getText());
				// types:  int, string, date, 
				String[] basic_types = {
				        "整数型",
				        "单字符",
				        "单精度浮点型",
				        "双精度浮点型",
				        "布尔型",
				        "长整型"
				};
				
				String[] array_types = {
				        "整型数组",
				        "字符串",
				        "单精度浮点数组",
				        "双精度浮点数组",
				        "布尔型数组",
				        "长整形数组"
				};
				
				// get attributes from this table
				// and translate them into bytes
				int row_n = atable.getRowCount();
				
				int[] offset = new int[row_n];
				int[] length = new int[row_n];
				String[] types = new String[row_n];
				String[] values = new String[row_n];
				
				for(int i=0; i<row_n; i++){
					types[i] = (String) atable.getValueAt(i, 1);
					values[i] = (String) atable.getValueAt(i, 4);
					offset[i] = (int) atable.getValueAt(i, 2);
					length[i] = (int) atable.getValueAt(i, 3);
				}
				
				// get total bytes of single frame
				int offset_max = offset[0];
				int i = 0;
				for(;i<row_n;i++)
					offset_max = offset_max<offset[i]?offset[i]:offset_max;
				
				int total = offset_max + length[i];
				
				ByteArrayOutputStream buf = new ByteArrayOutputStream();   
				DataOutputStream opt = new DataOutputStream(buf);   
				
				for(i=0; i<row_n; i++){
					// first parse string to typed data. then convert it to bytes.
					String _type_ = types[i];
					int _offset_ = offset[i];
					int _length_ = length[i];
					String _value_ = values[i];
					
					int tid = FunctionUtil.find(basic_types, _type_);
					if(tid!=-1){
						// it is basic type
						switch(tid){
						case 0:
							try {
								opt.writeInt(Integer.valueOf(_value_));
							} catch (NumberFormatException | IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						case 1:
							try {
								opt.writeChar(_value_.charAt(0));
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						case 2:
							try {
								opt.writeFloat(Float.valueOf(_value_));
							} catch (NumberFormatException | IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						case 3:
							try {
								opt.writeDouble(Double.valueOf(_value_));
							} catch (NumberFormatException | IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						case 4:
							try {
								opt.writeBoolean(Boolean.valueOf(_value_));
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						case 5:
							try {
								opt.writeLong(Long.valueOf(_value_));
							} catch (NumberFormatException | IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						}
					} else {
						tid = FunctionUtil.find(array_types, _type_);
						if(tid!=-1){
							switch(tid){
							case 0:
								System.out.println("TEST");
							}
						}
					}
				}
		}
	}

}
