using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Protocollo.Sigedo.PresaInCarico
{
    public class SmistamentoActionSegnaturaAdapter
    {
        private class Constants
        {
            public const string AzioneCarico = "CARICO";
            public const string AzioneEsegui = "ESEGUI"; 
        }

        string _numeroProtocollo = "";
        string _annoProtocollo = "";
        string _codiceAmministrazione = "";
        string _aoo = "";
        string _registro = "";
        string _codiceUnita = "";
        string _codiceUtente = "";
        string _nomeProtocollo = "";

        public SmistamentoActionSegnaturaAdapter(string numeroProtocollo, string annoProtocollo, string aoo, string codiceAmministrazione, string registro, string codiceUnita, string codiceUtente, string nomeProtocollo)
        {
            _numeroProtocollo = numeroProtocollo;
            _annoProtocollo = annoProtocollo;
            _aoo = aoo;
            _codiceAmministrazione = codiceAmministrazione;
            _registro = registro;
            _codiceUnita = codiceUnita;
            _codiceUtente = codiceUtente;
            _nomeProtocollo = nomeProtocollo;
        }

        private SmistamentoActionSegnatura Adatta(string azione)
        {
            var parametri = new SegnaturaApplicativoProtocolloParametro[] 
            { 
                new SegnaturaApplicativoProtocolloParametro{ nome = "utente", valore = _codiceUtente },
                new SegnaturaApplicativoProtocolloParametro{ nome = "uo", valore = _codiceUnita },
                new SegnaturaApplicativoProtocolloParametro{ nome = "azione", valore = azione }
            };

            var segnatura = new SmistamentoActionSegnatura
            {
                Intestazione = new SegnaturaIntestazione
                {
                    Identificatore = new SegnaturaIntestazioneIdentificatore
                    {
                        AnnoProtocollo = _annoProtocollo,
                        NumeroProtocollo = _numeroProtocollo,
                        CodiceAmministrazione = _codiceAmministrazione,
                        CodiceAOO = _aoo,
                        TipoRegistroProtocollo = _registro
                    }
                },
                ApplicativoProtocollo = new SegnaturaApplicativoProtocollo { nome = _nomeProtocollo, Parametro = parametri }
            };

            return segnatura;
        }


        public SmistamentoActionSegnatura AdattaCarico()
        {
            return Adatta(Constants.AzioneCarico);
        }

        public SmistamentoActionSegnatura AdattaEseguito()
        {
            return Adatta(Constants.AzioneEsegui);
        }
    }
}
