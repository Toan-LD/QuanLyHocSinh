/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package GUI.GUI_ONSITECOURSE;

import BUS.OnsiteCourseService;
import BUS.OnsiteCourseServiceImpl;
import DAO.ConnectDB;
import DTO.OnsiteCourseDTO;
import GUI.GUI_KETQUA.CustomTableCellRenderer;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.RowFilter;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author Admin
 */
public class jpnOnsite extends javax.swing.JPanel {
    

//	FORMAT
	private Font sgUI15 = new Font("Segoe UI", Font.BOLD, 15);
	private Font sgUI15p = new Font("Segoe UI", Font.PLAIN, 15);
	private Font sgUI15I = new Font("Segoe UI", Font.ITALIC, 15);
	private Font sgUI13 = new Font("Segoe UI", Font.PLAIN, 13);
	private Font sgUI13I = new Font("Segoe UI", Font.ITALIC, 13);
	private Font sgUI13b = new Font("Segoe UI", Font.BOLD, 13);
	private Font sgUI18b = new Font("Segoe UI", Font.BOLD, 17);
	private Font tNR13 = new Font("Times New Roman", Font.ITALIC, 13);
	private Font fontTittle = new Font("Tahoma", Font.BOLD, 25);
	private Font fontSubTittle = new Font("Tahoma", Font.BOLD, 20);
	private Font fontTable = new Font("Segoe UI", Font.BOLD, 13);
	private DecimalFormat dcf = new DecimalFormat("###,###");
	private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	private Color btnoldColor = new Color(52, 152, 219);
	private Color texfieldColor = new Color(45, 52, 54);
	private String colorTableCode = "#dee9fc";
	MatteBorder matteBorderCB = new MatteBorder(2, 2, 2, 2, Color.decode("#EFEFEF"));
	LineBorder lineCB = new LineBorder(Color.white);
	MatteBorder matteBorderCBDark = new MatteBorder(2, 2, 2, 2, Color.decode("#919191"));
	MatteBorder borderTxt = new MatteBorder(2, 2, 2, 2, Color.decode("#EFEFEF"));
	MatteBorder borderTxtDark = new MatteBorder(2, 2, 2, 2, Color.decode("#919191"));
	EmptyBorder emptyBorderTxt = new EmptyBorder(0, 7, 0, 7);
	EmptyBorder emptyBorderCB = new EmptyBorder(0, 7, 0, 0);

    OnsiteCourseDTO onsite = new OnsiteCourseDTO();

    /**
     * Creates new form QLOnsite
     */
    public jpnOnsite() {
        initComponents();
        showTable();
    }
    
