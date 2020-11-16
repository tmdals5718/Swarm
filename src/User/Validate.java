package User;

import Robot.RobotParameter;

public class Validate {
    private int category;
    private String[] array;
    private boolean[] flag = new boolean[4];

    public Validate(String[] array, int category) {
        this.category = category;
        this.array = array;
    }

    public String[] getArray() {
        return array;
    }

    boolean checkUserCommands() {
        int length = array.length;
        int count = 0;
        switch (category) {
            case 0:
                if (length > 1) {
                    errorReport(1);
                    return false;
                } else return true;
            case 1:
                return checkObject(length);
            case 2:
                return checkCoordinate(length, 1);
            case 3:
                if (!checkObject(length - 1) || !checkCoordinate(length - 1, 2))
                    return false;
                else return true;
            case 4:
                if (!checkObject(length - 1) || !checkParameter(array[0]))
                    return false;
                else return true;
            case 5:
                if (!checkObject(length - 2) || !checkCoordinate(length - 2, 2) || !checkParameter(array[0]))
                    return false;
                else return true;
            default:
                errorReport(4);
                return false;
        }
    }

    private boolean checkObject(int length) {
        String string = "";
        String save = "";
        boolean error = true;
        if (length != 2) {
            errorReport(1);
            return false;
        } else if (array[1].equals("all") || array[1].equals("ALL")) {
            array[1] = "-1";
            return true;
        } else if (array[1].contains("(")) {
            string = array[1];

            while (string.length() > 0) {
                String conditionedCommand = string.substring(string.indexOf("(") + 1, string.indexOf(")"));
                string = string.substring(string.indexOf(")") + 1);
                String[] split = conditionedCommand.split(",");
                for (int i = 0; i < split.length; i++) {
                    if (!conditionList(split[i])) return false;
                    save = save + split[i] + ",";
                }
                save = save.substring(0, save.length() - 1) + "&";
                flag[0] = false;
                flag[1] = false;
                flag[2] = false;
                flag[3] = false;
            }
            save = save.substring(0, save.length() - 1);
            array[1] = save;
        } else {
            String[] temp = array[1].split(",");
            for (int i = 0; i < temp.length; i++) {
                try {
                    if (temp[i].contains("-")) {
                        Integer.parseInt(temp[i].substring(0, temp[i].indexOf("-")));
                        Integer.parseInt(temp[i].substring(temp[i].indexOf("-") + 1));
                    } else {
                        Integer.parseInt(temp[i]);
                    }
                } catch (Exception e) {
                    errorReport(2);
                    return false;
                }
            }
        }
        return true;

    }

    private boolean checkCoordinate(int length, int idx) {
        if (length != 2) {
            errorReport(1);
            return false;
        } else if (array[idx].contains("(")) {
            try {
                String string = array[idx].replace("(", "").replace(")", "").trim();
                String[] temp = string.split(",");
                if (temp.length != 3) {
                    errorReport(2);
                    return false;
                }
                boolean tempswitch = false;
                for (int i = 0; i < temp.length; i++) {
                    if (temp[i].equals("-")) continue;
                    Integer.parseInt(temp[i]);
                    tempswitch = true;
                }
                if (tempswitch == false) {
                    errorReport(1);
                    return false;
                }
            } catch (Exception e) {
                errorReport(3);
                return false;
            }
        } else {
            errorReport(1);
            return false;
        }
        return true;
    }


    private boolean conditionList(String input) {
        RobotParameter param;
        String[] split = null;
        if (input.contains("=")) {
            split = input.split("=");
        } else if (input.contains(">")) {
            split = input.split(">");
        } else if (input.contains("<")) {
            split = input.split("<");
        } else if (input.contains("!")) {
            split = input.split("!");
        } else {
            return false;
        }
        if (input.contains("=") || input.contains("!")) {
            switch (split[0]) {
                case "x":
                case "y":
                case "z":
                case "h":
                    try {
                        int arrange = Integer.parseInt(split[1]);
                        if (arrange > -1000 && arrange < 1000)
                            return true;
                    } catch (Exception e) {
                        errorReport(1);
                    }
                case "state":
                    if (flag[0] == true)
                        return false;
                    flag[0] = true;
                    param = RobotParameter.state;
                    return param.check(split[1]);
                case "camera":
                    if (flag[1] == true)
                        return false;
                    flag[1] = true;
                    param = RobotParameter.camera;
                    return param.check(split[1]);
                case "light":
                    if (flag[2] == true)
                        return false;
                    flag[2] = true;
                    param = RobotParameter.light;
                    return param.check(split[1]);
                case "inventory":
                    if (flag[3] == true)
                        return false;
                    flag[3] = true;
                    param = RobotParameter.inventory;
                    return param.check(split[1]);
                default:
                    return false;
            }
        }

        if (split[0].equals("x") || split[0].equals("y") || split[0].equals("z") || split[0].equals("h")) {
            try {
                int arrange = Integer.parseInt(split[1]);
                if (arrange > -1000 && arrange < 1000)
                    return true;
            } catch (Exception e) {
                errorReport(1);
            }
        }
        return false;
    }

    private boolean checkParameter(String input) {
        String temp=array[array.length-1].replace("(","").replace(")","");
        switch (input) {
            case "CONTROL":
            case "CAPTURE":
                String[] string =temp.split(",");
                try{
                    for(int i=0;i<2;i++)
                        Integer.parseInt(string[i]);
                    array[array.length-1]=temp;
                    return true;
                }catch(Exception e){
                    errorReport(2);
                }
                return false;
            case "RECORD":
            case "PATROL":
            case "SEARCH":
                try {
                    Integer.parseInt(temp);
                    array[array.length-1]=temp;
                    return true;
                } catch (Exception e) {
                    errorReport(2);
                }
                return false;
            default:
                return false;
        }
    }

    private void errorReport(int number) {
        switch (number) {
            case 0:
                System.out.println("▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒\nUnregistered Command.\n▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒\n");
                return;
            case 1:
                System.out.println("▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒\nInappropriate Parameters.\n▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒\n");
                return;
            case 2:
                System.out.println("▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒\nInvalid Robot ID.\n▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒\n");
                return;
            case 3:
                System.out.println("▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒\nInvalid Coordinate.\n▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒\n");
                return;
            case 4:
                System.out.println("▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒\nInvalid Command.\n▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒\n");
                return;
        }
    }

}
