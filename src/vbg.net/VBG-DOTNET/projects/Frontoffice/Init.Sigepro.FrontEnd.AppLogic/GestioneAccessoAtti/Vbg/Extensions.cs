using Init.Sigepro.FrontEnd.AppLogic.WebServiceReferences.IstanzeService;
using System;
using System.Collections.Generic;
using System.Linq;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneAccessoAtti.Vbg
{
    public static class AccessoAttiExtensions
    {
        public static IEnumerable<int> GetCodiciOggettoDocumentiPerAccessoAtti(this Istanze _dataSource, int livelloAccessoDocumenti)
        {
            var documentiIstanza = _dataSource.DocumentiIstanza
                                                .Where(x => x.IsAllegatoValido(livelloAccessoDocumenti))
                                                .Select(x => x.CodiceOggettoAsInt());

            var documentiEndo = GetAllegatiEndoprocedimenti(_dataSource, livelloAccessoDocumenti);

            var documentiMovimento = GetAllegatiMovimenti(_dataSource, livelloAccessoDocumenti);

            return documentiIstanza.Union(documentiEndo).Union(documentiMovimento).ToArray();
        }

        private static IEnumerable<int> GetAllegatiMovimenti(Istanze dataSource, int livelloAccessoDocumenti)
        {
            return dataSource.Movimenti
               .GetMovimentiPubblicati()
               .SelectMany(mov => mov.GetAllegatiPubbliciConCodiceOggetto().Where(all => all.IsAllegatoValido(livelloAccessoDocumenti)))
               .Select(x => x.CodiceOggettoAsInt());
        }

        private static IEnumerable<int> GetAllegatiEndoprocedimenti(Istanze _dataSource, int livelloAccessoDocumenti)
        {
            return _dataSource.EndoProcedimenti.SelectMany(x => x.IstanzeAllegati?
                                                                   .Where(all => all.IsAllegatoValido(livelloAccessoDocumenti))
                                                                   .Select(all => all.CodiceOggettoAsInt()) ?? Enumerable.Empty<int>());
            /*
            return this._dataSource.EndoProcedimenti.Select(x => new VisuraEndoListItem
            {
                Id = Convert.ToInt32(x.CODICEINVENTARIO),
                CodiceIstanza = Convert.ToInt32(this._dataSource.CODICEISTANZA),
                Endoprocedimento = x.Endoprocedimento.Procedimento,
                Allegati = x.IstanzeAllegati?.Where(y => y.ContieneOggetto && this._vbgAccessoAttiService.IsAllegatoValido(y, this.LivelloAccessoDocumenti))
                    ?? Enumerable.Empty<IstanzeAllegati>()
            });
            */
        }
        public static int CodiceOggettoAsInt(this IDocumentoIstanzaOggettoDiVerifica doc)
        {
            return Convert.ToInt32(doc.CodiceOggetto);
        }

        public static bool IsAllegatoValido(this IDocumentoIstanzaOggettoDiVerifica doc, int livelloAccessoDocumenti)
        {
            if (doc is null || !doc.ContieneOggetto || doc.ContieneDatiSensibili)
            {
                return false;
            }

            //if (livelloAccessoDocumenti is null)
            //    return doc.EsitoVerifica == StatoVerificaDocumentoEnum.Valido ||
            //            doc.EsitoVerifica == StatoVerificaDocumentoEnum.NonValido ||
            //            doc.EsitoVerifica == StatoVerificaDocumentoEnum.DaVerificare;

            switch (livelloAccessoDocumenti)
            {
                case 0:
                    return doc.EsitoVerifica == StatoVerificaDocumentoEnum.Valido;
                case 2:
                    return doc.EsitoVerifica == StatoVerificaDocumentoEnum.Valido ||
                            doc.EsitoVerifica == StatoVerificaDocumentoEnum.DaVerificare;
                default:
                    return true;
            }
        }
    }
}
