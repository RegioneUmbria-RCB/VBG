using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Web.UI;
using System.Web.UI.WebControls;
using System.Web;
using Init.SIGePro.DatiDinamici.Interfaces;
using System.Reflection;
using System.ComponentModel;
using Init.SIGePro.DatiDinamici.Interfaces.WebControls;
using System.Web.UI.HtmlControls;
using Init.SIGePro.DatiDinamici.Properties;

[assembly: WebResource("Init.SIGePro.DatiDinamici.WebControls.Js.AjaxCallManager.js", "text/javascript")]
[assembly: WebResource("Init.SIGePro.DatiDinamici.WebControls.Js.D2FocusManager.js", "text/javascript")]
[assembly: WebResource("Init.SIGePro.DatiDinamici.WebControls.Js.D2PannelloErrori.js", "text/javascript")]
[assembly: WebResource("Init.SIGePro.DatiDinamici.WebControls.Js.GetterSetterDatiDinamici.js", "text/javascript")]
[assembly: WebResource("Init.SIGePro.DatiDinamici.WebControls.Js.DatiDinamiciExtender.js", "text/javascript")]
[assembly: WebResource("Init.SIGePro.DatiDinamici.WebControls.Js.DescrizioneControllo.js", "text/javascript")]
[assembly: System.Web.UI.WebResource("Init.SIGePro.DatiDinamici.WebControls.Js.jquery.uploadDatiDinamici.js", "text/javascript")]
[assembly: System.Web.UI.WebResource("Init.SIGePro.DatiDinamici.WebControls.Js.jQuery.searchDatiDinamici.js", "text/javascript")]
[assembly: System.Web.UI.WebResource("Init.SIGePro.DatiDinamici.WebControls.Js.jQuery.resetValues.js", "text/javascript")]

[assembly: WebResource("Init.SIGePro.DatiDinamici.WebControls.help-icon.png", "image/png")]

namespace Init.SIGePro.DatiDinamici.WebControls
{
	[ControlValueProperty("Valore")]
	public abstract partial class DatiDinamiciBaseControl<T> : WebControl, IDatiDinamiciControl, INamingContainer where T : WebControl, new()
	{

        private static class Constants
        {
            public const string NomeCampoAttribute = "data-nome-campo";
            public const string NotificaValoreDecodificatoAttribute = "data-notifica-valore-decodificato";
            public const string EventoModificaAttribute = "data-evento-modifica";
        }

		#region properties

		public int Indice { get; set; }
		public int NumeroRiga { get; set; }
		public abstract string Valore { get; set; }

		protected IDyn2DataAccessProvider DataAccessProvider { get; private set; }
		protected bool IgnoraRegistrazioneJavascript{get;set;}
		protected T InnerControl{get;set;}
		
		private Image ImgDescrizione { get;set; }
		private Literal _testoDescrizione { get; set; }
		private Panel _pnlDescrizione { get; set; }
		

		public string Descrizione
		{
			get { return this._testoDescrizione.Text; }
			private set { this._testoDescrizione.Text= value; }
		}
		public string IdComune
		{
			get { return HttpContext.Current.Items["IdComune"].ToString(); }
		}
		public string Software
		{
			get { return HttpContext.Current.Items["Software"].ToString(); }
		}

		public int IdCampoCollegato
		{
			get { object o = this.ViewState["IdCampoCollegato"]; return o == null ? -1 : (int)o; }
			private set { this.ViewState["IdCampoCollegato"] = value; }
		}

		public bool RichiedeNotificaSuModificaValoreDecodificato
		{
			get { object o = this.ViewState["RichiedeNotificaSuModificaValoreDecodificato"]; return o == null ? false : (bool)o; }
			set { this.ViewState["RichiedeNotificaSuModificaValoreDecodificato"] = value; }
		}

        public string NomeCampo
        {
            get { object o = this.ViewState["NomeCampo"]; return o == null ? String.Empty : o.ToString(); }
            set { this.ViewState["NomeCampo"] = value.ToString(); }
        }

		#endregion

		#region Costruttori

		public DatiDinamiciBaseControl(CampoDinamicoBase campo):this()
		{
			this.IdCampoCollegato = campo.Id;
			this.Descrizione = campo.Descrizione;
			this.DataAccessProvider = campo.ModelloCorrente.DataAccessProvider;
            this.NomeCampo = new ControlSafeNomeCampo(campo.NomeCampo).ToString();

			InizializzaPropertiesDaCampoDinamico(campo);
		}

		protected DatiDinamiciBaseControl()
		{
			IgnoraRegistrazioneJavascript = false;

			InnerControl = new T{
				ID = "_InnerControl"
			};

			ImgDescrizione = new Image
			{
				ID = "_imgDescrizione"
			};

			
			ImgDescrizione.Style.Add("width", "16px");
			ImgDescrizione.Style.Add("height", "16px");
			ImgDescrizione.Style.Add("vertical-align", "sub");
			ImgDescrizione.Style.Add("margin-left", "2px");
			

			this._testoDescrizione = new Literal();

			this._pnlDescrizione = new Panel{
				ID = "_descrizione",
				CssClass = "descrizioneCampoDinamico"
			};

			this._pnlDescrizione.Controls.Add(this._testoDescrizione);
			
			this.Controls.Add(InnerControl);
			this.Controls.Add(ImgDescrizione);
			this.Controls.Add(this._pnlDescrizione);
		}

		#endregion

