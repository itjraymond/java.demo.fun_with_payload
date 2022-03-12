package ca.jent.payload.dtos;

import ca.jent.payload.Country;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Locale;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toUnmodifiableMap;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Payload {
    private PersonalInfo personalInfo;
    private MaritalStatus maritalStatus;
    private PersonalInfo spouseInfo;
    private Address address;
}


record PersonalInfo(String firstname, String lastname, LocalDate dob) { }

record CanadianAddressRec(Country country, Province province, String streetNumber, String streetName, String postalCode) { }

record UsaAddressRec(Country country, State state, String streetNumber, String streetName, String zipCode) { }

record InternationalAddressRec(Country country, String region, String streetNumber, String streetName, String zipCode) { }

record JsonPayload (
        PersonalInfo personalInfo,
        MaritalStatus maritalSatus,
        PersonalInfo spouseInfo,
        Address residence,
        Address mailing
){};

sealed class Address {
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    static final class CanadianAddress extends Address {
        CanadianAddressRec address;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    static final class UsaAddress extends Address {
        UsaAddressRec address;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    static final class InternationalAddress extends Address {
        InternationalAddressRec address;
    }
}

/**
 * Because we are returning Map<String, String>, we should avoid this as much as possible.
 *
 *  ****** DON'T USE THIS ******
 *
 * Instead, use the enum Country facility.
 */
class Countries {
    private static Map<String, String> COUNTRIES = Arrays.stream(Locale.getISOCountries()).collect(toUnmodifiableMap(code -> code, code -> new Locale("", code).getDisplayCountry()));
}

/**
 * Note: the provinceName (String) should NEVER be used within the CORE of the application.  The provinceName (String) should
 * only be used to map a downstream incoming payload that does not use a KEY (CODE or ISO-CODE), but instead uses
 * the full province name (String), to our enum Province type.
 */
enum Province {
    AB("Alberta"),
    BC("British Columbia"),
    MA("Manitoba"),
    NB("New Brunswick"),
    NF("Newfoundland and Labrador"),
    NT("Northwest Territories"),
    NS("Nova Scotia"),
    NU("Nunavut"),
    ON("Ontario"),
    PE("Prince Edward Island"),
    QC("Québec"),
    SK("Saskatchewan"),
    YT("Yukon")
    ;
    private final String provinceName;

    Province(String name) {
        this.provinceName = name;
    }

    public String getProvinceName() {
        return provinceName;
    }

    /**
     * Usage: Province.PROVINCES.getOrDefault("British Columbia", null)
     */
    public static final Map<String, Province> PROVINCES = Arrays.stream(Province.values()).collect(toUnmodifiableMap(
            Province::getProvinceName,
            Function.identity()
    ));
}

/**
 * Depricated
 * This Province enum does not follow best practice.  See Province enum above.
 */
//enum Province {
//    ALBERTA("Alberta", "AB"),
//    BRITISH_COLUMBIA("British Columbia", "BC"),
//    MANITOBA("Manitoba", "MA"),
//    NEW_BRUNSWICK("New Brunswick", "NB"),
//    NEWFOUNDLAND("Newfoundland and Labrador", "NF"),
//    NORTHWEST_TERRITORIES("Northwest Territories", "NT"),
//    NOVA_SCOTIA("Nova Scotia", "NS"),
//    NUNAVUT("Nunavut", "NU"),
//    ONTARIO("Ontario", "ON"),
//    PRINCE_EDWARD_ISLAND("Prince Edward Island", "PE"),
//    QUEBEC("Québec", "QC"),
//    SASKATCHEWAN("", "SK"),
//    YUKON("Yukon", "YT");
//
//    Province(String name, String abbreviation){}
//}

enum State {
    OK("Oklahoma"),
    OH("Ohio"),
    SD("South Dakota"),
    WA("Washington"),
    SC("South Carolina"),
    CO("Colorado"),
    VT("Vermont"),
    NY("New York"),
    GA("Georgia"),
    NV("Nevada"),
    CA("California"),
    VI("Virgin Islands"),
    NM("New Mexico"),
    RI("Rhode Island"),
    NJ("New Jersey"),
    VA("Virginia"),
    NH("New Hampshire"),
    FM("Federated States of Micronesia"),
    FL("Florida"),
    NE("Nebraska"),
    ND("North Dakota"),
    NC("North Carolina"),
    UT("Utah"),
    MT("Montana"),
    MS("Mississippi"),
    AZ("Arizona"),
    MP("Northern Mariana Islands"),
    MO("Missouri"),
    MN("Minnesota"),
    IN("Indiana"),
    IL("Illinois"),
    MI("Michigan"),
    MH("Marshall Islands"),
    AS("American Samoa"),
    AR("Arkansas"),
    ME("Maine"),
    MD("Maryland"),
    AL("Alabama"),
    MA("Massachusetts"),
    TX("Texas"),
    ID("Idaho"),
    AK("Alaska"),
    IA("Iowa"),
    PW("Palau"),
    TN("Tennessee"),
    PR("Puerto Rico"),
    PA("Pennsylvania"),
    WY("Wyoming"),
    HI("Hawaii"),
    WV("West Virginia"),
    LA("Louisiana"),
    DE("Delaware"),
    DC("District of Columbia"),
    KY("Kentucky"),
    OR("Oregon"),
    WI("Wisconsin"),
    KS("Kansas"),
    CT("Connecticut"),
    GU("Guam");

    private final String stateName;
    State(String stateName){
        this.stateName = stateName;
    }

    public String getStateName() {
        return stateName;
    }

    /**
     * Use the STATES facility to map incoming payload that uses full state name instead of ISO-CODE.
     */
    public static final Map<String, State> STATES = Arrays.stream(State.values()).collect(Collectors.toUnmodifiableMap(
            State::getStateName,
            Function.identity()
    ));
}
/**
 * Depricated
 * Should not use KEY such as ALABAMA but instead use key that correspond to ISO-CODE.
 * The KEY is what should be exchanged to downstream/upstream services (OUTPUT)
  */
//enum State {
//    ALABAMA("Alabama", "AL"),
//    ALASKA("Alaska", "AK"),
//    AMERICAN_SAMOA("American Samoa", "AS"),
//    ARIZONA("Arizona", "AZ"),
//    ARKANSAS("Arkansas", "AR"),
//    CALIFORNIA("California", "CA"),
//    COLORADO("Colorado", "CO"),
//    CONNECTICUT("Connecticut", "CT"),
//    DELAWARE("Delaware", "DE"),
//    DISTRICT_OF_COLUMBIA("District of Columbia", "DC"),
//    FEDERATED_STATES_OF_MICRONESIA("Federated States of Micronesia", "FM"),
//    FLORIDA("Florida", "FL"),
//    GEORGIA("Georgia", "GA"),
//    GUAM("Guam", "GU"),
//    HAWAII("Hawaii", "HI"),
//    IDAHO("Idaho", "ID"),
//    ILLINOIS("Illinois", "IL"),
//    INDIANA("Indiana", "IN"),
//    IOWA("Iowa", "IA"),
//    KANSAS("Kansas", "KS"),
//    KENTUCKY("Kentucky", "KY"),
//    LOUISIANA("Louisiana", "LA"),
//    MAINE("Maine", "ME"),
//    MARYLAND("Maryland", "MD"),
//    MARSHALL_ISLANDS("Marshall Islands", "MH"),
//    MASSACHUSETTS("Massachusetts", "MA"),
//    MICHIGAN("Michigan", "MI"),
//    MINNESOTA("Minnesota", "MN"),
//    MISSISSIPPI("Mississippi", "MS"),
//    MISSOURI("Missouri", "MO"),
//    MONTANA("Montana", "MT"),
//    NEBRASKA("Nebraska", "NE"),
//    NEVADA("Nevada", "NV"),
//    NEW_HAMPSHIRE("New Hampshire", "NH"),
//    NEW_JERSEY("New Jersey", "NJ"),
//    NEW_MEXICO("New Mexico", "NM"),
//    NEW_YORK("New York", "NY"),
//    NORTH_CAROLINA("North Carolina", "NC"),
//    NORTH_DAKOTA("North Dakota", "ND"),
//    NORTHERN_MARIANA_ISLANDS("Northern Mariana Islands", "MP"),
//    OHIO("Ohio", "OH"),
//    OKLAHOMA("Oklahoma", "OK"),
//    OREGON("Oregon", "OR"),
//    PALAU("Palau", "PW"),
//    PENNSYLVANIA("Pennsylvania", "PA"),
//    PUERTO_RICO("Puerto Rico", "PR"),
//    RHODE_ISLAND("Rhode Island", "RI"),
//    SOUTH_CAROLINA("South Carolina", "SC"),
//    SOUTH_DAKOTA("South Dakota", "SD"),
//    TENNESSEE("Tennessee", "TN"),
//    TEXAS("Texas", "TX"),
//    UTAH("Utah", "UT"),
//    VERMONT("Vermont", "VT"),
//    VIRGIN_ISLANDS("Virgin Islands", "VI"),
//    VIRGINIA("Virginia", "VA"),
//    WASHINGTON("Washington", "WA"),
//    WEST_VIRGINIA("West Virginia", "WV"),
//    WISCONSIN("Wisconsin", "WI"),
//    WYOMING("Wyoming", "WY"),
//    UNKNOWN("Unknown", "");
//
//    private final String name;
//    private final String abbreviation;
//
//    private static final Map<String, State> MAP_STATES_BY_ABBR = Arrays.stream(State.values()).collect(toUnmodifiableMap(state -> state.abbreviation, state -> state));
//    private static final Map<State, String> MAP_STATE_NAME_BY_STATE = Arrays.stream(State.values()).collect(toUnmodifiableMap(state -> state, state -> state.name));
//
//    State(String name, String abbreviation) {
//        this.name = name;
//        this.abbreviation = abbreviation;
//    }
//
//    public String getAbbreviation() {
//        return abbreviation;
//    }
//    public String getName() {
//        return name;
//    }
//
//}

enum MaritalStatus {
    MARRIED,
    COMMON_LAWS,
    SINGLE,
    SEPARATED,
    DIVORCED,
    WIDOWED
}

