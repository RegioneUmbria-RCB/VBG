using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using Init.SIGePro.Data;
using Init.SIGePro.Authentication;
using Init.SIGePro.Manager;
using ProtocolloSigeproNLA.Properties;
using PersonalLib2.Data;
using Init.Utils;
using ProtocolloSigeproNLA.STCService;

namespace ProtocolloSigeproNLA.Classes
{
    public class LogicaInvioIstanza
    {

        string Token = String.Empty;
        string IdComune = String.Empty;
        string Software = String.Empty;
        DataBase Db = null;

        public int? CodiceAnagrafe { get; set; }
        public int? IdProtocollo { get; set; }
        public string NumeroProtocollo { get; set; }
        public DateTime? DataProtocollo { get; set; }
        public int? Intervento { get; set; }
        public int? TipoProcedura { get; set; }
        public string TipoMovimentoAvvio { get; set; }
        public int? CodiceStradario { get; set; }
        public string Civico { get; set; }
        public string Colore { get; set; }
        public int? InQualitaDi { get; set; }
        public int? PerContoDi { get; set; }
        public string Esponente { get; set; }
        public string Piano { get; set; }
        public string CodiceComune { get; set; }

        public LogicaInvioIstanza(string token, string idComune, string software)
        {
            Token = token;
            IdComune = idComune;
            Software = software;
        }

        public void InviaIstanza()
        {
            if (!CodiceAnagrafe.HasValue) throw new ArgumentException("Mittente non valorizzato");
            if (!IdProtocollo.HasValue) throw new ArgumentException("Id Protocollo non valorizzato");
            if (String.IsNullOrEmpty(NumeroProtocollo)) throw new ArgumentException("Numero Protocollo non valorizzato");
            if (!DataProtocollo.HasValue) throw new ArgumentException("Data Protocollo non valorizzata");

            var authInfo = AuthenticationManager.CheckToken(Token);

            Db = authInfo.CreateDatabase();

            var anagraficaRichiedente = new AnagrafeMgr(Db).GetById(IdComune, CodiceAnagrafe.Value);

            var dettaglioPratica = new DettaglioPraticaType();

            dettaglioPratica.richiedente = new RichiedenteType
            {
                anagrafica = AdattaPersonaFisica(anagraficaRichiedente),
                ruolo = AdattaRuolo()
            };

            if (PerContoDi.HasValue)
            {
                var anagraficaPerCondoDi = new AnagrafeMgr(Db).GetById(IdComune, PerContoDi.Value);
                
                if (anagraficaPerCondoDi.TIPOANAGRAFE == "G")
                    dettaglioPratica.aziendaRichiedente = AdattaPersonaGiuridica(anagraficaPerCondoDi);
            }

            dettaglioPratica.dataProtocolloGeneraleSpecified = true;
            dettaglioPratica.dataProtocolloGenerale = DataProtocollo.Value;
            dettaglioPratica.numeroProtocolloGenerale = NumeroProtocollo;
            dettaglioPratica.dataPratica = DataProtocollo.Value;
            dettaglioPratica.idPratica = Settings.Default.PREFISSO_IDPRATICA + IdComune + IdProtocollo;
            dettaglioPratica.numeroPratica = dettaglioPratica.idPratica;
            dettaglioPratica.oggetto = AdattaProtocollo().Pg_Oggetto;

            if (!String.IsNullOrEmpty(CodiceComune))
                dettaglioPratica.codiceComune = new ComuneType { Item = CodiceComune };

            var imgr = new InterventiMgr(Db);

            dettaglioPratica.intervento = AdattaIntervento();

            var smgr = new StradarioMgr(Db);

            dettaglioPratica.localizzazione = null;

            if (CodiceStradario.HasValue)
            {
                var stradario = smgr.GetById(IdComune, CodiceStradario.Value);
                if (stradario != null)
                {
                    dettaglioPratica.localizzazione = new LocalizzazioneNelComuneType[]
                {
                     new LocalizzazioneNelComuneType
                     {
                        piano = Piano,
                        esponente = Esponente,
                        colore = Colore,
                        civico = Civico,
                        denominazione = stradario.DESCRIZIONE,
                        id = CodiceStradario.Value.ToString(),
                        codiceViario = String.IsNullOrEmpty(stradario.CODVIARIO) ? stradario.CODICESTRADARIO : stradario.CODVIARIO
                     }
                };
                }
            }

            using (var ws = new STCService.StcClient())
            {
                var stcUsername = Settings.Default.USERNAME;
                var stcPassword = Settings.Default.PASSWORD;
                
                var tokenResponse = ws.Login(new LoginRequest { username = stcUsername, password = stcPassword });

                var nuovaIstanzaRequest = new InserimentoPraticaRequest
                {
                    token = tokenResponse.token,
                    sportelloMittente = new SportelloType
                    {
                        idEnte = IdComune,
                        idSportello = Software,
                        idNodo = Settings.Default.IDNODO_MITTENTE,
                        pecSportello = String.Empty
                    },

                    sportelloDestinatario = new SportelloType
                    {
                        idEnte = IdComune,
                        idSportello = Software,
                        idNodo = Settings.Default.IDNODO_DESTINATARIO,
                        pecSportello = String.Empty
                    },

                    dettaglioPratica = dettaglioPratica
                };

                string s = StreamUtils.SerializeClass(nuovaIstanzaRequest);

                var result = ws.InserimentoPratica(nuovaIstanzaRequest);

                if (result.Items != null && result.Items.Length > 0)
                {
                    if (result.Items[0] is ErroreType)
                    {
                        throw new ArgumentException("Errore restituito da webservice InserimentoPratica:\r\n" + result.Items[0].ToString());
                    }
                }
            }
        }

