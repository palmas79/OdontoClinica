package com.backend.clinicaDental.service.impl;

import com.backend.clinicaDental.dto.entrada.DomicilioEntradaDto;
import com.backend.clinicaDental.dto.entrada.PacienteEntradaDto;
import com.backend.clinicaDental.dto.salida.PacienteSalidaDto;
import com.backend.clinicaDental.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestPropertySource(locations = "classpath:application-test.properties")
class PacienteServiceTest {

    @Autowired
    PacienteService pacienteService;


    //*** UN TEST NO DEBE DEPENDER DE LA OPERACION DE OTRO TEST ***
    //*** UNA DE LAS COSAS QUE PRUEBO CON ESTE TEST ES QUE FUNCIONE EL CONFIGURE MAPPING. ***
    @Test
    @Order(1) // *** LO IDEAL ES NO UTILIZAR EL ORDEN ***
    void deberiaRegistrarseUnPacienteDeNombreJuan_yRetornarSuId() {

        //ARRANGE | DISPONER
        PacienteEntradaDto pacienteEntradaDto = new PacienteEntradaDto("Diego", "Maradona", 25443332, LocalDate.of(2024,4,25), new DomicilioEntradaDto("Segurola", 5023, "Devoto", "CABA"));

        // ACT | ACTUAR
        PacienteSalidaDto pacienteSalidaDto = pacienteService.registrarPaciente(pacienteEntradaDto);

        // ASSERT | COMPROBACIONES
        assertNotNull(pacienteSalidaDto); // compruebo que pacienteSalidaDto no sea nulo.
        assertNotNull(pacienteSalidaDto.getId()); // compruebo que el ID no sea nulo.
        assertEquals("Diego", pacienteSalidaDto.getNombre()); // compruebo que el nombre fue como Digo y volvio como Diego.

    }

    @Test
    @Order(2)
    void deberiaEliminarseElPacienteConId1YLanzarExcepcionAlIntentarEliminarloNuevamente(){

        try{
            pacienteService.eliminarPaciente(1L);
        } catch (Exception e){
            e.printStackTrace();
        }
        assertThrows(ResourceNotFoundException.class,() -> pacienteService.eliminarPaciente(1L));
    }

    @Test
    @Order(3)
    void deberiaDevolverUnaListaVaciaDePacientes() {
        List<PacienteSalidaDto> pacientes = pacienteService.listarPacientes();
        assertTrue(pacientes.isEmpty());
    }

}