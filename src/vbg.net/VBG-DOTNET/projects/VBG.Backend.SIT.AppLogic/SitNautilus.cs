using log4net;
using PersonalLib2.Data;
using SIGePro.Manager.VerticalizzazioniBase;
using System;
using VBG.Backend.SIT.AppLogic.Data;
using VBG.Backend.SIT.AppLogic.Exceptions;
using VBG.Backend.SIT.AppLogic.Manager;
using VBG.Backend.SIT.AppLogic.ValidazioneFormale;
using VBG.Backend.SIT.Verticalizzazioni;

namespace VBG.Backend.SIT.AppLogic
{
    [SitImplementation("SIT_NAUTILUS")]
    public class SitNautilus : SitBase
    {
        private static class Constants
        {
            public const string TabellaSit = "VWS3_CT08T_IDENT_UIU";
            public const string TabellaStradario = "vwSIGeProInfoStradario";

            public const string ColonnaSezione = "CT08_Sez_Urbana";
            public const string ColonnaFoglio = "CT08_Foglio";
            public const string ColonnaParticella = "CT08_Numero";
            public const string ColonnaSubalterno = "CT08_Subalterno";
            public const string ColonnaCivico = "EncNumero";
            public const string ColonnaEsponente = "EncParte";
            public const string ColonnaCap = "ElcfCAP";
            public const string ColonnaFrazione = "ElfFrazione";
            public const string ColonnaCircoscrizione = "ElcDescCircoscrizione";
            public const string ColonnaCodiceVia = "ElvCodTopon";

            public const string TipoCatastoFabbricati = "F";

            public const string NomeOwnerTabelle = "SITarcsde.dbo";
        }

        private readonly ILog _log = LogManager.GetLogger(typeof(SitNautilus));

        public SitNautilus()
            : base(new CompositeValidazioneFormaleService(
                    new IValidazioneFormaleService[]
                    {
                        new ValidazioneFormaleTramiteCodViarioECivicoService(),
                        new ValidazioneFormaleTramiteFoglioParticellaSubService()
                    }))
        {
        }

        private string _connectionString = "";
        private string _providerName = "";
        private string _commandText = "";

        #region Utility
        public override void SetupVerticalizzazione(IVerticalizzazioniFactory verticalizzazioniFactory)
        {
            try
            {
                var verticalizzazione = verticalizzazioniFactory.Create<VerticalizzazioneSitNautilus>(this.IdComuneAlias, this.Software);

                if (!verticalizzazione.Attiva)
                    throw new Exception("La verticalizzazione SIT_NAUTILUS non è attiva.\r\n");

                this._connectionString = verticalizzazione.Connectionstring;
                this._providerName = verticalizzazione.Provider;

                var provider = (ProviderType)Enum.Parse(typeof(ProviderType), this._providerName, false);
                this.InternalDatabaseConnection = new DataBase(this._connectionString, provider);
            }
            catch (Exception ex)
            {
                throw new Exception("Errore generato durante la lettura della verticalizzazione SIT_NAUTILUS. Metodo: GetParametriFromVertSITNAUTILUS, modulo: SitNautilus. " + ex.Message + "\r\n");
            }
        }

        private string GetMessaggioErrorePerValidazione(string colonnaDb)
        {
            switch (colonnaDb)
            {
                case Constants.ColonnaSezione /*"CT08_Sez_Urbana"*/:
                    return "La sezione " + this.DataSit.Sezione + " non è valida per i dati inseriti";
                case Constants.ColonnaFoglio /*"CT08_Foglio"*/:
                    return "Il foglio " + this.DataSit.Foglio + " non è valido per i dati inseriti";
                case Constants.ColonnaParticella /*"CT08_Numero"*/:
                    return "La particella " + this.DataSit.Particella + " non è valida per i dati inseriti";
                case Constants.ColonnaSubalterno /*"CT08_Subalterno"*/:
                    return "Il subalterno " + this.DataSit.Sub + " non è valido per i dati inseriti";
                case Constants.ColonnaCivico /*"EncNumero"*/:
                    return "Il civico " + this.DataSit.Civico + " non è valido per i dati inseriti";
                case Constants.ColonnaEsponente /*"EncParte"*/:
                    return "L'esponente " + this.DataSit.Esponente + " non è valido per i dati inseriti";
                case Constants.ColonnaCap /*"ElcfCAP"*/:
                    return "Il CAP " + this.DataSit.CAP + " non è valido per i dati inseriti";
                case Constants.ColonnaFrazione /*"ElfFrazione"*/:
                    return "La frazione " + this.DataSit.Frazione + " non è valida per i dati inseriti";
                case Constants.ColonnaCircoscrizione /*"ElcDescCircoscrizione"*/:
                    return "La circoscrizione " + this.DataSit.Circoscrizione + " non è valida per i dati inseriti";
            }

            return "Errore non specificato (colonna=" + colonnaDb + ")";
        }

        private string GetMessaggioErrorePerLetturaLista(string colonnaDb)
        {
            switch (colonnaDb)
            {
                case Constants.ColonnaSezione /*"CT08_Sez_Urbana"*/:
                    return "Non è possibile ottenere la lista delle sezioni per insufficienza di dati: il catasto deve essere fornito";
                case Constants.ColonnaFoglio /*"CT08_Foglio"*/:
                    return "Non è possibile ottenere la lista dei fogli per insufficienza di dati; il catasto e la sezione devono essere forniti";
                case Constants.ColonnaParticella /*"CT08_Numero"*/:
                    return "Non è possibile ottenere la lista delle particelle per insufficienza di dati: il catasto, la sezione ed il foglio devono essere forniti";
                case Constants.ColonnaSubalterno /*"CT08_Subalterno"*/:
                    return "Non è possibile ottenere la lista dei sub per insufficienza di dati: il catasto, la sezione , il foglio e la particella devono essere forniti";
                case Constants.ColonnaCivico /*"EncNumero"*/:
                    return "Non è possibile ottenere la lista dei civici per insufficienza di dati: la via deve essere fornita";
                case Constants.ColonnaEsponente /*"EncParte"*/:
                    return "Non è possibile ottenere la lista degli esponenti per insufficienza di dati: la via ed il civico devono essere forniti";
            }

            return "Errore non specificato (colonna=" + colonnaDb + ")";
        }