        private ProtGenerale AdattaProtocollo()
        {
            var prot = new ProtGeneraleMgr(Db).GetById(IdProtocollo.Value, IdComune);
            if (prot == null) throw new ArgumentException("I dati relativi al protocollo non sono stati trovati:\r\nId Protocollo " + IdProtocollo.Value + "\r\nIdComune: " + IdComune);

            return prot;
        }

        private InterventoType AdattaIntervento()
        {
            var i = new AlberoProcMgr(Db).GetById(Intervento.Value, IdComune);

            if (i == null) throw new ArgumentException("I dati relativi all'intervento non sono stati trovati:\r\nIntervento: " + Intervento.Value + "\r\nIdComune: " + IdComune);

            return new InterventoType
            {
                codice = i.Sc_id.Value.ToString(),
                descrizione = i.SC_DESCRIZIONE
            };
        }

        private RuoloType AdattaRuolo()
        {
            if (!InQualitaDi.HasValue) return null;

            var ts = new TipiSoggettoMgr(Db).GetById(InQualitaDi.Value.ToString(), IdComune);

            return new RuoloType { ruolo = ts.TIPOSOGGETTO, idRuolo = InQualitaDi.Value.ToString() };

        }

        /// <summary>
        /// Adatta un'anagrafica dell'area riservata in un'anagrafica della domanda STC
        /// </summary>
        /// <param name="anagrafe"></param>
        /// <returns></returns>
        private AnagrafeType AdattaAnagrafica(Anagrafe anagrafe)
        {
            if (anagrafe.TIPOANAGRAFE == "F") // Persona fisica
            {
                return new AnagrafeType { Item = AdattaPersonaFisica(anagrafe) };
            }
            else
            {
                return new AnagrafeType { Item = AdattaPersonaGiuridica(anagrafe) };
            }
        }

