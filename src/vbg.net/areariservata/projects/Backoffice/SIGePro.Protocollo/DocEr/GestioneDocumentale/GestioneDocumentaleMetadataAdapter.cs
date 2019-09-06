using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Data;
using Init.SIGePro.Protocollo.ProtocolloDocErGestioneDocumentaleService;
using Init.SIGePro.Protocollo.ProtocolloServices;
using Init.SIGePro.Manager;
using Init.SIGePro.Protocollo.Logs;
using Init.SIGePro.Protocollo.DocEr.Verticalizzazioni;

namespace Init.SIGePro.Protocollo.DocEr.GestioneDocumentale
{
    internal class GestioneDocumentaleMetadataAdapter
    {
        private static class Constants
        {
            public const string TYPE_ID = "TYPE_ID";
            public const string NOME_OGGETTO = "DOCNAME";
            public const string CODICE_ENTE = "COD_ENTE";
            public const string CODICE_AOO = "COD_AOO";
            public const string DESCRIZIONE_OGGETTO = "ABSTRACT";
            public const string TIPO_ALLEGATO = "TIPO_COMPONENTE";

            public const string TIPO_ALLEGATO_PRINCIPALE = "PRINCIPALE";
            public const string TIPO_ALLEGATO_ALLEGATO = "ALLEGATO";
            public const string TIPO_ALLEGATO_ANNOTAZIONE = "ANNOTAZIONE";
            public const string TIPO_ALLEGATO_ANNESSO = "ANNESSO";

            public static class MetadatiSuapBase
            {
                public const string TipoProvvedimento = "tipo_provvedimento";
                public const string TipoAdempimento = "tipo_adempimento";
                public const string NomeRichiedente = "nome_richiedente";
                public const string CognomeRichiedente = "cognome_richiedente";
                public const string CfRichiedente = "cf_richiedente";
                public const string RagioneSocialeRichiedente = "ragione_sociale_richiedente";
                public const string PivaRichiedente = "piva_richiedente";
                public const string NomeTecnico = "nome_tecnico";
                public const string CognomeTecnico = "cognome_tecnico";
                public const string CfTecnico = "cf_tecnico";
                public const string PivaTecnico = "piva_tecnico";
                public const string IndirizziToponomastici = "indirizzi_toponomastici";
                public const string RiferimentiCatastali = "riferimenti_catastali";
                public const string OggettoIstanza = "oggetto_istanza";
                public const string RiferimentoIstanza = "riferimento_istanza";
                public const string NumeroIstanza = "numero_istanza";
            }
        }

        string[] _tipiAllegati;
        ProtocolloAllegati _allegato;
        string _typeId;
        string _codiceEnte;
        string _codiceAoo;
        string _tipoAllegato;
        ResolveDatiProtocollazioneService _datiProtoSrv;
        ProtocolloLogs _log;

        public GestioneDocumentaleMetadataAdapter(ProtocolloAllegati allegato, ResolveDatiProtocollazioneService datiProtoSrv, string typeId, string codiceEnte, string codiceAoo, string tipoAllegato, ProtocolloLogs log)
        {
            _tipiAllegati = new string[] { Constants.TIPO_ALLEGATO_PRINCIPALE, Constants.TIPO_ALLEGATO_ALLEGATO, Constants.TIPO_ALLEGATO_ANNOTAZIONE, Constants.TIPO_ALLEGATO_ANNESSO };
            _allegato = allegato;
            _typeId = typeId;
            _codiceEnte = codiceEnte;
            _codiceAoo = codiceAoo;
            _tipoAllegato = tipoAllegato;
            _datiProtoSrv = datiProtoSrv;
            _log = log;
        }

