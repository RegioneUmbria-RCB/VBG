using VBG.Shared.Infrastructure.ServiceModel;
using SIGePro.Manager.VerticalizzazioniBase;
using VBG.Backend.Protocollo.AppLogic.Core.ItCity;
using VBG.Backend.Protocollo.AppLogic.Core.ItCity.Classificazione;
using VBG.Backend.Protocollo.AppLogic.Core.ItCity.Fascicolazione;
using VBG.Backend.Protocollo.AppLogic.Core.ItCity.LeggiProtocollo;
using VBG.Backend.Protocollo.AppLogic.Core.ItCity.Protocollazione;
using VBG.Backend.Protocollo.AppLogic.Core.ItCity.RicercaUO;
using VBG.Backend.Protocollo.AppLogic.Core.ItCity.Titolazione;
using VBG.Backend.Protocollo.AppLogic.Shared;
using VBG.Backend.Protocollo.AppLogic.Shared.Data;
using VBG.Backend.Protocollo.AppLogic.Shared.Enums;
using VBG.Backend.Protocollo.AppLogic.Shared.Factories;
using VBG.Backend.Protocollo.AppLogic.Shared.Managers;
using VBG.Backend.Protocollo.AppLogic.Shared.WsDataClass;

namespace VBG.Backend.Protocollo.AppLogic.Core
{
    public class PROTOCOLLO_ITCITY : ProtocolloBase
    {
        private readonly IVerticalizzazioniFactory _verticalizzazioniFactory;
        private readonly IBindingFactory _bindingFactory;

        public PROTOCOLLO_ITCITY(IVerticalizzazioniFactory verticalizzazioniFactory, IBindingFactory bindingFactory)
        {
            this._verticalizzazioniFactory = verticalizzazioniFactory;
            this._bindingFactory = bindingFactory;
        }

        public override DatiProtocolloResponseType Protocollazione(DatiProtocolloIn protoIn)
        {
            //risoluzione della verticalizzazione
            var par = new ParametriRegoleInfoAdapter(base.DatiProtocollo.Token, base.DatiProtocollo.IdComuneAlias, base.DatiProtocollo.Software, base.DatiProtocollo.CodiceComune, this._verticalizzazioniFactory).Adatta();

            var datiProto = DatiProtocolloInsertFactory.Create(protoIn);
            var loginInfo = new LoginWsInfo(par.Username, par.Password, base.Operatore);
            int idFascicolo = 0;
            int? numeroSottoFascicolo = null;
            string classifica = base.GetIdClassificaByCodice(datiProto.ProtoIn.Classifica).ToString();

            if (base.DatiProtocollo.TipoAmbito == AmbitoProtocollazioneEnum.DA_MOVIMENTO)
            {
                if (String.IsNullOrEmpty(base.DatiProtocollo.Istanza.NUMEROPROTOCOLLO) || !base.DatiProtocollo.Istanza.DATAPROTOCOLLO.HasValue)
                {
                    throw new Exception($"DATI DI PROTOCOLLAZIONE DELL'ISTANZA {base.DatiProtocollo.NumeroIstanza} NON VALORIZZATI");
                }

                var infoLeggi = new LeggiProtocolloRequestInfo(base.DatiProtocollo.Istanza.NUMEROPROTOCOLLO, base.DatiProtocollo.Istanza.DATAPROTOCOLLO.Value.Year.ToString(), par.Sigla);
                var serviceLeggi = new LeggiProtocolloServiceWrapper(par.Url, loginInfo, base._protocolloLogs, base._protocolloSerializer, this._bindingFactory);
                var leggiResponse = serviceLeggi.LeggiProtocollo(infoLeggi);

                if (leggiResponse.Fascicolo == null || leggiResponse.Fascicolo.Id == 0)
                {
                    throw new Exception($"IL PROTOCOLLO NUMERO {base.DatiProtocollo.Istanza.NUMEROPROTOCOLLO} ANNO {base.DatiProtocollo.Istanza.DATAPROTOCOLLO.Value.Year.ToString()} RELATIVO ALL'ISTANZA {base.DatiProtocollo.NumeroIstanza} NON HA I DATI DI FASCICOLAZIONE VALORIZZATI, E' NECESSARIO CHE IL PROTOCOLLO VENGA FASCICOLATO");
                }

                idFascicolo = leggiResponse.Fascicolo.Id;
                numeroSottoFascicolo = leggiResponse.Fascicolo.NumeroSottofascicolo;
                classifica = $"{leggiResponse.Fascicolo.Titolo.Trim()}.{leggiResponse.Fascicolo.Classe}.{leggiResponse.Fascicolo.Sottoclasse}";
                classifica = base.GetIdClassificaByCodice(classifica).ToString();
            }

            var service = new ProtocollazioneServiceWrapper(par.Url, loginInfo, base._protocolloLogs, base._protocolloSerializer, this._bindingFactory);
            var adapterRequest = new ProtocollazioneRequestAdapter(par);

            var request = adapterRequest.Adatta(datiProto, this.Anagrafiche, idFascicolo, numeroSottoFascicolo, classifica);
            var info = new ProtocollazioneRequestInfo(request.Coordinate, request.MittenteInternoInfo, request.MittentiEsterniInfo, request.DestinatariInterniInfo, request.DestinatariEsterniInfo, request.Allegati);

            var response = service.Protocolla(info, request.AllegatiBuffer);

            var adapterResponse = new ProtocollazioneResponseAdapter();
            return adapterResponse.Adatta(response);

        }

