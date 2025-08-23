package com.example.Restaurante_ms.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Cocinero {
    @Id
    private Integer codigoEmpleado;
    private String nombre;
    private double salario;
    private String especialidad;
}
