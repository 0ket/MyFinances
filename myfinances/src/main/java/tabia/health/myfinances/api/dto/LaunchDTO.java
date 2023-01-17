package tabia.health.myfinances.api.dto;

import java.math.BigDecimal;

public class LaunchDTO {
    private Long id;
    private String description;
    private Integer month;
    private Integer year;
    private BigDecimal value;
    private Long userId;
    private String launchType;
    private String launchStatus;

    public LaunchDTO(Long id, String description, Integer month, Integer year, BigDecimal value, Long userId,
            String launchType, String launchStatus) {
        this.id = id;
        this.description = description;
        this.month = month;
        this.year = year;
        this.value = value;
        this.userId = userId;
        this.launchType = launchType;
        this.launchStatus = launchStatus;
    }
    public LaunchDTO() {
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
    public BigDecimal getValue() {
        return value;
    }
    public void setValue(BigDecimal value) {
        this.value = value;
    }
    public Long getUserId() {
        return userId;
    }
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    public String getLaunchType() {
        return launchType;
    }
    public void setLaunchType(String launchType) {
        this.launchType = launchType;
    }
    public String getLaunchStatus() {
        return launchStatus;
    }
    public void setLaunchStatus(String launchStatus) {
        this.launchStatus = launchStatus;
    }
}
