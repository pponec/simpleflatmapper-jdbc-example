package net.ponec.jdbc.dao;

import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.jetbrains.annotations.NotNull;
import org.simpleflatmapper.jdbc.Crud;
import org.simpleflatmapper.jdbc.JdbcMapperFactory;
import org.ujorm.tools.sql.SqlParamBuilder;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;
import java.util.function.BiConsumer;

/** T represents the Entity type, K represents the Primary Key type */
public class GenericCrudManager<T, K> {

    @NotNull
    private final Crud<T, K> crudOperation;
    @NotNull
    private final Connection connection;
    @NotNull
    private final BiConsumer<T, K> idSetter;

    /** Suppress unchecked cast warning because reflection type resolution is dynamic */
    @SuppressWarnings("unchecked")
    public GenericCrudManager(
            @NotNull Connection connection,
            @NotNull Class<T> entityClass,
            @NotNull BiConsumer<T, K> idSetter) throws SQLException {
        this(connection, entityClass, (Class<K>) getPrimaryKeyType(entityClass, Long.class), idSetter );
    }

    /** Suppress unchecked cast warning because reflection type resolution is dynamic */
    @SuppressWarnings("unchecked")
    public GenericCrudManager(
            @NotNull Connection connection,
            @NotNull Class<T> entityClass,
            @NotNull Class<K> primaryKeyClass,
            @NotNull BiConsumer<T, K> idSetter) throws SQLException {

        this.connection = connection;
        this.idSetter = idSetter;
        this.crudOperation = JdbcMapperFactory.newInstance()
                .crud(entityClass, primaryKeyClass)
                .table(connection, getTableName(entityClass));
    }

    /** Insert a new entity into the database and assign the generated ID */
    public void create(@NotNull T entity) throws SQLException {
        crudOperation.create(connection, entity, key -> idSetter.accept(entity, key));
    }

    /** Batch insert entities and assign their generated IDs */
    public void createAll(@NotNull Collection<T> entities) throws SQLException {
        var iterator = entities.iterator();

        crudOperation.create(connection, entities, key -> {
            if (iterator.hasNext()) {
                var currentEntity = iterator.next();
                idSetter.accept(currentEntity, key);
            }
        });
    }

    /** Inserts all provided entities individually and updates their generated keys. */
    public void createAllIndividually(@NotNull Collection<T> entities) throws SQLException {
        for (var entity : entities) {
            create(entity);
        }
    }

    /** Read an entity by its primary key */
    public T read(K id) throws SQLException {
        return crudOperation.read(connection, id);
    }

    /** Update an existing entity */
    public void update(T entity) throws SQLException {
        crudOperation.update(connection, entity);
    }

    /** Delete an entity by its primary key */
    public void delete(K id) throws SQLException {
        crudOperation.delete(connection, id);
    }

    /** Insert the entity if it does not exist, or update it if it does */
    public void createOrUpdate(T entity) throws SQLException {
        crudOperation.createOrUpdate(connection, entity, key -> idSetter.accept(entity, key));
    }

    /** Method to extract the table name from the jakarta.persistence.Table annotation */
    private static String getTableName(Class<?> targetClass) {
        var annotation = targetClass.getAnnotation(Table.class);
        return (annotation != null && !annotation.name().isEmpty())
                ? annotation.name()
                : targetClass.getSimpleName().toLowerCase();
    }

    public static <C, K> Class<K> getPrimaryKeyType(Class<C> targetClass, Class<K> defaultKey) {
        for (var field : targetClass.getDeclaredFields()) {
            if (field.isAnnotationPresent(Id.class)) {
              return (Class<K>) field.getType();
            }
        }
        return defaultKey;
    }

    protected SqlParamBuilder sqlBuilder() {
        return new SqlParamBuilder(connection);
    }
}