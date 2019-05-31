package data;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PlayerInfo {
    private Long id;
    private String playerName;
    private Double height;
    private Double weight;
    private Integer overallRating;
    private Integer potential;
    private Integer acceleration;
    private Integer sprintSpeed;
    private Integer agility;
    private Integer shotPower;
    private Integer stamina;
    private Integer strength;
    private Integer aggression;
    private Integer age;
}
