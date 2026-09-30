using Init.SIGePro.Data;
using Init.SIGePro.Manager.Validator;
using PersonalLib2.Data;
using System.Collections.Generic;

namespace Init.SIGePro.Manager
{

    ///
    /// File generato automaticamente dalla tabella ISTANZEFRONTOFFICE per la classe IstanzeFrontOffice il 30/01/2009 10.09.09
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
    public partial class IstanzeFrontOfficeMgr : BaseManager
    {
        public IstanzeFrontOfficeMgr(DataBase dataBase) : base(dataBase) { }

        public IstanzeFrontOffice GetById(string idcomune, int id)
        {
            var c = new IstanzeFrontOffice();


            c.Idcomune = idcomune;
            c.Id = id;

            return (IstanzeFrontOffice)this.db.GetClass(c);
        }

        public List<IstanzeFrontOffice> GetList(IstanzeFrontOffice filtro)
        {
            return this.db.GetClassList(filtro).ToList<IstanzeFrontOffice>();
        }

        public IstanzeFrontOffice Insert(IstanzeFrontOffice cls)
        {
            cls = this.DataIntegrations(cls);

            this.Validate(cls, AmbitoValidazione.Insert);

            this.db.Insert(cls);

            this.ChildInsert(cls);

            return cls;
        }



        private IstanzeFrontOffice ChildInsert(IstanzeFrontOffice cls)
        {
            return cls;
        }

        private IstanzeFrontOffice DataIntegrations(IstanzeFrontOffice cls)
        {
            return cls;
        }


        public IstanzeFrontOffice Update(IstanzeFrontOffice cls)
        {
            this.Validate(cls, AmbitoValidazione.Update);

            this.db.Update(cls);

            return cls;
        }

        public void Delete(IstanzeFrontOffice cls)
        {
            this.VerificaRecordCollegati(cls);

            this.EffettuaCancellazioneACascata(cls);

            this.db.Delete(cls);
        }

        private void VerificaRecordCollegati(IstanzeFrontOffice cls)
        {
            // Inserire la logica di verifica di integrità referenziale
            // Sollevare un'eccezione di tipo ReferentialIntegrityException nel caso in cui il record sia usato in foreign key in altre tabelle
        }

        private void EffettuaCancellazioneACascata(IstanzeFrontOffice cls)
        {
            // Inserire la logica di cancellazione a cascata di dati collegati
        }


        private void Validate(IstanzeFrontOffice cls, AmbitoValidazione ambitoValidazione)
        {
            this.RequiredFieldValidate(cls, ambitoValidazione);
        }
    }
}


