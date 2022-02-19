package fr.scriptis.simplechess.managers;

import fr.scriptis.simplechess.entities.Entity;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class EntityManager {

    private final LinkedHashMap<Integer, Entity> entities = new LinkedHashMap<>();

    public void add(Entity entity) {
        entities.put(entity.getId(), entity);
    }

    public Entity get(int id) {
        return entities.get(id);
    }

    public Set<Map.Entry<Integer, Entity>> getAll() {
        return entities.entrySet();
    }
    public Collection<Entity> getAllEntities() {
        return entities.values();
    }

    @SuppressWarnings("unchecked")
    public  <T extends Entity> Optional<T> find(Class<T> clazz) {
        return (Optional<T>) entities.values().stream()
                .filter(entity -> entity.getClass().equals(clazz))
                .findFirst();
    }

    public void remove(int id) {
        entities.remove(id);
    }

}
