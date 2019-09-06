using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.Sigedo.Proxies;
using Init.SIGePro.Protocollo.Data;
using Init.SIGePro.Protocollo.Sigedo.Configurations;
using Init.SIGePro.Protocollo.Constants;
using Init.SIGePro.Manager;
using PersonalLib2.Data;
using Init.SIGePro.Protocollo.Sigedo.Proxies.QueryService;
using Init.SIGePro.Protocollo.WsDataClass;

namespace Init.SIGePro.Protocollo.Sigedo.Adapters
{
    public class SigedoProtocolloLettoAdapter
    {

        public static class Constants
        {
            public const string METADATO_NOME = "NOME";
            public const string METADATO_COGNOME = "COGNOME";
            public const string METADATO_CODICEFISCALE = "CF_PER_SEGNATURA";
        }

        RisultatoRicerca _responseProto;
        DataBase _db;
        
        public SigedoProtocolloLettoAdapter(RisultatoRicerca responseProto, DataBase db)
        {
            _responseProto = responseProto;
            _db = db;
        }

        public string GetIdRiferimento()
        {
            var listDocs = _responseProto.listaDocumenti;
            var metadataOutConf = new SigedoMetadataOutputConfiguration(listDocs[0].metadati);
            var retVal = metadataOutConf.GetValue(SigedoMetadataOutputConfiguration.IDRIF);
            return retVal;
        }

        public void CreaDatiAllegatiSecondari(RisultatoRicerca responseAllegati, DatiProtocolloLetto dati)
        {
            if (responseAllegati.listaDocumenti != null && responseAllegati.listaDocumenti.Length > 0)
            {
                var listDocs = responseAllegati.listaDocumenti;

                var listAll = listDocs.SelectMany(docs => docs.allegati.Select(doc => new AllOut
                {
                    Serial = doc.idAllegato.ToString(),
                    IDBase = doc.idAllegato.ToString(),
                    Commento = doc.descrizione,
                    TipoFile = doc.tipoAllegato,
                    ContentType = String.IsNullOrEmpty(doc.tipoAllegato) ? String.Empty : new OggettiMgr(_db).GetContentType(doc.descrizione)
                }));

                if (dati.Allegati != null)
                    dati.Allegati = dati.Allegati.Union(listAll).ToArray();
                else
                    dati.Allegati = listAll.ToArray();
            }
        }

        public void CreaDatiMittentiDestinatari(RisultatoRicerca responseMittentiDestinatari, DatiProtocolloLetto dati, string area, string descrizioneEnte)
        {
            if (responseMittentiDestinatari.listaDocumenti != null && responseMittentiDestinatari.listaDocumenti.Length > 0)
            {
                var metaDatiMittentiODestinatari = responseMittentiDestinatari
                                                        .listaDocumenti
                                                        .Select(x => x.metadati.Where(metaDato => metaDato.area == area && (metaDato.codice == Constants.METADATO_COGNOME || metaDato.codice == Constants.METADATO_NOME || metaDato.codice == Constants.METADATO_CODICEFISCALE)));

                var mittentiDestinatari = metaDatiMittentiODestinatari.Select(x => new MittDestOut
                {
                    CognomeNome = x.Where(md => md.area == area && md.codice == Constants.METADATO_COGNOME).Select(md => md.valore).FirstOrDefault() + " " +
                                  x.Where(md => md.area == area && md.codice == Constants.METADATO_NOME).Select(md => md.valore).FirstOrDefault() + " " +
                                  x.Where(md => md.area == area && md.codice == Constants.METADATO_CODICEFISCALE).Select(md => md.valore).FirstOrDefault(),
                    IdSoggetto = ""
                });

                dati.MittentiDestinatari = mittentiDestinatari.ToArray();
            }
            else
            {
                var mittenti = new List<MittDestOut>();
                mittenti.Add(new MittDestOut { CognomeNome = descrizioneEnte });

                dati.MittentiDestinatari = mittenti.ToArray();
            }
        }

        public void SetDescrizioneClassifica(RisultatoRicerca responseClassifica, DatiProtocolloLetto dati)
        {
            if (responseClassifica.listaDocumenti != null && responseClassifica.listaDocumenti.Length > 0)
            {
                var listDocs = responseClassifica.listaDocumenti;

                var metadataOutConf = new SigedoMetadataOutputConfiguration(responseClassifica.listaDocumenti[0].metadati);
                
                if (metadataOutConf != null)
                    dati.Classifica_Descrizione = metadataOutConf.GetValue(SigedoMetadataOutputConfiguration.NOME);
            }
        }

