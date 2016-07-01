using System;
using System.Collections.Generic;
using System.Data;
using System.Text;
using System.IO;
using System.Globalization;
using Init.SIGePro.Data;
using Init.SIGePro.Manager;
using PersonalLib2.Data;
using Init.SIGePro.Manager.Logic.RicercheAnagrafiche;
using System.Xml.Serialization;
using Init.Utils;
using System.Xml;
using System.Xml.Schema;
using System.Web;
using Init.SIGePro.Manager.Logic.RicercheAnagrafiche.Parix;

namespace Cesena
{
    public class AnagrafeSearcher : AnagrafeSearcherParixBase
    {
        public AnagrafeSearcher()
            : base("CESENA")
        {
            
        }

        public override Anagrafe ByCodiceFiscaleImp(string codiceFiscale)
        {
            LogMessage("Il codice fiscale è " + codiceFiscale);
            DataBase db = new DataBase(Configuration["ConnectionString"], (ProviderType)Enum.Parse(typeof(ProviderType), Configuration["Provider"], true));

            Anagrafe anagrafe = null;

            DataSet ds = new DataSet();
            string sql;
            string table = (string.IsNullOrEmpty(Configuration["Owner"]) ? Configuration["View"] : Configuration["Owner"] + "." + Configuration["View"]);

            sql = "SELECT * " +
                  "FROM " +
                          table +
                //"COMUNE.CED_V_AN_RESID_MAGGIOLI " +
                        " WHERE " +
                           "COD_FISCALE = {0}";


            sql = String.Format(sql, db.Specifics.QueryParameterName("COD_FISCALE"));

            using (IDbCommand cmd = db.CreateCommand(sql))
            {
                cmd.Parameters.Add(db.CreateParameter("COD_FISCALE", codiceFiscale));

                IDataAdapter da = db.CreateDataAdapter(cmd);
                da.Fill(ds);
            }

            if (ds.Tables[0].Rows.Count == 0)
                throw new Exception("Non è stato trovato nessun soggetto anagrafico per il codice fiscale: " + codiceFiscale);
            else
            {
                if (ds.Tables[0].Rows.Count == 1)
                    anagrafe = GetAnagrafe(ds.Tables[0].Rows[0]);
                else
                    throw new Exception("Per il CF " + codiceFiscale + " il metodo ha restituito " + ds.Tables[0].Rows.Count + " record");
            }

            return anagrafe;
        }

        public override Anagrafe ByCodiceFiscaleImp(TipoPersona tipoPersona, string codiceFiscale)
        {
            if (tipoPersona == TipoPersona.PersonaFisica)
            {
                return ByCodiceFiscaleImp(codiceFiscale);
            }
            else
            {
                //Persona giuridica
                return base.ByPartitaIvaImp(codiceFiscale);
            }
        }

        public override List<Anagrafe> ByNomeCognomeImp(string nome, string cognome)
        {
            DataBase db = new DataBase(Configuration["ConnectionString"], (ProviderType)Enum.Parse(typeof(ProviderType), Configuration["Provider"], true));
            List<Anagrafe> list = new List<Anagrafe>();

            DataSet ds = new DataSet();
            string sql;
            string table = (string.IsNullOrEmpty(Configuration["Owner"]) ? Configuration["View"] : Configuration["Owner"] + "." + Configuration["View"]);

            sql = "SELECT * " +
                  "FROM " +
                          table +
                //"COMUNE.CED_V_AN_RESID_MAGGIOLI " +
                        " WHERE " +
                           "NOME = {0} AND " +
                           "COGNOME = {1}";


            sql = String.Format(sql, db.Specifics.QueryParameterName("NOME"), db.Specifics.QueryParameterName("COGNOME"));

            using (IDbCommand cmd = db.CreateCommand(sql))
            {
                cmd.Parameters.Add(db.CreateParameter("NOME", nome));
                cmd.Parameters.Add(db.CreateParameter("COGNOME", cognome));

                IDataAdapter da = db.CreateDataAdapter(cmd);
                da.Fill(ds);
            }

            foreach (DataRow dr in ds.Tables[0].Rows)
                list.Add(GetAnagrafe(dr));

            return list;
        }

