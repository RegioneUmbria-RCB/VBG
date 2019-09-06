using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Data;
using Init.SIGePro.Manager;
using Init.SIGePro.Manager.Verticalizzazioni;
using Init.SIGePro.Protocollo.Constants;
using Init.SIGePro.Protocollo.Data;
using Init.SIGePro.Protocollo.Prisma;
using Init.SIGePro.Protocollo.Prisma.Allegati;
using Init.SIGePro.Protocollo.Prisma.Classificazione;
using Init.SIGePro.Protocollo.Prisma.Fascicolazione;
using Init.SIGePro.Protocollo.Prisma.LeggiProtocollo;
using Init.SIGePro.Protocollo.Prisma.Pec;
using Init.SIGePro.Protocollo.Prisma.Protocollazione;
using Init.SIGePro.Protocollo.Prisma.Smistamento;
using Init.SIGePro.Protocollo.ProtocolloFactories;
using Init.SIGePro.Protocollo.WsDataClass;

namespace Init.SIGePro.Protocollo
{
    public class PROTOCOLLO_PRISMA : ProtocolloBase
    {
        public override DatiProtocolloRes Protocollazione(DatiProtocolloIn protoIn)
        {
            var vert = new ParametriRegoleWrapper(new VerticalizzazioneProtocolloPrisma(this.DatiProtocollo.IdComuneAlias, this.DatiProtocollo.Software, this.DatiProtocollo.CodiceComune));
            var datiProto = DatiProtocolloInsertFactory.Create(protoIn);
            
            var authService = new AuthenticationServiceWrapper(vert.UrlProtoDocArea, this._protocolloLogs, this._protocolloSerializer, new CredentialsInfo(vert.Username, vert.Password, vert.CodiceEnte, ""));
            var token = authService.Login();

            var service = new ProtocollazioneServiceWrapper(vert.UrlProtoDocArea, this._protocolloLogs, this._protocolloSerializer, new CredentialsInfo(vert.Username, vert.Password, vert.CodiceEnte, token));
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

                    var servicePec = new PecServiceWrapper(vert.UrlPec, base._protocolloLogs, base._protocolloSerializer, new CredentialsInfo(vert.Username, vert.Password, vert.CodiceEnte, token));
                    servicePec.InviaPec(request);
                }
                catch (Exception ex)
                {
                    base._protocolloLogs.Warn($"{ex.Message}");
                }
            }

