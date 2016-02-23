package com.bowenzhang.contact;

/**
 * Created by bowenzhang on 15/11/14.
 */
public class ContactInfo {

    private String name;
    private String phone;
    private long rawContactId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public long getRawContactId() {
        return rawContactId;
    }

    public void setRawContactId(long rawContactId) {
        this.rawContactId = rawContactId;
    }

    @Override
    public String toString() {
        return "ContactInfo{" +
                "name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", rawContactId=" + rawContactId +
                '}';
    }
}