        /*private object Deserializza(string result, string fileName, Type type)
        {
            object imprese = null;
            MemoryStream pMemStream = null;
            FileStream pFileStreamOut = new FileStream(Path + fileName, FileMode.Create);
            try
            {
                pMemStream = StreamUtils.StringToStream(result);
                StreamUtils.BulkTransfer(pMemStream, pFileStreamOut);
                pMemStream.Seek(0, SeekOrigin.Begin);
                switch (type.Name)
                {
                    case "ListaImprese":
                        ValidateXml(pMemStream, "ListaImprese.xsd");
                        break;
                    case "DettaglioImpresaRidotto":
                        ValidateXml(pMemStream, "DettaglioImpresaRidotto.xsd");
                        break;
                }
                XmlSerializer pXmlSerializer;
                //Deserializzazione del file xml ricevuto in ingresso
                pXmlSerializer = new XmlSerializer(type);
                imprese = pXmlSerializer.Deserialize(pFileStreamOut);
            }
            catch (Exception ex)
            {
                throw new Exception("Errore durante la deserializzazione del file xml restituito dal wm. " + ex.Message + "\r\n");
            }
            finally
            {
                if (pMemStream != null)
                    pMemStream.Close();
                if (pFileStreamOut != null)
                    pFileStreamOut.Close();
            }

            return imprese;
        }*/ 

        /*
        //Codice per effettuare la validazione
        private bool b_success;
        private string sMessage = "";
        /// <summary>
        /// Metodo usato per validare il file segnatura.xml in base al file xsd
        /// </summary>
        /// <param name="pStream">File xml da validare</param>
        private void ValidateXml(Stream pStream, string fileName)
        {
            XmlValidatingReader vreader = null;
            try
            {
                b_success = true;
                pStream.Seek(0, SeekOrigin.Begin);
                XmlTextReader reader = new XmlTextReader(pStream);

                //Creo un validating reader.
                vreader = new XmlValidatingReader(reader);

                XmlSchemaCollection xsc = new XmlSchemaCollection();

                string schemaPath = HttpContext.Current.Server.MapPath(Configuration["Xsd"]);
                if (!schemaPath.EndsWith(@"\"))
                    schemaPath += @"\";
                xsc.Add(null, schemaPath + fileName);
                //Valido usando lo schema conservato nello schema collection.
                vreader.Schemas.Add(xsc);

                vreader.ValidationEventHandler += new ValidationEventHandler(ValidationCallBack);
                //Leggo e valido il file xml.
                while (vreader.Read())
                {
                    if (!b_success)
                        throw new Exception(sMessage);
                }

            }
            catch (Exception ex)
            {
                throw new Exception("Errore generato durante la validazione. " + ex.Message);
            }
            finally
            {
                //Chiudo il reader.
                if (vreader != null)
                    vreader.Close();
            }
        }
        /
        private void ValidationCallBack(object sender, ValidationEventArgs args)
        {
            b_success = false;
            sMessage = "Errore di validazione: " + args.Message;
        }*/

