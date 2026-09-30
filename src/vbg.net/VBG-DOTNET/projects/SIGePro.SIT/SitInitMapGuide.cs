using Init.SIGePro.Exceptions.SIT;
using Init.SIGePro.Sit.Data;
using Init.SIGePro.Sit.Manager;
using Init.SIGePro.Sit.ValidazioneFormale;
using Init.SIGePro.Verticalizzazioni;
using PersonalLib2.Data;
using System;
using System.Data;

namespace Init.SIGePro.Sit
{
    internal class SIT_INITMAPGUIDE : SitBase
    {
        public SIT_INITMAPGUIDE()
            : base(new ValidazioneFormaleTramiteCodiceCivicoService())
        {
        }

        private string sConnectionString = "";
        private string sProvider = "";
        private string sCommandText = "";

        #region Utility
        public override void SetupVerticalizzazione()
        {
            this.GetParametriFromVertSITINITMAPGUIDE();
        }

        private void GetParametriFromVertSITINITMAPGUIDE()
        {
            try
            {
                VerticalizzazioneSitInitmapguide pSitInitMapGuide;
                DataBase dDataBase = this.Database;

                pSitInitMapGuide = new VerticalizzazioneSitInitmapguide(this.IdComuneAlias, this.Software);

                if (pSitInitMapGuide.Attiva)
                {
                    this.sConnectionString = pSitInitMapGuide.Connectionstring;
                    this.sProvider = pSitInitMapGuide.Provider;

                    ProviderType provider = (ProviderType)Enum.Parse(typeof(ProviderType), this.sProvider, false);
                    this.InternalDatabaseConnection = new DataBase(this.sConnectionString, provider);
                }
                else
                    throw new Exception("La verticalizzazione SIT_INITMAPGUIDE non è attiva.\r\n");

            }
            catch (Exception ex)
            {
                throw new Exception("Errore generato durante la lettura della verticalizzazione SIT_INITMAPGUIDE. Metodo: GetParametriFromVertSITINITMAPGUIDE, modulo: SitInitMapGuide. " + ex.Message + "\r\n");
            }
        }

        private string GetMessageValidate(string sField)
        {
            string sValue = string.Empty;

            switch (sField)
            {
                case "SEZ":
                    sValue = "La sezione " + this.DataSit.Sezione + " non è valida per i dati inseriti";
                    break;
                case "FOGLIO":
                    sValue = "Il foglio " + this.DataSit.Foglio + " non è valido per i dati inseriti";
                    break;
                case "NUMERO":
                    sValue = "La particella " + this.DataSit.Particella + " non è valida per i dati inseriti";
                    break;
                case "DESCRIZI_1":
                    sValue = "La circoscrizione " + this.DataSit.Frazione + " non è valida per i dati inseriti";
                    break;
                case "TEXT":
                    sValue = "Il civico " + this.DataSit.Civico + " non è valido per i dati inseriti";
                    break;
            }

            return sValue;
        }

        private string GetMessageList(string sField)
        {
            string sValue = string.Empty;

            switch (sField)
            {
                case "FOGLIO":
                    sValue = "Non è possibile ottenere la lista dei fogli per insufficienza di dati: il catasto e la sezione devono essere forniti";
                    break;
                case "NUMERO":
                    sValue = "Non è possibile ottenere la lista delle particelle per insufficienza di dati: il catasto, la sezione ed il foglio devono essere forniti";
                    break;
                case "SEZ":
                    sValue = "Non è possibile ottenere la lista delle sezioni per insufficienza di dati: il catasto deve essere fornito";
                    break;
                case "TEXT":
                    sValue = "Non è possibile ottenere la lista dei civici per insufficienza di dati: la via deve essere fornita";
                    break;
            }

            return sValue;
        }

