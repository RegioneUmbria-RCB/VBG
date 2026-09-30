using Init.Sigepro.FrontEnd.AppLogic.GestioneAnagrafiche;
using Init.Sigepro.FrontEnd.AppLogic.GestioneComuni;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneAnagrafiche;
using Init.Sigepro.FrontEnd.AppLogic.ObjectSpace.PresentazioneIstanza;
using log4net;
using System;
using System.Data;
using System.Text.RegularExpressions;
using VBG.Frontend.AppLogic.WsAnagraficheService;

namespace Init.Sigepro.FrontEnd.AppLogic.Adapters
{
    /// <summary>
    /// Classe che permette di convertire una classe Anagrafe in vari formati
    /// </summary>
    public class AnagrafeAdapter
    {
        private readonly ILog _log = LogManager.GetLogger(typeof(AnagrafeAdapter));

        private readonly IComuniService _comuniService;
        private readonly Anagrafe _anagraficaAreaRiservata;

        public AnagrafeAdapter(Anagrafe anagraficaAreaRiservata, IComuniService comuniService)
        {
            this._anagraficaAreaRiservata = anagraficaAreaRiservata;
            this._comuniService = comuniService;
        }

        /// <summary>
        /// Adatta l'anagrafica nel formato utilizzato dal dataset della presentazione istanze
        /// </summary>
        /// <returns>riga popolata</returns>
        public PresentazioneIstanzaDbV2.ANAGRAFERow? ToAnagrafeRow()
        {
            var anagrafeTable = new PresentazioneIstanzaDbV2.ANAGRAFEDataTable();

            var newRow = (PresentazioneIstanzaDbV2.ANAGRAFERow)anagrafeTable.NewRow();

            newRow.ANAGRAFE_PK = -1;

            if (this._anagraficaAreaRiservata == null)
                return null;

            var anagrafeType = this._anagraficaAreaRiservata.GetType();
            foreach (DataColumn dc in newRow.Table.Columns)
            {
                var columnName = dc.ColumnName;

                var propInfo = anagrafeType.GetProperty(columnName);

                if (propInfo != null)
                {
                    var value = propInfo.GetValue(this._anagraficaAreaRiservata, null);

                    if (value == null)
                        value = DBNull.Value;
                    try
                    {
                        if (columnName == "FORMAGIURIDICA" && value == null || value.ToString() == "")
                        {
                            continue;
                        }
                        newRow[columnName] = value;
                    }
                    catch (Exception ex)
                    {
                        this._log.WarnFormat("Impossibile adattare la proprietà {0} (tipo: {1}, tipo destinazione: {2}) dell'anagrafica con id {3} per il seguente errore: {4}",
                                        columnName, propInfo.PropertyType, anagrafeTable.Columns[columnName].DataType, this._anagraficaAreaRiservata.CODICEANAGRAFE, ex.ToString());
                    }
                }
            }
            newRow.PartitaIva = this._anagraficaAreaRiservata.PARTITAIVA;
            newRow.IdAlbo = this._anagraficaAreaRiservata.CODICEELENCOPRO;
            newRow.NumeroAlbo = this._anagraficaAreaRiservata.NUMEROELENCOPRO;
            newRow.ProvinciaAlbo = this._anagraficaAreaRiservata.PROVINCIAELENCOPRO;

            newRow.MatricolaInps = this._anagraficaAreaRiservata.InpsMatricola;
            newRow.CodSedeIscrizioneInps = this._anagraficaAreaRiservata.InpsCodiceSede;
            newRow.MatricolaInail = this._anagraficaAreaRiservata.InailMatricola;
            newRow.CodSedeIscrizioneInail = this._anagraficaAreaRiservata.InailCodiceSede;

            //DatiSigepro datiSigepro = new DatiSigepro(aliasComune);

            if (this._anagraficaAreaRiservata.TIPOANAGRAFE == "F")
            {
                // in SIGepro la provincia di nascita non è memorizzata...

                if (newRow.IsPROVINCIANASCITANull() || String.IsNullOrEmpty(newRow.PROVINCIANASCITA))
                {
                    var provincia = this._comuniService.GetProvinciaDaCodiceComune(this._anagraficaAreaRiservata.CODCOMNASCITA);

                    newRow.PROVINCIANASCITA = provincia == null ? String.Empty : provincia.SiglaProvincia;
                }
            }
            else
            {
                /*
				if (_anagraficaAreaRiservata.PARTITAIVA != null && _anagraficaAreaRiservata.PARTITAIVA.Length > 0)
				{
					newRow.CODICEFISCALE = _anagraficaAreaRiservata.PARTITAIVA;
				}
				*/
                if (newRow.IsCODPROVREGDITTENull() || newRow.CODPROVREGDITTE == String.Empty)
                {
                    var provincia = this._comuniService.GetProvinciaDaCodiceComune(this._anagraficaAreaRiservata.CODCOMREGDITTE);

                    newRow.CODPROVREGDITTE = provincia == null ? String.Empty : provincia.SiglaProvincia;
                }

                if (newRow.IsCODPROVREGTRIBNull() || newRow.CODPROVREGTRIB == String.Empty)
                {
                    var provincia = this._comuniService.GetProvinciaDaCodiceComune(this._anagraficaAreaRiservata.CODCOMREGTRIB);

                    newRow.CODPROVREGTRIB = provincia == null ? String.Empty : provincia.SiglaProvincia;
                }
            }

            return newRow;
        }