        private string GetDescrizioneColonna(string colonnaDb)
        {
            switch (colonnaDb)
            {
                case Constants.ColonnaSezione /*"CT08_Sez_Urbana"*/:
                    return "Sezione";

                case Constants.ColonnaFoglio /*"CT08_Foglio"*/:
                    return "Foglio";

                case Constants.ColonnaParticella /*"CT08_Numero"*/:
                    return "Particella";

                case Constants.ColonnaSubalterno /*"CT08_Subalterno"*/:
                    return "Subalterno";
            }

            return string.Empty;
        }

        private RetSit GetElencoDaTabellaInfoStradario(string colonnaDb)
        {
            if (string.IsNullOrEmpty(this.DataSit.TipoCatasto) || (this.DataSit.TipoCatasto == Constants.TipoCatastoFabbricati /*"F"*/))
                return this.GetElenco(colonnaDb, Constants.TabellaStradario /*"vwSIGeProInfoStradario"*/);

            return new RetSit(true);
        }

        private RetSit GetElencoDaTabellaSit(string colonnaDb)
        {
            if (string.IsNullOrEmpty(this.DataSit.TipoCatasto))
                throw new CatastoException(this.GetMessaggioErrorePerLetturaLista(colonnaDb));

            if (this.DataSit.TipoCatasto == Constants.TipoCatastoFabbricati /*"F"*/)
                return this.GetElenco(colonnaDb, Constants.TabellaSit/*"VWS3_CT08T_IDENT_UIU"*/);

            throw new CatastoException("Non è possibile estrarre dalle viste informazioni su " + this.GetDescrizioneColonna(colonnaDb) + " (catasto terreno)", true);
        }

        private string GetValoreDaTabellaInfoStradario(string colonnaDb, TipoQuery eTipoQuery)
        {
            if ((colonnaDb == Constants.ColonnaCap /*"ElcfCAP"*/) || (colonnaDb == Constants.ColonnaFrazione /*"ElfFrazione"*/) || (colonnaDb == Constants.ColonnaCircoscrizione /*"ElcDescCircoscrizione"*/) || (colonnaDb == Constants.ColonnaCodiceVia /*"ElvCodTopon"*/))
                return this.GetElemento(colonnaDb, Constants.TabellaStradario /*"vwSIGeProInfoStradario"*/, eTipoQuery);

            if (string.IsNullOrEmpty(this.DataSit.TipoCatasto) || this.DataSit.TipoCatasto == Constants.TipoCatastoFabbricati /*"F"*/)
                return this.GetElemento(colonnaDb, Constants.TabellaStradario /*"vwSIGeProInfoStradario"*/, eTipoQuery);

            throw new CatastoException(this.GetMessaggioErrorePerValidazione(colonnaDb));
        }

        private string GetValoreDaTabellaSit(string sField, TipoQuery eTipoQuery)
        {
            if (string.IsNullOrEmpty(this.DataSit.TipoCatasto) || this.DataSit.TipoCatasto == Constants.TipoCatastoFabbricati)
                return this.GetElemento(sField, Constants.TabellaSit /*"VWS3_CT08T_IDENT_UIU"*/, eTipoQuery);

            throw new CatastoException("Non è possibile tramite le viste validare " + this.GetDescrizioneColonna(sField) + " (catasto terreno)", true);

        }

