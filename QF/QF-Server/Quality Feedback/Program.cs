using System;
using System.Collections.Generic;
using System.Linq;
using System.Windows.Forms;

namespace Quality_Feedback
{
    static class Program
    {
        /// <summary>
        /// The main entry point for the application.
        /// </summary>
        [STAThread]
        static void Main()
        {
            Application.EnableVisualStyles();
            Application.SetCompatibleTextRenderingDefault(false);
            //Application.Run(new Login());
            //Application.Run(new Form1());
            Application.Run(new Mainform());
            //Application.Run(new Details_Information());
            //Application.Run(new Error_Graph());
        }
    }
}
