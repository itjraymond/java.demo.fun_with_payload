package ca.jent.payload.dtos

import ca.jent.payload.Country
import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.ObjectMapper
import spock.lang.Specification

import java.time.LocalDate
import java.time.Month
import java.util.stream.Collectors

class PayloadSpec extends Specification {

    def mapper = new ObjectMapper()

    void setup() {
        mapper.findAndRegisterModules().configure(JsonGenerator.Feature.IGNORE_UNKNOWN, true)
    }

    def "Test countries"() {
        when:
        Address a = new Address.CanadianAddress(new CanadianAddressRec(Country.CA, Province.AB, "589", "Hodgson Rd", "T6R3N5"))
        Address a2 = new Address.CanadianAddress(new CanadianAddressRec(Country.COUNTRIES.getOrDefault("Canada", null), Province.PROVINCES.getOrDefault("Alberta", null), "589", "Hodgson Rd", "T6R3N5"))
        Address a3 = new Address.CanadianAddress(new CanadianAddressRec(Country.COUNTRIES.getOrDefault("Unknown", null), Province.PROVINCES.getOrDefault("Unknown", null), "589", "Hodgson Rd", "T6R3N5"))
        Address b = new Address.UsaAddress(new UsaAddressRec(Country.US, State.AL, "1", "street", "12345"))
        Address b2 = new Address.UsaAddress(new UsaAddressRec(Country.COUNTRIES.getOrDefault("United States",null), State.STATES.getOrDefault("Alabama", null), "1", "street", "12345"))
        Address b3 = new Address.UsaAddress(new UsaAddressRec(Country.COUNTRIES.getOrDefault("Unknown",null), State.STATES.getOrDefault("Unknown", null), "1", "street", "12345"))
        Address c = new Address.InternationalAddress(new InternationalAddressRec(Country.AG, "Somewhere", "2", "Avenue", "98989"))

        def payloadCdn = new JsonPayload(
                new PersonalInfo("Jocelyn", "Raymond", LocalDate.of(1967, Month.FEBRUARY, 16)),
                MaritalStatus.MARRIED,
                new PersonalInfo("Julie", "Ali", LocalDate.of(1958, Month.MAY, 23)),
                a,
                a2
        )
        def payloadWithA2 = new JsonPayload(
                new PersonalInfo("Jocelyn", "Raymond", LocalDate.of(1967, Month.FEBRUARY, 16)),
                MaritalStatus.MARRIED,
                new PersonalInfo("Julie", "Ali", LocalDate.of(1958, Month.MAY, 23)),
                a2,
                a3
        )
        def payloadWithA3 = new JsonPayload(
                new PersonalInfo("Jocelyn", "Raymond", LocalDate.of(1967, Month.FEBRUARY, 16)),
                MaritalStatus.MARRIED,
                new PersonalInfo("Julie", "Ali", LocalDate.of(1958, Month.MAY, 23)),
                a3,
                a
        )
        def payloadUs = new JsonPayload(
                new PersonalInfo("Jocelyn", "Raymond", LocalDate.of(1967, Month.FEBRUARY, 16)),
                MaritalStatus.MARRIED,
                new PersonalInfo("Julie", "Ali", LocalDate.of(1958, Month.MAY, 23)),
                b,
                b2
        )
        def payloadWithB2 = new JsonPayload(
                new PersonalInfo("Jocelyn", "Raymond", LocalDate.of(1967, Month.FEBRUARY, 16)),
                MaritalStatus.MARRIED,
                new PersonalInfo("Julie", "Ali", LocalDate.of(1958, Month.MAY, 23)),
                b2,
                b3
        )
        def payloadWithB3 = new JsonPayload(
                new PersonalInfo("Jocelyn", "Raymond", LocalDate.of(1967, Month.FEBRUARY, 16)),
                MaritalStatus.MARRIED,
                new PersonalInfo("Julie", "Ali", LocalDate.of(1958, Month.MAY, 23)),
                b3,
                b
        )
        def payloadInter = new JsonPayload(
                new PersonalInfo("Jocelyn", "Raymond", LocalDate.of(1967, Month.FEBRUARY, 16)),
                MaritalStatus.MARRIED,
                new PersonalInfo("Julie", "Ali", LocalDate.of(1958, Month.MAY, 23)),
                c,
                c
        )

        def s1 = mapper.writeValueAsString(payloadCdn)
        def s2 = mapper.writeValueAsString(payloadUs)
        def s3 = mapper.writeValueAsString(payloadInter)
        def s4 = mapper.writeValueAsString(payloadWithA2)
        def s5 = mapper.writeValueAsString(payloadWithA3)
        def s6 = mapper.writeValueAsString(payloadWithB2)
        def s7 = mapper.writeValueAsString(payloadWithB3)

        then:
        s1.contains("Jocelyn")
        s1.contains("\"residence\":{\"address\":{\"country\":\"CA\",\"province\":\"AB\"")
        s2.contains("\"residence\":{\"address\":{\"country\":\"US\",\"state\":\"AL\"")
        s3.contains("\"residence\":{\"address\":{\"country\":\"AG\",\"region\":\"Somewhere")
        s4.contains("\"residence\":{\"address\":{\"country\":\"CA\",\"province\":\"AB\"")
        s5.contains("\"residence\":{\"address\":{\"country\":null,\"province\":null")
        s6.contains("\"residence\":{\"address\":{\"country\":\"US\",\"state\":\"AL\"")
        s7.contains("\"residence\":{\"address\":{\"country\":null,\"state\":null")

    }
}
// {"personalInfo":{"firstname":"Jocelyn","lastname":"Raymond","dob":[1967,2,16]},"maritalSatus":"MARRIED","spouseInfo":{"firstname":"Julie","lastname":"Ali","dob":[1958,5,23]},"address":{"address":{"country":"CA","province":"ALBERTA","streetNumber":"589","streetName":"Hodgson Rd","postalCode":"T6R3N5"}}}

