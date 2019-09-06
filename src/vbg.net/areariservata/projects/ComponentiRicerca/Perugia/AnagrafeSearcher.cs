using System;
using System.Collections.Generic;
using System.Data;
using System.Collections.Generic;
using System.Text;
using System.IO;
using System.Globalization;
using Init.SIGePro.Data;
using Init.SIGePro.Manager;
using PersonalLib2.Data;
using Init.SIGePro.Manager.Logic.RicercheAnagrafiche;
using Perugia.Proxy;

namespace Perugia
{
    public class AnagrafeSearcher : AnagrafeSearcherBase
    {
        public AnagrafeSearcher(): base("PERUGIA")
        {
        }

        public override Anagrafe ByCodiceFiscaleImp(string codiceFiscale)
        {
            Cerca pProxyAnagrafe = new Cerca();
            LogMessage("Il codice fiscale è " + codiceFiscale);
            LogMessage("L'url è " + Configuration["URL"]);
            Anagrafe anagrafe = null;
            pProxyAnagrafe.Url = Configuration["URL"];
            DataSet dsElenco = pProxyAnagrafe.Elenco("", "", "", "", "", codiceFiscale, "", "", "", "", "", "T");
            //DataSet dsElenco = pProxyAnagrafe.Elenco("", "", "", "", "", codiceFiscale, "", "", "", "", "", "");

            if (dsElenco != null)
            {
                if (dsElenco.Tables[0].Rows.Count == 0)
                    return new Anagrafe();
                else
                {
                    if (dsElenco.Tables[0].Rows.Count > 1)
                        throw new Exception("Per il CF " + codiceFiscale + " il metodo ha restituito " + dsElenco.Tables[0].Rows.Count + " record");
                    else
                    {
                        int iNumInd = Convert.ToInt32(dsElenco.Tables[0].Rows[0]["PERSNIND"]);
                        LogMessage("Il NumInd è " + iNumInd);
                        DataSet dsIndividuo = pProxyAnagrafe.Individuo(iNumInd);

                        if (dsIndividuo != null)
                        {
                            if (dsIndividuo.Tables[0].Rows.Count == 0)
                                return new Anagrafe();
                            else
                            {
                                if (dsIndividuo.Tables[0].Rows.Count > 1)
                                    throw new Exception("Per il NumInd " + iNumInd + " il metodo ha restituito " + dsIndividuo.Tables[0].Rows.Count + " record");
                                else
                                    anagrafe = GetAnagrafe(dsIndividuo.Tables[0].Rows[0]);
                            }
                        }
                    }
                }
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
                return ByPartitaIvaImp(codiceFiscale);
            }
        }

        public override Anagrafe ByPartitaIvaImp(string partitaIva)
        {
            return null;
        }

        public override List<Anagrafe> ByNomeCognomeImp(string nome, string cognome)
        {
            Cerca pProxyAnagrafe = new Cerca();
            LogMessage("Il nome " + nome);
            LogMessage("Il cognome " + cognome);
            LogMessage("L'url è " + Configuration["URL"]);
            pProxyAnagrafe.Url = Configuration["URL"];
            DataSet dsElenco = pProxyAnagrafe.Elenco("", string.IsNullOrEmpty(cognome) ? "" : cognome, string.IsNullOrEmpty(nome) ? "" : nome, "", "", "", "", "", "", "", "", "");

            List<Anagrafe> list = new List<Anagrafe>();
            foreach (DataRow dr in dsElenco.Tables[0].Rows)
            {
                int iNumInd = Convert.ToInt32(dr["PERSNIND"]);
                LogMessage("Il NumInd è " + iNumInd);
                DataSet dsIndividuo = pProxyAnagrafe.Individuo(iNumInd);

                if (dsIndividuo != null)
                {
                    if (dsIndividuo.Tables[0].Rows.Count > 1)
                        throw new Exception("Per il NumInd " + iNumInd + " il metodo ha restituito " + dsIndividuo.Tables[0].Rows.Count + " record");
                    else
                        list.Add(GetAnagrafe(dsIndividuo.Tables[0].Rows[0]));
                }
            }

            return list;
        }

