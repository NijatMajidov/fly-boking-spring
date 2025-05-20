package az.turing.mapper;

public interface EntityMapper<E,D>{
    E toEntity(D dto);
    D toDTO(E entity);
}