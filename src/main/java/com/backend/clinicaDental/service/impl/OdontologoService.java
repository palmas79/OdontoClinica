package com.backend.clinicaDental.service.impl;

import com.backend.clinicaDental.entity.Odontologo;
import com.backend.clinicaDental.repository.OdontologoRepository;
import com.backend.clinicaDental.service.IOdontologoService;

import org.modelmapper.ModelMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OdontologoService implements IOdontologoService {
    private final Logger LOGGER = LoggerFactory.getLogger(OdontologoService.class);
    private OdontologoRepository odontologoRepository;
    private ModelMapper modelMapper;

    public OdontologoService(OdontologoRepository odontologoRepository, ModelMapper modelMapper) {
        this.odontologoRepository = odontologoRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public Odontologo registrarOdontologo(Odontologo odontologo) {
        return odontologoRepository.save(odontologo);
    }

    @Override
    public List<Odontologo> listarOdontologo() {
        return odontologoRepository.findAll();
    }

    @Override
    public Odontologo buscarOdontologoPorId(Long id) {
        return odontologoRepository.findById(id).orElse(null);
    }
}