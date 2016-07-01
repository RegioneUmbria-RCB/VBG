using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Data;
using Init.SIGePro.Protocollo.ProtocolloDocErGestioneDocumentaleService;
using Init.SIGePro.Protocollo.ProtocolloServices;
using Init.SIGePro.Manager;

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

            public static class MetadatiDizBase
            {
                public const string NUMERO_ISTANZA = "INITMD_NUMERO_ISTANZA";
                public const string RICHIEDENTE_CF = "INITMD_RICHIEDENTE_CF";
                public const string AZIENDA_CF_PIVA = "INITMD_AZIENDA_CF_PIVA";
                public const string RICHIEDENTE_DESCRIZIONE = "INITMD_RICHIEDENTE_DESCRIZIONE";
                public const string AZIENDA_RAGIONESOCIALE = "INITMD_AZIENDA_RAGIONESOCIALE";
                public const string TIPO_INTERVENTO = "INITMD_TIPO_INTERVENTO";
                public const string DOMICILIO_ELETTRONICO = "INITMD_DOMICILIO_ELETTRONICO";
                public const string CODICEPRATICATELEMATICA = "INITMD_CODICEPRATICATELEMATICA";
                public const string DATAPRATICA = "INITMD_DATAPRATICA";
                public const string SPORTELLO = "INITMD_SPORTELLO";
                public const string RESP_PROCEDIMENTO = "INITMD_RESP_PROCEDIMENTO";
            }
        }

        string[] _tipiAllegati;
        ProtocolloAllegati _allegato;
        string _typeId;
        string _codiceEnte;
        string _codiceAoo;
        string _tipoAllegato;
        ResolveDatiProtocollazioneService _datiProtoSrv;

        public GestioneDocumentaleMetadataAdapter(ProtocolloAllegati allegato, ResolveDatiProtocollazioneService datiProtoSrv, string typeId, string codiceEnte, string codiceAoo, string tipoAllegato)
        {
            _tipiAllegati = new string[] { Constants.TIPO_ALLEGATO_PRINCIPALE, Constants.TIPO_ALLEGATO_ALLEGATO, Constants.TIPO_ALLEGATO_ANNOTAZIONE, Constants.TIPO_ALLEGATO_ANNESSO };
            _allegato = allegato;
            _typeId = typeId;
            _codiceEnte = codiceEnte;
            _codiceAoo = codiceAoo;
            _tipoAllegato = tipoAllegato;
            _datiProtoSrv = datiProtoSrv;
        }

        public KeyValuePair[] Adatta()
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
                    new KeyValuePair { key = Constants.TIPO_ALLEGATO, value = _tipoAllegato },
                }.ToList();

                var mgrTipiDoc = new ProtocolloTipiDocumentoMgr(_datiProtoSrv.Db);
                var tipiDoc = mgrTipiDoc.GetById(_datiProtoSrv.IdComune, _typeId, _datiProtoSrv.Software, _datiProtoSrv.CodiceComune);

                var tipiDocMetadatiMgr = new ProtTipiDocumentoMetadatiMgr(_datiProtoSrv.Db);
                var metadatiList = tipiDocMetadatiMgr.GetList(new ProtTipiDocumentoMetadati { Idcomune = _datiProtoSrv.IdComune, Fkidprottpdoc = tipiDoc.Id });

                foreach (var item in metadatiList)
                {
                    var metadato = ValorizzaMetadati(item);
                    if (metadato != null)
                        metadati.Add(metadato);
                }

                return metadati.ToArray();
            }
            catch (System.Exception ex)
            {
                throw new System.Exception("ERRORE GENERATO DURANTE LA CONFIGURAZIONE DELLA GESTIONE DOCUMENTALE", ex);
            }
        }

        private KeyValuePair ValorizzaMetadati(ProtTipiDocumentoMetadati metadato)
        {
            var istanza = _datiProtoSrv.Istanza;

            if (metadato.Fkidmetadatidizbase == Constants.MetadatiDizBase.NUMERO_ISTANZA)
                return new KeyValuePair { key = metadato.Fkidmetadatidizbase, value = istanza.NUMEROISTANZA };

            if (metadato.Fkidmetadatidizbase == Constants.MetadatiDizBase.RICHIEDENTE_CF && istanza.Richiedente != null && !String.IsNullOrEmpty(istanza.Richiedente.CODICEFISCALE))
                return new KeyValuePair { key = metadato.Fkidmetadatidizbase, value = istanza.Richiedente.CODICEFISCALE };

            if (metadato.Fkidmetadatidizbase == Constants.MetadatiDizBase.AZIENDA_CF_PIVA && istanza.AziendaRichiedente != null)
                return new KeyValuePair { key = metadato.Fkidmetadatidizbase, value = String.IsNullOrEmpty(istanza.AziendaRichiedente.CODICEFISCALE) ? istanza.AziendaRichiedente.PARTITAIVA : istanza.AziendaRichiedente.CODICEFISCALE };

            if (metadato.Fkidmetadatidizbase == Constants.MetadatiDizBase.RICHIEDENTE_DESCRIZIONE && istanza.Richiedente != null)
                return new KeyValuePair { key = metadato.Fkidmetadatidizbase, value = istanza.Richiedente.GetNomeCompleto() };

            if (metadato.Fkidmetadatidizbase == Constants.MetadatiDizBase.AZIENDA_RAGIONESOCIALE && istanza.AziendaRichiedente != null)
                return new KeyValuePair { key = metadato.Fkidmetadatidizbase, value = istanza.AziendaRichiedente.NOMINATIVO };

            if (metadato.Fkidmetadatidizbase == Constants.MetadatiDizBase.TIPO_INTERVENTO && istanza.Intervento != null)
                return new KeyValuePair { key = metadato.Fkidmetadatidizbase, value = istanza.Intervento.SC_DESCRIZIONE };

            if (metadato.Fkidmetadatidizbase == Constants.MetadatiDizBase.DOMICILIO_ELETTRONICO && !String.IsNullOrEmpty(istanza.DOMICILIO_ELETTRONICO))
                return new KeyValuePair { key = metadato.Fkidmetadatidizbase, value = istanza.DOMICILIO_ELETTRONICO };

            if (metadato.Fkidmetadatidizbase == Constants.MetadatiDizBase.CODICEPRATICATELEMATICA && !String.IsNullOrEmpty(istanza.CODICEPRATICATEL))
                return new KeyValuePair { key = metadato.Fkidmetadatidizbase, value = istanza.CODICEPRATICATEL };

            if (metadato.Fkidmetadatidizbase == Constants.MetadatiDizBase.DATAPRATICA)
                return new KeyValuePair { key = metadato.Fkidmetadatidizbase, value = istanza.DATA.Value.ToString("yyyy-MM-dd") };

            if (metadato.Fkidmetadatidizbase == Constants.MetadatiDizBase.SPORTELLO)
            {
                var sportelloMgr = new SoftwareMgr(_datiProtoSrv.Db);
                var sportello = sportelloMgr.GetById(istanza.SOFTWARE);

                return new KeyValuePair { key = metadato.Fkidmetadatidizbase, value = sportello.DESCRIZIONE };
            }

            if (metadato.Fkidmetadatidizbase == Constants.MetadatiDizBase.RESP_PROCEDIMENTO && istanza.ResponsabileProc != null)
                return new KeyValuePair { key = metadato.Fkidmetadatidizbase, value = istanza.ResponsabileProc.RESPONSABILE };

            return null;
        }
    }
}
