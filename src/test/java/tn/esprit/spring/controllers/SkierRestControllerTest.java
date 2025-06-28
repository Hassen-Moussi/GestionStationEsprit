package tn.esprit.spring.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import tn.esprit.spring.entities.Skier;
import tn.esprit.spring.entities.TypeSubscription;
import tn.esprit.spring.services.ISkierServices;

import java.time.LocalDate;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@WebMvcTest(SkierRestController.class)
public class SkierRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ISkierServices skierServices;

    @Autowired
    private ObjectMapper objectMapper; // For converting objects to JSON

    @Test
    void testAddSkier() throws Exception {
        Skier skier = new Skier();
        skier.setNumSkier(1L);
        skier.setFirstName("Ali");
        skier.setLastName("Ben Salah");
        skier.setCity("Tunis");
        skier.setDateOfBirth(LocalDate.of(2000, 1, 1));

        when(skierServices.addSkier(any(Skier.class))).thenReturn(skier);

        mockMvc.perform(post("/skier/add")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(skier)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.numSkier").value(1L))
                .andExpect(jsonPath("$.firstName").value("Ali"));

        verify(skierServices, times(1)).addSkier(any(Skier.class));
    }

    @Test
    void testGetAllSkiers() throws Exception {
        Skier skier = new Skier();
        skier.setNumSkier(1L);
        skier.setFirstName("Ali");

        when(skierServices.retrieveAllSkiers()).thenReturn(List.of(skier));

        mockMvc.perform(get("/skier/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].numSkier").value(1L))
                .andExpect(jsonPath("$[0].firstName").value("Ali"));
    }
}