        public void CreaInCaricoA(RisultatoRicerca responseSmistamento, DatiProtocolloLetto dati, string descrizioneEnte)
        {
            if (responseSmistamento.listaDocumenti != null && responseSmistamento.listaDocumenti.Length > 1)
            {
                var listaDocumenti = responseSmistamento.listaDocumenti.Where(d => d.metadati.Count(x => x.codice == "TIPO_SMISTAMENTO" && x.valore == "DUMMY") == 0);

                foreach (var smistamento in listaDocumenti)
                {
                    var metadataOutConf = new SigedoMetadataOutputConfiguration(smistamento.metadati);
                    if (metadataOutConf != null)
                    {
                        /*dati.InCaricoA = metadataOutConf.GetValue(SigedoMetadataOutputConfiguration.CODICE_UFFICIO_SMISTAMENTO);
                        dati.InCaricoA_Descrizione = metadataOutConf.GetValue(SigedoMetadataOutputConfiguration.DESCRIZIONE_UFFICIO_SMISTAMENTO);*/
                        dati.InCaricoA_Descrizione += String.Concat(metadataOutConf.GetValue(SigedoMetadataOutputConfiguration.DESCRIZIONE_UFFICIO_SMISTAMENTO), " Trasmesso da: ", metadataOutConf.GetValue(SigedoMetadataOutputConfiguration.DESCRIZIONE_UFFICIO_TRASMISSIONE), "<br/>");
                    }
                }

            }

            if (String.IsNullOrEmpty(dati.InCaricoA_Descrizione))
                dati.InCaricoA_Descrizione = descrizioneEnte;
        }   

        public void CreaDatiProtocollo(RisultatoRicerca responseProto, DatiProtocolloLetto dati)
        {
            try
            {
                var listDocs = _responseProto.listaDocumenti;

                var metadataOutConf = new SigedoMetadataOutputConfiguration(listDocs[0].metadati);
                dati.NumeroProtocollo = metadataOutConf.GetValue(SigedoMetadataOutputConfiguration.NUMERO_PROTOCOLLO);

                dati.Oggetto = metadataOutConf.GetValue(SigedoMetadataOutputConfiguration.OGGETTO);
                dati.AnnoProtocollo = metadataOutConf.GetValue(SigedoMetadataOutputConfiguration.ANNO);

                dati.Classifica = metadataOutConf.GetValue(SigedoMetadataOutputConfiguration.CODICE_CLASSIFICA);
                //dati.Classifica_Descrizione = metadataOutConf.GetValue(SigedoMetadataOutputConfiguration.CODICE_CLASSIFICA);
                dati.DataProtocollo = metadataOutConf.GetValue(SigedoMetadataOutputConfiguration.DATA);
                //dati.IdProtocollo = metadataOutConf.GetValue(SigedoMetadataOutputConfiguration.IDRIF);
                
                //dati.InCaricoA = metadataOutConf.GetValue(SigedoMetadataOutputConfiguration.CODICE_UFFICIO);

                string flusso = String.Empty;
                string flussoWs = metadataOutConf.GetValue(SigedoMetadataOutputConfiguration.FLUSSO);

                if (flussoWs == SigedoMetadataOutputConfiguration.ARRIVO)
                    flusso = ProtocolloConstants.COD_ARRIVO;

                if (flussoWs == SigedoMetadataOutputConfiguration.PARTENZA)
                    flusso = ProtocolloConstants.COD_PARTENZA;

                if (flussoWs == SigedoMetadataOutputConfiguration.INTERNO)
                    flusso = ProtocolloConstants.COD_INTERNO;

                if (flusso != ProtocolloConstants.COD_INTERNO)
                {
                    var annullato = metadataOutConf.GetValue(SigedoMetadataOutputConfiguration.ANNULLATO);
                    dati.Annullato = String.IsNullOrEmpty(annullato) ? "No" : metadataOutConf.GetValue(SigedoMetadataOutputConfiguration.ANNULLATO);
                }

                dati.Origine = flusso;

                if (listDocs[0].allegati == null)
                    return;

                var listAllegati = new List<AllOut>();

                foreach (var doc in listDocs[0].allegati)
                {
                    listAllegati.Add(new AllOut
                    {
                        Serial = doc.idAllegato.ToString(),
                        IDBase = doc.idAllegato.ToString(),
                        Commento = doc.descrizione,
                        TipoFile = doc.tipoAllegato,
                        ContentType = String.IsNullOrEmpty(doc.tipoAllegato) ? String.Empty : new OggettiMgr(_db).GetContentType(doc.descrizione)
                    });
                }

                dati.Allegati = listAllegati.ToArray();
            }
            catch (Exception ex)
            {
                throw new Exception("ERRORE GENERATO DURANTE LA MAPPATURA DEI DATI RESTITUITI DAL WEB SERVICE QUERYSERVICE NELL'ADATTATORE", ex);
            }
        }
    }
}
