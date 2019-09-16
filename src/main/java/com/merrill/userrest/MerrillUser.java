package com.merrill.userrest;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MerrillUser {
    private String id;
    private String city;
    private String company;
    private String country;
    private String firstName;
    private String lastName;
    private String organizationType;
    private String phone;
    private String state;
    private String zipCode;
    private boolean disclaimerAccepted;
    private String languageCode;
    private String emailAddress;
    private List<String> projectIds = new ArrayList<>();
}
