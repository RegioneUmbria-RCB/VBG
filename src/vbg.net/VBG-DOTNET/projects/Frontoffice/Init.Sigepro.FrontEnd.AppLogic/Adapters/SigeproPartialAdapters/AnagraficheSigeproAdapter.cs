using Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Vbg.TraduzioneIdComune;
using Init.Sigepro.FrontEnd.AppLogic.GestioneComuni;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneAnagrafiche;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneAnagrafiche.LogicaRisoluzioneSoggetti;
using Init.Sigepro.FrontEnd.AppLogic.GestioneTabelleDiBase;
using Init.Sigepro.FrontEnd.AppLogic.WebServiceReferences.IstanzeService;
using System;
using System.Collections.Generic;
using System.Linq;

namespace Init.Sigepro.FrontEnd.AppLogic.Adapters.SigeproPartialAdapters
{
    public class AnagraficheSigeproAdapter : IIstanzaSigeproPartialAdapter
    {
        private readonly IComuniService _comuniService;
        private readonly IAliasToIdComuneTranslator _aliasToIdComuneTranslator;
        private readonly ILogicaRisoluzioneTecnico _logicaRisoluzioneTecnico;
        private readonly ICittadinanzeService _cittadinanzeService;
        private readonly IFormeGiuridicheRepository _formeGiuridicheService;
        private string _idComuneTradotto = "";

        public AnagraficheSigeproAdapter(IComuniService comuniService, ICittadinanzeService cittadinanzeService, IFormeGiuridicheRepository formeGiuridicheService,
            IAliasToIdComuneTranslator aliasToIdComuneTranslator, ILogicaRisoluzioneTecnico logicaRisoluzioneTecnico)
        {
            this._comuniService = comuniService;
            this._cittadinanzeService = cittadinanzeService;
            this._formeGiuridicheService = formeGiuridicheService;
            this._aliasToIdComuneTranslator = aliasToIdComuneTranslator;
            this._logicaRisoluzioneTecnico = logicaRisoluzioneTecnico;
        }

        public void Adatta(IDomandaOnlineReadInterface src, Istanze dst, IstanzaSigeproAdapterFlags flags)
        {
            this._idComuneTradotto = this._aliasToIdComuneTranslator.Translate(src.AltriDati.AliasComune);

            dst.Richiedente = this.AdattaAnagrafe(src.Anagrafiche.GetRichiedente());
            dst.AziendaRichiedente = this.AdattaAnagrafe(src.Anagrafiche.GetAzienda());
            dst.Professionista = this.AdattaAnagrafe(src.Anagrafiche.GetTecnico(this._logicaRisoluzioneTecnico));
            dst.DOMICILIO_ELETTRONICO = src.AltriDati.DomicilioElettronico;
            dst.Natura = src.AltriDati.NaturaBase;

            this.FillAnagrafiche(src, dst);
        }

        private void FillAnagrafiche(IDomandaOnlineReadInterface src, Istanze istanza)
        {

            var istRichiedenti = new List<IstanzeRichiedenti>();

            foreach (var row in src.Anagrafiche.Anagrafiche)
            {
                var ric = new IstanzeRichiedenti
                {
                    Richiedente = this.AdattaAnagrafe(row),
                    CODICETIPOSOGGETTO = row.TipoSoggetto.Id.ToString(),
                    DESCRSOGGETTO = String.IsNullOrEmpty(row.TipoSoggetto.DescrizioneEstesa) ? row.TipoSoggetto.Descrizione : row.TipoSoggetto.DescrizioneEstesa,

                    TipoSoggetto = new TipiSoggetto
                    {
                        CODICETIPOSOGGETTO = row.TipoSoggetto.Id.ToString(),
                        TIPOSOGGETTO = row.TipoSoggetto.Descrizione,
                        TIPODATO = row.TipoSoggetto.RuoloAsCodiceBackoffice()
                    }
                };


                if (row.IdAnagraficaCollegata.HasValue)
                {
                    var anagrafeCollegata = src.Anagrafiche.GetById(row.IdAnagraficaCollegata.Value);

                    ric.AnagrafeCollegata = this.AdattaAnagrafe(anagrafeCollegata);
                }

                var codAventeProcura = String.Empty;

                if (ric.Richiedente?.TIPOANAGRAFE == "F")
                {
                    codAventeProcura = src.Procure.GetCodiceFiscaleDelProcuratoreDi(row.Codicefiscale);
                }

                if (!String.IsNullOrEmpty(codAventeProcura))
                {
                    var procuratore = src.Anagrafiche.FindByRiferimentiSoggetto(GestionePresentazioneDomanda.GestioneAnagrafiche.TipoPersonaEnum.Fisica, codAventeProcura);

                    ric.Procuratore = this.AdattaAnagrafe(procuratore);

                    var procura = src.Procure
                                    .Procure
                                    .Where(x => x.Procuratore != null && x.Procuratore.CodiceFiscale == codAventeProcura && x.Procurato.CodiceFiscale == row.Codicefiscale)
                                    .FirstOrDefault();

                    if (procura != null && procura.Allegato != null)
                    {
                        ric.CodiceoggettoProcura = procura.Allegato.CodiceOggetto;
                        ric.OggettoProcura = new IstanzeRichiedentiOggetto
                        {
                            IDCOMUNE = this._idComuneTradotto,
                            CODICEOGGETTO = procura.Allegato.CodiceOggetto.ToString(),
                            NOMEFILE = procura.Allegato.NomeFile
                        };
                    }

                }

                istRichiedenti.Add(ric);
            }

            istanza.Richiedenti = istRichiedenti.ToArray();
        }


