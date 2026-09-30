using VBG.Shared.Infrastructure.ServiceModel;
using Init.SIGePro.Manager;
using SIGePro.Manager.VerticalizzazioniBase;
using VBG.Backend.Protocollo.AppLogic.Core.CiviliaNext.OAuth2;
using VBG.Backend.Protocollo.AppLogic.Core.Prisma;
using VBG.Backend.Protocollo.AppLogic.Core.Prisma.Allegati;
using VBG.Backend.Protocollo.AppLogic.Core.Prisma.Classificazione;
using VBG.Backend.Protocollo.AppLogic.Core.Prisma.Fascicolazione;
using VBG.Backend.Protocollo.AppLogic.Core.Prisma.LeggiProtocollo;
using VBG.Backend.Protocollo.AppLogic.Core.Prisma.Pec;
using VBG.Backend.Protocollo.AppLogic.Core.Prisma.Protocollazione;
using VBG.Backend.Protocollo.AppLogic.Core.Prisma.Smistamento;
using VBG.Backend.Protocollo.AppLogic.Shared;
using VBG.Backend.Protocollo.AppLogic.Shared.Costants;
using VBG.Backend.Protocollo.AppLogic.Shared.Data;
using VBG.Backend.Protocollo.AppLogic.Shared.Factories;
using VBG.Backend.Protocollo.AppLogic.Shared.Services;
using VBG.Backend.Protocollo.AppLogic.Shared.WsDataClass;
using VBG.Backend.Protocollo.Verticalizzazioni.Core;

namespace VBG.Backend.Protocollo.AppLogic.Core
{
    public class PROTOCOLLO_PRISMA : ProtocolloBase
    {
        private readonly IVerticalizzazioniFactory _verticalizzazioniFactory;
        private readonly IBindingFactory _bindingFactory;

        public PROTOCOLLO_PRISMA(IVerticalizzazioniFactory verticalizzazioniFactory, IBindingFactory bindingFactory)
        {
            this._verticalizzazioniFactory = verticalizzazioniFactory;
            this._bindingFactory = bindingFactory;
        }

        public override void InizializzaProtocolloBase(ResolveDatiProtocollazioneService datiProtocolloService)
        {
            base.InizializzaProtocolloBase(datiProtocolloService);
            base._protocolloSerializer = new PrismaSerializer(this._protocolloLogs, this._protocolloValidation);
        }

        public override DatiProtocolloResponseType Protocollazione(DatiProtocolloIn protoIn)
        {
            var vert = new ParametriRegoleWrapper(this._verticalizzazioniFactory.Create<VerticalizzazioneProtocolloPrisma>(this.DatiProtocollo.IdComuneAlias, this.DatiProtocollo.Software, this.DatiProtocollo.CodiceComune));
            var datiProto = DatiProtocolloInsertFactory.Create(protoIn);

            var authService = new AuthenticationServiceWrapper(vert.UrlProtoDocArea, this._protocolloLogs, this._protocolloSerializer, _bindingFactory, vert.Username, vert.Password, vert.CodiceEnte);
            var token = authService.Login();

            var service = new ProtocollazioneServiceWrapper(vert.UrlProtoDocArea, this._protocolloLogs, this._protocolloSerializer, _bindingFactory, new CredentialsInfo(vert.Username, vert.Password, vert.CodiceEnte, token));
            var info = new ProtocollazioneInfo(datiProto.Uo, datiProto.DescrizioneAmministrazione, protoIn, this.Anagrafiche, vert, service, base.DatiProtocollo.TipoAmbito, datiProto.Ruolo);
            var adapter = new ProtocollazioneInAdapter(this._protocolloSerializer, info);

            var requestProtocollazione = adapter.Adatta();
            var response = service.Protocolla(requestProtocollazione);

            string messaggio = "";

            if (protoIn.Flusso == ProtocolloConstants.COD_PARTENZA && !info.ParametriRegola.DisabilitaInvioPec)
            {
                try
                {

                    var infoPec = new PecInfo(response.lngAnnoPG, response.lngNumPG, vert.Username, vert.TipoRegistro, base.Anagrafiche);
                    var adapterPec = new PecInAdapter();
                    var request = adapterPec.Adatta(infoPec);

                    messaggio = $"Pec inviata ai seguenti destinatari:<br> {String.Join("<br>", adapterPec.DestinatariConPec)}";

                    var servicePec = new PecServiceWrapper(vert.UrlPec, base._protocolloLogs, base._protocolloSerializer, new CredentialsInfo(vert.Username, vert.Password, vert.CodiceEnte, token), _bindingFactory);
                    servicePec.InviaPec(request);
                }
                catch (Exception ex)
                {
                    base._protocolloLogs.Warn($"{ex.Message}");
                }
            }

            if (info.ParametriRegola.AttivaSmistamentoArrivo || (protoIn.Flusso != ProtocolloConstants.COD_ARRIVO && !info.ParametriRegola.DisabilitaEseguito))
            {
                try
                {
                    var infoSmistamento = new SmistamentoInfo(vert, response.lngNumPG.ToString(), response.lngAnnoPG.ToString(), datiProto.Ruolo);
                    if (String.IsNullOrEmpty(infoSmistamento.Uo))
                    {
                        throw new Exception("UO SMISTAMENTO NON VALORIZZATA");
                    }
                    var adapterSmistamento = new SmistamentoInAdapter(infoSmistamento);
                    var requestEseguito = adapterSmistamento.AdattaEseguito();
                    var smistamentoSrv = new SmistamentoServiceWrapper(vert.UrlProtoDocArea, this._protocolloLogs, this._protocolloSerializer, _bindingFactory, new CredentialsInfo(vert.Username, vert.Password, vert.CodiceEnte, token));
                    smistamentoSrv.Smista(requestEseguito);
                }
                catch (Exception ex)
                {
                    base._protocolloLogs.Warn($"{ex.Message}");
                }
            }

            return new DatiProtocolloResponseType
            {
                AnnoProtocollo = response.lngAnnoPG.ToString(),
                DataProtocollo = response.strDataPG,
                NumeroProtocollo = response.lngNumPG.ToString(),
                Warning = this._protocolloLogs.Warnings.WarningMessage,
                Messaggio = messaggio
            };
        }

