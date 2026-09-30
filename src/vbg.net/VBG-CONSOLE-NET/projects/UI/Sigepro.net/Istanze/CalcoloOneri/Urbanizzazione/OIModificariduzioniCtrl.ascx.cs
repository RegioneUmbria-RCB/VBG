using System;
using System.Data;
using System.Configuration;
using System.Collections;
using System.Web;
using System.Web.Security;
using System.Web.UI;
using System.Web.UI.WebControls;
using System.Web.UI.WebControls.WebParts;
using System.Web.UI.HtmlControls;
using Init.SIGePro.Manager;
using Init.SIGePro.Authentication;
using PersonalLib2.Data;
using Init.Utils.Web.UI;
using System.Collections.Generic;
using Init.SIGePro.Data;
using System.Drawing;

namespace Sigepro.net.Istanze.CalcoloOneri.Urbanizzazione
{
	public partial class OIModificariduzioniCtrl : System.Web.UI.UserControl
	{
		AuthenticationInfo m_authInfo = null;
		DataBase m_dataBase = null;

		public string Token
		{
			get { return Request.QueryString["Token"]; }
			
		}

		private string IdComune
		{
			get 
			{
				if (m_authInfo == null)
					Authenticate();

				return m_authInfo.IdComune;
			}
		}

		private DataBase Database
		{
			get { if (m_dataBase == null)Authenticate(); return m_dataBase; }
		}

		public DataSet DataSource
		{
			get { return (DataSet)Session[this.ClientID + "_dataSource"]; }
			set { Session[this.ClientID + "_dataSource"] = value; }
		}

		protected override void OnInit(EventArgs e)
		{
			DataBind();
		}

		protected void Page_Load(object sender, EventArgs e)
		{

		}

		private void Authenticate()
		{
			m_authInfo = AuthenticationManager.CheckToken(Token);
			m_dataBase = m_authInfo.CreateDatabase();
		}

		public override void DataBind()
		{
			dgCausali.DataSource = DataSource;
			dgCausali.DataBind();
		}

		protected void dgCausali_ItemDataBound(object sender, DataGridItemEventArgs e)
		{
			DataRowView row = (DataRowView)e.Item.DataItem;
			DataTable dt = DataSource.Tables[0];

			e.Item.Cells[0].Visible = false;

			if (e.Item.ItemType == ListItemType.Header)
			{
				for (int i = 0; i < dt.Columns.Count; i++)
				{
					string columnName = dt.Columns[i].ColumnName;
					TableCell currCell = e.Item.Cells[i];

					if (columnName.IndexOf("_note") != -1)
					{
						currCell.Visible = false;
					}
					else if (columnName == "descrizioneCausale")
					{
						currCell.Text = "Causale";
						currCell.Attributes.Add("width", "250px");
						//currCell.Width = new Unit( 250 , UnitType.Pixel);
					}
					else if (columnName == "percentuale")
					{
						currCell.Text = "Incremento/Riduzione";
						currCell.Attributes.Add("width", "100px");
						//currCell.Width = new Unit(100, UnitType.Pixel);
					}
					else if (columnName.IndexOf("_selezionato") != -1)
					{
						currCell.ColumnSpan = 3;
						int idTipoOnere = Convert.ToInt32(columnName.Replace("_selezionato", ""));
						currCell.Text = "Applica a " + new OTipiOneriMgr(Database).GetById(IdComune, idTipoOnere).Descrizione;
						currCell.HorizontalAlign = HorizontalAlign.Center;

						currCell.Attributes.Add("width", "100px");
						//currCell.Width = new Unit(100, UnitType.Pixel);
					}
					else if (columnName.IndexOf("_percentuale") != -1)
					{
						currCell.Visible = false;
					}

					if (currCell.Visible)
						currCell.Text = "<b>" + currCell.Text + "</b>";
				}
			}
			else if (e.Item.ItemType == ListItemType.Item || e.Item.ItemType == ListItemType.AlternatingItem)
			{
				int idxControllo = 0;

				for (int i = 0; i < dt.Columns.Count; i++)
				{
					string columnName = dt.Columns[i].ColumnName;
					TableCell currCell = e.Item.Cells[i];

					if (columnName.IndexOf("_note") != -1)
					{
						currCell.Visible = false;
					}
					else if (columnName.IndexOf("_selezionato") != -1)
					{
						currCell.Attributes.Add("width", "10px");
						//currCell.Width = new Unit(20, UnitType.Pixel);

						CheckBox cb = new CheckBox();
						cb.ID = idxControllo + "_CheckBox";
						cb.Checked = Convert.ToBoolean(row[columnName]);

						HiddenField hf = new HiddenField();
						hf.ID = idxControllo + "_HiddenField";
						hf.Value = row["percentuale"].ToString();

						currCell.Controls.Clear();
						currCell.Controls.Add(cb);
						currCell.Controls.Add(hf);
					}
					else if (columnName == "percentuale")
					{
						double percentuale = Convert.ToDouble(row[columnName]);
						currCell.Text = percentuale.ToString("N2") + "%";
						currCell.HorizontalAlign = HorizontalAlign.Right;
					}
					else if (columnName.IndexOf("_percentuale") != -1)
					{
						string idTipoOnere = columnName.Replace("_percentuale", "");
						currCell.Attributes.Add("width", "80px");
											

						// Updatepanel con textbox per l'immissione della percentuale
						DoubleTextBox dtb = new DoubleTextBox();
						dtb.ID = idxControllo + "_DoubleTextBox";
						dtb.ValoreDouble = Convert.ToDouble(row[columnName]);
						dtb.Columns = 9;

						ImageButton imgBtn = new ImageButton();
						imgBtn.ID = idxControllo + "_BtnNote";
						imgBtn.ImageUrl = "~/Images/edit.gif";

						Panel pnl = new Panel();
						pnl.Controls.Add(dtb);
						pnl.Controls.Add(imgBtn);
						pnl.Style.Add( HtmlTextWriterStyle.Width, "120px");

						OIModificaRiduzioniNote ctrlNote = new OIModificaRiduzioniNote();
						ctrlNote.ID = idxControllo + "_CtrlNote";
						ctrlNote.Titolo = row["descrizioneCausale"].ToString();
						ctrlNote.Text = row[idTipoOnere + "_note"].ToString();
						ctrlNote.AssociatedControlId = idxControllo + "_CheckBox";
						ctrlNote.ActivationControlId = imgBtn.ID;

						OIModificariduzioniVisualizzazioneExtender visExt = new OIModificariduzioniVisualizzazioneExtender();
						visExt.CheckBoxId = idxControllo + "_CheckBox";
						visExt.ImageButtonId = imgBtn.ID;
						visExt.DoubleTextBoxId = dtb.ID;
						visExt.Riduzione = Convert.ToDouble(row["percentuale"]);


						currCell.Controls.Clear();
						currCell.Controls.Add(pnl);
						currCell.Controls.Add(ctrlNote);
						currCell.Controls.Add(visExt);

						idxControllo++;
					}
				}
			}
		}


