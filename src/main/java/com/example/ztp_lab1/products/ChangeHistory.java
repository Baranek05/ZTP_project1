package com.example.ztp_lab1.products;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class ChangeHistory {
    private Date changeDate;
    private String changeType; // Może być np. "Update", "Delete"
    private String fieldName; // Nazwa zmienionego pola
    private String oldValue; // Wartość przed zmianą
    private String newValue; // Wartość po zmianie
    private String description; // Opis zmiany, który można wygenerować na podstawie powyższych informacji
}
