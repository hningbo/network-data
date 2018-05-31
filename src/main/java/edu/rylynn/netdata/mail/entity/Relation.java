package edu.rylynn.netdata.mail.entity;

public class Relation {
    private String A;
    private String B;
    private double strength;

    public Relation(String a, String b, double strength) {

        A = a;
        B = b;
        this.strength = strength;
    }

    public String getA() {
        return A;
    }

    public void setA(String a) {
        A = a;
    }

    public String getB() {
        return B;
    }

    public void setB(String b) {
        B = b;
    }

    public double getStrength() {
        return strength;
    }

    public void setStrength(double strength) {
        this.strength = strength;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(A).append("--(").append(strength).append(")-->").append(B).append("\n");
        return sb.toString();
    }
}
