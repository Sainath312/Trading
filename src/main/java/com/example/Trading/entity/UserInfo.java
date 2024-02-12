package com.example.Trading.entity;

import com.example.Trading.constants.StringConstants;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class UserInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long userId;

    @Column(unique = true)
    @NotBlank(message = StringConstants.UserName)
    private String userName;

    @NotBlank(message = StringConstants.ValidPassword)
    public String password;

    @NotEmpty(message = StringConstants.MailRequired)
    @Email(message = StringConstants.ValidMail)
    public String email;

    @OneToMany(mappedBy = "userOrdersId", cascade = CascadeType.ALL)
    private List<Orders> orders = new ArrayList<>();

    @OneToMany(mappedBy = "userTradeId", cascade = CascadeType.ALL)
    private List<Trade> trade = new ArrayList<>();

    public String roles="ROLE_ADMIN";
    public String mobileNumber;

    @Temporal(TemporalType.TIMESTAMP)
    @JoinColumn(name = "updated_at")
    private Date updatedAt;

    public UserInfo(String userName, String email, String mobileNumber, String password, String status) {
        this.userName = userName;
        this.email = email;
        this.mobileNumber = mobileNumber;
        this.password = password;
        this.status=status;
    }

    @PreUpdate
    public void onUpdate(){updatedAt = new Date();}

    public String status;


    public UserInfo(String userName, String email, String password) {
        this.userName = userName;
        this.email = email;
        this.password = password;
    }
}
