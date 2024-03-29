package BUS;

import java.util.ArrayList;
import java.util.Vector;

import DAO.KetQuaDAO;
import GUI.GUI_BASIC.ThongBaoDialog;

public class KetQuaBUS {
	public static KetQuaBUS getInstance() {
		return new KetQuaBUS();
	}
	public Vector<Vector<String>> getListTable() {
		
		return KetQuaDAO.getInstance().getListTable();
	}
	public Vector<String> getListCourse() {
		return KetQuaDAO.getInstance().getListCourse();
	}
	public Vector<String> getListStudent() {
		return KetQuaDAO.getInstance().getListStudent();
	}
	public int getIdbyFullName(String fullName) {
		return KetQuaDAO.getInstance().getIdbyFullName(fullName);
	}
	public String getTeacherNameByCourseId(int courseId) {
		return KetQuaDAO.getInstance().getTeacherNameByCourseId(courseId);
	}
	public int updateGrade(int grade, int courseid, String fullname) {
		int studentid=getIdbyFullName(fullname);
		int check=KetQuaDAO.getInstance().updateGrade(grade, courseid, studentid);
		if(check==0) {
			new ThongBaoDialog("Cập nhật thất bại", ThongBaoDialog.ERROR_DIALOG);
			return 0;
		}
		return check;
	}
	public int cancel(int courseid, String fullname) {
		new ThongBaoDialog("Hủy kết quả của "+fullname, ThongBaoDialog.WARNING_DIALOG);
		if(ThongBaoDialog.action==ThongBaoDialog.CANCEL_OPTION) {
			return 0;
		}
		int studentid=getIdbyFullName(fullname);
		int check = KetQuaDAO.getInstance().cancel(courseid, studentid);
		if(check!=0) {
			new ThongBaoDialog("Thành công", ThongBaoDialog.SUCCESS_DIALOG);
		}
		return check;
	}
	
	public int delete(int courseid, String fullName) {
		int studentid= getIdbyFullName(fullName);
		int check =KetQuaDAO.getInstance().delete(courseid, studentid);
		if(check==0) {
			new ThongBaoDialog("Xóa thất bại", ThongBaoDialog.ERROR_DIALOG);
			return 0;
		}
		else {
			new ThongBaoDialog("Xóa thành công", ThongBaoDialog.SUCCESS_DIALOG);
		}
		return check;
		
	}
}