        private RetSit ElencoCivici_All(string sField, IDataReader pDataReader)
        {
            RetSit pRetSit = null;

            //if (string.IsNullOrEmpty(DataSit.TipoCatasto))
            //    throw new CatastoException(GetMessageList(sField));
            //else
            //{
            if (string.IsNullOrEmpty(this.DataSit.TipoCatasto) || (this.DataSit.TipoCatasto == "F"))
                pRetSit = this.GetElenco(sField, "CIVICI_ALL", pDataReader);
            else
                pRetSit = new RetSit(true);
            //}

            return pRetSit;
        }

        private RetSit ElencoTerreni(string sField, IDataReader pDataReader)
        {
            RetSit pRetSit = null;

            if (string.IsNullOrEmpty(this.DataSit.TipoCatasto))
                throw new CatastoException(this.GetMessageList(sField));
            else
            {
                if (this.DataSit.TipoCatasto == "F")
                    pRetSit = this.GetElenco(sField, "TERRENI", pDataReader);
                else
                {
                    if (this.IsCampiToponomasticaImmobileVuoti())
                        pRetSit = this.GetElenco(sField, "TERRENI", pDataReader);
                    else
                        pRetSit = new RetSit(true);
                }
            }

            return pRetSit;
        }

        private string GetCivici_All(string sField, IDataReader pDataReader, TipoQuery eTipoQuery)
        {
            string sRetVal = string.Empty;

            if (sField == "COD_VIA")
            {
                sRetVal = this.GetElemento(sField, "CIVICI_ALL", pDataReader, eTipoQuery);
            }
            else
            {
                if (string.IsNullOrEmpty(this.DataSit.TipoCatasto))
                    sRetVal = this.GetElemento(sField, "CIVICI_ALL", pDataReader, eTipoQuery);
                else
                {
                    if (this.DataSit.TipoCatasto == "F")
                        sRetVal = this.GetElemento(sField, "CIVICI_ALL", pDataReader, eTipoQuery);
                    else
                        throw new CatastoException(this.GetMessageValidate(sField));
                }
            }

            return sRetVal;
        }

        private string GetTerreni(string sField, IDataReader pDataReader, TipoQuery eTipoQuery)
        {
            string sRetVal = string.Empty;

            if (string.IsNullOrEmpty(this.DataSit.TipoCatasto))
                sRetVal = this.GetElemento(sField, "TERRENI", pDataReader, eTipoQuery);
            else
            {
                if (this.DataSit.TipoCatasto == "F")
                    sRetVal = this.GetElemento(sField, "TERRENI", pDataReader, eTipoQuery);
                else
                {
                    if (this.IsCampiToponomasticaImmobileVuoti())
                        sRetVal = this.GetElemento(sField, "TERRENI", pDataReader, eTipoQuery);
                    else
                        throw new CatastoException(this.GetMessageValidate(sField));
                }
            }

            return sRetVal;
        }

