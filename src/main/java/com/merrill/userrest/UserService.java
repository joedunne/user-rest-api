package com.merrill.userrest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class UserService {

    @Value("${user.restapi.registered}")
    private String registeredUsersUrl;

    @Value("${user.restapi.unregistered}")
    private String unregisteredUsersUrl;

    @Value("${user.restapi.projectMembership}")
    private String projectMembershipUrl;

    @Autowired
    private RestTemplate restTemplate;

    public List<MerrillUser> registeredUsers() {
        ResponseEntity<List<MerrillUser>> responseEntity =
                restTemplate.exchange(registeredUsersUrl, HttpMethod.GET, null, new ParameterizedTypeReference<List<MerrillUser>>() {
                });
        return responseEntity.getBody();
    }

    public List<MerrillUser> unregisteredUsers() {
        ResponseEntity<List<MerrillUser>> responseEntity =
                restTemplate.exchange(unregisteredUsersUrl, HttpMethod.GET, null, new ParameterizedTypeReference<List<MerrillUser>>() {
                });
        return responseEntity.getBody();
    }

    public List<ProjectMembership> projectMembership() {
        ResponseEntity<List<ProjectMembership>> responseEntity =
                restTemplate.exchange(projectMembershipUrl, HttpMethod.GET, null, new ParameterizedTypeReference<List<ProjectMembership>>() {
                });
        return responseEntity.getBody();
    }

}
