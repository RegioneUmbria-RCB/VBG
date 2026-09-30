using Init.SIGePro.Manager;
using SIGePro.Manager.VerticalizzazioniBase;
using System;
using System.Collections.Generic;
using System.IO;
using vbg.Backend.Protocollo.AppLogic.Legacy.JProtocollo2.Verticalizzazioni;
using VBG.Backend.Protocollo.AppLogic.Legacy.JProtocollo2.LeggiProtocollo;
using VBG.Backend.Protocollo.AppLogic.Legacy.JProtocollo2.Protocollazione;
using VBG.Backend.Protocollo.AppLogic.Legacy.JProtocollo2.Services;
using VBG.Backend.Protocollo.AppLogic.Shared;
using VBG.Backend.Protocollo.AppLogic.Shared.Data;
using VBG.Backend.Protocollo.AppLogic.Shared.Factories;
using VBG.Backend.Protocollo.AppLogic.Shared.WsDataClass;
using VBG.Backend.Protocollo.Verticalizzazioni.Legacy;
using VBG.Backend.Protocollo.Verticalizzazioni.Shared;

namespace VBG.Backend.Protocollo.AppLogic.Legacy
{
    public class PROTOCOLLO_JPROTOCOLLO2 : ProtocolloBase
    {
        private readonly IVerticalizzazioniFactory _verticalizzazioniFactory;

        public PROTOCOLLO_JPROTOCOLLO2(IVerticalizzazioniFactory verticalizzazioniFactory)
        {
            this._verticalizzazioniFactory = verticalizzazioniFactory;
        }

        public override DatiProtocolloResponseType Protocollazione(DatiProtocolloIn protoIn)
        {
            var vert = new VerticalizzazioniConfiguration(_protocolloLogs, this._verticalizzazioniFactory.Create<VerticalizzazioneProtocolloJprotocollo>(DatiProtocollo.IdComuneAlias, DatiProtocollo.Software, DatiProtocollo.CodiceComune));

            var datiProto = DatiProtocolloInsertFactory.Create(protoIn);
            var service = new ProtocolloService(_protocolloLogs, _protocolloSerializer, vert.Url);
            var protocollo = ProtocollazioneFactory.Create(datiProto, service, vert, Operatore);

            var datiRes = protocollo.Protocolla();

            datiRes.Warning = _protocolloLogs.Warnings.WarningMessage;

            return datiRes;
        }

        public override AllegatoResponseType LeggiAllegato()
        {
            var vert = new VerticalizzazioniConfiguration(_protocolloLogs, this._verticalizzazioniFactory.Create<VerticalizzazioneProtocolloJprotocollo>(DatiProtocollo.IdComuneAlias, DatiProtocollo.Software, DatiProtocollo.CodiceComune));
            var service = new ProtocolloService(_protocolloLogs, _protocolloSerializer, vert.Url);
            var response = service.LeggiAllegato(IdAllegato, AnnoProtocollo, NumProtocollo, Operatore);

            string nomeFile = String.IsNullOrEmpty(response.documento.nomeFile) ? response.documento.titolo : response.documento.nomeFile;

            return new AllegatoResponseType
            {
                Image = response.documento.file,
                IDBase = response.documento.progressivo,
                Commento = response.documento.titolo,
                Serial = nomeFile,
                ContentType = new OggettiMgr(DatiProtocollo.Db).GetContentType(nomeFile),
                TipoFile = Path.GetExtension(nomeFile)
            };
        }

        public override List<DatiProtocolloLettoResponseType> LeggiProtocollo(LeggiProtocolloRequest leggiProtocolloRequest)
        {
            var vert = new VerticalizzazioniConfiguration(_protocolloLogs, this._verticalizzazioniFactory.Create<VerticalizzazioneProtocolloJprotocollo>(DatiProtocollo.IdComuneAlias, DatiProtocollo.Software, DatiProtocollo.CodiceComune));
            var adapterIn = new LeggiProtocolloRequestAdapter(leggiProtocolloRequest.NumeroProtocollo, leggiProtocolloRequest.AnnoProtocollo, Operatore);
            var request = adapterIn.Adatta();

            var service = new ProtocolloService(_protocolloLogs, _protocolloSerializer, vert.Url);
            var response = service.LeggiProtocollo(request);
            var adapterOut = new LeggiProtocolloResponseAdapter(response);

            return new List<DatiProtocolloLettoResponseType>() { adapterOut.Adatta(DatiProtocollo.Db) };
        }
    }
}
