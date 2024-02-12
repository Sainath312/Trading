package com.example.Trading.model;

import com.example.Trading.constants.StringConstants;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UpdateProfile {

    @Column(unique = true)
    @NotEmpty(message = StringConstants.MobileNumberRequired)
    public String mobileNumber;

    public String status;

}
