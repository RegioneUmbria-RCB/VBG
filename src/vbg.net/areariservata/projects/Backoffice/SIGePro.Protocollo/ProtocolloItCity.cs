using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Manager;
using Init.SIGePro.Protocollo.Data;
using Init.SIGePro.Protocollo.ItCity;
using Init.SIGePro.Protocollo.ItCity.Classificazione;
using Init.SIGePro.Protocollo.ItCity.Fascicolazione;
using Init.SIGePro.Protocollo.ItCity.LeggiProtocollo;
using Init.SIGePro.Protocollo.ItCity.Protocollazione;
using Init.SIGePro.Protocollo.ProtocolloFactories;
using Init.SIGePro.Protocollo.WsDataClass;

namespace Init.SIGePro.Protocollo
{
    public class PROTOCOLLO_ITCITY : ProtocolloBase
    {
        public override DatiProtocolloRes Protocollazione(DatiProtocolloIn protoIn)
        {
            var vert = new ParametriRegoleInfo(base._protocolloLogs, new Verticalizzazioni.VerticalizzazioneProtocolloItCity(base.DatiProtocollo.IdComuneAlias, base.DatiProtocollo.Software, base.DatiProtocollo.CodiceComune));
            var datiProto = DatiProtocolloInsertFactory.Create(protoIn);
            var loginInfo = new LoginWsInfo(vert.Username, vert.Password, base.Operatore);
            int idFascicolo = 0;
            int? numeroSottoFascicolo = null;

            if (base.DatiProtocollo.TipoAmbito == ProtocolloEnumerators.ProtocolloEnum.AmbitoProtocollazioneEnum.DA_MOVIMENTO)
            {
                if (String.IsNullOrEmpty(base.DatiProtocollo.Istanza.NUMEROPROTOCOLLO) || !base.DatiProtocollo.Istanza.DATAPROTOCOLLO.HasValue)
                {
                    throw new Exception($"DATI DI PROTOCOLLAZIONE DELL'ISTANZA {base.DatiProtocollo.NumeroIstanza} NON VALORIZZATI");
                }

                var infoLeggi = new LeggiProtocolloRequestInfo(base.DatiProtocollo.Istanza.NUMEROPROTOCOLLO, base.DatiProtocollo.Istanza.DATAPROTOCOLLO.Value.Year.ToString(), vert.Sigla);
                var serviceLeggi = new LeggiProtocolloServiceWrapper(vert.Url, loginInfo, base._protocolloLogs, base._protocolloSerializer);
                var leggiResponse = serviceLeggi.LeggiProtocollo(infoLeggi);

                if(leggiResponse.Fascicolo == null || leggiResponse.Fascicolo.Id == 0)
                {
                    throw new Exception($"IL PROTOCOLLO NUMERO {base.DatiProtocollo.Istanza.NUMEROPROTOCOLLO} ANNO {base.DatiProtocollo.Istanza.DATAPROTOCOLLO.Value.Year.ToString()} RELATIVO ALL'ISTANZA {base.DatiProtocollo.NumeroIstanza} NON HA I DATI DI FASCICOLAZIONE VALORIZZATI, E' NECESSARIO CHE IL PROTOCOLLO VENGA FASCICOLATO");
                }

                idFascicolo = leggiResponse.Fascicolo.Id;
                numeroSottoFascicolo = leggiResponse.Fascicolo.NumeroSottofascicolo;
            }

            var service = new ProtocollazioneServiceWrapper(vert.Url, loginInfo, base._protocolloLogs, base._protocolloSerializer);
            var adapterRequest = new ProtocollazioneRequestAdapter();

            var request = adapterRequest.Adatta(datiProto, this.Anagrafiche, idFascicolo, numeroSottoFascicolo, base.GetIdClassificaByCodice(datiProto.ProtoIn.Classifica).ToString());
            var info = new ProtocollazioneRequestInfo(request.Coordinate, request.MittenteInternoInfo, request.MittentiEsterniInfo, request.DestinatariInterniInfo, request.DestinatariEsterniInfo, request.Allegati);

            var response = service.Protocolla(info, request.AllegatiBuffer);

            //salvataggio temporaneo del PROT_UO dell'amministrazione
            if (protoIn.Flusso == "P") {
                SetIdUnitaOperativa(response.Protocollo.IdDocumento, protoIn.Mittenti.Amministrazione[0].PROT_UO);
            } else
            {
                SetIdUnitaOperativa(response.Protocollo.IdDocumento, protoIn.Destinatari.Amministrazione[0].PROT_UO);
            }

            var adapterResponse = new ProtocollazioneResponseAdapter();
            return adapterResponse.Adatta(response);

        }

