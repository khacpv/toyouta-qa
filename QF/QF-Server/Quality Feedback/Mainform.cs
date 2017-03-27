using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Reflection;
using System.Threading;
using System.IO;
using System.Configuration;
using System.Net;
using System.Net.Sockets;
using System.Windows.Forms;
using System.Windows;
using mshtml;
using ZedGraph;
using System.Collections;
using System.Media;

namespace Quality_Feedback
{
    public partial class Mainform : Form
    {
        public Mainform()
        {
            InitializeComponent(); IPAddress[] localIP = Dns.GetHostAddresses(Dns.GetHostName());
            foreach (IPAddress address in localIP)
            {
                if (address.AddressFamily == AddressFamily.InterNetwork)
                {
                    lbIPServer.Text = address.ToString();
                    lbPortServer.Text = "8888";
                }
            }

            listener = new TcpListener(IPAddress.Parse(lbIPServer.Text), int.Parse(lbPortServer.Text));
            listener.Start();

            bgwConnect.RunWorkerAsync();
            bgwConnect.WorkerSupportsCancellation = true;

            bgwSend.WorkerSupportsCancellation = true;
            bgwRec.WorkerSupportsCancellation = true;

        }

        private TcpClient[] client = new TcpClient[1024];
        private TcpListener listener;
        public StreamReader[] STR = new StreamReader[1024];
        public StreamWriter[] STW = new StreamWriter[1024];
        public Socket[] sck = new Socket[1024];
        static ASCIIEncoding encoding = new ASCIIEncoding();

        public string data_buff;
        private string FileName;
        private string PictureName;
        bool select_picture = true;
        string[] data_split;
        string Error_repeat;
        bool Repeat_Error = false;
        SoundPlayer player;

        String[] Stage = { "T1.1", "T1.2", "T2.1", "T2.2", "T3.1", "T3.2", "T4.1", "T4.2", "T5.1", "T5.2" };
        String Stage2 = "-------------";
        String[] Shift = {"Ca R", "Ca Y" };
        bool delete_file = false;
        bool search = false;
        bool search_queue = false;
        bool resend = false;
        string Msg_Send;
        int i2, i = 0;

        private void Mainform_Load(object sender, EventArgs e)
        {
            i2 = 0;
            dgrShift.ColumnCount = 11;
            dgrShift.Rows.Add("", "T1.1", "T1.2", "T2.1", "T2.2", "T3.1", "T3.2", "T4.1", "T4.2", "T5.1", "T5.2");
            int count = int.Parse(lblErrTotal.Text.ToString());
            int count2 = Stage.Length * 2 + 1;
            int dem = count;
            while (i2 < count2)         // tao cot
            {
                dgrGraph.ColumnCount = count2;
                dgrGraph.Columns[i2].Width = dgrGraph.Width / (count2);
                if (i2 < 11) dgrShift.Columns[i2].Width = dgrGraph.Width / (count2/2) - 6;
                dgrGraph.Columns[i2].Name = "";
                i2++;
            }
            dgrShift.Columns[0].Width = dgrGraph.Width / (count2);
            while (i <= count)      // tao hang
            {
                dgrGraph.Rows.Add(dem.ToString(), "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "");
                dgrGraph.Rows[i].Height = dgrGraph.Height / (count + 1);
                i++;
                dem--;
                dgrGraph.ClearSelection();
                dgrShift.ClearSelection();
            }
            dgrGraph.Rows[0].Cells[1].Selected = true;
            dgrGraph.Rows[0].Cells[1].Selected = false;
            dgrShift.Rows[0].Cells[1].Selected = true;
            dgrShift.Rows[0].Cells[1].Selected = false;
            i = 1;
            while (i < dgrGraph.ColumnCount)
            {
                if (i % 2 == 0)
                {
                    dgrGraph.Rows[count].Cells[i].Value = Shift[1];
                    dgrGraph.Rows[count].Cells[i].Style.BackColor = Color.Yellow;
                }
                else
                {
                    dgrGraph.Rows[count].Cells[i].Value = Shift[0];
                    dgrGraph.Rows[count].Cells[i].Style.BackColor = Color.Red;
                }
                i++;
            }
            //dgrGraph.Columns[i].Visible = false;
            
            count = 5;
            //  dgrGraph.Rows[count].Height = 10;
            for (int jk = 1; jk < dgrGraph.ColumnCount; jk++)
            {
                dgrGraph.Rows[count].Cells[jk].Style.ForeColor = Color.Red;
                dgrGraph.Rows[count].Cells[jk].Style.Alignment = DataGridViewContentAlignment.MiddleCenter;
                dgrGraph.Rows[count].Cells[jk].Value = Stage2;
            }

            dgrData.Columns[2].Visible = false;
            dgrData.Columns[7].Visible = false;
            dgrData.Columns[8].Visible = false;
            dgrData.Columns[9].Visible = false;
            dgrData.Columns[10].Visible = false;
            dgrData.Columns[11].Visible = false;
            dgrData.Columns[12].Visible = false;
            dgrData.Columns[13].Visible = false;
            dgrData.Columns[14].Visible = false;
            dgrData.Columns[15].Visible = false;
        }

        bool processing = false;

        #region Network Connect
        private void bgwConnect_DoWork(object sender, DoWorkEventArgs e)
        {
            try
            {
                while (true)
                {
                    if (!listener.Pending())
                    {
                        Thread.Sleep(200);
                        continue;
                    }
                    client[0] = listener.AcceptTcpClient();
                    sck[0] = client[0].Client;

                    this.lblClientAddr.Invoke(new MethodInvoker(delegate() { lblClientAddr.Text = ((IPEndPoint)sck[0].RemoteEndPoint).Address.ToString(); }));
                    this.lblClientPort.Invoke(new MethodInvoker(delegate() { lblClientPort.Text = ((IPEndPoint)sck[0].RemoteEndPoint).Port.ToString(); }));

                    STR[0] = new StreamReader(client[0].GetStream());
                    STW[0] = new StreamWriter(client[0].GetStream());
                    STW[0].AutoFlush = true;
                    bgwRec.RunWorkerAsync();
                }
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.ToString());
            }
        }
        #endregion

