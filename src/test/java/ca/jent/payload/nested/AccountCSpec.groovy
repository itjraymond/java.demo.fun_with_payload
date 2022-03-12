package ca.jent.payload.nested

import spock.lang.Specification

class AccountCSpec extends Specification {

    def "Test AccountC accessibility and immutability"() {
        when:
        def a1 = new AccountC();
        def a2 = AccountC.getInstance(AccountC.Money.getInstance(20.0))


        then:
        a1.getAmount() != null
        a2.getAmount() != null
    }
}
