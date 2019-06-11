package data;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PlayerInfo {
    private String playerName;
    private Double height;
    private Double weight;
    private Double overallRating;
    private Double potential;
    private Double acceleration;
    private Double sprintSpeed;
    private Double agility;
    private Double shotPower;
    private Double stamina;
    private Double strength;
    private Double aggression;
    private Double age;

    //todo it should throw exception not return 0
    public Double getAttributeValue(String attribute) {
        switch(attribute) {
            case "height":
                return height;
            case "weight":
                return weight;
            case "overall_rating":
                return overallRating;
            case "potential":
                return potential;
            case "acceleration":
                return acceleration;
            case "sprint_speed":
                return sprintSpeed;
            case "agility":
                return sprintSpeed;
            case "shot_power":
                return shotPower;
            case "stamina":
                return stamina;
            case "strength":
                return strength;
            case "aggression":
                return aggression;
            case "age":
                return age;
            default:
                return 0d;
        }
    }
}
