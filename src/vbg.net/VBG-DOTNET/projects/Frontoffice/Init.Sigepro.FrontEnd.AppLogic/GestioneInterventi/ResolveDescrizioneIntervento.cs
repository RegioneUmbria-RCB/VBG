using Init.Sigepro.FrontEnd.AppLogic.Common;
using Init.SIGePro.Manager.DTO.Interventi;
using System;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneInterventi
{
    public class ResolveDescrizioneIntervento : IResolveDescrizioneIntervento
    {
        private readonly IInterventiRepository _interventiRepository;
        private readonly IAliasResolver _aliasResolver;

        public ResolveDescrizioneIntervento(IInterventiRepository interventiRepository, IAliasResolver aliasResolver)
        {
            this._interventiRepository = interventiRepository;
            this._aliasResolver = aliasResolver;
        }


        public string GetDescrizioneDaCodiceintervento(int codiceIntervento)
        {
            ClassTree<InterventoDto> strutturaAlbero = this._interventiRepository.GetAlberaturaNodoDaId(this._aliasResolver.AliasComune, codiceIntervento);

            var nomeIntervento = new StringBuilder();

            nomeIntervento.Append(strutturaAlbero.Elemento.Descrizione);

            while (strutturaAlbero.NodiFiglio != null && strutturaAlbero.NodiFiglio.Count > 0)
            {
                strutturaAlbero = strutturaAlbero.NodiFiglio[0];

                nomeIntervento.Append(Environment.NewLine);
                nomeIntervento.Append(strutturaAlbero.Elemento.Descrizione);
            }

            return nomeIntervento.ToString();
        }
    }
}