        private string GetQuery(string sField, string sTableName, TipoQuery eTipoQuery)
        {
            string sQuery = string.Empty;

            switch (sTableName)
            {
                case "CIVICI_ALL":
                    switch (eTipoQuery)
                    {
                        case TipoQuery.Elenco:
                            sQuery = "Select distinct " + sTableName + "." + sField + " from CIVICI_ALL where " +
                                (((!string.IsNullOrEmpty(this.DataSit.CodVia)) && (sField != "COD_VIA")) ? this.InternalDatabaseConnection.Specifics.RTrimFunction("CIVICI_ALL.COD_VIA") + " = " + this.RightTrim(this.DataSit.CodVia) + " and " : "") +
                                (((!string.IsNullOrEmpty(this.DataSit.Civico)) && (sField != "TEXT")) ? this.InternalDatabaseConnection.Specifics.RTrimFunction("CIVICI_ALL.TEXT") + " = '" + this.RightTrim(Init.Utils.StringChecker.IsNumeric(this.DataSit.Civico) ? this.DataSit.Civico : "-1") + "' and " : "") +
                                (((!string.IsNullOrEmpty(this.DataSit.Circoscrizione)) && (sField != "DESCRIZI_1")) ? this.InternalDatabaseConnection.Specifics.RTrimFunction("CIVICI_ALL.DESCRIZI_1") + " = '" + this.RightTrim(this.DataSit.Circoscrizione) + "' and " : "") +
                                (((!string.IsNullOrEmpty(this.DataSit.CodCivico)) && (sField != "FEATID")) ? this.InternalDatabaseConnection.Specifics.RTrimFunction("CIVICI_ALL.FEATID") + " = '" + this.RightTrim(this.DataSit.CodCivico) + "' and " : "");
                            break;
                        case TipoQuery.Validazione:
                            sQuery = "Select distinct " + sTableName + "." + sField + " from CIVICI_ALL where " +
                                (((!string.IsNullOrEmpty(this.DataSit.CodVia))) ? this.InternalDatabaseConnection.Specifics.RTrimFunction("CIVICI_ALL.COD_VIA") + " = " + this.RightTrim(this.DataSit.CodVia) + " and " : "") +
                                (((!string.IsNullOrEmpty(this.DataSit.Civico))) ? this.InternalDatabaseConnection.Specifics.RTrimFunction("CIVICI_ALL.TEXT") + " = '" + this.RightTrim(Init.Utils.StringChecker.IsNumeric(this.DataSit.Civico) ? this.DataSit.Civico : "-1") + "' and " : "") +
                                (((!string.IsNullOrEmpty(this.DataSit.Circoscrizione))) ? this.InternalDatabaseConnection.Specifics.RTrimFunction("CIVICI_ALL.DESCRIZI_1") + " = '" + this.RightTrim(this.DataSit.Circoscrizione) + "' and " : "") +
                                (((!string.IsNullOrEmpty(this.DataSit.CodCivico))) ? this.InternalDatabaseConnection.Specifics.RTrimFunction("CIVICI_ALL.FEATID") + " = " + this.RightTrim(this.DataSit.CodCivico) + " and " : "");
                            break;
                    }
                    break;
                case "TERRENI":
                    switch (eTipoQuery)
                    {
                        case TipoQuery.Elenco:
                            sQuery = "Select distinct " + sTableName + "." + sField + " from TERRENI where " +
                                (((!string.IsNullOrEmpty(this.DataSit.Sezione)) && (sField != "SEZ")) ? this.InternalDatabaseConnection.Specifics.RTrimFunction("TERRENI.SEZ") + " = '" + this.RightTrim(this.DataSit.Sezione) + "' and " : "") +
                                (((!string.IsNullOrEmpty(this.DataSit.Foglio)) && (sField != "FOGLIO")) ? this.InternalDatabaseConnection.Specifics.RTrimFunction("TERRENI.FOGLIO") + " = " + this.RightTrim(this.DataSit.Foglio) + " and " : "") +
                                (((!string.IsNullOrEmpty(this.DataSit.Particella)) && (sField != "NUMERO")) ? this.InternalDatabaseConnection.Specifics.RTrimFunction("TERRENI.NUMERO") + " = '" + this.RightTrim(this.DataSit.Particella) + "' " : "");
                            break;
                        case TipoQuery.Validazione:
                            sQuery = "Select distinct " + sTableName + "." + sField + " from TERRENI where " +
                                (((!string.IsNullOrEmpty(this.DataSit.Sezione))) ? this.InternalDatabaseConnection.Specifics.RTrimFunction("TERRENI.SEZ") + " = '" + this.RightTrim(this.DataSit.Sezione) + "' and " : "") +
                                (((!string.IsNullOrEmpty(this.DataSit.Foglio))) ? this.InternalDatabaseConnection.Specifics.RTrimFunction("TERRENI.FOGLIO") + " = " + this.RightTrim(this.DataSit.Foglio) + " and " : "") +
                                (((!string.IsNullOrEmpty(this.DataSit.Particella))) ? this.InternalDatabaseConnection.Specifics.RTrimFunction("TERRENI.NUMERO") + " = '" + this.RightTrim(this.DataSit.Particella) + "' " : "");
                            break;
                    }
                    break;
            }

            if (sQuery.EndsWith("and "))
                sQuery = sQuery.Remove(sQuery.Length - 4, 4) + " order by " + sTableName.Split(new Char[] { ',' })[0] + "." + sField;
            else if (sQuery.EndsWith("where "))
                sQuery = sQuery.Remove(sQuery.Length - 6, 6) + " order by " + sTableName.Split(new Char[] { ',' })[0] + "." + sField;
            else
                sQuery = sQuery + " order by " + sTableName.Split(new Char[] { ',' })[0] + "." + sField;

            return sQuery;
        }


