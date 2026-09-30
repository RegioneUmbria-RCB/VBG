using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.Data;
using Init.SIGePro.Protocollo.ProtocolloFactories;
using Init.SIGePro.Protocollo.ProtocolloInsielMercatoService2;
using Init.SIGePro.Protocollo.InsielMercato2.Verticalizzazioni;
using Init.SIGePro.Protocollo.InsielMercato2.Protocollazione;
using Init.SIGePro.Protocollo.InsielMercato2.LeggiProtocollo;
using Init.SIGePro.Protocollo.InsielMercato2.LeggiClassifiche;
using Init.SIGePro.Manager;
using Init.SIGePro.Protocollo.WsDataClass;
using Init.SIGePro.Verticalizzazioni;
using Init.SIGePro.Protocollo.InsielMercato2.Services;
using Init.SIGePro.Protocollo.InsielMercato2.LeggiProtocollo.Identificativo;
using System.IO;

namespace Init.SIGePro.Protocollo
{
    public class PROTOCOLLO_INSIELMERCATO2 : ProtocolloBase
    {
        public static class Constants
        {
            public const string SEPARATORE_ID_PROTOCOLLO = ";";
        }

        public override DatiProtocolloRes Protocollazione(DatiProtocolloIn protoIn)
        {
            var vert = new VerticalizzazioniConfiguration(_protocolloLogs, new VerticalizzazioneProtocolloInsielmercato(DatiProtocollo.IdComuneAlias, DatiProtocollo.Software, DatiProtocollo.CodiceComune));
            var datiAnagrafici = DatiProtocolloInsertFactory.Create(protoIn);

            var ufficio = GetUfficioRegistro(datiAnagrafici.Uo);

            var sequenza = new ProtocolloSequenzeMgr(DatiProtocollo.Db).GetById(DatiProtocollo.IdComune, protoIn.Flusso, DatiProtocollo.Software, DatiProtocollo.CodiceComune);
            if (sequenza == null && String.IsNullOrEmpty(sequenza.Codicesequenza))
                throw new Exception("SEQUENZA NON PRESENTE");

            var utenteProtocollo = new user { code = vert.Username, password = vert.Password };

            var wrapper = new ProtocollazioneService(vert.Url, _protocolloLogs, _protocolloSerializer, utenteProtocollo);
            var adapter = new ProtocollazioneAdapter(wrapper, datiAnagrafici, vert, ufficio, sequenza.Codicesequenza, DatiProtocollo, _protocolloLogs);

            var response = adapter.Adatta();

            var adapterResponse = new ProtocollazioneResponseAdapter(response, _protocolloLogs);
            return adapterResponse.Adatta();
        }

        public override DatiProtocolloLetto LeggiProtocollo(string idProtocollo, string annoProtocollo, string numeroProtocollo)
        {
            var vert = new VerticalizzazioniConfiguration(_protocolloLogs, new VerticalizzazioneProtocolloInsielmercato(DatiProtocollo.IdComuneAlias, DatiProtocollo.Software, DatiProtocollo.CodiceComune));
            var utenteProtocollo = new user { code = vert.Username, password = vert.Password };

            var identificativo = IdentificativoFactory.Create(idProtocollo, Convert.ToInt32(numeroProtocollo), Convert.ToInt32(annoProtocollo), this.DatiProtocollo);

            var request = new protocolDetailRequest { recordIdentifier = identificativo.GetRecordIdentifier() };

            var service = new ProtocollazioneService(vert.Url, _protocolloLogs, _protocolloSerializer, utenteProtocollo);
            var response = service.LeggiProtocollo(request);

            var adapterResponse = new LeggiProtoResponseAdapter(response, DatiProtocollo.Db);

            return adapterResponse.Adatta();
        }

        public override AllOut LeggiAllegato()
        {
            var vert = new VerticalizzazioniConfiguration(_protocolloLogs, new VerticalizzazioneProtocolloInsielmercato(DatiProtocollo.IdComuneAlias, DatiProtocollo.Software, DatiProtocollo.CodiceComune));
            var utenteProtocollo = new user { code = vert.Username, password = vert.Password };

            var identificativo = new IdentificativoId(IdProtocollo);
            var request = new protocolDetailRequest { recordIdentifier = identificativo.GetRecordIdentifier() };

            var service = new ProtocollazioneService(vert.Url, _protocolloLogs, _protocolloSerializer, utenteProtocollo);
            var response = service.LeggiProtocollo(request);

            var documento = response.documentList.Where(x => x.name == IdAllegato).First();

            return new AllOut
            {
                IDBase = documento.name,
                Serial = documento.name,
                Commento = Path.GetFileNameWithoutExtension(documento.name),
                ContentType = new OggettiMgr(this.DatiProtocollo.Db).GetContentType(documento.name),
                TipoFile = Path.GetExtension(documento.name).Replace(".", ""),
                Image = documento.file
            };
        }

        public override ListaTipiClassifica GetClassifiche()
        {
            var vert = new VerticalizzazioniConfiguration(_protocolloLogs, new VerticalizzazioneProtocolloInsielmercato(DatiProtocollo.IdComuneAlias, DatiProtocollo.Software, DatiProtocollo.CodiceComune));
            if (!vert.UsaWsClassifiche)
            {
                _protocolloLogs.Debug("Parametro USA_WS_CLASSIFICHE valorizzato a 0 o non valorizzato, le classifiche non saranno recuperate dal web service");
                return base.GetClassifiche();
            }
            var utenteProtocollo = new user { code = vert.Username, password = vert.Password };
            var service = new ProtocollazioneService(vert.Url, _protocolloLogs, _protocolloSerializer, utenteProtocollo);
            var response = service.LeggiClassifiche(LeggiClassificheRequestAdapter.Adatta());
            var adapterResponse = new LeggiClassificheResponseAdapter(response);

            return adapterResponse.Classifiche;
        }
    }
}