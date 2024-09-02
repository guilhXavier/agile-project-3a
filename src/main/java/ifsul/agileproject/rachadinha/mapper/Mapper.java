package ifsul.agileproject.rachadinha.mapper;

public interface Mapper<D, E> {
    E toEntity(D dto);
    D toDTO(E entity);

    @Deprecated
    default
    E apply(D dto) {
        return toEntity(dto);
    }
}