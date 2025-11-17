package ch.hearc.ig.service;

import ch.hearc.ig.business.Country;

public class CountryMapper extends AbstractMapper<Country> {

    public CountryMapper(String jsonAsStr) {
        super(jsonAsStr);
    }

    public Country create() throws NoDataFoundException {
        try {
            Country country = new Country(root.get("code").asText(),
                    root.get("name").asText()
            );
            return country;
        } catch (NullPointerException e) {
            throw new NoDataFoundException("Il n'existe pas de pays avec ce code / code inexistant");
        }
    }


}
