using VBG.Backend.Protocollo.Verticalizzazioni.Core;
using System;
using System.Collections.Generic;
using System.Linq;
using VBG.Backend.Protocollo.AppLogic.Shared;
using VBG.Backend.Protocollo.AppLogic.Shared.WsDataClass;
using VBG.Backend.Protocollo.AppLogic.Shared.Data;
using VBG.Backend.Protocollo.AppLogic.Core.Urbi;
using VBG.Backend.Protocollo.AppLogic.Shared.Factories;
using VBG.Backend.Protocollo.AppLogic.Core.Urbi.Protocollazione;
using VBG.Backend.Protocollo.AppLogic.Core.Urbi.LeggiProtocollo;
using VBG.Backend.Protocollo.AppLogic.Core.Urbi.LeggiAllegato;
using VBG.Backend.Protocollo.AppLogic.Core.Urbi.Fascicolazione;
using VBG.Backend.Protocollo.AppLogic.Core.Urbi.Fascicolazione.Ricerca;
using VBG.Backend.Protocollo.AppLogic.Core.Urbi.TipiDocumento;
using VBG.Backend.Protocollo.AppLogic.Core.Urbi.Classificazione;
using SIGePro.Manager.VerticalizzazioniBase;

namespace VBG.Backend.Protocollo.AppLogic.Core
{
    public class PROTOCOLLO_URBI : ProtocolloBase
    {
        private readonly IVerticalizzazioniFactory _verticalizzazioniFactory;

        public PROTOCOLLO_URBI(IVerticalizzazioniFactory verticalizzazioniFactory)
        {
            this._verticalizzazioniFactory = verticalizzazioniFactory;
        }

        public override DatiProtocolloResponseType Protocollazione(DatiProtocolloIn protoIn)
        {
            var vert = new VerticalizzazioniWrapper(this._verticalizzazioniFactory.Create<VerticalizzazioneProtocolloUrbi>(DatiProtocollo.IdComuneAlias, DatiProtocollo.Software, DatiProtocollo.CodiceComune), _protocolloLogs);
            var datiProto = DatiProtocolloInsertFactory.Create(protoIn);
            var requestProto = ProtocollazioneFactory.Create(datiProto, vert, this.Operatore, this._protocolloLogs, this._protocolloSerializer, this.Anagrafiche, this.DatiProtocollo);
            var parameters = requestProto.GetParameters();
            var wrapper = new ProtocollazioneServiceWrapper(this._protocolloLogs, this._protocolloSerializer, vert);
            var response = wrapper.Protocolla(protoIn.RecuperaAllegati().ToList(), parameters);
            return ProtocollazioneResponseAdapter.Adatta(response, this._protocolloLogs.Warnings.WarningMessage);
        }

        public override DatiFascicoloResponseType CambiaFascicolo(Fascicolo fascicolo)
        {
            throw new Exception("Non è possibile modificare il fascicolo di un protocollo, procedere all'interno del sistema di protocollazione");
        }

        public override List<DatiProtocolloLettoResponseType> LeggiProtocollo(LeggiProtocolloRequest leggiProtocolloRequest)
        {
            var vert = new VerticalizzazioniWrapper(this._verticalizzazioniFactory.Create<VerticalizzazioneProtocolloUrbi>(DatiProtocollo.IdComuneAlias, DatiProtocollo.Software, DatiProtocollo.CodiceComune), _protocolloLogs);
            var wrapper = new LeggiProtocolloServiceWrapper(_protocolloLogs, _protocolloSerializer, vert);
            var response = wrapper.LeggiProtocollo(vert.Aoo, leggiProtocolloRequest.AnnoProtocollo, leggiProtocolloRequest.NumeroProtocollo, "AP");
            return new List<DatiProtocolloLettoResponseType>() { LeggiProtocolloResponseAdapter.Adatta(response, this.DatiProtocollo.Db) };
        }

        public override AllegatoResponseType LeggiAllegato()
        {
            var vert = new VerticalizzazioniWrapper(this._verticalizzazioniFactory.Create<VerticalizzazioneProtocolloUrbi>(DatiProtocollo.IdComuneAlias, DatiProtocollo.Software, DatiProtocollo.CodiceComune), _protocolloLogs);
            var allegatoInfo = base.LeggiAllegatoDaLeggiProtocollo();
            var wrapper = new LeggiAllegatoServiceWrapper(_protocolloLogs, _protocolloSerializer, vert);
            var allegato = new Urbi.LeggiAllegato.Allegato(IdAllegato);
            var buffer = wrapper.DownloadAllegato(allegato.Id, allegato.Versione);
            allegatoInfo.Image = buffer;
            return allegatoInfo;
        }

