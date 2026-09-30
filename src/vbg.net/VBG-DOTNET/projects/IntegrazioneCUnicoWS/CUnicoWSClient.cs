using IntegrazioneCUnicoWS.CUnicoWS;
using log4net;
using System;
using System.Net;
using System.ServiceModel;

namespace IntegrazioneCUnicoWS
{
    public class CUnicoWSClient
    {
        private readonly Logger _logger = null;
        private readonly CUnicoConfigurazione _configurazione;
        public CUnicoWSClient(CUnicoConfigurazione configurazione)
        {
            this._configurazione = configurazione ?? throw new ArgumentNullException(nameof(configurazione));

            this._logger = new Logger(LogManager.GetLogger(this.GetType()));

            this._logger.Debug("Configurazione", configurazione);
        }
        public AnnullaConcessioneResponse AnnullaConcessione(AnnullaConcessioneRequest request)
        {

            var endPoint = new EndpointAddress(this._configurazione.Url);
#if NET9_0_OR_GREATER
            var binding = new BasicHttpBinding();
#else
            var binding = new BasicHttpBinding(this._configurazione.BindingName);
#endif

            ServicePointManager.SecurityProtocol = (SecurityProtocolType)3072;
            if (this._configurazione.Url.StartsWith("HTTPS", StringComparison.OrdinalIgnoreCase))
            {
                binding.Security.Mode = BasicHttpSecurityMode.Transport;
            }

            using (var ws = new CUNICOWSAClient(binding, endPoint))
            {
                try
                {
                    var richiesta = request.ToannullaConcessioneRequest(this._configurazione);

                    this._logger.Debug("Richiesta ws.annullaConcessione", richiesta);

                    var risposta = ws.annullaConcessione(richiesta);

                    this._logger.Debug("Risposta ws.annullaConcessione", risposta);

                    var response = AnnullaConcessioneResponse.FromannullaConcessioneRisposta(richiesta, risposta);

                    return response;
                }
                catch (Exception ex)
                {
                    this._logger.Error($"Errore durante la chiamata a AnnullaConcessione: {ex}", request);
                    throw new CUnicoWSException(ex.Message, ex);
                }
            }

        }
        public CessaConcessioneResponse CessaConcessione(CessaConcessioneRequest request)
        {
            var endPoint = new EndpointAddress(this._configurazione.Url);
#if NET9_0_OR_GREATER
            var binding = new BasicHttpBinding();
#else
            var binding = new BasicHttpBinding(this._configurazione.BindingName);
#endif

            ServicePointManager.SecurityProtocol = (SecurityProtocolType)3072;
            if (this._configurazione.Url.StartsWith("HTTPS", StringComparison.OrdinalIgnoreCase))
            {
                binding.Security.Mode = BasicHttpSecurityMode.Transport;
            }

            using (var ws = new CUNICOWSAClient(binding, endPoint))
            {
                try
                {
                    var richiesta = request.TomodificaConcessioneRichiesta(this._configurazione);

                    this._logger.Debug("Richiesta ws.cessaConcessione", richiesta);

                    var risposta = ws.cessaConcessione(richiesta);

                    this._logger.Debug("Risposta ws.cessaConcessione", risposta);

                    var response = CessaConcessioneResponse.FromModificaConcessioneRisposta(richiesta, risposta);

                    return response;
                }
                catch (Exception ex)
                {
                    this._logger.Error($"Errore durante la chiamata a CessaConcessione: {ex}", request);
                    throw new CUnicoWSException(ex.Message, ex);
                }
            }

        }
        public ModificaConcessioneResponse ModificaConcessione(ModificaConcessioneRequest request)
        {
            var endPoint = new EndpointAddress(this._configurazione.Url);

#if NET9_0_OR_GREATER
            var binding = new BasicHttpBinding();
#else
            var binding = new BasicHttpBinding(this._configurazione.BindingName);
#endif

            ServicePointManager.SecurityProtocol = (SecurityProtocolType)3072;
            if (this._configurazione.Url.StartsWith("HTTPS", StringComparison.OrdinalIgnoreCase))
            {
                binding.Security.Mode = BasicHttpSecurityMode.Transport;
            }

            using (var ws = new CUNICOWSAClient(binding, endPoint))
            {
                try
                {
                    this._logger.Debug("Richiesta CUnicoWSClient.ModificaConcessione", request);

                    var richiesta = request.TomodificaConcessioneRichiesta(this._configurazione);

                    this._logger.Debug("Richiesta ws.modificaConcessione", richiesta);

                    var risposta = ws.modificaConcessione(richiesta);

                    this._logger.Debug("Risposta ws.modificaConcessione", risposta);

                    var response = ModificaConcessioneResponse.FromModificaConcessioneRisposta(richiesta, risposta);

                    return response;
                }
                catch (Exception ex)
                {
                    this._logger.Error($"Errore durante la chiamata a ModificaConcessione: {ex}", request);
                    throw new CUnicoWSException(ex.Message, ex);
                }
            }
        }
        public InserisciConcessioneResponse InserisciConcessione(InserisciConcessioneRequest request)
        {
            var endPoint = new EndpointAddress(this._configurazione.Url);

#if NET9_0_OR_GREATER
            var binding = new BasicHttpBinding();
#else
            var binding = new BasicHttpBinding(this._configurazione.BindingName);
#endif

            ServicePointManager.SecurityProtocol = (SecurityProtocolType)3072;
            if (this._configurazione.Url.StartsWith("HTTPS", StringComparison.OrdinalIgnoreCase))
            {

                binding.Security.Mode = BasicHttpSecurityMode.Transport;
            }


            using (var ws = new CUNICOWSAClient(binding, endPoint))
            {
                try
                {
                    var richiesta = request.ToinserisciConcessioneRichiesta(this._configurazione);

                    this._logger.Debug("Richiesta ws.inserisciConcessione", richiesta);

                    var risposta = ws.inserisciConcessione(richiesta);

                    this._logger.Debug("Risposta ws.inserisciConcessione", risposta);

                    return InserisciConcessioneResponse.FromInserisciConcessioneRisposta(richiesta, risposta);
                }
                catch (Exception ex)
                {
                    this._logger.Error($"Errore durante la chiamata a InserisciConcessione: {ex}", request);
                    throw new CUnicoWSException(ex.Message, ex);
                }
            }
        }
        public PreventivoResponse Preventivo(PreventivoRequest request)
        {
            var endPoint = new EndpointAddress(this._configurazione.Url);

#if NET9_0_OR_GREATER
            var binding = new BasicHttpBinding();
#else
            var binding = new BasicHttpBinding(this._configurazione.BindingName);
#endif

            ServicePointManager.SecurityProtocol = (SecurityProtocolType)3072;
            if (this._configurazione.Url.StartsWith("HTTPS", StringComparison.OrdinalIgnoreCase))
            {

                binding.Security.Mode = BasicHttpSecurityMode.Transport;
            }

            using (var ws = new CUNICOWSAClient(binding, endPoint))
            {
                try
                {
                    var richiesta = request.TopreventivoRichiesta(this._configurazione);

                    this._logger.Debug("Richiesta ws.preventivo", richiesta);

                    var risposta = ws.preventivo(richiesta);

                    this._logger.Debug("Risposta ws.preventivo", risposta);

                    return PreventivoResponse.FrompreventivoRisposta(risposta);
                }
                catch (Exception ex)
                {
                    this._logger.Error($"Errore durante la chiamata a Preventivo: {ex}", request);
                    throw new CUnicoWSException(ex.Message, ex);
                }
            }

        }
        public SospensioneConcessioneResponse SospensioneConcessione(SospensioneConcessioneRequest request)
        {
            var endPoint = new EndpointAddress(this._configurazione.Url);

#if NET9_0_OR_GREATER
            var binding = new BasicHttpBinding();
#else
            var binding = new BasicHttpBinding(this._configurazione.BindingName);
#endif

            ServicePointManager.SecurityProtocol = (SecurityProtocolType)3072;
            if (this._configurazione.Url.StartsWith("HTTPS", StringComparison.OrdinalIgnoreCase))
            {
                binding.Security.Mode = BasicHttpSecurityMode.Transport;
            }

            using (var ws = new CUNICOWSAClient(binding, endPoint))
            {
                try
                {
                    var richiesta = request.TosospensioneConcessioneRichiesta(this._configurazione);

                    this._logger.Debug("Richiesta ws.sospensioneConcessione", richiesta);

                    var risposta = ws.sospensioneConcessione(richiesta);

                    this._logger.Debug("Risposta ws.sospensioneConcessione", risposta);

                    return SospensioneConcessioneResponse.FromsospensioneConcessioneRisposta(risposta);
                }
                catch (Exception ex)
                {
                    this._logger.Error($"Errore durante la chiamata a SospensioneConcessione: {ex}", request);
                    throw new CUnicoWSException(ex.Message, ex);
                }
            }

        }
        public InserisciSoggettoResponse InserisciSoggetto(InserisciSoggettoRequest request)
        {
            var endPoint = new EndpointAddress(this._configurazione.Url);

#if NET9_0_OR_GREATER
            var binding = new BasicHttpBinding();
#else
            var binding = new BasicHttpBinding(this._configurazione.BindingName);
#endif

            ServicePointManager.SecurityProtocol = (SecurityProtocolType)3072;
            if (this._configurazione.Url.StartsWith("HTTPS", StringComparison.OrdinalIgnoreCase))
            {
                binding.Security.Mode = BasicHttpSecurityMode.Transport;
            }

            using (var ws = new CUNICOWSAClient(binding, endPoint))
            {
                try
                {
                    var richiesta = request.ToinserisciSoggettoRichiesta(this._configurazione);

                    this._logger.Debug("Richiesta ws.inserisciSoggetto", richiesta);

                    var risposta = ws.inserisciSoggetto(richiesta);

                    this._logger.Debug("Risposta ws.inserisciSoggetto", risposta);

                    return InserisciSoggettoResponse.FrominserisciSoggettoRisposta(risposta);
                }
                catch (Exception ex)
                {
                    this._logger.Error($"Errore durante la chiamata a InserisciSoggetto: {ex}", request);
                    throw new CUnicoWSException(ex.Message, ex);
                }
            }
        }
        public GetSoggettoResponse GetSoggetto(string cfPiva)
        {
            var endPoint = new EndpointAddress(this._configurazione.Url);

#if NET9_0_OR_GREATER
            var binding = new BasicHttpBinding();
#else
            var binding = new BasicHttpBinding(this._configurazione.BindingName);
#endif

            ServicePointManager.SecurityProtocol = (SecurityProtocolType)3072;
            if (this._configurazione.Url.StartsWith("HTTPS", StringComparison.OrdinalIgnoreCase))
            {

                binding.Security.Mode = BasicHttpSecurityMode.Transport;
            }


            using (var ws = new CUNICOWSAClient(binding, endPoint))
            {
                try
                {
                    var richiesta = new richiestaSoggetti
                    {
                        codBel = this._configurazione.CodBel,
                        codUte = this._configurazione.CodUte,
                        codFis = cfPiva
                    };

                    this._logger.Debug("Richiesta ws.getSoggetto", richiesta);

                    var risposta = ws.getSoggetto(richiesta);

                    this._logger.Debug("Risposta ws.getSoggetto", risposta);

                    return GetSoggettoResponse.FromrichiestaSoggettiResponse(richiesta, risposta);

                }
                catch (Exception ex)
                {
                    this._logger.Error($"Errore durante la chiamata a GetSoggetto: {ex}", cfPiva);
                    throw new CUnicoWSException(ex.Message, ex);
                }
            }
        }
        public GetPosizioneDebitoriaIUVResponse GetPosizioneDebitoriaIUV(GetPosizioneDebitoriaIUVRequest request)
        {

            var endPoint = new EndpointAddress(this._configurazione.Url);

#if NET9_0_OR_GREATER
            var binding = new BasicHttpBinding();
#else
            var binding = new BasicHttpBinding(this._configurazione.BindingName);
#endif

            ServicePointManager.SecurityProtocol = (SecurityProtocolType)3072;
            if (this._configurazione.Url.StartsWith("HTTPS", StringComparison.OrdinalIgnoreCase))
            {
                binding.Security.Mode = BasicHttpSecurityMode.Transport;
            }

            using (var ws = new CUNICOWSAClient(binding, endPoint))
            {
                try
                {
                    var richiesta = request.ToposizioneDebitoriaIUVRichiesta(this._configurazione);

                    this._logger.Debug("Richiesta ws.getPosizioneDebitoriaIUV", richiesta);

                    var risposta = ws.getPosizioneDebitoriaIUV(richiesta);

                    this._logger.Debug("Risposta ws.getPosizioneDebitoriaIUV", risposta);

                    return GetPosizioneDebitoriaIUVResponse.FromposizioneDebitoriaIUVResponse(risposta);
                }
                catch (Exception ex)
                {
                    this._logger.Error($"Errore durante la chiamata a GetPosizioneDebitoriaIUV: {ex}", request);
                    throw new CUnicoWSException(ex.Message, ex);
                }
            }


            /*
            return this.CallService(ws =>
            {
                try
                {
                    posizioneDebitoriaIUVRichiesta richiesta = request.ToposizioneDebitoriaIUVRichiesta(this._configurazione);

                    this._logger.Debug("Richiesta ws.getPosizioneDebitoriaIUV", richiesta);

                    var risposta = ws.getPosizioneDebitoriaIUV(richiesta);

                    this._logger.Debug("Risposta ws.getPosizioneDebitoriaIUV", risposta);

                    return GetPosizioneDebitoriaCFIResponse.FromposizioneDebitoriaIUVResponse(risposta);
                }
                catch (Exception ex)
                {
                    this._logger.Error($"Errore durante la chiamata a getPosizioneDebitoriaIUV: {ex}", request);
                    throw new CUnicoWSException(ex.Message, ex);
                }
            });
            */
        }
        public SubentraConcessioneResponse SubentraConcessione(SubentraConcessioneRequest request)
        {
            var endPoint = new EndpointAddress(this._configurazione.Url);

#if NET9_0_OR_GREATER
            var binding = new BasicHttpBinding();
#else
            var binding = new BasicHttpBinding(this._configurazione.BindingName);
#endif

            ServicePointManager.SecurityProtocol = (SecurityProtocolType)3072;
            if (this._configurazione.Url.StartsWith("HTTPS", StringComparison.OrdinalIgnoreCase))
            {
                binding.Security.Mode = BasicHttpSecurityMode.Transport;
            }



            using (var ws = new CUNICOWSAClient(binding, endPoint))
            {
                try
                {
                    var richiesta = request.TosubentroConcessioneRichiesta(this._configurazione);

                    this._logger.Debug("Richiesta ws.subentroConcessione", richiesta);

                    var risposta = ws.subentroConcessione(richiesta);

                    this._logger.Debug("Risposta ws.subentroConcessione", risposta);

                    var response = SubentraConcessioneResponse.FromsubentroConcessioneRisposta(richiesta, risposta);

                    return response;
                }
                catch (Exception ex)
                {
                    this._logger.Error($"Errore durante la chiamata a SubentraConcessione: {ex}", request);
                    throw new CUnicoWSException(ex.Message, ex);
                }
            }
        }
        public InserimentoVerbaleResponse InserisciVerbale(InserimentoVerbaleRequest request)
        {
            var endPoint = new EndpointAddress(this._configurazione.Url);

#if NET9_0_OR_GREATER
            var binding = new BasicHttpBinding();
#else
            var binding = new BasicHttpBinding(this._configurazione.BindingName);
#endif

            ServicePointManager.SecurityProtocol = (SecurityProtocolType)3072;
            if (this._configurazione.Url.StartsWith("HTTPS", StringComparison.OrdinalIgnoreCase))
            {
                binding.Security.Mode = BasicHttpSecurityMode.Transport;
            }

            using (var ws = new CUNICOWSAClient(binding, endPoint))
            {
                try
                {
                    var richiesta = request.ToinserisciVerbaleRichiesta(this._configurazione);

                    this._logger.Debug("Richiesta ws.inserisciVerbale", richiesta);

                    var risposta = ws.inserisciVerbale(richiesta);

                    this._logger.Debug("Risposta ws.inserisciVerbale", risposta);

                    var response = InserimentoVerbaleResponse.FrominserisciVerbaleRisposta(richiesta, risposta);

                    return response;
                }
                catch (Exception ex)
                {
                    this._logger.Error($"Errore durante la chiamata a InserisciVerbale: {ex}", request);
                    throw new CUnicoWSException(ex.Message, ex);
                }
            }
        }


        /*
        private T CallService<T>(Func<CUNICOWSAClient, T> operation)
        {
            var endPoint = new EndpointAddress(this._configurazione.Url);

            var binding = new BasicHttpBinding(this._configurazione.BindingName);

            using (var ws = new CUNICOWSAClient(binding, endPoint))
            {
                try
                {

                    return operation(ws);
                }
                catch (Exception)
                {
                    ws.Abort();

                    throw;
                }
            }
        }
        private string XmlSerializeToString(object objectInstance)
        {
            var overrides = new OverrideXml()
                    .Override<inserisciConcessioneRisposta>()
                    .Member("modelloPagoPA").XmlIgnore()
                    .Commit();

            var serializer = new XmlSerializer(objectInstance.GetType(), overrides);

            var memoryStream = new MemoryStream();
            var streamWriter = new StreamWriter(memoryStream, System.Text.Encoding.UTF8);

            serializer.Serialize(streamWriter, objectInstance);

            memoryStream.Seek(0, SeekOrigin.Begin);
            var streamReader = new StreamReader(memoryStream, System.Text.Encoding.UTF8);
            return streamReader.ReadToEnd();
        }
        */
    }
}
