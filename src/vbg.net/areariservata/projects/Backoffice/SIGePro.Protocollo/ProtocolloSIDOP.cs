using System;
using System.Data;
using System.IO;
using Init.SIGePro.Verticalizzazioni;
using Init.Utils;
using PersonalLib2.Data;
using Init.SIGePro.Manager;
using Init.SIGePro.Data;
using System.Data.OleDb;
using System.Configuration;
using System.Text;
using System.Security;
using System.Runtime.InteropServices;
using Init.SIGePro.Authentication;
using System.Collections.Generic;
using Init.SIGePro.Exceptions.Protocollo;
using log4net;
using Init.SIGePro.Protocollo.Data;
using Init.SIGePro.Protocollo.Logs;
using Init.SIGePro.Protocollo.WsDataClass;

namespace Init.SIGePro.Protocollo
{
    /// <summary>
    /// Descrizione di riepilogo per ProtocolloSIDOP.
    /// </summary>
    public class PROTOCOLLO_SIDOP : ProtocolloBase
    {

        #region Costruttori
        public PROTOCOLLO_SIDOP()
        {
        }
        #endregion

        #region Membri privati
        private string _ConnectionString = "";
        private string _Provider = "";
        private string _StoredProcedure_Prot = "";
        private string _StoredProcedure_Etic = "";
        private string _IdInd = "";
        private int _AnnoFasc;
        private string _ProgrFasc = "";
        private string _PathPrinterExe = "";
        private string _View = "";
        private ILog _log = LogManager.GetLogger(typeof(PROTOCOLLO_SIDOP));
        private DataBase db;
        ProtocolloVista pProtVista;

        //Solo test
        //private string sNumProt;
        //private string sDataProt;
        //private int iNumCopie;
        //private string sStamp;
        //Solo test

        #endregion

        #region Metodi pubblici e privati della classe

        #region Metodi per la stampa di etichette

        public override DatiEtichette StampaEtichette(string idProtocollo, DateTime? dataProtocollo, string numeroProtocollo, int numeroCopie, string stampante)
        {
            string sCommand = "";
            DatiEtichette datiEtichette = new DatiEtichette();

            try
            {
                GetParametriFromVertSIDOP();

                string[] sNumProtSplit = numeroProtocollo.Split(new Char[] { '/' });
                NumProtocollo = sNumProtSplit[0];

                DataProtocollo = dataProtocollo;
                NumeroCopie = numeroCopie;
                Stampante = stampante;

                //Viene chiamata la stored procedure per la stampa delle etichette
                sCommand = RunStoredProcedureStampaEtich();

                if (String.IsNullOrEmpty(sCommand))
                    throw new Exception("Errore generato durante l'esecuzione della stored procedure");

                return datiEtichette;
            }
            catch (Exception ex)
            {
                throw _protocolloLogs.LogErrorException(String.Format("ERRORE GENERATO DURANTE LA STAMPA DELLE ETICHETTE ESEGUITA (PER ULTERIORI INFORMAZIONI GUARDARE IL FILE: {0})", Path.Combine(_protocolloLogs.Folder, "BatchFiles", "CMDFILE.ERR")), ex);
            }
        }

        private string RunStoredProcedureStampaEtich()
        {
            IDbCommand pCmd = null;
            string sCommand;

            try
            {
                _protocolloLogs.Debug("Stato Connessione db : " + db.Connection.ConnectionString);

                //Implementazione della chiamata alla stored procedure
                db.Connection.Open();

                _protocolloLogs.Debug("Stato Connessione db : " + db.Connection.State.ToString());

                //Creazione di un oggetto command che usa una stored procedure
                pCmd = CreateCommand(TipoStoredProcedure.STAMPA_ETICHETTE);
                //Creazione degli oggetti parameter necessari per la stored procedure
                AddParameters(pCmd);

                _protocolloLogs.Debug("Esegui la stored procedure");

                //Esecuzione della stored procedure
                pCmd.ExecuteNonQuery();
                _protocolloLogs.Debug("Stored procedure eseguita");

                //Ricavo il comando batch dalla stored procedure
                if (((IDbDataParameter)pCmd.Parameters["STRINGA_CMD"]).Value != DBNull.Value)
                    sCommand = Convert.ToString(((IDbDataParameter)pCmd.Parameters["STRINGA_CMD"]).Value);
                else
                    sCommand = "";

                var directory = Path.Combine(_protocolloLogs.Folder, "BatchFiles");

                if (!Directory.Exists(directory))
                    Directory.CreateDirectory(directory);

                int iFrsInd = sCommand.IndexOf("PRINTER");
                iFrsInd = sCommand.IndexOf('\"', iFrsInd);

                //int iFrsInd = sCommand.IndexOf("PRINTER          = ");
                int iScnInd = sCommand.IndexOf('\"', iFrsInd + 1);
                string sStampanteCmd = sCommand.Substring(iFrsInd + 1, iScnInd - iFrsInd - 1);
                //int iScnInd = sCommand.IndexOf('\"', iFrsInd + "PRINTER          = ".Length);
                //string sStampanteCmd = sCommand.Substring(iFrsInd + "PRINTER          = ".Length, iScnInd - iFrsInd - "PRINTER	      = ".Length);
                sCommand = sCommand.Replace(sStampanteCmd, Stampante);

                _protocolloLogs.Debug("File : " + sCommand);
                
                string sFileName = Path.Combine(directory, String.Concat("EtichettaSidop_", NumProtocollo, "_", DataProtocollo.Value.ToString("ddMMyyyy"), ".cmd"));
                StreamUtils.StreamToFile(StreamUtils.StringToStream(sCommand), sFileName);

                StartProcess(sFileName);

            }
            catch (Exception ex)
            {
                throw ex;
            }
            finally
            {
                if (pCmd != null)
                    pCmd.Dispose();
                db.Connection.Close();
            }
            return sCommand;
        }



