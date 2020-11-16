package User;

import Robot.OutputMachineCommand;
import Robot.Robot;
import Simulation.Robots;

import java.util.Map;
import java.util.Scanner;

public class InputUserCommand {
    OutputMachineCommand robot;
    Map<String,Robot> data;
    public InputUserCommand(Map<String,Robot> robots) {
        this.data=robots;
    }

    private String userCmd;

    public Map<String,Robot> inputCommand() {
        Scanner scan = new Scanner(System.in);
        TranslateCommand translate = new TranslateCommand();
        userCmd = scan.nextLine();
        try {
            while (userCmd.length() > 0 && userCmd!=null) {
                userCmd = translate.receiveCommand(userCmd);
                robot = new OutputMachineCommand(translate.getCommandLine(),data);
                data=robot.action();
            }
        }
        catch (Exception e) {
        }
        return data;
    }
}

