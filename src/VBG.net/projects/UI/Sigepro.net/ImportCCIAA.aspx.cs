using System;
using System.Data;
using System.Web.UI;
using System.Web.UI.HtmlControls;
using System.Web.UI.WebControls;
using Init.Utils;
using PersonalLib2.Data;
using SIGePro.Net.Data;
using SIGePro.Net.Navigation;

namespace SIGePro.Net
{
	/// <summary>
	/// Descrizione di riepilogo per WebForm1.
	/// </summary>
	public partial class ImportCCIAA : BasePage
	{


		protected string ConnectionString
		{
			get
			{
				object o = this.ViewState["ConnectionString"];
				return (o == null) ? String.Empty : o.ToString();
			}

			set { this.ViewState["ConnectionString"] = value; }
		}


		protected void Page_Load(object sender, EventArgs e)
		{
			if (! StringChecker.IsStringEmpty(Request.Form["ConnectionString"]))
				ConnectionString = Request.Form["ConnectionString"];
		}

		private void BtnAvvia_Click(object sender, ImageClickEventArgs e)
		{
			string err = String.Empty;

			LstLog.Items.Clear();
			SIGePro.Net.Data.DataTableReader dtr = new SIGePro.Net.Data.DataTableReader();

			try
			{
				DataBase db = new DataBase(ConnectionString, ProviderType.OleDb);
				db.Connection.Open();

				string Sql = "SELECT COUNT(*) AS CONTA FROM TMP_IMPORT";

				if (dtr.recAffected(Sql, db) == 0 || CkbDelete.Checked)
				{
					if (FlImport.PostedFile.FileName != String.Empty)
					{
						IDbCommand cmd = db.CreateCommand("DELETE FROM TMP_IMPORT");
						int NumRec = cmd.ExecuteNonQuery();

						cmd.Connection.Close();
						cmd.Dispose();

						LstLog.Items.Add("...Sono stati cancellati " + NumRec + " records nella tabella TMP_IMPORT...");
						err = TmpImport();
					}
					else
					{
						//Page.RegisterStartupScript("","<script language'Javascript'>alert('Attenzione, la tabella temporanea d\'importazione è vuota, è necessario selezionare il file di impotrazione !!');</script>");
						LstLog.Items.Add("'Attenzione, la tabella temporanea d\'importazione è vuota, è necessario selezionare il file di impotrazione !!'");
						return;
					}
				}

				db.Connection.Close();
				db.Dispose();

				if (StringChecker.IsStringEmpty(err))
					RunImport();
				else
				{
					LstLog.Items.Add(err);
				}

			}
			catch (Exception ex)
			{
				LstLog.Items.Add(ex.Message);
				return;
			}
		}

		private string TmpImport()
		{
			//string FilePath = FlImport.PostedFile.FileName;
			string retVal = String.Empty;
			DataImport im = new DataImport();

			im.ConnectionString = ConnectionString;
			//im.FilePath = FilePath;
			im.IsStepFirstRow = CkbSaltaPrimaRiga.Checked;

			try
			{
				LblRunTime.Text = "...Importazione dati su TMP_IMPORT...";
				LstLog.Items.Add("...Sono stati importati " + im.RunTmp(FlImport.PostedFile.InputStream) + " records nella tabella TMP_IMPORT...");
			}
			catch (Exception e)
			{
				retVal = e.Message;
			}
			LblRunTime.Text = "";
			return retVal;
		}

		private void RunImport()
		{
			DataBase db = new DataBase(ConnectionString, ProviderType.OleDb);
			db.Connection.Open();

			IDbCommand cmd = db.CreateCommand("DELETE FROM T_IMPR_IMPRESA");

			SIGePro.Net.Data.DataTableReader dtr = new SIGePro.Net.Data.DataTableReader();

			string Sql = "SELECT COUNT(*) AS CONTA FROM T_IMPR_IMPRESA";
			int NumRec = 0;

			if (dtr.recAffected(Sql, db) == 0 || CkbDelete.Checked)
			{
				LstLog.Items.Add("...Sono stati cancellati " + cmd.ExecuteNonQuery() + " records nella tabella T_IMPR_IMPRESA...");

				IMPR_IMPRESA imp = new IMPR_IMPRESA();

				imp.CONNECTIONSTRING = ConnectionString;
				imp.PROVIDER = ProviderType.OleDb;

				LblRunTime.Text = "...Importazione dati su T_IMPR_IMPRESA...";
				NumRec = imp.Insert(db);
			}
			LstLog.Items.Add("...Sono stati importati " + NumRec + " records nella tabella T_IMPR_IMPRESA...");

			Sql = "SELECT COUNT(*) AS CONTA FROM T_UNLO_UNITALOCALE";
			if (dtr.recAffected(Sql, db) == 0 || CkbDelete.Checked)
			{
				cmd.CommandText = "DELETE FROM T_UNLO_UNITALOCALE";
				LstLog.Items.Add("...Sono stati cancellati " + cmd.ExecuteNonQuery() + " records nella tabella T_UNLO_UNITALOCALE...");
				UNLO_UNITALOCALE ul = new UNLO_UNITALOCALE();
				LblRunTime.Text = "...Importazione dati su UNLO_UNITALOCALE...";
				NumRec = ul.Insert(ConnectionString, db);
			}
			LstLog.Items.Add("...Sono stati importati " + NumRec + " records nella tabella T_UNLO_UNITALOCALE...");
			LblRunTime.Text = "";
			LstLog.Items.Add("...Importazione terminata correttamente");

			cmd.Connection.Close();
			cmd.Dispose();
			db.Connection.Close();
			db.Dispose();
		}

		private void BtnEsci_Click(object sender, ImageClickEventArgs e)
		{
			base.CloseCurrentPage(); 
		}
	}
}