        public override List<DatiProtocolloLettoResponseType> LeggiProtocollo(LeggiProtocolloRequest leggiProtocolloRequest)
        {
            try
            {
                var serializer = new PrismaSerializer(this._protocolloLogs, this._protocolloValidation);

                this._protocolloLogs.DebugFormat($"Inizio lettura protocollo id: {leggiProtocolloRequest.IdProtocollo}, anno: {leggiProtocolloRequest.AnnoProtocollo}, numero: {leggiProtocolloRequest.NumeroProtocollo}");

                this._protocolloLogs.DebugFormat($"Inizio parametri dalla verticalizzazione");
                var vert = new ParametriRegoleWrapper(this._verticalizzazioniFactory.Create<VerticalizzazioneProtocolloPrisma>(this.DatiProtocollo.IdComuneAlias, this.DatiProtocollo.Software, this.DatiProtocollo.CodiceComune));
                this._protocolloLogs.DebugFormat($"Fine parametri dalla verticalizzazione");


                var adapterIn = new LeggiProtocolloInAdapter();
                var request = adapterIn.Adatta(leggiProtocolloRequest.NumeroProtocollo, leggiProtocolloRequest.AnnoProtocollo, vert.TipoRegistro, vert.Username);

                var authService = new AuthenticationServiceWrapper(vert.UrlProtoDocArea, this._protocolloLogs, serializer, _bindingFactory, vert.Username, vert.Password, vert.CodiceEnte);
                var token = authService.Login();

                var service = new LeggiProtocolloServiceWrapper(vert.UrlExtended, this._protocolloLogs, serializer, _bindingFactory, new CredentialsInfo(vert.Username, vert.Password, vert.CodiceEnte, token));
                var response = service.Leggi(request);

                var adapterPecIn = new LeggiDatiPecAdapter();
                var requestDatiPec = adapterPecIn.Adatta(leggiProtocolloRequest.NumeroProtocollo, leggiProtocolloRequest.AnnoProtocollo, vert.TipoRegistro, vert.Username);
                var responseDatiPec = service.GetDatiPec(requestDatiPec);

                var serviceAllegati = new AllegatiServiceWrapper(vert.UrlAllegati, this._protocolloLogs, serializer, new CredentialsInfo(vert.Username, vert.Password, vert.CodiceEnte, token), _bindingFactory);

                var adapter = new LeggiProtocolloOutAdapter(serializer);

                this._protocolloLogs.DebugFormat($"Fine lettura protocollo id: {leggiProtocolloRequest.IdProtocollo}, anno: {leggiProtocolloRequest.AnnoProtocollo}, numero: {leggiProtocolloRequest.NumeroProtocollo}");

                return new List<DatiProtocolloLettoResponseType>() { adapter.Adatta(response, responseDatiPec, serviceAllegati, this._protocolloLogs) };
            }
            catch (Exception ex)
            {
                this._protocolloLogs.Error(ex);
                throw (ex);
            }
        }

