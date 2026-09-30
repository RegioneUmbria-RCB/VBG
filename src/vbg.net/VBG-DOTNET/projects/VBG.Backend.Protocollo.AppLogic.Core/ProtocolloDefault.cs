using Init.SIGePro.Manager.Validator;
using VBG.Backend.Protocollo.Verticalizzazioni.Core;
using System;
using System.Collections.Generic;
using VBG.Backend.Protocollo.AppLogic.Shared.WsDataClass;
using VBG.Backend.Protocollo.AppLogic.Shared;
using VBG.Backend.Protocollo.AppLogic.Core.Default.Metadati;
using VBG.Backend.Protocollo.AppLogic.Shared.Data;
using VBG.Backend.Protocollo.AppLogic.Shared.Logs;
using SIGePro.Manager.VerticalizzazioniBase;
using VBG.Backend.Protocollo.AppLogic.Shared.Exceptions;

namespace Init.SIGePro.Protocollo
{
    internal class PROTOCOLLO_DEFAULT : ProtocolloBase
    {
        private readonly IVerticalizzazioniFactory _verticalizzazioniFactory;

        #region Costruttori
        public PROTOCOLLO_DEFAULT(IVerticalizzazioniFactory verticalizzazioniFactory)
        {
            this._verticalizzazioniFactory = verticalizzazioniFactory;
        }
        #endregion

        #region Metodi pubblici e privati della classe

        #region Metodi per la stampa di un'etichetta

