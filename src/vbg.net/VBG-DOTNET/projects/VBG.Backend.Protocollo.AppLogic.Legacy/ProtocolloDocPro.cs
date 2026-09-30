using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using VBG.Backend.Protocollo.AppLogic.Shared;
using VBG.Backend.Protocollo.AppLogic.Shared.WsDataClass;
using VBG.Backend.Protocollo.AppLogic.Shared.Data;
using VBG.Backend.Protocollo.AppLogic.Legacy.DocPro.Adapters;
using VBG.Backend.Protocollo.AppLogic.Legacy.DocPro.Services;
using VBG.Backend.Protocollo.AppLogic.Shared.Factories;
using VBG.Backend.Protocollo.AppLogic.Shared.Enums;
using VBG.Backend.Protocollo.AppLogic.Legacy.DocPro.Configurations;
using VBG.Backend.Protocollo.AppLogic.Legacy.DocPro.Builders;
using VBG.Backend.Protocollo.Verticalizzazioni.Legacy;
using SIGePro.Manager.VerticalizzazioniBase;

namespace VBG.Backend.Protocollo.AppLogic.Legacy
{
    public class PROTOCOLLO_DOCPRO : ProtocolloBase
    {
        private readonly IVerticalizzazioniFactory _verticalizzazioniFactory;

        public PROTOCOLLO_DOCPRO(IVerticalizzazioniFactory verticalizzazioniFactory)
        {
            this._verticalizzazioniFactory = verticalizzazioniFactory;
        }

        public override DatiProtocolloResponseType Protocollazione(DatiProtocolloIn protoIn)
        {
            var vertParams = new DocProVerticalizzazioneParametriAdapter(_protocolloLogs, this._verticalizzazioniFactory.Create<VerticalizzazioneProtocolloDocpro>(DatiProtocollo.IdComuneAlias, DatiProtocollo.Software, DatiProtocollo.CodiceComune));
            var protoSrv = new DocProProtocollazioneService(vertParams.Url, _protocolloLogs, _protocolloSerializer);
            string token = protoSrv.Login(vertParams.Codiceente, vertParams.Username, vertParams.Password);

            var datiProto = DatiProtocolloInsertFactory.Create(protoIn);

            DateTime? dataRicevimento = DateTime.Now;

            if (this.DatiProtocollo.TipoAmbito == AmbitoProtocollazioneEnum.DA_ISTANZA)
                dataRicevimento = this.DatiProtocollo.Istanza.DATA.Value;

            var conf = new DocProSegnaturaParamConfiguration(vertParams, Operatore, protoIn, /*domicilioElettronico,*/ dataRicevimento.Value);

            var segnaturaBuilder = new DocProSegnaturaBuilder(datiProto, conf, _protocolloLogs, _protocolloSerializer, protoIn.RecuperaAllegati().ToList());

            if (vertParams.InviaSegnatura && !protoIn.HaAllegati())
                segnaturaBuilder.CreaSegnaturaFittizia();

            if (vertParams.InviaAllMovAvvio && !protoIn.HaAllegati() && this.DatiProtocollo.TipoAmbito == AmbitoProtocollazioneEnum.DA_ISTANZA)
                protoSrv.InserisciAllegatiDaMovimentoAvvio(this.DatiProtocollo.Istanza, this.DatiProtocollo.Db, DatiProtocollo.IdComune, protoIn.RecuperaAllegati().ToList());

            protoSrv.InserisciAllegati(protoIn.RecuperaAllegati().ToList(), token, vertParams.Username);

            segnaturaBuilder.SerializzaSegnatura();

            var response = protoSrv.Protocollazione(vertParams.Username, token);

            var adapter = new DocProProtocolloInsertOutputAdapter(response);
            return adapter.DatiProtocollo;
        }
    }
}
