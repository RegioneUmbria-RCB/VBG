using System;
using System.IO;
using System.Text;
using System.Xml.Serialization;
using Init.SIGePro.Data;
using Init.SIGePro.Manager;
//using Init.SIGePro.Protocollo.Proxy;
using Init.SIGePro.Verticalizzazioni;
using Init.Utils;
using Init.SIGePro.Validator;
using Init.SIGePro.Utils;
using System.Collections.Generic;
using Init.SIGePro.Exceptions.Protocollo;
using System.Data;
using Init.SIGePro.Protocollo.Logs;
using Init.SIGePro.Protocollo.Data;
using Init.SIGePro.Protocollo.WsDataClass;

namespace Init.SIGePro.Protocollo
{
    class PROTOCOLLO_DEFAULT : ProtocolloBase
    {
        #region Costruttori
        public PROTOCOLLO_DEFAULT()
        {

        }
        #endregion

        #region Metodi pubblici e privati della classe

        #region Metodi per la stampa di un'etichetta

        public override DatiEtichette StampaEtichette(string idProtocollo, DateTime? dataProtocollo, string numeroProtocollo, int numeroCopie, string stampante)
        {
            DatiEtichette datiEtichette = new DatiEtichette();

            try
            {
                GetParametriFromVertDefault();

                DataProtocollo = dataProtocollo;
                _protocolloLogs.DebugFormat("INVIATA RICHIESTA STAMPA ETICHETTA AL PROTOCOLLO DI DEFAULT. DATA: {0}, NUMERO: {1}, OPERATORE: {2}, RUOLO: {3} ", AnnoProtocollo, numeroProtocollo, Operatore, Ruolo);
                datiEtichette.IdEtichetta = numeroProtocollo.PadLeft(8, '0');
            }
            catch (Exception ex)
            {
                throw _protocolloLogs.LogErrorException("ERRORE GENERATO DURANTE LA STAMPA DI UN'ETICHETTA", ex);
            }

            return datiEtichette;
        }

        #endregion

        #region Metodi per mettere alla firma

        public override DatiProtocolloRes MettiAllaFirma(Data.DatiProtocolloIn pProt)
        {

            DatiProtocolloRes pProtocollo = null;

            try
            {
                GetParametriFromVertDefault();
                pProtocollo = CreaDatiProtocollo(ModoProtocollazione.METTI_ALLA_FIRMA);
                _protocolloSerializer.Serialize("DocInserisciOut.xml", pProtocollo);
            }
            catch (Exception ex)
            {
                throw _protocolloLogs.LogErrorException("ERRORE GENERATO DURANTE LA CHIAMATA METTIALLAFIRMA", ex);
            }

            return pProtocollo;
        }

        #endregion

        #region Metodi di protocollazione
        public override DatiProtocolloRes Protocollazione(Data.DatiProtocolloIn pProt)
        {
            DatiProtocolloRes pProtocollo = null;

            try
            {
                GetParametriFromVertDefault();

                _protocolloLogs.Debug("Chiamata al metodo di protocollazione");

                pProtocollo = CreaDatiProtocollo(ModoProtocollazione.PROTOCOLLAZIONE);
                _protocolloSerializer.Serialize("ProtOut.xml", pProtocollo);


                /*if (_protocolloLogs.IsDebugEnabled)
                {
                    CreateFileXmlFromObj("ProtOut.xml", pProtocollo);
                    LogMessage("Ricevuta risposta inserimento protocollo da protocollo Default: ProtOut.xml");
                }*/
            }
            catch (ProtocolloException ex)
            {
                throw ex;
            }
            catch (Exception ex)
            {
                throw new ProtocolloException("Errore generato durante la protocollazione eseguita con il protocollo Default. Metodo: Protocollazione, modulo: ProtocolloDefault. Problemi specifici: " + ex.Message + "\r\n");
            }

            return pProtocollo;
        }

        private DatiProtocolloRes CreaDatiProtocollo(ModoProtocollazione eModoProtocollazione)
        {
            DatiProtocolloRes pProtocollo = new DatiProtocolloRes();

            //La classe ClassValidator viene usata per generare il numero del protocollo
            ClassValidator mClsVal = new ClassValidator(null);
            pProtocollo.IdProtocollo = mClsVal.GetNextVal(this.DatiProtocollo.Db, DatiProtocollo.IdComune, "PROT_GENERALE.PG_ID").ToString();

            switch (eModoProtocollazione)
            {
                case ModoProtocollazione.METTI_ALLA_FIRMA:
                    break;
                case ModoProtocollazione.PROTOCOLLAZIONE:
                    pProtocollo.NumeroProtocollo = mClsVal.GetNextVal(this.DatiProtocollo.Db, DatiProtocollo.IdComune, "PROT_GENERALE.PG_ID").ToString();
                    pProtocollo.AnnoProtocollo = DateTime.Now.Year.ToString();

                    if (ModificaNumero)
                        pProtocollo.NumeroProtocollo = pProtocollo.NumeroProtocollo.TrimStart(new char[] { '0' });

                    if (AggiungiAnno)
                        pProtocollo.NumeroProtocollo += "/" + pProtocollo.AnnoProtocollo;

                    pProtocollo.DataProtocollo = DateTime.Now.ToString("dd/MM/yyyy");
                    break;
            }

            return pProtocollo;
        }
        #endregion

        #region Metodi per la lettura di un protocollo

