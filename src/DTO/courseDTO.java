package DTO;

public class courseDTO {
	private int id;
	private String tittle;
	private int credits;
	private int maKhoa;
	
	public courseDTO() {
	}

	public courseDTO(int id, String tittle, int credits, int maKhoa) {
		this.id = id;
		this.tittle = tittle;
		this.credits = credits;
		this.maKhoa = maKhoa;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTittle() {
		return tittle;
	}

	public void setTittle(String tittle) {
		this.tittle = tittle;
	}

	public int getCredits() {
		return credits;
	}

	public void setCredits(int credits) {
		this.credits = credits;
	}

	public int getMaKhoa() {
		return maKhoa;
	}

	public void setMaKhoa(int maPhong) {
		this.maKhoa = maPhong;
	}
	
	
}
