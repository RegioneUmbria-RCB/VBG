using VBG.Shared.Infrastructure.ServiceModel;
using VBG.Backend.Protocollo.AppLogic.Core.Auriga.Folder.Exceptions;
using VBG.Backend.Protocollo.AppLogic.Core.Auriga.Folder.GetMetadataFolder;
using VBG.Backend.Protocollo.AppLogic.Shared.Data;
using VBG.Backend.Protocollo.AppLogic.Shared.Logs;
using VBG.Backend.Protocollo.AppLogic.Shared.Serialize;

namespace VBG.Backend.Protocollo.AppLogic.Core.Auriga.Folder.NewFolder
{
    public class NewFolderServiceWrapper : ProxyServiceWrapper
    {
        private static class Constants
        {
            public const string Titolo = "FASCICOLAZIONE";
            public const string NameSpace = @"http://addfolder.webservices.repository2.auriga.eng.it";
            public const string ServiceName = "WSAddFolder";
            public const string RequestServiceName = "add";
            public const string FlagTipoFascicolo = "F";
            public const string FolderEsistenteException = "nome duplicato";
        }

        public NewFolderServiceWrapper(ParametriRegoleInfo parametri, ProtocolloSerializer serializer, ProtocolloLogs log, ProxyRequestInfo request, IBindingFactory bindingFactory)
            : base(parametri, serializer, log, request, bindingFactory, Constants.Titolo, Constants.ServiceName)
        {
        }

        public ResponseInfo Fascicola(Fascicolo fascicolo)
        {
            try
            {
                using (var ws = this.CreaWebService())
                {
                    var fascicoloRequest = new NewFolder
                    {
                        Item = new FascDiTitolarioType
                        {
                            Classificazione = new ClassificazioneType
                            {

                                LivelloClassificazione = Utility.GetLivelloGerarchiaDaClassifica(fascicolo.Classifica)
                            },
                            FlagTipo = Constants.FlagTipoFascicolo

                        },
                        Apertura = new NewFolderApertura
                        {
                            Item = fascicolo.AnnoFascicolo.ToString(),
                            UO = null,
                            Utente = null
                        },
                        Oggetto = fascicolo.Oggetto
                    };

                    var newFolderRequestXML = Utility.HtmlEncodeContent(this._serializer.Serialize("newFolderRequest.xml", fascicoloRequest));

                    this._request.xml = newFolderRequestXML;
                    this._request.hash = this.getHashSHA1();

                    var service = this.CreateServiceRequest(Constants.NameSpace, Constants.RequestServiceName, TipoOperazione.NEW_FOLDER_REQUEST);
                    var xmlService = this._serializer.Serialize(ProtocolloLogsConstants.CreaFascicoloRequestFileName, service);

                    this.LogInfoRequestWS(xmlService);

                    var serviceResponse = ws.AurigaProxy(service);
                    var responseXml = this._serializer.Serialize(ProtocolloLogsConstants.CreaFascicoloResponseFileName, serviceResponse);

                    this.LogInfoResponseWS(responseXml);

                    var response = new ResponseInfoAdapter(serviceResponse).Adatta();

                    if (response.WsResult != "1")
                        throw new Exception(response.WsError);

                    this.LogSuccess();

                    return response;
                }
            }
            catch (Exception ex)
            {
                if (ex.Message.ToLower().Contains(Constants.FolderEsistenteException))
                {
                    var v = this.GetFascicoloLibreriaPathFromErrore(ex.Message, fascicolo.Oggetto);
                    throw new FolderExistException(v.Libreria, v.PathNome, $"{this._titolo} fallita: {ex.Message}", ex);
                }
                else
                {
                    throw new Exception($"{this._titolo} fallita: {ex.Message}", ex);
                }
            }

        }

        private OggDiTabDiSistemaType SetTipoDiDettaglio()
        {
            return null;
        }

        private LibreriaPath GetFascicoloLibreriaPathFromErrore(string errore, string nomeFolder)
        {
            var startString = "errore = nome duplicato: in ";
            var endString = " esiste già un fascicolo con il nome indicato";

            if (!string.IsNullOrEmpty(errore))
            {
                var retVal = new LibreriaPath();

                var v = errore.ToLower().IndexOf(startString);
                var s = errore.Substring(v + startString.Length);

                v = s.IndexOf(endString);
                s = s.Substring(0, v);

                retVal.Libreria = s.Split('\\')[0];
                retVal.PathNome = s.Substring(retVal.Libreria.Length + 1) + "\\" + nomeFolder;

                if (!String.IsNullOrEmpty(this._parametri.CaratteriDaEliminare))
                {
                    var c = this._parametri.CaratteriDaEliminare.ToCharArray();

                    for (var i = 0; i < c.Length; i++)
                    {
                        retVal.Libreria = retVal.Libreria.Replace(c[i], '%');
                        retVal.PathNome = retVal.PathNome.Replace(c[i], '%');
                    }
                }

                return retVal;
            }
            return null;
        }
    }
}