        private string GetQuery(string colonnaDb, string nomeTabella, TipoQuery tipoQuery)
        {
            var query = string.Empty;

            switch (nomeTabella)
            {
                case Constants.TabellaStradario /*"vwSIGeProInfoStradario"*/:
                    switch (tipoQuery)
                    {
                        case TipoQuery.Elenco:
                            query = "Select distinct " + nomeTabella + "." + colonnaDb + " from " + nomeTabella + " where " +
                                (((!string.IsNullOrEmpty(this.DataSit.CodVia)) && (colonnaDb != Constants.ColonnaCodiceVia /*"ElvCodTopon"*/)) ? this.InternalDatabaseConnection.Specifics.RTrimFunction(Constants.ColonnaCodiceVia /*"ElvCodTopon"*/) + " = " + this.RightTrim(this.DataSit.CodVia) + " and " : "") +
                                (((!string.IsNullOrEmpty(this.DataSit.Civico)) && (colonnaDb != Constants.ColonnaCivico /*"EncNumero"*/)) ? this.InternalDatabaseConnection.Specifics.RTrimFunction(Constants.ColonnaCivico /*"EncNumero"*/) + " = " + this.RightTrim(Init.Utils.StringChecker.IsNumeric(this.DataSit.Civico) ? this.DataSit.Civico : "-1") + " and " : "") +
                                (((!string.IsNullOrEmpty(this.DataSit.Esponente)) && (colonnaDb != Constants.ColonnaEsponente /*"EncParte"*/)) ? this.InternalDatabaseConnection.Specifics.RTrimFunction(Constants.ColonnaEsponente /*"EncParte"*/) + " = '" + this.RightTrim(this.DataSit.Esponente) + "' and " : "") +
                                (((!string.IsNullOrEmpty(this.DataSit.Circoscrizione)) && (colonnaDb != Constants.ColonnaCircoscrizione /*"ElcDescCircoscrizione"*/)) ? this.InternalDatabaseConnection.Specifics.RTrimFunction(Constants.ColonnaCircoscrizione /*"ElcDescCircoscrizione"*/) + " = '" + this.RightTrim(this.DataSit.Circoscrizione) + "' and " : "") +
                                (((!string.IsNullOrEmpty(this.DataSit.Frazione)) && (colonnaDb != Constants.ColonnaFrazione /*"ElfFrazione"*/)) ? this.InternalDatabaseConnection.Specifics.RTrimFunction(Constants.ColonnaFrazione /*"ElfFrazione"*/) + " = '" + this.RightTrim(this.DataSit.Frazione) + "' and " : "") +
                                (((!string.IsNullOrEmpty(this.DataSit.CAP)) && (colonnaDb != Constants.ColonnaCap /*"ElcfCAP"*/)) ? this.InternalDatabaseConnection.Specifics.RTrimFunction(Constants.ColonnaCap /*"ElcfCAP"*/) + " = '" + this.RightTrim(this.DataSit.CAP) + "' and " : "");
                            break;
                        case TipoQuery.Validazione:
                            query = "Select distinct " + nomeTabella + "." + colonnaDb + " from " + nomeTabella + " where " +
                                (((!string.IsNullOrEmpty(this.DataSit.CodVia))) ? this.InternalDatabaseConnection.Specifics.RTrimFunction(Constants.ColonnaCodiceVia /*"ElvCodTopon"*/) + " = " + this.RightTrim(this.DataSit.CodVia) + " and " : "") +
                                (((!string.IsNullOrEmpty(this.DataSit.Civico))) ? this.InternalDatabaseConnection.Specifics.RTrimFunction(Constants.ColonnaCivico /*"EncNumero"*/) + " = " + this.RightTrim(Init.Utils.StringChecker.IsNumeric(this.DataSit.Civico) ? this.DataSit.Civico : "-1") + " and " : "") +
                                (((!string.IsNullOrEmpty(this.DataSit.Esponente))) ? this.InternalDatabaseConnection.Specifics.RTrimFunction(Constants.ColonnaEsponente /*"EncParte"*/) + " = '" + this.RightTrim(this.DataSit.Esponente) + "' and " : "") +
                                (((!string.IsNullOrEmpty(this.DataSit.Circoscrizione))) ? this.InternalDatabaseConnection.Specifics.RTrimFunction(Constants.ColonnaCircoscrizione /*"ElcDescCircoscrizione"*/) + " = '" + this.RightTrim(this.DataSit.Circoscrizione) + "' and " : "") +
                                (((!string.IsNullOrEmpty(this.DataSit.Frazione))) ? this.InternalDatabaseConnection.Specifics.RTrimFunction(Constants.ColonnaFrazione /*"ElfFrazione"*/) + " = '" + this.RightTrim(this.DataSit.Frazione) + "' and " : "") +
                                (((!string.IsNullOrEmpty(this.DataSit.CAP))) ? this.InternalDatabaseConnection.Specifics.RTrimFunction(Constants.ColonnaCap /*"ElcfCAP"*/) + " = '" + this.RightTrim(this.DataSit.CAP) + "' and " : "");
                            break;
                    }
                    break;
                case Constants.TabellaSit:
                    switch (tipoQuery)
                    {
                        case TipoQuery.Elenco:
                            query = "Select distinct " + colonnaDb + " from " + this.GetCompleteTableName(nomeTabella) + " where " +
                                (((this.DataSit.Sezione != "") && (colonnaDb != Constants.ColonnaSezione /*"CT08_Sez_Urbana"*/)) ? this.InternalDatabaseConnection.Specifics.RTrimFunction(Constants.ColonnaSezione /*"CT08_Sez_Urbana"*/) + " = '" + this.RightTrim(this.DataSit.Sezione) + "' and " : "") +
                                (((this.DataSit.Foglio != "") && (colonnaDb != Constants.ColonnaFoglio /*"CT08_Foglio"*/)) ? this.InternalDatabaseConnection.Specifics.RTrimFunction(Constants.ColonnaParticella /*"CT08_Numero"*/) + " = '" + this.LeftPad(this.RightTrim(this.DataSit.Foglio), 4) + "' and " : "") +
                                (((this.DataSit.Particella != "") && (colonnaDb != Constants.ColonnaParticella /*"CT08_Numero"*/)) ? this.InternalDatabaseConnection.Specifics.RTrimFunction(Constants.ColonnaParticella /*"CT08_Numero"*/) + " = '" + this.LeftPad(this.RightTrim(this.DataSit.Particella), 5) + "' and " : "") +
                                (((this.DataSit.Sub != "") && (colonnaDb != Constants.ColonnaSubalterno /*"CT08_Subalterno"*/)) ? this.InternalDatabaseConnection.Specifics.RTrimFunction(Constants.ColonnaSubalterno /*"CT08_Subalterno"*/) + " = '" + this.LeftPad(this.RightTrim(this.DataSit.Sub), 4) + "' and " : "");
                            break;
                        case TipoQuery.Validazione:
                            query = "Select distinct " + colonnaDb + " from " + this.GetCompleteTableName(nomeTabella) + " where " +
                                (((this.DataSit.Sezione != "")) ? this.InternalDatabaseConnection.Specifics.RTrimFunction(Constants.ColonnaSezione /*"CT08_Sez_Urbana"*/) + " = '" + this.RightTrim(this.DataSit.Sezione) + "' and " : "") +
                                (((this.DataSit.Foglio != "")) ? this.InternalDatabaseConnection.Specifics.RTrimFunction(Constants.ColonnaFoglio /*"CT08_Foglio"*/) + " = '" + this.LeftPad(this.RightTrim(this.DataSit.Foglio), 4) + "' and " : "") +
                                (((this.DataSit.Particella != "")) ? this.InternalDatabaseConnection.Specifics.RTrimFunction(Constants.ColonnaParticella /*"CT08_Numero"*/) + " = '" + this.LeftPad(this.RightTrim(this.DataSit.Particella), 5) + "' and " : "") +
                                (((this.DataSit.Sub != "")) ? this.InternalDatabaseConnection.Specifics.RTrimFunction(Constants.ColonnaSubalterno /*"CT08_Subalterno"*/) + " = '" + this.LeftPad(this.RightTrim(this.DataSit.Sub), 4) + "' and " : "");
                            break;

                    }
                    break;
            }

            if (query.EndsWith("and "))
                query = query.Remove(query.Length - 4, 4) + " order by " + nomeTabella.Split(new Char[] { ',' })[0] + "." + colonnaDb;
            else if (query.EndsWith("where "))
                query = query.Remove(query.Length - 6, 6) + " order by " + nomeTabella.Split(new Char[] { ',' })[0] + "." + colonnaDb;
            else
                query = query + " order by " + nomeTabella.Split(new Char[] { ',' })[0] + "." + colonnaDb;

            return query;
        }

