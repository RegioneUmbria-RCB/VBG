using Init.SIGePro.Data;
using VBG.Backend.Protocollo.Verticalizzazioni.Core;
using System;
using System.Collections.Generic;
using VBG.Backend.Protocollo.AppLogic.Core.Kibernetes;
using VBG.Backend.Protocollo.AppLogic.Shared;
using VBG.Backend.Protocollo.AppLogic.Shared.Data;
using VBG.Backend.Protocollo.AppLogic.Shared.Services;
using VBG.Backend.Protocollo.AppLogic.Shared.WsDataClass;
using SIGePro.Manager.VerticalizzazioniBase;
using VBG.Shared.Infrastructure.ServiceModel;

namespace VBG.Backend.Protocollo.AppLogic.Core
{
    public class PROTOCOLLO_KIBERNETES : ProtocolloBase
    {
        private readonly IVerticalizzazioniFactory _verticalizzazioniFactory;
        private readonly IBindingFactory _bindingFactory;

        public PROTOCOLLO_KIBERNETES(IVerticalizzazioniFactory verticalizzazioniFactory, IBindingFactory bindingFactory)
        {
            this._verticalizzazioniFactory = verticalizzazioniFactory;
            this._bindingFactory = bindingFactory;
        }

        public override void InizializzaProtocolloBase(ResolveDatiProtocollazioneService datiProtocolloService)
        {
            base.InizializzaProtocolloBase(datiProtocolloService);
            base._protocolloSerializer = new KibernetesSerializer(this._protocolloLogs, this._protocolloValidation);
        }

        public override DatiProtocolloResponseType Protocollazione(DatiProtocolloIn protoIn)
        {

            var parametriService = new ParametriService(this._verticalizzazioniFactory.Create<VerticalizzazioneProtocolloKibernetes>(DatiProtocollo.IdComuneAlias, DatiProtocollo.Software, DatiProtocollo.CodiceComune));

            var protoService = new ProtocollazioneServiceBuilder(parametriService, _protocolloLogs, _protocolloSerializer, this._bindingFactory).Build(this.Anagrafiche, this.Operatore);

            var response = protoService.Protocolla(protoIn);

            return new DatiProtocolloResponseType
            {
                AnnoProtocollo = response.Anno.ToString(),
                NumeroProtocollo = response.Numero.ToString(),
                DataProtocollo = DateTime.Now.ToString("dd/MM/yyyy"),
                Warning = response.Warning
            };
        }

        public override List<DatiProtocolloLettoResponseType> LeggiProtocollo(LeggiProtocolloRequest leggiProtocolloRequest)
        {
            var parametriService = new ParametriService(this._verticalizzazioniFactory.Create<VerticalizzazioneProtocolloKibernetes>(DatiProtocollo.IdComuneAlias, DatiProtocollo.Software, DatiProtocollo.CodiceComune));

            var service = new LeggiProtocolloServiceBuilder(parametriService, _protocolloLogs, _protocolloSerializer, this._bindingFactory).Build();

            return new List<DatiProtocolloLettoResponseType>() { service.LeggiProtocollo(leggiProtocolloRequest.IdProtocollo, leggiProtocolloRequest.AnnoProtocollo, leggiProtocolloRequest.NumeroProtocollo, DataProtocollo) };
        }

        public override void AggiungiAllegati(string idProtocollo, string numeroProtocollo, DateTime? dataProtocollo, IEnumerable<ProtocolloAllegati> allegati)
        {
            var parametriService = new ParametriService(this._verticalizzazioniFactory.Create<VerticalizzazioneProtocolloKibernetes>(DatiProtocollo.IdComuneAlias, DatiProtocollo.Software, DatiProtocollo.CodiceComune));

            var service = new AggiungiAllegatiServiceBuilder(parametriService, _protocolloLogs, _protocolloSerializer, this._bindingFactory).Build();

            var response = service.AggiungiAllegati(allegati, long.Parse(numeroProtocollo), Convert.ToInt16(dataProtocollo.Value.Year));

            if (!response.Ok)
            {
                throw new Exception(String.Join(", ", response.Errori));
            }
        }

        public override AllegatoResponseType LeggiAllegato()
        {
            var parametriService = new ParametriService(this._verticalizzazioniFactory.Create<VerticalizzazioneProtocolloKibernetes>(DatiProtocollo.IdComuneAlias, DatiProtocollo.Software, DatiProtocollo.CodiceComune));

            var service = new LeggiAllegatoServiceBuilder(parametriService, _protocolloLogs, _protocolloSerializer, this._bindingFactory).Build();

            var ids = IdAllegato.Split('-');

            var idProtocollo = Convert.ToInt32(ids[0]);
            var idAllegato = Convert.ToInt32(ids[1]);

            return service.LeggiAllegato(idProtocollo, idAllegato).ToAllOut();
        }

    }
}