        private string GetElemento(string sField, string sTableName, IDataReader pDataReader, TipoQuery eTipoQuery)
        {
            string sRetVal = "";
            int iCount = 0;
            this.EnsureConnectionIsOpen();
            this.sCommandText = this.GetQuery(sField, sTableName, eTipoQuery);

            IDbCommand pCommand = this.InternalDatabaseConnection.CreateCommand(this.sCommandText);
            pDataReader = pCommand.ExecuteReader();

            while (pDataReader.Read())
            {
                //Istruzione RightTrimSit usata per togliere eventuali spazi a destra
                string sDato = this.RightTrim(pDataReader[sField].ToString());


                iCount++;
                if (iCount == 1)
                    sRetVal = sDato;
                else
                {
                    sRetVal = "";
                    break;
                }

            }
            pCommand.Dispose();

            return sRetVal;
        }

        private RetSit GetElenco(string sField, string sTableName, IDataReader pDataReader)
        {
            RetSit pRetSit = new RetSit(true);
            this.EnsureConnectionIsOpen();
            this.sCommandText = this.GetQuery(sField, sTableName, TipoQuery.Elenco);

            IDbCommand pCommand = this.InternalDatabaseConnection.CreateCommand(this.sCommandText);
            pDataReader = pCommand.ExecuteReader();

            while (pDataReader.Read())
            {
                //Istruzione RightTrimSit usata per eliminare eventuali spazi a destra
                string sDato = this.RightTrim(pDataReader[sField].ToString());

                if (!string.IsNullOrEmpty(sDato) && !pRetSit.DataCollection.Contains(sDato))
                {
                    pRetSit.DataCollection.Add(sDato);
                }
            }
            pCommand.Dispose();

            return pRetSit;
        }

        #endregion



        #region Metodi per ottenere elenchi di elementi catastali o facenti parte dell'indirizzo


        public override RetSit ElencoCivici()
        {
            IDataReader pDataReader = null;
            RetSit pRetSit;

            try
            {
                if (!String.IsNullOrEmpty(this.DataSit.CodVia))
                {
                    pRetSit = this.ElencoCivici_All("TEXT", pDataReader);
                }
                else
                {
                    pRetSit = this.RestituisciErroreSit(this.GetMessageList("TEXT"), MessageCode.ElencoCivici, false);
                }
            }
            catch (CatastoException ex)
            {
                pRetSit = this.RestituisciErroreSit(ex.Message, MessageCode.ElencoCivici, false);
            }
            catch (Exception ex)
            {
                throw new Exception("Errore durante la restituzione dell'elenco dei civici. Metodo: ElencoCivici, modulo: SitInitMapGuide. " + ex.Message + "\r\n Query: " + this.sCommandText);
            }
            finally
            {
                if (pDataReader != null)
                    pDataReader.Close();
                this.InternalDatabaseConnection.Connection.Close();
            }

            return pRetSit;
        }


        public override RetSit ElencoFrazioni()
        {
            IDataReader pDataReader = null;
            RetSit pRetSit;

            try
            {
                if (!String.IsNullOrEmpty(this.DataSit.Civico) && !String.IsNullOrEmpty(this.DataSit.CodVia))
                {
                    pRetSit = this.ElencoCivici_All("DESCRIZI_1", pDataReader);
                }
                else
                {
                    pRetSit = this.RestituisciErroreSit(this.GetMessageList("DESCRIZI_1"), MessageCode.ElencoCircoscrizioni, false);
                }
            }
            catch (CatastoException ex)
            {
                pRetSit = this.RestituisciErroreSit(ex.Message, MessageCode.ElencoFrazioni, false);
            }
            catch (Exception ex)
            {
                throw new Exception("Errore durante la restituzione dell'elenco delle frazioni. Metodo: ElencoFrazioni, modulo: SitInitMapGuide. " + ex.Message + "\r\n Query: " + this.sCommandText);
            }
            finally
            {
                if (pDataReader != null)
                    pDataReader.Close();
                this.InternalDatabaseConnection.Connection.Close();
            }

            return pRetSit;
        }

