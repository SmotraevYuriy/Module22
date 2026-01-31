package hard.service;

import hard.model.Country;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class CountryService {

    private final Map<String, Country> countries = new HashMap<>() {{
        put("Russia", new Country("Russia", 145_000_000));
        put("USA", new Country("USA", 300_000_000));
        put("China", new Country("China", 1_426_000_000));
        put("India", new Country("India", 1_436_000_000));
    }};

    public Map<String, Country> getAllCountries() {
        return new HashMap<>(countries);
    }

    public Country getCountryByName(String name) {
        if (name == null || name.isEmpty()) {
            return null;
        }
        return countries.get(name);
    }

    public Integer getPopulationByCountryName(String countryName) {
        Country country = countries.get(countryName);
        return country != null ? country.getPopulation() : null;
    }

    public void saveCountry(Country country) {
        validateCountry(country);
        countries.put(country.getName(), country);
    }

    public boolean updateCountry(Country country) {
        validateCountry(country);
        if (!countries.containsKey(country.getName())) {
            return false;
        }
        countries.put(country.getName(), country);
        return true;
    }

    public boolean deleteCountry(String name) {
        if (name == null || name.isEmpty()) {
            return false;
        }
        return countries.remove(name) != null;
    }

    private void validateCountry(Country country) {
        if (country == null) {
            throw new IllegalArgumentException("Объект Country не может быть null");
        }

        if (country.getName() == null || country.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Имя страны не может быть null или пустым");
        }

        if (country.getPopulation() <= 0) {
            throw new IllegalArgumentException("Население должно быть положительным числом");
        }

        if (!country.getName().matches("[a-zA-Z\\s]+")) {
            throw new IllegalArgumentException("Имя страны должно содержать только буквы и пробелы");
        }
    }
}