        private void StartProcess(string sFileName)
        {
            try
            {
                string sBat = _PathPrinterExe + " /CMD " + sFileName;
                // Get the full file path
                string strFilePath = Path.Combine(_protocolloLogs.Folder, "BatchFiles", String.Concat("StampaEtichetta_", NumProtocollo, "_", DataProtocollo.Value.ToString("ddMMyyyy"), ".bat"));
                StreamUtils.StreamToFile(StreamUtils.StringToStream(sBat), strFilePath);

                _protocolloLogs.InfoFormat("Inizio funzionalità di stampa etichette, file cmd: {0}, file bat: {1}", sBat, strFilePath);

                // Create the ProcessInfo object
                System.Diagnostics.ProcessStartInfo psi = new System.Diagnostics.ProcessStartInfo("cmd.exe");

                psi.UseShellExecute = false;
                psi.RedirectStandardOutput = true;
                psi.RedirectStandardInput = true;
                psi.RedirectStandardError = true;
                psi.WorkingDirectory = Path.Combine(_protocolloLogs.Folder, "BatchFiles");
                //psi.Domain = ConfigurationManager.AppSettings["Domain"];
                //psi.UserName = ConfigurationManager.AppSettings["UserName"];
                //SecureString pwdString = new SecureString();
                //foreach (char c in ConfigurationManager.AppSettings["Password"].ToString())
                //    pwdString.AppendChar(c);
                //psi.Password = pwdString;


                // Start the process
                System.Diagnostics.Process proc = System.Diagnostics.Process.Start(psi);

                // Open the batch file for reading
                System.IO.StreamReader strm = System.IO.File.OpenText(strFilePath);

                // Attach the output for reading
                System.IO.StreamReader sOut = proc.StandardOutput;

                // Attach the in for writing
                System.IO.StreamWriter sIn = proc.StandardInput;


                // Write each line of the batch file to standard input
                while (strm.Peek() != -1)
                {
                    sIn.WriteLine(strm.ReadLine());
                }

                strm.Close();

                // Exit CMD.EXE
                string stEchoFmt = "# {0} run successfully. Exiting";

                sIn.WriteLine(String.Format(stEchoFmt, strFilePath));
                sIn.WriteLine("EXIT");

                // Close the process
                proc.Close();

                // Read the sOut to a string.
                string results = sOut.ReadToEnd().Trim();


                // Close the io Streams;
                sIn.Close();
                sOut.Close();

                _protocolloLogs.Info("Funzionalità stampa etichette avvenuta con successo");
            }
            catch (Exception ex)
            {
                throw new Exception("ERRORE VERIFICATO DURANTE LA STAMPA DELLE ETICHETTE", ex);
            }
        }

        #endregion

        #region Metodi di protocollazione
        public override DatiProtocolloRes Protocollazione(Data.DatiProtocolloIn protoIn)
        {
            DatiProtocolloRes protoRes = null;
            string sMessage = string.Empty;

            try
            {
                GetParametriFromVertSIDOP();

                //Viene chiamata la stored procedure per la protocollazione
                ProtocolloOutStoredProcedure pProtOut = RunStoredProcedureProt(protoIn);

                if (!String.IsNullOrEmpty(pProtOut.NumeroProtocollo))
                    protoRes = CreaDatiProtocollo(pProtOut);
                else
                    throw new Exception(String.Format("CODICE ERRORE: {0}, MESSAGGIO ERRORE: {1} ", pProtOut.ErrorCode, pProtOut.ErrorMsg));

                return protoRes;
            }
            catch (Exception ex)
            {
                throw _protocolloLogs.LogErrorException("ERRORE GENERATO DURANTE LA PROTOCOLLAZIONE", ex);
            }
        }

        private ProtocolloOutStoredProcedure RunStoredProcedureProt(DatiProtocolloIn pProt)
        {
            var pProtOut = new ProtocolloOutStoredProcedure();
            IDbCommand pCmd = null;

            try
            {
                //Implementazione della chiamata alla stored procedure
                db.Connection.Open();
                //Recupero dei dati dalla vista CPG_UTENTI_EDILIZIA
                pProtVista = GetFromVista();
                _log.Debug("pProtVista is null? " + (pProtVista == null));
                //Recupero mittenti (protocollo in arrivo) destinatari (protocollo in uscita)
                ProtocolloMittDest pProtMittDest = new ProtocolloMittDest();
                SetMittenti(pProt, pProtMittDest);
                SetDestinatari(pProt, pProtMittDest);
                //Recupero allegati
                ProtocolloAll pProtAllegati = new ProtocolloAll();
                SetAllegati(pProt, pProtAllegati);
                //Creazione di un oggetto command che usa una stored procedure
                pCmd = CreateCommand(TipoStoredProcedure.PROTOCOLLAZIONE);
                //Creazione degli oggetti parameter necessari per la stored procedure
                
                _protocolloSerializer.Serialize(ProtocolloLogsConstants.MittentiDestinatariDbRequestFileName, pProtMittDest);

                AddParameters(pCmd, pProtVista, pProtMittDest, pProt);
                _protocolloLogs.InfoFormat("Richiesta di protocollazione, dati vista: {0}, dati mittenti / destinatari: {1}, query: {2}", ProtocolloLogsConstants.VistaDbRequestFileName, ProtocolloLogsConstants.MittentiDestinatariDbRequestFileName, pCmd.CommandText);

                //Esecuzione della stored procedure
                pCmd.ExecuteNonQuery();

                //Setto le proprietà dell'oggetto ProtocolloOutStoredProcedure
                if (((IDbDataParameter)pCmd.Parameters["NumProtOut"]).Value != DBNull.Value)
                    pProtOut.NumeroProtocollo = Convert.ToString(((IDbDataParameter)pCmd.Parameters["NumProtOut"]).Value);
                else
                    pProtOut.NumeroProtocollo = "";
                
                _protocolloLogs.Debug("Numero protocollo: " + pProtOut.NumeroProtocollo);

                if (((IDbDataParameter)pCmd.Parameters["DtRegOut"]).Value != DBNull.Value)
                    pProtOut.DataProtocollo = Convert.ToDateTime(((IDbDataParameter)pCmd.Parameters["DtRegOut"]).Value);
                else
                    pProtOut.DataProtocollo = DateTime.MinValue;
                
                _protocolloLogs.Debug("Data protocollo: " + pProtOut.DataProtocollo.ToString("dd/MM/yyyy"));

                if (((IDbDataParameter)pCmd.Parameters["ErrCode"]).Value != DBNull.Value)
                    pProtOut.ErrorCode = Convert.ToString(((IDbDataParameter)pCmd.Parameters["ErrCode"]).Value);
                else
                    pProtOut.ErrorCode = "";
                
                _protocolloLogs.Debug("Error code: " + pProtOut.ErrorCode);

                if (((IDbDataParameter)pCmd.Parameters["ErrMsg"]).Value != DBNull.Value)
                    pProtOut.ErrorMsg = Convert.ToString(((IDbDataParameter)pCmd.Parameters["ErrMsg"]).Value);
                else
                    pProtOut.ErrorMsg = "";
                
                _protocolloLogs.Debug("Error message: " + pProtOut.ErrorMsg); 

                if (((IDbDataParameter)pCmd.Parameters["RETURN_VALUE"]).Value != DBNull.Value)
                    pProtOut.ReturnValue = Convert.ToInt32(((IDbDataParameter)pCmd.Parameters["RETURN_VALUE"]).Value);
                else
                    pProtOut.ReturnValue = -1;

                string strXmlResponse = _protocolloSerializer.Serialize(ProtocolloLogsConstants.ProtocolloOutputDbRequestFileName, pProtOut);
                _protocolloLogs.InfoFormat("Valore dei parametri di output dopo la protocollazione: file {0}, xml: {1}", ProtocolloLogsConstants.ProtocolloOutputDbRequestFileName, strXmlResponse);

                _protocolloLogs.Debug("Return value: " + pProtOut.ReturnValue.ToString());
            }
            catch (Exception ex)
            {
                throw new Exception("ERRORE GENERATO DURANTE L'ESECUZIONE DELLA STORED PROCEDURE DI PROTOCOLLAZIONE", ex);
            }
            finally
            {
                if (pCmd != null)
                    pCmd.Dispose();
                db.Connection.Close();
            }

            return pProtOut;
        }

