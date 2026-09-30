using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.Delta.Adapters;
using Init.SIGePro.Protocollo.ProtocolloInterfaces;
using Init.SIGePro.Protocollo.Constants;
using Init.SIGePro.Protocollo.Data;
using Init.SIGePro.Data;
using Init.SIGePro.Protocollo.Logs;

namespace Init.SIGePro.Protocollo.Delta.Builders
{
    internal class DeltaSegnaturaParamConfiguration
    {
        public static class Constants
        {
            public const string FLUSSO_ENTRATA_DELTA = "E";
            public const string FLUSSO_USCITA_DELTA = "U";
        }

        public readonly string CodiceRegistro;
        public readonly string Destinatario;
        public readonly string Mittente;
        public readonly string Oggetto;
        public readonly string Tipo;
        public readonly string TipoLettera;
        public readonly string Classifica1;
        public readonly string Classifica2;
        public readonly string Conoscenza;

        private IDatiProtocollo _datiProto;
        private DeltaVerticalizzazioneParametriAdapter _vert;
        private ProtocolloLogs _logs;

        public DeltaSegnaturaParamConfiguration(DeltaVerticalizzazioneParametriAdapter vert, IDatiProtocollo datiProto, string oggetto, string classifica, 
                                                string tipoDocumento, ProtocolloLogs logs)
        {
            _logs = logs;
            _datiProto = datiProto;
            _vert = vert;

            CodiceRegistro = vert.Registro;
            Destinatario = CreaDestinatario();
            Mittente = CreaMittente();
            Oggetto = oggetto;
            Tipo = MappaturaFlusso();
            TipoLettera = tipoDocumento;

            if (String.IsNullOrEmpty(classifica))
                throw new Exception("CLASSIFICA NON VALORIZZATA");

            var listClassifica = classifica.Split('.').ToList();
            Classifica1 = listClassifica[0];

            if (listClassifica.Count > 1)
                Classifica2 = listClassifica[1];

            Conoscenza = CreaConoscenza();
        }

        private string CreaMittente()
        {
            try
            {
                _logs.Debug("Configurazione dei mittenti");
                string retVal = String.Empty;

                if (_datiProto.Flusso == ProtocolloConstants.COD_ARRIVO)
                {
                    string anagrafiche = String.Join(",", _datiProto.AnagraficheProtocollo.Where(x => x.ModalitaTrasmissione != _vert.CodiceCC).Select(x => String.Concat(x.NOMINATIVO, " ", x.NOME)));
                    string amministrazioni = String.Join(",", _datiProto.AmministrazioniProtocollo.Where(x => x.ModalitaTrasmissione != _vert.CodiceCC).Select(x => x.AMMINISTRAZIONE));

                    /*if (_datiProto.IsAmministrazioneInterna)
                        throw new Exception("NON E' POSSIBILE SPECIFICARE UN'AMMINISTRAZIONE INTERNA NEL MITTENTE PER UN PROTOCOLLO IN ARRIVO");*/

                    if (!String.IsNullOrEmpty(anagrafiche) && !String.IsNullOrEmpty(amministrazioni))
                        retVal = String.Concat(anagrafiche, ",", amministrazioni);
                    else if (String.IsNullOrEmpty(anagrafiche) && !String.IsNullOrEmpty(amministrazioni))
                        retVal = amministrazioni;
                    else if (!String.IsNullOrEmpty(anagrafiche) && String.IsNullOrEmpty(amministrazioni))
                        retVal = anagrafiche;
                    /*else
                        throw new Exception("NON E' PRESENTE NESSUN MITTENTE, CONTROLLARE CHE QUELLI INSERITI NON SIANO SOLAMENTE PER CONOSCENZA");*/
                }

                if (_datiProto.Flusso == ProtocolloConstants.COD_PARTENZA)
                    retVal = _datiProto.Amministrazione.PROT_UO;

                _logs.Debug("Fine configurazione dei mittenti");

                return retVal;
            }
            catch (Exception ex)
            {
                throw new Exception("ERRORE GENERATO DURANTE LA CONFIGURAZIONE DEI MITTENTI", ex);
            }
        }

