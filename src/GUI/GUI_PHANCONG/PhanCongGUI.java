package GUI.GUI_PHANCONG;

import BUS.PhanCongBUS;
import DAO.PhanCongDAO;
import DTO.PersonDTO;
import DTO.PhanCongDTO;
import DTO.courseDTO;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Objects;
import java.util.stream.Stream;

public class PhanCongGUI extends JPanel {
    Color primaryColor = Color.decode("#dee9fc");
    private JTable table;
    JButton addButton;
    JButton editButton;
    JButton deleteButton;
    JTextField searchField;
    String[] courseValue;
    String[] personValue;
    JComboBox<String> personComboBox;
    JComboBox<String> courseComboBox;

    PhanCongBUS phanCongBUS = new PhanCongBUS();

    //    tạo để dùng edit
    PhanCongDTO oldPc;

    private TableRowSorter<TableModel> rowSorter = null;

    public PhanCongGUI() {
        setLayout(new BorderLayout());

        // Tạo headerPanel
        JLabel header = new JLabel("Phân công giảng dạy");
        header.setForeground(Color.BLACK);
        header.setHorizontalAlignment(SwingConstants.CENTER);
        Font font = new Font("Segoe UI", Font.BOLD, 25);
        header.setFont(font);
        header.setPreferredSize(new Dimension(300, 40));

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        topPanel.setBackground(primaryColor);
        topPanel.add(header);

        // Tạo bottomPanel với GridBagLayout
        JPanel bottomPanel = createBottomPanel();

        // Thêm headerPanel và bottomPanel vào PhanCongGUI
        add(topPanel, BorderLayout.NORTH);
        add(bottomPanel, BorderLayout.CENTER);
    }

    private JPanel createBottomPanel() {
        JPanel bottomPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        // gridx là cột, gridy là dòng
        // weight là tỷ lệ không gian ngang mà mỗi cột của lưới sẽ mở rộng hoặc co lại khi cửa sổ cha thay đổi kích thước
        // weightx là theo chiều ngang, weighty là theo chiều dọc

        // Thiết lập ràng buộc cho bottomPanel
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;

        // gbc.fill : Thuộc tính này xác định cách mà thành phần sẽ được căn chỉnh bên trong ô của lưới nếu có khoảng trống.
        // GridBagConstraints.BOTH: thành phần sẽ được căn chỉnh để chiếm toàn bộ không gian trống có sẵn trong ô của lưới cả theo chiều ngang và chiều dọc.
        gbc.fill = GridBagConstraints.BOTH;

        // Tạo leftPanel
        JPanel leftPanel = createLeftPanel();
        bottomPanel.add(leftPanel, gbc);

        // Thiết lập ràng buộc cho bottomPanel
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 0.5;

        // Tạo rightPanel
        JPanel rightPanel = createRightPanel();
        bottomPanel.add(rightPanel, gbc);

        return bottomPanel;
    }

    private JPanel createLeftPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(new EmptyBorder(15, 15, 15, 10)); // Thiết lập padding 10px cho mỗi phía

        table = new JTable();
        ArrayList<PhanCongDTO> pcLst = (ArrayList<PhanCongDTO>) phanCongBUS.getAllPhanCong();
        loadDataToTable(pcLst);