        public override List<DatiProtocolloLettoResponseType> LeggiProtocollo(LeggiProtocolloRequest leggiProtocolloRequest)
        {
            //risoluzione della verticalizzazione
            var par = new ParametriRegoleInfoAdapter(base.DatiProtocollo.Token, base.DatiProtocollo.IdComuneAlias, base.DatiProtocollo.Software, base.DatiProtocollo.CodiceComune, this._verticalizzazioniFactory).Adatta();

            var loginInfo = new LoginWsInfo(par.Username, par.Password, base.Operatore);
            var service = new LeggiProtocolloServiceWrapper(par.Url, loginInfo, base._protocolloLogs, base._protocolloSerializer, this._bindingFactory);
            var info = new LeggiProtocolloRequestInfo(leggiProtocolloRequest.NumeroProtocollo, leggiProtocolloRequest.AnnoProtocollo, par.Sigla);
            var response = service.LeggiProtocollo(info);
            var adapter = new LeggiProtocolloResponseAdapter();
            return new List<DatiProtocolloLettoResponseType>() { adapter.Adatta(response) };
        }

        public override DatiFascicoloResponseType Fascicola(Fascicolo fascicolo)
        {
            if (String.IsNullOrEmpty(fascicolo.NumeroFascicolo) && this.DatiProtocollo.TipoAmbito != AmbitoProtocollazioneEnum.DA_ISTANZA)
            {
                base._protocolloLogs.InfoFormat("NON E' POSSIBILE CREARE UN FASCICOLO DA UN AMBITO DIVERSO DALL'ISTANZA");
                return new DatiFascicoloResponseType();
            }

            //risoluzione della verticalizzazione
            var par = new ParametriRegoleInfoAdapter(base.DatiProtocollo.Token, base.DatiProtocollo.IdComuneAlias, base.DatiProtocollo.Software, base.DatiProtocollo.CodiceComune, this._verticalizzazioniFactory).Adatta();

            var loginInfo = new LoginWsInfo(par.Username, par.Password, base.Operatore);
            var service = new FascicolazioneServiceWrapper(par.Url, loginInfo, base._protocolloLogs, base._protocolloSerializer, this._bindingFactory);



            var serviceLeggi = new LeggiProtocolloServiceWrapper(par.Url, loginInfo, base._protocolloLogs, base._protocolloSerializer, this._bindingFactory);
            var info = new LeggiProtocolloRequestInfo(base.NumProtocollo, base.AnnoProtocollo, par.Sigla);
            var responseLeggi = serviceLeggi.LeggiProtocollo(info);

            base._protocolloLogs.DebugFormat("Fascicolazione in ambito {0}: IdProtocollo {1}, IdDocumento {2}, ChiaveUOAssegnataria {3}", this.DatiProtocollo.TipoAmbito, base.IdProtocollo, responseLeggi.IdDocumento, responseLeggi.ChiaveUOAssegnataria);

            string idDocumento = String.IsNullOrEmpty(base.IdProtocollo) ? responseLeggi.IdDocumento : base.IdProtocollo;

            //recupero ID UO
            var serviceRicercaUO = new RicercaUOServiceWrapper(par.Url, loginInfo, base._protocolloLogs, base._protocolloSerializer, this._bindingFactory);
            var responseUO = serviceRicercaUO.CercaUOPerChiaveAletrnativa(responseLeggi.ChiaveUOAssegnataria);


            var serviceClassifiche = new ClassificheServiceWrapper(base.DatiProtocollo.Db, base.DatiProtocollo.IdComune);

            int? idFascicolo = null;
            ItCityService.Fascicolo datiFascicoloRequest = null;
            IFascicolazioneRequest fascicoloFactoryRequest = null;
            ISottoFascicolazioneRequest sottoFascicoloFactoryRequest = null;
            bool completaRegistrazione = false;


            if (String.IsNullOrEmpty(fascicolo.NumeroFascicolo) || !fascicolo.NumeroFascicolo.ToUpper().EndsWith(".X"))
            {
                base._protocolloLogs.DebugFormat("(1) In relazione al protocollo {0} - Fascicola con questi parametri: NumeroFascicolo {1}, Classifica {2}, Oggetto {3}, Anno {4}", info.Numero, fascicolo.NumeroFascicolo, fascicolo.Classifica, fascicolo.Oggetto, fascicolo.AnnoFascicolo);

                fascicoloFactoryRequest = FascicolazioneRequestFactory.Create(fascicolo.Classifica, fascicolo.NumeroFascicolo, fascicolo.AnnoFascicolo, fascicolo.Oggetto, serviceClassifiche, idDocumento);
                datiFascicoloRequest = fascicoloFactoryRequest.GetDatiFascicoloRequest(service, responseUO.UO[0].IdUnitaOperativa);
                if (this.DatiProtocollo.TipoAmbito == AmbitoProtocollazioneEnum.DA_ISTANZA)
                {
                    completaRegistrazione = true;
                }
                else
                {
                    completaRegistrazione = fascicoloFactoryRequest.CompletaRegistrazione;
                }
                idFascicolo = datiFascicoloRequest.Id;
            }
            else
            {
                base._protocolloLogs.DebugFormat("(2) In relazione al protocollo {0} - Fascicola con questi parametri: NumeroFascicolo {1}, Classifica {2}, Oggetto {3}, Anno {4}", info.Numero, fascicolo.NumeroFascicolo, fascicolo.Classifica, fascicolo.Oggetto, fascicolo.AnnoFascicolo);

                // siamo nel caso di creazione del sottofascicolo per cui devo prima recuperare l'id del fascicolo
                var tNumFascicolo = fascicolo.NumeroFascicolo.Substring(0, fascicolo.NumeroFascicolo.Length - 2);
                fascicoloFactoryRequest = FascicolazioneRequestFactory.Create(fascicolo.Classifica, tNumFascicolo, fascicolo.AnnoFascicolo, fascicolo.Oggetto, serviceClassifiche, idDocumento);
                datiFascicoloRequest = fascicoloFactoryRequest.GetDatiFascicoloRequest(service, responseUO.UO[0].IdUnitaOperativa);
                completaRegistrazione = fascicoloFactoryRequest.CompletaRegistrazione;
                idFascicolo = datiFascicoloRequest.Id;

                var tcs = fascicolo.Classifica.Split('.');
                var titoloRomano = tcs[0];
                var classe = Convert.ToInt32(tcs[1]);
                var sottoclasse = Convert.ToInt32(tcs[2]);

                //procedo con la sottofascicolazione
                sottoFascicoloFactoryRequest = SottoFascicolazioneRequestFactory.Create(fascicolo.AnnoFascicolo.Value, classe, idFascicolo.Value, Convert.ToInt32(tNumFascicolo), sottoclasse, titoloRomano, fascicolo.Oggetto, serviceClassifiche, idDocumento);
                datiFascicoloRequest = sottoFascicoloFactoryRequest.GetDatiFascicoloRequest(service, responseUO.UO[0].IdUnitaOperativa);
                idFascicolo = datiFascicoloRequest.Id;
                base._protocolloLogs.InfoFormat($"CREAZIONE DEL SOTTO-FASCICOLO PER IL FASCICOLO {tNumFascicolo}");
                base._protocolloLogs.InfoFormat($"DATI DEL SOTTO-FASCICOLO: {datiFascicoloRequest}");
                completaRegistrazione = sottoFascicoloFactoryRequest.CompletaRegistrazione;
                fascicolo.NumeroFascicolo = datiFascicoloRequest.Numero.ToString() + '.' + datiFascicoloRequest.NumeroSottofascicolo.ToString();
                base._protocolloLogs.InfoFormat($"ID DEL SOTTO-FASCICOLO: {datiFascicoloRequest.Id}");
                base._protocolloLogs.InfoFormat($"NUMERO DEL SOTTO-FASCICOLO: {fascicolo.NumeroFascicolo}");
            }


            if (completaRegistrazione)
            {
                int idDocumentoParsed;
                bool isParsableIdDocumento = Int32.TryParse(idDocumento, out idDocumentoParsed);
                if (!isParsableIdDocumento)
                {
                    throw new Exception($"IL NUMERO DEL DOCUMENTO DEVE AVERE UN VALORE NUMERICO, VALORE ATTUALE: {idDocumento}");
                }

                //recupero ID Indice
                var serviceGetCoordinateTitolazione = new CoordinateServiceWrapper(par.Url, loginInfo, base._protocolloLogs, base._protocolloSerializer, this._bindingFactory);
                var responseCoordinate = serviceGetCoordinateTitolazione.GetCoordinateTitolazione(fascicolo.Classifica);

                service.FascicolaProtocollo(idDocumentoParsed, Convert.ToInt32(idFascicolo), datiFascicoloRequest.NumeroSottofascicolo, responseCoordinate.Titolazione.IdIndice);
            }

            return new DatiFascicoloResponseType
            {
                AnnoFascicolo = datiFascicoloRequest.Anno,
                DataFascicolo = datiFascicoloRequest.DataApertura,
                NumeroFascicolo = datiFascicoloRequest.Numero.ToString() + ((datiFascicoloRequest.NumeroSottofascicolo > 0) ? "." + datiFascicoloRequest.NumeroSottofascicolo.ToString() : "")
            };
        }

