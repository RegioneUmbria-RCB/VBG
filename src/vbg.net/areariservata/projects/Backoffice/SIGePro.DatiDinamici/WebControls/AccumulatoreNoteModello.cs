using System;
using System.Collections.Generic;
using System.IO;
using System.Web;
using System.Web.UI;
using System.Web.UI.HtmlControls;

namespace Init.SIGePro.DatiDinamici.WebControls
{
    public class AccumulatoreNoteModello
    {
        private static class Constants
        {

            public const string PrefissoElementoNote = "d2-nota-compilazione";
            public const string HttpContextInstanceKey = "AccumulatoreNoteModello.ContextInstance";
            public const string CssClass = "d2-note-compilazione";
        }

        List<string> _noteCompilazione = new List<string>();
        Dictionary<string, IEnumerable<string>> _listaValoriCampo = new Dictionary<string, IEnumerable<string>>();

        int IdProssimaNota => _noteCompilazione.Count + 1;

        public string TitoloSezioneListaValori { get; private set; }
        public string TitoloSezioneNoteCompilazione { get; private set; }

        public AccumulatoreNoteModello(string titoloSezioneNoteCompilazione= "Note di compilazione", string titoloSezioneValoriCampi = "Possibili valori campi")
        {
            this.TitoloSezioneListaValori = titoloSezioneValoriCampi;
            this.TitoloSezioneNoteCompilazione = titoloSezioneNoteCompilazione;
        }

        public static void InitContextInstance()
        {
            if (HttpContext.Current == null)
            {
                return;
            }

            HttpContext.Current.Items[Constants.HttpContextInstanceKey] = new AccumulatoreNoteModello();
        }

        public static AccumulatoreNoteModello GetContextInstance()
        {
            if (HttpContext.Current == null)
            {
                return null;
            }

            return (AccumulatoreNoteModello)HttpContext.Current.Items[Constants.HttpContextInstanceKey];
        }

        public string ToHtml()
        {
            return RenderNoteCompilazione() + "<br />" +
                    RenderListaValori();
        }

        private string RenderListaValori()
        {
            if (_listaValoriCampo.Count == 0)
            {
                return String.Empty;
            }

            using (var stringWriter = new StringWriter())
            using (var tw = new HtmlTextWriter(stringWriter))
            {
                var rootElement = new HtmlGenericControl("div");
                rootElement.Attributes.Add("class", Constants.CssClass);
                var titoloSezione = new HtmlGenericControl("h2");

                titoloSezione.InnerHtml = this.TitoloSezioneListaValori;

                rootElement.Controls.Add(titoloSezione);

                var indiceNota = 1;

                foreach (var campo in this._listaValoriCampo.Keys)
                {
                    var parent = new HtmlGenericControl("div");
                    var titoloCampo = new HtmlGenericControl("h3");
                    var ul = new HtmlGenericControl("ul");

                    parent.Controls.Add(titoloCampo);
                    parent.Controls.Add(ul);

                    titoloCampo.InnerHtml = $"<span class='riferimento-valori'>V{indiceNota}</span>: {campo}";

                    indiceNota++;

                    foreach (var valore in this._listaValoriCampo[campo])
                    {
                        var li = new HtmlGenericControl("li");

                        li.InnerHtml = $"<span>{valore}</span>";
                        //li.Attributes.Add("id", Constants.PrefissoElementoNote + (i + 1).ToString());

                        ul.Controls.Add(li);
                    }

                    rootElement.Controls.Add(parent);
                }

                rootElement.RenderControl(tw);

                return stringWriter.ToString();
            }
        }

        private string RenderNoteCompilazione()
        {
            using (var stringWriter = new StringWriter())
            using (var tw = new HtmlTextWriter(stringWriter))
            {
                var rootElement = new HtmlGenericControl("div");
                rootElement.Attributes.Add("class", Constants.CssClass);
                var titoloSezione = new HtmlGenericControl("h2");

                titoloSezione.InnerHtml = this.TitoloSezioneNoteCompilazione;

                rootElement.Controls.Add(titoloSezione);

                var ol = new HtmlGenericControl("ul");

                for (int i = 0; i < _noteCompilazione.Count; i++)
                {
                    var nota = _noteCompilazione[i];
                    var li = new HtmlGenericControl("li");

                    li.InnerHtml = $"<span><span class='riferimento-nota'>N{(i+1)}</span>:<br/> {nota}</span>";
                    li.Attributes.Add("id", Constants.PrefissoElementoNote + (i + 1).ToString());

                    ol.Controls.Add(li);
                }

                rootElement.Controls.Add(ol);

                rootElement.RenderControl(tw);

                return stringWriter.ToString();
            }
        }

        //public void AnalizzaControllo(Control campoRoot)
        //{
        //    foreach (var controllo in campoRoot.Controls.Cast<Control>())
        //    {
        //        if (controllo is IDatiDinamiciControl)
        //        {
        //            var campo = (IDatiDinamiciControl)controllo;
        //            var note = campo.Note;

        //            if (!String.IsNullOrEmpty(note))
        //            {
        //                campo.IdRiferimentoNote = IdProssimaNota;

        //                _note.Add(note);
        //            }
        //        }
        //        else
        //        {
        //            foreach (var figlio in controllo.Controls.Cast<Control>())
        //            {
        //                AnalizzaControllo(figlio);
        //            }
        //        }
        //    }
        //}

        public void ValutaNote(CampoDinamicoBase campo)
        {
            if (!String.IsNullOrEmpty(campo.Descrizione?.Trim()))
            {
                _noteCompilazione.Add(campo.Descrizione);

                campo.IdRiferimentoNote = _noteCompilazione.Count;
            }
        }
        /*
        internal void EstendiNota(int idx, string noteEstese)
        {
            if (idx < 0 || idx >= this._noteCompilazione.Count)
            {
                return;
            }

            this._noteCompilazione[idx] += noteEstese;
        }
        */

        public int AggiungiValoriCampo(string nomeCampo, IEnumerable<string> listaValori)
        {
            if (_listaValoriCampo.ContainsKey(nomeCampo))
            {
                return -1;
            }

            _listaValoriCampo[nomeCampo] = listaValori;

            return _listaValoriCampo.Keys.Count;
        }
    }
}
