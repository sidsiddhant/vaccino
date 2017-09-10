package com.example.deepakprasad.vaccino;

/**
 * Created by Deepak Prasad on 06-09-2017.
 */

public class Patient {

    String uid,mothersName,fathersName,mobileNumber,adharID,childsName,gender,date;
    String hepb,opv0,bcg,penta1,opv1,ipv1,penta2,opv2,penta3,opv3,ipv2,
            measles1,measles2,opvbooster,dptbooster1,dptbooster2,next;


    public Patient(){}
    public Patient(String uid,String mothersName,String fathersName,String mobileNumber,String adharID,String childsName
                    ,String gender,String date,String hepb,String opv0,String bcg,String penta1,String opv1,
                   String ipv1,String penta2,String opv2,String penta3,String opv3,String ipv2,
                   String measles1,String measles2,String opvbooster,String dptbooster1,String dptbooster2,String next){
        this.uid=uid;
        this.mothersName=mothersName;
        this.fathersName=fathersName;
        this.mobileNumber=mobileNumber;
        this.adharID=adharID;
        this.childsName=childsName;
        this.gender=gender;
        this.date=date;
        this.hepb=hepb;
        this.opv0=opv0;
        this.opv1=opv1;
        this.opv2=opv2;
        this.opv3=opv3;
        this.bcg=bcg;
        this.penta1=penta1;
        this.penta2=penta2;
        this.penta3=penta3;
        this.ipv1=ipv1;this.ipv2=ipv2;this.measles1=measles1;this.measles2=measles2;this.opvbooster=opvbooster;this.dptbooster1=dptbooster1;
        this.dptbooster2=dptbooster2;this.next=next;

    }
    public String getUid(){return uid;}
    public String getMothersName(){return mothersName;}
    public String getFathersName(){return fathersName;}
    public String getMobileNumber(){return mobileNumber;}
    public String getAdharID(){return adharID;}
    public String getChildsName(){return childsName;}
    public String getGender(){return gender;}
    public String getDate(){return date;}

}