        private string GetCompleteTableName(string nomeTabella)
        {
            return String.Format("{0}.{1} as {1}", Constants.NomeOwnerTabelle, nomeTabella);
        }


        private string GetElemento(string campoDb, string nomeTabella, TipoQuery tipoQuery)
        {
            var sRetVal = "";
            var iCount = 0;

            this.EnsureConnectionIsOpen();

            try
            {
                this._commandText = this.GetQuery(campoDb, nomeTabella, tipoQuery);

                this._log.DebugFormat("GetElemento-> Esecuzione della query: {0}", this._commandText);

                using (var cmd = this.InternalDatabaseConnection.CreateCommand(this._commandText))
                {
                    using (var dataReader = cmd.ExecuteReader())
                    {
                        while (dataReader.Read())
                        {
                            //Istruzione RightTrimSit usata per togliere eventuali spazi a destra
                            var dato = this.RightTrim(dataReader[campoDb].ToString());

                            iCount++;

                            if (iCount == 1)
                                sRetVal = dato;
                            else
                            {
                                sRetVal = "";
                                break;
                            }
                        }
                    }

                    return sRetVal;
                }
            }
            finally
            {
                this.InternalDatabaseConnection.Connection.Close();
            }


        }

        private RetSit GetElenco(string sField, string sTableName)
        {
            var pRetSit = new RetSit(true);

            this.EnsureConnectionIsOpen();

            try
            {

                this._commandText = this.GetQuery(sField, sTableName, TipoQuery.Elenco);

                this._log.DebugFormat("GetElemento-> Esecuzione della query: {0}", this._commandText);

                using (var pCommand = this.InternalDatabaseConnection.CreateCommand(this._commandText))
                {
                    using (var pDataReader = pCommand.ExecuteReader())
                    {
                        while (pDataReader.Read())
                        {
                            //Istruzione RightTrimSit usata per eliminare eventuali spazi a destra
                            var sDato = this.RightTrim(pDataReader[sField].ToString());

                            if (!string.IsNullOrEmpty(sDato) && !pRetSit.DataCollection.Contains(sDato))
                            {
                                pRetSit.DataCollection.Add(sDato);
                            }
                        }
                    }
                }

                return pRetSit;
            }
            finally
            {
                this.InternalDatabaseConnection.Connection.Close();
            }


        }
        #endregion



        #region Metodi per ottenere elenchi di elementi catastali o facenti parte dell'indirizzo

        public override RetSit ElencoCivici()
        {
            this._log.DebugFormat("Chiamata al metodo {0}", "ElencoCivici");

            try
            {
                if (!String.IsNullOrEmpty(this.DataSit.CodVia))
                    return this.GetElencoDaTabellaInfoStradario(Constants.ColonnaCivico /*"EncNumero"*/);

                return this.RestituisciErroreSit(this.GetMessaggioErrorePerLetturaLista(Constants.ColonnaCivico /*"EncNumero"*/), MessageCode.ElencoCivici, false);
            }
            catch (CatastoException ex)
            {
                this._log.DebugFormat("Errore controllato: {0}", ex.Message);

                return this.RestituisciErroreSit(ex.Message, MessageCode.ElencoCivici, false);
            }
            catch (Exception ex)
            {
                this._log.DebugFormat("Errore imprevisto: {0}", ex.Message);
                throw new Exception("Errore durante la restituzione dell'elenco dei civici. Metodo: ElencoCivici, modulo: SitNautilus. " + ex.Message + "\r\n Query: " + this._commandText);
            }
        }

        public override RetSit ElencoEsponenti()
        {
            this._log.DebugFormat("Chiamata al metodo {0}", "ElencoEsponenti");

            try
            {
                if (!String.IsNullOrEmpty(this.DataSit.CodVia) && !String.IsNullOrEmpty(this.DataSit.Civico))
                    return this.GetElencoDaTabellaInfoStradario(Constants.ColonnaEsponente /*"EncParte"*/);

                return this.RestituisciErroreSit(this.GetMessaggioErrorePerLetturaLista(Constants.ColonnaEsponente /*"EncParte"*/), MessageCode.ElencoEsponenti, false);
            }
            catch (CatastoException ex)
            {
                this._log.DebugFormat("Errore controllato: {0}", ex.Message);

                return this.RestituisciErroreSit(ex.Message, MessageCode.ElencoEsponenti, ex.ReturnValue);
            }
            catch (Exception ex)
            {
                this._log.DebugFormat("Errore imprevisto: {0}", ex.ToString());

                throw new Exception("Errore durante la restituzione dell'elenco degli esponenti. Metodo: ElencoEsponenti, modulo: SitNautilus. " + ex.Message + "\r\n Query: " + this._commandText);
            }
        }

        public override RetSit ElencoSezioni()
        {
            this._log.DebugFormat("Chiamata al metodo {0}", "ElencoSezioni");

            try
            {
                return this.GetElencoDaTabellaSit(Constants.ColonnaSezione /*"CT08_Sez_Urbana"*/);
            }
            catch (CatastoException ex)
            {
                this._log.DebugFormat("Errore controllato: {0}", ex.Message);

                return this.RestituisciErroreSit(ex.Message, MessageCode.ElencoSezioni, ex.ReturnValue);
            }
            catch (Exception ex)
            {
                this._log.DebugFormat("Errore imprevisto {0}", ex.ToString());

                throw new Exception("Errore durante la restituzione dell'elenco delle sezioni. Metodo: ElencoSezioni, modulo: SitNautilus. " + ex.Message + "\r\n Query: " + this._commandText);
            }
        }

