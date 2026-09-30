using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Protocollo.DocArea.Interfaces
{
    public interface IDatiDocAreaSegnaturaApplicativoProtocollo
    {
        string Indirizzo {get;}
        /// <summary>
        /// Indicare qui l'indirizzo senza specificare il toponimo, Esempio corretto: Garibaldi, esempio non corretto: Via Garibaldi
        /// </summary>
        string DescrizioneIndirizzo { get; }
        string Localita { get; }
        string Provincia { get; }
        string SiglaProvincia { get; }
        string Cap { get; }
        string CodiceIstatComune { get; }
        string Comune { get; }
        string Toponimo { get; }
        string Mezzo { get; }
        string ModalitaInvio { get; }
    }
}
