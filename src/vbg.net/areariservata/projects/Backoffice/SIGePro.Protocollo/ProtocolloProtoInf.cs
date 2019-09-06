using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.Data;
using Init.SIGePro.Protocollo.WsDataClass;
using Init.SIGePro.Protocollo.ProtoInf;
using Init.SIGePro.Manager.Verticalizzazioni;
using Init.SIGePro.Protocollo.ProtoInf.Protocollazione;
using Init.SIGePro.Protocollo.ProtocolloInterfaces;
using Init.SIGePro.Protocollo.ProtocolloFactories;
using Init.SIGePro.Protocollo.ProtocolloProtInfService;
using Init.SIGePro.Protocollo.ProtoInf.DatiConfigurazioneProtocollo;

namespace Init.SIGePro.Protocollo
{
    public class PROTOCOLLO_PROTOINF : ProtocolloBase
    {
        public PROTOCOLLO_PROTOINF() : base()
        {

        }

        public override DatiProtocolloRes Protocollazione(DatiProtocolloIn protoIn)
        {
            var vert = new VerticalizzazioniServiceWrapper(new VerticalizzazioneProtocolloProtInf(this.DatiProtocollo.IdComuneAlias, this.DatiProtocollo.Software, this.DatiProtocollo.CodiceComune));
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

            var serviceWrapper = new ProtocolloServiceWrapper(_protocolloLogs, _protocolloSerializer, vert.Url);
            var response = serviceWrapper.Protocolla(protocolloXml, mittenteXml, destinatarioXml, assegnatariXml, allegatiXml, allegatiAdapter.PercorsoDirectoryDaProtocollo);

            return new DatiProtocolloRes
            {
                AnnoProtocollo = response.Dati.AnnoProtocollo,
                NumeroProtocollo = response.Dati.NumeroProtocollo,
                DataProtocollo = DateTime.Now.ToString("dd/MM/yyyy")
            };
        }
    }
}
