using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Verticalizzazioni;
using Init.SIGePro.Protocollo.ProtocolloEnumerators;
using Init.SIGePro.Protocollo.Logs;
using Init.SIGePro.Protocollo.ProtocolloServices.OperatoreProtocollo;
using Init.SIGePro.Protocollo.ProtocolloFactories;
using Init.SIGePro.Protocollo.ProtocolloInterfaces;
using System.Reflection;

namespace Init.SIGePro.Protocollo.ProtocolloServices
{
    public class AttivazioneProtocolloService
    {
        public VerticalizzazioneProtocolloAttivo VertProtocolloAttivo { get; private set; }

        private ResolveDatiProtocollazioneService _datiProtocollazione;
        private ProtocolloLogs _protocolloLogs;

        public AttivazioneProtocolloService(ResolveDatiProtocollazioneService datiProtocollazione, ProtocolloLogs protocolloLogs)
        {
            if (datiProtocollazione == null)
                throw new ArgumentNullException("datiProtocollazione");

            if (protocolloLogs == null)
                throw new ArgumentNullException("protocolloLogs");

            _datiProtocollazione = datiProtocollazione;
            _protocolloLogs = protocolloLogs;

            VertProtocolloAttivo = new VerticalizzazioneProtocolloAttivo(this._datiProtocollazione.IdComuneAlias, this._datiProtocollazione.Software, this._datiProtocollazione.CodiceComune);
        }

        public ProtocolloBase AttivaProtocollo()
        {
            var tipoProtocollo = GetTipoProtocollo();

            var objAssembly = Assembly.Load("SIGePro.Protocollo");

            if (tipoProtocollo == ProtocolloEnum.TipiProtocollo.NESSUNO)
                throw new Exception("LA VERTICALIZZAZIONE PROTOCOLLO_ATTIVO NON E' VALORIZZATA");

            Type tCls = objAssembly.GetType(String.Format("Init.SIGePro.Protocollo.{0}", tipoProtocollo.ToString()));
            var protoBase = (ProtocolloBase)Activator.CreateInstance(tCls);

            protoBase.InizializzaProtocolloBase(this._datiProtocollazione);
            
            return protoBase;
        }

        private ProtocolloEnum.TipiProtocollo GetTipoProtocollo()
        {
            try
            {
                _protocolloLogs.DebugFormat("VERTICALIZZAZIONE ATTIVA: {0}", VertProtocolloAttivo.Attiva);

                if (!VertProtocolloAttivo.Attiva)
                    throw new Exception("LA VERTICALIZZAZIONE PROTOCOLLO_ATTIVO NON E' VALORIZZATA");

                _protocolloLogs.DebugFormat("CODICE OPERATORE VERTICALIZZAZIONE: {0}, CODICE OPERATORE FO: {1}, CODICE OPERATORE PASSATO: {2}", VertProtocolloAttivo.Operatore, VertProtocolloAttivo.Codoperatorefo, _datiProtocollazione.CodiceResponsabile);

                if (!this._datiProtocollazione.CodiceResponsabile.HasValue)
                {
                    _protocolloLogs.Debug("PROTOCOLLAZIONE DA ONLINE");

                    var operatoreOnline = new OperatoreProtocolloOnline(VertProtocolloAttivo.Codoperatorefo, _datiProtocollazione.Istanza);
                    this._datiProtocollazione.CodiceResponsabile = Convert.ToInt32(operatoreOnline.CodiceOperatore);
                    _protocolloLogs.DebugFormat("CODICE OPERATORE: {0}", _datiProtocollazione.CodiceResponsabile.Value.ToString());
                }

                IOperatoreProtocollo operatoreProto = OperatoreProtocolloFactory.Create(this._datiProtocollazione.Db, VertProtocolloAttivo.Operatore, this._datiProtocollazione.CodiceResponsabile, this._datiProtocollazione.IdComune);
                VertProtocolloAttivo.Operatore = operatoreProto.CodiceOperatore;

                _protocolloLogs.DebugFormat("OPERATORE EFFETTIVO CON CUI EFFETTUARE LA PROTOCOLLAZIONE: {0}", VertProtocolloAttivo.Operatore);

                return (ProtocolloEnum.TipiProtocollo)Enum.Parse(typeof(ProtocolloEnum.TipiProtocollo), VertProtocolloAttivo.Tipoprotocollo, false);
            }
            catch (Exception ex)
            {
                throw new Exception("ERRORE GENERATO DURANTE L'ATTIVAZIONE DEL PROTOCOLLO", ex);
            }
        }
        

    }
}