        public AnagraficaDomanda? ToAnagraficaDomanda()
        {
            var row = this.ToAnagrafeRow();

            if (row == null)
            {
                return null;
            }

            return AnagraficaDomanda.FromAnagrafeRow(row);
        }



        /// <summary>
        /// Adatta l'anagrafica dal formato utilizzato nell'area riservata al formato utilizzato dal web service di creazione anagrafiche
        /// Tratta tutte le anagrafiche come abilitate
        /// </summary>
        /// <returns>Anagrafica nel formato del web service di creazione anagrafiche</returns>
        public CreazioneAnagrafeService.AnagrafeType ToAnagrafeType()
        {
            var rVal = new CreazioneAnagrafeService.AnagrafeType();

            rVal.codiceFiscale = this._anagraficaAreaRiservata.CODICEFISCALE;
            rVal.sesso = this._anagraficaAreaRiservata.SESSO == "M" ?
                            CreazioneAnagrafeService.AnagrafeTypeSesso.M :
                            CreazioneAnagrafeService.AnagrafeTypeSesso.F;

            rVal.cognome = this._anagraficaAreaRiservata.NOMINATIVO;
            rVal.comuneNascita = AdattaComune(this._anagraficaAreaRiservata.CODCOMNASCITA);

            if (this._anagraficaAreaRiservata.DATANASCITA.HasValue)
            {
                rVal.dataNascita = this._anagraficaAreaRiservata.DATANASCITA.Value;
                rVal.dataNascitaSpecified = true;
            }

            if (!String.IsNullOrEmpty(this._anagraficaAreaRiservata.CAP) ||
                !String.IsNullOrEmpty(this._anagraficaAreaRiservata.INDIRIZZO) ||
                !String.IsNullOrEmpty(this._anagraficaAreaRiservata.COMUNERESIDENZA) ||
                !String.IsNullOrEmpty(this._anagraficaAreaRiservata.CITTA))
            {
                rVal.residenza = new Init.Sigepro.FrontEnd.AppLogic.CreazioneAnagrafeService.LocalizzazioneType
                {
                    cap = this._anagraficaAreaRiservata.CAP,
                    indirizzo = String.IsNullOrEmpty(this._anagraficaAreaRiservata.INDIRIZZO) ? String.Empty : this._anagraficaAreaRiservata.INDIRIZZO,
                    comune = AdattaComune(this._anagraficaAreaRiservata.COMUNERESIDENZA),
                    localita = this._anagraficaAreaRiservata.CITTA
                };
            }

            if (!String.IsNullOrEmpty(this._anagraficaAreaRiservata.CAPCORRISPONDENZA) ||
                !String.IsNullOrEmpty(this._anagraficaAreaRiservata.INDIRIZZOCORRISPONDENZA) ||
                !String.IsNullOrEmpty(this._anagraficaAreaRiservata.COMUNECORRISPONDENZA) ||
                !String.IsNullOrEmpty(this._anagraficaAreaRiservata.CITTACORRISPONDENZA))
            {
                rVal.corrispondenza = new Init.Sigepro.FrontEnd.AppLogic.CreazioneAnagrafeService.LocalizzazioneType
                {
                    cap = this._anagraficaAreaRiservata.CAPCORRISPONDENZA,
                    indirizzo = String.IsNullOrEmpty(this._anagraficaAreaRiservata.INDIRIZZOCORRISPONDENZA) ? String.Empty : this._anagraficaAreaRiservata.INDIRIZZOCORRISPONDENZA,
                    comune = AdattaComune(this._anagraficaAreaRiservata.COMUNECORRISPONDENZA),
                    localita = this._anagraficaAreaRiservata.CITTACORRISPONDENZA
                };
            }


            rVal.email = this._anagraficaAreaRiservata.EMAIL;
            rVal.fax = this._anagraficaAreaRiservata.FAX;
            rVal.nome = this._anagraficaAreaRiservata.NOME;
            rVal.partitaIva = this._anagraficaAreaRiservata.PARTITAIVA;
            rVal.pec = this._anagraficaAreaRiservata.Pec;
            rVal.strongAuthId = this._anagraficaAreaRiservata.Username;
            rVal.tecnico = false;
            rVal.telefono = this._anagraficaAreaRiservata.TELEFONO;
            rVal.note = this._anagraficaAreaRiservata.NOTE;
            rVal.disabilitato = false;
            rVal.dataDisabilitatoSpecified = false;
            //rVal.cellulare = _anagraficaAreaRiservata.TELEFONOCELLULARE;

            return rVal;
        }

