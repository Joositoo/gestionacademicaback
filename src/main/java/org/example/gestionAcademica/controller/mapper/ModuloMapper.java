package org.example.gestionAcademica.controller.mapper;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import org.example.gestionAcademica.controller.dto.AlumnoDto;
import org.example.gestionAcademica.controller.dto.ModuloDto;
import org.example.gestionAcademica.modelo.Modulo;
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
public class ModuloMapper {
    private CicloMapper cicloMapper;
    private ProfesorMapper profesorMapper;

    @Autowired
    private Validator validator;

    public ModuloMapper(CicloMapper cicloMapper, ProfesorMapper profesorMapper) {
        this.cicloMapper = cicloMapper;
        this.profesorMapper = profesorMapper;
    }
    public ModuloDto getDto(Modulo modulo) {
        return new ModuloDto(
                modulo.getId(),
                cicloMapper.getDto(modulo.getIdCiclo()),
                profesorMapper.getDto(modulo.getIdProfesor()),
                modulo.getNombre()
        );
    }

    public List<ModuloDto> getModulosByFile(MultipartFile file) {
        List<ModuloDto> listaModulos = new ArrayList<>();

        try(BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            String linea;
            boolean cabecera = true;
            int numLinea = 1;
            while ((linea = br.readLine()) != null) {
                if (cabecera){
                    cabecera = false;
                    continue;
                }

                String[] datos = linea.split(",");

                if (datos.length == 3){
                    ModuloDto moduloDto = new ModuloDto();
                    moduloDto.setNombreCiclo(datos[0].trim());
                    moduloDto.setEmailProfesor(datos[1].trim());
                    moduloDto.setNombre(datos[2].trim());

                    Set<ConstraintViolation<ModuloDto>> errores = validator.validate(moduloDto);

                    if (!errores.isEmpty()) {
                        StringBuilder sb = new StringBuilder();
                        sb.append("Error en línea " +numLinea+ ": ").append(numLinea).append(": ");

                        for (ConstraintViolation<ModuloDto> error : errores) {
                            sb.append(error.getPropertyPath())
                                    .append(" - ")
                                    .append(error.getMessage())
                                    .append(". ");
                        }

                        throw new ConstraintViolationException(sb.toString(), errores);
                    }

                    listaModulos.add(moduloDto);
                }
                else{
                    throw new RuntimeException("El archivo debe tener 3 columnas (nombreCiclo, emailProfesor, nombre)");
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return listaModulos;
    }
}
