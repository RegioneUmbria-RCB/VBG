using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.Data;
using Init.SIGePro.Protocollo.Constants;
using Init.SIGePro.Protocollo.Delta.Services;
using Init.SIGePro.Protocollo.Logs;
using Init.SIGePro.Manager;
using PersonalLib2.Data;
using Init.SIGePro.Protocollo.WsDataClass;

namespace Init.SIGePro.Protocollo.Delta.Adapters
{
    internal class DeltaProtocolloLettoAdapter
    {
        public static class Constants
        { 
            public const string CLASSIFICA_LIVELLO_1 = "Livello 1: ";
            public const string CLASSIFICA_LIVELLO_2 = "Livello 2: ";

            public const string FLUSSO_ENTRATA_DELTA = "E";
            public const string FLUSSO_USCITA_DELTA = "U";

            public const string SI = "Si";
            public const string NO = "No";

            public const string DATA_DEFAULT = "01/01/0001";
        }

        public readonly DatiProtocolloLetto ProtocolloLetto;

        private DeltaTipiDocumentiService _tipiDocSrv;
        private ProtocolloLogs _logs;
        private DataBase _database;

        public DeltaProtocolloLettoAdapter(ProtocolloDeltaService.Protocollo response, DeltaTipiDocumentiService tipiDocSrv, ProtocolloLogs logs, DataBase database)
        {
            _tipiDocSrv = tipiDocSrv;
            _logs = logs;
            _database = database;

            ProtocolloLetto = CreaDatiProtocolloLetto(response);
        }

        private DatiProtocolloLetto CreaDatiProtocolloLetto(ProtocolloDeltaService.Protocollo response)
        {
            var retVal = new DatiProtocolloLetto();

            retVal.NumeroProtocollo = response.Progressivo.ToString();
            retVal.AnnoProtocollo = response.Anno.ToString();

            string codiceClassifica = String.Empty;
            string classifica = String.Empty;

            retVal.Annullato = Constants.NO;

            

            if (response.DataAttoAnnullamento != null && response.DataAttoAnnullamento.ToString("dd/MM/yyyy") != Constants.DATA_DEFAULT)
            {
                retVal.Annullato = Constants.SI;
                retVal.DataAnnullamento = response.DataAttoAnnullamento.ToString("dd/MM/yyyy");
            }

            if (!String.IsNullOrEmpty(response.ClassificazioneLiv1))
            {
                codiceClassifica = response.ClassificazioneLiv1;
                classifica = String.Concat(Constants.CLASSIFICA_LIVELLO_1, response.ClassificazioneLiv1);

                if (!String.IsNullOrEmpty(response.ClassificazioneLiv2))
                {
                    codiceClassifica += String.Concat(".", response.ClassificazioneLiv2);
                    classifica += String.Concat(", ", Constants.CLASSIFICA_LIVELLO_2, response.ClassificazioneLiv2);
                }
            }

            retVal.Classifica = codiceClassifica;
            retVal.Classifica_Descrizione = classifica;
            
            retVal.DataInserimento = response.DataDocumento.ToString("dd/MM/yyyy");
            retVal.DataProtocollo = response.DataProtocollo.ToString("dd/MM/yyyy");

            retVal.Origine = GetFlusso(response.Tipo);
            retVal.TipoDocumento = response.TipoLettera;
            var tipoDoc = GetTipoDocumento(response.TipoLettera);
            retVal.TipoDocumento_Descrizione = tipoDoc != null ? tipoDoc.Descrizione : response.TipoLettera;

            if(retVal.Origine == ProtocolloConstants.COD_ARRIVO)
            {
                //retVal.InCaricoA = response.Destinatario;
                retVal.InCaricoA_Descrizione = response.Destinatario;

                var mittenti = new List<MittDestOut>();
                List<string> mittentiResponse = new List<string>();

                if(!String.IsNullOrEmpty(response.Mittente))
                    mittentiResponse = response.Mittente.Split(',').ToList();

                if (!String.IsNullOrEmpty(response.Conoscenza))
                    mittentiResponse = mittentiResponse.Union(response.Conoscenza.Split(',').Select(x => String.Concat(x, " (PER CONOSCIENZA)"))).ToList();

                mittentiResponse.ForEach(x => mittenti.Add(new MittDestOut{ IdSoggetto = String.Empty, CognomeNome = x}));

                /*var conoscenzaResponse = response.Conoscenza.Split(',').ToList();
                conoscenzaResponse.ForEach(x => mittenti.Add(new MittDestOut { IdSoggetto = String.Empty, CognomeNome = String.Concat(x, " (PER CONOSCENZA)") }));*/

                retVal.MittentiDestinatari = mittenti.ToArray();
            }
            
            if (retVal.Origine == ProtocolloConstants.COD_PARTENZA)
            {
                //retVal.InCaricoA = response.Mittente;
                retVal.InCaricoA_Descrizione = response.Mittente;

                var destinatari = new List<MittDestOut>();
                List<string> destinatariResponse = new List<string>();

                if(!String.IsNullOrEmpty(response.Destinatario))
                    destinatariResponse = response.Destinatario.Split(',').ToList();

                if (!String.IsNullOrEmpty(response.Conoscenza))
                    destinatariResponse = destinatariResponse.Union(response.Conoscenza.Split(',').Select(x => String.Concat(x, " (PER CONOSCIENZA)"))).ToList();

                destinatariResponse.ForEach(x => destinatari.Add(new MittDestOut { IdSoggetto = String.Empty, CognomeNome = x }));

                //var conoscenzaResponse = response.Conoscenza.Split(',').ToList();
                //conoscenzaResponse.ForEach(x => destinatari.Add(new MittDestOut { IdSoggetto = String.Empty, CognomeNome = String.Concat(x, " (PER CONOSCENZA)") }));

                retVal.MittentiDestinatari = destinatari.ToArray();
            }

            retVal.Oggetto = response.Oggetto;

            if (response.Allegati != null)
            {
                var allegati = GetAllegati(response.Allegati.ToList());

                if (allegati != null)
                    retVal.Allegati = allegati;
            }

            return retVal;
        }