        private PersonaFisicaType AdattaPersonaFisica(Anagrafe anagrafe)
        {
            if (anagrafe.TIPOANAGRAFE != "F")
                throw new ArgumentException("AdattaPersonaFisica: L'anagrafica con codice " + anagrafe.CODICEFISCALE + " non è una persona fisica");

            if (String.IsNullOrEmpty(anagrafe.CODICEFISCALE))
                throw new Exception("Codice Fiscale non presente");

            if (String.IsNullOrEmpty(anagrafe.NOME))
                throw new Exception("Nome non presente");

            if (String.IsNullOrEmpty(anagrafe.NOMINATIVO))
                throw new Exception("Nominativo non presente");

            var persona = new PersonaFisicaType
            {
                codiceFiscale = anagrafe.CODICEFISCALE,
                cognome = anagrafe.NOMINATIVO,
                nome = anagrafe.NOME
            };

            /*
            if (!String.IsNullOrEmpty(anagrafe.TITOLO))
                persona.titolo = anagrafe.TITOLO;

            if (!String.IsNullOrEmpty(anagrafe.INDIRIZZO))
            {
                var residenza = new LocalizzazioneType { indirizzo = anagrafe.INDIRIZZO };

                if (!String.IsNullOrEmpty(anagrafe.CAP))
                    residenza.cap = anagrafe.CAP;

                if (!String.IsNullOrEmpty(anagrafe.COMUNERESIDENZA))
                    residenza.comune = AdattaComuneDaCodiceBelfiore(anagrafe.COMUNERESIDENZA);

                if (!String.IsNullOrEmpty(anagrafe.CITTA))
                    residenza.localita = anagrafe.CITTA;

                if (!String.IsNullOrEmpty(anagrafe.PROVINCIA))
                    residenza.provincia = anagrafe.PROVINCIA;
            }                

            if (!String.IsNullOrEmpty(anagrafe.CODCOMNASCITA))
                persona.comuneNascita = AdattaComuneDaCodiceBelfiore(anagrafe.CODCOMNASCITA);

            if (anagrafe.DATANASCITA.HasValue)
                persona.dataNascita = anagrafe.DATANASCITA.Value;
            */

            return persona;

        }

