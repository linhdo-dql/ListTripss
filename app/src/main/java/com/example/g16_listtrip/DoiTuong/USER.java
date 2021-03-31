package com.example.g16_listtrip.DoiTuong;

public class USER {
    public String sName, sDateBirth, sAdrress, sJob, sStudy, sRelation, sImageA, sImageC, sAccount;

    public String getsImageC() {
        return sImageC;
    }

    public void setsImageC(String sImageC) {
        this.sImageC = sImageC;
    }

    public USER(String sName, String sDateBirth, String sAdrress, String sJob, String sStudy, String sRelation, String sImageA, String sImageC, String sAccount) {
        this.sName = sName;
        this.sDateBirth = sDateBirth;
        this.sAdrress = sAdrress;
        this.sJob = sJob;
        this.sStudy = sStudy;
        this.sRelation = sRelation;
        this.sImageA = sImageA;
        this.sImageC = sImageC;
        this.sAccount = sAccount;
    }

    public String getsName() {
        return sName;
    }

    public void setsName(String sName) {
        this.sName = sName;
    }

    public String getsDateBirth() {
        return sDateBirth;
    }

    public void setsDateBirth(String sDateBirth) {
        this.sDateBirth = sDateBirth;
    }

    public String getsAdrress() {
        return sAdrress;
    }

    public void setsAdrress(String sAdrress) {
        this.sAdrress = sAdrress;
    }

    public String getsJob() {
        return sJob;
    }

    public void setsJob(String sJob) {
        this.sJob = sJob;
    }

    public String getsStudy() {
        return sStudy;
    }

    public void setsStudy(String sStudy) {
        this.sStudy = sStudy;
    }

    public String getsRelation() {
        return sRelation;
    }

    public void setsRelation(String sRelation) {
        this.sRelation = sRelation;
    }

    public String getsImageA() {
        return sImageA;
    }

    public void setsImageA(String sImageA) {
        this.sImageA = sImageA;
    }

    public String getsAccount() {
        return sAccount;
    }

    public void setsAccount(String sAccount) {
        this.sAccount = sAccount;
    }

    public USER() {
    }
}
