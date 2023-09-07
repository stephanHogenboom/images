package io.hogenboom.familyfoto.entity;

import java.time.LocalDate;
import java.util.UUID;


public record Person(UUID id, String name, LocalDate birthDay) {
}
