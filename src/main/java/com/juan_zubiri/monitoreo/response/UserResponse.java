package com.juan_zubiri.monitoreo.response;

import java.util.List;

import com.juan_zubiri.monitoreo.dto.UserDTO;
import lombok.Data;

@Data
public class UserResponse {
	
	  private List<UserDTO> users;
}
