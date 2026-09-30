using Init.SIGePro.Data;
using Init.SIGePro.Manager.DTO.Endoprocedimenti;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace Sigepro.net.WebServices.WsAreaRiservata.WcfServices.Endoprocedimenti
{
    public static class InventarioProcTipiTitoloExtensions
    {
        public static TipiTitoloDto ToTipiTitoloDto(this InventarioProcTipiTitolo el)
        {
            if (el == null)
                return null;

            return new TipiTitoloDto
            {
                Codice = el.Id.Value,
                Descrizione = el.Tipotitolo,
                Flags = new TipiTitoloDtoFlags
                {
                    MostraData = el.FlgMostraData.GetValueOrDefault(0) == 1,
                    MostraNumero = el.FlgMostraNumero.GetValueOrDefault(0) == 1,
                    MostraRilasciatoDa = el.FlgMostraRilasciatoDa.GetValueOrDefault(0) == 1,
                    RichiedeAllegato = el.FlgRichiedeAllegato.GetValueOrDefault(0) == 1,
                    VerificaFirmaAllegato = el.FlgVerificaFirmaAllegato.GetValueOrDefault(0) == 1,
                    AllegatoObbligatorio = el.FlgAllObbligatorio.GetValueOrDefault(0) == 1
                }
            };
        }
    }
}