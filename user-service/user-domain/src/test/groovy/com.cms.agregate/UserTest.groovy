package com.cms.agregate

import spock.lang.Specification

class UserTest extends Specification {

    final char[] VALID_PASSWORD_VALUE = "Password1".toCharArray()
    final char[] VALID_PASSWORD_VALUE_2 = "Password2".toCharArray()
    final Login VALID_LOGIN = Login.of("login1")
    final Password VALID_PASSWORD = Password.of(VALID_PASSWORD_VALUE,VALID_PASSWORD_VALUE)
    final Email VALID_EMAIL = Email.of("test@mail.com")

    def "should create new user"() {
        when:
        User user = User.of(VALID_LOGIN, VALID_PASSWORD, VALID_EMAIL)

        then:
        user.id.userId != null
        user.login.value == "login1"
        user.password.value == "Password1".toCharArray()
        user.email.value == "test@mail.com"
        user.position == Position.USER
        user.creationDate != null
        user.status == UserStatus.INACTIVE_EMAIL_CONFIRM_NEEDED
    }

    def "should change user password"() {
        given:
        User user = User.of(VALID_LOGIN, VALID_PASSWORD, VALID_EMAIL)

        when:
        user.changePassword(VALID_PASSWORD_VALUE_2, VALID_PASSWORD_VALUE_2)

        then:
        user.getPassword().value == VALID_PASSWORD_VALUE_2
    }

    def "should throw exception when password is to short"() {
        given:
        def wrongPassword = "12345".toCharArray()

        when:
        User.of(VALID_LOGIN, Password.of(wrongPassword), VALID_EMAIL)

        then:
        thrown(IllegalArgumentException)
    }

    def "should throw exception when password is to long"() {
        given:
        def wrongPassword = "1234567890123456789012345678901".toCharArray()

        when:
        User.of(VALID_LOGIN, Password.of(wrongPassword), VALID_EMAIL)

        then:
        thrown(IllegalArgumentException)
    }

    def "should throw exception when passwords are not equal"() {

        when:
        User.of(VALID_LOGIN, Password.of(VALID_PASSWORD_VALUE, VALID_PASSWORD_VALUE_2), VALID_EMAIL)

        then:
        thrown(IllegalArgumentException)
    }

    def "should throw exception when password not contains letters"() {
        given:
        def wrongPassword = "1234567".toCharArray()

        when:
        User.of(VALID_LOGIN, Password.of(wrongPassword), VALID_EMAIL)

        then:
        thrown(IllegalArgumentException)
    }

    def "should throw exception when password not contains numbers and lower letters"() {
        given:
        def wrongPassword = "ABCDEFGH".toCharArray()

        when:
        User.of(VALID_LOGIN, Password.of(wrongPassword), VALID_EMAIL)

        then:
        thrown(IllegalArgumentException)
    }

    def "should throw exception when password not contains upper letters"() {
        given:
        def wrongPassword = "abcdef1".toCharArray()

        when:
        User.of(VALID_LOGIN, Password.of(wrongPassword), VALID_EMAIL)

        then:
        thrown(IllegalArgumentException)
    }

    def "should activate user"() {
        given:
        User user = User.of(VALID_LOGIN, VALID_PASSWORD, VALID_EMAIL)

        when:
        user.activate()

        then:
        user.status == UserStatus.ACTIVE
    }

    def "should throw exception on activating deactivated user"() {
        given:
        User user = User.of(VALID_LOGIN, VALID_PASSWORD, VALID_EMAIL)

        when:
        user.deactivate()
        user.activate()

        then:
        thrown(RuntimeException)
    }
}
