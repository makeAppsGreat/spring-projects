package kr.makeappsgreat.springbootdata;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;

@Component
public class SQLRunner implements ApplicationRunner {

    @Autowired
    DataSource dataSource;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        try (Connection connection = dataSource.getConnection()) {
            System.out.println(">> " + dataSource.getClass());
            System.out.println(">> " + connection.getMetaData().getDriverName());
            System.out.println(">> " + connection.getMetaData().getURL());
            System.out.println(">> " + connection.getMetaData().getUserName());

            /* Statement statement = connection.createStatement();
            String sql = "CREATE TABLE USERS(id INTEGER NOT NULL, name VARCHAR(255), PRIMARY KEY (id))";
            staement.executeUpdate(sql); */
        }

        jdbcTemplate.execute("INSERT INTO USERS VALUES (1, 'Gayoun')");
    }
}