		public class OIModificariduzioniCtrlReturnValue
		{
			private int m_idCausale;
			private int m_idTipoOnere;
			private double m_importo;
			private string m_note;

			public string Note
			{
				get { return m_note; }
				set { m_note = value; }
			}

			public double Importo
			{
				get { return m_importo; }
				set { m_importo = value; }
			}

			public int IdTipoOnere
			{
				get { return m_idTipoOnere; }
				set { m_idTipoOnere = value; }
			}

			public int IdCausale
			{
				get { return m_idCausale; }
				set { m_idCausale = value; }
			}

			internal OIModificariduzioniCtrlReturnValue (int idCausale , int idTipoOnere , double importo , string note)
			{
				m_idCausale = idCausale;
				m_idTipoOnere = idTipoOnere;
				m_importo = importo;
				m_note = note;
			}
		}

		public List<OIModificariduzioniCtrlReturnValue> GetValoriModificati()
		{
			List<OIModificariduzioniCtrlReturnValue> rVal = new List<OIModificariduzioniCtrlReturnValue>();

			int maxIdxControllo = 0;
			List<int> listaTipiOnere = new List<int>();

			foreach (DataColumn col in DataSource.Tables[0].Columns)
			{
				if (col.ColumnName.IndexOf("_percentuale") != -1)
				{
					maxIdxControllo++;
					int idTipoOnere = Convert.ToInt32(col.ColumnName.Replace("_percentuale","") );
					listaTipiOnere.Add(idTipoOnere);
				}
			}

			foreach (DataGridItem item in dgCausali.Items)
			{
				for (int i = 0; i < maxIdxControllo; i++)
				{
					CheckBox cb = (CheckBox)item.FindControl(i + "_CheckBox");
					DoubleTextBox dtb = (DoubleTextBox)item.FindControl(i + "_DoubleTextBox");
					OIModificaRiduzioniNote txtNote = (OIModificaRiduzioniNote)item.FindControl(i + "_CtrlNote");
					

					if (!cb.Checked) continue;

					int idTipoOnere = listaTipiOnere[i];
					int idCausale = Convert.ToInt32(dgCausali.DataKeys[item.ItemIndex]);
					double importo = String.IsNullOrEmpty(dtb.Text) ? 0.0d : dtb.ValoreDouble;
					string note = txtNote.Text;

					rVal.Add(new OIModificariduzioniCtrlReturnValue(idCausale, idTipoOnere, importo,note));
				}
			}

			return rVal;
		}
	}
}