    public void showTable() {
        OnsiteCourseService onsiteCourseService = new OnsiteCourseServiceImpl();
        List<OnsiteCourseDTO> list = onsiteCourseService.getList();
        
        DefaultTableModel dtm = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }           
        };
        
        onsiteTable.setModel(dtm);
        
        DefaultTableModel model = (DefaultTableModel) onsiteTable.getModel();
        
        model.setColumnIdentifiers(new Object[]{
            "ID", "Tên môn học", "Mã khoa", "Thứ", "Thời gian", "Địa điểm", "Số tín chỉ"
        });
        
        for(OnsiteCourseDTO onsiteCourse: list) {
            model.addRow(new Object[]{
                onsiteCourse.getId(), onsiteCourse.getTittle(),onsiteCourse.getMaKhoa(),onsiteCourse.getDays(),onsiteCourse.getTime(),onsiteCourse.getLocation(), onsiteCourse.getCredits()
            });
        }
        
        jtfSearch.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                performSearch();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                performSearch();
            }

            @Override
            
            public void changedUpdate(DocumentEvent e) {
            }
        });
        
        
        onsiteTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(e.getClickCount() == 1 && onsiteTable.getSelectedRow() != -1) {
                    DefaultTableModel model = (DefaultTableModel) onsiteTable.getModel();
                    int selectedRowIndex1 = onsiteTable.getSelectedRow();
                    int selectedRowIndex = onsiteTable.convertRowIndexToModel(selectedRowIndex1);
                    
                    onsite.setId((int) model.getValueAt(selectedRowIndex,0));
                    onsite.setTittle(model.getValueAt(selectedRowIndex, 1).toString());
                    onsite.setMaKhoa((int) model.getValueAt(selectedRowIndex, 2));
                    onsite.setDays(model.getValueAt(selectedRowIndex, 3).toString());
                    onsite.setTime((Date) model.getValueAt(selectedRowIndex, 4));
                    onsite.setLocation(model.getValueAt(selectedRowIndex, 5).toString());
                    onsite.setCredits((int) model.getValueAt(selectedRowIndex, 6));                    
                    }
            }
            
        });
    }
    
    private void performSearch() {
        
        TableRowSorter<TableModel> rowSorter = new TableRowSorter<>(onsiteTable.getModel());
        onsiteTable.setRowSorter(rowSorter);
        
        String text = jtfSearch.getText();

        List<RowFilter<Object, Object>> filters = new ArrayList<>();

        if (text.trim().length() > 0) {
            filters.add(RowFilter.regexFilter("(?i)" + text));
        }


        RowFilter<Object, Object> combinedFilter = RowFilter.andFilter(filters);
        rowSorter.setRowFilter(combinedFilter);
    }

    
    public void reset() {
        jtfSearch.setText("");
//        showTable();
    }
    
    public boolean ktNhapDayDu() {
        if(jtfId.getText() == null ||
            jtfTenMonHoc.getText() == null ||
            jtfMaKhoa.getText() == null ||
            jtfNgay.getText() == null ||
            jdcThoiGian.getDate() == null ||
            jtfDiaDiem.getText() == null ||
            jtfSoTinChi.getText() == null) {
            JOptionPane.showMessageDialog(this, "Bạn nhập thiếu thông tin", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            return false;
        }
        return true;        
    }
    
    public boolean ktID() {
        OnsiteCourseService onsiteService = new OnsiteCourseServiceImpl();
        List<OnsiteCourseDTO> list = onsiteService.getList();
        
        for(OnsiteCourseDTO os: list) {
            if(jtfId.getText().equals(String.valueOf(os.getId()))) {
                return false;
            }    
        }
        
        return true;
    }
    
    public void insert(){
        if(ktNhapDayDu())
        if(ktID())
            try {
                Connection cons = ConnectDB.getConnection();
                String sql1 = "INSERT INTO course (CourseID, Title, Credits, DepartmentID) VALUES (?, ?, ?, ?)";
                PreparedStatement ps1 = cons.prepareStatement(sql1);
                
                ps1.setString(1, jtfId.getText());
                ps1.setString(2, jtfTenMonHoc.getText());
                ps1.setString(3, jtfSoTinChi.getText());
                ps1.setString(4, jtfMaKhoa.getText());
                
                ps1.executeUpdate();

                ps1.close();
                
                String sql = "INSERT INTO onsitecourse (CourseID, Location, Days, Time) VALUES (?, ?, ?, ?)";
                PreparedStatement ps = cons.prepareStatement(sql);

                ps.setString(1, jtfId.getText());
                ps.setString(2, jtfDiaDiem.getText());
                ps.setString(3, jtfNgay.getText());
                ps.setString(4, jtfNgay.getText());

                int rowsAffected = ps.executeUpdate();
                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(this, "Thêm thông tin thành công", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                    showTable();
                } else {
                    JOptionPane.showMessageDialog(this, "Thêm thông tin không thành công", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }

                ps.close();
                cons.close();
                showTable();
                jDialog1.setVisible(false);
            } catch (SQLException ex) {
                ex.printStackTrace(); // In ra thông tin chi tiết của lỗi
                JOptionPane.showMessageDialog(this, "Lỗi khi thêm thông tin", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }

        else JOptionPane.showMessageDialog(this, "Đã có ID trong dữ liệu", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
    }
    
    public void delete() {
        
            try {
                Connection cons = ConnectDB.getConnection();
                int id = onsite.getId();
                
                // Xóa khóa học từ bảng 'onsitecourse'
                String sql1 = "DELETE FROM onsitecourse WHERE CourseID = ?";
                PreparedStatement ps1 = cons.prepareStatement(sql1);
                ps1.setInt(1, id);
                ps1.executeUpdate();
                ps1.close();

                // Xóa khóa học từ bảng 'course'
                String sql = "DELETE FROM course WHERE CourseID = ?";
                PreparedStatement ps = cons.prepareStatement(sql);
                ps.setInt(1, id);
                ps.executeUpdate();
                ps.close();

                cons.close();
                JOptionPane.showMessageDialog(this, "Xóa thông tin thành công", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                showTable();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }

    }
    
    public void update() {
        if(ktNhapDayDu())
        if(ktID())
            JOptionPane.showMessageDialog(this, "Không có ID khóa học trong dữ liệu", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        else    
            try {
                Connection cons = ConnectDB.getConnection();
                String id = jtfId.getText();
                String sql1 = "Update course set CourseID = ?, Title = ?, Credits = ?, DepartmentID = ? where CourseID = '" + id + "'";
                PreparedStatement ps1 = cons.prepareStatement(sql1);

                ps1.setString(1, jtfId.getText());
                ps1.setString(2, jtfTenMonHoc.getText());
                ps1.setString(3, jtfSoTinChi.getText());
                ps1.setString(4, jtfMaKhoa.getText());

                ps1.executeUpdate();

                ps1.close();
                
                String sql = "Update onsitecourse set CourseID = ?, Location = ?, Days = ?, Time = ? where CourseID = '" + id + "'";
                PreparedStatement ps = cons.prepareStatement(sql);

                ps.setString(1, jtfId.getText());
                ps.setString(2, jtfDiaDiem.getText());
                ps.setString(3, jtfNgay.getText());
                ps.setString(4, jtfNgay.getText());

                ps.executeUpdate();

                ps.close();
                cons.close();
                JOptionPane.showMessageDialog(this, "Sửa thông tin thành công", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                showTable();
                jDialog1.setVisible(false);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
    
    }
    
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new jpnOnsite().setVisible(true);
            }
        });
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jDialog1 = new javax.swing.JDialog();
        jPanel7 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jtfId = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jtfTenMonHoc = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jtfMaKhoa = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jtfNgay = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jtfDiaDiem = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jtfSoTinChi = new javax.swing.JTextField();
        jdcThoiGian = new com.toedter.calendar.JDateChooser();
        jPanel9 = new javax.swing.JPanel();
        btnThem = new javax.swing.JButton();
        btnSua = new javax.swing.JButton();
        btnThoat = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jtfSearch = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        onsiteScroll = new javax.swing.JScrollPane();
        onsiteTable = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        btnAdd = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        btnUpdate = new javax.swing.JButton();
        btnReset = new javax.swing.JButton();

        jDialog1.setTitle("Thông tin khóa học Onsite");
        jDialog1.setMinimumSize(new java.awt.Dimension(500, 500));
        jDialog1.setModal(true);

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));
        jPanel7.setLayout(new javax.swing.BoxLayout(jPanel7, javax.swing.BoxLayout.Y_AXIS));

        jPanel8.setBackground(new java.awt.Color(255, 255, 255));
        jPanel8.setPreferredSize(new java.awt.Dimension(100, 250));
        java.awt.GridBagLayout jPanel8Layout = new java.awt.GridBagLayout();
        jPanel8Layout.columnWidths = new int[] {0, 5, 0, 5, 0, 5, 0, 5, 0};
        jPanel8Layout.rowHeights = new int[] {0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0};
        jPanel8.setLayout(jPanel8Layout);

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel4.setText("ID");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(4, 0, 0, 0);
        jPanel8.add(jLabel4, gridBagConstraints);

        jtfId.setMinimumSize(new java.awt.Dimension(64, 30));
        jtfId.setPreferredSize(new java.awt.Dimension(200, 30));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 8;
        gridBagConstraints.gridy = 0;
        jPanel8.add(jtfId, gridBagConstraints);

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel5.setText("Tên môn học");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 8;
        jPanel8.add(jLabel5, gridBagConstraints);

        jtfTenMonHoc.setPreferredSize(new java.awt.Dimension(200, 30));
        jtfTenMonHoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtfTenMonHocActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 8;
        gridBagConstraints.gridy = 8;
        jPanel8.add(jtfTenMonHoc, gridBagConstraints);

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel6.setText("Mã khoa");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 16;
        jPanel8.add(jLabel6, gridBagConstraints);

        jtfMaKhoa.setPreferredSize(new java.awt.Dimension(200, 30));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 8;
        gridBagConstraints.gridy = 16;
        jPanel8.add(jtfMaKhoa, gridBagConstraints);

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel7.setText("Ngày");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 24;
        jPanel8.add(jLabel7, gridBagConstraints);

        jtfNgay.setPreferredSize(new java.awt.Dimension(200, 30));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 8;
        gridBagConstraints.gridy = 24;
        jPanel8.add(jtfNgay, gridBagConstraints);

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel8.setText("Thời gian");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 32;
        jPanel8.add(jLabel8, gridBagConstraints);

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel9.setText("Địa điểm");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 40;
        jPanel8.add(jLabel9, gridBagConstraints);

        jtfDiaDiem.setPreferredSize(new java.awt.Dimension(200, 30));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 8;
        gridBagConstraints.gridy = 40;
        jPanel8.add(jtfDiaDiem, gridBagConstraints);

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel10.setText("Số tín chỉ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 48;
        jPanel8.add(jLabel10, gridBagConstraints);

        jtfSoTinChi.setPreferredSize(new java.awt.Dimension(200, 30));
        jtfSoTinChi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtfSoTinChiActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 8;
        gridBagConstraints.gridy = 48;
        jPanel8.add(jtfSoTinChi, gridBagConstraints);

        jdcThoiGian.setPreferredSize(new java.awt.Dimension(200, 30));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 8;
        gridBagConstraints.gridy = 32;
        jPanel8.add(jdcThoiGian, gridBagConstraints);

        jPanel7.add(jPanel8);

        jPanel9.setBackground(new java.awt.Color(255, 255, 255));
        jPanel9.setMinimumSize(new java.awt.Dimension(236, 50));

        btnThem.setBackground(new java.awt.Color(255, 153, 153));
        btnThem.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        btnThem.setText("Thêm");
        btnThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemActionPerformed(evt);
            }
        });
        jPanel9.add(btnThem);

        btnSua.setBackground(new java.awt.Color(102, 153, 255));
        btnSua.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        btnSua.setText("Sửa");
        btnSua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaActionPerformed(evt);
            }
        });
        jPanel9.add(btnSua);

        btnThoat.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        btnThoat.setText("Thoát");
        btnThoat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThoatActionPerformed(evt);
            }
        });
        jPanel9.add(btnThoat);

        jPanel7.add(jPanel9);

        jDialog1.getContentPane().add(jPanel7, java.awt.BorderLayout.CENTER);

        setBackground(new java.awt.Color(255, 255, 255));
        setPreferredSize(new java.awt.Dimension(900, 700));
        setLayout(new javax.swing.BoxLayout(this, javax.swing.BoxLayout.PAGE_AXIS));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jPanel2.setPreferredSize(new java.awt.Dimension(890, 80));
        jPanel2.setLayout(new javax.swing.BoxLayout(jPanel2, javax.swing.BoxLayout.LINE_AXIS));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel1.setText("Khóa học Onsite");
        jPanel2.add(jLabel1);

        add(jPanel2);

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setPreferredSize(new java.awt.Dimension(890, 50));
        jPanel3.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 5, 0));

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));
        jPanel6.setMinimumSize(new java.awt.Dimension(100, 20));
        jPanel6.setPreferredSize(new java.awt.Dimension(700, 110));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel3.setText("Tìm kiếm:");
        jPanel6.add(jLabel3);

        jtfSearch.setPreferredSize(new Dimension(200, 30));
        jtfSearch.setFont(sgUI13);
        jtfSearch.setBorder(BorderFactory.createCompoundBorder(borderTxt, new EmptyBorder(0, 3, 0, 3)));
        jtfSearch.setForeground(Color.black);

        jPanel6.add(jtfSearch);

        jPanel3.add(jPanel6);

        add(jPanel3);

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setLayout(new java.awt.BorderLayout());

        onsiteTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
