package com.backend.clinicaDental.repository;

import com.backend.clinicaDental.entity.Odontologo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface OdontologoRepository extends JpaRepository<Odontologo, Long>{

}
