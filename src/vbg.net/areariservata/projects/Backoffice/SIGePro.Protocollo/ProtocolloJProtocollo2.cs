using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.Data;
using Init.SIGePro.Protocollo.JProtocollo2.Verticalizzazioni;
using Init.SIGePro.Verticalizzazioni;
using Init.SIGePro.Protocollo.JProtocollo2.Protocollazione;
using Init.SIGePro.Protocollo.ProtocolloFactories;
using Init.SIGePro.Protocollo.JProtocollo2.Services;
using Init.SIGePro.Protocollo.JProtocollo2.LeggiProtocollo;
using Init.SIGePro.Protocollo.WsDataClass;
using Init.SIGePro.Manager;
using System.IO;

namespace Init.SIGePro.Protocollo
{
    public class PROTOCOLLO_JPROTOCOLLO2 : ProtocolloBase
    {
        public PROTOCOLLO_JPROTOCOLLO2()
        {

        }

        public override DatiProtocolloRes Protocollazione(DatiProtocolloIn protoIn)
        {
            var vert = new VerticalizzazioniConfiguration(_protocolloLogs, new VerticalizzazioneProtocolloJprotocollo(DatiProtocollo.IdComuneAlias, DatiProtocollo.Software, DatiProtocollo.CodiceComune));
            var datiProto = DatiProtocolloInsertFactory.Create(protoIn);
            var service = new ProtocolloService(_protocolloLogs, _protocolloSerializer, vert.Url);
            var protocollo = ProtocollazioneFactory.Create(datiProto, service, vert, Operatore);

            var datiRes = protocollo.Protocolla();

            datiRes.Warning = _protocolloLogs.Warnings.WarningMessage;

            return datiRes;
        }

        public override AllOut LeggiAllegato()
        {
            var vert = new VerticalizzazioniConfiguration(_protocolloLogs, new VerticalizzazioneProtocolloJprotocollo(DatiProtocollo.IdComuneAlias, DatiProtocollo.Software, DatiProtocollo.CodiceComune));
            var service = new ProtocolloService(_protocolloLogs, _protocolloSerializer, vert.Url);
            var response = service.LeggiAllegato(IdAllegato, AnnoProtocollo, NumProtocollo, Operatore);

            string nomeFile = String.IsNullOrEmpty(response.documento.nomeFile) ? response.documento.titolo : response.documento.nomeFile;

            return new AllOut
            {
                Image = response.documento.file,
                IDBase = response.documento.progressivo,
                Commento = response.documento.titolo,
                Serial = nomeFile,
                ContentType = new OggettiMgr(DatiProtocollo.Db).GetContentType(nomeFile),
                TipoFile = Path.GetExtension(nomeFile)
            };
        }

        public override DatiProtocolloLetto LeggiProtocollo(string idProtocollo, string annoProtocollo, string numeroProtocollo)
        {
            var vert = new VerticalizzazioniConfiguration(_protocolloLogs, new VerticalizzazioneProtocolloJprotocollo(DatiProtocollo.IdComuneAlias, DatiProtocollo.Software, DatiProtocollo.CodiceComune));
            var adapterIn = new LeggiProtocolloRequestAdapter(numeroProtocollo, annoProtocollo, Operatore);
            var request = adapterIn.Adatta();

            var service = new ProtocolloService(_protocolloLogs, _protocolloSerializer, vert.Url);
            var response = service.LeggiProtocollo(request);
            var adapterOut = new LeggiProtocolloResponseAdapter(response);

            return adapterOut.Adatta(DatiProtocollo.Db);
        }
    }
}