        public override RetSit ElencoFogli()
        {
            this._log.DebugFormat("Chiamata al metodo {0}", "ElencoFogli");

            try
            {
                if (!String.IsNullOrEmpty(this.DataSit.Sezione))
                {
                    var rVal = this.GetElencoDaTabellaSit(Constants.ColonnaFoglio /*"CT08_Foglio"*/);

                    char[] charsToTrim = { '0' };

                    for (var iCount = 0; iCount < rVal.DataCollection.Count; iCount++)
                    {
                        rVal.DataCollection[iCount] = this.LeftTrim(rVal.DataCollection[iCount], charsToTrim);
                    }

                    return rVal;
                }

                return this.RestituisciErroreSit(this.GetMessaggioErrorePerLetturaLista(Constants.ColonnaFoglio /*"CT08_Foglio"*/), MessageCode.ElencoFogli, false);
            }
            catch (CatastoException ex)
            {
                this._log.DebugFormat("Errore controllato: {0}", ex.Message);

                return this.RestituisciErroreSit(ex.Message, MessageCode.ElencoFogli, ex.ReturnValue);
            }
            catch (Exception ex)
            {
                this._log.ErrorFormat("Errore imprevisto: {0}", ex.ToString());

                throw new Exception("Errore durante la restituzione dell'elenco dei fogli. Metodo: ElencoFogli, modulo: SitNautilus. " + ex.Message + "\r\n Query: " + this._commandText);
            }
        }

        public override RetSit ElencoParticelle()
        {
            this._log.DebugFormat("Chiamata al metodo {0}", "ElencoParticelle");

            try
            {
                if (!String.IsNullOrEmpty(this.DataSit.Sezione) && !String.IsNullOrEmpty(this.DataSit.Foglio))
                {
                    var rVal = this.GetElencoDaTabellaSit(Constants.ColonnaParticella /*"CT08_Numero"*/);

                    char[] charsToTrim = { '0' };

                    for (var iCount = 0; iCount < rVal.DataCollection.Count; iCount++)
                    {
                        rVal.DataCollection[iCount] = this.LeftTrim(rVal.DataCollection[iCount], charsToTrim);
                    }

                    return rVal;
                }

                return this.RestituisciErroreSit(this.GetMessaggioErrorePerLetturaLista(Constants.ColonnaParticella /*"CT08_Numero"*/), MessageCode.ElencoParticelle, false);
            }
            catch (CatastoException ex)
            {
                this._log.DebugFormat("Errore controllato: {0}", ex.Message);

                return this.RestituisciErroreSit(ex.Message, MessageCode.ElencoParticelle, ex.ReturnValue);
            }
            catch (Exception ex)
            {
                this._log.ErrorFormat("Errore imprevisto {0}", ex.ToString());

                throw new Exception("Errore durante la restituzione dell'elenco delle particelle. Metodo: ElencoParticelle, modulo: SitNautilus. " + ex.Message + "\r\n Query: " + this._commandText);
            }
        }

        public override RetSit ElencoSub()
        {
            this._log.DebugFormat("Chiamata al metodo {0}", "ElencoSub");

            try
            {
                if (!String.IsNullOrEmpty(this.DataSit.Sezione) && !String.IsNullOrEmpty(this.DataSit.Foglio) && !String.IsNullOrEmpty(this.DataSit.Particella))
                {
                    var rVal = this.GetElencoDaTabellaSit(Constants.ColonnaSubalterno /*"CT08_Subalterno"*/);

                    char[] charsToTrim = { '0' };

                    for (var iCount = 0; iCount < rVal.DataCollection.Count; iCount++)
                    {
                        rVal.DataCollection[iCount] = this.LeftTrim(rVal.DataCollection[iCount], charsToTrim);
                    }

                    return rVal;
                }

                return this.RestituisciErroreSit(this.GetMessaggioErrorePerLetturaLista(Constants.ColonnaSubalterno /*"CT08_Subalterno"*/), MessageCode.ElencoParticelle, false);
            }
            catch (CatastoException ex)
            {
                this._log.DebugFormat("Errore controllato: {0}", ex.Message);

                return this.RestituisciErroreSit(ex.Message, MessageCode.ElencoSub, ex.ReturnValue);
            }
            catch (Exception ex)
            {
                this._log.ErrorFormat("Errore imprevisto {0}", ex.Message);

                throw new Exception("Errore durante la restituzione dell'elenco dei sub. Metodo: ElencoSub, modulo: SitNautilus. " + ex.Message + "\r\n Query: " + this._commandText);
            }
        }

        #endregion

        #region Metodi per la verifica e la restituzione di un singolo elemento catastale o facente parte dell'indirizzo

        protected override string GetEsponente()
        {
            this._log.DebugFormat("Chiamata al metodo {0}", "GetEsponente");

            try
            {
                return this.GetValoreDaTabellaInfoStradario(Constants.ColonnaEsponente /*"EncParte"*/, TipoQuery.Elenco);
            }
            catch (CatastoException)
            {
                return String.Empty;
            }
            catch (Exception ex)
            {
                throw new Exception("Errore durante la restituzione di un esponente. Metodo: GetEsponente, modulo: SitNautilus. " + ex.Message + "\r\n Query: " + this._commandText);
            }
        }