        #region Receive Message
        private void bgwRec_DoWork(object sender, DoWorkEventArgs e)
        {
            this.lblClientAddr.Invoke(new MethodInvoker(delegate() { lblClientAddr.Text = ((IPEndPoint)sck[0].RemoteEndPoint).Address.ToString(); }));
            this.lblClientPort.Invoke(new MethodInvoker(delegate() { lblClientPort.Text = ((IPEndPoint)sck[0].RemoteEndPoint).Port.ToString(); }));
            while (true)
            {
                try
                {
                    byte[] data_C1 = new byte[1024];
                    int recv_C1 = sck[0].Receive(data_C1);
                    data_buff = Encoding.ASCII.GetString(data_C1, 0, recv_C1);
                    this.txbRecevice.Invoke(new MethodInvoker(delegate() { txbRecevice.Text = data_buff; }));
                    this.txbLink.Invoke(new MethodInvoker(delegate()
                        #region Split Data
                    {

                        data_split = data_buff.Split('-');
                        {
                            if (data_split[0] != "" && data_split.Count() == 1)
                            {
                                Message msg = new Message();
                                msg.recmsg = data_buff;
                                msg.ipserver = lblClientAddr.Text;
                                msg.port = lbPortServer.Text;
                                msg.ShowDialog();
                                if (msg.sendok == "OK")
                                {
                                    Msg_Send = msg.sendmsg;
                                    if (Msg_Send != "" && Msg_Send != null) bgwSend.RunWorkerAsync();
                                }
                            }
                            else
                            {
                                if (data_split[0] == "") bgwRec.WorkerSupportsCancellation = false;
                                else
                                {
                                    if (data_split.Count() > 1 && data_split.Count() <= 4)      // Anh binh thuong
                                    {
                                        txbLink.Text = "http://" + lblClientAddr.Text + ":1234/storage/emulated/0/DCIM/Camera/" + data_split[0];
                                        //txbLink.Text = "http://192.168.1.105:1234/storage/emulated/0/DCIM/Camera/" + data_split[0];
                                        if (data_split[0] == "DEL")
                                        {
                                            Reclaim(data_split[1]);     // Thu hoi anh
                                        }
                                        /////////////////////////////////////////////////////////////////////////////////////////////
                                        else
                                        {
                                            delete_file = false;
                                            search = search_queue = false;
                                            if (dgrQueue.RowCount == 0 && select_picture == true)
                                            {
                                                select_picture = false;                 // Da chon anh dau tien
                                                webBrowser1.Navigate(txbLink.Text.ToString());
                                                lblShift.Text = data_split[3];
                                                timer1.Start();
                                                timer2.Start();
                                            }
                                        }
                                    }
                                    else
                                    {
                                        if (data_split.Count() >= 5)
                                        {
                                            search = search_queue = false;
                                            txbLink.Text = "http://" + lblClientAddr.Text + ":1234/storage/emulated/0/DCIM/Camera/" + data_split[0];
                                            //txbLink.Text = "http://192.168.1.105:1234/storage/emulated/0/DCIM/Camera/" + data_split[0];
                                            if (data_split[4] != "reSend")
                                            {
                                                Repeat_Error = true;
                                                Error_repeat = data_split[3];
                                            }
                                            else resend = true;
                                            if (dgrQueue.RowCount == 0 && select_picture == true)
                                            {
                                                select_picture = false;                 // Da chon anh dau tien
                                                timer1.Start();
                                                webBrowser1.Navigate(txbLink.Text.ToString());
                                                timer2.Start();
                                                lblShift.Text = data_split[3];
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                    ));

                    if (data_split.Count() > 3)
                    {
                        this.dgrData.Invoke(new MethodInvoker(delegate()
                        {
                            int stt = dgrData.RowCount;
                            int stt2 = dgrData.RowCount;
                            if (stt >= 1)
                            {
                                string row_stt = dgrData.Rows[stt - 1].Cells[0].Value.ToString();
                                stt2 = int.Parse(row_stt);
                            }
                            string datetime = DateTime.Now.ToString();
                            dgrData.Rows.Add(stt2 + 1, datetime, "", data_split[1], data_split[2], "");
                        }));
                    }
                    #endregion
                }
                catch (Exception ex)
                {
                    MessageBox.Show(ex.ToString());
                }
            }
        }
        #endregion

        #region Send Message
        private void bgwSend_DoWork(object sender, DoWorkEventArgs e)
        {
            if (sck[0].Connected)
            {
                sck[0].Send(encoding.GetBytes(Msg_Send + "-" + "\n"));
            }
            bgwSend.CancelAsync();
        }
        #endregion

        int count_pic = 0;
        int Count_err = 0;
        int Count_err2 = 0;
        string num = "";
        int number = 0;
        int number_queue = 0;
        int num_errorcode;      // so thu tu chua ma loi

        private void Reclaim(string picture)            // thu hoi anh
        {
            // Xem do thi da ve chua
            int row = Num_Return(picture);        // tra ve hang co thong tin cua anh
            string confirm = dgrData.Rows[row].Cells[6].Value.ToString();
            string Rows = dgrData.Rows[row].Cells[10].Value.ToString();
            if (Rows != "")
            {
                dgrData.Rows[row].Cells[6].Value = "Thu hồi";
                dgrData.Rows[row].Cells[7].Value = "";
                dgrData.Rows[row].Cells[8].Value = "";
                dgrData.Rows[row].Cells[9].Value = "";
                string[] located = dgrData.Rows[row].Cells[4].Value.ToString().Split('-');
                DataGrid_paint(Num_Return(picture));
                Color_ReFill();
            }
            else
            {
                if (dgrQueue.RowCount == 0 || (Num_Queue_Return(picture) >= 0 && search_queue == false))        // tim kiem trong hang cho
                {
                    if (processing == false) delete_file = true;            // neu dang xu ly
                    if (dgrQueue.RowCount == 0 || row == 0) pictureBox1.Image = null;
                    select_picture = true;
                    processing = false;         // bien de add vao hang cho
                    panel1.BackColor = Color.FromArgb(192, 192, 255);
                    timer1.Stop();
                    timer2.Stop();
                }
                if (dgrData.RowCount >= 1 && search == true) dgrData.Rows.RemoveAt(Num_Return(picture));        // remove ten anh
                if (dgrQueue.RowCount >= 1 && search_queue == true) dgrQueue.Rows.RemoveAt(Num_Queue_Return(picture));    // remove ten anh trong hang cho
            }
        }
        private string Num_Error(string Name)
        {
            for (int j = 0; j < dgrData.Rows.Count; j++)
            {
                string picname = dgrData.Rows[j].Cells[7].Value.ToString();
                if (String.Compare(picname, Name) == 0)
                {
                    num = dgrData.Rows[j].Cells[0].Value.ToString();
                    num_errorcode = j;
                    return num;
                }
            }
            return num;
        }

        private int Num_Queue_Return(string Name)
        {
            for (int j = 0; j < dgrQueue.Rows.Count; j++)
            {
                string picname = dgrQueue.Rows[j].Cells[1].Value.ToString();
                if (String.Compare(picname, Name) == 0)
                {
                    //num = dgrData.Rows[j].Cells[6].Value.ToString();
                    search = true;
                    number_queue = j;
                    return number_queue;
                }
            }
            return number_queue;
        }
        private int Num_Return(string Name)
        {
            for (int j = 0; j < dgrData.Rows.Count; j++)
            {
                string picname = dgrData.Rows[j].Cells[7].Value.ToString();
                if (String.Compare(picname, Name) == 0)
                {
                    //num = dgrData.Rows[j].Cells[6].Value.ToString();
                    search = true;
                    number = j;
                    return number;
                }
            }
            return number;
        }

        private void DataGrid_paint(int row)
        {
            int i = 0;
            while (i < dgrData.ColumnCount)
            {
                if (data_split[0] == "DEL") dgrData.Rows[row].Cells[i].Style.BackColor = Color.Yellow;
                /*
                else
                {
                    if (clear == false) dgrData.Rows[row].Cells[i].Style.BackColor = Color.Cyan;
                    else dgrData.Rows[row].Cells[i].Style.BackColor = Color.White;
                }
                 */
                i++;
            }
        }
        int numpicture = 0;
        private int Num_Picture(string name)
        {
            for (int jk = 0; jk < dgrData.RowCount; jk++)
            {
                string picname = dgrData.Rows[jk].Cells[7].Value.ToString();
                if (String.Compare(name, picname) == 0)
                {
                    numpicture = jk;
                    return numpicture;
                }
            }
            return numpicture;
        }

        private void Color_ReFill()
        {
            Count_err = 0;
            Count_err2 = 0;
            for (int i = 0; i < dgrGraph.RowCount - 1; i++)
            {
                for (int jk = 1; jk < dgrGraph.ColumnCount; jk++)
                {
                    dgrGraph.Rows[i].Cells[jk].Value = "";
                    dgrGraph.Rows[i].Cells[jk].Style.BackColor = Color.White;
                }
            }

            for (int i = 0; i < dgrData.RowCount; i++)
            {
                string confirm = dgrData.Rows[i].Cells[6].Value.ToString();
                string cur_shift = dgrData.Rows[i].Cells[16].Value.ToString();
                if (confirm == "OK")
                {
                    string[] located = dgrData.Rows[i].Cells[4].Value.ToString().Split('-');            
                    string err = dgrData.Rows[i].Cells[3].Value.ToString();
                    int cot = Cell_return(located[1]);
                    int hang = Check_Total(cot);
                    //Fill_Color(err, int.Parse(lblErrTotal.Text) - hang, cot, true, false);
                    Fill_Color(err, int.Parse(lblErrTotal.Text) - hang, cot, cur_shift, false);
                    if (cur_shift == "Ca R") Count_err++;
                    else if (cur_shift == "Ca Y") Count_err2++;
                }
            }
            lblErr.Text = Count_err.ToString();     // hien thi tong so loi thuc te
            lblErrY.Text = Count_err2.ToString();     // hien thi tong so loi thuc te

            //  dgrGraph.Rows[count].Height = 10;
            for (int jk = 1; jk < dgrGraph.ColumnCount; jk++)
            {
                dgrGraph.Rows[5].Cells[jk].Style.ForeColor = Color.Red;
                dgrGraph.Rows[5].Cells[jk].Value = Stage2;
            }
        }

        int total_err = 0;
        private int Check_Total(int location)
        {
            total_err = 0;
            for (int jk = dgrGraph.RowCount - 1; jk > 1; jk--)
            {
                if (dgrGraph.Rows[jk].Cells[cell].Value.ToString() != "" && dgrGraph.Rows[jk].Cells[cell].Value.ToString() != Stage2) total_err++;
            }
            /*
            for (int jk = dgrGraph.RowCount - 2; jk > 1; jk--)
            {
                if (dgrGraph.Rows[jk].Cells[cell + 1].Value.ToString() != "")   total_err++;
            }
             */
            return total_err;
        }

        private int Cell_return(String Location)
        {
            switch (Location)
            {
                case "T1.1":
                    cell = 1;
                    break;
                case "T1.2":
                    cell = 3;
                    break;
                case "T2.1":
                    cell = 5;
                    break;
                case "T2.2":
                    cell = 7;
                    break;
                case "T3.1":
                    cell = 9;
                    break;
                case "T3.2":
                    cell = 11;
                    break;
                case "T4.1":
                    cell = 13;
                    break;
                case "T4.2":
                    cell = 15;
                    break;
                case "T5.1":
                    cell = 17;
                    break;
                case "T5.2":
                    cell = 19;
                    break;
                case "T6.1":
                    cell = 21;
                    break;
                case "T6.2":
                    cell = 23;
                    break;
                default:
                    MessageBox.Show("Kiểm tra lại thông tin trạm. Không có trong CSDL.", "Thông báo");
                    break;
            }

            return cell;
        }

        int cell = 0;
        private void Fill_Color(String Err, int row, int col, string current_shift, bool modify)
        {
             //state = true: ca R
             //state = false: ca V

            if (current_shift == "Ca Y") col += 1;

            if (modify == false)
            {
                dgrData.Rows[count_pic].Cells[10].Value = row;     // Ten hang
                dgrData.Rows[count_pic].Cells[11].Value = col;     // Ten cot
            }

            dgrGraph.Rows[row].Cells[col].Value = Err;
            switch (Err)
            {
                case "AB":
                    dgrGraph.Rows[row].Cells[col].Style.BackColor = Color.ForestGreen;
                    break;
                case "B_":
                    dgrGraph.Rows[row].Cells[col].Style.BackColor = Color.Goldenrod;
                    break;
                case "BR":
                    dgrGraph.Rows[row].Cells[col].Style.BackColor = Color.Aqua;
                    break;
                case "CF":
                    dgrGraph.Rows[row].Cells[col].Style.BackColor = Color.Aquamarine;
                    break;
                case "CR":
                    dgrGraph.Rows[row].Cells[col].Style.BackColor = Color.Violet;
                    break;
                case "D":
                    dgrGraph.Rows[row].Cells[col].Style.BackColor = Color.Beige;
                    break;
                case "D_":
                    dgrGraph.Rows[row].Cells[col].Style.BackColor = Color.DarkViolet;
                    break;
                case "DI":
                    dgrGraph.Rows[row].Cells[col].Style.BackColor = Color.Blue;
                    break;
                case "F":
                    dgrGraph.Rows[row].Cells[col].Style.BackColor = Color.BlueViolet;
                    break;
                case "F_":
                    dgrGraph.Rows[row].Cells[col].Style.BackColor = Color.Brown;
                    break;
                case "G":
                    dgrGraph.Rows[row].Cells[col].Style.BackColor = Color.BurlyWood;
                    break;
                case "H":
                    dgrGraph.Rows[row].Cells[col].Style.BackColor = Color.CadetBlue;
                    break;
                case "L":
                    dgrGraph.Rows[row].Cells[col].Style.BackColor = Color.Green;
                    break;
                case "M":
                    dgrGraph.Rows[row].Cells[col].Style.BackColor = Color.Chocolate;
                    break;
                case "OL":
                    dgrGraph.Rows[row].Cells[col].Style.BackColor = Color.Red;
                    break;
                case "OR":
                    dgrGraph.Rows[row].Cells[col].Style.BackColor = Color.CornflowerBlue;
                    break;
                case "OT":
                    dgrGraph.Rows[row].Cells[col].Style.BackColor = Color.DeepPink;
                    break;
                case "OV":
                    dgrGraph.Rows[row].Cells[col].Style.BackColor = Color.SeaGreen;
                    break;
                case "PC":
                    dgrGraph.Rows[row].Cells[col].Style.BackColor = Color.Cyan;
                    break;
                case "PD":
                    dgrGraph.Rows[row].Cells[col].Style.BackColor = Color.DarkBlue;
                    break;
                case "PP":
                    dgrGraph.Rows[row].Cells[col].Style.BackColor = Color.DarkCyan;
                    break;
                case "PS":
                    dgrGraph.Rows[row].Cells[col].Style.BackColor = Color.SkyBlue;
                    break;
                case "R":
                    dgrGraph.Rows[row].Cells[col].Style.BackColor = Color.DarkGray;
                    break;
                case "S_":
                    dgrGraph.Rows[row].Cells[col].Style.BackColor = Color.DarkGreen;
                    break;
                case "SA":
                    dgrGraph.Rows[row].Cells[col].Style.BackColor = Color.Yellow;
                    break;
                case "SC":
                    dgrGraph.Rows[row].Cells[col].Style.BackColor = Color.DarkMagenta;
                    break;
                case "SG":
                    dgrGraph.Rows[row].Cells[col].Style.BackColor = Color.Pink;
                    break;
                case "T_":
                    dgrGraph.Rows[row].Cells[col].Style.BackColor = Color.PaleGreen;
                    break;
                case "WA":
                    dgrGraph.Rows[row].Cells[col].Style.BackColor = Color.PaleTurquoise;
                    break;
                case "SP":
                    dgrGraph.Rows[row].Cells[col].Style.BackColor = Color.PaleVioletRed;
                    break;
                case "SR":
                    dgrGraph.Rows[row].Cells[col].Style.BackColor = Color.PapayaWhip;
                    break;
                case "TC":
                    dgrGraph.Rows[row].Cells[col].Style.BackColor = Color.Peru;
                    break;
                case "U":
                    dgrGraph.Rows[row].Cells[col].Style.BackColor = Color.PowderBlue;
                    break;
                case "W_":
                    dgrGraph.Rows[row].Cells[col].Style.BackColor = Color.RoyalBlue;
                    break;
                case "WP":
                    dgrGraph.Rows[row].Cells[col].Style.BackColor = Color.SaddleBrown;
                    break;
                default:
                    MessageBox.Show("Kiểm tra lại thông tin trạm. Không có trong CSDL.", "Thông báo");
                    break;
            }
        }

        private void bgwQueue_DoWork(object sender, DoWorkEventArgs e)
        {
            this.dgrQueue.Invoke(new MethodInvoker(delegate()
            {
                if (dgrQueue.RowCount >= 1)
                {
                    if (delete_file == true) delete_file = false;
                    webBrowser1.Navigate(dgrQueue.Rows[0].Cells[2].Value.ToString());
                    string cur_shift = dgrQueue.Rows[0].Cells[3].Value.ToString(); // lay ca lam viec trong hang cho
                    lblShift.Text = cur_shift;
                    select_picture = false;
                    processing = false;
                    dgrQueue.Rows.RemoveAt(0);
                }
                else
                {
                    if (dgrQueue.RowCount == 0)
                    {
                        panel4.BackColor = Color.White;
                        panel3.BackColor = Color.White;
                    }
                }
            }));
        }

        private void timer1_Tick(object sender, EventArgs e)
        {
            if (panel1.BackColor == Color.Red) panel1.BackColor = Color.FromArgb(192, 192, 255);
            else panel1.BackColor = Color.Red;
            if (processing == false) processing = true;
        }

        private void timer2_Tick(object sender, EventArgs e)
        {
            player = new SoundPlayer("Finish.wav");
            player.Play();
        }

        private void webBrowser1_DocumentCompleted(object sender, WebBrowserDocumentCompletedEventArgs e)
        {
            double origSizeWidth, origSizeHeight;
            origSizeWidth = webBrowser1.Size.Width; //get the original size of browser 
            origSizeHeight = webBrowser1.Size.Height;

            try
            {
                HtmlElement pic = webBrowser1.Document.Images[0]; //get your image
                double origPicWidth, origPicHeight;
                origPicWidth = double.Parse(pic.GetAttribute("WIDTH")); //save the image width
                origPicHeight = double.Parse(pic.GetAttribute("HEIGHT"));//and height

                double tempW, tempY, widthScale, heightScale;
                double scale = 0;

                if (origPicWidth > origSizeWidth)
                {
                    widthScale = origSizeWidth / origPicWidth; //find out the scale factor for the width
                    heightScale = origSizeHeight / origPicHeight; //scale factor for height
                    scale = Math.Min(widthScale, heightScale);//determine which scale to use from the smallest
                    tempW = origPicWidth * scale; //multiply picture original width by the scale
                    tempY = origPicHeight * scale; // multiply original picture height by the scale
                    pic.SetAttribute("WIDTH", tempW.ToString()); // set your attributes
                    pic.SetAttribute("HEIGHT", tempY.ToString());
                }

                // Add reference MSHTML
                IHTMLDocument2 doc = (IHTMLDocument2)webBrowser1.Document.DomDocument;
                IHTMLControlRange imgRange = (IHTMLControlRange)((HTMLBody)doc.body).createControlRange();

                foreach (IHTMLImgElement img in doc.images)
                {
                    imgRange.add((IHTMLControlElement)img);

                    imgRange.execCommand("Copy", false, null);

                    using (Bitmap bmp = (Bitmap)Clipboard.GetDataObject().GetData(DataFormats.Bitmap))
                    {
                        if (delete_file == false)
                        {
                            if (resend == true)
                            {
                                resend = false;
                                pictureBox1.Image = Image.FromFile(FileName);
                                dgrData.Rows[Num_Picture(PictureName)].Cells[6].Value = "Process";
                            }
                            bmp.Save(@"D:\Pictures\" + img.nameProp);
                            PictureName = img.nameProp;
                            FileName = @"D:\Pictures\" + img.nameProp.ToString();
                            pictureBox1.Visible = true;
                            pictureBox1.Image = Image.FromFile(FileName);
                            pictureBox1.Image.Save(FileName);
                        }
                        else
                        {
                            pictureBox1.Image = Image.FromFile(FileName);
                            dgrData.Rows[Num_Picture(PictureName)].Cells[6].Value = "Process";
                        }
                    }
                    if (dgrData.RowCount >= 1)
                    {
                        timer2.Start();
                        timer1.Start();
                    }
                }
            }
            catch
            {
                if (dgrData.RowCount >= 1)
                {
                    timer2.Start();
                    timer1.Start();
                    dgrData.Rows[Num_Picture(PictureName)].Cells[6].Value = "Process";
                }
            }
        }

        private void webBrowser2_DocumentCompleted(object sender, WebBrowserDocumentCompletedEventArgs e)
        {
            double origSizeWidth, origSizeHeight;
            origSizeWidth = webBrowser2.Size.Width; //get the original size of browser 
            origSizeHeight = webBrowser2.Size.Height;

            try
            {
                HtmlElement pic = webBrowser2.Document.Images[0]; //get your image
                double origPicWidth, origPicHeight;
                origPicWidth = double.Parse(pic.GetAttribute("WIDTH")); //save the image width
                origPicHeight = double.Parse(pic.GetAttribute("HEIGHT"));//and height

                double tempW, tempY, widthScale, heightScale;
                double scale = 0;

                if (origPicWidth > origSizeWidth)
                {
                    widthScale = origSizeWidth / origPicWidth; //find out the scale factor for the width
                    heightScale = origSizeHeight / origPicHeight; //scale factor for height
                    scale = Math.Min(widthScale, heightScale);//determine which scale to use from the smallest
                    tempW = origPicWidth * scale; //multiply picture original width by the scale
                    tempY = origPicHeight * scale; // multiply original picture height by the scale
                    pic.SetAttribute("WIDTH", tempW.ToString()); // set your attributes
                    pic.SetAttribute("HEIGHT", tempY.ToString());
                }
            }
            catch { }
        }

        private void webBrowser4_DocumentCompleted(object sender, WebBrowserDocumentCompletedEventArgs e)
        {
            double origSizeWidth, origSizeHeight;
            origSizeWidth = webBrowser4.Size.Width; //get the original size of browser 
            origSizeHeight = webBrowser4.Size.Height;

            try
            {
                HtmlElement pic = webBrowser4.Document.Images[0]; //get your image
                double origPicWidth, origPicHeight;
                origPicWidth = double.Parse(pic.GetAttribute("WIDTH")); //save the image width
                origPicHeight = double.Parse(pic.GetAttribute("HEIGHT"));//and height

                double tempW, tempY, widthScale, heightScale;
                double scale = 0;

                if (origPicWidth > origSizeWidth)
                {
                    widthScale = origSizeWidth / origPicWidth; //find out the scale factor for the width
                    heightScale = origSizeHeight / origPicHeight; //scale factor for height
                    scale = Math.Min(widthScale, heightScale);//determine which scale to use from the smallest
                    tempW = origPicWidth * scale; //multiply picture original width by the scale
                    tempY = origPicHeight * scale; // multiply original picture height by the scale
                    pic.SetAttribute("WIDTH", tempW.ToString()); // set your attributes
                    pic.SetAttribute("HEIGHT", tempY.ToString());
                }

            }
            catch { }
        }

        private void webBrowser5_DocumentCompleted(object sender, WebBrowserDocumentCompletedEventArgs e)
        {
            double origSizeWidth, origSizeHeight;
            origSizeWidth = webBrowser5.Size.Width; //get the original size of browser 
            origSizeHeight = webBrowser5.Size.Height;

            try
            {
                HtmlElement pic = webBrowser5.Document.Images[0]; //get your image
                double origPicWidth, origPicHeight;
                origPicWidth = double.Parse(pic.GetAttribute("WIDTH")); //save the image width
                origPicHeight = double.Parse(pic.GetAttribute("HEIGHT"));//and height

                double tempW, tempY, widthScale, heightScale;
                double scale = 0;

                if (origPicWidth > origSizeWidth)
                {
                    widthScale = origSizeWidth / origPicWidth; //find out the scale factor for the width
                    heightScale = origSizeHeight / origPicHeight; //scale factor for height
                    scale = Math.Min(widthScale, heightScale);//determine which scale to use from the smallest
                    tempW = origPicWidth * scale; //multiply picture original width by the scale
                    tempY = origPicHeight * scale; // multiply original picture height by the scale
                    pic.SetAttribute("WIDTH", tempW.ToString()); // set your attributes
                    pic.SetAttribute("HEIGHT", tempY.ToString());
                }
            }
            catch { }
        }

        private void lblIPClient_Click(object sender, EventArgs e)
        {
            Message msg = new Message();
            msg.port = lbPortServer.Text;      // lay thong tin port server
            msg.ipserver = lblClientAddr.Text;  // lay thong tin ip server
            msg.ShowDialog();//0985444993
            if (msg.sendok == "OK")
            {
                Msg_Send = msg.sendmsg;
                if (Msg_Send != "" && Msg_Send != null) bgwSend.RunWorkerAsync();
            }
        }

        private void dgrData_CellClick(object sender, DataGridViewCellEventArgs e)
        {
            if (e.RowIndex >= 0)
            {
                string[] located = dgrData.Rows[e.RowIndex].Cells[4].Value.ToString().Split('-');
                if (located.Length >= 2 && dgrData.Rows[e.RowIndex].Cells[6].Value.ToString() != "Thu hồi")
                {
                    Details_Information details = new Details_Information();
                    details.State = "Details";          // hien thi thong tin chi tiet
                    string errCode = dgrData.Rows[e.RowIndex].Cells[3].Value.ToString();        // lay ma loi
                    string rpt = dgrData.Rows[e.RowIndex].Cells[5].Value.ToString();            // lap lai hay khong
                    string name = dgrData.Rows[e.RowIndex].Cells[7].Value.ToString();           // ten anh
                    details.Location = located[1];
                    details.repeat = dgrData.Rows[count_pic].Cells[5].Value.ToString();    // lay so anh bi loi
                    details.error = errCode;        // lay ma loi
                    details.GetLink_Repeat = dgrData.Rows[e.RowIndex].Cells[9].Value.ToString();     // lay ten anh 
                    details.GetLink = @"D:\Pictures\" + name;
                    details.ShowDialog();
                    if (details.SetOK == "NG")
                    {
                        Msg_Send = "check-" + name;
                        Reclaim(name);
                        bgwSend.RunWorkerAsync();
                    }
                }
            }
        }

        private void dgrData_RowsAdded(object sender, DataGridViewRowsAddedEventArgs e)
        {
            string Link_pictrue = "http://" + lblClientAddr.Text + ":1234/storage/emulated/0/DCIM/Camera/";
            int stt_queue = 0;
            dgrQueue.Rows.Clear();
            int stt = dgrData.RowCount;
            stt -= 1;
            dgrData.Rows[stt].Cells[0].Style.BackColor = Color.White;
            dgrData.Rows[stt].Cells[6].Value = "";
            dgrData.Rows[stt].Cells[9].Value = "";
            dgrData.Rows[stt].Cells[10].Value = "";
            dgrData.Rows[stt].Cells[11].Value = "";
            dgrData.Rows[stt].Cells[12].Value = "";
            dgrData.Rows[stt].Cells[7].Value = data_split[0];
            dgrData.Rows[stt].Cells[16].Value = data_split[3];
            //dgrData.Rows[stt].Cells[16].Value = "Ca R";
            if (Repeat_Error == true)
            {
                Repeat_Error = false;
                dgrData.Rows[stt].Cells[7].Value = data_split[0];
                dgrData.Rows[stt].Cells[9].Value = Error_repeat;
                dgrData.Rows[stt].Cells[5].Value = Num_Error(Error_repeat);
                dgrData.Rows[stt].Cells[3].Value = dgrData.Rows[num_errorcode].Cells[3].Value.ToString();
            }
            if (dgrData.RowCount > 1)
            {
                for (int jk = 0; jk < dgrData.RowCount; jk++)
                {
                    string confirm = dgrData.Rows[jk].Cells[6].Value.ToString();
                    string namepic = dgrData.Rows[jk].Cells[7].Value.ToString();        // lay ten anh
                    string cur_shift = dgrData.Rows[jk].Cells[16].Value.ToString();        // lay ten anh
                    if (confirm == "" && processing == true)
                    {
                        stt_queue++;
                        dgrQueue.Rows.Add(stt_queue, namepic, Link_pictrue + namepic, cur_shift);
                    }
                }
            }
        }

        private void dgrData_RowsRemoved(object sender, DataGridViewRowsRemovedEventArgs e)
        {
            if (dgrQueue.RowCount >= 1) bgwQueue.RunWorkerAsync();
        }

        private void dgrQueue_RowsAdded(object sender, DataGridViewRowsAddedEventArgs e)
        {
            if (dgrQueue.RowCount == 0)
            {
                webBrowser2.Visible = false;
                webBrowser4.Visible = false;
                webBrowser5.Visible = false;
                processing = true;
            }
            if (dgrQueue.RowCount == 1)
            {
                webBrowser2.Visible = true;
                webBrowser4.Visible = false;
                webBrowser5.Visible = false;
                webBrowser2.Navigate(dgrQueue.Rows[0].Cells[2].Value.ToString());
            }
            if (dgrQueue.RowCount == 2)
            {
                webBrowser4.Visible = true;
                webBrowser5.Visible = false;
                webBrowser4.Navigate(dgrQueue.Rows[1].Cells[2].Value.ToString());
            }
            if (dgrQueue.RowCount == 3)
            {
                webBrowser5.Visible = true;
                webBrowser5.Navigate(dgrQueue.Rows[2].Cells[2].Value.ToString());
            }
        }

        private void dgrQueue_RowsRemoved(object sender, DataGridViewRowsRemovedEventArgs e)
        {
            dgrQueue_RowsAdded(this, null);
        }

        private void pictureBox1_Click(object sender, EventArgs e)
        {
            if (dgrData.RowCount >= 1)
            {
                timer1.Stop();
                timer2.Stop();
                player.Stop();
                if (dgrQueue.RowCount == 0 && processing == true) processing = false;
                panel1.BackColor = Color.FromArgb(192, 192, 255);
                select_picture = true;
                count_pic = Num_Picture(PictureName);
                string confirm = dgrData.Rows[count_pic].Cells[6].Value.ToString();
                if (confirm != "OK")
                {
                    Details_Information details = new Details_Information();
                    details.State = "";
                    details.repeat = "";

                    string shift = dgrData.Rows[count_pic].Cells[16].Value.ToString();      // lay ca lam viec
                    details.error = dgrData.Rows[count_pic].Cells[3].Value.ToString();      // lay ma loi
                    details.stage = dgrData.Rows[count_pic].Cells[4].Value.ToString();      // lay thong tin tram
                    details.repeat = dgrData.Rows[count_pic].Cells[5].Value.ToString();     // lay so anh bi loi
                    details.GetLink_Repeat = dgrData.Rows[count_pic].Cells[9].Value.ToString();     // lay ten anh
                    details.GetLink = FileName;     // lay ten file anh
                    details.ipserver = lblClientAddr.Text;
                    details.port = lblClientPort.Text;
                    details.Shift = shift;          // Ca lam viec
                    details.ShowDialog();
                    if (details.SetOK == "OK")
                    {
                        dgrData.ClearSelection();
                        //dgrData.Rows[count_pic].Cells[2].Value = details.ErrorName;
                        dgrData.Rows[count_pic].Cells[4].Value += "-" + details.Location;
                        if (dgrData.Rows[count_pic].Cells[9].Value.ToString() != "")            // Co anh lap
                        {
                            string err_rpt = dgrData.Rows[count_pic].Cells[9].Value.ToString();
                            //dgrData.Rows[count_pic - 1].Cells[7].Value = Image.FromFile(Link_pictrue + err_rpt);
                            dgrData.Rows[count_pic].Cells[8].Value = err_rpt;
                        }
                        else dgrData.Rows[count_pic].Cells[7].Value = PictureName;
                        dgrData.Rows[count_pic].Selected = true;
                        string err = dgrData.Rows[count_pic].Cells[3].Value.ToString();
                        string cur_shift = dgrData.Rows[count_pic].Cells[16].Value.ToString();
                        dgrData.Rows[count_pic].Cells[6].Value = "OK";
                        //Fill_Color(err, details.Location, 1, true, 5);
                        int cot = Cell_return(details.Location);
                        int hang = Check_Total(cot);
                        Fill_Color(err, int.Parse(lblErrTotal.Text) - hang, cot, cur_shift, false);
                        Count_err = int.Parse(lblErr.Text.ToString());
                        Count_err2 = int.Parse(lblErrY.Text.ToString());
                        if (cur_shift == "Ca R") Count_err++;
                        else if (cur_shift == "Ca Y") Count_err2++;
                        lblErr.Text = Count_err.ToString();     // hien thi tong so loi thuc te
                        lblErrY.Text = Count_err2.ToString();     // hien thi tong so loi thuc te
                    }
                    else
                    {
                        if (details.SetOK == "NG")
                        {
                            Msg_Send = "check-" + PictureName;
                            Reclaim(PictureName);
                            bgwSend.RunWorkerAsync();
                        }
                    }
                    bgwQueue.RunWorkerAsync();
                }
            }
        }

        private void lblErr_TextChanged(object sender, EventArgs e)
        {
            lblErr2.Text = lblErr3.Text = lblErr.Text;
        }

        private void lblErrTotal_TextChanged(object sender, EventArgs e)
        {
            lblErrTotal2.Text = lblErrTotal3.Text = lblErrTotal.Text;
        }

        private void lblShift_TextChanged(object sender, EventArgs e)
        {
            if (lblShift.Text == "Ca Y")
            {
                panel4.BackColor = Color.Yellow;
                panel3.BackColor = Color.White;
                lblShift.Text = "Ca";
            }
            else
            {
                if (lblShift.Text == "Ca R")
                {
                    panel4.BackColor = Color.White;
                    panel3.BackColor = Color.Red;
                    lblShift.Text = "Ca";
                }
            }
        }

        private void lblErrY_TextChanged(object sender, EventArgs e)
        {
            lblErr2Y.Text = lblErr3Y.Text = lblErrY.Text;
        }

        private void lblErrTotalY_TextChanged(object sender, EventArgs e)
        {
            lblErrTotal2Y.Text = lblErrTotal3Y.Text = lblErrY.Text;
        }
    }
}