		protected override void OnInit(EventArgs e)
		{
			base.OnInit(e);

			string jsKey = "RegistrazioneScript";

			if (Settings.Default.UsaJavascriptEmbedded && !this.Page.ClientScript.IsClientScriptIncludeRegistered(typeof(IDatiDinamiciControl), jsKey + "1"))
			{
				// Referenzio gli script javascript
				var listaFilesJs = new string[]{
					"Init.SIGePro.DatiDinamici.WebControls.Js.AjaxCallManager.js",
					"Init.SIGePro.DatiDinamici.WebControls.Js.D2FocusManager.js",
					"Init.SIGePro.DatiDinamici.WebControls.Js.D2PannelloErrori.js",
					"Init.SIGePro.DatiDinamici.WebControls.Js.GetterSetterDatiDinamici.js",
					"Init.SIGePro.DatiDinamici.WebControls.Js.DatiDinamiciExtender.js",
					"Init.SIGePro.DatiDinamici.WebControls.Js.DescrizioneControllo.js",
					"Init.SIGePro.DatiDinamici.WebControls.Js.jQuery.searchDatiDinamici.js",
					"Init.SIGePro.DatiDinamici.WebControls.Js.jquery.uploadDatiDinamici.js",
					"Init.SIGePro.DatiDinamici.WebControls.Js.jQuery.resetValues.js",
				};

				for (var i = 0; i < listaFilesJs.Length ; i++)
				{
		 			this.Page.ClientScript.RegisterClientScriptInclude(
						typeof(IDatiDinamiciControl), jsKey + i.ToString(),
						Page.ClientScript.GetWebResourceUrl(typeof(IDatiDinamiciControl),
						listaFilesJs[i] ));
				}

			}
		}

		protected override void OnPreRender(EventArgs e)
		{
			this.ImgDescrizione.Visible = !String.IsNullOrEmpty(this.Descrizione);
			this._pnlDescrizione.Visible = ImgDescrizione.Visible;

			this.InnerControl.CssClass = "d2Control " + GetNomeTipoControllo();
			this.InnerControl.Attributes.Add("data-d2id", IdCampoCollegato.ToString());
			this.InnerControl.Attributes.Add("data-d2tipo", GetNomeTipoControllo());
			this.InnerControl.Attributes.Add("data-d2indice", Indice.ToString());
			this.InnerControl.Attributes.Add(Constants.EventoModificaAttribute, GetNomeEventoModifica());
			this.InnerControl.Attributes.Add("data-d2clientid", this.ClientID);
			this.InnerControl.Attributes.Add(Constants.NotificaValoreDecodificatoAttribute, this.RichiedeNotificaSuModificaValoreDecodificato.ToString());
            this.InnerControl.Attributes.Add(Constants.NomeCampoAttribute, this.NomeCampo);

			if (ImgDescrizione.Visible)
			{
				ImgDescrizione.ImageUrl = Page.ClientScript.GetWebResourceUrl(this.GetType(), @"Init.SIGePro.DatiDinamici.WebControls.help-icon.png");
				ImgDescrizione.CssClass = "helpIcon";
			}

			base.OnPreRender(e);
		}

		/// <summary>
		/// Restituisce il nome del tipo di controllo (ad esempio text, intTextBox, etc)
		/// </summary>
		/// <returns></returns>
		protected abstract string GetNomeTipoControllo();


		/// <summary>
		/// Restituisce il nome dell'evento che causa la modifica del campo
		/// </summary>
		/// <returns></returns>
		protected virtual string GetNomeEventoModifica()
		{
			return "blur";
		}

		protected void NascondiIconaHelp()
		{
			this.ImgDescrizione.Visible = false;
			this._pnlDescrizione.Visible = false;
		}

		protected override void Render(HtmlTextWriter writer)
		{
			base.RenderChildren(writer);
		}

		private void InizializzaPropertiesDaCampoDinamico(CampoDinamicoBase campo)
		{
			MethodInfo mi = this.GetType().GetMethod("GetProprietaDesigner", BindingFlags.Static | BindingFlags.Public);

			ProprietaDesigner[] proprieta = (ProprietaDesigner[])mi.Invoke(null, null);

			// Salvo i valori di default in un dictionary
			Dictionary<string, object> propDictionary = new Dictionary<string, object>();

			for (int i = 0; i < proprieta.Length; i++)
				propDictionary.Add(proprieta[i].NomeProprieta, proprieta[i].ValoreDefault);

			// Assegno al dictionary delle proprietà i valori letti dal db
			foreach (KeyValuePair<string, string> it in campo.ProprietaControlloWeb)
			{
				if (!propDictionary.ContainsKey(it.Key))
					propDictionary.Add(it.Key, it.Value);
				else
					propDictionary[it.Key] = it.Value;
			}

			// Assegno i valori al controllo
			PropertyDescriptorCollection propCollection = TypeDescriptor.GetProperties(this);

			foreach (string key in propDictionary.Keys)
			{
				var nomeProperty = key;
				var value = propDictionary[key];

				// HACK: data la separazione dei progetti non è possibile assegnarla nel campo dinamico (anche se sarebbe più corretto)
				if (nomeProperty == "TipoNumerico" && campo is CampoDinamico)
				{
					(campo as CampoDinamico).TipoNumerico = Convert.ToBoolean(value);
					continue;
				}

                try
                {
                    PropertyDescriptor pd = propCollection[nomeProperty];

                    if (pd != null)
                        pd.SetValue(this, Convert.ChangeType(value, pd.PropertyType));
                }
                catch (Exception ex)
                {
                    var errMsg = String.Format("Errore durante l'assegnazione del valore \"{0}\" proprietà {1} al campo {2}: {3}", value , nomeProperty, campo.Id, ex.Message);
                    throw new Exception(errMsg, ex);
                }

			}
		}
	}
}
