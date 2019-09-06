using System;
using System.Collections.Generic;
using System.Linq;
using System.Web.UI.HtmlControls;
using System.Web.UI.WebControls;

namespace Init.SIGePro.DatiDinamici.WebControls
{
    class DatiDinamiciReadOnlyListBox : DatiDinamiciBaseControl<Panel>
    {

        private static class Constants
        {
            public const int NumeroMassimoElementiMostrati = 5;
        }

        public override string Valore { get; set; }

        protected override string GetNomeTipoControllo()
        {
            return "d2-read-only-list-box";
        }

        public static ProprietaDesigner[] GetProprietaDesigner()
        {
            return new ProprietaDesigner[] { };
        }

        public DatiDinamiciReadOnlyListBox(CampoDinamicoBase campo)
            : base(campo)
        {
            IgnoraRegistrazioneJavascript = true;
        }

        List<KeyValuePair<string, string>> _elementiLista = new List<KeyValuePair<string, string>>();

        public virtual string ElementiLista
        {
            get
            {
                if (_elementiLista.Count == 0)
                {
                    return string.Empty;
                }

                return String.Join(";",
                        _elementiLista.Select(el => el.Key == el.Value ? el.Value : $"{el.Key}${el.Value}")
                                           .ToArray()
                    );
            }
            set
            {
                _elementiLista.Clear();

                _elementiLista = value.Split(';')
                                           .Select(x => x.Split('$'))
                                           .Select(x => x.Length == 1 ? new { key = x[0], value = x[0] } : new { key = x[0], value = x[1] })
                                           .Select(x => new KeyValuePair<string, string>(x.key, x.value))
                                           .ToList();
            }
        }

        public bool NascondiValoriSuRiepilogo { get; set; } = false;

        public int? IdRiferimentoNote { get; internal set; }

        protected override void Render(System.Web.UI.HtmlTextWriter writer)
        {
            NascondiIconaHelp();

            InnerControl.Controls.Clear();
            var ul = new HtmlGenericControl("ul");
            ul.Attributes.Add("class", "d2-elementi-lista-valori");



            var accumulatoreNote = AccumulatoreNoteModello.GetContextInstance();

            if (accumulatoreNote != null)
            {

                var li = new HtmlGenericControl("li");
                var span = new HtmlGenericControl("span");

                for (int i = 0; i < _elementiLista.Count; i++)
                {
                    var item = _elementiLista[i];

                    li = new HtmlGenericControl("li");
                    span = new HtmlGenericControl("span");

                    span.InnerHtml = item.Value;

                    // Essendo un campo di sola lettura viene ripreso solo il valore decodificato
                    if (Valore == item.Value)
                    {
                        li.Attributes.Add("class", "d2-elemento-lista-selezionato");

                        li.Controls.Add(span);
                        ul.Controls.Add(li);
                    }
                }

                if (!NascondiValoriSuRiepilogo)
                {
                    var valori = this._elementiLista.Select(x => x.Value);

                    var indiceNotaValore = accumulatoreNote.AggiungiValoriCampo(Etichetta, valori);
                    li = new HtmlGenericControl("li");
                    span = new HtmlGenericControl("span")
                    {
                        InnerHtml = $"Possibili valori: <span class='id-riferimento-valori'>(V{indiceNotaValore})</b>"
                    };
                    span.Attributes.Add("class", "nota-possibili-valori");

                    li.Controls.Add(span);
                    ul.Controls.Add(li);
                }
            }
            else
            {
                for (int i = 0; i < _elementiLista.Count; i++)
                {
                    var item = _elementiLista[i];
                    var li = new HtmlGenericControl("li");
                    var span = new HtmlGenericControl("span");

                    span.InnerHtml = item.Value;

                    // Essendo un campo di sola lettura viene ripreso solo il valore decodificato
                    if (Valore == item.Value)
                    {
                        li.Attributes.Add("class", "d2-elemento-lista-selezionato");
                    }

                    li.Controls.Add(span);
                    ul.Controls.Add(li);
                }
            }

            InnerControl.Controls.Add(ul);

            base.Render(writer);
        }
    }
}
