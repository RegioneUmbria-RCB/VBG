using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.Sicraweb.Protocollazione.Segnatura;
using Init.SIGePro.Data;

namespace Init.SIGePro.Protocollo.Sicraweb.Protocollazione.MittentiDestinatari
{
    public static class AmministrazioniExtensions
    {
        public static MittenteMulti ToMittentiMultipli(this Amministrazioni amm)
        {
            var validator = new AmministrazioneEsternaValidator();
            validator.Validate(amm);

            var adapter = new AmministrazioneEsternaAdapter(amm);
            return adapter.AdattaMittente();
        }

        public static DestinataroMulti ToDestinatariMultipli(this Amministrazioni amm)
        {
            var validator = new AmministrazioneEsternaValidator();
            validator.Validate(amm);

            var adapter = new AmministrazioneEsternaAdapter(amm);
            return adapter.AdattaDestinatario();
        }
    }
}
