using Init.SIGePro.DatiDinamici.WebControls.RenderersRigheModelloDinamico;
using Init.SIGePro.DatiDinamici.WebControls.Utils;
using System.Collections.Generic;
using System.Linq;
using System.Web.UI.HtmlControls;
using System.Web.UI.WebControls;
using VBG.DatiDinamici;
using VBG.DatiDinamici.WebControls;

namespace Init.SIGePro.DatiDinamici.WebControls
{
    internal class DatiDinamiciReadOnlyListBox : DatiDinamiciBaseControl<Panel>
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
            this.IgnoraRegistrazioneJavascript = true;
        }

        private List<ElementoListaValoriListBox> _elementiLista = new List<ElementoListaValoriListBox>();

        public virtual string ElementiLista
        {
            get
            {
                if (this._elementiLista.Count == 0)
                {
                    return string.Empty;
                }

                return ListBoxUtils.ConvertiInStringaValori(this._elementiLista);

                // return  String.Join(";",
                //         _elementiLista.Select(el => el.Key == el.Value ? el.Value : $"{el.Key}${el.Value}")
                //                            .ToArray()
                //     );
            }
            set
            {
                this._elementiLista.Clear();

                this._elementiLista = ListBoxUtils.ConvertiDaStringaValori(value).ToList();

                // _elementiLista = value.Split(';')
                //                            .Select(x => x.Split('$'))
                //                            .Select(x => x.Length == 1 ? new { key = x[0], value = x[0] } : new { key = x[0], value = x[1] })
                //                            .Select(x => new KeyValuePair<string, string>(x.key, x.value))
                //                            .ToList();
            }
        }

        public bool NascondiValoriSuRiepilogo { get; set; } = false;

        public int? IdRiferimentoNote { get; internal set; }

        protected override void Render(System.Web.UI.HtmlTextWriter writer)
        {
            this.NascondiIconaHelp();

            this.InnerControl.Controls.Clear();
            var ul = new HtmlGenericControl("ul");
            ul.Attributes.Add("class", "d2-elementi-lista-valori");



            var accumulatoreNote = AccumulatoreNoteModello.GetContextInstance();

            if (accumulatoreNote != null)
            {

                var li = new HtmlGenericControl("li");
                var span = new HtmlGenericControl("span");

                for (var i = 0; i < this._elementiLista.Count; i++)
                {
                    var item = this._elementiLista[i];

                    li = new HtmlGenericControl("li");
                    span = new HtmlGenericControl("span");

                    span.InnerHtml = item.Testo;

                    // Essendo un campo di sola lettura viene ripreso solo il valore decodificato
                    if (this.Valore == item.Testo.Trim())
                    {
                        li.Attributes.Add("class", "d2-elemento-lista-selezionato");

                        li.Controls.Add(span);
                        ul.Controls.Add(li);
                    }
                }

                if (!this.NascondiValoriSuRiepilogo)
                {
                    var valori = this._elementiLista.Select(x => x.Testo);

                    var indiceNotaValore = accumulatoreNote.AggiungiValoriCampo(this.NomeCampo, this.Etichetta, valori);
                    li = new HtmlGenericControl("li");
                    span = new HtmlGenericControl("span")
                    {
                        InnerHtml = $"Possibili valori: <span class='id-riferimento-valori'>(V{indiceNotaValore})</span>"
                    };
                    span.Attributes.Add("class", "nota-possibili-valori");

                    li.Controls.Add(span);
                    ul.Controls.Add(li);
                }
            }
            else
            {
                for (var i = 0; i < this._elementiLista.Count; i++)
                {
                    var item = this._elementiLista[i];
                    var li = new HtmlGenericControl("li");
                    var span = new HtmlGenericControl("span");

                    span.InnerHtml = item.Testo;

                    // Essendo un campo di sola lettura viene ripreso solo il valore decodificato
                    if (this.Valore == item.Testo)
                    {
                        li.Attributes.Add("class", "d2-elemento-lista-selezionato");
                    }

                    li.Controls.Add(span);
                    ul.Controls.Add(li);
                }
            }

            this.InnerControl.Controls.Add(ul);

            base.Render(writer);
        }
    }
}
