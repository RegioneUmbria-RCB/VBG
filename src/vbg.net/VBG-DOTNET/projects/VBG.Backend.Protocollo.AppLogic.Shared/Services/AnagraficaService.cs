using VBG.Backend.Protocollo.AppLogic.Shared.Data;
using VBG.Backend.Protocollo.AppLogic.Shared.Interfaces;

namespace VBG.Backend.Protocollo.AppLogic.Shared.Services
{
    public class AnagraficaService : IAnagraficaAmministrazione
    {
        private readonly ProtocolloAnagrafe _anag;

        public AnagraficaService(ProtocolloAnagrafe anag)
        {
            this._anag = anag;
        }

        public string Codice => this._anag.CODICEANAGRAFE ?? "";
        public string Nome => this._anag.TIPOANAGRAFE == "F" ? this._anag.NOME : "";
        public string Cognome => this._anag.TIPOANAGRAFE == "F" ? this._anag.NOMINATIVO : "";
        public string CodiceFiscale => this._anag.CODICEFISCALE ?? "";
        public string PartitaIva => this._anag.PARTITAIVA ?? "";
        public string Denominazione => this._anag.GetNomeCompleto() ?? "";
        public string Indirizzo => this._anag.INDIRIZZO ?? "";
        public string Email => this._anag.EMAIL ?? "";
        public string Pec => this._anag.PecProtocollazione ?? "";
        public string Tipo => this._anag.TIPOANAGRAFE ?? "";
        public string Sesso => this._anag.SESSO ?? "";
        public string NomeCognome => String.Format("{0} {1}", this._anag.NOME ?? "", this._anag.NOMINATIVO ?? "").TrimStart();
        public ProtocolloComune ComuneResidenza => this._anag.ComuneResidenza;
        public string Localita => this._anag.CITTA;
        public string CodiceFiscalePartitaIva => !String.IsNullOrEmpty(this._anag.CODICEFISCALE) ? this._anag.CODICEFISCALE : this._anag.PARTITAIVA ?? "";
        public string ModalitaTrasmissione => this._anag.ModalitaTrasmissione;
        public string MezzoInvio => this._anag.Mezzo;
        public string Provincia => this._anag.PROVINCIA;
        public string Cap => this._anag.CAP;
        public string Comune => this._anag.ComuneResidenza != null ? this._anag.ComuneResidenza.DenominazioneComune : "";
        public string CodiceIstatResidenza => this._anag.CodiceIstatComRes;
        public string Telefono => String.IsNullOrEmpty(this._anag.TELEFONO) ? this._anag.TELEFONOCELLULARE : "";
        public string Fax => this._anag.FAX;
        public string TipologiaAnagrafica => "ANAGRAFICA";
        public string Uo => null;
    }
}
