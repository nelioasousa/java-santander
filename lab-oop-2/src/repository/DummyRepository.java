package repository;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Optional;

public class DummyRepository<T extends DatabaseTableEntry> implements IRepository<T>
{
    private List<T> elements;

    public DummyRepository()
    {
        this.elements = new ArrayList<T>();
    }

    public DummyRepository(List<T> initialElements)
    {
        this.elements = initialElements;
    }

    public Optional<T> get(Integer id)
    {
        return this.elements.stream()
                            .filter(c -> c.getId().equals(id))
                            .findAny();
    }

    public void save(T element)
    {
        Integer queryId = element.getId();
        if (this.elements.stream().anyMatch(c -> c.getId().equals(queryId))) {
            return;
        }
        this.elements.add(element);
    }

    public void update(T element)
    {
        Integer queryId = element.getId();
        ListIterator<T> iterator = this.elements.listIterator();
        while (iterator.hasNext()) {
            T e = iterator.next();
            if (e.getId().equals(queryId)) {
                iterator.set(element);
                return;
            }
        }
    }

    public void put(T element)
    {
        Integer queryId = element.getId();
        ListIterator<T> iterator = this.elements.listIterator();
        while (iterator.hasNext()) {
            T c = iterator.next();
            if (c.getId().equals(queryId)) {
                iterator.set(element);
                return;
            }
        }
        iterator.add(element);
    }

    public void remove(T element)
    {
        Integer queryId = element.getId();
        ListIterator<T> iterator = this.elements.listIterator();
        while (iterator.hasNext()) {
            T c = iterator.next();
            if (c.getId().equals(queryId)) {
                iterator.remove();
                return;
            }
        }
    }

    public List<T> getAll()
    {
        return this.elements;
    }
}
