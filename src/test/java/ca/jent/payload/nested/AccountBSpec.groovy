package ca.jent.payload.nested

import spock.lang.Specification

class AccountBSpec extends Specification {

    def "Test account accessibility and immutability"() {
        when:
        def amount = new BigDecimal("20.23")
        def money = new AccountB.Money(amount)  // GroovyRuntimeException: Could not find matching constructor
        def account = new AccountB(UUID.randomUUID(), money)

        then:
        account.accountBalance.amount == 20.23
    }
}
