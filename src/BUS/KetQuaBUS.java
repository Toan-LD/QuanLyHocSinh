package BUS;

import java.util.Vector;

import DAO.KetQuaDAO;

public class KetQuaBUS {
	public static KetQuaBUS getInstance() {
		return new KetQuaBUS();
	}
	public Vector<Vector<String>> getListTable() {
		
		return KetQuaDAO.getInstance().getListTable();
	}
}