        public override RetSit ElencoSezioni()
        {
            IDataReader pDataReader = null;
            RetSit pRetSit;

            try
            {
                pRetSit = this.ElencoTerreni("SEZ", pDataReader);

            }
            catch (CatastoException ex)
            {
                pRetSit = this.RestituisciErroreSit(ex.Message, MessageCode.ElencoSezioni, false);
            }
            catch (Exception ex)
            {
                throw new Exception("Errore durante la restituzione dell'elenco delle sezioni. Metodo: ElencoSezioni, modulo: SitInitMapGuide. " + ex.Message + "\r\n Query: " + this.sCommandText);
            }
            finally
            {
                if (pDataReader != null)
                    pDataReader.Close();
                this.InternalDatabaseConnection.Connection.Close();
            }

            return pRetSit;
        }

        public override RetSit ElencoFogli()
        {
            IDataReader pDataReader = null;
            RetSit pRetSit;

            try
            {
                if (!String.IsNullOrEmpty(this.DataSit.Sezione))
                {
                    pRetSit = this.ElencoTerreni("FOGLIO", pDataReader);
                }
                else
                {
                    pRetSit = this.RestituisciErroreSit(this.GetMessageList("FOGLIO"), MessageCode.ElencoFogli, false);
                }
            }
            catch (CatastoException ex)
            {
                pRetSit = this.RestituisciErroreSit(ex.Message, MessageCode.ElencoFogli, false);
            }
            catch (Exception ex)
            {
                throw new Exception("Errore durante la restituzione dell'elenco dei fogli. Metodo: ElencoFogli, modulo: SitInitMapGuide. " + ex.Message + "\r\n Query: " + this.sCommandText);
            }
            finally
            {
                if (pDataReader != null)
                    pDataReader.Close();
                this.InternalDatabaseConnection.Connection.Close();
            }

            return pRetSit;
        }

        public override RetSit ElencoParticelle()
        {
            IDataReader pDataReader = null;
            RetSit pRetSit;

            try
            {
                if (!String.IsNullOrEmpty(this.DataSit.Sezione) && !String.IsNullOrEmpty(this.DataSit.Foglio))
                {
                    pRetSit = this.ElencoTerreni("NUMERO", pDataReader);
                }
                else
                {
                    pRetSit = this.RestituisciErroreSit(this.GetMessageList("NUMERO"), MessageCode.ElencoParticelle, false);
                }
            }
            catch (CatastoException ex)
            {
                pRetSit = this.RestituisciErroreSit(ex.Message, MessageCode.ElencoParticelle, false);
            }
            catch (Exception ex)
            {
                throw new Exception("Errore durante la restituzione dell'elenco delle particelle. Metodo: ElencoParticelle, modulo: SitInitMapGuide. " + ex.Message + "\r\n Query: " + this.sCommandText);
            }
            finally
            {
                if (pDataReader != null)
                    pDataReader.Close();
                this.InternalDatabaseConnection.Connection.Close();
            }

            return pRetSit;
        }

        #endregion

        #region Metodi per la verifica e la restituzione di un singolo elemento catastale o facente parte dell'indirizzo
        protected override string GetFrazione()
        {
            string sRetVal = "";
            IDataReader pDataReader = null;

            try
            {
                sRetVal = this.GetCivici_All("DESCRIZI_1", pDataReader, TipoQuery.Elenco);
            }
            catch (CatastoException ex)
            {
            }
            catch (Exception ex)
            {
                throw new Exception("Errore durante la restituzione di una frazione. Metodo: GetFrazione, modulo: SitInitMapGuide. " + ex.Message + "\r\n Query: " + this.sCommandText);
            }
            finally
            {
                if (pDataReader != null)
                    pDataReader.Close();
                this.InternalDatabaseConnection.Connection.Close();
            }

            return sRetVal;

        }

