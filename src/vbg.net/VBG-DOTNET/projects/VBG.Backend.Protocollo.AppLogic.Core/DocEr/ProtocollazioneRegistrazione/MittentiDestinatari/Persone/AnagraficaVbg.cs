using VBG.Backend.Protocollo.AppLogic.Shared.Data;

namespace VBG.Backend.Protocollo.AppLogic.Core.DocEr.ProtocollazioneRegistrazione.MittentiDestinatari.Persone
{
    public class AnagraficaVbg : IAmministrazioneAnagraficaVbg
    {
        private readonly ProtocolloAnagrafe _anagrafe;

        public AnagraficaVbg(ProtocolloAnagrafe anagrafe)
        {
            this._anagrafe = anagrafe;
        }

        public string CodiceFiscalePartitaIva
        {
            get { return !String.IsNullOrEmpty(this._anagrafe.CODICEFISCALE) ? this._anagrafe.CODICEFISCALE : this._anagrafe.PARTITAIVA; }
        }

        public string Nominativo
        {
            get { return this._anagrafe.GetNomeCompleto(); }
        }

        public string Tipo
        {
            get { return "ANAGRAFICA"; }
        }

        public string CodiceVbg
        {
            get { return this._anagrafe.CODICEANAGRAFE; }
        }
    }
}
