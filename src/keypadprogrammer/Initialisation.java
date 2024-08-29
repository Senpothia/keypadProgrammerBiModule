/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package keypadprogrammer;

/**
 *
 * @author Michel
 */
public class Initialisation {

    private String programmerDirectory;
    private String varEnv;
    private String binaryLocation;
    private String bleLocation;
    private String snProgramer1;
    private String snProgramer2;

    public Initialisation(String programmerDirectory, String varEnv, String binaryLocation, String bleLocation, String snProgramer1, String snProgramer2) {
        this.programmerDirectory = programmerDirectory;
        this.varEnv = varEnv;
        this.binaryLocation = binaryLocation;
        this.bleLocation = bleLocation;
        this.snProgramer1 = snProgramer1;
        this.snProgramer2 = snProgramer2;
    }

   

   

    public String getProgrammerDirectory() {
        return programmerDirectory;
    }

    public void setProgrammerDirectory(String programmerDirectory) {
        this.programmerDirectory = programmerDirectory;
    }

    public String getVarEnv() {
        return varEnv;
    }

    public void setVarEnv(String varEnv) {
        this.varEnv = varEnv;
    }

    public String getBinaryLocation() {
        return binaryLocation;
    }

    public void setBinaryLocation(String binaryLocation) {
        this.binaryLocation = binaryLocation;
    }

    public String getBleLocation() {
        return bleLocation;
    }

    public void setBleLocation(String bleLocation) {
        this.bleLocation = bleLocation;
    }

    public String getSnProgramer1() {
        return snProgramer1;
    }

    public void setSnProgramer1(String snProgramer1) {
        this.snProgramer1 = snProgramer1;
    }

    public String getSnProgramer2() {
        return snProgramer2;
    }

    public void setSnProgramer2(String snProgramer2) {
        this.snProgramer2 = snProgramer2;
    }
    
    

}
