package com.backend.clinicaDental.service;

import com.backend.clinicaDental.entity.Odontologo;


import java.util.List;

public interface IOdontologoService {

    Odontologo guardarOdontologo();
    List<Odontologo> listarOdontologo();
    Odontologo buscarOdontologoPorId(int id);

}