        public KeyValuePair[] Adatta(bool disabilitaMetadati)
        {
            try
            {
                if (!_tipiAllegati.Contains(_tipoAllegato.Trim().ToUpper()))
                    throw new System.Exception(String.Format("TIPO ALLEGATO {0} NON VALIDO, SI ACCETTANO SOLO I VALORI: {1}", _tipoAllegato, String.Join(", ", _tipiAllegati)));

                var metadati = new KeyValuePair[]{
                    new KeyValuePair { key = Constants.TYPE_ID, value = _typeId },
                    new KeyValuePair { key = Constants.NOME_OGGETTO, value = _allegato.NOMEFILE },
                    new KeyValuePair { key = Constants.CODICE_ENTE, value = _codiceEnte },
                    new KeyValuePair { key = Constants.CODICE_AOO, value = _codiceAoo },
                    new KeyValuePair { key = Constants.DESCRIZIONE_OGGETTO,  value = _allegato.Descrizione },
                    new KeyValuePair { key = Constants.TIPO_ALLEGATO, value = _tipoAllegato }
                }.ToList();

                _log.DebugFormat("Istanza null: {0}", _datiProtoSrv.Istanza == null);
                _log.InfoFormat("Disabilita Metadati: {0}", disabilitaMetadati);

                if (_datiProtoSrv.Istanza == null || disabilitaMetadati)
                    return metadati.ToArray();

                var istanza = _datiProtoSrv.Istanza;

                string nomeRichiedente = istanza.Richiedente != null && istanza.Richiedente.TIPOANAGRAFE == "F" ? istanza.Richiedente.NOME : "";
                string cognomeRichiedente = istanza.Richiedente != null && istanza.Richiedente.TIPOANAGRAFE == "F" ? istanza.Richiedente.NOMINATIVO : "";
                string cfRichiedente = istanza.Richiedente != null && istanza.Richiedente.TIPOANAGRAFE == "F" ? istanza.Richiedente.CODICEFISCALE : "";

                string RagioneSocialeRichiedente = "";
                string PartitaIvaRichiedente = "";

                _log.DebugFormat("Azienda null: {0}", istanza.AziendaRichiedente == null);
                _log.DebugFormat("Richiedente null: {0}", istanza.Richiedente == null);
                _log.DebugFormat("Tipo Anagrafe: {0}", istanza.Richiedente.TIPOANAGRAFE);

                if (istanza.AziendaRichiedente != null)
                {
                    RagioneSocialeRichiedente = istanza.AziendaRichiedente.NOMINATIVO;
                    PartitaIvaRichiedente = istanza.AziendaRichiedente.PARTITAIVA;
                }
                else
                    if (istanza.Richiedente != null && istanza.Richiedente.TIPOANAGRAFE == "G")
                    {
                        RagioneSocialeRichiedente = istanza.Richiedente.NOMINATIVO;
                        PartitaIvaRichiedente = istanza.Richiedente.PARTITAIVA;
                    }

                string nomeTecnico = "";
                string cognomeTecnico = "";
                string cfTecnico = "";
                string pivaTecnico = "";

                _log.DebugFormat("Tecnico is null: {0}", istanza.Professionista == null);

                if (istanza.Professionista != null)
                {
                    nomeTecnico = istanza.Professionista.NOME;
                    cognomeTecnico = istanza.Professionista.NOMINATIVO;
                    cfTecnico = istanza.Professionista.CODICEFISCALE;
                    pivaTecnico = istanza.Professionista.PARTITAIVA;
                }

                _log.DebugFormat("Stradario Primario is null: {0}", istanza.StradarioPrimario == null);

                string indirizzo = "";
                if (istanza.StradarioPrimario != null && istanza.StradarioPrimario.Stradario != null)
                    indirizzo = String.Format("{0} {1} {2} {3} {4}", istanza.StradarioPrimario.Stradario.PREFISSO, istanza.StradarioPrimario.Stradario.DESCRIZIONE, istanza.StradarioPrimario.CIVICO, istanza.StradarioPrimario.Stradario.LOCFRAZ, istanza.StradarioPrimario.Stradario.CAP);

                _log.DebugFormat("Settato l'indirizzo: {0}", indirizzo);

                string riferimentiCatastali = "";

                var stradarioPrimario = istanza.StradarioPrimario;

                if (stradarioPrimario != null)
                {
                    var mappaleMgr = new IstanzeMappaliMgr(_datiProtoSrv.Db);
                    _log.DebugFormat("chiamata a GetPrimarioByIdStradario, ID stradario primario: {0}", stradarioPrimario.ID);
                    var mappale = mappaleMgr.GetPrimarioByIdStradario(_datiProtoSrv.IdComune, Convert.ToInt32(stradarioPrimario.ID));
                    _log.DebugFormat("mappale is null: {0}", mappale == null);
                    if (mappale != null)
                        riferimentiCatastali = String.Format("{0}, Sezione: {1}, Foglio: {2}, Particella: {3}, Sub: {4}, Unità Immobiliare: {5}", mappale.Catasto != null ? mappale.Catasto.DESCRIZIONE : "", mappale.Sezione, mappale.Foglio, mappale.Particella, mappale.Sub, mappale.Unitaimmob);

                    _log.DebugFormat("riferimentiCatastali: {0}", riferimentiCatastali);
                }

                _log.DebugFormat("codice intervento proc: {0}", _datiProtoSrv.CodiceInterventoProc);
                string intervento = "";
                if (_datiProtoSrv.CodiceInterventoProc.HasValue)
                {
                    _log.Debug("chiamata a GetDescrizioneCompletaDaIdIntervento");
                    intervento = new AlberoProcMgr(_datiProtoSrv.Db).GetDescrizioneCompletaDaIdIntervento(_datiProtoSrv.CodiceInterventoProc.Value, _datiProtoSrv.IdComune, _datiProtoSrv.Software);
                    _log.DebugFormat("chiamata a GetDescrizioneCompletaDaIdIntervento avvenuta con successo, intervento null: {0}", intervento == null);
                }

                var tipoProcedura = new TipiProcedureMgr(_datiProtoSrv.Db).GetById(_datiProtoSrv.IdComune, Convert.ToInt32(istanza.CODICEPROCEDURA));
                _log.DebugFormat("Tipo procedura null: {0}", tipoProcedura == null);
                string procedura = "";
                if (tipoProcedura != null)
                    procedura = tipoProcedura.Procedura;

                var tipiDocMgr = new ProtocolloTipiDocumentoMgr(_datiProtoSrv.Db);
                var tipoDoc = tipiDocMgr.GetById(_datiProtoSrv.IdComune, _typeId, _datiProtoSrv.Software, _datiProtoSrv.CodiceComune);

                if(tipoDoc == null)
                    return metadati.ToArray();;

                var metadatiMgr = new ProtocolloTipiDocMetadatiMgr(_datiProtoSrv.Db);
                if (!String.IsNullOrEmpty(intervento) && metadatiMgr.GetById(_datiProtoSrv.IdComune, tipoDoc.Id.Value, Constants.MetadatiSuapBase.TipoProvvedimento) != null)
                    metadati.Add(new KeyValuePair { key = Constants.MetadatiSuapBase.TipoProvvedimento, value = intervento });

                if (!String.IsNullOrEmpty(procedura) && metadatiMgr.GetById(_datiProtoSrv.IdComune, tipoDoc.Id.Value, Constants.MetadatiSuapBase.TipoAdempimento) != null)
                    metadati.Add(new KeyValuePair { key = Constants.MetadatiSuapBase.TipoAdempimento, value = procedura });

                if (!String.IsNullOrEmpty(nomeRichiedente) && metadatiMgr.GetById(_datiProtoSrv.IdComune, tipoDoc.Id.Value, Constants.MetadatiSuapBase.NomeRichiedente) != null)
                    metadati.Add(new KeyValuePair { key = Constants.MetadatiSuapBase.NomeRichiedente, value = nomeRichiedente });

                if (!String.IsNullOrEmpty(cognomeRichiedente) && metadatiMgr.GetById(_datiProtoSrv.IdComune, tipoDoc.Id.Value, Constants.MetadatiSuapBase.CognomeRichiedente) != null)
                    metadati.Add(new KeyValuePair { key = Constants.MetadatiSuapBase.CognomeRichiedente, value = cognomeRichiedente });

                if (!String.IsNullOrEmpty(cfRichiedente) && metadatiMgr.GetById(_datiProtoSrv.IdComune, tipoDoc.Id.Value, Constants.MetadatiSuapBase.CfRichiedente) != null)
                    metadati.Add(new KeyValuePair { key = Constants.MetadatiSuapBase.CfRichiedente, value = cfRichiedente });

                if (!String.IsNullOrEmpty(RagioneSocialeRichiedente) && metadatiMgr.GetById(_datiProtoSrv.IdComune, tipoDoc.Id.Value, Constants.MetadatiSuapBase.RagioneSocialeRichiedente) != null)
                    metadati.Add(new KeyValuePair { key = Constants.MetadatiSuapBase.RagioneSocialeRichiedente, value = RagioneSocialeRichiedente });

                if (!String.IsNullOrEmpty(PartitaIvaRichiedente) && metadatiMgr.GetById(_datiProtoSrv.IdComune, tipoDoc.Id.Value, Constants.MetadatiSuapBase.PivaRichiedente) != null)
                    metadati.Add(new KeyValuePair { key = Constants.MetadatiSuapBase.PivaRichiedente, value = PartitaIvaRichiedente });

                if (!String.IsNullOrEmpty(nomeTecnico) && metadatiMgr.GetById(_datiProtoSrv.IdComune, tipoDoc.Id.Value, Constants.MetadatiSuapBase.NomeTecnico) != null)
                    metadati.Add(new KeyValuePair { key = Constants.MetadatiSuapBase.NomeTecnico, value = nomeTecnico });

                if (!String.IsNullOrEmpty(cognomeTecnico) && metadatiMgr.GetById(_datiProtoSrv.IdComune, tipoDoc.Id.Value, Constants.MetadatiSuapBase.CognomeTecnico) != null)
                    metadati.Add(new KeyValuePair { key = Constants.MetadatiSuapBase.CognomeTecnico, value = cognomeTecnico });

                if (!String.IsNullOrEmpty(cfTecnico) && metadatiMgr.GetById(_datiProtoSrv.IdComune, tipoDoc.Id.Value, Constants.MetadatiSuapBase.CfTecnico) != null)
                    metadati.Add(new KeyValuePair { key = Constants.MetadatiSuapBase.CfTecnico, value = cfTecnico });

                if (!String.IsNullOrEmpty(pivaTecnico) && metadatiMgr.GetById(_datiProtoSrv.IdComune, tipoDoc.Id.Value, Constants.MetadatiSuapBase.PivaTecnico) != null)
                    metadati.Add(new KeyValuePair { key = Constants.MetadatiSuapBase.PivaTecnico, value = pivaTecnico });

                if (!String.IsNullOrEmpty(indirizzo) && metadatiMgr.GetById(_datiProtoSrv.IdComune, tipoDoc.Id.Value, Constants.MetadatiSuapBase.IndirizziToponomastici) != null)
                    metadati.Add(new KeyValuePair { key = Constants.MetadatiSuapBase.IndirizziToponomastici, value = indirizzo });

                if (!String.IsNullOrEmpty(riferimentiCatastali) && metadatiMgr.GetById(_datiProtoSrv.IdComune, tipoDoc.Id.Value, Constants.MetadatiSuapBase.RiferimentiCatastali) != null)
                    metadati.Add(new KeyValuePair { key = Constants.MetadatiSuapBase.RiferimentiCatastali, value = riferimentiCatastali });

                if (!String.IsNullOrEmpty(istanza.LAVORI) && metadatiMgr.GetById(_datiProtoSrv.IdComune, tipoDoc.Id.Value, Constants.MetadatiSuapBase.OggettoIstanza) != null)
                    metadati.Add(new KeyValuePair { key = Constants.MetadatiSuapBase.OggettoIstanza, value = istanza.LAVORI });

                if (!String.IsNullOrEmpty(istanza.NUMEROISTANZA) && metadatiMgr.GetById(_datiProtoSrv.IdComune, tipoDoc.Id.Value, Constants.MetadatiSuapBase.RiferimentoIstanza) != null)
                    metadati.Add(new KeyValuePair { key = Constants.MetadatiSuapBase.RiferimentoIstanza, value = istanza.NUMEROISTANZA });

                if (!String.IsNullOrEmpty(istanza.NUMEROISTANZA) && metadatiMgr.GetById(_datiProtoSrv.IdComune, tipoDoc.Id.Value, Constants.MetadatiSuapBase.NumeroIstanza) != null)
                    metadati.Add(new KeyValuePair { key = Constants.MetadatiSuapBase.NumeroIstanza, value = istanza.NUMEROISTANZA });

                _log.Debug("fine valorizzazione metadati");

                return metadati.ToArray();
            }
            catch (System.Exception ex)
            {
                throw new System.Exception("ERRORE GENERATO DURANTE LA CONFIGURAZIONE DELLA GESTIONE DOCUMENTALE", ex);
            }
        }
    }
}
