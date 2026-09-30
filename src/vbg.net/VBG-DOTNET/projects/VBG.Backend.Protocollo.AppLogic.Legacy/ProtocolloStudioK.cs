using System;
using System.Collections.Generic;
using VBG.Backend.Protocollo.AppLogic.Shared;
using VBG.Backend.Protocollo.AppLogic.Shared.WsDataClass;
using VBG.Backend.Protocollo.AppLogic.Shared.Data;
using VBG.Backend.Protocollo.AppLogic.Legacy.StudioK;
using VBG.Backend.Protocollo.AppLogic.Shared.Factories;
using VBG.Backend.Protocollo.AppLogic.Legacy.StudioK.Protocollazione;
using VBG.Backend.Protocollo.AppLogic.Legacy.StudioK.LeggiProtocollo;
using VBG.Backend.Protocollo.Verticalizzazioni.Legacy;
using SIGePro.Manager.VerticalizzazioniBase;

namespace VBG.Backend.Protocollo.AppLogic.Legacy
{
    public class PROTOCOLLO_STUDIOK : ProtocolloBase
    {
        private readonly IVerticalizzazioniFactory _verticalizzazioniFactory;

        public PROTOCOLLO_STUDIOK(IVerticalizzazioniFactory verticalizzazioniFactory)
        {
            this._verticalizzazioniFactory = verticalizzazioniFactory;
        }

        public override DatiProtocolloResponseType Protocollazione(DatiProtocolloIn protoIn)
        {
            var vert = new VerticalizzazioniWrapper(this._verticalizzazioniFactory.Create<VerticalizzazioneProtocolloStudioK>(DatiProtocollo.IdComuneAlias, DatiProtocollo.Software, DatiProtocollo.CodiceComune), _protocolloLogs);
            var datiProto = DatiProtocolloInsertFactory.Create(protoIn);
            var wrapperProtocollazione = new ProtocollazioneServiceWrapper(vert.Url, base.ProxyAddress, vert.ConnectionString, _protocolloLogs, _protocolloSerializer);
            var segnaturaAdapter = new SegnaturaAdapter(datiProto, this.Anagrafiche, vert, wrapperProtocollazione, _protocolloSerializer);
            var segnatura = segnaturaAdapter.Adatta();
            var response = wrapperProtocollazione.Protocolla(segnatura);

            return new DatiProtocolloResponseType
            {
                AnnoProtocollo = response.lngAnnoPG.ToString(),
                NumeroProtocollo = response.lngNumPG.ToString(),
                Warning = _protocolloLogs.Warnings.WarningMessage,
                DataProtocollo = DateTime.Now.ToString("dd/MM/yyyy")
            };
        }

        public override List<DatiProtocolloLettoResponseType> LeggiProtocollo(LeggiProtocolloRequest leggiProtocolloRequest)
        {
            long numProto = long.Parse(leggiProtocolloRequest.NumeroProtocollo);
            long annoProto = long.Parse(leggiProtocolloRequest.AnnoProtocollo);

            var vert = new VerticalizzazioniWrapper(this._verticalizzazioniFactory.Create<VerticalizzazioneProtocolloStudioK>(DatiProtocollo.IdComuneAlias, DatiProtocollo.Software, DatiProtocollo.CodiceComune), _protocolloLogs);
            var wrapper = new LeggiProtocolloServiceWrapper(vert.Url, base.ProxyAddress, vert.ConnectionString, _protocolloLogs, _protocolloSerializer);
            var response = wrapper.Leggi(numProto, annoProto, vert.CodiceAoo);

            return new List<DatiProtocolloLettoResponseType>() { LeggiProtocolloResponseAdapter.Adatta(response, _protocolloLogs, DatiProtocollo.Db) };
        }

        public override AllegatoResponseType LeggiAllegato()
        {
            long numProto = long.Parse(NumProtocollo);
            long annoProto = long.Parse(AnnoProtocollo);

            var vert = new VerticalizzazioniWrapper(this._verticalizzazioniFactory.Create<VerticalizzazioneProtocolloStudioK>(DatiProtocollo.IdComuneAlias, DatiProtocollo.Software, DatiProtocollo.CodiceComune), _protocolloLogs);
            var wrapper = new LeggiProtocolloServiceWrapper(vert.Url, base.ProxyAddress, vert.ConnectionString, _protocolloLogs, _protocolloSerializer);
            var oggetto = wrapper.DownloadAllegato(numProto, annoProto, IdAllegato, vert.CodiceAoo);
            return new AllegatoResponseType { Serial = IdAllegato, Image = oggetto };
        }
    }
}
