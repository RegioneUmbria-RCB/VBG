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
using Init.SIGePro.Protocollo.DocPro.Adapters;
using Init.SIGePro.Protocollo.DocPro;
using Init.SIGePro.Protocollo.DocPro.Configurations;
using Init.SIGePro.Protocollo.DocPro.Builders;
using Init.SIGePro.Protocollo.DocPro.Services;
using Init.SIGePro.Protocollo.WsDataClass;

namespace Init.SIGePro.Protocollo
{
    public class PROTOCOLLO_DOCPRO : ProtocolloBase
    {
        public PROTOCOLLO_DOCPRO()
        {

        }

        public override DatiProtocolloRes Protocollazione(DatiProtocolloIn protoIn)
        {
            var vertParams = new DocProVerticalizzazioneParametriAdapter(_protocolloLogs, new VerticalizzazioneProtocolloDocpro(DatiProtocollo.IdComuneAlias, DatiProtocollo.Software, DatiProtocollo.CodiceComune));
            var protoSrv = new DocProProtocollazioneService(vertParams.Url, _protocolloLogs, _protocolloSerializer);
            string token = protoSrv.Login(vertParams.Codiceente, vertParams.Username, vertParams.Password);

            var datiProto = DatiProtocolloInsertFactory.Create(protoIn);

            DateTime? dataRicevimento = DateTime.Now;

            if (this.DatiProtocollo.TipoAmbito == ProtocolloEnumerators.ProtocolloEnum.AmbitoProtocollazioneEnum.DA_ISTANZA)
                dataRicevimento = this.DatiProtocollo.Istanza.DATA.Value;

            var conf = new DocProSegnaturaParamConfiguration(vertParams, Operatore, protoIn, /*domicilioElettronico,*/ dataRicevimento.Value);

            var segnaturaBuilder = new DocProSegnaturaBuilder(datiProto, conf, _protocolloLogs, _protocolloSerializer, protoIn.Allegati);

            if (vertParams.InviaSegnatura && protoIn.Allegati.Count == 0)
                segnaturaBuilder.CreaSegnaturaFittizia();

            if (vertParams.InviaAllMovAvvio && protoIn.Allegati.Count == 0 && this.DatiProtocollo.TipoAmbito == ProtocolloEnumerators.ProtocolloEnum.AmbitoProtocollazioneEnum.DA_ISTANZA)
                protoSrv.InserisciAllegatiDaMovimentoAvvio(this.DatiProtocollo.Istanza, this.DatiProtocollo.Db, DatiProtocollo.IdComune, protoIn.Allegati);

            protoSrv.InserisciAllegati(protoIn.Allegati, token, vertParams.Username);

            segnaturaBuilder.SerializzaSegnatura();

            var response = protoSrv.Protocollazione(vertParams.Username, token);

            var adapter = new DocProProtocolloInsertOutputAdapter(response);
            return adapter.DatiProtocollo;
        }
    }
}