        private PersonaGiuridicaType AdattaPersonaGiuridica(Anagrafe anagrafe)
        {

            string partitaIva = String.IsNullOrEmpty(anagrafe.PARTITAIVA) ? anagrafe.CODICEFISCALE : anagrafe.PARTITAIVA;

            if (anagrafe.TIPOANAGRAFE != "G")
                throw new ArgumentException("AdattaPersonaGiuridica: L'anagrafica con codice " + anagrafe.CODICEANAGRAFE + " non è una persona giuridica");

            if (String.IsNullOrEmpty(anagrafe.NOMINATIVO))
                throw new Exception("Ragione Sociale (Nominativo) non presente");

            if (String.IsNullOrEmpty(partitaIva))
                throw new Exception("La persona giuridica " + anagrafe.NOMINATIVO + " non ha partita iva ne codice fiscale");

            if(partitaIva.Length != 11)
                throw new Exception("La persona giuridica " + anagrafe.NOMINATIVO + " ha una partita iva non valida: " + partitaIva);

            var azienda = new PersonaGiuridicaType
            {
                partitaIva = partitaIva,
                ragioneSociale = anagrafe.NOMINATIVO
            };

            if (!String.IsNullOrEmpty(anagrafe.CODICEFISCALE))
                azienda.codiceFiscale = anagrafe.CODICEFISCALE;

            /*
            var azienda = new PersonaGiuridicaType
            {
                naturaGiuridica = anagrafe.FORMAGIURIDICA,
                partitaIva = anagrafe.PARTITAIVA,
                ragioneSociale = anagrafe.NOMINATIVO
            };

            if (!String.IsNullOrEmpty(anagrafe.FAX))
                azienda.fax = anagrafe.FAX;

            if (!String.IsNullOrEmpty(anagrafe.CODICEFISCALE))
                azienda.codiceFiscale = anagrafe.CODICEFISCALE;

            if (!String.IsNullOrEmpty(anagrafe.TELEFONO))
                azienda.telefono = anagrafe.TELEFONO;

            if(!String.IsNullOrEmpty(anagrafe.INDIRIZZO))
            {
                var sedeLegale = new LocalizzazioneType { indirizzo = anagrafe.INDIRIZZO };

                 if(!String.IsNullOrEmpty(anagrafe.CAP)) 
                    sedeLegale.cap = anagrafe.CAP;

                 if(!String.IsNullOrEmpty(anagrafe.COMUNERESIDENZA)) 
                    sedeLegale.comune = AdattaComuneDaCodiceBelfiore(anagrafe.COMUNERESIDENZA);

                 if (!String.IsNullOrEmpty(anagrafe.CITTA))
                     sedeLegale.localita = anagrafe.CITTA;

                 if (!String.IsNullOrEmpty(anagrafe.PROVINCIA))
                     sedeLegale.provincia = anagrafe.PROVINCIA;

            }

            if(!String.IsNullOrEmpty(anagrafe.INDIRIZZOCORRISPONDENZA))
            {
                var indirizzoCorrispondenza = new LocalizzazioneType { indirizzo = anagrafe.INDIRIZZOCORRISPONDENZA };

                if(!String.IsNullOrEmpty(anagrafe.CAPCORRISPONDENZA)) 
                    indirizzoCorrispondenza.cap = anagrafe.CAPCORRISPONDENZA;
                
                if(!String.IsNullOrEmpty(anagrafe.COMUNECORRISPONDENZA)) 
                    indirizzoCorrispondenza.comune = AdattaComuneDaCodiceBelfiore(anagrafe.COMUNECORRISPONDENZA);

                if(!String.IsNullOrEmpty(anagrafe.CITTACORRISPONDENZA)) 
                    indirizzoCorrispondenza.localita = anagrafe.CITTACORRISPONDENZA;

                if(!String.IsNullOrEmpty(anagrafe.PROVINCIACORRISPONDENZA)) 
                    indirizzoCorrispondenza.provincia = anagrafe.PROVINCIACORRISPONDENZA;

                azienda.indirizzoCorrispondenza = indirizzoCorrispondenza;
            }

            if (!String.IsNullOrEmpty(anagrafe.CODCOMREGDITTE) || !String.IsNullOrEmpty(anagrafe.REGDITTE) || (anagrafe.DATAREGDITTE.HasValue && anagrafe.DATAREGDITTE != DateTime.MinValue))
            {
                azienda.iscrizioneCCIAA = new IscrizioneRegistroType();

                if (!String.IsNullOrEmpty(anagrafe.CODCOMREGDITTE))
                    azienda.iscrizioneCCIAA.comune = AdattaComuneDaCodiceBelfiore(anagrafe.CODCOMREGDITTE);

                if (!String.IsNullOrEmpty(anagrafe.REGDITTE))
                    azienda.iscrizioneCCIAA.numero = anagrafe.REGDITTE;

                if (anagrafe.DATAREGDITTE.HasValue && anagrafe.DATAREGDITTE != DateTime.MinValue)
                    azienda.iscrizioneCCIAA.data = anagrafe.DATAREGDITTE.Value;
            }

            if (!String.IsNullOrEmpty(anagrafe.PROVINCIAREA) &&
                !String.IsNullOrEmpty(anagrafe.NUMISCRREA) &&
                anagrafe.DATAISCRREA.HasValue && anagrafe.DATAISCRREA != DateTime.MinValue)
            {
                azienda.iscrizioneREA = new RegistroREAType
                {
                    data = anagrafe.DATAISCRREA.Value,
                    numero = anagrafe.NUMISCRREA,
                    siglaProvincia = anagrafe.PROVINCIAREA
                };
            }
            */
            return azienda;
        }

        /// <summary>
        /// Crea un oggetto ComuneType a partire dal codice belfiore del comune
        /// </summary>
        /// <param name="codiceBelfiore"></param>
        /// <returns></returns>
        private ComuneType AdattaComuneDaCodiceBelfiore(string codiceBelfiore)
        {
            ComuneType retVal = null;
            if (!String.IsNullOrEmpty(codiceBelfiore))
            {
                retVal = new ComuneType
                {
                    Item = String.IsNullOrEmpty(codiceBelfiore) ? null : codiceBelfiore,
                    ItemElementName = ItemChoiceType.codiceCatastale
                };
            }
            return retVal;
        }

    }
}