        public override DatiProtocolloLetto LeggiProtocollo(string idProtocollo, string annoProtocollo, string numeroProtocollo)
        {
            var vert = new ParametriRegoleInfo(base._protocolloLogs, new Verticalizzazioni.VerticalizzazioneProtocolloItCity(base.DatiProtocollo.IdComuneAlias, base.DatiProtocollo.Software, base.DatiProtocollo.CodiceComune));
            var loginInfo = new LoginWsInfo(vert.Username, vert.Password, base.Operatore);
            var service = new LeggiProtocolloServiceWrapper(vert.Url, loginInfo, base._protocolloLogs, base._protocolloSerializer);
            var info = new LeggiProtocolloRequestInfo(numeroProtocollo, annoProtocollo, vert.Sigla);
            var response = service.LeggiProtocollo(info);
            var adapter = new LeggiProtocolloResponseAdapter();
            return adapter.Adatta(response);
        }

        public override DatiFascicolo Fascicola(Fascicolo fascicolo)
        {
            if (String.IsNullOrEmpty(fascicolo.NumeroFascicolo) && this.DatiProtocollo.TipoAmbito != ProtocolloEnumerators.ProtocolloEnum.AmbitoProtocollazioneEnum.DA_ISTANZA)
            {
                base._protocolloLogs.InfoFormat("NON E' POSSIBILE CREARE UN FASCICOLO DA UN AMBITO DIVERSO DALL'ISTANZA");
                return new DatiFascicolo();
            }

            var vert = new ParametriRegoleInfo(base._protocolloLogs, new Verticalizzazioni.VerticalizzazioneProtocolloItCity(base.DatiProtocollo.IdComuneAlias, base.DatiProtocollo.Software, base.DatiProtocollo.CodiceComune));
            var loginInfo = new LoginWsInfo(vert.Username, vert.Password, base.Operatore);
            var service = new FascicolazioneServiceWrapper(vert.Url, loginInfo, base._protocolloLogs, base._protocolloSerializer);

            string idDocumento = base.IdProtocollo;

            if (String.IsNullOrEmpty(base.IdProtocollo))
            {
                var serviceLeggi = new LeggiProtocolloServiceWrapper(vert.Url, loginInfo, base._protocolloLogs, base._protocolloSerializer);
                var info = new LeggiProtocolloRequestInfo(base.NumProtocollo, base.AnnoProtocollo, vert.Sigla);
                var responseLeggi = serviceLeggi.LeggiProtocollo(info);
                idDocumento = responseLeggi.IdDocumento;
            }

            var serviceClassifiche = new ClassificheServiceWrapper(base.DatiProtocollo.Db, base.DatiProtocollo.IdComune);

            int? idFascicolo = null;
            Init.SIGePro.Protocollo.ProtocolloItCityService.Fascicolo datiFascicoloRequest = null;
            IFascicolazioneRequest fascicoloFactoryRequest = null;
            ISottoFascicolazioneRequest sottoFascicoloFactoryRequest = null;
            bool completaRegistrazione = false;

            
            if( String.IsNullOrEmpty(fascicolo.NumeroFascicolo) || !fascicolo.NumeroFascicolo.ToUpper().EndsWith(".X"))
            {
                fascicoloFactoryRequest = FascicolazioneRequestFactory.Create(fascicolo.Classifica, fascicolo.NumeroFascicolo, fascicolo.AnnoFascicolo, fascicolo.Oggetto, idFascicolo, serviceClassifiche, idDocumento);
                datiFascicoloRequest = fascicoloFactoryRequest.GetDatiFascicoloRequest(service);
                if(this.DatiProtocollo.TipoAmbito == ProtocolloEnumerators.ProtocolloEnum.AmbitoProtocollazioneEnum.DA_ISTANZA)
                {
                    completaRegistrazione = true;
                } else { 
                    completaRegistrazione = fascicoloFactoryRequest.CompletaRegistrazione;
                }
                idFascicolo = datiFascicoloRequest.Id;
            } else {
                // siamo nel caso di creazione del sottofascicolo per cui devo prima recuperare l'id del fascicolo
                var tNumFascicolo = fascicolo.NumeroFascicolo.Substring(0, fascicolo.NumeroFascicolo.Length - 2);
                fascicoloFactoryRequest = FascicolazioneRequestFactory.Create(fascicolo.Classifica, tNumFascicolo, fascicolo.AnnoFascicolo, fascicolo.Oggetto, idFascicolo, serviceClassifiche, idDocumento);
                datiFascicoloRequest = fascicoloFactoryRequest.GetDatiFascicoloRequest(service);
                completaRegistrazione = fascicoloFactoryRequest.CompletaRegistrazione;
                idFascicolo = datiFascicoloRequest.Id;

                var tcs = fascicolo.Classifica.Split('.');
                var titoloRomano = tcs[0];
                var classe = Convert.ToInt32(tcs[1]);
                var sottoclasse = Convert.ToInt32(tcs[2]);

                //procedo con la sottofascicolazione
                sottoFascicoloFactoryRequest = SottoFascicolazioneRequestFactory.Create(fascicolo.AnnoFascicolo.Value, classe, idFascicolo.Value, Convert.ToInt32(tNumFascicolo), sottoclasse, titoloRomano, fascicolo.Oggetto, serviceClassifiche, idDocumento);
                datiFascicoloRequest = sottoFascicoloFactoryRequest.GetDatiFascicoloRequest(service);
                completaRegistrazione = sottoFascicoloFactoryRequest.CompletaRegistrazione;

                fascicolo.NumeroFascicolo = datiFascicoloRequest.Numero.ToString() + '.' + datiFascicoloRequest.NumeroSottofascicolo.ToString();
            }


            if (completaRegistrazione)
            {
                int idDocumentoParsed;
                bool isParsableIdDocumento = Int32.TryParse(idDocumento, out idDocumentoParsed);
                if (!isParsableIdDocumento)
                {
                    throw new Exception($"IL NUMERO DEL DOCUMENTO DEVE AVERE UN VALORE NUMERICO, VALORE ATTUALE: {idDocumento}");
                }

                service.FascicolaProtocollo(idDocumentoParsed, Convert.ToInt32(idFascicolo), datiFascicoloRequest.NumeroSottofascicolo );
            }

            return new DatiFascicolo
            {
                AnnoFascicolo = datiFascicoloRequest.Anno,
                DataFascicolo = datiFascicoloRequest.DataApertura,
                NumeroFascicolo = datiFascicoloRequest.Numero.ToString() + ( (datiFascicoloRequest.NumeroSottofascicolo > 0) ? "." + datiFascicoloRequest.NumeroSottofascicolo.ToString() : "")
            };
        }

