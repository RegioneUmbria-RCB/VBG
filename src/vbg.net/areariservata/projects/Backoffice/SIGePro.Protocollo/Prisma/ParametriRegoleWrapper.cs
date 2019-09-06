using Init.SIGePro.Manager.Verticalizzazioni;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Protocollo.Prisma
{
    public class ParametriRegoleWrapper
    {
        public string Username { get; private set; }
        public string Password { get; private set; }
        public string CodiceEnte { get; private set; }
        public string CodiceAoo { get; private set; }
        public string UrlProtoDocArea { get; private set; }
        public string UrlAllegati { get; private set; }
        public string UrlExtended { get; private set; }
        public string UrlPec { get; set; }
        public string TipoDocumentoPrincipale { get; private set; }
        public string TipoDocumentoAllegato { get; private set; }
        public string ApplicativoProtocollo { get; private set; }
        public string Uo { get; private set; }
        public string TipoRegistro { get; private set; }
        public string UoSmistamentoMovimento { get; private set; }
        public string DenominazioneEnte { get; private set; }
        public bool DisabilitaInvioPec { get; private set; }
        public bool DisabilitaEseguito { get; private set; }

        VerticalizzazioneProtocolloPrisma _vert;

        public ParametriRegoleWrapper(VerticalizzazioneProtocolloPrisma vert)
        {
            if (!vert.Attiva)
                throw new Exception("LA VERTICALIZZAZIONE PROTOCOLLO_PRISMA NON E' ATTIVA");

            this._vert = vert;

            EstraiParametri();
        }

        private void EstraiParametri()
        {
            this.Username = this._vert.Username;
            this.UrlProtoDocArea = this._vert.UrlProtoDocArea;
            this.Password = this._vert.Password;
            this.CodiceEnte = this._vert.CodiceEnte;
            this.CodiceAoo = this._vert.CodiceAoo;
            this.TipoDocumentoPrincipale = this._vert.TipoDocumentoPrincipale;
            this.TipoDocumentoAllegato = this._vert.TipoDocumentoAllegato;
            this.ApplicativoProtocollo = this._vert.ApplicativoProtocollo;
            this.Uo = this._vert.Uo;
            this.UrlPec = this._vert.UrlPec;
            this.TipoRegistro = this._vert.TipoRegistro;
            this.UrlAllegati = this._vert.UrlAllegati;
            this.UrlExtended = this._vert.UrlExtended;
            this.UoSmistamentoMovimento = this._vert.UoSmistamentoMovimento;
            this.DenominazioneEnte = this._vert.DenominazioneEnte;
            this.DisabilitaInvioPec = this._vert.DisabilitaInvioPec == "1";
            this.DisabilitaEseguito = this._vert.DisabilitaEseguito == "1";
        }
    }
}