        private Anagrafe GetAnagrafe(DataRow dr)
        {
            Anagrafe anagrafe = new Anagrafe();

            //Setto idcomune
            anagrafe.IDCOMUNE = IdComune;
            LogMessage("IDCOMUNE " + anagrafe.IDCOMUNE);
            //Setto CF
            anagrafe.CODICEFISCALE = dr["CodiceFiscale"].ToString().Trim().ToUpper();
            LogMessage("CODICEFISCALE " + anagrafe.CODICEFISCALE);

            if (string.IsNullOrEmpty(dr["MorteData"].ToString().Trim()))
                anagrafe.FLAG_DISABILITATO = "0";
            else
            {
                anagrafe.FLAG_DISABILITATO = "1";
                anagrafe.DATA_DISABILITATO = DateTime.ParseExact(dr["MorteData"].ToString().Trim(), "dd/MM/yyyy", null);
            }
            LogMessage("FLAG_DISABILITATO " + anagrafe.FLAG_DISABILITATO);
            //Setto il cognome
            anagrafe.NOMINATIVO = dr["Cognome"].ToString().Trim().ToUpper();
            LogMessage("NOMINATIVO " + anagrafe.NOMINATIVO);
            //Setto il nome
            anagrafe.NOME = dr["Nome"].ToString().Trim().ToUpper();
            LogMessage("NOME " + anagrafe.NOME);
            //Setto il sesso
            anagrafe.SESSO = dr["Sesso"].ToString().Trim().ToUpper();
            LogMessage("SESSO " + anagrafe.SESSO);
            //Setto la data di nascita
            anagrafe.DATANASCITA = string.IsNullOrEmpty(dr["NascitaData"].ToString().Trim()) ? (DateTime?)null : DateTime.ParseExact(dr["NascitaData"].ToString().Trim(), "dd/MM/yyyy", null);
            LogMessage("DATANASCITA " + anagrafe.DATANASCITA);
            //Setto il codice del comune di nascita
            if (!string.IsNullOrEmpty(dr["NascitaComune"].ToString().Trim()))
            {
                ComuniMgr pComuniMgr = new ComuniMgr(SigeproDb);
                Comuni pComuni = pComuniMgr.GetByComune(dr["NascitaComune"].ToString().Trim().ToUpper());
                if (pComuni != null)
                {
                    anagrafe.CODCOMNASCITA = pComuni.CODICECOMUNE;
                    LogMessage("CODCOMNASCITA " + anagrafe.CODCOMNASCITA);
                }
            }
            if (string.IsNullOrEmpty(dr["EmigrazioneData"].ToString().Trim()))
            {
                SetIndirizzo(dr, anagrafe);
            }
            else
            {
                if (!string.IsNullOrEmpty(dr["ImmigrazioneData"].ToString().Trim()))
                {
                    if (DateTime.ParseExact(dr["ImmigrazioneData"].ToString().Trim(), "dd/MM/yyyy", null).CompareTo(DateTime.ParseExact(dr["EmigrazioneData"].ToString().Trim(), "dd/MM/yyyy", null)) >= 0)
                        SetIndirizzo(dr, anagrafe);
                }
            }

            return anagrafe;
        }

        private void SetIndirizzo(DataRow dr, Anagrafe anagrafe)
        {
            //Setto l'indirizzo  
            anagrafe.INDIRIZZO = dr["IndirizzoDescrizione"].ToString().Trim().ToUpper();
            if (!string.IsNullOrEmpty(dr["IndirizzoNumeroCivico"].ToString().Trim()))
                anagrafe.INDIRIZZO += " " + dr["IndirizzoNumeroCivico"].ToString().Trim().ToUpper();
            if (!string.IsNullOrEmpty(dr["IndirizzoLettera"].ToString().Trim()))
                anagrafe.INDIRIZZO += "/" + dr["IndirizzoLettera"].ToString().Trim().ToUpper();
            if (!string.IsNullOrEmpty(dr["IndirizzoInterno"].ToString().Trim()))
                anagrafe.INDIRIZZO += "/" + dr["IndirizzoInterno"].ToString().Trim().ToUpper();
            if (!string.IsNullOrEmpty(dr["IndirizzoBis"].ToString().Trim()))
                anagrafe.INDIRIZZO += "/" + dr["IndirizzoBis"].ToString().Trim().ToUpper();
            LogMessage("INDIRIZZO " + anagrafe.INDIRIZZO);

            //Setto il codice del comune di residenza
            Comuni pComune = null;
            ComuniMgr pComuniMgr = new ComuniMgr(SigeproDb);
            pComune = pComuniMgr.GetByComune("PERUGIA");
            if (pComune != null)
            {
                anagrafe.COMUNERESIDENZA = pComune.CODICECOMUNE;
                LogMessage("COMUNERESIDENZA " + anagrafe.COMUNERESIDENZA);
                //Setto la provincia
                anagrafe.PROVINCIA = pComune.SIGLAPROVINCIA;
                LogMessage("PROVINCIA " + anagrafe.PROVINCIA);
            }
        }
    }
}
