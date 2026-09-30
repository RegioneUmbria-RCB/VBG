

using Init.SIGePro.Data;
using Init.SIGePro.Manager.Validator;
using PersonalLib2.Data;
using System.Collections.Generic;

namespace Init.SIGePro.Manager
{

    ///
    /// File generato automaticamente dalla tabella O_ICALCOLOCONTRIBR per la classe OICalcoloContribR il 08/07/2008 10.13.19
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
    public partial class OICalcoloContribRMgr : BaseManager
    {
        public OICalcoloContribRMgr(DataBase dataBase) : base(dataBase) { }

        public OICalcoloContribR GetById(string idcomune, int id)
        {
            var c = new OICalcoloContribR();


            c.Idcomune = idcomune;
            c.Id = id;

            return (OICalcoloContribR)this.db.GetClass(c);
        }

        public List<OICalcoloContribR> GetList(OICalcoloContribR filtro)
        {
            return this.db.GetClassList(filtro).ToList<OICalcoloContribR>();
        }

        public OICalcoloContribR Insert(OICalcoloContribR cls)
        {
            cls = this.DataIntegrations(cls);

            this.Validate(cls, AmbitoValidazione.Insert);

            this.db.Insert(cls);

            this.ChildInsert(cls);

            return cls;
        }



        private OICalcoloContribR ChildInsert(OICalcoloContribR cls)
        {
            return cls;
        }

        private OICalcoloContribR DataIntegrations(OICalcoloContribR cls)
        {
            return cls;
        }




        public void Delete(OICalcoloContribR cls)
        {
            this.VerificaRecordCollegati(cls);

            this.EffettuaCancellazioneACascata(cls);

            this.db.Delete(cls);
        }

        private void VerificaRecordCollegati(OICalcoloContribR cls)
        {
            // Inserire la logica di verifica di integrità referenziale
            // Sollevare un'eccezione di tipo ReferentialIntegrityException nel caso in cui il record sia usato in foreign key in altre tabelle
        }

        private void Validate(OICalcoloContribR cls, AmbitoValidazione ambitoValidazione)
        {
            this.RequiredFieldValidate(cls, ambitoValidazione);
        }
    }
}


