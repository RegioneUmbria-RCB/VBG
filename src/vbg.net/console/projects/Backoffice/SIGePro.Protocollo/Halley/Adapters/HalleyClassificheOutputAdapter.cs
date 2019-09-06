using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.ProtocolloHalleyDizionarioServiceProxy;
using Init.SIGePro.Protocollo.Data;
using Init.SIGePro.Protocollo.WsDataClass;

namespace Init.SIGePro.Protocollo.Halley.Adapters
{
    public class HalleyClassificheOutputAdapter
    {
        public readonly ListaTipiClassifica ListaClassifiche;
        CodiciTitolarioCodiceTitolario[] _response;

        public HalleyClassificheOutputAdapter(CodiciTitolarioCodiceTitolario[] response)
        {
            _response = response;
            ListaClassifiche = GetListaClassifiche();
        }

        private string GetDescrizioneTitolario(CodiciTitolarioCodiceTitolario titolario)
        {
            string codice = "";
            string descrizione = "";

            if (titolario.Categoria != null)
            {
                codice = titolario.Categoria.id;
                descrizione = titolario.Categoria.Value;
            }

            if (titolario.Classe != null)
            {
                codice += String.Concat(".", titolario.Classe.id);
                descrizione = titolario.Classe.Value;
            }

            if (titolario.Fascicolo != null)
            {
                codice += String.Concat(".", titolario.Fascicolo.id);
                descrizione = titolario.Fascicolo.Value;
            }

            return String.Format("[{0}] {1}", codice, descrizione);
        }

        private ListaTipiClassifica GetListaClassifiche()
        {
            var classificheList = new List<ListaTipiClassificaClassifica>();
            _response.ToList().ForEach(x => classificheList.Add(new ListaTipiClassificaClassifica
            {
                Codice = x.id,
                Descrizione = GetDescrizioneTitolario(x)
            }));

            

            var retVal = new ListaTipiClassifica { Classifica = classificheList.ToArray() };

            return retVal;
        }
    }
}
