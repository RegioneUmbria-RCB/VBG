using Init.Sigepro.FrontEnd.AppLogic.GestioneEndoprocedimenti;
using Init.Sigepro.FrontEnd.AppLogic.GestioneServiziFVG.Database;
using Init.Sigepro.FrontEnd.Infrastructure.IOC;
using Init.SIGePro.DatiDinamici;
using Init.SIGePro.DatiDinamici.Interfaces.Istanze;
using Init.SIGePro.DatiDinamici.Utils;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneServiziFVG
{
    public class FvgDyn2DatiRepository : IIstanzeDyn2DatiManager
    {
        FvgDatabase _database;
        IEndoprocedimentiService _endoService;


        public FvgDyn2DatiRepository(FvgDatabase database)
        {
            this._database = database;

            this._endoService = FoKernelContainer.GetService<IEndoprocedimentiService>();
        }



        public void EliminaValoriCampi(ModelloDinamicoIstanza modello, IEnumerable<CampoDinamico> campiDaEliminare)
        {
            
        }

        public SerializableDictionary<int, List<IIstanzeDyn2Dati>> GetValoriCampoDaIdModello(string idComune, int codiceIstanza, int idModello, int indiceCampo)
        {
            var campi = this._database.GetListaValori();
            var rVal = new SerializableDictionary<int, List<IIstanzeDyn2Dati>>();

            foreach (var campo in campi)
            {
                if (!rVal.TryGetValue(campo.FkD2cId.Value, out var listaValori))
                {
                    listaValori = new List<IIstanzeDyn2Dati>();
                    rVal.Add(campo.FkD2cId.Value, listaValori);
                }

                listaValori.Add(campo);
            }

            return rVal;
        }

        public void SalvaValoriCampi(bool salvaStorico, ModelloDinamicoIstanza modello, IEnumerable<CampoDinamico> campiDaSalvare)
        {
            // todo: far persistere i valori dei campi
            foreach(var campo in campiDaSalvare)
            {
                this._database.EliminaValoriCampo(campo.Id);

                for(var i=0; i<campo.ListaValori.Count;i++)
                {
                    var valore = campo.ListaValori[i];

                    if (String.IsNullOrEmpty(valore.Valore))
                    {
                        continue;
                    }

                    this._database.ImpostaValoreCampo(campo.Id, campo.NomeCampo, i, valore.Valore, valore.ValoreDecodificato);
                }                
            }

            this._database.SetSchedaCompilata(modello.IdModello);
            this._database.Salva();
        }
    }
}
