using Init.SIGePro.Data;
using Init.SIGePro.Manager.Verticalizzazioni;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Protocollo.ProtoInf
{
    public class VerticalizzazioniServiceWrapper
    {
        public string Url { get; private set; }
        public string Username { get; private set; }
        public string Password { get; private set; }
        public string CodiceEnte { get; private set; }
        public string CodiceAmministrazione { get; private set; }
        public string CodiceAoo { get; private set; }
        public string PathBase { get; private set; }
        public string BasePathProto { get; private set; }
        public string BasePathLocal { get; private set; }
        public string Protocollatore { get; private set; }
        public string Proprietario { get; private set; }
        public string CodiceAmministrazioneDestinatario { get; private set; }
        public bool IgnoraClassifica { get; private set; }
        public string NetworkUsername { get; private set; }
        public string NetworkPassword { get; private set; }
        public string NetworkDomain { get; private set; }
        public string[] EndoProcedimentiSmistamento { get; private set; }

        VerticalizzazioneProtocolloProtInf _vert;

        public VerticalizzazioniServiceWrapper(VerticalizzazioneProtocolloProtInf vert)
        {
            if (!vert.Attiva)
                throw new Exception("LA VERTICALIZZAZIONE PROTOCOLLO_PROTOINF NON E' ATTIVA");

            _vert = vert;

            EstraiParametri();
        }

        private void VerificaIntegritaParametri()
        {
            if (String.IsNullOrEmpty(_vert.Username))
                throw new Exception("IL PARAMETRO USERNAME DELLA REGOLA PROTOCOLLO_PROTOINF NON E' STATO VALORIZZATO");

            if (String.IsNullOrEmpty(_vert.Password))
                throw new Exception("IL PARAMETRO PASSWORD DELLA REGOLA PROTOCOLLO_PROTOINF NON E' STATO VALORIZZATO");

            if (String.IsNullOrEmpty(_vert.Url))
                throw new Exception("IL PARAMETRO URL DELLA REGOLA PROTOCOLLO_PROTOINF NON E' STATO VALORIZZATO");

            if (String.IsNullOrEmpty(_vert.CodiceEnte))
                throw new Exception("IL PARAMETRO CODICE_ISTAT DELLA REGOLA PROTOCOLLO_PROTOINF NON E' STATO VALORIZZATO");

            if (String.IsNullOrEmpty(_vert.CodiceAmministrazione))
                throw new Exception("IL PARAMETRO CODICEAMMINISTRAZIONE DELLA REGOLA PROTOCOLLO_PROTOINF NON E' STATO VALORIZZATO");

            if (String.IsNullOrEmpty(_vert.CodiceAoo))
                throw new Exception("IL PARAMETRO CODICEAOO DELLA REGOLA PROTOCOLLO_PROTOINF NON E' STATO VALORIZZATO");

            if (String.IsNullOrEmpty(_vert.BasePath_Proto))
                throw new Exception("IL PARAMETRO BASEPATH_FILES_PROTO DELLA REGOLA PROTOCOLLO_PROTOINF NON E' STATO VALORIZZATO");

            if (String.IsNullOrEmpty(_vert.BasePath_Local))
                throw new Exception("IL PARAMETRO BASEPATH_FILES_LOCAL DELLA REGOLA PROTOCOLLO_PROTOINF NON E' STATO VALORIZZATO");

            if (String.IsNullOrEmpty(_vert.Proprietario))
                throw new Exception("IL PARAMETRO PROPRIETARIO DELLA REGOLA PROTOCOLLO_PROTOINF NON E' STATO VALORIZZATO");

            if (String.IsNullOrEmpty(_vert.Protocollatore))
                throw new Exception("IL PARAMETRO PROTOCOLLATORE DELLA REGOLA PROTOCOLLO_PROTOINF NON E' STATO VALORIZZATO");

            if (String.IsNullOrEmpty(_vert.CodiceAmministrazioneDestinatario))
                throw new Exception("IL PARAMETRO CODAMM_DESTINATARIO DELLA REGOLA PROTOCOLLO_PROTOINF NON E' STATO VALORIZZATO");

            if (String.IsNullOrEmpty(_vert.CodiciEndoProcedimentiSmistamento))
                throw new Exception("IL PARAMETRO CODICI_ENDPROC_SMISTAMENTI DELLA REGOLA PROTOCOLLO_PROTOINF NON E' STATO VALORIZZATO");

        }

        private void EstraiParametri()
        {
            VerificaIntegritaParametri();

            this.Username = _vert.Username;
            this.Password = _vert.Password;
            this.CodiceEnte = _vert.CodiceEnte;
            this.Url = _vert.Url;
            this.CodiceAmministrazione = _vert.CodiceAmministrazione;
            this.CodiceAoo = _vert.CodiceAoo;
            this.PathBase = _vert.PathBase;
            this.BasePathProto = _vert.BasePath_Proto;
            this.BasePathLocal = _vert.BasePath_Local;
            this.Protocollatore = _vert.Protocollatore;
            this.Proprietario = _vert.Proprietario;
            this.CodiceAmministrazioneDestinatario = _vert.CodiceAmministrazioneDestinatario;
            this.IgnoraClassifica = _vert.IgnoraClassifica == "1";
            this.NetworkUsername = _vert.NetworkUsername;
            this.NetworkPassword = _vert.NetworkPassword;
            this.NetworkDomain = _vert.NetworkDomain;
            this.EndoProcedimentiSmistamento = _vert.CodiciEndoProcedimentiSmistamento.Split(',');
        }
    }
}
