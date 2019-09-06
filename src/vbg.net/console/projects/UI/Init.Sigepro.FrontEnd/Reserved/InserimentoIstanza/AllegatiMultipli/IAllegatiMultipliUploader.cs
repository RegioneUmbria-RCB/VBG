using Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneDocumenti;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza.AllegatiMultipli
{
    public interface IAllegatiMultipliUploader
    {
        void AggiungiAllegatoPrincipale(DocumentoDomanda allegatoOriginale, BinaryFile file);
        void AggiungiAllegatoSecondario(DocumentoDomanda allegatoOriginale, int indice, BinaryFile file);
        DocumentoDomanda GetById(int idDocumento);
    }
}
