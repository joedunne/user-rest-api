package com.merrill.userrest

import org.springframework.boot.test.context.SpringBootTest
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.test.context.web.WebAppConfiguration
import org.springframework.web.client.RestTemplate
import spock.lang.Specification
import spock.lang.Unroll

@WebAppConfiguration
@SpringBootTest
class UserControllerTest extends Specification {

    @Unroll
    def "AllUsers"() {
        given:
        RestTemplate mockRestTemplate = Mock()
        UserController controller = new UserController(restTemplate: mockRestTemplate, registeredUsersUrl: 'registered', unregisteredUsersUrl: 'unregistered', projectMembershipUrl: 'projectMembership')
        mockRestTemplate.exchange('registered', HttpMethod.GET, null, new ParameterizedTypeReference<List<MerrillUser>>() {}) >> new ResponseEntity<List<MerrillUser>>(registered, HttpStatus.OK)
        mockRestTemplate.exchange('unregistered', HttpMethod.GET, null, new ParameterizedTypeReference<List<MerrillUser>>() {}) >> new ResponseEntity<List<MerrillUser>>(unregistered, HttpStatus.OK)
        mockRestTemplate.exchange('projectMembership', HttpMethod.GET, null, new ParameterizedTypeReference<List<ProjectMembership>>() {}) >> new ResponseEntity<List<ProjectMembership>>(projectMembership, HttpStatus.OK)

        when:
        List<MerrillUser> result = controller.allUsers()

        then:
        result == expected

        where:
        expected                                                                                   || registered                                   || unregistered                                 || projectMembership
        [new MerrillUser(id: '1', projectIds: ['1']), new MerrillUser(id: '2', projectIds: [])]    || [new MerrillUser(id: '1')]                   || [new MerrillUser(id: '2')]                   || [new ProjectMembership(projectId: '1', userId: '1')]
        [new MerrillUser(id: '1', projectIds: ['1']), new MerrillUser(id: '2', projectIds: ['2'])] || [new MerrillUser(id: '1')]                   || [new MerrillUser(id: '2')]                   || [new ProjectMembership(projectId: '1', userId: '1'), new ProjectMembership(projectId: '2', userId: '2')]
        [new MerrillUser(id: '1', firstName: null), new MerrillUser(id: '2', firstName: 'Joe')]    || [new MerrillUser(id: '1')]                   || [new MerrillUser(id: '2', firstName: 'Joe')] || [new ProjectMembership(projectId: '1', userId: '7')]
        [new MerrillUser(id: '1', firstName: 'Joe'), new MerrillUser(id: '2', firstName: null)]    || [new MerrillUser(id: '1', firstName: 'Joe')] || [new MerrillUser(id: '2', firstName: null)]  || [new ProjectMembership(projectId: '1', userId: '7')]

    }
}