        private AllOut[] GetAllegati(List<ProtocolloDeltaService.Allegati> allegatiResponse)
        {
            List<AllOut> listAllegati = new List<AllOut>();
            
            allegatiResponse.ForEach(x => listAllegati.Add(new AllOut
            {
                IDBase = x.IdAllegato.ToString(),
                Commento = x.NomeFile,
                ContentType = new OggettiMgr(_database).GetContentType(x.NomeFile)
            }));

            if (listAllegati.Count > 0)
                return listAllegati.ToArray();

            return null;
        }

        private ProtocolloDeltaService.TipoLettera GetTipoDocumento(string tipoDocumento)
        {
            try
            {
                ProtocolloDeltaService.TipoLettera retVal = null;

                var documenti = _tipiDocSrv.GetTipidocumenti().ToList();
                var tipoDoc = documenti.Where(x => x.Codice == tipoDocumento).ToList();

                if (tipoDoc == null || tipoDoc.Count == 0)
                    _logs.WarnFormat("Il tipodocumento {0} non è stato trovato nell'archivio del protocollo, non sarà quindi visualizzata la descrizione in fase di lettura protocollo", tipoDocumento);
                else if (tipoDoc.Count > 1)
                    _logs.WarnFormat("Il tipodocumento {0} è stato trovato {1} volte nell'archivio del protocollo, non sarà quindi visualizzata la descrizione in fase di lettura protocollo", tipoDocumento, tipoDoc.Count.ToString());
                else
                    retVal = tipoDoc[0];

                return retVal;
            }
            catch (Exception ex)
            {
                throw new Exception("ERRORE GENERATO DURANTE IL RECUPERO DELLA TIPOLOGIA DI DOCUMENTO (TIPO LETTERA)", ex);
            }
        }


        private string GetFlusso(string flussoProtocollo)
        {
            try
            {
                string retVal = String.Empty;

                if (flussoProtocollo == Constants.FLUSSO_ENTRATA_DELTA)
                    retVal = ProtocolloConstants.COD_ARRIVO;

                if (flussoProtocollo == Constants.FLUSSO_USCITA_DELTA)
                    retVal = ProtocolloConstants.COD_PARTENZA;

                return retVal;
            }
            catch (Exception ex)
            {
                throw new Exception("ERRORE GENERATO DURANTE LA MAPPATURA DEL FLUSSO", ex);
            }
        }
    }
}
