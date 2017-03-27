using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Windows.Forms;
using mshtml;
namespace Quality_Feedback
{
    public partial class Details_Information : Form
    {
        public Details_Information()
        {
            InitializeComponent();
        }

        private string _error = "AB";
        private string _stage = "T";
        private string _rpt;
        private string _setok;
        private string _link;
        private string _errorName;
        private string _located;
        private string _state = "Details";
        private string _link_rpt="";
        private string Link = @"D:\Pictures\";
        private string _shift;
        private string _ipserver;
        private string _port;

        public string ipserver
        {
            get { return _ipserver; }
            set { _ipserver = value; }
        }

        public string port
        {
            get { return _port; }
            set { _port = value; }
        }

        public string error
        {
            get { return _error; }
            set { _error = value; }
        }

        public string GetLink
        {
            get { return _link; }
            set { _link = value; }
        }

        public string GetLink_Repeat
        {
            get { return _link_rpt; }
            set { _link_rpt = value; }
        }

        public string stage
        {
            get { return _stage; }
            set { _stage = value; }
        }

        public string repeat
        {
            get { return _rpt; }
            set { _rpt = value; }
        }

        public string SetOK
        {
            get { return _setok; }
            set { _setok = value; }
        }

        public string ErrorName
        {
            get { return _errorName; }
            set { _errorName = value; }
        }

        public string Location
        {
            get { return _located; }
            set { _located = value; }
        }

        public string State
        {
            get { return _state; }
            set { _state = value; }
        }

        public string Shift
        {
            get { return _shift; }
            set { _shift = value; }
        }

        private void Details_Information_Load(object sender, EventArgs e)
        {
            //_link = @"D:\OneDrive\STU Data\JIJIKOKOKU\QF - 20161028\Quality Feedback\bin\Debug\Untitled.png";
            lblErr.Text = _error;
            lblRpt.Text = _rpt;
            lblStage.Text = _stage;
            cmbStage.Text = _located;
            lblShift.Text = _shift;
            if (_state == "Details")
            {
                cmbStage.Enabled = false;
                //cmbName.Enabled = false;
                btnLuu.Enabled = false;
                //cmbName.Text = _errorName;
            }
           
            pictureBox1.Image = Image.FromFile(_link);

            //pictureBox1.SizeMode = PictureBoxSizeMode.StretchImage;
            if (_link_rpt == "")
            {
                btnNext.Enabled = false;
                btnPre.Enabled = false;
                lblCount.Text = "1/1";
            }
            else
            {
                btnNext.Enabled = true;
                lblCount.Text = "1/2";
            }
        }

        private void btnLuu_Click(object sender, EventArgs e)
        {
            try
            {
                SetOK = "OK";
                //ErrorName = cmbName.Text;
                //Location = cmbStage.SelectedItem.ToString();
                //_errorName = cmbName.SelectedItem.ToString();
                _located = cmbStage.SelectedItem.ToString();
                this.Close();
            }
            catch 
            {
                SetOK = "NG";
                MessageBox.Show("Thông tin trạm không được để trống!!!", "Thông báo");
            }
        }

        private void btnNext_Click(object sender, EventArgs e)
        {
            btnNext.Enabled = false;
            btnPre.Enabled = true;
            pictureBox1.Image = Image.FromFile(Link + _link_rpt);
            //pictureBox1.SizeMode = PictureBoxSizeMode.StretchImage;
            lblCount.Text = "2/2";                
        }

        private void btnPre_Click(object sender, EventArgs e)
        {
            btnNext.Enabled = true;
            btnPre.Enabled = false;
            pictureBox1.Image = Image.FromFile(_link);
            pictureBox1.SizeMode = PictureBoxSizeMode.StretchImage;
            lblCount.Text = "1/2";
        }

        private void Details_Information_FormClosing(object sender, FormClosingEventArgs e)
        {
            if (State == "Details")
            {
                e.Cancel = false;
            }
            else
            {
                if (SetOK != "OK" && SetOK != "NG")
                {
                    e.Cancel = true;
                    MessageBox.Show("Bạn phải ấn nút Lưu để xác nhận lỗi!!!", "Thông báo");
                }
            }
        }

        private void comboBox1_SelectedIndexChanged(object sender, EventArgs e)
        {
            btnLuu.Enabled = true;
        }

        private void cmbStage_SelectedIndexChanged(object sender, EventArgs e)
        {
            btnLuu.Enabled = true;
        }

        private void btnSendMsg_Click(object sender, EventArgs e)
        {
            
        }

        private void txbNote_TextChanged(object sender, EventArgs e)
        {
            if (txbNote.Text != "") btnLuu.Enabled = true;
            else btnLuu.Enabled = false;
        }

        private void btnCcancel_Click(object sender, EventArgs e)
        {
            DialogResult dr = MessageBox.Show("Thông tin của ảnh bị từ chối sẽ được gửi đi. Bạn muốn tiếp tục???", "Thông báo", MessageBoxButtons.OKCancel, MessageBoxIcon.Information);
            if (dr == DialogResult.OK)
            {
                SetOK = "NG";
                this.Close();
            }
        }
    }
}
