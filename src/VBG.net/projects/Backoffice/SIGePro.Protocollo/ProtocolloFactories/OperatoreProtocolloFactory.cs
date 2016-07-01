using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.ProtocolloInterfaces;
using PersonalLib2.Data;
using Init.SIGePro.Protocollo.ProtocolloServices.OperatoreProtocollo;
using Init.SIGePro.Data;

namespace Init.SIGePro.Protocollo.ProtocolloFactories
{
    public class OperatoreProtocolloFactory
    {
        private static class Constants
        {
            public const string CodiceOperatore = "$CODICEOPERATORE$";
            public const string SigeproUserId = "$SIGEPRO_USERID$";
            public const string Responsabile = "$RESPONSABILE$";
            public const string Matricola = "$MATRICOLA$";
            public const string CodiceFiscale = "$CODICEFISCALE$";
        }

        public static IOperatoreProtocollo Create(DataBase db, string segnalibro, int? codiceOperatore, string idComune)
        {
            if (!codiceOperatore.HasValue)
                throw new Exception(String.Format("OPERATORE NON VALORIZZATO, NON E' POSSIBILE RECUPERARE IL VALORE DAL SEGNALIBRO {0}", segnalibro));

            if(segnalibro == Constants.CodiceOperatore)
                return new OperatoreProtocolloCodiceResponsabile(codiceOperatore.Value, idComune, db);

            if (segnalibro == Constants.SigeproUserId)
                return new OperatoreProtocolloUserId(codiceOperatore.Value, idComune, db);

            if (segnalibro == Constants.Responsabile)
                return new OperatoreProtocolloResponsabile(codiceOperatore.Value, idComune, db);

            if (segnalibro == Constants.Matricola)
                return new OperatoreProtocolloMatricola(codiceOperatore.Value, idComune, db);

            if (segnalibro == Constants.CodiceFiscale)
                return new OperatoreProtocolloCodiceFiscale(codiceOperatore.Value, idComune, db);
            
            return new OperatoreProtocolloDefault(segnalibro);
        }
    }
}