        private IDbCommand CreateCommand(TipoStoredProcedure eTipoStoredProc)
        {
            IDbCommand pCmd = db.CreateCommand();
            pCmd.CommandType = CommandType.StoredProcedure;
            switch (eTipoStoredProc)
            {
                case TipoStoredProcedure.PROTOCOLLAZIONE:
                    pCmd.CommandText = _StoredProcedure_Prot;
                    break;
                case TipoStoredProcedure.STAMPA_ETICHETTE:
                    pCmd.CommandText = _StoredProcedure_Etic;
                    break;
            }
            if (_protocolloLogs.IsDebugEnabled)
                _protocolloLogs.DebugFormat("Parametro: STOREDPROCEDURE, Valore: {0}", pCmd.CommandText);

            return pCmd;
        }

        private void AddParameters(IDbCommand pCmd)
        {
            //ReturnValue
            pCmd.Parameters.Add(AddParameter("RETURN_VALUE", DbType.Int32, 1, ParameterDirection.ReturnValue));
            //Numero protocollo
            pCmd.Parameters.Add(AddParameter("NumDOC", DbType.Decimal, 7, ParameterDirection.Input, Convert.ToDecimal(NumProtocollo)));
            //Data protocollo
            pCmd.Parameters.Add(AddParameter("dtdoc", DbType.DateTime, 12, ParameterDirection.Input, DataProtocollo.Value));
            //Etichetta integrale
            pCmd.Parameters.Add(AddParameter("ETICH_INTEGR", DbType.AnsiString, 1, ParameterDirection.Input, "N"));
            //Flag no destinatario
            pCmd.Parameters.Add(AddParameter("Flg_no_dest", DbType.AnsiString, 1, ParameterDirection.Input, "N"));
            //Numero copie
            pCmd.Parameters.Add(AddParameter("NRO_COPIE", DbType.Decimal, 3, ParameterDirection.Input, Convert.ToDecimal(NumeroCopie)));
            //String cmd
            pCmd.Parameters.Add(AddParameter("STRINGA_CMD", DbType.AnsiString, 2000, ParameterDirection.Output));
        }


        private void AddParameters(IDbCommand pCmd, ProtocolloVista pProtVista, ProtocolloMittDest pProtMittDest, Data.DatiProtocolloIn pProt)
        {
            //ReturnValue
            pCmd.Parameters.Add(AddParameter("RETURN_VALUE", DbType.Int32, 1, ParameterDirection.ReturnValue));
            //IdUteIn
            pCmd.Parameters.Add(AddParameter("IdUteIn", DbType.Decimal, 6, ParameterDirection.Input, Convert.ToDecimal(pProtVista.IdUteIn)));
            //IdUOIn
            pCmd.Parameters.Add(AddParameter("IdUOIn", DbType.Decimal, 8, ParameterDirection.Input, Convert.ToDecimal(pProtVista.IdUOIn)));
            //NumProtOut
            pCmd.Parameters.Add(AddParameter("NumProtOut", DbType.Int32, 7, ParameterDirection.Output));
            //DtRegOut
            pCmd.Parameters.Add(AddParameter("DtRegOut", DbType.DateTime, 12, ParameterDirection.Output));
            //DtArrivoIn
            pCmd.Parameters.Add(AddParameter("DtArrivoIn", DbType.DateTime, 12, ParameterDirection.Input, this.DatiProtocollo.Istanza.DATA));
            //FlgComplIn
            pCmd.Parameters.Add(AddParameter("FlgComplIn", DbType.AnsiString, 1, ParameterDirection.Input, "N"));
            //FlgRsvIn
            pCmd.Parameters.Add(AddParameter("FlgRsvIn", DbType.AnsiString, 1, ParameterDirection.Input, "N"));
            //FlgEvdIn
            pCmd.Parameters.Add(AddParameter("FlgEvdIn", DbType.AnsiString, 1, ParameterDirection.Input, "N"));
            //IdTpFisIn
            pCmd.Parameters.Add(AddParameter("IdTpFisIn", DbType.Int32, 4, ParameterDirection.Input, Convert.ToInt32(pProt.TipoDocumento)));
            //IdTpLogIn
            pCmd.Parameters.Add(AddParameter("IdTpLogIn", DbType.Int32, 4, ParameterDirection.Input, DBNull.Value));
            //IdSttpLogIn
            pCmd.Parameters.Add(AddParameter("IdSttpLogIn", DbType.Int32, 4, ParameterDirection.Input, DBNull.Value));
            //TxtOggIn
            pCmd.Parameters.Add(AddParameter("TxtOggIn", DbType.AnsiString, 500, ParameterDirection.Input, pProt.Oggetto));
            //NoteIn
            pCmd.Parameters.Add(AddParameter("NoteIn", DbType.AnsiString, 250, ParameterDirection.Input, DBNull.Value));
            //RifProvIn
            pCmd.Parameters.Add(AddParameter("RifProvIn", DbType.AnsiString, 12, ParameterDirection.Input, DBNull.Value));
            //ProtProvIn
            pCmd.Parameters.Add(AddParameter("ProtProvIn", DbType.AnsiString, 20, ParameterDirection.Input, DBNull.Value));
            //DtProvIn
            pCmd.Parameters.Add(AddParameter("DtProvIn", DbType.DateTime, 12, ParameterDirection.Input, DBNull.Value));
            //FlgNoPubblIn
            pCmd.Parameters.Add(AddParameter("FlgNoPubblIn", DbType.AnsiString, 1, ParameterDirection.Input, "N"));
            //DtTermNoPubblIn
            pCmd.Parameters.Add(AddParameter("DtTermNoPubblIn", DbType.DateTime, 12, ParameterDirection.Input, DBNull.Value));
            //IdEsibDestArrIn
            pCmd.Parameters.Add(AddParameter("V_IdEsibDestArrIn", DbType.AnsiString, 255, ParameterDirection.Input, pProtMittDest.Id));
            //FlgPDEsibDestArrIn
            pCmd.Parameters.Add(AddParameter("V_FlgPDEsibDestArrIn", DbType.AnsiString, 255, ParameterDirection.Input, pProtMittDest.FlgPD));
            //DesEsibDestArrIn
            pCmd.Parameters.Add(AddParameter("V_DesEsibDestArrIn", DbType.AnsiString, 255, ParameterDirection.Input, pProtMittDest.Des));
            //CodFisEsibDestArrIn
            pCmd.Parameters.Add(AddParameter("V_CodFisEsibDestArrIn", DbType.AnsiString, 255, ParameterDirection.Input, pProtMittDest.CodFis));
            //PivaEsibDestArrIn
            pCmd.Parameters.Add(AddParameter("V_PivaEsibDestArrIn", DbType.AnsiString, 255, ParameterDirection.Input, pProtMittDest.Piva));
            //IndirizziEsibDestArrIn
            pCmd.Parameters.Add(AddParameter("V_IndirizziEsibDestArrIn", DbType.AnsiString, 255, ParameterDirection.Input, pProtMittDest.Ind));
            //FlgDestCopiaArrIn
            pCmd.Parameters.Add(AddParameter("V_FlgDestCopiaArrIn", DbType.AnsiString, 255, ParameterDirection.Input, pProtMittDest.FlgDestCopia));
            //nRimandaOrigIn
            pCmd.Parameters.Add(AddParameter("nRimandaOrigIn", DbType.Int32, 1, ParameterDirection.Input, 0));
            //IdFirmArrIn
            pCmd.Parameters.Add(AddParameter("V_IdFirmArrIn", DbType.AnsiString, 255, ParameterDirection.Input, "0-"));
            //FlgPDFirmArrIn
            pCmd.Parameters.Add(AddParameter("V_FlgPDFirmArrIn", DbType.AnsiString, 255, ParameterDirection.Input, "0-"));
            //DesFirmArrIn
            pCmd.Parameters.Add(AddParameter("V_DesFirmArrIn", DbType.AnsiString, 255, ParameterDirection.Input, "0-"));
            //CodFisFirmArrIn
            pCmd.Parameters.Add(AddParameter("V_CodFisFirmArrIn", DbType.AnsiString, 255, ParameterDirection.Input, "0-"));
            //PivaFirmArrIn
            pCmd.Parameters.Add(AddParameter("V_PivaFirmArrIn", DbType.AnsiString, 255, ParameterDirection.Input, "0-"));
            //IndirizziFirmArrIn
            pCmd.Parameters.Add(AddParameter("V_IndirizziFirmArrIn", DbType.AnsiString, 255, ParameterDirection.Input, "0-"));
            //IdUOProvIO
            switch (pProt.Flusso)
            {
                case "A":
                    pCmd.Parameters.Add(AddParameter("IdUOProvIO", DbType.Int32, 8, ParameterDirection.InputOutput, DBNull.Value));
                    break;
                case "P":
                    pCmd.Parameters.Add(AddParameter("IdUOProvIO", DbType.Int32, 8, ParameterDirection.InputOutput, Convert.ToInt32((pProtMittDest.IdUOProv))));
                    break;
            }
            //SettProvIn
            pCmd.Parameters.Add(AddParameter("SettProvIn", DbType.Int32, 2, ParameterDirection.Input, DBNull.Value));
            //ServProvIn
            pCmd.Parameters.Add(AddParameter("ServProvIn", DbType.Int32, 2, ParameterDirection.Input, DBNull.Value));
            //UOCProvIn
            pCmd.Parameters.Add(AddParameter("UOCProvIn", DbType.Int32, 2, ParameterDirection.Input, DBNull.Value));
            //UOSProvIn
            pCmd.Parameters.Add(AddParameter("UOSProvIn", DbType.Int32, 2, ParameterDirection.Input, DBNull.Value));
            //PostProvIn
            pCmd.Parameters.Add(AddParameter("PostProvIn", DbType.Int32, 4, ParameterDirection.Input, DBNull.Value));
            //CopieArrIn
            pCmd.Parameters.Add(AddParameter("V_CopieArrIn", DbType.AnsiString, 255, ParameterDirection.Input, pProtMittDest.CopieArrIn));
            //CreaSottoFascicolo
            pCmd.Parameters.Add(AddParameter("CreaSottoFasc", DbType.Int32, 1, ParameterDirection.Input, 0));
            //TipoProtIN
            switch (pProt.Flusso)
            {
                case "A":
                    pCmd.Parameters.Add(AddParameter("TipoProtIN", DbType.AnsiString, 1, ParameterDirection.Input, "E"));
                    break;
                case "P":
                    pCmd.Parameters.Add(AddParameter("TipoProtIN", DbType.AnsiString, 1, ParameterDirection.Input, "U"));
                    break;
            }
            //AllegaArrIn
            pCmd.Parameters.Add(AddParameter("V_AllegArrIN", DbType.AnsiString, 255, ParameterDirection.Input, "0-"));
            //ErrCode
            pCmd.Parameters.Add(AddParameter("ErrCode", DbType.Int32, 6, ParameterDirection.Output));
            //ErrMsg
            pCmd.Parameters.Add(AddParameter("ErrMsg", DbType.AnsiString, 255, ParameterDirection.Output));
        }

