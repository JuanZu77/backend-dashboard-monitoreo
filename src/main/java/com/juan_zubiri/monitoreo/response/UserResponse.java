package com.juan_zubiri.monitoreo.response;

import java.util.List;
import com.juan_zubiri.monitoreo.model.User;
import lombok.Data;

@Data
public class UserResponse {
	
	private List<User> users;
}