        public override DatiProtocolloFascicolatoResponseType IsFascicolato(string idProtocollo, string annoProtocollo, string numeroProtocollo)
        {
            //risoluzione della verticalizzazione
            var par = new ParametriRegoleInfoAdapter(base.DatiProtocollo.Token, base.DatiProtocollo.IdComuneAlias, base.DatiProtocollo.Software, base.DatiProtocollo.CodiceComune, this._verticalizzazioniFactory).Adatta();

            var loginInfo = new LoginWsInfo(par.Username, par.Password, base.Operatore);
            var service = new LeggiProtocolloServiceWrapper(par.Url, loginInfo, base._protocolloLogs, base._protocolloSerializer, this._bindingFactory);
            var info = new LeggiProtocolloRequestInfo(numeroProtocollo, annoProtocollo, par.Sigla);
            var response = service.LeggiProtocollo(info);

            if (response.Fascicolo == null || response.Fascicolo.Numero == 0)
            {
                return new DatiProtocolloFascicolatoResponseType { Fascicolato = EnumFascicolatoType.no };
            }

            string classifica = $"{response.Fascicolo.Titolo.Trim()}.{response.Fascicolo.Classe}.{response.Fascicolo.Sottoclasse}";

            return new DatiProtocolloFascicolatoResponseType
            {
                AnnoFascicolo = response.Fascicolo.Anno,
                Classifica = classifica,
                DataFascicolo = response.Fascicolo.DataApertura,
                NumeroFascicolo = response.Fascicolo.NumeroSottofascicolo != 0 ? $"{response.Fascicolo.Numero.ToString()}.{response.Fascicolo.NumeroSottofascicolo.ToString()}" : response.Fascicolo.Numero.ToString(),
                Oggetto = response.Fascicolo.Oggetto,
                Fascicolato = EnumFascicolatoType.si
            };
        }

