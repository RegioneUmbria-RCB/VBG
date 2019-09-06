using Init.SIGePro.Protocollo.Constants;
using Init.SIGePro.Protocollo.ProtocolloInterfaces;
using Init.SIGePro.Protocollo.WsDataClass;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Protocollo.Prisma.LeggiProtocollo
{
    public class MittentiDestinatariOutArrivo : ILeggiProtoMittentiDestinatari
    {
        IEnumerable<SmistamentoOutXml> _smistamenti;
        IEnumerable<RapportoOutXml> _mittenti;

        public MittentiDestinatariOutArrivo(IEnumerable<SmistamentoOutXml> smistamenti, IEnumerable<RapportoOutXml> mittenti)
        {
            this._smistamenti = smistamenti;
            this._mittenti = mittenti;
        }

        public string InCaricoA { get { return this._smistamenti == null || this._smistamenti.Count() == 0 ? "" : this._smistamenti.Last().UfficioTrasmissione; } }

        public string InCaricoADescrizione { get { return this._smistamenti == null || this._smistamenti.Count() == 0 ? "" : this._smistamenti.Last().DescrizioneUfficioTrasmissione; } }

        public string Flusso { get { return ProtocolloConstants.COD_ARRIVO; } }

        public MittDestOut[] GetMittenteDestinatario()
        {
            var mittenti = new List<MittDestOut>();

            foreach (var mittente in this._mittenti)
            {
                var datoDaVisualizzare = mittente.CognomeNome;

                if (!String.IsNullOrEmpty(mittente.Email))
                {
                    datoDaVisualizzare = $"{mittente.CognomeNome} [{mittente.Email}]";
                }

                mittenti.Add(new MittDestOut { CognomeNome = datoDaVisualizzare });
            }

            return mittenti.ToArray();
        }
    }
}
