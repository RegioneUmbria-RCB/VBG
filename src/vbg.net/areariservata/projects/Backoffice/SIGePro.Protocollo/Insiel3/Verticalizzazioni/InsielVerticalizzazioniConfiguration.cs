using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using PersonalLib2.Data;
using Init.SIGePro.Protocollo.Logs;
using Init.SIGePro.Verticalizzazioni;
using Init.SIGePro.Protocollo.Insiel3.Protocollazione.MittentiDestinatari;

namespace Init.SIGePro.Protocollo.Insiel3.Verticalizzazioni
{
    public class InsielVerticalizzazioniConfiguration
    {
        ProtocolloLogs _logs;

        public string CodiceRegistro { get; private set; }
        public string CodiceUfficioOperante { get; private set; }
        public string CodiceUtente { get; private set; }
        public bool DisabilitaAnnullaProtocollo { get; private set; }
        public bool EscludiClassifica { get; private set; }
        public string Password { get; private set; }
        public bool TipiDocumentoWs { get; private set; }
        public string Url { get; private set; }
        public string UrlUploadFile { get; private set; }
        public bool IsMonfalcone { get; private set; }
        public bool UsaLivelliClassifica { get; private set; }
        public string Iteratti { get; private set; }
        public bool UsaWsClassifiche { get; private set; }
        public bool InviaPec { get; private set; }

        public TipoGestioneAnagraficaEnum.TipoGestione TipoGestionePec { get; private set; }
        public TipoGestioneAnagraficaEnum.TipoAggiornamento TipoAggiornamentoAnagrafica { get; private set; }

        public InsielVerticalizzazioniConfiguration(ProtocolloLogs logs, VerticalizzazioneProtocolloInsiel vert)
        {
            if (!vert.Attiva)
                throw new Exception("La verticalizzazione PROTOCOLLO_INSIEL non è attiva");

            _logs = logs;
            EstraiParametri(vert);
        }

        private void VerificaIntegritaParametri(VerticalizzazioneProtocolloInsiel paramVert)
        {
            if (String.IsNullOrEmpty(paramVert.Codiceutente))
                throw new Exception("IL PARAMETRO CODICEUTENTE NON E' STATO VALORIZZATO");

            //if (String.IsNullOrEmpty(paramVert.Password))
            //    throw new Exception("IL PARAMETRO PASSWORD NON E' STATO VALORIZZATO");

            if (String.IsNullOrEmpty(paramVert.Url))
                throw new Exception("IL PARAMETRO URL RIGUARDANTE L'ENDPOINT DEL WEB SERVICE NON E' STATO VALORIZZATO");

            if (String.IsNullOrEmpty(paramVert.UrlUploadfile))
                throw new Exception("IL PARAMETRO URL UPLOAD FILE RIGUARDANTE L'ENDPOINT DEL WEB SERVICE DI UPLOAD FILE NON E' STATO VALORIZZATO");
        }

        public void EstraiParametri(VerticalizzazioneProtocolloInsiel vert)
        {
            try
            {
                _logs.Debug("Inizio recupero valori da verticalizzazione");

                VerificaIntegritaParametri(vert);

                this.CodiceRegistro = vert.Codiceregistro;
                this.CodiceUfficioOperante = vert.CodiceUfficioOperante;
                this.CodiceUtente = vert.Codiceutente;
                this.DisabilitaAnnullaProtocollo = vert.DisabilitaAnnullaProtocollo == "1";
                this.EscludiClassifica = vert.EscludiClassifica == "1";
                this.Password = vert.Password;
                this.TipiDocumentoWs = vert.TipiDocumentoWs == "1";
                this.Url = vert.Url;
                this.UrlUploadFile = vert.UrlUploadfile;
                this.IsMonfalcone = vert.AttivaMonf == "1";
                this.UsaLivelliClassifica = vert.UsaLivelliClassifica == "1";
                this.Iteratti = vert.TipoUfficioIteratti;
                this.UsaWsClassifiche = vert.UsaWsClassifiche == "1";
                this.InviaPec = vert.InviaPec == "1";

                this.TipoGestionePec = TipoGestioneAnagraficaEnum.TipoGestione.RICERCA_CODICE_FISCALE;
                if (!String.IsNullOrEmpty(vert.TipoGestionePec))
                {
                    TipoGestioneAnagraficaEnum.TipoGestione tmpTipoGestionePec;
                    var isParsable = Enum.TryParse(vert.TipoGestionePec, out tmpTipoGestionePec);

                    if (!isParsable)
                    {
                        throw new Exception($"IL VALORE {vert.TipoGestionePec} RELATIVO AL PARAMETRO TIPO_GESTIONE_PEC NON E' CORRETTO");
                    }

                    this.TipoGestionePec = tmpTipoGestionePec;
                }

                this.TipoAggiornamentoAnagrafica = TipoGestioneAnagraficaEnum.TipoAggiornamento.NO_AGGIORNAMENTO;
                if (!String.IsNullOrEmpty(vert.TipoAggiornamentoAnagrafica))
                {
                    TipoGestioneAnagraficaEnum.TipoAggiornamento tmpTipoAggiornamento;
                    var isParsable = Enum.TryParse(vert.TipoAggiornamentoAnagrafica, out tmpTipoAggiornamento);

                    if (!isParsable)
                    {
                        throw new Exception($"IL VALORE {vert.TipoAggiornamentoAnagrafica} RELATIVO AL PARAMETRO TIPO_AGGIORNAMENTO_ANAG NON E' CORRETTO");
                    }

                    this.TipoAggiornamentoAnagrafica = tmpTipoAggiornamento;
                }
            }
            catch (Exception ex)
            {
                throw new Exception($"ERRORE GENERATO DURANTE IL RECUPERO DEI VALORI DALLA VERTICALIZZAZIONE PROTOCOLLO_INSIEL, {ex.Message}", ex);
            }
        }
    }
}