        protected override RetSit VerificaFrazione()
        {
            RetSit pRetSit;
            IDataReader pDataReader = null;

            try
            {
                string sElem = this.GetCivici_All("DESCRIZI_1", pDataReader, TipoQuery.Validazione);
                if (!String.IsNullOrEmpty(sElem))
                {
                    pRetSit = new RetSit(true);
                    this.DataSit.Frazione = sElem;
                }
                else
                {
                    pRetSit = this.RestituisciErroreSit(this.GetMessageValidate("DESCRIZI_1"), MessageCode.FrazioneValidazione, false);
                }
            }
            catch (CatastoException ex)
            {
                pRetSit = this.RestituisciErroreSit(ex.Message, MessageCode.FrazioneValidazione, false);
            }
            catch (Exception ex)
            {
                throw new Exception("Errore durante la validazione di una frazione. Metodo: VerificaFrazione, modulo: SitInitMapGuide. " + ex.Message + "\r\n Query: " + this.sCommandText);
            }
            finally
            {
                if (pDataReader != null)
                    pDataReader.Close();
                this.InternalDatabaseConnection.Connection.Close();
            }

            return pRetSit;
        }

        protected override RetSit VerificaTipoCatasto()
        {
            RetSit pRetSit = null;

            if (this.IsCampiToponomasticaImmobileVuoti())
                pRetSit = new RetSit(true);
            else
            {
                if (this.DataSit.TipoCatasto == "T")
                    pRetSit = this.RestituisciErroreSit("Il tipocatasto " + this.DataSit.TipoCatasto + " non è valido per i dati inseriti", MessageCode.TipoCatastoValidazione, false);
                else
                    pRetSit = new RetSit(true);
            }

            return pRetSit;
        }


        protected override string GetSezione()
        {
            string sRetVal = "";
            IDataReader pDataReader = null;

            try
            {
                sRetVal = this.GetTerreni("SEZ", pDataReader, TipoQuery.Elenco);
            }
            catch (CatastoException ex)
            {
            }
            catch (Exception ex)
            {
                throw new Exception("Errore durante la restituzione di una sezione. Metodo: GetSezione, modulo: SitInitMapGuide. " + ex.Message + "\r\n Query: " + this.sCommandText);
            }
            finally
            {
                if (pDataReader != null)
                    pDataReader.Close();
                this.InternalDatabaseConnection.Connection.Close();
            }

            return sRetVal;

        }

        protected override RetSit VerificaSezione()
        {
            RetSit pRetSit;

            IDataReader pDataReader = null;

            try
            {
                string sElem = this.GetTerreni("SEZ", pDataReader, TipoQuery.Validazione);
                if (!String.IsNullOrEmpty(sElem))
                {
                    pRetSit = new RetSit(true);
                    this.DataSit.Sezione = sElem;
                }
                else
                {
                    pRetSit = this.RestituisciErroreSit(this.GetMessageValidate("SEZ"), MessageCode.SezioneValidazione, false);
                }
            }
            catch (CatastoException ex)
            {
                pRetSit = this.RestituisciErroreSit(ex.Message, MessageCode.SezioneValidazione, false);
            }
            catch (Exception ex)
            {
                throw new Exception("Errore durante la validazione di una sezione. Metodo: VerificaSezione, modulo: SitInitMapGuide. " + ex.Message + "\r\n Query: " + this.sCommandText);
            }
            finally
            {
                if (pDataReader != null)
                    pDataReader.Close();
                this.InternalDatabaseConnection.Connection.Close();
            }

            return pRetSit;
        }

