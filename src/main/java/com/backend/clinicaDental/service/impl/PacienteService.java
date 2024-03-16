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

    // *** METODO 1 --- REGISTRAR PACIENTE --- ***
    @Override
    public PacienteSalidaDto registrarPaciente(PacienteEntradaDto paciente) {

        // *** Punto 1 *** : LOGUEAR LA ENTRADA
            LOGGER.info("PacienteEntradaDto: " + JsonPrinter.toString(paciente));
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


    // *** METODO 2 --- LISTAR PACIENTES --- ***

    @Override
    public List<PacienteSalidaDto> listarPacientes() {

        //*** Punto 1 *** : OBTENER TODOS LOS PACIENTES
        List<PacienteSalidaDto> pacientesSalidaDto = pacienteRepository.findAll()
                // *** Punto 2 *** : TRANSFORMAR LAS ENTIDADES A DTOs DE SALIDA
                .stream()
                .map(paciente -> modelMapper.map(paciente, PacienteSalidaDto.class))
                .toList();
        // *** Punto 3 *** : LOGUEAR LA SALIDA
        LOGGER.info("Listado de todos los pacientes: {}", JsonPrinter.toString(pacientesSalidaDto));

        return pacientesSalidaDto;
    }

    // *** METODO 3 --- BUSCAR PACIENTE POR ID --- ***

    @Override
    public PacienteSalidaDto buscarPacientePorId(Long id) {

        //*** Punto 1 *** : BUSCAR PACIENTE POR ID
        Paciente pacienteBuscado = pacienteRepository.findById(id).orElse(null);
        //*** Punto 2 *** : INICIALIZAR DTO DE SALIDA
        PacienteSalidaDto pacienteEncontrado = null;

        //*** Punto 3 *** : VERIFICAR SI EL PACIENTE FUE ENCONTRADO
        if(pacienteBuscado != null){
            pacienteEncontrado = modelMapper.map(pacienteBuscado, PacienteSalidaDto.class);
            LOGGER.info("Paciente encontrado: {}", JsonPrinter.toString(pacienteEncontrado));

        } else LOGGER.error("El ID no se encuentra registrado en la base de datos");

        //*** Punto 4 *** : DEVOLVER EL DTO DE SALIDA
        return pacienteEncontrado;
    }




    // *** METODO 4 --- CONFIGURE MAPPING --- ***

    // 1. CLAVE DTO ENTRADA -> SERVICE -> ENTIDAD : ENTIDAD -> SERVICE -> DTO SALIDA
    // 2. MAPEA ATRIBUTOS CON DISTINTO NOMBRE EN ESTE CASO: domicilio | domicilioEntradaDto en la Entidad Paciente y DTO PacienteEntradaDto.

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
