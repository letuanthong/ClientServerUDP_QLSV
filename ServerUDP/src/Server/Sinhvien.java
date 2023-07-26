package Server;

import java.util.Vector;

public class Sinhvien {
    public String name;
    public String code;
    public float math;
    public float licterature;
    public float english;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public float getMath() {
        return math;
    }

    public void setMath(float math) {
        this.math = math;
    }

    public float getLicterature() {
        return licterature;
    }

    public void setLicterature(float licterature) {
        this.licterature = licterature;
    }

    public float getEnglish() {
        return english;
    }

    public void setEnglish(float english) {
        this.english = english;
    }

    public Sinhvien(){
        
    }
    
    public Sinhvien(String name, String code, float math, float licterature, float english){
        this.name = name;
        this.code = code;
        this.math = math;
        this.licterature = licterature;
        this.english = english;
    }
    
    public Vector hienThiRow() {
        Vector row = new Vector();
        row.add(this.name);
        row.add(this.code);
        float avg = (math + licterature + english)/3;
        row.add(avg);
        return row;
    }
}
