package Simulation;

import Robot.Robot;
import User.InputUserCommand;

import java.util.HashMap;
import java.util.Map;

public class Start {
    public Start() {
    }

    public static void main(String args[]) {
        Robots robots = new Robots();
        InputUserCommand initiate = new InputUserCommand(robots.getRobots());
        System.out.println("Ready to initiated. Input commands.");
        while(true) {
            robots.setRobots(initiate.inputCommand());
        }

    }

}