        /*private Anagrafe GetImpresa(RISPOSTADATILISTA_IMPRESEESTREMI_IMPRESA result)
        {
            Anagrafe impresa = new Anagrafe();
            //Setto idcomune
            impresa.IDCOMUNE = IdComune;
            LogMessage("IDCOMUNE " + impresa.IDCOMUNE);
            //Setto la forma giuridica
            FormeGiuridicheMgr formeGiuridicheMgr = new FormeGiuridicheMgr(SigeproDb);
            FormeGiuridiche formeGiuridiche = new FormeGiuridiche();
            formeGiuridiche.IDCOMUNE = IdComune;
            if (result.FORMA_GIURIDICA != null)
            {
                formeGiuridiche.CODICECCIAA = result.FORMA_GIURIDICA.C_FORMA_GIURIDICA.Trim().ToUpper();
                formeGiuridiche = formeGiuridicheMgr.GetByClass(formeGiuridiche);
                if (formeGiuridiche != null)
                {
                    impresa.FORMAGIURIDICA = formeGiuridiche.CODICEFORMAGIURIDICA;
                    LogMessage("FORMAGIURIDICA " + impresa.FORMAGIURIDICA);
                }
            }
            //Setto CF
            impresa.CODICEFISCALE = result.CODICE_FISCALE.Trim().ToUpper();
            LogMessage("CODICEFISCALE " + impresa.CODICEFISCALE);
            //Setto la PIVA
            impresa.PARTITAIVA = result.PARTITA_IVA.Trim();
            LogMessage("PARTITAIVA " + impresa.PARTITAIVA);
            //Setto il flag disabilitato
            RISPOSTADATILISTA_IMPRESEESTREMI_IMPRESADATI_ISCRIZIONE_REA resultSede = null;
            foreach (RISPOSTADATILISTA_IMPRESEESTREMI_IMPRESADATI_ISCRIZIONE_REA elem in result.DATI_ISCRIZIONE_REA)
            {
                if (elem.FLAG_SEDE.Trim().ToUpper() == "SI")
                {
                    resultSede = elem;
                    break;
                }
            }
            if (resultSede.CESSAZIONE != null)
            {
                if (string.IsNullOrEmpty(resultSede.CESSAZIONE.CAUSALE.Trim().ToUpper()) && string.IsNullOrEmpty(resultSede.CESSAZIONE.DT_CESSAZIONE.Trim()) && string.IsNullOrEmpty(resultSede.CESSAZIONE.DT_DENUNCIA_CESS.Trim()))
                    impresa.FLAG_DISABILITATO = "0";
                else
                {
                    impresa.FLAG_DISABILITATO = "1";
                    if (!string.IsNullOrEmpty(resultSede.CESSAZIONE.DT_CESSAZIONE.Trim()))
                        impresa.DATA_DISABILITATO = DateTime.ParseExact(resultSede.CESSAZIONE.DT_CESSAZIONE.Trim(), "yyyyMMdd", null);
                    else if (!string.IsNullOrEmpty(resultSede.CESSAZIONE.DT_DENUNCIA_CESS.Trim()))
                        impresa.DATA_DISABILITATO = DateTime.ParseExact(resultSede.CESSAZIONE.DT_DENUNCIA_CESS.Trim(), "yyyyMMdd", null);
                }
            }
            else
                impresa.FLAG_DISABILITATO = "0";
            LogMessage("FLAG_DISABILITATO " + impresa.FLAG_DISABILITATO);

            //Setto la denominazione
            impresa.NOMINATIVO = result.DENOMINAZIONE.Trim().ToUpper();
            LogMessage("NOMINATIVO " + impresa.NOMINATIVO);

            if (result.DATI_ISCRIZIONE_RI != null)
            {
                //Setto Nr RI
                impresa.REGDITTE = result.DATI_ISCRIZIONE_RI.NUMERO_RI.Trim().ToUpper();
                LogMessage("REGDITTE " + impresa.REGDITTE);

                //Setto data RI
                impresa.DATAREGDITTE = string.IsNullOrEmpty(result.DATI_ISCRIZIONE_RI.DATA.Trim()) ? (DateTime?)null : DateTime.ParseExact(result.DATI_ISCRIZIONE_RI.DATA.Trim(), "yyyyMMdd", null);
                LogMessage("DATAREGDITTE " + impresa.DATAREGDITTE);
            }

            //Setto Nr REA
            impresa.NUMISCRREA = resultSede.NREA.Trim().ToUpper();
            LogMessage("NUMISCRREA " + impresa.NUMISCRREA);

            //Setto provincia REA
            impresa.PROVINCIAREA = resultSede.CCIAA.Trim().ToUpper();
            if (impresa.PROVINCIAREA == "FO")
                impresa.PROVINCIAREA = "FC";
            LogMessage("PROVINCIAREA " + impresa.PROVINCIAREA);

            //Setto la data di iscrizione REA
            impresa.DATAISCRREA = string.IsNullOrEmpty(resultSede.DATA.Trim()) ? (DateTime?)null : DateTime.ParseExact(resultSede.DATA.Trim(), "yyyyMMdd", null);
            LogMessage("DATAISCRREA " + impresa.DATAISCRREA);

            //Setto l'indirizzo
            ICRSimpleWSImplService proxyParixGate = new ICRSimpleWSImplService();

            proxyParixGate.Url = Configuration["Url"];
            //proxyParixGate.Url = "https://servizicner.regione.emilia-romagna.it/parixgate/services/gate?wsdl";
            string resultDettaglio = proxyParixGate.DettaglioRidottoImpresa(resultSede.CCIAA, resultSede.NREA, Configuration["SwitchControl"], Configuration["User"], Configuration["Password"]);
            //string resultDettaglio = proxyParixGate.DettaglioRidottoImpresa(resultSede.CCIAA,resultSede.NREA, "no", "GATE3253479N", "HMbGgpj8");
            DettaglioImpresaRidotto dettImpresa = (DettaglioImpresaRidotto)Deserializza(resultDettaglio, "ParixGateDettaglioImpresaRidotto.xml", typeof(DettaglioImpresaRidotto));
            //Verifico se la chiamata al ws è andata a buon fine
            if (dettImpresa.HEADER.ESITO == "OK")
            {
                if (((RISPOSTADATIDATI_IMPRESA)dettImpresa.DATI.Item).INFORMAZIONI_SEDE != null)
                {
                    RISPOSTADATIDATI_IMPRESAINFORMAZIONI_SEDEINDIRIZZO indiriz = ((RISPOSTADATIDATI_IMPRESA)dettImpresa.DATI.Item).INFORMAZIONI_SEDE.INDIRIZZO;
                    if (indiriz != null)
                    {
                        impresa.INDIRIZZO = indiriz.TOPONIMO.Trim().ToUpper() + " " + indiriz.VIA.Trim().ToUpper() + (string.IsNullOrEmpty(indiriz.N_CIVICO.Trim()) ? "" : " " + indiriz.N_CIVICO);
                        LogMessage("INDIRIZZO " + impresa.INDIRIZZO);

                        //Setto la frazione
                        impresa.CITTA = indiriz.FRAZIONE.Trim().ToUpper();
                        LogMessage("CITTA " + impresa.CITTA);

                        //Setto il CAP
                        impresa.CAP = indiriz.CAP.Trim();
                        LogMessage("CAP " + impresa.CAP);

                        //Setto il comune e la provincia della sede
                        Comuni comuni = new Comuni();
                        ComuniMgr comuniMgr = new ComuniMgr(SigeproDb);
                        comuni.COMUNE = indiriz.COMUNE.Trim().ToUpper();
                        comuni.CODICEISTAT = indiriz.C_COMUNE.Trim();
                        comuni = comuniMgr.GetByClass(comuni);
                        if (comuni != null)
                        {
                            impresa.COMUNERESIDENZA = comuni.CODICECOMUNE;
                            LogMessage("COMUNERESIDENZA " + impresa.COMUNERESIDENZA);
                            impresa.PROVINCIA = comuni.SIGLAPROVINCIA;
                            LogMessage("PROVINCIA " + impresa.PROVINCIA);
                        }

                        //Setto il fax
                        impresa.FAX = indiriz.FAX.Trim();
                        LogMessage("FAX " + impresa.FAX);

                        //Setto il telefono
                        impresa.TELEFONO = indiriz.TELEFONO.Trim();
                        LogMessage("TELEFONO " + impresa.TELEFONO);
                    }
                }
            }
            else
            {
                throw new Exception("La chiamata al wm DettaglioRidottoImpresa non è andata a buon fine. Codice di errore: " + ((Cesena.Proxy.ResponseDettaglioRidotto.RISPOSTADATIERRORE)dettImpresa.DATI.Item).TIPO + ".Descrizione errore: " + ((Cesena.Proxy.ResponseDettaglioRidotto.RISPOSTADATIERRORE)dettImpresa.DATI.Item).MSG_ERR);
            }

            return impresa;
        }*/

