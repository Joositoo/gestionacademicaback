package org.example.gestionAcademica.controller.mapper;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import org.example.gestionAcademica.controller.dto.AlumnoDto;
import org.example.gestionAcademica.controller.dto.MatriculaDto;
import org.example.gestionAcademica.modelo.Matricula;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class MatriculaMapper {
    private CicloMapper cicloMapper;
    private AlumnoMapper alumnoMapper;

    @Autowired
    Validator validator;

    public MatriculaMapper(CicloMapper cicloMapper, AlumnoMapper alumnoMapper) {
        this.cicloMapper = cicloMapper;
        this.alumnoMapper = alumnoMapper;
    }

    public MatriculaDto getDto(Matricula matricula) {
        return new MatriculaDto(
                matricula.getId(),
                cicloMapper.getDto(matricula.getIdCiclo()),
                alumnoMapper.getDto(matricula.getIdAlumno())
        );
    }

    public List<MatriculaDto> getMatriculasByFile(MultipartFile file) {
        List<MatriculaDto> listaMatriculas = new ArrayList<>();

        try(BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            String linea;
            int numLinea = 1;
            boolean cabecera = false;
            while ((linea = br.readLine()) != null) {
                if (!cabecera) {
                    cabecera = true;
                    continue;
                }

                String[] datos = linea.split(",");

                if (datos.length == 2) {
                    MatriculaDto matriculaDto = new MatriculaDto();
                    matriculaDto.setEmailAlumno(datos[0].trim());
                    matriculaDto.setNombreCiclo(datos[1].trim());

                    Set<ConstraintViolation<MatriculaDto>> errores = validator.validate(matriculaDto);

                    if (!errores.isEmpty()) {
                        StringBuilder sb = new StringBuilder();
                        sb.append("Error en línea " +numLinea+ ": ").append(numLinea).append(": ");

                        for (ConstraintViolation<MatriculaDto> error : errores) {
                            sb.append(error.getPropertyPath())
                                    .append(" - ")
                                    .append(error.getMessage())
                                    .append(". ");
                        }

                        throw new ConstraintViolationException(sb.toString(), errores);
                    }

                    listaMatriculas.add(matriculaDto);
                }
                else{
                    throw new RuntimeException("El archivo debe tener 2 columnas (emailAlumno , nombreCiclo)");
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return listaMatriculas;
    }
}
