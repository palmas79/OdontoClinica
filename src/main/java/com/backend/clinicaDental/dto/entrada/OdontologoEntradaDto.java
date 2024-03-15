package com.backend.clinicaDental.dto.entrada;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


public class OdontologoEntradaDto {

    //NotNull porque le indicamos que tiene que haber un objeto ahi.
    @NotNull (message = "la matricula no puede ser nula")
    @NotBlank(message = "el campo no puede estar vacio")
    @Size (min = 5, max = 20, message = "El campo debe tener un minimo de 5 caracteres y un maximo de 10")
    private String matricula;
    @NotNull (message = "el nombre no puede ser nulo")
    @NotBlank(message = "el campo no puede estar vacio")
    @Size (min = 2, max = 50, message = "El campo debe tener un minimo de 2 caracteres")
    private String nombre;
    @NotNull (message = "el apellido no puede ser nulo")
    @NotBlank(message = "el campo no puede estar vacio")
    @Size (min = 2, max = 50, message = "El campo debe tener un minimo de 2 caracteres")
    private String apellido;

    public OdontologoEntradaDto() {
    }

    public OdontologoEntradaDto(String matricula, String nombre, String apellido) {
        this.matricula = matricula;
        this.nombre = nombre;
        this.apellido = apellido;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }
}
