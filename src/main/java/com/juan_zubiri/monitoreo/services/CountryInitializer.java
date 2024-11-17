package com.juan_zubiri.monitoreo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class CountryInitializer implements CommandLineRunner {

    @Autowired
    private ICountryService countryService;

    @Override
    public void run(String... args) {
        countryService.loadCountriesFromApi();
    }
}