        private string CreaDestinatario()
        {
            try
            {
                _logs.Debug("Configurazione dei destinatari");

                string retVal = String.Empty;

                if (_datiProto.Flusso == ProtocolloConstants.COD_ARRIVO)
                    retVal = _datiProto.Amministrazione.PROT_UO;

                if (_datiProto.Flusso == ProtocolloConstants.COD_PARTENZA)
                {
                    string anagrafiche = String.Join(",", _datiProto.AnagraficheProtocollo.Where(x => x.ModalitaTrasmissione != _vert.CodiceCC).Select(x => String.Concat(x.NOMINATIVO, " ", x.NOME)));
                    string amministrazioni = String.Join(",", _datiProto.AmministrazioniProtocollo.Where((x => x.ModalitaTrasmissione != _vert.CodiceCC)).Select(x => !String.IsNullOrEmpty(x.PROT_UO) ? x.PROT_UO : x.AMMINISTRAZIONE));

                    if (!String.IsNullOrEmpty(anagrafiche) && !String.IsNullOrEmpty(amministrazioni))
                        retVal = String.Concat(anagrafiche, ",", amministrazioni);
                    else if (String.IsNullOrEmpty(anagrafiche) && !String.IsNullOrEmpty(amministrazioni))
                        retVal = amministrazioni;
                    else if (!String.IsNullOrEmpty(anagrafiche) && String.IsNullOrEmpty(amministrazioni))
                        retVal = anagrafiche;
                    /*else
                        throw new Exception("NON E' PRESENTE NESSUN DESTINATARIO, CONTROLLARE CHE QUELLI INSERITI NON SIANO SOLAMENTE PER CONOSCENZA");*/
                }

                _logs.Debug("Fine configurazione dei destinatari");

                return retVal;
            }
            catch (Exception ex)
            {
                throw new Exception("ERRORE GENERATO DURANTE LA CONFIGURAZIONE DEI DESTINATARI", ex);
            }
        }

        private string CreaConoscenza()
        {
            try
            {
                _logs.Debug("Configurazione dei mittenti / destinatari per Conoscenza");

                string anagrafiche = String.Join(",", _datiProto.AnagraficheProtocollo.Where(x => x.ModalitaTrasmissione == _vert.CodiceCC).Select(x => String.Concat(x.NOMINATIVO, " ", x.NOME) )); ;
                string amministrazioni = String.Join(",", _datiProto.AmministrazioniProtocollo.Where((x => x.ModalitaTrasmissione == _vert.CodiceCC)).Select(x => !String.IsNullOrEmpty(x.PROT_UO) ? x.PROT_UO : x.AMMINISTRAZIONE));
                string retVal = String.Empty;

                if (!String.IsNullOrEmpty(anagrafiche) && !String.IsNullOrEmpty(amministrazioni))
                    retVal = String.Concat(anagrafiche, ",", amministrazioni);
                else if (String.IsNullOrEmpty(anagrafiche) && !String.IsNullOrEmpty(amministrazioni))
                    retVal = amministrazioni;
                else if (!String.IsNullOrEmpty(anagrafiche) && String.IsNullOrEmpty(amministrazioni))
                    retVal = anagrafiche;

                _logs.Debug("Fine configurazione dei mittenti / destinatari per Conoscenza");

                return retVal;
            }
            catch (Exception ex)
            {
                throw new Exception("ERRORE GENERATO DURANTE LA CONFIGURAZIONE DEI MITTENTI / DESTINATARI PER CONOSCENZA ", ex);
            }
        }

        private string MappaturaFlusso()
        {
            try
            {
                string retVal = String.Empty;

                if (_datiProto.Flusso != ProtocolloConstants.COD_ARRIVO && _datiProto.Flusso != ProtocolloConstants.COD_PARTENZA)
                    throw new Exception(String.Format("IL FLUSSO DEL PROTOCOLLO DEVE ESSERE O IN ARRIVO O IN PARTENZA, NON SONO SUPPORTATI ALTRE TIPOLOGIE DI FLUSSI, CODICE FLUSSO INVIATO: {0}", _datiProto.Flusso));

                if (_datiProto.Flusso == ProtocolloConstants.COD_ARRIVO)
                    retVal = Constants.FLUSSO_ENTRATA_DELTA;

                if (_datiProto.Flusso == ProtocolloConstants.COD_PARTENZA)
                    retVal = Constants.FLUSSO_USCITA_DELTA;

                if (String.IsNullOrEmpty(retVal))
                    throw new Exception("IL FLUSSO NON E' VALORIZZATO");

                return retVal;
            }
            catch (Exception ex)
            {
                throw new Exception("ERRORE GENERATO DURANTE LA MAPPATURA DEL FLUSSO", ex);
            }
        }

    }
}
