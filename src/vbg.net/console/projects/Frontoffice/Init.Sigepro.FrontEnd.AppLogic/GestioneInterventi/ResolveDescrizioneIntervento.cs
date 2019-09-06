using Init.Sigepro.FrontEnd.AppLogic.AreaRiservataService;
using Init.Sigepro.FrontEnd.AppLogic.Common;
using Init.Sigepro.FrontEnd.AppLogic.Repositories.Interfaces;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneInterventi
{
    public class ResolveDescrizioneIntervento : IResolveDescrizioneIntervento
    {
        IInterventiRepository _interventiRepository;
        IAliasResolver _aliasResolver;

        public ResolveDescrizioneIntervento(IInterventiRepository interventiRepository, IAliasResolver aliasResolver)
        {
            this._interventiRepository = interventiRepository;
            this._aliasResolver = aliasResolver;
        }


        public string GetDescrizioneDaCodiceintervento(int codiceIntervento)
        {
            ClassTreeOfInterventoDto strutturaAlbero = _interventiRepository.GetAlberaturaNodoDaId(this._aliasResolver.AliasComune, codiceIntervento);

            var nomeIntervento = new StringBuilder();

            nomeIntervento.Append(strutturaAlbero.Elemento.Descrizione);

            while (strutturaAlbero.NodiFiglio != null && strutturaAlbero.NodiFiglio.Length > 0)
            {
                strutturaAlbero = strutturaAlbero.NodiFiglio[0];

                nomeIntervento.Append(Environment.NewLine);
                nomeIntervento.Append(strutturaAlbero.Elemento.Descrizione);
            }

            return nomeIntervento.ToString();
        }
    }
}
