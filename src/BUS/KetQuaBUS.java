package BUS;

import java.util.ArrayList;
import java.util.Vector;

import DAO.KetQuaDAO;

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
}
