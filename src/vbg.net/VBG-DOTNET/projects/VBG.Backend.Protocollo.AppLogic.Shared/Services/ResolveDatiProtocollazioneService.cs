using Init.SIGePro.Data;
using PersonalLib2.Data;
using VBG.Backend.Protocollo.AppLogic.Shared.Data;
using VBG.Backend.Protocollo.AppLogic.Shared.Enums;

namespace VBG.Backend.Protocollo.AppLogic.Shared.Services
{
    public class ResolveDatiProtocollazioneService
    {
        public string IdComune { get; private set; }
        public string IdComuneAlias { get; private set; }
        public string Software { get; private set; }
        public IIstanzaDaProtocollare? Istanza { get; private set; }
        public IMovimentoDaProtocollare? Movimento { get; private set; }
        public string CodiceComune { get; private set; }
        public AmbitoProtocollazioneEnum TipoAmbito { get; private set; }
        public string CodiceIstanza { get { return this.Istanza != null ? this.Istanza.CODICEISTANZA : ""; } }
        public string CodiceMovimento { get { return this.Movimento != null ? this.Movimento.CODICEMOVIMENTO : ""; } }
        public int? CodiceInterventoProc
        {
            get
            {
                if (this.Istanza == null)
                    return (int?)null;
                else
                    if (String.IsNullOrEmpty(this.Istanza.CODICEINTERVENTOPROC))
                        throw new Exception("CODICEINTERVENTOPROC NON VALORIZZATO");

                return Convert.ToInt32(this.Istanza.CODICEINTERVENTOPROC);
            }

        }
        public DataBase Db { get; private set; }
        public int? CodiceResponsabileUtenteLoggato { get; set; }
        public int? CodiceResponsabileProcedimentoIstanza { get { return this.Istanza != null && !String.IsNullOrEmpty(this.Istanza.CODICERESPONSABILEPROC) ? Convert.ToInt32(this.Istanza.CODICERESPONSABILEPROC) : (int?)null; } }
        public string NumeroIstanza { get { return this.Istanza != null ? this.Istanza.NUMEROISTANZA : ""; } }
        public string Token { get; private set; }
        public PecInbox? DatiPec { get; private set; }


        public ResolveDatiProtocollazioneService(string idComune, string idComuneAlias, string software, DataBase db, Istanze? istanza, Movimenti? movimento, int? codiceOperatore, AmbitoProtocollazioneEnum ambito, string token, string codiceComune = "", PecInbox? datiPec = null)
        {
            this.IdComune = idComune;
            this.IdComuneAlias = idComuneAlias;
            this.Software = software;
            this.Istanza = istanza;
            this.Movimento = movimento;
            this.CodiceResponsabileUtenteLoggato = codiceOperatore;
            this.CodiceComune = codiceComune;
            this.TipoAmbito = ambito;
            this.Db = db;
            this.Token = token;
            this.DatiPec = datiPec;
        }
    }
}