            if (protoIn.Flusso != ProtocolloConstants.COD_ARRIVO && !info.ParametriRegola.DisabilitaEseguito)
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
                    var smistamentoSrv = new SmistamentoServiceWrapper(vert.UrlProtoDocArea, this._protocolloLogs, this._protocolloSerializer, new CredentialsInfo(vert.Username, vert.Password, vert.CodiceEnte, token));
                    smistamentoSrv.Smista(requestEseguito);
                }
                catch (Exception ex)
                {
                    base._protocolloLogs.Warn($"{ex.Message}");
                }
            }

            return new DatiProtocolloRes
            {
                AnnoProtocollo = response.lngAnnoPG.ToString(),
                DataProtocollo = response.strDataPG,
                NumeroProtocollo = response.lngNumPG.ToString(),
                Warning = this._protocolloLogs.Warnings.WarningMessage,
                Messaggio = messaggio
            };
        }

        public override DatiProtocolloLetto LeggiProtocollo(string idProtocollo, string annoProtocollo, string numeroProtocollo)
        {
            var vert = new ParametriRegoleWrapper(new VerticalizzazioneProtocolloPrisma(this.DatiProtocollo.IdComuneAlias, this.DatiProtocollo.Software, this.DatiProtocollo.CodiceComune));

            var adapterIn = new LeggiProtocolloInAdapter();
            var request = adapterIn.Adatta(numeroProtocollo, annoProtocollo, vert.TipoRegistro, vert.Username);

            var authService = new AuthenticationServiceWrapper(vert.UrlProtoDocArea, this._protocolloLogs, this._protocolloSerializer, new CredentialsInfo(vert.Username, vert.Password, vert.CodiceEnte, ""));
            var token = authService.Login();

            var service = new LeggiProtocolloServiceWrapper(vert.UrlExtended, this._protocolloLogs, this._protocolloSerializer, new CredentialsInfo(vert.Username, vert.Password, vert.CodiceEnte, token));
            var response = service.Leggi(request);

            var adapterPecIn = new LeggiDatiPecAdapter();
            var requestDatiPec = adapterPecIn.Adatta(numeroProtocollo, annoProtocollo, vert.TipoRegistro, vert.Username);
            var responseDatiPec = service.GetDatiPec(requestDatiPec);

            var serviceAllegati = new AllegatiServiceWrapper(vert.UrlAllegati, this._protocolloLogs, this._protocolloSerializer, new CredentialsInfo(vert.Username, vert.Password, vert.CodiceEnte, token));

            var adapter = new LeggiProtocolloOutAdapter();
            return adapter.Adatta(response, responseDatiPec, serviceAllegati, this._protocolloLogs, this._protocolloSerializer);
        }

        public override AllOut LeggiAllegato()
        {
            var vert = new ParametriRegoleWrapper(new VerticalizzazioneProtocolloPrisma(this.DatiProtocollo.IdComuneAlias, this.DatiProtocollo.Software, this.DatiProtocollo.CodiceComune));
            var service = new AllegatiServiceWrapper(vert.UrlAllegati, this._protocolloLogs, this._protocolloSerializer, new CredentialsInfo(vert.Username, vert.Password, vert.CodiceEnte, ""));

            var datiAllegato = IdAllegato.Split(',');

            var image = service.Download(datiAllegato[1], datiAllegato[0]);
            var retVal = base.GetAllegato();
            retVal.Image = image;

            return retVal;
        }

        public override DatiFascicolo Fascicola(Data.Fascicolo fascicolo)
        {
            var vert = new ParametriRegoleWrapper(new VerticalizzazioneProtocolloPrisma(this.DatiProtocollo.IdComuneAlias, this.DatiProtocollo.Software, this.DatiProtocollo.CodiceComune));
            var authService = new AuthenticationServiceWrapper(vert.UrlProtoDocArea, this._protocolloLogs, this._protocolloSerializer, new CredentialsInfo(vert.Username, vert.Password, vert.CodiceEnte, ""));
            var token = authService.Login();

            var leggi = this.LeggiProtocollo("", base.AnnoProtocollo, base.NumProtocollo);
            var info = new FascicolazioneInfo(fascicolo, vert.Username, leggi.InCaricoA, base.NumProtocollo, base.AnnoProtocollo, vert.TipoRegistro);
            var service = new FascicolazioneServiceWrapper(vert.UrlExtended, this._protocolloLogs, this._protocolloSerializer, new CredentialsInfo(vert.Username, vert.Password, vert.CodiceEnte, token));

            var adapter = new FascicolazioneInAdapter();
            var request = adapter.Adatta(info, service);

            var response = service.FascicolaProtocollo(request);

            return new DatiFascicolo
            {
                AnnoFascicolo = response.AnnoFascicolo,
                NumeroFascicolo = response.NumeroFascicolo
            };
        }

        public override DatiFascicolo CambiaFascicolo(Data.Fascicolo fascicolo)
        {
            var vert = new ParametriRegoleWrapper(new VerticalizzazioneProtocolloPrisma(this.DatiProtocollo.IdComuneAlias, this.DatiProtocollo.Software, this.DatiProtocollo.CodiceComune));
            var authService = new AuthenticationServiceWrapper(vert.UrlProtoDocArea, this._protocolloLogs, this._protocolloSerializer, new CredentialsInfo(vert.Username, vert.Password, vert.CodiceEnte, ""));
            var token = authService.Login();

            var srv = new FascicolazioneServiceWrapper(vert.UrlExtended, base._protocolloLogs, base._protocolloSerializer, new CredentialsInfo(vert.Username, vert.Password, vert.CodiceEnte, token));
            var info = new FascicolazioneInfo(fascicolo, vert.Username, "", this.DatiProtocollo.Istanza.NUMEROPROTOCOLLO, this.DatiProtocollo.Istanza.DATAPROTOCOLLO.Value.Year.ToString(), vert.TipoRegistro);

            var leggi = this.LeggiProtocollo("", this.DatiProtocollo.Istanza.DATAPROTOCOLLO.Value.Year.ToString(), this.DatiProtocollo.Istanza.NUMEROPROTOCOLLO);
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

            return new DatiFascicolo
            {
                AnnoFascicolo = requestIstanza.FascicoloGruppo.Anno,
                DataFascicolo = info.DataFascicolo,
                NumeroFascicolo = requestIstanza.FascicoloGruppo.Numero
            };
        }

        public override DatiProtocolloFascicolato IsFascicolato(string idProtocollo, string annoProtocollo, string numeroProtocollo)
        {
            var vert = new ParametriRegoleWrapper(new VerticalizzazioneProtocolloPrisma(this.DatiProtocollo.IdComuneAlias, this.DatiProtocollo.Software, this.DatiProtocollo.CodiceComune));
            var authService = new AuthenticationServiceWrapper(vert.UrlProtoDocArea, this._protocolloLogs, this._protocolloSerializer, new CredentialsInfo(vert.Username, vert.Password, vert.CodiceEnte, ""));
            var token = authService.Login();

            var adapterIn = new LeggiProtocolloInAdapter();
            var request = adapterIn.Adatta(numeroProtocollo, annoProtocollo, vert.TipoRegistro, vert.Username);
            var serviceLeggi = new LeggiProtocolloServiceWrapper(vert.UrlExtended, this._protocolloLogs, this._protocolloSerializer, new CredentialsInfo(vert.Username, vert.Password, vert.CodiceEnte, token));
            var response = serviceLeggi.Leggi(request);

            if (String.IsNullOrEmpty(response.Doc.FascicoloNumero) || String.IsNullOrEmpty(response.Doc.FascicoloAnno) || String.IsNullOrEmpty(response.Doc.ClassificaCod))
            {
                return new DatiProtocolloFascicolato { Fascicolato = EnumFascicolato.no };
            }

            var adapterLeggiFasc = new LeggiFascicoloInAdapter();
            var requestLeggiFasc = adapterLeggiFasc.Adatta(response.Doc.FascicoloAnno, response.Doc.FascicoloNumero, vert.Username, response.Doc.ClassificaCod);
            var serviceFasc = new FascicolazioneServiceWrapper(vert.UrlExtended, this._protocolloLogs, this._protocolloSerializer, new CredentialsInfo(vert.Username, vert.Password, vert.CodiceEnte, token));

            var responseFasc = serviceFasc.GetDettaglioFascicolo(requestLeggiFasc);



            var adapterOut = new IsFascicolatoOutAdapter();
            return adapterOut.Adatta(responseFasc);
        }

        public override ListaFascicoli GetFascicoli(Data.Fascicolo fascicolo)
        {
            var vert = new ParametriRegoleWrapper(new VerticalizzazioneProtocolloPrisma(this.DatiProtocollo.IdComuneAlias, this.DatiProtocollo.Software, this.DatiProtocollo.CodiceComune));

            var authService = new AuthenticationServiceWrapper(vert.UrlProtoDocArea, this._protocolloLogs, this._protocolloSerializer, new CredentialsInfo(vert.Username, vert.Password, vert.CodiceEnte, ""));
            var token = authService.Login();

            var adapterLeggiFasc = new LeggiFascicoloInAdapter();
            var requestLeggiFasc = adapterLeggiFasc.Adatta(fascicolo.AnnoFascicolo.GetValueOrDefault(DateTime.Now.Year).ToString(), fascicolo.NumeroFascicolo, vert.Username, fascicolo.Classifica);
            var serviceFasc = new FascicolazioneServiceWrapper(vert.UrlExtended, this._protocolloLogs, this._protocolloSerializer, new CredentialsInfo(vert.Username, vert.Password, vert.CodiceEnte, token));
            var responseFasc = serviceFasc.GetFascicoli(requestLeggiFasc);

            var adapterOut = new LeggiFascicoliOutAdapter();
            return adapterOut.Adatta(responseFasc);
        }

        public override ListaTipiClassifica GetClassifiche()
        {
            var vert = new ParametriRegoleWrapper(new VerticalizzazioneProtocolloPrisma(this.DatiProtocollo.IdComuneAlias, this.DatiProtocollo.Software, this.DatiProtocollo.CodiceComune));
            var authService = new AuthenticationServiceWrapper(vert.UrlProtoDocArea, this._protocolloLogs, this._protocolloSerializer, new CredentialsInfo(vert.Username, vert.Password, vert.CodiceEnte, ""));
            var token = authService.Login();
            var extService = new ClassificheServiceWrapper(vert.UrlExtended, base._protocolloLogs, base._protocolloSerializer, new CredentialsInfo(vert.Username, vert.Password, vert.CodiceEnte, token));

            var adapter = new ClassificheInAdapter();
            var request = adapter.Adatta(vert.CodiceEnte, vert.CodiceAoo, vert.Username);

            var response = extService.LeggiClassifiche(request);

            return new ListaTipiClassifica
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