        public override DatiProtocolloFascicolato IsFascicolato(string idProtocollo, string annoProtocollo, string numeroProtocollo)
        {
            var vert = new ParametriRegoleInfo(base._protocolloLogs, new Verticalizzazioni.VerticalizzazioneProtocolloItCity(base.DatiProtocollo.IdComuneAlias, base.DatiProtocollo.Software, base.DatiProtocollo.CodiceComune));
            var loginInfo = new LoginWsInfo(vert.Username, vert.Password, base.Operatore);
            var service = new LeggiProtocolloServiceWrapper(vert.Url, loginInfo, base._protocolloLogs, base._protocolloSerializer);
            var info = new LeggiProtocolloRequestInfo(numeroProtocollo, annoProtocollo, vert.Sigla);
            var response = service.LeggiProtocollo(info);

            if (response.Fascicolo == null || response.Fascicolo.Numero == 0)
            {
                return new DatiProtocolloFascicolato { Fascicolato = EnumFascicolato.no };
            }

            string classifica = $"{response.Fascicolo.Titolo.Trim()}.{response.Fascicolo.Classe}.{response.Fascicolo.Sottoclasse}";
            // var serviceClassifiche = new ClassificheServiceWrapper(base.DatiProtocollo.Db, base.DatiProtocollo.IdComune);
            // var classifiche = serviceClassifiche.GetClassificaByCodice(classifica);

            return new DatiProtocolloFascicolato
            {
                AnnoFascicolo = response.Fascicolo.Anno,
                Classifica = classifica,
                DataFascicolo = response.Fascicolo.DataApertura,
                NumeroFascicolo = response.Fascicolo.NumeroSottofascicolo != 0 ? $"{response.Fascicolo.Numero.ToString()}.{response.Fascicolo.NumeroSottofascicolo.ToString()}" : response.Fascicolo.Numero.ToString(),
                Oggetto = response.Fascicolo.Oggetto,
                Fascicolato = EnumFascicolato.si
            };
        }

