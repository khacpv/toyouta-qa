package com.example.ngothi.feebbackquality;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ChonloiActivity extends AppCompatActivity {
Button AB, B_,BR,CF,CR,D1,D_,DI,F1,F_,G1,H1,L1,M1,OL,OR,OT,OV,PC,PD,PP,PS,R1,S_,SA,SC,SG,SP,SR,T_,TC,U1,W_,WA,WP;
    int loi=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chonloi);
        AB=(Button) findViewById(R.id.btnAB);// nut chon AB
        B_=(Button) findViewById(R.id.btnB_);
        BR=(Button) findViewById(R.id.btnBR);
        CF=(Button) findViewById(R.id.btnCF);
        CR=(Button) findViewById(R.id.btnCR);
        D1=(Button) findViewById(R.id.btnD1);//nut chon D
        D_=(Button) findViewById(R.id.btnD_);
        DI=(Button) findViewById(R.id.btnDI);
        F1=(Button) findViewById(R.id.btnF1);
        F_=(Button) findViewById(R.id.btnF_);
        G1=(Button) findViewById(R.id.btnG1); //nut chon G
        H1=(Button) findViewById(R.id.btnH1);//nut chon H
        L1=(Button) findViewById(R.id.btnL1);// nut chon L
        M1=(Button) findViewById(R.id.btnM1);//nut chon M
        OL=(Button) findViewById(R.id.btnOL);
        OR=(Button) findViewById(R.id.btnOR);
        OT=(Button) findViewById(R.id.btnOT);
        OV=(Button) findViewById(R.id.btnOV);
        PC=(Button) findViewById(R.id.btnPC);
        PD=(Button) findViewById(R.id.btnPD);
        PP=(Button) findViewById(R.id.btnPP);
        PS=(Button) findViewById(R.id.btnPS);
        R1=(Button) findViewById(R.id.btnR1);//nut chon R
        S_=(Button) findViewById(R.id.btnS_);
        SA=(Button) findViewById(R.id.btnSA);
        SC=(Button) findViewById(R.id.btnSC);
        SG=(Button) findViewById(R.id.btnSG);
        SP=(Button) findViewById(R.id.btnSP);
        SR=(Button) findViewById(R.id.btnSR);
        T_=(Button) findViewById(R.id.btnT_);
        TC=(Button) findViewById(R.id.btnTC);//nut chon R
        U1=(Button) findViewById(R.id.btnU1);
        W_=(Button) findViewById(R.id.btnSA);
        WA=(Button) findViewById(R.id.btnWA);
        WP=(Button) findViewById(R.id.btnWP);
    }
    public void trave(int x,String maloi)
    {

        Intent data = new Intent();
        Bundle ten_loi = new Bundle();
        ten_loi.putString("tenloi", maloi);
        ten_loi.putInt("chisoloi",x);
        data.putExtra("GoiTen", ten_loi);
        this.setResult(MainActivity.RESULT_OK, data);
        ChonloiActivity.super.finish();
    }
    public void ClickAB(View v)
    {
        trave(1,"AB");
    }
    public void ClickB_(View v)
    {
        trave(2,"B_");
    }
    public void ClickBR(View v)
    {
        trave(3,"BR");
    }
    public void ClickCF(View v)
    {
        trave(4,"CF");
    }
    public void ClickCR(View v)
    {
        trave(5,"CR");
    }
    public void ClickD1(View v)
    {
        trave(6,"D");
    }
    public void ClickD_(View v)
    {
        trave(7,"D_");
    }
    public void ClickDI(View v)
    {
        trave(8,"DI");
    }
    public void ClickF1(View v)
    {
        trave(9,"F");
    }
    public void ClickF_(View v)
    {
        trave(10,"F_");
    }
    public void ClickG1(View v)
    {
        trave(11,"G");
    }
    public void ClickH1(View v)
    {
        trave(12,"H");
    }
    public void ClickL1(View v)
    {
        trave(13,"L");
    }
    public void ClickM1(View v)
    {
        trave(14,"M");
    }
    public void ClickOL(View v)
    {
        trave(15,"OL");
    }
    public void ClickOR(View v)
    {
        trave(16,"OR");
    }
    public void ClickOT(View v)
    {
        trave(17,"OT");
    }
    public void ClickOV(View v)
    {
        trave(18,"OV");
    }
    public void ClickPC(View v)
    {
        trave(19,"PC");
    }
    public void ClickPD(View v)
    {
        trave(20,"PD");
    }
    public void ClickPP(View v)
    {
        trave(21,"PP");
    }
    public void ClickPS(View v)
    {
        trave(22,"PS");
    }
    public void ClickR1(View v)
    {
        trave(23,"R");
    }
    public void ClickS_(View v)
    {
        trave(24,"S_");
    }
    public void ClickSA(View v)
    {
        trave(25,"SA");
    }
    public void ClickSC(View v)
    {
        trave(26,"SC");
    }
    public void ClickSG(View v)
    {
        trave(27,"SG");
    }
    public void ClickSP(View v)
    {
        trave(28,"SP");
    }
    public void ClickSR(View v)
    {
        trave(29,"SR");
    }
    public void ClickT_(View v)
    {
        trave(30,"T_");
    }
    public void ClickTC(View v)
    {
        trave(31,"TC");
    }
    public void ClickU1(View v)
    {
        trave(32,"U");
    }
    public void ClickW_(View v)
    {
        trave(33,"W_");
    }
    public void ClickWA(View v)
    {
        trave(34,"WA");
    }
    public void ClickWP(View v)
    {
        trave(35,"WP");
    }

}
