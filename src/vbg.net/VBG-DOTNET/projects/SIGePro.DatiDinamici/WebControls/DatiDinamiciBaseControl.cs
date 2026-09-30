using Init.SIGePro.DatiDinamici.Properties;
using Ninject.Web.Common;
using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Reflection;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using VBG.DatiDinamici;
using VBG.DatiDinamici.Interfaces;
using VBG.DatiDinamici.Interfaces.WebControls;
using VBG.DatiDinamici.Utils;
using VBG.DatiDinamici.WebControls;

[assembly: WebResource("Init.SIGePro.DatiDinamici.WebControls.Js.D2FocusManager.js", "text/javascript")]
[assembly: WebResource("Init.SIGePro.DatiDinamici.WebControls.Js.D2PannelloErrori.js", "text/javascript")]
[assembly: WebResource("Init.SIGePro.DatiDinamici.WebControls.Js.GetterSetterDatiDinamici.js", "text/javascript")]
[assembly: WebResource("Init.SIGePro.DatiDinamici.WebControls.Js.DatiDinamiciExtender.js", "text/javascript")]
[assembly: WebResource("Init.SIGePro.DatiDinamici.WebControls.Js.DescrizioneControllo.js", "text/javascript")]
[assembly: System.Web.UI.WebResource("Init.SIGePro.DatiDinamici.WebControls.Js.jquery.uploadDatiDinamici.js", "text/javascript")]
[assembly: System.Web.UI.WebResource("Init.SIGePro.DatiDinamici.WebControls.Js.jQuery.searchDatiDinamici.js", "text/javascript")]
[assembly: System.Web.UI.WebResource("Init.SIGePro.DatiDinamici.WebControls.Js.CampoDinamicoEagle.js", "text/javascript")]

[assembly: WebResource("Init.SIGePro.DatiDinamici.WebControls.help-icon.png", "image/png")]

namespace Init.SIGePro.DatiDinamici.WebControls
{
    public interface DatiDinamiciWebControl
    {

    }



    [ControlValueProperty("Valore")]
    public abstract partial class DatiDinamiciBaseControl<T> : Ninject.Web.WebControlBase, IDatiDinamiciControl, INamingContainer where T : WebControl, new()
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

        //public int? IdRiferimentoNote {
        //    get;
        //    set;
        //}

        protected IDyn2DataAccessFactory DataAccessFactory { get; }
        protected bool IgnoraRegistrazioneJavascript { get; set; }
        protected T InnerControl { get; set; }

        // private Image ImgDescrizione { get; set; }
        private Literal _testoDescrizione { get; set; }
        private Panel _pnlDescrizione { get; set; }


        public string Descrizione
        {
            get { return this._testoDescrizione.Text; }
            private set { this._testoDescrizione.Text = value; }
        }

        public string Note => this._pnlDescrizione.Controls.Count > 0 ? ((Literal)this._pnlDescrizione.Controls[0]).Text : String.Empty;

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

        public string Etichetta
        {
            get { object o = this.ViewState["Etichetta"]; return o == null ? String.Empty : o.ToString(); }
            set { this.ViewState["Etichetta"] = value ?? ""; }
        }

        #endregion

        #region Costruttori

        public DatiDinamiciBaseControl(CampoDinamicoBase campo) : this()
        {
            this.IdCampoCollegato = campo.Id;
            this.Descrizione = campo.Descrizione;
            this.DataAccessFactory = campo.DataAccessFactory;
            this.NomeCampo = new ControlSafeNomeCampo(campo.NomeCampo).ToString();
            this.Etichetta = campo.Etichetta;

            this.InizializzaPropertiesDaCampoDinamico(campo);


        }

        protected DatiDinamiciBaseControl()
        {
            this.IgnoraRegistrazioneJavascript = false;

            this.InnerControl = new T
            {
                ID = "_InnerControl"
            };

            /*
            ImgDescrizione = new Image
            {
                ID = "_imgDescrizione"
            };


            ImgDescrizione.Style.Add("width", "16px");
            ImgDescrizione.Style.Add("height", "16px");
            ImgDescrizione.Style.Add("vertical-align", "sub");
            ImgDescrizione.Style.Add("margin-left", "2px");
            */

            this._testoDescrizione = new Literal();

            this._pnlDescrizione = new Panel
            {
                ID = "_descrizione",
                CssClass = "descrizioneCampoDinamico"
            };

            this._pnlDescrizione.Controls.Add(this._testoDescrizione);

            this.Controls.Add(this.InnerControl);
            //Controls.Add(ImgDescrizione);
            this.Controls.Add(this._pnlDescrizione);


        }

        #endregion

        protected override void OnInit(EventArgs e)
        {
            // Schifezza!!!
            new Bootstrapper().Kernel.Inject(this);

            //NinjectDIProvider.Kernel.Inject(this);

            base.OnInit(e);

            string jsKey = "RegistrazioneScript";
            var regType = typeof(DatiDinamiciWebControl);

            if (Settings.Default.UsaJavascriptEmbedded && !this.Page.ClientScript.IsClientScriptIncludeRegistered(regType, jsKey + "1"))
            {
                // Referenzio gli script javascript
                var listaFilesJs = new string[]{
                    "Init.SIGePro.DatiDinamici.WebControls.Js.D2FocusManager.js",
                    "Init.SIGePro.DatiDinamici.WebControls.Js.D2PannelloErrori.js",
                    "Init.SIGePro.DatiDinamici.WebControls.Js.GetterSetterDatiDinamici.js",
                    "Init.SIGePro.DatiDinamici.WebControls.Js.DatiDinamiciExtender.js",
                    "Init.SIGePro.DatiDinamici.WebControls.Js.DescrizioneControllo.js",
                    "Init.SIGePro.DatiDinamici.WebControls.Js.jQuery.searchDatiDinamici.js",
                    "Init.SIGePro.DatiDinamici.WebControls.Js.jquery.uploadDatiDinamici.js",
                    "Init.SIGePro.DatiDinamici.WebControls.Js.CampoDinamicoEagle.js",

                };

                for (var i = 0; i < listaFilesJs.Length; i++)
                {
                    this.Page.ClientScript.RegisterClientScriptInclude(
                       regType,
                       jsKey + i.ToString(),
                       this.Page.ClientScript.GetWebResourceUrl(regType, listaFilesJs[i])
                    );
                }

            }
        }

