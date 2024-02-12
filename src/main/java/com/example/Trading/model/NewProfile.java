package com.example.Trading.model;

import com.example.Trading.constants.StringConstants;
import jakarta.persistence.Column;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class NewProfile {

    @NotEmpty(message =StringConstants.UserName)
    public String userName;

    @NotBlank(message = StringConstants.MailRequired)
    @Email(message = StringConstants.ValidMail)
    public String email;


    @NotBlank(message = StringConstants.MobileNumberRequired)
    @Pattern(regexp = "^[7-9]\\d{9}$", message = StringConstants.ValidMobileNumber)
    public String mobileNumber;

    public String status;

    @Column
    @NotBlank(message = StringConstants.ValidPassword)
    @Size(min = 8, message =StringConstants.PasswordLength)
    @Pattern.List({
            @Pattern(regexp = ".*[A-Z].*", message =StringConstants.UpperCase),
            @Pattern(regexp = ".*[a-z].*", message = StringConstants.LowerCase),
            @Pattern(regexp = ".*\\d.*", message = StringConstants.Numeric),
            @Pattern(regexp = ".*[@#$%^&+=].*", message =StringConstants.SpecialCharacter) })
    public String password;

}

