using System.Text;
using VBG.DatiDinamici.Agid.WebControls.Controlli.DatiDinamiciSearch;

namespace Init.Sigepro.FrontEnd.CoreServices.DatiDinamici.Ricerche
{
    internal static class CampiRicercaExtensions
    {
        public static StringBuilder GetQueryBase(this ProprietaCampoRicerca pc)
        {
            var campiSelect = pc.CampiSelect;
            var tabelleSelect = pc.TabelleSelect;
            var condizioneJoin = pc.CondizioneJoin;
            var condizioniWhere = pc.CondizioniWhere;

            var sb = new StringBuilder();

            sb.AppendFormat("select {0} from {1} where 1=1 ",
                            String.IsNullOrEmpty(campiSelect) ? "*" : campiSelect,
                            tabelleSelect);

            if (!String.IsNullOrEmpty(condizioneJoin))
            {
                sb.Append($" and {condizioneJoin} ");
            }

            if (!String.IsNullOrEmpty(condizioniWhere))
            {
                sb.Append($" and {condizioniWhere} ");
            }

            return sb;
        }
    }
}