        protected override RetSit VerificaEsponente()
        {
            this._log.DebugFormat("Chiamata al metodo {0}", "VerificaEsponente");

            try
            {
                var esponente = this.GetValoreDaTabellaInfoStradario(Constants.ColonnaEsponente /*"EncParte"*/, TipoQuery.Validazione);

                if (!String.IsNullOrEmpty(esponente))
                {
                    var rVal = new RetSit(true);
                    this.DataSit.Esponente = esponente;
                }

                return this.RestituisciErroreSit(this.GetMessaggioErrorePerValidazione(Constants.ColonnaEsponente /*"EncParte"*/), MessageCode.EsponenteValidazione, false);
            }
            catch (CatastoException ex)
            {
                return this.RestituisciErroreSit(ex.Message, MessageCode.EsponenteValidazione, ex.ReturnValue);
            }
            catch (Exception ex)
            {
                throw new Exception("Errore durante la validazione di un esponente. Metodo: VerificaEsponente, modulo: SitNautilus. " + ex.Message + "\r\n Query: " + this._commandText);
            }
        }



        protected override string GetFrazione()
        {
            this._log.DebugFormat("Chiamata al metodo {0}", "GetFrazione");

            try
            {
                return this.GetValoreDaTabellaInfoStradario(Constants.ColonnaFrazione /*"ElfFrazione"*/, TipoQuery.Elenco);
            }
            catch (CatastoException)
            {
                return string.Empty;
            }
            catch (Exception ex)
            {
                throw new Exception("Errore durante la restituzione di una frazione. Metodo: GetFrazione, modulo: SitNautilus. " + ex.Message + "\r\n Query: " + this._commandText);
            }
        }

        protected override RetSit VerificaFrazione()
        {
            this._log.DebugFormat("Chiamata al metodo {0}", "VerificaFrazione");

            try
            {
                var sElem = this.GetValoreDaTabellaInfoStradario(Constants.ColonnaFrazione /*"ElfFrazione"*/, TipoQuery.Validazione);
                if (!String.IsNullOrEmpty(sElem))
                {
                    var pRetSit = new RetSit(true);
                    this.DataSit.Frazione = sElem;

                    return pRetSit;
                }
                else
                {
                    return this.RestituisciErroreSit(this.GetMessaggioErrorePerValidazione(Constants.ColonnaFrazione /*"ElfFrazione"*/), MessageCode.FrazioneValidazione, false);
                }
            }
            catch (CatastoException ex)
            {
                return this.RestituisciErroreSit(ex.Message, MessageCode.FrazioneValidazione, ex.ReturnValue);
            }
            catch (Exception ex)
            {
                throw new Exception("Errore durante la validazione di una frazione. Metodo: VerificaFrazione, modulo: SitNautilus. " + ex.Message + "\r\n Query: " + this._commandText);
            }

        }

        protected override string GetCircoscrizione()
        {
            this._log.DebugFormat("Chiamata al metodo {0}", "GetCircoscrizione");

            try
            {
                return this.GetValoreDaTabellaInfoStradario(Constants.ColonnaCircoscrizione /*"ElcDescCircoscrizione"*/, TipoQuery.Elenco);
            }
            catch (CatastoException)
            {
                return String.Empty;
            }
            catch (Exception ex)
            {
                throw new Exception("Errore durante la restituzione di una circoscrizione. Metodo: GetCircoscrizione, modulo: SitNautilus. " + ex.Message + "\r\n Query: " + this._commandText);
            }
        }

        protected override RetSit VerificaCircoscrizione()
        {
            this._log.DebugFormat("Chiamata al metodo {0}", "VerificaCircoscrizione");

            try
            {
                var sElem = this.GetValoreDaTabellaInfoStradario(Constants.ColonnaCircoscrizione /*"ElcDescCircoscrizione"*/, TipoQuery.Validazione);
                if (!String.IsNullOrEmpty(sElem))
                {
                    var pRetSit = new RetSit(true);
                    this.DataSit.Circoscrizione = sElem;

                    return pRetSit;
                }

                return this.RestituisciErroreSit(this.GetMessaggioErrorePerValidazione(Constants.ColonnaCircoscrizione /*"ElcDescCircoscrizione"*/), MessageCode.CircoscrizioneValidazione, false);

            }
            catch (CatastoException ex)
            {
                return this.RestituisciErroreSit(ex.Message, MessageCode.CircoscrizioneValidazione, ex.ReturnValue);
            }
            catch (Exception ex)
            {
                throw new Exception("Errore durante la validazione di una circoscrizione. Metodo: VerificaCircoscrizione, modulo: SitNautilus. " + ex.Message + "\r\n Query: " + this._commandText);
            }
        }


        protected override RetSit VerificaTipoCatasto()
        {
            this._log.DebugFormat("Chiamata al metodo {0}", "VerificaTipoCatasto");

            if (this.IsCampiToponomasticaImmobileVuoti())
                return new RetSit(true);

            if (this.DataSit.TipoCatasto == "T")
                return this.RestituisciErroreSit("Il tipocatasto " + this.DataSit.TipoCatasto + " non è valido per i dati inseriti", MessageCode.TipoCatastoValidazione, false);

            return new RetSit(true);
        }


        protected override string GetSezione()
        {
            this._log.DebugFormat("Chiamata al metodo {0}", "GetSezione");

            try
            {
                return this.GetValoreDaTabellaSit(Constants.ColonnaSezione /*"CT08_Sez_Urbana"*/, TipoQuery.Elenco);
            }
            catch (CatastoException)
            {
                return String.Empty;
            }
            catch (Exception ex)
            {
                throw new Exception("Errore durante la restituzione di una sezione. Metodo: GetSezione, modulo: SitNautilus. " + ex.Message + "\r\n Query: " + this._commandText);
            }
        }

        protected override RetSit VerificaSezione()
        {
            this._log.DebugFormat("Chiamata al metodo {0}", "VerificaSezione");

            try
            {
                var sElem = this.GetValoreDaTabellaSit(Constants.ColonnaSezione /*"CT08_Sez_Urbana"*/, TipoQuery.Validazione);

                if (!String.IsNullOrEmpty(sElem))
                {
                    var pRetSit = new RetSit(true);
                    this.DataSit.Sezione = sElem;

                    return pRetSit;
                }

                return this.RestituisciErroreSit(this.GetMessaggioErrorePerValidazione(Constants.ColonnaSezione /*"CT08_Sez_Urbana"*/), MessageCode.SezioneValidazione, false);
            }
            catch (CatastoException ex)
            {
                return this.RestituisciErroreSit(ex.Message, MessageCode.SezioneValidazione, ex.ReturnValue);
            }
            catch (Exception ex)
            {
                throw new Exception("Errore durante la validazione di una sezione. Metodo: VerificaSezione, modulo: SitNautilus. " + ex.Message + "\r\n Query: " + this._commandText);
            }
        }

