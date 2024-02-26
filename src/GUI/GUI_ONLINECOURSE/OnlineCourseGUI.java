package GUI.GUI_ONLINECOURSE;



import java.awt.BorderLayout;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.Color;
import java.awt.FlowLayout;
import javax.swing.JScrollPane;
import javax.swing.JComponent;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import BUS.OnlineCourseBUS;
import DTO.OnlineCourseDTO;

public class OnlineCourseGUI extends JPanel{
	OnlineCourseBUS olBUS= new OnlineCourseBUS();
	private Font fontSubTittle = new Font("Tahoma", Font.BOLD, 20);
	private Font fontbtn = new Font("Tahoma",Font.PLAIN, 13);
	private Font fontTable = new Font("Segoe UI", Font.BOLD, 13);
	
	private Color texfieldColor = new Color(45, 52, 54);
	private DefaultTableModel model;
	private JTable listCourse;
	
	
	public OnlineCourseGUI() {
		unitGUI();
	}
	
	public void unitGUI() {
		setLayout(new BorderLayout());
		setVisible(true);
		JLabel lbTitle = new JLabel("KHOÁ HỌC ONLINE");
		JPanel pnTitle = new JPanel();
		pnTitle.setBackground(Color.WHITE);
		lbTitle.setFont(fontSubTittle);
		pnTitle.add(lbTitle);
		pnTitle.setPreferredSize(new Dimension(0, 80));
		JScrollPane listOnlineCourse =tblistCourse();
		
		JPanel footer=new JPanel(new FlowLayout(FlowLayout.RIGHT));
		
		JButton btnThem=new JButton("Thêm");
		btnThem.setBackground(Color.decode("#ebf2fc"));
		btnThem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	JFrame frame=new JFrame();
        		frame.setLayout(new BorderLayout());
        		
        		frame.setVisible(true);
        		frame.setLocationRelativeTo(null);
        		frame.setSize(400,500);
        		frame.setResizable(false);
        		JPanel content=new JPanel();
        		content.setLayout(new BoxLayout(content,BoxLayout.Y_AXIS));
        		
        		JPanel pnID=new JPanel(new FlowLayout(FlowLayout.LEFT));
        		JLabel lbid=new JLabel("ID");
        		lbid.setPreferredSize(new Dimension(80, 30));
        		JTextField txtid=new JTextField();
        		txtid.setPreferredSize(new Dimension(180, 30));
        		pnID.add(lbid);
        		pnID.add(txtid);
        		
        		JPanel pnTitle=new JPanel(new FlowLayout(FlowLayout.LEFT));
        		JLabel lbTitle=new JLabel("Tittle");
        		lbTitle.setPreferredSize(new Dimension(80, 30));
        		JTextField txtTitle=new JTextField();
        		txtTitle.setPreferredSize(new Dimension(180, 30));
        		pnTitle.add(lbTitle);
        		pnTitle.add(txtTitle);
        		
        		JPanel pnMK=new JPanel(new FlowLayout(FlowLayout.LEFT));
        		JLabel lbMK=new JLabel("DepartmentID");
        		lbMK.setPreferredSize(new Dimension(80, 30));
        		JTextField txtMK=new JTextField();
        		txtMK.setPreferredSize(new Dimension(180, 30));
        		pnMK.add(lbMK);
        		pnMK.add(txtMK);
        		
        		JPanel pnCre=new JPanel(new FlowLayout(FlowLayout.LEFT));
        		JLabel lbCre=new JLabel("Credits");
        		lbCre.setPreferredSize(new Dimension(80, 30));
        		JTextField txtCre=new JTextField();
        		txtCre.setPreferredSize(new Dimension(180, 30));
        		pnCre.add(lbCre);
        		pnCre.add(txtCre);
        		
        		JPanel pnUrl=new JPanel(new FlowLayout(FlowLayout.LEFT));
        		JLabel lbUrl=new JLabel("Url");
        		lbUrl.setPreferredSize(new Dimension(80, 30));
        		JTextField txtUrl=new JTextField();
        		txtUrl.setPreferredSize(new Dimension(180, 30));
        		pnUrl.add(lbUrl);
        		pnUrl.add(txtUrl);
        		
        		content.add(pnID);
        		content.add(pnTitle);
        		content.add(pnMK);
        		content.add(pnCre);
        		content.add(pnUrl);
        		
        		JPanel pntitle=new JPanel(new FlowLayout(FlowLayout.CENTER));
        		JLabel title=new JLabel("Thong Tin");
        		title.setFont(fontSubTittle);
        		pntitle.setPreferredSize(new Dimension(0, 50));
        		pntitle.add(title);
        		
        		JPanel pnL=new JPanel();
        		pnL.setPreferredSize(new Dimension(50, 0));
        		JPanel pnR=new JPanel();
        		pnL.setPreferredSize(new Dimension(50, 0));
        		
        		JPanel pnBottom= new JPanel(new FlowLayout(FlowLayout.CENTER));
        		JButton btnAccept=new JButton("Xác nhận");
        		btnAccept.setBackground(Color.decode("#ebf2fc"));
        		btnAccept.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                    	int id=Integer.parseInt(txtid.getText());
                    	String name=txtTitle.getText();
                    	int dpid=Integer.parseInt(txtMK.getText());
                    	int cre=Integer.parseInt(txtCre.getText());
                    	String url=txtUrl.getText();
                    	OnlineCourseDTO onl= new OnlineCourseDTO(id,name,cre,dpid,url);
                    	JOptionPane.showMessageDialog(frame,olBUS.insert(onl),"Thong bao",JOptionPane.INFORMATION_MESSAGE);
                    	refreshTable();
                    }
        		});
        		JButton btnDeny=new JButton("Huỷ");
        		btnDeny.setBackground(Color.decode("#ebf2fc"));
        		btnDeny.setFont(fontbtn);
        		btnDeny.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                    	frame.dispose();
                    }
        		});
        		btnAccept.setPreferredSize(new Dimension(100, 40));
        		btnDeny.setPreferredSize(new Dimension(100, 40));
        		
        		pnBottom.add(btnAccept);
        		pnBottom.add(btnDeny);
        		
        		frame.add(content,BorderLayout.CENTER);
        		frame.add(pntitle,BorderLayout.NORTH);
        		frame.add(pnL,BorderLayout.WEST);
        		frame.add(pnR,BorderLayout.EAST);
        		frame.add(pnBottom,BorderLayout.SOUTH);
            }
		});
		btnThem.setFont(fontbtn);
		btnThem.setPreferredSize(new Dimension(100, 40));
		ImageIcon iconPlus= new ImageIcon(new ImageIcon(getClass().getResource("/GUI/assets/plus2.png")).getImage().getScaledInstance(18, 18, Image.SCALE_SMOOTH));
		btnThem.setIcon(iconPlus);
		btnThem.setBackground(Color.BLACK);
		
		JButton btnXoa=new JButton("Xoá");
		btnXoa.setBackground(Color.decode("#ebf2fc"));
		btnXoa.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	int selectedRow=listCourse.getSelectedRow();
            	if (selectedRow != -1) {
            		int value=(Integer)model.getValueAt(selectedRow, getColumnIndex("ID"));
            		OnlineCourseDTO onl= olBUS.findById(value);
            		JOptionPane.showMessageDialog(new JFrame(),olBUS.delete(onl),"Thong bao",JOptionPane.INFORMATION_MESSAGE);
            		refreshTable();
            	}
            }
		});
		btnXoa.setFont(fontbtn);
		btnXoa.setPreferredSize(new Dimension(100, 40));
		ImageIcon iconXoa= new ImageIcon(new ImageIcon(getClass().getResource("/GUI/assets/trash.png")).getImage().getScaledInstance(18, 18, Image.SCALE_SMOOTH));
		btnXoa.setIcon(iconXoa);
		btnXoa.setBackground(Color.BLACK);
		
		JButton btnSua=new JButton("Sửa");
		btnSua.setBackground(Color.decode("#ebf2fc"));
		btnSua.setFont(fontbtn);
		btnSua.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	JFrame frame=new JFrame();
        		frame.setLayout(new BorderLayout());
        		
        		int selectedRow=listCourse.getSelectedRow();
        		int value=(Integer)model.getValueAt(selectedRow, getColumnIndex("ID"));
        		OnlineCourseDTO onl=olBUS.findById(value);
        		
        		frame.setVisible(true);
        		frame.setLocationRelativeTo(null);
        		frame.setSize(400,500);
        		frame.setResizable(false);
        		JPanel content=new JPanel();
        		content.setLayout(new BoxLayout(content,BoxLayout.Y_AXIS));
        		
        		JPanel pnID=new JPanel(new FlowLayout(FlowLayout.LEFT));
        		JLabel lbid=new JLabel("ID");
        		lbid.setPreferredSize(new Dimension(100, 30));
        		JTextField txtid=new JTextField();
        		txtid.setText(String.valueOf(onl.getId()));
        		txtid.setPreferredSize(new Dimension(180, 30));
        		pnID.add(lbid);
        		pnID.add(txtid);
        		
        		JPanel pnTitle=new JPanel(new FlowLayout(FlowLayout.LEFT));
        		JLabel lbTitle=new JLabel("Tittle");
        		lbTitle.setPreferredSize(new Dimension(100, 30));
        		JTextField txtTitle=new JTextField();
        		txtTitle.setText(onl.getTittle());
        		txtTitle.setPreferredSize(new Dimension(180, 30));
        		pnTitle.add(lbTitle);
        		pnTitle.add(txtTitle);
        		
        		JPanel pnMK=new JPanel(new FlowLayout(FlowLayout.LEFT));
        		JLabel lbMK=new JLabel("DepartmentID");
        		lbMK.setPreferredSize(new Dimension(100, 30));
        		JTextField txtMK=new JTextField();
        		txtMK.setText(String.valueOf(onl.getMaKhoa()));
        		txtMK.setPreferredSize(new Dimension(180, 30));
        		pnMK.add(lbMK);
        		pnMK.add(txtMK);
        		
        		JPanel pnCre=new JPanel(new FlowLayout(FlowLayout.LEFT));
        		JLabel lbCre=new JLabel("Credits");
        		lbCre.setPreferredSize(new Dimension(100, 30));
        		JTextField txtCre=new JTextField();
        		txtCre.setText(String.valueOf(onl.getCredits()));
        		txtCre.setPreferredSize(new Dimension(180, 30));
        		pnCre.add(lbCre);
        		pnCre.add(txtCre);
        		
        		JPanel pnUrl=new JPanel(new FlowLayout(FlowLayout.LEFT));
        		JLabel lbUrl=new JLabel("Url");
        		lbUrl.setPreferredSize(new Dimension(100, 30));
        		JTextField txtUrl=new JTextField();
        		txtUrl.setText(onl.getUrl());
        		txtUrl.setPreferredSize(new Dimension(180, 30));
        		pnUrl.add(lbUrl);
        		pnUrl.add(txtUrl);
        		
        		content.add(pnID);
        		content.add(pnTitle);
        		content.add(pnMK);
        		content.add(pnCre);
        		content.add(pnUrl);
        		
        		JPanel pntitle=new JPanel(new FlowLayout(FlowLayout.CENTER));
        		JLabel title=new JLabel("Thong Tin");
        		title.setFont(fontSubTittle);
        		pntitle.setPreferredSize(new Dimension(0, 50));
        		pntitle.add(title);
        		
        		JPanel pnL=new JPanel();
        		pnL.setPreferredSize(new Dimension(50, 0));
        		JPanel pnR=new JPanel();
        		pnL.setPreferredSize(new Dimension(50, 0));
        		
        		JPanel pnBottom= new JPanel(new FlowLayout(FlowLayout.CENTER));
        		JButton btnAccept=new JButton("Xác nhận");
        		btnAccept.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                    	int id=Integer.parseInt(txtid.getText());
                    	String name=txtTitle.getText();
                    	int dpid=Integer.parseInt(txtMK.getText());
                    	int cre=Integer.parseInt(txtCre.getText());
                    	String url=txtUrl.getText();
                    	OnlineCourseDTO onl= new OnlineCourseDTO(id,name,cre,dpid,url);
                    	JOptionPane.showMessageDialog(frame,olBUS.update(onl),"Thong bao",JOptionPane.INFORMATION_MESSAGE);
                    	refreshTable();
                    }
        		});
        		JButton btnDeny=new JButton("Huỷ");
        		btnDeny.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                    	frame.dispose();
                    }
        		});
        		btnAccept.setPreferredSize(new Dimension(100, 40));
        		btnDeny.setPreferredSize(new Dimension(100, 40));
        		
        		pnBottom.add(btnAccept);
        		pnBottom.add(btnDeny);
        		
        		frame.add(content,BorderLayout.CENTER);
        		frame.add(pntitle,BorderLayout.NORTH);
        		frame.add(pnL,BorderLayout.WEST);
        		frame.add(pnR,BorderLayout.EAST);
        		frame.add(pnBottom,BorderLayout.SOUTH);
            }
		});
		btnSua.setFont(fontbtn);
		btnSua.setPreferredSize(new Dimension(100, 40));
		ImageIcon iconSua= new ImageIcon(new ImageIcon(getClass().getResource("/GUI/assets/pencil.png")).getImage().getScaledInstance(18, 18, Image.SCALE_SMOOTH));
		btnSua.setIcon(iconSua);
		btnSua.setBackground(Color.LIGHT_GRAY);
		
		
		footer.setBackground(Color.WHITE);
		footer.add(btnThem);
		footer.add(btnSua);
		footer.add(btnXoa);
		
		JPanel search=new JPanel(new FlowLayout(FlowLayout.LEFT));
		search.setBackground(Color.WHITE);
 		JLabel lbsearch=new JLabel("Tìm kiếm");
 		JTextField txtSearch=new JTextField();
 		txtSearch.setBackground(texfieldColor);
 		txtSearch.setPreferredSize(new Dimension(250, 30));
 		
 		String[] Choise = {"All","CourseID", "Title", "DepartmentID", "Url", "Credit"};

        JComboBox<String> comboBox = new JComboBox<>(Choise);
        
        JButton accept=new JButton("Tìm");
        accept.setBackground(Color.decode("#ebf2fc"));
        accept.setFont(fontbtn);
        accept.setPreferredSize(new Dimension(120, 30));
        accept.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	System.out.println(String.valueOf(comboBox.getSelectedItem()));
            	System.out.println(txtSearch.getText());
            	search(String.valueOf(comboBox.getSelectedItem()),txtSearch.getText());
            	txtSearch.setText("");
            }
        });
        
 		search.add(lbsearch);
 		search.add(txtSearch);
 		search.add(comboBox);
 		search.add(accept);
 		
 		
 		JPanel Content=new JPanel(new BorderLayout());
 		Content.add(listOnlineCourse,BorderLayout.CENTER);
 		Content.add(search,BorderLayout.NORTH);
 		
		
		add(pnTitle, BorderLayout.NORTH);
		add(Content,BorderLayout.CENTER);
		add(footer, BorderLayout.SOUTH);
	}
	
	public JScrollPane tblistCourse() {
		String column[] = {"ID", "Tên Môn Học", "Khoa", "Giá", "Url"};
        model = new DefaultTableModel(column, 0){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Không cho phép chỉnh sửa ô trong JTable
            }
        };
        
        List<OnlineCourseDTO> list= olBUS.findAll();
        for(OnlineCourseDTO course:list) {
        	Object[] duLieu = {course.getId(),course.getTittle(),course.getMaKhoa(),course.getCredits(),course.getUrl()};
        	model.addRow(duLieu);
        }
        listCourse = new JTable(model);
        for (int i = 0; i < listCourse.getColumnCount(); i++) {
            listCourse.getColumnModel().getColumn(i).setCellEditor(null);
        }
        listCourse.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
            	 if (e.getClickCount() == 2) { // Kiểm tra double click
                     int selectedRow = listCourse.getSelectedRow();
                     if (selectedRow != -1) {
                    	 JFrame frame=new JFrame();
                 		frame.setLayout(new BorderLayout());
                 		int value=(Integer)model.getValueAt(selectedRow, getColumnIndex("ID"));
                 		OnlineCourseDTO onl=olBUS.findById(value);
                 		
                 		frame.setVisible(true);
                 		frame.setLocationRelativeTo(null);
                 		frame.setSize(400,500);
                 		frame.setResizable(false);
                 		JPanel content=new JPanel();
                 		content.setLayout(new BoxLayout(content,BoxLayout.Y_AXIS));
                 		
                 		JPanel pnID=new JPanel(new FlowLayout(FlowLayout.LEFT));
                 		JLabel lbid=new JLabel("ID");
                 		lbid.setPreferredSize(new Dimension(100, 30));
                 		JTextField txtid=new JTextField();
                 		txtid.setEditable(false);
                 		txtid.setText(String.valueOf(onl.getId()));
                 		txtid.setPreferredSize(new Dimension(180, 30));
                 		pnID.add(lbid);
                 		pnID.add(txtid);
                 		
                 		JPanel pnTitle=new JPanel(new FlowLayout(FlowLayout.LEFT));
                 		JLabel lbTitle=new JLabel("Tittle");
                 		lbTitle.setPreferredSize(new Dimension(100, 30));
                 		JTextField txtTitle=new JTextField();
                 		txtTitle.setEditable(false);
                 		txtTitle.setText(onl.getTittle());
                 		txtTitle.setPreferredSize(new Dimension(180, 30));
                 		pnTitle.add(lbTitle);
                 		pnTitle.add(txtTitle);
                 		
                 		JPanel pnMK=new JPanel(new FlowLayout(FlowLayout.LEFT));
                 		JLabel lbMK=new JLabel("DepartmentID");
                 		lbMK.setPreferredSize(new Dimension(100, 30));
                 		JTextField txtMK=new JTextField();
                 		txtMK.setEditable(false);
                 		txtMK.setText(String.valueOf(onl.getMaKhoa()));
                 		txtMK.setPreferredSize(new Dimension(180, 30));
                 		pnMK.add(lbMK);
                 		pnMK.add(txtMK);
                 		
                 		JPanel pnCre=new JPanel(new FlowLayout(FlowLayout.LEFT));
                 		JLabel lbCre=new JLabel("Credits");
                 		lbCre.setPreferredSize(new Dimension(100, 30));
                 		JTextField txtCre=new JTextField();
                 		txtCre.setEditable(false);
                 		txtCre.setText(String.valueOf(onl.getCredits()));
                 		txtCre.setPreferredSize(new Dimension(180, 30));
                 		pnCre.add(lbCre);
                 		pnCre.add(txtCre);
                 		
                 		JPanel pnUrl=new JPanel(new FlowLayout(FlowLayout.LEFT));
                 		JLabel lbUrl=new JLabel("Url");
                 		lbUrl.setPreferredSize(new Dimension(100, 30));
                 		JTextField txtUrl=new JTextField();
                 		txtUrl.setEditable(false);
                 		txtUrl.setText(onl.getUrl());
                 		txtUrl.setPreferredSize(new Dimension(180, 30));
                 		pnUrl.add(lbUrl);
                 		pnUrl.add(txtUrl);
                 		
                 		content.add(pnID);
                 		content.add(pnTitle);
                 		content.add(pnMK);
                 		content.add(pnCre);
                 		content.add(pnUrl);
                 		
                 		JPanel pntitle=new JPanel(new FlowLayout(FlowLayout.CENTER));
                 		JLabel title=new JLabel("Thong Tin");
                 		title.setFont(fontSubTittle);
                 		pntitle.setPreferredSize(new Dimension(0, 50));
                 		pntitle.add(title);
                 		
                 		JPanel pnL=new JPanel();
                 		pnL.setPreferredSize(new Dimension(50, 0));
                 		JPanel pnR=new JPanel();
                 		pnL.setPreferredSize(new Dimension(50, 0));
                 		
                 		JPanel pnBottom= new JPanel(new FlowLayout(FlowLayout.CENTER));
       
                 		JButton btnDeny=new JButton("Huỷ");
                 		btnDeny.addActionListener(new ActionListener() {
                             @Override
                             public void actionPerformed(ActionEvent e) {
                             	frame.dispose();
                             }
                 		});
                 		btnDeny.setPreferredSize(new Dimension(100, 40));
                 		btnDeny.setBackground(Color.decode("#ebf2fc"));
                 		btnDeny.setFont(fontbtn);
                 		pnBottom.add(btnDeny);
                 		
                 		
                 		
                 		frame.add(content,BorderLayout.CENTER);
                 		frame.add(pntitle,BorderLayout.NORTH);
                 		frame.add(pnL,BorderLayout.WEST);
                 		frame.add(pnR,BorderLayout.EAST);
                 		frame.add(pnBottom,BorderLayout.SOUTH);
                
                     }
                 }
            }
        });
        listCourse.setFont(fontTable);
        JScrollPane SpCourse=new JScrollPane(listCourse);
        return SpCourse;
	}
	
	private void refreshTable() {
        model.setRowCount(0); // Xóa tất cả dòng trong mô hình
        List<OnlineCourseDTO> list = olBUS.findAll();
        for (OnlineCourseDTO course : list) {
            Object[] duLieu = {course.getId(), course.getTittle(), course.getMaKhoa(), course.getCredits(), course.getUrl()};
            model.addRow(duLieu);
        }
    }
	
	private void search(String a,String b) {
        model.setRowCount(0); // Xóa tất cả dòng trong mô hình
        List<OnlineCourseDTO> list = olBUS.findByCondition(a,b);
        if(list!=null) {
	        for (OnlineCourseDTO course : list) {
	            Object[] duLieu = {course.getId(), course.getTittle(), course.getMaKhoa(), course.getCredits(), course.getUrl()};
	            model.addRow(duLieu);
	        }
        }
    }
	
	
	private int getColumnIndex(String columnName) {
        for (int i = 0; i < model.getColumnCount(); i++) {
            if (model.getColumnName(i).equals(columnName)) {
                return i;
            }
        }
        return -1;
    }
	

}