        public override AllegatoResponseType LeggiAllegato()
        {
            var vert = new ParametriRegoleWrapper(this._verticalizzazioniFactory.Create<VerticalizzazioneProtocolloPrisma>(this.DatiProtocollo.IdComuneAlias, this.DatiProtocollo.Software, this.DatiProtocollo.CodiceComune));
            var service = new AllegatiServiceWrapper(vert.UrlAllegati, this._protocolloLogs, this._protocolloSerializer, new CredentialsInfo(vert.Username, vert.Password, vert.CodiceEnte, ""), _bindingFactory);

            var datiAllegato = IdAllegato.Split(',');

            var image = service.Download(datiAllegato[1], datiAllegato[0]);
            var retVal = base.LeggiAllegatoDaLeggiProtocollo();
            retVal.Image = image;

            return retVal;
        }

        public override DatiFascicoloResponseType Fascicola(Shared.Data.Fascicolo fascicolo)
        {
            var vert = new ParametriRegoleWrapper(this._verticalizzazioniFactory.Create<VerticalizzazioneProtocolloPrisma>(this.DatiProtocollo.IdComuneAlias, this.DatiProtocollo.Software, this.DatiProtocollo.CodiceComune));
            var authService = new AuthenticationServiceWrapper(vert.UrlProtoDocArea, this._protocolloLogs, this._protocolloSerializer, _bindingFactory, vert.Username, vert.Password, vert.CodiceEnte);
            var token = authService.Login();

            var leggi = this.LeggiProtocollo(new LeggiProtocolloRequest() { AnnoProtocollo = base.AnnoProtocollo, NumeroProtocollo = base.NumProtocollo });
            var info = new FascicolazioneInfo(fascicolo, vert.Username, leggi.FirstOrDefault().InCaricoA, base.NumProtocollo, base.AnnoProtocollo, vert.TipoRegistro);
            var service = new FascicolazioneServiceWrapper(vert.UrlExtended, this._protocolloLogs, this._protocolloSerializer, _bindingFactory, new CredentialsInfo(vert.Username, vert.Password, vert.CodiceEnte, token));

            var adapter = new FascicolazioneInAdapter();
            var request = adapter.Adatta(info, service);

            var response = service.FascicolaProtocollo(request);

            return new DatiFascicoloResponseType
            {
                AnnoFascicolo = response.AnnoFascicolo,
                NumeroFascicolo = response.NumeroFascicolo
            };
        }

