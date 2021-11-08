package service.interfaceService;

public interface GeneralService<T> {
    void add(T t);
    void update(int id, T t);
    void delete(int id);
    int findIndexById(int id);
}
