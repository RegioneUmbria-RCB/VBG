using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.GenerazioneDocumentiDomanda.GenerazioneCertificatoDiInvio.AllegaCertificatoDiInvio
{
    public class CompositeCertificatoInvioFinder : ICertificatoDiInvioFinder
    {
        private readonly List<ICertificatoDiInvioFinder> _finders = new List<ICertificatoDiInvioFinder>();

        public CompositeCertificatoInvioFinder(CertificatoDiInvioFinderDaMetadati finderDaMetadati, CertificatoDiInvioFinderDaVisura finderDaVisura)
        {
            this._finders.Add(finderDaMetadati);
            this._finders.Add(finderDaVisura);
        }


        public int? GetCodiceOggettoCertificatoDiInvio(int idDomandaBackoffice)
        {
            foreach(var finder in this._finders)
            {
                var codiceOggetto = finder.GetCodiceOggettoCertificatoDiInvio(idDomandaBackoffice);

                if (codiceOggetto.HasValue)
                {
                    return codiceOggetto;
                }
            }

            return null;
        }

        public bool PraticaHaCertificatoDiInvio(int idDomandaBackoffice)
        {
            return GetCodiceOggettoCertificatoDiInvio(idDomandaBackoffice).HasValue;
        }
    }
}
