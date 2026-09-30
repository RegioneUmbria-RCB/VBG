using Init.SIGePro.Data;
using Init.SIGePro.Manager;
using System;


namespace VBG.Backend.Protocollo.AppLogic.Legacy.Acaris.Sottofascicolazione
{
    public class DescrizioneSottofascicoloResolver : IDescrizioneSottofascicoloResolver
    {
        private string _descrizione;

        public DescrizioneSottofascicoloResolver(ProtocolloExt datiProtocollo)
        {
            if (String.IsNullOrEmpty(datiProtocollo.Configurazione.DescrizioneSottofascicolo) || !datiProtocollo.CodiceIstanza.HasValue)
            {
                return;
            }
            this._descrizione = datiProtocollo.Configurazione.DescrizioneSottofascicolo;
            var istanza = new IstanzeMgr(datiProtocollo.Db).GetById(datiProtocollo.Idcomune, datiProtocollo.CodiceIstanza.Value);
            var intervento = new AlberoProcMgr(datiProtocollo.Db).GetById(Convert.ToInt32(istanza.CODICEINTERVENTOPROC), istanza.IDCOMUNE);

            this.CalcolaDescrizione(istanza, intervento);

        }

        public DescrizioneSottofascicoloResolver(Istanze istanza, AlberoProc intervento, String templateSottofascicolo)
        {
            this._descrizione = templateSottofascicolo;
            this.CalcolaDescrizione(istanza, intervento);
        }

        private void CalcolaDescrizione(Istanze istanza, AlberoProc intervento)
        {
            if (String.IsNullOrEmpty(this._descrizione)) { return; }
            if (istanza == null) { return; }
            if (intervento == null) { return; }
            this._descrizione = this._descrizione
                .Replace("[INTERVENTO]", intervento.SC_DESCRIZIONE)
                .Replace("[DATA-PRESENTAZIONE]", istanza.DATA.Value.ToString("dd/MM/yyyy"));
        }

        public string Get()
        {
            return this._descrizione;
        }
    }
}
