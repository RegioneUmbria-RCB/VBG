using Init.Sigepro.FrontEnd.AppLogic.AreaRiservataService;
using Init.Sigepro.FrontEnd.AppLogic.ServiceCreators;
using Init.Sigepro.FrontEnd.Infrastructure.IOC;
using Init.SIGePro.DatiDinamici.Interfaces.Istanze;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.DatiDinamici.Formule
{
    public class FormuleDatiDinamiciService
    {
        IDatiDinamiciService _service;

        public FormuleDatiDinamiciService()
        {
            _service = FoKernelContainer.GetService<IDatiDinamiciService>();

        }

        public IEnumerable<IIstanzeDyn2Dati> GetDyn2DatiByCodiceIstanza(int idDomanda)
        {
            return _service.GetDyn2DatiByCodiceIstanza(idDomanda);

        }

        public IEnumerable<DecodificaDTO> GetDecodificheAttive( string tabella)
        {
            return _service.GetDecodificheAttive(tabella);
        }

        public void RecuperaDocumentiIstanzaCollegata(int codiceIstanzaOrigine, int idDomandaDestinazione)
        {
            _service.RecuperaDocumentiIstanzaCollegata(codiceIstanzaOrigine, idDomandaDestinazione);
        }
    }
}