        private IDbDataParameter AddParameter(string sNameParameter, DbType eType, int iSize, ParameterDirection eParameterDir, object oValueParameter)
        {
            IDbDataParameter pParam = AddParameter(sNameParameter, eType, iSize, eParameterDir);
            pParam.Value = oValueParameter;

            if (_protocolloLogs.IsDebugEnabled)
                _protocolloLogs.Debug("Parametro: " + sNameParameter + ", Valore: " + oValueParameter.ToString());

            return pParam;
        }


        private IDbDataParameter AddParameter(string sNameParameter, DbType eType, int iSize, ParameterDirection eParameterDir)
        {
            DataProviderFactory pDataProvFactory = new DataProviderFactory(db.Connection);
            IDbDataParameter pParam = pDataProvFactory.CreateDataParameter(sNameParameter, eType, iSize);
            pParam.Direction = eParameterDir;

            return pParam;
        }

        private void SetMittenti(Data.DatiProtocolloIn pValueParameter, ProtocolloMittDest pProtMittDest)
        {
            //Verifico le amministrazioni interne ed esterne
            if (pValueParameter.Mittenti.Amministrazione.Count >= 1)
            {
                if (!String.IsNullOrEmpty(pValueParameter.Mittenti.Amministrazione[0].PROT_UO))
                {
                    //CopieArrIn
                    //pProtMittDest.CopieArrIn = "1-S§§§§§§§342§2001§1§;";
                    //pProtMittDest.CopieArrIn = "1-S§§§§§§§" + _IdInd + "§" + _AnnoFasc.ToString() + "§" + _ProgrFasc + "§;";
                    pProtMittDest.CopieArrIn = "1-S§§§§§§§" + pValueParameter.Classifica + "§" + _AnnoFasc.ToString() + "§" + _ProgrFasc + "§;";
                    //IdUOProvIO
                    pProtMittDest.IdUOProv = pValueParameter.Mittenti.Amministrazione[0].PROT_UO;
                }
                else if (!String.IsNullOrEmpty(pValueParameter.Mittenti.Amministrazione[0].PROT_RUOLO))
                {
                    //CopieArrIn
                    //pProtMittDest.CopieArrIn = "1-S§§§§§§§342§2001§1§;";
                    //pProtMittDest.CopieArrIn = "1-S§§§§§§§" + _IdInd + "§" + _AnnoFasc.ToString() + "§" + _ProgrFasc + "§;";
                    pProtMittDest.CopieArrIn = "1-S§§§§§§§" + pValueParameter.Classifica + "§" + _AnnoFasc.ToString() + "§" + _ProgrFasc + "§;";
                    //IdUOProvIO
                    pProtMittDest.IdUOProv = pValueParameter.Mittenti.Amministrazione[0].PROT_RUOLO;
                }
                else
                {
                    pProtMittDest.Count = pValueParameter.Mittenti.Amministrazione.Count;
                    if (pValueParameter.Mittenti.Anagrafe != null)
                        pProtMittDest.Count += pValueParameter.Mittenti.Anagrafe.Count;

                    string sCount = pProtMittDest.Count.ToString() + "-";

                    //IdEsibDestArrIn
                    pProtMittDest.Id = sCount;
                    for (int iCount = 0; iCount < pProtMittDest.Count; iCount++)
                    {
                        pProtMittDest.Id += ";";
                    }
                    //FlgPDEsibDestArrIn
                    pProtMittDest.FlgPD = sCount;
                    for (int iCount = 0; iCount < pValueParameter.Mittenti.Amministrazione.Count; iCount++)
                    {
                        pProtMittDest.FlgPD += "D;";
                    }
                    //DesEsibDestArrIn
                    pProtMittDest.Des = sCount;
                    foreach (Amministrazioni pAmministrazioni in pValueParameter.Mittenti.Amministrazione)
                    {
                        pProtMittDest.Des += "§§" + pAmministrazioni.AMMINISTRAZIONE + ";";
                    }
                    //CodFisEsibDestArrIn
                    pProtMittDest.CodFis = sCount;
                    foreach (Amministrazioni pAmministrazioni in pValueParameter.Mittenti.Amministrazione)
                    {
                        pProtMittDest.CodFis += pAmministrazioni.PARTITAIVA + ";"; //Modifica problema CF vuoto
                    }
                    //PivaEsibDestArrIn
                    pProtMittDest.Piva = sCount;
                    foreach (Amministrazioni pAmministrazioni in pValueParameter.Mittenti.Amministrazione)
                    {
                        pProtMittDest.Piva += pAmministrazioni.PARTITAIVA + ";";
                    }

                    //IndirizziEsibDestArrIn
                    pProtMittDest.Ind = sCount;
                    foreach (Amministrazioni pAmministrazioni in pValueParameter.Mittenti.Amministrazione)
                    {
                        //Inseriamo la città laddove ci dovrebbe essere la descrizione del comune
                        pProtMittDest.Ind += "§§§" + pAmministrazioni.INDIRIZZO + "§§" + pAmministrazioni.CITTA + "§" + pAmministrazioni.CAP + ";";
                    }

                    //FlgDestCopiaArrIn
                    pProtMittDest.FlgDestCopia = sCount;
                    switch (pValueParameter.Flusso)
                    {
                        case "A":
                            for (int iCount = 0; iCount < pProtMittDest.Count; iCount++)
                            {
                                pProtMittDest.FlgDestCopia += "N;";
                            }
                            break;
                        case "P":
                            for (int iCount = 0; iCount < pProtMittDest.Count; iCount++)
                            {
                                pProtMittDest.FlgDestCopia += "S;";
                            }
                            break;
                    }
                }
            }

            if (pValueParameter.Mittenti.Anagrafe.Count >= 1)
            {
                if (pProtMittDest.Count == 0)
                {
                    pProtMittDest.Count = pValueParameter.Mittenti.Anagrafe.Count;

                    string sCount = pProtMittDest.Count.ToString() + "-";

                    //IdEsibDestArrIn
                    pProtMittDest.Id = sCount;
                    for (int iCount = 0; iCount < pProtMittDest.Count; iCount++)
                    {
                        pProtMittDest.Id += ";";
                    }
                    //FlgPDEsibDestArrIn
                    pProtMittDest.FlgPD = sCount;
                    //DesEsibDestArrIn
                    pProtMittDest.Des = sCount;
                    //CodFisEsibDestArrIn
                    pProtMittDest.CodFis = sCount;
                    //PivaEsibDestArrIn
                    pProtMittDest.Piva = sCount;
                    //IndirizziEsibDestArrIn
                    pProtMittDest.Ind = sCount;
                    //FlgDestCopiaArrIn
                    pProtMittDest.FlgDestCopia = sCount;
                    switch (pValueParameter.Flusso)
                    {
                        case "A":
                            for (int iCount = 0; iCount < pProtMittDest.Count; iCount++)
                            {
                                pProtMittDest.FlgDestCopia += "N;";
                            }
                            break;
                        case "P":
                            for (int iCount = 0; iCount < pProtMittDest.Count; iCount++)
                            {
                                pProtMittDest.FlgDestCopia += "S;";
                            }
                            break;
                    }
                }
                //FlgPDEsibDestArrIn
                foreach (ProtocolloAnagrafe pAnagrafe in pValueParameter.Mittenti.Anagrafe)
                {
                    if (pAnagrafe.TIPOANAGRAFE == "F")
                        pProtMittDest.FlgPD += "P;";
                    if (pAnagrafe.TIPOANAGRAFE == "G")
                        pProtMittDest.FlgPD += "D;";
                }
                //DesEsibDestArrIn
                foreach (ProtocolloAnagrafe pAnagrafe in pValueParameter.Mittenti.Anagrafe)
                {
                    if (pAnagrafe.TIPOANAGRAFE == "F")
                        pProtMittDest.Des += pAnagrafe.NOMINATIVO + "§" + pAnagrafe.NOME + "§;";
                    if (pAnagrafe.TIPOANAGRAFE == "G")
                        pProtMittDest.Des += "§§" + pAnagrafe.NOMINATIVO + ";";
                }
                //CodFisEsibDestArrIn
                foreach (ProtocolloAnagrafe pAnagrafe in pValueParameter.Mittenti.Anagrafe)
                {
                    if (pAnagrafe.TIPOANAGRAFE == "F")
                        pProtMittDest.CodFis += pAnagrafe.CODICEFISCALE + ";";
                    if (pAnagrafe.TIPOANAGRAFE == "G")
                        pProtMittDest.CodFis += !string.IsNullOrEmpty(pAnagrafe.CODICEFISCALE) ? pAnagrafe.CODICEFISCALE : pAnagrafe.PARTITAIVA + ";"; //Modifica problema CF vuoto
                }
                //PivaEsibDestArrIn
                foreach (ProtocolloAnagrafe pAnagrafe in pValueParameter.Mittenti.Anagrafe)
                {
                    if (pAnagrafe.TIPOANAGRAFE == "F")
                        pProtMittDest.Piva += pAnagrafe.PARTITAIVA + ";";
                    if (pAnagrafe.TIPOANAGRAFE == "G")
                        pProtMittDest.Piva += pAnagrafe.PARTITAIVA + ";";
                }

                //IndirizziEsibDestArrIn
                foreach (ProtocolloAnagrafe pAnagrafe in pValueParameter.Mittenti.Anagrafe)
                {
                    Comuni pComuni;
                    if (!String.IsNullOrEmpty(pAnagrafe.COMUNERESIDENZA))
                    {
                        ComuniMgr pComuniMgr = new ComuniMgr(this.DatiProtocollo.Db);
                        pComuni = pComuniMgr.GetById(pAnagrafe.COMUNERESIDENZA);
                    }
                    else
                    {
                        pComuni = new Comuni();
                        pComuni.COMUNE = "";
                    }

                    if (string.IsNullOrEmpty(pAnagrafe.INDIRIZZO) || string.IsNullOrEmpty(pAnagrafe.CodiceIstatComRes) || string.IsNullOrEmpty(pComuni.COMUNE) || string.IsNullOrEmpty(pAnagrafe.CAP))
                    {
                        pProtMittDest.Ind += "§§§" + "§" + "§" + "§" + ";";
                    }
                    else
                    {
                        if (pAnagrafe.CodiceIstatComRes == "054039" || pAnagrafe.CodiceIstatComRes == "54039")
                            pProtMittDest.Ind += "§§§" + "§" + "§" + "§" + ";";
                        else
                        {

                            pProtMittDest.Ind += "§§§" + pAnagrafe.INDIRIZZO + "§" + pAnagrafe.CodiceIstatComRes + "§" + pComuni.COMUNE + "§" + pAnagrafe.CAP + ";";
                        }
                    }
                }
            }
        }

