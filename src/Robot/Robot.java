package Robot;

public class Robot {
    int x;
    int y;
    int z;
    int h;
    int d;
    String state;
    String camera;
    String light;
    String function;
    int[] base={0,0,0};
    int[] cameraCoordinate={0,0};

    public Robot() {
        this.x=0;
        this.y=0;
        this.z=0;
        this.h=0;
        this.d=5;
        this.state = "Deactivated";
        this.camera = "Off";
        this.function = "Off";
        this.light="Off";
    }

    public Robot(int x, int y, int z, int h, int d) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.h = h;
        this.d = d;
    }

    public void setX(int input) {
        this.x = input;
    }

    public void setY(int input) {
        this.y = input;
    }

    public void setZ(int input) {
        this.z = input;
    }

    public void setH(int input) {
        this.h = input;
    }
    public void setD(int input){this.d = input;}

    public void setState(String input){this.state = input;}
    public void setCamera(String input){this.camera = input;}
    public void setFunction(String input){this.function=input;}
    public void setBase(int[] input){this.base=input;}
    public void setLight(String light){this.light=light;}

    public int getStateNo(){
        switch(state){
            case "Deactivated":
                return 0;
            case "Activated":
                return 1;
            case "LowBattery":
                return 2;
            case "Lost":
                return 3;
            case "Failure":
                return 4;
        }
        return -1;
    }
    public int getCameraNo(){  switch(camera){
        case "Off":
            return 0;
        case "On":
            return 1;
    }
        return -1;
    }
    public int getFunctionNo(){
        switch(function){
            case "Off":
                return 0;
            case "On":
                return 1;
        }
        return -1;
    }
    public int getLightNo(){
        switch(light){
            case "Off":
                return 0;
            case "On":
                return 1;
        }
        return -1;
    }
    public int getD(){return d;}
    public String getState(){return state;}
    public String getCamera(){return camera;}
    public String getFunction(){return function;}
    public String getLight(){return light;}
    public int[] getBase(){return base;}

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getZ() {
        return z;
    }

    public int getH() {
        return h;
    }

}
