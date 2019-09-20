package com.merrill.userrest

import org.springframework.boot.test.context.SpringBootTest
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.test.context.web.WebAppConfiguration
import org.springframework.web.client.RestTemplate
import spock.lang.Specification

@WebAppConfiguration
@SpringBootTest
class UserServiceIntegrationTest extends Specification {

    def "RegisteredUsers"() {
        given:
        RestTemplate restTemplate = new RestTemplate()
        UserService userService = new UserService(restTemplate: restTemplate, registeredUsersUrl: 'https://5c3ce12c29429300143fe570.mockapi.io/api/registeredusers')

        when:
        List<MerrillUser> result = userService.registeredUsers()

        then:
        result.size() > 0 && result.get(0).getId() !=null && result.get(0).getEmailAddress() != null

    }

    def "UnregisteredUsers"() {

        given:
        RestTemplate restTemplate = new RestTemplate()
        UserService userService = new UserService(restTemplate: restTemplate, unregisteredUsersUrl: 'https://5c3ce12c29429300143fe570.mockapi.io/api/unregisteredusers')

        when:
        List<MerrillUser> result = userService.unregisteredUsers()

        then:
        result.size() > 0 && result.get(0).getId() !=null && result.get(0).getEmailAddress() != null && result.get(0).getRegistrationId() != null && result.get(0).getRegistrationIdGeneratedTime() != null
    }

    def "ProjectMembership"() {
        given:
        RestTemplate restTemplate = new RestTemplate()
        UserService userService = new UserService(restTemplate: restTemplate, projectMembershipUrl: 'https://5c3ce12c29429300143fe570.mockapi.io/api/projectmemberships')

        when:
        List<ProjectMembership> result = userService.projectMembership()

        then:
        result.size() > 0 && result.get(0).id !=null
    }
}
