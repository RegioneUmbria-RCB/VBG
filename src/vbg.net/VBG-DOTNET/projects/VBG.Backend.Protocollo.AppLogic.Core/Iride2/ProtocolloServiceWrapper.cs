using VBG.Shared.Infrastructure.ServiceModel;
using ProtocolloIride2Service;
using VBG.Backend.Protocollo.AppLogic.Core.Iride2.Configuration;
using VBG.Backend.Protocollo.AppLogic.Core.Iride2.Services;
using VBG.Backend.Protocollo.AppLogic.Shared.Logs;
using VBG.Backend.Protocollo.AppLogic.Shared.Serialize;

namespace VBG.Backend.Protocollo.AppLogic.Core.Iride2
{
    public class ProtocolloServiceWrapper
    {
        private readonly ProtocolloLogs _logs;
        private readonly ProtocolloSerializer _serializer;
        private readonly ProtocolloClientServiceCreator _protocolloClientServiceCreator;
        private readonly VerticalizzazioniConfiguration _vert;
        private readonly string _operatore;
        private readonly string _ruolo;
        private readonly string _proxy;

        private IProtocolloIrideService _protocolloIrideService;

        public ProtocolloServiceWrapper(VerticalizzazioniConfiguration vert, string operatore, string ruolo, string proxy, ProtocolloLogs logs, ProtocolloSerializer serializer, IBindingFactory bindingFactory)
        {
            _logs = logs;
            _serializer = serializer;
            _operatore = operatore;
            _ruolo = ruolo;
            _proxy = proxy;
            _vert = vert;
            _protocolloClientServiceCreator = new ProtocolloClientServiceCreator(logs, bindingFactory, proxy, vert.Url);
            _protocolloIrideService = ProtocolloIrideFactory.Create(vert.CodiceAmministrazione, _protocolloClientServiceCreator, logs);
        }

        public DocumentoOut LeggiProtocolloDocumento(string idProtocollo, string annoProtocollo, string numeroProtocollo)
        {
            _logs.InfoFormat("Inizio metodo LeggiProtocolloDocumento, idprotocollo {0}, annoprotocollo {1}, numeroprotocollo {2}", idProtocollo, annoProtocollo, numeroProtocollo);
            if (!string.IsNullOrEmpty(idProtocollo) || (!string.IsNullOrEmpty(numeroProtocollo) && !string.IsNullOrEmpty(annoProtocollo)))
            {
                DocumentoOut docOut = new DocumentoOut();
                GC.Collect();
                if ((String.IsNullOrEmpty(idProtocollo) || _vert.UsaNumAnnoLeggi) && !String.IsNullOrEmpty(numeroProtocollo))
                {
                    string[] sNumProtSplit = numeroProtocollo.Split(new Char[] { '/' });
                    string sNumProtocollo = sNumProtSplit[0];
                    _logs.Info("Chiamata a LeggiProtocollo");
                    docOut = LeggiProtocollo(Convert.ToInt16(annoProtocollo), Convert.ToInt32(sNumProtocollo));
                    _logs.Info("Fine Chiamata a LeggiProtocollo");
                }
                else
                {
                    _logs.Debug("Chiamata a LeggiDocumento");
                    docOut = LeggiDocumento(Convert.ToInt32(idProtocollo));
                    _logs.Debug("Fine chiamata a LeggiDocumento");
                }

                return docOut;
            }
            else
                throw new Exception("NON È POSSIBILE RILEGGERE IL PROTOCOLLO/DOCUMENTO");
        }

        public virtual DocumentoOut LeggiProtocollo(short annoProtocollo, int numeroProtocollo)
        {
            _logs.InfoFormat("Chiamata a web method LeggiProtocollo, numero protocollo: {0}, anno protocollo: {1}, operatore: {2}, ruolo: {3}", numeroProtocollo, annoProtocollo, _operatore.ToUpper(), _ruolo);
            var response = _protocolloIrideService.LeggiProtocollo(annoProtocollo, numeroProtocollo, _operatore.ToUpper(), _ruolo);
            _logs.InfoFormat("Fine lettura del protocollo, numero: {0}, anno: {1}, operatore: {2}, ruolo: {3}", numeroProtocollo, annoProtocollo, _operatore.ToUpper(), _ruolo);

            if (_logs.IsDebugEnabled)
                _serializer.LogAndValidate(ProtocolloLogsConstants.LeggiProtocolloResponseFileName, response);

            return response;
        }

        public virtual DocumentoOut LeggiDocumento(int idProtocollo)
        {
            _logs.InfoFormat("Chiamata a LeggiDocumento, id protocollo: {0}, operatore: {1}, ruolo: {2}", idProtocollo, _operatore.ToUpper(), _ruolo);
            var response = _protocolloIrideService.LeggiDocumento(idProtocollo, _operatore.ToUpper(), _ruolo);
            _logs.InfoFormat("Fine lettura del documento, con id: {0}, operatore: {1}, ruolo: {2}", idProtocollo, _operatore.ToUpper(), _ruolo);

            if (_logs.IsDebugEnabled)
                _serializer.LogAndValidate(ProtocolloLogsConstants.LeggiProtocolloResponseFileName, response);

            return response;
        }

        public ProtocolloOut InserisciDocumento(ProtocolloIn protocolloIn)
        {
            _logs.Info("Chiamata a InserisciDocumento");
            return _protocolloIrideService.InserisciDocumento(protocolloIn);
        }

        public ProtocolloOut InserisciProtocollo(ProtocolloIn protocolloIn)
        {
            _logs.Info("Chiamata a InserisciProtocollo");
            return _protocolloIrideService.InserisciProtocollo(protocolloIn);
        }

        public string CollegaDocumento(string collegaDocumentoIn)
        {
            _logs.Info("Chiamata a InserisciProtocollo");
            return _protocolloIrideService.CollegaDocumento(collegaDocumentoIn);
        }
    }
}
