package io.hogenboom.familyfoto.rest;

import java.time.LocalDate;

public record AddPersonRequest(String name, String familyName, String gender, LocalDate birthday) {
}
