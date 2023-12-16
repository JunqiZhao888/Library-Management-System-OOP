package service;

import dao.RegistDao;
import model.Admin;
import model.AdminCode;
import model.Register;
import model.User;

public class RegistService {
	
	private RegistDao registDao = new RegistDao();
	

	public String regist(Register register) {
		String msg = "";
		if(register.getName().contains(" ") || register.getName().equals("") || 
				register.getCode().contains(" ") || register.getCode().equals("") || 
				register.getPassword().contains(" ") || register.getPassword().equals("")) {
			msg = "The input info cannot be empty";
		}else if(register.getName().length() < 2 || register.getCode().length() < 5|| register.getPassword().length()<5){
			msg = "name.len>2, password.len>5";
		}else if (isExist(register)) {
			msg = "Username existed already";
		}else if(register.getMold().equals("User")) {
			msg = registDao.userRegist(register);
		}else if(checkAdminCode(register.getAdminCode())){
			msg = registDao.adminRegist(register);
		}else {
			msg = "Admin key wrong or too many times";
		}
		return msg;
	}

	private boolean checkAdminCode(String adminCode) {
		AdminCode adminCodeMold = new AdminCode();
		adminCodeMold = registDao.checkAdminCode(adminCode);
		if (adminCodeMold != null) {
			if (adminCodeMold.getCount() != 0) {
				// decrement admin key
				registDao.updateAdminCode(adminCodeMold);
				return true;
			}else {
				return false;
			}
		}else {
			return false;
		}
	}


	private boolean isExist(Register register) {
		User user = registDao.findUserByCode(register.getCode());
		if (user != null) {
			// check user existence
			return true;
		}else {
			// check admin existence
			Admin admin = registDao.findAdminByCode(register.getCode());
			if (admin != null) {
				return true;
			}
		}
		return false;
	}
}
