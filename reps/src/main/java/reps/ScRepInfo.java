package reps;

public class ScRepInfo {
    /**
     * 文件路径
     */
    private String filePAth;
    /**
     *
     */
    private Integer EnDTime;
    /**
     * 游戏时间
     */
    private Integer GameTime;
    /**
     * 种族对抗
     */
    private String race;
    /**
     * 地图名字
     */
    private String mapName;
    /**
     * 胜负关系
     */
    private String Win;
    /**
     * 新名字
     */
    private String newName;


    private String newPath;
    /**
     * 视频时间
     */
    private Integer StartTime;

    public ScRepInfo() {
    }

    public ScRepInfo(String filePAth, Integer enDTime, Integer gameTime, String race, String mapName, String win, String newName, String newPath, Integer startTime) {
        this.filePAth = filePAth;
        EnDTime = enDTime;
        GameTime = gameTime;
        this.race = race;
        this.mapName = mapName;
        Win = win;
        this.newName = newName;
        this.newPath = newPath;
        StartTime = startTime;
    }

    @Override
    public String toString() {
        return "ScRepInfo{" +
                "filePAth='" + filePAth + '\'' +
                ", EnDTime=" + EnDTime +
                ", GameTime=" + GameTime +
                ", race='" + race + '\'' +
                ", mapName='" + mapName + '\'' +
                ", Win='" + Win + '\'' +
                ", newName='" + newName + '\'' +
                ", newPath='" + newPath + '\'' +
                ", StartTime=" + StartTime +
                '}';
    }

    public String getFilePAth() {
        return filePAth;
    }

    public void setFilePAth(String filePAth) {
        this.filePAth = filePAth;
    }

    public Integer getEnDTime() {
        return EnDTime;
    }

    public void setEnDTime(Integer enDTime) {
        EnDTime = enDTime;
    }

    public Integer getGameTime() {
        return GameTime;
    }

    public void setGameTime(Integer gameTime) {
        GameTime = gameTime;
    }

    public String getRace() {
        return race;
    }

    public void setRace(String race) {
        this.race = race;
    }

    public String getMapName() {
        return mapName;
    }

    public void setMapName(String mapName) {
        this.mapName = mapName;
    }

    public String getWin() {
        return Win;
    }

    public void setWin(String win) {
        Win = win;
    }

    public String getNewName() {
        return newName;
    }

    public void setNewName(String newName) {
        this.newName = newName;
    }

    public String getNewPath() {
        return newPath;
    }

    public void setNewPath(String newPath) {
        this.newPath = newPath;
    }

    public Integer getStartTime() {
        return StartTime;
    }

    public void setStartTime(Integer startTime) {
        StartTime = startTime;
    }

}
