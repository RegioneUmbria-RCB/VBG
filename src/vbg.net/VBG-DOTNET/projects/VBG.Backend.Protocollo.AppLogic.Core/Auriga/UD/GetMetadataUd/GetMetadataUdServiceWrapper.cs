using VBG.Shared.Infrastructure.ServiceModel;
using VBG.Backend.Protocollo.AppLogic.Core.Auriga.Helper;
using VBG.Backend.Protocollo.AppLogic.Shared.Logs;
using VBG.Backend.Protocollo.AppLogic.Shared.Serialize;
using VBG.Backend.Protocollo.AppLogic.Shared.WsDataClass;
using static VBG.Backend.Protocollo.AppLogic.Core.Auriga.Helper.CommonColumns;

namespace VBG.Backend.Protocollo.AppLogic.Core.Auriga.UD.GetMetadataUd
{
    public class GetMetadataUdServiceWrapper : ProxyServiceWrapper
    {
        private readonly IBindingFactory _bindingFactory;

        private static class Constants
        {
            public const string Titolo = "LEGGI PROTOCOLLO";
            public const string NameSpace = @"http://getmetadataud.webservices.repository2.auriga.eng.it";
            public const string ServiceName = "WSGetMetadataUd";
            public const string RequestServiceName = "get";
            public const EstremiRegNumTypeCategoriaReg registroDefault = EstremiRegNumTypeCategoriaReg.PG;
        }

        public GetMetadataUdServiceWrapper(ParametriRegoleInfo parametri, ProtocolloSerializer serializer, ProtocolloLogs log, ProxyRequestInfo request, IBindingFactory bindingFactory)
            : base(parametri, serializer, log, request, bindingFactory, Constants.Titolo, Constants.ServiceName)
        {
            this._bindingFactory = bindingFactory;
        }

        private Dictionary<int, string> ComponiFiltri(string assegnatario, string flusso)
        {
            if (string.IsNullOrEmpty(assegnatario) && string.IsNullOrEmpty(flusso))
                return null;

            var filters = new Dictionary<int, string>();

            if (!string.IsNullOrEmpty(assegnatario))
            {
                filters.Add((int)DocumentField.Assignees, assegnatario);
            }

            if (!string.IsNullOrEmpty(flusso))
            {
                if (flusso == "A")  //arrivo
                    flusso = "E";   //entrata

                if (flusso == "P")  //partenza
                    flusso = "U";   //uscita

                filters.Add((int)DocumentField.OriginType, flusso);
            }

            return filters;
        }

        public List<ResponseInfo> LeggiProtocollo(LeggiProtocolloRequest leggiProtocolloRequest)
        {
            // nuova logica che passa per CercaProtocolli bypassando la lettura per EstremiRegNumType
            if (string.IsNullOrEmpty(leggiProtocolloRequest.IdProtocollo))
            {
                var wrapper = new Folder.TrovaDocFolder.TrovaDocFolderServiceWrapper(this._parametri, this._serializer, this._logs, this._request, this._bindingFactory);

                // faccio una richiesta paginata
                leggiProtocolloRequest.Pagina = "1";

                var protocolli = wrapper.CercaProtocolli(leggiProtocolloRequest);

                // Se ho trovato un solo protocollo, lo ricerco con la logica dell'IdProtocollo
                if (protocolli.ServiceResponse.Righe.Count == 1)
                {
                    leggiProtocolloRequest.IdProtocollo = protocolli.ServiceResponse.Righe[0].Colonne.First(x => x.Nro == (int)DocumentField.IdUnitOrFolder).Valore;
                    return this.LeggiProtocollo(leggiProtocolloRequest); // Richiamo nuovamente il metodo con l'IdProtocollo trovato
                }

                this._logs.DebugFormat("numero pagine: {0}", protocolli.ServiceResponse.NroPagine);
                this._logs.DebugFormat("righe per pagina: {0}", protocolli.ServiceResponse.Righe);
                this._logs.DebugFormat("tot record: {0}", protocolli.ServiceResponse.NroTotaleRecord);

                var respInfo = ResponseInfoAdapterHelper.FilterAndConvertToGetMetadataUdResponseInfo(
                    protocolli,
                    this.ComponiFiltri(leggiProtocolloRequest.Assegnatario, leggiProtocolloRequest.Flusso) // new Dictionary<int, string>() { { (int)DocumentField.Assignees, leggiProtocolloRequest.Assegnatario } }
                );

                if (protocolli.ServiceResponse.NroPagine > 1)
                {
                    // per la prima ho già filtrato e convertito quindi proseguo con le restanti
                    for (var i = 2; i <= protocolli.ServiceResponse.NroPagine; i++)
                    {
                        leggiProtocolloRequest.Pagina = i.ToString();
                        var altriProtocolli = wrapper.CercaProtocolli(leggiProtocolloRequest);

                        respInfo.AddRange(ResponseInfoAdapterHelper.FilterAndConvertToGetMetadataUdResponseInfo(
                            altriProtocolli,
                            this.ComponiFiltri(leggiProtocolloRequest.Assegnatario, leggiProtocolloRequest.Flusso)
                        ));
                    }
                }

                return respInfo;
            }

            try
            {
                using (var ws = this.CreaWebService())
                {
                    var letturaRequest = new EstremiXIdentificazioneUD
                    {
                        //Item = !String.IsNullOrEmpty(leggiProtocolloRequest.IdProtocollo) ? (object)leggiProtocolloRequest.IdProtocollo : new EstremiRegNumType
                        //{
                        //    AnnoReg = leggiProtocolloRequest.AnnoProtocollo,
                        //    NumReg = leggiProtocolloRequest.NumeroProtocollo,
                        //    CategoriaReg = Constants.registroDefault,
                        //    SiglaReg = null
                        //},
                        Item = (object)leggiProtocolloRequest.IdProtocollo
                    };

                    var letturaRequestXML = this._serializer.Serialize("letturaRequest.xml", letturaRequest);

                    this._request.xml = Utility.HtmlEncodeContent(letturaRequestXML);
                    this._request.hash = this.getHashSHA1();

                    var service = this.CreateServiceRequest(Constants.NameSpace, Constants.RequestServiceName, TipoOperazione.LETTURA_REQUEST);
                    var xmlService = this._serializer.Serialize(ProtocolloLogsConstants.LeggiProtocolloRequestFileName, service);

                    this.LogInfoRequestWS(xmlService);

                    var serviceResponse = ws.AurigaProxy(service);
                    var responseXml = this._serializer.Serialize(ProtocolloLogsConstants.LeggiProtocolloResponseFileName, serviceResponse);

                    this.LogInfoResponseWS(responseXml);

                    var response = new ResponseInfoAdapter(serviceResponse).Adatta();

                    if (response.WsResult != "1")
                        throw new Exception(response.WsError);

                    this.LogSuccess();

                    return new List<ResponseInfo>() { response };
                }
            }
            catch (Exception ex)
            {
                throw new Exception($"{this._titolo} fallita: {ex.Message}", ex);
            }
        }
    }
}
