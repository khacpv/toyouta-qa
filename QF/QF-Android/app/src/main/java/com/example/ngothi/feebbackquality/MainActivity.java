package com.example.ngothi.feebbackquality;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.media.RingtoneManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.Activity;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Button;
import android.view.View;

import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ImageView;
import android.view.View.OnClickListener;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;

import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import java.net.InetAddress;
import java.util.Date;
import java.text.SimpleDateFormat;

import android.provider.MediaStore;

import java.io.File;

import android.os.Environment;
import android.net.Uri;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.File;

import android.app.AlertDialog;
import android.content.DialogInterface;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;

import android.database.Cursor;

import android.os.Vibrator;
import android.media.Ringtone;
import android.content.res.Configuration;

public class MainActivity extends Activity {
    Button ButtonChup, lap, gui,thu, chonLoi, vT1, vT2, vF1, vF2, vC1, vC2;
    ImageView hienAnh;
    // String SERVER_IP ;//huynn
    String SERVER_IP = "192.168.0.103";// huynt
    //SurfaceView surface;

    int IMAGE_TAKE = 100;// hang so chup anh
    int IMAGE_EDIT = 102;//hang so edit
    int LOI_LAP = 104;// hang so loi lap
    int THU_HOI = 106; // hang sothu hôi
    int CHON_LOI = 107;
    //String savePath;
    // MyClientTask myClientTask;//

    public String msgToServer1;
    private Dialog dialog, dialogthu_hoi, dialog_guilai,dialog_guilaianh,dialog_chonCa;

    int Index_gui = 1;// BIẾN DÙNG ĐỂ LƯU CÁC PHƯƠNG THỨC XỬ LÝ CỦA NÚT CHUP ẢNH
    boolean loilap1;// Bien dung để sử lý lôi lặp true có lăp false ko lăp
    boolean dF1 = false, dF2 = false, dT1 = false, dT2 = false, dC1 = false, dC2 = false,check_guilai=false;

    Socket clientSocket;

    public String tenFile, tenFile1,tenFileMoi=""; // BIẾN LUU TEN ANH ĐÃ EDIT

    public String tenFileLoiLap;
    public String tenFileThuHoi;
    SurfaceHolder surfaceHolder;// khai báo holder
    int LoiHienTai = 0;// dùng để chứa số thứ tự của nút vừa được nhấn
    String MaLoi = "";
    String MaProcess = "";
    String CaLamViec="";
    MyClientTask myClientTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        hienAnh = (ImageView) findViewById(R.id.image1);
        hienAnh.setImageResource(R.drawable.toyotalogo);
        ButtonChup = (Button) findViewById(R.id.btnChup);//khai bao nut chup
        lap = (Button) findViewById(R.id.btnLap);//khai bao nut chon anh lap lai
        gui = (Button) findViewById(R.id.btnGui);//nut gui, gui lai
        thu= (Button) findViewById(R.id.btnmenu);// nut thuhoi
        chonLoi = (Button) findViewById(R.id.btnChonLoi);
        vT1 = (Button) findViewById(R.id.btnT1);
        vT2 = (Button) findViewById(R.id.btnT2);
        vC1 = (Button) findViewById(R.id.btnC1);
        vC2 = (Button) findViewById(R.id.btnC2);
        vF1 = (Button) findViewById(R.id.btnF1);
        vF2 = (Button) findViewById(R.id.btnF2);
        VoHieu();