        public override DatiProtocolloLetto LeggiProtocollo(string idProtocollo, string annoProtocollo, string numeroProtocollo)
        {
            try
            {
                GetParametriFromVertDefault();

                if (_protocolloLogs.IsDebugEnabled)
                    _protocolloLogs.Debug("Inviata richiesta lettura di un protocollo a protocollo Default. ID: " + idProtocollo + ", anno: " + annoProtocollo + ", numero: " + numeroProtocollo + ", operatore: " + Operatore.ToUpper() + ", ruolo: " + Ruolo);

                if (_protocolloLogs.IsDebugEnabled)
                    _protocolloLogs.Debug("Chiamata al metodo LeggiProtocollo");

                var response = CreaDatiProtocolloLetto(annoProtocollo, numeroProtocollo);

                if (response != null && _protocolloLogs.IsDebugEnabled)
                {
                    _protocolloSerializer.Serialize(ProtocolloLogsConstants.ResponseFileName, response);
                    _protocolloLogs.DebugFormat("Ricevuta risposta lettura da protocollo Default: {0}", ProtocolloLogsConstants.ResponseFileName);
                }

                return response;
            }
            catch (Exception ex)
            {
                throw _protocolloLogs.LogErrorException("ERRORE GENERATO DURANTE IL LEGGI PROTOCOLLO", ex);
            }
        }

        private DatiProtocolloLetto CreaDatiProtocolloLetto(string annoProtocollo, string numeroProtocollo)
        {
            DatiProtocolloLetto pDatiProtocolloLetto = new DatiProtocolloLetto();

            string[] sNumProtSplit = numeroProtocollo.Split(new Char[] { '/' });
            string sNumProtocollo = sNumProtSplit[0];

            var dt = DateTime.Now.AddDays(-1);

            pDatiProtocolloLetto.AnnoProtocollo = annoProtocollo;
            pDatiProtocolloLetto.NumeroProtocollo = sNumProtocollo;
            pDatiProtocolloLetto.DataProtocollo = dt.ToString("dd/MM/yyyy");
            pDatiProtocolloLetto.IdProtocollo = (Convert.ToInt32(sNumProtocollo) - 1).ToString();

            return pDatiProtocolloLetto;
        }


        #endregion

        #region Metodi Fascicolazione

        public override DatiProtocolloFascicolato IsFascicolato(string idProtocollo, string annoProtocollo, string numeroProtocollo)
        {
            try
            {
                GetParametriFromVertDefault();

                return Fascicolato(idProtocollo, annoProtocollo, numeroProtocollo);
            }
            catch (ProtocolloException ex)
            {
                throw ex;
            }
            catch (Exception ex)
            {
                throw new ProtocolloException("Errore generato durante la verifica fascicolazione di un protocollo eseguita con il protocollo Default. Metodo: LeggiProtocollo, modulo: ProtocolloDefaul. " + ex.Message + "\r\n");
            }
        }

        //Se numeroProtocollo % 2 = 0 --> il protocollo risulta fascicolato
        //Se numeroProtocollo % 2 = 1 --> il protocollo risulta non fascicolato
        private DatiProtocolloFascicolato Fascicolato(string idProtocollo, string annoProtocollo, string numeroProtocollo)
        {
            DatiProtocolloFascicolato datiProtFasc = new DatiProtocolloFascicolato();
            string[] sNumProtSplit = numeroProtocollo.Split(new Char[] { '/' });
            string sNumProtocollo = sNumProtSplit[0];
            int iNumeroProtocollo = Convert.ToInt32(sNumProtocollo);

            //Verifico se il protocollo è stato fascicolato
            if ((iNumeroProtocollo % 2) == 0)
            {
                datiProtFasc.AnnoFascicolo = DateTime.Now.Year.ToString();
                datiProtFasc.Classifica = "Classifica fascicolazione";
                datiProtFasc.DataFascicolo = DateTime.Now.ToString("dd/MM/yyyy");
                datiProtFasc.NumeroFascicolo = (iNumeroProtocollo + 1).ToString();
                datiProtFasc.Oggetto = "Fascicolo numero " + datiProtFasc.NumeroFascicolo;
                datiProtFasc.Fascicolato = EnumFascicolato.si;
            }
            else
            {
                datiProtFasc.Fascicolato = EnumFascicolato.no;
            }

            return datiProtFasc;
        }

        public override DatiFascicolo Fascicola(Fascicolo fascicolo)
        {
            DatiFascicolo pFascicolo = new DatiFascicolo();
            return pFascicolo;
        }

        public override DatiFascicolo CambiaFascicolo(Fascicolo fascicolo)
        {
            DatiFascicolo pFascicolo = new DatiFascicolo();
            return pFascicolo;
        }

        #endregion

        #endregion

        #region Utility

        private void GetParametriFromVertDefault()
        {
            try
            {
                VerticalizzazioneProtocolloDefault protocolloDefault;

                protocolloDefault = new VerticalizzazioneProtocolloDefault(DatiProtocollo.IdComuneAlias, DatiProtocollo.Software, DatiProtocollo.CodiceComune);

                if (!protocolloDefault.Attiva)
                    throw new ProtocolloException("La verticalizzazione PROTOCOLLO_DEFAULT non è attiva.\r\n");
            }
            catch (ProtocolloException ex)
            {
                throw ex;
            }
            catch (Exception ex)
            {
                throw new ProtocolloException("Errore generato durante la lettura della verticalizzazione PROTOCOLLO_DEFAULT. Metodo: GetParametriFromVertDefault, modulo: ProtocolloDefault. " + ex.Message + "\r\n");
            }
        }

        #endregion
    }

    enum ModoProtocollazione { PROTOCOLLAZIONE, METTI_ALLA_FIRMA }
}

