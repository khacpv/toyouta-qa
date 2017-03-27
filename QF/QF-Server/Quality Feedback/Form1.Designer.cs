namespace Quality_Feedback
{
    partial class Form1
    {
        /// <summary>
        /// Required designer variable.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        /// Clean up any resources being used.
        /// </summary>
        /// <param name="disposing">true if managed resources should be disposed; otherwise, false.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Windows Form Designer generated code

        /// <summary>
        /// Required method for Designer support - do not modify
        /// the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
            this.components = new System.ComponentModel.Container();
            System.Windows.Forms.DataGridViewCellStyle dataGridViewCellStyle1 = new System.Windows.Forms.DataGridViewCellStyle();
            this.pictureBox1 = new System.Windows.Forms.PictureBox();
            this.webBrowser1 = new System.Windows.Forms.WebBrowser();
            this.tableLayoutPanel1 = new System.Windows.Forms.TableLayoutPanel();
            this.webBrowser2 = new System.Windows.Forms.WebBrowser();
            this.webBrowser5 = new System.Windows.Forms.WebBrowser();
            this.webBrowser4 = new System.Windows.Forms.WebBrowser();
            this.tableLayoutPanel2 = new System.Windows.Forms.TableLayoutPanel();
            this.label1 = new System.Windows.Forms.Label();
            this.label2 = new System.Windows.Forms.Label();
            this.lblErr = new System.Windows.Forms.Label();
            this.lblErrTotal = new System.Windows.Forms.Label();
            this.btnSetting = new System.Windows.Forms.Button();
            this.button1 = new System.Windows.Forms.Button();
            this.dgrData = new System.Windows.Forms.DataGridView();
            this.Column1 = new System.Windows.Forms.DataGridViewTextBoxColumn();
            this.Column2 = new System.Windows.Forms.DataGridViewTextBoxColumn();
            this.Column3 = new System.Windows.Forms.DataGridViewTextBoxColumn();
            this.Column4 = new System.Windows.Forms.DataGridViewTextBoxColumn();
            this.Column5 = new System.Windows.Forms.DataGridViewTextBoxColumn();
            this.Column6 = new System.Windows.Forms.DataGridViewTextBoxColumn();
            this.Column7 = new System.Windows.Forms.DataGridViewTextBoxColumn();
            this.Column8 = new System.Windows.Forms.DataGridViewTextBoxColumn();
            this.Column11 = new System.Windows.Forms.DataGridViewTextBoxColumn();
            this.Column24 = new System.Windows.Forms.DataGridViewTextBoxColumn();
            this.Column9 = new System.Windows.Forms.DataGridViewTextBoxColumn();
            this.Column10 = new System.Windows.Forms.DataGridViewTextBoxColumn();
            this.Column23 = new System.Windows.Forms.DataGridViewTextBoxColumn();
            this.Column12 = new System.Windows.Forms.DataGridViewTextBoxColumn();
            this.Column13 = new System.Windows.Forms.DataGridViewTextBoxColumn();
            this.Column14 = new System.Windows.Forms.DataGridViewTextBoxColumn();
            this.Column15 = new System.Windows.Forms.DataGridViewTextBoxColumn();
            this.tableLayoutPanel3 = new System.Windows.Forms.TableLayoutPanel();
            this.lbPortServer = new System.Windows.Forms.Label();
            this.lblClientPort = new System.Windows.Forms.Label();
            this.lbIPServer = new System.Windows.Forms.Label();
            this.lblClientAddr = new System.Windows.Forms.Label();
            this.bgwConnect = new System.ComponentModel.BackgroundWorker();
            this.txbRecevice = new System.Windows.Forms.TextBox();
            this.txbLink = new System.Windows.Forms.TextBox();
            this.dgrGraph = new System.Windows.Forms.DataGridView();
            this.label3 = new System.Windows.Forms.Label();
            this.bgwQueue = new System.ComponentModel.BackgroundWorker();
            this.timer1 = new System.Windows.Forms.Timer(this.components);
            this.timer2 = new System.Windows.Forms.Timer(this.components);
            this.panel1 = new System.Windows.Forms.Panel();
            this.dgrQueue = new System.Windows.Forms.DataGridView();
            this.Column25 = new System.Windows.Forms.DataGridViewTextBoxColumn();
            this.Column26 = new System.Windows.Forms.DataGridViewTextBoxColumn();
            this.Column27 = new System.Windows.Forms.DataGridViewTextBoxColumn();
            this.lblShift = new System.Windows.Forms.Label();
            this.bgwSend = new System.ComponentModel.BackgroundWorker();
            this.bgwRec = new System.ComponentModel.BackgroundWorker();
            this.printDialog1 = new System.Windows.Forms.PrintDialog();
            ((System.ComponentModel.ISupportInitialize)(this.pictureBox1)).BeginInit();
            this.tableLayoutPanel1.SuspendLayout();
            this.tableLayoutPanel2.SuspendLayout();
            ((System.ComponentModel.ISupportInitialize)(this.dgrData)).BeginInit();
            this.tableLayoutPanel3.SuspendLayout();
            ((System.ComponentModel.ISupportInitialize)(this.dgrGraph)).BeginInit();
            this.panel1.SuspendLayout();
            ((System.ComponentModel.ISupportInitialize)(this.dgrQueue)).BeginInit();
            this.SuspendLayout();
            // 
            // pictureBox1
            // 
            this.pictureBox1.BackColor = System.Drawing.Color.White;
            this.pictureBox1.InitialImage = null;
            this.pictureBox1.Location = new System.Drawing.Point(17, 24);
            this.pictureBox1.Name = "pictureBox1";
            this.pictureBox1.Size = new System.Drawing.Size(512, 687);
            this.pictureBox1.SizeMode = System.Windows.Forms.PictureBoxSizeMode.StretchImage;
            this.pictureBox1.TabIndex = 1;
            this.pictureBox1.TabStop = false;
            this.pictureBox1.Click += new System.EventHandler(this.pictureBox1_Click);
            // 
            // webBrowser1
            // 
            this.webBrowser1.Location = new System.Drawing.Point(33, 32);
            this.webBrowser1.MinimumSize = new System.Drawing.Size(20, 20);
            this.webBrowser1.Name = "webBrowser1";
            this.webBrowser1.ScrollBarsEnabled = false;
            this.webBrowser1.Size = new System.Drawing.Size(501, 660);
            this.webBrowser1.TabIndex = 0;
            this.webBrowser1.Url = new System.Uri("http://192.168.1.2:1234/storage/emulated/0/Download/toyotalogo.jpg", System.UriKind.Absolute);
            this.webBrowser1.Visible = false;
            this.webBrowser1.DocumentCompleted += new System.Windows.Forms.WebBrowserDocumentCompletedEventHandler(this.webBrowser1_DocumentCompleted);
            // 
            // tableLayoutPanel1
            // 
            this.tableLayoutPanel1.CellBorderStyle = System.Windows.Forms.TableLayoutPanelCellBorderStyle.Single;
            this.tableLayoutPanel1.ColumnCount = 1;
            this.tableLayoutPanel1.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Percent, 100F));
            this.tableLayoutPanel1.Controls.Add(this.webBrowser2, 0, 0);
            this.tableLayoutPanel1.Controls.Add(this.webBrowser5, 0, 4);
            this.tableLayoutPanel1.Controls.Add(this.webBrowser4, 0, 2);
            this.tableLayoutPanel1.Location = new System.Drawing.Point(560, 32);
            this.tableLayoutPanel1.Name = "tableLayoutPanel1";
            this.tableLayoutPanel1.RowCount = 5;
            this.tableLayoutPanel1.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Percent, 31.88775F));
            this.tableLayoutPanel1.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Percent, 2.168367F));
            this.tableLayoutPanel1.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Percent, 31.88775F));
            this.tableLayoutPanel1.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Percent, 2.168367F));
            this.tableLayoutPanel1.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Percent, 31.88775F));
            this.tableLayoutPanel1.Size = new System.Drawing.Size(148, 434);
            this.tableLayoutPanel1.TabIndex = 18;
            // 
            // webBrowser2
            // 
            this.webBrowser2.Dock = System.Windows.Forms.DockStyle.Fill;
            this.webBrowser2.Location = new System.Drawing.Point(4, 4);
            this.webBrowser2.MinimumSize = new System.Drawing.Size(20, 20);
            this.webBrowser2.Name = "webBrowser2";
            this.webBrowser2.ScrollBarsEnabled = false;
            this.webBrowser2.Size = new System.Drawing.Size(140, 130);
            this.webBrowser2.TabIndex = 4;
            this.webBrowser2.Url = new System.Uri("http://192.168.1.2:1234/storage/emulated/0/Download/toyotalogo.jpg", System.UriKind.Absolute);
            this.webBrowser2.Visible = false;
            this.webBrowser2.DocumentCompleted += new System.Windows.Forms.WebBrowserDocumentCompletedEventHandler(this.webBrowser2_DocumentCompleted);
            // 
            // webBrowser5
            // 
            this.webBrowser5.Dock = System.Windows.Forms.DockStyle.Fill;
            this.webBrowser5.Location = new System.Drawing.Point(4, 298);
            this.webBrowser5.MinimumSize = new System.Drawing.Size(20, 20);
            this.webBrowser5.Name = "webBrowser5";
            this.webBrowser5.ScrollBarsEnabled = false;
            this.webBrowser5.Size = new System.Drawing.Size(140, 132);
            this.webBrowser5.TabIndex = 3;
            this.webBrowser5.Url = new System.Uri("http://192.168.1.2:1234/storage/emulated/0/Download/toyotalogo.jpg", System.UriKind.Absolute);
            this.webBrowser5.Visible = false;
            this.webBrowser5.DocumentCompleted += new System.Windows.Forms.WebBrowserDocumentCompletedEventHandler(this.webBrowser5_DocumentCompleted);
            // 
            // webBrowser4
            // 
            this.webBrowser4.Dock = System.Windows.Forms.DockStyle.Fill;
            this.webBrowser4.Location = new System.Drawing.Point(4, 151);
            this.webBrowser4.MinimumSize = new System.Drawing.Size(20, 20);
            this.webBrowser4.Name = "webBrowser4";
            this.webBrowser4.ScrollBarsEnabled = false;
            this.webBrowser4.Size = new System.Drawing.Size(140, 130);
            this.webBrowser4.TabIndex = 2;
            this.webBrowser4.Url = new System.Uri("http://192.168.1.2:1234/storage/emulated/0/Download/toyotalogo.jpg", System.UriKind.Absolute);
            this.webBrowser4.Visible = false;
            this.webBrowser4.DocumentCompleted += new System.Windows.Forms.WebBrowserDocumentCompletedEventHandler(this.webBrowser4_DocumentCompleted);
            // 
            // tableLayoutPanel2
            // 
            this.tableLayoutPanel2.CellBorderStyle = System.Windows.Forms.TableLayoutPanelCellBorderStyle.Outset;
            this.tableLayoutPanel2.ColumnCount = 3;
            this.tableLayoutPanel2.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Percent, 39.68254F));
            this.tableLayoutPanel2.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Percent, 23.49206F));
            this.tableLayoutPanel2.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Percent, 36.50794F));
            this.tableLayoutPanel2.Controls.Add(this.label1, 0, 0);
            this.tableLayoutPanel2.Controls.Add(this.label2, 0, 1);
            this.tableLayoutPanel2.Controls.Add(this.lblErr, 1, 0);
            this.tableLayoutPanel2.Controls.Add(this.lblErrTotal, 1, 1);
            this.tableLayoutPanel2.Controls.Add(this.btnSetting, 2, 0);
            this.tableLayoutPanel2.Controls.Add(this.button1, 2, 1);
            this.tableLayoutPanel2.Location = new System.Drawing.Point(714, 5);
            this.tableLayoutPanel2.Name = "tableLayoutPanel2";
            this.tableLayoutPanel2.RowCount = 2;
            this.tableLayoutPanel2.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Percent, 50F));
            this.tableLayoutPanel2.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Percent, 50F));
            this.tableLayoutPanel2.Size = new System.Drawing.Size(342, 81);
            this.tableLayoutPanel2.TabIndex = 19;
            // 
            // label1
            // 
            this.label1.Anchor = System.Windows.Forms.AnchorStyles.Left;
            this.label1.AutoSize = true;
            this.label1.Font = new System.Drawing.Font("Microsoft Sans Serif", 9.75F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.label1.Location = new System.Drawing.Point(5, 12);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(98, 16);
            this.label1.TabIndex = 0;
            this.label1.Text = "Tổng lỗi thực tế";
            this.label1.Click += new System.EventHandler(this.label1_Click);
            // 
            // label2
            // 
            this.label2.Anchor = System.Windows.Forms.AnchorStyles.Left;
            this.label2.AutoSize = true;
            this.label2.Font = new System.Drawing.Font("Microsoft Sans Serif", 9.75F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.label2.Location = new System.Drawing.Point(5, 52);
            this.label2.Name = "label2";
            this.label2.Size = new System.Drawing.Size(116, 16);
            this.label2.TabIndex = 0;
            this.label2.Text = "Tổng lỗi cho phép";
            // 
            // lblErr
            // 
            this.lblErr.Anchor = System.Windows.Forms.AnchorStyles.Left;
            this.lblErr.AutoSize = true;
            this.lblErr.BorderStyle = System.Windows.Forms.BorderStyle.Fixed3D;
            this.lblErr.Font = new System.Drawing.Font("Microsoft Sans Serif", 24F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.lblErr.ForeColor = System.Drawing.Color.Red;
            this.lblErr.Location = new System.Drawing.Point(139, 2);
            this.lblErr.Name = "lblErr";
            this.lblErr.Size = new System.Drawing.Size(37, 37);
            this.lblErr.TabIndex = 0;
            this.lblErr.Text = "0";
            // 
            // lblErrTotal
            // 
            this.lblErrTotal.Anchor = System.Windows.Forms.AnchorStyles.Left;
            this.lblErrTotal.AutoSize = true;
            this.lblErrTotal.BorderStyle = System.Windows.Forms.BorderStyle.Fixed3D;
            this.lblErrTotal.Font = new System.Drawing.Font("Microsoft Sans Serif", 24F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.lblErrTotal.Location = new System.Drawing.Point(139, 41);
            this.lblErrTotal.Name = "lblErrTotal";
            this.lblErrTotal.Size = new System.Drawing.Size(55, 38);
            this.lblErrTotal.TabIndex = 0;
            this.lblErrTotal.Text = "20";
            // 
            // btnSetting
            // 
            this.btnSetting.Font = new System.Drawing.Font("Microsoft Sans Serif", 9.75F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.btnSetting.Location = new System.Drawing.Point(219, 5);
            this.btnSetting.Name = "btnSetting";
            this.btnSetting.Size = new System.Drawing.Size(113, 31);
            this.btnSetting.TabIndex = 2;
            this.btnSetting.Text = "Cài đặt hệ thống";
            this.btnSetting.UseVisualStyleBackColor = true;
            this.btnSetting.Click += new System.EventHandler(this.button2_Click);
            // 
            // button1
            // 
            this.button1.Font = new System.Drawing.Font("Microsoft Sans Serif", 9.75F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.button1.Location = new System.Drawing.Point(219, 44);
            this.button1.Name = "button1";
            this.button1.Size = new System.Drawing.Size(113, 32);
            this.button1.TabIndex = 1;
            this.button1.Text = "Xuất báo cáo";
            this.button1.UseVisualStyleBackColor = true;
            // 
            // dgrData
            // 
            this.dgrData.AllowUserToAddRows = false;
            this.dgrData.BackgroundColor = System.Drawing.Color.White;
            this.dgrData.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            this.dgrData.Columns.AddRange(new System.Windows.Forms.DataGridViewColumn[] {
            this.Column1,
            this.Column2,
            this.Column3,
            this.Column4,
            this.Column5,
            this.Column6,
            this.Column7,
            this.Column8,
            this.Column11,
            this.Column24,
            this.Column9,
            this.Column10,
            this.Column23,
            this.Column12,
            this.Column13,
            this.Column14,
            this.Column15});
            this.dgrData.Location = new System.Drawing.Point(560, 472);
            this.dgrData.Name = "dgrData";
            this.dgrData.ReadOnly = true;
            this.dgrData.Size = new System.Drawing.Size(796, 255);
            this.dgrData.TabIndex = 21;
            this.dgrData.CellClick += new System.Windows.Forms.DataGridViewCellEventHandler(this.dgrData_CellClick);
            this.dgrData.RowsAdded += new System.Windows.Forms.DataGridViewRowsAddedEventHandler(this.dgrData_RowsAdded);
            this.dgrData.RowsRemoved += new System.Windows.Forms.DataGridViewRowsRemovedEventHandler(this.dgrData_RowsRemoved);
            // 
            // Column1
            // 
            this.Column1.HeaderText = "Số xe";
            this.Column1.Name = "Column1";
            this.Column1.ReadOnly = true;
            this.Column1.Width = 80;
            // 
            // Column2
            // 
            this.Column2.HeaderText = "Ngày giờ";
            this.Column2.Name = "Column2";
            this.Column2.ReadOnly = true;
            this.Column2.Width = 150;
            // 
            // Column3
            // 
            this.Column3.HeaderText = "Tên lỗi";
            this.Column3.Name = "Column3";
            this.Column3.ReadOnly = true;
            // 
            // Column4
            // 
            this.Column4.HeaderText = "Mã lỗi";
            this.Column4.Name = "Column4";
            this.Column4.ReadOnly = true;
            this.Column4.Width = 90;
            // 
            // Column5
            // 
            this.Column5.HeaderText = "Khu vực tạo ra lỗi";
            this.Column5.Name = "Column5";
            this.Column5.ReadOnly = true;
            this.Column5.Width = 120;
            // 
            // Column6
            // 
            this.Column6.HeaderText = "Lỗi lặp lại";
            this.Column6.Name = "Column6";
            this.Column6.ReadOnly = true;
            this.Column6.Width = 80;
            // 
            // Column7
            // 
            this.Column7.HeaderText = "Xác nhận lỗi";
            this.Column7.Name = "Column7";
            this.Column7.ReadOnly = true;
            // 
            // Column8
            // 
            this.Column8.HeaderText = "Ảnh 1";
            this.Column8.Name = "Column8";
            this.Column8.ReadOnly = true;
            // 
            // Column11
            // 
            this.Column11.HeaderText = "Ảnh 2";
            this.Column11.Name = "Column11";
            this.Column11.ReadOnly = true;
            // 
            // Column24
            // 
            this.Column24.HeaderText = "Tên ảnh";
            this.Column24.Name = "Column24";
            this.Column24.ReadOnly = true;
            // 
            // Column9
            // 
            this.Column9.HeaderText = "Tên hàng";
            this.Column9.Name = "Column9";
            this.Column9.ReadOnly = true;
            // 
            // Column10
            // 
            this.Column10.HeaderText = "Tên cột";
            this.Column10.Name = "Column10";
            this.Column10.ReadOnly = true;
            // 
            // Column23
            // 
            this.Column23.HeaderText = "Tên trạm";
            this.Column23.Name = "Column23";
            this.Column23.ReadOnly = true;
            // 
            // Column12
            // 
            this.Column12.HeaderText = "Họ tên";
            this.Column12.Name = "Column12";
            this.Column12.ReadOnly = true;
            // 
            // Column13
            // 
            this.Column13.HeaderText = "Sđt";
            this.Column13.Name = "Column13";
            this.Column13.ReadOnly = true;
            // 
            // Column14
            // 
            this.Column14.HeaderText = "Tin nhắn";
            this.Column14.Name = "Column14";
            this.Column14.ReadOnly = true;
            // 
            // Column15
            // 
            this.Column15.HeaderText = "Ca làm việc";
            this.Column15.Name = "Column15";
            this.Column15.ReadOnly = true;
            // 
            // tableLayoutPanel3
            // 
            this.tableLayoutPanel3.CellBorderStyle = System.Windows.Forms.TableLayoutPanelCellBorderStyle.OutsetDouble;
            this.tableLayoutPanel3.ColumnCount = 2;
            this.tableLayoutPanel3.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Percent, 52.8777F));
            this.tableLayoutPanel3.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Percent, 47.1223F));
            this.tableLayoutPanel3.Controls.Add(this.lbPortServer, 1, 1);
            this.tableLayoutPanel3.Controls.Add(this.lblClientPort, 0, 1);
            this.tableLayoutPanel3.Controls.Add(this.lbIPServer, 1, 0);
            this.tableLayoutPanel3.Controls.Add(this.lblClientAddr, 0, 0);
            this.tableLayoutPanel3.Location = new System.Drawing.Point(1057, 4);
            this.tableLayoutPanel3.Name = "tableLayoutPanel3";
            this.tableLayoutPanel3.RowCount = 2;
            this.tableLayoutPanel3.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Percent, 50F));
            this.tableLayoutPanel3.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Percent, 50F));
            this.tableLayoutPanel3.Size = new System.Drawing.Size(299, 82);
            this.tableLayoutPanel3.TabIndex = 23;
            // 
            // lbPortServer
            // 
            this.lbPortServer.Anchor = System.Windows.Forms.AnchorStyles.Left;
            this.lbPortServer.AutoSize = true;
            this.lbPortServer.Location = new System.Drawing.Point(162, 54);
            this.lbPortServer.Name = "lbPortServer";
            this.lbPortServer.Size = new System.Drawing.Size(41, 13);
            this.lbPortServer.TabIndex = 5;
            this.lbPortServer.Text = "label10";
            // 
            // lblClientPort
            // 
            this.lblClientPort.Anchor = System.Windows.Forms.AnchorStyles.Left;
            this.lblClientPort.AutoSize = true;
            this.lblClientPort.Location = new System.Drawing.Point(6, 54);
            this.lblClientPort.Name = "lblClientPort";
            this.lblClientPort.Size = new System.Drawing.Size(31, 13);
            this.lblClientPort.TabIndex = 4;
            this.lblClientPort.Text = "8888";
            // 
            // lbIPServer
            // 
            this.lbIPServer.Anchor = System.Windows.Forms.AnchorStyles.Left;
            this.lbIPServer.AutoSize = true;
            this.lbIPServer.Location = new System.Drawing.Point(162, 14);
            this.lbIPServer.Name = "lbIPServer";
            this.lbIPServer.Size = new System.Drawing.Size(35, 13);
            this.lbIPServer.TabIndex = 1;
            this.lbIPServer.Text = "label6";
            // 
            // lblClientAddr
            // 
            this.lblClientAddr.Anchor = System.Windows.Forms.AnchorStyles.Left;
            this.lblClientAddr.AutoSize = true;
            this.lblClientAddr.Cursor = System.Windows.Forms.Cursors.Hand;
            this.lblClientAddr.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.25F, System.Drawing.FontStyle.Underline, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.lblClientAddr.ForeColor = System.Drawing.Color.Blue;
            this.lblClientAddr.Location = new System.Drawing.Point(6, 14);
            this.lblClientAddr.Name = "lblClientAddr";
            this.lblClientAddr.Size = new System.Drawing.Size(76, 13);
            this.lblClientAddr.TabIndex = 0;
            this.lblClientAddr.Text = "192.168.1.116";
            this.lblClientAddr.Click += new System.EventHandler(this.lblClientAddr_Click);
            // 
            // bgwConnect
            // 
            this.bgwConnect.DoWork += new System.ComponentModel.DoWorkEventHandler(this.bgwConnect_DoWork);
            // 
            // txbRecevice
            // 
            this.txbRecevice.Location = new System.Drawing.Point(714, 92);
            this.txbRecevice.Name = "txbRecevice";
            this.txbRecevice.Size = new System.Drawing.Size(235, 20);
            this.txbRecevice.TabIndex = 24;
            this.txbRecevice.Visible = false;
            // 
            // txbLink
            // 
            this.txbLink.Location = new System.Drawing.Point(955, 92);
            this.txbLink.Name = "txbLink";
            this.txbLink.Size = new System.Drawing.Size(383, 20);
            this.txbLink.TabIndex = 25;
            this.txbLink.Text = "http://192.168.1.102:1234/storage/emulated/0/DCIM/Camera/";
            this.txbLink.Visible = false;
            // 
            // dgrGraph
            // 
            this.dgrGraph.AllowUserToAddRows = false;
            this.dgrGraph.AllowUserToResizeColumns = false;
            this.dgrGraph.AllowUserToResizeRows = false;
            dataGridViewCellStyle1.WrapMode = System.Windows.Forms.DataGridViewTriState.True;
            this.dgrGraph.AlternatingRowsDefaultCellStyle = dataGridViewCellStyle1;
            this.dgrGraph.BackgroundColor = System.Drawing.Color.White;
            this.dgrGraph.BorderStyle = System.Windows.Forms.BorderStyle.None;
            this.dgrGraph.ColumnHeadersBorderStyle = System.Windows.Forms.DataGridViewHeaderBorderStyle.Single;
            this.dgrGraph.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            this.dgrGraph.ColumnHeadersVisible = false;
            this.dgrGraph.Cursor = System.Windows.Forms.Cursors.Arrow;
            this.dgrGraph.GridColor = System.Drawing.Color.Beige;
            this.dgrGraph.Location = new System.Drawing.Point(714, 127);
            this.dgrGraph.MultiSelect = false;
            this.dgrGraph.Name = "dgrGraph";
            this.dgrGraph.ReadOnly = true;
            this.dgrGraph.RowHeadersBorderStyle = System.Windows.Forms.DataGridViewHeaderBorderStyle.None;
            this.dgrGraph.RowHeadersVisible = false;
            this.dgrGraph.ScrollBars = System.Windows.Forms.ScrollBars.None;
            this.dgrGraph.ShowRowErrors = false;
            this.dgrGraph.Size = new System.Drawing.Size(642, 339);
            this.dgrGraph.TabIndex = 26;
            this.dgrGraph.CellClick += new System.Windows.Forms.DataGridViewCellEventHandler(this.dgrGraph_CellClick);
            // 
            // label3
            // 
            this.label3.Anchor = System.Windows.Forms.AnchorStyles.Left;
            this.label3.AutoSize = true;
            this.label3.BorderStyle = System.Windows.Forms.BorderStyle.Fixed3D;
            this.label3.Font = new System.Drawing.Font("Microsoft Sans Serif", 21.75F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.label3.ForeColor = System.Drawing.Color.FromArgb(((int)(((byte)(192)))), ((int)(((byte)(0)))), ((int)(((byte)(192)))));
            this.label3.Location = new System.Drawing.Point(889, 83);
            this.label3.Name = "label3";
            this.label3.Size = new System.Drawing.Size(281, 35);
            this.label3.TabIndex = 27;
            this.label3.Text = "Bảng tổng hợp số lỗi";
            // 
            // bgwQueue
            // 
            this.bgwQueue.DoWork += new System.ComponentModel.DoWorkEventHandler(this.bgwQueue_DoWork);
            // 
            // timer1
            // 
            this.timer1.Interval = 1000;
            this.timer1.Tick += new System.EventHandler(this.timer1_Tick);
            // 
            // timer2
            // 
            this.timer2.Interval = 3500;
            this.timer2.Tick += new System.EventHandler(this.timer2_Tick);
            // 
            // panel1
            // 
            this.panel1.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(192)))), ((int)(((byte)(192)))), ((int)(((byte)(255)))));
            this.panel1.Controls.Add(this.pictureBox1);
            this.panel1.Location = new System.Drawing.Point(12, 9);
            this.panel1.Name = "panel1";
            this.panel1.Size = new System.Drawing.Size(542, 718);
            this.panel1.TabIndex = 28;
            // 
            // dgrQueue
            // 
            this.dgrQueue.AllowUserToAddRows = false;
            this.dgrQueue.BackgroundColor = System.Drawing.Color.White;
            this.dgrQueue.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            this.dgrQueue.Columns.AddRange(new System.Windows.Forms.DataGridViewColumn[] {
            this.Column25,
            this.Column26,
            this.Column27});
            this.dgrQueue.Location = new System.Drawing.Point(710, 127);
            this.dgrQueue.Name = "dgrQueue";
            this.dgrQueue.Size = new System.Drawing.Size(646, 21);
            this.dgrQueue.TabIndex = 32;
            this.dgrQueue.Visible = false;
            this.dgrQueue.RowsAdded += new System.Windows.Forms.DataGridViewRowsAddedEventHandler(this.dgrQueue_RowsAdded);
            this.dgrQueue.RowsRemoved += new System.Windows.Forms.DataGridViewRowsRemovedEventHandler(this.dgrQueue_RowsRemoved);
            // 
            // Column25
            // 
            this.Column25.HeaderText = "No";
            this.Column25.Name = "Column25";
            this.Column25.Width = 40;
            // 
            // Column26
            // 
            this.Column26.HeaderText = "Tên ảnh";
            this.Column26.Name = "Column26";
            this.Column26.Width = 150;
            // 
            // Column27
            // 
            this.Column27.HeaderText = "Link";
            this.Column27.Name = "Column27";
            this.Column27.Width = 400;
            // 
            // lblShift
            // 
            this.lblShift.AutoSize = true;
            this.lblShift.Font = new System.Drawing.Font("Microsoft Sans Serif", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.lblShift.ForeColor = System.Drawing.Color.Blue;
            this.lblShift.Location = new System.Drawing.Point(613, 7);
            this.lblShift.Name = "lblShift";
            this.lblShift.Size = new System.Drawing.Size(52, 15);
            this.lblShift.TabIndex = 33;
            this.lblShift.Text = " ffafsdfaf";
            // 
            // bgwSend
            // 
            this.bgwSend.DoWork += new System.ComponentModel.DoWorkEventHandler(this.bgwSend_DoWork);
            // 
            // bgwRec
            // 
            this.bgwRec.DoWork += new System.ComponentModel.DoWorkEventHandler(this.bgwRec_DoWork);
            // 
            // printDialog1
            // 
            this.printDialog1.UseEXDialog = true;
            // 
            // Form1
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(1359, 730);
            this.Controls.Add(this.lblShift);
            this.Controls.Add(this.dgrQueue);
            this.Controls.Add(this.dgrGraph);
            this.Controls.Add(this.label3);
            this.Controls.Add(this.txbLink);
            this.Controls.Add(this.txbRecevice);
            this.Controls.Add(this.tableLayoutPanel3);
            this.Controls.Add(this.dgrData);
            this.Controls.Add(this.tableLayoutPanel2);
            this.Controls.Add(this.tableLayoutPanel1);
            this.Controls.Add(this.panel1);
            this.Controls.Add(this.webBrowser1);
            this.MaximizeBox = false;
            this.Name = "Form1";
            this.Text = "Quality Feedback";
            this.WindowState = System.Windows.Forms.FormWindowState.Maximized;
            this.Load += new System.EventHandler(this.Form1_Load);
            ((System.ComponentModel.ISupportInitialize)(this.pictureBox1)).EndInit();
            this.tableLayoutPanel1.ResumeLayout(false);
            this.tableLayoutPanel2.ResumeLayout(false);
            this.tableLayoutPanel2.PerformLayout();
            ((System.ComponentModel.ISupportInitialize)(this.dgrData)).EndInit();
            this.tableLayoutPanel3.ResumeLayout(false);
            this.tableLayoutPanel3.PerformLayout();
            ((System.ComponentModel.ISupportInitialize)(this.dgrGraph)).EndInit();
            this.panel1.ResumeLayout(false);
            ((System.ComponentModel.ISupportInitialize)(this.dgrQueue)).EndInit();
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.TableLayoutPanel tableLayoutPanel1;
        private System.Windows.Forms.TableLayoutPanel tableLayoutPanel2;
        private System.Windows.Forms.Label label1;
        private System.Windows.Forms.Label label2;
        private System.Windows.Forms.Label lblErr;
        private System.Windows.Forms.Label lblErrTotal;
        private System.Windows.Forms.Button button1;
        private System.Windows.Forms.DataGridView dgrData;
        private System.Windows.Forms.Button btnSetting;
        private System.Windows.Forms.TableLayoutPanel tableLayoutPanel3;
        private System.Windows.Forms.Label lblClientPort;
        private System.Windows.Forms.Label lbIPServer;
        private System.Windows.Forms.Label lblClientAddr;
        private System.ComponentModel.BackgroundWorker bgwConnect;
        private System.Windows.Forms.Label lbPortServer;
        private System.Windows.Forms.TextBox txbRecevice;
        private System.Windows.Forms.TextBox txbLink;
        private System.Windows.Forms.PictureBox pictureBox1;
        private System.Windows.Forms.WebBrowser webBrowser1;
        private System.Windows.Forms.WebBrowser webBrowser4;
        private System.Windows.Forms.WebBrowser webBrowser5;
        private System.Windows.Forms.WebBrowser webBrowser2;
        private System.Windows.Forms.DataGridView dgrGraph;
        private System.Windows.Forms.Label label3;
        private System.ComponentModel.BackgroundWorker bgwQueue;
        private System.Windows.Forms.Timer timer1;
        private System.Windows.Forms.Timer timer2;
        private System.Windows.Forms.Panel panel1;
        private System.Windows.Forms.DataGridView dgrQueue;
        private System.Windows.Forms.DataGridViewTextBoxColumn Column25;
        private System.Windows.Forms.DataGridViewTextBoxColumn Column26;
        private System.Windows.Forms.DataGridViewTextBoxColumn Column27;
        private System.Windows.Forms.DataGridViewTextBoxColumn Column1;
        private System.Windows.Forms.DataGridViewTextBoxColumn Column2;
        private System.Windows.Forms.DataGridViewTextBoxColumn Column3;
        private System.Windows.Forms.DataGridViewTextBoxColumn Column4;
        private System.Windows.Forms.DataGridViewTextBoxColumn Column5;
        private System.Windows.Forms.DataGridViewTextBoxColumn Column6;
        private System.Windows.Forms.DataGridViewTextBoxColumn Column7;
        private System.Windows.Forms.DataGridViewTextBoxColumn Column8;
        private System.Windows.Forms.DataGridViewTextBoxColumn Column11;
        private System.Windows.Forms.DataGridViewTextBoxColumn Column24;
        private System.Windows.Forms.DataGridViewTextBoxColumn Column9;
        private System.Windows.Forms.DataGridViewTextBoxColumn Column10;
        private System.Windows.Forms.DataGridViewTextBoxColumn Column23;
        private System.Windows.Forms.DataGridViewTextBoxColumn Column12;
        private System.Windows.Forms.DataGridViewTextBoxColumn Column13;
        private System.Windows.Forms.DataGridViewTextBoxColumn Column14;
        private System.Windows.Forms.DataGridViewTextBoxColumn Column15;
        private System.Windows.Forms.Label lblShift;
        private System.ComponentModel.BackgroundWorker bgwSend;
        private System.ComponentModel.BackgroundWorker bgwRec;
        private System.Windows.Forms.PrintDialog printDialog1;
    }
}

