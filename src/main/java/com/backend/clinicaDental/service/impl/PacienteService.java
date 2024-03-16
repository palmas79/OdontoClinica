package com.backend.clinicaDental.service.impl;

import com.backend.clinicaDental.dto.entrada.PacienteEntradaDto;
import com.backend.clinicaDental.dto.salida.PacienteSalidaDto;
import com.backend.clinicaDental.entity.Paciente;
import com.backend.clinicaDental.repository.PacienteRepository;
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
    private PacienteRepository pacienteRepository;
    private ModelMapper modelMapper;

    //*** @Autowired *** | NO AUTOWIREES DEMAS!!! determina injeccion de dependencia. No hace falta que la anotacion @Autowired se escriba porque ya al ver el constructor SpringBoot sabe que es una injeccion de dependencia.
    public PacienteService(PacienteRepository pacienteRepository, ModelMapper modelMapper) {
        this.pacienteRepository = pacienteRepository;
        this.modelMapper = modelMapper;
        configureMapping(); // en el constructor se llama al configureMapping
    }


    // LOGICA EN EL SERVICIO
    @Override
    public PacienteSalidaDto registrarPaciente(PacienteEntradaDto paciente) {

        // *** Punto 1 *** : LOGUEAR LA ENTRADA
            LOGGER.info("PacienteentradaDto: " + JsonPrinter.toString(paciente));
        // *** Punto 2 *** : CONVERTIR DTO A ENTIDAD
            Paciente pacienteEntidad = modelMapper.map(paciente, Paciente.class);
        // *** Punto 3 *** : PERSISTIR LA ENTIDAD
            Paciente pacienteEntidadconID = pacienteRepository.save(pacienteEntidad);
        // *** Punto 4 *** : CONVERTIR ENTIDAD A DTO DE SALIDA
            PacienteSalidaDto pacienteSalidaDto = modelMapper.map(pacienteEntidadconID, PacienteSalidaDto.class);
        // *** Punto 5 *** : LOGUEAR LA SALIDA
            LOGGER.info("PacienteentradaDto: " + JsonPrinter.toString(pacienteSalidaDto));
        return pacienteSalidaDto;
    }

    @Override
    public List<PacienteSalidaDto> listarPacientes() {
        return pacienteRepository.listarTodos();
    }
    @Override
    public PacienteSalidaDto buscarPacientePorId(Long id) {
        return pacienteRepository.buscarPorId(id);
    }







    // *** CONFIGURE MAPPING *** CLAVE DTO ENTRADA -> SERVICE -> ENTIDAD : ENTIDAD -> SERVICE -> DTO SALIDA
    // lo que hace este metodo es mapear atributos con distinto nombre en este caso domicilio | domicilioEntradaDto en la entidad Paciente y la entidad PacienteEntradaDto.

    private void configureMapping(){
        //configuro los datos de DomicilioEntradaDto y Domicilio de las entidades para que setear los datos a atributos de distinto nombre pero que son los mismos.

        // mapeo desde entrada a entidad
        modelMapper.typeMap(PacienteEntradaDto.class, Paciente.class)
                .addMappings(mapper -> mapper.map(PacienteEntradaDto::getDomicilioEntradaDto, Paciente::setDomicilio)); //comentario: funcion lambda (ARROW FUNTION).

        //mapeo inverso desde entidad a salida
        modelMapper.typeMap(Paciente.class, PacienteSalidaDto.class)
                .addMappings(mapper -> mapper.map(Paciente::getDomicilio, PacienteSalidaDto::setDomicilioSalidaDto));

    }



}
