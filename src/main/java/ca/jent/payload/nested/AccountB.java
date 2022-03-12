package ca.jent.payload.nested;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * The problem is we are using an inner class as "member" which means that the intent of the
 * inner class is to "NEVER" leak out of the outer class and is solely used for encapsulating
 * "something" within the outer class.  Example like a inner "helper" which outside the outer
 * class make no sense but very useful inside.
 *
 * The abstraction of "Money" is met to be exposed to the outside because it act as a an alias
 * type for Double or BigDecimal.  It only help making the code more clear: i.e. Money has a
 * more specific meaning than Double or BigDecimal.
 * In addition, if we would have a method like "calculate(Double amountOfMoney, Double weight)"
 * we need to look at the parameter name and not at the type to find out the "meaning".  In
 * addition, if we call this method with the wrong order of the parameters
 * e.g. Double result = calculate(myWeight, myMoney);
 * The compiler won't complaint and won't generate a run time exception either. But it will
 * be wrong.  A better way is to use alias types to help us.  So it is much better for us to
 * have: "calculate(Money amount, Weight weight);"
 * as you no longer can make the mistake of mixing the parameter.  In addition, you don't need
 * to look at the parameter name for meaning.  The alias type is sufficient.
 */
@Data
@AllArgsConstructor
public class AccountB {

    /**
     *  Since this inner class is not static, it has to be used with an instance of AccountB
     *  You won't be able to "create" an instance of Money outside this class
     */
    @Data
    @AllArgsConstructor  // since member is final, cannot use @NoArgsConstructor
    protected final class Money {
        private final BigDecimal amount;

        // Factory method in Money won't compile using static factory,
        // so try to create factory method in AccountB
        // Note: we could remove the static keyword but then it would mean you need an instance
        //       of Money in the first place to access the factory method (so make no sense).
//        public static Money getInstance(Double amount) {
//            var amt = new BigDecimal(amount);
//            return new Money(amt);
//        }
    }

    private UUID id;
    private Money accountBalance;

    // CANNOT be static factory method.  The reason is the inner class is declared as a "member" of
    //        an instance of AccountB.  So you need an instance of AccountB to be able to create
    //        an instance of Money. But since AccountB is immutable, it can only be created via its
    //        constructor in the first place and it needs an instance of Money.  Hence the code below
    //        won't work.
//    public static Money getInstance(Double amount) {
//        return new Money(new BigDecimal(amount));
//    }

    protected Money getInstance(Double amount) {
        return new Money(new BigDecimal(amount));
    }

}
