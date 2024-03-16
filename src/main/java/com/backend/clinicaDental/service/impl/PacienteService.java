package com.backend.clinicaDental.service.impl;

import com.backend.clinicaDental.dto.entrada.PacienteEntradaDto;
import com.backend.clinicaDental.dto.salida.PacienteSalidaDto;
import com.backend.clinicaDental.entity.Paciente;
import com.backend.clinicaDental.service.IPacienteService;
import com.backend.clinicaDental.utils.JsonPrinter;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PacienteService implements IPacienteService {

    private final Logger LOGGER = LoggerFactory.getLogger(PacienteService.class);
    private
    nteIDao;
    private ModelMapper modelMapper;

    //@Autowired | revisar esta anotacion.
    public PacienteService(IDao<Paciente> pacienteIDao, ModelMapper modelMapper) {
        this.pacienteIDao = pacienteIDao;
        this.modelMapper = modelMapper;
        configureMapping();
    }

    // LOGICA EN EL SERVICIO


    @Override
    public PacienteSalidaDto registrarPaciente(PacienteEntradaDto paciente) {
        LOGGER.info("PacienteEntradaDto:" + JsonPrinter.toString(paciente));
        Paciente pacienteEntidad = modelMapper.map(paciente, Paciente.class);

        return pacienteIDao.registrar(paciente);}

    @Override
    public List<PacienteSalidaDto> listarPacientes() {
        return pacienteIDao.listarTodos();
    }
    @Override
    public PacienteSalidaDto buscarPacientePorId(int id) {
        return pacienteIDao.buscarPorId(id);
    }







    // *** CONFIGURE MAPPING *** CLAVE DTO ENTRADA -> SERVICE -> ENTIDAD : ENTIDAD -> SERVICE -> DTO SALIDA
    private void configureMapping(){
        //configuro los datos de DomicilioEntradaDto y Domicilio de las entidades para que setear los datos a atributos de distinto nombre pero que son los mismos.
        modelMapper.typeMap(PacienteEntradaDto.class, Paciente.class)
                .addMappings(mapper -> mapper.map(PacienteEntradaDto::getDomicilioEntradaDto, Paciente::setDomicilio)); //comentario: funcion lambda (ARROW FUNTION).

        //mapeo inverso
        modelMapper.typeMap(Paciente.class, PacienteSalidaDto.class)
                .addMappings(mapper -> mapper.map(Paciente::getDomicilio, PacienteSalidaDto::setDomicilioSalidaDto));

    }



}
