using Init.Sigepro.FrontEnd.AppLogic.ServiceCreators;
using log4net;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.GenerazioneDocumentiDomanda.GenerazioneCertificatoDiInvio.GestioneQrCode
{
    public class GenerazioneQrCodeServiceWrapper : IGenerazioneQrCodeServiceWrapper
    {
        ILog _log = LogManager.GetLogger(typeof(GenerazioneQrCodeServiceWrapper));
        QrCodeServiceCreator _serviceCreator;

        public GenerazioneQrCodeServiceWrapper(QrCodeServiceCreator serviceCreator)
        {
            this._serviceCreator = serviceCreator;
        }

        public QrVisuraIstanza CreateQrCode(int codiceIstanza, LivelloAutenticazioneVisuraEnum livelloAutenticazione)
        {
            using (var ws = this._serviceCreator.CreateService())
            {
                try
                {
                    var response = ws.Service.Visurapratica(new BackendQrCodeWs.VisurapraticaRequest
                    {
                        codiceIstanza = codiceIstanza.ToString(),
                        tipoAuth = livelloAutenticazione.ToString(),
                        token = ws.Token
                    });

                    return new QrVisuraIstanza(response.qrcode, response.url);
                }
                catch (Exception ex)
                {
                    ws.Service.Abort();

                    _log.Error($"Errore nella chiamata a Visurapratica per l'istanza {codiceIstanza}, livello autenticazione {livelloAutenticazione}: {ex.ToString()}");
                    throw;
                }
            }
        }
    }
}
