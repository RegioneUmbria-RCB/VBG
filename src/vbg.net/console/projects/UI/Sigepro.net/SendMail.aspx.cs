using System;
using System.Collections;
using System.Data;
using System.Web.UI;
using System.Web.UI.WebControls;
using Init.SIGePro.Data;
using Init.SIGePro.Manager;
using Init.SIGePro.Verticalizzazioni;
using Init.Utils;
using PersonalLib2.Data;
using SIGePro.Net.WebServices.WSSIGeProSmtpMail;
using Init.SIGePro.Manager.Logic.SmtpMail;

namespace SIGePro.Net
{
	/// <summary>
	/// Descrizione di riepilogo per SendMail.
	/// </summary>
	public partial class SendMail : BasePage
	{


		protected string CodiceIstanza
		{
			get { return StringChecker.IsStringEmpty(Request.QueryString["CodiceIstanza"]) ? String.Empty : Request.QueryString["CodiceIstanza"].ToString(); }
			//get { return "280"; }
		}

		protected string CodiceMovimento
		{
			get { return StringChecker.IsStringEmpty(Request.QueryString["CodiceMovimento"]) ? String.Empty : Request.QueryString["CodiceMovimento"].ToString(); }
			//get { return "577"; }
		}

		protected string CodiceAmministrazione
		{
			get { return StringChecker.IsStringEmpty(Request.QueryString["CodiceAmministrazione"]) ? String.Empty : Request.QueryString["CodiceAmministrazione"].ToString(); }
			//get { return "3"; }
		}

		protected string CodiceUfficio
		{
			get { return StringChecker.IsStringEmpty(Request.QueryString["CodiceUfficio"]) ? String.Empty : Request.QueryString["CodiceUfficio"].ToString(); }
		}


//		protected string Software
//		{
//			get { return StringChecker.IsStringEmpty(Request.QueryString["Software"]) ? String.Empty : Request.QueryString["Software"].ToString(); }
//			//get { return "CO"; }
//		}


		protected void Page_Load(object sender, EventArgs e)
		{
            if (Software == null || String.IsNullOrEmpty(Software))
				throw new ArgumentException("Il parametro \"Software\" non è stato valorizzato");

			if (! IsPostBack)
			{
				this.Close.Attributes.Add("OnClick", "_Close(); return false;");
				Populate();
			}
		}

		private void Populate()
		{
			#region 1. Prendo il mittente della mail

			if (StringChecker.IsStringEmpty(this.txtFrom.Text))
			{
				ConfigurazioneMgr cfgMgr = new ConfigurazioneMgr(Database);
				Configurazione cfg = cfgMgr.GetById(AuthenticationInfo.IdComune, "TT");
				this.txtFrom.Text = cfg.EMAIL;
			}

			#endregion

			#region 2. Popolo la combo con le mail tipo

			if (this.ddlMailTipo.Items.Count == 0)
			{
				this.ddlMailTipo.DataTextField = "DESCRIZIONE";
				this.ddlMailTipo.DataValueField = "CODICEMAIL";

				this.ddlMailTipo.DataSource = ListaMailTipo();
				this.ddlMailTipo.DataBind();

				this.ddlMailTipo.Items.Insert(0, new ListItem());
			}

			#endregion

			#region 3. Recupero il destinatario

			if (StringChecker.IsStringEmpty(CodiceMovimento))
				RecuperaDestinatarioDaIstanza();
			else
				RecuperaDestinatarioDaMovimento();

			#endregion

			#region 4. Recupero gli allegati

			if (this.chkAttachments.Items.Count == 0)
			{
				this.chkAttachments.DataTextField = "ALLEGATO";
				this.chkAttachments.DataValueField = "CODICEOGGETTO";
				this.chkAttachments.DataSource = ListaAllegatiMovimenti();
				this.chkAttachments.DataBind();

			}

			for (int i = 0; i < this.chkAttachments.Items.Count; i++)
			{
				ListItem item = (ListItem) this.chkAttachments.Items[i];
				item.Selected = true;
			}

			#endregion
		}