        private Anagrafe GetAnagrafe(DataRow dr)
        {
            
            Anagrafe anagrafe = new Anagrafe();
            //Setto idcomune
            anagrafe.IDCOMUNE = IdComune;
            LogMessage("IDCOMUNE " + anagrafe.IDCOMUNE);
            //Setto CF
            anagrafe.CODICEFISCALE = dr["COD_FISCALE"].ToString().Trim().ToUpper();
            LogMessage("CODICEFISCALE " + anagrafe.CODICEFISCALE);
            //Setto il flag disabilitato
            switch (dr["POSIZIONE_ANAG"].ToString().Trim().ToUpper())
            {
                case "AIRE":
                case "EMIG":
                case "RESI":
                    anagrafe.FLAG_DISABILITATO = "0";
                    break;
                case "IRRE":
                case "ERR?":
                case "DECE":
                    anagrafe.FLAG_DISABILITATO = "1";
                    anagrafe.DATA_DISABILITATO = string.IsNullOrEmpty(dr["DATA_DECES"].ToString().Trim()) ? (DateTime?)null : (DateTime)dr["DATA_DECES"];
                    break;
                default:
                    throw new Exception("Il valore riportato dal campo POSIZIONE_ANAG non rientra nei casi gestiti: " + dr["POSIZIONE_ANAG"].ToString());
            }
            LogMessage("FLAG_DISABILITATO " + anagrafe.FLAG_DISABILITATO);

            //Setto il cognome
            anagrafe.NOMINATIVO = dr["COGNOME"].ToString().Trim().ToUpper();
            LogMessage("NOMINATIVO " + anagrafe.NOMINATIVO);
            //Setto il nome
            anagrafe.NOME = dr["NOME"].ToString().Trim().ToUpper();
            LogMessage("NOME " + anagrafe.NOME);
            //Setto il sesso
            anagrafe.SESSO = dr["SESSO"].ToString().Trim().ToUpper();
            LogMessage("SESSO " + anagrafe.SESSO);
            //Setto la data di nascita
            anagrafe.DATANASCITA = string.IsNullOrEmpty(dr["DATA_NASCITA"].ToString().Trim()) ? (DateTime?)null : (DateTime)dr["DATA_NASCITA"];
            LogMessage("DATANASCITA " + anagrafe.DATANASCITA);
            //Setto il codice del comune di nascita
            if (!string.IsNullOrEmpty(dr["COD_COMUNE_NASC"].ToString().Trim()))
            {
                ComuniMgr pComuniMgr = new ComuniMgr(SigeproDb);
                Comuni pComuni = new Comuni();
                string codiceIstat = dr["COD_COMUNE_NASC"].ToString().Trim();
                pComuni.CODICEISTAT = codiceIstat.PadLeft(6, '0');
                pComuni = pComuniMgr.GetByClass(pComuni);
                if (pComuni != null)
                {
                    anagrafe.CODCOMNASCITA = pComuni.CODICECOMUNE;
                    LogMessage("CODCOMNASCITA " + anagrafe.CODCOMNASCITA);
                }
                else
                {
                    //Uso il COD_COMUNE_NASC come codice stato estero
                    pComuni = new Comuni();
                    string codiceStatoEstero = dr["COD_COMUNE_NASC"].ToString().Trim();
                    pComuni.CODICESTATOESTERO = codiceStatoEstero.TrimStart(new char[] { '0' });
                    pComuni = pComuniMgr.GetByClass(pComuni);
                    if (pComuni != null)
                    {
                        anagrafe.CODCOMNASCITA = pComuni.CODICECOMUNE;
                        LogMessage("CODCOMNASCITA " + anagrafe.CODCOMNASCITA);
                    }
                }
            }

            switch (dr["POSIZIONE_ANAG"].ToString().Trim().ToUpper())
            {
                case "EMIG":
                case "AIRE":
                case "RESI":
                    //Setto l'indirizzo 
                    SetIndirizzo(dr, anagrafe, dr["POSIZIONE_ANAG"].ToString().Trim().ToUpper());
                    break;
                case "DECE":
                case "ERR?":
                case "IRRE":
                    if (string.IsNullOrEmpty(dr["DATA_EMIGR"].ToString().Trim()))
                    {
                        SetIndirizzo(dr, anagrafe, "RESI");
                    }
                    else
                    {
                        if (!string.IsNullOrEmpty(dr["DATA_IMMIGR"].ToString().Trim()))
                        {
                            if (((DateTime)dr["DATA_IMMIGR"]).CompareTo((DateTime)dr["DATA_EMIGR"]) >= 0)
                                SetIndirizzo(dr, anagrafe, "RESI");
                            else
                                SetIndirizzo(dr, anagrafe, "EMIG");
                        }
                        else
                            SetIndirizzo(dr, anagrafe, "EMIG");
                    }
                    break;
                default:
                    throw new Exception("Il valore riportato dal campo POSIZIONE_ANAG non rientra nei casi gestiti: " + dr["POSIZIONE_ANAG"].ToString());
            }

            return anagrafe;
        }

