using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Protocollo.Prisma.Smistamento
{
    public class SmistamentoInfo
    {
        public string AnnoProtocollo { get; private set; }
        public string NumeroProtocollo { get; private set; }
        public string CodiceAmministrazione { get; private set; }
        public string CodiceAoo { get; private set; }
        public string TipoRegistro { get; private set; }
        public string ApplicativoProtocollo { get; private set; }
        public string Utente { get; private set; }
        public string Uo { get; private set; }

        public SmistamentoInfo(ParametriRegoleWrapper vert, string numeroProtocollo, string annoProtocollo, string ruolo)
        {
            this.AnnoProtocollo = annoProtocollo;
            this.NumeroProtocollo = numeroProtocollo;
            this.CodiceAmministrazione = vert.CodiceEnte;
            this.CodiceAoo = vert.CodiceAoo;
            this.TipoRegistro = vert.TipoRegistro;
            this.ApplicativoProtocollo = vert.ApplicativoProtocollo;
            this.Utente = vert.Username;
            this.Uo = String.IsNullOrEmpty(ruolo) ? vert.UoSmistamentoMovimento : ruolo;
        }
    }
}
