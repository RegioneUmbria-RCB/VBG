using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;
using Init.Sigepro.FrontEnd.AppLogic.GenerazioneDocumentiDomanda.GenerazioneCertificatoDiInvio.Configurazione;
using Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti;
using Init.Sigepro.FrontEnd.AppLogic.STC.Service;

namespace Init.Sigepro.FrontEnd.AppLogic.GenerazioneDocumentiDomanda.GenerazioneCertificatoDiInvio.AllegaCertificatoDiInvio
{
    public class AllegaCertificatoDiInvioService : IAllegaCertificatoDiInvioService
    {
        public static class Constants
        {
            public const string ChiaveMetadati = "TIPO_DOCUMENTO";
            public const string ValoreMetadati = "CertificatoInvio";
        }

        private readonly ICertificatoDiInvioFinder _certificatoDiInvioFinder;
        private readonly IStcServiceCreator _stcServiceCreator;
        private readonly IOggettiService _oggettiService;
        private readonly IConfigurazione<ParametriGenerazioneCertificatoInvio> _configurazione;

        public AllegaCertificatoDiInvioService(ICertificatoDiInvioFinder certificatoDiInvioFinder, IStcServiceCreator stcServiceCreator, IOggettiService oggettiService, IConfigurazione<ParametriGenerazioneCertificatoInvio> configurazione)
        {
            _certificatoDiInvioFinder = certificatoDiInvioFinder;
            _stcServiceCreator = stcServiceCreator;
            _oggettiService = oggettiService;
            _configurazione = configurazione;
        }

        public void AllegaSeNonEsiste(int idDomandaBackoffice, BinaryFile certificatoDiInvio)
        {
            if (!this._certificatoDiInvioFinder.PraticaHaCertificatoDiInvio(idDomandaBackoffice))
            {
                var codiceOggetto = this._oggettiService.InserisciOggetto(certificatoDiInvio);
                this.AllegaCertificatoDiInvio(idDomandaBackoffice, codiceOggetto);
            }
        }

        private void AllegaCertificatoDiInvio(int idDomandaBackoffice, int codiceOggetto)
        {
            using (var stc = this._stcServiceCreator.CreateClient())
            {
                var errori = stc.Service.AggiungiDocumenti(new StcService.AggiungiDocumentiRequest
                {
                    sportelloMittente = this._stcServiceCreator.ConfigurazioneStc.NodoMittente.AsSportelloType(),
                    sportelloDestinatario = this._stcServiceCreator.ConfigurazioneStc.NodoDestinatario.AsSportelloType(),
                    token = stc.Token,
                    idPraticaDest = idDomandaBackoffice.ToString(),
                    documenti = new StcService.DocumentiType[]
                    {
                        new StcService.DocumentiType
                        {
                            id = codiceOggetto.ToString(),
                            documento = this._configurazione.Parametri.DescrizioneFile,
                            tipoDocumento = Constants.ValoreMetadati,
                            allegati = new StcService.AllegatiType
                            {
                                id = codiceOggetto.ToString()
                            }
                        }
                    }
                });

                if (!errori.Any())
                {
                    // Tutto ok :)
                    return;
                }
                var errMsg = new StringBuilder();

                errori.Select(x => $"{x.numeroErrore} - {x.descrizione}").ToList().ForEach(err => errMsg.Append(err).Append(Environment.NewLine));

                throw new Exception($"Non è stato possibile allegare il certificato di invio alla pratica {idDomandaBackoffice}. Descrizione dell'errore: {errMsg.ToString()}");
            }

        }
    }
}
