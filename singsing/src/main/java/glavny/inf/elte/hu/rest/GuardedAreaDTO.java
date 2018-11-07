package glavny.inf.elte.hu.rest;

public class GuardedAreaDTO {
    private int areaID;
    private int level;
    private int guardNumber;
    private String areaName;

    public GuardedAreaDTO(int areaID, String areaName, int level, int guardNumber) {
        this.areaID = areaID;
        this.level = level;
        this.guardNumber = guardNumber;
        this.areaName = areaName;
    }

    public int getAreaID() {
        return areaID;
    }

    public int getLevel() {
        return level;
    }

    public int getGuardNumber() {
        return guardNumber;
    }

    public String getAreaName() {
        return areaName;
    }
}