        private Anagrafe? AdattaAnagrafe(AnagraficaDomanda? row)
        {
            if (row is null) return null;

            var ret = new Anagrafe
            {
                IDCOMUNE = this._idComuneTradotto,

                NOME = row.Nome,
                NOMINATIVO = row.Nominativo,
                NOTE = row.Note,
                DATANASCITA = row.DatiNascita.Data,
                DATANOMINATIVO = row.DataCostituzione,
                SESSO = row.Sesso == SessoEnum.Maschio ? "M" : "F",
                TIPOANAGRAFE = row.TipoPersona == TipoPersonaEnum.Fisica ? "F" : "G",
                TITOLO = row.IdTitolo.ToString(),

                // Residenza
                CAP = row.IndirizzoResidenza.Cap,
                CITTA = row.IndirizzoResidenza.Citta,
                COMUNERESIDENZA = row.IndirizzoResidenza.CodiceComune,
                INDIRIZZO = row.IndirizzoResidenza.Via,
                PROVINCIA = String.Empty
            };

            var comuneResidenza = this._comuniService.GetByCodiceComune(ret.COMUNERESIDENZA);

            if (comuneResidenza != null)
            {
                ret.PROVINCIA = comuneResidenza.SiglaProvincia;
                ret.ComuneResidenza = new Comuni
                {
                    CODICECOMUNE = ret.COMUNERESIDENZA,
                    COMUNE = comuneResidenza.Comune + " (" + comuneResidenza.SiglaProvincia + ")"
                };
            }

            // Corrispondenza
            ret.CAPCORRISPONDENZA = row.IndirizzoCorrispondenza.Cap;
            ret.CITTACORRISPONDENZA = row.IndirizzoCorrispondenza.Citta;
            ret.COMUNECORRISPONDENZA = row.IndirizzoCorrispondenza.CodiceComune;
            ret.INDIRIZZOCORRISPONDENZA = row.IndirizzoCorrispondenza.Via;
            ret.PROVINCIACORRISPONDENZA = String.Empty;

            var comuneCorrispondenza = this._comuniService.GetByCodiceComune(ret.COMUNECORRISPONDENZA);

            if (comuneCorrispondenza != null)
            {
                ret.PROVINCIACORRISPONDENZA = comuneCorrispondenza.SiglaProvincia;
                ret.ComuneCorrispondenza = new Comuni
                {
                    CODICECOMUNE = ret.COMUNECORRISPONDENZA,
                    COMUNE = comuneCorrispondenza.Comune + " (" + comuneCorrispondenza.SiglaProvincia + ")"
                };
            }


            ret.CODCOMNASCITA = row.DatiNascita.CodiceComune;

            ret.CODCOMREGTRIB = row.DatiIscrizioneRegTrib == null ? String.Empty : row.DatiIscrizioneRegTrib.CodiceComune;

            if (!String.IsNullOrEmpty(ret.CODCOMNASCITA))
            {
                var comuneNascita = this._comuniService.GetByCodiceComune(ret.CODCOMNASCITA);

                ret.ComuneNascita = new Comuni();

                if (comuneNascita != null)
                {
                    ret.ComuneNascita.CODICECOMUNE = comuneNascita.CodiceComune;
                    ret.ComuneNascita.COMUNE = comuneNascita.Comune + " (" + comuneNascita.SiglaProvincia + ")";
                }
            }

            if (row.IdCittadinanza.HasValue)
            {
                var datiCittadinanza = this._cittadinanzeService.GetCittadinanzaDaId(row.IdCittadinanza.Value);

                if (datiCittadinanza != null)
                {
                    ret.Cittadinanza = new Cittadinanza
                    {
                        Codice = datiCittadinanza.Codice,
                        Descrizione = datiCittadinanza.Descrizione,
                        FlgPaeseComunitario = datiCittadinanza.FlgPaeseComunitario ? 1 : 0
                    };
                    ret.CODICECITTADINANZA = row.IdCittadinanza.ToString();

                }
            }

            ret.CODICEFISCALE = row.Codicefiscale;
            ret.PARTITAIVA = row.PartitaIva;

            // Iscrizione rea
            ret.DATAISCRREA = null;
            ret.NUMISCRREA = String.Empty;
            ret.PROVINCIAREA = String.Empty;

            if (row.DatiIscrizioneRea != null)
            {
                ret.DATAISCRREA = row.DatiIscrizioneRea.Data;
                ret.NUMISCRREA = row.DatiIscrizioneRea.Numero;
                ret.PROVINCIAREA = row.DatiIscrizioneRea.SiglaProvincia;
            }

            // Iscrizione reg trib
            ret.DATAREGTRIB = null;
            ret.REGTRIB = String.Empty;

            if (row.DatiIscrizioneRegTrib != null)
            {
                ret.DATAREGTRIB = row.DatiIscrizioneRegTrib.Data;
                ret.REGTRIB = row.DatiIscrizioneRegTrib.Numero;
            }

            // Iscrizione CCIAA
            ret.CODCOMREGDITTE = String.Empty;
            ret.DATAREGDITTE = null;
            ret.REGDITTE = String.Empty;

            if (row.DatiIscrizioneCciaa != null)
            {
                ret.CODCOMREGDITTE = row.DatiIscrizioneCciaa.CodiceComune;
                ret.DATAREGDITTE = row.DatiIscrizioneCciaa.Data;
                ret.REGDITTE = row.DatiIscrizioneCciaa.Numero;

                var comuneRegDitte = this._comuniService.GetByCodiceComune(ret.CODCOMREGDITTE);

                if (comuneRegDitte != null)
                {
                    ret.ComuneRegDitte = new Comuni
                    {
                        CODICECOMUNE = comuneRegDitte.CodiceComune,
                        COMUNE = String.Format("{0} ({1})", comuneRegDitte.Comune, comuneRegDitte.SiglaProvincia)
                    };
                }
            }



            // Contatti
            ret.EMAIL = row.Contatti.Email;
            ret.FAX = row.Contatti.Fax;
            ret.Pec = row.Contatti.Pec;
            ret.TELEFONO = row.Contatti.Telefono;
            ret.TELEFONOCELLULARE = row.Contatti.TelefonoCellulare;
            ret.Pec = row.Contatti.Pec;



            // Forma giuridica
            ret.FORMAGIURIDICA = row.IdFormagiuridica.HasValue ? row.IdFormagiuridica.ToString() : String.Empty;

            if (!String.IsNullOrEmpty(ret.FORMAGIURIDICA))
            {
                var fg = this._formeGiuridicheService.GetById(ret.FORMAGIURIDICA);

                if (fg != null)
                {
                    ret.FormaGiuridicaClass = new FormeGiuridiche
                    {
                        FORMAGIURIDICA = fg.FormaGiuridica
                    };
                }
                ;
            }

            if (row.DatiIscrizioneAlboProfessionale != null)
            {
                ret.CODICEELENCOPRO = row.DatiIscrizioneAlboProfessionale.IdAlbo.ToString();
                ret.PROVINCIAELENCOPRO = row.DatiIscrizioneAlboProfessionale.SiglaProvincia;
                ret.NUMEROELENCOPRO = row.DatiIscrizioneAlboProfessionale.Numero;

                ret.ElencoProfessionale = new ElenchiProfessionaliBase
                {
                    EpId = row.DatiIscrizioneAlboProfessionale.IdAlbo,
                    EpDescrizione = row.DatiIscrizioneAlboProfessionale.Descrizione
                };

            }

            if (row.DatiIscrizioneInps != null)
            {
                ret.InpsMatricola = row.DatiIscrizioneInps.Matricola;
                ret.InpsCodiceSede = row.DatiIscrizioneInps.CodiceSede;
                ret.SedeInps = new ElencoInpsBase
                {
                    Codice = row.DatiIscrizioneInps.CodiceSede,
                    Descrizione = row.DatiIscrizioneInps.DescrizioneSede
                };
            }

            if (row.DatiIscrizioneInail != null)
            {
                ret.InailMatricola = row.DatiIscrizioneInail.Matricola;
                ret.InailCodiceSede = row.DatiIscrizioneInail.CodiceSede;
                ret.SedeInail = new ElencoInailBase
                {
                    Codice = row.DatiIscrizioneInail.CodiceSede,
                    Descrizione = row.DatiIscrizioneInail.DescrizioneSede
                };
            }


            return ret;
        }

    }
}
