using VBG.Shared.Infrastructure.ServiceModel;
using ProtocolloFoliumService;
using SIGePro.Manager.VerticalizzazioniBase;
using VBG.Backend.Protocollo.AppLogic.Core.Folium;
using VBG.Backend.Protocollo.AppLogic.Core.Folium.Allegati;
using VBG.Backend.Protocollo.AppLogic.Core.Folium.Classifiche;
using VBG.Backend.Protocollo.AppLogic.Core.Folium.LeggiProtocollo;
using VBG.Backend.Protocollo.AppLogic.Core.Folium.Protocollo;
using VBG.Backend.Protocollo.AppLogic.Core.Folium.ServiceWrapper;
using VBG.Backend.Protocollo.AppLogic.Shared;
using VBG.Backend.Protocollo.AppLogic.Shared.Data;
using VBG.Backend.Protocollo.AppLogic.Shared.Enums;
using VBG.Backend.Protocollo.AppLogic.Shared.Factories;
using VBG.Backend.Protocollo.AppLogic.Shared.WsDataClass;
using VBG.Backend.Protocollo.Verticalizzazioni.Core;

namespace VBG.Backend.Protocollo.AppLogic.Core
{
    public class PROTOCOLLO_FOLIUM : ProtocolloBase
    {
        private readonly IVerticalizzazioniFactory _verticalizzazioniFactory;
        private readonly IBindingFactory _bindingFactory;

        public PROTOCOLLO_FOLIUM(IVerticalizzazioniFactory verticalizzazioniFactory, IBindingFactory bindingFactory)
        {
            this._verticalizzazioniFactory = verticalizzazioniFactory;
            this._bindingFactory = bindingFactory;
        }

        public override ListaTipiClassificaType GetClassifiche()
        {
            var vert = new VerticalizzazioniWrapper(this._protocolloLogs, this._verticalizzazioniFactory.Create<VerticalizzazioneProtocolloFolium>(this.DatiProtocollo.IdComuneAlias, this.DatiProtocollo.Software, this.DatiProtocollo.CodiceComune));
            if (!vert.UsaWsClassifiche)
                return base.GetClassifiche();

            var auth = new WSAuthentication
            {
                aoo = vert.Aoo,
                applicazione = vert.Applicazione,
                ente = vert.CodiceEnte,
                password = vert.Password,
                username = vert.Username
            };

            var srv = new ProtocollazioneServiceWrapper(vert.Url, vert.Binding, auth, this._protocolloLogs, this._protocolloSerializer, this._bindingFactory);
            var adapter = new ClassificheOutputAdapter(srv);

            var retVal = new ListaTipiClassificaType();
            retVal.Classifica = adapter.Adatta().ToArray();
            return retVal;
        }

        public override AllegatoResponseType LeggiAllegato()
        {
            var vert = new VerticalizzazioniWrapper(this._protocolloLogs, this._verticalizzazioniFactory.Create<VerticalizzazioneProtocolloFolium>(this.DatiProtocollo.IdComuneAlias, this.DatiProtocollo.Software, this.DatiProtocollo.CodiceComune));

            var auth = new WSAuthentication
            {
                aoo = vert.Aoo,
                applicazione = vert.Applicazione,
                ente = vert.CodiceEnte,
                password = vert.Password,
                username = vert.Username
            };

            var srv = new ProtocollazioneServiceWrapper(vert.Url, vert.Binding, auth, this._protocolloLogs, this._protocolloSerializer, this._bindingFactory);
            var adapter = new AllegatoOutputAdapter(srv, Convert.ToInt32(this.IdAllegato));

            var retVal = new AllegatoResponseType();

            if (this.IdAllegato == "0")
            {
                retVal = base.LeggiAllegatoDaLeggiProtocollo();
                var image = srv.LeggiAllegatoPrincipale(Convert.ToInt64(this.IdProtocollo));
                retVal.Image = image;
            }
            else
            {
                retVal = adapter.Adatta();
            }

            return retVal;
        }

        public override DatiProtocolloResponseType Protocollazione(DatiProtocolloIn protoIn)
        {
            var vert = new VerticalizzazioniWrapper(this._protocolloLogs, this._verticalizzazioniFactory.Create<VerticalizzazioneProtocolloFolium>(this.DatiProtocollo.IdComuneAlias, this.DatiProtocollo.Software, this.DatiProtocollo.CodiceComune));
            var datiProto = DatiProtocolloInsertFactory.Create(protoIn);
            string note = "";

            if (this.DatiProtocollo.TipoAmbito == AmbitoProtocollazioneEnum.DA_MOVIMENTO && this.DatiProtocollo.Movimento != null)
            {
                note = this.DatiProtocollo.Movimento.NOTE;
            }

            var info = new RequestInfo(vert, datiProto, note, base.Anagrafiche, base.Operatore);
            var factory = ProtocollazioneFactory.Create(info, base._protocolloLogs, base._protocolloSerializer, this._bindingFactory);
            var response = factory.Protocolla();

            if (!String.IsNullOrEmpty(response.IdProtocollo))
            {
                var idProtocollo = Convert.ToInt64(response.IdProtocollo);
                factory.InserisciAllegati(idProtocollo);
                factory.Assegna(idProtocollo);
                factory.InviaMail(idProtocollo);
            }

            return response;
        }

        public override List<DatiProtocolloLettoResponseType> LeggiProtocollo(LeggiProtocolloRequest leggiProtocolloRequest)
        {
            var vert = new VerticalizzazioniWrapper(this._protocolloLogs, this._verticalizzazioniFactory.Create<VerticalizzazioneProtocolloFolium>(this.DatiProtocollo.IdComuneAlias, this.DatiProtocollo.Software, this.DatiProtocollo.CodiceComune));
            var auth = new WSAuthentication
            {
                aoo = vert.Aoo,
                applicazione = vert.Applicazione,
                ente = vert.CodiceEnte,
                password = vert.Password,
                username = vert.Username
            };

            var adapterInput = new LeggiProtocolloInputAdapter(leggiProtocolloRequest.NumeroProtocollo, leggiProtocolloRequest.AnnoProtocollo, vert.Registro);

            var srv = new ProtocollazioneServiceWrapper(vert.Url, vert.Binding, auth, this._protocolloLogs, this._protocolloSerializer, this._bindingFactory);

            var adapterOutput = new LeggiProtocolloOutputAdapter(srv);

            return new List<DatiProtocolloLettoResponseType>() { adapterOutput.Adatta(adapterInput.Request) };
        }
    }
}