        public override EtichetteResponseType StampaEtichette(string idProtocollo, DateTime? dataProtocollo, string numeroProtocollo, int numeroCopie, string stampante)
        {
            EtichetteResponseType datiEtichette = new EtichetteResponseType();

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

        public override DatiProtocolloResponseType MettiAllaFirma(DatiProtocolloIn pProt)
        {

            DatiProtocolloResponseType pProtocollo = null;

            try
            {
                GetParametriFromVertDefault();
                pProtocollo = CreaDatiProtocollo(ModoProtocollazione.METTI_ALLA_FIRMA);
                _protocolloSerializer.LogAndValidate("DocInserisciOut.xml", pProtocollo);
            }
            catch (Exception ex)
            {
                throw _protocolloLogs.LogErrorException("ERRORE GENERATO DURANTE LA CHIAMATA METTIALLAFIRMA", ex);
            }

            return pProtocollo;
        }

        #endregion

        #region Metodi di protocollazione
        public override DatiProtocolloResponseType Protocollazione(DatiProtocolloIn pProt)
        {
            DatiProtocolloResponseType pProtocollo = null;

            try
            {
                GetParametriFromVertDefault();

                _protocolloLogs.Debug("Chiamata al metodo di protocollazione");

                pProtocollo = CreaDatiProtocollo(ModoProtocollazione.PROTOCOLLAZIONE);
                _protocolloSerializer.LogAndValidate("ProtOut.xml", pProtocollo);


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

        private DatiProtocolloResponseType CreaDatiProtocollo(ModoProtocollazione eModoProtocollazione)
        {
            DatiProtocolloResponseType pProtocollo = new DatiProtocolloResponseType();

            //La classe ClassValidator viene usata per generare il numero del protocollo
            ClassValidator mClsVal = new ClassValidator(null);
            pProtocollo.IdProtocollo = mClsVal.GetNextVal(DatiProtocollo.Db, DatiProtocollo.IdComune, "PROT_GENERALE.PG_ID").ToString();

            switch (eModoProtocollazione)
            {
                case ModoProtocollazione.METTI_ALLA_FIRMA:
                    break;
                case ModoProtocollazione.PROTOCOLLAZIONE:
                    pProtocollo.NumeroProtocollo = mClsVal.GetNextVal(DatiProtocollo.Db, DatiProtocollo.IdComune, "PROT_GENERALE.PG_ID").ToString();
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

        public override List<DatiProtocolloLettoResponseType> LeggiProtocollo(LeggiProtocolloRequest leggiProtocolloRequest)
        {
            try
            {
                GetParametriFromVertDefault();

                if (_protocolloLogs.IsDebugEnabled)
                    _protocolloLogs.Debug("Inviata richiesta lettura di un protocollo a protocollo Default. ID: " + leggiProtocolloRequest.IdProtocollo + ", anno: " + leggiProtocolloRequest.AnnoProtocollo + ", numero: " + leggiProtocolloRequest.NumeroProtocollo + ", operatore: " + Operatore.ToUpper() + ", ruolo: " + Ruolo);

                if (_protocolloLogs.IsDebugEnabled)
                    _protocolloLogs.Debug("Chiamata al metodo LeggiProtocollo");

                var response = CreaDatiProtocolloLetto(leggiProtocolloRequest.AnnoProtocollo, leggiProtocolloRequest.NumeroProtocollo);

                if (response != null && _protocolloLogs.IsDebugEnabled)
                {
                    _protocolloSerializer.LogAndValidate(ProtocolloLogsConstants.ResponseFileName, response);
                    _protocolloLogs.DebugFormat("Ricevuta risposta lettura da protocollo Default: {0}", ProtocolloLogsConstants.ResponseFileName);
                }

                return new List<DatiProtocolloLettoResponseType> { response };
            }
            catch (Exception ex)
            {
                throw _protocolloLogs.LogErrorException("ERRORE GENERATO DURANTE IL LEGGI PROTOCOLLO", ex);
            }
        }

        private DatiProtocolloLettoResponseType CreaDatiProtocolloLetto(string annoProtocollo, string numeroProtocollo)
        {
            if (numeroProtocollo == null)
            {
                throw new ArgumentNullException("Per il ProtocolloDefault è necessario specificare i valori di numeroProtocollo e annoProtocollo");
            }

            DatiProtocolloLettoResponseType pDatiProtocolloLetto = new DatiProtocolloLettoResponseType();

            string[] sNumProtSplit = numeroProtocollo.Split(new Char[] { '/' });
            string sNumProtocollo = sNumProtSplit[0];

            var dt = DateTime.Now.AddDays(-1);

            pDatiProtocolloLetto.AnnoProtocollo = annoProtocollo;
            pDatiProtocolloLetto.NumeroProtocollo = sNumProtocollo;
            pDatiProtocolloLetto.DataProtocollo = dt.ToString("dd/MM/yyyy");
            pDatiProtocolloLetto.IdProtocollo = (Convert.ToInt32(sNumProtocollo) - 1).ToString();
            pDatiProtocolloLetto.AnnoNumeroPratica = $"{sNumProtocollo}/{annoProtocollo}";

            return pDatiProtocolloLetto;
        }


        #endregion

        #region Metodi Fascicolazione

        public override DatiProtocolloFascicolatoResponseType IsFascicolato(string idProtocollo, string annoProtocollo, string numeroProtocollo)
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
        private DatiProtocolloFascicolatoResponseType Fascicolato(string idProtocollo, string annoProtocollo, string numeroProtocollo)
        {
            DatiProtocolloFascicolatoResponseType datiProtFasc = new DatiProtocolloFascicolatoResponseType();
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
                datiProtFasc.Fascicolato = EnumFascicolatoType.si;
            }
            else
            {
                datiProtFasc.Fascicolato = EnumFascicolatoType.no;
            }

            return datiProtFasc;
        }

        public override DatiFascicoloResponseType Fascicola(Fascicolo fascicolo)
        {
            DatiFascicoloResponseType pFascicolo = new DatiFascicoloResponseType();
            return pFascicolo;
        }

        public override DatiFascicoloResponseType CambiaFascicolo(Fascicolo fascicolo)
        {
            DatiFascicoloResponseType pFascicolo = new DatiFascicoloResponseType();
            return pFascicolo;
        }

        #endregion

        #endregion

        #region Utility

        private void GetParametriFromVertDefault()
        {
            try
            {
                var protocolloDefault = this._verticalizzazioniFactory.Create<VerticalizzazioneProtocolloDefault>(DatiProtocollo.IdComuneAlias, DatiProtocollo.Software, DatiProtocollo.CodiceComune);

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

        public override List<MetadatoType> RecuperaMetadati()
        {
            return new List<MetadatoType>
            {
                new MetadatoType
                {
                    Chiave = MetadatiConstants.Oggetto,
                    Valore = null
                }
            };
        }
    }

    internal enum ModoProtocollazione { PROTOCOLLAZIONE, METTI_ALLA_FIRMA }
}