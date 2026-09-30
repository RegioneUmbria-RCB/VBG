using Init.SIGePro.Manager.Verticalizzazioni;
using Init.SIGePro.Protocollo.Data;
using Init.SIGePro.Protocollo.ProtocolloFactories;
using Init.SIGePro.Protocollo.ProtocolloInterfaces;
using Init.SIGePro.Protocollo.ProtocolloServices;
using Init.SIGePro.Protocollo.Urbi;
using Init.SIGePro.Protocollo.Urbi.Classificazione;
using Init.SIGePro.Protocollo.Urbi.LeggiAllegato;
using Init.SIGePro.Protocollo.Urbi.LeggiProtocollo;
using Init.SIGePro.Protocollo.Urbi.Protocollazione;
using Init.SIGePro.Protocollo.Urbi.TipiDocumento;
using Init.SIGePro.Protocollo.WsDataClass;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Protocollo
{
    public class PROTOCOLLO_URBI : ProtocolloBase
    {

        public override DatiProtocolloRes Protocollazione(DatiProtocolloIn protoIn)
        {
            var vert = new VerticalizzazioniWrapper(new VerticalizzazioneProtocolloUrbi(DatiProtocollo.IdComuneAlias, DatiProtocollo.Software, DatiProtocollo.CodiceComune), _protocolloLogs);
            var datiProto = DatiProtocolloInsertFactory.Create(protoIn);
            var requestProto = ProtocollazioneFactory.Create(datiProto, vert, this.Operatore, this._protocolloLogs, this._protocolloSerializer, this.Anagrafiche);
            var parameters = requestProto.GetParameters();
            var wrapper = new ProtocollazioneServiceWrapper(this._protocolloLogs, this._protocolloSerializer, vert);
            var response = wrapper.Protocolla(protoIn.Allegati, parameters);
            var retVal = ProtocollazioneResponseAdapter.Adatta(response, this._protocolloLogs.Warnings.WarningMessage);
            return retVal;
        }

        public override DatiProtocolloLetto LeggiProtocollo(string idProtocollo, string annoProtocollo, string numeroProtocollo)
        {
            var vert = new VerticalizzazioniWrapper(new VerticalizzazioneProtocolloUrbi(DatiProtocollo.IdComuneAlias, DatiProtocollo.Software, DatiProtocollo.CodiceComune), _protocolloLogs);
            var wrapper = new LeggiProtocolloServiceWrapper(_protocolloLogs, _protocolloSerializer, vert);
            var response = wrapper.LeggiProtocollo(vert.Aoo, annoProtocollo, numeroProtocollo, "AP");
            return LeggiProtocolloResponseAdapter.Adatta(response, this.DatiProtocollo.Db);
        }

        public override AllOut LeggiAllegato()
        {
            var vert = new VerticalizzazioniWrapper(new VerticalizzazioneProtocolloUrbi(DatiProtocollo.IdComuneAlias, DatiProtocollo.Software, DatiProtocollo.CodiceComune), _protocolloLogs);
            var allegatoInfo = base.GetAllegato();
            var wrapper = new LeggiAllegatoServiceWrapper(_protocolloLogs, _protocolloSerializer, vert);
            var allegato = new Urbi.LeggiAllegato.Allegato(IdAllegato);
            var buffer = wrapper.DownloadAllegato(allegato.Id, allegato.Versione);
            allegatoInfo.Image = buffer;
            return allegatoInfo;

            //throw new Exception("NON IMPLEMENTATO");
        }

        public override ListaTipiDocumento GetTipiDocumento()
        {
            var vert = new VerticalizzazioniWrapper(new VerticalizzazioneProtocolloUrbi(DatiProtocollo.IdComuneAlias, DatiProtocollo.Software, DatiProtocollo.CodiceComune), _protocolloLogs);
            var wrapper = new TipiDocumentoServiceWrapper(_protocolloLogs, _protocolloSerializer, vert.Username, vert.Password, vert.Url);
            var response = wrapper.GetTipiDocumento();

            var adapter = TipiDocumentoResponseAdapter.Adatta(response);
            return adapter;
        }

        public override ListaTipiClassifica GetClassifiche()
        {
            var vert = new VerticalizzazioniWrapper(new VerticalizzazioneProtocolloUrbi(DatiProtocollo.IdComuneAlias, DatiProtocollo.Software, DatiProtocollo.CodiceComune), _protocolloLogs);
            var wrapper = new ClassificazioneServiceWrapper(_protocolloLogs, _protocolloSerializer, vert.Username, vert.Password, vert.Url);
            var response = wrapper.GetTitolario(vert.Aoo);

            var adapter = ClassificazioneResponseAdapter.Adatta(response, vert.ReplaceTitolario);
            return adapter;
        }
    }
}
