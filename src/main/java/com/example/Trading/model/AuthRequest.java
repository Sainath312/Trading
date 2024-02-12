package com.example.Trading.model;

import com.example.Trading.constants.StringConstants;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class AuthRequest {
	@NotEmpty(message = StringConstants.UserName)
	public String username;
	public String password;
}