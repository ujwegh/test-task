package ru.nik.testtask.config;

import org.testcontainers.containers.PostgreSQLContainer;

public class CommonDbContainer extends PostgreSQLContainer<CommonDbContainer> {

    private static final String IMAGE_VERSION = "postgres:9.6-alpine";
    private static CommonDbContainer container;

    private CommonDbContainer() {
        super(IMAGE_VERSION);
    }

    public static CommonDbContainer getInstance() {
        if (container == null) {
            container = new CommonDbContainer();
        }
        return container;
    }

    @Override
    public void start() {
        super.start();
        System.setProperty("DB_URL", container.getJdbcUrl());
        System.setProperty("DB_USERNAME", container.getUsername());
        System.setProperty("DB_PASSWORD", container.getPassword());
    }

    @Override
    public void stop() {
        //do nothing, JVM handles shut down
    }
}
