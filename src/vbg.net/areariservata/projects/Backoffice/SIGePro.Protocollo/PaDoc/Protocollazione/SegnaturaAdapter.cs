using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.ProtocolloInterfaces;
using Init.SIGePro.Protocollo.Serialize;
using Init.SIGePro.Protocollo.PaDoc.Verticalizzazioni;
using Init.SIGePro.Protocollo.PaDoc.Protocollazione.Flusso;
using Init.SIGePro.Protocollo.PaDoc.Protocollazione.DocumentiAllegati;
using Init.SIGePro.Protocollo.Logs;

namespace Init.SIGePro.Protocollo.PaDoc.Protocollazione
{
    public class SegnaturaAdapter
    {
        IDatiProtocollo _datiProto;
        VerticalizzazioniConfiguration _vert;
        IProtocollazione _proto;
        ProtocolloSerializer _serializer;
        string _idComuneAlias;
        string _software;

        public string Flusso { get; private set; }

        public SegnaturaAdapter(IDatiProtocollo datiProto, VerticalizzazioniConfiguration vert, ProtocolloSerializer serializer, IProtocollazione proto, string idComuneAlias, string software)
        {
            _datiProto = datiProto;
            _vert = vert;
            _proto = proto;
            _serializer = serializer;
            _idComuneAlias = idComuneAlias;
            _software = software;
        }

        public string AdattaSuap()
        {
            var protoFlusso = ProtocollazioneFlussoFactory.Create(_datiProto, _vert);


            var amm = _datiProto.AmministrazioniEsterne.Select(x => new form_jobForm_section
            {
                field = new form_jobForm_sectionField[] 
                { 
                    new form_jobForm_sectionField { name = "CORRISPONDENT_NAME", Value = x.AMMINISTRAZIONE },
                    new form_jobForm_sectionField { name = "CORRISPONDENT_ADDRESS", Value = x.INDIRIZZO },
                    new form_jobForm_sectionField { name = "CORRISPONDENT_CAP", Value = x.CAP },
                    new form_jobForm_sectionField { name = "CORRISPONDENT_TOWN", Value = x.CITTA },
                    new form_jobForm_sectionField { name = "CORRISPONDENT_PROVINCE", Value = x.PROVINCIA },
                    new form_jobForm_sectionField { name = "CORRISPONDENT_EMAIL", Value = x.PEC },
                }
            });

            var anag = _datiProto.AnagraficheProtocollo.Select(x => new form_jobForm_section
            {
                field = new form_jobForm_sectionField[] 
                { 
                    new form_jobForm_sectionField { name = "CORRISPONDENT_NAME", Value = x.GetNomeCompleto() },
                    new form_jobForm_sectionField { name = "CORRISPONDENT_ADDRESS", Value = x.INDIRIZZO },
                    new form_jobForm_sectionField { name = "CORRISPONDENT_CAP", Value = x.CAP },
                    new form_jobForm_sectionField { name = "CORRISPONDENT_TOWN", Value = x.CITTA },
                    new form_jobForm_sectionField { name = "CORRISPONDENT_PROVINCE", Value = x.PROVINCIA },
                    new form_jobForm_sectionField { name = "CORRISPONDENT_EMAIL", Value = x.Pec },
                }
            });

            var formJob = amm.Union(anag);

            var segnaturaSuap = new form_job { Items = formJob.ToArray() };
            var res = _serializer.Serialize(ProtocolloLogsConstants.SegnaturaXmlFileName, segnaturaSuap);

            Flusso = protoFlusso.Flusso;

            return res;
        }

        public string Adatta()
        {
            var protoFlusso = ProtocollazioneFlussoFactory.Create(_datiProto, _vert);
            var docsAllegatiAdapter = new DocumentiAllegatiAdapter(_datiProto.ProtoIn.Allegati, _vert, _idComuneAlias, _software);

            Mittente corrispondente = null;

            if (_datiProto.AmministrazioniEsterne.Count > 0)
                corrispondente = new Mittente { Amministrazione = new Amministrazione { Denominazione = new Denominazione { Text = new string[] { _datiProto.AmministrazioniEsterne[0].AMMINISTRAZIONE } } } };

            if (_datiProto.AnagraficheProtocollo.Count > 0)
                corrispondente = new Mittente { Amministrazione = new Amministrazione { Denominazione = new Denominazione { Text = new string[] { _datiProto.AnagraficheProtocollo[0].GetNomeCompleto() } } } };


            var segnatura = new Segnatura
            {
                Intestazione = new Intestazione
                {
                    Identificatore = new Identificatore
                    {
                        CodiceAmministrazione = new CodiceAmministrazione { Text = new string[] { _vert.CodiceAmministrazione } },
                        CodiceAOO = new CodiceAOO { Text = new string[] { _vert.CodiceAoo } },
                        NumeroRegistrazione = new NumeroRegistrazione { Text = new string[] { _proto.Codice } },
                        DataRegistrazione = new DataRegistrazione { Text = new string[] { DateTime.Now.ToString("yyyy-MM-dd") } }
                    },
                    //Origine = new Origine { IndirizzoTelematico = protoFlusso.IndirizzoTelematicoOrigine, Mittente = protoFlusso.GetMittente() },
                    Origine = new Origine{ Mittente = corrispondente },
                    Oggetto = new Oggetto { Text = new string[] { _datiProto.ProtoIn.Oggetto } },
                    Destinazione = protoFlusso.GetDestinazioni(),
                    Riservato = new Riservato { Text = new string[] { "N" } }
                },
                Descrizione = docsAllegatiAdapter.Adatta()
            };
            
            var res = _serializer.Serialize(ProtocolloLogsConstants.SegnaturaXmlFileName, segnatura);

            Flusso = protoFlusso.Flusso;

            return res;
        }
    }
}
