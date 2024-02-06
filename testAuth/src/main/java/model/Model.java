package model;

import java.util.UUID;

public interface Model {

    public static final String SEPARATIOR_KEY = "@";

    public UUID getMId();

    default public String getKeyConverter() {
        return this.getClass().getName().hashCode() + SEPARATIOR_KEY + this.getMId().toString();
    }
}
