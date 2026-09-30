using Init.Sigepro.FrontEnd.AppLogic.Common;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti.Metadati
{
    public class MetadatiOggettoUtenteProvider : IMetadatiOggettoProvider
    {
        private static class Constants 
        {
            public const string Nominativo = "MITTENTE_NOMINATIVO";
            public const string Nome = "MITTENTE_NOME";
            public const string CodiceFiscale = "MITTENTE_CODICEFISCALE";
            public const string Partitaiva = "MITTENTE_PARTITAIVA";
        }

        private readonly IAuthenticationDataResolver _resolver;

        public MetadatiOggettoUtenteProvider(IAuthenticationDataResolver resolver)
        {
            this._resolver = resolver;
        }

        public IEnumerable<Metadato> Metadati => new[]
            {
                new Metadato(Constants.Nominativo, this._resolver.DatiAutenticazione.DatiUtente.Nominativo),
                new Metadato(Constants.Nome, this._resolver.DatiAutenticazione.DatiUtente.Nome),
                new Metadato(Constants.CodiceFiscale, this._resolver.DatiAutenticazione.DatiUtente.Codicefiscale),
                new Metadato(Constants.Partitaiva, this._resolver.DatiAutenticazione.DatiUtente.Partitaiva),

            }.Where(x => !String.IsNullOrEmpty(x.Valore));
        
    }
}
