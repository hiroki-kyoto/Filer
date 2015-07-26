package edu.ucas.rdms.filer;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BoxLayout;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import com.alee.laf.WebLookAndFeel;
import com.alee.laf.button.WebButton;
import com.alee.laf.filechooser.WebFileChooser;
import com.alee.laf.label.WebLabel;
import com.alee.laf.optionpane.WebOptionPane;
import com.alee.laf.panel.WebPanel;
import com.alee.laf.rootpane.WebFrame;
import com.alee.laf.scroll.WebScrollPane;
import com.alee.laf.table.WebTable;
import com.alee.laf.text.WebPasswordField;
import com.alee.laf.text.WebTextField;
import com.alee.utils.SwingUtils;

public class Main 
{
	public static void main ( final String[] args )
    {
        SwingUtils.invokeLater ( new Runnable ()
        {
            public void run ()
            {
                // Initialize L&F here, before creating any UI
            	WebLookAndFeel.install();
            	WebFrame.setDefaultLookAndFeelDecorated(true);
            	
            	// window
            	final WebFrame frame = new WebFrame();
                frame.setTitle("分布式大文件存储与检索系统");
                frame.setMinimumSize(new Dimension(900, 600));
                frame.setLocation(200, 150);
                frame.setResizable(true);
                frame.setLayout(new BorderLayout());
                frame.setDefaultCloseOperation(WebFrame.DO_NOTHING_ON_CLOSE);
                frame.addWindowListener(new WindowAdapter() {  
                	public void windowClosing(WindowEvent e) {
                		int option = WebOptionPane.showConfirmDialog(null, "确定要关闭窗口？",  
                                        "系统提示", WebOptionPane.YES_NO_OPTION,  
                                        WebOptionPane.QUESTION_MESSAGE);  
                		if (option == WebOptionPane.YES_OPTION)  
                				System.exit(0);
                		}
                });
                frame.setExtendedState(WebFrame.MAXIMIZED_BOTH);
                
                final WebPanel container = new WebPanel();
                container.setLayout(new FlowLayout());
                container.setMinimumSize(new Dimension(900, 600));
                
                // components
            	final WebLabel label_name = new WebLabel();
            	label_name.setText("用户名");
            	label_name.setPreferredSize(new Dimension(60, 40));
            	label_name.setFontSize(16);
            	
            	final WebLabel label_password = new WebLabel();
            	label_password.setText("密码");
            	label_password.setPreferredSize(new Dimension(60, 40));
            	label_password.setFontSize(16);
            	
            	final WebTextField field_name = new WebTextField();
            	field_name.setPreferredSize(new Dimension(160, 40));
            	
            	final WebPasswordField field_password = new WebPasswordField();
            	field_password.setPreferredSize(new Dimension(160, 40));
            	
            	final WebButton button_login = new WebButton();
            	button_login.setText("登录系统");
            	button_login.addActionListener(new LoginButtonClickListener());
            	
            	final WebLabel login_info = new WebLabel();
            	login_info.setText("");
            	login_info.setFontSize(16);
            	login_info.setForeground(new Color(200, 0, 0));
            	login_info.setHorizontalAlignment(SwingConstants.CENTER);
                
            	final WebButton btn_exit = new WebButton();
            	btn_exit.setText("退出系统");
            	btn_exit.addActionListener(new CloseApplicationButtonClickListener());
            	
                WebPanel panel_name = new WebPanel();
                panel_name.setLayout(new FlowLayout());
                panel_name.setBackground(new Color(250, 250, 250));
                
                panel_name.add(label_name);
                panel_name.add(field_name);
                
                WebPanel panel_password = new WebPanel();
                panel_password.setLayout(new FlowLayout());
                panel_password.setBackground(new Color(200, 230, 210));
                
                panel_password.add(label_password);
                panel_password.add(field_password);
                
                WebPanel login_form = new WebPanel();
                login_form.setLayout(new GridLayout(5, 1));
                
                login_form.add(panel_name);
                login_form.add(panel_password);
                login_form.add(button_login);
                login_form.add(btn_exit);
                login_form.add(login_info);
                
                WebPanel menu = new WebPanel();
                menu.setPreferredSize(new Dimension(200, 300));
                menu.setLayout(new GridLayout(5, 1));
                
                WebButton make_file_btn = new WebButton();
                make_file_btn.setText("制作测试文件");
                make_file_btn.setFontSize(16);
                make_file_btn.addActionListener(new MakeFileButtonClickListener());
                make_file_btn.setForeground(new Color(80, 200, 0));
                
                WebButton view_file_btn = new WebButton();
                view_file_btn.setText("解析远程文件");
                view_file_btn.setFontSize(16);
                view_file_btn.setForeground(new Color(200, 160, 0));
                
                WebButton upload_file_btn = new WebButton();
                upload_file_btn.setText("上传本地文件");
                upload_file_btn.setFontSize(16);
                upload_file_btn.setForeground(new Color(0, 0, 200));
                
                WebButton download_file_btn = new WebButton();
                download_file_btn.setText("下载远程文件");               
                download_file_btn.setFontSize(16);
                download_file_btn.setForeground(new Color(100, 0, 200));
                
                WebButton close_application_btn = new WebButton();
                close_application_btn.setText("退出系统");
                close_application_btn.setFontSize(16);
                close_application_btn.setForeground(new Color(200, 0, 0));
                close_application_btn.addActionListener(new CloseApplicationButtonClickListener());
                
                menu.add(make_file_btn);
                menu.add(view_file_btn);
                menu.add(upload_file_btn);
                menu.add(download_file_btn);
                menu.add(close_application_btn);
                
                // make file interface
                WebTable solution_table = new WebTable();
                solution_table.setShowHorizontalLines(true);
                solution_table.setShowVerticalLines(true);
                solution_table.setFontSize(14);
                solution_table.setRowHeight(30);
                solution_table.setAutoscrolls(true);
                solution_table.setAutoResizeMode(WebTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS);
                
                DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
                renderer.setHorizontalAlignment(WebLabel.CENTER);
                solution_table.setDefaultRenderer(Object.class, renderer);
                
                DefaultTableModel data_model = new DefaultTableModel();
                data_model.setColumnIdentifiers(new Object[]{
                		"项目编号", 
                		"项目名称", 
                		"项目描述", 
                		"项目模板", 
                });
                
                data_model.addRow(new Object[]{"1", "测试项目A", "测试项目的解释A", "属性A"});
                data_model.addRow(new Object[]{"2", "测试项目B", "测试项目的解释B", "属性B"});
                
                solution_table.setModel(data_model);
                
                solution_table.getColumnModel().getColumn(0).setPreferredWidth(100);
                solution_table.getColumnModel().getColumn(1).setPreferredWidth(200);
                solution_table.getColumnModel().getColumn(2).setPreferredWidth(400);
                solution_table.getColumnModel().getColumn(3).setPreferredWidth(300);
                solution_table.setEditable(false);
                solution_table.setBackground(new Color(200, 230, 210));
                solution_table.setForeground(new Color(0, 0, 0));
                solution_table.setSelectionBackground(new Color(0, 0, 0));
                solution_table.setSelectionForeground(new Color(250, 250, 250));
                solution_table.setGridColor(new Color(250, 250, 250));
                solution_table.getTableHeader().setPreferredSize(new Dimension(800, 25));
                
                solution_table.addMouseListener(new SolutionTableClickListener());
                
                // go back to main frame
                WebButton btn_back = new WebButton();
                btn_back.setText("返回菜单界面");
                btn_back.setFontSize(16);
                btn_back.addActionListener(new GoBackButtonClickListener());
                
                WebButton btn_last_page = new WebButton("上一页");
                WebButton btn_next_page = new WebButton("下一页");
                WebButton btn_home_page = new WebButton("首页");
                WebButton btn_final_page = new WebButton("末页");
                WebLabel label_page_index = new WebLabel("1");
                
                btn_last_page.setFontSize(16);
                btn_home_page.setFontSize(16);
                btn_next_page.setFontSize(16);
                label_page_index.setFontSize(16);
                label_page_index.setHorizontalAlignment(WebLabel.CENTER);
                btn_final_page.setFontSize(16);
                
                btn_last_page.addActionListener(new LastPageButtonClickListener());
                btn_next_page.addActionListener(new NextPageButtonClickListener());
                btn_home_page.addActionListener(new HomePageButtonClickListener());
                btn_final_page.addActionListener(new FinalPageButtonClickListener());
                
                WebPanel panel_page_control = new WebPanel();
                panel_page_control.setLayout(new GridLayout(1, 5));
                
                panel_page_control.add(btn_last_page);
                panel_page_control.add(btn_next_page);
                panel_page_control.add(label_page_index);
                panel_page_control.add(btn_home_page);
                panel_page_control.add(btn_final_page);
                
                // tips for the making file module
                WebLabel operation_tip = new WebLabel(
                			"【操作提示】"
                					+ "[1]选中项目后出现帧模板 "
                					+ "[2]设置帧属性值及帧数 "
                					+ "[3]选择保存路径 "
                					+ "[4]点击制作文件按钮 "
                					+ "[5]可在您选择的目录下查看文件"
                		);
                
                operation_tip.setHorizontalAlignment(WebLabel.CENTER);
                operation_tip.setPreferredWidth(400);
                operation_tip.setPreferredHeight(30);
                operation_tip.setForeground(new Color(250, 250, 250));
                operation_tip.setFontSize(16);
                
                WebPanel panel_tip = new WebPanel();
                panel_tip.setBackground(new Color(0,0,100));
                panel_tip.add(operation_tip);
                
                // ====== Attribute Table ========
                WebTable attr_table = new WebTable();
                
                attr_table.setBackground(new Color(255,255,255));
                attr_table.setForeground(new Color(0,0,0));
                attr_table.setDefaultRenderer(Object.class, renderer);
                attr_table.setFontSize(14);
                attr_table.setRowHeight(25);
                attr_table.getTableHeader().setPreferredSize(new Dimension(800, 25));
                
                DefaultTableModel attr_model = new DefaultTableModel(){
					private static final long serialVersionUID = 1L;

					public boolean isCellEditable(int row, int col){
                		if(col==4)
                			return true;
                		else
                			return false;
                	}
                };
                attr_table.setModel(attr_model);
                
                attr_model.setColumnIdentifiers(new Object[]{
                		"属性名", 
                		"数据类型", 
                		"偏移字节", 
                		"字节长度", 
                		"填充值"
                });
                
                attr_model.addRow(new Object[]{
                		"文件名", 
                		"字符串", 
                		"0", 
                		"128", 
                		"001.HD5"
                });
                
                // frame number
                WebLabel fn_label = new WebLabel("请设定文件帧数: ");
                fn_label.setPreferredHeight(30);
                fn_label.setFontSize(16);
                
                WebTextField frameset_number = new WebTextField();
                frameset_number.setText("10000"); // 10000 is a demo 
                frameset_number.setPreferredSize(new Dimension(200, 30));
                
                WebPanel fn_panel = new WebPanel();
                fn_panel.setLayout(new FlowLayout());
                
                fn_panel.add(fn_label);
                fn_panel.add(frameset_number);
                
                // choose location to store the file to make
                WebFileChooser file_chooser = new WebFileChooser();
                file_chooser.setPreferredSize(new Dimension(600, 400));
                
                // make file button
                WebButton btn_create_file = new WebButton("按照上述模板创建文件");
                btn_create_file.addActionListener(new CreateFileButtonClickListener());
                
                WebPanel panel_make_file = new WebPanel();
                panel_make_file.setLayout(new BoxLayout(panel_make_file, BoxLayout.Y_AXIS));
                
                panel_make_file.add(btn_back); //0
                panel_make_file.add(solution_table.getTableHeader());//1
                panel_make_file.add(solution_table);//2
                panel_make_file.add(panel_page_control);//3
                panel_make_file.add(panel_tip);//4
                panel_make_file.add(attr_table.getTableHeader());//5
                panel_make_file.add(attr_table);//6
                panel_make_file.add(fn_panel);//7
                panel_make_file.add(file_chooser);//8
                panel_make_file.add(btn_create_file);//9
                
                // add login form into the flow layout
                container.add(login_form); // 0
                container.add(menu); // 1
                container.add(panel_make_file); // 2
                
                // add scroll bar
                frame.add("West", container);
                frame.getContentPane().add(new WebScrollPane(container));
                
                // initial the UI components
                login_form.setVisible(true);
                menu.setVisible(false);
                panel_make_file.setVisible(false);
                
                // show the window
                frame.setVisible(true);
            }
        } );
    }
}