        private void SetDestinatari(Data.DatiProtocolloIn pValueParameter, ProtocolloMittDest pProtMittDest)
        {
            //Verifico le amministrazioni interne ed esterne
            if (pValueParameter.Destinatari.Amministrazione.Count >= 1)
            {
                if (!String.IsNullOrEmpty(pValueParameter.Destinatari.Amministrazione[0].PROT_UO))
                {
                    //CopieArrIn
                    //Per i seguenti parametri si usano dei valori di test (si deve stabilire la soluzione da utilizzare)
                    //-IdInd : 342
                    //-AnnoFasc : 2001
                    //-ProgrFasc : 1
                    //pProtMittDest.CopieArrIn = "1-S§§"+pProtVista.SettAss+"§"+pProtVista.ServAss+"§"+pProtVista.UOCAss+"§"+pProtVista.UOSAss+"§"+pProtVista.PostAss+"§342§2001§1§;";
                    //pProtMittDest.CopieArrIn = "1-S§§" + pProtVista.SettAss + "§" + pProtVista.ServAss + "§" + pProtVista.UOCAss + "§" + pProtVista.UOSAss + "§" + pProtVista.PostAss + "§" + _IdInd + "§" + _AnnoFasc.ToString() + "§" + _ProgrFasc + "§;";
                    pProtMittDest.CopieArrIn = "1-S§§" + pProtVista.SettAss + "§" + pProtVista.ServAss + "§" + pProtVista.UOCAss + "§" + pProtVista.UOSAss + "§" + pProtVista.PostAss + "§" + pValueParameter.Classifica + "§" + _AnnoFasc.ToString() + "§" + _ProgrFasc + "§;";
                    //IdUOProvIO
                    //pProtMittDest.IdUOProv = DBNull.Value;
                }
                else if (!String.IsNullOrEmpty(pValueParameter.Destinatari.Amministrazione[0].PROT_RUOLO))
                {
                    //CopieArrIn
                    //Per i seguenti parametri si usano dei valori di test (si deve stabilire la soluzione da utilizzare)
                    //-IdInd : 342
                    //-AnnoFasc : 2001
                    //-ProgrFasc : 1
                    //pProtMittDest.CopieArrIn = "1-S§§"+pProtVista.SettAss+"§"+pProtVista.ServAss+"§"+pProtVista.UOCAss+"§"+pProtVista.UOSAss+"§"+pProtVista.PostAss+"§342§2001§1§;";
                    //pProtMittDest.CopieArrIn = "1-S§§" + pProtVista.SettAss + "§" + pProtVista.ServAss + "§" + pProtVista.UOCAss + "§" + pProtVista.UOSAss + "§" + pProtVista.PostAss + "§" + _IdInd + "§" + _AnnoFasc.ToString() + "§" + _ProgrFasc + "§;";
                    pProtMittDest.CopieArrIn = "1-S§§" + pProtVista.SettAss + "§" + pProtVista.ServAss + "§" + pProtVista.UOCAss + "§" + pProtVista.UOSAss + "§" + pProtVista.PostAss + "§" + pValueParameter.Classifica + "§" + _AnnoFasc.ToString() + "§" + _ProgrFasc + "§;";
                    //IdUOProvIO
                    //pProtMittDest.IdUOProv = DBNull.Value;
                }
                else
                {
                    pProtMittDest.Count = pValueParameter.Destinatari.Amministrazione.Count;
                    if (pValueParameter.Destinatari.Anagrafe != null)
                        pProtMittDest.Count += pValueParameter.Destinatari.Anagrafe.Count;

                    string sCount = pProtMittDest.Count.ToString() + "-";

                    //IdEsibDestArrIn
                    pProtMittDest.Id = sCount;
                    for (int iCount = 0; iCount < pProtMittDest.Count; iCount++)
                    {
                        pProtMittDest.Id += ";";
                    }
                    //FlgPDEsibDestArrIn
                    pProtMittDest.FlgPD = sCount;
                    for (int iCount = 0; iCount < pValueParameter.Destinatari.Amministrazione.Count; iCount++)
                    {
                        pProtMittDest.FlgPD += "D;";
                    }
                    //DesEsibDestArrIn
                    pProtMittDest.Des = sCount;
                    foreach (Amministrazioni pAmministrazioni in pValueParameter.Destinatari.Amministrazione)
                    {
                        pProtMittDest.Des += "§§" + pAmministrazioni.AMMINISTRAZIONE + "§;";
                    }
                    //CodFisEsibDestArrIn
                    pProtMittDest.CodFis = sCount;
                    foreach (Amministrazioni pAmministrazioni in pValueParameter.Destinatari.Amministrazione)
                    {
                        pProtMittDest.CodFis += pAmministrazioni.PARTITAIVA + ";";
                    }
                    //PivaEsibDestArrIn
                    pProtMittDest.Piva = sCount;
                    foreach (Amministrazioni pAmministrazioni in pValueParameter.Destinatari.Amministrazione)
                    {
                        pProtMittDest.Piva += pAmministrazioni.PARTITAIVA + ";";
                    }

                    //IndirizziEsibDestArrIn
                    pProtMittDest.Ind = sCount;
                    foreach (Amministrazioni pAmministrazioni in pValueParameter.Destinatari.Amministrazione)
                    {
                        pProtMittDest.Ind += "§§§" + pAmministrazioni.INDIRIZZO + "§§" + pAmministrazioni.CITTA + "§" + pAmministrazioni.CAP + ";";
                    }

                    //FlgDestCopiaArrIn
                    pProtMittDest.FlgDestCopia = sCount;
                    switch (pValueParameter.Flusso)
                    {
                        case "A":
                            for (int iCount = 0; iCount < pProtMittDest.Count; iCount++)
                            {
                                pProtMittDest.FlgDestCopia += "N;";
                            }
                            break;
                        case "P":
                            for (int iCount = 0; iCount < pProtMittDest.Count; iCount++)
                            {
                                pProtMittDest.FlgDestCopia += "S;";
                            }
                            break;
                    }
                }
            }

            if (pValueParameter.Destinatari.Anagrafe.Count >= 1)
            {
                if (pProtMittDest.Count == 0)
                {
                    pProtMittDest.Count = pValueParameter.Destinatari.Anagrafe.Count;

                    string sCount = pProtMittDest.Count.ToString() + "-";

                    //IdEsibDestArrIn
                    pProtMittDest.Id = sCount;
                    for (int iCount = 0; iCount < pProtMittDest.Count; iCount++)
                    {
                        pProtMittDest.Id += ";";
                    }
                    //FlgPDEsibDestArrIn
                    pProtMittDest.FlgPD = sCount;
                    //DesEsibDestArrIn
                    pProtMittDest.Des = sCount;
                    //CodFisEsibDestArrIn
                    pProtMittDest.CodFis = sCount;
                    //PivaEsibDestArrIn
                    pProtMittDest.Piva = sCount;
                    //IndirizziEsibDestArrIn
                    pProtMittDest.Ind = sCount;
                    //FlgDestCopiaArrIn
                    pProtMittDest.FlgDestCopia = sCount;
                    switch (pValueParameter.Flusso)
                    {
                        case "A":
                            for (int iCount = 0; iCount < pProtMittDest.Count; iCount++)
                            {
                                pProtMittDest.FlgDestCopia += "N;";
                            }
                            break;
                        case "P":
                            for (int iCount = 0; iCount < pProtMittDest.Count; iCount++)
                            {
                                pProtMittDest.FlgDestCopia += "S;";
                            }
                            break;
                    }
                }
                //FlgPDEsibDestArrIn
                foreach (ProtocolloAnagrafe pAnagrafe in pValueParameter.Destinatari.Anagrafe)
                {
                    if (pAnagrafe.TIPOANAGRAFE == "F")
                        pProtMittDest.FlgPD += "P;";
                    if (pAnagrafe.TIPOANAGRAFE == "G")
                        pProtMittDest.FlgPD += "D;";
                }
                //DesEsibDestArrIn
                foreach (ProtocolloAnagrafe pAnagrafe in pValueParameter.Destinatari.Anagrafe)
                {
                    if (pAnagrafe.TIPOANAGRAFE == "F")
                        pProtMittDest.Des += pAnagrafe.NOMINATIVO + "§" + pAnagrafe.NOME + "§;";
                    if (pAnagrafe.TIPOANAGRAFE == "G")
                        pProtMittDest.Des += "§§" + pAnagrafe.NOMINATIVO + ";";
                }
                //CodFisEsibDestArrIn
                foreach (ProtocolloAnagrafe pAnagrafe in pValueParameter.Destinatari.Anagrafe)
                {
                    if (pAnagrafe.TIPOANAGRAFE == "F")
                        pProtMittDest.CodFis += pAnagrafe.CODICEFISCALE + ";";
                    if (pAnagrafe.TIPOANAGRAFE == "G")
                        pProtMittDest.CodFis += !string.IsNullOrEmpty(pAnagrafe.CODICEFISCALE) ? pAnagrafe.CODICEFISCALE : pAnagrafe.PARTITAIVA + ";";
                }
                //PivaEsibDestArrIn
                foreach (ProtocolloAnagrafe pAnagrafe in pValueParameter.Destinatari.Anagrafe)
                {
                    if (pAnagrafe.TIPOANAGRAFE == "F")
                        pProtMittDest.Piva += pAnagrafe.PARTITAIVA + ";";
                    if (pAnagrafe.TIPOANAGRAFE == "G")
                        pProtMittDest.Piva += pAnagrafe.PARTITAIVA + ";";
                }

                //IndirizziEsibDestArrIn
                foreach (ProtocolloAnagrafe pAnagrafe in pValueParameter.Destinatari.Anagrafe)
                {

                    Comuni pComuni;
                    if (!String.IsNullOrEmpty(pAnagrafe.COMUNERESIDENZA))
                    {
                        ComuniMgr pComuniMgr = new ComuniMgr(this.DatiProtocollo.Db);
                        pComuni = pComuniMgr.GetById(pAnagrafe.COMUNERESIDENZA);
                    }
                    else
                    {
                        pComuni = new Comuni();
                        pComuni.COMUNE = "";
                    }

                    if (string.IsNullOrEmpty(pAnagrafe.INDIRIZZO) || string.IsNullOrEmpty(pAnagrafe.CodiceIstatComRes) || string.IsNullOrEmpty(pComuni.COMUNE) || string.IsNullOrEmpty(pAnagrafe.CAP))
                    {
                        pProtMittDest.Ind += "§§§" + "§" + "§" + "§" + ";";
                    }
                    else
                    {
                        if (pAnagrafe.CodiceIstatComRes == "054039" || pAnagrafe.CodiceIstatComRes == "54039")
                            pProtMittDest.Ind += "§§§" + "§" + "§" + "§" + ";";
                        else
                        {

                            pProtMittDest.Ind += "§§§" + pAnagrafe.INDIRIZZO + "§" + pAnagrafe.CodiceIstatComRes + "§" + pComuni.COMUNE + "§" + pAnagrafe.CAP + ";";
                        }
                    }
                }
            }
        }