        public override DatiFascicoloResponseType CambiaFascicolo(Shared.Data.Fascicolo fascicolo)
        {
            var vert = new ParametriRegoleWrapper(this._verticalizzazioniFactory.Create<VerticalizzazioneProtocolloPrisma>(this.DatiProtocollo.IdComuneAlias, this.DatiProtocollo.Software, this.DatiProtocollo.CodiceComune));
            var authService = new AuthenticationServiceWrapper(vert.UrlProtoDocArea, this._protocolloLogs, this._protocolloSerializer, _bindingFactory, vert.Username, vert.Password, vert.CodiceEnte);
            var token = authService.Login();

            var srv = new FascicolazioneServiceWrapper(vert.UrlExtended, base._protocolloLogs, base._protocolloSerializer, _bindingFactory, new CredentialsInfo(vert.Username, vert.Password, vert.CodiceEnte, token));
            var info = new FascicolazioneInfo(fascicolo, vert.Username, "", this.DatiProtocollo.Istanza.NUMEROPROTOCOLLO, this.DatiProtocollo.Istanza.DATAPROTOCOLLO.Value.Year.ToString(), vert.TipoRegistro);

            var protocolli = this.LeggiProtocollo(new LeggiProtocolloRequest() { AnnoProtocollo = this.DatiProtocollo.Istanza.DATAPROTOCOLLO.Value.Year.ToString(), NumeroProtocollo = this.DatiProtocollo.Istanza.NUMEROPROTOCOLLO });
            var leggi = protocolli.FirstOrDefault();

            info.Uo = leggi.InCaricoA;

            if (String.IsNullOrEmpty(fascicolo.NumeroFascicolo))
            {
                var adapterLeggiFasc = new LeggiFascicoloInAdapter();
                var requestLeggiFasc = adapterLeggiFasc.Adatta(fascicolo.AnnoFascicolo.GetValueOrDefault(DateTime.Now.Year).ToString(), leggi.NumeroPratica, vert.Username, leggi.Classifica);
                var fascicoloProtocollo = srv.GetDettaglioFascicolo(requestLeggiFasc);

                if (fascicoloProtocollo == null)
                {
                    throw new Exception($"NON E' STATO POSSIBILE RECUPERARE IL FASCICOLO NUMERO {fascicolo.NumeroFascicolo}, ANNO {fascicolo.AnnoFascicolo.GetValueOrDefault(DateTime.Now.Year).ToString()}, CLASSIFICA {leggi.Classifica}, RELATIVO AL PROTOCOLLO NUMERO {this.DatiProtocollo.Istanza.NUMEROPROTOCOLLO} ANNO {this.DatiProtocollo.Istanza.DATAPROTOCOLLO.Value.Year.ToString()}, FASCICOLO NON TROVATO, NON E' STATO QUINDI POSSIBILE VALORIZZARE L'OGGETTO PER IL NUOVO FASCICOLO");
                }

                info.OggettoFascicolo = fascicoloProtocollo.OggettoFascicolo;
            }
            else
            {
                var annoFascicolo = fascicolo.AnnoFascicolo.GetValueOrDefault(DateTime.Now.Year).ToString();
                var adapterLeggiFasc = new LeggiFascicoloInAdapter();
                var requestLeggiFasc = adapterLeggiFasc.Adatta(annoFascicolo, fascicolo.NumeroFascicolo, vert.Username, leggi.Classifica);
                var responseFasc = srv.GetDettaglioFascicolo(requestLeggiFasc);

                if (responseFasc == null)
                {
                    throw new Exception($"FASCICOLO NUMERO {fascicolo.NumeroFascicolo}, ANNO {annoFascicolo}, CLASSIFICA {leggi.Classifica}, NON TROVATO");
                }
            }

            var adapter = new CambiaFascicoloInAdapter();
            var requestIstanza = adapter.Adatta(info, srv);

            srv.CambiaFascicolo(requestIstanza);

            var movimenti = new MovimentiMgr(base.DatiProtocollo.Db);
            var movimentiProtocollati = movimenti.GetMovimentiProtocollati(base.DatiProtocollo.IdComune, base.DatiProtocollo.CodiceIstanza);

            foreach (var movimento in movimentiProtocollati)
            {
                if (!(movimento.NUMEROPROTOCOLLO == this.DatiProtocollo.Istanza.NUMEROPROTOCOLLO && movimento.DATAPROTOCOLLO.Value.Year.ToString() == this.DatiProtocollo.Istanza.DATAPROTOCOLLO.Value.Year.ToString()))
                {
                    info.NumeroProtocollo = movimento.NUMEROPROTOCOLLO;
                    info.AnnoProtocollo = movimento.DATAPROTOCOLLO.Value.Year.ToString();
                    var requestMovimento = adapter.Adatta(info, srv);
                    srv.CambiaFascicolo(requestMovimento);
                }
            }

            return new DatiFascicoloResponseType
            {
                AnnoFascicolo = requestIstanza.FascicoloGruppo.Anno,
                DataFascicolo = info.DataFascicolo,
                NumeroFascicolo = requestIstanza.FascicoloGruppo.Numero
            };
        }

