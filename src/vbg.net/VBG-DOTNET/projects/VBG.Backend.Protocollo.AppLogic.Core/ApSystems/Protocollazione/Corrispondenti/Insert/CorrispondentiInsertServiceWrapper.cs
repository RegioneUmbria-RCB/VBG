using VBG.Shared.Infrastructure.ServiceModel;
using Microsoft.AspNetCore.Http.Authentication;
using ProtocolloApSystemsService;
using VBG.Backend.Protocollo.AppLogic.Core.ApSystems.Protocollazione.Comuni;
using VBG.Backend.Protocollo.AppLogic.Shared.Interfaces;
using VBG.Backend.Protocollo.AppLogic.Shared.Logs;
using VBG.Backend.Protocollo.AppLogic.Shared.Serialize;


namespace VBG.Backend.Protocollo.AppLogic.Core.ApSystems.Protocollazione.Corrispondenti.Insert
{
    public class CorrispondentiInsertServiceWrapper
    {
        private IBindingFactory _bindingFactory;
        private readonly ProtocolloLogs _log;
        private readonly ProtocolloSerializer _serializer;
        private readonly ProtocolloClientServiceCreator _protocolloClientServiceCreator;
        private readonly string _operatore;
        private readonly AuthenticationDetails _authenticationDetails;

        public CorrispondentiInsertServiceWrapper(ProtocolloLogs logs, ProtocolloSerializer serializer, IBindingFactory bindingFactory, string username, string password, string url, string operatore)
        {
            this._bindingFactory = bindingFactory;
            this._log = logs;
            this._serializer = serializer;
            this._protocolloClientServiceCreator = new ProtocolloClientServiceCreator(logs, bindingFactory, url);
            this._operatore = operatore;
            this._authenticationDetails = new AuthenticationDetails() { UserName = username, Password = password };
        }

        public corrispondenti.corrispondenteRow InsertCorrispondente(IAnagraficaAmministrazione anag, string userName)
        {
            try
            {
                using (var ws = _protocolloClientServiceCreator.CreateClient())
                {
                    if (String.IsNullOrEmpty(anag.CodiceFiscalePartitaIva))
                        throw new Exception("IL CODICE FISCALE O LA PARTITA IVA NON SONO STATI VALORIZZATI");

                    string codiceComune = String.Empty;
                    if (!String.IsNullOrEmpty(anag.CodiceIstatResidenza))
                    {
                        var comuniSrv = new ComuniServiceWrapper(_log, _serializer, _authenticationDetails, _protocolloClientServiceCreator.GetEndpointUrl(), _bindingFactory);
                        var com = comuniSrv.GetComuneByCodiceIstat(anag.CodiceIstatResidenza);

                        if (com != null)
                            codiceComune = com.codice;
                    }
                    _log.InfoFormat("INSERIMENTO ANAGRAFICA CODICE {0}, NOMINATIVO {1}, CODICE FISCALE / PARTITA IVA: {2}", anag.Codice, anag.NomeCognome, anag.CodiceFiscalePartitaIva);
                    var response = ws.Service.InsertCorrispondente(_authenticationDetails, anag.CodiceFiscalePartitaIva, anag.NomeCognome, anag.Indirizzo, anag.Cap, codiceComune, anag.Pec, anag.Telefono, anag.Fax, userName);
                    _log.Info("INSERIMENTO ANAGRAFICA AVVENUTO CON SUCCESSO");

                    var ds = new corrispondenti();

                    ds.Merge(response);

                    if (ds.ContieneErroreCorrispondente())
                        throw new Exception(ds.GetDescrizioneErroreCorrispondente());

                    return ds.corrispondente[0];
                }
            }
            catch (Exception ex)
            {
                throw new Exception(String.Format("ERRORE RESTITUITO DURANTE L'INSERIMENTO DEL CORRISPONDENTE {0}, CODICE {1}, {2}", anag.NomeCognome, anag.Codice, ex.Message), ex);
            }

        }
    }
}
