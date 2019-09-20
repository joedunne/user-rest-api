package com.merrill.userrest

import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.web.WebAppConfiguration
import spock.lang.Specification
import spock.lang.Unroll

@WebAppConfiguration
@SpringBootTest
class UserControllerTest extends Specification {

    @Unroll
    def "AllUsers"() {
        given:
        UserService mockUserService = Mock()
        UserController controller = new UserController(userService: mockUserService)
        mockUserService.registeredUsers() >> registered
        mockUserService.unregisteredUsers() >> unregistered
        mockUserService.projectMembership() >> projectMembership

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

    @Unroll
    def "booleanValueAllowNull"() {
        given:
        UserService mockUserService = Mock()
        UserController controller = new UserController(userService: mockUserService)
        mockUserService.registeredUsers() >> registered
        mockUserService.unregisteredUsers() >> unregistered
        mockUserService.projectMembership() >> projectMembership

        when:
        List<MerrillUser> result = controller.allUsers()

        then:
        result == expected

        where:
        expected                                                                 || registered                                            || unregistered || projectMembership
        [new MerrillUser(id: '1', projectIds: ['1'], disclaimerAccepted: null)]  || [new MerrillUser(id: '1')]                            || []           || [new ProjectMembership(projectId: '1', userId: '1')]
        [new MerrillUser(id: '1', projectIds: ['1'], disclaimerAccepted: false)] || [new MerrillUser(id: '1', disclaimerAccepted: false)] || []           || [new ProjectMembership(projectId: '1', userId: '1')]
        [new MerrillUser(id: '1', projectIds: ['1'], disclaimerAccepted: true)]  || [new MerrillUser(id: '1', disclaimerAccepted: true)]  || []           || [new ProjectMembership(projectId: '1', userId: '1')]

    }


    @Unroll
    def "unregisterdProperties"() {
        given:
        UserService mockUserService = Mock()
        UserController controller = new UserController(userService: mockUserService)
        mockUserService.registeredUsers() >> registered
        mockUserService.unregisteredUsers() >> unregistered
        mockUserService.projectMembership() >> projectMembership

        when:
        List<MerrillUser> result = controller.allUsers()

        then:
        result == expected

        where:
        expected                                                                              || registered || unregistered                                                                          || projectMembership
        [new MerrillUser(id: '1', registrationId: '123', registrationIdGeneratedTime: '456')] || []         || [new MerrillUser(id: '1', registrationId: '123', registrationIdGeneratedTime: '456')] || []

    }
}