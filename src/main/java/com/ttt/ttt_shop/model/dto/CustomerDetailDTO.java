package com.ttt.ttt_shop.model.dto;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public class CustomerDetailDTO {
    private Long id;
    @NotBlank(message = "Họ và tên không được để trống")
    private String fullName;

    @NotBlank(message = "Địa chỉ không được để trống")
    private String address;

    @Pattern(regexp="[0-9]+", message="Số điện thoại không hợp lệ")
    private String phoneNumber;

    @NotBlank(message = "Ngày sinh không được để trống")
    private String birthday;

    private String avatar;

    public CustomerDetailDTO() {
    }

    public CustomerDetailDTO(Long id, String fullName, String address, String phoneNumber, String birthday, String avatar) {
        this.id = id;
        this.fullName = fullName;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.birthday = birthday;
        this.avatar = avatar;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
