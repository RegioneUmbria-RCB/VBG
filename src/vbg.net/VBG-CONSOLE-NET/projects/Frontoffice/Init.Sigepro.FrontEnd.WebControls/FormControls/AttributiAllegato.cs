using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Web.UI.WebControls;

namespace Init.Sigepro.FrontEnd.WebControls.FormControls
{
    public class AttributiAllegato : WebControl
    {
        private static class Constants
        {
            public const string CssClassDefault = "contenitore-attributi-allegato";
            public const string CssClassAttributi = "attributi-allegato";
            public const string CssClassAttributiLegenda = "attributi-allegato legenda";

            public const string CssClassObbligatorio = "glyphicon glyphicon-exclamation-sign allegato-obbligatorio";
            public const string CssClassRichiedeFirma = "glyphicon glyphicon-pencil allegato-richiede-firma";
            public const string CssClassContieneNote = "glyphicon glyphicon-question-sign allegato-contiene-note";
            public const string TestoObbligatorio = "Allegato obbligatorio";
            public const string TestoRichiedeFirma = "L'allegato richiede l'apposizione di una firma digitale";
            public const string TestoContieneNote = "L'allegato contiene note per la compilazione, fare click per visualizzarle";
        }

        public bool Obbligatorio
        {
            get { object o = this.ViewState["Obbligatorio"]; return o == null ? false : (bool)o; }
            set { this.ViewState["Obbligatorio"] = value; }
        }

        public bool RichiedeFirma
        {
            get { object o = this.ViewState["RichiedeFirma"]; return o == null ? false : (bool)o; }
            set { this.ViewState["RichiedeFirma"] = value; }
        }

        public bool Legenda
        {
            get { object o = this.ViewState["Legenda"]; return o == null ? false : (bool)o; }
            set { this.ViewState["Legenda"] = value; }
        }

        public bool ContieneNote
        {
            get { object o = this.ViewState["ContieneNote"]; return o == null ? false : (bool)o; }
            set { this.ViewState["ContieneNote"] = value; }
        }

        public int IdAllegato
        {
            get { object o = this.ViewState["IdAllegato"]; return o == null ? -1 : (int)o; }
            set { this.ViewState["IdAllegato"] = value; }
        }

        public bool NascontiNoteCompilazioneInLegenda
        {
            get { object o = this.ViewState["NascontiNoteCompilazioneInLegenda"]; return o == null ? false : (bool)o; }
            set { this.ViewState["NascontiNoteCompilazioneInLegenda"] = value; }
        }

        public bool StaccoDoppio
        {
            get { object o = this.ViewState["StaccoDoppio"]; return o == null ? true : (bool)o; }
            set { this.ViewState["StaccoDoppio"] = value; }
        }



        public override void RenderControl(System.Web.UI.HtmlTextWriter writer)
        {
            if (!this.Visible)
            {
                return;
            }

            if (Legenda)
                RenderLegenda(writer);
            else
                RenderControllo(writer);
        }

        private void RenderLegenda(System.Web.UI.HtmlTextWriter writer)
        {
            // <div class="attributi-allegato">
            //     <div>
            //         <i class="glyphicon glyphicon-exclamation-sign"></i>= Allegato obbligatorio
            //         <i class="glyphicon glyphicon-pencil"></i>= L'allegato richiede l'apposizione di una firma digitale
            //     </div>
            // </div>

            writer.AddAttribute("class", Constants.CssClassAttributiLegenda);
            writer.RenderBeginTag("div");

            writer.RenderBeginTag("div");


            RenderElementoLegenda(writer, Constants.CssClassObbligatorio, Constants.TestoObbligatorio);
            RenderElementoLegenda(writer, Constants.CssClassRichiedeFirma, Constants.TestoRichiedeFirma);

            if (!NascontiNoteCompilazioneInLegenda)
                RenderElementoLegenda(writer, Constants.CssClassContieneNote, Constants.TestoContieneNote);

            if (StaccoDoppio)
            {
                writer.RenderBeginTag("br");
                writer.RenderEndTag();
            }

            writer.RenderEndTag();
            writer.RenderEndTag();
        }

        private void RenderElementoLegenda(System.Web.UI.HtmlTextWriter writer, string classeElemento, string testoElemento)
        {
            //         <i class="glyphicon glyphicon-exclamation-sign"></i>= Allegato obbligatorio
            writer.AddAttribute("class", "help-block");
            writer.RenderBeginTag("div");
            writer.AddAttribute("class", classeElemento);
            writer.RenderBeginTag("i");
            writer.RenderEndTag();
            writer.Write(" = ");
            writer.Write(testoElemento);
            writer.RenderEndTag();
        }

        private void RenderControllo(System.Web.UI.HtmlTextWriter writer)
        {
            // Contenitore
            writer.AddAttribute("class", Constants.CssClassDefault);
            writer.AddAttribute("style", "white-space: nowrap");
            writer.RenderBeginTag("div");

            // Obbligatorio
            var sb = new StringBuilder();



            //Obbligatorio
            writer.AddAttribute("class", Constants.CssClassAttributi);
            writer.RenderBeginTag("span");

            writer.AddAttribute("class", Constants.CssClassObbligatorio);
            writer.AddAttribute("title", Constants.TestoObbligatorio);

            if (!Obbligatorio)
            {
                writer.AddAttribute("style", "visibility:hidden");
            }
            writer.RenderBeginTag("i");
            writer.RenderEndTag();

            writer.RenderEndTag();

            //Richiede firma
            writer.AddAttribute("class", Constants.CssClassAttributi);
            writer.RenderBeginTag("span");

            writer.AddAttribute("class", Constants.CssClassRichiedeFirma);
            writer.AddAttribute("title", Constants.TestoRichiedeFirma);

            if (!RichiedeFirma)
            {
                writer.AddAttribute("style", "visibility:hidden");
            }
            writer.RenderBeginTag("i");
            writer.RenderEndTag();

            writer.RenderEndTag();
            

            // Contiene note
            writer.AddAttribute("class", Constants.CssClassAttributi);
            writer.RenderBeginTag("span");

            writer.AddAttribute("class", Constants.CssClassContieneNote);
            writer.AddAttribute("title", Constants.TestoContieneNote);
            writer.AddAttribute("data-idallegato", IdAllegato.ToString());

            if (!ContieneNote)
            {
                writer.AddAttribute("style", "visibility:hidden");
            }

            writer.RenderBeginTag("i");
            writer.RenderEndTag();

            writer.RenderEndTag();

            //}

            writer.RenderEndTag();
        }
    }
}
