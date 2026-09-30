using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using VBG.Backend.Protocollo.Verticalizzazioni.Core;
using VBG.Backend.Protocollo.AppLogic.Shared;
using VBG.Backend.Protocollo.AppLogic.Shared.WsDataClass;
using VBG.Backend.Protocollo.AppLogic.Shared.Data;
using VBG.Backend.Protocollo.AppLogic.Core.ProtoInf;
using VBG.Backend.Protocollo.AppLogic.Shared.Interfaces;
using VBG.Backend.Protocollo.AppLogic.Shared.Factories;
using VBG.Backend.Protocollo.AppLogic.Core.ProtoInf.DatiConfigurazioneProtocollo;
using SIGePro.Manager.VerticalizzazioniBase;
using VBG.Shared.Infrastructure.ServiceModel;

namespace VBG.Backend.Protocollo.AppLogic.Core
{
    public class PROTOCOLLO_PROTOINF : ProtocolloBase
    {
        private readonly IVerticalizzazioniFactory _verticalizzazioniFactory;
        private readonly IBindingFactory _bindingFactory;

        public PROTOCOLLO_PROTOINF(IVerticalizzazioniFactory verticalizzazioniFactory, IBindingFactory bindingFactory) : base()
        {
            this._verticalizzazioniFactory = verticalizzazioniFactory;
            this._bindingFactory = bindingFactory;
        }

        public override DatiProtocolloResponseType Protocollazione(DatiProtocolloIn protoIn)
        {
            var vert = new VerticalizzazioniServiceWrapper(this._verticalizzazioniFactory.Create<VerticalizzazioneProtocolloProtInf>(DatiProtocollo.IdComuneAlias, DatiProtocollo.Software, DatiProtocollo.CodiceComune));
            IDatiProtocollo datiProto = DatiProtocolloInsertFactory.Create(protoIn);


            var factoryDefOnline = DatiConfigurazioneProtocolloFactory.Create(base.Provenienza, base.DatiProtocollo, datiProto, vert, base._protocolloLogs);
            var datiDaConfigurazione = factoryDefOnline.GetDati();

            var info = new RequestInfo(vert, datiProto, datiDaConfigurazione, base._protocolloLogs, base._protocolloSerializer, this.Anagrafiche, this.DatiProtocollo.Db, this.DatiProtocollo.IdComune, this.DatiProtocollo.CodiceIstanza, this.DatiProtocollo.CodiceMovimento, this.DatiProtocollo.DatiPec);

            var adapter = new RequestAdapter(info);
            var protocolloXml = adapter.AdattaProtocolloXml();
            var mittdest = adapter.GetMittenteDestinatario();
            var mittenteXml = mittdest.GetMittente();
            var destinatarioXml = mittdest.GetDestinatario();
            var allegatiAdapter = adapter.AdattaAllegatiXml();
            var allegatiXml = allegatiAdapter.Adatta(info);
            
            var assegnatariXml = adapter.AdattaAssegnatarioXml();

            var serviceWrapper = new ProtocolloServiceWrapper(_protocolloLogs, _protocolloSerializer, _bindingFactory, vert.Url);
            var response = serviceWrapper.Protocolla(protocolloXml, mittenteXml, destinatarioXml, assegnatariXml, allegatiXml, allegatiAdapter.PercorsoDirectoryDaProtocollo);

            return new DatiProtocolloResponseType
            {
                AnnoProtocollo = response.Dati.AnnoProtocollo,
                NumeroProtocollo = response.Dati.NumeroProtocollo,
                DataProtocollo = DateTime.Now.ToString("dd/MM/yyyy")
            };
        }
    }
}