/**
 * Here are sample of the serialization:
 *
 */

/*
{
  "personalInfo": {
    "firstname": "Jocelyn",
    "lastname": "Raymond",
    "dob": [
      1967,
      2,
      16
    ]
  },
  "maritalSatus": "MARRIED",
  "spouseInfo": {
    "firstname": "Julie",
    "lastname": "Ali",
    "dob": [
      1958,
      5,
      23
    ]
  },
  "residence": {
    "address": {
      "country": "CA",
      "province": "AB",
      "streetNumber": "589",
      "streetName": "Hodgson Rd",
      "postalCode": "T6R3N5"
    }
  },
  "mailing": {
    "address": {
      "country": "CA",
      "province": "AB",
      "streetNumber": "589",
      "streetName": "Hodgson Rd",
      "postalCode": "T6R3N5"
    }
  }
}{
  "personalInfo": {
    "firstname": "Jocelyn",
    "lastname": "Raymond",
    "dob": [
      1967,
      2,
      16
    ]
  },
  "maritalSatus": "MARRIED",
  "spouseInfo": {
    "firstname": "Julie",
    "lastname": "Ali",
    "dob": [
      1958,
      5,
      23
    ]
  },
  "residence": {
    "address": {
      "country": "US",
      "state": "AL",
      "streetNumber": "1",
      "streetName": "street",
      "zipCode": "12345"
    }
  },
  "mailing": {
    "address": {
      "country": "US",
      "state": "AL",
      "streetNumber": "1",
      "streetName": "street",
      "zipCode": "12345"
    }
  }
}{
  "personalInfo": {
    "firstname": "Jocelyn",
    "lastname": "Raymond",
    "dob": [
      1967,
      2,
      16
    ]
  },
  "maritalSatus": "MARRIED",
  "spouseInfo": {
    "firstname": "Julie",
    "lastname": "Ali",
    "dob": [
      1958,
      5,
      23
    ]
  },
  "residence": {
    "address": {
      "country": "AG",
      "region": "Somewhere",
      "streetNumber": "2",
      "streetName": "Avenue",
      "zipCode": "98989"
    }
  },
  "mailing": {
    "address": {
      "country": "AG",
      "region": "Somewhere",
      "streetNumber": "2",
      "streetName": "Avenue",
      "zipCode": "98989"
    }
  }
}{
  "personalInfo": {
    "firstname": "Jocelyn",
    "lastname": "Raymond",
    "dob": [
      1967,
      2,
      16
    ]
  },
  "maritalSatus": "MARRIED",
  "spouseInfo": {
    "firstname": "Julie",
    "lastname": "Ali",
    "dob": [
      1958,
      5,
      23
    ]
  },
  "residence": {
    "address": {
      "country": "CA",
      "province": "AB",
      "streetNumber": "589",
      "streetName": "Hodgson Rd",
      "postalCode": "T6R3N5"
    }
  },
  "mailing": {
    "address": {
      "country": null,
      "province": null,
      "streetNumber": "589",
      "streetName": "Hodgson Rd",
      "postalCode": "T6R3N5"
    }
  }
}{
  "personalInfo": {
    "firstname": "Jocelyn",
    "lastname": "Raymond",
    "dob": [
      1967,
      2,
      16
    ]
  },
  "maritalSatus": "MARRIED",
  "spouseInfo": {
    "firstname": "Julie",
    "lastname": "Ali",
    "dob": [
      1958,
      5,
      23
    ]
  },
  "residence": {
    "address": {
      "country": null,
      "province": null,
      "streetNumber": "589",
      "streetName": "Hodgson Rd",
      "postalCode": "T6R3N5"
    }
  },
  "mailing": {
    "address": {
      "country": "CA",
      "province": "AB",
      "streetNumber": "589",
      "streetName": "Hodgson Rd",
      "postalCode": "T6R3N5"
    }
  }
}{
  "personalInfo": {
    "firstname": "Jocelyn",
    "lastname": "Raymond",
    "dob": [
      1967,
      2,
      16
    ]
  },
  "maritalSatus": "MARRIED",
  "spouseInfo": {
    "firstname": "Julie",
    "lastname": "Ali",
    "dob": [
      1958,
      5,
      23
    ]
  },
  "residence": {
    "address": {
      "country": "US",
      "state": "AL",
      "streetNumber": "1",
      "streetName": "street",
      "zipCode": "12345"
    }
  },
  "mailing": {
    "address": {
      "country": null,
      "state": null,
      "streetNumber": "1",
      "streetName": "street",
      "zipCode": "12345"
    }
  }
}{
  "personalInfo": {
    "firstname": "Jocelyn",
    "lastname": "Raymond",
    "dob": [
      1967,
      2,
      16
    ]
  },
  "maritalSatus": "MARRIED",
  "spouseInfo": {
    "firstname": "Julie",
    "lastname": "Ali",
    "dob": [
      1958,
      5,
      23
    ]
  },
  "residence": {
    "address": {
      "country": null,
      "state": null,
      "streetNumber": "1",
      "streetName": "street",
      "zipCode": "12345"
    }
  },
  "mailing": {
    "address": {
      "country": "US",
      "state": "AL",
      "streetNumber": "1",
      "streetName": "street",
      "zipCode": "12345"
    }
  }
}
*/