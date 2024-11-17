package com.juan_zubiri.monitoreo.response;

import java.util.List;
import com.juan_zubiri.monitoreo.model.RegisterUser;
import lombok.Data;

@Data
public class RegisterUserResponse {

	private List<RegisterUser> registerUser;
}
