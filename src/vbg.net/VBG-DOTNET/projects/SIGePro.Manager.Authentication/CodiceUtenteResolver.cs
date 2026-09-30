using PersonalLib2.Data;
using System;
using System.Linq;

namespace Init.SIGePro.Manager.Authentication
{
    internal class CodiceUtenteResolver
    {
        private readonly DataBase _dataBase;
        private readonly string _idComune;

        private enum TipoAnagrafica
        {
            PersonaFisica,
            PersonaGiuridica
        }

        public CodiceUtenteResolver(DataBase dataBase, string idComune)
        {
            this._dataBase = dataBase;
            this._idComune = idComune;
        }

        public int? GetCodiceResponsabileByCodiceUtente(string codUtente)
        {
            FormattableString sql = $"select CODICERESPONSABILE from RESPONSABILI where IDCOMUNE = {this._idComune} and USERID = {codUtente}";

            var result = this._dataBase.ExecuteReader(sql, reader => reader.GetInt("CODICERESPONSABILE"));

            if (!result.Any())
            {
                return null;
            }

            if (result.Count() > 1)
                throw new ArgumentException("Trovati più responsabili con user id " + codUtente);

            return result.First();
        }

        public int? GetCodiceAnagrafePersonaGiuridica(string codFiscalePartitaIva)
        {
            return this.GetCodiceAnagrafePersona(codFiscalePartitaIva, TipoAnagrafica.PersonaGiuridica);
        }

        public int? GetCodiceAnagrafePersonaFisica(string codFiscalePartitaIva)
        {
            return this.GetCodiceAnagrafePersona(codFiscalePartitaIva, TipoAnagrafica.PersonaFisica);
        }

        private int? GetCodiceAnagrafePersona(string codiceFiscalePartitaIva, TipoAnagrafica tipoAnagrafica)
        {
            FormattableString sql = $@"select 
                    CODICEANAGRAFE 
                from 
                    ANAGRAFE 
                where 
                    IDCOMUNE = {this._idComune} and 
                    CODICEFISCALE = {codiceFiscalePartitaIva} and
                    TIPOANAGRAFE = {(tipoAnagrafica == TipoAnagrafica.PersonaFisica ? "F" : "G")} and
                    FLAG_DISABILITATO = 0";

            var result = this._dataBase.ExecuteReader(sql, reader => reader.GetInt("CODICEANAGRAFE"));

            if (result.Count() == 1)
            {
                return result.First();
            }

            if (result.Count() > 1)
                throw new ArgumentException("Trovate più anagrafiche con codice fiscale " + codiceFiscalePartitaIva);

            // Cerco per partita IVA
            sql = $@"select 
                    CODICEANAGRAFE 
                from 
                    ANAGRAFE 
                where 
                    IDCOMUNE = {this._idComune} and 
                    PARTITAIVA = {codiceFiscalePartitaIva} and
                    TIPOANAGRAFE = {(tipoAnagrafica == TipoAnagrafica.PersonaFisica ? "F" : "G")} and
                    FLAG_DISABILITATO = 0";

            result = this._dataBase.ExecuteReader(sql, reader => reader.GetInt("CODICEANAGRAFE"));

            if (!result.Any())
            {
                return null;
            }

            if (result.Count() > 1)
                throw new ArgumentException("Trovate più anagrafiche con partita iva " + codiceFiscalePartitaIva);

            return result.First().Value;
        }
    }
}