        // Thêm MouseListener vào table
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) { // Kiểm tra dòng được chọn
                    String personID = table.getValueAt(selectedRow, 0).toString();
                    String pernameName = table.getValueAt(selectedRow, 1).toString();
                    String courseID = table.getValueAt(selectedRow, 2).toString();
                    String courseTitle = table.getValueAt(selectedRow, 3).toString();

                    // để edit
                    oldPc = new PhanCongDTO(Integer.parseInt(courseID), Integer.parseInt(personID));

                    // JOptionPane.showMessageDialog(PhanCongGUI.this, personID + "-" + pernameName + ";;;" + courseID + "-" + courseTitle);
                    personComboBox.setSelectedItem(personID + "-" + pernameName);
                    courseComboBox.setSelectedItem(courseID + "-" + courseTitle);
                }
            }
        });

        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane, BorderLayout.CENTER);

        JPanel sortPn = new JPanel();
        String[] sortOption = {"Mặc định", "Tên giảng viên", "ID giảng viên"};

        // Tạo JComboBox với danh sách dữ liệu
        JComboBox<String> sortComboBox = new JComboBox<>(sortOption);
        sortComboBox.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        // Click vào sẽ dựa theo lựa chọn để sắp xếp
        sortComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedOption = (String) sortComboBox.getSelectedItem();
                if (selectedOption.equals("Tên giảng viên")) {
                    ArrayList<PhanCongDTO> pcLst = (ArrayList<PhanCongDTO>) phanCongBUS.getAllPhanCongAfterSortingByPersonName();
                    loadDataToTable(pcLst);
                } else if (selectedOption.equals("ID giảng viên")) {
                    ArrayList<PhanCongDTO> pcLst = (ArrayList<PhanCongDTO>) phanCongBUS.getAllPhanCongAfterSortingByPersonID();
                    loadDataToTable(pcLst);
                } else {
                    ArrayList<PhanCongDTO> pcLst = (ArrayList<PhanCongDTO>) phanCongBUS.getAllPhanCong();
                    loadDataToTable(pcLst);
                }
