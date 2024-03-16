package com.backend.clinicaDental.service;

import com.backend.clinicaDental.dto.entrada.OdontologoEntradaDto;
import com.backend.clinicaDental.dto.salida.OdontologoSalidaDto;
import com.backend.clinicaDental.entity.Odontologo;


import java.util.List;

public interface IOdontologoService {

    OdontologoSalidaDto registrarOdontologo(OdontologoEntradaDto odontologo);

    List<OdontologoSalidaDto> listarOdontologos();

    OdontologoSalidaDto buscarOdontologoPorId(Long id);

}
