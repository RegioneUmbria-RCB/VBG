
using Init.Sigepro.FrontEnd.AppLogic.Common;
using Init.Sigepro.FrontEnd.AppLogic.GenerazioneDocumentiDomanda.GenerazioneCertificatoDiInvio.StrategiaLetturaRiepilogo;
using Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti;
using Init.Sigepro.FrontEnd.Infrastructure.FileEncoding;
using System;

namespace Init.Sigepro.FrontEnd.AppLogic.GenerazioneDocumentiDomanda.GenerazioneCertificatoDiInvio
{
    public class RiepilogoDomandaReader
    {
        ISoftwareResolver _aliasSoftwareResolver;
        IOggettiService _oggettiService;

        public RiepilogoDomandaReader(ISoftwareResolver aliasSoftwareResolver, IOggettiService oggettiService)
        {
            if (aliasSoftwareResolver == null)
                throw new ArgumentNullException(nameof(aliasSoftwareResolver));

            if (oggettiService == null)
                throw new ArgumentNullException(nameof(oggettiService));
            //Condition.Requires(aliasSoftwareResolver, "aliasSoftwareResolver").IsNotNull();
            //Condition.Requires(oggettiService, "oggettiService").IsNotNull();

            this._aliasSoftwareResolver = aliasSoftwareResolver;
            this._oggettiService = oggettiService;
        }

        public string Read(int idDomandaBackoffice, IStrategiaIndividuazioneCertificatoInvio strategiaLetturaRiepilogo)
        {
            var codiceOggetto = strategiaLetturaRiepilogo.CodiceOggetto(idDomandaBackoffice);

            var oggetto = _oggettiService.GetById(codiceOggetto);

            if (oggetto == null)
                throw new InvalidOperationException("Non è stato possibile leggere l'oggetto con id " + codiceOggetto);

            return UnknownEncodingToString.Convert(oggetto.FileContent);
        }
    }
}