        private void SetAllegati(Data.DatiProtocolloIn pValueParameter, ProtocolloAll pProtMittDest)
        {
            //TODO
        }

        private ProtocolloVista GetFromVista()
        {
            //Rileggo dalla vista informazioni relative all'utente SIDOP che si è loggato in SIGePro
            //- IdUteIn (necessario sempre)
            //- IdUOIn (necessario sempre)
            //- DesUte
            //- SettAss (necessario solo in arrivo)
            //- ServAss (necessario solo in arrivo)
            //- PostAss (necessario solo in arrivo)
            //- UOCAss (necessario solo in arrivo)
            //- UOSAss (necessario solo in arrivo)
            //string sQuery = "select * from " + _View + " where USERID ='" + Operatore + "'";
            string sQuery = "select * from " + _View + " where USERNAME ='" + Operatore + "'";
            _protocolloLogs.InfoFormat("Query di richiesta dati dalla vista {0}, {1}", _View, sQuery);
            IDbCommand pCmdVista = db.CreateCommand(sQuery);
            IDataReader pDataReader = pCmdVista.ExecuteReader();
            ProtocolloVista pProtVista = new ProtocolloVista();
            int iCount = 0;

            if (pDataReader != null)
            {
                while (pDataReader.Read())
                {
                    //pProtVista.IdUteIn = pDataReader["ID_UTEIN"].ToString();
                    //pProtVista.IdUOIn = pDataReader["ID_UOIN"].ToString();
                    //pProtVista.DesUte = pDataReader["DES_UTE"].ToString();
                    //pProtVista.SettAss = pDataReader["SETTASS"].ToString();
                    //pProtVista.ServAss = pDataReader["SERVASS"].ToString();
                    //pProtVista.PostAss = pDataReader["POSTASS"].ToString();
                    //pProtVista.UOCAss = pDataReader["UOCASS"].ToString();
                    //pProtVista.UOSAss = pDataReader["UOSASS"].ToString();

                    pProtVista.IdUteIn = pDataReader["ID_UTE"].ToString();
                    pProtVista.IdUOIn = pDataReader["ID_UO"].ToString();
                    pProtVista.DesUte = pDataReader["DES_UTE"].ToString();
                    pProtVista.SettAss = pDataReader["SETTORE"].ToString();
                    pProtVista.ServAss = pDataReader["SERVIZIO"].ToString();
                    pProtVista.PostAss = pDataReader["POSTAZIONE"].ToString();
                    pProtVista.UOCAss = pDataReader["UOC"].ToString();
                    pProtVista.UOSAss = pDataReader["UOS"].ToString();
                    iCount++;
                }
            }

            if (pDataReader != null)
                pDataReader.Close();

            if (pCmdVista != null)
                pCmdVista.Dispose();

            switch (iCount)
            {
                case 0:
                    //throw new Exception("L'operatore di SIGePro user id " + Operatore + " non è stato trovato tra gli utenti di SIDOP.\r\n");
                    throw new Exception("L'operatore di SIGePro di matricola " + Operatore + " non è stato trovato tra gli utenti di SIDOP.\r\n");
                case 1:
                    break;
                default:
                    //throw new Exception("L'operatore di SIGePro user id " + Operatore + " è stato trovato " + iCount.ToString() + " volte tra gli utenti di SIDOP.\r\n");
                    throw new Exception("L'operatore di SIGePro di matricola " + Operatore + " è stato trovato " + iCount.ToString() + " volte tra gli utenti di SIDOP.\r\n");
            }

            _protocolloSerializer.Serialize(ProtocolloLogsConstants.VistaDbRequestFileName, pProtVista);

            return pProtVista;
        }

