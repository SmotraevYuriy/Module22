package hard;


import com.fasterxml.jackson.databind.ObjectMapper;
import hard.model.Country;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import hard.service.CountryService;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = Application.class)
@AutoConfigureMockMvc
class CountryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CountryService countryService;

    private Map<String, Country> testCountries;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        testCountries = new HashMap<>();
        testCountries.put("Russia", new Country("Russia", 145_000_000));
        testCountries.put("USA", new Country("USA", 300_000_000));
        testCountries.put("China", new Country("China", 1_426_000_000));
        testCountries.put("India", new Country("India", 1_436_000_000));

        when(countryService.getAllCountries()).thenReturn(testCountries);

        when(countryService.getCountryByName(ArgumentMatchers.anyString()))
                .thenAnswer(invocation -> {
                    String name = invocation.getArgument(0);
                    return testCountries.get(name);
                });
        when(countryService.getPopulationByCountryName(ArgumentMatchers.anyString()))
                .thenAnswer(invocation -> {
                    String name = invocation.getArgument(0);
                    Country country = testCountries.get(name);
                    return country != null ? country.getPopulation() : null;
                });
    }

    @Test
    void testGetAllCountries_Success() throws Exception {
        mockMvc.perform(get("/country/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.Russia.name").value("Russia"))
                .andExpect(jsonPath("$.USA.population").value(300_000_000));
    }

    @Test
    void testGetCountryByName_Existing_Success() throws Exception {
        mockMvc.perform(get("/country").param("name", "Russia"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Russia"))
                .andExpect(jsonPath("$.population").value(145_000_000));
    }

    @Test
    void testGetCountryByName_NullName_BadRequest() throws Exception {
        mockMvc.perform(get("/country").param("name", (String) null))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testGetCountryByName_EmptyName_BadRequest() throws Exception {
        mockMvc.perform(get("/country").param("name", ""))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testGetCountryByName_NonExisting_NotFound() throws Exception {
        mockMvc.perform(get("/country").param("name", "Atlantis"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testGetPopulationByCountryName_Existing_Success() throws Exception {
        mockMvc.perform(get("/country/Russia/population"))
                .andExpect(status().isOk())
                .andExpect(content().string("145000000"));
    }

    @Test
    void testGetPopulationByCountryName_NonExisting_NotFound() throws Exception {
        mockMvc.perform(get("/country/Atlantis/population"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testGetPopulationByCountryName_BlankPath_BadRequest() throws Exception {
        mockMvc.perform(get("/country//population"))
                .andExpect(status().isNotFound()); // @NotBlank → 404
    }

    @Test
    void testSaveCountry_Valid_Success() throws Exception {
        Country newCountry = new Country("France", 68_000_000);

        mockMvc.perform(post("/country")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newCountry)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("France"))
                .andExpect(jsonPath("$.population").value(68_000_000));
    }

    @Test
    void testSaveCountry_NullName_BadRequest() throws Exception {
        Country invalidCountry = new Country(null, 1_000_000);

        mockMvc.perform(post("/country")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidCountry)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testSaveCountry_EmptyName_BadRequest() throws Exception {
        Country invalidCountry = new Country("", 1_000_000);

        mockMvc.perform(post("/country")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidCountry)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testSaveCountry_NegativePopulation_BadRequest() throws Exception {
        Country invalidCountry = new Country("Test", -100);

        mockMvc.perform(post("/country")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidCountry)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testUpdateCountry_Existing_Success() throws Exception {
        Country updatedCountry = new Country("Russia", 200_000_000);

        when(countryService.updateCountry(any(Country.class))).thenReturn(true);
        testCountries.put("Russia", updatedCountry);
        when(countryService.getAllCountries()).thenReturn(testCountries);

        mockMvc.perform(put("/country")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedCountry)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.Russia.population").value(200_000_000));
    }

    @Test
    void testUpdateCountry_NonExisting_NotFound() throws Exception {
        Country newCountry = new Country("Atlantis", 1_000_000);

        when(countryService.updateCountry(newCountry)).thenReturn(false);

        mockMvc.perform(put("/country")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newCountry)))
                .andExpect(status().isNotFound());
    }

    @Test
    void testUpdateCountry_NullName_BadRequest() throws Exception {
        Country invalidCountry = new Country(null, 1_000_000);

        mockMvc.perform(put("/country")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidCountry)))
                .andExpect(status().isBadRequest());
    }
    @Test
    void testUpdateCountry_NegativePopulation_BadRequest() throws Exception {
        Country invalidCountry = new Country("Russia", -1);

        mockMvc.perform(put("/country")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidCountry)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testDeleteCountryByName_Existing_Success() throws Exception {
        when(countryService.deleteCountry("USA")).thenReturn(true);
        testCountries.remove("USA");
        when(countryService.getAllCountries()).thenReturn(testCountries);

        mockMvc.perform(delete("/country").param("name", "USA"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.USA").doesNotExist());
    }

    @Test
    void testDeleteCountryByName_NonExisting_NotFound() throws Exception {
        when(countryService.deleteCountry("Atlantis")).thenReturn(false);

        mockMvc.perform(delete("/country").param("name", "Atlantis"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testDeleteCountryByName_NullName_BadRequest() throws Exception {
        mockMvc.perform(delete("/country").param("name", (String) null))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testDeleteCountryByName_EmptyName_BadRequest() throws Exception {
        mockMvc.perform(delete("/country").param("name", ""))
                .andExpect(status().isBadRequest());
    }

}

