package swt6.orm.domain;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
public class LogbookEntry implements Serializable {

    @Id
    @GeneratedValue
    private Long id;
    private String activity;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    @Fetch(FetchMode.SELECT)
    @ManyToOne(fetch = FetchType.EAGER)
    private Employee employee;

    public LogbookEntry() {
    }

    public LogbookEntry(String activity, LocalDateTime startTime, LocalDateTime endTime) {
        this.activity = activity;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public void attachEmployee(Employee employee) {
        if(this.employee != null) {
            this.employee.getLogbookEntries().remove(this);
        }
        this.employee = employee;
        this.employee.getLogbookEntries().add(this);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return activity + ": " + startTime.format(formatter) + " - " + endTime.format(formatter);
    }
}
