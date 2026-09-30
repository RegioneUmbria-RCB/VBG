using System;
using System.Collections.Generic;
using System.Linq;
using VBG.Backend.Protocollo.AppLogic.Shared;
using VBG.Backend.Protocollo.AppLogic.Shared.WsDataClass;
using VBG.Backend.Protocollo.AppLogic.Shared.Data;
using VBG.Backend.Protocollo.AppLogic.Legacy.Microsis;
using VBG.Backend.Protocollo.AppLogic.Legacy.Microsis.Protocollazione;
using VBG.Backend.Protocollo.AppLogic.Shared.Factories;
using VBG.Backend.Protocollo.AppLogic.Legacy.Microsis.Classifiche;
using Init.SIGePro.Data;
using VBG.Backend.Protocollo.Verticalizzazioni.Legacy;
using SIGePro.Manager.VerticalizzazioniBase;
using VBG.Shared.Infrastructure.ServiceModel;

namespace VBG.Backend.Protocollo.AppLogic.Legacy
{
    public class PROTOCOLLO_MICROSIS : ProtocolloBase
    {
        private readonly IVerticalizzazioniFactory _verticalizzazioniFactory;
        private readonly IBindingFactory _bindingFactory;

        public PROTOCOLLO_MICROSIS(IVerticalizzazioniFactory verticalizzazioniFactory, IBindingFactory bindingFactory)
        {
            this._verticalizzazioniFactory = verticalizzazioniFactory;
            this._bindingFactory = bindingFactory;
        }

        public override DatiProtocolloResponseType Protocollazione(DatiProtocolloIn protoIn)
        {
            var vert = new VerticalizzazioniWrapper(this._verticalizzazioniFactory.Create<VerticalizzazioneProtocolloMicrosis>(DatiProtocollo.IdComuneAlias, DatiProtocollo.Software, DatiProtocollo.CodiceComune), _protocolloLogs);
            var datiProto = DatiProtocolloInsertFactory.Create(protoIn);
            var factory = ProtocollazioneFactory.Create(datiProto, this.Anagrafiche);
            var serviceWrapper = new ProtocolloServiceWrapper(vert, _protocolloLogs, _protocolloSerializer, this._bindingFactory);
            var response = factory.Protocolla(serviceWrapper);
            serviceWrapper.TrasmettiAllegato(protoIn.RecuperaAllegati().ToList(), response.NumeroProtocollo, response.AnnoProtocollo);
            response.Warning = _protocolloLogs.Warnings.WarningMessage;
            return response;
        }

        public override ListaTipiClassificaType GetClassifiche()
        {
            var vert = new VerticalizzazioniWrapper(this._verticalizzazioniFactory.Create<VerticalizzazioneProtocolloMicrosis>(DatiProtocollo.IdComuneAlias, DatiProtocollo.Software, DatiProtocollo.CodiceComune), _protocolloLogs);
            var wrapper = new ProtocolloServiceWrapper(vert, _protocolloLogs, _protocolloSerializer, this._bindingFactory);
            var response = wrapper.GetClassifiche();
            return ClassificheResponseAdapter.Adatta(response);
        }

        public override void AggiungiAllegati(string idProtocollo, string numeroProtocollo, DateTime? dataProtocollo, IEnumerable<ProtocolloAllegati> allegati)
        {
            if (allegati.Count() == 0)
                throw new Exception("ALLEGATI NON SELEZIONATI");

            var vert = new VerticalizzazioniWrapper(this._verticalizzazioniFactory.Create<VerticalizzazioneProtocolloMicrosis>(DatiProtocollo.IdComuneAlias, DatiProtocollo.Software, DatiProtocollo.CodiceComune), _protocolloLogs);
            var serviceWrapper = new ProtocolloServiceWrapper(vert, _protocolloLogs, _protocolloSerializer, this._bindingFactory);
            serviceWrapper.TrasmettiAllegato(allegati.ToList(), numeroProtocollo, dataProtocollo.Value.ToString("yyyy"));
        }

        public override List<DatiProtocolloLettoResponseType> LeggiProtocollo(LeggiProtocolloRequest leggiProtocolloRequest)
        {
            return new List<DatiProtocolloLettoResponseType>() { new DatiProtocolloLettoResponseType { NumeroProtocollo = leggiProtocolloRequest.NumeroProtocollo, DataProtocollo = leggiProtocolloRequest.AnnoProtocollo } };
        }
    }
}
