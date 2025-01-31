package br.com.diegomarques.textrix.domains.mappers;

import org.mapstruct.MappingTarget;

import java.util.List;

public interface GenericMapper<E, D, N> {

    D toDto(E entity);

    E toEntity(D dto);

    N toDtoEntity(E entity);

    E toEntityDtoNovo(N dto);

    List<D> toDtoList(List<E> entityList);

    List<E> toEntityList(List<D> dtoList);

    void updateEntityFromDto(D dto, @MappingTarget E entity);
}