        protected override string GetFoglio()
        {
            string sRetVal = "";
            IDataReader pDataReader = null;

            try
            {
                sRetVal = this.GetTerreni("FOGLIO", pDataReader, TipoQuery.Elenco);
            }
            catch (CatastoException ex)
            {
            }
            catch (Exception ex)
            {
                throw new Exception("Errore durante la restituzione di un foglio. Metodo: GetFoglio, modulo: SitInitMapGuide. " + ex.Message + "\r\n Query: " + this.sCommandText);
            }
            finally
            {
                if (pDataReader != null)
                    pDataReader.Close();
                this.InternalDatabaseConnection.Connection.Close();
            }

            return sRetVal;
        }

        protected override RetSit VerificaFoglio()
        {
            RetSit pRetSit;

            IDataReader pDataReader = null;

            try
            {
                string sElem = this.GetTerreni("FOGLIO", pDataReader, TipoQuery.Validazione);
                if (!String.IsNullOrEmpty(sElem))
                {
                    pRetSit = new RetSit(true);
                    this.DataSit.Foglio = sElem;
                }
                else
                {
                    pRetSit = this.RestituisciErroreSit(this.GetMessageValidate("FOGLIO"), MessageCode.FoglioValidazione, false);
                }
            }
            catch (CatastoException ex)
            {
                pRetSit = this.RestituisciErroreSit(ex.Message, MessageCode.FoglioValidazione, false);
            }
            catch (Exception ex)
            {
                throw new Exception("Errore durante la validazione di un foglio. Metodo: VerificaFoglio, modulo: SitInitMapGuide. " + ex.Message + "\r\n Query: " + this.sCommandText);
            }
            finally
            {
                if (pDataReader != null)
                    pDataReader.Close();
                this.InternalDatabaseConnection.Connection.Close();
            }
            return pRetSit;
        }

        protected override string GetParticella()
        {
            string sRetVal = "";
            IDataReader pDataReader = null;

            try
            {
                sRetVal = this.GetTerreni("NUMERO", pDataReader, TipoQuery.Elenco);
            }
            catch (CatastoException ex)
            {
            }
            catch (Exception ex)
            {
                throw new Exception("Errore durante la restituzione di una particella. Metodo: GetParticella, modulo: SitInitMapGuide. " + ex.Message + "\r\n Query: " + this.sCommandText);
            }
            finally
            {
                if (pDataReader != null)
                    pDataReader.Close();
                this.InternalDatabaseConnection.Connection.Close();
            }

            return sRetVal;

        }

        protected override RetSit VerificaParticella()
        {
            RetSit pRetSit;

            IDataReader pDataReader = null;

            try
            {
                string sElem = this.GetTerreni("NUMERO", pDataReader, TipoQuery.Validazione);
                if (!String.IsNullOrEmpty(sElem))
                {
                    pRetSit = new RetSit(true);
                    this.DataSit.Particella = sElem;
                }
                else
                {
                    pRetSit = this.RestituisciErroreSit(this.GetMessageValidate("NUMERO"), MessageCode.ParticellaValidazione, false);
                }
            }
            catch (CatastoException ex)
            {
                pRetSit = this.RestituisciErroreSit(ex.Message, MessageCode.ParticellaValidazione, false);
            }
            catch (Exception ex)
            {
                throw new Exception("Errore durante la validazione di una particella. Metodo: VerificaParticella, modulo: SitInitMapGuide. " + ex.Message + "\r\n Query: " + this.sCommandText);
            }
            finally
            {
                if (pDataReader != null)
                    pDataReader.Close();
                this.InternalDatabaseConnection.Connection.Close();
            }

            return pRetSit;
        }

        protected override string GetCivico()
        {
            string sRetVal = "";
            IDataReader pDataReader = null;

            try
            {
                sRetVal = this.GetCivici_All("TEXT", pDataReader, TipoQuery.Elenco);
            }
            catch (CatastoException ex)
            {
            }
            catch (Exception ex)
            {
                throw new Exception("Errore durante la restituzione di un civico. Metodo: GetCivico, modulo: SitInitMapGuide. " + ex.Message + "\r\n Query: " + this.sCommandText);
            }
            finally
            {
                if (pDataReader != null)
                    pDataReader.Close();
                this.InternalDatabaseConnection.Connection.Close();
            }

            return sRetVal;
        }

