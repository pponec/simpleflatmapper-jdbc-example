package net.ponec.jdbc.dao;

import net.ponec.jdbc.entity.Country;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.SQLException;

class CommonDaoTest extends AbstractDaoTest {

    /** Test the creation and retrieval of a Country entity. */
    //@Test
    void exportData() throws SQLException, IOException {
        loadDemoData();
        exportData("employee");
    }

    private void exportData(String table) throws SQLException, IOException {
        var csvFile = "export_%s.csv".formatted(table);
        try (var stmt = dbConnection.createStatement()) {
            stmt.execute("CALL CSVWRITE('%s', 'SELECT * FROM %s', 'charset=UTF-8 fieldSeparator=| fieldDelimiter=')"
                    .formatted(csvFile, table));
        }
        printFile(csvFile);
    }

    private void printFile(String fileName) throws IOException {
        final var path = Path.of(fileName);
        if (Files.exists(path)) {
            var content = Files.readString(path, StandardCharsets.UTF_8);

            System.out.println("--- CONTENT OF " + fileName + " ---");
            System.out.println(content);
            System.out.println("-----------------------------------");

            Files.delete(path); // Clean up the file after printing if it's just temporary
        }
    }
}