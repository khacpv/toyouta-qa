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
    public partial class Error_Graph : Form
    {
        int i3 = 0;
        int i2 = 0;
        int Count_err = 0;
        int total_err = 0;
        String[] Stage = { "T1.1", "T1.2", "T2.1", "T2.2", "T3.1", "T3.2", "T4.1", "T4.2", "T5.1", "T5.2" };

        public Error_Graph(DataGridView dgrData)
        {
            InitializeComponent();
            int count = int.Parse(lblErrTotal.Text.ToString());
            int count2 = Stage.Length + 1;
            // dgrData.Columns[8].Visible = false;
            int dem = count;
            while (i2 < count2)
            {
                dgrGraph.ColumnCount = count2;
                dgrGraph.Columns[i2].Width = (dgrGraph.Width + 70) / count2;
                dgrGraph.Columns[i2].Name = "";
                i2++;
            }
            dgrGraph.Columns[0].Width = 60;
            while (i3 <= count)
            {
                dgrGraph.Rows.Add(dem.ToString(), "", "", "", "", "", "", "", "", "");
                dgrGraph.Rows[i3].Height = dgrGraph.Height / (count + 1);
                i3++;
                dem--;
                dgrGraph.ClearSelection();
            }
            dgrGraph.Rows[count].Cells[1].Value = Stage[0];
            dgrGraph.Rows[count].Cells[2].Value = Stage[1];
            dgrGraph.Rows[count].Cells[3].Value = Stage[2];
            dgrGraph.Rows[count].Cells[4].Value = Stage[3];
            dgrGraph.Rows[count].Cells[5].Value = Stage[4];
            dgrGraph.Rows[count].Cells[6].Value = Stage[5];
            dgrGraph.Rows[count].Cells[7].Value = Stage[6];
            dgrGraph.Rows[count].Cells[8].Value = Stage[7];
            dgrGraph.Rows[count].Cells[9].Value = Stage[8];
            dgrGraph.Rows[count].Cells[10].Value = Stage[9];
            Count_err = 0;
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
                if (confirm == "OK")
                {
                    string[] located = dgrData.Rows[i].Cells[4].Value.ToString().Split('-');
                    string err = dgrData.Rows[i].Cells[3].Value.ToString();
                    int cot = Cell_return(located[1]);
                    int hang = Check_Total(cot);
                    Fill_Color(err, int.Parse(lblErrTotal.Text) - hang, cot, true, false);
                    Count_err++;
                    dgrGraph.ClearSelection();
                }
            }
            lblErr.Text = Count_err.ToString();     // hien thi tong so loi thuc te
        }

        private int Check_Total(int location)
        {
            total_err = 0;
            for (int jk = dgrGraph.RowCount - 1; jk > 1; jk--)
            {
                if (dgrGraph.Rows[jk].Cells[cell].Value.ToString() != "") total_err++;
            }
            /*
            for (int jk = dgrGraph.RowCount - 2; jk > 1; jk--)
            {
                if (dgrGraph.Rows[jk].Cells[cell + 1].Value.ToString() != "")   total_err++;
            }
             */
            return total_err;
        }

        private void Error_Graph_Load(object sender, EventArgs e)
        {
            

            //Fill_Color("SA", 19, 3, true, true);
            //Fill_Color("AB", 18, 3, true, true);
            //Fill_Color("D_", 17, 3, true, true);
            //Fill_Color("D", 19, 5, true, true);
            //Fill_Color("SA", 19, 6, true, true);
        }

        private int Cell_return(String Location)
        {
            switch (Location)
            {
                case "T1.1":
                    cell = 1;
                    break;
                case "T1.2":
                    cell = 2;
                    break;
                case "T2.1":
                    cell = 3;
                    break;
                case "T2.2":
                    cell = 4;
                    break;
                case "T3.1":
                    cell = 5;
                    break;
                case "T3.2":
                    cell = 6;
                    break;
                case "T4.1":
                    cell = 7;
                    break;
                case "T4.2":
                    cell = 8;
                    break;
                case "T5.1":
                    cell = 9;
                    break;
                case "T5.2":
                    cell = 10;
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
        private void Fill_Color(String Err, int row, int col, bool state, bool modify)
        {
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
    }
}