        protected override RetSit VerificaCivico()
        {
            RetSit pRetSit;

            if (Init.Utils.StringChecker.IsNumeric(this.DataSit.Civico))
            {
                IDataReader pDataReader = null;

                try
                {
                    string sElem = this.GetCivici_All("TEXT", pDataReader, TipoQuery.Validazione);
                    if (!String.IsNullOrEmpty(sElem))
                    {
                        pRetSit = new RetSit(true);
                        this.DataSit.Civico = sElem;
                    }
                    else
                    {
                        pRetSit = this.RestituisciErroreSit(this.GetMessageValidate("TEXT"), MessageCode.CivicoValidazione, false);
                    }
                }
                catch (CatastoException ex)
                {
                    pRetSit = this.RestituisciErroreSit(ex.Message, MessageCode.CivicoValidazione, false);
                }
                catch (Exception ex)
                {
                    throw new Exception("Errore durante la validazione di un civico. Metodo: VerificaCivico, modulo: SitInitMapGuide. " + ex.Message + "\r\n Query: " + this.sCommandText);
                }
                finally
                {
                    if (pDataReader != null)
                        pDataReader.Close();
                    this.InternalDatabaseConnection.Connection.Close();
                }
            }
            else
            {
                pRetSit = this.RestituisciErroreSit("Non è possibile validare il civico " + this.DataSit.Civico + " perchè non è un numero", MessageCode.CivicoValidazioneNumero, false);
            }

            return pRetSit;
        }



        protected override string GetCodCivico()
        {
            string sRetVal = "";
            IDataReader pDataReader = null;

            try
            {
                sRetVal = this.GetCivici_All("FEATID", pDataReader, TipoQuery.Elenco);
            }
            catch (CatastoException ex)
            {
            }
            catch (Exception ex)
            {
                throw new Exception("Errore durante la restituzione di un codice civico. Metodo: GetCodCivico, modulo: SitInitMapGuide. " + ex.Message + "\r\n Query: " + this.sCommandText);
            }
            finally
            {
                if (pDataReader != null)
                    pDataReader.Close();
                this.InternalDatabaseConnection.Connection.Close();
            }

            return sRetVal;
        }




        protected override string GetCodVia()
        {
            string sRetVal = "";
            IDataReader pDataReader = null;

            try
            {
                sRetVal = this.GetCivici_All("COD_VIA", pDataReader, TipoQuery.Elenco);
            }
            catch (CatastoException ex)
            {
            }
            catch (Exception ex)
            {
                throw new Exception("Errore durante la restituzione di un codice via. Metodo: GetCodVia, modulo: SitInitMapGuide. " + ex.Message + "\r\n Query: " + this.sCommandText);
            }
            finally
            {
                if (pDataReader != null)
                    pDataReader.Close();
                this.InternalDatabaseConnection.Connection.Close();
            }

            return sRetVal;
        }

        #endregion

        public override string[] GetListaCampiGestiti()
        {
            return new string[]{
				//SitMgr.NomiCampiSit.Esponente,
				SitIntegrationService.NomiCampiSit.Frazione,
				//SitMgr.NomiCampiSit.Circoscrizione,
				//SitMgr.NomiCampiSit.Interno,
				//SitMgr.NomiCampiSit.EsponenteInterno,
				//SitMgr.NomiCampiSit.Fabbricato,
				SitIntegrationService.NomiCampiSit.Sezione,
                SitIntegrationService.NomiCampiSit.TipoCatasto,
                SitIntegrationService.NomiCampiSit.Foglio,
                SitIntegrationService.NomiCampiSit.Particella,
				//SitMgr.NomiCampiSit.Sub,
				//SitMgr.NomiCampiSit.UnitaImmobiliare,
				SitIntegrationService.NomiCampiSit.Civico,
				//SitMgr.NomiCampiSit.Cap,
				SitIntegrationService.NomiCampiSit.CodiceVia,
                SitIntegrationService.NomiCampiSit.CodiceCivico
            };
        }
    }
}
