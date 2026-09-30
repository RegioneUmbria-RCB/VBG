using System.Collections.Generic;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti.UrlDownloadOggetti
{
    public class CodiceOggettoDownload
    {
        public readonly int Id;
        public readonly string Alias;

        public CodiceOggettoDownload(string alias, int id)
        {
            Alias = alias;
            Id = id;
        }

        public override bool Equals(object obj)
        {
            return obj is CodiceOggettoDownload download &&
                   Id == download.Id &&
                   Alias == download.Alias;
        }

        public override int GetHashCode()
        {
            int hashCode = -1600875529;
            hashCode = hashCode * -1521134295 + Id.GetHashCode();
            hashCode = hashCode * -1521134295 + EqualityComparer<string>.Default.GetHashCode(Alias);
            return hashCode;
        }
    }
}
