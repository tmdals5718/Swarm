package Robot;

import Simulation.Robots;

import java.util.Map;

public class OutputMachineCommand {
    Map<String, Robot> robots;
    private String command;
    private String[] target;

    public OutputMachineCommand(String command, Map<String, Robot> robots) {
        this.command = command;
        this.robots = robots;
    }

    public Map<String, Robot> action() {


        command = command.replace(".", "");
        String[] grammar = command.split("_");
        String string = "";

        System.out.println(command);
        if (grammar[0].equals("0")) {
            for (int i = 1; i <= 10; i++) {
                Robot entity = robots.get(Integer.toString(i));
                entity.setState("Activated");
                robots.put(Integer.toString(i), entity);
            }
            return robots;
        } else if (grammar[1].equals("-1")) {
            grammar[1] = "1,2,3,4,5,6,7,8,9,10";
        } else if (grammar[1].contains("=") || grammar[1].contains("<") || grammar[1].contains(">") || grammar[1].contains("!")) {
            String[] temp = grammar[1].split("&");
            for (int i = 0; i < temp.length; i++) {
                for (int j = 1; j <= 10; j++) {
                    Robot robot = robots.get(Integer.toString(j));
                    if (conditionCheck(robot, temp[i]))
                        string = string + j + ",";
                }
            }
            grammar[1] = string;
        } else if (grammar[1].contains("-") && !grammar[1].equals("-1")) {
            String[] temp = grammar[1].split(",");
            for (int i = 0; i < temp.length; i++) {
                if (temp[i].contains("-")) {
                    int a = Integer.parseInt(temp[i].substring(0, temp[i].indexOf("-")));
                    int b = Integer.parseInt(temp[i].substring(temp[i].indexOf("-") + 1));
                    for (int j = a; j <= b; j++) {
                        string = string + j + ",";
                    }
                } else {
                    string = string + temp[i] + ",";
                }
            }
            grammar[1] = string;
        }
        target = grammar[1].split(",");
        for (int i = 1; i <= target.length; i++) {
            Robot entity = robots.get(target[i - 1]);
            switch (grammar[0]) {
                case "0":
                    entity.setState("Activated");
                    robots.put(Integer.toString(i), entity);
                    continue;
                case "1":
                    entity.setD(Integer.parseInt(grammar[3]));
                    robots.put(Integer.toString(i), entity);
                    continue;
                case "2":
                    int[] base = entity.getBase();
                    entity.setX(entity.base[0]);
                    entity.setY(entity.base[1]);
                    entity.setZ(entity.base[2]);
                    robots.put(Integer.toString(i), entity);
                    continue;
                case "3":
                case "4":
                case "5":
                case "6":
                case "7":
                    String[] location = grammar[2].split(",");
                    if (location[0] != "-")
                        entity.setX(Integer.parseInt(location[0]));
                    if (location[1] != "-")
                        entity.setY(Integer.parseInt(location[1]));
                    if (location[2] != "-")
                        entity.setZ(Integer.parseInt(location[2]));
                    robots.put(target[i-1], entity);
                    continue;
                case "8":
                    System.out.println("\n\nRobot No." + target[i - 1] + "\n");
                    System.out.println("   X coordinate :" + entity.getX());
                    System.out.println("   y coordinate :" + entity.getY());
                    System.out.println("   z coordinate :" + entity.getZ());
                    System.out.println("   State :" + entity.getState());
                    System.out.println("   Camera :" + entity.getCamera());
                    System.out.println("   Inventory :" + entity.getFunction());
                    continue;
                case "9":
                    entity.setCamera("On");
                    String[] temp = grammar[grammar.length - 1].split(",");
                    System.out.println("\n\nRobot No." + target[i - 1] + "\n");
                    System.out.println("Horizontal Degree : " + temp[0] + "\nVertical Degree :" + temp[1] + "\n");
                    robots.put(Integer.toString(i), entity);
                    continue;
                case "10":
                case "11":
                case "12":
                case "13":
                case "14":
                case "15":
            }
        }
        return robots;
    }

    private boolean conditionCheck(Robot robot, String string) {
        String[] check = string.split(",");
        for (int i = 0; i < check.length; i++) {
            char a = check[i].charAt(1);
            char b = check[i].charAt(2);
            String value = null;
            switch (check[i].charAt(0)) {
                case 'x':
                    value = Integer.toString(robot.getX());
                    break;
                case 'y':
                    value = Integer.toString(robot.getY());
                    break;
                case 'z':
                    value = Integer.toString(robot.getZ());
                    break;
                case '0':
                    if (Character.getNumericValue(b) != robot.getStateNo()) return false;
                    else return true;
                case '1':
                    if (Character.getNumericValue(b) != robot.getCameraNo())
                        return false;
                    else return true;
                case '2':
                    if (Character.getNumericValue(b) != robot.getLightNo()) return false;
                    else return true;
                case '3':
                    if (Character.getNumericValue(b) != robot.getFunctionNo()) return false;
                    else return true;
            }
            if (a == '<') {
                if (!(Integer.parseInt(value) < b)) {
                    return false;
                }
            } else if (a == '>') {
                if (!(Integer.parseInt(value) > b)) {
                    return false;
                }
            } else if (a == '=') {
                if (!(Integer.parseInt(value) > b)) {
                    return false;
                }
            }
        }
        return true;
    }


}
