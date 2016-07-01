using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.Data;
using Init.SIGePro.Protocollo.ProtocolloFactories;
using Init.SIGePro.Protocollo.Logs;
using Init.SIGePro.Protocollo.Constants;
using Init.SIGePro.Protocollo.Validation;
using Init.SIGePro.Data;
using System.IO;
using Init.SIGePro.Verticalizzazioni;
using Init.SIGePro.Protocollo.DocArea.Adapters;
using Init.SIGePro.Protocollo.DocArea;
using Init.SIGePro.Protocollo.DocArea.Configurations;
using Init.SIGePro.Protocollo.DocArea.Builders;
using Init.SIGePro.Protocollo.DocArea.Services;
using Init.SIGePro.Protocollo.WsDataClass;

namespace Init.SIGePro.Protocollo
{
    public class PROTOCOLLO_DOCAREA : ProtocolloBase
    {
        public PROTOCOLLO_DOCAREA()
        {

        }

        public override DatiProtocolloRes Protocollazione(DatiProtocolloIn protoIn)
        {
            var vertParams = new DocAreaVerticalizzazioneParametriAdapter(_protocolloLogs, new VerticalizzazioneProtocolloDocarea(DatiProtocollo.IdComuneAlias, DatiProtocollo.Software, DatiProtocollo.CodiceComune));

            var protoSrv = new DocAreaProtocollazioneService(vertParams.Url, _protocolloLogs, _protocolloSerializer);
            string token = protoSrv.Login(vertParams.Codiceente, vertParams.Username, vertParams.Password);

            var datiProto = DatiProtocolloInsertFactory.Create(protoIn);

            DateTime? dataRicevimento = DateTime.Now;
            string domicilioElettronico = "";

            if (this.DatiProtocollo.TipoAmbito == ProtocolloEnumerators.ProtocolloEnum.AmbitoProtocollazioneEnum.DA_ISTANZA)
            {
                dataRicevimento = this.DatiProtocollo.Istanza.DATA.Value;
                domicilioElettronico = this.DatiProtocollo.Istanza.DOMICILIO_ELETTRONICO;
            }

            var conf = new DocAreaSegnaturaParamConfiguration(vertParams, Operatore, protoIn, domicilioElettronico, dataRicevimento.Value);

            var segnaturaBuilder = new DocAreaSegnaturaBuilder(datiProto, conf, _protocolloLogs, _protocolloSerializer, protoIn.Allegati);

            if (vertParams.InviaSegnatura && protoIn.Allegati.Count == 0)
                segnaturaBuilder.CreaSegnaturaFittizia();

            if (vertParams.InviaAllMovAvvio && protoIn.Allegati.Count == 0 && this.DatiProtocollo.TipoAmbito == ProtocolloEnumerators.ProtocolloEnum.AmbitoProtocollazioneEnum.DA_ISTANZA)
                protoSrv.InserisciAllegatiDaMovimentoAvvio(this.DatiProtocollo.Istanza, this.DatiProtocollo.Db, DatiProtocollo.IdComune, protoIn.Allegati);

            protoSrv.InserisciAllegati(protoIn.Allegati, token, vertParams.Username, vertParams.InviaNomeFileAttachment);

            segnaturaBuilder.SerializzaSegnatura();

            var response = protoSrv.Protocollazione(vertParams.Username, token);

            var adapter = new DocAreaProtocolloInsertOutputAdapter(response);
            return adapter.DatiProtocollo;
        }
    }
}

