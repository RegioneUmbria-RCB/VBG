using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Protocollo.Prisma.Smistamento
{
    public class SmistamentoInAdapter
    {
        private class Constants
        {
            public const string Utente = "utente";
            public const string Uo = "uo";
            public const string Azione = "azione";
        }

        enum TipoAzioneEnum { ESEGUI, CARICO }
        SmistamentoInfo _smistamentoInfo;

        public SmistamentoInAdapter(SmistamentoInfo info)
        {
            this._smistamentoInfo = info;
        }

        public SmistamentoInXML AdattaEseguito()
        {
            return GetSmistamento(TipoAzioneEnum.ESEGUI);
        }

        public SmistamentoInXML AdattaPresaInCarico()
        {
            return GetSmistamento(TipoAzioneEnum.CARICO);
        }

        private SmistamentoInXML GetSmistamento(TipoAzioneEnum azione)
        {
            return new SmistamentoInXML
            {
                Intestazione = new IntestazioneInXml
                {
                    Identificatore = new IdentificatoreInXml
                    {
                        AnnoProtocollo = _smistamentoInfo.AnnoProtocollo,
                        NumeroProtocollo = _smistamentoInfo.NumeroProtocollo,
                        CodiceAmministrazione = _smistamentoInfo.CodiceAmministrazione,
                        CodiceAOO = _smistamentoInfo.CodiceAoo,
                        TipoRegistroProtocollo = _smistamentoInfo.TipoRegistro
                    }
                },
                ApplicativoProtocollo = new ApplicativoProtocolloInXml
                {
                    Nome = _smistamentoInfo.ApplicativoProtocollo,
                    Parametro = GetParametro(azione).ToArray()
                }
            };
        }

        private IEnumerable<ParametroInXml> GetParametro(TipoAzioneEnum azione)
        {
            yield return new ParametroInXml { Nome = Constants.Utente, Valore = this._smistamentoInfo.Utente };
            yield return new ParametroInXml { Nome = Constants.Uo, Valore = this._smistamentoInfo.Uo };
            yield return new ParametroInXml { Nome = Constants.Azione, Valore = azione.ToString() };
        }
    }
}