//        onsiteTable.setGridColor(new java.awt.Color(255, 255, 255));
//        onsiteTable.setSelectionBackground(new java.awt.Color(102, 153, 255));
//        onsiteScroll.setViewportView(onsiteTable);
        onsiteScroll = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        onsiteScroll.setBorder(BorderFactory.createEmptyBorder());
        onsiteScroll.setViewportView(onsiteTable);
//		renderTB(onsiteTable);
//		renderData(onsiteTable);
        onsiteScroll.setViewportBorder(null);

		onsiteTable.setShowGrid(false);
		onsiteTable.setIntercellSpacing(new Dimension(0, 0));
		TableCellRenderer renderer = new CustomTableOnlsiteCourse();
		for (int i = 0; i < onsiteTable.getColumnCount(); i++) {
			onsiteTable.getColumnModel().getColumn(i).setCellRenderer(renderer);
		}
		onsiteTable.setRowHeight(35);
		onsiteTable.getTableHeader().setPreferredSize(new Dimension(1, 30));
		onsiteTable.getTableHeader().setFont(fontTable);
		onsiteTable.getTableHeader().setBorder(null);
		onsiteTable.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
//		onsiteTable.getColumnModel().getColumn(0).setPreferredWidth(50);
//		onsiteTable.getColumnModel().getColumn(1).setPreferredWidth(200);
//		onsiteTable.getColumnModel().getColumn(2).setPreferredWidth(50);
//		onsiteTable.getColumnModel().getColumn(3).setPreferredWidth(100);
//		onsiteTable.getColumnModel().getColumn(4).setPreferredWidth(100);
//		onsiteTable.getColumnModel().getColumn(5).setPreferredWidth(100);
//		onsiteTable.getColumnModel().getColumn(6).setPreferredWidth(50);
		
		onsiteScroll.getViewport().setBackground(Color.white);
		onsiteTable.getTableHeader().setBackground(Color.decode(colorTableCode));

        jPanel4.add(onsiteScroll, java.awt.BorderLayout.CENTER);

        add(jPanel4);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setPreferredSize(new java.awt.Dimension(890, 70));
        jPanel1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT, 10, 10));

        btnAdd.setBackground(new java.awt.Color(102, 255, 204));
        ImageIcon addIcon = new ImageIcon(new ImageIcon(getClass().getResource("/GUI/assets/them.png")).getImage()
            .getScaledInstance(20, 20, Image.SCALE_SMOOTH));
        btnAdd.setIcon(addIcon);
        btnAdd.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        btnAdd.setText("Thêm");
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });
        jPanel1.add(btnAdd);

        btnDelete.setBackground(new java.awt.Color(255, 153, 153));
        ImageIcon deleteIcon = new ImageIcon(new ImageIcon(getClass().getResource("/GUI/assets/xoa.png")).getImage()
            .getScaledInstance(20, 20, Image.SCALE_SMOOTH));
        btnDelete.setIcon(deleteIcon);
        btnDelete.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        btnDelete.setText("Xóa");
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });
        jPanel1.add(btnDelete);

        btnUpdate.setBackground(new java.awt.Color(102, 153, 255));
        ImageIcon editIcon = new ImageIcon(new ImageIcon(getClass().getResource("/GUI/assets/IconEdit.png")).getImage()
            .getScaledInstance(20, 20, Image.SCALE_SMOOTH));
        btnUpdate.setIcon(editIcon);
        btnUpdate.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        btnUpdate.setText("Sửa");
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
            }
        });
        jPanel1.add(btnUpdate);

        btnReset.setBackground(new java.awt.Color(153, 255, 153));
        ImageIcon resetIcon = new ImageIcon(new ImageIcon(getClass().getResource("/GUI/assets/Refresh-icon.png")).getImage()
            .getScaledInstance(20, 20, Image.SCALE_SMOOTH));
        btnReset.setIcon(resetIcon);
        btnReset.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        btnReset.setText("Reset");
        btnReset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnResetActionPerformed(evt);
            }
        });
        jPanel1.add(btnReset);

        add(jPanel1);
    }// </editor-fold>//GEN-END:initComponents

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        // TODO add your handling code here:
        jDialog1.setLocationRelativeTo(null);
        jDialog1.setVisible(true);
    }//GEN-LAST:event_btnAddActionPerformed

    private void jtfTenMonHocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtfTenMonHocActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jtfTenMonHocActionPerformed

    private void jtfSoTinChiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtfSoTinChiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jtfSoTinChiActionPerformed

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
        // TODO add your handling code here:
        insert();
    }//GEN-LAST:event_btnThemActionPerformed

    private void btnSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaActionPerformed
        // TODO add your handling code here:
        update();
    }//GEN-LAST:event_btnSuaActionPerformed

    private void btnThoatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThoatActionPerformed
        // TODO add your handling code here:
        jDialog1.setVisible(false);
    }//GEN-LAST:event_btnThoatActionPerformed

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        // TODO add your handling code here:
        if(onsite.getId() == 0){
            JOptionPane.showMessageDialog(this, "Bạn vui lòng chọn khóa học cần sửa", "Thông báo", JOptionPane.INFORMATION_MESSAGE);            
        }
        else {
            jtfId.setText(String.valueOf(onsite.getId()));
            jtfTenMonHoc.setText(onsite.getTittle());
            jtfMaKhoa.setText(String.valueOf(onsite.getMaKhoa()));
            jtfNgay.setText(onsite.getDays());
            jdcThoiGian.setDate(onsite.getTime());
            jtfDiaDiem.setText(onsite.getLocation());
            jtfSoTinChi.setText(String.valueOf(onsite.getCredits()));
            
            jDialog1.setLocationRelativeTo(null);
            jDialog1.setVisible(true);
        }
    }//GEN-LAST:event_btnUpdateActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        // TODO add your handling code here:
        if(onsite.getId() == 0)
            JOptionPane.showMessageDialog(this, "Bạn vui lòng chọn khóa học cần xóa", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        else {
            int option = JOptionPane.showConfirmDialog(this, "Bạn chắc chắn muốn xóa không?", "Xác nhận xóa", JOptionPane.YES_NO_OPTION);
            if (option == JOptionPane.YES_OPTION) {
            // Xử lý xóa dữ liệu ở đây
                delete();
            }
        }
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void btnResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResetActionPerformed
        // TODO add your handling code here:
        reset();
    }//GEN-LAST:event_btnResetActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnReset;
    private javax.swing.JButton btnSua;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnThoat;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JDialog jDialog1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private com.toedter.calendar.JDateChooser jdcThoiGian;
    private javax.swing.JTextField jtfDiaDiem;
    private javax.swing.JTextField jtfId;
    private javax.swing.JTextField jtfMaKhoa;
    private javax.swing.JTextField jtfNgay;
    private javax.swing.JTextField jtfSearch;
    private javax.swing.JTextField jtfSoTinChi;
    private javax.swing.JTextField jtfTenMonHoc;
    private javax.swing.JScrollPane onsiteScroll;
    private javax.swing.JTable onsiteTable;
    // End of variables declaration//GEN-END:variables
}
