using Init.SIGePro.Manager.Verticalizzazioni;
using Init.SIGePro.Protocollo.Data;
using Init.SIGePro.Protocollo.ProtocolloFactories;
using Init.SIGePro.Protocollo.StudioK;
using Init.SIGePro.Protocollo.StudioK.LeggiProtocollo;
using Init.SIGePro.Protocollo.StudioK.Protocollazione;
using Init.SIGePro.Protocollo.WsDataClass;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Protocollo
{
    public class PROTOCOLLO_STUDIOK : ProtocolloBase
    {
        public override DatiProtocolloRes Protocollazione(DatiProtocolloIn protoIn)
        {
            var vert = new VerticalizzazioniWrapper(new VerticalizzazioneProtocolloStudioK(DatiProtocollo.IdComuneAlias, DatiProtocollo.Software, DatiProtocollo.CodiceComune), _protocolloLogs);
            var datiProto = DatiProtocolloInsertFactory.Create(protoIn);
            var wrapperProtocollazione = new ProtocollazioneServiceWrapper(vert.Url, base.ProxyAddress, vert.ConnectionString, _protocolloLogs, _protocolloSerializer);
            var segnaturaAdapter = new SegnaturaAdapter(datiProto, this.Anagrafiche, vert, wrapperProtocollazione, _protocolloSerializer);
            var segnatura = segnaturaAdapter.Adatta();
            var response = wrapperProtocollazione.Protocolla(segnatura);

            return new DatiProtocolloRes
            {
                AnnoProtocollo = response.lngAnnoPG.ToString(),
                NumeroProtocollo = response.lngNumPG.ToString(),
                Warning = _protocolloLogs.Warnings.WarningMessage,
                DataProtocollo = DateTime.Now.ToString("dd/MM/yyyy")
            };
        }

        public override DatiProtocolloLetto LeggiProtocollo(string idProtocollo, string annoProtocollo, string numeroProtocollo)
        {
            long numProto = long.Parse(numeroProtocollo);
            long annoProto = long.Parse(annoProtocollo);

            var vert = new VerticalizzazioniWrapper(new VerticalizzazioneProtocolloStudioK(DatiProtocollo.IdComuneAlias, DatiProtocollo.Software, DatiProtocollo.CodiceComune), _protocolloLogs);
            var wrapper = new LeggiProtocolloServiceWrapper(vert.Url, base.ProxyAddress, vert.ConnectionString, _protocolloLogs, _protocolloSerializer);
            var response = wrapper.Leggi(numProto, annoProto, vert.CodiceAoo);

            return LeggiProtocolloResponseAdapter.Adatta(response, _protocolloLogs, DatiProtocollo.Db);
        }

        public override AllOut LeggiAllegato()
        {
            long numProto = long.Parse(NumProtocollo);
            long annoProto = long.Parse(AnnoProtocollo);

            var vert = new VerticalizzazioniWrapper(new VerticalizzazioneProtocolloStudioK(DatiProtocollo.IdComuneAlias, DatiProtocollo.Software, DatiProtocollo.CodiceComune), _protocolloLogs);
            var wrapper = new LeggiProtocolloServiceWrapper(vert.Url, base.ProxyAddress, vert.ConnectionString, _protocolloLogs, _protocolloSerializer);
            var oggetto = wrapper.DownloadAllegato(numProto, annoProto, IdAllegato, vert.CodiceAoo);
            return new AllOut { Serial = IdAllegato, Image = oggetto };
        }
    }
}
