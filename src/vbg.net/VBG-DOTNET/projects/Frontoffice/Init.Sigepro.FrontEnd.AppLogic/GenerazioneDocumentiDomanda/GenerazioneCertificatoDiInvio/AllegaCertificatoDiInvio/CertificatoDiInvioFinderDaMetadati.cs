using Init.Sigepro.FrontEnd.AppLogic.GestioneVisuraIstanza.VisuraSigepro;
using System.Linq;

namespace Init.Sigepro.FrontEnd.AppLogic.GenerazioneDocumentiDomanda.GenerazioneCertificatoDiInvio.AllegaCertificatoDiInvio
{
    public class CertificatoDiInvioFinderDaMetadati : ICertificatoDiInvioFinder
    {
        private readonly IstanzeServiceCreator _istanzeServiceCreator;

        public CertificatoDiInvioFinderDaMetadati(IstanzeServiceCreator istanzeServiceCreator)
        {
            _istanzeServiceCreator = istanzeServiceCreator;
        }

        public int? GetCodiceOggettoCertificatoDiInvio(int idDomandaBackoffice)
        {
            using (var istanze = this._istanzeServiceCreator.CreateClient())
            {
                var oggetti = istanze.Service.GetOggettiIstanzaDaValoriMetadato(istanze.Token, idDomandaBackoffice, AllegaCertificatoDiInvioService.Constants.ChiaveMetadati, AllegaCertificatoDiInvioService.Constants.ValoreMetadati);

                if (!oggetti.Any())
                {
                    return null;
                }

                return oggetti.First();
            }
        }

        public bool PraticaHaCertificatoDiInvio(int idDomandaBackoffice)
        {
            return GetCodiceOggettoCertificatoDiInvio(idDomandaBackoffice).HasValue;
        }
    }
}
