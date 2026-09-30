using Init.Sigepro.FrontEnd.AppLogic.RicercaPraticheWs;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Web.UI.WebControls;

namespace Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza.ricerca_pratiche
{
    public partial class ricerca_pratiche_dettaglio : System.Web.UI.UserControl
    {
        protected class AnagraficaModel
        {
            public string Qualifica { get; set; } = "";
            public string Nominativo { get; set; } = "";
        }

        protected class MappaleModel
        {
            public string Catasto { get; set; }
            public string Estremi { get; set; }
        }


        public RisultatoRicercaPratiche DataSource { get; set; }

        protected void Page_Load(object sender, EventArgs e)
        {

        }

        public override void DataBind()
        {
            base.DataBind();

            if (this.DataSource != null)
            {
                var numeroProtocollo = this.DataSource.EstremiPratica.NumeroProtocollo;

                if (this.DataSource.EstremiPratica.DataProtocollo.HasValue)
                {
                    numeroProtocollo += $" del {this.DataSource.EstremiPratica.DataProtocollo.Value.ToString("dd/MM/yyyy")}";
                }

                this.lblIntervento.Text = this.DataSource.EstremiPratica?.Intervento;
                this.lblIntervento.Visible = !String.IsNullOrEmpty(this.lblIntervento.Text);

                this.lblDescrizioneLavori.Text = this.DataSource.EstremiPratica?.DescrizioneLavori;
                this.lblDescrizioneLavori.Visible = !String.IsNullOrEmpty(this.lblDescrizioneLavori.Text);

                this.lblNumeroPratica.Text = $"{this.DataSource.EstremiPratica.NumeroIstanza} del {this.DataSource.EstremiPratica.DataIstanza}";
                this.lblNumeroProtocollo.Text = numeroProtocollo;

                this.rptAnagrafiche.DataSource = this.AdattaAnagrafiche(this.DataSource.Anagrafiche);
                this.rptAnagrafiche.DataBind();

                this.rptLocalizzazioni.DataSource = this.DataSource.Localizzazioni;
                this.rptLocalizzazioni.DataBind();
            }
        }

        private IEnumerable<AnagraficaModel> AdattaAnagrafiche(AppLogic.RicercaPraticheWs.AnagraficaIstanzaTrovata[] anagrafiche)
        {
            return anagrafiche.Select(x => new AnagraficaModel
            {
                Nominativo = $"{x.Proprieta.FirstOrDefault(p => p.Chiave == "NOMINATIVO")?.Valore} {x.Proprieta.FirstOrDefault(p => p.Chiave == "NOME")?.Valore}",
                Qualifica = x.TipoSoggetto?.Descrizione ?? "Non definita"
            });
        }

        protected void rptLocalizzazioni_ItemDataBound(object sender, System.Web.UI.WebControls.RepeaterItemEventArgs e)
        {
            if (e.Item.ItemType == ListItemType.Item || e.Item.ItemType == ListItemType.AlternatingItem)
            {
                var stradario = e.Item.DataItem as StradarioIstanzaTrovata;
                var rptMappali = (Repeater)e.Item.FindControl("rptMappali");

                rptMappali.DataSource = this.AdattaMappali(stradario.Mappali);
                rptMappali.DataBind();
            }
        }


        private IEnumerable<MappaleModel> AdattaMappali(MappaleIstanzaTrovata[] mappali)
        {
            if (mappali == null)
            {
                return Enumerable.Empty<MappaleModel>();
            }

            return mappali.Select(m => new MappaleModel
            {
                Catasto = m.TipoCatasto,
                Estremi = new StringBuilder()
                            .Append(String.IsNullOrEmpty(m.Foglio) ? " " : $"F: {m.Foglio}")
                            .Append(String.IsNullOrEmpty(m.Particella) ? " " : $" P: {m.Particella}")
                            .Append(String.IsNullOrEmpty(m.Sub) ? " " : $" S: {m.Sub}")
                            .ToString()
            });
        }
    }
}