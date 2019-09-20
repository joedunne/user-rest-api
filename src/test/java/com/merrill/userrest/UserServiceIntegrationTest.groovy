package com.merrill.userrest

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter
import org.springframework.test.context.web.WebAppConfiguration
import org.springframework.web.client.RestTemplate
import spock.lang.Specification

@WebAppConfiguration
@SpringBootTest
class UserServiceIntegrationTest extends Specification {

    RestTemplate restTemplate

    def "RegisteredUsers"() {
        given:
        UserService userService = new UserService(restTemplate: restTemplate, registeredUsersUrl: 'https://5c3ce12c29429300143fe570.mockapi.io/api/registeredusers')

        when:
        List<MerrillUser> result = userService.registeredUsers()

        then:
        result.size() > 0 && result.get(0).getId() !=null && result.get(0).getEmailAddress() != null

    }

    def "UnregisteredUsers"() {

        given:
        UserService userService = new UserService(restTemplate: restTemplate, unregisteredUsersUrl: 'https://5c3ce12c29429300143fe570.mockapi.io/api/unregisteredusers')

        when:
        List<MerrillUser> result = userService.unregisteredUsers()

        then:
        result.size() > 0 && result.get(0).getId() !=null && result.get(0).getEmailAddress() != null && result.get(0).getRegistrationId() != null && result.get(0).getRegistrationIdGeneratedTime() != null
    }

    def "ProjectMembership"() {
        given:
        UserService userService = new UserService(restTemplate: restTemplate, projectMembershipUrl: 'https://5c3ce12c29429300143fe570.mockapi.io/api/projectmemberships')

        when:
        List<ProjectMembership> result = userService.projectMembership()

        then:
        result.size() > 0 && result.get(0).id !=null
    }

    void setup() {
        //For the integration test we want to force failure on any unknown properties. This will alert us when the schema changes.
        restTemplate = new RestTemplate()
        ObjectMapper objectMapper = new ObjectMapper()
        objectMapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES,true)
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,true)

        MappingJackson2HttpMessageConverter messageConverter = new MappingJackson2HttpMessageConverter()
        messageConverter.setPrettyPrint(false)
        messageConverter.setObjectMapper(objectMapper)
        restTemplate.setMessageConverters([messageConverter])
    }
}
