package ca.jent.payload.nested;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class AccountC {
    private AccountC(){
        this.amount = Money.getInstance(0.0);
    }
    private AccountC(Money amount) {
        this.amount = amount;
    }
    /**
     * The "function" of Helper is to help keeping track of the account balance of last transaction
     * in other word, it will keep the last account balance value and provide a function to indicate
     * it went up or down (gain or loss).
     */
    @Data
    @AllArgsConstructor
    private final class Helper {
        private final Money previousAmount;

        public Trend getTrend() {
             Integer difference = amount.amount.compareTo(previousAmount.amount);
             return  switch (difference) {
                 case Integer i && i < 0  -> Trend.LOSS;
                 case Integer i && i > 0  -> Trend.GAIN;
                 default -> Trend.UNCHANGED;
             };
        }
    }

    // here static class because we want to be able to call factory method or be somewhat available to
    // the outside of AccountC.  Money == alias type for BigDecimal
    protected static final class Money {
        private final BigDecimal amount;

        private Money(){
            this.amount = new BigDecimal("0");
        }
        private Money(BigDecimal amount) {
            this.amount = amount;
        }

        protected static Money getInstance(Double amount) {
            // defensive copy
            return new Money(new BigDecimal(amount.doubleValue()));
        }
        protected static Money getInstance(BigDecimal amount) {
            // note: the caller has access to the Object amount hence not safe
            //       so we need to "copy" the "value" of amount into new object.
            //       Defensive copy
            //       The BigDecimal being passed in could be a "sabotaged" version
            //       because BigDecimal isn't final, whose value can be changed
            //       after the fact.  So we need to protect against this.
            //       BigDecimal does not have a copy constructor so we use add method
            //       which always return a new BigDecimal with new added value.
            BigDecimal amt = amount.add(BigDecimal.ZERO);
            return new Money(amt);
        }
    }

    protected static final String SOMETHING = "wow!";
    private Money amount;
    private Helper helper;

    public enum Trend {
        GAIN,
        LOSS,
        UNCHANGED
    }

    /**
     * Factory method to create AccountC
     */
    public static AccountC getInstance(Money amount) {
        return new AccountC(amount);
    }
    public static AccountC getInstance() {
        return new AccountC();
    }

}

/**
 * One of the questions that arise is what should be "publicly" exposed to the outside of the AccountC class?
 * Should the caller be able to create its own Money amount?
 * If we decide to expose the AccountC creation using only BigDecimal then we lose the Type alias of Money
 * I believe that the caller should be able to access the immutable Money type and let the AccountC to
 * only "see" or "understand" the type Money and not BigDecimal or Double.
 * Money would essentially "protect" by defense copying the value passed by the caller.
 */