        public override ListaTipiClassificaClassifica GetClassificaById(int idClassifica)
        {
            return base.GetClassificaById(idClassifica);

        }

        public override ListaTipiClassifica GetClassifiche()
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
                return new ListaTipiClassifica();
            }

            return new ListaTipiClassifica { Classifica = titolario.ToArray() };

        }

        public override ListaFascicoli GetFascicoli(Fascicolo fascicolo)
        {
            var vert = new ParametriRegoleInfo(base._protocolloLogs, new Verticalizzazioni.VerticalizzazioneProtocolloItCity(base.DatiProtocollo.IdComuneAlias, base.DatiProtocollo.Software, base.DatiProtocollo.CodiceComune));
            var loginInfo = new LoginWsInfo(vert.Username, vert.Password, base.Operatore);

            var service = new FascicolazioneServiceWrapper(vert.Url, loginInfo, base._protocolloLogs, base._protocolloSerializer);

            int? numeroFascicolo = String.IsNullOrEmpty(fascicolo.NumeroFascicolo) ? (int?)null : Convert.ToInt32(fascicolo.NumeroFascicolo);
            var info = new CercaFascicoliInfo(fascicolo.Classifica, fascicolo.AnnoFascicolo, fascicolo.Oggetto, numeroFascicolo);

            var response = service.CercaFascicoli(info);

            var listFascicoli = response.Select(x => new DatiFasc
            {
                AnnoFascicolo = x.Anno,
                ClassificaFascicolo = $"{x.Titolo}.{x.Classe}.{x.Sottoclasse}",
                DataFascicolo = x.DataApertura,
                NumeroFascicolo = x.NumeroSottofascicolo != 0 ? $"{x.Numero.ToString()}.{x.NumeroSottofascicolo.ToString()}" : x.Numero.ToString(),
                OggettoFascicolo = x.Oggetto
            });

            return new ListaFascicoli { Fascicolo = listFascicoli.ToArray() };
        }

        //funzione di merda che scrive il destinatario su FS per utilizzarlo come IdUnitaOperativa in fase di fascicolazione
        //tra poco sparisce perchè ce la restituiranno tramite LeggiProtocollo 
        public void SetIdUnitaOperativa(string idprotocollo, string idDestinatarioProtocollo)
        {
            if(System.Configuration.ConfigurationManager.AppSettings["APPOGGIO_ITCITY"] != null) { 
                string[] lines = { idDestinatarioProtocollo.ToString() };
                string path = System.IO.Path.Combine(System.Configuration.ConfigurationManager.AppSettings["APPOGGIO_ITCITY"].ToString(), idprotocollo);
                System.IO.File.WriteAllLines(path, lines);
            }
        }
    }
}