        public override DatiFascicoloResponseType Fascicola(Fascicolo fascicolo)
        {
            var vert = new VerticalizzazioniWrapper(this._verticalizzazioniFactory.Create<VerticalizzazioneProtocolloUrbi>(DatiProtocollo.IdComuneAlias, DatiProtocollo.Software, DatiProtocollo.CodiceComune), _protocolloLogs);
            var sezione = "";

            // 1. Se manca idProtocollo devo ricavare la sezione ( A, P, I )
            if (String.IsNullOrEmpty(this.IdProtocollo))
            {
                var protocolli = this.LeggiProtocollo(new LeggiProtocolloRequest() { IdProtocollo = this.IdProtocollo, AnnoProtocollo = this.AnnoProtocollo, NumeroProtocollo = this.NumProtocollo });
                var protocollo = protocolli.FirstOrDefault();
                if (!String.IsNullOrEmpty(protocollo.NumeroPratica) && vert.AttivaFascicolazioneContestuale)
                {
                    throw new Exception($"La fascicolazione del protocollo {this.NumProtocollo} è stata già associata al fascicolo Id {protocollo.NumeroPratica}");
                }

                sezione = protocollo.Origine;
            }

            var idFascicolo = (int?)null;

            if (!String.IsNullOrEmpty(fascicolo.NumeroFascicolo))
            {
                //2. Faccio una ricerca per vedere se è stato passato un fascicolo esistente
                var fascicoloUrbi = this.CercaFascicoli(new Fascicolo
                {
                    AnnoFascicolo = fascicolo.AnnoFascicolo,
                    Classifica = fascicolo.Classifica,
                    NumeroFascicolo = fascicolo.NumeroFascicolo
                });

                //3. Se sono presenti più fascicoli con quel riferimento, errore
                if (fascicoloUrbi?.Fascicoli?.Count() > 1)
                {
                    throw new Exception($"Impossibile fascicolare, sono stati trovati {fascicoloUrbi.Fascicoli.Count()} fascicoli con i dati passati");
                }

                idFascicolo = fascicoloUrbi?.Fascicoli?.First().Id;
            }

            var response = new FascicolazioneService(this._protocolloLogs, this._protocolloSerializer, vert.Username, vert.Password, vert.Url).Fascicola(new FascicolaRequest
            {
                AnnoProtocollo = Convert.ToInt32(this.AnnoProtocollo),
                EseguiSoloFascicolazione = true,
                GeneraFascicolo = idFascicolo.HasValue ? (bool?)null : true,
                IdAoo = vert.Aoo,
                IdFascicolo = idFascicolo,
                IdProtocollo = String.IsNullOrEmpty(this.IdProtocollo) ? (int?)null : Convert.ToInt32(this.IdProtocollo),
                NumeroProtocollo = Convert.ToInt32(this.NumProtocollo),
                OggettoFascicolo = idFascicolo.HasValue ? "" : fascicolo.Oggetto,
                TipologiaProtocollo = sezione
            });

            if (!response.Ok)
            {
                throw new Exception($"Errore durante la fascicolazione: {response.ErrorCode} - {response.Messaggio}");
            }

            var datiFascicolo = this.IsFascicolato(this.IdProtocollo, this.AnnoProtocollo, this.NumProtocollo);

            return new DatiFascicoloResponseType
            {
                AnnoFascicolo = datiFascicolo.AnnoFascicolo,
                DataFascicolo = datiFascicolo.DataFascicolo,
                Errore = datiFascicolo.Errore,
                NumeroFascicolo = datiFascicolo.NumeroFascicolo,
            };
        }

        private RicercaFascicoliResponse CercaFascicoli(Fascicolo fascicolo)
        {
            var vert = new VerticalizzazioniWrapper(this._verticalizzazioniFactory.Create<VerticalizzazioneProtocolloUrbi>(DatiProtocollo.IdComuneAlias, DatiProtocollo.Software, DatiProtocollo.CodiceComune), _protocolloLogs);

            var dataValida = DateTime.TryParse(fascicolo.DataFascicolo, out DateTime dataFascicolo);

            return new FascicolazioneService(this._protocolloLogs, this._protocolloSerializer, vert.Username, vert.Password, vert.Url).CercaFascicoli(new RicercaFascicoliRequest
            {
                AllaData = dataValida ? dataFascicolo : (DateTime?)null,
                AnnoFascicolo = fascicolo.AnnoFascicolo,
                DallaData = dataValida ? dataFascicolo : (DateTime?)null,
                EstraiProtocolli = false,
                Numero = fascicolo.NumeroFascicolo,
                Oggetto = fascicolo.Oggetto,
                RicercaFascicolo = true,
                Titolario = fascicolo.Classifica,
                EnableCDATA = "S"
            });
        }