        private void SetIndirizzo(DataRow dr, Anagrafe anagrafe, string posAnag)
        {
            ComuniMgr pComuniMgr = new ComuniMgr(SigeproDb);
            Comuni pComune = new Comuni();

            switch (posAnag)
            {
                case "AIRE":
                    anagrafe.INDIRIZZO = dr["VIA"].ToString().Trim().ToUpper();
                    if (!string.IsNullOrEmpty(dr["NUM_CIVICO"].ToString().Trim()))
                        anagrafe.INDIRIZZO += " " + dr["NUM_CIVICO"].ToString().Trim().ToUpper();
                    if (!string.IsNullOrEmpty(dr["BIS"].ToString().Trim()))
                        anagrafe.INDIRIZZO += dr["BIS"].ToString().Trim().ToUpper();
                    if (!string.IsNullOrEmpty(dr["INTERNO"].ToString().Trim()))
                        anagrafe.INDIRIZZO += " INT.: " + dr["INTERNO"].ToString().Trim().ToUpper();
                    LogMessage("INDIRIZZO " + anagrafe.INDIRIZZO);
                    anagrafe.CAP = dr["CAP"].ToString().Trim();
                    LogMessage("CAP " + anagrafe.CAP);
                    if (!string.IsNullOrEmpty(dr["COD_COMUNE_RESID"].ToString().Trim()))
                    {
                        string codiceStatoEstero = dr["COD_COMUNE_RESID"].ToString().Trim();
                        pComune.CODICESTATOESTERO = codiceStatoEstero.TrimStart(new char[] { '0' });
                        pComune = pComuniMgr.GetByClass(pComune);
                        if (pComune != null)
                        {
                            anagrafe.COMUNERESIDENZA = pComune.CODICECOMUNE;
                            LogMessage("COMUNERESIDENZA " + anagrafe.COMUNERESIDENZA);
                            //Setto la provincia
                            anagrafe.PROVINCIA = pComune.SIGLAPROVINCIA;
                            LogMessage("PROVINCIA " + anagrafe.PROVINCIA);
                        }
                    }
                    break;
                case "RESI":
                    anagrafe.INDIRIZZO = dr["VIA"].ToString().Trim().ToUpper();
                    if (!string.IsNullOrEmpty(dr["NUM_CIVICO"].ToString().Trim()))
                        anagrafe.INDIRIZZO += " " + dr["NUM_CIVICO"].ToString().Trim().ToUpper();
                    if (!string.IsNullOrEmpty(dr["BIS"].ToString().Trim()))
                        anagrafe.INDIRIZZO += dr["BIS"].ToString().Trim().ToUpper();
                    if (!string.IsNullOrEmpty(dr["INTERNO"].ToString().Trim()))
                        anagrafe.INDIRIZZO += " INT.: " + dr["INTERNO"].ToString().Trim().ToUpper();
                    LogMessage("INDIRIZZO " + anagrafe.INDIRIZZO);
                    anagrafe.CAP = dr["CAP"].ToString().Trim();
                    LogMessage("CAP " + anagrafe.CAP);
                    //Setto il codice del comune di residenza
                    pComune = pComuniMgr.GetByComune("CESENA");
                    if (pComune != null)
                    {
                        anagrafe.COMUNERESIDENZA = pComune.CODICECOMUNE;
                        LogMessage("COMUNERESIDENZA " + anagrafe.COMUNERESIDENZA);
                        //Setto la provincia
                        anagrafe.PROVINCIA = pComune.SIGLAPROVINCIA;
                        LogMessage("PROVINCIA " + anagrafe.PROVINCIA);
                    }
                    break;
                case "EMIG":
                    //Non ho informazioni aggiornate
                    break;
            }
        } 


    }
}
