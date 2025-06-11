package org.example.gestionAcademica.controller.mapper;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import org.example.gestionAcademica.controller.dto.AlumnoDto;
import org.example.gestionAcademica.controller.dto.CalificacionDto;
import org.example.gestionAcademica.modelo.Calificacion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class CalificacionMapper {
    private ModuloMapper moduloMapper;
    private AlumnoMapper alumnoMapper;

    @Autowired
    private Validator validator;
    @Autowired
    private PorcentajesRaMapper porcentajesRaMapper;

    public CalificacionMapper(ModuloMapper moduloMapper, AlumnoMapper alumnoMapper) {
        this.moduloMapper = moduloMapper;
        this.alumnoMapper = alumnoMapper;
    }

    public CalificacionDto getDto(Calificacion calificacion) {
        return new CalificacionDto(
                calificacion.getId(),
                moduloMapper.getDto(calificacion.getIdModulo()),
                alumnoMapper.getDto(calificacion.getIdAlumno()),
                porcentajesRaMapper.getDto(calificacion.getIdRa()),
                calificacion.getNota()
        );
    }

    public List<CalificacionDto> getCalificacionesByFile(MultipartFile file) {
        List<CalificacionDto> listaCalificaciones = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            String linea;
            int numLinea = 1;
            boolean cabecera = true;

            while ((linea = br.readLine()) != null) {
                if (cabecera) {
                    cabecera = false;
                    continue;
                }

                String[] datos = linea.split(",");
                if (datos.length != 4) {
                    throw new RuntimeException("Línea " + numLinea + ": el archivo debe tener 4 columnas (nombreModulo, emailAlumno, descripcionRa, nota)");
                }

                String nombreModulo = datos[0].trim();
                String emailAlumno = datos[1].trim();
                String descripcionRa = datos[2].trim();
                String notaStr = datos[3].trim();

                if (nombreModulo == null || nombreModulo.isBlank()) {
                    throw new RuntimeException("Línea " + numLinea + ": nombreModulo está vacío o en blanco");
                }
                if (emailAlumno == null || emailAlumno.isBlank()) {
                    throw new RuntimeException("Línea " + numLinea + ": emailAlumno está vacío o en blanco");
                }
                if (descripcionRa == null || descripcionRa.isBlank()) {
                    throw new RuntimeException("Línea " + numLinea + ": descripcionRa está vacío o en blanco");
                }
                if (notaStr == null || notaStr.isBlank()) {
                    throw new RuntimeException("Línea " + numLinea + ": nota está vacío o en blanco");
                }

                BigDecimal nota;
                try {
                    nota = new BigDecimal(notaStr);
                } catch (NumberFormatException e) {
                    throw new RuntimeException("Línea " + numLinea + ": la nota no es un número válido");
                }

                CalificacionDto calificacionDto = new CalificacionDto(nombreModulo, emailAlumno, descripcionRa, nota);

                Set<ConstraintViolation<CalificacionDto>> errores = validator.validate(calificacionDto);

                if (!errores.isEmpty()) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("Error en línea ").append(numLinea).append(": ");
                    for (ConstraintViolation<CalificacionDto> error : errores) {
                        sb.append(error.getPropertyPath())
                                .append(" - ")
                                .append(error.getMessage())
                                .append(". ");
                    }
                    throw new ConstraintViolationException(sb.toString(), errores);
                }

                listaCalificaciones.add(calificacionDto);
                numLinea++;
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return listaCalificaciones;
    }

    private BigDecimal parseBigDecimal(String valor) {
        if (valor != null) {
            valor = valor.trim();
            if (!valor.equalsIgnoreCase("null") && !valor.isEmpty()) {
                return new BigDecimal(valor);
            }
        }
        return null;
    }
}
