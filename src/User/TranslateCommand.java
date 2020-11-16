package User;

import Robot.Robot;
import Robot.RobotParameter;

public class TranslateCommand {
    public TranslateCommand() {
    }
    private String commandLine;
    private String temp;
    private ORDER_LIST trans;
    private boolean correct;
    private RobotParameter robot;
    public String getCommandLine(){
        return commandLine;
    }
    String receiveCommand(String input) throws Exception {
        int idx = input.indexOf(".");//.을 이용하여 명령어를 분리.

        if (idx < 0) {//.을 입력하지 않아 명령어간 구별이 안갈경우 아래와 같은 문장 출력 후 명령어를 다시 입력받는다.
            System.out.println("▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒\nThere is no period(.) at the end.\nType period(.) at the end of the line to complete your command.\n▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒\n");
            return "";
        }

        temp = input.substring(idx + 1);//문장이 여러개 들어올 경우를 대비
        String command = input.substring(0, idx);
        command = command.toLowerCase();
        String[] array = command.split(" ");
        trans = trans.CheckingCommand(array[0]);
        array[0] = trans.name();
        Validate validate = new Validate(array, trans.getValue());//명령어가 문법에 맞는지 확인.
        correct = validate.checkUserCommands();

        if (correct == false) {
            return null;
        }

        commandLine = translateCommand(validate.getArray(), trans.getValue());

        return temp;
    }

    private String translateCommand(String[] input, int code) {//로봇에게 전달하기 위한 machineLang.
        String finalOrder = Integer.toString(trans.ordinal());
        switch (code) {
            case 0:
                finalOrder = finalOrder + "___.";
                return finalOrder;
            case 1:
                finalOrder = finalOrder+"_"+arrangeObject(input[1])+"__;";
                return finalOrder;
            case 2:
                finalOrder = finalOrder+"_"+input[1].replace("(","").replace(")","")+"__.";
                return finalOrder;
            case 3:
                finalOrder = finalOrder+"_"+arrangeObject(input[1])+"_"+input[2].replace("(","").replace(")","")+".";
                return finalOrder;
            case 4:
                finalOrder = finalOrder+"_"+arrangeObject(input[1])+"_"+input[2]+".";
                return finalOrder;
            case 5:
                finalOrder = finalOrder+"_"+arrangeObject(input[1])+"_"+input[2].replace("(","").replace(")","")+"_"+input[3]+".";
        }
        return finalOrder;
    }
    private String arrangeObject(String input){
        return input.replace("state","0").replace("camera","1").replace("light","2").replace("inventory","3")
                .replace("deactivated","0").replace("activated","1").replace("lowbattery","2")
                .replace("lost","3").replace("failure","4").replace("on","1").replace("off","0")
                .replace("open","1").replace("close","0");
    }

    public enum ORDER_LIST {
        POWER_SWITCH(0),
        DISTANCE(4),
        RETURN(1),
        ASSEMBLE(2),
        DISASSEMBLE(2),
        STOP(1),
        PATROL(5),
        MOVE(3),
        STATE(1),
        CAPTURE(4),
        INVENTORY(1),
        SEARCH(5),
        RECORD(4),
        LIGHT(1),
        CONTROL(4),
        INVALID_COMMAND(-1);


        private int value;

        ORDER_LIST(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        static ORDER_LIST CheckingCommand(String string) {
            switch (string) {
                case "power":
                case "po":
                    return POWER_SWITCH;
                case "distance":
                case "dt":
                    return DISTANCE;
                case "control":
                case "cn":
                    return CONTROL;
                case "return":
                case "rt":
                    return RETURN;
                case "assemble":
                case "ab":
                    return ASSEMBLE;
                case "disassemble":
                case "da":
                    return DISASSEMBLE;
                case "stop":
                case "sp":
                    return STOP;
                case "patrol":
                case "pt":
                    return PATROL;
                case "move":
                case "mv":
                    return MOVE;
                case "state":
                case "st":
                    return STATE;
                case "capture":
                case "ct":
                    return CAPTURE;
                case "inventory":
                case "iv":
                    return INVENTORY;
                case "light":
                case "li":
                    return LIGHT;
                case "search":
                case "sr":
                    return SEARCH;
                case "record":
                case "rc":
                    return RECORD;
                default:
                    return INVALID_COMMAND;
            }
        }
    }
}
