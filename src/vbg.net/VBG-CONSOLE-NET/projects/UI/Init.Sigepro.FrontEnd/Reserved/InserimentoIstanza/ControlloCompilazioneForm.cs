using System;
using System.Data;
using System.Configuration;
using System.Web;
using System.Web.Security;
using System.Web.UI;
using System.Web.UI.WebControls;
using System.Web.UI.WebControls.WebParts;
using System.Web.UI.HtmlControls;
using Init.Sigepro.FrontEnd.LR13.Utils;
using Init.Sigepro.FrontEnd.AppLogic.ObjectSpace.PresentazioneIstanza;
using System.Reflection;
using Init.Sigepro.FrontEnd.AppLogic.Autenticazione;
using System.Collections.Generic;
using Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Vbg;

namespace Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza
{
	public class ControlloCompilazioneForm : UserControl
	{
		public string IdComune
		{
			get { object o = this.ViewState["IdComune"]; return o == null ? String.Empty : (string)o; }
			set { this.ViewState["IdComune"] = value; }
		}

		public string Software
		{
			get { object o = this.ViewState["Software"]; return o == null ? String.Empty : (string)o; }
			set { this.ViewState["Software"] = value; }
		}

		DatiCompilazioneForm m_datiCompilazioneForm;

		public DatiCompilazioneForm DatiCompilazioneForm
		{
			get { return m_datiCompilazioneForm; }
			set { m_datiCompilazioneForm = value; }
		}


		PresentazioneIstanzaDataSet m_dataSource;

		public PresentazioneIstanzaDataSet DataSource
		{
			get { return m_dataSource; }
			set { m_dataSource = value; }
		}

		public UserAuthenticationResult UserAuthenticationResult
		{
			get { return HttpContext.Current.Items["UserAuthenticationResult"] as UserAuthenticationResult; }
		}

		protected void BindaControlliInterni()
		{
			for (int i = 0; i < DatiCompilazioneForm.MappaturaDati.Length; i++)
			{
				string val = DataSource.DatiDinamici.GetValoreCampo( /*DatiCompilazioneForm.IdModelloDinamico*/-1 , DatiCompilazioneForm.MappaturaDati[i].IdCampoDinamico );

				if (String.IsNullOrEmpty(val))
					continue;

				Control ctrl = this.FindControl(DatiCompilazioneForm.MappaturaDati[i].Controllo);

				if (ctrl != null)
				{
					PropertyInfo pi = ctrl.GetType().GetProperty(DatiCompilazioneForm.MappaturaDati[i].ProprietaValore);

					if (pi != null)
						pi.SetValue(ctrl, Convert.ChangeType(val, pi.PropertyType), null);
				}
			}
		}

		public void SalvaDati( )
		{
			DataSource.DatiDinamici.Clear();

			for (int i = 0; i < DatiCompilazioneForm.MappaturaDati.Length; i++)
			{
				Control ctrl = this.FindControl(DatiCompilazioneForm.MappaturaDati[i].Controllo);

				PropertyInfo pi = ctrl.GetType().GetProperty(DatiCompilazioneForm.MappaturaDati[i].ProprietaValore);

				if (pi != null)
				{
					string value = pi.GetValue(ctrl, null).ToString();
					//DataSource.DatiDinamici.AddDatiDinamiciRow(DatiCompilazioneForm.IdModelloDinamico, DatiCompilazioneForm.MappaturaDati[i].IdCampoDinamico, value);
					DataSource.DatiDinamici.AddDatiDinamiciRow(-1, DatiCompilazioneForm.MappaturaDati[i].IdCampoDinamico, value);
				}
			}
		}

		public virtual List<string> EffettuaValidazione()
		{
			return new List<string>();
		}
	}
}
