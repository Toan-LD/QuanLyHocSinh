package GUI.GUI_KETQUA;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;
import javax.swing.plaf.basic.BasicComboBoxUI;
import javax.swing.plaf.basic.BasicComboPopup;
import javax.swing.plaf.basic.ComboPopup;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import BUS.KetQuaBUS;


public class KetQuaGUI extends JPanel{
	//----------MAIN__COMPONENTS------------
	private JPanel pnNorth,pnCenter,pnSouth,pnNorthTittle,pnNorthContent,pnNorthContentLeft,pnNorthContentRight,pnNotification;
	private JLabel lbTittle,lbSortText;
	private JButton btnSearch,btnAdd,btnDelete,btnSave,btnPrint,btnRefresh;
	private JComboBox<String> cbbSortContent;
	private JTextField tfSearch;
	private JTable tbMain;
	private JScrollPane scrMain;
	
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
	
	public KetQuaGUI () {
		initComponents();
		componentsStyle();
		componentsColor();
	}
	public void initComponents() {
//		SUB COMPONENTS
		lbTittle=new JLabel("KẾT QUẢ KHÓA HỌC");
		
		btnRefresh=new JButton();
		
		lbSortText=new JLabel("Sắp Xếp");
		
		cbbSortContent=new JComboBox<String>();
		
		tfSearch=new JTextField();
		
		btnSearch=new JButton("Tìm Kiếm");
		
		tbMain=new JTable();
		scrMain = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrMain.setBorder(BorderFactory.createEmptyBorder());
		scrMain.setViewportView(tbMain);
		renderTB(tbMain);
		renderData(tbMain);
		scrMain.setViewportBorder(null);

		tbMain.setShowGrid(false);
		tbMain.setIntercellSpacing(new Dimension(0, 0));
		TableCellRenderer renderer = new CustomTableCellRenderer();
		for (int i = 0; i < tbMain.getColumnCount(); i++) {
			tbMain.getColumnModel().getColumn(i).setCellRenderer(renderer);
		}
		tbMain.setRowHeight(35);
		tbMain.getTableHeader().setPreferredSize(new Dimension(1, 30));
		tbMain.getTableHeader().setFont(fontTable);
		tbMain.getTableHeader().setBorder(null);
		tbMain.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tbMain.getColumnModel().getColumn(0).setPreferredWidth(50);
		
		tbMain.getColumnModel().getColumn(1).setPreferredWidth(200);
		tbMain.getColumnModel().getColumn(2).setPreferredWidth(200);
		tbMain.getColumnModel().getColumn(3).setPreferredWidth(50);
		tbMain.getColumnModel().getColumn(4).setPreferredWidth(50);
		tbMain.getColumnModel().getColumn(5).setPreferredWidth(50);
		
		btnAdd=new JButton("Thêm");
		
		btnDelete=new JButton("Xóa");
		
		btnSave=new JButton("Lưu");
		
		btnPrint=new JButton("In");
//		NOTIFICATION PANEL
		pnNotification=new JPanel();
		pnNotification.setLayout(new FlowLayout(FlowLayout.LEFT));
		JLabel lbNotification=new JLabel("*Nhấn vào biểu tượng ở cột chi tiết để xem chi tiết");
		lbNotification.setFont(sgUI13I);
		lbNotification.setForeground(Color.gray);
		pnNotification.add(lbNotification);
//		NORTH PANEL CONTENT LEFT
		pnNorthContentLeft=new JPanel();
		pnNorthContentLeft.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
		pnNorthContentLeft.add(lbSortText);
		pnNorthContentLeft.add(cbbSortContent);
//		NORTH PANEL CONTENT RIGHT
		pnNorthContentRight=new JPanel();
		pnNorthContentRight.setLayout(new FlowLayout(FlowLayout.RIGHT, 10, 10));
		pnNorthContentRight.add(tfSearch);
		pnNorthContentRight.add(btnSearch);
		
//		NORTH PANEL CONTENT
		pnNorthContent =new JPanel();
		pnNorthContent.setLayout(new FlowLayout(FlowLayout.CENTER, 100, 10));
		pnNorthContent.add(pnNorthContentLeft);
		pnNorthContent.add(pnNorthContentRight);
		
		
//		NORTH PANEL TITTLE
		pnNorthTittle=new JPanel();
		pnNorthTittle.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
		pnNorthTittle.add(lbTittle);
		pnNorthTittle.add(btnRefresh);
		
//		NORTH PANEL
		pnNorth=new JPanel();
		pnNorth.setLayout(new BorderLayout());
		pnNorth.add(pnNorthTittle,BorderLayout.NORTH);
		pnNorth.add(pnNorthContent,BorderLayout.CENTER);
		
//		CENTER PANEL
		pnCenter=new JPanel();
		pnCenter.setLayout(new BorderLayout());
		pnCenter.add(pnNotification,BorderLayout.NORTH);
		pnCenter.add(scrMain,BorderLayout.CENTER);
		
//		SOUTH PANEL
		pnSouth=new JPanel();
		pnSouth.setLayout(new FlowLayout(FlowLayout.RIGHT, 10, 10));
		pnSouth.add(btnAdd);
		pnSouth.add(btnDelete);
		pnSouth.add(btnSave);
		pnSouth.add(btnPrint);
		
		
//		THE BIGGEST PANEL
		this.setLayout(new BorderLayout());
		this.add(pnNorth,BorderLayout.NORTH);
		this.add(pnCenter,BorderLayout.CENTER);
		this.add(pnSouth,BorderLayout.SOUTH);
		
	}
	
//	FONTS,SIZES,ICONS... OF COMPONENTS
	public void componentsStyle() {
		lbTittle.setFont(fontTittle);
		lbTittle.setSize(300, 50);
		
		btnRefresh.setSize(30, 30);
		
		lbSortText.setFont(sgUI15);
		lbSortText.setPreferredSize(new Dimension(100, 40));
		
		cbbSortContent.setBorder(new MatteBorder(2, 2, 2, 0, Color.decode("#EFEFEF")));
		cbbSortContent.setBackground(Color.white);
		cbbSortContent.setFont(sgUI13);
		cbbSortContent.setPreferredSize(new Dimension(200, 30));
		cbbSortContent.setUI(new BasicComboBoxUI() {
            @Override
            protected ComboPopup createPopup() {
                BasicComboPopup basicComboPopup = new BasicComboPopup(comboBox);
                basicComboPopup.setBorder(lineCB);
                return basicComboPopup;
            }
        });
		cbbSortContent.setBorder(matteBorderCB);
		
		tfSearch.setPreferredSize(new Dimension(200, 30));
		tfSearch.setFont(sgUI13);
		tfSearch.setBorder(BorderFactory.createCompoundBorder(borderTxt, new EmptyBorder(0, 3, 0, 3)));
		tfSearch.setForeground(Color.black);
		
		ImageIcon iconSearch = new ImageIcon(new ImageIcon(getClass().getResource("/GUI/assets/searchIcon.png")).getImage()
				.getScaledInstance(20, 20, Image.SCALE_SMOOTH));
		btnSearch.setIcon(iconSearch);
		btnSearch.setFont(sgUI13b);
		btnSearch.setFocusPainted(false);
		btnSearch.setBorderPainted(false);
		
		ImageIcon addIcon = new ImageIcon(new ImageIcon(getClass().getResource("/GUI/assets/btnAdd.png")).getImage()
				.getScaledInstance(20, 20, Image.SCALE_SMOOTH));
		btnAdd.setIcon(addIcon);
		btnAdd.setFont(sgUI13b);
		btnAdd.setFocusPainted(false);
		btnAdd.setBorderPainted(false);
		
		ImageIcon delIcon = new ImageIcon(new ImageIcon(getClass().getResource("/GUI/assets/btnDel.png")).getImage()
				.getScaledInstance(20, 20, Image.SCALE_SMOOTH));
		btnDelete.setIcon(delIcon);
		btnDelete.setFont(sgUI13b);
		btnDelete.setFocusPainted(false);
		btnDelete.setBorderPainted(false);
		
		ImageIcon saveIcon = new ImageIcon(new ImageIcon(getClass().getResource("/GUI/assets/btnSave.png")).getImage()
				.getScaledInstance(20, 20, Image.SCALE_SMOOTH));
		btnSave.setIcon(saveIcon);
		btnSave.setFont(sgUI13b);
		btnSave.setFocusPainted(false);
		btnSave.setBorderPainted(false);
		
		ImageIcon printIcon = new ImageIcon(new ImageIcon(getClass().getResource("/GUI/assets/btnPrint.png")).getImage()
				.getScaledInstance(20, 20, Image.SCALE_SMOOTH));
		btnPrint.setIcon(printIcon);
		btnPrint.setFont(sgUI13b);
		btnPrint.setFocusPainted(false);
		btnPrint.setBorderPainted(false);
		
		ImageIcon refreshIcon = new ImageIcon(new ImageIcon(getClass().getResource("/GUI/assets/Refresh-icon.png")).getImage()
				.getScaledInstance(20, 20, Image.SCALE_SMOOTH));
		btnRefresh.setIcon(refreshIcon);
		btnRefresh.setFont(sgUI13b);
		btnRefresh.setFocusPainted(false);
		btnRefresh.setBorderPainted(false);
		
		
	}
	
//	COLORS OF COMPONENTS
	public void componentsColor() {
		pnCenter.setBackground(Color.white);
		pnNorth.setBackground(Color.white);
		pnNorthContent.setBackground(Color.white);
		pnNorthTittle.setBackground(Color.white);
		pnSouth.setBackground(Color.white);
		pnNorthContentLeft.setBackground(Color.white);
		pnNorthContentRight.setBackground(Color.white);
		pnNotification.setBackground(Color.white);
		this.setBackground(Color.white);
		
		btnAdd.setBackground(Color.decode("#ebf2fc"));
		btnDelete.setBackground(Color.decode("#ebf2fc"));
		btnPrint.setBackground(Color.decode("#ebf2fc"));
		btnRefresh.setBackground(this.getBackground());
		btnSave.setBackground(Color.decode("#ebf2fc"));
		btnSearch.setBackground(Color.decode("#ebf2fc"));
		scrMain.getViewport().setBackground(Color.white);
		tbMain.getTableHeader().setBackground(Color.decode(colorTableCode));
	}
	
//	VALUE OF SORT COMBOBOX
	public void initSortComboboxValue() {
		
	}
	
//	EVENT
	public void events() {
		
	}
	public void renderTB(JTable tbP) {
		DefaultTableModel dtm = new DefaultTableModel();
		dtm.addColumn("ID Khóa Học");
		dtm.addColumn("Tên Khóa Học");
		dtm.addColumn("Họ Và Tên");
		dtm.addColumn("Điểm Số");
		dtm.addColumn("Chi Tiết");
		dtm.addColumn("Chọn");

		tbP.setModel(dtm);
		tbP.setBorder(new MatteBorder(1, 1, 1, 1, Color.white));
	}
	public void renderData(JTable tb) {
		DefaultTableModel model = (DefaultTableModel) tb.getModel();
//		for (HoaDonDTO hoaDon : HoaDonBUS.getIntance().getList()) {
//			Vector<String> vec = new Vector<>();
//			vec.add(hoaDon.getMaHD());
//			vec.add(NhanVienBUS.getIntance()
//					.layTenNVtheoMA(ChiTietThueBUS.getInstance().selectById(hoaDon.getMaCTT()).getMaNV()));
//			vec.add(hoaDon.getMaCTT());
//			vec.add(dcf.format(hoaDon.getTienP()) + " VNĐ");
//			vec.add(dcf.format(hoaDon.getTienDV()) + " VNĐ");
//			vec.add(hoaDon.getGiamGia() + " %");
//			vec.add(dcf.format(hoaDon.getPhuThu()) + " %");
//			vec.add(dcf.format(hoaDon.getTongTien()) + " VNĐ");
//			vec.add(hoaDon.getNgayThanhToan());
//			vec.add(hoaDon.getPhuongThucThanhToan());
//			vec.add(KhachHangBUS.getIntance()
//					.layTenBangMa(ChiTietThueBUS.getInstance().selectById(hoaDon.getMaCTT()).getMaKH()));
//			model.addRow(vec);
//
//		}
		for (Vector<String> vec : KetQuaBUS.getInstance().getListTable()) {
			model.addRow(vec);
		}
	}
}
