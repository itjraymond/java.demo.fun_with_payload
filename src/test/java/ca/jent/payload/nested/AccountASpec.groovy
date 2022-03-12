package ca.jent.payload.nested

import spock.lang.Specification

class AccountASpec extends Specification {
    void setup() {
    }

    def "Test accessibility and mutability of Account"() {
        when:
         def account = new AccountA(UUID.randomUUID(), new AccountA.Money(20.0));  // GroovyRuntimeException: Could not find matching constructor for AccountA.Money(20.0)

        then:
         account.getAccountBalance().getAmount() == 20.0

    }
}
