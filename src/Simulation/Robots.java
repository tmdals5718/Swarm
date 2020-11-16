package Simulation;

import Robot.Robot;

import java.util.HashMap;
import java.util.Map;

public class Robots {
    private static Map<String, Robot> robots = new HashMap(10);
    public Robots(){
        robots.put("1",new Robot());
        robots.put("2",new Robot());
        robots.put("3",new Robot());
        robots.put("4",new Robot());
        robots.put("5",new Robot());
        robots.put("6",new Robot());
        robots.put("7",new Robot());
        robots.put("8",new Robot());
        robots.put("9",new Robot());
        robots.put("10",new Robot());
    }

    public Robots(Map<String,Robot> input){
        robots=input;
    }
    public void setRobots(Map input){
        this.robots=input;
    }
    public Map getRobots(){
        return robots;
    }
}