        /// <summary>
        /// Adatta l'anagrafica dal formato utilizzato nell'area riservata al formato utilizzato per gestire i dati dell'utente
        /// </summary>
        /// <returns>Anagrafica nel formato del web service di creazione anagrafiche</returns>
        public AnagraficaUtente? ToAnagraficaUtente()
        {
            if (this._anagraficaAreaRiservata == null)
                return null;

            var rVal = new AnagraficaUtente();

            if (!String.IsNullOrEmpty(this._anagraficaAreaRiservata.CODICEANAGRAFE))
                rVal.Codiceanagrafe = Convert.ToInt32(this._anagraficaAreaRiservata.CODICEANAGRAFE);

            rVal.Idcomune = this._anagraficaAreaRiservata.IDCOMUNE;
            rVal.Nominativo = this._anagraficaAreaRiservata.NOMINATIVO;

            if (!String.IsNullOrEmpty(this._anagraficaAreaRiservata.FORMAGIURIDICA))
                rVal.Formagiuridica = Convert.ToInt32(this._anagraficaAreaRiservata.FORMAGIURIDICA);

            if (!String.IsNullOrEmpty(this._anagraficaAreaRiservata.TIPOLOGIA))
                rVal.Tipologia = Convert.ToInt32(this._anagraficaAreaRiservata.TIPOLOGIA);

            rVal.Indirizzo = this._anagraficaAreaRiservata.INDIRIZZO;
            rVal.Citta = this._anagraficaAreaRiservata.CITTA;
            rVal.Cap = this._anagraficaAreaRiservata.CAP;
            rVal.Provincia = this._anagraficaAreaRiservata.PROVINCIA;
            rVal.Telefono = this._anagraficaAreaRiservata.TELEFONO;
            rVal.Telefonocellulare = this._anagraficaAreaRiservata.TELEFONOCELLULARE;
            rVal.Fax = this._anagraficaAreaRiservata.FAX;
            rVal.Partitaiva = this._anagraficaAreaRiservata.PARTITAIVA;
            rVal.Codicefiscale = this._anagraficaAreaRiservata.CODICEFISCALE;
            rVal.Note = this._anagraficaAreaRiservata.NOTE;
            rVal.Email = this._anagraficaAreaRiservata.EMAIL;
            rVal.Regditte = this._anagraficaAreaRiservata.REGDITTE;
            rVal.Regtrib = this._anagraficaAreaRiservata.REGTRIB;
            rVal.Codcomregditte = this._anagraficaAreaRiservata.CODCOMREGDITTE;
            rVal.Codcomregtrib = this._anagraficaAreaRiservata.CODCOMREGTRIB;
            rVal.Codcomnascita = this._anagraficaAreaRiservata.CODCOMNASCITA;
            rVal.Datanascita = this._anagraficaAreaRiservata.DATANASCITA;
            rVal.Dataregditte = this._anagraficaAreaRiservata.DATAREGDITTE;
            rVal.Dataregtrib = this._anagraficaAreaRiservata.DATAREGTRIB;

            if (!String.IsNullOrEmpty(this._anagraficaAreaRiservata.INVIOEMAIL))
                rVal.Invioemail = Convert.ToInt32(this._anagraficaAreaRiservata.INVIOEMAIL);

            rVal.Sesso = this._anagraficaAreaRiservata.SESSO;
            rVal.Nome = this._anagraficaAreaRiservata.NOME;

            if (!String.IsNullOrEmpty(this._anagraficaAreaRiservata.TITOLO))
                rVal.Titolo = Convert.ToInt32(this._anagraficaAreaRiservata.TITOLO);


            rVal.Tipoanagrafe = this._anagraficaAreaRiservata.TIPOANAGRAFE;
            rVal.Datanominativo = this._anagraficaAreaRiservata.DATANOMINATIVO;

            if (!String.IsNullOrEmpty(this._anagraficaAreaRiservata.INVIOEMAILTEC))
                rVal.Invioemailtec = Convert.ToInt32(this._anagraficaAreaRiservata.INVIOEMAILTEC);



            if (!String.IsNullOrEmpty(this._anagraficaAreaRiservata.CODICECITTADINANZA))
                rVal.Codicecittadinanza = Convert.ToInt32(this._anagraficaAreaRiservata.CODICECITTADINANZA);


            rVal.Comuneresidenza = this._anagraficaAreaRiservata.COMUNERESIDENZA;
            rVal.Password = this._anagraficaAreaRiservata.PASSWORD;
            rVal.Indirizzocorrispondenza = this._anagraficaAreaRiservata.INDIRIZZOCORRISPONDENZA;
            rVal.Cittacorrispondenza = this._anagraficaAreaRiservata.CITTACORRISPONDENZA;
            rVal.Capcorrispondenza = this._anagraficaAreaRiservata.CAPCORRISPONDENZA;
            rVal.Provinciacorrispondenza = this._anagraficaAreaRiservata.PROVINCIACORRISPONDENZA;
            rVal.Comunecorrispondenza = this._anagraficaAreaRiservata.COMUNECORRISPONDENZA;
            rVal.Provinciarea = this._anagraficaAreaRiservata.PROVINCIAREA;
            rVal.Numiscrrea = this._anagraficaAreaRiservata.NUMISCRREA;
            rVal.Dataiscrrea = this._anagraficaAreaRiservata.DATAISCRREA;

            if (!String.IsNullOrEmpty(this._anagraficaAreaRiservata.FLAG_NOPROFIT))
                rVal.FlagNoprofit = Convert.ToInt32(this._anagraficaAreaRiservata.FLAG_NOPROFIT);

            if (!String.IsNullOrEmpty(this._anagraficaAreaRiservata.FLAG_DISABILITATO))
                rVal.FlagDisabilitato = Convert.ToInt32(this._anagraficaAreaRiservata.FLAG_DISABILITATO);

            rVal.DataDisabilitato = this._anagraficaAreaRiservata.DATA_DISABILITATO;

            if (!String.IsNullOrEmpty(this._anagraficaAreaRiservata.CODICEELENCOPRO))
                rVal.Codiceelencopro = Convert.ToInt32(this._anagraficaAreaRiservata.CODICEELENCOPRO);


            rVal.Numeroelencopro = this._anagraficaAreaRiservata.NUMEROELENCOPRO;
            rVal.Provinciaelencopro = this._anagraficaAreaRiservata.PROVINCIAELENCOPRO;
            rVal.UtenteTester = this._anagraficaAreaRiservata.FoUtenteTester.GetValueOrDefault(0) == 1;

            return rVal;
        }

        private static CreazioneAnagrafeService.ComuneType? AdattaComune(string codicecomune)
        {
            if (String.IsNullOrEmpty(codicecomune)) return null;

            var comune = new CreazioneAnagrafeService.ComuneType
            {
                // A seconda del tipo di registrazione che si sta effettuando (authGateway, fed, federa etc...)
                // il valore di codiceComune potrebbe essere 
                // - il codice belfiore
                // - il codice istat
                // - il nome del comune
                // le regexp servono ad identificare il tipo di dato che sta arrivando

                ItemElementName = CreazioneAnagrafeService.ItemChoiceType.comune
            };

            if (Regex.IsMatch(codicecomune, @"^\w\d{3}$"))
            {
                comune.ItemElementName = CreazioneAnagrafeService.ItemChoiceType.codiceCatastale;
            }
            else if (Regex.IsMatch(codicecomune, @"^\d{6}$"))
            {
                comune.ItemElementName = CreazioneAnagrafeService.ItemChoiceType.codiceIstat;
            }

            comune.Item = codicecomune;

            return comune;
        }
    }
}