     //   myClientTask = new MyClientTask(SERVER_IP,8888,"Android");
       // myClientTask.execute();
        new Thread(new ClientThread()).start();
        //========================================================================
        chonLoi.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ChonloiActivity.class);
                startActivityForResult(intent, CHON_LOI);
            }
        });
        //==========================================================================
        gui.setOnClickListener(new OnClickListener() {// chuong trinh gui
            @Override
            public void onClick(View v) {
                if (dF1 == true)
                    MaProcess += "F1";
                if (dF2 == true)
                    MaProcess += "F2";
                if (dT1 == true)
                    MaProcess = "T";
                if (dT2 == true)
                    MaProcess = "T";
                if (dC1 == true)
                    MaProcess += "C1";
                if (dC2 == true)
                    MaProcess += "C2";

                    switch (Index_gui) {
                        case 1:// gui du lieu thong thuong
                            if ((MaProcess != "") && (MaLoi != "")) {
                                gui.setEnabled(true);
                                chonLoi.setText("Chọn Lỗi");
                               /* if (loilap1 == false)
                                    msgToServer1 = tenFile + "-" + MaLoi + "-" + MaProcess ;
                                else {
                                    if (tenFileLoiLap != null)
                                        msgToServer1 = tenFile + "-" + MaLoi + "-" + MaProcess + "-" + tenFileLoiLap  ;
                                    else
                                        msgToServer1 = tenFile + "-" + MaLoi + "-" + MaProcess ;
                                    loilap1 = false;
                                }*/
                                chonCa();

                                break;
                            }
                            else {
                                Toast.makeText(MainActivity.this, "Bạn cần chọn lỗi", Toast.LENGTH_LONG).show();
                                break;
                            }

                        case 3:// gui lai du liệu
                            if (dF1 == true)
                                MaProcess += "F1";
                            if (dF2 == true)
                                MaProcess += "F2";
                            if (dT1 == true)
                                MaProcess += "T";
                            if (dT2 == true)
                                MaProcess += "T";
                            if (dC1 == true)
                                MaProcess += "C1";
                            if (dC2 == true)
                                MaProcess += "C2";
                            if ((MaProcess != "") && (MaLoi != "")) {
                                guilai();
                            }
                            else {
                                Toast.makeText(MainActivity.this, "Bạn cần chọn lỗi", Toast.LENGTH_LONG).show();
                            }
                            break;
                    }
            }
        });

    }
    @Override
    protected void onResume() {
        super.onResume();

     //  myClientTask = new MyClientTask(SERVER_IP,8888,"");
      // myClientTask.execute();
    }
    //=======================================Cac PHƯƠNG THỨC CON================================================

    public void VoHieu() {
        vT1.setEnabled(false);
        vT2.setEnabled(false);
        vC1.setEnabled(false);
        vC2.setEnabled(false);
        vF1.setEnabled(false);
        vF2.setEnabled(false);
        lap.setEnabled(false);
        chonLoi.setEnabled(false);
        gui.setEnabled(false);
        vT1.setBackground(getResources().getDrawable(android.R.drawable.btn_default_small));
        vT2.setBackground(getResources().getDrawable(android.R.drawable.btn_default_small));
        vF1.setBackground(getResources().getDrawable(android.R.drawable.btn_default_small));
        vF2.setBackground(getResources().getDrawable(android.R.drawable.btn_default_small));
        vC1.setBackground(getResources().getDrawable(android.R.drawable.btn_default_small));
        vC2.setBackground(getResources().getDrawable(android.R.drawable.btn_default_small));
        chonLoi.setBackground(getResources().getDrawable(android.R.drawable.btn_default_small));
        lap.setBackground(getResources().getDrawable(android.R.drawable.btn_default_small));
        chonLoi.setText("Chọn lỗi");
        gui.setText("GỬI");
       // MaLoi="";
       // MaProcess="";
        dT1=false;
        dT2=false;
        dF1=false;
        dF2=false;
        dC1=false;
        dC2=false;
    }
    public void Kichhoat(){
        vT1.setBackground(getResources().getDrawable(android.R.drawable.btn_default_small));
        vT2.setBackground(getResources().getDrawable(android.R.drawable.btn_default_small));
        vF1.setBackground(getResources().getDrawable(android.R.drawable.btn_default_small));
        vF2.setBackground(getResources().getDrawable(android.R.drawable.btn_default_small));
        vC1.setBackground(getResources().getDrawable(android.R.drawable.btn_default_small));
        vC2.setBackground(getResources().getDrawable(android.R.drawable.btn_default_small));
        chonLoi.setBackground(getResources().getDrawable(android.R.drawable.btn_default_small));
        lap.setBackground(getResources().getDrawable(android.R.drawable.btn_default_small));
        vT1.setEnabled(true);
        vT2.setEnabled(true);
        vC1.setEnabled(true);
        vC2.setEnabled(true);
        vF1.setEnabled(true);
        vF2.setEnabled(true);
        lap.setEnabled(true);
        chonLoi.setEnabled(true);
        gui.setEnabled(true);
        chonLoi.setText("chọn lỗi");
    }

    public void chonCa()
    {
        dialog_chonCa = new Dialog(MainActivity.this);
        dialog_chonCa.setContentView(R.layout.chonca_layout);
        dialog_chonCa.setTitle("    CHỌN CA LÀM VIỆC");
        Button nut_CaVang = (Button) dialog_chonCa.findViewById(R.id.btnCavang);
        Button nut_CaDo = (Button) dialog_chonCa.findViewById(R.id.btnCado);
        nut_CaVang.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                CaLamViec="Ca Y";
                if(Index_gui==1) {
                    Index_gui = 2;
                    if (loilap1 == false)
                        msgToServer1 = tenFile + "-" + MaLoi + "-" + MaProcess + "-" + CaLamViec;
                    else {
                        if (tenFileLoiLap != "")
                            msgToServer1 = tenFile + "-" + MaLoi + "-" + MaProcess + "-" + CaLamViec + "-" + tenFileLoiLap;
                        else
                            msgToServer1 = tenFile + "-" + MaLoi + "-" + MaProcess + "-" + CaLamViec;
                        loilap1 = false;
                    }
                    hienAnh.setImageResource(R.drawable.toyotalogo);
                    VoHieu();
                }
                else {// index =3
                    Index_gui=1;
                    if (loilap1 == false)
                        msgToServer1 = tenFile + "-" + MaLoi + "-" + MaProcess +"-" + CaLamViec + "-reSend" ;
                    else {
                        if (tenFileLoiLap != "")
                            msgToServer1 = tenFile + "-" + MaLoi + "-" + MaProcess + "-" + CaLamViec + "-" + tenFileLoiLap ;
                        else
                            msgToServer1 = tenFile + "-" + MaLoi + "-" + MaProcess + "-" + CaLamViec  ;
                        loilap1 = false;
                    }
                }
                new Thread(new ClientThread()).start();
                try {
                    OutputStream outputStream = null;
                    outputStream = clientSocket.getOutputStream();
                    final PrintStream printStream = new PrintStream(outputStream);
                    printStream.print(msgToServer1);
                    printStream.flush();
                    MaLoi = "";
                    MaProcess = "";
                    tenFile="";
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if(tenFileMoi!="")
                {
                    Bitmap myBitmap = BitmapFactory.decodeFile("/storage/emulated/0/DCIM/Camera/"+tenFileMoi);
                    tenFile=tenFileMoi;
                    hienAnh.setImageBitmap(myBitmap);
                    Kichhoat();
                    gui.setText("Gửi");
                    tenFileMoi="";
                }
                else {
                    hienAnh.setImageResource(R.drawable.toyotalogo);
                    VoHieu();
                    Index_gui=1;
                }
                dialog_chonCa.dismiss();
            }
        });
        nut_CaDo.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                CaLamViec="Ca R";
                if(Index_gui==1) {
                    if (loilap1 == false)
                        msgToServer1 = tenFile + "-" + MaLoi + "-" + MaProcess + "-" + CaLamViec;
                    else {
                        if (tenFileLoiLap != "")
                            msgToServer1 = tenFile + "-" + MaLoi + "-" + MaProcess + "-" + CaLamViec + "-" + tenFileLoiLap;
                        else
                            msgToServer1 = tenFile + "-" + MaLoi + "-" + MaProcess + "-" + CaLamViec;
                        loilap1 = false;
                    }
                    hienAnh.setImageResource(R.drawable.toyotalogo);
                    VoHieu();
                }
                else { // index_gui =3 gui lai anh
                    if (loilap1 == false)
                        msgToServer1 = tenFile + "-" + MaLoi + "-" + MaProcess +"-" + CaLamViec + "-reSend" ;
                    else {
                        if (tenFileLoiLap != "")
                            msgToServer1 = tenFile + "-" + MaLoi + "-" + MaProcess + "-" + CaLamViec + "-" + tenFileLoiLap ;
                        else
                            msgToServer1 = tenFile + "-" + MaLoi + "-" + MaProcess + "-" + CaLamViec  ;
                        loilap1 = false;
                    }

                }
                new Thread(new ClientThread()).start();
                // msgToServer1=msgToServer1+ "-" + CaLamViec ;
                try {
                    // PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream())), true);
                    // out.println(msgToServer1);
                    OutputStream outputStream = null;
                    outputStream = clientSocket.getOutputStream();
                    final PrintStream printStream = new PrintStream(outputStream);
                    printStream.print(msgToServer1);
                    printStream.flush();
                    MaLoi = "";
                    MaProcess = "";
                    tenFile="";
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if(tenFileMoi!="")
                {
                    Bitmap myBitmap = BitmapFactory.decodeFile("/storage/emulated/0/DCIM/Camera/"+tenFileMoi);
                    tenFile=tenFileMoi;
                    hienAnh.setImageBitmap(myBitmap);
                    Kichhoat();
                    gui.setText("Gửi");
                    Index_gui=1;
                    tenFileMoi="";
                }
                else {
                    hienAnh.setImageResource(R.drawable.toyotalogo);
                    VoHieu();
                    Index_gui=1;
                }
                dialog_chonCa.dismiss();
            }
        });
        dialog_chonCa.show();
    }
    public void ThuHoi() {
        dialogthu_hoi = new Dialog(MainActivity.this);
        dialogthu_hoi.setContentView(R.layout.thu_hoi);
        dialogthu_hoi.setTitle("Chắc chắn thu hồi ??");
        Button nut_Co = (Button) dialogthu_hoi.findViewById(R.id.btnCo_thuhoi);
        Button nut_Khong = (Button) dialogthu_hoi.findViewById(R.id.btnKhong_thuhoi);
        nut_Co.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, thuhoi_activity.class);
                startActivityForResult(intent, THU_HOI);
                dialogthu_hoi.dismiss();
            }
        });
        nut_Khong.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogthu_hoi.dismiss();
            }
        });
        dialogthu_hoi.show();
    }

    public void thuhoiOk() {
        new Thread(new ClientThread()).start();
        try {
            OutputStream outputStream = null;
            outputStream = clientSocket.getOutputStream();
            final PrintStream printStream = new PrintStream(outputStream);
            msgToServer1 = "DEL" + "-" + tenFileThuHoi;
            printStream.print(msgToServer1);
            printStream.flush();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(tenFile!="")
            tenFileMoi=tenFile;
        tenFile=tenFileThuHoi;
        Bitmap myBitmap = BitmapFactory.decodeFile("/storage/emulated/0/DCIM/Camera/"+tenFile);
        hienAnh.setImageBitmap(myBitmap);
        Kichhoat();
        MaProcess="";
        tenFileThuHoi = "";
        MaLoi="";
        Index_gui = 3;
        gui.setText("Gửi lại");
    }

    public void guilai() {
        dialog_guilai = new Dialog(MainActivity.this);
        dialog_guilai.setContentView(R.layout.gui_lai);
        dialog_guilai.setTitle("Chắc chắn gửi lại ??");
        Button nut_Co_guilai = (Button) dialog_guilai.findViewById(R.id.btnCo_guilai);
        Button nut_Khong_guilai = (Button) dialog_guilai.findViewById(R.id.btnKhong_guilai);
        nut_Co_guilai.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                chonCa();
                dialog_guilai.dismiss();
            }
        });
        nut_Khong_guilai.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog_guilai.dismiss();
            }
        });
        dialog_guilai.show();

    }

   class ClientThread implements Runnable {
        @Override
        public void run() {
            try {
                InetAddress serverAddr = InetAddress.getByName(SERVER_IP);
                clientSocket = new Socket(serverAddr, 8888);
            } catch (UnknownHostException e1) {
                e1.printStackTrace();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }
    class StopClientThread implements Runnable {
        @Override
        public void run() {
            try {
                clientSocket.close();
            } catch (UnknownHostException e1) {
                e1.printStackTrace();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }
    //--------------------------------------------------------------------------------------------------------

    //----------------------------------------------------------------------------------------------------------
//=======================================CODE SU LY NUT BAM=================================================
    //------------------------------------------------------------------------------------------------------

    public void ClickT1(View v) {
        if (dT1 == false)//nut chua duoc an
        {
            vT1.setBackground(getResources().getDrawable(R.drawable.blue_drak));
            dT1 = true;
        } else {
            vT1.setBackground(getResources().getDrawable(android.R.drawable.btn_default_small));
            dT1 = false;
        }
    }

    public void ClickT2(View v) {
        if (dT2 == false)//nut chua duoc an
        {
            vT2.setBackground(getResources().getDrawable(R.drawable.blue_drak));
            dT2 = true;
        } else {
            vT2.setBackground(getResources().getDrawable(android.R.drawable.btn_default_small));
            dT2 = false;
        }
    }
    public void ClickC1(View v) {
        if (dC1 == false)//nut chua duoc an
        {
            vC1.setBackground(getResources().getDrawable(R.drawable.blue_drak));
            dC1 = true;
        } else {
            vC1.setBackground(getResources().getDrawable(android.R.drawable.btn_default_small));
            dC1 = false;
        }
    }
    public void ClickC2(View v) {
        if (dC2 == false)//nut chua duoc an
        {
            vC2.setBackground(getResources().getDrawable(R.drawable.blue_drak));
            dC2 = true;
        } else {
            vC2.setBackground(getResources().getDrawable(android.R.drawable.btn_default_small));
            dC2 = false;
        }
    }
    public void ClickF1(View v) {
        if (dF1 == false)//nut chua duoc an
        {
            vF1.setBackground(getResources().getDrawable(R.drawable.blue_drak));
            dF1 = true;
        } else {
            vF1.setBackground(getResources().getDrawable(android.R.drawable.btn_default_small));
            dF1 = false;
        }
    }

    public void ClickF2(View v) {
        if (dF2 == false)//nut chua duoc an
        {
            vF2.setBackground(getResources().getDrawable(R.drawable.blue_drak));
            dF2 = true;
        } else {
            vF2.setBackground(getResources().getDrawable(android.R.drawable.btn_default_small));
            dF2 = false;
        }
    }

    public void ClickChup(View v) throws IOException //PHUONG THUC XU LY NUT CHUP ANH
    {

        Index_gui = 1;
        VoHieu();
        Intent intent = new Intent(MainActivity.this, CameraMain.class);
        startActivityForResult(intent, IMAGE_TAKE);

    }

    public void ClickThuhoi(View v)
    {
        ThuHoi();
    }
    //-------------------------------------------------------------------------------------------------------
    public void loilap(View v) {


        Intent intent = new Intent(MainActivity.this, chonLoiLapActivity.class);
        startActivityForResult(intent, LOI_LAP);
    }

    //----------------------------------------------------------------------------------------------


    //====================================KET THUC CODE XU LY NUT BAM============================================
//-----------------------------------------------------------------------------------------------------------
//====================================CODE SU LY CAC SU KIEN O CAC ACTIVITY KHAC==================================
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == CHON_LOI) {
                Bundle packageFromCaller = data.getBundleExtra("GoiTen");
                LoiHienTai = packageFromCaller.getInt("chisoloi");
                MaLoi = packageFromCaller.getString("tenloi");
                chonLoi.setText("lỗi " + MaLoi);
               chonLoi.setBackground(getResources().getDrawable(android.R.color.holo_blue_light));
            }
            if (requestCode == IMAGE_EDIT) {
                Kichhoat();
                Bundle packageFromCaller = data.getBundleExtra("GoiTen");
                tenFile = packageFromCaller.getString("tenfile");
                // Toast.makeText(MainActivity.this,tenFile,Toast.LENGTH_LONG).show();
                File imgFile = new File(tenFile);
                if (imgFile.exists()) {
                    Bitmap myBitmap1 = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                    hienAnh.setImageBitmap(myBitmap1);
                }
                tenFile = tenFile.substring(32);
            }
            if (requestCode == LOI_LAP) {
                Bundle packageFromCaller = data.getBundleExtra("GoiTen");
                tenFileLoiLap = packageFromCaller.getString("tenfile");
               lap.setBackground(getResources().getDrawable(android.R.color.holo_blue_light));
                loilap1 = true;
            }
            if (requestCode == THU_HOI) {
                Bundle packageFromCaller = data.getBundleExtra("GoiTen");
                tenFileThuHoi = packageFromCaller.getString("tenfile");
                thuhoiOk();

            }
            if (requestCode == IMAGE_TAKE) {
                Bundle packageFromCaller = data.getBundleExtra("GoiTen");
                tenFile1 = packageFromCaller.getString("tenfile");
                Intent Myintent = new Intent(MainActivity.this, edit_vaythoi.class);
                Bundle ten_image = new Bundle();
                ten_image.putString("tenfile1", tenFile1);
                Myintent.putExtra("GoiTen1", ten_image);
                startActivityForResult(Myintent, IMAGE_EDIT);
            }
        }
    }

    //==================================KET THUC CODE SU LY SU KIEN===============================================
//------------------------------------------------------------------------------------------------------------
    public String getPath(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        int column_index = cursor
                .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }
    //=======================================CODE GIAO THUC CLIENT SERVER======================================================
//=========================================================================================================================
    protected class MyClientTask extends AsyncTask<Void, String, Void> {

        String dstAddress;
        int dstPort;
        String response = "";
        String msgToServer;

        MyClientTask(String addr, int port, String msgTo) {
            dstAddress = addr;
            dstPort = port;
            msgToServer = msgTo;


        }

        @Override
        protected Void doInBackground(Void... arg0) {
            String msgrec=null;

            try {
                OutputStream outputStream = null;
                 clientSocket = new Socket(dstAddress, dstPort);
                outputStream = clientSocket.getOutputStream();
                final BufferedReader in1 = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                final PrintStream printStream = new PrintStream(outputStream);
                response = "Connected";
                publishProgress(response);
                        while((msgrec = in1.readLine())!=null){
                            publishProgress(msgrec);
                            Log.d("Database operations","String"+msgrec);
                    }

            } catch (UnknownHostException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } finally {
                if (clientSocket != null) {
                    try {
                        clientSocket.close();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }

            return null;

        }//ket thuc doInBackground

        @Override
        protected void onProgressUpdate(final String... update) {
            if (update[0].equals("Connected") || update[0].equals("Cannot connect to server")) {

                Toast.makeText(MainActivity.this, update[0], Toast.LENGTH_SHORT).show();
            } else //textResponse.setText(update[0]);
            {
              //  Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
               // Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
               //final Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), notification);
                //v.vibrate(400);
                //r.play();
                dialog_guilaianh = new Dialog(MainActivity.this);
                dialog_guilaianh.setContentView(R.layout.loiguilai);
                dialog_guilaianh.setTitle("ẢNH ĐÃ BỊ TỪ CHỐI BỞI TRIM A1");
                Button coGuiLai = (Button) dialog_guilaianh.findViewById(R.id.btncoguilai);
                Button khongGuilai= (Button) dialog_guilaianh.findViewById(R.id.btnkhongguilai) ;
                coGuiLai.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                  //       r.stop(
                            if(tenFile!="")
                             tenFileMoi=tenFile;
                             tenFile=update[0].substring(6,25);
                            Bitmap myBitmap = BitmapFactory.decodeFile("/storage/emulated/0/DCIM/Camera/"+tenFile);
                            hienAnh.setImageBitmap(myBitmap);
                            Kichhoat();
                            gui.setText("gửi lại");
                            Index_gui=3;
                            dialog_guilaianh.dismiss();
                        }
                    });
                    khongGuilai.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                       //    r.stop();
                            dialog_guilaianh.dismiss();
                        }
                    });
                dialog_guilaianh.show();

            }
        }

        @Override
        protected void onCancelled() {
            // TODO Auto-generated method stub
            //txtStatus.setText("Disconnect");
            Toast.makeText(MainActivity.this, "Disconnect", Toast.LENGTH_SHORT).show();
            super.onCancelled();
        }

        protected void onPostExecute(Void result) {
            // txtStatus.setText("Disconnect");
            Toast.makeText(MainActivity.this, "Disconnect", Toast.LENGTH_SHORT).show();
            super.onPostExecute(result);
        }
    }
    //================================KET THUC CODE GIAO THUC CLIENT SERVER==============================

}






