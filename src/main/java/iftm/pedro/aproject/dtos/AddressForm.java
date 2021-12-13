package iftm.pedro.aproject.dtos;

import javax.validation.constraints.Pattern;

public class AddressForm {

    @Pattern(regexp = "^\\d{5}-\\d{3}$", message = "Illegal CEP Format")
    private String originLocale;

    @Pattern(regexp = "^\\d{5}-\\d{3}$", message = "Illegal CEP Format")
    private String destinyLocale;

    private int originNumber;
    private int destinyNumber;

    public AddressForm() {
    }

    public String getOriginLocale() {
        return originLocale;
    }

    public void setOriginLocale(String originLocale) {
        this.originLocale = originLocale;
    }

    public String getDestinyLocale() {
        return destinyLocale;
    }

    public void setDestinyLocale(String destinyLocale) {
        this.destinyLocale = destinyLocale;
    }

    public int getOriginNumber() {
        return originNumber;
    }

    public void setOriginNumber(int originNumber) {
        this.originNumber = originNumber;
    }

    public int getDestinyNumber() {
        return destinyNumber;
    }

    public void setDestinyNumber(int destinyNumber) {
        this.destinyNumber = destinyNumber;
    }

    @Override
    public String toString() {
        return "AddressForm{" +
                "originLocale='" + originLocale + '\'' +
                ", destinyLocale='" + destinyLocale + '\'' +
                ", originNumber=" + originNumber +
                ", destinyNumber=" + destinyNumber +
                '}';
    }
}