		private void RecuperaDestinatarioDaMovimento()
		{
            if (!String.IsNullOrEmpty(CodiceMovimento) && String.IsNullOrEmpty(this.txtTo.Text))
			{
				#region 1. Ricerco prima per ufficio ( se passato )

				if (! StringChecker.IsStringEmpty(CodiceUfficio))
				{
					AmministrazioniReferentiMgr ammRefMgr = new AmministrazioniReferentiMgr(Database);
					AmministrazioniReferenti ammRef = ammRefMgr.GetById(AuthenticationInfo.IdComune, CodiceUfficio);

					if (ammRef != null)
						this.txtTo.Text = ammRef.EMAIL;
				}

				#endregion

				#region 2. Ricerco eventualmente per il codice amministrazione ( se passato )

                if (String.IsNullOrEmpty(this.txtTo.Text) && !String.IsNullOrEmpty(CodiceAmministrazione))
				{
					var ammMgr = new AmministrazioniMgr(Database);
                    var amm = ammMgr.GetById(AuthenticationInfo.IdComune, Convert.ToInt32(CodiceAmministrazione));

					if (amm != null)
						this.txtTo.Text = amm.EMAIL;
				}

				#endregion

				#region 3. Ricerco per il richiedente dell istanza ( se passata )

				if (StringChecker.IsStringEmpty(this.txtTo.Text) && ! StringChecker.IsStringEmpty(CodiceIstanza))
				{
					IstanzeMgr istMgr = new IstanzeMgr(Database);
                    Istanze ist = istMgr.GetById(AuthenticationInfo.IdComune, Convert.ToInt32(CodiceIstanza));

					if (ist != null)
					{
						AnagrafeMgr anagMgr = new AnagrafeMgr(Database);
						Anagrafe anag = anagMgr.GetById(ist.CODICERICHIEDENTE, AuthenticationInfo.IdComune);

						if (anag != null)
							this.txtTo.Text = anag.EMAIL;
					}
				}

				#endregion
			}
		}

		private void RecuperaDestinatarioDaIstanza()
		{
            if (!String.IsNullOrEmpty(CodiceIstanza) && String.IsNullOrEmpty(this.txtTo.Text))
			{
				#region 1. Ricerco per il richiedente dell istanza ( se passata )

				if (StringChecker.IsStringEmpty(this.txtTo.Text) && ! StringChecker.IsStringEmpty(CodiceIstanza))
				{
					IstanzeMgr istMgr = new IstanzeMgr(Database);
                    Istanze ist = istMgr.GetById(AuthenticationInfo.IdComune, Convert.ToInt32(CodiceIstanza));

					if (ist != null)
					{
						AnagrafeMgr anagMgr = new AnagrafeMgr(Database);
						Anagrafe anag = anagMgr.GetById(ist.CODICERICHIEDENTE, AuthenticationInfo.IdComune);

						if (anag != null)
							this.txtTo.Text = anag.EMAIL;
					}
				}

				#endregion
			}
		}

		private DataSet ListaMailTipo()
		{
			DataSet retVal = new DataSet();

			IDbConnection conn = Database.Connection;

			string sql = "SELECT CODICEMAIL, DESCRIZIONE FROM MAILTIPO WHERE IDCOMUNE = '" + AuthenticationInfo.IdComune + "' AND SOFTWARE = '" + Software + "' ORDER BY DESCRIZIONE";

			DataProviderFactory dpf = new DataProviderFactory(conn);
			IDataAdapter adapter = dpf.CreateDataAdapter(sql, conn);

			adapter.Fill(retVal);

			return retVal;
		}

