package edu.java.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public abstract class GenericMapper<E, D> {
    private final ModelMapper mapper;

    protected GenericMapper(ModelMapper mapper) {
        this.mapper = mapper;
    }

    protected abstract Class<D> getDtoClass();

    public D toDto(E entity) {
        return Objects.isNull(entity) ? null : mapper.map(entity, getDtoClass());
    }

    public List<D> toDtoList(List<E> entityList) {
        return entityList
            .stream()
            .map(this::toDto)
            .collect(Collectors.toList());
    }
}
