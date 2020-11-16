package Robot;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public enum RobotParameter {
    state(0, Arrays.asList("deactivated","activated","lowbattery","lost","failure")),
    camera(1,Arrays.asList("on","off")),
    light(2,Arrays.asList("on","off")),
    inventory(3,Arrays.asList("open","close")),
    EMPTY(-1,Collections.EMPTY_LIST);


    private int param;
    private List<String> list;

    RobotParameter(int param, List<String> list){
        this.param=param;
        this.list=list;
    }
    public int location(String input){
        return list.indexOf(input);
    }
    public int getValue(){
        return param;
    }
    public static RobotParameter parameter(String input){
        return Arrays.stream(RobotParameter.values())
                .filter(title -> title.check(input))
                .findAny()
                .orElse(EMPTY);
    }
    public boolean check(String input){
        return list.stream().anyMatch(list -> list.equals(input));
    }
    /*
    public boolean isState(String input){
        return list.contains(input);
    }
    public boolean isCamera(String input){
        return list.contains(input);
    }
    public boolean isLight(String input){
        return list.contains(input);
    }
    public boolean isInventory(String input){
        return list.contains(input);
    }

     */
}
