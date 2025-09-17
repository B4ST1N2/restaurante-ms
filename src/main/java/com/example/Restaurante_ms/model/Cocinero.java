package com.example.Restaurante_ms.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "cocineros")
public class Cocinero {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer codigoEmpleado;
    private String nombre;
    private double salario;
    private String especialidad;
}
