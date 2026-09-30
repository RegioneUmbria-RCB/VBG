using System;
using System.Collections.Generic;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.VerificaSoggettiFirmatari.Errori
{
    public static class MessaggioSoggettoFirmatarioNonTrovato
    {
        public static string BuildMessage(string nome, string nominativo, string codiceFiscale, string tipoSoggetto)
        {
            return $"Il documento deve essere firmato da <span class=\"fw-bold\"> {nome} {nominativo} [{codiceFiscale}] </span> in qualità di <span class=\"fw-bold\"> {tipoSoggetto} </span>";
        }
    }
}
