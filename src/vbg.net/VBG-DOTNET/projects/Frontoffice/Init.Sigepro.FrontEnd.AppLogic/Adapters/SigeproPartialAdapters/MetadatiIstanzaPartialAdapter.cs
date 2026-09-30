using Init.Sigepro.FrontEnd.AppLogic.GestioneMetadatiToken.Client;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda;
using Init.Sigepro.FrontEnd.AppLogic.WebServiceReferences.IstanzeService;
using System;
using System.Linq;

namespace Init.Sigepro.FrontEnd.AppLogic.Adapters.SigeproPartialAdapters
{
    public class MetadatiIstanzaPartialAdapter : IIstanzaSigeproPartialAdapter
    {
        private readonly IMetadatiTokenUtenteService _metadatiTokenService;

        public MetadatiIstanzaPartialAdapter(IMetadatiTokenUtenteService metadatiTokenService)
        {
            this._metadatiTokenService = metadatiTokenService;
        }

        public void Adatta(IDomandaOnlineReadInterface src, Istanze dst, IstanzaSigeproAdapterFlags flags)
        {
            if (!flags.RecuperaMetadatiToken)
            {
                return;
            }

            const string identificatoreMetadatoIstanza = "ISTANZA_METADATO_";

            var metadati = this._metadatiTokenService.GetMetadatiTokenUtente();

            dst.Metadati = metadati.Where(x => x.Nome.StartsWith(identificatoreMetadatoIstanza))
                .Select(m => new IstanzeMetadati
                {
                    Chiave = m.Nome.Substring(identificatoreMetadatoIstanza.Length),
                    Valore = m.Valore,
                    IdComune = dst.IDCOMUNE,
                    CodiceIstanza = String.IsNullOrEmpty(dst.CODICEISTANZA) ? (int?)null : int.Parse(dst.CODICEISTANZA)
                }).ToArray();
        }
    }
}