		private DataSet ListaAllegatiMovimenti()
		{
			DataSet retVal = new DataSet();
			using (IDbConnection conn = Database.Connection)
			{
				if (conn.State == ConnectionState.Closed)
					conn.Open();

				DataProviderFactory dpf = new DataProviderFactory(conn);

				string sql = "SELECT MOVIMENTIALLEGATI.CODICEOGGETTO as CODICEOGGETTO, MOVIMENTIALLEGATI.DESCRIZIONE || ' ( ' || OGGETTI.NOMEFILE || ' ) ' AS ALLEGATO FROM MOVIMENTIALLEGATI, OGGETTI WHERE OGGETTI.IDCOMUNE = MOVIMENTIALLEGATI.IDCOMUNE AND OGGETTI.CODICEOGGETTO = MOVIMENTIALLEGATI.CODICEOGGETTO AND MOVIMENTIALLEGATI.IDCOMUNE = '" + AuthenticationInfo.IdComune + "' AND MOVIMENTIALLEGATI.CODICEMOVIMENTO = " + CodiceMovimento;
				IDataAdapter adapter = dpf.CreateDataAdapter(sql, conn);

				adapter.Fill(retVal);

				sql = @"
					SELECT 
					DOCUMENTIISTANZA.CODICEOGGETTO as CODICEOGGETTO,
					DOCUMENTIISTANZA.NOMEDOCUMENTO || ' ( ' || OGGETTI.NOMEFILE || ' ) ' AS ALLEGATO
					FROM
					DOCUMENTIISTANZA , OGGETTI
					WHERE 
					OGGETTI.IDCOMUNE = DOCUMENTIISTANZA.IDCOMUNE AND 
					OGGETTI.CODICEOGGETTO = DOCUMENTIISTANZA.CODICEOGGETTO AND 
					DOCUMENTIISTANZA.IDCOMUNE = '" + AuthenticationInfo.IdComune + @"' AND 
					DOCUMENTIISTANZA.CODICEISTANZA = " + CodiceIstanza;

				using (IDbCommand cmd = conn.CreateCommand())
				{
					cmd.CommandText = sql;

					using (IDataReader reader = cmd.ExecuteReader())
					{
						while (reader.Read())
						{
							DataRow newRow = retVal.Tables[0].NewRow();

							newRow["CODICEOGGETTO"] = reader["CODICEOGGETTO"];
							newRow["ALLEGATO"] = reader["ALLEGATO"];

							retVal.Tables[0].Rows.Add(newRow);
						}
					}
				}

				return retVal;
			}
		}

		protected void ddlMailTipo_SelectedIndexChanged(object sender, EventArgs e)
		{
			if (! StringChecker.IsStringEmpty(this.ddlMailTipo.SelectedValue))
			{
				MailTipoMgr mailMgr = new MailTipoMgr(Database);
				MailTipo mail = mailMgr.GetById(this.ddlMailTipo.SelectedValue, AuthenticationInfo.IdComune);

				this.txtSubject.Text = mail.OGGETTO; //MailUtils.SostituisciTesto( mail.OGGETTO , CodiceMovimento , CodiceIstanza );
				this.txtBody.Text = mail.CORPO; //MailUtils.SostituisciTesto( mail.CORPO , CodiceMovimento , CodiceIstanza );
			}
			else
			{
				this.txtSubject.Text = String.Empty;
				this.txtBody.Text = String.Empty;
			}
		}

		private void Send_Click(object sender, ImageClickEventArgs e)
		{

			#region 2 Messaggio

			SIGeProMailMessage message = new SIGeProMailMessage();
			message.DestinatariInCopiaNascosta = this.txtBCC.Text;
			message.CorpoMail = this.txtBody.Text;
			message.DestinatariInCopia = this.txtCC.Text;
			message.Oggetto = this.txtSubject.Text;
			message.Destinatari = this.txtTo.Text;

			#endregion

			#region 3. Attachments

			ArrayList arAllegati = new ArrayList();
			foreach (ListItem item in this.chkAttachments.Items)
			{
				if (item.Selected)
				{
					BinaryObject obj = new BinaryObject();

					if (! StringChecker.IsStringEmpty(item.Value))
					{
						OggettiMgr oggMgr = new OggettiMgr(Database);
						Oggetti ogg = oggMgr.GetById(AuthenticationInfo.IdComune, Convert.ToInt32(item.Value));

						int LenOggetto = ogg.OGGETTO.Length;
						byte[] newOggetto = new byte[LenOggetto - 1];
						for (int i = 0; i <= LenOggetto - 2; i++)
							newOggetto[i] = ogg.OGGETTO[i];

						obj.FileContent = newOggetto;
						obj.FileName = ogg.NOMEFILE;

					}
					arAllegati.Add(obj);
				}
			}

			if (arAllegati.Count > 0)
			{
				BinaryObject[] allegati = new BinaryObject[arAllegati.Count];
				for (int i = 0; i < arAllegati.Count; i++)
					allegati[i] = (arAllegati[i] as BinaryObject);

				message.Attachments = allegati;
			}

			#endregion

			#region 4. Invio

			//string webServiceUrl = "~/WebServices/WsSIGeProSmtpMail/SmtpMailSender.asmx";// new VerticalizzazioneSmtpMailer(IdComune,Software).WsSmtpMailUrl;
			SmtpMailSender SMTPms = new SmtpMailSender();
			//SMTPms.Url = webServiceUrl;
			//SMTPms.Send(  message, cnInfo );

			#endregion

			#region 5. Chiudo il pop up

			Page.ClientScript.RegisterStartupScript(typeof(string) , "onLoad", "self.close();" , true);

			#endregion
		}

	}
}