        public override ListaTipiClassificaType GetClassifiche()
        {
            var mgr = new ProtocolloClassificheMgr(this.DatiProtocollo.Db);
            var list = mgr.GetBySoftwareCodiceComune(this.DatiProtocollo.IdComune, this.DatiProtocollo.Software, this.DatiProtocollo.CodiceComune);

            var titolario = list.Select(x => new ListaTipiClassificaClassifica
            {
                // Codice = x.Id.Value.ToString(),
                Codice = x.Codice,
                Descrizione = x.Descrizione,
                Ordinamento = String.IsNullOrEmpty(x.Ordinamento) ? 0 : Convert.ToInt32(x.Ordinamento)
            }).OrderBy(x => x.Ordinamento);

            if (titolario.Count() == 0)
            {
                return new ListaTipiClassificaType();
            }

            return new ListaTipiClassificaType { Classifica = titolario.ToArray() };

        }

        public override ListaFascicoliResponseType GetFascicoli(Fascicolo fascicolo)
        {
            //risoluzione della verticalizzazione
            var par = new ParametriRegoleInfoAdapter(base.DatiProtocollo.Token, base.DatiProtocollo.IdComuneAlias, base.DatiProtocollo.Software, base.DatiProtocollo.CodiceComune, this._verticalizzazioniFactory).Adatta();

            var loginInfo = new LoginWsInfo(par.Username, par.Password, base.Operatore);

            var service = new FascicolazioneServiceWrapper(par.Url, loginInfo, base._protocolloLogs, base._protocolloSerializer, this._bindingFactory);

            int? numeroFascicolo = String.IsNullOrEmpty(fascicolo.NumeroFascicolo) ? (int?)null : Convert.ToInt32(fascicolo.NumeroFascicolo);
            var info = new CercaFascicoliInfo(fascicolo.Classifica, fascicolo.AnnoFascicolo, fascicolo.Oggetto, numeroFascicolo);

            var response = service.CercaFascicoli(info);

            var listFascicoli = response.Select(x => new DatiFascType
            {
                AnnoFascicolo = x.Anno,
                ClassificaFascicolo = $"{x.Titolo}.{x.Classe}.{x.Sottoclasse}",
                DataFascicolo = x.DataApertura,
                NumeroFascicolo = x.NumeroSottofascicolo != 0 ? $"{x.Numero.ToString()}.{x.NumeroSottofascicolo.ToString()}" : x.Numero.ToString(),
                OggettoFascicolo = x.Oggetto
            });

            return new ListaFascicoliResponseType { Fascicolo = listFascicoli.ToArray() };
        }
    }
}