        protected override string GetFoglio()
        {
            this._log.DebugFormat("Chiamata al metodo {0}", "GetFoglio");

            try
            {
                var sRetVal = this.GetValoreDaTabellaSit(Constants.ColonnaFoglio /*"CT08_Foglio"*/, TipoQuery.Elenco);

                char[] charsToTrim = { '0' };
                return this.LeftTrim(sRetVal, charsToTrim);
            }
            catch (CatastoException)
            {
                return String.Empty;
            }
            catch (Exception ex)
            {
                throw new Exception("Errore durante la restituzione di un foglio. Metodo: GetFoglio, modulo: SitNautilus. " + ex.Message + "\r\n Query: " + this._commandText);
            }
        }

        protected override RetSit VerificaFoglio()
        {
            this._log.DebugFormat("Chiamata al metodo {0}", "VerificaFoglio");

            try
            {
                var sElem = this.GetValoreDaTabellaSit(Constants.ColonnaFoglio /*"CT08_Foglio"*/, TipoQuery.Validazione);

                if (!String.IsNullOrEmpty(sElem))
                {
                    var pRetSit = new RetSit(true);

                    char[] charsToTrim = { '0' };
                    sElem = this.LeftTrim(sElem, charsToTrim);

                    this.DataSit.Foglio = sElem;

                    return pRetSit;
                }

                //this.DataSit.Foglio = LeftPadSit(this.DataSit.Foglio, 4);
                return this.RestituisciErroreSit(this.GetMessaggioErrorePerValidazione("CT08_Sez_Foglio"), MessageCode.FoglioValidazione, false);
            }
            catch (CatastoException ex)
            {
                //this.DataSit.Foglio = LeftPadSit(this.DataSit.Foglio, 4);
                return this.RestituisciErroreSit(ex.Message, MessageCode.FoglioValidazione, ex.ReturnValue);
            }
            catch (Exception ex)
            {
                throw new Exception("Errore durante la validazione di un foglio. Metodo: VerificaFoglio, modulo: SitNautilus. " + ex.Message + "\r\n Query: " + this._commandText);
            }

        }

        protected override string GetParticella()
        {
            var sRetVal = "";

            try
            {
                sRetVal = this.GetValoreDaTabellaSit(Constants.ColonnaParticella /*"CT08_Numero"*/, TipoQuery.Elenco);

                char[] charsToTrim = { '0' };
                sRetVal = this.LeftTrim(sRetVal, charsToTrim);
            }
            catch (CatastoException)
            {
            }
            catch (Exception ex)
            {
                throw new Exception("Errore durante la restituzione di una particella. Metodo: GetParticella, modulo: SitNautilus. " + ex.Message + "\r\n Query: " + this._commandText);
            }

            return sRetVal;
        }

        protected override RetSit VerificaParticella()
        {
            RetSit pRetSit;

            try
            {
                var sElem = this.GetValoreDaTabellaSit(Constants.ColonnaParticella /*"CT08_Numero"*/, TipoQuery.Validazione);

                if (!String.IsNullOrEmpty(sElem))
                {
                    pRetSit = new RetSit(true);

                    char[] charsToTrim = { '0' };
                    sElem = this.LeftTrim(sElem, charsToTrim);

                    this.DataSit.Particella = sElem;
                }
                else
                {
                    pRetSit = this.RestituisciErroreSit(this.GetMessaggioErrorePerValidazione(Constants.ColonnaParticella /*"CT08_Numero"*/), MessageCode.ParticellaValidazione, false);
                }
            }
            catch (CatastoException ex)
            {
                //this.DataSit.Particella = LeftPadSit(this.DataSit.Particella, 5);
                pRetSit = this.RestituisciErroreSit(ex.Message, MessageCode.ParticellaValidazione, ex.ReturnValue);
            }
            catch (Exception ex)
            {
                throw new Exception("Errore durante la validazione di una particella. Metodo: VerificaParticella, modulo: SitNautilus. " + ex.Message + "\r\n Query: " + this._commandText);
            }

            return pRetSit;
        }

        protected override string GetSub()
        {
            var sRetVal = "";

            try
            {
                sRetVal = this.GetValoreDaTabellaSit(Constants.ColonnaSubalterno /*"CT08_Subalterno"*/, TipoQuery.Elenco);

                char[] charsToTrim = { '0' };
                sRetVal = this.LeftTrim(sRetVal, charsToTrim);
            }
            catch (CatastoException)
            {
            }
            catch (Exception ex)
            {
                throw new Exception("Errore durante la restituzione di un sub. Metodo: GetSub, modulo: SitNautilus. " + ex.Message + "\r\n Query: " + this._commandText);
            }

            return sRetVal;
        }

        protected override RetSit VerificaSub()
        {
            RetSit pRetSit;


            try
            {
                var sElem = this.GetValoreDaTabellaSit(Constants.ColonnaSubalterno /*"CT08_Subalterno"*/, TipoQuery.Validazione);

                if (!String.IsNullOrEmpty(sElem))
                {
                    pRetSit = new RetSit(true);

                    char[] charsToTrim = { '0' };
                    sElem = this.LeftTrim(sElem, charsToTrim);

                    this.DataSit.Sub = sElem;
                }
                else
                {
                    //this.DataSit.Sub = LeftPadSit(this.DataSit.Sub, 4);
                    pRetSit = this.RestituisciErroreSit(this.GetMessaggioErrorePerValidazione(Constants.ColonnaSubalterno /*"CT08_Subalterno"*/), MessageCode.SubValidazione, false);
                }
            }
            catch (CatastoException ex)
            {
                //this.DataSit.Sub = LeftPadSit(this.DataSit.Sub, 4);
                pRetSit = this.RestituisciErroreSit(ex.Message, MessageCode.SubValidazione, ex.ReturnValue);
            }
            catch (Exception ex)
            {
                throw new Exception("Errore durante la validazione di un sub. Metodo: VerificaSub, modulo: SitNautilus. " + ex.Message + "\r\n Query: " + this._commandText);
            }

            return pRetSit;
        }





