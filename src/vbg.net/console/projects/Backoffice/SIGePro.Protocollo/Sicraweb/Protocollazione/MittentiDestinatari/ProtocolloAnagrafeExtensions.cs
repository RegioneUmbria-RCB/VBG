using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Data;
using Init.SIGePro.Protocollo.Sicraweb.Protocollazione.Segnatura;

namespace Init.SIGePro.Protocollo.Sicraweb.Protocollazione.MittentiDestinatari
{
    public static class ProtocolloAnagrafeExtensions
    {
        public static MittenteMulti ToMittentiMultipli(this ProtocolloAnagrafe anagrafe)
        {
            var validator = new ProtocolloAnagrafeValidator();
            validator.Validate(anagrafe);

            var adapter = new ProtocolloAnagrafeAdapter(anagrafe);
            return adapter.AdattaMittente();
        }

        public static DestinataroMulti ToDestinatariMultipli(this ProtocolloAnagrafe anagrafe)
        {
            var validator = new ProtocolloAnagrafeValidator();
            validator.Validate(anagrafe);

            var adapter = new ProtocolloAnagrafeAdapter(anagrafe);
            return adapter.AdattaDestinatario();
        }

        public static string GetCognome(this ProtocolloAnagrafe anagrafe)
        {
            if (anagrafe.TIPOANAGRAFE == "G")
                return "";

            if (anagrafe.NOMINATIVO.Length > 40)
                return "";

            return anagrafe.NOMINATIVO;
        }

        public static string GetDenominazione(this ProtocolloAnagrafe anagrafe)
        {
            if (anagrafe.TIPOANAGRAFE == "G")
                return anagrafe.NOMINATIVO;

            if (anagrafe.NOMINATIVO.Length > 40)
                return anagrafe.NOMINATIVO;

            return "";
        }

        public static string GetId(this ProtocolloAnagrafe anagrafe)
        {
            return !String.IsNullOrEmpty(anagrafe.CODICEFISCALE) ? anagrafe.CODICEFISCALE : anagrafe.PARTITAIVA;
        }

        public static Comuni GetComune(this ProtocolloAnagrafe anagrafe)
        {
            var comune = anagrafe.ComuneResidenza;

            if (comune == null)
                comune = new Comuni { COMUNE = anagrafe.CITTA, CAP = anagrafe.CAP, SIGLAPROVINCIA = "" };
            else
                comune.CAP = anagrafe.CAP;

            return comune;

        }
    }
}
