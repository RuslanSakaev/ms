package server.server.repo;

public interface Repository<T> {
    void save(T text);
    T load();
}