        protected override string GetCivico()
        {
            var sRetVal = "";

            try
            {
                sRetVal = this.GetValoreDaTabellaInfoStradario(Constants.ColonnaCivico /*"EncNumero"*/, TipoQuery.Elenco);
            }
            catch (CatastoException)
            {
            }
            catch (Exception ex)
            {
                throw new Exception("Errore durante la restituzione di un civico. Metodo: GetCivico, modulo: SitNautilus. " + ex.Message + "\r\n Query: " + this._commandText);
            }

            return sRetVal;
        }

        protected override RetSit VerificaCivico()
        {
            RetSit pRetSit;

            if (Init.Utils.StringChecker.IsNumeric(this.DataSit.Civico))
            {
                try
                {
                    var sElem = this.GetValoreDaTabellaInfoStradario(Constants.ColonnaCivico /*"EncNumero"*/, TipoQuery.Validazione);
                    if (!String.IsNullOrEmpty(sElem))
                    {
                        pRetSit = new RetSit(true);
                        this.DataSit.Civico = sElem;
                    }
                    else
                    {
                        pRetSit = this.RestituisciErroreSit(this.GetMessaggioErrorePerValidazione(Constants.ColonnaCivico /*"EncNumero"*/), MessageCode.CivicoValidazione, false);
                    }
                }
                catch (CatastoException ex)
                {
                    pRetSit = this.RestituisciErroreSit(ex.Message, MessageCode.CivicoValidazione, ex.ReturnValue);
                }
                catch (Exception ex)
                {
                    throw new Exception("Errore durante la validazione di un civico. Metodo: VerificaCivico, modulo: SitNautilus. " + ex.Message + "\r\n Query: " + this._commandText);
                }

            }
            else
            {
                pRetSit = this.RestituisciErroreSit("Non è possibile validare il civico " + this.DataSit.Civico + " perchè non è un numero", MessageCode.CivicoValidazioneNumero, false);
            }

            return pRetSit;
        }


        protected override string GetCAP()
        {
            var sRetVal = "";

            try
            {
                sRetVal = this.GetValoreDaTabellaInfoStradario(Constants.ColonnaCap /*"ElcfCAP"*/, TipoQuery.Elenco);
            }
            catch (CatastoException)
            {
            }
            catch (Exception ex)
            {
                throw new Exception("Errore durante la restituzione di un CAP. Metodo: GetCAP, modulo: SitNautilus. " + ex.Message + "\r\n Query: " + this._commandText);
            }

            return sRetVal;
        }

        protected override RetSit VerificaCAP()
        {
            RetSit pRetSit;

            try
            {
                var sElem = this.GetValoreDaTabellaInfoStradario(Constants.ColonnaCap /*"ElcfCAP"*/, TipoQuery.Validazione);
                if (!String.IsNullOrEmpty(sElem))
                {
                    pRetSit = new RetSit(true);
                    this.DataSit.CAP = sElem;
                }
                else
                {
                    pRetSit = this.RestituisciErroreSit(this.GetMessaggioErrorePerValidazione(Constants.ColonnaCap /*"ElcfCAP"*/), MessageCode.CAPValidazione, false);
                }
            }
            catch (CatastoException ex)
            {
                pRetSit = this.RestituisciErroreSit(ex.Message, MessageCode.CAPValidazione, ex.ReturnValue);
            }
            catch (Exception ex)
            {
                throw new Exception("Errore durante la validazione di un CAP. Metodo: VerificaCAP, modulo: SitNautilus. " + ex.Message + "\r\n Query: " + this._commandText);
            }

            return pRetSit;
        }





        protected override string GetCodVia()
        {
            var sRetVal = "";

            try
            {
                sRetVal = this.GetValoreDaTabellaInfoStradario(Constants.ColonnaCodiceVia /*"ElvCodTopon"*/, TipoQuery.Elenco);
            }
            catch (CatastoException)
            {
            }
            catch (Exception ex)
            {
                throw new Exception("Errore durante la restituzione di un codice via. Metodo: GetCodVia, modulo: SitNautilus. " + ex.Message + "\r\n Query: " + this._commandText);
            }

            return sRetVal;
        }

        #endregion

        public override string[] GetListaCampiGestiti()
        {
            return new string[]{
                SitIntegrationService.NomiCampiSit.Esponente,
                SitIntegrationService.NomiCampiSit.Frazione,
                SitIntegrationService.NomiCampiSit.Circoscrizione,
				//SitMgr.NomiCampiSit.Interno,
				//SitMgr.NomiCampiSit.EsponenteInterno,
				//SitMgr.NomiCampiSit.Fabbricato,
				SitIntegrationService.NomiCampiSit.Sezione,
                SitIntegrationService.NomiCampiSit.TipoCatasto,
                SitIntegrationService.NomiCampiSit.Foglio,
                SitIntegrationService.NomiCampiSit.Particella,
                SitIntegrationService.NomiCampiSit.Sub,
				//SitMgr.NomiCampiSit.UnitaImmobiliare,
				SitIntegrationService.NomiCampiSit.Civico,
                SitIntegrationService.NomiCampiSit.Cap,
                SitIntegrationService.NomiCampiSit.CodiceVia
				//SitMgr.NomiCampiSit.CodiceCivico
			};
        }
    }
}
