package it.gruppoinit.dss.ws.legacy;

public class WSSignatureLevelAnalysis {

    private WSSignatureLevelBES levelBES;
    private WSSignatureLevelEPES levelEPES;
    private WSSignatureLevelT levelT;
    private WSSignatureLevelC levelC;
    private WSSignatureLevelX levelX;
    private WSSignatureLevelXL levelXL;
    private WSSignatureLevelA levelA;
    private WSSignatureLevelLTV levelLTV;
    private String signatureFormat;

    public WSSignatureLevelAnalysis() {}

    public WSSignatureLevelBES getLevelBES() { return levelBES; }
    public void setLevelBES(WSSignatureLevelBES levelBES) { this.levelBES = levelBES; }

    public WSSignatureLevelEPES getLevelEPES() { return levelEPES; }
    public void setLevelEPES(WSSignatureLevelEPES levelEPES) { this.levelEPES = levelEPES; }

    public WSSignatureLevelT getLevelT() { return levelT; }
    public void setLevelT(WSSignatureLevelT levelT) { this.levelT = levelT; }

    public WSSignatureLevelC getLevelC() { return levelC; }
    public void setLevelC(WSSignatureLevelC levelC) { this.levelC = levelC; }

    public WSSignatureLevelX getLevelX() { return levelX; }
    public void setLevelX(WSSignatureLevelX levelX) { this.levelX = levelX; }

    public WSSignatureLevelXL getLevelXL() { return levelXL; }
    public void setLevelXL(WSSignatureLevelXL levelXL) { this.levelXL = levelXL; }

    public WSSignatureLevelA getLevelA() { return levelA; }
    public void setLevelA(WSSignatureLevelA levelA) { this.levelA = levelA; }

    public WSSignatureLevelLTV getLevelLTV() { return levelLTV; }
    public void setLevelLTV(WSSignatureLevelLTV levelLTV) { this.levelLTV = levelLTV; }

    public String getSignatureFormat() { return signatureFormat; }
    public void setSignatureFormat(String signatureFormat) { this.signatureFormat = signatureFormat; }
}
