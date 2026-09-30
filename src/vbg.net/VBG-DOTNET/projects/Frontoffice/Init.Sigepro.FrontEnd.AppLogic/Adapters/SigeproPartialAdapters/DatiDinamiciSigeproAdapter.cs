using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda;
using Init.Sigepro.FrontEnd.AppLogic.WebServiceReferences.IstanzeService;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.Adapters.SigeproPartialAdapters
{
    public class DatiDinamiciSigeproAdapter : IIstanzaSigeproPartialAdapter
    {
        public void Adatta(IDomandaOnlineReadInterface src, Istanze dst, IstanzaSigeproAdapterFlags flags)
        {
            dst.IstanzeDyn2ModelliT = src.DatiDinamici
                                        .Modelli
                                        .Where(x => x.Compilato)
                                        .Select(x => new IstanzeDyn2ModelliT
                                        {
                                            FkD2mtId = x.IdModello
                                        })
                                        .ToArray();

            dst.IstanzeDyn2Dati = src.DatiDinamici
                                    .DatiDinamici
                                    .Select(foDato => new IstanzeDyn2Dati
                                    {
                                        FkD2cId = foDato.IdCampo,
                                        Valore = foDato.Valore,
                                        Valoredecodificato = foDato.ValoreDecodificato,
                                        IndiceMolteplicita = foDato.IndiceMolteplicita,
                                        Indice = 0,
                                        CampoDinamico = new Dyn2Campi
                                        {
                                            Nomecampo = foDato.NomeCampo
                                        }
                                    })
                                    .ToArray();
        }
    }
}
