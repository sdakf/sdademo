package pl.sda.democlass.sdademo.users;

import lombok.Getter;

@Getter
public enum Countries {

    POLAND("PL","Polska"),
    //todo 7 dopisać dwa kraje
    FRANCE("FRA", "Francja");

    private String symbol;
    private String plName;

    Countries(String symbol, String plName) {

        this.symbol = symbol;
        this.plName = plName;
    }
}
