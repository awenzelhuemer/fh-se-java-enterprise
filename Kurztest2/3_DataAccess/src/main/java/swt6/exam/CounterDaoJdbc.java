package swt6.exam;

import org.springframework.jdbc.core.JdbcTemplate;

public class CounterDaoJdbcImpl implements CounterDao {

    private JdbcTemplate jdbcTemplate;

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void increment(String name, int delta) {
        jdbcTemplate.update("update counter (name, delta) values (?, ?)", name, delta);
    }
}
