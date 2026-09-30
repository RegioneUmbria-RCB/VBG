using Init.SIGePro.Data;
using Init.SIGePro.Manager.Validator;
using PersonalLib2.Data;
using System.Collections.Generic;

namespace Init.SIGePro.Manager
{

    ///
    /// File generato automaticamente dalla tabella ISTANZEONERI_CANONI per la classe IstanzeOneri_Canoni il 16/09/2008 18.51.41
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
    public partial class IstanzeOneri_CanoniMgr : BaseManager
    {
        public IstanzeOneri_CanoniMgr(DataBase dataBase) : base(dataBase) { }

        public IstanzeOneri_Canoni GetById(string idcomune, int fk_id_istoneri, int fk_idtestata)
        {
            var c = new IstanzeOneri_Canoni();


            c.Idcomune = idcomune;
            c.FkIdIstoneri = fk_id_istoneri;
            c.FkIdtestata = fk_idtestata;

            return (IstanzeOneri_Canoni)this.db.GetClass(c);
        }

        public List<IstanzeOneri_Canoni> GetList(IstanzeOneri_Canoni filtro)
        {
            return this.db.GetClassList(filtro).ToList<IstanzeOneri_Canoni>();
        }

        public IstanzeOneri_Canoni Insert(IstanzeOneri_Canoni cls)
        {
            cls = this.DataIntegrations(cls);

            this.Validate(cls, AmbitoValidazione.Insert);

            this.db.Insert(cls);

            this.ChildInsert(cls);

            return cls;
        }



        private IstanzeOneri_Canoni ChildInsert(IstanzeOneri_Canoni cls)
        {
            return cls;
        }

        private IstanzeOneri_Canoni DataIntegrations(IstanzeOneri_Canoni cls)
        {
            return cls;
        }


        public IstanzeOneri_Canoni Update(IstanzeOneri_Canoni cls)
        {
            this.Validate(cls, AmbitoValidazione.Update);

            this.db.Update(cls);

            return cls;
        }

        public void Delete(IstanzeOneri_Canoni cls)
        {
            this.VerificaRecordCollegati(cls);

            this.EffettuaCancellazioneACascata(cls);

            this.db.Delete(cls);
        }

        private void VerificaRecordCollegati(IstanzeOneri_Canoni cls)
        {
            // Inserire la logica di verifica di integrità referenziale
            // Sollevare un'eccezione di tipo ReferentialIntegrityException nel caso in cui il record sia usato in foreign key in altre tabelle
        }

        private void EffettuaCancellazioneACascata(IstanzeOneri_Canoni cls)
        {
            // Inserire la logica di cancellazione a cascata di dati collegati
        }


        private void Validate(IstanzeOneri_Canoni cls, AmbitoValidazione ambitoValidazione)
        {
            this.RequiredFieldValidate(cls, ambitoValidazione);
        }
    }
}