        protected override void OnPreRender(EventArgs e)
        {
            // ImgDescrizione.Visible = !String.IsNullOrEmpty(Descrizione);
            // _pnlDescrizione.Visible = ImgDescrizione.Visible;

            this.InnerControl.CssClass = "d2Control " + this.GetNomeTipoControllo() + " " + this.GetExtraCssClasses();
            this.InnerControl.Attributes.Add("data-d2id", this.IdCampoCollegato.ToString());
            this.InnerControl.Attributes.Add("data-d2tipo", this.GetNomeTipoControllo());
            this.InnerControl.Attributes.Add("data-d2indice", this.Indice.ToString());
            this.InnerControl.Attributes.Add(Constants.EventoModificaAttribute, this.GetNomeEventoModifica());
            this.InnerControl.Attributes.Add("data-d2clientid", this.ClientID);
            this.InnerControl.Attributes.Add(Constants.NotificaValoreDecodificatoAttribute, this.RichiedeNotificaSuModificaValoreDecodificato.ToString());
            this.InnerControl.Attributes.Add(Constants.NomeCampoAttribute, this.NomeCampo);
            /*
            if (ImgDescrizione.Visible)
            {
                ImgDescrizione.ImageUrl = Page.ClientScript.GetWebResourceUrl(GetType(), @"Init.SIGePro.DatiDinamici.WebControls.help-icon.png");
                ImgDescrizione.CssClass = "helpIcon";
            }
            */
            base.OnPreRender(e);
        }

        protected virtual string GetExtraCssClasses()
        {
            return string.Empty;
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
            // ImgDescrizione.Visible = false;
            this._pnlDescrizione.Visible = false;
        }

        protected override void Render(HtmlTextWriter writer)
        {
            //if (this.IdRiferimentoNote.HasValue)
            //{
            //    var hrefNote = new HtmlGenericControl("a");

            //    hrefNote.Attributes.Add("href", $"#{AccumulatoreNoteModello.Constants.PrefissoElementoNote}{this.IdRiferimentoNote.ToString()}");
            //    hrefNote.Attributes.Add("class", "d2-href-note");
            //    hrefNote.InnerText = $"({this.IdRiferimentoNote})";

            //    this.Controls.Add(hrefNote);
            //}
            writer.AddAttribute(HtmlTextWriterAttribute.Class, "form-group feedback-container");
            writer.RenderBeginTag(HtmlTextWriterTag.Div);

            var haDescrizione = !String.IsNullOrEmpty(this.Descrizione);

            if (haDescrizione)
            {
                writer.AddAttribute(HtmlTextWriterAttribute.Class, "d2-input-group");
                writer.RenderBeginTag(HtmlTextWriterTag.Div);

            }

            base.RenderChildren(writer);

            if (haDescrizione)
            {

                writer.AddAttribute(HtmlTextWriterAttribute.Class, "d2-input-group-addon");
                writer.RenderBeginTag(HtmlTextWriterTag.Span);

                writer.AddAttribute(HtmlTextWriterAttribute.Class, "fa fa-info-circle");
                writer.RenderBeginTag(HtmlTextWriterTag.I);
                writer.RenderEndTag();

                writer.RenderEndTag();

                writer.RenderEndTag();
            }

            writer.AddAttribute(HtmlTextWriterAttribute.Class, "help-block error-feedback");
            writer.RenderBeginTag(HtmlTextWriterTag.Span);
            writer.RenderEndTag();

            writer.RenderEndTag();
        }

        private void InizializzaPropertiesDaCampoDinamico(CampoDinamicoBase campo)
        {
            MethodInfo mi = this.GetType().GetMethod("GetProprietaDesigner", BindingFlags.Static | BindingFlags.Public);

            if (mi == null)
            {
                return;
            }

            var proprieta = (ProprietaDesigner[])mi.Invoke(null, null);

            // Salvo i valori di default in un dictionary
            Dictionary<string, object> propDictionary = new Dictionary<string, object>();

            for (int i = 0; i < proprieta.Length; i++)
                propDictionary.Add(proprieta[i].NomeProprieta, proprieta[i].ValoreDefault);

            // Assegno al dictionary delle proprietà i valori letti dal db
            foreach (var it in campo.ProprietaControlloWeb)
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
                /*
                // HACK: data la separazione dei progetti non è possibile assegnarla nel campo dinamico (anche se sarebbe più corretto)
                if (nomeProperty == "TipoNumerico" && campo is CampoDinamico)
                {
                    (campo as CampoDinamico).TipoNumerico = Convert.ToBoolean(value);
                    continue;
                }
                */
                try
                {
                    PropertyDescriptor pd = propCollection[nomeProperty];

                    if (pd != null)
                        pd.SetValue(this, Convert.ChangeType(value, pd.PropertyType));
                }
                catch (Exception ex)
                {
                    var errMsg = String.Format("Errore durante l'assegnazione del valore \"{0}\" proprietà {1} al campo {2}: {3}", value, nomeProperty, campo.Id, ex.Message);
                    throw new Exception(errMsg, ex);
                }

            }
        }
    }
}
