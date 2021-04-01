package com.example.driverapp.Model;

public class Processing {


    private String pickuploc;
    private String droploc;
    private String totalKm;
    private String bookdate;
    private String booktime;
    private String amount;
    private String Vechicaltype;
    private String vechicalname;
    private String customername;
    private int drop;
    private String id;
    private String custophone;
    private String StartotpTime;
    private String StartTripTime;
    private String ReachDestinationTime;



    private String Endtriptime;
    private String startotp;




    public Processing(String pickuploc, String droploc, String totalKm, String bookdate, String booktime, String amount, String vechicaltype, String vechicalname, String customername, int drop, String id, String custophone, String startotpTime, String startTripTime, String reachDestinationTime, String endtriptime, String startotp) {
        this.pickuploc = pickuploc;
        this.droploc = droploc;
        this.totalKm = totalKm;
        this.bookdate = bookdate;
        this.booktime = booktime;
        this.amount = amount;
        Vechicaltype = vechicaltype;
        this.vechicalname = vechicalname;
        this.customername = customername;
        this.drop = drop;
        this.id = id;
        this.custophone = custophone;
        this.StartotpTime = startotpTime;
        this.StartTripTime = startTripTime;
        this.ReachDestinationTime = reachDestinationTime;
        this.Endtriptime = endtriptime;
        this.startotp = startotp;
    }

    public String getStartotp() {
        return startotp;
    }

    public void setStartotp(String startotp) {
        this.startotp = startotp;
    }

    public String getStartotpTime() {
        return StartotpTime;
    }

    public void setStartotpTime(String startotpTime) {
        StartotpTime = startotpTime;
    }

    public String getStartTripTime() {
        return StartTripTime;
    }

    public void setStartTripTime(String startTripTime) {
        StartTripTime = startTripTime;
    }

    public String getReachDestinationTime() {
        return ReachDestinationTime;
    }

    public void setReachDestinationTime(String reachDestinationTime) {
        ReachDestinationTime = reachDestinationTime;
    }

    public String getEndtriptime() {
        return Endtriptime;
    }

    public void setEndtriptime(String endtriptime) {
        Endtriptime = endtriptime;
    }

    public String getCustophone() {
        return custophone;
    }

    public void setCustophone(String custophone) {
        this.custophone = custophone;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getDrop() {
        return drop;
    }

    public void setDrop(int drop) {
        this.drop = drop;
    }
    public String getPickuploc() {
        return pickuploc;
    }

    public void setPickuploc(String pickuploc) {
        this.pickuploc = pickuploc;
    }

    public String getDroploc() {
        return droploc;
    }

    public void setDroploc(String droploc) {
        this.droploc = droploc;
    }

    public String getTotalKm() {
        return totalKm;
    }

    public void setTotalKm(String totalKm) {
        this.totalKm = totalKm;
    }

    public String getBookdate() {
        return bookdate;
    }

    public void setBookdate(String bookdate) {
        this.bookdate = bookdate;
    }

    public String getBooktime() {
        return booktime;
    }

    public void setBooktime(String booktime) {
        this.booktime = booktime;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getVechicaltype() {
        return Vechicaltype;
    }

    public void setVechicaltype(String vechicaltype) {
        Vechicaltype = vechicaltype;
    }

    public String getVechicalname() {
        return vechicalname;
    }

    public void setVechicalname(String vechicalname) {
        this.vechicalname = vechicalname;
    }

    public String getCustomername() {
        return customername;
    }

    public void setCustomername(String customername) {
        this.customername = customername;
    }
}