//                JOptionPane.showMessageDialog(PhanCongGUI.this, "Chọn giá trị " + selectedOption);
            }
        });

        sortPn.add(new JLabel(" Sắp xếp theo : "));
        sortPn.add(sortComboBox);
        sortPn.setBorder(new EmptyBorder(0,0,10,0));
        panel.add(sortPn, BorderLayout.NORTH);

        return panel;
    }

    private JPanel createRightPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(new EmptyBorder(15, 10, 15, 15)); // Thiết lập padding 10px cho mỗi phía

        Font buttonFont = new Font("Segoe UI", Font.BOLD, 15);

        addButton = new JButton("Thêm");
        addButton.setFont(buttonFont);
        ImageIcon addButtonIcon = new ImageIcon(new ImageIcon(getClass().getResource("/GUI/assets/save.jpg")).getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH));
        addButton.setIcon(addButtonIcon);
        addButton.setBackground(Color.decode("#ebf2fc"));
        addButton.setForeground(new Color(50,205,50));
        addButton.setHorizontalTextPosition(SwingConstants.LEADING); // Chuyển icon ra sau text
        addButton.setBorder(new EmptyBorder(8, 12, 8, 12));
        addButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        addButton.addActionListener(new AddButtonListener());

        editButton = new JButton("Sửa");
        editButton.setFont(buttonFont);
        ImageIcon editButtonIcon = new ImageIcon(new ImageIcon(getClass().getResource("/GUI/assets/sua.png")).getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH));
        editButton.setIcon(editButtonIcon);
        editButton.setForeground(new Color(0,191,255));
        editButton.setBackground(Color.decode("#ebf2fc"));
        editButton.setHorizontalTextPosition(SwingConstants.LEADING); // Chuyển icon ra sau text
        editButton.setBorder(new EmptyBorder(8, 12, 8, 12));
        editButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        editButton.addActionListener(new EditButtonListener());

        deleteButton = new JButton("Xóa");
        deleteButton.setFont(buttonFont);
        ImageIcon deleteButtonIcon = new ImageIcon(new ImageIcon(getClass().getResource("/GUI/assets/xoa.png")).getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH));
        deleteButton.setIcon(deleteButtonIcon);
        deleteButton.setForeground(Color.red);
        deleteButton.setBackground(Color.decode("#ebf2fc"));
        deleteButton.setHorizontalTextPosition(SwingConstants.LEADING); // Chuyển icon ra sau text
        deleteButton.setBorder(new EmptyBorder(8, 12, 8, 12));
        deleteButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        deleteButton.addActionListener(new DeleteButtonListener());

        JPanel searchPn = new JPanel();
        JPanel teacherPn = new JPanel();
        JPanel subjectPn = new JPanel();
        JPanel buttonPn = new JPanel();

        Font textFieldFont = new Font("Segoe UI", Font.BOLD, 13);

        searchField = new JTextField();
        searchField.setPreferredSize(new Dimension(200, 30));
        setBorderForJTextField(searchField);
        searchField.setFont(textFieldFont);

        // tìm kiếm theo tên giảng viên
        // tạo TableRowSorter để có thể sắp xếp và lọc các hàng trong bảng
        rowSorter = new TableRowSorter<>(table.getModel());
        table.setRowSorter(rowSorter);
        searchField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                String text = searchField.getText().trim();
                if (text.isEmpty()) {
                    rowSorter.setRowFilter(null);
                } else {
                    // số 1 là tìm kiếm theo cột thứ 2 (vì bắt đầu từ 0,1,...)
                    // "(?i)" + text tạo ra một biểu thức chính quy trong đó việc tìm kiếm sẽ không phân biệt chữ hoa chữ thường và sẽ tìm kiếm dựa trên chuỗi text.
                    rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text, 1));
                }
            }
            @Override
            public void removeUpdate(DocumentEvent e) {
                String text = searchField.getText().trim();
                if (text.isEmpty()) {
                    rowSorter.setRowFilter(null);
                } else {
                    // số 1 là tìm kiếm theo cột thứ 2 (vì bắt đầu từ 0,1,...)
                    // "(?i)" + text tạo ra một biểu thức chính quy trong đó việc tìm kiếm sẽ không phân biệt chữ hoa chữ thường và sẽ tìm kiếm dựa trên chuỗi text.
                    rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text, 1));
                }
            }
            @Override
            public void changedUpdate(DocumentEvent e) {
            }
        });

        // Danh sách dữ liệu cho JComboBox
        ArrayList<courseDTO> courseLst = (ArrayList<courseDTO>) PhanCongDAO.getAllCourse();
        ArrayList<PersonDTO> personLst = (ArrayList<PersonDTO>) PhanCongDAO.getAllPerson();
        courseValue = Stream.concat(
                Stream.of(""),
                courseLst.stream()
                        .map(courseDTO -> courseDTO.getId() + "-" + courseDTO.getTittle())
        ).toArray(size -> new String[size]);

        personValue = Stream.concat(
                Stream.of(""),
                personLst.stream()
                        .map(personDTO -> personDTO.getId() + "-" + personDTO.getFirstName() + " " + personDTO.getLastName())
        ).toArray(size -> new String[size]);

        // Tạo JComboBox với danh sách dữ liệu
        personComboBox = new JComboBox<>(personValue);
        personComboBox.setPreferredSize(new Dimension(200, 30));

        courseComboBox = new JComboBox<>(courseValue);
        courseComboBox.setPreferredSize(new Dimension(200, 30));

        JLabel search = new JLabel(" Tìm kiếm theo tên GV :");
        search.setFont(new Font("Segoe UI", Font.BOLD, 15));

        searchPn.add(search);
        searchPn.add(searchField);
        teacherPn.add(ceateStyleJLabel(new JLabel("Giảng viên")));
        teacherPn.add(personComboBox);
        subjectPn.add(ceateStyleJLabel(new JLabel(" Môn học  ")));
        subjectPn.add(courseComboBox);
        buttonPn.add(addButton);
        buttonPn.add(editButton);
        buttonPn.add(deleteButton);

        JPanel suggest = new JPanel();
        JLabel format = new JLabel("*Theo định dạng \"ID-Tên\" VD: 01-Đạt => ID:01, Tên:Đạt");
        suggest.add(format);

        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));

        formPanel.add(Box.createVerticalGlue()); // Glue component để căn giữa phần tử đầu tiên
        formPanel.add(searchPn);
        formPanel.add(suggest);
        formPanel.add(teacherPn);
        formPanel.add(subjectPn);
        formPanel.add(buttonPn);
        formPanel.add(Box.createVerticalGlue()); // Glue component để căn giữa phần tử cuối cùng

        panel.add(formPanel, BorderLayout.CENTER);

        return panel;
    }

    // Xử lý sự kiện khi JButton được nhấn
    private class AddButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try {
                String person = Objects.requireNonNull(personComboBox.getSelectedItem()).toString();
                String course = Objects.requireNonNull(courseComboBox.getSelectedItem()).toString();
                if (person.equals("") || course.trim().equals(""))
                    JOptionPane.showMessageDialog(PhanCongGUI.this,"Vui lòng nhập đủ thông tin");
                else {
                    String[] personParts = person.split("-"); // Tách chuỗi dựa trên dấu gạch ngang "-"
                    String personID = personParts[0]; // Lấy phần tử đầu tiên là ID

                    String[] courseParts = course.split("-"); // Tách chuỗi dựa trên dấu gạch ngang "-"
                    String courseID = courseParts[0]; // Lấy phần tử đầu tiên là ID

                    // JOptionPane.showMessageDialog(PhanCongGUI.this, personID + "---" + courseID);
                    PhanCongDTO pc = new PhanCongDTO();
                    pc.setPersonId(Integer.parseInt(personID));
                    pc.setCourseId(Integer.parseInt(courseID));
                    JOptionPane.showMessageDialog(PhanCongGUI.this, phanCongBUS.addPhanCong(pc));

                    ArrayList<PhanCongDTO> newPcLst = (ArrayList<PhanCongDTO>) phanCongBUS.getAllPhanCong();
                    loadDataToTable(newPcLst);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(PhanCongGUI.this, "Thông tin không hợp lệ");
            }
        }
    }
    private class EditButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try {
                String person = Objects.requireNonNull(personComboBox.getSelectedItem()).toString();
                String course = Objects.requireNonNull(courseComboBox.getSelectedItem()).toString();
                if (person.equals("") || course.trim().equals(""))
                    JOptionPane.showMessageDialog(PhanCongGUI.this,"Vui lòng chọn thông tin cần sửa");
                else {
                    String[] personParts = person.split("-"); // Tách chuỗi dựa trên dấu gạch ngang "-"
                    String personID = personParts[0]; // Lấy phần tử đầu tiên là ID

                    String[] courseParts = course.split("-"); // Tách chuỗi dựa trên dấu gạch ngang "-"
                    String courseID = courseParts[0]; // Lấy phần tử đầu tiên là ID

                    // JOptionPane.showMessageDialog(PhanCongGUI.this, personID + "---" + courseID);
                    PhanCongDTO newPC = new PhanCongDTO();
                    newPC.setPersonId(Integer.parseInt(personID));
                    newPC.setCourseId(Integer.parseInt(courseID));
                    JOptionPane.showMessageDialog(PhanCongGUI.this, phanCongBUS.editPhanCong(newPC, oldPc));

                    ArrayList<PhanCongDTO> newPcLst = (ArrayList<PhanCongDTO>) phanCongBUS.getAllPhanCong();
                    loadDataToTable(newPcLst);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(PhanCongGUI.this, "Thông tin không hợp lệ");
            }
        }
    }

    // Lớp inner để xử lý sự kiện cho button2
    private class DeleteButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try {
                String person = Objects.requireNonNull(personComboBox.getSelectedItem()).toString();
                String course = Objects.requireNonNull(courseComboBox.getSelectedItem()).toString();
                if (person.equals("") || course.trim().equals(""))
                    JOptionPane.showMessageDialog(PhanCongGUI.this,"Vui lòng chọn thông tin cần xóa");
                else {
                    String[] personParts = person.split("-"); // Tách chuỗi dựa trên dấu gạch ngang "-"
                    int personID = Integer.parseInt(personParts[0]); // Lấy phần tử đầu tiên là ID

                    String[] courseParts = course.split("-"); // Tách chuỗi dựa trên dấu gạch ngang "-"
                    int courseID = Integer.parseInt(courseParts[0]); // Lấy phần tử đầu tiên là ID

                    // JOptionPane.showMessageDialog(PhanCongGUI.this, personID + "---" + courseID);
                    JOptionPane.showMessageDialog(PhanCongGUI.this, phanCongBUS.deletePhanCong(personID, courseID));

                    ArrayList<PhanCongDTO> newPcLst = (ArrayList<PhanCongDTO>) phanCongBUS.getAllPhanCong();
                    loadDataToTable(newPcLst);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(PhanCongGUI.this, "Thông tin không hợp lệ");
            }
        }
    }

    public void loadDataToTable(ArrayList<PhanCongDTO> pcLst) {
        if(pcLst.isEmpty()) {
            return;
        }

        DefaultTableModel defaultTableModel = new DefaultTableModel();
        defaultTableModel.addColumn("ID GV");
        defaultTableModel.addColumn("Tên giảng viên");
        defaultTableModel.addColumn("ID MH");
        defaultTableModel.addColumn("Tên môn học");

        // Đặt font cho tiêu đề cột
        Font headerFont = new Font("Segoe UI", Font.BOLD, 15);
        JTableHeader tableHeader = table.getTableHeader();
        tableHeader.setFont(headerFont);
        tableHeader.setBackground(Color.decode("#dee9fc")); // Màu nền
        tableHeader.setPreferredSize(new Dimension(1, 30));
        tableHeader.setBorder(null);

        table.setModel(defaultTableModel);
        table.setBorder(null);
        table.setRowHeight(35); // Thiết lập chiều cao cho các dòng trong bảng là 40
        table.setFont(new Font("Segoe UI", Font.BOLD, 13)); // Thiết lập font cho các dòng trong bảng
        table.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.getColumnModel().getColumn(0).setPreferredWidth(50);
        // bỏ viền các dòng trong jtable
        table.setShowGrid(false);

        // Truy cập vào model của bảng để điều chỉnh kích thước cột và font chữ
        TableColumnModel columnModel = table.getColumnModel();
        // Đặt chiều rộng cho các cột
        int[] columnWidths = {80, 200, 80, 200}; // Chiều rộng của mỗi cột
        for (int i = 0; i < columnWidths.length; i++) {
            TableColumn column = columnModel.getColumn(i);
            column.setPreferredWidth(columnWidths[i]);
        }

        TableCellRenderer renderer = new CustomCourseInstructorTableRenderCell();
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(renderer);
        }

        Object rowsData = null;
        for (PhanCongDTO pc : pcLst) {
            PersonDTO giangvien = PhanCongDAO.getPersonByID(pc.getPersonId());
            courseDTO monhoc = PhanCongDAO.getCourseByID(pc.getCourseId());

            String tenGV = giangvien.getFirstName() + " " + giangvien.getLastName();
            rowsData = new Object[]{giangvien.getId(), tenGV, monhoc.getId(), monhoc.getTittle()};
            defaultTableModel.addRow((Object[]) rowsData);
        }

        table.setModel(defaultTableModel);
    }

    public JPanel ceateStyleJLabel(JLabel newLp) {
        JLabel lp = newLp;
        lp.setForeground(Color.BLACK);
        lp.setHorizontalAlignment(SwingConstants.CENTER);
        lp.setVerticalAlignment(SwingConstants.CENTER);
        Font font = new Font("Segoe UI", Font.BOLD, 15);
        lp.setFont(font);

        JPanel panel = new JPanel(new BorderLayout()); // Sử dụng BorderLayout
        panel.setBackground(primaryColor);
        panel.add(lp, BorderLayout.CENTER);
        panel.setBorder(new EmptyBorder(5, 8, 5, 8)); // tạo padding

        return panel;
    };

    public void setBorderForJTextField(JTextField textField) {
        // Tạo CompoundBorder để kết hợp EmptyBorder và LineBorder
        Border paddingBorder = new EmptyBorder(5, 10, 5, 10); // Padding 5px ở trên/dưới và 10px trái/phải
        Border border = BorderFactory.createLineBorder(Color.BLACK); // Viền đen
        Border compoundBorder = BorderFactory.createCompoundBorder(border, paddingBorder);

        // Đặt CompoundBorder cho JTextField
        textField.setBorder(compoundBorder);
    }
}
