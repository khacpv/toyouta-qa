using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Windows.Forms;

namespace Quality_Feedback
{
    public partial class Login : Form
    {
        public Login()
        {
            InitializeComponent();
        }
        bool done = false;

        private string _shift;

        public string Shift
        {
            set { _shift = value; }
            get { return _shift; }
        }

        private void btnOK_Click(object sender, EventArgs e)
        {
            if (txbPassword.Text == "1234")
            {
                //this.Close();
                //Application.Run(new Form1());
                //Form1 form1 = new Form1();
                //form1.ShowDialog();
                done = true;
                Shift = cmbShift.SelectedItem.ToString();
                this.Close();
              //  Application.Run(new Form1());
            }
            else
            {
                done = false;
                MessageBox.Show("Bạn đã nhập sai mật khẩu. Vui lòng kiểm tra lại!!!", "Thông báo");
            }
        }

        private void Login_FormClosing(object sender, FormClosingEventArgs e)
        {
            if (done == false)
            {
                e.Cancel = false;
                Application.Exit();
            }
        }

        private void cmbShift_SelectedIndexChanged(object sender, EventArgs e)
        {
            btnOK.Enabled = true;
        }
    }
}