        public override DatiProtocolloFascicolatoResponseType IsFascicolato(string idProtocollo, string annoProtocollo, string numeroProtocollo)
        {
            var vert = new ParametriRegoleWrapper(this._verticalizzazioniFactory.Create<VerticalizzazioneProtocolloPrisma>(this.DatiProtocollo.IdComuneAlias, this.DatiProtocollo.Software, this.DatiProtocollo.CodiceComune));
            var authService = new AuthenticationServiceWrapper(vert.UrlProtoDocArea, this._protocolloLogs, this._protocolloSerializer, _bindingFactory, vert.Username, vert.Password, vert.CodiceEnte);
            var token = authService.Login();

            var adapterIn = new LeggiProtocolloInAdapter();
            var request = adapterIn.Adatta(numeroProtocollo, annoProtocollo, vert.TipoRegistro, vert.Username);
            var serviceLeggi = new LeggiProtocolloServiceWrapper(vert.UrlExtended, this._protocolloLogs, this._protocolloSerializer, _bindingFactory, new CredentialsInfo(vert.Username, vert.Password, vert.CodiceEnte, token));
            var response = serviceLeggi.Leggi(request);

            if (String.IsNullOrEmpty(response.Doc.FascicoloNumero) || String.IsNullOrEmpty(response.Doc.FascicoloAnno) || String.IsNullOrEmpty(response.Doc.ClassificaCod))
            {
                return new DatiProtocolloFascicolatoResponseType { Fascicolato = EnumFascicolatoType.no };
            }

            var adapterLeggiFasc = new LeggiFascicoloInAdapter();
            var requestLeggiFasc = adapterLeggiFasc.Adatta(response.Doc.FascicoloAnno, response.Doc.FascicoloNumero, vert.Username, response.Doc.ClassificaCod);
            var serviceFasc = new FascicolazioneServiceWrapper(vert.UrlExtended, this._protocolloLogs, this._protocolloSerializer, _bindingFactory, new CredentialsInfo(vert.Username, vert.Password, vert.CodiceEnte, token));

            var responseFasc = serviceFasc.GetDettaglioFascicolo(requestLeggiFasc);



            var adapterOut = new IsFascicolatoOutAdapter();
            return adapterOut.Adatta(responseFasc);
        }

        public override ListaFascicoliResponseType GetFascicoli(Shared.Data.Fascicolo fascicolo)
        {
            var vert = new ParametriRegoleWrapper(this._verticalizzazioniFactory.Create<VerticalizzazioneProtocolloPrisma>(this.DatiProtocollo.IdComuneAlias, this.DatiProtocollo.Software, this.DatiProtocollo.CodiceComune));

            var authService = new AuthenticationServiceWrapper(vert.UrlProtoDocArea, this._protocolloLogs, this._protocolloSerializer, _bindingFactory, vert.Username, vert.Password, vert.CodiceEnte);
            var token = authService.Login();

            var adapterLeggiFasc = new LeggiFascicoloInAdapter();
            var requestLeggiFasc = adapterLeggiFasc.Adatta(fascicolo.AnnoFascicolo.GetValueOrDefault(DateTime.Now.Year).ToString(), fascicolo.NumeroFascicolo, vert.Username, fascicolo.Classifica);
            var serviceFasc = new FascicolazioneServiceWrapper(vert.UrlExtended, this._protocolloLogs, this._protocolloSerializer, _bindingFactory, new CredentialsInfo(vert.Username, vert.Password, vert.CodiceEnte, token));
            var responseFasc = serviceFasc.GetFascicoli(requestLeggiFasc);

            var adapterOut = new LeggiFascicoliOutAdapter();
            return adapterOut.Adatta(responseFasc);
        }

        public override ListaTipiClassificaType GetClassifiche()
        {
            var vert = new ParametriRegoleWrapper(this._verticalizzazioniFactory.Create<VerticalizzazioneProtocolloPrisma>(this.DatiProtocollo.IdComuneAlias, this.DatiProtocollo.Software, this.DatiProtocollo.CodiceComune));
            if (vert.DisabilitaWsClassifiche)
            {
                return base.GetClassifiche();
            }

            var authService = new AuthenticationServiceWrapper(vert.UrlProtoDocArea, this._protocolloLogs, this._protocolloSerializer, _bindingFactory, vert.Username, vert.Password, vert.CodiceEnte);
            var token = authService.Login();
            var extService = new ClassificheServiceWrapper(vert.UrlExtended, base._protocolloLogs, base._protocolloSerializer, new CredentialsInfo(vert.Username, vert.Password, vert.CodiceEnte, token), _bindingFactory);

            var adapter = new ClassificheInAdapter();
            var request = adapter.Adatta(vert.CodiceEnte, vert.CodiceAoo, vert.Username);

            var response = extService.LeggiClassifiche(request);

            return new ListaTipiClassificaType
            {
                Classifica = response.Classifica.Select(x => new ListaTipiClassificaClassifica
                {
                    Codice = x.CodiceClassifica,
                    Descrizione = $"{x.CodiceClassifica} - {x.DescrizioneTroncata}"
                }).OrderBy(x => x.Codice).ToArray()
            };
        }

        public override void AggiungiAllegati(string idProtocollo, string numeroProtocollo, DateTime? dataProtocollo, IEnumerable<ProtocolloAllegati> allegati)
        {



            base.AggiungiAllegati(idProtocollo, numeroProtocollo, dataProtocollo, allegati);
        }
    }
}
