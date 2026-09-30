using VBG.Backend.Protocollo.AppLogic.Shared.Data;

namespace VBG.Backend.Protocollo.AppLogic.Core.DocEr.ProtocollazioneRegistrazione.MittentiDestinatari.Persone
{
    public class AmministrazioneVbg : IAmministrazioneAnagraficaVbg
    {
        private readonly ProtocolloAmministrazioni _amministrazione;

        public AmministrazioneVbg(ProtocolloAmministrazioni amministrazione)
        {
            this._amministrazione = amministrazione;
        }

        public string CodiceFiscalePartitaIva
        {
            get { return this._amministrazione.PARTITAIVA; }
        }

        public string Nominativo
        {
            get { return this._amministrazione.AMMINISTRAZIONE; }
        }

        public string Tipo
        {
            get { return "AMMINISTRAZIONE"; }
        }

        public string CodiceVbg
        {
            get { return this._amministrazione.CODICEAMMINISTRAZIONE; }
        }
    }
}
