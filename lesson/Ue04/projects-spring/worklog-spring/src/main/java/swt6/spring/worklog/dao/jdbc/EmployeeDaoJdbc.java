package swt6.spring.worklog.dao.jdbc;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import swt6.spring.worklog.dao.EmployeeDao;
import swt6.spring.worklog.domain.Employee;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;
import java.util.Optional;

public class EmployeeDaoJdbc implements EmployeeDao {

    private JdbcTemplate jdbcTemplate;
    private DataSource dataSource;

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<Employee> findById(Long id) {
        final String sql = "select ID, FIRSTNAME, LASTNAME, DATEOFBIRTH from EMPLOYEE where ID=?";

        var employeeList = jdbcTemplate.query(sql, new EmployeeRowMapper(), id);

        if (employeeList.isEmpty()) {
            return Optional.empty();
        } else if (employeeList.size() == 1) {
            return Optional.of(employeeList.get(0));
        } else {
            throw new IncorrectResultSizeDataAccessException(1, employeeList.size());
        }
    }

    public Optional<Employee> findById1(Long id) {
        final String sql = "select ID, FIRSTNAME, LASTNAME, DATEOFBIRTH from EMPLOYEE where ID=?";

        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(sql, new EmployeeRowMapper(), id));
        } catch (EmptyResultDataAccessException ex) {
            return Optional.empty();
        }
    }

    @Override
    public List<Employee> findAll() {
        final String sql = "select ID, FIRSTNAME, LASTNAME, DATEOFBIRTH from EMPLOYEE";
        return jdbcTemplate.query(sql, new EmployeeRowMapper());
    }

    @Override
    public void insert(Employee e) {
        final String sql =
                "insert into EMPLOYEE (FIRSTNAME, LASTNAME, DATEOFBIRTH) "
                        + "values (?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(sql, new String[]{"ID"});
            ps.setString(1, e.getFirstName());
            ps.setString(2, e.getLastName());
            ps.setDate(3, Date.valueOf(e.getDateOfBirth()));
            ps.executeUpdate();
            return ps;
        }, keyHolder);

        e.setId(keyHolder.getKey().longValue());
    }

    public void insert3(Employee e) {
        final String sql =
                "insert into EMPLOYEE (FIRSTNAME, LASTNAME, DATEOFBIRTH) "
                        + "values (?, ?, ?)";

        jdbcTemplate.update(sql, e.getFirstName(), e.getLastName(), Date.valueOf(e.getDateOfBirth()));
    }

    public void insert2(Employee e) {
        final String sql =
                "insert into EMPLOYEE (FIRSTNAME, LASTNAME, DATEOFBIRTH) "
                        + "values (?, ?, ?)";

        jdbcTemplate.update(sql, ps -> {
            ps.setString(1, e.getFirstName());
            ps.setString(2, e.getLastName());
            ps.setDate(3, Date.valueOf(e.getDateOfBirth()));
            ps.executeUpdate();
        });
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    // Version 1: Data access code without Spring
    public void insert1(final Employee e) throws DataAccessException {
        final String sql =
                "insert into EMPLOYEE (FIRSTNAME, LASTNAME, DATEOFBIRTH) "
                        + "values (?, ?, ?)";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, e.getFirstName());
            stmt.setString(2, e.getLastName());
            stmt.setDate(3, Date.valueOf(e.getDateOfBirth()));
            stmt.executeUpdate();
        } catch (SQLException ex) {
            System.err.println(ex);
        }
    }

    private void update(Employee employee) throws DataAccessException {
        final String sql = "update EMPLOYEE set FIRSTNAME=?, LASTNAME=?, DATEOFBIRTH=? " +
                "where ID=?";
        jdbcTemplate.update(sql, employee.getFirstName(), employee.getLastName(), Date.valueOf(employee.getDateOfBirth()), employee.getId());
    }

    @Override
    public Employee merge(Employee entity) {
        if (entity.getId() == null) {
            insert(entity);
        } else {
            update(entity);
        }
        return entity;
    }

    protected static class EmployeeRowMapper implements RowMapper<Employee> {
        @Override
        public Employee mapRow(ResultSet rs, int rowNum) throws SQLException {
            Employee employee = new Employee();
            employee.setId(rs.getLong(1));
            employee.setFirstName(rs.getString(2));
            employee.setLastName(rs.getString(3));
            employee.setDateOfBirth(rs.getDate(4).toLocalDate());

            return employee;
        }
    }
}
