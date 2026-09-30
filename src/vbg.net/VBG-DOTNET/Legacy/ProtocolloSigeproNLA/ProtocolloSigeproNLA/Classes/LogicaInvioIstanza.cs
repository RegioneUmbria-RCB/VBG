using Init.SIGePro.Authentication;
using Init.SIGePro.Data;
using Init.SIGePro.Manager;
using Init.SIGePro.Manager.Authentication;
using Init.Utils;
using PersonalLib2.Data;
using ProtocolloSigeproNLA.Properties;
using ProtocolloSigeproNLA.STCService;
using System;

namespace ProtocolloSigeproNLA.Classes
{
    public class LogicaInvioIstanza
    {
        private readonly string Token = String.Empty;
        private readonly string IdComune = String.Empty;
        private readonly string Software = String.Empty;
        private DataBase Db = null;

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
            this.Token = token;
            this.IdComune = idComune;
            this.Software = software;
        }

        public void InviaIstanza()
        {
            if (!this.CodiceAnagrafe.HasValue) throw new ArgumentException("Mittente non valorizzato");
            if (!this.IdProtocollo.HasValue) throw new ArgumentException("Id Protocollo non valorizzato");
            if (String.IsNullOrEmpty(this.NumeroProtocollo)) throw new ArgumentException("Numero Protocollo non valorizzato");
            if (!this.DataProtocollo.HasValue) throw new ArgumentException("Data Protocollo non valorizzata");

            var authMgr = new AuthenticationManager(new AuthenticationInfoRepositoryFactory());

            var authInfo = authMgr.CheckToken(this.Token);

            this.Db = authInfo.CreateDatabase();

            var anagraficaRichiedente = new AnagrafeMgr(this.Db).GetById(this.IdComune, this.CodiceAnagrafe.Value);

            var dettaglioPratica = new DettaglioPraticaType();

            dettaglioPratica.richiedente = new RichiedenteType
            {
                anagrafica = this.AdattaPersonaFisica(anagraficaRichiedente),
                ruolo = this.AdattaRuolo()
            };

            if (this.PerContoDi.HasValue)
            {
                var anagraficaPerCondoDi = new AnagrafeMgr(this.Db).GetById(this.IdComune, this.PerContoDi.Value);

                if (anagraficaPerCondoDi.TIPOANAGRAFE == "G")
                    dettaglioPratica.aziendaRichiedente = this.AdattaPersonaGiuridica(anagraficaPerCondoDi);
            }

            dettaglioPratica.dataProtocolloGeneraleSpecified = true;
            dettaglioPratica.dataProtocolloGenerale = this.DataProtocollo.Value;
            dettaglioPratica.numeroProtocolloGenerale = this.NumeroProtocollo;
            dettaglioPratica.dataPratica = this.DataProtocollo.Value;
            dettaglioPratica.idPratica = Settings.Default.PREFISSO_IDPRATICA + this.IdComune + this.IdProtocollo;
            dettaglioPratica.numeroPratica = dettaglioPratica.idPratica;
            dettaglioPratica.oggetto = this.AdattaProtocollo().Pg_Oggetto;

            if (!String.IsNullOrEmpty(this.CodiceComune))
                dettaglioPratica.codiceComune = new ComuneType { Item = this.CodiceComune };

            var imgr = new InterventiMgr(this.Db);

            dettaglioPratica.intervento = this.AdattaIntervento();

            var smgr = new StradarioMgr(this.Db);

            dettaglioPratica.localizzazione = null;

            if (this.CodiceStradario.HasValue)
            {
                var stradario = smgr.GetById(this.IdComune, this.CodiceStradario.Value);
                if (stradario != null)
                {
                    dettaglioPratica.localizzazione = new LocalizzazioneNelComuneType[]
                {
                     new LocalizzazioneNelComuneType
                     {
                        piano = this.Piano,
                        esponente = this.Esponente,
                        colore = this.Colore,
                        civico = this.Civico,
                        denominazione = stradario.DESCRIZIONE,
                        id = this.CodiceStradario.Value.ToString(),
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
                        idEnte = this.IdComune,
                        idSportello = this.Software,
                        idNodo = Settings.Default.IDNODO_MITTENTE,
                        pecSportello = String.Empty
                    },

                    sportelloDestinatario = new SportelloType
                    {
                        idEnte = this.IdComune,
                        idSportello = this.Software,
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
            var prot = new ProtGeneraleMgr(this.Db).GetById(this.IdProtocollo.Value, this.IdComune);
            if (prot == null) throw new ArgumentException("I dati relativi al protocollo non sono stati trovati:\r\nId Protocollo " + this.IdProtocollo.Value + "\r\nIdComune: " + this.IdComune);

            return prot;
        }

        private InterventoType AdattaIntervento()
        {
            var i = new AlberoProcMgr(this.Db).GetById(this.Intervento.Value, this.IdComune);

            if (i == null) throw new ArgumentException("I dati relativi all'intervento non sono stati trovati:\r\nIntervento: " + this.Intervento.Value + "\r\nIdComune: " + this.IdComune);

            return new InterventoType
            {
                codice = i.Sc_id.Value.ToString(),
                descrizione = i.SC_DESCRIZIONE
            };
        }

        private RuoloType AdattaRuolo()
        {
            if (!this.InQualitaDi.HasValue) return null;

            var ts = new TipiSoggettoMgr(this.Db, this.IdComune).GetById(this.InQualitaDi.Value);

            return new RuoloType { ruolo = ts.TIPOSOGGETTO, idRuolo = this.InQualitaDi.Value.ToString() };

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
                return new AnagrafeType { Item = this.AdattaPersonaFisica(anagrafe) };
            }
            else
            {
                return new AnagrafeType { Item = this.AdattaPersonaGiuridica(anagrafe) };
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

            if (partitaIva.Length != 11)
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