        public override ListaFascicoliResponseType GetFascicoli(Fascicolo fascicolo)
        {
            var response = this.CercaFascicoli(fascicolo);

            if (!response.Ok)
            {
                throw new Exception(response.Messaggio);
            }

            return new ListaFascicoliResponseType
            {
                Fascicolo = response
                                .Fascicoli
                                .Select(x => new DatiFascType
                                {
                                    AnnoFascicolo = x.Anno.ToString(),
                                    ClassificaFascicolo = x.EtichettaClassificazioneEstesa,
                                    DataFascicolo = x.DataInizio.ToString("dd/MM/yyyy"),
                                    NumeroFascicolo = x.NumeroFascicolo.ToString(),
                                    OggettoFascicolo = x.Oggetto
                                })
                                .ToArray()
            };
        }
        public override ListaTipiDocumentoResponseType GetTipiDocumento()
        {
            var vert = new VerticalizzazioniWrapper(this._verticalizzazioniFactory.Create<VerticalizzazioneProtocolloUrbi>(DatiProtocollo.IdComuneAlias, DatiProtocollo.Software, DatiProtocollo.CodiceComune), _protocolloLogs);
            var wrapper = new TipiDocumentoServiceWrapper(_protocolloLogs, _protocolloSerializer, vert.Username, vert.Password, vert.Url);
            var response = wrapper.GetTipiDocumento();

            return TipiDocumentoResponseAdapter.Adatta(response);
        }

        public override ListaTipiClassificaType GetClassifiche()
        {
            var vert = new VerticalizzazioniWrapper(this._verticalizzazioniFactory.Create<VerticalizzazioneProtocolloUrbi>(DatiProtocollo.IdComuneAlias, DatiProtocollo.Software, DatiProtocollo.CodiceComune), _protocolloLogs);
            var wrapper = new ClassificazioneServiceWrapper(_protocolloLogs, _protocolloSerializer, vert.Username, vert.Password, vert.Url);
            var response = wrapper.GetTitolario(vert.Aoo);

            return new ClassificazioneResponseAdapter(_protocolloLogs).Adatta(response, vert.ReplaceTitolario);
        }

        public override DatiProtocolloFascicolatoResponseType IsFascicolato(string idProtocollo, string annoProtocollo, string numeroProtocollo)
        {
            var vert = new VerticalizzazioniWrapper(this._verticalizzazioniFactory.Create<VerticalizzazioneProtocolloUrbi>(DatiProtocollo.IdComuneAlias, DatiProtocollo.Software, DatiProtocollo.CodiceComune), _protocolloLogs);
            var wrapper = new LeggiProtocolloServiceWrapper(_protocolloLogs, _protocolloSerializer, vert);

            //if(vert.DataSwitch.HasValue && !String.IsNullOrEmpty(vert.IdFascicoloGenerico))
            //{
            //    if (DatiProtocollo.Istanza != null)
            //    {
            //        if (!DatiProtocollo.Istanza.DATAPROTOCOLLO.HasValue)
            //        {
            //            throw new Exception("ATTENZIONE NON E' STATA VALORIZZATA LA DATA DI PROTCOLLAZIONE DELL'ISTANZA, VALORIZZARE TALE INFORMAZIONE E RIPROVARE");
            //        }
            //        if (DatiProtocollo.Istanza.DATAPROTOCOLLO.Value < vert.DataSwitch)
            //        {
            //            return new DatiProtocolloFascicolato 
            //            {

            //            }
            //        }
            //    }
            //}
            var response = wrapper.LeggiProtocollo(vert.Aoo, annoProtocollo, numeroProtocollo, "AP");

            var fascicolo = response.GetFascicoli().DefaultIfEmpty(new FascicoloUrbi()).First();

            return new DatiProtocolloFascicolatoResponseType
            {
                Fascicolato = String.IsNullOrEmpty(fascicolo.Numero) ? EnumFascicolatoType.no : EnumFascicolatoType.si,
                AnnoFascicolo = fascicolo.Anno,
                Classifica = $"{fascicolo.CodiceClassificazione}",
                NumeroFascicolo = (String.IsNullOrEmpty(fascicolo.NumeroSottoFascicolo) || fascicolo.NumeroSottoFascicolo.Equals("0")) ? fascicolo.Numero : $"{fascicolo.Numero}.{fascicolo.NumeroSottoFascicolo}",
            };
        }
    }
}
