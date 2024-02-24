package BUS;

import java.util.List;

import DAO.OnlineCourseDAO;
import DTO.OnlineCourseDTO;

public class OnlineCourseBUS implements iBUS<OnlineCourseDTO>{
	OnlineCourseDAO OlDAO= new OnlineCourseDAO();
	public List<OnlineCourseDTO> findAll(){
		return OlDAO.findAll();
	}
	@Override
	public OnlineCourseDTO findById(int id) {
		return OlDAO.findByID(id);
	}
	@Override
	public String insert(OnlineCourseDTO a) {
		int result=OlDAO.insert(a);
		if(result==0) return "Thêm không thành công";
		if(result==1) return "Thêm thành công";
		if(result==3) return "Mã đã bị trùng";
		return "Thông tin nhập đã sai";
	}
	
	@Override
	public String delete(OnlineCourseDTO a) {
		int result=OlDAO.delete(a);
		if(result==0) return "Xoá không thành công";
		if(result==1) return "Xoá thành công";
		return "Mã không được tìm thấy";
	}
	
	@Override
	public String update(OnlineCourseDTO a) {
		int result=OlDAO.update(a);
		if(result==0) return "Thay đổi không thành công";
		if(result==1) return "Thay đổi thành công";
		return "Mã không được tìm thấy";
	}
	
	public List<OnlineCourseDTO> findByCondition(String condition,String con2){
		return OlDAO.findByCondition(condition,con2);
	}
}
