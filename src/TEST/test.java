package TEST;

import BUS.OnlineCourseBUS;
import GUI.GUI_KETQUA.AddEditFORM;

public class test {
	public static void main(String[] args) {
//		new AddEditFORM();
		OnlineCourseBUS b= new OnlineCourseBUS();
		System.out.println(b.getNewestId());
	}
}
