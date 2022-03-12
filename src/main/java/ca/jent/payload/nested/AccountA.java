package ca.jent.payload.nested;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * Practice 4 flavors of nested classes:
 * 1. inner non-static a.k.a. member inner class
 * 2. static nested class
 * 3. local class
 * 4. anonmous class
 */
@Data
@AllArgsConstructor
//@NoArgsConstructor CANT COMPILE because member of Account are final
public class AccountA {

    // if the class Money is inner and private, it means it won't be accessable from the outside
    // including "creating" it.  This code although it compiles, cannot be used because
    // we are asking to create an Account only by constructor (immutable) and must pass a
    // Account.Money constructed object.
    @Data
    private final class Money {
        private Double amount;
    }

    private final UUID id;
    private final Money accountBalance;

}
