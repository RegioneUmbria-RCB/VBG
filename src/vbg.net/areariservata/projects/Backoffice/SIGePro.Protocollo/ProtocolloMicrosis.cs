using Init.SIGePro.Manager.Verticalizzazioni;
using Init.SIGePro.Protocollo.Data;
using Init.SIGePro.Protocollo.Microsis;
using Init.SIGePro.Protocollo.Microsis.Classifiche;
using Init.SIGePro.Protocollo.Microsis.Protocollazione;
using Init.SIGePro.Protocollo.ProtocolloFactories;
using Init.SIGePro.Protocollo.WsDataClass;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Protocollo
{
    public class PROTOCOLLO_MICROSIS : ProtocolloBase
    {
        public PROTOCOLLO_MICROSIS()
        {

        }

        public override DatiProtocolloRes Protocollazione(DatiProtocolloIn protoIn)
        {
            var vert = new VerticalizzazioniWrapper(new VerticalizzazioneProtocolloMicrosis(DatiProtocollo.IdComuneAlias, DatiProtocollo.Software, DatiProtocollo.CodiceComune), _protocolloLogs);
            var datiProto = DatiProtocolloInsertFactory.Create(protoIn);
            var factory = ProtocollazioneFactory.Create(datiProto, this.Anagrafiche);
            var serviceWrapper = new ProtocolloServiceWrapper(vert, _protocolloLogs, _protocolloSerializer);
            var response = factory.Protocolla(serviceWrapper);
            serviceWrapper.TrasmettiAllegato(protoIn.Allegati, response.NumeroProtocollo, response.AnnoProtocollo);
            response.Warning = _protocolloLogs.Warnings.WarningMessage;
            return response;
        }

        public override ListaTipiClassifica GetClassifiche()
        {
            var vert = new VerticalizzazioniWrapper(new VerticalizzazioneProtocolloMicrosis(DatiProtocollo.IdComuneAlias, DatiProtocollo.Software, DatiProtocollo.CodiceComune), _protocolloLogs);
            var wrapper = new ProtocolloServiceWrapper(vert, _protocolloLogs, _protocolloSerializer);
            var response = wrapper.GetClassifiche();
            return ClassificheResponseAdapter.Adatta(response);
        }

        public override void AggiungiAllegati(string idProtocollo, string numeroProtocollo, DateTime? dataProtocollo, IEnumerable<SIGePro.Data.ProtocolloAllegati> allegati)
        {
            if (allegati.Count() == 0)
                throw new Exception("ALLEGATI NON SELEZIONATI");

            var vert = new VerticalizzazioniWrapper(new VerticalizzazioneProtocolloMicrosis(DatiProtocollo.IdComuneAlias, DatiProtocollo.Software, DatiProtocollo.CodiceComune), _protocolloLogs);
            var serviceWrapper = new ProtocolloServiceWrapper(vert, _protocolloLogs, _protocolloSerializer);
            serviceWrapper.TrasmettiAllegato(allegati.ToList(), numeroProtocollo, dataProtocollo.Value.ToString("yyyy"));
        }

        public override DatiProtocolloLetto LeggiProtocollo(string idProtocollo, string annoProtocollo, string numeroProtocollo)
        {
            return new DatiProtocolloLetto { NumeroProtocollo = numeroProtocollo, DataProtocollo = annoProtocollo };
        }
    }
}
