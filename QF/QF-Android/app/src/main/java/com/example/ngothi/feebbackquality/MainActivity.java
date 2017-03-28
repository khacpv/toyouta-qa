package com.example.ngothi.feebbackquality;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.SurfaceHolder;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.view.View;

import android.widget.ImageButton;
import android.widget.Toast;
import android.widget.ImageView;
import android.view.View.OnClickListener;
import android.os.AsyncTask;

import java.net.InetAddress;

import android.provider.MediaStore;

import java.io.File;

import android.net.Uri;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;

import android.database.Cursor;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = MainActivity.class.getSimpleName();
    public static final int REQUEST_CAMERA = 1993;
    public static final int TAKE_FOTO = 9001;
    public static final String PHOTO_FILE_NAME = "photo_file_name";
    private static final String photoFileName = "photoFileName.jpg";

    @BindView(R.id.btn_chup_anh)
    ImageButton mImageButtonChupAnh;

    @BindView(R.id.btn_lap)
    Button mButtonLap;

    @BindView(R.id.btn_gui)
    Button mButtonGui;

    @BindView(R.id.btn_thu_hoi)
    Button mButtonThuHoi;

    @BindView(R.id.btn_chon_loi)
    Button mButtonChonLoi;

    @BindView(R.id.btn_position_t1)
    Button mButtonPositionT1;

    @BindView(R.id.btn_position_t2)
    Button mButtonPositionT2;

    @BindView(R.id.btn_position_f1)
    Button mButtonPositionF1;

    @BindView(R.id.btn_position_f2)
    Button mButtonPositionF2;

    @BindView(R.id.btn_position_c1)
    Button mButtonPositionC1;

    @BindView(R.id.btn_position_c2)
    Button mButtonPositionC2;

    @BindView(R.id.image_logo)
    ImageView mImageViewLogo;

    private Uri uriPhoto;
    private String imgPath;

    // String SERVER_IP ;//huynn
    String SERVER_IP = "192.168.0.103";// huynt
    //SurfaceView surface;

    int IMAGE_TAKE = 100;// hang so chup anh
    int IMAGE_EDIT = 102;//hang so edit
    int LOI_LAP = 104;// hang so loi mButtonLap
    int THU_HOI = 106; // hang sothu hôi
    int CHON_LOI = 107;
    //String savePath;
    // MyClientTask myClientTask;//

    public String msgToServer1;
    private Dialog dialog, dialogthu_hoi, dialog_guilai, dialog_guilaianh, dialog_chonCa;

    int Index_gui = 1;// BIẾN DÙNG ĐỂ LƯU CÁC PHƯƠNG THỨC XỬ LÝ CỦA NÚT CHUP ẢNH
    boolean loilap1;// Bien dung để sử lý lôi lặp true có lăp false ko lăp
    boolean dF1 = false, dF2 = false, dT1 = false, dT2 = false, dC1 = false, dC2 = false, check_guilai = false;

    Socket clientSocket;

    public String tenFile, tenFile1, tenFileMoi = ""; // BIẾN LUU TEN ANH ĐÃ EDIT

    public String tenFileLoiLap;
    public String tenFileThuHoi;
    SurfaceHolder surfaceHolder;// khai báo holder
    int LoiHienTai = 0;// dùng để chứa số thứ tự của nút vừa được nhấn
    String MaLoi = "";
    String MaProcess = "";
    String CaLamViec = "";
    MyClientTask myClientTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mImageViewLogo.setImageResource(R.drawable.toyotalogo);

        VoHieu();

        //   myClientTask = new MyClientTask(SERVER_IP,8888,"Android");
        // myClientTask.execute();
        new Thread(new ClientThread()).start();
        //========================================================================
        mButtonChonLoi.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ChonloiActivity.class);
                startActivityForResult(intent, CHON_LOI);
            }
        });
        //==========================================================================
        mButtonGui.setOnClickListener(new OnClickListener() {// chuong trinh mButtonGui
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
                    case 1:// mButtonGui du lieu thong thuong
                        if ((MaProcess != "") && (MaLoi != "")) {
                            mButtonGui.setEnabled(true);
                            mButtonChonLoi.setText("Chọn Lỗi");
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
                        } else {
                            Toast.makeText(MainActivity.this, "Bạn cần chọn lỗi", Toast.LENGTH_LONG).show();
                            break;
                        }

                    case 3:// mButtonGui lai du liệu
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
                        } else {
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
        mButtonPositionT1.setEnabled(false);
        mButtonPositionT2.setEnabled(false);
        mButtonPositionC1.setEnabled(false);
        mButtonPositionC2.setEnabled(false);
        mButtonPositionF1.setEnabled(false);
        mButtonPositionF2.setEnabled(false);
        mButtonLap.setEnabled(false);
        mButtonChonLoi.setEnabled(false);
        mButtonGui.setEnabled(false);
        mButtonPositionT1.setBackground(getResources().getDrawable(android.R.drawable.btn_default_small));
        mButtonPositionT2.setBackground(getResources().getDrawable(android.R.drawable.btn_default_small));
        mButtonPositionF1.setBackground(getResources().getDrawable(android.R.drawable.btn_default_small));
        mButtonPositionF2.setBackground(getResources().getDrawable(android.R.drawable.btn_default_small));
        mButtonPositionC1.setBackground(getResources().getDrawable(android.R.drawable.btn_default_small));
        mButtonPositionC2.setBackground(getResources().getDrawable(android.R.drawable.btn_default_small));
        mButtonChonLoi.setBackground(getResources().getDrawable(android.R.drawable.btn_default_small));
        mButtonLap.setBackground(getResources().getDrawable(android.R.drawable.btn_default_small));
        mButtonChonLoi.setText("Chọn lỗi");
        mButtonGui.setText("GỬI");
        // MaLoi="";
        // MaProcess="";
        dT1 = false;
        dT2 = false;
        dF1 = false;
        dF2 = false;
        dC1 = false;
        dC2 = false;
    }

    public void Kichhoat() {
        mButtonPositionT1.setBackground(getResources().getDrawable(android.R.drawable.btn_default_small));
        mButtonPositionT2.setBackground(getResources().getDrawable(android.R.drawable.btn_default_small));
        mButtonPositionF1.setBackground(getResources().getDrawable(android.R.drawable.btn_default_small));
        mButtonPositionF2.setBackground(getResources().getDrawable(android.R.drawable.btn_default_small));
        mButtonPositionC1.setBackground(getResources().getDrawable(android.R.drawable.btn_default_small));
        mButtonPositionC2.setBackground(getResources().getDrawable(android.R.drawable.btn_default_small));
        mButtonChonLoi.setBackground(getResources().getDrawable(android.R.drawable.btn_default_small));
        mButtonLap.setBackground(getResources().getDrawable(android.R.drawable.btn_default_small));
        mButtonPositionT1.setEnabled(true);
        mButtonPositionT2.setEnabled(true);
        mButtonPositionC1.setEnabled(true);
        mButtonPositionC2.setEnabled(true);
        mButtonPositionF1.setEnabled(true);
        mButtonPositionF2.setEnabled(true);
        mButtonLap.setEnabled(true);
        mButtonChonLoi.setEnabled(true);
        mButtonGui.setEnabled(true);
        mButtonChonLoi.setText("chọn lỗi");
    }

    public void chonCa() {
        dialog_chonCa = new Dialog(this);
        dialog_chonCa.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog_chonCa.setContentView(R.layout.chonca_layout);
        dialog_chonCa.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        dialog_chonCa.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        Button nut_CaVang = (Button) dialog_chonCa.findViewById(R.id.btnCavang);
        Button nut_CaDo = (Button) dialog_chonCa.findViewById(R.id.btnCado);
        nut_CaVang.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                CaLamViec = "Ca Y";
                if (Index_gui == 1) {
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
                    mImageViewLogo.setImageResource(R.drawable.toyotalogo);
                    VoHieu();
                } else {// index =3
                    Index_gui = 1;
                    if (loilap1 == false)
                        msgToServer1 = tenFile + "-" + MaLoi + "-" + MaProcess + "-" + CaLamViec + "-reSend";
                    else {
                        if (tenFileLoiLap != "")
                            msgToServer1 = tenFile + "-" + MaLoi + "-" + MaProcess + "-" + CaLamViec + "-" + tenFileLoiLap;
                        else
                            msgToServer1 = tenFile + "-" + MaLoi + "-" + MaProcess + "-" + CaLamViec;
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
                    tenFile = "";
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (tenFileMoi != "") {
                    Bitmap myBitmap = BitmapFactory.decodeFile("/storage/emulated/0/DCIM/Camera/" + tenFileMoi);
                    tenFile = tenFileMoi;
                    mImageViewLogo.setImageBitmap(myBitmap);
                    Kichhoat();
                    mButtonGui.setText("Gửi");
                    tenFileMoi = "";
                } else {
                    mImageViewLogo.setImageResource(0);
                    mImageViewLogo.setImageResource(R.drawable.toyotalogo);
                    VoHieu();
                    Index_gui = 1;
                }
                dialog_chonCa.dismiss();
            }
        });
        nut_CaDo.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                CaLamViec = "Ca R";
                if (Index_gui == 1) {
                    if (loilap1 == false)
                        msgToServer1 = tenFile + "-" + MaLoi + "-" + MaProcess + "-" + CaLamViec;
                    else {
                        if (tenFileLoiLap != "")
                            msgToServer1 = tenFile + "-" + MaLoi + "-" + MaProcess + "-" + CaLamViec + "-" + tenFileLoiLap;
                        else
                            msgToServer1 = tenFile + "-" + MaLoi + "-" + MaProcess + "-" + CaLamViec;
                        loilap1 = false;
                    }
                    mImageViewLogo.setImageResource(R.drawable.toyotalogo);
                    VoHieu();
                } else { // index_gui =3 mButtonGui lai anh
                    if (loilap1 == false)
                        msgToServer1 = tenFile + "-" + MaLoi + "-" + MaProcess + "-" + CaLamViec + "-reSend";
                    else {
                        if (tenFileLoiLap != "")
                            msgToServer1 = tenFile + "-" + MaLoi + "-" + MaProcess + "-" + CaLamViec + "-" + tenFileLoiLap;
                        else
                            msgToServer1 = tenFile + "-" + MaLoi + "-" + MaProcess + "-" + CaLamViec;
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
                    tenFile = "";
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (tenFileMoi != "") {
                    Bitmap myBitmap = BitmapFactory.decodeFile("/storage/emulated/0/DCIM/Camera/" + tenFileMoi);
                    tenFile = tenFileMoi;
                    mImageViewLogo.setImageBitmap(myBitmap);
                    Kichhoat();
                    mButtonGui.setText("Gửi");
                    Index_gui = 1;
                    tenFileMoi = "";
                } else {
                    mImageViewLogo.setImageResource(0);
                    mImageViewLogo.setImageResource(R.drawable.toyotalogo);
                    VoHieu();
                    Index_gui = 1;
                }
                dialog_chonCa.dismiss();
            }
        });
        dialog_chonCa.show();
    }

    public void ThuHoi() {
        dialogthu_hoi = new Dialog(MainActivity.this);
        dialogthu_hoi.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogthu_hoi.setContentView(R.layout.thu_hoi);
        dialogthu_hoi.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        dialogthu_hoi.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
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
        if (tenFile != "")
            tenFileMoi = tenFile;
        tenFile = tenFileThuHoi;
        Bitmap myBitmap = BitmapFactory.decodeFile("/storage/emulated/0/DCIM/Camera/" + tenFile);
        mImageViewLogo.setImageBitmap(myBitmap);
        Kichhoat();
        MaProcess = "";
        tenFileThuHoi = "";
        MaLoi = "";
        Index_gui = 3;
        mButtonGui.setText("Gửi lại");
    }

    public void guilai() {
        dialog_guilai = new Dialog(MainActivity.this);
        dialog_guilai.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog_guilai.setContentView(R.layout.gui_lai);
        dialogthu_hoi.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        dialogthu_hoi.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
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
            mButtonPositionT1.setBackground(getResources().getDrawable(R.drawable.blue_drak));
            dT1 = true;
        } else {
            mButtonPositionT1.setBackground(getResources().getDrawable(android.R.drawable.btn_default_small));
            dT1 = false;
        }
    }

    public void ClickT2(View v) {
        if (dT2 == false)//nut chua duoc an
        {
            mButtonPositionT2.setBackground(getResources().getDrawable(R.drawable.blue_drak));
            dT2 = true;
        } else {
            mButtonPositionT2.setBackground(getResources().getDrawable(android.R.drawable.btn_default_small));
            dT2 = false;
        }
    }

    public void ClickC1(View v) {
        if (dC1 == false)//nut chua duoc an
        {
            mButtonPositionC1.setBackground(getResources().getDrawable(R.drawable.blue_drak));
            dC1 = true;
        } else {
            mButtonPositionC1.setBackground(getResources().getDrawable(android.R.drawable.btn_default_small));
            dC1 = false;
        }
    }

    public void ClickC2(View v) {
        if (dC2 == false)//nut chua duoc an
        {
            mButtonPositionC2.setBackground(getResources().getDrawable(R.drawable.blue_drak));
            dC2 = true;
        } else {
            mButtonPositionC2.setBackground(getResources().getDrawable(android.R.drawable.btn_default_small));
            dC2 = false;
        }
    }

    public void ClickF1(View v) {
        if (dF1 == false)//nut chua duoc an
        {
            mButtonPositionF1.setBackground(getResources().getDrawable(R.drawable.blue_drak));
            dF1 = true;
        } else {
            mButtonPositionF1.setBackground(getResources().getDrawable(android.R.drawable.btn_default_small));
            dF1 = false;
        }
    }

    public void ClickF2(View v) {
        if (dF2 == false)//nut chua duoc an
        {
            mButtonPositionF2.setBackground(getResources().getDrawable(R.drawable.blue_drak));
            dF2 = true;
        } else {
            mButtonPositionF2.setBackground(getResources().getDrawable(android.R.drawable.btn_default_small));
            dF2 = false;
        }
    }

    @OnClick(R.id.btn_chup_anh)
    void launchCamera() {
        Index_gui = 1;
        VoHieu();
        //ClickChup();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA);
            } else {
                intentCamera();
            }
        } else {
            intentCamera();
        }
    }

    private void intentCamera() {
        Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        takePicture.putExtra(MediaStore.EXTRA_OUTPUT, Utils.getPhotoFileUri(this, photoFileName));
        if (takePicture.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePicture, TAKE_FOTO);
        }
    }

    public void ClickChup() {

        Index_gui = 1;
        VoHieu();
        Intent intent = new Intent(MainActivity.this, CameraMain.class);
        startActivityForResult(intent, IMAGE_TAKE);

    }

    public void ClickThuhoi(View v) {
        ThuHoi();
    }

    //-------------------------------------------------------------------------------------------------------
    public void loilap(View v) {


        Intent intent = new Intent(MainActivity.this, chonLoiLapActivity.class);
        startActivityForResult(intent, LOI_LAP);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case REQUEST_CAMERA:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    intentCamera();
                } else {
                    Toast.makeText(this, "Camera Permission Denied", Toast.LENGTH_LONG).show();
                }

                break;
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == CHON_LOI) {
                Bundle packageFromCaller = data.getBundleExtra("GoiTen");
                LoiHienTai = packageFromCaller.getInt("chisoloi");
                MaLoi = packageFromCaller.getString("tenloi");
                mButtonChonLoi.setText("lỗi " + MaLoi);
                mButtonChonLoi.setBackground(getResources().getDrawable(android.R.color.holo_blue_light));
            }
            if (requestCode == IMAGE_EDIT) {
                Kichhoat();
                Bundle packageFromCaller = data.getBundleExtra("GoiTen");
                tenFile = packageFromCaller.getString("photoFileName");
                // Toast.makeText(MainActivity.this,tenFile,Toast.LENGTH_LONG).show();

                mImageViewLogo.setImageResource(0);

                Glide.with(this).load(Uri.fromFile(new File(tenFile))).fitCenter().into(mImageViewLogo);


//                File imgFile = new File(tenFile);
//                if (imgFile.exists()) {
//                    Bitmap myBitmap1 = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
//                    mImageViewLogo.setImageBitmap(myBitmap1);
//                }
                tenFile = tenFile.substring(32);
            }
            if (requestCode == LOI_LAP) {
                Bundle packageFromCaller = data.getBundleExtra("GoiTen");
                tenFileLoiLap = packageFromCaller.getString("photoFileName");
                mButtonLap.setBackground(getResources().getDrawable(android.R.color.holo_blue_light));
                loilap1 = true;
            }
            if (requestCode == THU_HOI) {
                Bundle packageFromCaller = data.getBundleExtra("GoiTen");
                tenFileThuHoi = packageFromCaller.getString("photoFileName");
                thuhoiOk();

            }
            if (requestCode == IMAGE_TAKE) {
                Bundle packageFromCaller = data.getBundleExtra("GoiTen");
                tenFile1 = packageFromCaller.getString("photoFileName");

                Log.e(TAG, "onActivityResult: " + tenFile1);
                Intent Myintent = new Intent(MainActivity.this, EditImageAfterCaptureActivity.class);
                Bundle ten_image = new Bundle();
                ten_image.putString("tenfile1", tenFile1);
                Myintent.putExtra("GoiTen1", ten_image);
                startActivityForResult(Myintent, IMAGE_EDIT);
            }

            if (requestCode == TAKE_FOTO) {
                uriPhoto = Utils.getPhotoFileUri(this, photoFileName);
                if (uriPhoto != null) {
                    imgPath = uriPhoto.getPath();
                }
                Log.e(TAG, "onActivityResult: " + imgPath);

                Intent intent = new Intent(MainActivity.this, EditImageAfterCaptureActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString(PHOTO_FILE_NAME, imgPath);
                intent.putExtra(PHOTO_FILE_NAME, bundle);
                startActivityForResult(intent, IMAGE_EDIT);
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
            String msgrec = null;

            try {
                OutputStream outputStream = null;
                clientSocket = new Socket(dstAddress, dstPort);
                outputStream = clientSocket.getOutputStream();
                final BufferedReader in1 = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                final PrintStream printStream = new PrintStream(outputStream);
                response = "Connected";
                publishProgress(response);
                while ((msgrec = in1.readLine()) != null) {
                    publishProgress(msgrec);
                    Log.d("Database operations", "String" + msgrec);
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
                dialog_guilaianh.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog_guilaianh.setContentView(R.layout.loiguilai);
                dialog_guilaianh.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
                dialog_guilaianh.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
               // dialog_guilaianh.setTitle("ẢNH ĐÃ BỊ TỪ CHỐI BỞI TRIM A1");
                Button coGuiLai = (Button) dialog_guilaianh.findViewById(R.id.btncoguilai);
                Button khongGuilai = (Button) dialog_guilaianh.findViewById(R.id.btnkhongguilai);
                coGuiLai.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //       r.stop(
                        if (tenFile != "")
                            tenFileMoi = tenFile;
                        tenFile = update[0].substring(6, 25);
                        Bitmap myBitmap = BitmapFactory.decodeFile("/storage/emulated/0/DCIM/Camera/" + tenFile);
                        mImageViewLogo.setImageBitmap(myBitmap);
                        Kichhoat();
                        mButtonGui.setText("gửi lại");
                        Index_gui = 3;
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






