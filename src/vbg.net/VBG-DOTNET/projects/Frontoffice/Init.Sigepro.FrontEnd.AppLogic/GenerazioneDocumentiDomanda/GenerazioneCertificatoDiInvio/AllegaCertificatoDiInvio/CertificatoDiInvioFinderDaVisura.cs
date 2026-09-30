using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;
using Init.Sigepro.FrontEnd.AppLogic.GenerazioneDocumentiDomanda.GenerazioneCertificatoDiInvio.Configurazione;
using Init.Sigepro.FrontEnd.AppLogic.GestioneVisuraIstanza;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.GenerazioneDocumentiDomanda.GenerazioneCertificatoDiInvio.AllegaCertificatoDiInvio
{
    public class CertificatoDiInvioFinderDaVisura : ICertificatoDiInvioFinder
    {
        private readonly IVisuraService _visuraService;
        private readonly IConfigurazione<ParametriGenerazioneCertificatoInvio> _configurazione;

        public CertificatoDiInvioFinderDaVisura(IVisuraService visuraService, IConfigurazione<ParametriGenerazioneCertificatoInvio> configurazione)
        {
            _visuraService = visuraService;
            _configurazione = configurazione;
        }

        public int? GetCodiceOggettoCertificatoDiInvio(int idDomandaBackoffice)
        {
            var istanza = _visuraService.GetById(idDomandaBackoffice, new VisuraIstanzaFlags { LeggiDatiConfigurazione = false });

            if (istanza == null)
                return (int?)null;

            foreach (var documento in istanza.DocumentiIstanza)
            {
                if (documento.DOCUMENTO == this._configurazione.Parametri.NomeFile && !String.IsNullOrEmpty(documento.CODICEOGGETTO))
                    return Convert.ToInt32(documento.CODICEOGGETTO);
            }

            return null;
        }

        public bool PraticaHaCertificatoDiInvio(int idDomandaBackoffice)
        {
            return GetCodiceOggettoCertificatoDiInvio(idDomandaBackoffice).HasValue;
        }
    }
}
