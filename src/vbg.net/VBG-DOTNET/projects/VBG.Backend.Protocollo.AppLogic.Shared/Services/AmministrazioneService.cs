using VBG.Backend.Protocollo.AppLogic.Shared.Data;
using VBG.Backend.Protocollo.AppLogic.Shared.Interfaces;

namespace VBG.Backend.Protocollo.AppLogic.Shared.Services
{
    public class AmministrazioneService : IAnagraficaAmministrazione
    {
        private readonly ProtocolloAmministrazioni _amm;

        public AmministrazioneService(ProtocolloAmministrazioni amm)
        {
            this._amm = amm;
        }

        public string Codice => this._amm.CODICEAMMINISTRAZIONE ?? "";
        public string Nome => "";
        public string Cognome => "";
        public string CodiceFiscale => "";
        public string PartitaIva => this._amm.PARTITAIVA ?? "";
        public string Denominazione => this._amm.AMMINISTRAZIONE ?? "";
        public string Indirizzo => this._amm.INDIRIZZO ?? "";
        public string Email => this._amm.EMAIL ?? "";
        public string Pec => this._amm.PEC ?? "";
        public string Tipo => "G";
        public string Sesso => "";
        public string NomeCognome => this._amm.AMMINISTRAZIONE ?? "";
        public ProtocolloComune ComuneResidenza => this._amm.ComuneResidenza;
        public string Localita => this._amm.CITTA;
        public string CodiceFiscalePartitaIva => this._amm.PARTITAIVA ?? "";
        public string ModalitaTrasmissione => this._amm.ModalitaTrasmissione;
        public string MezzoInvio => this._amm.Mezzo;
        public string Provincia => this._amm.PROVINCIA;
        public string Cap => this._amm.CAP;
        public string Comune => this._amm.CITTA;
        public string CodiceIstatResidenza => this._amm.ComuneResidenza != null ? this._amm.ComuneResidenza.CodiceIstat : "";
        public string Telefono => this._amm.TELEFONO1;
        public string Fax => this._amm.FAX;
        public string Uo => this._amm.PROT_UO;
        public string Ruolo => this._amm.PROT_RUOLO;
        public string TipologiaAnagrafica => "AMMINISTRAZIONE";
    }
}
