package com.merrill.userrest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@Slf4j
public class UserController {

    @Value("${user.restapi.registered}")
    private String registeredUsersUrl;

    @Value("${user.restapi.unregistered}")
    private String unregisteredUsersUrl;

    @Value("${user.restapi.projectMembership}")
    private String projectMembershipUrl;

    @Autowired
    private RestTemplate restTemplate;

    /**
     * Get All Users both registered and unregistered in a unified list.
     * Also fills in the project membership information for each user.
     * @return Collection of all users
     */
    @RequestMapping("/api/allusers")
    public Collection<MerrillUser> allUsers() {
        Map<String, MerrillUser> users = Stream.of(userRestApiCall(registeredUsersUrl), userRestApiCall(unregisteredUsersUrl))
                .flatMap(List::stream).collect(Collectors.toMap(MerrillUser::getId, Function.identity()));

        //now add in all the projectIds...
        projectMembershipRestApiCall(projectMembershipUrl).forEach(projectMembership -> {
            if (users.containsKey(projectMembership.getUserId())) {
                users.get(projectMembership.getUserId()).getProjectIds().add(projectMembership.getProjectId());
            } else {
                log.info("Project Member not found : projectMembership.getUserId()={} ", projectMembership.getUserId());
            }
        });
        return new ArrayList<>(users.values());
    }

    private List<MerrillUser> userRestApiCall(String url) {
        ResponseEntity<List<MerrillUser>> responseEntity =
                restTemplate.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<MerrillUser>>() {
                });
        return responseEntity.getBody();
    }

    private List<ProjectMembership> projectMembershipRestApiCall(String url) {
        ResponseEntity<List<ProjectMembership>> responseEntity =
                restTemplate.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<ProjectMembership>>() {
                });
        return responseEntity.getBody();
    }

}
