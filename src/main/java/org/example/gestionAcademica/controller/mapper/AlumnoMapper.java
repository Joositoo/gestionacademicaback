package org.example.gestionAcademica.controller.mapper;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import org.example.gestionAcademica.controller.dto.AlumnoDto;
import org.example.gestionAcademica.modelo.Alumno;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;


@Service
public class AlumnoMapper {
    private final ProfesorMapper profesorMapper;

    @Autowired
    private Validator validator;

    public AlumnoMapper(ProfesorMapper profesorMapper) {
        this.profesorMapper = profesorMapper;
    }

    public AlumnoDto getDto(Alumno alumno) {
        return new AlumnoDto(
                alumno.getId(),
                profesorMapper.getDto(alumno.getIdProfesor()),
                alumno.getNombre(),
                alumno.getApellidos(),
                alumno.getEmail());
    }

    public List<AlumnoDto> getAlumnosByFile(MultipartFile file) {
        List<AlumnoDto> listaAlumnos = new ArrayList<>();

        try(BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream()))){
            String linea;
            int numLinea = 1;
            boolean cabecera = true;
            while ((linea = br.readLine()) != null) {
                if (cabecera) {
                    cabecera = false;
                    continue;
                }

                String[] datos = linea.split(",");

                if (datos.length == 4) {
                    AlumnoDto alumnoDto = new AlumnoDto();
                    alumnoDto.setNombre(datos[0].trim());
                    alumnoDto.setApellidos(datos[1].trim());
                    alumnoDto.setEmail(datos[2].trim());
                    alumnoDto.setEmailProfesor(datos[3].trim());

                    Set<ConstraintViolation<AlumnoDto>> errores = validator.validate(alumnoDto);

                    if (!errores.isEmpty()) {
                        StringBuilder sb = new StringBuilder();
                        sb.append("Error en línea " +numLinea+ ": ").append(numLinea).append(": ");

                        for (ConstraintViolation<AlumnoDto> error : errores) {
                            sb.append(error.getPropertyPath())
                                    .append(" - ")
                                    .append(error.getMessage())
                                    .append(". ");
                        }

                        throw new ConstraintViolationException(sb.toString(), errores);
                    }

                    listaAlumnos.add(alumnoDto);
                }
                else{
                    throw new RuntimeException("El archivo debe tener 4 columnas (nombre, apellidos, email, emailProfesor)");
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return listaAlumnos;
    }
}
