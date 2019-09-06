using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Web.UI.HtmlControls;
using System.Web.UI.WebControls;
using Init.SIGePro.DatiDinamici.Interfaces.WebControls;

namespace Init.SIGePro.DatiDinamici.WebControls.RenderersRigheModelloDinamico
{
    public class RigaSingola : IRigaRenderizzata
    {
        HtmlTableRow _row = new HtmlTableRow();

        public RigaSingola()
        {

        }

        internal void AggiungiCellaVuota()
        {
            var cellaVuota = new HtmlTableCell();

            cellaVuota.Controls.Add(new Literal { Text = String.Empty });

            this._row.Cells.Add(cellaVuota);
        }

        internal void AggiungiCampoDinamico(IDatiDinamiciControl campo, string classeCss)
        {
            var cell1 = new HtmlTableCell();

            cell1.Attributes.Add("class", classeCss);
            cell1.Controls.Add(campo as WebControl);

            this._row.Cells.Add(cell1);
        }

        internal void AggiungiCellaDiIntestazione(EtichettaCampo etichetta, string classeCss)
        {
            var cell0 = new HtmlTableCell();

            cell0.Attributes.Add("class", classeCss);

            this._row.Cells.Add(cell0);

            var intestazione = new HtmlGenericControl("span");
            intestazione.InnerHtml = etichetta.Valore;

            if (etichetta.IdRiferimentoNote.HasValue)
            {
                intestazione.InnerHtml += " <span class='id-riferimento-nota'>(N" + etichetta.IdRiferimentoNote + ")</span>";

            }
            intestazione.Attributes.Add("class", "EtichettaControllo");

            cell0.Controls.Add(intestazione);
        }

        public int NumeroColonne
        {
            get
            {
                return this._row.Cells.Count;
            }
        }

        HtmlTableRow IRigaRenderizzata.AsHtmlRow()
        {
            return this._row;
        }

        public int NumeroCelle
        {
            get { return this._row.Cells.Count; }
        }

        public int NumeroControlli
        {
            get { return this._row.Controls.Count; }
        }
    }
}
