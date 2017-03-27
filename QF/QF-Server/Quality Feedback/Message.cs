using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Windows.Forms;
using System.Reflection;
using System.Threading;
using System.IO;
using System.Configuration;
using System.Net;
using System.Net.Sockets;

namespace Quality_Feedback
{
    public partial class Message : Form
    {
        public Message()
        {
            InitializeComponent();
        }

        private string _recmsg;
        private string _ipserver;
        private string _port;
        private string _sendmsg;
        private string _sendok;

        public string ipserver
        {
            get { return _ipserver; }
            set { _ipserver = value; }
        }

        public string sendok
        {
            get { return _sendok; }
            set { _sendok = value; }
        }

        public string port
        {
            get { return _port; }
            set { _port = value; }
        }

        public string recmsg
        {
            get { return _recmsg; }
            set { _recmsg = value; }
        }

        public string sendmsg
        {
            get { return _sendmsg; }
            set { _sendmsg = value; }
        }

        private void Message_Load(object sender, EventArgs e)
        {
            txbMessage.Text = _recmsg;
            txbMessage.Select(0, 0);
        }

        private void button1_Click(object sender, EventArgs e)
        {
          //  Send_data(_ipserver, _port, txbSendMsg.Text);
            sendok = "OK";
            sendmsg = txbSendMsg.Text;
            this.Close();
        }

        private void txbSendMsg_TextChanged(object sender, EventArgs e)
        {
            if (txbSendMsg.Text == "")  btnSend.Enabled = false;
            else btnSend.Enabled = true;
        }

        private void Message_FormClosing(object sender, FormClosingEventArgs e)
        {
            if (sendok != "OK") sendok = "NG";
            e.Cancel = false;
        }
    }
}
