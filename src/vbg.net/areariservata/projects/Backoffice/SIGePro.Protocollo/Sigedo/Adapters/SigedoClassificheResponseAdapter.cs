using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.Data;
using Init.SIGePro.Protocollo.Sigedo.Services;
using Init.SIGePro.Protocollo.Sigedo.Configurations;
using Init.SIGePro.Protocollo.WsDataClass;

namespace Init.SIGePro.Protocollo.Sigedo.Adapters
{
    public class SigedoClassificheResponseAdapter
    {
        SigedoQueryService _wrapper;

        public SigedoClassificheResponseAdapter(SigedoQueryService wrapper)
        {
            _wrapper = wrapper;
        }

        public ListaTipiClassifica Adatta(string area, string modello, string stato, int operatoreRicerche, string operatore)
        {
            var response = _wrapper.LeggiClassifica(area, modello, stato, operatoreRicerche, String.Empty, operatore);

            try
            {
                var listClassifiche = new List<ListaTipiClassificaClassifica>();

                foreach (var m in response.listaDocumenti)
                {
                    var metadati = new SigedoMetadataOutputConfiguration(m.metadati);

                    if (metadati != null)
                    {
                        listClassifiche.Add(new ListaTipiClassificaClassifica
                        {
                            Codice = metadati.GetValue(SigedoMetadataOutputConfiguration.CODICE_CLASSIFICA),
                            Descrizione = String.Concat("[", metadati.GetValue(SigedoMetadataOutputConfiguration.CODICE_CLASSIFICA), "] ", metadati.GetValue(SigedoMetadataOutputConfiguration.DESCRIZIONE_CLASSIFICA))
                        });
                    }
                }

                return new ListaTipiClassifica { Classifica = listClassifiche.OrderBy(x => x.Codice).ToArray() };
            }
            catch (Exception ex)
            {
                throw new Exception("ERRORE GENERATO DURANTE LA MAPPATURA DEI DATI DI CLASSIFICA DAL WEB SERVICE SIGEDO", ex);
            }
        }
    }
}
