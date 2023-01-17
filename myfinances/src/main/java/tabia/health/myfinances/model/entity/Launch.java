package tabia.health.myfinances.model.entity;
import jakarta.persistence.*;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;
import tabia.health.myfinances.model.enums.LaunchStatus;
import tabia.health.myfinances.model.enums.LaunchType;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "launch", schema = "finances")
public class Launch {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;

    private Integer month;

    private Integer year;

    @ManyToOne
    @JoinColumn(name = "id_user")
    private User user;

    private BigDecimal value;

    //data de cadastro
    @Convert(converter = Jsr310JpaConverters.LocalDateConverter.class)
    private LocalDate registrationDate;

    @Enumerated(value = EnumType.STRING)
    private LaunchType launchType;

    @Enumerated(value = EnumType.STRING)
    private LaunchStatus launchStatus;

    public Launch() {
    }

    public Launch(String description, Integer month,
                  Integer year, User user, BigDecimal value,
                  LocalDate registrationDate, LaunchType launchType,
                  LaunchStatus launchStatus) {
        this.description = description;
        this.month = month;
        this.year = year;
        this.user = user;
        this.value = value;
        this.registrationDate = registrationDate;
        this.launchType = launchType;
        this.launchStatus = launchStatus;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public LocalDate getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDate registrationDate) {
        this.registrationDate = registrationDate;
    }

    public LaunchType getLaunchType() {
        return launchType;
    }

    public void setLaunchType(LaunchType launchType) {
        this.launchType = launchType;
    }

    public LaunchStatus getLaunchStatus() {
        return launchStatus;
    }

    public void setLaunchStatus(LaunchStatus launchStatus) {
        this.launchStatus = launchStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Launch launch = (Launch) o;
        return Objects.equals(id, launch.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Launch{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", month=" + month +
                ", year=" + year +
                ", user=" + user +
                ", value=" + value +
                ", registrationDate=" + registrationDate +
                ", launchType=" + launchType +
                ", launchStatus=" + launchStatus +
                '}';
    }
}