        private DatiProtocolloRes CreaDatiProtocollo(ProtocolloOutStoredProcedure response)
        {
            try
            {
                var protoRes = new DatiProtocolloRes();
                protoRes.AnnoProtocollo = response.DataProtocollo.Year.ToString();
                protoRes.DataProtocollo = response.DataProtocollo.ToString("dd/MM/yyyy");

                protoRes.NumeroProtocollo = response.NumeroProtocollo;

                if (ModificaNumero)
                    protoRes.NumeroProtocollo = protoRes.NumeroProtocollo.TrimStart(new char[] { '0' });

                if (AggiungiAnno)
                    protoRes.NumeroProtocollo += "/" + protoRes.AnnoProtocollo;

                _protocolloLogs.InfoFormat("Dati protocollo restituiti, numero: {0}, anno: {1}, data: {2}", protoRes.NumeroProtocollo, protoRes.AnnoProtocollo, protoRes.DataProtocollo);

                return protoRes;
            }
            catch (Exception ex)
            {
                throw new Exception("ERRORE GENERATO DURANTE LA CREAZIONE DEI DATI DI PROTOCOLLO", ex);
            }
        }
        #endregion

        #region Utility
        /// <summary>
        /// Metodo usato per leggere i parametri della verticalizzazione Protocollo SIDOP
        /// </summary>
        private void GetParametriFromVertSIDOP()
        {
            try
            {
                VerticalizzazioneProtocolloSidop protocolloSIDOP;

                //string tSoftware = string.IsNullOrEmpty(Software) ? GetIstanza().SOFTWARE : Software;

                protocolloSIDOP = new VerticalizzazioneProtocolloSidop(DatiProtocollo.IdComuneAlias, DatiProtocollo.Software, DatiProtocollo.CodiceComune);

                if (protocolloSIDOP.Attiva)
                {
                    _protocolloLogs.DebugFormat(@"Valori parametri verticalizzazioni, 
                                            ConnectionString: {0},
                                            Provider: {1},
                                            StoredprocedureProt: {2},
                                            StoredprocedureEtic: {3},
                                            AnnoFasc: {4},
                                            ProgrFasc: {5},
                                            Pathprinterexe: {6},
                                            View: {7}",
                    protocolloSIDOP.Connectionstring,
                    protocolloSIDOP.Provider,
                    protocolloSIDOP.StoredprocedureProt,
                    protocolloSIDOP.StoredprocedureEtic,
                    protocolloSIDOP.Annofasc,
                    protocolloSIDOP.Progrfasc,
                    protocolloSIDOP.Pathprinterexe,
                    protocolloSIDOP.View);

                    _ConnectionString = protocolloSIDOP.Connectionstring;
                    _Provider = protocolloSIDOP.Provider;
                    _StoredProcedure_Prot = protocolloSIDOP.StoredprocedureProt;
                    _StoredProcedure_Etic = protocolloSIDOP.StoredprocedureEtic;
                    //Il parametro IdInd non verrà più utilizzato ed al suo posto verrà utilizzato il parametro classfica (può anche essere rimosso dalla verticalizzazione)
                    //_IdInd = protocolloSIDOP.Idind;
                    _AnnoFasc = int.Parse(protocolloSIDOP.Annofasc);
                    _ProgrFasc = protocolloSIDOP.Progrfasc;
                    _PathPrinterExe = protocolloSIDOP.Pathprinterexe;
                    _View = protocolloSIDOP.View;

                    db = new DataBase(_ConnectionString, (ProviderType)Enum.Parse(typeof(ProviderType), _Provider, true));

                    _protocolloLogs.Debug("Fine recupero valori da verticalizzazioni");
                }
                else
                    throw new Exception("La verticalizzazione PROTOCOLLO_SIDOP non è attiva");
            }
            catch (Exception ex)
            {
                throw new Exception("ERRORE GENERATO DURANTE IL RECUPERO DEI DATI DALLA VERTICALIZZAZIONE PROTOCOLLO_SIDOP", ex);
            }
        }

