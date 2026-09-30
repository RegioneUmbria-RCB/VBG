using PersonalLib2.Data;
using VBG.Backend.Protocollo.AppLogic.Shared.Interfaces;
using VBG.Backend.Protocollo.AppLogic.Shared.Services.OperatoreProtocollo;

namespace VBG.Backend.Protocollo.AppLogic.Shared.Factories
{
    public static class OperatoreProtocolloFactory
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

            if (segnalibro == Constants.CodiceOperatore)
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
