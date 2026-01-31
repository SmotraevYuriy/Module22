package hard.controller;



import hard.model.Country;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import hard.service.CountryService;

import javax.validation.constraints.NotBlank;
import java.util.Map;

@RestController
@RequestMapping("/country")
public class CountryController {

    @Autowired
    private CountryService countryService;

    @GetMapping("/all")
    public ResponseEntity<Map<String, Country>> getAllCountries() {
        return ResponseEntity.ok(countryService.getAllCountries());
    }

    @GetMapping
    public ResponseEntity<Country> getCountryByName(
            @RequestParam("name") @NotBlank(message = "Имя страны не может быть пустым или null") String name) {
        if (name.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        Country country = countryService.getCountryByName(name);
        if (country == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(country);
    }

    @GetMapping("/{countryName}/population")
    public ResponseEntity<Integer> getPopulationByCountryName(
            @PathVariable("countryName") @NotBlank(message = "Имя страны не может быть пустым") String countryName) {

        Integer population = countryService.getPopulationByCountryName(countryName);
        if (population == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(population);
    }

    @PostMapping
    public ResponseEntity<Country> saveCountry(@RequestBody @Validated Country country) {
        countryService.saveCountry(country);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(country);
    }

    @PutMapping
    public ResponseEntity<Map<String, Country>> updateCountry(@RequestBody @Validated Country country) {
        boolean updated = countryService.updateCountry(country);
        if (!updated) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(countryService.getAllCountries());
    }

    @DeleteMapping
    public ResponseEntity<Map<String, Country>> deleteCountryByName(
            @RequestParam("name") @NotBlank(message = "Имя страны не может быть пустым") String name) {
        if (name.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        boolean deleted = countryService.deleteCountry(name);
        if (!deleted) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(countryService.getAllCountries());
    }
}