        #endregion

        #endregion
    }

    #region Enum per i tipi di chiamata alla stored procedure
    public enum TipoStoredProcedure { PROTOCOLLAZIONE, STAMPA_ETICHETTE }
    #endregion

    #region Classi utilizzate per recuperare i parametri di output della stored procedure e per interrogare la vista
    //Classe contenente come proprieta i parametri di output della stored procedure
    public class ProtocolloOutStoredProcedure
    {
        public ProtocolloOutStoredProcedure()
        { 
        
        }

        public int ReturnValue { get; set; }
        public string NumeroProtocollo { get; set; }
        public DateTime DataProtocollo { get; set; }
        public string ErrorCode { get; set; }
        public string ErrorMsg { get; set; }
    }

    //Classe utilizzata per recuperare i dati dalla vista CPG_UTENTI_EDILIZIA
    public class ProtocolloVista
    {
        public string UserId { get; set; }
        public string IdUteIn { get; set; }
        public string IdUOIn { get; set; }
        public string SettAss { get; set; }
        public string ServAss { get; set; }
        public string UOCAss { get; set; }
        public string UOSAss { get; set; }
        public string PostAss { get; set; }
        public string DesUte { get; set; }
    }

    //Classe utilizzata per settare i mittenti ed i destinatari
    public class ProtocolloMittDest
    {
        public int Count { get; set; }
        public string Id { get; set; }
        public string FlgPD { get; set; }
        public string Des { get; set; }
        public string CodFis { get; set; }
        public string Piva { get; set; }
        public string Ind { get; set; }
        public string FlgDestCopia { get; set; }
        public string IdUOProv { get; set; }
        public string CopieArrIn { get; set; }
    }

    //Classe utilizzata per settare gli allegati
    public class ProtocolloAll
    {
        public int Count { get; set; }
        public string AllegaArrIn { get; set; }
    }

    #endregion
}

