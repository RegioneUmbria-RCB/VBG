using Init.SIGePro.Data;
using Init.SIGePro.Manager.Validator;
using PersonalLib2.Data;
using System.Collections.Generic;

namespace Init.SIGePro.Manager
{

    ///
    /// File generato automaticamente dalla tabella COMUNIASSOCIATISOFTWARE per la classe ComuniAssociatiSoftware il 10/03/2011 10.43.11
    ///
    ///						ELENCARE DI SEGUITO EVENTUALI MODIFICHE APPORTATE MANUALMENTE ALLA CLASSE
    ///				(per tenere traccia dei cambiamenti nel caso in cui la classe debba essere generata di nuovo)
    /// -
    /// -
    /// -
    /// - 
    ///
    ///	Prima di effettuare modifiche al template di MyGeneration in caso di dubbi contattare Nicola Gargagli ;)
    ///
    public partial class ComuniAssociatiSoftwareMgr : BaseManager
    {
        public ComuniAssociatiSoftwareMgr(DataBase dataBase) : base(dataBase) { }

        public ComuniAssociatiSoftware GetById(string idcomune, int? id)
        {
            var c = new ComuniAssociatiSoftware();


            c.Idcomune = idcomune;
            c.Id = id;

            return (ComuniAssociatiSoftware)this.db.GetClass(c);
        }

        public List<ComuniAssociatiSoftware> GetList(ComuniAssociatiSoftware filtro)
        {
            return this.db.GetClassList(filtro).ToList<ComuniAssociatiSoftware>();
        }

        public ComuniAssociatiSoftware Insert(ComuniAssociatiSoftware cls)
        {
            cls = this.DataIntegrations(cls);

            this.Validate(cls, AmbitoValidazione.Insert);

            this.db.Insert(cls);

            this.ChildInsert(cls);

            return cls;
        }



        private ComuniAssociatiSoftware ChildInsert(ComuniAssociatiSoftware cls)
        {
            return cls;
        }

        private ComuniAssociatiSoftware DataIntegrations(ComuniAssociatiSoftware cls)
        {
            return cls;
        }


        public ComuniAssociatiSoftware Update(ComuniAssociatiSoftware cls)
        {
            this.Validate(cls, AmbitoValidazione.Update);

            this.db.Update(cls);

            return cls;
        }

        public void Delete(ComuniAssociatiSoftware cls)
        {
            this.VerificaRecordCollegati(cls);

            this.EffettuaCancellazioneACascata(cls);

            this.db.Delete(cls);
        }

        private void VerificaRecordCollegati(ComuniAssociatiSoftware cls)
        {
            // Inserire la logica di verifica di integrità referenziale
            // Sollevare un'eccezione di tipo ReferentialIntegrityException nel caso in cui il record sia usato in foreign key in altre tabelle
        }

        private void EffettuaCancellazioneACascata(ComuniAssociatiSoftware cls)
        {
            // Inserire la logica di cancellazione a cascata di dati collegati
        }


        private void Validate(ComuniAssociatiSoftware cls, AmbitoValidazione ambitoValidazione)
        {
            this.RequiredFieldValidate(cls, ambitoValidazione);
        }
    }
}


